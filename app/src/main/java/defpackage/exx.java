package defpackage;

import android.os.Bundle;
import java.util.HashMap;

/* renamed from: exx reason: default package */
/* compiled from: ReporterCommand */
public final class exx extends fbh {
    public HashMap<String, String> a;
    private long b;

    public exx() {
        super(2012);
    }

    public exx(long j) {
        this();
        this.b = j;
    }

    public final void a(ewx ewx) {
        HashMap<String, String> hashMap = this.a;
        if (ewx.a == null) {
            ewx.a = new Bundle();
        }
        ewx.a.putSerializable("ReporterCommand.EXTRA_PARAMS", hashMap);
        ewx.a((String) "ReporterCommand.EXTRA_REPORTER_TYPE", this.b);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ReporterCommand（");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }

    public final void b(ewx ewx) {
        this.a = (HashMap) (ewx.a == null ? null : ewx.a.getSerializable("ReporterCommand.EXTRA_PARAMS"));
        this.b = ewx.b((String) "ReporterCommand.EXTRA_REPORTER_TYPE", this.b);
    }
}
