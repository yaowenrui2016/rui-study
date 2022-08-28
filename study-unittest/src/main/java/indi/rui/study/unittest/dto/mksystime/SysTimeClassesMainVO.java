package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SysTimeClassesMainVO {

    private String fdId;

    private String fdName;

    private Boolean fdStatus = false;

    private String fdColor;

    private String fdCreatorName;

    private Date fdCreateTime;

    private Date fdAlterTime;
}