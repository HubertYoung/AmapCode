package com.autonavi.map.core.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;

public class MapLayerView extends LinearLayout implements cem {
    private ImageView mIconImageView;
    private TextView mTipsTextView;

    public MapLayerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public MapLayerView(Context context) {
        this(context, null);
    }

    public MapLayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mTipsTextView.getVisibility() != 8) {
            this.mTipsTextView.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(0, 0));
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        setClipChildren(i3 - i <= 0);
        super.onLayout(z, i, i2, i3, i4);
        if (this.mTipsTextView.getVisibility() != 8) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mTipsTextView.getLayoutParams();
            int i5 = marginLayoutParams != null ? marginLayoutParams.topMargin : 0;
            this.mTipsTextView.layout(0, i5, this.mTipsTextView.getMeasuredWidth(), this.mTipsTextView.getMeasuredHeight() + i5);
        }
    }

    private void init(Context context) {
        setOrientation(0);
        setClipChildren(false);
        setClipToPadding(false);
        this.mIconImageView = new ImageView(context);
        this.mIconImageView.setId(R.id.btn_maplayers);
        this.mIconImageView.setContentDescription(context.getString(R.string.map_cont_des_maplayer));
        this.mTipsTextView = new TextView(context);
        this.mTipsTextView.setTextSize(1, 13.0f);
        this.mTipsTextView.setEllipsize(TruncateAt.END);
        this.mTipsTextView.setSingleLine();
        this.mTipsTextView.setMaxEms(9);
        this.mTipsTextView.setBackgroundResource(R.drawable.layer_tip_kuang);
        this.mTipsTextView.setTextColor(Color.parseColor("#ffffff"));
        this.mTipsTextView.setId(R.id.layer_tip_tv);
        this.mTipsTextView.setPadding(agn.a(context, 10.0f), agn.a(context, 8.0f), agn.a(context, 18.0f), agn.a(context, 8.0f));
        this.mTipsTextView.setVisibility(8);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.topMargin = agn.a(context, 6.0f);
        addView(this.mTipsTextView, layoutParams);
        addView(this.mIconImageView, new LayoutParams(dimensionPixelSize, dimensionPixelSize));
    }

    public void setIconImageResource(int i) {
        this.mIconImageView.setImageResource(i);
    }

    public void setIconBackgroundResource(int i) {
        this.mIconImageView.setBackgroundResource(i);
    }

    public void setIconClickListener(OnClickListener onClickListener) {
        this.mIconImageView.setOnClickListener(onClickListener);
    }

    public void setTipsClickListener(OnClickListener onClickListener) {
        this.mTipsTextView.setOnClickListener(onClickListener);
    }

    public boolean showTips(AmapMessage amapMessage) {
        if (amapMessage == null || TextUtils.isEmpty(amapMessage.title)) {
            this.mTipsTextView.setVisibility(8);
            return false;
        }
        this.mTipsTextView.setVisibility(0);
        this.mTipsTextView.setTag(amapMessage);
        this.mTipsTextView.setText(amapMessage.title);
        return true;
    }

    public boolean isTipsShown() {
        return this.mTipsTextView.getVisibility() == 0;
    }

    public Object dismissTips() {
        if (this.mTipsTextView.getVisibility() != 0) {
            return null;
        }
        this.mTipsTextView.setVisibility(8);
        return (AmapMessage) this.mTipsTextView.getTag();
    }
}
