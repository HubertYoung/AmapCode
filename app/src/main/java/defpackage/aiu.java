package defpackage;

import android.os.RemoteException;
import com.autonavi.data.service.IResultCallBack;

/* renamed from: aiu reason: default package */
/* compiled from: VoiceResultCallbackUtil */
public final class aiu {
    public static void a(int i, IResultCallBack iResultCallBack, String str, String str2, String str3, String str4) {
        aiq.a(i, str4);
        if (iResultCallBack != null) {
            try {
                iResultCallBack.onJSONResultCallBack(i, str4);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        aiq.a(str, str2, str3, str4);
    }
}
