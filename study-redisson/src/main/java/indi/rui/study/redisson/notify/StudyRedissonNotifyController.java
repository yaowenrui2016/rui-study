package indi.rui.study.redisson.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@RestController
@RequestMapping("/data/notify/")
public class StudyRedissonNotifyController {

    @Autowired
    private StudyRedissonNotifyService studyRedissonNotifyService;

    @PostMapping("findAll")
    public List<StudyRedissonNotifyVO> findAll() {
        return studyRedissonNotifyService.findAll();
    }
}
