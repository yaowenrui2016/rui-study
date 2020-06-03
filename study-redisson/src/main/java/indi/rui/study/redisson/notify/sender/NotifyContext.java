package indi.rui.study.redisson.notify.sender;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
public class NotifyContext {
    private String subject;
    private String appName;
    private String moduleName;
    private String entityName;
    private String entityId;
    private List<String> target;
    private String provider;
    private String parameter;
}
