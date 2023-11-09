package szu.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import szu.model.Comment;

/**
 * @ClassName: CommentReposary
 * @Description: TODO
 * @Version 1.0
 * @Date: 2023-11-09 21:52
 * @Auther: UserXin
 */
public interface CommentRepository extends MongoRepository<Comment,String> {
}
