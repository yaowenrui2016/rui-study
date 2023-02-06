package indi.rui.study.activity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2023-02-05
 */
@RestController
@RequestMapping("example")
public class ExampleController {

    @GetMapping("hello")
    public String hello() {
        return "Hello World";
    }
}
