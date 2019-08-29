package defpackage;

import android.text.TextUtils;
import android.util.Base64;
import java.security.PublicKey;

/* renamed from: eya reason: default package */
/* compiled from: OnReceiveTask */
public abstract class eya extends fbe {
    protected ezy a;

    eya(fbh fbh) {
        super(fbh);
    }

    public final void a(ezy ezy) {
        this.a = ezy;
    }

    public final boolean a(PublicKey publicKey, String str, String str2) {
        if (!ezv.a().b()) {
            fat.d("OnVerifyCallBackCommand", "vertify is not support , vertify is ignore");
            return true;
        } else if (publicKey == null) {
            fat.d("OnVerifyCallBackCommand", "vertify key is null");
            return false;
        } else if (TextUtils.isEmpty(str)) {
            fat.d("OnVerifyCallBackCommand", "contentTag is null");
            return false;
        } else if (!TextUtils.isEmpty(str2)) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str.hashCode());
                sb.append(" = ");
                sb.append(str2);
                fat.d("OnVerifyCallBackCommand", sb.toString());
                if (fax.a(str.getBytes("UTF-8"), publicKey, Base64.decode(str2, 2))) {
                    fat.d("OnVerifyCallBackCommand", "vertify id is success");
                    return true;
                }
                fat.d("OnVerifyCallBackCommand", "vertify fail srcDigest is ".concat(String.valueOf(str)));
                fat.c(this.b, "vertify fail srcDigest is ".concat(String.valueOf(str)));
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                fat.d("OnVerifyCallBackCommand", "vertify exception");
                return false;
            }
        } else {
            fat.d("OnVerifyCallBackCommand", "vertify id is null");
            return false;
        }
    }
}
