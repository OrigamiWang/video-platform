package szu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import szu.model.Star;

/**
 * @ClassName: StarDao
 * @Description: 收藏夹Dao接口
 * @Version 1.0
 * @Date: 2023-11-23 19:38
 * @Auther: UserXin
 */
@Mapper
public interface StarRepository extends MongoRepository<Star,String> {

}
