package defpackage;

import android.annotation.TargetApi;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.State;

@TargetApi(9)
/* renamed from: eri reason: default package */
/* compiled from: OverscrollHelper */
public final class eri {

    /* renamed from: eri$1 reason: invalid class name */
    /* compiled from: OverscrollHelper */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Orientation.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation[] r0 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation r1 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.widget.pulltorefresh.PullToRefreshBase$Orientation r1 = com.autonavi.widget.pulltorefresh.PullToRefreshBase.Orientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.eri.AnonymousClass1.<clinit>():void");
        }
    }

    public static void a(PullToRefreshBase<?> pullToRefreshBase, int i, int i2, int i3, int i4, boolean z) {
        a(pullToRefreshBase, i, i2, i3, i4, 0, z);
    }

    public static void a(PullToRefreshBase<?> pullToRefreshBase, int i, int i2, int i3, int i4, int i5, boolean z) {
        a(pullToRefreshBase, i, i2, i3, i4, i5, 0, 1.0f, z);
    }

    public static void a(PullToRefreshBase<?> pullToRefreshBase, int i, int i2, int i3, int i4, int i5, int i6, float f, boolean z) {
        int i7;
        if (AnonymousClass1.a[pullToRefreshBase.getPullToRefreshScrollDirection().ordinal()] != 1) {
            i2 = i4;
            int i8 = i3;
            i7 = pullToRefreshBase.getScrollY();
            i = i8;
        } else {
            i7 = pullToRefreshBase.getScrollX();
        }
        if (pullToRefreshBase.isPullToRefreshOverScrollEnabled() && !pullToRefreshBase.isRefreshing()) {
            Mode mode = pullToRefreshBase.getMode();
            if (mode.permitsPullToRefresh() && !z && i != 0) {
                int i9 = i + i2;
                if (i9 < 0 - i6) {
                    if (mode.showHeaderLoadingLayout()) {
                        if (i7 == 0) {
                            pullToRefreshBase.setState(State.OVERSCROLLING, new boolean[0]);
                        }
                        pullToRefreshBase.setHeaderScroll((int) (f * ((float) (i7 + i9))));
                    }
                } else if (i9 > i5 + i6) {
                    if (mode.showFooterLoadingLayout()) {
                        if (i7 == 0) {
                            pullToRefreshBase.setState(State.OVERSCROLLING, new boolean[0]);
                        }
                        pullToRefreshBase.setHeaderScroll((int) (f * ((float) ((i7 + i9) - i5))));
                    }
                } else if (Math.abs(i9) <= i6 || Math.abs(i9 - i5) <= i6) {
                    pullToRefreshBase.setState(State.RESET, new boolean[0]);
                }
            } else if (z && State.OVERSCROLLING == pullToRefreshBase.getState()) {
                pullToRefreshBase.setState(State.RESET, new boolean[0]);
            }
        }
    }
}
