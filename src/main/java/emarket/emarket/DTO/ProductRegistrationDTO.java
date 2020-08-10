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


    public ProductRegistrationDTO() {
    }

    public ProductRegistrationDTO(String name, String type, double price, byte[] data) {
        this.name= name;
        this.type = type;
        this.price = price;
        this.Image = data;
    }
}
