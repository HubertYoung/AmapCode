package com.autonavi.map.suspend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class CommonSuspendWidget extends ViewGroup implements OnClickListener, brh {
    private static final long DEFAULT_TIPS_DURATION = 3000;
    private int mDefaultHeight;
    private int mDefaultMargin;
    private boolean mGravityLeft;
    private OnClickListener mIconClickListener;
    private int mIconHeight;
    private int mIconWidth;
    private ImageView mImageView;
    private TextView mTipsView;

    public CommonSuspendWidget(Context context) {
        this(context, null);
    }

    public CommonSuspendWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CommonSuspendWidget(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mGravityLeft = true;
        init(context);
    }

    private void init(Context context) {
        this.mDefaultHeight = getResources().getDimensionPixelOffset(R.dimen.map_container_btn_size);
        this.mDefaultMargin = getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        this.mIconWidth = this.mDefaultHeight;
        this.mIconHeight = this.mDefaultHeight;
        setClipChildren(false);
        setClipToPadding(false);
        this.mImageView = new ImageView(context);
        this.mImageView.setScaleType(ScaleType.FIT_XY);
        this.mImageView.setBackgroundResource(R.drawable.icon_c_bg_single);
        this.mImageView.setOnClickListener(this);
        this.mTipsView = new TextView(context);
        this.mTipsView.setGravity(17);
        this.mTipsView.setTextSize(13.0f);
        this.mTipsView.setTextColor(Color.parseColor("#ffffff"));
        this.mTipsView.setMaxEms(9);
        this.mTipsView.setVisibility(8);
        this.mTipsView.setOnClickListener(this);
        setGravityLeft(false);
        addViewInLayout(this.mImageView, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mTipsView, 1, generateDefaultLayoutParams(), true);
    }

    private void setGravityLeft(boolean z, boolean z2) {
        if (this.mGravityLeft != z || !z2) {
            this.mGravityLeft = z;
            this.mTipsView.setBackgroundResource(z ? R.drawable.layer_tip_kuang_r : R.drawable.layer_tip_kuang);
            int a = agn.a(getContext(), 8.0f);
            int a2 = agn.a(getContext(), 18.0f);
            int a3 = agn.a(getContext(), 10.0f);
            TextView textView = this.mTipsView;
            int i = z ? a2 : a3;
            if (z) {
                a2 = a3;
            }
            textView.setPadding(i, a, a2, a);
            requestLayout();
        }
    }

    public void setIconSize(int i, int i2) {
        if ((i > 0 || i2 > 0) && !(this.mIconWidth == i && this.mIconHeight == i2)) {
            this.mIconWidth = i;
            this.mIconHeight = i2;
            if (this.mIconWidth <= 0) {
                this.mIconWidth = this.mDefaultHeight;
            }
            if (this.mIconHeight <= 0) {
                this.mIconHeight = this.mDefaultHeight;
            }
            requestLayout();
        }
    }

    public void setGravityLeft(boolean z) {
        setGravityLeft(z, true);
    }

    public void setGravityLeftSimply(boolean z) {
        if (this.mGravityLeft != z) {
            this.mGravityLeft = z;
            requestLayout();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        this.mImageView.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new BitmapDrawable(bitmap), getResources().getDrawable(R.drawable.icon_c_pre_single)});
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{16842919}, layerDrawable);
            stateListDrawable.addState(new int[0], bitmapDrawable);
            this.mImageView.setImageDrawable(stateListDrawable);
        }
    }

    public void setIconClickListener(OnClickListener onClickListener) {
        this.mIconClickListener = onClickListener;
    }

    public void showTips(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mTipsView.setText(charSequence);
            this.mTipsView.setVisibility(0);
            postDelayed(new Runnable() {
                public final void run() {
                    CommonSuspendWidget.this.hideTips();
                }
            }, 3000);
        }
    }

    public void showTipsSimply(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mTipsView.setText(charSequence);
            this.mTipsView.setVisibility(0);
        }
    }

    public void hideTips() {
        if (this.mTipsView.getVisibility() != 8) {
            this.mTipsView.setVisibility(8);
        }
    }

    public ImageView getImageView() {
        return this.mImageView;
    }

    public TextView getTipsView() {
        return this.mTipsView;
    }

    public void onClick(View view) {
        if (view == this.mImageView) {
            hideTips();
            if (this.mIconClickListener != null) {
                this.mIconClickListener.onClick(view);
            }
        } else if (view == this.mTipsView) {
            hideTips();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3 = this.mIconWidth + this.mDefaultMargin;
        this.mImageView.measure(MeasureSpec.makeMeasureSpec(this.mIconWidth, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(this.mIconHeight, UCCore.VERIFY_POLICY_QUICK));
        if (this.mTipsView.getVisibility() != 8) {
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
            this.mTipsView.measure(makeMeasureSpec, makeMeasureSpec);
            i3 += this.mTipsView.getMeasuredWidth();
        }
        setMeasuredDimension(resolveSize(i3, i), resolveSize(this.mIconHeight, i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        setClipChildren(i5 <= 0);
        int i6 = i4 - i2;
        if (this.mGravityLeft) {
            int i7 = this.mDefaultMargin;
            this.mImageView.layout(i7, 0, this.mImageView.getMeasuredWidth() + i7, this.mImageView.getMeasuredHeight());
            int measuredWidth = i7 + this.mImageView.getMeasuredWidth();
            if (this.mTipsView.getVisibility() != 8) {
                int measuredHeight = (i6 - this.mTipsView.getMeasuredHeight()) / 2;
                this.mTipsView.layout(measuredWidth, measuredHeight, this.mTipsView.getMeasuredWidth() + measuredWidth, this.mTipsView.getMeasuredHeight() + measuredHeight);
            }
            return;
        }
        int i8 = i5 - this.mDefaultMargin;
        ImageView imageView = this.mImageView;
        imageView.layout(i8 - imageView.getMeasuredWidth(), 0, i8, this.mImageView.getMeasuredHeight());
        int measuredWidth2 = i8 - this.mImageView.getMeasuredWidth();
        if (this.mTipsView.getVisibility() != 8) {
            int measuredHeight2 = (i6 - this.mTipsView.getMeasuredHeight()) / 2;
            TextView textView = this.mTipsView;
            textView.layout(measuredWidth2 - textView.getMeasuredWidth(), measuredHeight2, measuredWidth2, this.mTipsView.getMeasuredHeight() + measuredHeight2);
        }
    }
}
