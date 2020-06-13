package indi.rui.study.redisson.person;

import indi.rui.study.common.IApi;

import java.util.List;
import java.util.Set;

/**
 * @author: yaowr
 * @create: 2020-06-13
 */
public interface PersonApi extends IApi<PersonVO> {

    void testTx(Person person);

    void batchedAdd(int amount);

    Person randomPerson(long num);

    Person findById(Long id);

    Person update(Person person);

    int deleteByGender(GenderEnum gender);

    int deleteByName(String name);

    int deleteById(Long id);

    int deleteAll(List<Long> ids);

    int deleteBetweenIds(Long start, Long end);

    void send();

    void addByKafka(Person person);

    void distinctRandomPut();

    void distinctRandomPutAll();

    Set<Integer> distinctRandomGet();

    Set<Integer> rsetGet();

    String addUpdateAndDelete();
}
