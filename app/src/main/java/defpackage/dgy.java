package defpackage;

import com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo;
import com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.RequestAllVoiceCallback;
import com.autonavi.minimap.voice.VoiceRequestHolder;
import com.autonavi.minimap.voice.param.VoiceListRequest;

/* renamed from: dgy reason: default package */
/* compiled from: NetworkRequestHelper */
public final class dgy {
    public static void a(dgx dgx) {
        RequestAllVoiceInfo requestAllVoiceInfo = new RequestAllVoiceInfo();
        requestAllVoiceInfo.a.lock();
        try {
            VoiceListRequest voiceListRequest = new VoiceListRequest();
            voiceListRequest.b = "6";
            voiceListRequest.c = "3.0";
            VoiceRequestHolder.getInstance().sendList(voiceListRequest, new dkn(8000), new RequestAllVoiceCallback(dgx));
        } finally {
            requestAllVoiceInfo.a.unlock();
        }
    }
}
