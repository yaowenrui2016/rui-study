package indi.rui.study.unittest.dto;

/**
 * @author: yaowr
 * @create: 2022-04-12
 */
public enum MkExportStep {
    SELECT_ENTITY,  // 选择主文档
    SELECT_REFER,   // 选择关联数据
    STARTING_TASK,  // 启动任务
    RUNNING,        // 运行中
    FINISH,         // 已完成
    ;
}
