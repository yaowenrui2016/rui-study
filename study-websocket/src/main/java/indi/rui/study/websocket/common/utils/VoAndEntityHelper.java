package indi.rui.study.websocket.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2020-06-12
 */
public class VoAndEntityHelper {

    private static final List<String> IGNORE_PROPS = new ArrayList<>();
    static {
        IGNORE_PROPS.add("dynamicProps");
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : new ArrayList<>());
        for (PropertyDescriptor targetPd : BeanUtils.getPropertyDescriptors(target.getClass())) {
            String propertyName = targetPd.getName();
            if (IGNORE_PROPS.contains(propertyName) || ignoreList.contains(propertyName)) {
                continue;
            }
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (Objects.isNull(value)) {
                                continue;
                            }
                            writeMethod.invoke(target, value);
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
}
