package emarket.emarket.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationtoken;

    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private User user;

    public ConfirmationToken(){}

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        confirmationtoken = UUID.randomUUID().toString();
    }
}
