package indi.rui.study.shard.sample.conference.controller;

import indi.rui.study.shard.sample.conference.service.IStudyShardConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2021-06-30
 */
@RestController
@RequestMapping("/study/shard/conference")
public class StudyShardConferenceController {

    @Autowired
    private IStudyShardConferenceService studyShardConferenceService;

    @PostMapping("add")
    public String add(@RequestBody String name) {
        studyShardConferenceService.add(name);
        return "ok";
    }
}
