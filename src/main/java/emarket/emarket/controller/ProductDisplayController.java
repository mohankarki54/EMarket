package emarket.emarket.controller;

import emarket.emarket.DTO.CommentBean;
import emarket.emarket.Service.*;
import emarket.emarket.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private TimeCalculation timeCalculation;
    @Autowired
    private RatingService ratingService;

    @GetMapping(value = {"/productInfo"})
    public ModelAndView root(@RequestParam String action, ModelAndView modelAndView) {
        Date currentDate = new Date();
        int value = Integer.parseInt(action);
        Helper.instance.setId(value);
        CommentBean commentBean = new CommentBean();
        boolean isFavorite = favService.isUserFavroite(Account.instance.currentUserName(),Long.valueOf(value));
        Long favID = favService.getUserFavroiteId(Account.instance.currentUserName(),Long.valueOf(value));

        List<Comment> comments = commentService.find(Long.valueOf(Helper.instance.getId()));

        Product product = service.get(Helper.instance.getId());
        String category = product.getCategory();

        //calculate view
        int current_view = product.getViewed();
        int new_view = current_view + 1;
        service.updateViewed(new_view, product.getId());

        List<Product> productCategory= service.categoryList(category);
        Collections.shuffle(productCategory);
        if (product != null) {
            String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
            String imagename1 = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage2());
            product.setImagename(imagename);
            product.setBackname(imagename1);
            modelAndView.addObject("product", product);
            Date start = product.getListeddate();
            String posted = timeCalculation.timeDifference(currentDate, start);
            modelAndView.addObject("posted", posted);
            modelAndView.addObject("condition", "Condition: " + product.getConditon());
        }

        if (productCategory.size() != 0) {
            if(productCategory.size() < 13) {
                for (Product pro : productCategory) {
                    String image = "data:image/png;base64," + Base64.getEncoder().encodeToString(pro.getImage());
                    pro.setImagename(image);
                }
                modelAndView.addObject("productCategory", productCategory);
            }
            else{
                ArrayList<Product> proCategory = new ArrayList<Product>();
                for(int i = 0; i < 13; i++){
                    proCategory.add(productCategory.get(i));
                }
                for (Product pro : proCategory) {
                    String image = "data:image/png;base64," + Base64.getEncoder().encodeToString(pro.getImage());
                    pro.setImagename(image);
                }
                modelAndView.addObject("productCategory", proCategory);
            }
        }

        for(Comment comment: comments){
            String time = timeCalculation.timeDifference1(currentDate,comment.getCreated());
            comment.setTime(time);
        }
        modelAndView.addObject("comments", comments);

        modelAndView.addObject("cat", category);
        User seller = userService.findByEmail(product.getOwner());
        modelAndView.addObject("seller",seller);
        modelAndView.addObject("commentBean", commentBean);
        double seller_rating = ratingService.sellerRating(product.getOwner());
        if(seller_rating != 0){
            modelAndView.addObject("seller_rating","Seller Rating: "+seller_rating+" / 5");
        }

        modelAndView.addObject("isFavorite", isFavorite);
        modelAndView.addObject("favID", favID);

        Contact contact = new Contact();
        modelAndView.addObject("contact",contact);
        Rating rating = new Rating();
        modelAndView.addObject("rating", rating);
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
        return "redirect:/productInfo?action="+action;
    }

    @GetMapping(path = {"/addFavorite"})
    public String addFav(@RequestParam String action, RedirectAttributes redirectAttrs ){
        int value = Integer.parseInt(action);
        Product product = service.get(value);
        String owner = Account.instance.currentUserName();
        Favroite favroite = new Favroite(owner, product.getId());
        favService.save(favroite);
        redirectAttrs.addAttribute("success","Added to the Favorite List" );
        return "redirect:/productInfo?action="+action;
    }

    @GetMapping(path = {"/unFavorite"})
    public String delete(@RequestParam String action, RedirectAttributes redirectAttrs) {
        int value = Integer.parseInt(action);
        Favroite favroite = favService.findFavroiteById(Long.valueOf(value));
        Long productId = favroite.getProductid();
        favService.delete(Long.valueOf(value));
        redirectAttrs.addAttribute("favRemove","Successfully removed from favroite list." );
        return "redirect:/productInfo?action="+productId;
    }


    @PostMapping(path = {"/review"})
    public String saveReview(@RequestParam String action, RedirectAttributes redirectAttrs, @ModelAttribute("rating") Rating rating ){
        int value = Integer.parseInt(action);
        Product product = service.get(value);
        String currentUser = Account.instance.currentUserName();
        String seller = product.getOwner();

        if (!currentUser.trim().equals(seller.trim())){
            Rating rating1 = new Rating(rating.getRatingvalue(), rating.getDescription(), seller);
            ratingService.save(rating1);
            redirectAttrs.addAttribute("success","Seller's Review successfully submitted. Thank you" );
        }
        else{
            redirectAttrs.addAttribute("success","Error: You cannot review your self." );
        }
        return "redirect:/productInfo?action="+action;
    }

    @PostMapping(path = {"/report"})
    public String sendReport(@RequestParam String action, RedirectAttributes redirectAttrs, @ModelAttribute("contact") Contact contact, HttpServletRequest request ){
        redirectAttrs.addAttribute("success","Thank you for reaching the us. We value your feedback and look forward to work it." );
        System.out.println(contact.getOptionMessage());
        Product product = service.get(Integer.parseInt(action));
        User user = userService.findByEmail(Account.instance.currentUserName());
        Mail mail = new Mail();
        mail.setFrom("emarketofficial123@gmail.com");
        mail.setTo("emarketofficial123@gmail.com");
        mail.setSubject("Attention: Report from User");
        Map<String, Object> model = new HashMap<>();
        model.put("user", user.getFirstname()+ " likes to file the Report.");
        model.put("productid", "Product id: " + action);
        model.put("seller_email","Seller email: " + product.getOwner());
        model.put("customer_email", "Customer Email: "+Account.instance.currentUserName());
        model.put("msg", "Message: "+contact.getMessage());
        model.put("optmsg", "Breif Message: " +contact.getOptionMessage());
        mail.setModel(model);
        emailService.sendcontactEmailAdmin(mail);
        return "redirect:/productInfo?action="+action;
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
        mail.setFrom("emarketofficial123@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Product contact from customer");

        Map<String, Object> model = new HashMap<>();
        model.put("user", "Hello "+ user.getFirstname() + ", ");
        model.put("id", "Product id: " + action);
        model.put("productname", "Product Name: "+product.getName());
        model.put("name", "Name: "+user_customer.getFirstname()+" "+ user_customer.getLastname());
        model.put("email", "Email: "+user_customer.getEmail());
        if(contact.getPrice() != 0){
            model.put("price", "Offered Price: $"+contact.getPrice());
        }
        model.put("msg", "Message: "+contact.getMessage());
        mail.setModel(model);
        emailService.sendcontactEmail(mail);
        return "redirect:/productInfo?action="+action;
    }

    @GetMapping("/user_current_listed_product/{id}")
    public ModelAndView user_current_listed_product(@PathVariable(name = "id") int id, RedirectAttributes redirectAttrs, ModelAndView modelAndView) {
        Product product = service.get(id);
        String seller = product.getOwner();
        List<Product> products = service.listbyOwner(seller);
        User user = userService.findByEmail(seller);
        List<Rating> comments = ratingService.findRatingByUsername(seller);

        if (products != null) {
            for (Product pro : products) {
                String image = "data:image/png;base64," + Base64.getEncoder().encodeToString(pro.getImage());
                pro.setImagename(image);
            }

            modelAndView.addObject("products", products);
        }

        double seller_rating = ratingService.sellerRating(product.getOwner());
        if(seller_rating != 0){
            modelAndView.addObject("seller_rating","Seller Rating: "+seller_rating+" / 5");
        }

        modelAndView.addObject("user", user);
        modelAndView.addObject("product", product);
        Contact contact = new Contact();
        modelAndView.addObject("contact",contact);
        Rating rating = new Rating();
        modelAndView.addObject("rating", rating);
        //modelAndView.addObject("message", "All active Ads posted by " + user.getFirstname() + user.getLastname());

        modelAndView.addObject("comments", comments);
        modelAndView.setViewName("user_current_listed");
        return modelAndView;
    }

    @PostMapping(path = {"/review1"})
    public String saveReview1(@RequestParam String action, RedirectAttributes redirectAttrs, @ModelAttribute("rating") Rating rating ){
        int value = Integer.parseInt(action);
        Product product = service.get(value);
        String currentUser = Account.instance.currentUserName();
        String seller = product.getOwner();

        if (!currentUser.trim().equals(seller.trim())){
            Rating rating1 = new Rating(rating.getRatingvalue(), rating.getDescription(), seller);
            ratingService.save(rating1);
            redirectAttrs.addAttribute("success","Seller's Review successfully submitted. Thank you" );
        }
        else{
            redirectAttrs.addAttribute("error","Error: You cannot review your self." );
        }
        return "redirect:/user_current_listed_product/"+action;
    }

    @PostMapping(path = {"/report1"})
    public String sendReport1(@RequestParam String action, RedirectAttributes redirectAttrs, @ModelAttribute("contact") Contact contact, HttpServletRequest request ){
        redirectAttrs.addAttribute("success","Thank you for reaching the us. We value your feedback and look forward to work it." );
        System.out.println(contact.getOptionMessage());
        Product product = service.get(Integer.parseInt(action));
        User user = userService.findByEmail(Account.instance.currentUserName());
        Mail mail = new Mail();
        mail.setFrom("emarketofficial123@gmail.com");
        mail.setTo("emarketofficial123@gmail.com");
        mail.setSubject("Attention: Report from User");
        Map<String, Object> model = new HashMap<>();
        model.put("user", user.getFirstname()+ " likes to file the Report.");
        model.put("productid", "Product id: " + action);
        model.put("seller_email","Seller email: " + product.getOwner());
        model.put("customer_email", "Customer Email: "+Account.instance.currentUserName());
        model.put("msg", "Message: "+contact.getMessage());
        model.put("optmsg", "Breif Message: " +contact.getOptionMessage());
        mail.setModel(model);
        emailService.sendcontactEmailAdmin(mail);
        return "redirect:/user_current_listed_product/"+action;
    }




}
