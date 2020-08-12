package emarket.emarket.Service;


import emarket.emarket.Repository.FavRepository;
import emarket.emarket.bean.Favroite;
import emarket.emarket.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FavService {

    @Autowired
    private ProductService service;

    @Autowired
    private FavRepository repo;

    public void save(Favroite favroite) {
        repo.save(favroite);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public List<Favroite> favroiteSearch(String owner){
       return repo.findFavroiteByOwner(owner);
    }

}
