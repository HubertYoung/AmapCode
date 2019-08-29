package com.autonavi.bundle.uitemplate.mapwidget.widget.layer;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer;
import com.autonavi.bundle.uitemplate.mapwidget.common.MapWidgetTip;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter.LayerTipsHelper;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class MapLayerMapWidget extends AbstractMapWidget<LayerWidgetPresenter> {
    private final String NOVICE_GUIDE_SHOW = "1";
    private MapWidgetTip mLayerTip;

    public MapLayerMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        View loadLayoutRes = loadLayoutRes(context, getLayoutId());
        loadLayoutRes.setContentDescription(context.getResources().getString(R.string.ctdes_text_layer_icon));
        this.mLayerTip = (MapWidgetTip) loadLayoutRes.findViewById(R.id.layer_tip);
        return loadLayoutRes;
    }

    public int getLayoutId() {
        return R.layout.map_widget_layer_layout;
    }

    private boolean isShowNoviceGuide() {
        return "1".equals(ModuleSlideContainer.getTipStateChange());
    }

    public void showTip() {
        if (!isShowNoviceGuide()) {
            final LayerTipsHelper layerTipsHelper = null;
            if (getPresenter() != null) {
                layerTipsHelper = ((LayerWidgetPresenter) getPresenter()).getLayerTipsHelper();
                if (layerTipsHelper != null && layerTipsHelper.isTipsShowed()) {
                    return;
                }
            }
            if (this.mLayerTip != null) {
                this.mLayerTip.setVisibility(0);
                logUpdate();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (layerTipsHelper != null) {
                            layerTipsHelper.setTipsShowFlag(true);
                        }
                        MapLayerMapWidget.this.hideTip();
                    }
                }, 5000);
            }
        }
    }

    private void logUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", "手绘地图");
        } catch (JSONException unused) {
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_SMART_TIPS_SHOW, jSONObject);
    }

    public void hideTip() {
        if (this.mLayerTip != null) {
            this.mLayerTip.setVisibility(8);
        }
    }
}
