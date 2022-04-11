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
public class NotifyBusLogTodoStatic {

    private static final String DIR_PATH = "C:\\Users\\yaowr\\Downloads\\notifybus";

    private static final String REGEX_PREFIX = "mk-paas-notify-api 2022-03-26 (\\d{2}):\\d{2}:\\d{2}\\.\\d{3}.*?";


    /**
     * 统计指定目录下的日志文件，根据regex正则表达式匹配找出相应内容
     *
     * @param args
     */
    public static void main(String[] args) {
        doStatistic("send", "调用消息中心WebService接口sendTodo方法");
        doStatistic("done", "调用消息中心WebService接口setTodoDone");
    }

    private static void doStatistic(String operation, String regexSuffix) {
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
        log.debug("扫描目录...{}", dirPath);
        Map<Integer, AtomicInteger> numMap = new HashMap<>();
        int amount = 0;
        for (File file : files) {
            try {
                List<String> lines = FileUtils.readLines(file);
                log.debug("读取到\"{}\"中的 {} 行", file.getName(), lines.size());
                String fullLine = null;
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    // 由于越秀的日志，在INFO、WARN、ERROR 后面有换行，因此需要将下一行拼接到以INFO、WARN、ERROR结尾的行
                    if (line.endsWith("INFO ") || line.endsWith("WARN ") || line.endsWith("ERROR ")) {
                        fullLine = line;
                        continue;
                    }
                    fullLine += line;
                    // 处理完整日志行
                    String regex = REGEX_PREFIX + regexSuffix;
                    Matcher matcher = Pattern.compile(regex).matcher(fullLine);
                    while (matcher.find()) {
                        String num = matcher.group(1);
                        AtomicInteger count = numMap.computeIfAbsent(Integer.valueOf(num),
                                k -> new AtomicInteger(0));
                        count.getAndIncrement();
                        amount++;
                    }
                    fullLine = null;
                }
            } catch (IOException e) {
                log.error("读取文件失败！\"{}\"", file.getName());
            }
        }
        List<String> resultStrings = new ArrayList<>();
        for (Integer num : new TreeSet<>(numMap.keySet())) {
            resultStrings.add(num + "(" + numMap.get(num).get() + ")");
        }
        log.info("扫描日志发现的 {}({})，按小时数量分布：{}", operation, amount, StringUtils.join(resultStrings, "、"));
    }
}
