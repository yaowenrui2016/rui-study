package indi.rui.study.redisson.person;

import indi.rui.study.common.controller.AbstractController;
import indi.rui.study.common.controller.ICombineController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@RestController
@RequestMapping("/service/person")
public class PersonController extends 
        AbstractController<PersonVO, PersonApi> implements 
        ICombineController<PersonVO, PersonApi> {

    @PostMapping("testTx")
    public String testTx(@RequestBody Person person) {
        getApi().testTx(person);
        return "OK";
    }

    @PostMapping("addByKafka")
    public String addByKafka(@RequestBody Person person) {
        getApi().addByKafka(person);
        return "OK";
    }

    @PostMapping("batchedAdd/{amount}")
    public String batchedAdd(@PathVariable int amount) {
        getApi().batchedAdd(amount);
        return "OK";
    }

    @PostMapping("get/{fdId}")
    public Person get(@PathVariable Long fdId) {
        return getApi().findById(fdId);
    }

    @PostMapping("deleteByGender/{gender}")
    public int deleteByGender(@PathVariable GenderEnum gender) {
        return getApi().deleteByGender(gender);
    }

    @PostMapping("deleteByName/{name}")
    public int deleteByName(@PathVariable String name) {
        return getApi().deleteByName(name);
    }

    @PostMapping("deleteById/{id}")
    public int deleteById(@PathVariable Long id) {
        return getApi().deleteById(id);
    }

    @PostMapping("deleteAll")
    public int deleteAll(@RequestBody List<Long> ids) {
        return getApi().deleteAll(ids);
    }

    @PostMapping("deleteBetweenIds/{start}/{end}")
    public int deleteBetweenIds(@PathVariable Long start, @PathVariable Long end) {
        return getApi().deleteBetweenIds(start, end);
    }

    @PostMapping("send")
    public String send() {
        getApi().send();
        return "ok";
    }

    @PostMapping("distinctRandomPut")
    public String distinctRandomPut() {
        getApi().distinctRandomPut();
        return "ok";
    }

    @PostMapping("distinctRandomPutAll")
    public String distinctRandomPutAll() {
        getApi().distinctRandomPutAll();
        return "ok";
    }

    @PostMapping("distinctRandomGet")
    public Set<Integer> distinctRandomGet() {
        return getApi().distinctRandomGet();
    }

    @PostMapping("rsetGet")
    public Set<Integer> rsetGet() {
        return getApi().rsetGet();
    }

    @PostMapping("addUpdateAndDelete")
    public String addUpdateAndDelete() {
        return getApi().addUpdateAndDelete();
    }
}
