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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping(value = {"/","/home"})
    public  ModelAndView home(ModelAndView modelAndView, @ModelAttribute("account") Account account){

        if(Account.instance.getIsauthenciated()){
            account.setIsauthenciated(true);
        }
        else {
            account.setIsauthenciated(false);
        }
        System.out.println(account.getIsauthenciated());
        modelAndView.addObject("isAuthenciated", account.getIsauthenciated());
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping(path = "/login")
    public String login(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView dologin(ModelAndView modelAndView, @ModelAttribute("account") Account account){

        String sql = "SELECT pass FROM user WHERE email = ?";
        String userpass = jdbcTemplate.queryForObject(sql, new Object[]{account.getEmail()}, String.class);
        boolean status = checkPass(account.getPassword(),userpass);
        if (status){
            Account.instance.setIsauthenciated(true);
            modelAndView.addObject("isAuthenciated", true);
            modelAndView.setViewName("home");
        }
        else{
            Account.instance.setIsauthenciated(false);
            modelAndView.addObject("message", "Incorrect password. Try again.");
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @GetMapping(value = {"/melogout"})
    public  ModelAndView logout(ModelAndView modelAndView, @ModelAttribute("account") Account account){

        Account.instance.setIsauthenciated(false);
        account.setIsauthenciated(false);
        modelAndView.addObject("isAuthenciated", false);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    /*@GetMapping(path = "/signup")
    public String singup(Model model){
        //User user1 = new User();
        //model.addAttribute("user1", user1);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String doRegistration(@ModelAttribute("user1") User user){

        if(isUniqueEmail(user.getEmail())){
            String pwd = user.getPassword();
            String encryptPW = passwordEncoder.encode(pwd);
            user.setPassword(encryptPW);
            jdbcTemplate.update(
                    "INSERT INTO  user(firstname, lastname, email, pass) VALUES (?, ?, ?, ?)", user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
            return "home";
        }
        else {
            System.out.println("Error email address already exits");
            return "signup";
        }


    }*/

    @GetMapping(path = "/about")
    public String aboutus(){
        return "about";
    }

    private boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            System.out.println("The password matches.");
        else {
            System.out.println("The password does not match.");
            return false;
        }
        return true;
    }

    private boolean isUniqueEmail(String email){
        boolean status = true;
        List<String> allemail = new ArrayList<>();
        allemail.addAll(jdbcTemplate.queryForList("select email from user;", String.class));
        for(int i = 0; i < allemail.size(); i++){
            if (email.equals(allemail.get(i))){
                status =  false;
                break;
            }
        }
        return status;
    }

}
