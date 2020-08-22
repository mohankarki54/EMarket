package emarket.emarket.controller;

import emarket.emarket.DTO.CommentBean;
import emarket.emarket.DTO.UserRegistrationDto;
import emarket.emarket.Service.*;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProductDisplayController {
    @Autowired
    private ProductService service;
    @Autowired
    private FavService favService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"/productInfo"})
    public ModelAndView root(@RequestParam String action, ModelAndView modelAndView) {
        int value = Integer.parseInt(action);
        Helper.instance.setId(value);
        CommentBean commentBean = new CommentBean();
        List<Comment> comments = commentService.find(Long.valueOf(Helper.instance.getId()));
        Product product = service.get(Helper.instance.getId());
        if (product != null) {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            String imagename1 = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage2());
            product.setImagename(imagename);
            product.setBackname(imagename1);
            modelAndView.addObject("product", product);
        }
        modelAndView.addObject("comments", comments);
        User seller = userService.findByEmail(product.getOwner());
        modelAndView.addObject("seller",seller);
        modelAndView.addObject("commentBean", commentBean);

        Contact contact = new Contact();
        modelAndView.addObject("contact",contact);
        modelAndView.setViewName("productView");
        return modelAndView;
    }

   /*@PostMapping(path = {"/productInfo"})
    public ModelAndView displayInfo(@RequestParam String action, ModelAndView modelAndView ){
        CommentBean commentBean = new CommentBean();
        int value = Integer.parseInt(action);
        Helper.instance.setId(value);
        List<Comment> comments = commentService.find((long)value);
        Product product = service.get(value);

        if (product != null) {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            String imagename1 = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage2());
            product.setImagename(imagename);
            product.setBackname(imagename1);

            modelAndView.addObject("product", product);
        }

        User seller = userService.findByEmail(product.getOwner());
        modelAndView.addObject("seller",seller);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("commentBean", commentBean);
        modelAndView.setViewName("productView");
        return modelAndView;
    }*/

    @PostMapping(path = {"/savecomment"})
    public String saveComment(@RequestParam String action,@ModelAttribute("commentBean") CommentBean commentBean){
        int value = Integer.parseInt(action);
        User user  = userService.findByEmail(Account.instance.currentUserName());
        String username = user.getFirstname();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        Comment comment = new Comment(Account.instance.currentUserName(),username, Long.valueOf(value),commentBean.getDescription(), date);
        commentService.save(comment);
        return "redirect:/productInfo?action="+action;
    }

    @PostMapping(path = {"/addFavorite"})
    public String addFav(@RequestParam String action, RedirectAttributes redirectAttrs ){
        int value = Integer.parseInt(action);
        Product product = service.get(value);
        String owner = Account.instance.currentUserName();
        Favroite favroite = new Favroite(owner, product.getId());
        favService.save(favroite);
        redirectAttrs.addAttribute("success","Added to the Favorite List" );
        return "redirect:/productInfo";
    }

    @PostMapping(path = {"/sendEmail"})
    public String sendcontactEmail(@RequestParam String action, RedirectAttributes redirectAttrs, @ModelAttribute("contact") Contact contact, HttpServletRequest request ){
        redirectAttrs.addAttribute("success","Your message has been successfully sent." );

        int value = Integer.parseInt(action);
        Product product = service.get(value);
        String user_email = Account.instance.currentUserName();
        String seller = product.getOwner();
        User user = userService.findByEmail(seller);
        User user_customer = userService.findByEmail(user_email);

        Mail mail = new Mail();
        mail.setFrom("technewsandblog@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Product contact from customer");

        Map<String, Object> model = new HashMap<>();
        model.put("user", "Hello "+ user.getFirstname() + ", ");
        model.put("signature", "https://emarket.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("productname", "Product Name: "+product.getName());
        model.put("name", "Name: "+user_customer.getFirstname()+" "+ user_customer.getLastname());
        model.put("email", "Email: "+user_customer.getEmail());
        model.put("price", "Offered Price: $"+contact.getPrice());
        model.put("msg", "Message: "+contact.getMessage());
        mail.setModel(model);
        emailService.sendcontactEmail(mail);

        return "redirect:/productInfo?action="+action;
    }
}
