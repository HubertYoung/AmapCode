package com.autonavi.map.search.comment.common.net;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import com.autonavi.map.widget.ProgressDlg;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.xidea.el.impl.ReflectUtil;

public class JsonParserCallback<T> implements AosResponseCallbackOnUi<AosByteResponse> {
    private bvz<T> a;
    private WeakReference<Callback<T>> b;
    private ProgressDlg c;

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        this.a.parser((byte[]) ((AosByteResponse) aosResponse).getResult());
        if (this.a.result) {
            a(this.c);
            Callback callback = (Callback) this.b.get();
            if (callback != null) {
                callback.callback(this.a.a);
            }
            return;
        }
        a(this.c);
        Callback callback2 = (Callback) this.b.get();
        if (callback2 != null) {
            callback2.error(new Exception(this.a.errorMessage), true);
        }
    }

    public JsonParserCallback(@NonNull WeakReference<Callback<T>> weakReference, ProgressDlg progressDlg) {
        Callback callback = (Callback) weakReference.get();
        if (callback == null) {
            throw new IllegalArgumentException("parameter callback is null");
        }
        this.b = weakReference;
        Type a2 = ReflectUtil.a((Type) callback.getClass(), Callback.class);
        if (a2 instanceof ParameterizedType) {
            a2 = ((ParameterizedType) a2).getRawType();
        } else if (a2 instanceof TypeVariable) {
            StringBuilder sb = new StringBuilder("not support callback type");
            sb.append(callback.getClass().getCanonicalName());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = new bvz<>((Class) a2);
        this.c = progressDlg;
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        a(this.c);
        Callback callback = (Callback) this.b.get();
        if (callback != null) {
            callback.error(new NetworkErrorException("请检查网络后重试!"), false);
        }
    }

    private static void a(ProgressDlg progressDlg) {
        adz adz = (adz) a.a.a(adz.class);
        if (adz != null) {
            adz.b().a();
            if (progressDlg != null) {
                progressDlg.dismiss();
            }
        }
    }
}
