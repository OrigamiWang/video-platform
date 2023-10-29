package szu.controller;


import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.controller
 * @Author: ljx
 * @Date: 2023/10/28 20:02
 */
@RestController
@Api(tags = "VisitController")
@Tag(name = "VisitController", description = "访问量管理")
@RequestMapping("/visit")
public class VisitController {


}
