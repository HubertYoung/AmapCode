package com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn;

import android.content.Context;
import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class AutoRemoteMapWidget extends AbstractMapWidget<AutoRemoteWidgetPresenter> {
    private View mAutoRemoteTipView;
    private View mAutoRemoteView;

    public AutoRemoteMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        AutoRemoteView createAutoRemoteView = createAutoRemoteView(context);
        this.mAutoRemoteView = createAutoRemoteView.getAutoRemoteView();
        this.mAutoRemoteTipView = createAutoRemoteView.getAutoRemoteTip();
        return createAutoRemoteView;
    }

    /* access modifiers changed from: protected */
    public AutoRemoteView createAutoRemoteView(Context context) {
        return new AutoRemoteView(context);
    }

    public void onInit(Context context) {
        if (getPresenter() != null) {
            setVisibility(this.mAutoRemoteView, 8);
            ((AutoRemoteWidgetPresenter) getPresenter()).updateIconVisible();
        }
    }

    public View getAutoRemoteTipView() {
        return this.mAutoRemoteTipView;
    }

    public View getAutoRemoteView() {
        return this.mAutoRemoteView;
    }

    public void setVisibility(View view, int i) {
        if (view != null) {
            if (view == this.mAutoRemoteView) {
                logUpdate();
            }
            view.setVisibility(i);
        }
    }

    private void logUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", "车机");
        } catch (JSONException unused) {
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_SMART_TIPS_SHOW, jSONObject);
    }
}
