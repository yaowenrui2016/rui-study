package com.landray.repair.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: yaowr
 * @create: 2022-01-28
 */
@Getter
@Setter
@Entity
@Table( name = "sys_notify_source_app_module" )
public class SourceAppModule {
    @Id
    private String fdId;

    private int fdTenantId;

    private String fdAppId;

    private String fdModuleId;

    private String fdSourceId;
}
