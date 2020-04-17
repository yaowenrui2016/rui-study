package indi.rui.study;

import java.lang.annotation.Annotation;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import indi.rui.study.plugin.Extension;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2019-10-14
 */
@Slf4j
@Component
public class Manager implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        String var2 = System.getProperty("java.security.manager");
//        log.info(var2);
//        log.info(ClassLoader.getSystemClassLoader().toString());
//        log.info(Arrays.toString(Launcher.getBootstrapClassPath().getURLs()));

//        URL ap = ClassLoader.getSystemResource("application.properties");
//        log.info(ap.getFile());

//        try {
//            Enumeration<URL> urlEnumeration = ClassLoader.getSystemResources("");
//            while (urlEnumeration.hasMoreElements()) {
//                URL url = urlEnumeration.nextElement();
//                log.info(url.getFile());
//                String[] subs = new File(url.getFile()).list();
//                log.info(Arrays.toString(subs));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Reflections reflections = Reflections.collect("indi.rui.study", s -> true);
        Reflections reflections = new Reflections(ConfigurationBuilder.build(
                Manager.class,
                new TypeElementsScanner(),
                new SubTypesScanner(false),
                new MethodAnnotationsScanner(),
                new TypeAnnotationsScanner()
                ));
        reflections.getTypesAnnotatedWith(Extension.class).stream()
                .forEach(extServiceCls -> {
                    Extension extension = extServiceCls.getAnnotation(Extension.class);
                    if (extension == null) {
                        log.warn("{}的extension为空了", extServiceCls.getCanonicalName());
                        return;
                    }
                    log.info("扩展服务名称: {}, 注解: {}, 接口: {}", extension.name(), extServiceCls.getSimpleName(), extension.provider().getCanonicalName());
                    reflections.getTypesAnnotatedWith((Class<? extends Annotation>) extServiceCls).stream()
                            .forEach(extProviderCls -> {
                                log.info("扩展实现: {}", extProviderCls.getCanonicalName());
                                Object bean = context.getBean(extProviderCls);
                                if (bean != null) {
                                    log.info("扩展提供者：", bean);
                                }
                            });
                });

        log.info("我的名字叫：{}", context.getDisplayName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
