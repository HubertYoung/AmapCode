package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback.WorkThread;
import java.util.HashMap;
import java.util.Map;

/* renamed from: dkn reason: default package */
/* compiled from: FalconAosConfig */
public final class dkn {
    public final WorkThread a;
    public final int b;
    public final int c;
    public final Map<String, String> d;

    public dkn() {
        this(WorkThread.WORK, new HashMap(), HttpConstants.CONNECTION_TIME_OUT, 3);
    }

    public dkn(WorkThread workThread) {
        this(workThread, new HashMap(), HttpConstants.CONNECTION_TIME_OUT, 3);
    }

    public dkn(int i) {
        this(WorkThread.WORK, new HashMap(), i, 0);
    }

    public dkn(WorkThread workThread, Map<String, String> map, int i, int i2) {
        this.a = workThread;
        this.b = i;
        this.c = i2;
        this.d = map;
    }
}
