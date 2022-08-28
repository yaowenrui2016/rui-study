package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysTimeClassesDetailVO {

    private String fdId;

    private String fdClassMainId;

    private Integer fdWorkBegin;

    private Integer fdWorkEnd;

    private Integer fdRestBegin;

    private Integer fdRestEnd;

    private Integer fdOrder;

    public Integer getFdOrder() {
        return fdOrder == null ? 0 : fdOrder;
    }
}
