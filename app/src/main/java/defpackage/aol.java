package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.accountopenauth.AFastOAuthModel;
import com.alipay.android.phone.inside.api.model.accountopenauth.AOAuthModel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.widget.ui.BalloonLayout;

/* renamed from: aol reason: default package */
/* compiled from: NewAlipayHandlerHelper */
public final class aol {
    private static aol f = new aol();
    public a a;
    public final Object b = new Object();
    public boolean c = false;
    public long d;
    public long e;
    /* access modifiers changed from: private */
    public Handler g = new Handler(Looper.getMainLooper());
    private long h;

    /* renamed from: aol$a */
    /* compiled from: NewAlipayHandlerHelper */
    public interface a {
        void a();

        void a(boolean z, aoh aoh);
    }

    private aol() {
    }

    public static aol a() {
        return f;
    }

    public final boolean a(long j) {
        boolean z;
        synchronized (this.b) {
            long currentTimeMillis = System.currentTimeMillis() - this.e;
            z = this.d == j && currentTimeMillis < 2000;
            StringBuilder sb = new StringBuilder("shouldShow, tid: ");
            sb.append(j);
            sb.append(", this.authTid: ");
            sb.append(this.d);
            sb.append(", timeDiff: ");
            sb.append(currentTimeMillis);
            sb.append(", shouldShow: ");
            sb.append(z);
            AMapLog.d("accountTAG", sb.toString());
        }
        return z;
    }

    public final void a(String str, String str2, String str3, boolean z, boolean z2, a aVar) {
        if (!z && !z2) {
            if (SystemClock.elapsedRealtime() - this.h < BalloonLayout.DEFAULT_DISPLAY_DURATION) {
                aVar.a();
                return;
            }
            this.h = SystemClock.elapsedRealtime();
        }
        synchronized (this.b) {
            this.d = -1;
            this.e = 0;
            this.c = false;
        }
        b();
        this.a = aVar;
        final boolean z3 = z;
        final boolean z4 = z2;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        AnonymousClass1 r0 = new Runnable() {
            public final void run() {
                synchronized (aol.this.b) {
                    aol.this.c = !z3 && z4;
                    if (aol.this.c) {
                        aol.this.e = System.currentTimeMillis();
                    }
                    aol.this.d = Thread.currentThread().getId();
                }
                final long c2 = aol.this.d;
                final OperationResult a2 = aol.b(str4, str5, str6, z3, z4, c2);
                AMapLog.d("accountTAG", "OperationResult, result: ".concat(String.valueOf(a2)));
                aol.this.g.post(new Runnable() {
                    public final void run() {
                        boolean z;
                        StringBuilder sb = new StringBuilder("currentTid: ");
                        sb.append(c2);
                        sb.append(", authTid: ");
                        sb.append(aol.this.d);
                        AMapLog.d("accountTAG", sb.toString());
                        synchronized (aol.this.b) {
                            z = c2 == aol.this.d;
                        }
                        if (z && aol.this.a != null) {
                            AMapLog.d("accountTAG", "about to notify");
                            aol.a(aol.this, a2, z3, z4);
                        }
                    }
                });
            }
        };
        new Thread(r0).start();
    }

    /* access modifiers changed from: private */
    public static OperationResult b(String str, String str2, String str3, boolean z, boolean z2, long j) {
        BaseOpenAuthModel baseOpenAuthModel;
        OperationResult operationResult;
        Throwable th;
        AMapLog.d("accountTAG", "authRunnable run().");
        if (z) {
            AOAuthModel aOAuthModel = new AOAuthModel();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("?");
            sb.append(str2);
            aOAuthModel.setAuthUrl(sb.toString());
            aOAuthModel.setPhoneNumber(str3);
            baseOpenAuthModel = aOAuthModel;
        } else {
            AFastOAuthModel aFastOAuthModel = new AFastOAuthModel();
            aFastOAuthModel.setAuthUrlAddress(str);
            aFastOAuthModel.setAuthUrlParams(str2);
            aFastOAuthModel.setPhoneNumber(str3);
            aFastOAuthModel.setRecommend(z2);
            aFastOAuthModel.setNeedShowFastAuthPage(z2);
            aFastOAuthModel.setAuthUUID(j);
            baseOpenAuthModel = aFastOAuthModel;
        }
        baseOpenAuthModel.setOpenAuthLogin(true);
        try {
            operationResult = InsideOperationService.getInstance().startAction((Context) AMapAppGlobal.getApplication(), (BaseModel<T>) baseOpenAuthModel);
            try {
                AMapLog.d("accountTAG", operationResult.toJsonString());
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            operationResult = null;
            th = th4;
            th.printStackTrace();
            return operationResult;
        }
        return operationResult;
    }

    private void a(boolean z, aoh aoh) {
        if (this.a != null) {
            this.a.a(z, aoh);
            this.a = null;
        }
    }

    private void b() {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
    }

    static /* synthetic */ void a(aol aol, OperationResult operationResult, boolean z, boolean z2) {
        aoh aoh = new aoh(operationResult);
        String str = aoh.a;
        AMapLog.d("accountTAG", "resultStatus: ".concat(String.valueOf(str)));
        if (!z && z2 && !TextUtils.equals(str, "afast_open_auth_6001")) {
            if (TextUtils.equals(str, "afast_open_auth_9000")) {
                AMapLog.d("accountTAG", "log alipay fast login succeed");
                LogManager.actionLogV2("P00464", "B002");
            } else if (TextUtils.equals(str, "afast_open_auth_8001")) {
                AMapLog.d("accountTAG", "log alipay fast login not show");
                LogManager.actionLogV2("P00464", "B001");
            } else if (TextUtils.equals(str, "afast_open_auth_6000")) {
                AMapLog.d("accountTAG", "log alipay fast login cancel");
                LogManager.actionLogV2("P00464", "B003");
            } else {
                AMapLog.d("accountTAG", "log alipay fast login fail");
                LogManager.actionLogV2("P00464", "B004");
            }
        }
        if (TextUtils.equals(str, "afast_open_auth_9000") || TextUtils.equals(str, "account_open_auth_9000")) {
            aol.a(true, aoh);
        } else if (TextUtils.equals(str, "afast_open_auth_6000") || TextUtils.equals(str, "afast_open_auth_6001") || TextUtils.equals(str, "afast_open_auth_8001") || TextUtils.equals(str, "account_open_auth_6000")) {
            aol.b();
        } else {
            if (TextUtils.equals(str, "account_open_auth_4002") || TextUtils.equals(str, "afast_open_auth_4002")) {
                ToastHelper.showToast("支付宝APP版本过低，无法授权，请更新");
            }
            aol.a(false, aoh);
        }
    }
}
