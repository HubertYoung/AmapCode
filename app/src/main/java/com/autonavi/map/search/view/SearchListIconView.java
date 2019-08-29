package com.autonavi.map.search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.util.List;

public class SearchListIconView extends LinearLayout {
    List<Integer> mIconResIds;
    private int mImageMargin;
    private int mImageOriginWidth;

    public SearchListIconView(Context context) {
        super(context);
        init();
    }

    public SearchListIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SearchListIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOrientation(0);
        setGravity(19);
        this.mImageMargin = agn.a(getContext(), 5.0f);
    }

    public void setData(List<Integer> list) {
        int i;
        removeAllViews();
        if (list != null || list.size() > 0) {
            this.mIconResIds = list;
            this.mImageOriginWidth = 0;
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (list.get(i2) != null && list.get(i2).intValue() > 0) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setScaleType(ScaleType.CENTER);
                    imageView.setImageResource(list.get(i2).intValue());
                    LayoutParams layoutParams = new LayoutParams(-2, -2);
                    if (i2 == 0) {
                        i = 0;
                    } else {
                        i = this.mImageMargin;
                    }
                    layoutParams.leftMargin = i;
                    imageView.measure(0, 0);
                    imageView.setLayoutParams(layoutParams);
                    addView(imageView);
                    this.mImageOriginWidth += imageView.getMeasuredWidth() + layoutParams.leftMargin;
                }
            }
        }
    }

    public int getImageOriginWidth() {
        return this.mImageOriginWidth;
    }

    public void adjustIconVisiableBySupportWidth(int i) {
        int i2;
        if (i < this.mImageOriginWidth) {
            if (this.mIconResIds != null || this.mIconResIds.size() > 0) {
                removeAllViews();
                int i3 = 0;
                for (int i4 = 0; i4 < this.mIconResIds.size(); i4++) {
                    if (this.mIconResIds.get(i4) != null && this.mIconResIds.get(i4).intValue() > 0) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ScaleType.CENTER);
                        imageView.setImageResource(this.mIconResIds.get(i4).intValue());
                        LayoutParams layoutParams = new LayoutParams(-2, -2);
                        if (i4 == 0) {
                            i2 = 0;
                        } else {
                            i2 = this.mImageMargin;
                        }
                        layoutParams.leftMargin = i2;
                        imageView.measure(0, 0);
                        imageView.setLayoutParams(layoutParams);
                        addView(imageView);
                        i3 += imageView.getMeasuredWidth() + layoutParams.leftMargin;
                        if (i3 <= i) {
                            imageView.setVisibility(0);
                        } else {
                            imageView.setVisibility(4);
                        }
                    }
                }
            }
        }
    }
}
