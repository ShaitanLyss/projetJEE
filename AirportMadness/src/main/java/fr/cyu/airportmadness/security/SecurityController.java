package fr.cyu.airportmadness.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.oidc.OidcIdToken;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

@Controller
public class SecurityController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private static String affiche;
    @Autowired
    UserRepository userRepository;


    public SecurityController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

//    @ResponseBody
//    @GetMapping("/login")
//    public String Login(HttpServletResponse res, Model model,
//                        @RequestParam(value = "login") String name, @RequestParam(value = "password") String psswd) {
//        res.setContentType("text/plain");
//        User user = userRepository.findByUsernameLike(name);
//
//        return (user == null) ? "login inexistant" : (
//                user.getPassword().equals(psswd) ? name +
//                        " connecté " : " forgotten password");
//    }

    @GetMapping("/saveUser")
    @ResponseBody
    public String saveUser(@RequestParam(value = "login") String name,
                           @RequestParam(value = "password") String psswd,
                           @RequestParam(value = "role") String role) {
        User user = new User();
        user.setUsername(name).setPassword(psswd).setRole(role);
        userRepository.save(user);
        return "User bien créé";
    }

//    @RequestMapping("/admin")
//    @RolesAllowed("ADMIN")
//    public String getAdmin() {
//        return affiche;
//    }
//
//    @RequestMapping("/**")
//    @RolesAllowed("USER")
//    public String getUser() {
//        return affiche;
//    }

//    @RequestMapping("/*")
//    public String getUserInfo(Principal user) {
//        StringBuffer userInfo = new StringBuffer();
//        if (user instanceof UsernamePasswordAuthenticationToken) {
//            userInfo.append(getUsernamePasswordLoginInfo(user));
//        } else {
//            if (user instanceof OAuth2AuthenticationToken) {
//                userInfo.append(getOauth2LoginInfo(user));
//            }
//        }
//        affiche = userInfo.toString();
//        return userInfo.toString();
//    }

    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
        StringBuffer usernameInfo = new StringBuffer();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) user;
        if (token.isAuthenticated()) {
            org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) token.getPrincipal();
            usernameInfo.append("Welcome" + u.getUsername());
        } else {
            usernameInfo.append("NA");
        }
        return usernameInfo;

    }

//    private StringBuffer getOauth2LoginInfo(Principal user) {
//
//        StringBuffer protectedInfo = new StringBuffer();
//
//        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
//        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
//        OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();
//        if (authToken.isAuthenticated()) {
//
//            Map<String, Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();
//
//            String userToken = authClient.getAccessToken().getTokenValue();
//            //if(authClient != null) return  (protectedInfo.append("les attributs sont : "+ userAttributes.toString()));
//            protectedInfo.append("Welcome, " + ((userAttributes.get("login") != null) ? userAttributes.get("login") : userAttributes.get("name")) + "<br><br>");
//            protectedInfo.append("e-mail: " + userAttributes.get("email") + "<br><br>");
//            protectedInfo.append("Access Token: " + userToken + "<br><br>");
//
//            //si l'utilisateur utilise un openId
//            OidcIdToken idToken = getIdToken(principal);
//            if (idToken != null) {
//                protectedInfo.append("idToken value : " + idToken.getTokenValue() + "<br><br>");
//                protectedInfo.append("Token mapped values : <br><br>");
//                Map<String, Object> claims = idToken.getClaims();
//                for (String key : claims.keySet()) {
//                    protectedInfo.append("     " + key + ":    " + claims.get(key) + "<br>");
//                }
//            }
//
//        } else {
//            protectedInfo.append("NA");
//        }
//
//        return protectedInfo;
//    }
//
//    private OidcIdToken getIdToken(OAuth2User principal) {
//        if (principal instanceof DefaultOidcUser oidcUser) {
//            return oidcUser.getIdToken();
//        }
//        return null;
//    }


}
