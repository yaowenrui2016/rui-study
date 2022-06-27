package indi.rui.study.unittest.dto;

import indi.rui.study.unittest.dto.e2e.MkReferGroup;
import indi.rui.study.unittest.dto.e2e.MkReferTreeNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-04-11
 */
@Getter
@Setter
public class MkEnvToEnvContext {

    /**
     * 离线导入的附件id
     */
    private String attachId;

    /**
     * 加密的在线导出口令
     */
    private String encOlCmd;

    /**
     * 主文档类名
     */
    private String entityName;

    /**
     * 主文档节点列表
     */
    private List<MkReferTreeNode> entityNodes;

    /**
     * 关联项分组
     */
    private List<MkReferGroup> referGroupList;

    /**
     * 关联树
     */
    private List<MkReferTreeNode> referTree;

    /**
     * 组织架构节点列表
     */
    private List<MkReferTreeNode> orgNodes;

    /**
     * 导入中数据记录
     */
    private List<ImportingData> importingDataList;

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
    public static class ImportingData {

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
         * 任务标记取 attachId 或 encOlCmd
         */
        private String fdFlag;

        /**
         * 执行顺序，小的优先
         */
        private Integer fdOrder;

        /**
         * 1-等待中
         * 2-导入中
         * 3-成功
         * 4-失败
         */
        private String fdState;

        /**
         * 错误信息
         */
        private String errMsg;
    }
}