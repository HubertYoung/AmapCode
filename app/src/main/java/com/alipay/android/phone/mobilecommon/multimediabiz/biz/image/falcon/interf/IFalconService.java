package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightPlayView;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.data.FalconConfig;
import tv.danmaku.ijk.media.encode.CameraEncoder;
import tv.danmaku.ijk.media.encode.SessionConfig;
import tv.danmaku.ijk.media.widget.CameraView;

public interface IFalconService {
    CameraEncoder createBeautyCameraEncoder(SessionConfig sessionConfig);

    CameraView createBeautyCameraView(Context context, int i, String str, String str2);

    CameraView createFalconCameraView(Context context, FalconConfig falconConfig, int i);

    SightPlayView createLivePlay(Context context);

    ISmartCutProcessor getSmartCutProcessor();

    boolean isAvailable(String str);

    boolean isSupportWaterMark(boolean z);
}
