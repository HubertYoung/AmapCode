package com.alipay.mobile.security.faceauth;

import android.graphics.Bitmap;
import com.alipay.mobile.security.bio.service.BioService;
import com.alipay.mobile.security.faceauth.api.FaceCallback;
import com.alipay.mobile.security.faceauth.api.FaceFrame;
import com.alipay.mobile.security.faceauth.api.RESULT;
import com.alipay.mobile.security.faceauth.api.YUVFrame;

public abstract class FaceService extends BioService {
    public abstract void closeService();

    public abstract FaceFrame faceQualityDetection(Bitmap bitmap);

    public abstract RESULT faceQualityDetection(YUVFrame yUVFrame, FaceCallback faceCallback);

    public abstract RESULT init();

    public abstract boolean isServicePaused();

    public abstract void pause();

    public abstract void resume();

    public abstract void setDetectType(FaceDetectType faceDetectType);
}
