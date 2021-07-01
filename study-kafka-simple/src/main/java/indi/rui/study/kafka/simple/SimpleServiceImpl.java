package indi.rui.study.kafka.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2021-06-04
 */
@Slf4j
@Service
@RestController
@RequestMapping("/simple")
public class SimpleServiceImpl implements ISimpleService {

    private AtomicInteger count = new AtomicInteger();

    @Autowired
    private SimpleProducer simpleProducer;

    @Override
    public void publish(String message) {
        simpleProducer.send(message);
    }

    @Override
    public void consume(String message) {
        int num = count.getAndIncrement();
        if (num % 4 == 0) {
            try {
                log.warn("睡眠...");
                Thread.sleep(3050);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("Consume: {}", message);
    }
}
