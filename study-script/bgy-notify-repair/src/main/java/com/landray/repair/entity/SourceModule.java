package com.landray.repair.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2022-01-27
 */
@Getter
@Setter
@Entity
@Table( name = "sys_notify_source_module" )
public class SourceModule {
    @Id
    private String fdId;

    private int fdTenantId;

    private String fdName;

    private String fdCode;

    private String fdDomain;

    private Boolean fdEnabled;

    private Boolean fdIsPreset;

    private String fdCreatorId;

    private String fdSourceId;

    private Date fdCreateTime;

    private Date fdLastModifiedTime;
}
