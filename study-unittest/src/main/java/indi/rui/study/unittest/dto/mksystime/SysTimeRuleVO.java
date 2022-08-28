package indi.rui.study.unittest.dto.mksystime;

import indi.rui.study.unittest.dto.IdNamePropertyExtend;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 人员排班信息表
 */
@Getter
@Setter
@ToString
public class SysTimeRuleVO {

    private String fdId;

    private String fdOrgId;

    private String fdOrgH;

    /**
     * 是否为过期数据，如果是过期数据不能更改
     */
    private Boolean fdOverdue;

    /**
     * 有效开始时间（精准到日）
     */
    private Date fdBegin;

    /**
     * 结束时间（精准到日）
     */
    private Date fdEnd;

    /**
     * 排班主信息ID
     */
    private String fdScheduleMainId;

    /**
     * 类型：1：动态排班（固定班制），2：手动排班（排班制）
     */
    private Integer fdType;

    /**
     * 班次主信息ID，仅在排班制时使用
     */
    private String fdClassesMainId;

    /**
     * 优先级：1、普通、2、例外
     */
    private Integer fdLevel;

    /**
     * 前端拓展字段-下标
     */
    private String fdIndex;

    /**
     * 当前年月份 yyyy-MM
     */
    private String fdMonth;

    private IdNamePropertyExtend fdCreator;

    /**
     * 返回当前人员的排班信息
     */
    private SysTimeClassesMainVO fdClassesMain;

    /**
     * 判断这条数据是否为手动排班范围，用来判断排班冲突，true为是
     */
    private Boolean fdIsManualScope;

    /**
     * 能否被修改
     */
    private Boolean fdCanChange;

    /**
     * 当天对应的排班
     */
    private SysTimeScheduleFixationVO fdFixation;

    /**
     * 当天为节假日的补班
     */
    private Boolean fdIsReplenishWork;

    private Date fdCreateTime;

    private Date fdAlterTime;
}
