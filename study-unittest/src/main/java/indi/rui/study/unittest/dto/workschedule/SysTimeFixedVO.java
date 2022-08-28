package indi.rui.study.unittest.dto.workschedule;

import indi.rui.study.unittest.dto.AbstractVO;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 固定排班
 *
 * @author: yaowr
 * @create: 2022-08-12
 */
@Getter
@Setter
public class SysTimeFixedVO extends AbstractVO {

    /**
     * 所属排班组
     */
    private IdNameProperty fdWorkSchedule;

    /**
     * 所属班次
     */
    private IdNameProperty fdWorkClasses;

    private Integer fdDayOfWeek;

    private Boolean fdIsTodayWork;

    private String fdTimes;
}
