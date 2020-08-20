package emarket.emarket.controller;

import emarket.emarket.Repository.AddressRepository;
import emarket.emarket.Service.AddressService;
import emarket.emarket.Service.UserService;
import emarket.emarket.bean.Account;
import emarket.emarket.bean.Address;
import emarket.emarket.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;

    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelAndView modelAndView) {
        User user = userService.findByEmail(Account.instance.currentUserName());
        String hello = "Hello, " + user.getFirstname();
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
