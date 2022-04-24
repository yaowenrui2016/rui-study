package com.landray.repair.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2022-01-28
 */
@Getter
@Setter
@Entity
@Table(name = "sys_notify_todo")
public class SysNotifyTodo {

    @Id
    private String fdId;

    /**
     * 发送消息的唯一ID
     * 由消息发送端产生，仅仅为 send 消息产生的一个唯一ID，其它消息操作均可通过 snid 识别到 send 消息
     */
    private String fdSnid;

    /**
     * MD5 摘要
     * 根据黄金字段生成
     */
    private String fdDigest;

    private String fdSubject;

    /**
     * 副标题
     */
    private String fdSubTitle;

    /**
     * 原始消息ID
     */
    private String fdNotifyId;

    /**
     * 来源系统
     */
    private String fdAppName;

    /**
     * 来源模块
     */
    private String fdModuleName;

    private String fdEntityName;

    private String fdEntityId;

    private String fdEntityKey;

    /**
     * 参数1
     */
    private String fdParameter1;

    /**
     * 参数2
     */
    private String fdParameter2;

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
     * 连接地址
     */
    private String fdLink;

    /**
     * 消息归属人
     */
    private String fdOwnerId;

    /**
     * 消息创建人
     */
    private String fdCreatorId;

    private Date fdCreateTime;

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

    /**
     * 待办状态
     */
    private Integer fdStatus;

    /**
     * 待办分类编码
     */
    private String fdCategory;

    /**
     * 系统和模块，系统code:模块code组成
     */
    private String fdAppModule;
}
