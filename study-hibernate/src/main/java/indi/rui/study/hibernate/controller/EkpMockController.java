package indi.rui.study.hibernate.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-29
 */
@Slf4j
@RestController
@RequestMapping("/api/sys-notify/sysNotifyTodoRestService")
public class EkpMockController {

    @PostMapping("addTodo")
    public void addTodo(@RequestBody Map<String, Object> body) {
        log.info("addTodo request body: \n{}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
    }

    @PostMapping("setTodoDone")
    public void setTodoDone(@RequestBody Map<String, Object> body) {
        log.info("setTodoDone request body: \n{}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
    }
}
