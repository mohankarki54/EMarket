package emarket.emarket.controller;

import emarket.emarket.Service.ProductService;
import emarket.emarket.Service.UserService;
import emarket.emarket.bean.Product;
import emarket.emarket.bean.Search;
import emarket.emarket.bean.User;
import emarket.emarket.DTO.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/signup")
public class UserRegistrationController {

    @Autowired
    protected AuthenticationManager authenticationManager;

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
    public ModelAndView registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto, BindingResult result, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response){

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
        authWithAuthManager(request,registrationDto.getEmail(),registrationDto.getPassword());

        modelAndView.setViewName("redirect:/home");
        return modelAndView;

    }

    public void authWithAuthManager(HttpServletRequest request, String email, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
