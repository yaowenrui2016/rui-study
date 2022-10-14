package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Slf4j
public class AutoTime {

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
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mktest.ywork.me", "jj01", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mktest.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmokemini.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmokemini.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        // 新建节假日
//        createHoliday();

        // 获取暂时上班数据
        temporaryCalculator();
    }

    /**
     * 获取上班日期
     */
    private static void createHoliday() {
        JSONObject body = FileUtils.loadJSON("AutoTime/save.json");
        log.info("create holiday: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/sysTimeHoliday/saveOrUpdate",
                body);
        log.info("create holiday: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 获取上班日期
     */
    private static void temporaryCalculator() {
        JSONObject dto = new JSONObject();
        try {
            dto.put("begin", new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-29"));
            dto.put("end", new SimpleDateFormat("yyyy-MM-dd").parse("2022-09-04"));
        } catch (ParseException e) {
            throw new RuntimeException("时间转换异常");
        }
        log.info("temporary calculator: request={}", dto.toString(SerializerFeature.PrettyFormat));
        List<Date> dateList = mkApiRequestHelper.callApiForList(
                "/api/sys-time/work/temporaryCalculator",
                dto,
                Date.class);
        List<String> printList = new ArrayList<>();
        for (Date date : dateList) {
            printList.add(new SimpleDateFormat("yyyy-MM-dd").format(date));
        }
        log.info("temporary calculator: response={}", JSONObject.toJSONString(printList, SerializerFeature.PrettyFormat));
    }
}
