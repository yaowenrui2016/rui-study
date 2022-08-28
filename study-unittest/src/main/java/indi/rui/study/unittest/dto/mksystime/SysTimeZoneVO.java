package indi.rui.study.unittest.dto.mksystime;

import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SysTimeZoneVO {

    private String fdId;

    private String fdName;

    private String fdTimeZone;

    private Integer fdZoneValue;

    private Boolean fdIsOpen;

    private IdNameProperty fdCreator;

    private Date fdCreateTime;

    private Date fdAlterTime;
}
