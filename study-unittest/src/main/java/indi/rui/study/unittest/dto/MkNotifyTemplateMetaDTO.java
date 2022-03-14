package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-02-28
 */
@Getter
@Setter
public class MkNotifyTemplateMetaDTO {
    /**
     * 预置模板
     */
    private List<TemplateMeta> templateMetas;

    /**
     * 标准消息模板参数
     */
    private List<MkReplaceVarMeta> standard;

    @Getter
    @Setter
    public static class TemplateMeta {
        /**
         * 编码
         */
        private String code;

        /**
         * 名称
         */
        private String name;

        /**
         * 扩展消息模板参数：ExtendFieldMeta.class
         */
        private List<MkReplaceVarMeta> extend;
    }
}
