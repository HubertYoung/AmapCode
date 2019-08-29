package com.autonavi.minimap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import com.autonavi.minimap.R;
import java.util.List;

public class UPMarqueeView extends ViewFlipper {
    private int interval = 5000;
    private boolean isSetAnimDuration = false;
    private Context mContext;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int i, View view);
    }

    public UPMarqueeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        this.mContext = context;
        setFlipInterval(this.interval);
        setInAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.anim_marquee_in));
        setOutAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.anim_marquee_out));
    }

    public void setViews(final List<View> list) {
        if (isFlipping()) {
            stopFlipping();
        }
        clearAnimation();
        removeAllViews();
        if (list != null && list.size() != 0) {
            for (final int i = 0; i < list.size(); i++) {
                if (this.onItemClickListener != null) {
                    list.get(i).setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (UPMarqueeView.this.onItemClickListener != null) {
                                UPMarqueeView.this.onItemClickListener.onItemClick(i, (View) list.get(i));
                            }
                        }
                    });
                }
                addView(list.get(i));
            }
            startFlipping();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }
}
