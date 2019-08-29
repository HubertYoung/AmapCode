package com.autonavi.minimap.voice;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.voice.param.VoiceListRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class VoiceRequestHolder {
    private static volatile VoiceRequestHolder instance;

    private VoiceRequestHolder() {
    }

    public static VoiceRequestHolder getInstance() {
        if (instance == null) {
            synchronized (VoiceRequestHolder.class) {
                if (instance == null) {
                    instance = new VoiceRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendList(VoiceListRequest voiceListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendList(voiceListRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendList(VoiceListRequest voiceListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            voiceListRequest.addHeaders(dkn.d);
            voiceListRequest.setTimeout(dkn.b);
            voiceListRequest.setRetryTimes(dkn.c);
        }
        voiceListRequest.setUrl(VoiceListRequest.a);
        voiceListRequest.addSignParam("channel");
        voiceListRequest.addSignParam("product");
        voiceListRequest.addReqParam("product", voiceListRequest.b);
        voiceListRequest.addReqParam("version", voiceListRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) voiceListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) voiceListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
