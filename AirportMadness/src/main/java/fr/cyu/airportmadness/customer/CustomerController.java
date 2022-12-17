package fr.cyu.airportmadness.customer;

import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomerController {
    @GetMapping("/booking")
    public String bookingForm(Model model) {
        model.addAttribute(
                "booking", new Booking()
                        .setNumLuggages(0)
        );
        return "customer/booking";
    }
    @GetMapping("/")
    public String welcomePage(Authentication authentication, Model model) {
        System.out.println(authentication);
        if(authentication != null){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            model.addAttribute("user", userDetails);

        }
        else {

        }
        return "index";
    }

}
