package com.autonavi.map.wallet;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import defpackage.cfs;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class WalletRequestCallback<T extends cfs> extends FalconAosPrepareResponseCallback<T> {
    public CompatDialog a;
    private T b;
    private Callback<T> c;

    public final /* synthetic */ void a(Object obj) {
        cfs cfs = (cfs) obj;
        a();
        if (cfs == null || cfs.a() == null) {
            if (!(this.c == null || cfs == null)) {
                this.c.callback(cfs);
            }
            return;
        }
        a((Throwable) cfs.a(), true);
    }

    public WalletRequestCallback(T t, Callback<T> callback) {
        this.b = t;
        this.c = callback;
    }

    private void a() {
        if (this.a != null) {
            this.a.dismiss();
            this.a = null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public T a(AosByteResponse aosByteResponse) {
        if (aosByteResponse.getResult() == null) {
            return null;
        }
        try {
            this.b.parser((byte[]) aosByteResponse.getResult());
            if (!this.b.isSuccessRequest()) {
                if (this.b.a() == null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            WalletRequestCallback.this.a((Throwable) null, true);
                        }
                    });
                    return null;
                }
            }
            return this.b;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new RuntimeException(e2.getMessage());
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        a();
        a((Throwable) aosResponseException, false);
    }

    /* access modifiers changed from: private */
    public void a(Throwable th, boolean z) {
        String errorDesc = this.b.getErrorDesc(this.b.errorCode);
        if (!TextUtils.isEmpty(errorDesc) && this.b != null && !(this.b instanceof cfv) && !(this.b instanceof cft)) {
            ToastHelper.showToast(errorDesc);
        }
        if (this.c != null) {
            this.c.error(th, z);
        }
    }
}
