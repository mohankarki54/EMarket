package emarket.emarket.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRegistrationDTO {
    private String name;
    private double price;
    private String type;
    private byte[] Image;
    private String description;
    private String condition;

    //Vehicle
    String model;
    String color;
    int year;
    int millage;

   //clothes
   String size;

    public ProductRegistrationDTO() {
    }

    public ProductRegistrationDTO(String name, String type, double price, byte[] data) {
        this.name= name;
        this.type = type;
        this.price = price;
        this.Image = data;
    }

    public ProductRegistrationDTO( String model, String color, int year, int millage) {
        this.model = model;
        this.color = color;
        this.year = year;
        this.millage = millage;
    }


}
