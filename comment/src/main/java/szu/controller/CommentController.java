package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import szu.common.api.CommonResult;
import szu.common.api.ResultCode;
import szu.model.Comment;
import szu.service.CommentService;

import javax.annotation.Resource;
import java.util.Map;

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
     * @param comment
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加评论")
    public CommonResult addComment(@RequestBody Comment comment){
        log.info("添加评论：{}",comment);
        commentService.addComment(comment);
        return CommonResult.success(ResultCode.SUCCESS);
    }



}
