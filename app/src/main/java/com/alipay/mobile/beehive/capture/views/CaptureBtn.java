package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.views.CustomRecordImageView.RecordListener;

public class CaptureBtn extends FrameLayout {
    private Type mCurrentType;
    public ImageView takePictureBtn;
    public CustomRecordImageView videoRecordBtn;

    public enum Type {
        NONE,
        VIDEO,
        PICTURE
    }

    public CaptureBtn(Context context) {
        this(context, null, 0);
    }

    public CaptureBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CaptureBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentType = Type.NONE;
        LayoutInflater.from(context).inflate(R.layout.view_capture_btn, this, true);
        this.videoRecordBtn = (CustomRecordImageView) findViewById(R.id.videoRecord);
        this.takePictureBtn = (ImageView) findViewById(R.id.takePicture);
        updateType();
    }

    private void updateType() {
        switch (this.mCurrentType) {
            case NONE:
                this.videoRecordBtn.setVisibility(8);
                this.takePictureBtn.setVisibility(8);
                return;
            case VIDEO:
                this.videoRecordBtn.setVisibility(0);
                this.takePictureBtn.setVisibility(8);
                return;
            case PICTURE:
                this.videoRecordBtn.setVisibility(8);
                this.takePictureBtn.setVisibility(0);
                return;
            default:
                return;
        }
    }

    public void setViewType(Type type) {
        this.mCurrentType = type;
        updateType();
    }

    public void setOnVideoRecordListener(RecordListener listener) {
        this.videoRecordBtn.setOnRecordListener(listener);
    }

    public void performFinsihRecord() {
        this.videoRecordBtn.performFinishRecord();
    }

    public void performCancelRecord() {
        this.videoRecordBtn.performCancelRecord();
    }

    public void setInterceptUserRecordAction(boolean canRecordNow) {
        this.videoRecordBtn.setInterceptUserRecordAction(canRecordNow);
    }
}
