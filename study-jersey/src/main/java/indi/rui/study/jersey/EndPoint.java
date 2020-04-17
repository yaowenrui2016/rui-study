package indi.rui.study.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

/**
 * @author: yaowr
 * @create: 2020-02-28
 */
@Component
@Path("/hello")
public class EndPoint {

    @GET
    public String hello() {
        return "hello";
    }
}
