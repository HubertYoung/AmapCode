package com.autonavi.minimap.route.sharebike.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.qrscan.scanner.AjxScanManager;

public class ShareRidingTorchView extends TextView implements OnClickListener {
    private boolean hasCloseAjxCamera;
    private egk mCameraManager;
    private boolean mHasOpenCamera;
    private a mListener;

    public interface a {
        void a(boolean z);
    }

    public ShareRidingTorchView(Context context) {
        this(context, null);
    }

    public ShareRidingTorchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShareRidingTorchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @TargetApi(21)
    public ShareRidingTorchView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void init() {
        this.mCameraManager = egk.g();
        setOnClickListener(this);
    }

    public void setOnTorchClickListener(a aVar) {
        this.mListener = aVar;
    }

    public void clickTorchView() {
        boolean isTorchOpen = isTorchOpen();
        updateView(!isTorchOpen);
        if (isTorchOpen) {
            closeTorch();
        } else {
            openTorch();
        }
        if (this.mListener != null) {
            this.mListener.a(!isTorchOpen);
        }
    }

    public void refreshView(boolean z) {
        updateView(z);
    }

    private void updateView(boolean z) {
        setSelected(z);
        if (z) {
            setText(R.string.sharebike_close_torch);
        } else {
            setText(R.string.sharebike_open_torch);
        }
    }

    public boolean isTorchOpen() {
        tryCloseAjxCamera();
        this.mHasOpenCamera = this.mCameraManager != null && this.mCameraManager.h();
        return this.mHasOpenCamera;
    }

    private void setTorch(boolean z) {
        tryCloseAjxCamera();
        if (!(z == this.mHasOpenCamera || this.mCameraManager == null)) {
            try {
                this.mCameraManager.i();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z != this.mHasOpenCamera) {
                this.mCameraManager.a(z);
            }
            this.mHasOpenCamera = isTorchOpen();
            updateView(this.mHasOpenCamera);
        }
    }

    private void tryCloseAjxCamera() {
        try {
            if (!this.hasCloseAjxCamera) {
                AjxScanManager.getInstance().setTorch(false);
                this.hasCloseAjxCamera = true;
            }
        } catch (Exception unused) {
        }
    }

    public void openTorch() {
        setTorch(true);
    }

    public void closeTorch() {
        setTorch(false);
        this.mCameraManager.b();
    }

    public void onClick(View view) {
        view.setSelected(!view.isSelected());
        clickTorchView();
    }
}
