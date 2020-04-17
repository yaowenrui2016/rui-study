package indi.rui.study.message.handler;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import indi.rui.common.base.util.AnnotationScanUtil;
import indi.rui.study.Application;
import indi.rui.study.message.annotation.HandlerType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2019-10-31
 */
@Slf4j
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Set<Class<?>> cls = AnnotationScanUtil.findTypesAnnotationedWith(Application.class, HandlerType.class);
        HandlerContext handlerContext = new HandlerContext(Maps.newHashMap(), beanFactory);
        if (cls != null) {
            log.info(Arrays.toString(cls.toArray()));
            cls.forEach(clazz -> {
                HandlerType handlerType = clazz.getAnnotation(HandlerType.class);
                handlerContext.getHandlerMap().put(handlerType.value(), clazz);
            });
        }
        beanFactory.registerSingleton(handlerContext.getClass().getSimpleName(), handlerContext);
    }
}
