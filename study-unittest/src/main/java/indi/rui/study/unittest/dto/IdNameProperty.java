package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 通用展现对象：fdId,fdName
 *
 * @author 叶中奇
 */
@Getter
@Setter
public class IdNameProperty {
    private String fdId;
    private String fdName;

    public static IdNameProperty of(String fdId, String fdName) {
        IdNameProperty result = new IdNameProperty();
        result.setFdId(fdId);
        result.setFdName(fdName);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdNameProperty that = (IdNameProperty) o;
        return Objects.equals(getFdId(), that.getFdId()) && Objects.equals(fdName, that.fdName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFdId(), fdName);
    }
}
