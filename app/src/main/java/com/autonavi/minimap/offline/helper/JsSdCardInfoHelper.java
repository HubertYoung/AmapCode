package com.autonavi.minimap.offline.helper;

import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.blutils.SdCardInfo;
import com.amap.bundle.blutils.SdCardInfo.SDCardType;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsSdCardInfoHelper {
    public static final String AMAP_ROOT_PATH = "autonavi";
    private static final String EMPTY_JSON_STR = "";

    private JsSdCardInfoHelper() {
    }

    public static String convertSdCardList(List<SdCardInfo> list, String str) {
        if (list == null || list.isEmpty() || TextUtils.isEmpty(str)) {
            return "";
        }
        List<SdCardInfo> cleanNullData = cleanNullData(list);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sdCardList", getSdCardJA(cleanNullData, str));
            return jSONObject.toString();
        } catch (JSONException unused) {
            return "";
        }
    }

    public static String convertCurDataSDCardInfo(List<SdCardInfo> list, String str) throws JSONException {
        String str2;
        if (list == null || list.isEmpty() || TextUtils.isEmpty(str)) {
            return "";
        }
        JSONObject jSONObject = null;
        Iterator<SdCardInfo> it = cleanNullData(list).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            SdCardInfo next = it.next();
            if (next != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(next.b);
                sb.append(File.separator);
                sb.append("autonavi");
                sb.append(DirType.OFFLINE.getPath());
                if (TextUtils.equals(sb.toString(), str)) {
                    jSONObject = new JSONObject();
                    if (isExternalCard(next.a)) {
                        str2 = OfflineUtil.getOfflineString(R.string.offline_storage_extern);
                    } else {
                        str2 = OfflineUtil.getOfflineString(R.string.offline_storage_inner);
                    }
                    jSONObject.put("sdcardName", str2);
                    jSONObject.put("availableSpace", next.e);
                    jSONObject.put("totalSpace", next.c);
                }
            }
        }
        return jSONObject != null ? jSONObject.toString() : "";
    }

    private static JSONArray getSdCardJA(List<SdCardInfo> list, String str) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        int i = 0;
        for (SdCardInfo next : list) {
            JSONObject jSONObject = new JSONObject();
            StringBuilder sb = new StringBuilder();
            sb.append(next.b);
            sb.append(File.separator);
            sb.append("autonavi");
            sb.append(DirType.OFFLINE.getPath());
            String sb2 = sb.toString();
            boolean isExternalCard = isExternalCard(next.a);
            jSONObject.put("isExternalCard", isExternalCard);
            jSONObject.put("isCurStorageCard", TextUtils.equals(sb2, str));
            if (isExternalCard) {
                i++;
                jSONObject.put("externalCardSize", i);
            }
            jSONObject.put("path", next.b);
            jSONObject.put("totalSize", next.c);
            jSONObject.put("availableSize", next.e);
            jSONObject.put("absolutePath", sb2);
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    private static List<SdCardInfo> cleanNullData(List<SdCardInfo> list) {
        Iterator<SdCardInfo> it = list.iterator();
        while (it.hasNext()) {
            SdCardInfo next = it.next();
            if (next == null || TextUtils.isEmpty(next.b)) {
                it.remove();
            }
        }
        return list;
    }

    private static boolean isExternalCard(SDCardType sDCardType) {
        return sDCardType == SDCardType.EXTERNALCARD;
    }
}
