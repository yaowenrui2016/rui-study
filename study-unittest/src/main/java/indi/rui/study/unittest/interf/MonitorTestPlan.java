package indi.rui.study.unittest.interf;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author: yaowr
 * @create: 2021-11-09
 */
public interface MonitorTestPlan extends TestPlan {

    /**
     * 返回监视的信息
     *
     * @return
     */
    String monitor();

    /**
     * 初始化私有字段值
     *
     * @param properties
     */
    default void init(Properties properties) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Value.class)) {
                Value value = fields[i].getAnnotation(Value.class);
                String propName = value.value();
                Object propValue = properties.getProperty(propName);
                if (propValue == null) {
                    throw new RuntimeException("property '" + propName + "' not exists");
                }
                try {
                    String fieldName = fields[i].getName();
                    // 通过setter方法赋值
//                    char[] cs = fieldName.toCharArray();
//                    cs[0] -= 32;
//                    Method writeMethod = this.getClass().getDeclaredMethod("set" + String.valueOf(cs), String.class);
//                    writeMethod.invoke(this, propValue);

                    // 通过字段直接赋值
                    if (!fields[i].isAccessible()) {
                        fields[i].setAccessible(true);
                    }
                    fields[i].set(this, propValue);

                    // 通过bean属性赋值，必须有getter和setter方法
//                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, this.getClass());
//                    pd.getWriteMethod().invoke(this, propValue);

                } catch (Exception e) {
                    throw new RuntimeException("field '" + fields[i].getName() + "' set failed", e);
                }
            }
        }
    }
}

