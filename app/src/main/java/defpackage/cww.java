package defpackage;

import android.view.View;
import android.view.ViewGroup.OnHierarchyChangeListener;

/* renamed from: cww reason: default package */
/* compiled from: StatisticOnHierarchyChangeListener */
public abstract class cww implements OnHierarchyChangeListener, d {
    public void onChildViewAdded(View view, View view2) {
        a(view, view2);
    }

    public void onChildViewRemoved(View view, View view2) {
        b(view, view2);
    }
}
