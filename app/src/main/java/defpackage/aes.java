package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

/* renamed from: aes reason: default package */
/* compiled from: ShareClickListener */
public final class aes implements a {
    private String a;
    private JsFunctionCallback b;

    public aes(@NonNull String str, JsFunctionCallback jsFunctionCallback) {
        this.a = str;
        this.b = jsFunctionCallback;
    }

    public final void a(int i) {
        String str = "";
        switch (i) {
            case 3:
                str = "WX";
                break;
            case 4:
                str = "WXCIRCLE";
                break;
            case 6:
                str = "LINK";
                break;
            case 7:
                str = "MORE";
                break;
            case 11:
                str = "DINGDING";
                break;
        }
        if (!TextUtils.isEmpty(str)) {
            this.b.callback(str);
        }
    }
}
