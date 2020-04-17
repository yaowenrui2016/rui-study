package indi.rui.study.micro.eureka;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: yaowr
 * @create: 2020-02-29
 */
@Component
public class Test implements ApplicationContextAware {

    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
