package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-12-06
 */
@Getter
@Setter
public class TimeComputingDTO {
    /**
     * 扩展点ID
     */
    private String provider;

    /**
     * 请求到达bus时间
     */
    private String requestTime;

    /**
     * 出队时间
     */
    private String dequeueTime;

    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * 完成状态
     */
    private String status;

    /**
     * 队列耗时，单位: 秒(s)
     */
    private Float dequeueDuration;

    /**
     * 业务逻辑处理耗时，单位: 秒(s)
     */
    private Float businessDuration;

    /**
     * 完整处理耗时，单位: 秒(s)
     */
    private Float totalDuration;
}
