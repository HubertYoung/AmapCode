package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;

public class TaskConstants {
    public static final int DEFAULT_MAX_TASK_OCCURS = ConfigManager.getInstance().getCommonConfigItem().taskConf.defaultMaxOccurs;
    public static final String FILE_NET_TASKSERVICE = "FileNet";
    public static final String IMAGE_BLACK_URL_TASKSERVICE = "ImgBlackUrl";
    public static final String IMAGE_DJG_TASKSERVICE = "ImgDjango";
    public static final String IMAGE_NET_TASKSERVICE = "ImgNet";
    public static final int IMAGE_TASK_OCCURS = ConfigManager.getInstance().getCommonConfigItem().taskConf.defaultImageOccurs;
    public static final String IMAGE_URL_TASKSERVICE = "ImgUrl";
    public static final String IMAGE_WHITE_URL_TASKSERVICE = "ImgWhiteUrl";
    public static final int PRIORITY_HIGH = 10;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_MID = 5;
}
