package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @desc 节假日设置下的节假日明细
 * @Author: wangwr
 * @Date: 2020/6/20 10:08
 * @Version: v2.0.0
 * @Copy: 深圳市蓝凌软件股份有限公司
 */
@Getter
@Setter
@ToString
public class SysTimeHolidayDetailVO {

    private String fdId;

    /**
     * 名称
     */
    private String fdName;

    /**
     * 休假开始时间
     */
    private Date fdStartDay;

    /**
     * 休假结束时间
     */
    private Date fdEndDay;

    /**
     * 补班时间
     */
    private String fdPatchDay;

    /**
     * 所属节假日设置名称
     */
    private String fdHolidayId;

    /**
     * 所属节假日设置名称
     */
    private String fdHolidayName;

    /**
     * 所属节假日
     */
    private SysTimeHolidayVO sysTimeHolidayVO;

    /**
     * 排序
     */
    private Integer fdNo;
}
