package defpackage;

import android.app.Application;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import com.autonavi.minimap.app.init.Process;

/* renamed from: ckz reason: default package */
/* compiled from: Launcher */
public final class ckz {
    public MapApplication a;

    public ckz(MapApplication mapApplication) {
        this.a = mapApplication;
    }

    public final boolean a() {
        ccm.a().c = this.a;
        ckx ckx = new ckx();
        ckx.a(new clo(), Process.MAIN).a(new clp(), Process.MAIN).a(new clm(), Process.MAIN, Process.LOCATION, Process.PUSH, Process.LOTUSPOOL).a(new ckw(), Process.MAIN, Process.LOCATION, Process.PUSH, Process.LOTUSPOOL).a(new cld(), Process.MAIN).a(new cks(), Process.MAIN).a(new f(), Process.LOCATION).a(new g(), Process.LOTUSPOOL).a(new e(), Process.INSTALLERROR).a(new ckl(), Process.MAIN).b(new ckh(), Process.MAIN, Process.LOTUSPOOL, Process.LOCATION).b(new ckk(), Process.MAIN).a(new clh(), Process.MAIN).a(new e(), Process.MAIN, Process.LOCATION, Process.PUSH, Process.LOTUSPOOL, Process.INSTALLERROR).a(new ckj(), Process.MAIN).a(new cll(), Process.MAIN).a(new a(), Process.MAIN, Process.LOCATION, Process.PUSH, Process.LOTUSPOOL).a(new b(), Process.MAIN).a(new cko(), Process.MAIN).b(new ckn(), Process.MAIN).b(new ckv(), Process.MAIN).b(new cla(), Process.MAIN).b(new ckt(), Process.MAIN).b(new c(), Process.MAIN).b(new cle(), Process.MAIN).b(new ckm(), Process.MAIN);
        if (bno.a) {
            ckx.b(new ckr(), Process.MAIN);
        }
        if (!AjxDebugUtils.isRelease()) {
            ckx.b(new clj(), Process.MAIN);
        }
        ckx.b(new cku(), Process.MAIN).b(new clc(), Process.MAIN).b(new cln(), Process.MAIN).b(new cli(), Process.MAIN);
        ckx.a((Application) this.a);
        return true;
    }
}
