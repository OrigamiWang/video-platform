package szu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UpdateService updatesService;

    @Value("${redis.user_prefix}")
    private String REDIS_USER_PREFIX;

    @PostMapping("/publish")
    @ApiOperation("发布动态")
    public CommonResult<String> publish(
            @RequestParam("title") String title,//标题
            @RequestParam("content") String content,//内容
            @RequestParam("type") int tid,//动态类型
            @RequestParam("pid") int pid,//动态分区的id
            @RequestParam(value = "images", required = false) MultipartFile[] images//图片
    ) {
//        //获取用户id
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        String token = request.getHeader("token");
//        User user = (User) redisTemplate.opsForValue().get(REDIS_USER_PREFIX + token);
//        int uid = user.getId();
//
//        if (StringUtils.isBlank(token)) {
//            return CommonResult.failed("no token");
//        }

        int uid = 0;//测试用

        //校验参数
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

    @RequestMapping("/all")
    @ApiOperation("获取所有动态")
    public CommonResult<List<Update>> allUpdates() {
        return CommonResult.success(updatesService.findAll());
    }

    @RequestMapping("/byPartition")
    @ApiOperation("获取指定分区的动态")
    public CommonResult<List<Update>> getUpdatesByType(@RequestParam("pid") int pid) {
        return CommonResult.success(updatesService.findByType(pid));
    }

    @RequestMapping("/delete")
    @ApiOperation("删除指定动态")
    public CommonResult deleteById(@RequestParam("id") int id) {
        updatesService.deleteById(id);
        return CommonResult.success(null);
    }

    /***
     * 请求参数:图片的url
     */
    @RequestMapping("/getImage")
    @ApiOperation("获取指定图片")
    public CommonResult<byte[]> getImages(@RequestParam("url") String url) {
        byte[] imageData = updatesService.getImage(url);
        if (imageData != null && imageData.length > 0) {
            return CommonResult.success(imageData);
        } else {
            return CommonResult.failed();
        }
    }

    /***
     * 修改动态
     */
    @PostMapping("/update")
    @ApiOperation("修改动态")
    public CommonResult<String> update(
            @RequestParam("id") int id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "pid", required = false) Integer pid,
            @RequestParam(value = "images", required = false) MultipartFile[] images
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
        params.put("type", type);
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
