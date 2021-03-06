package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean isEnabled;
    private boolean address;
    private Date joined;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    public User(){ }

    public User(String firstName, String lastName, String email, String password) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstname, String lastname, String email, String password, Collection<Role> roles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getFirstname() {
        return firstname;
    }


}
