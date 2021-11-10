package indi.rui.study.unittest.interf;

/**
 * @author: yaowr
 * @create: 2021-11-09
 */
public interface TestPlan {

    /**
     * 测试计划名称
     *
     * @return
     */
    default String planName() {
        return "Custom Test Plan";
    }

    /**
     * 测试计划执行方法
     */
    void test();
}
