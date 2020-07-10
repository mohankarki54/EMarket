package emarket.emarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import emarket.emarket.bean.User;

@Controller
public class HomeController {

    @GetMapping("/home")
    public  String check(){
        return  "home";
    }

    @GetMapping(path = "/login")
    public String login(){
        //Prints from the database displaying the login bar

        return "login";
    }

    @GetMapping(path = "/signup")
    public String singup(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String doRegistration(@ModelAttribute("user") User user){
        System.out.println(user.getFirstname());
        System.out.println(user.getLastname());
        System.out.println(user.getEmail());
        return "home";
    }


    @GetMapping(path = "/about")
    public String aboutus(){ return "about"; }

}
