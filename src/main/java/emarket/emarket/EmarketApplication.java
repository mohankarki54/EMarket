package emarket.emarket;

import emarket.emarket.controller.ProductController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class EmarketApplication {

    public static void main(String[] args) {
        //System.getProperties().put( "server.port", 5000 );
        SpringApplication.run(EmarketApplication.class, args);
    }

}
