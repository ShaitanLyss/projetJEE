package fr.cyu.airportmadness.customer;

import fr.cyu.airportmadness.entity.booking.Booking;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class CustomerController {
    @GetMapping("/booking")
    public String bookingForm(Model model) {
        model.addAttribute(
                "booking", new Booking()
                        .setNumLuggages(0)
        );
        return "customer/booking";
    }
}
