package com.autonavi.minimap.ajx3.platform.impl;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.ajx3.loader.AjxLoaderManager;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.IComponentMeasurement;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.IImgMeasurement;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.INativeFont;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.INativeImage;
import com.autonavi.minimap.ajx3.platform.ackor.IDeviceService.ITextMeasurement;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

public class DeviceServiceImpl extends IDeviceService {
    private Context mContext;
    private int mDeviceHeight = -1;
    private int mDeviceWidth = -1;
    private AjxLoaderManager mLoaderManager;

    public INativeFont createNativeFont(int i, int i2, boolean z) {
        return null;
    }

    public INativeImage createNativeImage(String str) {
        return null;
    }

    public void destroyNativeFont(INativeFont iNativeFont) {
    }

    public void destroyNativeImage(INativeImage iNativeImage) {
    }

    public DeviceServiceImpl(Context context, AjxLoaderManager ajxLoaderManager) {
        this.mContext = context;
        this.mLoaderManager = ajxLoaderManager;
    }

    public ITextMeasurement createTextMeasurement() {
        return new TextMeasurement(this.mContext);
    }

    public IImgMeasurement createImgMeasurement() {
        return new ImgMeasurement(this.mContext, this.mLoaderManager);
    }

    public IComponentMeasurement createComponentMeasurement() {
        return new ComponentMeasurement();
    }

    private void readDeviceDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
        this.mDeviceWidth = displayMetrics.widthPixels;
        this.mDeviceHeight = displayMetrics.heightPixels;
    }

    public int getDeviceWidth() {
        readDeviceDisplayMetrics();
        return this.mDeviceWidth;
    }

    public int getDeviceHeight() {
        readDeviceDisplayMetrics();
        return this.mDeviceHeight;
    }

    public int getDeviceOrientation() {
        if (this.mContext.getResources().getConfiguration().orientation == 2) {
            return 3;
        }
        return this.mContext.getResources().getConfiguration().orientation == 1 ? 1 : 1;
    }

    public float getDeviceDensisty() {
        return DimensionUtils.getDensisty();
    }
}
