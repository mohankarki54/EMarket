package emarket.emarket.controller;

import emarket.emarket.Repository.AddressRepository;
import emarket.emarket.Service.AddressService;
import emarket.emarket.Service.FavService;
import emarket.emarket.Service.ProductService;
import emarket.emarket.Service.UserService;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ProductService service;
    @Autowired
    private FavService favService;


    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelAndView modelAndView) {
        List<Product> listProducts = service.listbyOwner(Account.instance.currentUserName());
        List<Favroite> favroites = favService.favroiteSearch(Account.instance.currentUserName());
        User user = userService.findByEmail(Account.instance.currentUserName());
        String hello = "Hello, " + user.getFirstname();
        Address address = addressService.findByEmail(user.getEmail());
        if(address != null){
            modelAndView.addObject("address", "Address");
            modelAndView.addObject("street", "Street: "+ address.getStreet().toUpperCase());
            modelAndView.addObject("apartment", "Block: "+ address.getApartment().toUpperCase());
            modelAndView.addObject("city", "City: " + address.getCity().toUpperCase());
            modelAndView.addObject("state","State: " + address.getState().toUpperCase());
            modelAndView.addObject("zipcode","Zip Code: " + address.getZipcode());
        }
        if(listProducts != null){
            modelAndView.addObject("listed","Total Active listed product: " + listProducts.size());
        }

        if(favroites !=null){
            modelAndView.addObject("favorite","Total Favorite item count: " + favroites.size());
        }



        modelAndView.addObject("username", hello);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @GetMapping("/address")
    public String addressform(Model model){
        Address address = new Address();
        model.addAttribute("address", address);
        return "address";
    }

    @PostMapping("/address")
    public String saveAddress(@ModelAttribute("address") Address address){
        String owner = Account.instance.currentUserName();
        User user = userService.findByEmail(owner);
        String street = address.getStreet().toLowerCase();
        String apartment = address.getApartment();
        String city = address.getCity().toLowerCase();
        String state = address.getState().toLowerCase();
        int zipcode = address.getZipcode();
        Address add= new Address(owner,street,apartment,city,state,zipcode);
        Address address1 = addressRepository.findAddressByOwner(owner);
        if (address1 == null){
            addressRepository.save(add);
        }
        else{
            addressService.updateAddress(street,apartment,city,state,zipcode, address1.getId());
        }
        userService.updateAddressFlag(true, user.getId());
        return "redirect:/dashboard";
    }


}
