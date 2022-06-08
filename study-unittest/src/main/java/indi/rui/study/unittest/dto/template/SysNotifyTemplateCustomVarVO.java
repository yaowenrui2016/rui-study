package indi.rui.study.unittest.dto.template;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: yaowr
 * @create: 2022-06-06
 */
@Getter
@Setter
public class SysNotifyTemplateCustomVarVO {

    private String fdId;

    private String fdName;

    private Date fdCreateTime;

    /**
     * 所属模板
     */
    private String fdTemplateId;

    /**
     * 字段名（前端叫‘编码’）
     */
    private String fdField;
}
