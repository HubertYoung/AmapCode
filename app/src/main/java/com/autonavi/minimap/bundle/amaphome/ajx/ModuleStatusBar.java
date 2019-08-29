package com.autonavi.minimap.bundle.amaphome.ajx;

import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.minimap.statusbar.StatusBarManager.FeatureType;

@AjxModule("statusBarTip")
public class ModuleStatusBar extends AbstractModule {
    private static final String TAG = "ModuleStatusBar";
    private JsFunctionCallback statusBarOnClickListener;

    public ModuleStatusBar(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("showStatusBarTip")
    public void showStatusBarTip(final String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        FeatureType featureType = FeatureType.TYPE_TAXI;
        if (featureType != null) {
            StatusBarManager.d().a(featureType, str2, new OnClickListener() {
                public final void onClick(View view) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(str);
                    }
                }
            });
        }
    }

    @AjxMethod("hideStatusBarTip")
    public void hideStatusBarTip(String str) {
        FeatureType featureType = FeatureType.TYPE_TAXI;
        if (featureType != null) {
            StatusBarManager.d().a(featureType);
            if (this.statusBarOnClickListener != null) {
                this.statusBarOnClickListener = null;
            }
        }
    }

    private FeatureType getFeatureByType(int i) {
        if (i == FeatureType.TYPE_TAXI.ordinal()) {
            return FeatureType.TYPE_TAXI;
        }
        if (i == FeatureType.TYPE_BICYCLE.ordinal()) {
            return FeatureType.TYPE_BICYCLE;
        }
        if (i == FeatureType.TYPE_FREERIDE.ordinal()) {
            return FeatureType.TYPE_FREERIDE;
        }
        if (i == FeatureType.TYPE_GROUP.ordinal()) {
            return FeatureType.TYPE_GROUP;
        }
        AMapLog.e(TAG, "unsupport FeatureType");
        return null;
    }
}
