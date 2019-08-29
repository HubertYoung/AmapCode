package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* renamed from: dtx reason: default package */
/* compiled from: DecodeThread */
public final class dtx extends Thread {
    private final dtz a;
    private final Map<DecodeHintType, Object> b = new EnumMap(DecodeHintType.class);
    private Handler c;
    private final CountDownLatch d = new CountDownLatch(1);

    public dtx(dtz dtz, Collection<BarcodeFormat> collection, Map<DecodeHintType, ?> map, String str, ResultPointCallback resultPointCallback) {
        this.a = dtz;
        if (map != null) {
            this.b.putAll(map);
        }
        if (collection == null || collection.isEmpty()) {
            collection = EnumSet.noneOf(BarcodeFormat.class);
            a(collection);
        }
        this.b.put(DecodeHintType.POSSIBLE_FORMATS, collection);
        if (str != null) {
            this.b.put(DecodeHintType.CHARACTER_SET, str);
        }
        this.b.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
    }

    public static void a(Collection<BarcodeFormat> collection) {
        collection.addAll(dtu.c);
        collection.addAll(dtu.d);
        collection.addAll(dtu.a);
        collection.addAll(dtu.b);
    }

    public final Handler a() {
        try {
            this.d.await();
        } catch (InterruptedException unused) {
        }
        return this.c;
    }

    public final void run() {
        Looper.prepare();
        this.c = new dtv(this.a, this.b);
        this.d.countDown();
        Looper.loop();
    }
}
