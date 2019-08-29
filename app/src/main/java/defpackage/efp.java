package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.run.page.RunningHistoryPage;

/* renamed from: efp reason: default package */
/* compiled from: RunningHistoryPresenter */
public final class efp extends eaf<RunningHistoryPage> {
    public efp(RunningHistoryPage runningHistoryPage) {
        super(runningHistoryPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onResume() {
        super.onResume();
        ((RunningHistoryPage) this.mPage).a();
        RunningHistoryPage runningHistoryPage = (RunningHistoryPage) this.mPage;
        if (euk.a()) {
            euk.a(AMapAppGlobal.getTopActivity(), runningHistoryPage.getResources().getColor(R.color.c_12));
        }
        eft.a("performance-", "RunningHistoryPage  onResume");
    }
}
