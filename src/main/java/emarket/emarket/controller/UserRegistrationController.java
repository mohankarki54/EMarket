package emarket.emarket.controller;

import emarket.emarket.Repository.ConfirmationTokenRepository;
import emarket.emarket.Service.EmailService;
import emarket.emarket.Service.ProductService;
import emarket.emarket.Service.UserService;
import emarket.emarket.bean.ConfirmationToken;
import emarket.emarket.bean.User;
import emarket.emarket.DTO.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/signup")
public class UserRegistrationController {


    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

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
    public ModelAndView registerUserAccount(@ModelAttribute("user")UserRegistrationDto registrationDto, BindingResult result, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs){

        User existing = userService.findByEmail(registrationDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with this email");
        }

        if (result.hasErrors()) {
            modelAndView.addObject("message", "There is already an account registered with this email");
            modelAndView.setViewName("signup");
            return modelAndView;
        }
        else{
            userService.save(registrationDto);
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

            modelAndView.addObject("var", true);
            modelAndView.addObject("verify","A verification email has been sent to " + registrationDto.getEmail());
            modelAndView.setViewName("redirect:/home");

        }
        return modelAndView;
    }

}
