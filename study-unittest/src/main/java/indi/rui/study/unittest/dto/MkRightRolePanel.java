package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-01-07
 */
@Getter
@Setter
public class MkRightRolePanel {
    private String fdModule;
    private String header;
    private List<Option> options;

    @Getter
    @Setter
    public static class Option {
        private String fdId;
        private String fdName;
        private String fdOrder;
        private String fdDesc;
    }
}
