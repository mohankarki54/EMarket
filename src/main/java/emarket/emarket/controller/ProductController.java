package emarket.emarket.controller;

import emarket.emarket.DTO.ProductRegistrationDTO;
import emarket.emarket.Service.ProductService;
import emarket.emarket.bean.Account;
import emarket.emarket.bean.Helper;
import emarket.emarket.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;

@Controller
public class ProductController {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @Autowired
    private ProductService service;

    @ModelAttribute("product")
    public ProductRegistrationDTO userRegistrationDto(){
        return new ProductRegistrationDTO();
    }


    @GetMapping("/product/{value}")
    public String product(Model model, @PathVariable(name = "value") String category){
        Helper.instance.setCategory(category);
        model.addAttribute("category",category);
        return  "Productform";
    }

    @PostMapping("/product")
    public String addProduct(@RequestParam("file") MultipartFile file,@RequestParam("file1") MultipartFile file1, @ModelAttribute("product")ProductRegistrationDTO productRegistrationDTO, RedirectAttributes redirectAttrs) throws IOException {
        String category = Helper.instance.getCategory();
        String owner= Account.instance.currentUserName();
        String name = productRegistrationDTO.getName();
        String type = productRegistrationDTO.getType();
        Double price = productRegistrationDTO.getPrice();
        String description = productRegistrationDTO.getDescription();
        String condition = productRegistrationDTO.getCondition();

        //Vehicle
        String model = productRegistrationDTO.getModel();
        String color = productRegistrationDTO.getColor();
        int year = productRegistrationDTO.getYear();
        int millage = productRegistrationDTO.getMillage();

        //Clothes
        String size = productRegistrationDTO.getSize();
        Date currentDate = new Date();
        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.DATE, 10); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        // convert calendar to date
        Date currentDatePlusOne = c.getTime();
        Product product = new Product();

        try {
            byte[] bytes = file.getBytes();
            byte[] bytes1 = file1.getBytes();
            product = new Product(false, name,type, price, bytes,bytes1,model,color,year,millage,size ,owner,category,description, currentDate, currentDatePlusOne, condition,0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.save(product);
        redirectAttrs.addAttribute("success", "Your Ad with the title " +name+ " is successfully Listed.");
        return "redirect:/product/"+category;
    }

}
