package emarket.emarket.controller;

import emarket.emarket.DTO.ProductRegistrationDTO;
import emarket.emarket.Repository.ProductRepository;
import emarket.emarket.Service.ProductService;
import emarket.emarket.bean.Account;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @ModelAttribute("product")
    public ProductRegistrationDTO userRegistrationDto(){
        return new ProductRegistrationDTO();
    }


    @GetMapping
    public String product(Model model){
        return  "Productform";
    }

    @PostMapping
    public ModelAndView addProduct(@RequestParam("file") MultipartFile file, @ModelAttribute("product")ProductRegistrationDTO productRegistrationDTO,ModelAndView modelAndView) throws IOException {
        String owner= Account.instance.currentUserName();
        String name = productRegistrationDTO.getName();
        String type = productRegistrationDTO.getType();
        Double price = productRegistrationDTO.getPrice();
        Product product = new Product();

        try {
            byte[] bytes = file.getBytes();
            product = new Product(name,type, price, bytes,owner);

        } catch (IOException e) {
            e.printStackTrace();
        }

        service.save(product);

        List<Product> products = service.listAll();

        if (products != null) {

            for (Product product1 : products) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product1.getImage());
                product1.setImagename(imagename);
            }

            modelAndView.addObject("products", products);
            modelAndView.addObject("search", new Search());
        }

        modelAndView.setViewName("home");
        return modelAndView;
    }

}
