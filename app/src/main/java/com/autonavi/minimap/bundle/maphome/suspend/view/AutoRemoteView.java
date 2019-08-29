package com.autonavi.minimap.bundle.maphome.suspend.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.BalloonText;
import com.uc.webview.export.extension.UCCore;

public class AutoRemoteView extends ViewGroup implements dan {
    private ImageView auto_remote;
    private BalloonText auto_remote_tip;
    private int mHeight;

    public AutoRemoteView(Context context) {
        this(context, null);
    }

    public AutoRemoteView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AutoRemoteView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mHeight = context.getResources().getDimensionPixelOffset(R.dimen.map_container_btn_size);
        setClipChildren(false);
        setClipToPadding(false);
        initView(context);
    }

    private void initView(Context context) {
        this.auto_remote = new ImageView(context);
        this.auto_remote.setBackgroundResource(R.drawable.icon_c_bg_single);
        this.auto_remote.setImageResource(R.drawable.icon_c21_selector);
        this.auto_remote.setScaleType(ScaleType.FIT_CENTER);
        this.auto_remote.setClickable(false);
        this.auto_remote_tip = new BalloonText(context);
        this.auto_remote_tip.setType(9);
        this.auto_remote_tip.setArrowOffset(-1, false);
        this.auto_remote_tip.setText(context.getResources().getString(R.string.auto_remote_tip));
        this.auto_remote_tip.setVisibility(8);
        addViewInLayout(this.auto_remote, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.auto_remote_tip, 1, generateDefaultLayoutParams(), true);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int size = MeasureSpec.getSize(i);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.mHeight, UCCore.VERIFY_POLICY_QUICK);
        int i4 = 0;
        if (this.auto_remote.getVisibility() != 8) {
            this.auto_remote.measure(makeMeasureSpec, makeMeasureSpec);
            i3 = this.auto_remote.getMeasuredWidth() + 0;
            i4 = Math.max(0, this.auto_remote.getMeasuredHeight());
        } else {
            i3 = 0;
        }
        if (this.auto_remote_tip.getVisibility() != 8) {
            int i5 = size - i3;
            this.auto_remote_tip.measure(MeasureSpec.makeMeasureSpec(i5, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(i5, Integer.MIN_VALUE));
            i3 += this.auto_remote_tip.getMeasuredWidth();
        }
        setMeasuredDimension(resolveSize(i3, i), resolveSize(i4, i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = 0;
        if (this.auto_remote.getVisibility() != 8) {
            int measuredHeight = ((i4 - i2) - this.auto_remote.getMeasuredHeight()) / 2;
            this.auto_remote.layout(0, measuredHeight, this.auto_remote.getMeasuredWidth() + 0, this.auto_remote.getMeasuredHeight() + measuredHeight);
            i5 = 0 + this.auto_remote.getMeasuredWidth();
        }
        if (this.auto_remote_tip.getVisibility() != 8) {
            int measuredHeight2 = ((i4 - i2) - this.auto_remote_tip.getMeasuredHeight()) / 2;
            this.auto_remote_tip.layout(i5, measuredHeight2, this.auto_remote_tip.getMeasuredWidth() + i5, this.auto_remote_tip.getMeasuredHeight() + measuredHeight2);
        }
    }

    public int getTipViewVisibility() {
        return this.auto_remote_tip.getVisibility();
    }

    public void setTipViewVisibility(int i) {
        this.auto_remote_tip.setVisibility(i);
    }

    public ImageView getAutoRemoteView() {
        return this.auto_remote;
    }

    public void setAutoViewOnClickListener(OnClickListener onClickListener) {
        this.auto_remote.setOnClickListener(onClickListener);
    }

    public View getAutoRemoteTip() {
        return this.auto_remote_tip;
    }

    public void setTipClickListener(OnClickListener onClickListener) {
        this.auto_remote_tip.setOnClickListener(onClickListener);
    }

    public void setViewVisibility(int i) {
        setVisibility(i);
    }
}
