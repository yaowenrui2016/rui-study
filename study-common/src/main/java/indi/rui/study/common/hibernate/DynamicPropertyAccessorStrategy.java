package indi.rui.study.common.hibernate;

import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;

/**
 * @author: yaowr
 * @create: 2020-06-12
 */
public class DynamicPropertyAccessorStrategy implements PropertyAccessStrategy {
    @Override
    public PropertyAccess buildPropertyAccess(Class containerJavaType, String propertyName) {
        return new DynamicPropertyAccessor(this, containerJavaType, propertyName);
    }
}
