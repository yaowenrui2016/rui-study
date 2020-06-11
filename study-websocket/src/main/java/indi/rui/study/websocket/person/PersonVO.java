package indi.rui.study.websocket.person;

import indi.rui.study.websocket.common.dto.AbstractVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-06-08
 */
@Getter
@Setter
public class PersonVO extends AbstractVO {
    private String fdName;
    private String fdLoginName;
    private String fdPassword;
    private String fdGender;
}
