package indi.rui.study.something.nettyusage;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
public class HttpNettyUtils {

    public static String  headsToString(List<String> values) {
        StringBuffer buf = new StringBuffer();
        for (String value : values) {
            buf.append(value).append(",");
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }
}
