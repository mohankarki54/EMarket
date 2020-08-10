package emarket.emarket.controller;

import emarket.emarket.Service.ProductService;
import emarket.emarket.Service.UserService;
import emarket.emarket.bean.Product;
import emarket.emarket.bean.Search;
import emarket.emarket.bean.User;
import emarket.emarket.DTO.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/signup")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService service;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
       return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "signup";
    }

    @PostMapping
    public ModelAndView registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto, BindingResult result, ModelAndView modelAndView){

        User existing = userService.findByEmail(registrationDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with this email");
        }

        if (result.hasErrors()) {
            modelAndView.addObject("message", "There is already an account registered with this email");
            modelAndView.setViewName("signup");
            return modelAndView;
        }

        userService.save(registrationDto);

        List<Product> products = service.listAll();
        if (products != null) {

            for (Product product : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }

            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
        }

        modelAndView.addObject("success", "Thank you for signing up for EMarket.");
        modelAndView.setViewName("home");
        return modelAndView;

    }
}
