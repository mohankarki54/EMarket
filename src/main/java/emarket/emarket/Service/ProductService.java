package emarket.emarket.Service;

import emarket.emarket.Repository.ProductRepository;
import emarket.emarket.bean.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

}
