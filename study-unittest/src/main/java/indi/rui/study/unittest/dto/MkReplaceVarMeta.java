package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-02-22
 */
@Getter
@Setter
public class MkReplaceVarMeta {
    /**
     * 变量名
     */
    private String name;

    /**
     * 显示名
     */
    private String displayName;

    public static MkReplaceVarMeta of(String name, String displayName) {
        MkReplaceVarMeta obj = new MkReplaceVarMeta();
        obj.setName(name);
        obj.setDisplayName(displayName);
        return obj;
    }
}
