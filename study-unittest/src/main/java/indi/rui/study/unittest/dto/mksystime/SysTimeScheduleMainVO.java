package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 排班主表
 */
@Getter
@Setter
@ToString
public class SysTimeScheduleMainVO {

    private String fdId;

    /**
     * 排班名称
     */
    private String fdName;

    /**
     * 开始时间（到日）
     */
    private Date fdBegin;

    /**
     * 结束时间
     */
    private Date fdEnd;

    /**
     * 班制：1：固定班制，2：排班制
     */
    private Integer fdType;

    /**
     * 版本号
     */
    private Integer fdVersion;

    /**
     * 来源排班ID 为排班历史版本
     */
    private String fdSourceSid;

    /**
     * 用于多版本信息备份，如果是备份数据则不做判断
     */
    private Boolean fdIsOverdue;

    /**
     * 人员范围名称，仅用于查询使用
     */
    private List<String> fdOrgScope;

    /**
     * 节假日id
     */
    private String fdHolidayId;

    /**
     * 节假日
     */
    private SysTimeHolidayVO fdHoliday;

    /**
     * 时区信息id
     */
    private String fdTimeZoneId;

    /**
     * 时区信息
     */
    private SysTimeZoneVO fdTimeZone;

    /**
     * 创建者名称
     */
    private String fdCreatorName;

    /**
     * 默认班次ID
     */
    private String fdDefaultClassId;

    /**
     * 创建时间
     */
    private Date fdCreateTime;

    /**
     * 更改时间
     */
    private Date fdAlterTime;
}
