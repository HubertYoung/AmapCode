package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: dof reason: default package */
/* compiled from: NetTransferManagerImpl */
public final class dof implements doe {
    private Map<AosRequest, dod> a = new ConcurrentHashMap();

    public final void a(dod dod) {
        this.a.remove(dod.a());
    }
}
