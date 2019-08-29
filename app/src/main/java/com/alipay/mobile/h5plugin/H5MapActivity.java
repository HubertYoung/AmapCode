package com.alipay.mobile.h5plugin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.service.MapService;
import com.alipay.mobile.map.model.LatLonPointEx;
import com.alipay.mobile.map.widget.APMapView;
import com.alipay.mobile.nebula.util.H5Log;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public class H5MapActivity extends BaseActivity {
    public static final String TAG = "H5MapActivity";
    private APMapView apMapView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_h5map);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        MapService mapService = (MapService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MapService.class.getName());
        if (mapService == null) {
            H5Log.d("H5MapActivity", "mapService == null");
            return;
        }
        Intent intent = getIntent();
        if (intent == null) {
            H5Log.d("H5MapActivity", "intent == null");
            return;
        }
        this.apMapView = mapService.getMapView(this);
        if (this.apMapView == null) {
            H5Log.d("H5MapActivity", "apMapView == null");
            return;
        }
        try {
            this.apMapView.onCreateView(savedInstanceState);
            container.addView(this.apMapView, new LayoutParams(-1, -1));
            double longitude = intent.getDoubleExtra("longitude", 0.0d);
            double latitude = intent.getDoubleExtra("latitude", 0.0d);
            int scale = (int) intent.getDoubleExtra(WidgetType.SCALE, 17.0d);
            String name = intent.getStringExtra("name");
            String address = intent.getStringExtra("address");
            this.apMapView.setHiddenInfo(intent.getStringExtra("hidden"));
            LatLonPointEx point = new LatLonPointEx(latitude, longitude);
            if (!TextUtils.isEmpty(name)) {
                point.setTitle(name);
            }
            point.setSnippet(address);
            this.apMapView.showPointEx(point, scale);
            H5Log.d("H5MapActivity", "onCreate = " + intent.toUri(1));
        } catch (Throwable e) {
            H5Log.e((String) "H5MapActivity", "open H5MapActivity fail: " + Log.getStackTraceString(e));
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.apMapView != null) {
            this.apMapView.onDestroyView();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.apMapView != null) {
            this.apMapView.onResumeView();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.apMapView != null) {
            this.apMapView.onPauseView();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.apMapView != null) {
            this.apMapView.onSaveInstance(outState);
        }
    }
}
