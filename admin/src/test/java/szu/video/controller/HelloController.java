package szu.video.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.video.controller
 * @Author: Origami
 * @Date: 2023/10/1 15:53
 */
@RestController
@RequestMapping("/video")
public class HelloController {

    @GetMapping("/test")
    public String sayHello() {
        return "Hello World!";
    }

}
