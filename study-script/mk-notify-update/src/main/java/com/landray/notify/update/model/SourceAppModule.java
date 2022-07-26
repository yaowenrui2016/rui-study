package com.landray.notify.update.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        if (fdId != null) {
            return Objects.hashCode(fdId);
        } else {
            return Objects.hash(fdAppId, fdModuleId, fdSourceId);
        }
    }
}
