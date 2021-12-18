package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OpenDingAppSetting {

    public static final  String UNIVERSAL="UNIVERSAL";
    public static final  String NOTIFY="NOTIFY";
    public static final  String WORK_NOTICATION="WORK_NOTICATION";


    private String fdId;

    private String fdName;

    /**
     * 1、待办
     * 2、工作通知
     * 100 通用
     */
    private String fdType;

    private String appKey;

    private String appSecret;


}
