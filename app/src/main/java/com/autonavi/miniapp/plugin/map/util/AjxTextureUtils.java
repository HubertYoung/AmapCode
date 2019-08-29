package com.autonavi.miniapp.plugin.map.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.AjxFileUtils;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class AjxTextureUtils {
    public static final String STATIC_TEXTURE_PATH = "path://amap_bundle_lib_app/src/texture/src/sharenative/staticTextures.json";
    private static final String TAG = TextUtils.class.getSimpleName();

    public static class StaticTexture {
        public boolean isNinePatch;
        public String path;
    }

    public static ConcurrentHashMap<String, StaticTexture> getStaticTexturesMap(String str) {
        if (TextUtils.isEmpty(str)) {
            AMapLog.debug("infoservice.miniapp", TAG, "getStaticTexturesMap path is null");
            return null;
        }
        byte[] staticTextureData = getStaticTextureData(str);
        if (staticTextureData == null || staticTextureData.length == 0) {
            AMapLog.debug("infoservice.miniapp", TAG, "getStaticTexturesMap fileData is illegal");
            return null;
        }
        String str2 = new String(staticTextureData);
        AMapLog.debug("infoservice.miniapp", TAG, "getStaticTexturesMap file data=".concat(String.valueOf(str2)));
        JSONObject jsonStrFromNetData = getJsonStrFromNetData(str2);
        if (jsonStrFromNetData == null || jsonStrFromNetData.isEmpty()) {
            AMapLog.debug("infoservice.miniapp", TAG, "getStaticTexturesMap JSONObject is illegal, textures=".concat(String.valueOf(str2)));
            return null;
        }
        ConcurrentHashMap<String, StaticTexture> concurrentHashMap = new ConcurrentHashMap<>(469);
        for (Entry next : jsonStrFromNetData.entrySet()) {
            if (!(next.getKey() == null || next.getValue() == null)) {
                StaticTexture staticTexture = new StaticTexture();
                String obj = next.getValue().toString();
                JSONObject jsonStrFromNetData2 = getJsonStrFromNetData(obj);
                if (jsonStrFromNetData2 == null || jsonStrFromNetData2.isEmpty()) {
                    AMapLog.debug("infoservice.miniapp", TAG, "getStaticTexturesMap JSONObject is illegal, textJsonArray=".concat(String.valueOf(obj)));
                    return null;
                }
                for (Entry next2 : jsonStrFromNetData2.entrySet()) {
                    if (!(next2.getKey() == null || next2.getValue() == null)) {
                        if ("path".equals(next2.getKey())) {
                            staticTexture.path = next2.getValue().toString();
                        }
                        if ("isNinePatch".equals(next2.getKey())) {
                            staticTexture.isNinePatch = Boolean.valueOf(next2.getValue().toString()).booleanValue();
                        }
                    }
                }
                concurrentHashMap.put(next.getKey(), staticTexture);
                String str3 = TAG;
                StringBuilder sb = new StringBuilder("map size =");
                sb.append(concurrentHashMap.size());
                AMapLog.debug("infoservice.miniapp", str3, sb.toString());
            }
        }
        return concurrentHashMap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001d, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] loadImage(com.autonavi.minimap.ajx3.image.PictureParams r3, java.lang.String r4) {
        /*
            java.lang.Class<com.autonavi.miniapp.plugin.map.util.AjxTextureUtils> r0 = com.autonavi.miniapp.plugin.map.util.AjxTextureUtils.class
            monitor-enter(r0)
            r1 = 0
            if (r3 != 0) goto L_0x0008
            monitor-exit(r0)
            return r1
        L_0x0008:
            com.autonavi.minimap.ajx3.Ajx r2 = com.autonavi.minimap.ajx3.Ajx.getInstance()     // Catch:{ all -> 0x001e }
            com.autonavi.minimap.ajx3.loader.IAjxImageLoader r4 = r2.lookupLoader(r4)     // Catch:{ all -> 0x001e }
            byte[] r3 = r4.loadImage(r3)     // Catch:{ all -> 0x001e }
            if (r3 == 0) goto L_0x001c
            int r4 = r3.length     // Catch:{ all -> 0x001e }
            if (r4 != 0) goto L_0x001a
            goto L_0x001c
        L_0x001a:
            monitor-exit(r0)
            return r3
        L_0x001c:
            monitor-exit(r0)
            return r1
        L_0x001e:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.util.AjxTextureUtils.loadImage(com.autonavi.minimap.ajx3.image.PictureParams, java.lang.String):byte[]");
    }

    public static synchronized byte[] getStaticTextureData(String str) {
        byte[] bArr;
        synchronized (AjxTextureUtils.class) {
            bArr = null;
            String ajxUrl = AjxFileUtils.getAjxUrl(str);
            if (AjxFileInfo.isFileExists(ajxUrl)) {
                bArr = AjxFileInfo.getFileDataByPath(ajxUrl);
            } else {
                AMapLog.debug("infoservice.miniapp", "file", "getStaticTextureData file not exist=".concat(String.valueOf(ajxUrl)));
            }
        }
        return bArr;
    }

    public static Bitmap getBitmapByAjxPath(String str) {
        Bitmap bitmap = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        byte[] fileDataByPath = AjxFileInfo.getFileDataByPath(AjxFileUtils.getAjxUrl(str));
        if (!(fileDataByPath == null || fileDataByPath.length == 0)) {
            bitmap = BitmapFactory.decodeByteArray(fileDataByPath, 0, fileDataByPath.length);
        }
        return bitmap;
    }

    private static JSONObject getJsonStrFromNetData(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = JSON.parseObject(str);
            try {
                String str2 = TAG;
                StringBuilder sb = new StringBuilder("getJsonStrFromNetData file jarray=");
                sb.append(jSONObject.toJSONString());
                AMapLog.debug("infoservice.miniapp", str2, sb.toString());
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            jSONObject = null;
            e.printStackTrace();
            String str3 = TAG;
            StringBuilder sb2 = new StringBuilder("getJsonStrFromNetData e=");
            sb2.append(e.toString());
            sb2.append(",jsonString=");
            sb2.append(str);
            AMapLog.error("infoservice.miniapp", str3, sb2.toString());
            return jSONObject;
        }
        return jSONObject;
    }
}
