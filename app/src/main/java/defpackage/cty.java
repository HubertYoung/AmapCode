package defpackage;

import com.autonavi.annotation.MultipleImpl;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

@MultipleImpl(aiy.class)
/* renamed from: cty reason: default package */
/* compiled from: OnWebViewDestory */
public class cty implements aiy {
    public final void a() {
        ctw a = ctw.a();
        if (!a.f) {
            if (a.a && DoNotUseTool.getActivity() != null) {
                kj.a(DoNotUseTool.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, a.g);
            }
            if (a.e != null) {
                ctw.c();
                a.e = null;
            }
            if (a.c != null) {
                a.c = null;
            }
        }
        ctw.d();
    }
}
