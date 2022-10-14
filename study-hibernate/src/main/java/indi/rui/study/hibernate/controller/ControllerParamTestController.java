package indi.rui.study.hibernate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2022-09-02
 */
@Slf4j
@RestController
@RequestMapping("/study-hibernate/param")
public class ControllerParamTestController {

    @GetMapping("test")
    public String test(@RequestParam(name = "isEdit") Boolean isEdit) {
        return "1-" + isEdit;
    }

    @GetMapping("test?edit={edit}")
    public String test2(@RequestParam(name = "edit") String edit) {
        return "2-" + edit;
    }

    @GetMapping("dynamic")
    public String dynamic(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        StringBuilder builder = new StringBuilder("?");
        for (String name : parameters.keySet()) {
            String[] values = parameters.get(name);
            for (int i = 0; i < values.length; i++) {
                builder.append(name).append("=").append(values[i]).append("&");
            }
        }
        if (builder.lastIndexOf("&") == builder.length() - 1) {
            builder.deleteCharAt(builder.lastIndexOf("&"));
        }
        return "2-" + builder.toString();
    }
}
