package emarket.emarket.controller;

import emarket.emarket.Repository.ProductRepository;
import emarket.emarket.bean.Account;
import emarket.emarket.bean.Product;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import emarket.emarket.bean.User;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = {"/","/home"})
    public ModelAndView root(ModelAndView modelAndView) {

        List<Product> products  = productRepository.findAll();
       for(Product product : products)
        {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            product.setImagename(imagename);
        }
        modelAndView.addObject("products", products);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @PostMapping(params = "info", path = {"/","/home"})
    public String displayInfo(@RequestParam("info") int index){
        System.out.println(index);
        return "about";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}