package emarket.emarket.controller;

import emarket.emarket.Service.UserService;
import emarket.emarket.bean.User;
import emarket.emarket.DTO.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class UserRegistrationController {

    private UserService userService;

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
    public String registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto, BindingResult result){

        User existing = userService.findByEmail(registrationDto.getEmail());
        if (existing != null) {
            System.out.println("Already registered");
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "signup";
        }

        userService.save(registrationDto);
        return "home";
    }
}
