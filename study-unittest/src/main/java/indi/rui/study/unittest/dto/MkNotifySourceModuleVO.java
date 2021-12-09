package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-07-07
 */
@Getter
@Setter
public class MkNotifySourceModuleVO {

    /**
     * 主键
     */
    private String fdId;
    /**
     * 来源模块统名称
     */
    private String fdName;

    /**
     * 编码
     */
    private String fdCode;

    /**
     * 是否预置
     */
    private Boolean fdIsPreset;

    /**
     * 是否启用
     */
    private Boolean fdEnabled;

    /**
     * 所属来源系统
     */
    private List<IdNameProperty> fdSourceApp;

    /**
     * 创建人，用作显示
     */
    private IdNameProperty fdCreator;

    /**
     * 创建者 ID，与 entity 的映射
     */
    private String fdCreatorId;

    /**
     * 操作源ID
     */
    private String fdSourceId;
}
