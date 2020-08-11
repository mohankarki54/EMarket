package emarket.emarket.controller;

import emarket.emarket.Service.FavService;
import emarket.emarket.Service.ProductService;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HomeController {

    @Autowired
    private ProductService service;

    @Autowired
    private FavService favService;

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

    @PostMapping(path = {"/addFav"})
    public String addFav(@RequestParam String action, RedirectAttributes redirectAttrs ){
        int value = Integer.parseInt(action);
        Product product = service.get(value);
        String owner = Account.instance.currentUserName();
        Favroite favroite = new Favroite(owner, product.getId());
        favService.save(favroite);
        redirectAttrs.addAttribute("success","Added to the Favorite List" );
        return "redirect:/";
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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/favorite")
    public ModelAndView displayFavroite(ModelAndView modelAndView){
        List<Favroite> favroites = favService.favroiteSearch(Account.instance.currentUserName());
        ArrayList<FavBean> favproducts = new ArrayList<FavBean>() {};

        for(Favroite fav: favroites){
            FavBean f = new FavBean(fav.getOwner(),service.get(fav.getProductid()),fav.getId());
            favproducts.add(f);
        }

        modelAndView.addObject("listProducts", favproducts);
        modelAndView.addObject("fav", favroites);
        modelAndView.setViewName("favorite");
        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable(name = "id") int id, RedirectAttributes redirectAttrs) {
        favService.delete(id);
        redirectAttrs.addAttribute("success","Successfully removed." );
        return "redirect:/favorite";
    }


}