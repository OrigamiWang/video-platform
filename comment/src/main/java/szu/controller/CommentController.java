package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public CommonResult addComment(@RequestBody @ApiParam("要添加的评论（id不用传）") Comment comment){
        log.info("添加评论：{}",comment);
        commentService.addComment(comment);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 根据foreignId分页获取指定区域的评论，根据点赞数排序
     * @param foreignId 要获取评论的动态id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @GetMapping("/listRootComment/{foreignId}/{page}/{size}")
    @ApiOperation("分页获取评论")
    public CommonResult<List> listCommentByPages(@PathVariable("foreignId") @ApiParam("要获取评论的动态id") Integer foreignId,
                                          @PathVariable("page") @ApiParam("当前页") int page,
                                          @PathVariable("size") @ApiParam("每页大小") int size){
        log.info("要获取的评论区域，{},page:{},size:{}",foreignId,page,size);
        List<Comment> commentsByForeignIdAndPages = commentService.getCommentsByForeignIdAndPages(foreignId,page,size);
        return CommonResult.success(commentsByForeignIdAndPages);
    }

    /**
     * 分页获取对应根评论下的子评论
     * @param pid 要获取子评论根评论id
     * @param page 当前页
     * @param size 每页大小
     * @return
     */
    @GetMapping("/listChildrenComment/{pid}/{page}/{size}")
    @ApiOperation("分页获取对应根评论下的子评论")
    public CommonResult<List> listChildrenCommentByPages(@PathVariable("pid") @ApiParam("要获取子评论的根评论id") String pid,
                                                         @PathVariable("page") @ApiParam("当前页") int page,
                                                         @PathVariable("size") @ApiParam("每页大小") int size){
        log.info("pid：{}，page：{}，size：{}",pid,page,size);
        List<Comment> childrenCommentByPages = commentService.listChildrenCommentByPages(pid,page,size);
        return CommonResult.success(childrenCommentByPages);
    }

    /**
     * 回复评论
     * @param comment 回复的评论
     * @param pid 回复的根评论的id
     * @return
     */

    @PostMapping("/reply/{pid}")
    @ApiOperation("回复评论")
    public CommonResult replyComment(@RequestBody @ApiParam("要回复的评论（id不用传）") Comment comment,
                                     @PathVariable("pid") @ApiParam("回复的根评论的id") String pid){
        log.info("评论的内容和根评论id，{},{}",comment,pid);
        commentService.replyComment(comment,pid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 点赞根评论
     * @param flag 1：点赞  -1：取消点赞
     * @param pid 点赞的根评论的id
     * @return
     */
    @PostMapping("/likeRoot/{flag}/{pid}")
    @ApiOperation("点赞根评论")
    public CommonResult likeRootComment(@PathVariable("flag") @ApiParam("点赞或取消点赞（1：点赞  -1：取消点赞）") Integer flag,
                                        @PathVariable("pid") @ApiParam("点赞的根评论的id") String pid){
        log.info("点赞根评论：{}，点赞的根评论的id：{}",flag,pid);
        commentService.likeRootComment(flag,pid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 点赞子评论
     * @param flag 1：点赞  -1：取消点赞
     * @param pid 点赞的对应的根评论id
     * @param cid 点赞的子评论id
     * @return
     */
    @PostMapping("/likeChildren/{flag}/{pid}/{cid}")
    @ApiOperation("点赞子评论")
    public CommonResult likeChildrenComment(@PathVariable("flag") @ApiParam("点赞或取消点赞（1：点赞  -1：取消点赞）") Integer flag,
                                            @PathVariable("pid") @ApiParam("点赞的对应的根评论id")String pid,
                                            @PathVariable("cid") @ApiParam("点赞的子评论id") String cid){
        log.info("点赞子评论：{},点赞的对应的根评论id：{},点赞的子评论id：{}",flag,pid,cid);
        commentService.likeChildrenComment(flag,pid,cid);
        return CommonResult.success(ResultCode.SUCCESS);
    }


    /**
     * 删除根评论
     * @param pid 要删除的根评论的id
     * @return
     */
    @DeleteMapping("/deleteRoot/{pid}")
    @ApiOperation("删除根评论")
    public CommonResult deleteRootComment(@PathVariable("pid") @ApiParam("要删除的根评论的id") String pid){
        log.info("删除根评论，{}",pid);
        commentService.deleteRootComment(pid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 删除子评论
     * @param pid 要删除的子评论的根评论的id
     * @param cid 要删除的子评论的id
     * @return
     */
    @DeleteMapping("/deleteChild/{pid}/{cid}")
    @ApiOperation("删除子评论")
    public CommonResult deleteChildComment(@PathVariable("pid") @ApiParam("要删除的子评论的根评论的id") String pid,
                                           @PathVariable("cid") @ApiParam("要删除的子评论的id") String cid){
        log.info("删除子评论，pid：{}，cid：{}",pid,cid);
        commentService.deleteChildComment(pid,cid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

}
