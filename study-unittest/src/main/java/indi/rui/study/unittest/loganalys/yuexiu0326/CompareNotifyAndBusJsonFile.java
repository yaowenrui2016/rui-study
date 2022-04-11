package indi.rui.study.unittest.loganalys.yuexiu0326;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2022-03-28
 */
@Slf4j
public class CompareNotifyAndBusJsonFile {

    private static final String OPERATION_DIR = "C:\\Users\\yaowr\\Desktop\\yuexiu_0326";

    /**
     * 统计指定目录下的日志文件，根据regex正则表达式匹配找出相应内容
     *
     * @param args
     */
    public static void main(String[] args) {
        doCompare();
    }


    private static void doCompare() {
        // 读取bus请求报文
        File busCtxJsonFile = new File(OPERATION_DIR, "notifybus_out.json");
        String busCtxJsonString = null;
        try {
            busCtxJsonString = FileUtils.readFileToString(busCtxJsonFile);
        } catch (IOException e) {
            log.error("读取文件失败！\"{}\"", busCtxJsonFile.getName(), e);
        }
        if (StringUtils.isBlank(busCtxJsonString)) {
            log.warn("未读取到bus请求报文数据！");
            return;
        }
        Map<String, List<JSONObject>> busCtxMap = JSONObject.parseObject(busCtxJsonString,
                new TypeReference<Map<String, List<JSONObject>>>() {
                });
        if (busCtxMap.isEmpty()) {
            log.warn("bus请求报文数据为空！");
            return;
        }
        // 读取notify消费报文
        File notifyCtxJsonFile = new File(OPERATION_DIR, "notify_out.json");
        String notifyCtxJsonString = null;
        try {
            notifyCtxJsonString = FileUtils.readFileToString(notifyCtxJsonFile);
        } catch (IOException e) {
            log.error("读取文件失败！\"{}\"", notifyCtxJsonFile.getName(), e);
        }
        if (StringUtils.isBlank(notifyCtxJsonString)) {
            log.warn("未读取到notify消费报文数据！");
            return;
        }
        Map<String, List<JSONObject>> notifyCtxMap = JSONObject.parseObject(notifyCtxJsonString,
                new TypeReference<Map<String, List<JSONObject>>>() {
                });
        if (notifyCtxMap.isEmpty()) {
            log.warn("notify消费报文数据为空！");
            return;
        }
        // 比对notify消费记录和bus请求报文
        List<String> matchedLines = new ArrayList<>();
        int amount = 0;
        int matchedAmount = 0;
        for (Map.Entry<String, List<JSONObject>> busEntry : busCtxMap.entrySet()) {
            String key = busEntry.getKey();
            List<JSONObject> notifyCtxList = notifyCtxMap.get(key);
            Iterator<JSONObject> iterator = busEntry.getValue().iterator();
            while (iterator.hasNext()) {
                JSONObject busCtx = iterator.next();
                if (notifyCtxList != null && !notifyCtxList.isEmpty()) {
                    for (JSONObject notifyCtx : notifyCtxList) {
                        if (isMatched(busCtx, notifyCtx)) {
                            matchedLines.add(busCtx.toString(SerializerFeature.PrettyFormat)
                                    + " <=> " + notifyCtx.toString(SerializerFeature.PrettyFormat)
                                    + "\n ----------------------------------------------------------- ");
                            iterator.remove();
                            matchedAmount++;
                            break;
                        }
                    }
                }
                amount++;
            }
        }
        // 将比对结果写入json文件
        File outDir = new File(OPERATION_DIR);
        if (!outDir.exists() || !outDir.isDirectory()) {
            log.error("目录不存在！\"{}\"", outDir.getName());
            return;
        }
        File remainJsonFile = new File(outDir, "result_remain_out.json");
        try {
            String jsonString = JSONObject.toJSONString(busCtxMap, SerializerFeature.PrettyFormat);
            FileUtils.write(remainJsonFile, jsonString, false);
        } catch (IOException e) {
            log.error("写入文件失败！\"{}\"", remainJsonFile.getName(), e);
        }
        File matchedJsonFile = new File(outDir, "result_matched_out.json");
        try {
            FileUtils.writeLines(matchedJsonFile, matchedLines, false);
        } catch (IOException e) {
            log.error("写入文件失败！\"{}\"", matchedJsonFile.getName(), e);
        }
        log.info("比对bus请求报文与notify消费记录，剩余 {} 次，匹配 {} 次，分别写入文件‘{}’和‘{}’！",
                amount - matchedAmount,
                matchedAmount,
                remainJsonFile.getName(),
                matchedJsonFile.getName()
        );
    }

    /**
     * 比对是否匹配
     *
     * @param busCtx
     * @param notifyCtx
     * @return
     */
    private static boolean isMatched(JSONObject busCtx, JSONObject notifyCtx) {
        if (busCtx.get("cmd") != null && notifyCtx.get("cmd") != null &&
                !busCtx.get("cmd").equals(notifyCtx.get("cmd"))) {
            return false;
        }
        if (busCtx.get("appName") != null && notifyCtx.get("appName") != null &&
                !busCtx.get("appName").equals(notifyCtx.get("appName"))) {
            return false;
        }
        if (busCtx.get("modelName") != null && notifyCtx.get("entityName") != null &&
                !busCtx.get("modelName").equals(notifyCtx.get("entityName"))) {
            return false;
        }
        if (busCtx.get("modelId") != null && notifyCtx.get("entityId") != null &&
                !busCtx.get("modelId").equals(notifyCtx.get("entityId"))) {
            return false;
        }
        if (busCtx.get("key") != null && notifyCtx.get("entityKey") != null &&
                !busCtx.get("key").equals(notifyCtx.get("entityKey"))) {
            return false;
        }
        if (busCtx.get("subject") != null && notifyCtx.get("subject") != null &&
                !busCtx.get("subject").equals(notifyCtx.get("subject"))) {
            return false;
        }
        if (busCtx.get("link") != null && notifyCtx.get("link") != null &&
                !busCtx.get("link").equals(notifyCtx.get("link"))) {
            return false;
        }
        if (busCtx.get("todoType") != null && notifyCtx.get("type") != null &&
                !busCtx.get("todoType").equals(notifyCtx.get("type"))) {
            return false;
        }
        if (busCtx.get("param1") != null && notifyCtx.get("parameter1") != null &&
                !busCtx.get("param1").equals(notifyCtx.get("parameter1"))) {
            return false;
        }
        if (busCtx.get("param2") != null && notifyCtx.get("parameter2") != null &&
                !busCtx.get("param2").equals(notifyCtx.get("parameter2"))) {
            return false;
        }
        if (busCtx.get("level") != null && notifyCtx.get("todoLevel") != null &&
                !busCtx.get("level").equals(notifyCtx.get("todoLevel"))) {
            return false;
        }
        return true;
    }
}
