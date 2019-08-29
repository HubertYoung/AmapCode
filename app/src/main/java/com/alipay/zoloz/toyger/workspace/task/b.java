package com.alipay.zoloz.toyger.workspace.task;

import android.graphics.Color;

/* compiled from: CherryCaptureTask */
final class b implements Runnable {
    final /* synthetic */ CherryCaptureTask a;

    b(CherryCaptureTask cherryCaptureTask) {
        this.a = cherryCaptureTask;
    }

    public final void run() {
        this.a.mToygerCirclePattern.getTitleBar().setSoundButton(8);
        this.a.mToygerCirclePattern.getCircleUploadPattern().setBackgroundColor(Color.parseColor("#DA000000"));
        this.a.mToygerCirclePattern.getCircleUploadPattern().setVisibility(0);
        this.a.mToygerCirclePattern.getTitleBar().setCloseButtonVisible(8);
        this.a.mToygerCirclePattern.getTopTip().setVisibility(8);
        this.a.mToygerCirclePattern.getOuterBakRoundProgressBar().setRoundColor(Color.parseColor("#414146"));
        this.a.mToygerCirclePattern.getOuterBakRoundProgressBar().setCricleProgressColor(Color.parseColor("#414146"));
        this.a.mToygerCirclePattern.getOuterBakRoundProgressBar().setVisibility(8);
    }
}
