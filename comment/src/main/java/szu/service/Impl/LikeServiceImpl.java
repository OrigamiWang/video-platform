package szu.service.Impl;

import com.mongodb.client.result.UpdateResult;
import io.swagger.models.auth.In;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import szu.model.Comment;
import szu.model.Like;
import szu.service.LikeService;
import szu.vo.CommentVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: LikeServiceImpl
 * @Description: 点赞
 * @Version 1.0
 * @Date: 2023-11-22 19:11
 * @Auther: UserXin
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean like(Integer uid,String fid,Integer fUid,Integer flag) {
        if(flag!=-1&&flag!=1) throw new IllegalArgumentException();
        if(fid==null||uid==null) throw new NullPointerException();
        Query query = Query.query(Criteria.where("uid").is(uid)); //找到当前登录的用户的点赞列表
        Like like = mongoTemplate.findOne(query, Like.class);
        if(like==null){  //如果没有like列表就创建一个
            insertLikeUser(uid);
        }
        Update update = new Update();
        if(flag == 1){
            update.set("fidMap."+fid,fUid); //利用$set完成更新操作如果没有就插入
        }else {
            update.unset("fidMap."+fid);
        }
        mongoTemplate.updateFirst(query, update, "like");
        return true;
    }

    @Override
    public boolean insertLikeUser(Integer uid) {
        Like like = new Like();
        like.setUid(uid);
        like.setFidMap(new HashMap<String, Integer>());
        mongoTemplate.save(like);
        return true;
    }




}
