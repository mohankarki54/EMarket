package emarket.emarket.Service;

import emarket.emarket.Repository.AddressRepository;
import emarket.emarket.bean.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findByEmail(String email) {
        return addressRepository.findAddressByOwner(email);
    }

    @Override
    public List<Address> findAddressByZipcode(int zipcode) {
        return addressRepository.findAddressByZipcode(zipcode);
    }

    @Override
    @Transactional
    public void updateAddress(String street, String apartment, String city, String state, int zipcode, Long Id) {
        addressRepository.updateAddress(street,apartment,city, state, zipcode, Id);

    }
}
