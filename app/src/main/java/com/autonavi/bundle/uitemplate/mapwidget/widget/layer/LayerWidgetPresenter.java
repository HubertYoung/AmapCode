package com.autonavi.bundle.uitemplate.mapwidget.widget.layer;

import android.view.View;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.inter.layer.ILayerStateListener;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class LayerWidgetPresenter extends BaseMapWidgetPresenter<MapLayerMapWidget> {
    private ILayerStateListener mLayerStateListener = new MainMapLayerStateListener();
    private LayerTipsHelper mLayerTipsHelper = new LayerTipsHelper();

    public static class LayerTipsHelper {
        private final String KEY_TIPS_SHOW_TIMES = "key_tips_show_times";
        private MapSharePreference msp = new MapSharePreference(SharePreferenceName.SharedPreferences);

        public void setTipsShowFlag(boolean z) {
            this.msp.edit().putBoolean("key_tips_show_times", z).apply();
        }

        public boolean isTipsShowed() {
            return this.msp.getBooleanValue("key_tips_show_times", false);
        }
    }

    static class MainMapLayerStateListener implements ILayerStateListener {
        public Object dismissTips() {
            return null;
        }

        public boolean isTipsShown() {
            return false;
        }

        public boolean showTips(AmapMessage amapMessage) {
            return false;
        }

        MainMapLayerStateListener() {
        }
    }

    public ILayerStateListener getLayerStateListener() {
        return this.mLayerStateListener;
    }

    public void commonListener(View view) {
        if (isWidgetNotNull()) {
            ((MapLayerMapWidget) this.mBindWidget).hideTip();
        }
    }

    public LayerTipsHelper getLayerTipsHelper() {
        return this.mLayerTipsHelper;
    }

    public static void logClick() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", bnv.d());
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
        LogManager.actionLogV2("P00001", "B008", jSONObject);
    }
}
