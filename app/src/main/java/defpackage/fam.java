package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.share.widget.ResUtils;

/* renamed from: fam reason: default package */
/* compiled from: DefaultNotifyDataAdapter */
public final class fam implements fac {
    private Resources a;
    private String b;
    private String c;
    private String d;

    public final void a(Context context) {
        String str;
        this.b = context.getPackageName();
        this.a = context.getResources();
        this.c = fao.a();
        String str2 = VERSION.RELEASE;
        if (TextUtils.isEmpty(str2)) {
            str = null;
        } else {
            str = str2.replace(".", "");
        }
        this.d = str;
    }

    public final int a() {
        int i;
        String str = this.d;
        while (true) {
            if (VERSION.SDK_INT < 26) {
                break;
            } else if (TextUtils.isEmpty(str)) {
                fat.d("DefaultNotifyDataAdapter", "systemVersion is not suit ");
                break;
            } else {
                StringBuilder sb = new StringBuilder("vivo_push_ard");
                sb.append(str);
                sb.append("_notifyicon");
                String sb2 = sb.toString();
                i = this.a.getIdentifier(sb2, ResUtils.DRAWABLE, this.b);
                if (i > 0) {
                    fat.d("DefaultNotifyDataAdapter", "get notify icon : ".concat(String.valueOf(sb2)));
                    break;
                }
                fat.d("DefaultNotifyDataAdapter", "get notify error icon : ".concat(String.valueOf(sb2)));
                str = str.substring(0, str.length() - 1);
            }
        }
        i = -1;
        if (i != -1) {
            return i;
        }
        return a(this.c);
    }

    public final int b() {
        int i;
        String str = this.d;
        while (true) {
            if (VERSION.SDK_INT < 26) {
                break;
            } else if (TextUtils.isEmpty(str)) {
                fat.d("DefaultNotifyDataAdapter", "systemVersion is not suit ");
                break;
            } else {
                StringBuilder sb = new StringBuilder("vivo_push_ard");
                sb.append(str);
                sb.append("_icon");
                String sb2 = sb.toString();
                i = this.a.getIdentifier(sb2, ResUtils.DRAWABLE, this.b);
                if (i > 0) {
                    fat.d("DefaultNotifyDataAdapter", "get small icon : ".concat(String.valueOf(sb2)));
                    break;
                }
                fat.d("DefaultNotifyDataAdapter", "get small error icon : ".concat(String.valueOf(sb2)));
                str = str.substring(0, str.length() - 1);
            }
        }
        i = -1;
        if (i != -1) {
            return i;
        }
        return b(this.c);
    }

    private int a(String str) {
        while (!TextUtils.isEmpty(str)) {
            Resources resources = this.a;
            StringBuilder sb = new StringBuilder("vivo_push_rom");
            sb.append(str);
            sb.append("_notifyicon");
            int identifier = resources.getIdentifier(sb.toString(), ResUtils.DRAWABLE, this.b);
            if (identifier > 0) {
                return identifier;
            }
            str = str.substring(0, str.length() - 1);
        }
        return this.a.getIdentifier("vivo_push_notifyicon", ResUtils.DRAWABLE, this.b);
    }

    private int b(String str) {
        while (!TextUtils.isEmpty(str)) {
            Resources resources = this.a;
            StringBuilder sb = new StringBuilder("vivo_push_rom");
            sb.append(str);
            sb.append("_icon");
            int identifier = resources.getIdentifier(sb.toString(), ResUtils.DRAWABLE, this.b);
            if (identifier > 0) {
                return identifier;
            }
            str = str.substring(0, str.length() - 1);
        }
        return this.a.getIdentifier("vivo_push_icon", ResUtils.DRAWABLE, this.b);
    }

    public final int c() {
        return VERSION.SDK_INT >= 21 ? 2 : 1;
    }
}
