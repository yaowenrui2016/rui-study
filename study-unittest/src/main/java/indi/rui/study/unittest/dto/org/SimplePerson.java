package indi.rui.study.unittest.dto.org;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Getter
@Setter
public class SimplePerson {
    /**
     * 人员id
     */
    private String fdId;

    /**
     * 登录名
     */
    private String fdLoginName;

    /**
     * 姓名
     */
    private String fdName;

    /**
     * 组织类型
     */
    private Integer fdOrgType;

    /**
     * 层级id
     */
    private String fdHierarchyId;

    public static SimplePerson of(String fdId, String fdName) {
        SimplePerson person = new SimplePerson();
        person.setFdId(fdId);
        person.setFdName(fdName);
        return person;
    }
}
