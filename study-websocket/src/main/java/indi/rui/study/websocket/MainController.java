package indi.rui.study.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: yaowr
 * @create: 2020-06-07
 */
@Controller
public class MainController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
