package indi.rui.study.shard.sample.conference.service;

import indi.rui.study.shard.sample.conference.entity.StudyShardConferenceEntity;
import indi.rui.study.shard.sample.conference.mapper.StudyShardConferenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: yaowr
 * @create: 2021-06-30
 */
@Service
public class StudyShardConferenceServiceImpl implements IStudyShardConferenceService {

    @Autowired
    private StudyShardConferenceMapper mapper;

    @Override
    public void add(String name) {
        StudyShardConferenceEntity entity = new StudyShardConferenceEntity();
        entity.setName(name);
        mapper.insert(entity);
    }
}
