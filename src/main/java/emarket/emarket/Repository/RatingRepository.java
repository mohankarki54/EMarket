package emarket.emarket.Repository;

import emarket.emarket.bean.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findRatingByUsername(String username);
}

