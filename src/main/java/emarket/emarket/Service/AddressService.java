package emarket.emarket.Service;

import emarket.emarket.bean.Address;


public interface AddressService {
    Address findByEmail(String email);
    void updateAddress(String street, String apartment, String city, String state, int zipcode, Long Id);
}
