package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService;
import com.autonavi.minimap.bundle.agroup.api.IDataService;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;

/* renamed from: cuh reason: default package */
/* compiled from: IAgroupService */
public interface cuh extends bie, esc {
    cui a(bid bid);

    void a(Context context);

    void a(JsFunctionCallback jsFunctionCallback);

    void a(TeamStatus teamStatus);

    IAgroupOverlayService b();

    void b(JsFunctionCallback jsFunctionCallback);

    void b(@NonNull String str, @NonNull String str2);

    cug c();

    boolean c(JsFunctionCallback jsFunctionCallback);

    boolean c(String str);

    void d();

    boolean e();

    void f();

    void g();

    void j();

    String k();

    IDataService l();

    void p();

    boolean q();

    boolean r();
}
