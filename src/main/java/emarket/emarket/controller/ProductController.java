package emarket.emarket.controller;

import emarket.emarket.bean.Account;
import emarket.emarket.bean.Product;
import org.apache.commons.io.FilenameUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Controller
public class ProductController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

    @GetMapping("/product")
    public String product(Model model){
        Product product = new Product();
        /*if(Account.instance.getIsauthenciated()){
            model.addAttribute("product",product);
            return  "Productform";
        }else{
            Account account = new Account();
            model.addAttribute("account", account);
            return "login";
        }*/

        model.addAttribute("product",product);
        return  "Productform";
    }

    @RequestMapping(value = "product", method = RequestMethod.POST)
    public String addProduct(Model model, @ModelAttribute("product") Product product, @RequestParam("files") MultipartFile[] files) throws IOException {
        String imagename = "";
        StringBuilder fileNames = new StringBuilder();
        String sql = "INSERT INTO  product(create_date, name, image, description, price) VALUES (?, ?, ?, ?,?)";
        for (MultipartFile file: files){
            imagename = file.getOriginalFilename();
            Path fileNameandpath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            try {
                byte[] img = file.getBytes();
                Files.write(fileNameandpath, file.getBytes());
                System.out.println(compressBytes(file.getBytes()));
                //
                jdbcTemplate.update(sql ,  new Date(),product.getName(),compressBytes(file.getBytes()),product.getDescription(),product.getPrice());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        //ClassPathResource backImgFile = new ClassPathResource("uploads/" + imagename);


       // ImageModel blackImage = new ImageModel(1, "JSA-ABOUT-IMAGE-BLACK-BACKGROUND", "png", arrayPic);

        model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString());
        return "home";
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }


}
