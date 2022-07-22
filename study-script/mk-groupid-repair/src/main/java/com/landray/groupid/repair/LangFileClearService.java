package com.landray.groupid.repair;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landray.groupid.repair.model.LangFileInfo;
import com.landray.groupid.repair.model.ModuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.landray.groupid.repair.util.ModuleUtil.loadModules;

/**
 * @author: yaowr
 * @create: 2022-01-27
 */
@Slf4j
public class LangFileClearService {
    /**
     * 加载属性文件
     */
    private static Properties properties = new Properties();
    static {
        try {
            InputStream inputStream = PomGroupIdReplaceService.class.getClassLoader().getResourceAsStream("config/application.properties");
            properties.load(inputStream);
            log.info("load properties: {}", JSONObject.toJSONString(properties, SerializerFeature.PrettyFormat));
        } catch (IOException e) {
            throw new RuntimeException("Load 'config/application.properties' error!");
        }
    }

    private static String workspace = properties.getProperty("project.workspace");
    private static String resourcePattern = properties.getProperty("clear.lang.resource");


    public static void main(String[] args) {
        List<LangFileInfo> langFileInfos = findLangFileInfo();
        Map<String, List<String>> langFileMap = new LinkedHashMap<>();
        int totalFiles = 0;
        for (LangFileInfo langFileInfo : langFileInfos) {
            List<File> langFiles = Arrays.asList(langFileInfo.getLangFiles());
            langFileMap.put(langFileInfo.getProject(), langFiles.stream().map(File::getName).collect(Collectors.toList()));
            for (File langFile : langFiles) {
                langFile.delete();
            }
            totalFiles += langFiles.size();
        }
        log.info("lang file delete:{} (Totally delete {} modules with {} resource '.properties' files)",
                JSONObject.toJSONString(langFileMap, SerializerFeature.PrettyFormat),
                langFileInfos.size(),
                totalFiles
        );
    }


    private static List<LangFileInfo> findLangFileInfo() {
        List<LangFileInfo> langFileInfos = new ArrayList<>();
        for (ModuleInfo moduleInfo : loadModules()) {
            String module = moduleInfo.getModuleDir();
            File moduleDir = new File(workspace + File.separator + module);
            if (moduleDir.exists()) {
                File[] projectDirs = moduleDir.listFiles();
                if (projectDirs != null) {
                    for (int i = 0; i < projectDirs.length; i++) {
                        File projectDir = projectDirs[i];
                        String project;
                        if (projectDir.isDirectory() && (project = projectDir.getName()).startsWith("mk")
                                && project.endsWith("-core")) {
                            File resourceDir = new File(projectDir, String.format(resourcePattern, moduleInfo.getModuleName().replace("-", File.separator)));
                            File[] langFiles = resourceDir.listFiles(
                                    pathname -> !pathname.getName().equals("ApplicationResources.properties")
                                            && pathname.getName().matches("^ApplicationResources_.+\\.properties$"));
                            if (langFiles != null && langFiles.length > 0) {
                                LangFileInfo langFileInfo = new LangFileInfo();
                                langFileInfo.setModule(module);
                                langFileInfo.setProject(project);
                                langFileInfo.setLangFiles(langFiles);
                                langFileInfos.add(langFileInfo);
                            }
                        }
                    }
                }
            }
        }
        return langFileInfos;
    }
}
