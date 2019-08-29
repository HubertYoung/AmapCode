package defpackage;

import com.autonavi.minimap.alc.model.ALCLogLevel;

/* renamed from: cjz reason: default package */
/* compiled from: IALCLogListener */
public interface cjz {
    void onLog(ALCLogLevel aLCLogLevel, String str, String str2, String str3, String str4, String str5);

    void onRecord(ALCLogLevel aLCLogLevel, long j, String str, String str2, int i, String str3);
}
