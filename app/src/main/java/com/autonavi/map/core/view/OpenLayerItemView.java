package com.autonavi.map.core.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

public class OpenLayerItemView extends RelativeLayout {
    private boolean isSelected;
    private TextView mDescTextView;
    private ImageView mIconImageView;
    private ImageView mNewImageView;
    private ks mRoundImageFactory;
    private ImageView mSelectImageView;

    public OpenLayerItemView(Context context) {
        this(context, null);
    }

    public OpenLayerItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OpenLayerItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRoundImageFactory = new ks(20.0f, AMapAppGlobal.getApplication().getResources().getDisplayMetrics());
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_openlayer_item, this);
        this.mIconImageView = (ImageView) findViewById(R.id.iv_openlayer_item_icon);
        this.mSelectImageView = (ImageView) findViewById(R.id.iv_openlayer_item_select);
        this.mNewImageView = (ImageView) findViewById(R.id.iv_openlayer_item_new);
        this.mDescTextView = (TextView) findViewById(R.id.tv_openlayer_item_desc);
    }

    public void setNew(boolean z) {
        this.mNewImageView.setVisibility(z ? 0 : 8);
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
        this.mSelectImageView.setSelected(z);
        this.mDescTextView.setSelected(z);
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setImageResource(int i) {
        this.mIconImageView.setImageResource(i);
    }

    public void bindImage(String str) {
        if (!TextUtils.isEmpty(str)) {
            ko.a(this.mIconImageView, str, this.mRoundImageFactory, R.drawable.maplayer_list_def_pic);
        }
    }

    public void setDesc(CharSequence charSequence) {
        this.mDescTextView.setText(charSequence);
    }
}
