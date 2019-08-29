package com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools;

import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.event.AntEvent.Builder;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;

public class BehavorReporter {
    public static final String PROVIDE_BY_ALIPAY = "alipay";
    public static final String PROVIDE_BY_ALIYUN = "aliyun";
    public static final String PROVIDE_BY_LOCAL = "local";
    public static String STATUS_FAIL = UploadDataResult.FAIL_MSG;
    public static String STATUS_SUCCESS = "success";
    public static String TYPE_AICAMERA = "aicamera";
    public static String TYPE_AICAMERA_AUDIO_RECOGNIZE = "aicamera_audio_recognize";
    public static String TYPE_ASR = "asr";
    public static String TYPE_ASR_LEGACY = "asr_legacy";
    public static String TYPE_OCR = "ocr";
    public static String TYPE_PORN = "porn";

    public static void report(String monitorType, String appId, String referUrl, String businessId, String serviceId, String serviceScore, String status, String statusCode, String algorithmCost, String algorithmProvider, String modelId, String modelMd5, String modelConfig) {
        Builder eventBuilder = new Builder();
        eventBuilder.setEventID("101045");
        eventBuilder.setBizType("middle");
        eventBuilder.setLoggerLevel(2);
        eventBuilder.addExtParam("source_appid", appId);
        eventBuilder.addExtParam("referer_url", referUrl);
        eventBuilder.addExtParam("monitor_type", monitorType);
        eventBuilder.addExtParam(FileCacheModel.F_CACHE_BUSINESS_ID, businessId);
        eventBuilder.addExtParam("service_id", serviceId);
        eventBuilder.addExtParam("service_score", serviceScore);
        eventBuilder.addExtParam("status", status);
        eventBuilder.addExtParam("status_code", statusCode);
        eventBuilder.addExtParam("algorithm_cost", algorithmCost);
        eventBuilder.addExtParam("algorithm_provider", algorithmProvider);
        eventBuilder.addExtParam("model_id", modelId);
        eventBuilder.addExtParam("model_md5", modelMd5);
        eventBuilder.addExtParam("model_config", modelConfig);
        eventBuilder.build().send();
    }
}
