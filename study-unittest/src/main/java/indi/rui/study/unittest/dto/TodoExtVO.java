package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-11-19
 */
@Getter
@Setter
public class TodoExtVO {
    private String provider;
    private String name;

    /**
     * 配置页面URL
     * MK内部页面以'/'开头，例如：/web/#/current/notify/config
     * 外部配置页面给出完整地址，例如：http://xxx.com.cn/confi/xxx
     */
    private String configPageUrl;
    private Boolean enabled;
}
