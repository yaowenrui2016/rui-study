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
public class MkNotifyTemplateDetailVO {
    /**
     * 主键
     */
    private String fdId;
    /**
     * 所属模板主表
     */
    private IdNameProperty fdMain;

    /**
     * 冗余存储模板编码
     */
    private String fdCode;

    /**
     * 消息类型，存储扩展点ID
     */
    private String fdNotifyType;

    /**
     * 消息类型，接收前端多选值
     */
    private List<String> notifyTypes;

    /**
     * 模板类型
     */
    private String fdType;

    /**
     * 创建人ID
     */
    private String fdCreatorId;

    /**
     * 启用状态：0-禁用；1-启用
     */
    private Boolean fdEnabled;

    /**
     * 用于前端渲染唯一标识
     */
    private String fdKey;

    /**
     * 多语言标识
     */
    private String fdLang;

    /**
     * 标题模板体
     * 多语言字段
     */
    private String fdSubject;

    /**
     * 富文本类型的内容模板体
     * 多语言字段
     */
    private String fdContentHtml;

    /**
     * 图文类型的内容模板体
     * 多语言字段
     */
    private String fdText;

    /**
     * 图文类型的附件ID
     */
    private String fdAttachId;

    /**
     * 创建时间
     */
    private Date fdCreateTime;
}
