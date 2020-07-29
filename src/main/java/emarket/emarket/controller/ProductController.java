package emarket.emarket.controller;

import emarket.emarket.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/product")
    public String product(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "Productform";
    }

    @RequestMapping(value = "product", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product){
        System.out.println(product.getName());
        System.out.println(product.getProductType());
        return "home";
    }

}
