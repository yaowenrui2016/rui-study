package indi.rui.study.unittest.dto.workschedule;

import indi.rui.study.unittest.dto.AbstractVO;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 班次时间段
 *
 * @author: yaowr
 * @create: 2022-08-12
 */
@Getter
@Setter
public class SysTimeWorkClassesTimePeriodVO extends AbstractVO {

    private IdNameProperty fdWorkClasses;

    private Integer fdWorkBegin;

    private Integer fdWorkEnd;

    private Integer fdRestBegin;

    private Integer fdRestEnd;

    private Integer fdOrder;
}
