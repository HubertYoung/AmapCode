package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.network.response.exception.ServerException;

/* renamed from: cfs reason: default package */
/* compiled from: AosAbstractWalletParser */
public abstract class cfs extends AbstractAOSParser {
    protected ServerException a;

    public final void a(ServerException serverException) {
        this.a = serverException;
    }

    public final ServerException a() {
        return this.a;
    }
}
