package emarket.emarket.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRegistrationDTO {
    private String ProductName;
    private double price;
    private String ProductType;
    private byte[] Image;


    public ProductRegistrationDTO() {
    }

    public ProductRegistrationDTO(String ProductName, String ProductType, double price, byte[] data) {
        this.ProductName= ProductName;
        this.ProductType = ProductType;
        this.price = price;
        this.Image = data;
    }
}
