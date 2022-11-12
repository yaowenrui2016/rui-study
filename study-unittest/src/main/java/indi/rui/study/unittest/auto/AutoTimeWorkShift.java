package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.IdNameProperty;
import indi.rui.study.unittest.dto.IdVO;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.dto.mksystime.WorkScheduleRtnVO;
import indi.rui.study.unittest.dto.org.SimplePerson;
import indi.rui.study.unittest.dto.workschedule.SysTimeFixedVO;
import indi.rui.study.unittest.dto.workschedule.SysTimeWorkClassesVO;
import indi.rui.study.unittest.dto.workschedule.SysTimeWorkOrgVO;
import indi.rui.study.unittest.dto.workschedule.SysTimeWorkScheduleVO;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Slf4j
public class AutoTimeWorkShift {

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
     * 初始化排班人员
     */
    private static List<SimplePerson> workShiftPersons;

    static {
        int count = 1;
        workShiftPersons = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            SimplePerson person = new SimplePerson();
            person.setFdLoginName("WorkShift" + i);
            person.setFdName("排班用户" + i);
            SimplePerson foundPerson = AutoCreatePersonAndAuthorized.createAndGetPerson(person);
            workShiftPersons.add(foundPerson);
        }
    }

    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final Integer FIXED = 1;
    public static final Integer MANUAL = 2;

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        // 创建班次
        createClasses();
        // 查询班次
        List<SysTimeWorkClassesVO> classesList = findClasses();
        // 获取班次
//        SysTimeWorkClassesVO classesVO = getClasses(IdVO.of(classesList.get(0).getFdId()));
        // 编辑班次
//        editClasses(classesVO);
        // 获取班次
//        getClasses(IdVO.of(classesList.get(0).getFdId()));
        // 删除班次
//        deleteClasses(classesList.stream().map(SysTimeWorkClassesVO::getFdId).collect(Collectors.toList()));


        // 创建排班
//        WorkScheduleRtnVO rtnVO = createFixedSchedule(classesList);
        // 查询排班
        List<SysTimeWorkScheduleVO> scheduleList = findSchedule();
        // 删除排班
        deleteSchedule(scheduleList);
    }

    /**
     * 创建固定排班
     */
    private static WorkScheduleRtnVO createFixedSchedule(List<SysTimeWorkClassesVO> classesList) {
        SysTimeWorkScheduleVO scheduleVO = new SysTimeWorkScheduleVO();
        scheduleVO.setFdName("测试固定排班");
        scheduleVO.setFdBegin(toDate("2022-08-01"));
        scheduleVO.setFdEnd(toDate("2022-10-01"));
        scheduleVO.setFdType(FIXED);
        scheduleVO.setFdFixed(buildFixedList(classesList));
        scheduleVO.setFdOrgList(buildOrgList());
        scheduleVO.setFdEditors(buildEditors());
        JSONObject body = (JSONObject) JSONObject.toJSON(scheduleVO);
        log.info("add fixed schedule: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<WorkScheduleRtnVO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/create", body, WorkScheduleRtnVO.class);
        log.info("add fixed schedule: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 查询排班
     */
    private static List<SysTimeWorkScheduleVO> findSchedule() {
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("fdType", 1);
        JSONObject body = new JSONObject();
        body.put("pageSize", 20);
        body.put("offset", 0);
        body.put("conditions", conditions);
        log.info("find schedule: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<QueryResult<SysTimeWorkScheduleVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-time/workSchedule/list", body, SysTimeWorkScheduleVO.class);
        log.info("find schedule: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData().getContent();
    }

    /**
     * 删除排班
     */
    private static void deleteSchedule(List<SysTimeWorkScheduleVO> scheduleList) {
        JSONObject idsDTO = new JSONObject();
        idsDTO.put("fdIds", scheduleList.stream().map(SysTimeWorkScheduleVO::getFdId).collect(Collectors.toList()));
        log.info("delete schedule: request={}", idsDTO.toString(SerializerFeature.PrettyFormat));
        MkResponse<QueryResult<SysTimeWorkScheduleVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-time/workSchedule/deleteAll",
                idsDTO,
                SysTimeWorkScheduleVO.class);
        log.info("delete schedule: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    // ===================== 班次 ===================== //

    /**
     * 创建班次
     */
    private static String createClasses() {
        JSONObject body = FileUtils.loadJSON("AutoTimeWorkShift/create_classes.json");
        body.put("fdEditors", workShiftPersons.stream().map(person -> {
            JSONObject rangeVO = new JSONObject();
            rangeVO.put("fdId", person.getFdId());
            rangeVO.put("fdName", person.getFdName());
            return rangeVO;
        }).collect(Collectors.toList()));
        log.info("create classes: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<String> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/create", body, String.class);
        log.info("create classes: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 查询班次
     */
    private static List<SysTimeWorkClassesVO> findClasses() {
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("fdEnabled", "true");
        JSONObject body = new JSONObject();
        body.put("pageSize", 20);
        body.put("offset", 0);
        body.put("conditions", conditions);
        log.info("find classes: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<QueryResult<SysTimeWorkClassesVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-time/workClasses/list", body, SysTimeWorkClassesVO.class);
        log.info("find classes: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData().getContent();
    }

    /**
     * 获取班次
     */
    private static SysTimeWorkClassesVO getClasses(IdVO idVO) {
        JSONObject body = (JSONObject) JSONObject.toJSON(idVO);
        log.info("get classes: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<SysTimeWorkClassesVO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/get", body, SysTimeWorkClassesVO.class);
        log.info("get classes: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 编辑班次
     *
     * @param classesVO
     */
    private static void editClasses(SysTimeWorkClassesVO classesVO) {
        classesVO.setFdName(classesVO.getFdName() + "_编辑");
        classesVO.setFdEnabled(!classesVO.getFdEnabled());
        JSONObject body = (JSONObject) JSONObject.toJSON(classesVO);
        log.info("edit classes: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/edit", body);
        log.info("edit classes: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 删除班次
     *
     * @param classesIds
     */
    private static void deleteClasses(List<String> classesIds) {
        JSONObject body = new JSONObject();
        body.put("fdIds", classesIds);
        log.info("delete classes: request={}", body.toString(SerializerFeature.PrettyFormat));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/deleteAll", body);
        log.info("delete classes: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    // ================= 其他方法 ================= //

    private static Date toDate(String text) {
        return Date.from(LocalDate.parse(text, DATE_FORMAT)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private static List<SysTimeFixedVO> buildFixedList(List<SysTimeWorkClassesVO> classesList) {
        List<SysTimeFixedVO> fixedList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            SysTimeFixedVO fixed = new SysTimeFixedVO();
            fixed.setFdDayOfWeek(i);
            fixed.setFdWorkClasses(IdNameProperty.of(getRandom(classesList).getFdId(), null));
            fixedList.add(fixed);
        }
        return fixedList;
    }

    private static List<SysTimeWorkOrgVO> buildOrgList() {
        return workShiftPersons.stream().map(person -> {
            SysTimeWorkOrgVO orgVO = new SysTimeWorkOrgVO();
            orgVO.setFdOrgId(person.getFdId());
            orgVO.setFdOrgName(person.getFdName());
            orgVO.setFdOrgType(person.getFdOrgType());
            orgVO.setFdHierarchyId(person.getFdHierarchyId());
            return orgVO;
        }).collect(Collectors.toList());
    }

    private static List<IdNameProperty> buildEditors() {
        return workShiftPersons.stream().map(person -> {
            IdNameProperty idNameProperty = new IdNameProperty();
            idNameProperty.setFdId(person.getFdId());
            return idNameProperty;
        }).collect(Collectors.toList());
    }

    private static <T> T getRandom(List<T> candidateList) {
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(candidateList.size());
        return candidateList.get(index);
    }
}
