package indi.rui.study.unittest.dto.workschedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-08-16
 */
@Getter
@Setter
public class WorkTimeRtnVO {
    /**
     * 人员排班时间表
     */
    private List<SysTimeWorkTimeVO> workTimeList;

    /**
     * 提示信息
     * 例如：xxx排班时段冲突
     */
    private String message;
}
