package indi.rui.study.websocket.notify.sysmsg;

import indi.rui.study.common.controller.AbstractController;
import indi.rui.study.common.controller.ICombineController;
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
@RequestMapping("/service/notify/sysmsg")
public class SysmsgController extends AbstractController<SysmsgVO, SysmsgApi> implements
        ICombineController<SysmsgVO, SysmsgApi> {
}
