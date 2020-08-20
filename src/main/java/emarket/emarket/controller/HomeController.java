package emarket.emarket.controller;


import emarket.emarket.DTO.FavBean;
import emarket.emarket.Repository.ConfirmationTokenRepository;
import emarket.emarket.Service.*;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
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
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FavService favService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = {"/","/home"})
    public ModelAndView root(ModelAndView modelAndView) {

      List<Product> products = service.listAll();
      List<Product> electronic = service.categoryList("electronics");
      List<Product> vehicle = service.categoryList("vehicle");
      List<Product> beauty = service.categoryList("beauty");
      List<Product> clothes= service.categoryList("clothes");
        if (products != null) {

        for (Product product : products) {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            product.setImagename(imagename);
        }

        modelAndView.addObject("products", products);
        modelAndView.addObject("search", new Search());
        modelAndView.addObject("count", products.size());
        modelAndView.addObject("ecount", electronic.size());
        modelAndView.addObject("vcount", vehicle.size());
        modelAndView.addObject("bcount", beauty.size());
        modelAndView.addObject("ccount", clothes.size());
        }

        if(Account.instance.currentUserName() != "anonymousUser" && Account.instance.currentUserName() != null ){
            User user = userService.findByEmail(Account.instance.currentUserName());
            boolean add = user.isAddress();
            if(add){
                modelAndView.addObject("address", true);
            }
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


    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/favorite")
    public ModelAndView displayFavroite(ModelAndView modelAndView){
        List<Favroite> favroites = favService.favroiteSearch(Account.instance.currentUserName());
        ArrayList<FavBean> favproducts = new ArrayList<FavBean>() {};

        for(Favroite fav: favroites){
            Product product = service.get(fav.getProductid());
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            FavBean f = new FavBean(fav.getOwner(),product,fav.getId(),imagename);
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

    /*@GetMapping("/resendActivation")
    public ModelAndView resendActivation(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response){
        User user = ;
        ConfirmationToken confirmationToken = new ConfirmationToken(userService.findByEmail(registrationDto.getEmail()));
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(registrationDto.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("technewsandblog@gmail.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        mailMessage.setText("To confirm your account, please click here : "
                +url+ "/confirm-account?token="+confirmationToken.getConfirmationtoken());

        emailService.sendRegisterEmail(mailMessage);

        modelAndView.addObject("verify","A re-verification email has been sent to " + registrationDto.getEmail());
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }*/

}