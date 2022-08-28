package indi.rui.study.unittest.dto.workschedule;

import indi.rui.study.unittest.dto.AbstractVO;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 排班组
 *
 * @author: yaowr
 * @create: 2022-08-12
 */
@Getter
@Setter
public class SysTimeWorkScheduleVO extends AbstractVO {

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
     * 来源排班ID 排班历史版本
     */
    private String fdSourceSid;

    /**
     * 用于多版本信息备份，如果是备份数据则不做判断
     */
    private Boolean fdIsOverdue = false;


    /**
     * 节假日
     */
    private IdNameProperty fdHoliday;


    /**
     * 时区信息
     */
    private IdNameProperty fdTimeZone;

    /**
     * 创建者
     */
    private IdNameProperty fdCreator;

    /**
     * 可维护者
     */
    private List<IdNameProperty> fdEditors;

    // ================== 排班班次 ================== //
    private List<SysTimeWorkClassesVO> fdClassesList;

    // ================== 固定排班的星期 ================== //
    private List<SysTimeFixedVO> fdFixed;

    // ================== 排班组织架构 ================== //
    private List<SysTimeWorkOrgVO> fdOrgList;

    // ================== 排班工时表 ================== //
    private List<SysTimeWorkTimeVO> fdWorkTimeList;
}
