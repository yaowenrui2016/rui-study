package indi.rui.study.unittest.dto.e2e;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-04-30
 */
@Getter
@Setter
public class MkSysEnvToEnvImportDataVO {

    /**
     * 导入数据记录id
     */
    private String fdId;

    /**
     * 主文档id
     */
    private String fdEntityId;

    /**
     * 主文档名称
     */
    private String fdName;

    /**
     * 主文档实体类名
     */
    private String fdEntityName;

    /**
     * 1-等待中
     * 2-导入中
     * 3-成功
     * 4-失败
     */
    private String fdState;

    /**
     * 执行顺序，小的优先
     */
    private Integer fdOrder;

    /**
     * 所属任务
     */
    private IdNameProperty fdTask;

    /**
     * attachId 或 encOlCmd
     */
    private String fdFlag;

    /**
     * 所属任务
     */
    private String fdTaskId;

    /**
     * 主文档数据
     */
    private JSONObject fdData;
}
