package indi.rui.study.unittest.dto.template;

import indi.rui.study.unittest.constant.DispStyle;
import indi.rui.study.unittest.constant.PropType;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: yaowr
 * @create: 2022-02-25
 */
@Getter
@Setter
public class SysNotifyTemplateUnitVO {

    private String fdId;

    /**
     * 所属模板主表
     */
    private IdNameProperty fdMain;

    /**
     * 扩展点ID（消息扩展或待办扩展）
     */
    private String fdProvider;

    /**
     * 扩展点名称多语言字段
     */
    private String fdMessageKey;

    /**
     * 是否待办扩展
     */
    private Boolean fdIsTodoExt;

    /**
     * 消息呈现样式
     */
    private DispStyle fdStyle;

    /**
     * 当前多语言语种
     */
    private String fdLang;

    private Date fdCreateTime;

    // =================== 呈现字段 ====================
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    /**
     * 属性名（也叫字段名或编码）
     *
     * @return
     */
    private String fdProperty;

    /**
     * 属性值
     */
    private String fdValue;

    /**
     * 显示名
     *
     * @return
     */
    private String fdPropName;

    /**
     * 属性值类型
     */
    private PropType fdPropType;

    /**
     * 是否必填
     */
    private Boolean fdRequired;
    // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}
