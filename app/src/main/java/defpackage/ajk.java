package defpackage;

import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.nebula.provider.H5UcInitProvider;

/* renamed from: ajk reason: default package */
/* compiled from: NebulaUCInitProviderImpl */
public final class ajk implements H5UcInitProvider {
    public final boolean initUC() {
        return ajm.a();
    }

    public static void a() {
        H5Flag.ucReady = true;
        H5Flag.initUcNormal = true;
    }

    public static void b() {
        H5Flag.ucReady = false;
        H5Flag.initUcNormal = false;
    }
}
