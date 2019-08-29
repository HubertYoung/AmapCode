package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.util.HashMap;
import org.json.JSONObject;

/* renamed from: bqw reason: default package */
/* compiled from: HotfixLogger */
public final class bqw {
    public static void a(int i, String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("version", String.valueOf(i));
        hashMap.put("event", str);
        hashMap.put("value", str2);
        cjy.a(ALCLogLevel.P1, (String) AMapLog.GROUP_COMMON, (String) "D1", (String) "P0060", (String) ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, String.valueOf(new JSONObject(hashMap)));
    }
}
