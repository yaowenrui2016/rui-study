package indi.rui.study.unittest.dto.original;

import indi.rui.study.unittest.dto.MkOriginalStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-12-06
 */
@Getter
@Setter
public class OriginalAdditional {
    /**
     * 入队时间，即进入消息队列的时间
     */
    private Long enqueueTime;

    /**
     * 出队时间，即消费者开始处理的时间
     */
    private Long dequeueTime;

    /**
     * 消息处理完成时间
     */
    private Long finishTime;

    /**
     * 消息处理完成状态
     */
    private MkOriginalStatus status;
}