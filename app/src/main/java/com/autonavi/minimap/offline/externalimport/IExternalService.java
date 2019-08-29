package com.autonavi.minimap.offline.externalimport;

import android.app.Application;
import android.content.Context;
import com.amap.bundle.blutils.SdCardInfo;
import com.autonavi.common.model.GeoPoint;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONObject;

@Deprecated
public interface IExternalService extends bie {
    public static final int DATA_ADD_OP = 2;
    public static final int DATA_DELETE_BASE_CROSS_OP = 9;
    public static final int DATA_DELETE_OP = 5;
    public static final int DATA_FINISH_OP = 4;
    public static final int DATA_UPDAE_BASE_CROSS_OP = 8;
    public static final int DATA_UPDAE_OP = 3;
    public static final int JCB_ADCODE = 0;
    public static final int OFFLINE_DATAMANAGER = 1;
    public static final int VERSION_GET_CROSS_OP = 6;
    public static final int VERSION_GET_DGCONFIG = 7;
    public static final int VERSION_GET_OP = 1;
    public static final int[] ZHIXIA_ADCODE_ARRAY = {110000, 310000, 500000, 120000};

    String GetFileFullName(Context context);

    int IOParam(int i, int i2, int i3);

    void actionLogV2(String str, String str2, JSONObject jSONObject);

    void closeMapDB(bid bid);

    void deleteFile(File file);

    ArrayList<SdCardInfo> enumExternalSDcardInfo(Context context);

    String getAppSDCardFileDir();

    Application getApplication();

    String getFileMD5(File file);

    String getInnerSDCardPath(Context context);

    GeoPoint getLatestPosition();

    GeoPoint getLatestPosition(int i);

    GeoPoint getMapCenter(bid bid);

    int getMapCenterAdcode(bid bid);

    bid getPageContext();

    String getStringMD5(String str);

    int getVersionCode();

    String getVersionName();

    void openMapDB(bid bid);

    int setParam(String str, String str2);

    void setTtsStop();

    void showToast(String str);

    void showToast(String str, int i);
}
