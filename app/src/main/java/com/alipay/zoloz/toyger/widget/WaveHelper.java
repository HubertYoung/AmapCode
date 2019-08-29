package com.alipay.zoloz.toyger.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import com.alipay.biometrics.ui.widget.WaveView;
import java.util.ArrayList;

public class WaveHelper {
    private AnimatorSet mAnimatorSet;
    private WaveView mWaveView;

    public WaveHelper(WaveView waveView) {
        this.mWaveView = waveView;
        initAnimation();
    }

    public void start() {
        this.mWaveView.setShowWave(true);
        this.mWaveView.setVisibility(0);
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.start();
        }
    }

    public void setBorder(int i, int i2) {
        this.mWaveView.setBorder(i, i2);
    }

    public void setWaveColor(int i, int i2) {
        this.mWaveView.setWaveColor(i, i2);
    }

    private void initAnimation() {
        ArrayList arrayList = new ArrayList();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mWaveView, "waveShiftRatio", new float[]{0.0f, 1.0f});
        ofFloat.setRepeatCount(-1);
        ofFloat.setDuration(1000);
        ofFloat.setInterpolator(new LinearInterpolator());
        arrayList.add(ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mWaveView, "waterLevelRatio", new float[]{0.0f, 0.8f});
        ofFloat2.setDuration(2000);
        ofFloat2.setInterpolator(new DecelerateInterpolator());
        arrayList.add(ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mWaveView, "amplitudeRatio", new float[]{1.0E-4f, 0.05f});
        ofFloat3.setRepeatCount(-1);
        ofFloat3.setRepeatMode(2);
        ofFloat3.setDuration(1000);
        ofFloat3.setInterpolator(new LinearInterpolator());
        arrayList.add(ofFloat3);
        this.mAnimatorSet = new AnimatorSet();
        this.mAnimatorSet.playTogether(arrayList);
    }

    public void cancel() {
        if (this.mWaveView.isShowWave() && this.mAnimatorSet != null) {
            this.mWaveView.setVisibility(8);
            this.mWaveView.setShowWave(false);
            this.mAnimatorSet.cancel();
            this.mAnimatorSet.end();
        }
    }
}
