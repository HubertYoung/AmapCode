package com.autonavi.minimap.ajx3;

import android.content.Context;
import android.content.res.Configuration;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ajx3ViewLayer implements IViewLayer {
    private AtomicBoolean destroyed = new AtomicBoolean(false);
    private AmapAjxView mAjxView;

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void showBackground(boolean z) {
    }

    public Ajx3ViewLayer(Context context, String str, String str2, Object obj) {
        this.mAjxView = new AmapAjxView(context);
        this.mAjxView.load(str, obj, str2, "AJX_VIEW_LAYER");
    }

    public synchronized void destroy() {
        if (!this.destroyed.get()) {
            this.destroyed.set(true);
            this.mAjxView.onDestroy();
        }
    }

    public AmapAjxView getView() {
        return this.mAjxView;
    }

    public boolean onBackPressed() {
        if (this.destroyed.get() || this.mAjxView == null) {
            return false;
        }
        return this.mAjxView.backPressed();
    }
}
