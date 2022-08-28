package indi.rui.study.unittest.dto.mksystime;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 排班制使用VO
 */
@Getter
@Setter
@ToString
public class SysTimeScheduleSortVO {
    /**
     * 排班主文档
     */
    private SysTimeScheduleMainVO scheduleMainVO;

    /**
     * 排班制-排班与班次对应关系表
     */
    private List<SysTimeScheduleBindingClassVO> correlationClassesVOS;

    /**
     * 排班制-对应规则列表
     */
    private List<SysTimeScheduleRuleVO> scheduleRuleVOS;

    /**
     * 人员排班信息表
     */
    private List<SysTimeRuleVO> ruleVOS;

    /**
     * 可以修改的时间范围
     */
    private List<SysTimeRuleVO> canChangeTime;
}
