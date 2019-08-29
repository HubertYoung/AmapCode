package com.amap.bundle.drive.ajx.module;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import org.json.JSONException;
import org.json.JSONObject;

public class NaviEndShareStatusCallback extends dcd {
    private String mParms;

    public NaviEndShareStatusCallback(String str, JsFunctionCallback jsFunctionCallback) {
        this.mParms = str;
    }

    public ShareParam getShareDataByType(int i) {
        switch (i) {
            case 3:
                e eVar = new e(0);
                String snapImgPath = getSnapImgPath();
                eVar.h = snapImgPath;
                eVar.g = rd.c(snapImgPath);
                eVar.e = 3;
                eVar.c = false;
                return eVar;
            case 4:
                e eVar2 = new e(1);
                String snapImgPath2 = getSnapImgPath();
                eVar2.h = snapImgPath2;
                eVar2.g = rd.c(snapImgPath2);
                eVar2.c = false;
                eVar2.e = 3;
                return eVar2;
            case 5:
                f fVar = new f();
                try {
                    fVar.a = new JSONObject(this.mParms).optString("shareContent");
                    fVar.j = true;
                    fVar.h = getSnapImgPath();
                    fVar.c = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return fVar;
            default:
                return null;
        }
    }

    private String getSnapImgPath() {
        try {
            String optString = new JSONObject(this.mParms).optString("snapshotImg");
            try {
                return (TextUtils.isEmpty(optString) || !optString.startsWith("file:/")) ? optString : optString.replaceFirst("file:/", "");
            } catch (Exception unused) {
                return optString;
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    public void onFinish(int i, int i2) {
        super.onFinish(i, i2);
    }
}
