package indi.rui.study.antd.user;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.util.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2022-02-20
 */
@RestController
@RequestMapping("/api/currentUser")
public class UserController {

    @GetMapping
    public JSONObject currentUser() {
        JSONObject json = FileUtils.loadJSON("user.json");
        return json;
    }
}
