package indi.rui.study.unittest.dto;

import jdk.nashorn.internal.runtime.linker.Bootstrap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-11-18
 */
@Getter
@Setter
public class NotifyTypeVO {
    private String notifyType;
    private String displayName;
    private String configPageUrl;
    private Boolean enabled;
}
