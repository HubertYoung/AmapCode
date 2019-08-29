package defpackage;

import android.content.Context;
import android.support.annotation.Nullable;
import com.amap.bundle.pay.wechat.WechatSign;

/* renamed from: aby reason: default package */
/* compiled from: PayGenerator */
public final class aby {
    @Nullable
    public static abt a(int i, Context context) {
        if (i != 10) {
            return null;
        }
        return new acd(context);
    }

    @Nullable
    public static abv b(int i, Context context) {
        if (i != 10) {
            return null;
        }
        return new WechatSign(context);
    }

    @Nullable
    public static abs c(int i, Context context) {
        if (i != 10) {
            return null;
        }
        return new acc(context);
    }

    @Nullable
    public static abr d(int i, Context context) {
        if (i != 10) {
            return null;
        }
        return new acb(context);
    }
}
