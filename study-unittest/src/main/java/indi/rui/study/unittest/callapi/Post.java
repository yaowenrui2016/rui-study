package indi.rui.study.unittest.callapi;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.support.MkApiRequestHelper;

/**
 * @author: yaowr
 * @create: 2022-06-13
 */
public class Post {

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "https://trsgroup.ldsz1.landray.net", "596f78446f4e77424c627831447a3579666b6b52356a73474e6b66352b70486c526557324c5047486879773d");

    public static void main(String[] args) {
        JSONObject body  = new JSONObject();
        mkApiRequestHelper.callApi("/api/sys-notifybus/third/sync", body);
    }
}
