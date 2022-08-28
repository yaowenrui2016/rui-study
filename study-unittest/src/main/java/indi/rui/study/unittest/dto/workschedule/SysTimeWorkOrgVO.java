package indi.rui.study.unittest.dto.workschedule;

import indi.rui.study.unittest.dto.AbstractVO;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 排班组织范围封装类
 *
 * @author: yaowr
 * @create: 2022-08-16
 */
@Getter
@Setter
public class SysTimeWorkOrgVO extends AbstractVO {

    private IdNameProperty fdWorkSchedule;

    private String fdOrgId;

    private String fdOrgName;

    private Integer fdOrgType;

    private String fdHierarchyId;
}
