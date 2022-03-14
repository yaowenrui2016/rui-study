package indi.rui.study.antd.login;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * @author: yaowr
 * @create: 2022-02-20
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping("account")
    public JSONObject account(@RequestBody JSONObject params) {
        JSONObject json = new JSONObject();
        json.put("currentAuthority", "user");
        json.put("status", "ok");
        json.put("type", "account");
        return json;
    }

    @PostMapping("outLogin")
    public JSONObject outLogin() {
        JSONObject json = new JSONObject();
        json.put("success", "rue");
        json.put("data", new JSONObject());
        return json;
    }
}
