package emarket.emarket.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import emarket.emarket.Service.ProductService;
import emarket.emarket.Service.StripeService;
import emarket.emarket.bean.ChargeRequest;
import emarket.emarket.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.sasl.AuthenticationException;
import java.util.Calendar;
import java.util.Date;

@Controller
public class ChargeController {

    @Autowired
    private StripeService paymentsService;

    @Autowired
    private ProductService productService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, RedirectAttributes model)
            throws StripeException, AuthenticationException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
        Long productid = chargeRequest.getProductid();
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("payment", true);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        //model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        Product product = productService.get(productid);
        Date date = product.getEnddate();
        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // manipulate date
        c.add(Calendar.DATE, 10); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        // convert calendar to date
        Date newDate = c.getTime();

        productService.updateSponsorFlag(true, newDate,productid);

        return "redirect:/listedproduct";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "listedproduct";
    }
}
