package emarket.emarket.bean;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;

@Getter
@Setter
@Entity
@Table(name = "Product", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ProductName;
    private double price;
    private String ProductType;

    @Lob
    private byte[] Image;

    private String imagename;

    public Product() {
    }

    public Product(String ProductName, String ProductType, double price,byte[] data) {
        this.ProductName= ProductName;
        this.ProductType = ProductType;
        this.price = price;
        this.Image = data;
    }



}
