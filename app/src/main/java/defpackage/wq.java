package defpackage;

import android.util.LongSparseArray;
import com.autonavi.jni.alc.inter.IALCCloudStrategy;
import com.autonavi.jni.alc.inter.IALCNetwork;
import com.autonavi.jni.alc.inter.IALCRecordNetwork;

/* renamed from: wq reason: default package */
/* compiled from: LogConfig */
public final class wq {
    /* access modifiers changed from: private */
    public static int n = 153600;
    /* access modifiers changed from: private */
    public static int o = 20;
    public boolean a = false;
    public String b;
    public String c;
    public int d;
    public int e;
    public int f = 0;
    public long g = 0;
    public int h = 7;
    public IALCNetwork i;
    public IALCRecordNetwork j;
    public IALCCloudStrategy k;
    public int l = 3;
    public LongSparseArray m = new LongSparseArray();

    /* renamed from: wq$a */
    /* compiled from: LogConfig */
    public static final class a {
        public wq a = new wq();

        public final a a(int i) {
            this.a.f = i;
            return this;
        }

        public final a a(long j) {
            this.a.g = j;
            return this;
        }

        public final a a(LongSparseArray longSparseArray) {
            this.a.m = longSparseArray;
            return this;
        }
    }
}
