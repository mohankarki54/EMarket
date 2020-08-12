package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavBean {
    private Product product;
    private Long id;
    private String owner;
    private String imagename;

    public FavBean(){}

    public FavBean(String owner, Product product, Long id, String imagename){
        this.owner = owner;
        this.product = product;
        this.id = id;
        this.imagename = imagename;
    }
}
