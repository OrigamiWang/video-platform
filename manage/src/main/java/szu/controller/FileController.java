package szu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import szu.common.api.CommonResult;
import szu.common.util.OssUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@RestController
@Api(tags = "FileController")
@Tag(name = "FileController", description = "文件上传控制器")
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Resource
    private OssUtil ossUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传，上传时固定key=file，返回值为文件存储的路径")
    public CommonResult<String> upload(MultipartFile file){
        log.info("文件上传,{}", file);
        try {

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            //通过uuid创建唯一的文件名
            String uuid = UUID.randomUUID().toString();
            uuid = uuid.replaceAll("-", "");
            String objectName = uuid + extension;
//            String objectName = originalFilename;

            //后期将文件名修改为 user/userId/fileTye/文件名.fileType

            String filePath = ossUtil.upload(file.getBytes(), objectName);
            return CommonResult.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败,{0}", e);
            return CommonResult.failed("文件上传失败");
        }
    }


}
