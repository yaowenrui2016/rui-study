package indi.rui.study.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
@Getter
@Setter
public class IdsVO {
    private List<Long> fdIds = new ArrayList<>();

    public static IdsVO of(Long... fdIds) {
        IdsVO idsVO = new IdsVO();
        idsVO.setFdIds(fdIds == null ? new ArrayList<>() : Arrays.asList(fdIds));
        return idsVO;
    }

    public static IdsVO of(List<Long> fdIds) {
        IdsVO idsVO = new IdsVO();
        idsVO.setFdIds(fdIds);
        return idsVO;
    }
}
