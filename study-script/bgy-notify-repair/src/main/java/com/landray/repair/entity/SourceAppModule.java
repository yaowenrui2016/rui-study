package com.landray.repair.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: yaowr
 * @create: 2022-01-28
 */
@Getter
@Setter
public class SourceAppModule implements Serializable {

    private String fdId;

    private int fdTenantId;

    private String fdAppId;

    private String fdModuleId;

    private String fdSourceId;
}
