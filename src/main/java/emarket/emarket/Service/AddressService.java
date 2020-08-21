package emarket.emarket.Service;

import emarket.emarket.bean.Address;

import java.util.List;


public interface AddressService {
    Address findByEmail(String email);
    List<Address> findAddressByZipcode(int zipcode);;
    void updateAddress(String street, String apartment, String city, String state, int zipcode, Long Id);
}
