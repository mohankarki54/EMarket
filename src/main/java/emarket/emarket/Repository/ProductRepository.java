package emarket.emarket.Repository;

import emarket.emarket.bean.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductBynameContaining(String word);
    List<Product> findProductByOwner(String email);
    List<Product> findProductByCategory(String type);
    List<Product> findProductBySponsor(boolean value);

    @Modifying
    @Query("DELETE from Product u where u.enddate < CURRENT_DATE")
    void deleteProduct();

    @Query("SELECT e FROM Product e WHERE e.enddate < CURRENT_DATE")
    List<Product> findProductByEnddate();

    @Modifying
    @Query("update Product u set u.sponsor = :sponsor, u.enddate = :enddate where u.id = :id")
    void updateSponsorFlag(@Param("sponsor") boolean sponsor, @Param("enddate") Date enddate, @Param("id") Long id);



}
