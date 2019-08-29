package com.autonavi.minimap.nativesupport.platform;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ackor.ackorplatform.IDeviceService;
import com.autonavi.minimap.ackor.ackorplatform.IDeviceService.INativeFont;

public class DeviceServiceImpl extends IDeviceService {
    private int mDeviceHeight = -1;
    private int mDeviceWidth = -1;

    public void destroyNativeFont(INativeFont iNativeFont) {
    }

    public INativeFont createNativeFont(int i, int i2) {
        return new NativeFont(i, i2);
    }

    private void readDeviceDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) AMapAppGlobal.getApplication().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
        this.mDeviceWidth = displayMetrics.widthPixels;
        this.mDeviceHeight = displayMetrics.heightPixels;
    }

    public int getDeviceWidth() {
        if (this.mDeviceWidth < 0) {
            readDeviceDisplayMetrics();
        }
        return this.mDeviceWidth;
    }

    public int getDeviceHeight() {
        if (this.mDeviceHeight < 0) {
            readDeviceDisplayMetrics();
        }
        return this.mDeviceHeight;
    }

    public int getDeviceOrientation() {
        if (AMapAppGlobal.getApplication().getResources().getConfiguration().orientation == 2) {
            return 3;
        }
        return AMapAppGlobal.getApplication().getResources().getConfiguration().orientation == 1 ? 1 : 1;
    }
}
