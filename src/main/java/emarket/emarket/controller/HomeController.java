package emarket.emarket.controller;

import emarket.emarket.bean.Account;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import emarket.emarket.bean.User;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @GetMapping(value = {"/","/home"})
    public String root() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}