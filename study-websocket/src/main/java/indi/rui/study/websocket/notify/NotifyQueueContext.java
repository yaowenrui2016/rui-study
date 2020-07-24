package indi.rui.study.websocket.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-07-23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotifyQueueContext {
    /**
     * 操作命令
     * 例如：send,done,remove
     */
    private String command;

    /**
     * 消息摘要
     */
    private String digest;

    /**
     * 消息内容
     */
    private String content;
}
