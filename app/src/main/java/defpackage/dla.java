package defpackage;

import android.support.annotation.NonNull;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/* renamed from: dla reason: default package */
/* compiled from: MainMapSyncManager */
public final class dla {
    public boolean a;
    public boolean b;
    private bid c;
    private MapManager d = DoNotUseTool.getMapManager();

    @SuppressFBWarnings({"URF_UNREAD_FIELD"})
    public dla(@NonNull bid bid) {
        this.c = bid;
    }

    public static void a() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("cookie:");
            stringBuffer.append(abj.a().b());
            AMapLog.logNormalNative(AMapLog.GROUP_COMMON, "P0004", ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, stringBuffer.toString());
        } catch (Throwable unused) {
        }
    }
}
