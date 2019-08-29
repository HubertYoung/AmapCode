package com.autonavi.map.search.fragment;

import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

public final class BaseCQDetailOwner {
    public SearchCQDetailPage a;
    WeakReference<ViewGroup> b;
    WeakReference<View> c;
    WeakReference<View> d;
    WeakReference<View> e;

    public enum MiddleViewSlideType {
        None,
        Alpha,
        Alpha_Press,
        Dismiss
    }

    public BaseCQDetailOwner(SearchCQDetailPage searchCQDetailPage) {
        this.a = searchCQDetailPage;
    }

    public final ViewGroup a() {
        if (this.b != null) {
            return (ViewGroup) this.b.get();
        }
        return null;
    }

    public final View b() {
        if (this.c != null) {
            return (View) this.c.get();
        }
        return null;
    }

    public final View c() {
        if (this.d != null) {
            return (View) this.d.get();
        }
        return null;
    }

    public final View d() {
        if (this.e != null) {
            return (View) this.e.get();
        }
        return null;
    }
}
