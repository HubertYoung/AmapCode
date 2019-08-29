package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.bundle.routecommon.model.IRouteBusLineResult;
import com.autonavi.common.Callback;

/* renamed from: ata reason: default package */
/* compiled from: IBusLineRequest */
public interface ata {
    AosRequest a(String str, String str2, Callback<IRouteBusLineResult> callback);

    AosRequest b(String str, String str2, Callback<IRouteBusLineResult> callback);
}
