package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String owner;
    String username;
    String description;
    Long proid;
    Date created;

    public Comment(){}

    public Comment(String owner, String username, Long proid, String description, Date created){
        this.owner = owner;
        this.username = username;
        this.proid = proid;
        this.description = description;
        this.created = created;
    }

}
