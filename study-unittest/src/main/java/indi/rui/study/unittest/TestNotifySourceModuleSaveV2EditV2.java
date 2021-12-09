package indi.rui.study.unittest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.*;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.HttpClientUtils;
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
public class TestNotifySourceModuleSaveV2EditV2 {

//    private static final String ADDRESS = "https://bipnew-sit.countrygarden.com.cn";
//    private static final String X_SERVICE_NAME = "43534c48566d654e5031674d355238395259346736673d3d";

//    private static final String ADDRESS = "http://192.168.51.202:8050";
//    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String ADDRESS = "http://localhost:8040";
    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

//    private static final String ADDRESS = "https://p.landray.com.cn";
//    private static final String X_SERVICE_NAME = "7455654271706f49474936332f6857624757456a467a726c316838566b2f386f583350595477392b4c78593d";

    private static final int MIN_CODE_NO = 0;
    private static final int MAX_CODE_NO = 20;

    private static final String APP_NAME_PREFIX = "AutoTest-MkNotifyApp-";
    private static final String APP_CODE_PREFIX = "C";

    private static final String MODULE_NAME_PREFIX = "AutoTest-MkNotifyModule-";
    private static final String MODULE_CODE_PREFIX = "M";

    private static MkDataRequestHelper mkDataRequestHelper =
            new MkDataRequestHelper(ADDRESS, "yaowr", "0");


    public static void main(String[] args) {
        // 1.清理历史数据
        cleanHistory(MIN_CODE_NO, MAX_CODE_NO);

        // 3.新增系统
        int fromCode = 0, toCode = 10;
        List<SimpleDTO> addAppsNo1 = addApp(fromCode, toCode);

        // 5.新增模块
        String moduleName = MODULE_NAME_PREFIX + 0;
        String moduleCode = MODULE_CODE_PREFIX + 0;
        List<String> appIdsNo1 = addAppsNo1.stream().map(SimpleDTO::getId).collect(Collectors.toList());
        addModuleRPC(moduleName, moduleCode, appIdsNo1);

        // 6.检查模块正确与否
        checkModule(moduleCode, moduleName, Boolean.TRUE, appIdsNo1);

        // 7.禁用模块
        disable(moduleCode);

        // 8.检查模块正确与否
        checkModule(moduleCode, moduleName, Boolean.FALSE, appIdsNo1);

        // 9.启用模块
        enable(moduleCode);

        // 10.检查模块正确与否
        checkModule(moduleCode, moduleName, Boolean.TRUE, appIdsNo1);

        // 11.修改模块名称
        String newModuleName = "Module_0";
        modifyModuleNameRPC(moduleCode, newModuleName);

        // 12.检查模块正确与否
        checkModule(moduleCode, newModuleName, Boolean.TRUE, appIdsNo1);

        // 13.新增系统关联

    }
    // ================== business method ================= //

    private static void cleanHistory(int minCode, int MaxCode) {
        List<String> historyCodes = new ArrayList<>();
        for (int i = minCode; i <= MaxCode; i++) {
            historyCodes.add(i + "");
        }
        deleteApp(historyCodes);
    }

    private static List<SimpleDTO> addApp(int fromCode, int toCode) {
        // 新增系统
        List<String> appCodes = new ArrayList<>();
        for (int codeNo = fromCode; codeNo <= toCode; codeNo++) {
            String name = APP_NAME_PREFIX + codeNo;
            String code = APP_CODE_PREFIX + codeNo;
            addApp(name, code);
            appCodes.add(code);
        }
        List<MkNotifySourceAppVO> appVOs = getAppByCode(appCodes);
        if (appVOs == null || appVOs.isEmpty()) {
            throw new RuntimeException("Query app not found");
        }
        // 查询系统获取id、name和code
        return appVOs.stream()
                .map((appVO) -> SimpleDTO.of(appVO.getFdId(), appVO.getFdName(), appVO.getFdCode()))
                .collect(Collectors.toList());
    }

    private static void checkModule(String moduleCode,
                                    String expectModuleName,
                                    Boolean expectEnable,
                                    List<String> expectApps) {
        // 获取模块
        MkNotifySourceModuleVO moduleVO = getModuleByCodeRPC(moduleCode);
        // 检查关联系统
        List<IdNameProperty> realApps = moduleVO.getFdSourceApp();
        Iterator<String> expectIte = expectApps.iterator();
        while (expectIte.hasNext()) {
            String expect = expectIte.next();
            Iterator<IdNameProperty> realIte = realApps.iterator();
            while (realIte.hasNext()) {
                IdNameProperty real = realIte.next();
                if (real.getFdId().equals(expect)) {
                    realIte.remove();
                    expectIte.remove();
                }
            }
        }
        if (!expectApps.isEmpty() || !realApps.isEmpty()) {
            throw new RuntimeException("Check module associated app error! [diffExpect="
                    + Arrays.toString(expectApps.toArray())
                    + ", diffReal=" + Arrays.toString(realApps.toArray())
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

    private static void disable(String moduleCode) {
        enableOrDisableRPC("disable", moduleCode);
    }

    private static void enable(String moduleCode) {
        enableOrDisableRPC("enable", moduleCode);
    }


    // ==================== RPC method ===================== //

    private static void deleteApp(List<String> codes) {
        // 请求地址
        String url = ADDRESS + "/api/sys-notify/sysNotifySourceApp/deleteByCode";
        // 请求体
        JSONArray body = new JSONArray();
        body.addAll(codes);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            HttpClientUtils.httpPost(url, body, header);
        } catch (Exception e) {
            log.error("Delete app exception! ", e);
        }
    }

    private static void addApp(String name, String code) {
        String url = ADDRESS + "/data/sys-notify/sysNotifySourceApp/add";
        JSONObject json = new JSONObject();
        json.put("fdName", name);
        json.put("fdCode", code);
        MkResponse<Void> mkResponse = mkDataRequestHelper.callData(url, json, Void.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Add app failure! [name=" + name
                    + ", code=" + code +
                    "], errMsg: " + mkResponse.getMsg());
        }
    }

    private static List<MkNotifySourceAppVO> getAppByCode(List<String> appCodes) {
        String url = ADDRESS + "/data/sys-notify/sysNotifySourceApp/list";
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

    private static void addModuleRPC(String name, String code, List<String> appIds) {
        String url = ADDRESS + "/data/sys-notify/sysNotifySourceModule/addV2";
        JSONObject json = new JSONObject();
        json.put("fdName", name);
        json.put("fdCode", code);
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
        String url = ADDRESS + "/data/sys-notify/sysNotifySourceModule/" + method;
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
        // 获取模块修改名称
        MkNotifySourceModuleVO moduleVO = getModuleByCodeRPC(moduleCode);
        // 请求地址
        String url = ADDRESS + "/data/sys-notify/sysNotifySourceModule/updateV2";
        JSONObject json = new JSONObject();
        json.put("fdId", moduleVO.getFdId());
        json.put("fdName", newModuleName);
        json.put("fdSourceApps", moduleVO.getFdSourceApp().stream().map(IdNameProperty::getFdId).collect(Collectors.toList()));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(url, json, Void.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Update module failure! [newModuleName=" + newModuleName
                    + ", errMsg=" + mkResponse.getMsg()
                    + "]");
        }
    }

    private static MkNotifySourceModuleVO getModuleByCodeRPC(String moduleCode) {
        String url = ADDRESS + "/data/sys-notify/sysNotifySourceModule/list";
        JSONObject json = new JSONObject();
        json.put("columns", Arrays.asList("fdId", "fdName", "fdCode", "fdEnabled", "fdSourceId", "fdSourceApp"));
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
}
