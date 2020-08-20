package emarket.emarket.controller;

import emarket.emarket.DTO.ProductRegistrationDTO;
import emarket.emarket.Repository.ProductRepository;
import emarket.emarket.Service.ProductService;
import emarket.emarket.bean.Account;
import emarket.emarket.bean.Helper;
import emarket.emarket.bean.Product;
import emarket.emarket.bean.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @ModelAttribute("product")
    public ProductRegistrationDTO userRegistrationDto(){
        return new ProductRegistrationDTO();
    }


    @GetMapping("/product")
    public String product(Model model, @RequestParam String category){
        Helper.instance.setCategory(category);
        model.addAttribute("category",category);
        return  "Productform";
    }

    @PostMapping("/product")
    public String addProduct(@RequestParam("file") MultipartFile file,@RequestParam("file1") MultipartFile file1, @ModelAttribute("product")ProductRegistrationDTO productRegistrationDTO) throws IOException {
        String category = Helper.instance.getCategory();
        String owner= Account.instance.currentUserName();
        String name = productRegistrationDTO.getName();
        String type = productRegistrationDTO.getType();
        Double price = productRegistrationDTO.getPrice();

        //Vehicle
        String model = productRegistrationDTO.getModel();
        String color = productRegistrationDTO.getColor();
        int year = productRegistrationDTO.getYear();
        int millage = productRegistrationDTO.getMillage();

        //Clothes
        String size = productRegistrationDTO.getSize();

        Product product = new Product();

        try {
            byte[] bytes = file.getBytes();
            byte[] bytes1 = file1.getBytes();
            product = new Product(name,type, price, bytes,bytes1,model,color,year,millage,size ,owner,category);
        } catch (IOException e) {
            e.printStackTrace();
        }

        service.save(product);

        return "redirect:/home";
    }

}
