package emarket.emarket.Service;

import emarket.emarket.Repository.RatingRepository;
import emarket.emarket.bean.Product;
import emarket.emarket.bean.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    public List<Rating> findRatingByUsername(String username){
        return ratingRepository.findRatingByUsername(username);
    }

    public double sellerRating(String username){
        double rate = 0;
        List<Rating> ratings = findRatingByUsername(username);
        ArrayList<Integer> value = new ArrayList<>();
        for(int i = 0; i < ratings.size(); i++){
            value.add(ratings.get(i).getRatingvalue());
        }

        if(value.size() != 0){
            double sum = 0;
            double newValue = 0;
            for (Integer mark : value) {
                 sum += mark;
            }
            newValue = sum/value.size();
            String sValue = (String) String.format("%.2f", newValue);
            rate = Double.parseDouble(sValue);
        }
        return rate;
    }
}
