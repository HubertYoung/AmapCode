package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightPlayView;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.FalconConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf.IFalconService;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf.ISmartCutProcessor;
import tv.danmaku.ijk.media.encode.CameraEncoder;
import tv.danmaku.ijk.media.encode.SessionConfig;
import tv.danmaku.ijk.media.widget.CameraView;

public enum FalconFactory implements IFalconService {
    INS;
    
    private static final String FALCON_IMPL = "com.alipay.multimedia.falconlooks.FalconServiceImpl";
    private static final String TAG = "FalconFactory";
    private IFalconService mFalconService;

    public final ISmartCutProcessor getSmartCutProcessor() {
        return this.mFalconService.getSmartCutProcessor();
    }

    public final boolean isSupportWaterMark(boolean cloudSwitch) {
        return this.mFalconService.isSupportWaterMark(cloudSwitch);
    }

    public final boolean isAvailable(String path) {
        return this.mFalconService.isAvailable(path);
    }

    public final SightPlayView createLivePlay(Context context) {
        return this.mFalconService.createLivePlay(context);
    }

    public final CameraEncoder createBeautyCameraEncoder(SessionConfig config) {
        return this.mFalconService.createBeautyCameraEncoder(config);
    }

    public final CameraView createFalconCameraView(Context context, FalconConfig config, int beautyValue) {
        return this.mFalconService.createFalconCameraView(context, config, beautyValue);
    }

    public final CameraView createBeautyCameraView(Context context, int level, String crf, String preset) {
        return this.mFalconService.createBeautyCameraView(context, level, crf, preset);
    }
}
