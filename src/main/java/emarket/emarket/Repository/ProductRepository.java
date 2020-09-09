package emarket.emarket.Repository;

import emarket.emarket.bean.Product;
import org.hibernate.sql.Select;
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

    @Modifying
    @Query("update Product u set u.name = :name, u.description = :description, u.price = :price where u.id = :id")
    void updateProduct(@Param("name") String name, @Param("description") String description,@Param("price") double price,  @Param("id") Long id);


    @Query("select max(u.listeddate) from Product u where u.category = :category")
    Date latestDate(@Param("category") String category);



}
