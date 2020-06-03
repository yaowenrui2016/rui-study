package indi.rui.study.redisson.notify;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Service
public class StudyRedissonNotifyService {

    @Autowired
    private StudyRedissonNotifyRepository repository;

    public List<StudyRedissonNotifyVO> findAll() {
        Iterator<StudyRedissonNotify> iterator = repository.findAll().iterator();
        List<StudyRedissonNotifyVO> rtnList = new ArrayList<>();
        while (iterator.hasNext()) {
            StudyRedissonNotifyVO vo = new StudyRedissonNotifyVO();
            BeanUtils.copyProperties(iterator.next(), vo);
            rtnList.add(vo);
        }
        return rtnList;
    }

    @Transactional
    public void save(StudyRedissonNotifyVO vo) {
        StudyRedissonNotify entity = new StudyRedissonNotify();
        BeanUtils.copyProperties(vo, entity, "fdId");
        repository.save(entity);
    }
}
