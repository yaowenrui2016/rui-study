package indi.rui.study.unittest.dto.e2e;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.MkReferParseEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-05-17
 */
@Getter
@Setter
public class MkReferTreeNode {

    /**
     * 引用类型
     */
    private String referType;

    /**
     * 引用类型名称
     */
    private String referDispName;

    /**
     * 实体类名
     */
    private String entityName;

    /**
     * 实体是否是组织架构
     */
    private Boolean isOrg;

    /**
     * 实体是否是主文档
     */
    private Boolean isMain;

    /**
     * 是否存在该引用类型的导出provider实现
     */
    private Boolean existTransport;

    // ======================== 冗余实体数据 ======================== //
    private String fdId;
    private String fdName;
    private Integer fdOrgType;

    /**
     * 是否是副本节点
     * 如果fdId对应的实体数据存在，那么该节点就是副本节点
     */
    private Boolean isCopy;

    /**
     * 实体数据
     */
    private JSONObject data;

    /**
     * 关联解析
     */
    private List<MkReferParseEntry> refParEntries;

    /**
     * 实体数据引用的关联项
     */
    private List<MkReferTreeNode> referNodes;

    /**
     * 实体数据被引用的节点列表
     */
    private List<ReferByNode> referByNodes;

    /**
     * 目标环境是否存在
     */
    private Boolean exist;

    /**
     * 操作名
     */
    private String operation;

    /**
     * 替换组织架构
     */
    private JSONObject replaceOrg;


    @Getter
    @Setter
    public static class ReferByNode {

        /**
         * 引用类型
         */
        private String referType;

        /**
         * 引用类型名称
         */
        private String referDispName;

        // ======================== 实体数据字段 ======================== //
        private String fdId;
        private String fdName;

        /**
         * 引用路径
         */
        private String path;

        /**
         * 引用路径描述
         */
        private String pathDesc;
    }


    /**
     * 添加被关联节点
     *
     * @param referByNode
     */
    public void addReferBy(ReferByNode referByNode) {
        if (referByNode != null) {
            if (referByNodes == null) {
                referByNodes = new ArrayList<>();
            }
            referByNodes.add(referByNode);
        }
    }

    public ReferByNode toReferByNode(String path, String pathDesc) {
        ReferByNode referByNode = new ReferByNode();
        referByNode.setReferType(this.referType);
        referByNode.setReferDispName(this.referDispName);
        referByNode.setPath(path);
        referByNode.setPathDesc(pathDesc);
        referByNode.setFdId(this.fdId);
        referByNode.setFdName(this.fdName);
        return referByNode;
    }
}
