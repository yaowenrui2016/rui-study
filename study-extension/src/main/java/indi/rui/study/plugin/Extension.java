package indi.rui.study.plugin;

import indi.rui.study.user.annotation.UserExtensionProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: yaowr
 * @create: 2019-10-15
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Extension {

    String name();

    Class<? extends UserExtensionProvider> provider();
}
