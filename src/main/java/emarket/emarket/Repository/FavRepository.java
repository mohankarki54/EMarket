package emarket.emarket.Repository;

import emarket.emarket.bean.Favroite;
import emarket.emarket.bean.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavRepository extends JpaRepository<Favroite, Long> {
    List<Favroite> findFavroiteByOwner(String email);
}
