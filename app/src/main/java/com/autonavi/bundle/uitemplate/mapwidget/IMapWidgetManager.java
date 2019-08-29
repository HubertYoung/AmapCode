package com.autonavi.bundle.uitemplate.mapwidget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.bundle.uitemplate.api.IAMapActivityHost;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPageHost;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public interface IMapWidgetManager extends IAMapActivityHost, IMapWidgetManagerService, IMapWidgetPageHost {

    public static class Stub {
        private static IMapWidgetManager mMapWidgetManager = ((IMapWidgetManager) ank.a(IMapWidgetManager.class));

        public static IMapWidgetManager getMapWidgetManager() {
            return mMapWidgetManager;
        }

        public static String getCombineWidgetsTag(String... strArr) {
            if (strArr == null || strArr.length <= 0) {
                return null;
            }
            if (1 == strArr.length) {
                return strArr[0];
            }
            StringBuilder sb = new StringBuilder();
            for (String append : strArr) {
                sb.append("_");
                sb.append(append);
            }
            return sb.insert(0, WidgetType.COMBINE).toString();
        }
    }

    ViewGroup getRootView();

    void isWidgetsDispatchEvent(boolean z, IWidgetProperty... iWidgetPropertyArr);

    void removeHeaderView();

    void removeMsgStateBarView();

    void setABHomePage(boolean z);

    void setFullScreen(boolean z);

    void setHeaderView(View view, LayoutParams layoutParams);

    void setMsgStateBarView(View view, LayoutParams layoutParams);

    void setOnFooterChangeListener(OnFooterChangeListener onFooterChangeListener);
}
