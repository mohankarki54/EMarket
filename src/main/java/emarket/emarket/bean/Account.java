package emarket.emarket.bean;

import org.springframework.stereotype.Repository;

@Repository
public class Account {
    String email;
    String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account(){}
}
