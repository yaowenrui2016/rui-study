package com.landray.repair.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: yaowr
 * @create: 2022-04-25
 */
@Getter
@Setter
@Entity
@Table(name = "sys_org_element_summary")
public class SysOrgElementSummary {

    @Id
    private String fdId;

    private String fdLoginName;

    private String fdName;

    private Boolean fdIsAvailable;
}
