package indi.rui.study.unittest.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class MkReferParseEntry {

    private JSONObject localData;

    /**
     * 若获取localData时，能直接得出tranData，建议同时返回tranData，否则tranData留空，需要的时候再调用接口获取
     */
    private JSONObject tranData;

    private String type;

    private String path;

    private String pathDesc;

    private String replaceStrategy;

    /**
     * 构造一个entry
     */
    public static MkReferParseEntry of(JSONObject localData, String type, String path, String pathDesc) {
        MkReferParseEntry entry = new MkReferParseEntry();
        entry.setPath(path);
        entry.setPathDesc(pathDesc);
        entry.setType(type);
        entry.setLocalData(localData);
        return entry;
    }
}
