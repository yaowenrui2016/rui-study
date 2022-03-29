package indi.rui.study.unittest.loganalys;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: yaowr
 * @create: 2022-03-28
 */
@Slf4j
public class ExtractNotifyContextFromNotifyLog {

    private static final String OPERATION_DIR = "C:\\Users\\yaowr\\Desktop\\yuexiu_0326";

    private static final String NOTIFY_DIR = OPERATION_DIR + "\\notify";

    private static final String REGEX_PREFIX = "mk-paas-notify \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}.+待办待阅消息队列处理器监听到新消息.+topic = todo, command = (.+),.+, content = (\\{.*\\})$";


    /**
     * 统计指定目录下的日志文件，根据regex正则表达式匹配找出相应内容
     *
     * @param args
     */
    public static void main(String[] args) {
        extract();
    }


    private static void extract() {
        String dirPath = NOTIFY_DIR;
        File dirFile = new File(dirPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.error("目录不存在！\"{}\"", dirPath);
            return;
        }
        File[] subDirs = dirFile.listFiles();
        if (subDirs == null || subDirs.length == 0) {
            log.error("目录为空！\"{}\"", dirFile.getName());
            return;
        }
        log.debug("扫描目录...{}", dirPath);
        Map<String, List<JSONObject>> notifyCtxMap = new HashMap<>();
        int amount = 0;
        for (File subDir : subDirs) {
            File[] files = subDir.listFiles();
            if (files == null || files.length == 0) {
                log.warn("目录为空！\"{}\"", subDir.getName());
                continue;
            }
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
                        Matcher matcher = Pattern.compile(REGEX_PREFIX).matcher(fullLine);
                        while (matcher.find()) {
                            String cmd = matcher.group(1);
                            JSONObject json = JSONObject.parseObject(matcher.group(2));
                            json.put("cmd", cmd);
                            int hash = Objects.hash(
                                    json.get("appName"),
                                    json.get("entityId"),
                                    json.get("entityName")
                            );
                            notifyCtxMap.computeIfAbsent(String.valueOf(hash), k -> new ArrayList<>()).add(json);
                            amount++;
                        }
                        fullLine = null;
                    }
                } catch (IOException e) {
                    log.error("读取文件失败！\"{}\"", file.getName(), e);
                }
            }
        }
        // 写入json文件
        File outDir = new File(OPERATION_DIR);
        if (!outDir.exists() || !outDir.isDirectory()) {
            log.error("目录不存在！\"{}\"", outDir.getName());
            return;
        }
        File jsonFile = new File(outDir, "notify_out.json");
        try {
            String jsonString = JSONObject.toJSONString(notifyCtxMap, SerializerFeature.PrettyFormat);
            FileUtils.write(jsonFile, jsonString, false);
        } catch (IOException e) {
            log.error("写入文件失败！\"{}\"", jsonFile.getName(), e);
        }
        log.info("扫描 notify 日志发现的待办消息消费记录共有 {} 次，写入文件‘{}’成功 {} 次！",
                amount, jsonFile.getName(), notifyCtxMap.size()
        );
    }
}
