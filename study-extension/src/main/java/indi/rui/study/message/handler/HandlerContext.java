package indi.rui.study.message.handler;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import com.google.common.collect.Maps;

import io.jsonwebtoken.lang.Assert;
import lombok.Getter;

/**
 * @author: yaowr
 * @create: 2019-10-31
 */
public class HandlerContext {

    private ConfigurableBeanFactory beanFactory;

    @Getter
    private Map<String, Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap, ConfigurableBeanFactory beanFactory) {
        Assert.notNull(beanFactory, "beanFactory不能为空");
        if (Objects.isNull(handlerMap)) {
            handlerMap = Maps.newHashMapWithExpectedSize(0);
        }
        this.handlerMap = handlerMap;
        this.beanFactory = beanFactory;
    }

    public Handler getHandler(String type) {
        Class<Handler> clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new RuntimeException("Bean未找到");
        }
        return this.beanFactory.getBean(clazz);
    }
}
