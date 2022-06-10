package indi.rui.study.something.测试JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.util.FileUtils;

/**
 * @author: yaowr
 * @create: 2022-06-09
 */
public class Main {

    public static void main(String[] args) {
        ClassLoader classLoader = Main.class.getClassLoader();
        JSONObject jsonObject = FileUtils.loadJSON(classLoader, "test.json");
        recursive(jsonObject);
    }

    private static void test1() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("a");
        jsonArray.add("b");
        jsonArray.add("c");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
        }
    }

    private static void recursive(Object obj) {
        System.out.println(obj.getClass().getCanonicalName());
        if (obj instanceof String) {
            System.out.println(obj);
        } else {
            if (obj instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) obj).size(); i++) {
                    recursive(((JSONArray) obj).get(i));
                }
            } else if (obj instanceof JSONObject) {
                for (String key : ((JSONObject) obj).keySet()) {
                    recursive(((JSONObject) obj).get(key));
                }
            }
        }
    }
}
