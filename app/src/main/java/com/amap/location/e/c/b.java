package com.amap.location.e.c;

import android.content.Context;
import android.os.Handler;
import com.amap.location.common.model.CellState;

/* compiled from: CellAgeEstimator */
public final class b extends a<CellState> {
    public b(Context context, String str, Handler handler) {
        super(context, str, handler);
    }

    /* renamed from: a */
    public final String b(CellState cellState) {
        return cellState == null ? "" : cellState.getKey();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final int c(CellState cellState) {
        if (cellState == null) {
            return 99;
        }
        return cellState.signalStrength;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public final long d(CellState cellState) {
        if (cellState == null) {
            return 0;
        }
        return cellState.lastUpdateUtcMills;
    }

    /* access modifiers changed from: 0000 */
    public final void a(CellState cellState, long j) {
        if (cellState != null) {
            cellState.lastUpdateUtcMills = j;
        }
    }
}
