package defpackage;

import android.content.Context;
import android.os.Looper;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;

/* renamed from: bvo reason: default package */
/* compiled from: FindHereCallback */
public final class bvo extends aea<InfoliteResult> {
    private InfoliteParam b;
    private Context c;
    private bwx d;

    public final void error(int i) {
    }

    public bvo(Context context, InfoliteParam infoliteParam, bwx bwx) {
        this.b = infoliteParam;
        this.c = context;
        this.d = bwx;
    }

    /* renamed from: a */
    public final void callback(final InfoliteResult infoliteResult) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            aho.a(new Runnable() {
                public final void run() {
                    bvo.this.callback(infoliteResult);
                }
            });
            return;
        }
        bct.a();
        if (!bcy.b(infoliteResult) || infoliteResult.searchInfo.l.isEmpty()) {
            ToastHelper.showLongToast(this.c.getString(R.string.ic_no_rect_search_result));
            return;
        }
        infoliteResult.mWrapper = this.b;
        infoliteResult.mKeyword = this.b.keywords;
        this.d.a(infoliteResult, infoliteResult.responseHeader.c == 1 || infoliteResult.responseHeader.c == 7, (TipItem) null);
        if (infoliteResult.responseHeader.f) {
            LogManager.actionLogV25(LogConstant.SEARCH_RESULT_LIST, "B060", new SimpleEntry("status", ccd.a(infoliteResult)), new SimpleEntry("text", bcy.k(infoliteResult)));
        }
    }
}
