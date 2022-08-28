package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ID对象，用于Controller/API的get方法，可指定加载哪些机制
 *
 * @author 叶中奇
 */
@Getter
@Setter
@ToString(callSuper = true)
public class IdVO {

    private String fdId;

    public static IdVO of(String fdId) {
        IdVO vo = new IdVO();
        vo.setFdId(fdId);
        return vo;
    }
}
