package emarket.emarket.Service;

import emarket.emarket.Repository.ProductRepository;
import emarket.emarket.bean.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll();
    }

    public void save(Product product) {
        repo.save(product);
    }

    public Product get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public List<Product> productSearch(String word){
        return repo.findProductBynameContaining(word);
    }

    public List<Product> listbyOwner(String email){
        return repo.findProductByOwner(email);
    }

    public List<Product> categoryList(String category){
        return repo.findProductByCategory(category);
    }

    public List<Product> findProductByEnddate(){
        return repo.findProductByEnddate();
    }

    public List<Product> sponsorProduct(boolean value){
        return repo.findProductBySponsor(value);
    }

    public void deleteProduct(){
        repo.deleteProduct();
    }

    public void updateSponsorFlag(boolean sponsor, Date enddate, Long id){
        repo.updateSponsorFlag(sponsor,enddate,id);
    }

    public  void updateProduct(String name,  String description,double price,Long id){
        repo.updateProduct(name,description,price,id);
    }

    public void updateViewed(int value, Long id){
        repo.updateViewed(value,id);
    }

    public Date latestDate(String category){
        return repo.latestDate(category);
    }

}
