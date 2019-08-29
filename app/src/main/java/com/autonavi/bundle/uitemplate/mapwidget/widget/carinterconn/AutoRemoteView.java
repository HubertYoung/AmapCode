package com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.autonavi.bundle.uitemplate.mapwidget.common.MapWidgetTip;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class AutoRemoteView extends ViewGroup {
    private View auto_remote;
    private MapWidgetTip auto_remote_tip;
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
        this.mHeight = bet.a(context, 48);
        setClipChildren(false);
        setClipToPadding(false);
        initView(context);
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.map_widget_common_layout;
    }

    private void initView(Context context) {
        this.auto_remote = LayoutInflater.from(context).inflate(getLayoutId(), null, false);
        ImageView imageView = (ImageView) this.auto_remote.findViewById(R.id.map_widget_common_icon);
        if (imageView != null) {
            imageView.setImageResource(R.drawable.map_widget_auto_remote_icon);
            imageView.setScaleType(ScaleType.FIT_CENTER);
        }
        this.auto_remote_tip = new MapWidgetTip(context);
        this.auto_remote_tip.setArrowDirection(2);
        this.auto_remote_tip.setMaxLines(2);
        this.auto_remote_tip.setTextSizeDp(13.0f);
        this.auto_remote_tip.setText(context.getResources().getString(R.string.auto_remote_tip));
        int a = bet.a(context, 5);
        this.auto_remote_tip.setTipPadding(a, a, a, a);
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

    public View getAutoRemoteView() {
        return this.auto_remote;
    }

    public View getAutoRemoteTip() {
        return this.auto_remote_tip;
    }
}
