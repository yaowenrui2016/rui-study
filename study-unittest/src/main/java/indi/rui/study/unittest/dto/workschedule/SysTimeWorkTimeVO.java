package indi.rui.study.unittest.dto.workschedule;

import indi.rui.study.unittest.dto.AbstractVO;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 按自然日排班的排班时间表
 *
 * @author: yaowr
 * @create: 2022-08-12
 */
@Getter
@Setter
public class SysTimeWorkTimeVO extends AbstractVO {
    /**
     * 所属排班组
     */
    private IdNameProperty fdWorkSchedule;

    /**
     * 所属班次
     */
    private IdNameProperty fdWorkClasses;

    /**
     * 人员id
     */
    private String fdPersonId;

    /**
     * 该时间表数据是跟随哪个组织架构类型产生
     */
    private Integer fdByOrgType;

    /**
     * 年
     */
    private Integer fdYear;

    /**
     * 月
     */
    private Integer fdMonth;

    /**
     * 日
     */
    private Integer fdDayOfMonth;

    /**
     * 当日工时
     */
    private Integer fdTodayWorkTime;

    /**
     * 次日工时
     */
    private Integer fdTomorrowWorkTime;
}
