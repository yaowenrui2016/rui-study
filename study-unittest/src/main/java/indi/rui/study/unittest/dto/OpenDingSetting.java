package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Setter
@Getter
@ToString
public class OpenDingSetting {

    private Boolean enable;

    private String tenantId;

    private String domainName;

    private String protocol;

    private Integer timeout;

    /**
     * 待办类型推送开关
     * 如果关闭则待办不推送，仅推送待阅
     * 如果打开则待办和待阅都推送
     * 默认关闭
     */
    private Boolean todoTypeSwitch;

    /**
     * 是否推送工作通知（opending的一种消息通知方式）
     * 默认关闭
     */
    private Boolean sendWorkNotification;

    private Boolean openReadWork;

    /**
     * 按照app 类型进行存储
     */
    private Map<String, OpenDingAppSetting> appMap = new HashMap<>();


    /**
     * 蓝桥中转url ，便于后续服务自动单点
     * 需要有占位符 参考
     * OpenDingSetting.PARAM_PLACEHOLDER
     */
    private String landrayBridgeUrl;

    /**
     * 推送到专有钉待办的来源系统黑名单
     */
    private List<String> sourceAppsBlacklist;
}
