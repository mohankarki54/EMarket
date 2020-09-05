package emarket.emarket.Service;


import emarket.emarket.DTO.FavBean;
import emarket.emarket.Repository.FavRepository;
import emarket.emarket.bean.Account;
import emarket.emarket.bean.Favroite;
import emarket.emarket.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FavService {


    @Autowired
    private FavRepository repo;

    public Favroite findFavroiteById(Long id){
        return repo.findFavroiteById(id);
    }

    public void save(Favroite favroite) {
        repo.save(favroite);
    }

    public void delete(long id) {
        repo.deleteById(id);
    }

    public List<Favroite> favroiteSearch(String owner){
       return repo.findFavroiteByOwner(owner);
    }

    public void deleteFavProduct(Long id){
        repo.deleteFavProduct(id);
    }

    public boolean isUserFavroite(String user, Long id){
        boolean status = false;
        List<Favroite> favroites = favroiteSearch(user);

        for(int i= 0; i<favroites.size(); i++){
            if(favroites.get(i).getProductid() == id && favroites.get(i).getOwner().equals(user)){
                status = true;
            }
        }
        return status;
    }

    public Long getUserFavroiteId(String user, Long id){
        long value = 0;
        List<Favroite> favroites = favroiteSearch(user);

        for(int i= 0; i<favroites.size(); i++){
            if(favroites.get(i).getProductid() == id && favroites.get(i).getOwner().equals(user)){
                value = favroites.get(i).getId();
            }
        }
        return value;
    }

    

}
