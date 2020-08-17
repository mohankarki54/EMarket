package emarket.emarket.Repository;

import emarket.emarket.bean.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductBynameContaining(String word);
    List<Product> findProductByOwner(String email);
    List<Product> findProductByType(String type);
}
