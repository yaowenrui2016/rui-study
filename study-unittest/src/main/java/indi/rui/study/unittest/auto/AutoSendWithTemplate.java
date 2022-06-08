package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.dto.original.SysNotifyOriginalVO;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

import java.util.*;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoSendWithTemplate {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static RedissonClient redissonClient = RedisUtils.getRedis(
            "redis://study.rui.ubuntu:6379", 3);

    private static final int MAX_TIMEOUT_MS = 10000;

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        // 发送消息并获取原始消息日志
        getOriginal(sendTodoRPC());

        // 置已办消息并获取原始消息日志
//        getOriginal(doneTodoRPC());
        redissonClient.shutdown();
    }

    private static String sendTodoRPC() {
        JSONObject json = FileUtils.loadJSON("AutoSendWithTemplate/send.json");
        json.put("notifyType", "email");
        json.put("entityKey", redissonClient.getAtomicLong("mkpaas:notify:entityKey").getAndIncrement());
        json.put("todoLevel", 1 + RANDOM.nextInt(3));
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send todo error! errMsg=" + mkResponse.getMsg());
        }
        // 返回snid
        return mkResponse.getData();
    }

    private static String doneTodoRPC() {
        JSONObject json = FileUtils.loadJSON("AutoSendWithTemplate/done.json");
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/done",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Done todo error! errMsg=" + mkResponse.getMsg());
        }
        // 返回snid
        return mkResponse.getData();
    }

    private static void getOriginal(String snid) {
        long beginTime = System.currentTimeMillis();
        boolean timeout;
        int count = 0;
        SysNotifyOriginalVO original;
        do {
            count++;
            original = findOriginalIdRPC(snid);
            if (original != null) {
                original = loadOriginal(original.getFdId());
            }
            timeout = (System.currentTimeMillis() - beginTime) > MAX_TIMEOUT_MS;
        } while ((original == null || !isAllArrived(original)) && !timeout);
        if (original == null) {
            throw new RuntimeException("Get original with snid '" + snid + "' found nothing!");
        }
        log.info("Get original [{}]: {}", count, JSONObject.toJSONString(original, SerializerFeature.PrettyFormat));
    }

    private static boolean isAllArrived(SysNotifyOriginalVO original) {
        if (original == null) {
            return false;
        }
        List<String> providerIds = (List<String>) JSONObject.parseObject(original.getFdContent()).get("providerIds");
        return providerIds.size() == original.getFdNotifyType().split(",").length;
    }

    private static SysNotifyOriginalVO findOriginalIdRPC(String snid) {
        // 查询原始消息记录
        JSONObject json = new JSONObject();
        json.put("pageSize", 1);
        json.put("columns", Collections.singletonList("fdId"));
        Map<String, Object> sorts = (Map<String, Object>) json.computeIfAbsent("sorts", (k) -> new HashMap<>());
        sorts.put("fdCreateTime", "DESC");
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent("conditions", (k) -> new HashMap<>());
        conditions.put("fdSnid", snid);
        MkResponse<QueryResult<SysNotifyOriginalVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifyOriginal/list", json, SysNotifyOriginalVO.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("find original error! errMsg=" + mkResponse.getMsg());
        }
        List<SysNotifyOriginalVO> originals = mkResponse.getData().getContent();
        if (originals == null || originals.isEmpty()) {
            return null;
        }
        return originals.get(0);
    }

    private static SysNotifyOriginalVO loadOriginal(String fdId) {
        // 查询原始消息记录
        JSONObject json = new JSONObject();
        json.put("fdId", fdId);
        MkResponse<SysNotifyOriginalVO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyOriginal/get", json, SysNotifyOriginalVO.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Load original error! errMsg=" + mkResponse.getMsg());
        }
        return mkResponse.getData();
    }
}
