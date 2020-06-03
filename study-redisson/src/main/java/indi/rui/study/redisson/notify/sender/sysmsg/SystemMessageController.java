package indi.rui.study.redisson.notify.sender.sysmsg;

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
@RequestMapping("/data/notify/sysmsg")
public class SystemMessageController {

    @Autowired
    private SystemMessageService studyRedissonNotifyService;

    @PostMapping("findAll")
    public List<SystemMessageVO> findAll() {
        return studyRedissonNotifyService.findAll();
    }
}
