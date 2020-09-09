package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int ratingvalue;
    private String username;
    private String description;


    public Rating(){}

    public Rating(int ratingvalue, String description, String username){
        this.ratingvalue = ratingvalue;
        this.username = username;
        this.description = description;
    }
}
