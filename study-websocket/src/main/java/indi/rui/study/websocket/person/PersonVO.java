package indi.rui.study.websocket.person;

import indi.rui.study.common.dto.AbstractVO;
import indi.rui.study.common.field.FdCreateTime;
import indi.rui.study.common.field.FdStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-06-08
 */
@Getter
@Setter
public class PersonVO extends AbstractVO implements FdStatus, FdCreateTime {
    private String fdName;
    private String fdLoginName;
    private String fdPassword;
    private String fdGender;
}
