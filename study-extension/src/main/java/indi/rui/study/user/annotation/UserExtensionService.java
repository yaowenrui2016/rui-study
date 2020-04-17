package indi.rui.study.user.annotation;

import indi.rui.study.plugin.Extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: yaowr
 * @create: 2019-10-14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Extension(name = "用户注册扩展服务", provider = UserExtensionProvider.class)
public @interface UserExtensionService {
    /**
     * 扩展服务唯一标识
     * @return
     */
    String id();
}
