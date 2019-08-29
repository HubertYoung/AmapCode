package com.autonavi.miniapp.plugin.lbs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5plugin.H5LocationPlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public class H5Location {
    public static final int DEFAULT_TIMEOUT = 10000;
    public static final String REQUEST_TYPE = "requestType";
    public static final int REQUEST_TYPE_COORDINATE_ONLY = 0;
    public static final int REQUEST_TYPE_REVERSE = 1;
    public static final int REQUEST_TYPE_REVERSE_POI = 3;
    public static final int REQUEST_TYPE_REVERSE_STREET = 2;
    public static final String TAG = "H5LocationPlugin";

    public void openLocation(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        H5Log.d("H5LocationPlugin", H5LocationPlugin.OPEN_LOCATION);
        if (h5Event == null) {
            H5Log.d("H5LocationPlugin", "openLocation event == null");
            return;
        }
        try {
            JSONObject param = h5Event.getParam();
            if (param == null) {
                H5Log.d("H5LocationPlugin", "openLocation param == null");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("latitude", param.getDoubleValue("latitude"));
            intent.putExtra("longitude", param.getDoubleValue("longitude"));
            intent.putExtra(WidgetType.SCALE, param.getDoubleValue(WidgetType.SCALE));
            intent.putExtra("name", H5Utils.getString(param, (String) "name", (String) ""));
            intent.putExtra("address", H5Utils.getString(param, (String) "address", (String) ""));
            intent.putExtra("hidden", H5Utils.getString(param, (String) "hidden", (String) ""));
            intent.setClass(LauncherApplicationAgent.getInstance().getApplicationContext(), H5MapActivity.class);
            MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            if (microApplicationContext.getTopApplication() != null) {
                microApplicationContext.startActivity((MicroApplication) microApplicationContext.getTopApplication(), intent);
                return;
            }
            Context context = (Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
            if (context == null) {
                context = LauncherApplicationAgent.getInstance().getApplicationContext();
                intent.setFlags(268435456);
            }
            if (context != null) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
            H5Log.e("H5LocationPlugin", "openLocation exception.", e);
        }
    }

    public void openNavi(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event == null) {
            H5Log.d("H5LocationPlugin", "openNavi event == null");
            return;
        }
        try {
            JSONObject param = h5Event.getParam();
            if (param == null) {
                H5Log.d("H5LocationPlugin", "openNavi param == null");
                return;
            }
            double doubleValue = param.getDoubleValue("latitude");
            double doubleValue2 = param.getDoubleValue("longitude");
            if (doubleValue > 0.0d) {
                if (doubleValue2 > 0.0d) {
                    cgz.a((Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext());
                    StringBuilder sb = new StringBuilder("amapuri://drive/navi?sourceApplication=miniapp&lat=");
                    sb.append(doubleValue);
                    sb.append("&lon=");
                    sb.append(doubleValue2);
                    sb.append("&dev=1&style=2");
                    String sb2 = sb.toString();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setAction("com.autonavi.minimap.ACTION");
                    intent.setData(Uri.parse(sb2));
                    AMapPageUtil.getPageContext().startScheme(intent);
                }
            }
        } catch (Exception e) {
            H5Log.e("H5LocationPlugin", "openLocation exception.", e);
        }
    }

    public void getCurrentLocation(H5Event h5Event, H5BridgeContext h5BridgeContext, LocationListener locationListener, long j, SimpleLocationCache simpleLocationCache) {
        H5GetCurrentLocation h5GetCurrentLocation = new H5GetCurrentLocation(h5Event, h5BridgeContext, locationListener, j, simpleLocationCache);
        h5GetCurrentLocation.getCurrentLocation();
    }
}
