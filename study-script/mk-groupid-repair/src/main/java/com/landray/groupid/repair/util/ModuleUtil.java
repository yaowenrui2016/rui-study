package com.landray.groupid.repair.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landray.groupid.repair.model.ModuleInfo;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2022-07-18
 */
@Slf4j
public class ModuleUtil {

    private static final String MODULE_PREFIX = "mk-";

    public static List<ModuleInfo> loadModules() {
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                "config/modules")).getFile();
        String content = FileUtils.readFileToString(filePath, "utf-8");
        String[] arr = content.split("\n");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
        }
        List<String> moduleDirs = Arrays.asList(arr);
        List<ModuleInfo> moduleInfos = new ArrayList<>();
        for (String moduleDir : moduleDirs) {
            ModuleInfo moduleInfo = new ModuleInfo();
            moduleInfo.setModuleDir(moduleDir);
            moduleInfo.setModuleName(moduleDir.substring(MODULE_PREFIX.length()));
            moduleInfos.add(moduleInfo);
        }
        log.info("Load modules: {}", JSONObject.toJSONString(moduleDirs, SerializerFeature.PrettyFormat));
        return moduleInfos;
    }
}
