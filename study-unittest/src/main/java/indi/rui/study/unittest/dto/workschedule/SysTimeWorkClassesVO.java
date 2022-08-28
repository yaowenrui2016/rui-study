package indi.rui.study.unittest.dto.workschedule;

import indi.rui.study.unittest.dto.AbstractVO;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 班次
 *
 * @author: yaowr
 * @create: 2022-08-12
 */
@Getter
@Setter
public class SysTimeWorkClassesVO extends AbstractVO {

    private String fdName;

    private Boolean fdEnabled;

    private String fdColor;

    private IdNameProperty fdCreator;

    /**
     * 班次时间段
     */
    private List<SysTimeWorkClassesTimePeriodVO> fdTimePeriodList;

    private Date fdCreateTime;
}
