package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String owner;
    String street;
    String apartment;
    String city;
    String state;
    int zipcode;

    public Address(){}

    public Address(String owner, String street, String apartment, String city, String state, int zipcode){
        this.owner = owner;
        this.street = street;
        this.apartment = apartment;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }
}
