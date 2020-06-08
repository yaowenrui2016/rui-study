package indi.rui.study.websocket.notify.sender.sysmsg;

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
public class SysmsgService {

    @Autowired
    private SysmsgRepository repository;

    public List<SysmsgVO> findAll() {
        Iterator<SysmsgEntity> iterator = repository.findAll().iterator();
        List<SysmsgVO> rtnList = new ArrayList<>();
        while (iterator.hasNext()) {
            SysmsgVO vo = new SysmsgVO();
            BeanUtils.copyProperties(iterator.next(), vo);
            rtnList.add(vo);
        }
        return rtnList;
    }

    @Transactional
    public void save(SysmsgVO vo) {
        SysmsgEntity entity = new SysmsgEntity();
        BeanUtils.copyProperties(vo, entity, "fdId");
        repository.save(entity);
    }
}
