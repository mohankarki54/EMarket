package emarket.emarket.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentBean {
    String description;

    public CommentBean(){}

    public CommentBean(String description, Long proid){
        this.description = description;
    }

}
