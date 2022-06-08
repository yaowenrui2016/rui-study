package indi.rui.study.unittest.dto.e2e;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-04-08
 */
@Getter
@Setter
public class MkSysMechTptEnvToEnvTaskVO {

    private String fdId;

    /**
     * 任务标题
     */
    private String fdSubject;

    /**
     * 操作人
     */
    private String fdOperator;

    /**
     * 类型：在线或离线
     */
    private String fdType;

    /**
     * 开始执行时间
     */
    private Date fdStartTime;

    /**
     * 导入完成时间
     */
    private Date fdEndTime;

    /**
     * 创建时间
     */
    private Date fdCreateTime;

    /**
     *
     */
    private List<MkSysEnvToEnvImportDataVO> importDataVOList;

    private Integer success;

    private Integer failed;
}
