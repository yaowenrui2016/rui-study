package indi.rui.study.unittest.loganalys.yuexiu0326;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: yaowr
 * @create: 2022-03-28
 */
@Slf4j
public class KafkaPartitionStatistic {

    private static final String DIR_PATH = "C:\\Users\\yaowr\\Downloads\\188notify0328";

    private static String regex = "topic=todo_SysNotifyJobQueue, partition=(.*)?,";


    /**
     * 统计指定目录下的日志文件，根据regex正则表达式匹配找出相应内容
     *
     * @param args
     */
    public static void main(String[] args) {
        String dirPath = DIR_PATH;
        File dirFile = new File(dirPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.error("目录不存在！\"{}\"", dirPath);
            return;
        }
        File[] files = dirFile.listFiles();
        if (files == null || files.length == 0) {
            log.error("目录为空！\"{}\"", dirPath);
            return;
        }
        log.info("扫描目录...{}", dirPath);
        Map<Integer, AtomicInteger> numMap = new HashMap<>();
        for (File file : files) {
            try {
                List<String> lines = FileUtils.readLines(file);
                log.info("读取到\"{}\"中的 {} 行", file.getName(), lines.size());
                for (String line : lines) {
                    Matcher matcher = Pattern.compile(regex).matcher(line);
                    while (matcher.find()) {
                        String num = matcher.group(1);
                        AtomicInteger count = numMap.computeIfAbsent(Integer.valueOf(num),
                                k -> new AtomicInteger(0));
                        count.getAndIncrement();
                    }
                }
            } catch (IOException e) {
                log.error("读取文件失败！\"{}\"", file.getName());
            }
        }
        List<String> resultStrings = new ArrayList<>();
        for (Integer num : new TreeSet<>(numMap.keySet())) {
            resultStrings.add(num + "(" + numMap.get(num).get() + ")");
        }
        log.info("扫描日志发现的kafka分区有：{}", StringUtils.join(resultStrings, "、"));
    }
}
