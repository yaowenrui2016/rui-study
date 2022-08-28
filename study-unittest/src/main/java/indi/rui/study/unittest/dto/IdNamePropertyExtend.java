package indi.rui.study.unittest.dto;

import lombok.Data;

/**
 * @author 武林
 * @date 2021/5/20 10:11
 */
@Data
public class IdNamePropertyExtend extends IdNameProperty {

    public static IdNamePropertyExtend of(String fdId, String fdName, String fdVirtualizeId) {
        IdNamePropertyExtend result = new IdNamePropertyExtend();
        result.setFdId(fdId);
        result.setFdName(fdName);
        result.setFdVirtualizeId(fdVirtualizeId);
        return result;
    }

    public static IdNamePropertyExtend of(String fdId, String fdName, Integer fdOrgType, Boolean fdIsAvailable) {
        IdNamePropertyExtend result = new IdNamePropertyExtend();
        result.setFdId(fdId);
        result.setFdName(fdName);
        result.setFdOrgType(fdOrgType);
        result.setFdIsAvailable(fdIsAvailable);
        return result;
    }

    /**
     * 层级Id
     */
    private String fdHierarchyId;

    /**
     * 维度id
     */
    private String fdVirtualizeId;

    /**
     * 组织类型
     */
    private Integer fdOrgType;

    /**
     * 组织类型
     */
    private Boolean fdIsAvailable;

    /**
     * 原始Id
     */
    private String fdOriId;

}
