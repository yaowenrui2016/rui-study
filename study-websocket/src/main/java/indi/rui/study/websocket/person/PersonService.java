package indi.rui.study.websocket.person;

import indi.rui.study.websocket.common.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2020-06-08
 */
@Service
@RestController
@RequestMapping("/api/person")
public class PersonService extends AbstractService<Person, PersonVO, PersonRepository> {

}
