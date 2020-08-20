package emarket.emarket.controller;

import emarket.emarket.DTO.CommentBean;
import emarket.emarket.DTO.UserRegistrationDto;
import emarket.emarket.Service.CommentService;
import emarket.emarket.Service.FavService;
import emarket.emarket.Service.ProductService;
import emarket.emarket.Service.UserServiceImpl;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class ProductDisplayController {
    @Autowired
    private ProductService service;
    @Autowired
    private FavService favService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = {"/productInfo"})
    public ModelAndView root(ModelAndView modelAndView) {
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
        modelAndView.setViewName("productView");
        return modelAndView;
    }

    @PostMapping(path = {"/productInfo"})
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
    }

    @PostMapping(path = {"/savecomment"})
    public String saveComment(@RequestParam String action,@ModelAttribute("commentBean") CommentBean commentBean){
        int value = Integer.parseInt(action);
        User user  = userService.findByEmail(Account.instance.currentUserName());
        String username = user.getFirstname();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        Comment comment = new Comment(Account.instance.currentUserName(),username, Long.valueOf(value),commentBean.getDescription(), date);
        commentService.save(comment);
        return "redirect:/productInfo";
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
}
