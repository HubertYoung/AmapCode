package com.alipay.mobile.base.textsize;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.textsize.SizeUtil;
import com.alipay.mobile.framework.service.textsize.TextSizeService;

public class TextSizeServiceImpl extends TextSizeService {
    private static final String SP_GEAR = "gear";
    private static final String SP_NAME = "TextSize";
    private static final String TAG = "TextSizeServiceImpl";
    private static int mGear = -1;
    private Editor editor;
    private SharedPreferences mSharedPreferences;

    public void setSizeGear(int gear) {
        if (gear < 0 || gear > 5) {
            LoggerFactory.getTraceLogger().info(TAG, "gear is not correct = " + gear);
            return;
        }
        mGear = gear;
        this.editor.putInt(SP_GEAR, gear);
        this.editor.commit();
    }

    public int getSizeGear() {
        if (mGear == -1) {
            mGear = this.mSharedPreferences.getInt(SP_GEAR, 1);
        }
        return mGear;
    }

    public float transformSize(float size) {
        return SizeUtil.getTextSize(size, mGear);
    }

    public float getScaleByGear(int gear) {
        return SizeUtil.getScale(gear);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.mSharedPreferences = getMicroApplicationContext().getApplicationContext().getSharedPreferences(SP_NAME, 0);
        this.editor = this.mSharedPreferences.edit();
        mGear = this.mSharedPreferences.getInt(SP_GEAR, 1);
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
