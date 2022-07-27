package com.landray.notify.update.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2022-01-28
 */
@Getter
@Setter
@Entity
@Table(name = "sys_notify_source_app_module",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fdAppId", "fdModuleId", "fdSourceId"}))
public class SourceAppModule implements Serializable {
    @Id
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
