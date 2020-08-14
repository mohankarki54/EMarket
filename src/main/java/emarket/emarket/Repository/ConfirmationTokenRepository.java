package emarket.emarket.Repository;

import emarket.emarket.bean.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findConfirmationTokenByconfirmationtoken(String confirmationtoken);
}
