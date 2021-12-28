package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-03-05
 */
@Getter
@Setter
@NoArgsConstructor
public class MkNotifyAlarmSetting {

    /**
     * 是否开启消息告警，默认为：true
     */
    private Boolean enable;

    /**
     * 次要告警百分比，默认为；30%
     */
    private Integer minorAlarmPercentage;

    /**
     * 主要告警百分比，默认为：80%
     */
    private Integer majorAlarmPercentage;

    /**
     * 使用短信通知，默认为：true
     */
    private Boolean sms;

    /**
     * 短信通知的来源系统过滤
     */
    private List<IdNameProperty> sourceApp = new ArrayList<>();

    /**
     * 使用邮件通知，默认为：false
     */
    private Boolean email;

    /**
     * 是否显示黄灯
     */
    private Boolean showMinorAlarm;
}
