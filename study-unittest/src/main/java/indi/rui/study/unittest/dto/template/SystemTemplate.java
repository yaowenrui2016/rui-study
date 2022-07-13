package indi.rui.study.unittest.dto.template;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 系统模板
 *
 * @author: yaowr
 * @create: 2022-06-06
 */
@Getter
@Setter
public class SystemTemplate {

    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板名称
     */
    private Map<String, String> name4Lang;

    /**
     * 标题模板
     */
    private Map<String, String> subject4Lang;

    /**
     * 内容模板
     */
    private Map<String, String> content4Lang;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 扩展参数，来自于模板定义的extendContents和replaceText
     */
    private List<RepVariable> extendVars;
}
