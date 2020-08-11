package emarket.emarket.controller;

import emarket.emarket.Repository.ProductRepository;
import emarket.emarket.Service.ProductService;
import emarket.emarket.bean.Product;
import emarket.emarket.bean.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @Autowired
    private ProductService service;

    @GetMapping(value = {"/","/home", "/productInfo"})
    public ModelAndView root(ModelAndView modelAndView) {

        List<Product> products = service.listAll();

        if (products != null) {

        for (Product product : products) {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            product.setImagename(imagename);
        }

        modelAndView.addObject("products", products);
        modelAndView.addObject("search", new Search());
    }
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @PostMapping(path = {"/productInfo"})
    public ModelAndView displayInfo(@RequestParam String action, ModelAndView modelAndView ){
        int value = Integer.parseInt(action);
        List<Product> products = new ArrayList<Product>();
        products.add(service.get(value));
        if (products != null) {

            for (Product product : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }

            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
        }
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping(value = {"/productdisplay"})
    public ModelAndView preRoot(ModelAndView modelAndView) {

        Search.instance.setWord(" ");
        List<Product> products = new ArrayList<Product>();
        products = service.productSearch(Search.instance.getWord());
        if (products != null) {

            for (Product product : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }

            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
        }
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @PostMapping(path = {"/productdisplay"})
    public ModelAndView displaypro(@ModelAttribute("search") Search search, ModelAndView modelAndView){
        Search.instance.setWord(search.getWord());
        List<Product> products = new ArrayList<Product>();
        products = service.productSearch(search.getWord());
        if (products != null) {

            for (Product product : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }

            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
        }
        modelAndView.setViewName("home");
        return modelAndView;
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