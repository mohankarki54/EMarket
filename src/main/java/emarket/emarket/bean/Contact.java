package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {
    double price;
    String message;
    String phonenum;
    String email;

    public Contact(){}

    public Contact(double price, String message){
        this.price = price;
        this.message = message;
    }

    public Contact(String phonenum, String email, String message){
        this.phonenum = phonenum;
        this.email = email;
        this.message = message;
    }
}
