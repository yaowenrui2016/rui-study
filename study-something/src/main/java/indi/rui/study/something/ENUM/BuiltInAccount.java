package indi.rui.study.something.ENUM;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-08-26
 */
public enum BuiltInAccount {
    ADMIN("1"),
    USER("2");

    BuiltInAccount(String id) {
        this.id = id;
    }

    @Getter
    private String id;

    @Getter
    @Setter
    private String name;
}
