package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;

/* renamed from: ejc reason: default package */
/* compiled from: BaseVoucersResponser */
public abstract class ejc extends AbstractAOSParser {
    public int b;
    public String c;

    public abstract String b();

    public boolean isSuccessRequest() {
        return true;
    }

    public ejc() {
        this.b = 1;
        this.c = b();
    }

    public ejc(int i) {
        this.b = 1;
        this.b = i;
        this.c = b();
    }
}
