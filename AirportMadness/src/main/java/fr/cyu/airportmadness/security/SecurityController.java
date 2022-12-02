package fr.cyu.airportmadness.security;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityController {

    private final  OAuth2AuthorizedClientService  authorizedClientService;
    //private String affiche;
    private String name;
    @Autowired
    UserRepository userRepository;


    public SecurityController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @ResponseBody
        @GetMapping("/login")
        public String Login(HttpServletResponse res, Model model,
                            @RequestParam (value = "login") String name, @RequestParam (value = "password") String psswd){
            res.setContentType("text/plain");
            User user = userRepository.findByUsernameLike(name);

            return (user == null) ? "login inexistant" : (
                    user.getPassword().equals(psswd) ? name +
                            " connecté " : " forgotten password");
        }
        @GetMapping("/saveUser")
        @ResponseBody
            public String saveUser(@RequestParam (value = "login") String name,
                                   @RequestParam (value = "password") String psswd,
                                   @RequestParam (value = "role") String role){
            User user = new User();
            user.setUsername(name).setPassword(psswd).setRole(role);
            userRepository.save(user);
            return "User bien créé";
        }
        @RequestMapping("/admin")
        @RolesAllowed("ADMIN")
        public String getAdminPage(Model model){
            model.addAttribute("name", this.getName());
            return "index";
            }

        @RequestMapping("/**")
        @RolesAllowed("USER")
        public String getUserPage(Model model){
            model.addAttribute("name", this.getName());
            return "index";
        }

        @RequestMapping("/*")
        public String getUserInfo(Principal user) throws IOException {
            StringBuffer userInfo = new StringBuffer();
            if(user instanceof UsernamePasswordAuthenticationToken){
                userInfo.append(getUsernamePasswordLoginInfo(user));
            }else{
                if(user instanceof OAuth2AuthenticationToken){
                userInfo.append(getOauth2LoginInfo(user));
                }
            }
            //affiche = userInfo.toString();
            String pageUrl = "index";
            String   templatePath = "/templates/";
            ClasspathLoader loader = new ClasspathLoader();
            loader.setPrefix("templates/");
            loader.setSuffix(".html");
            //PebbleEngine engine = new PebbleEngine(loader);
            PebbleEngine engine = new PebbleEngine.Builder().loader(loader).build();
            PebbleTemplate compiledTemplate = engine.getTemplate(pageUrl);

            //PebbleEngine engine = new PebbleEngine.Builder().build();
            //PebbleTemplate compiledTemplate = engine.getTemplate(pageUrl);
            Writer writer = new StringWriter();

            Map<String, Object> context = new HashMap<>();
            context.put("name", this.getName());
            context.put("authenticated", 1);
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();

            //model.addObject("name", this.getName());
            //model.setViewName("index");
            return output;
        }
        private StringBuffer getUsernamePasswordLoginInfo(Principal user){
            StringBuffer usernameInfo = new StringBuffer();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
            if(token.isAuthenticated()){
                org.springframework.security.core.userdetails.User u =  (org.springframework.security.core.userdetails.User)token.getPrincipal();
                usernameInfo.append("Welcome" + u.getUsername());
                this.setName(u.getUsername());
            }else{
                usernameInfo.append("NA");
            }
            return usernameInfo;

        }
    private StringBuffer getOauth2LoginInfo(Principal user){

        StringBuffer protectedInfo = new StringBuffer();

        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
        OAuth2User principal = ((OAuth2AuthenticationToken)user).getPrincipal();
        if(authToken.isAuthenticated()){

            Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

            String userToken = authClient.getAccessToken().getTokenValue();
            //if(authClient != null) return  (protectedInfo.append("les attributs sont : "+ userAttributes.toString()));

            if ((userAttributes.get("login") != null)) {
                this.setName((String) userAttributes.get("login"));
            } else {
                this.setName((String) userAttributes.get("name"));
            }
            protectedInfo.append("Welcome, " + ((userAttributes.get("login")!=null)?userAttributes.get("login"):userAttributes.get("name"))+"<br><br>");
            protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
            protectedInfo.append("Access Token: " + userToken+"<br><br>");

            //si l'utilisateur utilise un openId
            OidcIdToken idToken = getIdToken(principal);
            if(idToken != null){
                protectedInfo.append("idToken value : "+ idToken.getTokenValue()+"<br><br>");
                protectedInfo.append("Token mapped values : <br><br>");
                Map<String, Object> claims = idToken.getClaims();
                for(String key : claims.keySet()){
                    protectedInfo.append("     "+ key +":    " + claims.get(key) + "<br>");
                }
            }

        }
        else{
            protectedInfo.append("NA");
        }

        return protectedInfo;
    }

    private OidcIdToken getIdToken(OAuth2User principal){
       if(principal instanceof DefaultOidcUser){
           DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
           return oidcUser.getIdToken();
       }
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
