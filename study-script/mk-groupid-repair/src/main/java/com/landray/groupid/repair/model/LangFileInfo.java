package com.landray.groupid.repair.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * @author: yaowr
 * @create: 2022-07-18
 */
@Getter
@Setter
public class LangFileInfo {

    /**
     * 模块名
     */
    private String module;

    /**
     * 工程名
     */
    private String project;

    /**
     * 多语言文件
     */
    private File[] langFiles;
}
