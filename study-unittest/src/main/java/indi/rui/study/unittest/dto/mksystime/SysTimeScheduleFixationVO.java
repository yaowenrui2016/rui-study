package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 固定排班表
 */
@Getter
@Setter
@ToString
public class SysTimeScheduleFixationVO {

    private String fdId;
    private String fdScheduleMainId;

    /**
     * 星期几（星期一-星期日:1-7）
     */
    private Integer fdDayOfWeek;

    /**
     * 班次主体ID
     */
    private String fdClassesMainId;

    /**
     * 当天是否上班
     */
    private Boolean fdIsWork;

    /**
     * 时间字段
     */
    private String fdTimes;

    /**
     * 默认班次名称
     */
    private String fdName;

}
