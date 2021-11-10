package indi.rui.study.unittest.interf;

/**
 * @author: yaowr
 * @create: 2021-11-09
 */
public interface TestPlan {

    default String planName() {
        return "Custom Test Plan";
    }

    void test();
}
