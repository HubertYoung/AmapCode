package com.alipay.zoloz.toyger.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.StringUtil;
import com.alipay.zoloz.toyger.R;

public class CircleUploadPattern extends RelativeLayout {
    Context mContext;
    ImageView mFacePreView;
    /* access modifiers changed from: private */
    public boolean mIsShowProcess;
    private Handler mMainHandler;
    TextView mProcessTextView;
    UploadProgressBar mUploadProgressBar;
    ValueAnimator mValueAnimator = null;
    /* access modifiers changed from: private */
    public int processsAngle = 180;
    RelativeLayout rootView;

    public CircleUploadPattern(Context context) {
        super(context);
        this.mContext = context;
        initViews();
    }

    public CircleUploadPattern(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initViews();
    }

    public CircleUploadPattern(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initViews();
    }

    public void initViews() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.faceeye_loading_pattern, this, true);
        this.rootView = (RelativeLayout) inflate.findViewById(R.id.face_eye_loading_page);
        this.mFacePreView = (ImageView) inflate.findViewById(R.id.simple_face_preview);
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mUploadProgressBar = (UploadProgressBar) inflate.findViewById(R.id.zoloz_back_progress);
        this.mProcessTextView = (TextView) inflate.findViewById(R.id.simple_process_text);
    }

    public void startProcess() {
        this.mProcessTextView.setText(this.mContext.getResources().getString(R.string.face_eye_processing));
        intervalAction();
    }

    /* access modifiers changed from: private */
    public void intervalAction() {
        this.mMainHandler.postDelayed(new a(this), 1000);
        if (!this.mIsShowProcess) {
            this.mIsShowProcess = true;
            if (this.mValueAnimator != null) {
                this.mValueAnimator.cancel();
            }
            this.mValueAnimator = ValueAnimator.ofInt(new int[]{0, 30});
            this.mValueAnimator.setDuration(1000);
            this.mValueAnimator.setInterpolator(new LinearInterpolator());
            this.mValueAnimator.addUpdateListener(new b(this));
            this.mValueAnimator.start();
        }
    }

    public void stopProcess() {
    }

    public void setBackgroundColor(int i) {
        this.rootView.setBackgroundColor(i);
    }

    private String getString(String str, String str2) {
        return StringUtil.isNullorEmpty(str) ? str2 : str;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        startProcess();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopProcess();
        this.mContext = null;
    }
}
