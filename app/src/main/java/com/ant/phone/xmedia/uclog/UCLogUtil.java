package com.ant.phone.xmedia.uclog;

import com.alipay.alipaylogger.Log;

public class UCLogUtil {
    public static void a(int success, String size, long time, String biz, String modelFileId, int engineType) {
        LogItem item = new LogItem("UC_XM_C01", "event", "InitEngine", String.valueOf(success), size, String.valueOf(time));
        item.a("bz", biz);
        item.a("engineType", String.valueOf(engineType));
        item.a("modelFileId", modelFileId);
        item.a(item);
        Log.i("UCLogUtil", "InitEngine, success:" + success + ", size:" + size + ", time:" + time + ",biz:" + biz + ", modelId:" + modelFileId + ", engineType:" + engineType);
    }

    public static void a(int success, String size, long time, String biz, String modelFileId, int errCode, String msg) {
        LogItem item = new LogItem("UC_XM_C02", "event", "ClassifyImage", String.valueOf(success), size, String.valueOf(time));
        item.a("bz", biz);
        item.a("errCode", String.valueOf(errCode));
        item.a("msg", msg);
        item.a("modelFileId", modelFileId);
        item.a(item);
        Log.i("UCLogUtil", "ClassifyImage, success:" + success + ", size:" + size + ", time:" + time + ",biz:" + biz + ", modelId:" + modelFileId + ", errCode:" + errCode + ", msg:" + msg);
    }

    public static void b(int success, String size, long time, String biz, String modelFileId, int errCode, String msg) {
        LogItem item = new LogItem("UC_XM_C03", "event", "DetectImage", String.valueOf(success), size, String.valueOf(time));
        item.a("bz", biz);
        item.a("errCode", String.valueOf(errCode));
        item.a("msg", msg);
        item.a("modelFileId", modelFileId);
        item.a(item);
        Log.i("UCLogUtil", "DetectImage, success:" + success + ", size:" + size + ", time:" + time + ",biz:" + biz + ", modelId:" + modelFileId + ", errCode:" + errCode + ", msg:" + msg);
    }

    public static void a(String size, long time, String biz, String modelFileId, long detectTime) {
        LogItem item = new LogItem("UC_XM_C04", "event", "DetectFrame", "0", size, String.valueOf(time));
        item.a("bz", biz);
        item.a("modelFileId", modelFileId);
        item.a("detectTime", String.valueOf(detectTime));
        item.a(item);
        Log.i("UCLogUtil", "DetectFrame, success:0, size:" + size + ", time:" + time + ",biz:" + biz + ", modelId:" + modelFileId + ", detectTime:" + detectTime);
    }

    public static void b(String size, long time, String biz, String modelFileId, long classifyTime) {
        LogItem item = new LogItem("UC_XM_C05", "event", "ClassifyFrame", "0", size, String.valueOf(time));
        item.a("bz", biz);
        item.a("modelFileId", modelFileId);
        item.a("classifyTime", String.valueOf(classifyTime));
        item.a(item);
        Log.i("UCLogUtil", "DetectFrame, success:0, size:" + size + ", time:" + time + ",biz:" + biz + ", modelId:" + modelFileId + ", classifyTime:" + classifyTime);
    }

    public static void a(String size, long time, String biz, String modelFileId, long detectTime, long trackTime) {
        LogItem item = new LogItem("UC_XM_C06", "event", "DetectTrackFrame", "0", size, String.valueOf(time));
        item.a("bz", biz);
        item.a("modelFileId", modelFileId);
        item.a("detectTime", String.valueOf(detectTime));
        item.a("trackTime", String.valueOf(trackTime));
        item.a(item);
        Log.i("UCLogUtil", "DetectFrame, success:0, size:" + size + ", time:" + time + ",biz:" + biz + ", modelId:" + modelFileId + ", detectTime:" + detectTime + ",trackTime:" + trackTime);
    }
}
