package indi.rui.study.unittest.dto.lang;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
public class SysLangEditPageVO {
    /**
     * 目录路径
     */
    private String fdPath;

    /**
     * 文件msgKey
     */
    private String fdMsgKey;

    /**
     * 排序号
     */
    private Integer fdOrder;

    /**
     * 返回多语言map
     */
    private Map<String, String> fdLangResouce;
}
