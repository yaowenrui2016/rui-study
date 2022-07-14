package com.landray.groupid.repair;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-01-27
 */
@Slf4j
public class Main {

    private static String filepath = "D:\\project\\rui-study\\study-script\\mk-groupid-repair\\pom.xml";

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream(filepath);
        SAXReader sr = new SAXReader();
        Document doc = sr.read(in);
        Element root = doc.getRootElement();
        Element dependenciesEle = root.element("dependencies");
        List<Element> dependencyEleList = dependenciesEle.elements();
        for (Element dependencyEle : dependencyEleList) {
            Element groupIdEle = dependencyEle.element("groupId");
            log.info("groupId: {}", groupIdEle.getText());
            groupIdEle.setText();
        }
    }
}
