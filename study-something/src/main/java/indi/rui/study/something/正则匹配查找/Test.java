package indi.rui.study.something.正则匹配查找;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: yaowr
 * @create: 2022-03-07
 */
public class Test {

    public static final String TEMP = "我是消息的内容模板，该合同是本季度${extendContents.level}的整改案例，现在由${extendContents.region}业务团队以及${extendContents.applicator}负责，请各位领导以及专家组知悉，详情请看${extendContents.contractLink}";

    public static void main(String[] args) {
        List<String> vars = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\$\\{(.+?)\\}").matcher(TEMP);
        while (matcher.find()) {
            String group = matcher.group(1);
            vars.add(group);
        }
    }
}
