package emarket.emarket.Repository;

import emarket.emarket.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByproid(Long id);
}
