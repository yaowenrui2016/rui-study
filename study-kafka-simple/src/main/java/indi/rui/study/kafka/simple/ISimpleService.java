package indi.rui.study.kafka.simple;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2021-06-04
 */
public interface ISimpleService {

    @PostMapping("publish")
    void publish(@RequestBody String message);

    void consume(String message);
}
