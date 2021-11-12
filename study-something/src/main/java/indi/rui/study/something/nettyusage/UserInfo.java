package indi.rui.study.something.nettyusage;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-11-11
 */
@Getter
@Setter
public class UserInfo {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "[username=" + username + ", password=" + password + "]";
    }
}
