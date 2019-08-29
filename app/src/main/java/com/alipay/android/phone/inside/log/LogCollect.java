package com.alipay.android.phone.inside.log;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.biz.ContextManager;
import com.alipay.android.phone.inside.log.cons.Constants;
import com.alipay.android.phone.inside.log.field.AbstractLogField;
import com.alipay.android.phone.inside.log.field.ApkField;
import com.alipay.android.phone.inside.log.field.BehaviorField;
import com.alipay.android.phone.inside.log.field.BussinessField;
import com.alipay.android.phone.inside.log.field.DeviceField;
import com.alipay.android.phone.inside.log.field.EnvField;
import com.alipay.android.phone.inside.log.field.ExceptionField;
import com.alipay.android.phone.inside.log.field.HeaderField;
import com.alipay.android.phone.inside.log.field.PerfField;
import com.alipay.android.phone.inside.log.util.DateUtil;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogCollect {
    static LogCollect a;
    private ConcurrentLinkedQueue<Object> b = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<BehaviorField> c = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<PerfField> d = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<ExceptionField> e = new ConcurrentLinkedQueue<>();
    private DeviceField f;
    private ApkField g;
    private EnvField h;
    private BussinessField i;
    private ExecutorService j = Executors.newFixedThreadPool(5);
    private String k = DateUtil.a();

    public static LogCollect a() {
        if (a == null) {
            a = new LogCollect();
        }
        return a;
    }

    public final void b() {
        this.k = DateUtil.a();
        this.j.execute(new Runnable() {
            public void run() {
                LogCollect.d();
            }
        });
    }

    private static String a(AbstractLogField abstractLogField) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.c[0]);
        sb.append(abstractLogField.a());
        sb.append(Constants.d[0]);
        return sb.toString();
    }

    private static <T extends AbstractLogField> String a(ConcurrentLinkedQueue<T> concurrentLinkedQueue) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.c[0]);
        ArrayList arrayList = new ArrayList();
        if (concurrentLinkedQueue != null && concurrentLinkedQueue.size() > 0) {
            arrayList.addAll(concurrentLinkedQueue);
        }
        if (arrayList.size() <= 0) {
            sb.append("-");
        } else {
            for (int i2 = 0; i2 < arrayList.size() - 1; i2++) {
                sb.append(((AbstractLogField) arrayList.get(i2)).a());
                sb.append(Constants.f[0]);
            }
            sb.append(((AbstractLogField) arrayList.get(arrayList.size() - 1)).a());
        }
        sb.append(Constants.d[0]);
        return sb.toString();
    }

    public final void a(BehaviorField behaviorField) {
        try {
            this.c.add(behaviorField);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    public final void a(PerfField perfField) {
        try {
            this.d.add(perfField);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    public final void a(ExceptionField exceptionField) {
        try {
            this.e.add(exceptionField);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    public final void c() {
        if (this.c.size() == 0 && this.e.size() == 0 && this.d.size() == 0 && this.b.size() == 0) {
            LoggerFactory.f().b((String) "inside", (String) "LogCollect::submitCache > log empty, return");
            return;
        }
        HeaderField headerField = new HeaderField();
        headerField.a(this.k);
        String a2 = a((AbstractLogField) headerField);
        if (this.f == null) {
            this.f = new DeviceField();
        }
        String a3 = a((AbstractLogField) this.f);
        if (this.g == null) {
            this.g = new ApkField();
        }
        String a4 = a((AbstractLogField) this.g);
        if (this.h == null) {
            this.h = new EnvField();
        }
        String a5 = a((AbstractLogField) this.h);
        if (this.i == null) {
            this.i = new BussinessField();
        }
        String a6 = a((AbstractLogField) this.i);
        String a7 = a(this.c);
        String a8 = a(this.e);
        String a9 = a(this.d);
        String a10 = a(this.b);
        this.c.clear();
        this.e.clear();
        this.d.clear();
        this.b.clear();
        String[] strArr = {a2, a3, a4, a5, a6, a7, a8, a9, a10};
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.a[0]);
        for (int i2 = 0; i2 < 8; i2++) {
            sb.append(strArr[i2]);
            sb.append(Constants.e[0]);
        }
        sb.append(strArr[8]);
        sb.append(Constants.b[0]);
        final String sb2 = sb.toString();
        this.j.execute(new Runnable() {
            public void run() {
                try {
                    LogCollect.a(sb2);
                } catch (Throwable th) {
                    LoggerFactory.f().c((String) "inside", th);
                }
            }
        });
    }

    static /* synthetic */ void d() {
        try {
            new LogUploader(ContextManager.a().getContext()).a();
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    static /* synthetic */ void a(String str) {
        if (str.endsWith("(-),(-),(-),(-)]")) {
            LoggerFactory.f().b((String) "inside", "LogUploader::submitCache > ignore: ".concat(String.valueOf(str)));
        } else {
            new LogUploader(ContextManager.a().getContext()).a(str);
        }
    }
}
