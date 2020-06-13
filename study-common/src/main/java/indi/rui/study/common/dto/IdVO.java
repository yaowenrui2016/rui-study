package indi.rui.study.common.dto;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public class IdVO extends AbstractVO {
    public static IdVO of(Long fdId) {
        IdVO idVO = new IdVO();
        idVO.setFdId(fdId);
        return idVO;
    }
}
