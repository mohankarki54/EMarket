package emarket.emarket.Repository;

import emarket.emarket.bean.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressByOwner(String email);

    List<Address> findAddressByZipcode(int zipcode);

    @Modifying
    @Query("update Address u set u.street = :street, u.apartment = :apartment, u.city = :city , u.state = :state , u.zipcode = :zipcode  where u.id = :id")
    void updateAddress(@Param("street") String street,@Param("apartment") String apartment,@Param("city") String city,@Param("state") String state, @Param("zipcode") int zipcode, @Param("id") Long id);

}
