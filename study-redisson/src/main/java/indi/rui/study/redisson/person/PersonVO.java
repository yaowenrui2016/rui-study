package indi.rui.study.redisson.person;

import indi.rui.study.common.dto.AbstractVO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-06-13
 */
@Getter
@Setter
public class PersonVO extends AbstractVO {
    private String fdName;

    private GenderEnum fdGender;

    private Date fdBirthday;

    private String fdComment;

    private Account fdAccount;
}
