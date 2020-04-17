package indi.rui.study.message;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息上下文
 * @author: yaowr
 * @create: 2019-10-31
 */
@Getter
@Setter
public class Message {
    private String id;
    private String type;
    private String topic;
    private String content;
}
