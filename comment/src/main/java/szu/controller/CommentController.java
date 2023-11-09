package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
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
     * 根据foreignId获取指定区域的评论，根据点赞数排序
     * @param foreignId 要评论的动态id
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取评论")
    public CommonResult<List> listComment(Integer foreignId){
        List<Comment> commentsByForeignId = commentService.getCommentsByForeignId(foreignId);
        return CommonResult.success(commentsByForeignId);
    }

    /**
     * 回复评论
     * @param comment 回复的评论
     * @param pid 回复的根评论的id
     * @return
     */

    @PostMapping("/reply")
    @ApiOperation("回复评论")
    public CommonResult replyComment(@RequestBody Comment comment,String pid){
        commentService.replyComment(comment,pid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 点赞评论
     * @param flag 1：点赞  -1：取消点赞
     * @param pid 点赞的根评论的id
     * @param index 子评论在根的下标
     * @return
     */
    @PostMapping("/like")
    @ApiOperation("点赞评论")
    public CommonResult likeComment(Integer flag, String pid, Integer index){
        commentService.likeComment(flag,pid,index);
        return CommonResult.success(ResultCode.SUCCESS);
    }
}
