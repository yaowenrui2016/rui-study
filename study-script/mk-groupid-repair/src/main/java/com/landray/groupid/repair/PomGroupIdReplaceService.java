package com.landray.groupid.repair;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landray.groupid.repair.model.ModuleInfo;
import com.landray.groupid.repair.model.PomInfo;
import com.landray.groupid.repair.util.XMLUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.landray.groupid.repair.util.ModuleUtil.loadModules;

/**
 * @author: yaowr
 * @create: 2022-07-18
 */
@Slf4j
public class PomGroupIdReplaceService {

    private static String workspace = "D:\\landray\\project\\mkpaas-0713";

    private static final String SOURCE = "${mk.group-id}";
    private static final String TARGET = "${landray.mk.group-id}";

    public static void main(String[] args) {
        List<PomInfo> pomInfos = findPomXml();
        Map<String, List<String>> projectMap = new LinkedHashMap<>();
        int totalReplaced = 0;
        int pomNum = 0;
        for (PomInfo pomInfo : pomInfos) {
            replace(pomInfo);
            int replaced = pomInfo.getReplaced();
            if (replaced > 0) {
                projectMap.computeIfAbsent(pomInfo.getModule(), k -> new ArrayList<>()).add(pomInfo.getProject() + " (" + pomInfo.getReplaced() + ")");
                pomNum++;
                totalReplaced += pomInfo.getReplaced();
            }
        }
        log.info("Finished: {} (总共{}个模块，{}个pom.xml，{}处替换)",
                JSONObject.toJSONString(projectMap, SerializerFeature.PrettyFormat),
                projectMap.size(),
                pomNum,
                totalReplaced);
    }


    private static void replace(PomInfo pomInfo) {
        File pomFile = pomInfo.getPomFile();
        FileInputStream in = null;
        FileOutputStream out = null;
        XMLWriter writer = null;
        int replaced = 0;
        try {
            in = new FileInputStream(pomFile);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            Element dependenciesEle = root.element("dependencies");
            if (dependenciesEle != null) {
                List<Element> dependencyEleList = dependenciesEle.elements();
                for (Element dependencyEle : dependencyEleList) {
                    Element groupIdEle = dependencyEle.element("groupId");
                    if (groupIdEle.getText().equals(SOURCE)) {
                        groupIdEle.setText(TARGET);
                        replaced++;
                    }
                }
            }
            pomInfo.setReplaced(replaced);
            if (replaced > 0) {
                out = new FileOutputStream(pomFile);
                writer = new XMLWriter(out);
                writer.write(doc);
            }
        } catch (Exception e) {
            log.error("Read xml error", e);
        } finally {
            XMLUtil.close(in, out);
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("Close failed!", e);
                }
            }
        }
    }

    private static List<PomInfo> findPomXml() {
        List<PomInfo> pomInfos = new ArrayList<>();
        for (ModuleInfo moduleInfo : loadModules()) {
            String module = moduleInfo.getModuleDir();
            File moduleDir = new File(workspace + File.separator + module);
            if (moduleDir.exists()) {
                File[] projectDirs = moduleDir.listFiles();
                if (projectDirs != null) {
                    for (int i = 0; i < projectDirs.length; i++) {
                        File projectDir = projectDirs[i];
                        if (projectDir.isDirectory() && projectDir.getName().startsWith("mk")) {
                            File[] pomFiles = projectDir.listFiles(
                                    pathname -> pathname != null && pathname.getName().equals("pom.xml"));
                            if (pomFiles != null && pomFiles.length > 0) {
                                File pomFile = pomFiles[0];
                                PomInfo pomInfo = new PomInfo();
                                pomInfo.setModule(module);
                                pomInfo.setProject(projectDir.getName());
                                pomInfo.setPomFile(pomFile);
                                pomInfos.add(pomInfo);
                            }
                        }
                    }
                }
            }
        }
        return pomInfos;
    }
}
