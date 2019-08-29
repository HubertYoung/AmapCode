package com.alipay.mobile.scansdk.ui;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.alipay.mobile.bqcscanservice.BQCScanResult;
import com.alipay.mobile.scansdk.activity.BaseScanRouter;

public abstract class BaseScanTopView extends RelativeLayout {
    protected BaseScanRouter mScanRouter;
    protected TopViewCallback topViewCallback;

    public interface TopViewCallback {
        void clearSurface();

        void scanSuccess();

        void startPreview();

        void stopPreview(boolean z);

        void turnEnvDetection(boolean z);

        boolean turnTorch();
    }

    public abstract void onCameraOpenFailed();

    public abstract void onDestroy();

    public abstract void onInitCamera();

    public abstract void onPreviewShow();

    public abstract void onStartScan();

    public abstract void onStopScan();

    public abstract void onTorchState(boolean z);

    public BaseScanTopView(Context context) {
        super(context);
    }

    public BaseScanTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseScanTopView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onArguments(Bundle bundle) {
    }

    public Rect getScanRect(Camera camera, int previewWidth, int previewHeight) {
        return null;
    }

    public void onResultMa(BQCScanResult maScanResult) {
    }

    public void setCodeRouter(BaseScanRouter scanRouter) {
        this.mScanRouter = scanRouter;
    }

    public void setTopViewCallback(TopViewCallback callback) {
        this.topViewCallback = callback;
    }
}
