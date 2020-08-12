package emarket.emarket.Service;

import emarket.emarket.Repository.CommentRepository;
import emarket.emarket.bean.Comment;
import emarket.emarket.bean.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Getter
@Setter
@Service
@Transactional
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> find(Long id){
        return commentRepository.findCommentByproid(id);
    }
}
