package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-18
 */
@Getter
@Setter
public class InformedMethodDTO {

    private List<NotifyTypeVO> notifyTypes;

    public static InformedMethodDTO of(List<NotifyTypeVO> notifyTypes) {
        InformedMethodDTO informedMethod = new InformedMethodDTO();
        informedMethod.setNotifyTypes(notifyTypes);
        return informedMethod;
    }
}
