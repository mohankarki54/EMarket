package emarket.emarket.controller;


import emarket.emarket.DTO.FavBean;
import emarket.emarket.Repository.ConfirmationTokenRepository;
import emarket.emarket.Service.*;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


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

    @Autowired
    private TimeCalculation timeCalculation;

    @GetMapping(value = {"/","/home"})
    public ModelAndView root(ModelAndView modelAndView) {
        Date currentDate = new Date();
        if(Account.instance.currentUserName() != "anonymousUser" && Account.instance.currentUserName() != null ){
            User user = userService.findByEmail(Account.instance.currentUserName());
            boolean add = user.isAddress();
            if(!add){
                modelAndView.addObject("address", true);
            }
            List<Product> userProduct = service.listbyOwner(Account.instance.currentUserName());
            if(userProduct.size() != 0){
                modelAndView.addObject("promoteFlag", true);
            }
        }


        List<Product> allDateProduct = service.findProductByEnddate();

        for(int i =0; i< allDateProduct.size(); i++){
            favService.deleteFavProduct(allDateProduct.get(i).getId());
        }
        service.deleteProduct();


      List<Product> products = service.sponsorProduct(true);
      Collections.shuffle(products);
      List<Product> electronic = service.categoryList("electronics");
      List<Product> vehicle = service.categoryList("vehicle");
      List<Product> beauty = service.categoryList("beauty");
      List<Product> clothes= service.categoryList("clothes");
      List<Product> realState= service.categoryList("estate");
      List<Product> furniture= service.categoryList("furniture");

      Date elecDate = service.latestDate("electronics");
      Date vehDate = service.latestDate("vehicle");
      Date beaDate  = service.latestDate("beauty");
      Date cloDate = service.latestDate("clothes");
      Date realDate = service.latestDate("esate");
      Date furDate = service.latestDate("furniture");

      String electroDate = timeCalculation.timeDifference(currentDate, elecDate);
      String vehicleDate = timeCalculation.timeDifference(currentDate, vehDate);
      String beautyDate = timeCalculation.timeDifference(currentDate, beaDate);
      String clothesDate = timeCalculation.timeDifference(currentDate, cloDate);
      String estateDate = timeCalculation.timeDifference(currentDate, realDate);
      String furnitureDate = timeCalculation.timeDifference(currentDate, furDate);

      if (products.size() != 0) {
        for (Product product : products) {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            product.setImagename(imagename);
        }
        modelAndView.addObject("products", products);
        modelAndView.addObject("present", true);
      }

      for(int i = 1; i <= products.size(); i++){
          modelAndView.addObject("product" + i, products.get(i-1));
      }

        modelAndView.addObject("search", new Search());
        modelAndView.addObject("count", products.size());
        modelAndView.addObject("ecount", "Available: "+ electronic.size());
        modelAndView.addObject("vcount", "Available: "+ vehicle.size());
        modelAndView.addObject("bcount", "Available: "+ beauty.size());
        modelAndView.addObject("ccount", "Available: "+ clothes.size());
        modelAndView.addObject("rcount", "Available: "+ realState.size());
        modelAndView.addObject("fcount", "Available: "+ furniture.size());


        modelAndView.addObject("electroDate", electroDate);
        modelAndView.addObject("vehicleDate", vehicleDate);
        modelAndView.addObject("beautyDate", beautyDate);
        modelAndView.addObject("clothesDate", clothesDate);
        modelAndView.addObject("esateDate", estateDate);
        modelAndView.addObject("furnitureDate", furnitureDate);

        Contact contact = new Contact();
        modelAndView.addObject("contact", contact);

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

        if(favroites.size() == 0){
            modelAndView.addObject("nodata", true);
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

    @PostMapping(path = {"/sendEmailtoAdmin"})
    public String sendEmailToAdmin(RedirectAttributes redirectAttrs, @ModelAttribute("contact") Contact contact, HttpServletRequest request ){
        redirectAttrs.addAttribute("contact","Thank you for reaching the us. One of the associate will soon contact you as soon as possible." );
        Mail mail = new Mail();
        mail.setFrom("emarketofficial123@gmail.com");
        mail.setTo("emarketofficial123@gmail.com");
        mail.setSubject("Attention: User contact");
        Map<String, Object> model = new HashMap<>();
        model.put("customer_email", "Customer Email: "+contact.getEmail());
        model.put("phone", "Phone number: " + contact.getPhonenum());
        model.put("msg", "Message: "+contact.getMessage());
        mail.setModel(model);
        emailService.sendcontactEmailAdmin(mail);
        return "redirect:/home";
    }

}