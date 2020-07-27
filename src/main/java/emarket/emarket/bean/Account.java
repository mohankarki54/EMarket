package emarket.emarket.bean;

import org.springframework.stereotype.Repository;

@Repository
public class Account {
    String email;
    String password;
    boolean isauthenciated = false;

    public static Account instance = new Account();

    public boolean getIsauthenciated() {
        return isauthenciated;
    }

    public void setIsauthenciated(boolean isauthenciated) {
        this.isauthenciated = isauthenciated;
    }

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
