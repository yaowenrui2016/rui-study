package indi.rui.study.unittest.dto.workschedule;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-08-16
 */
@Getter
@Setter
public class WorkScheduleRtnVO {
    /**
     * 排班组id
     */
    private String scheduleId;

    /**
     * 提示信息
     * 例如：xxx排班时段冲突
     */
    private String message;
}
