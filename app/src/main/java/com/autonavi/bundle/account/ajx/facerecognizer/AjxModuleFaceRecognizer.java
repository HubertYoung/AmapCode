package com.autonavi.bundle.account.ajx.facerecognizer;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.account.facerecognition.AMapFaceResponse;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleFaceRecognizer;

public class AjxModuleFaceRecognizer extends AbstractModuleFaceRecognizer {
    private static final String TAG = "AjxModuleFaceRecognizer";

    public AjxModuleFaceRecognizer(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public String getMetaInfo() {
        return anu.a();
    }

    public void verify(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("startFaceVerification()-zimID:");
            sb.append(str);
            sb.append(",params:");
            sb.append(str2);
            AMapLog.debug("basemap.account", TAG, sb.toString());
        }
        anu.a(str, str2, new AMapFaceResponse(jsFunctionCallback));
    }
}
