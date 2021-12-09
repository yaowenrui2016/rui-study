package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-12-09
 */
@Getter
@Setter
public class MkLoginResult {
    private UserInfo userInfo;
    private String xAuthToken;
}
