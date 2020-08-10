package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search {
    public static Search instance = new Search();
    private String word;

    public Search(){}

}
