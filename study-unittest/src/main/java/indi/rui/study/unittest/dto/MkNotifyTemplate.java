package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-02-25
 */
@Getter
@Setter
public class MkNotifyTemplate {

    /**
     * 主键
     */
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
     * 指定引用的模板编码，使用其模板的可替换参数，查询模板时可以根据fdRefCode关联查询
     */
    private String fdRefCode;

    /**
     * 消息类型，存储扩展点ID，例外：0表示通用类型
     */
    private List<MkNotifyTemplateDetailVO> fdDetails;

    /**
     * 创建人ID
     */
    private String fdCreatorId;

    /**
     * 创建人名字
     */
    private String fdCreatorName;

    /**
     * 启用状态：0-禁用；1-启用
     */
    private Boolean fdEnabled;

    /**
     * 是否预置
     */
    private Boolean fdPreset;

    /**
     * 创建时间
     */
    private Date fdCreateTime;
}
