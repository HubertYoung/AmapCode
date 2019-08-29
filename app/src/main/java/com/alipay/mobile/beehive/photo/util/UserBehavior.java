package com.alipay.mobile.beehive.photo.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;

public class UserBehavior {
    private static final String APPID_IMAGEEDIT = "20000979";
    private static final String TAG = "UserBehavior";

    public static class PhotoServiceStatistics {
        public static int scanCount;
        public long firstDataTimePast;
        public int mediaCount;
        public long startScanTime;
        public long timeCost;
        public long triggerScanTime;
        public boolean userLeaveBeforeScanFinish;
    }

    public static void onPhotoSelectActivityEditImageClicked() {
        clickAction("UC-TUYA-160817-02", "IMAGE_EDITOR_PHOTO_GRID", "20000979");
    }

    public static void onPhotoPreviewActivityEditImageClicked() {
        clickAction("UC-TUYA-160817-03", "IMAGE_EDITOR_PHOTO_PREVIEW", "20000979");
    }

    private static void clickAction(String seedId, String userCaseId, String appId) {
        Behavor behavor = new Behavor();
        behavor.setUserCaseID(userCaseId);
        behavor.setSeedID(seedId);
        behavor.setAppID(appId);
        behavor.setBehaviourPro("PhotoPreview");
        LoggerFactory.getBehavorLogger().event(null, behavor);
    }

    public static void reportUnusableEvent(String caseId, String DescKey, String DescValue) {
        try {
            Behavor unUsable = new Behavor();
            unUsable.setAppID("20000222");
            unUsable.addExtParam(DescKey, DescValue);
            unUsable.setUserCaseID(caseId);
            LoggerFactory.getBehavorLogger().event(null, unUsable);
        } catch (Exception e) {
            PhotoLogger.debug(TAG, "reportUnusableEvent exception:" + e.getMessage());
        }
    }

    public static void reportPhotoScanEvent(PhotoServiceStatistics s) {
        if (s != null) {
            Behavor behavor = new Behavor();
            behavor.setUserCaseID("UC_BeePhotoService");
            behavor.setSeedID("SEED_BeePhotoService");
            behavor.setParam1(String.valueOf(s.triggerScanTime));
            behavor.setParam2(String.valueOf(s.startScanTime));
            behavor.setParam3(String.valueOf(s.timeCost));
            behavor.addExtParam("firstDataTimePast", String.valueOf(s.firstDataTimePast));
            behavor.addExtParam("mediaCount", String.valueOf(s.mediaCount));
            behavor.addExtParam("userLeaveBeforeScanFinish", String.valueOf(s.userLeaveBeforeScanFinish));
            behavor.addExtParam("scanCount", String.valueOf(PhotoServiceStatistics.scanCount));
            LoggerFactory.getBehavorLogger().event("BeePhotoServiceScan", behavor);
        }
    }
}
