package com.autonavi.minimap.offline.helper;

import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.blutils.SdCardInfo;
import java.util.ArrayList;
import org.json.JSONException;

public class JsDeviceSpaceInfoHelper {
    private static final String EMPTY_JO_STR = "";

    private JsDeviceSpaceInfoHelper() {
    }

    public static String getInfoJOStr() {
        ArrayList<SdCardInfo> enumExternalSDcardInfo = FileUtil.enumExternalSDcardInfo(PathManager.a().a);
        if (enumExternalSDcardInfo == null || enumExternalSDcardInfo.isEmpty()) {
            return "";
        }
        String a = PathManager.a().a(DirType.OFFLINE);
        if (TextUtils.isEmpty(a)) {
            return "";
        }
        try {
            return JsSdCardInfoHelper.convertCurDataSDCardInfo(enumExternalSDcardInfo, a);
        } catch (JSONException unused) {
            return "";
        }
    }
}
