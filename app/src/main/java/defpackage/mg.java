package defpackage;

import com.autonavi.annotation.BundleInterface;
import java.io.File;

@BundleInterface(md.class)
/* renamed from: mg reason: default package */
/* compiled from: CloudResourceServiceImpl */
public class mg implements md {
    public final void a(String str, mc mcVar) {
        me meVar = mf.a().b;
        String str2 = null;
        a a = meVar == null ? null : meVar.a(str);
        if (a != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(a.f);
            sb.append(File.separator);
            sb.append("item.cloudVersion");
            if (new File(sb.toString()).exists()) {
                str2 = a.f;
            }
        }
        if (str2 != null) {
            mcVar.a(str2);
        } else {
            mf.a().a(str, mcVar);
        }
    }
}
