package indi.rui.study.unittest.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-12-09
 */
@Getter
@Setter
public class MkLoginResult {
    private JSONObject userInfo;
    private String xAuthToken;
}
