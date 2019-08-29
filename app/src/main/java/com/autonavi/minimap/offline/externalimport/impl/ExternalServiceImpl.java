package com.autonavi.minimap.offline.externalimport.impl;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.SdCardInfo;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.offline.externalimport.IExternalService;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.Tts;
import com.iflytek.tts.TtsService.TtsManager;
import com.iflytek.tts.TtsService.TtsManagerUtil;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONObject;

public class ExternalServiceImpl implements IExternalService {
    public bid getPageContext() {
        return AMapPageUtil.getPageContext();
    }

    @Nullable
    public synchronized ArrayList<SdCardInfo> enumExternalSDcardInfo(Context context) {
        try {
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
        return FileUtil.enumExternalSDcardInfo(context);
    }

    public GeoPoint getMapCenter(bid bid) {
        return GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapCenter());
    }

    public String getInnerSDCardPath(Context context) {
        return FileUtil.getInnerSDCardPath(context);
    }

    public int getVersionCode() {
        return chl.a();
    }

    public String getVersionName() {
        return chl.b();
    }

    public void closeMapDB(bid bid) {
        if (bid != null) {
            DoNotUseTool.getMapView();
        }
    }

    public void openMapDB(bid bid) {
        if (bid != null) {
            DoNotUseTool.getMapView();
        }
    }

    public int getMapCenterAdcode(bid bid) {
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapCenter());
        if (glGeoPoint2GeoPoint != null) {
            return glGeoPoint2GeoPoint.getAdCode();
        }
        return -1;
    }

    public Application getApplication() {
        return AMapAppGlobal.getApplication();
    }

    public void showToast(String str, int i) {
        ToastHelper.showToast(str, i);
    }

    public void showToast(String str) {
        ToastHelper.showToast(str);
    }

    public void deleteFile(File file) {
        ahd.a(file);
    }

    public String getAppSDCardFileDir() {
        return FileUtil.getAppSDCardFileDir();
    }

    public int IOParam(int i, int i2, int i3) {
        return ((dfm) ank.a(dfm.class)) != null ? 0 : 0;
    }

    public int setParam(String str, String str2) {
        return ((dfm) ank.a(dfm.class)) != null ? 0 : 0;
    }

    public void setTtsStop() {
        dfm dfm = (dfm) ank.a(dfm.class);
        if (Tts.getInstance().JniIsPlaying() == 1 && dfm != null && !dfm.b()) {
            TtsManager.getInstance().TTS_Stop();
        }
    }

    public String GetFileFullName(Context context) {
        return TtsManagerUtil.getDefaultFileFullName(context);
    }

    public void actionLogV2(String str, String str2, JSONObject jSONObject) {
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    public GeoPoint getLatestPosition(int i) {
        return LocationInstrument.getInstance().getLatestPosition(i);
    }

    public GeoPoint getLatestPosition() {
        return LocationInstrument.getInstance().getLatestPosition();
    }

    public String getFileMD5(File file) {
        return agy.a(file, null, true);
    }

    public String getStringMD5(String str) {
        return agy.a(str);
    }
}
