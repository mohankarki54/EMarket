package emarket.emarket.Service;

import emarket.emarket.bean.User;
import emarket.emarket.controller.DTO.UserRegistrationDto;

public abstract class UserService {

    public abstract User save(UserRegistrationDto registrationDto);

}
