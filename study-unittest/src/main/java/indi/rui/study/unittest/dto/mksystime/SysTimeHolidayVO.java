package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Desc: 节假日设置
 * @Author: wangwr
 * @Date: 2020/6/20 10:06
 * @Version: v2.0.0
 * @Copy: 深圳市蓝凌软件股份有限公司
 */
@Getter
@Setter
@ToString
public class SysTimeHolidayVO {

    private String fdId;

    /**
     * 节假日名称
     */
    private String fdName;

    /**
     * 创建者姓名(供节假日查询列表用)
     */
    private String fdCreatorName;

    /**
     * 序号(供节假日列表排序用)
     */
    private Integer fdNo;

    /**
     * 节假日设置明细
     */
    private List<SysTimeHolidayDetailVO> fdHolidayDetails;

    private String fdCreator;

    private Date fdCreateTime;

    private String fdAlter;

    private Date fdAlterTime;
}
