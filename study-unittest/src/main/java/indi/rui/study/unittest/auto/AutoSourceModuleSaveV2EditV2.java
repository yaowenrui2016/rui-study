package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.*;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 碧桂园待办完整性测试程序
 *
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class AutoSourceModuleSaveV2EditV2 {

    private static final String ADDRESS = "http://localhost:8040";
    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            ADDRESS, "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            ADDRESS, "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static final String ADDRESS = "http://mksmoke.ywork.me";
//    private static MkDataRequestHelper mkDataRequestHelper =
//            new MkDataRequestHelper(ADDRESS, "yuxd", "1");
//    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";


    private static final int MIN_CODE_NO = 0;
    private static final int MAX_CODE_NO = 20;

    private static final String APP_NAME_PREFIX = "AutoTest-MkNotifyApp-";
    private static final String APP_CODE_PREFIX = "C";

    private static final String MODULE_NAME_PREFIX = "AutoTest-MkNotifyModule-";
    private static final String MODULE_CODE_PREFIX = "M";

    static {
        // 清理历史数据
        int delFromCodeNo = MIN_CODE_NO, delToCodeNo = MAX_CODE_NO;
        cleanHistory(delFromCodeNo, delToCodeNo);
    }


    public static void main(String[] args) {
        // 用例1.新增系统
        int addAppNo1FromCodeNo = 0, addAppNo1ToCodeNo = 9;
        List<SimpleDTO> addAppsNo1 = addApp(addAppNo1FromCodeNo, addAppNo1ToCodeNo);

        // 用例2.新增模块，并关联系统
        String moduleName = MODULE_NAME_PREFIX + 0;
        String moduleCode = MODULE_CODE_PREFIX + 0;
        List<String> appIdsNo1 = addAppsNo1.stream().map(SimpleDTO::getId).collect(Collectors.toList());
        addModuleRPC(moduleName, moduleCode, appIdsNo1);
        checkModule(moduleCode, moduleName, Boolean.TRUE, appIdsNo1);

        // 用例3.编辑系统
        updateApp(addAppNo1FromCodeNo);

        // 用例4.禁用模块
        disable(moduleCode);
        checkModule(moduleCode, moduleName, Boolean.FALSE, appIdsNo1);

        // 用例5.启用模块
        enable(moduleCode);
        checkModule(moduleCode, moduleName, Boolean.TRUE, appIdsNo1);

        // 用例6.修改模块名称
        String newModuleName = MODULE_NAME_PREFIX + "XX" + 0;
        modifyModuleNameRPC(moduleCode, newModuleName);
        checkModule(moduleCode, newModuleName, Boolean.TRUE, appIdsNo1);

        // 用例7.增加关联系统
        int addAppNo2FromCodeNo = 10, addAppNo2ToCodeNo = 19;
        List<SimpleDTO> addAppsNo2 = addApp(addAppNo2FromCodeNo, addAppNo2ToCodeNo);
        addAppsNo2.addAll(addAppsNo1);
        List<String> appIdsNo2 = addAppsNo2.stream().map(SimpleDTO::getId).collect(Collectors.toList());
        modifyModuleAssociatedAppRPC(moduleCode, appIdsNo2);
        checkModule(moduleCode, newModuleName, Boolean.TRUE, appIdsNo2);

        // 用例8.移除关联系统
        modifyModuleAssociatedAppRPC(moduleCode, appIdsNo1);
        checkModule(moduleCode, newModuleName, Boolean.TRUE, appIdsNo1);

        // 用例9.新增带有domain的模块
        String moduleNameWithDomain = MODULE_NAME_PREFIX + "Domain" + 0;
        String moduleCodeWithDomain = MODULE_CODE_PREFIX + "Domain" + 0;
        String domain = "oa.landray.com";
        addModuleRPC(moduleNameWithDomain, moduleCodeWithDomain, domain, appIdsNo1);
        checkModuleWithDomain(moduleCodeWithDomain, domain);

        // 用例10.新增重复domain的模块
        try {
            moduleNameWithDomain = MODULE_NAME_PREFIX + "Domain" + 1;
            moduleCodeWithDomain = MODULE_CODE_PREFIX + "Domain" + 1;
            addModuleRPC(moduleNameWithDomain, moduleCodeWithDomain, domain, appIdsNo1);
        } catch (Exception e) {

        }

        // 用例11.根据domain查询
        findByDomainRPC(domain);

        log.info("All passed!");
    }

    // ================== business method ================= //

    private static void cleanHistory(int fromCodeNo, int toCodeNo) {
        List<String> historyCodes = new ArrayList<>();
        for (int codeNo = fromCodeNo; codeNo <= toCodeNo; codeNo++) {
            historyCodes.add(APP_CODE_PREFIX + codeNo);
        }
        deleteAppRPC(historyCodes);
    }

    private static List<SimpleDTO> addApp(int fromCodeNo, int toCodeNo) {
        // 新增系统
        List<String> appCodes = new ArrayList<>();
        for (int codeNo = fromCodeNo; codeNo <= toCodeNo; codeNo++) {
            String name = APP_NAME_PREFIX + codeNo;
            String code = APP_CODE_PREFIX + codeNo;
            addAppRPC(name, code);
            appCodes.add(code);
        }
        List<MkNotifySourceAppVO> appVOs = getAppByCodeRPC(appCodes);
        if (appVOs == null || appVOs.isEmpty()) {
            throw new RuntimeException("Query app not found");
        }
        // 查询系统获取id、name和code
        return appVOs.stream()
                .map((appVO) -> SimpleDTO.of(appVO.getFdId(), appVO.getFdName(), appVO.getFdCode()))
                .collect(Collectors.toList());
    }

    private static void updateApp(int codeNo) {
        String code = APP_CODE_PREFIX + codeNo;
        MkNotifySourceAppVO appVO = findAppByCode(code);
        String newName = appVO.getFdName() + "_updated";
        appVO.setFdName(newName);
        updateAppByCodeRPC(appVO);
        MkNotifySourceAppVO appVOAfter = findAppByCode(code);
        if (!appVOAfter.getFdName().equals(newName)) {
            throw new RuntimeException("Update app usecase failure![expect="
                    + newName + ", real="
                    + appVOAfter.getFdName());
        }
    }

    private static MkNotifySourceAppVO findAppByCode(String code) {
        List<MkNotifySourceAppVO> appVOs = getAppByCodeRPC(Collections.singletonList(code));
        if (appVOs == null || appVOs.isEmpty()) {
            throw new RuntimeException("Query app not found");
        }
        return appVOs.get(0);
    }

    private static void checkModule(String moduleCode,
                                    String expectModuleName,
                                    Boolean expectEnable,
                                    List<String> expectApps) {
        // 获取模块
        MkNotifySourceModuleVO moduleVO = getModuleByCodeRPC(moduleCode);
        // 备份
        List<String> expectAppsBak = new ArrayList<>(expectApps);
        List<String> realAppsBak = moduleVO.getFdSourceApp().stream()
                .map(IdNameProperty::getFdId)
                .collect(Collectors.toList());
        // 检查关联系统
        Iterator<String> expectIte = expectAppsBak.iterator();
        while (expectIte.hasNext()) {
            String expect = expectIte.next();
            Iterator<String> realIte = realAppsBak.iterator();
            while (realIte.hasNext()) {
                String real = realIte.next();
                if (expect.equals(real)) {
                    realIte.remove();
                    expectIte.remove();
                }
            }
        }
        if (!expectAppsBak.isEmpty() || !realAppsBak.isEmpty()) {
            throw new RuntimeException("Check module associated app error! [expect="
                    + Arrays.toString(expectApps.toArray())
                    + ", real=" + Arrays.toString(moduleVO.getFdSourceApp().stream()
                    .map(IdNameProperty::getFdId).toArray())
                    + "]");
        }
        // 检查启用状态
        if (expectEnable != moduleVO.getFdEnabled()) {
            throw new RuntimeException("Check module enable error! [expect=" + expectEnable
                    + ", real=" + moduleVO.getFdEnabled()
                    + "]");
        }
        // 检查模块名称
        if (!expectModuleName.equals(moduleVO.getFdName())) {
            throw new RuntimeException("Check module name error! [expect=" + expectModuleName
                    + ", real=" + moduleVO.getFdName()
                    + "]");
        }
    }

    private static void checkModuleWithDomain(String moduleCode, String domain) {
        // 获取模块
        MkNotifySourceModuleVO moduleVO = getModuleByCodeRPC(moduleCode);
        // 检查domain
        if (!domain.equals(moduleVO.getFdDomain())) {
            throw new RuntimeException("Check module domain error! [expect=" + domain
                    + ", real=" + moduleVO.getFdDomain()
                    + "]");
        }
    }

    private static void disable(String moduleCode) {
        enableOrDisableRPC("disable", moduleCode);
    }

    private static void enable(String moduleCode) {
        enableOrDisableRPC("enable", moduleCode);
    }


    // ==================== RPC method ===================== //

    private static void deleteAppRPC(List<String> codes) {
        // 请求体
        JSONArray body = new JSONArray();
        body.addAll(codes);
        mkApiRequestHelper.callApi("/api/sys-notify/sysNotifySourceApp/deleteByCode", body);
    }

    private static void addAppRPC(String name, String code) {
        JSONObject json = new JSONObject();
        json.put("fdName", name);
        json.put("fdCode", code);
        MkResponse<Void> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifySourceApp/add", json, Void.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Add app failure! [name=" + name
                    + ", code=" + code +
                    "], errMsg: " + mkResponse.getMsg());
        }
    }

    private static List<MkNotifySourceAppVO> getAppByCodeRPC(List<String> appCodes) {
        String url = "/data/sys-notify/sysNotifySourceApp/list";
        JSONObject json = new JSONObject();
        json.put("columns", Arrays.asList("fdId", "fdName", "fdCode"));
        json.put("pageSize", 1000);
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent("conditions", (k) -> new HashMap<>());
        conditions.put("fdCode", appCodes);
        MkResponse<QueryResult<MkNotifySourceAppVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                url, json, MkNotifySourceAppVO.class);
        List<MkNotifySourceAppVO> appVOs = null;
        if (mkResponse.isSuccess()) {
            appVOs = mkResponse.getData().getContent();
        }
        return appVOs;
    }

    private static void updateAppByCodeRPC(MkNotifySourceAppVO appVO) {
        String url = "/data/sys-notify/sysNotifySourceApp/update";
        JSONObject json = (JSONObject) JSONObject.toJSON(appVO);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.CallDataForJson(url, json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Update app failure! [code=" + appVO.getFdCode()
                    + ", errMsg=" + mkResponse.getMsg()
                    + "]");
        }
    }

    private static void addModuleRPC(String name, String code, List<String> appIds) {
        addModuleRPC(name, code, null, appIds);
    }

    private static void addModuleRPC(String name, String code, String domain, List<String> appIds) {
        String url = "/data/sys-notify/sysNotifySourceModule/addV2";
        JSONObject json = new JSONObject();
        json.put("fdName", name);
        json.put("fdCode", code);
        json.put("fdDomain", domain);
        json.put("fdSourceApps", appIds);
        MkResponse<Void> mkResponse = mkDataRequestHelper.callData(url, json, Void.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Add module failure! [name=" + name
                    + ", code=" + code
                    + ", appIds=" + Arrays.toString(appIds.toArray())
                    + ", errMsg=" + mkResponse.getMsg()
                    + "]");
        }
    }

    private static void enableOrDisableRPC(String method, String moduleCode) {
        // 获取模块ID
        MkNotifySourceModuleVO moduleVO = getModuleByCodeRPC(moduleCode);
        // 请求地址
        String url = "/data/sys-notify/sysNotifySourceModule/" + method;
        JSONObject json = new JSONObject();
        json.put("fdId", moduleVO.getFdId());
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                url, json, Void.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Execution [" + method + "] module failure! [code=" + moduleCode
                    + ", errMsg=" + mkResponse.getMsg()
                    + "]");
        }
    }

    private static void modifyModuleNameRPC(String moduleCode, String newModuleName) {
        MkNotifySourceModuleVO moduleVO = getModuleByCodeRPC(moduleCode);
        // 请求地址
        String url = "/data/sys-notify/sysNotifySourceModule/updateV2";
        JSONObject json = new JSONObject();
        json.put("fdId", moduleVO.getFdId());
        // 更新模块修改名称
        json.put("fdName", newModuleName);
        json.put("fdSourceApps", moduleVO.getFdSourceApp().stream().map(IdNameProperty::getFdId).collect(Collectors.toList()));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(url, json, Void.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Update module failure! [newModuleName=" + newModuleName
                    + ", errMsg=" + mkResponse.getMsg()
                    + "]");
        }
    }

    private static void modifyModuleAssociatedAppRPC(String moduleCode, List<String> appIds) {
        MkNotifySourceModuleVO moduleVO = getModuleByCodeRPC(moduleCode);
        // 请求地址
        String url = "/data/sys-notify/sysNotifySourceModule/updateV2";
        JSONObject json = new JSONObject();
        json.put("fdId", moduleVO.getFdId());
        json.put("fdName", moduleVO.getFdName());
        // 更新模块关联系统
        json.put("fdSourceApps", appIds);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(url, json, Void.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Update module associated app! [appIds=" + Arrays.toString(appIds.toArray())
                    + ", errMsg=" + mkResponse.getMsg()
                    + "]");
        }
    }

    private static MkNotifySourceModuleVO getModuleByCodeRPC(String moduleCode) {
        String url = "/data/sys-notify/sysNotifySourceModule/list";
        JSONObject json = new JSONObject();
        json.put("columns", Arrays.asList("fdId", "fdName", "fdCode", "fdDomain", "fdEnabled", "fdSourceId", "fdSourceApp"));
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent("conditions", (k) -> new HashMap<>());
        conditions.put("fdCode", moduleCode);
        json.put("pageSize", 1000);
        MkResponse<QueryResult<MkNotifySourceModuleVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                url, json, MkNotifySourceModuleVO.class);
        MkNotifySourceModuleVO rtnModule = null;
        if (mkResponse.isSuccess()) {
            List<MkNotifySourceModuleVO> data = mkResponse.getData().getContent();
            if (data != null && !data.isEmpty()) {
                rtnModule = data.get(0);
            }
        }
        if (rtnModule == null) {
            throw new RuntimeException("Module with code '" + moduleCode + "' not found! errMsg: " + mkResponse.getMsg());
        }
        return rtnModule;
    }

    private static void findByDomainRPC(String domain) {
        String url = "/api/sys-notify/sysNotifySourceModule/findByDomain?domain=" + domain;
        String moduleVO = mkApiRequestHelper.callApi(url, null);
        log.info("Find by domain: {}", JSONObject.toJSONString(moduleVO, SerializerFeature.PrettyFormat));
    }
}
