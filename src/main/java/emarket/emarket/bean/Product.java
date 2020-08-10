package emarket.emarket.bean;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] Image;
    private String name;
    private String type;
    private double price;
    private String owner;
    private String Imagename;


    public Product() {}

    public Product(String name, String type, double price,byte[] data, String ownerEmail) {
        this.name= name;
        this.type = type;
        this.price = price;
        this.Image = data;
        this.owner = owner;
    }


}
