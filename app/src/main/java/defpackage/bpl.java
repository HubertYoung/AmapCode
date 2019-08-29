package defpackage;

import com.autonavi.core.network.inter.response.ResponseException;
import defpackage.bpk;

/* renamed from: bpl reason: default package */
/* compiled from: ResponseCallback */
public interface bpl<T extends bpk> {
    void onFailure(bph bph, ResponseException responseException);

    void onSuccess(T t);
}
