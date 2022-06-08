package indi.rui.study.unittest.dto.template;

import indi.rui.study.unittest.constant.TemplateType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 消息通知模板VO
 *
 * @author 叶中奇
 */
@Getter
@Setter
public class SysNotifyTemplateVO {

    private String fdId;

    /**
     * 模板名称
     */
    private String fdName;

    /**
     * 模板编码，一种消息类型编码唯一
     */
    private String fdCode;

    /**
     * 所属模块
     */
    private String fdModule;

    /**
     * 指定引用的模板编码，可以使用模板的可替换参数
     */
    private String fdRefCode;

    /**
     * 消息类型，存储扩展点ID，例外：0表示通用类型
     */
    private List<SysNotifyTemplateUnitVO> fdTemplateUnit;

    /**
     * 各种消息类型的展现字段
     */
    private List<ProviderDisplay> providersDisplay;

    /**
     * 创建人ID
     */
    private String fdCreatorId;
    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 启用状态：0-禁用；1-启用
     */
    private Boolean fdEnabled;

    /**
     * 模板类型：系统模板、自定义模板或私有模板
     */
    private TemplateType fdType;

    /**
     * 自定义变量
     */
    private List<RepVariable> customVars;

    private Date fdCreateTime;
}
