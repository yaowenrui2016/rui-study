package indi.rui.study.unittest.dto.mksystime;

import indi.rui.study.unittest.dto.org.SimplePerson;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Getter
@Setter
public class SysTimeWorkCountVO {
    /**
     * 总时长
     */
    private Integer count;

    /**
     * 人员
     */
    private SimplePerson person;
}
