package com.alipay.mobile.base.stepdetect.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class StepDetector implements SensorEventListener {
    private static int mTempCount = 0;
    private final Context context;
    private float mLastDiff = 0.0f;
    private float mLastDirections = 0.0f;
    private final float[] mLastExtremes = new float[2];
    private int mLastMatch = 0;
    private long mLastTime = 0;
    private float mLastValues = 0.0f;
    private final float mLimit = 5.0f;
    private final float[] mPreValues = new float[3];
    private int mSampleCount = 0;
    private final int mSampleSize = 5;
    private float mScale = 0.0f;
    private int mStepCount = 0;
    private final float mThreshold = 0.25f;
    private final float mYOffset;

    public StepDetector(Context context2) {
        this.context = context2;
        int windowHeight = ((WindowManager) context2.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getHeight();
        this.mYOffset = ((float) windowHeight) * 0.5f;
        this.mScale = -(((float) windowHeight) * 0.5f * 0.016666668f);
        mTempCount = 0;
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != 1) {
            return;
        }
        if (this.mSampleCount < 5) {
            for (int i = 0; i < 3; i++) {
                float[] fArr = this.mPreValues;
                fArr[i] = fArr[i] + (event.values[i] / 5.0f);
            }
            this.mSampleCount++;
        } else if (this.mSampleCount == 5) {
            float vSum = 0.0f;
            for (int i2 = 0; i2 < 3; i2++) {
                vSum += this.mYOffset + (this.mPreValues[i2] * this.mScale);
            }
            float speed = vSum / 3.0f;
            int i3 = speed > this.mLastValues ? 1 : speed < this.mLastValues ? -1 : 0;
            float direction = (float) i3;
            if (direction == (-this.mLastDirections)) {
                int extType = direction > 0.0f ? 0 : 1;
                this.mLastExtremes[extType] = this.mLastValues;
                float diff = Math.abs(this.mLastExtremes[extType] - this.mLastExtremes[1 - extType]);
                float accelationSquareRoot = ((((this.mPreValues[0] * this.mPreValues[0]) + (this.mPreValues[1] * this.mPreValues[1])) + (this.mPreValues[2] * this.mPreValues[2])) / 96.17039f) - 1.0f;
                long currentTime = System.currentTimeMillis();
                if (diff > 5.0f && currentTime - this.mLastTime > 300) {
                    boolean isAlmostAsLargeAsPrevious = diff > this.mLastDiff / 2.0f;
                    boolean isPreviousLargeEnough = this.mLastDiff > diff / 3.0f;
                    boolean isContra = this.mLastMatch == 1 - extType;
                    if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isContra && currentTime - this.mLastTime > 300 && (accelationSquareRoot < -0.25f || accelationSquareRoot > 0.25f)) {
                        this.mStepCount++;
                        mTempCount++;
                        this.mLastMatch = extType;
                        this.mLastTime = currentTime;
                        if (mTempCount <= 10) {
                            this.mStepCount = 0;
                        } else if (this.mStepCount % 200 == 0) {
                            SharedPreferences stepsharedPreferences = this.context.getSharedPreferences(this.context.getPackageName() + "_stepsCount", 0);
                            Long steps = Long.valueOf(stepsharedPreferences.getLong("stepsCount", 0));
                            Editor editor = stepsharedPreferences.edit();
                            editor.putLong("stepsCount", steps.longValue() + ((long) this.mStepCount));
                            editor.commit();
                            this.mStepCount = 0;
                        }
                    }
                }
                this.mLastDiff = ((2.0f * diff) + (this.mLastDiff * 8.0f)) / 10.0f;
            }
            this.mLastDirections = direction;
            this.mLastValues = speed;
            this.mSampleCount = 0;
            for (int i4 = 0; i4 < 3; i4++) {
                this.mPreValues[i4] = 0.0f;
            }
        }
    }
}
