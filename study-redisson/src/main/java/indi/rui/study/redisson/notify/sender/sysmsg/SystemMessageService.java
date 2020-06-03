package indi.rui.study.redisson.notify.sender.sysmsg;

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
public class SystemMessageService {

    @Autowired
    private SystemMessageRepository repository;

    public List<SystemMessageVO> findAll() {
        Iterator<SystemMessage> iterator = repository.findAll().iterator();
        List<SystemMessageVO> rtnList = new ArrayList<>();
        while (iterator.hasNext()) {
            SystemMessageVO vo = new SystemMessageVO();
            BeanUtils.copyProperties(iterator.next(), vo);
            rtnList.add(vo);
        }
        return rtnList;
    }

    @Transactional
    public void save(SystemMessageVO vo) {
        SystemMessage entity = new SystemMessage();
        BeanUtils.copyProperties(vo, entity, "fdId");
        repository.save(entity);
    }
}
