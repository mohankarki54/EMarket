package emarket.emarket.controller;

import emarket.emarket.Service.ProductService;
import emarket.emarket.bean.ChargeRequest;
import emarket.emarket.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;

@Controller
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private ProductService productService;

    @RequestMapping("/feature/{id}")
    public String checkout(@PathVariable(name = "id") int id, Model model){
        int amount = (int) Math.ceil(2.99 * 100);
        Product product = productService.get(Long.valueOf(id));
        if (product != null) {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            product.setImagename(imagename);
        }
        model.addAttribute("product", product);
        model.addAttribute("productid", id);
        model.addAttribute("amount", amount);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        model.addAttribute("fee", 2.99);
        return "checkout";
    }


}
