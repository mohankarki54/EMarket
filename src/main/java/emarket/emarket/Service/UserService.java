package emarket.emarket.Service;

import emarket.emarket.bean.User;
import emarket.emarket.controller.DTO.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);
    User save(UserRegistrationDto registrationDto);
}
