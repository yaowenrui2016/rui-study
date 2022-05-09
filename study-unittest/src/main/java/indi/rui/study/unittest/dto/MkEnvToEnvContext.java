package indi.rui.study.unittest.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2022-04-11
 */
@Getter
@Setter
public class MkEnvToEnvContext {
    /**
     * 加密的在线导出口令
     */
    private String encOlCmd;

    /**
     * 主文档类名
     */
    private String entityName;

    /**
     * 简明主文档数据{fdId,fdName,fdCode}
     */
    private List<JSONObject> briefEntityList;

    /**
     * 完整主文档数据
     */
    private List<JSONObject> completeEntityList;

    /**
     * 简明关联数据{fdId,fdName,fdCode}
     */
    private List<ReferDataGroup> briefReferList;

    /**
     * 完整关联数据
     */
    private List<ReferDataGroup> completeReferList;

    /**
     * 关联信息
     */
    private List<MkReferParseEntry> referEntryList;

    /**
     * 导入状态
     */
    private Map<String, String> status;

    /**
     * 导入进度
     */
    private Integer progress;

    /**
     * 导入结果统计
     */
    private ResultStatistic resultStatistic;

    /**
     * 执行步骤
     */
    private MkExportStep exportStep;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 错误信息
     */
    private String errMsg;


    @Getter
    @Setter
    public static class ReferDataGroup {

        private String entityName;

        private String dispName;

        private List<JSONObject> referData;
    }


    @Getter
    @Setter
    public static class ResultStatistic {

        private Integer accomplish;

        private Integer add;

        private Integer override;

        private Integer failed;
    }
}
