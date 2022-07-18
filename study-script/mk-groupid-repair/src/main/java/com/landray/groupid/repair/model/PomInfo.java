package com.landray.groupid.repair.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * @author: yaowr
 * @create: 2022-07-15
 */
@Getter
@Setter
public class PomInfo {

    /**
     * 模块名
     */
    private String module;

    /**
     * 工程名
     */
    private String project;

    /**
     * pom文件
     */
    private File pomFile;

    /**
     * 替换的数量
     */
    private Integer replaced;
}
