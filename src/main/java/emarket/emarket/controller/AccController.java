package emarket.emarket.controller;


import emarket.emarket.DTO.ProductRegistrationDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class AccController {

    @Autowired
    private ProductService service;

    @GetMapping("/listedproduct")
    public String viewHomePage(Model model) {
        List<Product> listProducts = service.listbyOwner(Account.instance.currentUserName());
        if (listProducts!= null) {

            for (Product product : listProducts) {
                String imagename = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
                product.setImagename(imagename);
            }
            model.addAttribute("listProducts", listProducts);
        }
        return "listedproduct";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        Product product1 = service.get(id);
        ModelAndView mav;
        if(product1 != null){
            mav = new ModelAndView("edit_product");
            Product product = service.get(id);
            mav.addObject("product", product);
        }
        else{
            mav = new ModelAndView("listedproduct");
            mav.addObject("message", "Product is already deleted.");
        }
        return mav;
    }

    @PostMapping("/save")
    public String updateProduct(@RequestParam String action, @RequestParam("file") MultipartFile file, ModelAndView modelAndView, @ModelAttribute("product")Product productRegistrationDTO, RedirectAttributes redirectAttrs){
        List<Product> allproduct = service.listAll();
        int value = Integer.parseInt(action);
        Long val = Long.valueOf(value);
        Product product = new Product();
        product.setId(val);
        product.setName(productRegistrationDTO.getName());
        product.setPrice(productRegistrationDTO.getPrice());
        product.setOwner(Account.instance.currentUserName());

        if (file.isEmpty()){
            product.setImage(allproduct.get(value-1).getImage());
        }
        else{
            try {
                byte[] bytes = file.getBytes();
                product.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        product.setType(productRegistrationDTO.getType());
        service.save(product);
        redirectAttrs.addAttribute("success","Product successfully edited." );
        return "redirect:/listedproduct";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id, RedirectAttributes redirectAttrs) {
        service.delete(id);
        redirectAttrs.addAttribute("success","Successfully removed." );
        return "redirect:/listedproduct";
    }
}
