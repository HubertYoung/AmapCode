package com.alipay.zoloz.toyger.workspace.task;

import android.graphics.Color;

/* compiled from: CherryCaptureTask */
final class a implements Runnable {
    final /* synthetic */ CherryCaptureTask a;

    a(CherryCaptureTask cherryCaptureTask) {
        this.a = cherryCaptureTask;
    }

    public final void run() {
        if (!this.a.isDarkScreen) {
            this.a.mToygerCirclePattern.getTitleBar().setSoundButton(8);
            this.a.mToygerCirclePattern.getCircleUploadPattern().setVisibility(0);
            this.a.mToygerCirclePattern.getTitleBar().setCloseButtonVisible(8);
            this.a.mToygerCirclePattern.getTopTip().setVisibility(8);
            this.a.mToygerCirclePattern.getOuterBakRoundProgressBar().setRoundColor(Color.parseColor("#414146"));
            this.a.mToygerCirclePattern.getOuterBakRoundProgressBar().setCricleProgressColor(Color.parseColor("#414146"));
            this.a.mToygerCirclePattern.getOuterBakRoundProgressBar().setVisibility(8);
            if (this.a.mBestToygerFrame != null) {
                this.a.showBestFrameBlur(this.a.mBestToygerFrame.bestBitmap);
            }
        }
    }
}
