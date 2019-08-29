package com.alipay.zoloz.toyger.util;

import android.graphics.RectF;
import com.alipay.zoloz.toyger.algorithm.TGFaceAttr;
import com.alipay.zoloz.toyger.bean.ToygerFrame;
import java.util.HashMap;
import java.util.Map;

public class ToygerFrameUtil {
    public static Map<String, String> getFaceParam(ToygerFrame toygerFrame) {
        if (toygerFrame != null) {
            return getFaceParam(toygerFrame.tgFaceAttr);
        }
        return null;
    }

    public static Map<String, String> getFaceParam(TGFaceAttr tGFaceAttr) {
        HashMap hashMap = null;
        if (tGFaceAttr != null) {
            hashMap = new HashMap();
            RectF rectF = tGFaceAttr.faceRegion;
            if (rectF != null) {
                hashMap.put("frectwidth", rectF.width());
                hashMap.put("fwidthscale", rectF.height());
                hashMap.put("frectX", rectF.left);
                hashMap.put("frectY", rectF.top);
            } else {
                hashMap.put("frectwidth", "");
                hashMap.put("fwidthscale", "");
                hashMap.put("frectX", "");
                hashMap.put("frectY", "");
            }
            hashMap.put("fquality", tGFaceAttr.quality);
            hashMap.put("flight", tGFaceAttr.brightness);
            hashMap.put("facePitch", tGFaceAttr.pitch);
            hashMap.put("faceYaw", tGFaceAttr.yaw);
            hashMap.put("faceEyeLeftHwratio", tGFaceAttr.leftEyeBlinkRatio);
            hashMap.put("faceEyeRightHwratio", tGFaceAttr.rightEyeBlinkRatio);
            hashMap.put("faceGaussian", tGFaceAttr.gaussian);
            hashMap.put("faceMotion", tGFaceAttr.motion);
            hashMap.put("integrity", tGFaceAttr.integrity);
            hashMap.put("deviceLight", tGFaceAttr.brightness);
            hashMap.put("distance", tGFaceAttr.distance);
        }
        return hashMap;
    }
}
