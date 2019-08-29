package defpackage;

import android.support.annotation.CheckResult;
import com.autonavi.map.fragmentcontainer.page.IPresenter;

/* renamed from: ajh reason: default package */
/* compiled from: IWebViewPresenter */
public interface ajh extends IPresenter<ajc> {

    /* renamed from: ajh$a */
    /* compiled from: IWebViewPresenter */
    public interface a {
        String a();

        @CheckResult
        boolean b();
    }

    /* renamed from: ajh$b */
    /* compiled from: IWebViewPresenter */
    public interface b {
        boolean a();

        String b();

        long c();
    }

    void a(ajc ajc);

    String b();

    b c();

    boolean d();

    boolean e();

    boolean f();

    boolean g();

    boolean h();

    a l_();
}
