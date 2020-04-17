package indi.rui.study.user;

import indi.rui.study.user.annotation.UserExtensionService;
import indi.rui.study.user.annotation.UserExtensionProvider;
import org.springframework.stereotype.Component;

/**
 * @author: yaowr
 * @create: 2019-10-14
 */
@UserExtensionService(id = "user")
@Component
public class UserService implements UserExtensionProvider {
    @Override
    public String getUsername(String id) {
        return String.format("用户%s", System.currentTimeMillis());
    }
}
