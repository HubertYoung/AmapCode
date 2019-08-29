package defpackage;

import android.text.TextUtils;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.minimap.drive.errorreport.RouteCarErrorReportDialog;
import java.io.File;

/* renamed from: dfb reason: default package */
/* compiled from: RouteCarErrorReportPresenter */
public final class dfb extends sw<RouteCarErrorReportDialog, dfa> {
    public dfb(RouteCarErrorReportDialog routeCarErrorReportDialog) {
        super(routeCarErrorReportDialog);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        RouteCarErrorReportDialog routeCarErrorReportDialog = (RouteCarErrorReportDialog) this.mPage;
        if (routeCarErrorReportDialog.getContentView() != null) {
            routeCarErrorReportDialog.getContentView().setBackgroundColor(NodeAlertDialogPage.DIALOG_BG_COLOR);
            routeCarErrorReportDialog.getContentView().setOnTouchListener(routeCarErrorReportDialog);
            routeCarErrorReportDialog.requestScreenOrientation(1);
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        return RouteCarErrorReportDialog.a();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
        RouteCarErrorReportDialog routeCarErrorReportDialog = (RouteCarErrorReportDialog) this.mPage;
        if (!TextUtils.isEmpty(routeCarErrorReportDialog.a)) {
            File file = new File(routeCarErrorReportDialog.a);
            if (file.exists() && file.delete()) {
                routeCarErrorReportDialog.a = null;
            }
        }
    }

    public final /* synthetic */ su a() {
        return new dfa(this);
    }
}
