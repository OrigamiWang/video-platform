package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ResultCode;
import szu.model.Comment;
import szu.service.CommentService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description CommentController
 * @author UserXin
 * @date 2023-11-01
 */

@RestController
@RequestMapping("/comment")
@Api(tags = "评论模块")
@Slf4j
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 添加评论
     * @param comment 要添加的评论
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加评论")
    public CommonResult addComment(@RequestBody Comment comment){
        log.info("添加评论：{}",comment);
        commentService.addComment(comment);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 根据foreignId和foreignType获取指定区域的评论
     * @param foreignId 评论的对象id
     * @param foreignType 评论的对象类型(1:动态评论 2:视频评论)
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取评论")
    public CommonResult<Map> listComment(Integer foreignId,Integer foreignType){
        log.info("获取评论：foreignId：{}，foreignType：{}",foreignId,foreignType);
        Map map = new HashMap<String,Object>();
        //获取指定区域的所有评论
        List<Comment> comments = commentService.getCommentsByForeignIdAndForeignType(foreignId,foreignType);
        //进行分级，分出根评论和子评论 （只有两级）
        List<Comment> faComments = new ArrayList<>();
        for (Comment comment : comments) {   //遍历所有的评论
            if(comment.getPid()==null){   //没有父级评论的就是根评论
                List<Comment> childrens = new ArrayList<>();    //该根评论的子评论

                for (Comment comment1 : comments) {     //遍历所有评论
                    if(comment.getId().equals(comment1.getPid())){   //找到Pid为当前根评论id的评论
                        childrens.add(comment1);    //添加到子评论列表中
                    }
                }

                comment.setChildren(childrens); //为根评论设置子评论
                faComments.add(comment);
            }
        }

        map.put("faComments",faComments);
        return CommonResult.success(map);
    }

}
