package indi.rui.study.unittest.loganalys.yuexiu0326;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: yaowr
 * @create: 2022-03-28
 */
@Slf4j
public class NotifyExecFailedStatic {

    private static final String OPERATION_DIR = "C:\\Users\\yaowr\\Desktop\\yuexiu_0326";

    private static final String NOTIFY_DIR = OPERATION_DIR + "\\notify\\notify-gray0326";

    private static final String START_REGEX = "^\\d{1,} mk-paas-notify.*$";

    private static final String REGEX_PREFIX = "^\\d{1,} mk-paas-notify \\d{4}-\\d{2}-\\d{2} (\\d{2}):\\d{2}:\\d{2}\\.\\d{3} \\[sys-notify-todo-kafka-consumer.+待办待阅消息消费发生错误.+$";


    /**
     * 统计指定目录下的日志文件，根据regex正则表达式匹配找出相应内容
     *
     * @param args
     */
    public static void main(String[] args) {
        doStatistic();
    }

    private static void doStatistic() {
        String dirPath = NOTIFY_DIR;
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
        Map<Integer, List<String>> errorMap = new HashMap<>();
        int amount = 0;
        for (File file : files) {
            try {
                List<String> lines = FileUtils.readLines(file);
                log.debug("读取到\"{}\"中的 {} 行", file.getName(), lines.size());
                String fullLine = "";
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    // 由于越秀的日志，在INFO、WARN、ERROR 后面有换行，因此需要将下一行拼接到以INFO、WARN、ERROR结尾的行
                    // 由于错误堆栈是按行显示的，也需要拼接
                    if (i + 1 < lines.size()) {
                        String nextLine = lines.get(i + 1);
                        if (!nextLine.matches(START_REGEX)) {
                            fullLine += (line + "\n");
                            continue;
                        }
                    }
                    fullLine += (line + "\n");
                    // 处理完整日志行
                    Matcher matcher = Pattern.compile(REGEX_PREFIX, Pattern.DOTALL).matcher(fullLine);
                    while (matcher.find()) {
                        String hour = matcher.group(1);
                        String error = fullLine;
                        errorMap.computeIfAbsent(Integer.valueOf(hour),
                                k -> new ArrayList<>()).add(error);
                        amount++;
                    }
                    fullLine = "";
                }
            } catch (IOException e) {
                log.error("读取文件失败！\"{}\"", file.getName());
            }
        }
        // 将错误记录写入文件
        File outDir = new File(OPERATION_DIR);
        if (!outDir.exists() || !outDir.isDirectory()) {
            log.error("目录不存在！\"{}\"", outDir.getName());
            return;
        }
        File errorFile = new File(outDir, "result_error.json");
        try {
            String jsonString = JSONObject.toJSONString(errorMap, SerializerFeature.PrettyFormat);
            FileUtils.write(errorFile, jsonString, false);
        } catch (IOException e) {
            log.error("写入文件失败！\"{}\"", errorFile.getName(), e);
        }
        log.info("扫描日志发现的待办执行错误 {}，写入文件‘{}’成功！", amount, errorFile.getName());
    }
}
