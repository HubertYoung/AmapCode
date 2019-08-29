package com.alipay.mobile.nebula.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5TypefaceCache;
import com.uc.webview.export.extension.UCCore;
import java.io.File;
import java.util.Map;

public class H5WebLoadingView extends LoadingView {
    private TextView mBackButton;
    private Context mContext;
    ValueAnimator mDarkAnim;
    private int mDarkColor;
    /* access modifiers changed from: private */
    public int mDarkDotX;
    private int mDarkDotY;
    private Paint mDotPaint;
    private int mDotSize;
    private boolean mIsAnimating;
    private int mLightColor;
    /* access modifiers changed from: private */
    public int mLightDotX;
    private ImageView mLoadingIcon;
    private TextView mLoadingTitle;

    public H5WebLoadingView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public void onStart() {
        startLoadingAnimation();
    }

    public void onStop() {
    }

    public void onHandleMessage(String s, Map<String, Object> map) {
    }

    public void initView() {
        this.mLoadingIcon = new ImageView(this.mContext);
        this.mLoadingIcon.setScaleType(ScaleType.FIT_XY);
        this.mLoadingIcon.setImageResource(R.drawable.default_loading_icon);
        this.mLoadingTitle = new TextView(this.mContext);
        this.mLoadingTitle.setGravity(17);
        this.mLoadingTitle.setTextColor(this.mContext.getResources().getColor(R.color.h5_web_loading_text));
        this.mLoadingTitle.setSingleLine();
        this.mLoadingTitle.setTextSize(14.0f);
        this.mLoadingTitle.setEllipsize(TruncateAt.END);
        addView(this.mLoadingIcon);
        addView(this.mLoadingTitle);
        this.mBackButton = new TextView(this.mContext);
        this.mBackButton.setTypeface(H5TypefaceCache.getTypeface(this.mContext, "h5iconfont", "h5iconfont" + File.separator + "h5titlebar.ttf"));
        this.mBackButton.setText("");
        this.mBackButton.setTextSize(1, 17.0f);
        this.mBackButton.setTextColor(-15692055);
        this.mBackButton.setGravity(17);
        addView(this.mBackButton);
        this.mDarkColor = this.mContext.getResources().getColor(R.color.h5_web_loading_dot_dark);
        this.mLightColor = this.mContext.getResources().getColor(R.color.h5_web_loading_dot_light);
        this.mDotSize = getDimen(R.dimen.h5_loading_dot_size);
        this.mDotPaint = new Paint();
        this.mDotPaint.setStyle(Style.FILL);
        setBackgroundColor(this.mContext.getResources().getColor(R.color.h5_web_loading_default_bg));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = H5DimensionUtil.getScreenWidth(this.mContext) / 5;
        this.mLoadingIcon.measure(makeMeasureSpec(size), makeMeasureSpec(size));
        this.mLoadingTitle.measure(makeMeasureSpec(getDimen(R.dimen.h5_loading_title_width)), makeMeasureSpec(getDimen(R.dimen.h5_loading_title_height)));
        this.mBackButton.measure(makeMeasureSpec(getDimen(R.dimen.h5_loading_back_button_width)), makeMeasureSpec(getDimen(R.dimen.h5_title_height)));
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mBackButton.layout(0, 0, this.mBackButton.getMeasuredWidth(), this.mBackButton.getMeasuredHeight());
        int offsetX = (getMeasuredWidth() - this.mLoadingIcon.getMeasuredWidth()) / 2;
        int offsetY = getMeasuredHeight() / 4;
        this.mLoadingIcon.layout(offsetX, offsetY, this.mLoadingIcon.getMeasuredWidth() + offsetX, this.mLoadingIcon.getMeasuredHeight() + offsetY);
        int offsetX2 = (getMeasuredWidth() - this.mLoadingTitle.getMeasuredWidth()) / 2;
        int offsetY2 = this.mLoadingIcon.getMeasuredHeight() + offsetY + getDimen(R.dimen.h5_loading_title_margin_top);
        this.mLoadingTitle.layout(offsetX2, offsetY2, this.mLoadingTitle.getMeasuredWidth() + offsetX2, this.mLoadingTitle.getMeasuredHeight() + offsetY2);
        this.mDarkDotY = this.mLoadingTitle.getMeasuredHeight() + offsetY2 + getDimen(R.dimen.h5_loading_dot_margin_top);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mIsAnimating) {
            this.mDotPaint.setColor(this.mDarkColor);
            canvas.drawCircle((float) this.mDarkDotX, (float) this.mDarkDotY, (float) (this.mDotSize / 2), this.mDotPaint);
            this.mDotPaint.setColor(this.mLightColor);
            canvas.drawCircle((float) this.mLightDotX, (float) this.mDarkDotY, (float) (this.mDotSize / 2), this.mDotPaint);
        }
    }

    public void setLoadingInfo(String title, String bgColorStr, String themeColorStr) {
        this.mLoadingTitle.setText(title);
        if (!TextUtils.isEmpty(bgColorStr)) {
            setBackgroundColor(Color.parseColor(bgColorStr));
        }
        if (!TextUtils.isEmpty(themeColorStr)) {
            this.mLoadingTitle.setTextColor(Color.parseColor(themeColorStr));
        }
    }

    public void setLoadingBitmap(Bitmap bitmap) {
        this.mLoadingIcon.setImageBitmap(bitmap);
    }

    public void startLoadingAnimation() {
        if (!this.mIsAnimating) {
            final int centerX = H5DimensionUtil.getScreenWidth(this.mContext) / 2;
            int margin = getDimen(R.dimen.h5_loading_dot_margin_center);
            this.mIsAnimating = true;
            this.mDarkAnim = ValueAnimator.ofInt(new int[]{(centerX - (this.mDotSize / 2)) - margin, centerX + margin + (this.mDotSize / 2)});
            this.mDarkAnim.setDuration(400);
            this.mDarkAnim.setRepeatCount(90);
            this.mDarkAnim.setRepeatMode(2);
            this.mDarkAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    H5WebLoadingView.this.mDarkDotX = ((Integer) animation.getAnimatedValue()).intValue();
                    if (H5WebLoadingView.this.mDarkDotX < centerX) {
                        H5WebLoadingView.this.mLightDotX = (centerX + centerX) - H5WebLoadingView.this.mDarkDotX;
                    } else {
                        H5WebLoadingView.this.mLightDotX = centerX - (H5WebLoadingView.this.mDarkDotX - centerX);
                    }
                    H5WebLoadingView.this.invalidate();
                }
            });
            this.mDarkAnim.start();
        }
    }

    public void stopLoadingAnimation() {
        this.mIsAnimating = false;
        if (this.mDarkAnim != null) {
            this.mDarkAnim.end();
            this.mDarkAnim.removeAllUpdateListeners();
            this.mDarkAnim = null;
        }
    }

    public TextView getBackButton() {
        return this.mBackButton;
    }

    private int getDimen(int id) {
        return this.mContext.getResources().getDimensionPixelSize(id);
    }

    private static int makeMeasureSpec(int size) {
        return MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK);
    }
}
