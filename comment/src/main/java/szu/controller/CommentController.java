package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ListResult;
import szu.common.api.ResultCode;
import szu.model.Comment;
import szu.service.CommentService;
import szu.service.LikeService;
import szu.vo.CommentVo;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author UserXin
 * @description CommentController
 * @date 2023-11-01
 */

@RestController
@RequestMapping("/comment")
@Api(tags = "CommentController")
@Slf4j
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private LikeService likeService;

    /**
     * 添加评论
     *
     * @param comment 要添加的评论
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加评论")
    public CommonResult<ResultCode> addComment(@RequestBody @ApiParam("要添加的评论（id不用传）") Comment comment){
        log.info("添加评论：{}",comment);
        commentService.addComment(comment);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    @GetMapping("/count/{foreignId}")
    @ApiOperation("根据foreignId获取评论总数")
    public CommonResult<Long> countCommentsByForeignId(@PathVariable Integer foreignId) {
        return CommonResult.success(commentService.countCommentsByForeignId(foreignId));
    }

    /**
     * 根据foreignId分页获取指定区域的评论，根据点赞数排序
     * @param foreignId 要获取评论的动态id
     * @param page      当前页
     * @param size      每页大小
     * @param uid 当前登录的用户的id
     * @return
     */
    @GetMapping("/listRootComment/{foreignId}/{page}/{size}/{uid}")
    @ApiOperation("分页获取评论")
    public CommonResult<ListResult<CommentVo>> listCommentByPages(@PathVariable("uid") @ApiParam("当前登录的用户的id") Integer uid,
                                          @PathVariable("foreignId") @ApiParam("要获取评论的动态id") Integer foreignId,
                                          @PathVariable("page") @ApiParam("当前页") int page,
                                          @PathVariable("size") @ApiParam("每页大小") int size,
                                          @RequestParam @ApiParam("排序字段") String sortBy){
        log.info("uid，{} 要获取的评论区域，{},page:{},size:{}",uid,foreignId,page,size);
        Long total = commentService.countCommentsByForeignId(foreignId);
        List<CommentVo> commentsByForeignIdAndPages = commentService.getCommentsByForeignIdAndPages(uid,foreignId, page, size, sortBy);
        return CommonResult.success(new ListResult<>(commentsByForeignIdAndPages, total));
    }

    /**
     * 分页获取对应根评论下的子评论
     *
     * @param pid  要获取子评论根评论id
     * @param page 当前页
     * @param size 每页大小
     * @param uid 当前登录用户id
     * @return
     */
    @GetMapping("/listChildrenComment/{pid}/{page}/{size}/{uid}")
    @ApiOperation("分页获取对应根评论下的子评论")
    public CommonResult<ListResult<CommentVo>> listChildrenCommentByPages(@PathVariable("pid") @ApiParam("要获取子评论的根评论id") String pid,
                                                         @PathVariable("page") @ApiParam("当前页") int page,
                                                         @PathVariable("size") @ApiParam("每页大小") int size,
                                                         @PathVariable("uid") @ApiParam("当前登录用户id") Integer uid) {
        log.info("pid：{}，page：{}，size：{}，uid：{}", pid, page, size,uid);
        Long total = commentService.countChildCommentsByPid(pid);
        List<CommentVo> childrenCommentByPages = commentService.listChildrenCommentByPages(pid, page, size,uid);
        return CommonResult.success(new ListResult<>(childrenCommentByPages, total));
    }

    /**
     * 回复评论
     *
     * @param comment 回复的评论
     * @param pid     回复的根评论的id
     * @return
     */

    @PostMapping("/reply/{pid}")
    @ApiOperation("回复评论")
    public CommonResult<ResultCode> replyComment(@RequestBody @ApiParam("要回复的评论（id不用传）") Comment comment,
                                     @PathVariable("pid") @ApiParam("回复的根评论的id") String pid){
        log.info("评论的内容和根评论id，{},{}",comment,pid);
        commentService.replyComment(comment,pid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 点赞根评论
     *
     * @param flag 1：点赞  -1：取消点赞
     * @param pid  点赞的根评论的id
     * @param uid 当前登录用户id
     * @param pUid 被点赞的评论的用户的id
     * @return
     */
    @PostMapping("/likeRoot/{flag}/{pid}/{uid}/{pUid}")
    @ApiOperation("点赞根评论")
    public CommonResult<ResultCode> likeRootComment(@PathVariable("flag") @ApiParam("点赞或取消点赞（1：点赞  -1：取消点赞）") Integer flag,
                                        @PathVariable("pid") @ApiParam("点赞的根评论的id") String pid,
                                        @PathVariable("uid") @ApiParam("当前登录用户id") Integer uid,
                                        @PathVariable("pUid") @ApiParam("被点赞的评论的用户的id") Integer pUid){
        log.info("uid，{} 点赞根评论：{}，点赞的根评论的id：{}",uid,flag,pid);
        commentService.likeRootComment(flag,pid);
        likeService.like(uid,pid,pUid,flag);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 点赞子评论
     *
     * @param flag 1：点赞  -1：取消点赞
     * @param pid  点赞的对应的根评论id
     * @param cid  点赞的子评论id
     * @param uid 当前登录用户id
     * @param pUid 被点赞的评论的用户的id
     * @return
     */
    @PostMapping("/likeChildren/{flag}/{pid}/{cid}/{uid}/{pUid}")
    @ApiOperation("点赞子评论")
    public CommonResult<ResultCode> likeChildrenComment(@PathVariable("flag") @ApiParam("点赞或取消点赞（1：点赞  -1：取消点赞）") Integer flag,
                                            @PathVariable("pid") @ApiParam("点赞的对应的根评论id")String pid,
                                            @PathVariable("cid") @ApiParam("点赞的子评论id") String cid,
                                            @PathVariable("uid") @ApiParam("当前登录用户id") Integer uid,
                                            @PathVariable("pUid") @ApiParam("被点赞的评论的用户的id") Integer pUid){
        log.info("点赞子评论：{},点赞的对应的根评论id：{},点赞的子评论id：{}",flag,pid,cid);
        commentService.likeChildrenComment(flag,pid,cid);
        likeService.like(uid,cid,pUid,flag);
        return CommonResult.success(ResultCode.SUCCESS);
    }


    /**
     * 删除根评论
     *
     * @param pid 要删除的根评论的id
     * @return
     */
    @DeleteMapping("/deleteRoot/{pid}")
    @ApiOperation("删除根评论")
    public CommonResult<ResultCode> deleteRootComment(@PathVariable("pid") @ApiParam("要删除的根评论的id") String pid){
        log.info("删除根评论，{}",pid);
        commentService.deleteRootComment(pid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 删除子评论
     *
     * @param pid 要删除的子评论的根评论的id
     * @param cid 要删除的子评论的id
     * @return
     */
    @DeleteMapping("/deleteChild/{pid}/{cid}")
    @ApiOperation("删除子评论")
    public CommonResult<ResultCode> deleteChildComment(@PathVariable("pid") @ApiParam("要删除的子评论的根评论的id") String pid,
                                           @PathVariable("cid") @ApiParam("要删除的子评论的id") String cid){
        log.info("删除子评论，pid：{}，cid：{}",pid,cid);
        commentService.deleteChildComment(pid,cid);
        return CommonResult.success(ResultCode.SUCCESS);
    }

    /**
     * 置顶评论
     * @param pid 要置顶的评论id（只能为根评论）
     * @param flag 置顶或取消置顶，1：置顶 0：取消置顶
     * @return
     */
    @PostMapping("/toTopComment/{pid}/{flag}")
    @ApiOperation("置顶评论:1：置顶 0：取消置顶")
    public CommonResult<ResultCode> toTopComment(@PathVariable("pid") @ApiParam("要置顶的评论id（只能为根评论）") String pid,
                                                 @PathVariable("flag") @ApiParam("置顶或取消置顶") Integer flag){
        log.info("置顶评论,pid:{},flag:{}",pid,flag);
        commentService.toTopComment(pid,flag);
        return CommonResult.success(ResultCode.SUCCESS);
    }


}
