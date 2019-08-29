package defpackage;

import android.app.Application;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.Tts;
import com.iflytek.tts.TtsService.TtsManager;
import java.io.File;

/* renamed from: dhg reason: default package */
/* compiled from: ExternalServiceImpl */
public class dhg implements dhf {
    public final Application c() {
        return AMapAppGlobal.getApplication();
    }

    public final void a(String str) {
        ToastHelper.showToast(str, 1);
    }

    public final void b(File file) {
        ahd.a(file);
    }

    public final void d() {
        dfm dfm = (dfm) ank.a(dfm.class);
        if (Tts.getInstance().JniIsPlaying() == 1 && dfm != null && !dfm.b()) {
            TtsManager.getInstance().TTS_Stop();
        }
    }

    public final GeoPoint a() {
        return LocationInstrument.getInstance().getLatestPosition(5);
    }

    public final GeoPoint b() {
        return LocationInstrument.getInstance().getLatestPosition();
    }

    public final String a(File file) {
        return agy.a(file, null, true);
    }

    public final String b(String str) {
        return agy.a(str);
    }
}
