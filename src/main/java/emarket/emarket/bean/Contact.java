package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    double price;
    String message;

    public Contact(){}

    public Contact(double price, String message){
        this.price = price;
        this.message = message;
    }
}
