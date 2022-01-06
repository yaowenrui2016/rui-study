package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * 原始消息VO
 *
 * @author 金新
 * @date 2020/7/20 15:39
 */
@Getter
@Setter
@ToString
public class MkNotifyOriginal {
    /**
     * 唯一标识
     */
    private String fdId;

    /**
     * 发送消息的唯一ID
     * 由消息发送端产生，仅仅为 send 消息产生的一个唯一ID，其它消息操作均可通过 snid 识别到 send 消息
     */
    private String fdSnid;

    /**
     * 命令
     */
    private String fdCommand;

    /**
     * 消息内容
     */
    private String fdContent;

    /**
     * 原始消息转换的错误信息
     */
    private String fdErrMsg;

    /**
     * 创建时间
     */
    private Date fdCreateTime;

    /**
     * 记录bus收到请求的时间
     */
    private Long fdRequestTime;

    /**
     * 各个通知类型到达时间
     * 格式：{\"todo\":16112992904990,\"email\":16112991864071}
     */
    private Map<String, MkOriginalAdditional> fdAdditional;


    // ================== 冗余消息内容字段 ======================

    /**
     * 主题
     */
    private String fdSubject;

    /**
     * 副标题
     */
    private String fdSubTitle;

    /**
     * 通知类型，多个表示成功消费到的类型，使用','分隔
     */
    private String fdNotifyType;

    /**
     * 第三方消息ID
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

    /**
     * 消息所属主域模型ID
     */
    private String fdEntityId;

    /**
     * 消息所属主域模型全类名
     */
    private String fdEntityName;

    /**
     * 消息在所属主域模型下的关键字
     */
    private String fdEntityKey;

    /**
     * 参数1
     */
    private String fdParameter1;

    /**
     * 参数2
     */
    private String fdParameter2;
}
