package indi.rui.study.unittest.dto.mksystime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class SysTimeClassesVO {

    /**
     *
     */
    private SysTimeClassesMainVO main;

    /**
     *
     */
    private List<SysTimeClassesDetailVO> detail;

    /**
     *
     */
    private List<SysTimeClassesVindicatorVO> vindicator;

    /**
     *
     */
    private Integer workTime;

    /**
     *
     */
    private Date WorkDate;
}
