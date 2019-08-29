package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl;

import android.content.Context;
import android.os.Build.VERSION;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightPlayView;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.FalconConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.VideoConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf.IFalconService;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf.ISmartCutProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoDeviceWrapper;
import com.alipay.streammedia.encode.utils.OMXConfig;
import tv.danmaku.ijk.media.encode.CameraEncoder;
import tv.danmaku.ijk.media.encode.SessionConfig;
import tv.danmaku.ijk.media.widget.CameraView;
import tv.danmaku.ijk.media.widget.SightCameraGLESView;
import tv.danmaku.ijk.media.widget.SightCameraOMXView;
import tv.danmaku.ijk.media.widget.SightCameraTextureView;

public class DefaultFalconService implements IFalconService {
    public ISmartCutProcessor getSmartCutProcessor() {
        return new DefaultSmartCutProcessor();
    }

    public boolean isSupportWaterMark(boolean cloudSwitch) {
        return cloudSwitch;
    }

    public boolean isAvailable(String path) {
        return false;
    }

    public SightPlayView createLivePlay(Context context) {
        throw new UnsupportedOperationException("暂不支持该功能");
    }

    public CameraEncoder createBeautyCameraEncoder(SessionConfig config) {
        return new CameraEncoder(config);
    }

    public CameraView createFalconCameraView(Context context, FalconConfig config, int beautyValue) {
        return a(context);
    }

    public CameraView createBeautyCameraView(Context context, int level, String crf, String preset) {
        return a(context);
    }

    private static CameraView a(Context context) {
        CameraView cameraView = null;
        VideoConfig config = VideoDeviceWrapper.getVideoConfig();
        if (VERSION.SDK_INT >= 18) {
            OMXConfig omxConfig = VideoDeviceWrapper.getVideoOMXConfig();
            if (FalconUtil.omxSwitch(omxConfig)) {
                cameraView = new SightCameraOMXView(context, omxConfig);
            } else if (config.encodeType == VideoConfig.ENCODE_HARD) {
                cameraView = new SightCameraGLESView(context);
            }
        }
        if (cameraView == null) {
            return new SightCameraTextureView(context, config.level, config.crf, config.preset);
        }
        return cameraView;
    }
}
