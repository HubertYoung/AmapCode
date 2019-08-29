package defpackage;

import android.app.Activity;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

/* renamed from: dav reason: default package */
/* compiled from: IMainMapMsgDialog */
public interface dav {

    /* renamed from: dav$a */
    /* compiled from: IMainMapMsgDialog */
    public interface a {
        void a(AmapMessage amapMessage);

        void d();

        void e();

        boolean f();
    }

    void a();

    void a(Activity activity, a aVar);

    void a(AmapMessage amapMessage);

    boolean b();

    AmapMessage c();
}
