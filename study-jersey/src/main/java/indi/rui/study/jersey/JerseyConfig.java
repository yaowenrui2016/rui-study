package indi.rui.study.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * @author: yaowr
 * @create: 2020-02-28
 */
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(EndPoint.class);
    }
}
