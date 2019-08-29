package defpackage;

import android.app.Application;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.annotation.PageAction;

/* renamed from: uh reason: default package */
/* compiled from: CrashLogHeaderInfo */
final class uh {
    uc a;
    Application b;

    uh(uc ucVar) {
        this.a = ucVar;
        this.b = ucVar.a().a();
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        String str;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(this.a.b().f());
            String sb2 = sb.toString();
            try {
                Class g = this.a.b().g();
                if (g == null) {
                    return sb2;
                }
                String name = g.getName();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(MetaRecord.LOG_SEPARATOR);
                if (TextUtils.isEmpty(name)) {
                    name = "";
                }
                sb3.append(name);
                String sb4 = sb3.toString();
                try {
                    PageAction pageAction = (PageAction) g.getAnnotation(PageAction.class);
                    if (pageAction != null) {
                        String value = pageAction.value();
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(sb4);
                        if (TextUtils.isEmpty(value)) {
                            str = "";
                        } else {
                            str = String.format("@PageAction('%s')", new Object[]{value});
                        }
                        sb5.append(str);
                        return sb5.toString();
                    }
                } catch (Throwable unused) {
                }
                return sb4;
            } catch (Throwable unused2) {
                return sb2;
            }
        } catch (Throwable unused3) {
            r1 = "";
            return "";
        }
    }
}
