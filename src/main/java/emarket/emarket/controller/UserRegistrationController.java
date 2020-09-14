package emarket.emarket.controller;

import emarket.emarket.Repository.ConfirmationTokenRepository;
import emarket.emarket.Service.EmailService;
import emarket.emarket.Service.UserService;
import emarket.emarket.bean.ConfirmationToken;
import emarket.emarket.bean.Mail;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/signup")
public class UserRegistrationController {


    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
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
            registrationDto.setJoined(new Date());
            userService.save(registrationDto);
            ConfirmationToken confirmationToken = new ConfirmationToken(userService.findByEmail(registrationDto.getEmail()));
            confirmationTokenRepository.save(confirmationToken);

            Mail mail = new Mail();
            mail.setFrom("emarketofficial123@gmail.com");
            mail.setTo(registrationDto.getEmail());
            mail.setSubject("Welcome to eMarket");
            Map<String, Object> model = new HashMap<>();
            model.put("token", confirmationToken.getConfirmationtoken());
            model.put("user", registrationDto.getFirstname());
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            model.put("activateUrl", url + "/confirm-account?token="+confirmationToken.getConfirmationtoken());
            mail.setModel(model);
            emailService.sendRegisterEmail(mail);

            modelAndView.addObject("var", true);
            modelAndView.addObject("verify","A verification email has been sent to " + registrationDto.getEmail());
            modelAndView.setViewName("redirect:/home");

        }
        return modelAndView;
    }

}
