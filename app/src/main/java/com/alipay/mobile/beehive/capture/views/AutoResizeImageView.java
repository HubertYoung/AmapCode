package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.util.DensityUtils;

public class AutoResizeImageView extends ImageView {
    private static final String LAYOUT_UPDATE_PATTERN = "Update w from %s to %s, h from %s to %s.";
    private static final String TAG = "AutoResizeImageView";
    private boolean mIsFitParent;

    public void setFitParent(boolean isFitParent) {
        this.mIsFitParent = isFitParent;
    }

    public AutoResizeImageView(Context context) {
        super(context);
    }

    public AutoResizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoResizeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        adjustLayout();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        adjustLayout();
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        adjustLayout();
    }

    private void adjustLayout() {
        Drawable dr = getDrawable();
        if (dr instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) dr).getBitmap();
            int bw = bmp.getWidth();
            int bh = bmp.getHeight();
            int w = DensityUtils.dip2px(getContext(), ((float) bw) / 3.0f);
            int h = DensityUtils.dip2px(getContext(), ((float) bh) / 3.0f);
            LayoutParams lp = getLayoutParams();
            if (lp.width != w || lp.height != h) {
                if (this.mIsFitParent) {
                    Logger.debug(TAG, "Need fit parent.");
                    ViewParent vp = getParent();
                    if (vp instanceof ViewGroup) {
                        int pw = ((ViewGroup) vp).getMeasuredWidth();
                        int ph = ((ViewGroup) vp).getMeasuredHeight();
                        if (pw > 0 && ph > 0 && (w > pw || h > ph)) {
                            float rw = ((float) w) / ((float) pw);
                            float rh = ((float) h) / ((float) ph);
                            if (rw > rh) {
                                w = pw;
                                h = (int) (((float) h) / rw);
                                Logger.debug(TAG, "Scale to fit width");
                            } else {
                                w = (int) (((float) w) / rh);
                                h = ph;
                                Logger.debug(TAG, "Scale to fit height");
                            }
                        }
                    }
                }
                Logger.debug(TAG, String.format(LAYOUT_UPDATE_PATTERN, new Object[]{Integer.valueOf(lp.width), Integer.valueOf(w), Integer.valueOf(lp.height), Integer.valueOf(h)}));
                lp.width = w;
                lp.height = h;
                setLayoutParams(lp);
            }
        }
    }
}
