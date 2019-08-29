package com.alipay.mobile.antui.basic;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import com.alipay.mobile.antui.basic.AUHorizontalListView.LayoutParams;
import com.alipay.mobile.antui.basic.AUHorizontalListView.RecyclerListener;
import java.util.ArrayList;

/* compiled from: AUHorizontalListView */
final class r {
    final /* synthetic */ AUHorizontalListView a;
    /* access modifiers changed from: private */
    public RecyclerListener b;
    private int c;
    private View[] d = new View[0];
    private ArrayList<View>[] e;
    private int f;
    private ArrayList<View> g;
    private SparseArrayCompat<View> h;

    r(AUHorizontalListView this$0) {
        this.a = this$0;
    }

    public final void a(int viewTypeCount) {
        if (viewTypeCount <= 0) {
            throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
        }
        ArrayList[] scrapViews = new ArrayList[viewTypeCount];
        for (int i = 0; i < viewTypeCount; i++) {
            scrapViews[i] = new ArrayList();
        }
        this.f = viewTypeCount;
        this.g = scrapViews[0];
        this.e = scrapViews;
    }

    public final void a() {
        if (this.f == 1) {
            ArrayList scrap = this.g;
            int scrapCount = scrap.size();
            for (int i = 0; i < scrapCount; i++) {
                scrap.get(i).forceLayout();
            }
        } else {
            int typeCount = this.f;
            for (int i2 = 0; i2 < typeCount; i2++) {
                ArrayList scrap2 = this.e[i2];
                int scrapCount2 = scrap2.size();
                for (int j = 0; j < scrapCount2; j++) {
                    scrap2.get(j).forceLayout();
                }
            }
        }
        if (this.h != null) {
            int count = this.h.size();
            for (int i3 = 0; i3 < count; i3++) {
                ((View) this.h.valueAt(i3)).forceLayout();
            }
        }
    }

    private static boolean e(int viewType) {
        return viewType >= 0;
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if (this.f == 1) {
            ArrayList scrap = this.g;
            int scrapCount = scrap.size();
            for (int i = 0; i < scrapCount; i++) {
                this.a.removeDetachedView(scrap.remove((scrapCount - 1) - i), false);
            }
        } else {
            int typeCount = this.f;
            for (int i2 = 0; i2 < typeCount; i2++) {
                ArrayList scrap2 = this.e[i2];
                int scrapCount2 = scrap2.size();
                for (int j = 0; j < scrapCount2; j++) {
                    this.a.removeDetachedView(scrap2.remove((scrapCount2 - 1) - j), false);
                }
            }
        }
        if (this.h != null) {
            this.h.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int childCount, int firstActivePosition) {
        if (this.d.length < childCount) {
            this.d = new View[childCount];
        }
        this.c = firstActivePosition;
        View[] activeViews = this.d;
        for (int i = 0; i < childCount; i++) {
            activeViews[i] = this.a.getChildAt(i);
        }
    }

    /* access modifiers changed from: 0000 */
    public final View b(int position) {
        int index = position - this.c;
        View[] activeViews = this.d;
        if (index < 0 || index >= activeViews.length) {
            return null;
        }
        View match = activeViews[index];
        activeViews[index] = null;
        return match;
    }

    /* access modifiers changed from: 0000 */
    public final View c(int position) {
        if (this.h == null) {
            return null;
        }
        int index = this.h.indexOfKey(position);
        if (index < 0) {
            return null;
        }
        View view = (View) this.h.valueAt(index);
        this.h.removeAt(index);
        return view;
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        if (this.h != null) {
            this.h.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public final View d(int position) {
        if (this.f == 1) {
            return a(this.g, position);
        }
        int whichScrap = this.a.mAdapter.getItemViewType(position);
        if (whichScrap < 0 || whichScrap >= this.e.length) {
            return null;
        }
        return a(this.e[whichScrap], position);
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(14)
    public final void a(View scrap, int position) {
        LayoutParams lp = (LayoutParams) scrap.getLayoutParams();
        if (lp != null) {
            lp.scrappedFromPosition = position;
            int viewType = lp.viewType;
            boolean scrapHasTransientState = ViewCompat.hasTransientState(scrap);
            if (e(viewType) && !scrapHasTransientState) {
                if (this.f == 1) {
                    this.g.add(scrap);
                } else {
                    this.e[viewType].add(scrap);
                }
                if (VERSION.SDK_INT >= 14) {
                    scrap.setAccessibilityDelegate(null);
                }
                if (this.b != null) {
                    this.b.onMovedToScrapHeap(scrap);
                }
            } else if (scrapHasTransientState) {
                if (this.h == null) {
                    this.h = new SparseArrayCompat<>();
                }
                this.h.put(position, scrap);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(14)
    public final void d() {
        boolean multipleScraps = true;
        View[] activeViews = this.d;
        if (this.f <= 1) {
            multipleScraps = false;
        }
        ArrayList scrapViews = this.g;
        for (int i = activeViews.length - 1; i >= 0; i--) {
            View victim = activeViews[i];
            if (victim != null) {
                LayoutParams lp = (LayoutParams) victim.getLayoutParams();
                int whichScrap = lp.viewType;
                activeViews[i] = null;
                boolean scrapHasTransientState = ViewCompat.hasTransientState(victim);
                if (e(whichScrap) && !scrapHasTransientState) {
                    if (multipleScraps) {
                        scrapViews = this.e[whichScrap];
                    }
                    lp.scrappedFromPosition = this.c + i;
                    scrapViews.add(victim);
                    if (VERSION.SDK_INT >= 14) {
                        victim.setAccessibilityDelegate(null);
                    }
                    if (this.b != null) {
                        this.b.onMovedToScrapHeap(victim);
                    }
                } else if (scrapHasTransientState) {
                    this.a.removeDetachedView(victim, false);
                    if (this.h == null) {
                        this.h = new SparseArrayCompat<>();
                    }
                    this.h.put(this.c + i, victim);
                }
            }
        }
        e();
    }

    private void e() {
        int maxViews = this.d.length;
        int viewTypeCount = this.f;
        ArrayList[] scrapViews = this.e;
        for (int i = 0; i < viewTypeCount; i++) {
            ArrayList scrapPile = scrapViews[i];
            int size = scrapPile.size();
            int extras = size - maxViews;
            int j = 0;
            int size2 = size - 1;
            while (j < extras) {
                this.a.removeDetachedView(scrapPile.remove(size2), false);
                j++;
                size2--;
            }
        }
        if (this.h != null) {
            int i2 = 0;
            while (i2 < this.h.size()) {
                if (!ViewCompat.hasTransientState((View) this.h.valueAt(i2))) {
                    this.h.removeAt(i2);
                    i2--;
                }
                i2++;
            }
        }
    }

    private static View a(ArrayList<View> scrapViews, int position) {
        int size = scrapViews.size();
        if (size <= 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            View scrapView = scrapViews.get(i);
            if (((LayoutParams) scrapView.getLayoutParams()).scrappedFromPosition == position) {
                scrapViews.remove(i);
                return scrapView;
            }
        }
        return scrapViews.remove(size - 1);
    }
}
