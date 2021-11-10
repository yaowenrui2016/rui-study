package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: yaowr
 * @create: 2021-11-10
 */
@Getter
@Setter
public class NotifyTodo {

    /**
     * 发送消息的唯一ID
     */
    private String fdSnid;

    /**
     * MD5 摘要
     */
    private String fdDigest;

    /**
     *标题
     */
    private String fdSubject;

    /**
     * 副标题
     */
    private String fdSubTitle;

    /**
     * 主键ID
     */
    private String fdId;

    /**
     * 原始消息ID
     */
    private String fdNotifyId;

    /**
     * 来源系统名称
     */
    private String fdAppName;

    /**
     * 来源系统编码
     */
    private String fdAppCode;

    /**
     * 来源模块名称
     */
    private String fdModuleName;

    /**
     * 来源模块编码
     */
    private String fdModuleCode;

    private String fdEntityId;

    private String fdEntityName;

    private String fdEntityKey;

    /**
     * 待办类型
     */
    private Integer fdType;

    /**
     * 紧急度
     */
    private Integer fdLevel;

    /**
     * 已收藏
     */
    private Boolean fdFavorite;

    /**
     * 已查阅
     */
    private Boolean fdReaded;

    /**
     * MD5摘要，用于辨别重复待办
     */
    private transient String fdMd5;

    /**
     * 连接地址
     */
    private String fdLink;

    /**
     * 移动端连接地址
     */
    private String fdMobileLink;

    /**
     * 扩展参数1
     */
    private String fdParameter1;

    /**
     * 扩展参数2
     */
    private String fdParameter2;

    /**
     * 待办归属人ID
     */
    private String fdOwnerId;

    /**
     * 待办归属人
     */
    private String fdOwner;

    /**
     * 创建人
     * 为了与entity做值copy，列表查询，或展示用fdCreator
     */
    private String fdCreatorId;

    /**
     * 扩展信息
     */
    private String fdExtendContent;

    /**
     * 预留四个扩展字段
     */
    private String fdExtendField0;

    private String fdExtendField1;

    private String fdExtendField2;

    private String fdExtendField3;

    /**
     * 待办真实生成时间
     */
    private Date fdGenerateTime;

    private String fdNodeInfo;

    /**
     * 待办分类编码
     */
    private String fdCategory;

    /**
     * 系统和模块，系统code:模块code组成
     */
    private String fdAppModule;

    /**
     * 消息告警
     */
    private String notifyAlarm;

    /**
     * 节点名称
     * 用于流程类的待办，表示该待办所属的节点
     */
    private String fdNodeName;

    /**
     * 所属企业ID
     */
    private String fdCorp;
    /**
     * 主文档信息
     */
    private String fdDocInfo;
}
