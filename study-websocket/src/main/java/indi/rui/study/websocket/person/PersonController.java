package indi.rui.study.websocket.person;

import indi.rui.study.common.controller.AbstractController;
import indi.rui.study.common.controller.ICombineController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
@RestController
@RequestMapping("/service/person")
public class PersonController extends
        AbstractController<PersonVO, PersonService> implements
        ICombineController<PersonVO, PersonService> {
}
