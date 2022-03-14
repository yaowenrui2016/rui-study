package indi.rui.study.antd.contract;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.util.FileUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author: yaowr
 * @create: 2022-02-19
 */
@RestController
@RequestMapping("/api/contract")
public class ContractController {

    @PostMapping("list")
    public JSONObject list(@RequestBody(required = false) JSONObject body,
                           @RequestParam("current") int current,
                           @RequestParam("pageSize") int pageSize) {
        JSONArray array = FileUtils.loadJSON("contracts.json", JSONArray.class);
        JSONObject rtn = new JSONObject();
        rtn.put("data", array);
        rtn.put("success", true);
        rtn.put("total", 800);
        return rtn;
    }
}
