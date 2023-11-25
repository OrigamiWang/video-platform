package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import szu.common.api.CommonResult;
import szu.model.Update;
import szu.service.UpdateService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zyd
 * @Date: 2023/10/31 00:29
 */
@RestController
@Api(tags = "UpdatesController")
@Tag(name = "UpdatesController", description = "动态管理")
@RequestMapping("/updates")
public class UpdatesController {
    @Resource
    private UpdateService updatesService;

    @PostMapping("/publish")
    @ApiOperation("发布动态")
    @ApiResponse(code = 200, message = "发布成功")
    public CommonResult<String> publish(
            @ApiParam(value = "发布动态的用户id", required = true) @RequestParam("uid") int uid,
            @ApiParam(value = "动态的标题,长度1~40", required = true) @RequestParam("title") String title,//标题
            @ApiParam(value = "动态的正文，长度1~1024", required = true) @RequestParam("content") String content,//内容
            @ApiParam(value = "动态的类型，截至11.13日，只有图文，类型编号为0", required = true) @RequestParam("type") int tid,//动态类型
            @ApiParam(value = "动态的分区，未分区的动态的pid=1", required = true) @RequestParam("pid") int pid,//动态分区的id
            @ApiParam(value = "动态所附带的图片，最大9张；理论上，可以存视频吧", required = false) @RequestParam(value = "images", required = false) MultipartFile[] images//图片
    ) { //校验参数
        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return CommonResult.failed("标题、内容和分区不能为空");
        }

        if (images != null && images.length > 9) {
            return CommonResult.failed("图片数量不能超过9张");
        }

        boolean ifPublished = updatesService.publish(uid, title, content, tid, pid, images);
        if (!ifPublished) {
            return CommonResult.failed("发布失败");
        }
        return CommonResult.success("发布成功");
    }

    @GetMapping("/all")
    @ApiOperation("获取所有动态")
    @ApiResponse(code = 200, message = "Update的List的JSON字符串")
    public CommonResult<List<Update>> allUpdates(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return CommonResult.success(updatesService.findAll(pageNum, pageSize));
    }

    @GetMapping("/byPartition")
    @ApiOperation("获取指定分区的动态")
    @ApiResponse(code = 200, message = "List<Update>的JSON字符串")
    public CommonResult<List<Update>> getUpdatesByType(@RequestParam("pid") int pid) {
        return CommonResult.success(updatesService.findByType(pid));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除指定动态")
    public CommonResult<String> deleteById(@RequestParam("id") int id) {
        updatesService.deleteById(id);
        return CommonResult.success("操作成功");
    }

    /***
     * 请求参数:图片的url
     */
    @GetMapping("/getImage")
    @ApiOperation("获取指定图片")
    @ApiResponse(code = 200, message = "查看图片")
    public ResponseEntity<org.springframework.core.io.Resource> getImages(@RequestParam("url") String url) {
        return updatesService.getImage(url);
    }

    /***
     * 修改动态
     */
    @PostMapping("/update")
    @ApiOperation("修改动态")
    public CommonResult<String> update(
            @ApiParam(value = "目标修改动态的id", required = true) @RequestParam("id") int id,
            @ApiParam(value = "动态的标题,长度1~40", required = false) @RequestParam(value = "title", required = false) String title,
            @ApiParam(value = "动态的正文，长度1~1024", required = false) @RequestParam(value = "content", required = false) String content,
            @ApiParam(value = "动态的分区，未分区的动态的pid=1", required = false) @RequestParam(value = "pid", required = false) Integer pid,
            @ApiParam(value = "动态所附带的图片，最大9张；", required = false) @RequestParam(value = "images", required = false) MultipartFile[] images
    ) {
        //如果全部为空
        if (title == null && content == null) {
            return CommonResult.failed("参数不能为空");
        }
        //校验参数
        if (title != null && title.isEmpty() || content != null && content.isEmpty()) {
            return CommonResult.failed("修改内容不能为空");
        }
        //如果图片数量超过9张
        if (images != null && images.length > 9) {
            return CommonResult.failed("图片数量不能超过9张");
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("title", title);
        params.put("content", content);
        params.put("pid", pid);

        //修改动态
        boolean isSuccessful = updatesService.update(params, images);
        if (isSuccessful) {
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.failed("修改失败");
        }
    }

}
