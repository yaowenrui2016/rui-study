package indi.rui.study.unittest;

import indi.rui.study.unittest.interf.MonitorTestPlan;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaowr
 * @create: 2021-11-09
 */
@Slf4j
public class ConsoleTestPlanExecutor {

    // ===================== 静态成员变量 ===================== //

    private static int executeInterval = Integer.valueOf(System.getProperty(
            "mk.executeInterval.ms", String.valueOf(-1)));

    private static int monitorInterval = Integer.valueOf(System.getProperty(
            "mk.monitorInterval.s", String.valueOf(60)));

    private static LinkedBlockingQueue<Integer> executorQueue = new LinkedBlockingQueue<>(
            100);

    private static LinkedBlockingQueue<Integer> monitorQueue = new LinkedBlockingQueue<>(
            100);

    private static boolean executorRunning = false;

    private static boolean monitorRunning = false;

    private static long lastMonitorTime = System.currentTimeMillis();


    // 完整启动命令：
    // java -jar -Dmk.address=http://localhost:8040 -Dmk.xServiceName=73456775666d4c416f73776139584a4131432f6847413d3d -Dmk.execInterval.ms=100 -Dmk.monitorInterval.s=60 lib\study-unittest-sourceapp-0.0.1.SNAPSHOT.jar
    public static void main(String[] args) {
        Properties props = new Properties();
        String className = null;
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    "unittest.properties"));
            className = "indi.rui.study.unittest." + props.getProperty("test.plan");
            Class<? extends MonitorTestPlan> clazz = (Class<? extends MonitorTestPlan>) Class.forName(className);
            MonitorTestPlan testPlan = clazz.newInstance();
            log.info(">>>> {} <<<<", testPlan.planName());

            // 启动监控
            monitor(testPlan);

            // 执行测试计划
            test(testPlan);

            // 通过控制台手动触发查询以及停止程序
            Scanner scanner = new Scanner(System.in);
            log.info("\n===请输入'stop'停止执行===" +
                    "\n===再次输入'stop'则停止监控查询===" +
                    "\n===输入'exec'立即执行===" +
                    "\n===输入其他任意字符立即触发监控查询===");
            while (true) {
                String input = scanner.next();
                if (executorRunning && "stop".equalsIgnoreCase(input)) {
                    executorRunning = false;
                    wakeUpExecutor();
                } else if (executorRunning && "exec".equalsIgnoreCase(input)) {
                    wakeUpExecutor();
                } else if (monitorRunning && "stop".equalsIgnoreCase(input)) {
                    monitorRunning = false;
                    wakeUpMonitor();
                    break;
                } else {
                    wakeUpMonitor();
                }
            }
            log.info("accomplish!");
        } catch (IOException e) {
            throw new RuntimeException("Load [global.properties] error", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class [" + className + "] not found", e);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("New instance of [" + className + "] failed", e);
        } catch (Exception e) {
            throw new RuntimeException("Unknown exception", e);
        }
    }


    // ===================== 主要API ===================== //

    public static void test(MonitorTestPlan testPlan) {
        executorRunning = true;
        new Thread(() -> {
            long begin = System.currentTimeMillis();
            // 执行用例之前清理数据
            while (executorRunning) {
                try {
                    // 执行用例
                    testPlan.test();
                } catch (Exception e) {
                    log.error("executor run exception!", e);
                }
                if (executeInterval > 0) {
                    try {
                        executorQueue.poll(executeInterval, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                    }
                } else if (executeInterval < 0) {
                    executorRunning = false;
                }
            }
            long end = System.currentTimeMillis();
            log.info("executor stopped. [duration={}(s)]", (end - begin) / 1000f);
        }, "executor").start();
    }


    public static void monitor(MonitorTestPlan testPlan) {
        monitorRunning = true;
        new Thread(() -> {
            while (true) {
                if (monitorInterval > 0) {
                    try {
                        monitorQueue.poll(monitorInterval, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                    }
                }
                if (!monitorRunning) {
                    break;
                }
                long currentMonitorTime = System.currentTimeMillis();
                float monitorDuration = (currentMonitorTime - lastMonitorTime) / 1000f;
                lastMonitorTime = currentMonitorTime;
                long begin = System.currentTimeMillis();
                // 监控查询
                log.info("---------------- monitor interval: {}(s) ----------------\n" +
                                "{}\n" +
                                "executor status: {}\n" +
                                "monitor  status: {}",
                        monitorDuration,
                        testPlan.monitor(),
                        executorRunning ? "running" : "stopped",
                        monitorRunning ? "running" : "stopped");
                long end = System.currentTimeMillis();
                log.info("duration: {}(s)\n",
                        (end - begin) / 1000f);
            }
            log.info("monitor stopped.");
        }, "monitor").start();
    }

    private static void wakeUpMonitor() {
        try {
            monitorQueue.add(1);
        } catch (Exception e) {
            log.error("add permit failed!", e);
        }
    }

    private static void wakeUpExecutor() {
        try {
            executorQueue.add(1);
        } catch (Exception e) {
            log.error("add permit failed!", e);
        }
    }
}
