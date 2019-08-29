package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import com.alipay.mobile.common.share.widget.ResUtils;

/* renamed from: fan reason: default package */
/* compiled from: DefaultNotifyLayoutAdapter */
public final class fan implements fad {
    private Resources a;
    private String b;

    public final void a(Context context) {
        this.b = context.getPackageName();
        this.a = context.getResources();
    }

    public final int a() {
        return this.a.getIdentifier("push_notify", ResUtils.LAYOUT, this.b);
    }

    public final int c() {
        Resources resources;
        String str;
        if (fao.g) {
            resources = this.a;
            str = "notify_icon_rom30";
        } else if (fao.f) {
            resources = this.a;
            str = "notify_icon_rom20";
        } else {
            resources = this.a;
            str = "notify_icon";
        }
        return resources.getIdentifier(str, "id", this.b);
    }

    public final int b() {
        int i;
        try {
            i = ((Integer) fbd.a((String) "com.android.internal.R$color", (String) "vivo_notification_title_text_color")).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        if (i > 0) {
            return this.a.getColor(i);
        }
        if (fao.g) {
            return -1;
        }
        if (!fao.f) {
            return -16777216;
        }
        if (fao.g) {
            return Color.parseColor("#ff999999");
        }
        return -1;
    }
}
