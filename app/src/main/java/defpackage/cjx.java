package defpackage;

import android.content.res.Configuration;
import android.view.View;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.IViewLayerExt;
import com.autonavi.widget.ui.AlertView;

/* renamed from: cjx reason: default package */
/* compiled from: AlertViewWrapper */
public final class cjx implements IViewLayer, IViewLayerExt {
    public boolean a = true;
    private AlertView b;

    public cjx(AlertView alertView) {
        this.b = alertView;
    }

    public final View getView() {
        return this.b.getView();
    }

    public final void showBackground(boolean z) {
        this.b.showBackground(z);
    }

    public final boolean onBackPressed() {
        return this.b.onBackPressed();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        this.b.onConfigurationChanged(configuration);
    }

    public final boolean isDismiss() {
        return this.a;
    }
}
