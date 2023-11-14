package szu.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import szu.model.Comment;

/**
 * @ClassName: CommentRepository
 * @Description: Mongodb DAO
 * @Version 1.0
 * @Date: 2023-11-09 21:52
 * @Auther: UserXin
 */
public interface CommentRepository extends MongoRepository<Comment,String> {
}
