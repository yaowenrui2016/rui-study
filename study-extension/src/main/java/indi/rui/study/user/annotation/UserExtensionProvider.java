package indi.rui.study.user.annotation;

/**
 * @author: yaowr
 * @create: 2019-10-14
 */
public interface UserExtensionProvider {
    /**
     * 获取用户名
     * @param id
     * @return
     */
    String getUsername(String id);
}
