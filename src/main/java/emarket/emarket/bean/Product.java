package emarket.emarket.bean;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] Image;

    @Lob
    private byte[] Image2;
    private String name;
    private String type;
    private double price;
    private String owner;
    private String Imagename;
    private String backname;

    String model;
    String color;
    int year;
    int millage;

    String size;

    String category;
    String description;
    boolean sponsor;

    Date listeddate;
    Date enddate;

    public Product() {}

    public Product(Boolean sponsor, String name, String type, double price,byte[] data, byte[] data1,  String model, String color, int year, int millage, String size, String owner, String category, String description, Date listeddate, Date enddate) {
        this.sponsor = sponsor;
        this.name= name;
        this.type = type;
        this.price = price;
        this.Image = data;
        this.Image2 = data1;
        this.owner = owner;

        this.model = model;
        this.color = color;
        this.year = year;
        this.millage = millage;
        this.size = size;
        this.category = category;
        this.description = description;
        this.listeddate = listeddate;
        this.enddate = enddate;
    }
}
