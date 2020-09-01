package emarket.emarket.controller;

import emarket.emarket.Service.ProductService;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private ProductService service;

    @GetMapping(value = {"/category/{value}"})
    public ModelAndView root(@PathVariable(name = "value") String value, ModelAndView modelAndView) {
        Helper.instance.setCategory(value);
        List<Product> products = service.categoryList(value);
        if (products != null) {

            for (Product product : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }

            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
            modelAndView.addObject("count", products.size());
        }
        modelAndView.setViewName("categoryDisplay");
        return modelAndView;
    }

}
