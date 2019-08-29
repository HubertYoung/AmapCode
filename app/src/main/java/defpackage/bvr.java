package defpackage;

import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;

/* renamed from: bvr reason: default package */
/* compiled from: OnSearchResultListener */
public interface bvr {
    void a();

    void a(InfoliteResult infoliteResult);

    void a(InfoliteResult infoliteResult, TipItem tipItem, boolean z);

    void a(InfoliteResult infoliteResult, boolean z);

    void a(InfoliteResult infoliteResult, boolean z, TipItem tipItem);

    void a(String str);

    void b(InfoliteResult infoliteResult);

    void b(InfoliteResult infoliteResult, TipItem tipItem, boolean z);

    void b(InfoliteResult infoliteResult, boolean z, TipItem tipItem);

    void c(InfoliteResult infoliteResult);

    void c(InfoliteResult infoliteResult, TipItem tipItem, boolean z);
}
