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
public class Favroite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;
    private Long productid;

    public Favroite(){}

    public Favroite(String owner, Long productid){
        this.owner = owner;
        this.productid = productid;
    }
}
