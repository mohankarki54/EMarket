package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Helper {
    int id;
    public static Helper instance = new Helper();
}
