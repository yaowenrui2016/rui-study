package indi.rui.study.unittest.dto.template;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 模板元数据
 *
 * @author: yaowr
 * @create: 2022-02-28
 */
@Getter
@Setter
public class TemplateMetaDTO {

    /**
     * 系统模板
     */
    private List<SystemTemplate> systemTemplates;

    /**
     * 消息扩展点呈现样式
     */
    private List<ProviderDisplay> providersDisplay;

    /**
     * 标准消息模板参数
     */
    private List<RepVariable> standardVars;
}
