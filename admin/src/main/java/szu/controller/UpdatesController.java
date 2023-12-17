package szu.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import szu.common.api.CommonResult;
import szu.common.service.MinioService;
import szu.common.service.RedisService;
import szu.model.Partition;
import szu.model.Update;
import szu.model.User;
import szu.service.UpdateService;
import szu.util.AuthUtil;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Resource
    private RedisService redisService;
    @Resource
    private MinioService minioService;

    @Value("${redis.user_prefix}")
    private String USER_PREFIX;

    @PostMapping("/essay")
    @ApiOperation("发布图文动态")
    @ApiResponse(code = 200, message = "发布成功")
    public CommonResult<String> publish(
            @ApiParam(value = "动态的正文，长度1~1024", required = true) @RequestParam("content") String content,//内容
            @ApiParam(value = "动态所附带的图片，最大9张；理论上，可以存视频吧", required = false)
            @RequestParam(value = "images", required = false) MultipartFile[] images,//图片
            @RequestHeader(value = "Authorization") String token
    ) {
        User user = (User) redisService.get(USER_PREFIX + token);
        if (user == null) {
            return CommonResult.failed("请先登录");
        }
        //校验参数
        if (content == null || content.isEmpty()) {
            return CommonResult.failed("内容不能为空");
        }

        if (images != null && images.length > 9) {
            return CommonResult.failed("图片数量不能超过9张");
        }
        try {
            updatesService.publishEssay(user.getId(), content, images);
            return CommonResult.success("发布成功");
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
    }

    @GetMapping("/inPage")
    @ApiOperation("分页获取图文动态")
    @ApiResponse(code = 200, message = "Update的List")
    public CommonResult<List<Update>> getInPage(@ApiParam("页码") @RequestParam("pageNum") int pageNum,
                                                @ApiParam("每页大小") @RequestParam("pageSize") int pageSize) {
        return CommonResult.success(updatesService.findEssayInPage(pageNum, pageSize));
    }

    @GetMapping("/info/{id}")
    @ApiOperation("获取单条图文动态")
    @ApiResponse(code = 200, message = "Update")
    public CommonResult<Update> getEssayById(@PathVariable("id") int id) {
        return CommonResult.success(updatesService.findEssayById(id));
    }

    @GetMapping("/all")
    @ApiOperation("获取所有图文动态")
    @ApiResponse(code = 200, message = "Update的List")
    public CommonResult<List<Update>> allEssay() {
        return CommonResult.success(updatesService.findEssayAll());
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除指定图文动态")
    public CommonResult<String> deleteEssayById(@RequestParam("id") int id,
                                                @RequestHeader(value = "Authorization") String token) {
        //TOKEN校验,对比要删除的动态的uid和token的uid是否一致
        User user = (User) JSON.parse((String) redisService.get(USER_PREFIX + token));
        if (user == null) {
            return CommonResult.failed("请先登录");
        }
        if (!Objects.equals(user.getId(), updatesService.findEssayById(id).getUid())) {
            return CommonResult.failed("无权删除");
        }
        updatesService.deleteEssayById(id);
        return CommonResult.success("操作成功");
    }

    @GetMapping("/homePage")
    @ApiOperation("获取首页视频动态的简略推送，返回拼接好的vo")
    @ApiResponse(code = 200, message = "VideoVo List")
    public CommonResult<List<VideoVo>> getHomePage(@ApiParam("推送多少个视频") @RequestParam("pageSize") int pageSize,
                                                   @ApiParam("推送给的用户的uid") @RequestParam("uid") int uid) {
        return CommonResult.success(updatesService.getHomePage(pageSize,uid));
    }

    @GetMapping("/getImage")
    @ApiOperation("获取指定图片，暂时也可以用来获取视频资源")
    @ApiResponse(code = 200, message = "获取成功")
    public ResponseEntity<byte[]> getImages(@ApiParam(value = "图片的url") @RequestParam("url") String url) {
        return ResponseEntity.ok(updatesService.getImage(url));
    }

    @PutMapping("/essay")
    @ApiOperation("修改图文动态")
    public CommonResult<String> update(
            @ApiParam(value = "目标修改动态的id", required = true) @RequestParam("id") int id,
            @ApiParam(value = "动态的正文，长度1~1024", required = true) @RequestParam(value = "content") String content,
            @ApiParam(value = "动态所附带的图片，最大9张；", required = false) @RequestParam(value = "images", required = false) MultipartFile[] images,
            @RequestHeader(value = "Authorization") String token
    ) {
        //TOKEN校验,对比要修改的动态的uid和token的uid是否一致
        User user = (User) JSON.parse((String) redisService.get(USER_PREFIX + token));
        if (user == null) {
            return CommonResult.failed("请先登录");
        }
        if (!Objects.equals(user.getId(), updatesService.findEssayById(id).getUid())) {
            return CommonResult.failed("无权修改");
        }

        //校验参数
        if (content != null && content.isEmpty()) {
            return CommonResult.failed("修改内容不能为空");
        }
        //如果图片数量超过9张
        if (images != null && images.length > 9) {
            return CommonResult.failed("图片数量不能超过9张");
        }
        //修改动态
        try {
            boolean isOk = updatesService.updateEssay(id, content, images);
            if (!isOk) {
                return CommonResult.failed("修改失败");
            }
            return CommonResult.success("修改成功");
        } catch (Exception e) {
            return CommonResult.failed("修改失败");
        }
    }

    @PostMapping("/video")
    @ApiOperation("发布视频动态,返回msg结果信息")
    @ApiResponse(code = 200, message = "结果信息")
    public CommonResult<String> publishVideo(
            @ApiParam(value = "视频标题", required = true) @RequestParam("title") String title,//标题
            @ApiParam(value = "动态的正文，长度1~1024", required = true) @RequestParam("content") String content,//内容
            @ApiParam(value = "视频分区id,如果不发这个，默认为1，代表未分区", required = false) @RequestParam("pid") Integer pid,
            @RequestHeader(value = "Authorization") String token
    ) {
        User user = (User) redisService.get(USER_PREFIX + token);
        if (user == null) {
            return CommonResult.failed("请先登录");
        }
        //校验参数
        if (content == null || content.isEmpty()) {
            return CommonResult.failed("内容不能为空");
        }
        if (title == null || title.isEmpty()) {
            return CommonResult.failed("标题不能为空");
        }
        if (pid == null) pid = 1;
        else if (pid < 1) {
            return CommonResult.failed("分区id不能小于1");
        }
        try {
            String msg = updatesService.publishVideo(user.getId(), title, content, pid);
            if (msg.equals("发布成功"))
                return CommonResult.success(msg);
            return CommonResult.failed(msg);
        } catch (Exception e) {
            return CommonResult.failed("发布失败");
        }
    }

    @PostMapping("/uploadMedia")
    @ApiOperation("上传视频,返回视频截取封面的url")
    @ApiResponse(code = 200, message = "发布成功")
    public CommonResult<String> uploadVideo(@ApiParam("name=video，视频文件") @RequestBody MultipartFile video, @RequestHeader(value = "Authorization") String token) {
        try {
            User user = (User) redisService.get(USER_PREFIX + token);
            if (user == null) {
                return CommonResult.failed("请先登录");
            }
            String url = updatesService.uploadVideo(video, user.getId());
            return CommonResult.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed("上传失败");
        }
    }

    @GetMapping("/previewVideoCover")
    @ApiOperation("预览视频封面")
    @ApiResponse(code = 200, message = "成功")
    public ResponseEntity<org.springframework.core.io.Resource> previewVideoCover() {
        Optional<User> currentUser = AuthUtil.getCurrentUser();
        return currentUser.map(user -> updatesService.previewVideoCover(user.getId())).orElse(null);
    }

    @PostMapping("/changeVideoCover")
    @ApiOperation("更换视频封面，适用于生成的封面不满意，想自己更换的情况")
    @ApiResponse(code = 200, message = "成功")
    public CommonResult<String> changeVideoCover(@ApiParam("name=image，图片") @RequestBody MultipartFile image, @RequestHeader(value = "Authorization") String token) {
        try {
            User user = (User) redisService.get(USER_PREFIX + token);
            if (user == null) {
                return CommonResult.failed("请先登录");
            }
            if (image == null) {
                return CommonResult.failed("图片不能为空");
            }
            String url = updatesService.changeVideoCover(image, user.getId());
            return CommonResult.success(url);
        } catch (Exception e) {
            return CommonResult.failed("上传失败");
        }
    }

    @DeleteMapping("/video")
    @ApiOperation("删除指定视频动态")
    @ApiResponse(code = 200, message = "删除成功")
    public CommonResult<String> deleteVideoById(@ApiParam("vid") @RequestParam("id") int id,
                                                @RequestHeader(value = "Authorization") String token) {
        //TOKEN校验,对比要删除的动态的uid和token的uid是否一致
        User user = (User) redisService.get(USER_PREFIX + token);
        if (user == null) {
            return CommonResult.failed("请先登录");
        }
        if (!Objects.equals(user.getId(), updatesService.findVideoUpdateByVid(
                updatesService.findVideoByVid(id).getId())
                .getUid())) {
            return CommonResult.failed("无权删除");
        }
        updatesService.deleteVideoByVid(id);
        return CommonResult.success("操作成功");
    }

    @PostMapping("/partition")
    @ApiOperation("添加动态分区")
    public CommonResult<String> addPartition(@RequestParam("name") String name) {
        updatesService.addPartition(name);
        return CommonResult.success("添加成功");
    }

    @PutMapping("/partition")
    @ApiOperation("修改动态分区")
    public CommonResult<String> updatePartition(@RequestParam("id") int id, @RequestParam("name") String name) {
        updatesService.updatePartition(id, name);
        return CommonResult.success("修改成功");
    }

    @DeleteMapping("/partition")
    @ApiOperation("删除动态分区")
    public CommonResult<String> deletePartition(@RequestParam("id") int id) {
        updatesService.deletePartitionById(id);
        return CommonResult.success("删除成功");
    }

    @GetMapping("/partition")
    @ApiOperation("获取所有视频分区")
    @ApiResponse(code = 200, message = "Partition的List")
    public CommonResult<List<Partition>> allPartitions() {
        return CommonResult.success(updatesService.getPartitions());
    }


}
