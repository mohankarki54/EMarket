package emarket.emarket.controller;

import emarket.emarket.bean.ChargeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @RequestMapping("/feature/{id}")
    public String checkout(@PathVariable(name = "id") int id, Model model){
        int amount = (int) Math.ceil(2.99 * 100);
        model.addAttribute("productid", id);
        model.addAttribute("amount", amount);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        model.addAttribute("fee", 2.99);
        return "checkout";
    }


}
