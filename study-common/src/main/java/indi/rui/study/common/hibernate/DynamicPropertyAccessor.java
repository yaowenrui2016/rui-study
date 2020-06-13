package indi.rui.study.common.hibernate;

import indi.rui.study.common.entity.IEntity;
import lombok.AllArgsConstructor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2020-06-12
 */
public class DynamicPropertyAccessor implements PropertyAccess {

    private PropertyAccessStrategy propertyAccessStrategy;
    private Getter getter;
    private Setter setter;

    public DynamicPropertyAccessor(PropertyAccessStrategy propertyAccessStrategy, Class containerJavaType, String propertyName) {
        this.propertyAccessStrategy = propertyAccessStrategy;
        PropertyDescriptor propDesc = BeanUtils.getPropertyDescriptor(containerJavaType, propertyName);
        Method readMethod = propDesc.getReadMethod();
        Method writeMethod = propDesc.getWriteMethod();
        this.getter = new DynamicPropertyGetterSetter(propertyName, readMethod);
        this.setter = new DynamicPropertyGetterSetter(propertyName, writeMethod);
    }

    @Override
    public PropertyAccessStrategy getPropertyAccessStrategy() {
        return propertyAccessStrategy;
    }

    @Override
    public Getter getGetter() {
        return getter;
    }

    @Override
    public Setter getSetter() {
        return setter;
    }


    @AllArgsConstructor
    public static class DynamicPropertyGetterSetter implements Getter, Setter {

        private String propertyName;

        private Method method;

        @Override
        public Object get(Object owner) {
            return ((IEntity) owner).getDynamicProps().get(propertyName);
        }

        @Override
        public void set(Object target, Object value, SessionFactoryImplementor factory) {
            if (!Objects.isNull(value)) {
                ((IEntity) target).getDynamicProps().put(propertyName, value);
            }
        }

        @Override
        public Object getForInsert(Object owner, Map mergeMap, SharedSessionContractImplementor session) {
            return get(owner);
        }

        @Override
        public Class getReturnType() {
            return method.getReturnType();
        }

        @Override
        public Member getMember() {
            return method;
        }

        @Override
        public String getMethodName() {
            return method.getName();
        }

        @Override
        public Method getMethod() {
            return method;
        }
    }
}
