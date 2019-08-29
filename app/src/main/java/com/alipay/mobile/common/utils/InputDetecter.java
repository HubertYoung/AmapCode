package com.alipay.mobile.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.alipay.android.hackbyte.ClassVerifier;

@SuppressLint({"NewApi"})
@TargetApi(9)
public class InputDetecter {
    public InputDetecter() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static boolean isAdbClick(int adbFeature) {
        return adbFeature == 1;
    }

    public static int getAdbClickFeature(View v, MotionEvent event) {
        boolean hasAxis;
        int iHasAxis;
        int iHasPressure;
        int i = 1;
        boolean hasPressure = true;
        boolean isVDevId = false;
        try {
            event.getDevice().getMotionRange(4).getRange();
            hasAxis = true;
        } catch (Exception e) {
            hasAxis = false;
        }
        try {
            float pressure = event.getPressure();
            if (Float.compare(pressure * pressure, 0.0f) == 0 || Float.compare(pressure * pressure, 1.0f) == 0) {
                hasPressure = false;
            } else {
                hasPressure = true;
            }
        } catch (Exception e2) {
        }
        try {
            if (event.getDeviceId() == 0 || event.getDeviceId() == -1) {
                isVDevId = true;
            } else {
                isVDevId = false;
            }
        } catch (Exception e3) {
        }
        if (hasAxis) {
            iHasAxis = 1;
        } else {
            iHasAxis = 0;
        }
        if (hasPressure) {
            iHasPressure = 1;
        } else {
            iHasPressure = 0;
        }
        if (!isVDevId) {
            i = 0;
        }
        return i | (iHasPressure << 1) | (iHasAxis << 2);
    }
}
