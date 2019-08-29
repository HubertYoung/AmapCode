package defpackage;

import android.view.ViewGroup;

/* renamed from: cdq reason: default package */
/* compiled from: IFloorManager */
public interface cdq extends a, b, e {
    ami a();

    cds a(int i);

    void a(ViewGroup viewGroup);

    void a(cdc cdc);

    void a(cdp cdp);

    void a(String str);

    void a(String str, int i, boolean z);

    void a(String str, String str2, boolean z);

    void a(boolean z);

    ami b();

    void b(int i);

    void b(cdp cdp);

    void b(String str);

    boolean c();

    cds d();

    boolean e();

    boolean onIndoorBuildingActive(ami ami);

    void onResetViewState();

    void updateStateWhenCompassPaint();
}
