package indi.rui.study.websocket.notify;

import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 消息上下文
 *
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
public class NotifyContext {
    /**
     * 主题
     */
    @NotBlank
    private String subject;

    /**
     * 消息ID，非必填，为空则系统自动分配
     */
    private Long notifyId;

    /**
     * 来源应用名称
     */
    @NotBlank
    private String appName;

    /**
     * 来源模块名称
     */
    @NotBlank
    private String moduleName;

    /**
     * 主域模型名称
     */
    @NotBlank
    private String entityName;

    /**
     * 主域模型ID
     */
    @NotBlank
    private String entityId;

    /**
     * 接收人，默认为ID
     */
    @NotEmpty
    private List<String> targets;

    /**
     * 接收人的属性
     */
    private String targetProperty;

    /**
     * 消息发送器：邮件(email)、系统消息(sysmsg)、短信(sms)等
     */
    @NotBlank
    private String sender;

    /**
     * 消息发送器参数，由sender定义
     */
    private JSONArray senderParameters;

    /**
     * 扩展字段，预留4个扩展字段fdExtendField0、fdExtendField1、fdExtendField2、fdExtendField3
     */
    private JSONArray extendFields;
}
