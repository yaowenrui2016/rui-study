package indi.rui.study.unittest.dto;

import indi.rui.study.unittest.dto.org.SimplePerson;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author yaowr
 * 2019-04-24
 */
@Getter
@Setter
public class MkRightGroupVO {

    private String fdId;
    private String fdName;
    private String fdDescription;

    private IdNameProperty fdCategory;

    private Boolean fdIsBuiltIn;

    private String fdNumber;

    private String fdCreatorId;

    private String fdLastModifier;

    private List<SimplePerson> fdSysOrgElements;

    private List<IdNameProperty> fdSysRightRoles;

    private List<IdNameProperty> authorities;

    private List<IdNameProperty> fdParentGroups;

    private List<IdNameProperty> parents;

    private List<IdNameProperty> fdMaintains;

    private Date fdCreateTime;

    private Date fdLastModifiedTime;
}
