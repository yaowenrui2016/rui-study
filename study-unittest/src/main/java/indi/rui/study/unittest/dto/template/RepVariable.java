package indi.rui.study.unittest.dto.template;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-02-22
 */
@Getter
@Setter
public class RepVariable {

    /**
     * 变量名
     */
    private String varName;

    /**
     * 显示名
     */
    private String displayName;

    public static RepVariable of(String varName, String displayName) {
        RepVariable obj = new RepVariable();
        obj.setVarName(varName);
        obj.setDisplayName(displayName);
        return obj;
    }
}
