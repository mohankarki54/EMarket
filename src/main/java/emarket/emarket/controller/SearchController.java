package emarket.emarket.controller;

import emarket.emarket.Service.AddressServiceImpl;
import emarket.emarket.Service.FavService;
import emarket.emarket.Service.ProductService;
import emarket.emarket.Service.UserServiceImpl;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductService service;
    @Autowired
    private FavService favService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AddressServiceImpl addressService;

    @GetMapping(value = {"/productdisplay"})
    public ModelAndView preRoot(@ModelAttribute("search") Search search, ModelAndView modelAndView) {
        String word = " ";
        if(search.getWord() != null){
            Search.instance.setWord(search.getWord());
            word = Search.instance.getWord();
        }
        else{
            word = Search.instance.getWord();
        }

        List<Product> products = new ArrayList<Product>();
        products = service.productSearch(word);
        if (products != null) {

            for (Product product : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }

            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
        }
        if(products.size() == 0){
            modelAndView.addObject("nodata","Sorry, currently there is no listing available near this zip code.");
        }

        modelAndView.setViewName("Searchshow");
        return modelAndView;
    }

    @GetMapping(value = {"/zipsearch"})
    public ModelAndView zipsearch(@ModelAttribute("search") Search search, ModelAndView modelAndView) {

        int zipcode = 0;
        try{
            zipcode = Integer.parseInt(search.getWord());
        }
        catch (Exception e){
            modelAndView.addObject("zip", "Zipcode Error");
            modelAndView.setViewName("redirect:/home");
            return modelAndView;
        }

        ArrayList<Integer> zip = new ArrayList<Integer>();
        zip.add(zipcode-2);
        zip.add(zipcode-1);
        zip.add(zipcode);
        zip.add(zipcode + 1);
        zip.add(zipcode + 2);
        zip.add(zipcode + 3);

        ArrayList<Address> address = new ArrayList<Address>();

        for(int i = 0; i<zip.size();i++){
            List<Address> addresses  = addressService.findAddressByZipcode(zip.get(i));
            for (int k = 0; k< addresses.size(); k++){
                address.add(addresses.get(k));
            }
        }

        ArrayList<String> owner = new ArrayList<String>();
        for(int i = 0; i < address.size(); i++){
            owner.add(address.get(i).getOwner());
        }
        ArrayList<Product> products =  new ArrayList<Product>();
        for(int i = 0; i < owner.size(); i++){
            List<Product> pr = service.listbyOwner(owner.get(i));
            for(int j = 0; j < pr.size(); j++){
                products.add(pr.get(j));
            }
        }
        if (products != null) {
            for (Product product : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }
            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
        }
        if(products.size() == 0){
            modelAndView.addObject("nodata","Sorry, currently there is no listing available near this zip code.");
        }
        modelAndView.setViewName("Searchshow");
        return modelAndView;
    }

    @PostMapping(path = {"/fav"})
    public String addFav(@RequestParam String action, RedirectAttributes redirectAttrs ){
        int value = Integer.parseInt(action);
        Product product = service.get(value);
        String owner = Account.instance.currentUserName();
        Favroite favroite = new Favroite(owner, product.getId());
        favService.save(favroite);
        redirectAttrs.addAttribute("success","Added to the Favorite List" );
        return "redirect:/productdisplay";
    }

    /*@PostMapping(path = {"/productdisplay"})
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
    }*/

}
