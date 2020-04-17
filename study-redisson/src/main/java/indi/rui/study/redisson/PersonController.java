package indi.rui.study.redisson;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@RestController
@RequestMapping("person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("add")
    public String add(@RequestBody Person person) {
        personService.add(person);
        return "OK";
    }

    @PostMapping("testTx")
    public String testTx(@RequestBody Person person) {
        personService.testTx(person);
        return "OK";
    }

    @PostMapping("addByKafka")
    public String addByKafka(@RequestBody Person person) {
        personService.addByKafka(person);
        return "OK";
    }

    @PostMapping("batchedAdd/{amount}")
    public String batchedAdd(@PathVariable int amount) {
        personService.batchedAdd(amount);
        return "OK";
    }

    @PostMapping("findAll")
    public QueryResult<Person> findAll(@RequestBody QueryRequest request) {
        return personService.findAll(request);
    }

    @PostMapping("get/{fdId}")
    public Person get(@PathVariable Long fdId) {
        return personService.findById(fdId);
    }

    @PostMapping("update")
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @PostMapping("deleteByGender/{gender}")
    public int deleteByGender(@PathVariable GenderEnum gender) {
        return personService.deleteByGender(gender);
    }

    @PostMapping("deleteByName/{name}")
    public int deleteByName(@PathVariable String name) {
        return personService.deleteByName(name);
    }

    @PostMapping("deleteById/{id}")
    public int deleteById(@PathVariable Long id) {
        return personService.deleteById(id);
    }

    @PostMapping("deleteAll")
    public int deleteAll(@RequestBody List<Long> ids) {
        return personService.deleteAll(ids);
    }

    @PostMapping("deleteBetweenIds/{start}/{end}")
    public int deleteBetweenIds(@PathVariable Long start, @PathVariable Long end) {
        return personService.deleteBetweenIds(start, end);
    }

    @PostMapping("send")
    public String send() {
        personService.send();
        return "ok";
    }

    @PostMapping("distinctRandomPut")
    public String distinctRandomPut() {
        personService.distinctRandomPut();
        return "ok";
    }

    @PostMapping("distinctRandomPutAll")
    public String distinctRandomPutAll() {
        personService.distinctRandomPutAll();
        return "ok";
    }

    @PostMapping("distinctRandomGet")
    public Set<Integer> distinctRandomGet() {
        return personService.distinctRandomGet();
    }

    @PostMapping("rsetGet")
    public Set<Integer> rsetGet() {
        return personService.rsetGet();
    }

    @PostMapping("addUpdateAndDelete")
    public String addUpdateAndDelete() {
        return personService.addUpdateAndDelete();
    }
}
