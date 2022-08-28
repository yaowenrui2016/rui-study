package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class SysTimeScheduleRuleVO {

    private String fdId;

    private String fdScheduleMainId;

    private String fdName;

    private String fdClasses;

    private Date fdCreateTime;

    private Date fdAlterTime;
}
