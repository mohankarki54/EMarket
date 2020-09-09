package emarket.emarket.DTO;

import emarket.emarket.bean.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class UserRegistrationDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean address;
    private Date joined;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String firstname, String lastname, String email, String password, boolean address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
