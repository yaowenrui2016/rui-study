package indi.rui.study.shard.sample.conference.mapper;

import indi.rui.study.shard.sample.conference.entity.StudyShardConferenceEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: yaowr
 * @create: 2021-06-30
 */
@Mapper
public interface StudyShardConferenceMapper {

    @Insert("insert into study_shard_conference(name, create_time) values(#{name}, now())")
    void insert(StudyShardConferenceEntity entity);

    StudyShardConferenceEntity select(String id);

    void delete(String id);

    void update(StudyShardConferenceEntity entity);
}
