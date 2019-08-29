package com.alipay.android.nebulaapp;

import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DefaultLoadingView extends LoadingView {
    public static final String ANIMATION_STOP_LOADING_PREPARE = "ANIMATION_STOP_LOADING_PREPARE";
    public static final String DATA_UPDATE_APPEARANCE_BG_COLOR = "UPDATE_APPEARANCE_BG_COLOR";
    public static final String DATA_UPDATE_APPEARANCE_LOADING_ICON = "UPDATE_APPEARANCE_LOADING_ICON";
    public static final String DATA_UPDATE_APPEARANCE_LOADING_TEXT = "UPDATE_APPEARANCE_LOADING_TEXT";
    public static final String DATA_UPDATE_APPEARANCE_LOADING_TEXT_COLOR = "UPDATE_APPEARANCE_LOADING_TEXT_COLOR";
    public static final String MSG_UPDATE_APPEARANCE = "UPDATE_APPEARANCE";
    private static final String TAG = "DefaultLoadingView";
    private static int defaultAlphaColor = 855638016;
    private ImageButton mBackButton;
    private Context mContext;
    private int mDarkColor;
    private int mDarkDotX;
    private int mDarkDotY;
    private int mDarkGap;
    private View mDivider;
    private Paint mDotPaint;
    private int mDotSize;
    private int mLightColor;
    /* access modifiers changed from: private */
    public int mLightDotIndex;
    private ImageView mLoadingIcon;
    private TextView mLoadingTitle;
    private boolean mPlayingStartAnim;
    private Timer mTimer;
    private TimerTask mTimerTask;

    public DefaultLoadingView(Context context) {
        this(context, null);
    }

    public DefaultLoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DefaultLoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLightDotIndex = 0;
        this.mContext = context;
        initView();
        this.mBackButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DefaultLoadingView.this.cancel();
            }
        });
    }

    public void initView() {
        this.mLoadingIcon = new ImageView(this.mContext);
        this.mLoadingIcon.setScaleType(ScaleType.FIT_XY);
        this.mLoadingIcon.setImageResource(R.drawable.default_loading_icon);
        this.mLoadingTitle = new TextView(this.mContext);
        this.mLoadingTitle.setGravity(17);
        this.mLoadingTitle.setTextColor(this.mContext.getResources().getColor(R.color.h5_web_loading_text));
        this.mLoadingTitle.setSingleLine();
        this.mLoadingTitle.setTextSize(16.0f);
        this.mLoadingTitle.setEllipsize(TruncateAt.END);
        this.mLoadingTitle.setLayoutParams(new LayoutParams(-2, -2));
        addView(this.mLoadingIcon);
        addView(this.mLoadingTitle);
        this.mBackButton = new ImageButton(this.mContext);
        this.mBackButton.setBackgroundResource(R.drawable.h5_title_bar_back_btn_bg_selector);
        this.mBackButton.setImageResource(R.drawable.h5_title_bar_back_btn_selector);
        this.mBackButton.setScaleType(ScaleType.CENTER_INSIDE);
        this.mDivider = new View(this.mContext);
        this.mDivider.setBackgroundColor(-2500135);
        addView(this.mBackButton);
        addView(this.mDivider);
        this.mDarkColor = this.mContext.getResources().getColor(R.color.h5_web_loading_dot_dark_new);
        this.mLightColor = this.mContext.getResources().getColor(R.color.h5_web_loading_dot_light_new);
        this.mDotSize = getDimen(R.dimen.h5_loading_dot_size);
        this.mDotPaint = new Paint();
        this.mDotPaint.setStyle(Style.FILL);
        this.mDarkGap = getDimen(R.dimen.h5_loading_dot_margin);
        setBackgroundColor(this.mContext.getResources().getColor(R.color.h5_web_loading_default_bg));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int dimen = getDimen(R.dimen.h5_loading_icon_size);
        this.mLoadingIcon.measure(makeMeasureSpec(dimen), makeMeasureSpec(dimen));
        int dimen2 = getDimen(R.dimen.h5_loading_title_height);
        this.mLoadingTitle.measure(MeasureSpec.makeMeasureSpec(getDimen(R.dimen.h5_loading_title_width), Integer.MIN_VALUE), makeMeasureSpec(dimen2));
        this.mBackButton.measure(makeMeasureSpec(getDimen(R.dimen.h5_loading_back_button_width)), makeMeasureSpec(getDimen(R.dimen.h5_loading_titlebar_height)));
        this.mDivider.measure(makeMeasureSpec(getDimen(R.dimen.h5_loading_divider_width)), makeMeasureSpec(getDimen(R.dimen.h5_loading_divider_height)));
        setMeasuredDimension(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mBackButton.layout(0, 0, this.mBackButton.getMeasuredWidth(), this.mBackButton.getMeasuredHeight());
        int measuredWidth = this.mBackButton.getMeasuredWidth();
        int dimen = (getDimen(R.dimen.h5_loading_titlebar_height) - this.mDivider.getMeasuredHeight()) / 2;
        this.mDivider.layout(measuredWidth, dimen, this.mDivider.getMeasuredWidth() + measuredWidth, this.mDivider.getMeasuredHeight() + dimen);
        int measuredWidth2 = (getMeasuredWidth() - this.mLoadingIcon.getMeasuredWidth()) / 2;
        int dimen2 = getDimen(R.dimen.h5_loading_titlebar_height) + getDimen(R.dimen.h5_loading_icon_margin_top);
        this.mLoadingIcon.layout(measuredWidth2, dimen2, this.mLoadingIcon.getMeasuredWidth() + measuredWidth2, this.mLoadingIcon.getMeasuredHeight() + dimen2);
        int measuredWidth3 = (getMeasuredWidth() - this.mLoadingTitle.getMeasuredWidth()) / 2;
        int measuredHeight = dimen2 + this.mLoadingIcon.getMeasuredHeight() + getDimen(R.dimen.h5_loading_title_margin_top);
        this.mLoadingTitle.layout(measuredWidth3, measuredHeight, this.mLoadingTitle.getMeasuredWidth() + measuredWidth3, this.mLoadingTitle.getMeasuredHeight() + measuredHeight);
        this.mDarkDotX = ((getMeasuredWidth() / 2) - this.mDotSize) - this.mDarkGap;
        this.mDarkDotY = measuredHeight + this.mLoadingTitle.getMeasuredHeight() + getDimen(R.dimen.h5_loading_dot_margin_top);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mPlayingStartAnim) {
            this.mDotPaint.setColor(this.mDarkColor);
            this.mDarkDotX = ((getMeasuredWidth() / 2) - this.mDotSize) - this.mDarkGap;
            int i = 0;
            while (i < 3) {
                this.mDotPaint.setColor(this.mLightDotIndex == i ? this.mLightColor : this.mDarkColor);
                canvas.drawCircle((float) this.mDarkDotX, (float) this.mDarkDotY, (float) (this.mDotSize / 2), this.mDotPaint);
                this.mDarkDotX = this.mDarkDotX + this.mDarkGap + this.mDotSize;
                i++;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void startLoadingAnimation() {
        if (!this.mPlayingStartAnim) {
            this.mPlayingStartAnim = true;
            if (this.mTimerTask == null) {
                this.mTimerTask = new TimerTask() {
                    public void run() {
                        DefaultLoadingView.this.mLightDotIndex = DefaultLoadingView.this.mLightDotIndex + 1;
                        if (DefaultLoadingView.this.mLightDotIndex > 2) {
                            DefaultLoadingView.this.mLightDotIndex = 0;
                        }
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                DefaultLoadingView.this.invalidate();
                            }
                        });
                    }
                };
            }
            if (this.mTimer == null) {
                try {
                    this.mTimer = new Timer();
                    this.mTimer.schedule(this.mTimerTask, 0, 200);
                } catch (Throwable unused) {
                }
            }
        }
    }

    public void stopLoadingAnimation() {
        this.mPlayingStartAnim = false;
        if (this.mTimer != null) {
            this.mTimer.cancel();
        }
        if (this.mTimerTask != null) {
            this.mTimerTask.cancel();
        }
        invalidate();
    }

    private int getDimen(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i);
    }

    private int makeMeasureSpec(int i) {
        return MeasureSpec.makeMeasureSpec(i, UCCore.VERIFY_POLICY_QUICK);
    }

    public void onStart() {
        updateStatusBar();
        startLoadingAnimation();
    }

    public void onStop() {
        stopLoadingAnimation();
    }

    public void onHandleMessage(String str, Map<String, Object> map) {
        if ("UPDATE_APPEARANCE".equals(str)) {
            String str2 = (String) map.get("UPDATE_APPEARANCE_BG_COLOR");
            if (!TextUtils.isEmpty(str2)) {
                setBackgroundColor(Color.parseColor(str2));
            }
            Drawable drawable = (Drawable) map.get("UPDATE_APPEARANCE_LOADING_ICON");
            if (drawable != null) {
                this.mLoadingIcon.setImageDrawable(drawable);
            }
            String str3 = (String) map.get("UPDATE_APPEARANCE_LOADING_TEXT");
            if (str3 != null) {
                this.mLoadingTitle.setText(str3);
            }
            String str4 = (String) map.get("UPDATE_APPEARANCE_LOADING_TEXT_COLOR");
            if (!TextUtils.isEmpty(str4)) {
                this.mLoadingTitle.setTextColor(Color.parseColor(str4));
            }
        }
    }

    public void performAnimation(final String str, final AnimatorListener animatorListener) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            doPerformAnimation(str, animatorListener);
        } else {
            post(new Runnable() {
                public void run() {
                    DefaultLoadingView.this.doPerformAnimation(str, animatorListener);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void doPerformAnimation(String str, AnimatorListener animatorListener) {
        if (getParent() == null) {
            LoggerFactory.getTraceLogger().error(TAG, (String) "loading view has not added to parent container");
        } else if ("ANIMATION_STOP_LOADING_PREPARE".equals(str)) {
            this.mPlayingStartAnim = false;
            int dimen = getDimen(R.dimen.h5_loading_titlebar_height) / 4;
            float x = this.mDivider.getX() + ((float) this.mDivider.getMeasuredWidth()) + ((float) getDimen(R.dimen.h5_loading_title_margin_left));
            float dimen2 = (float) ((getDimen(R.dimen.h5_loading_titlebar_height) - this.mLoadingTitle.getMeasuredHeight()) / 2);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(400);
            if (animatorListener != null) {
                animatorSet.addListener(animatorListener);
            }
            animatorSet.play(ObjectAnimator.ofFloat(this.mLoadingIcon, DictionaryKeys.CTRLXY_Y, new float[]{this.mLoadingIcon.getY(), (float) dimen})).with(ObjectAnimator.ofFloat(this.mLoadingIcon, "scaleX", new float[]{this.mLoadingIcon.getScaleX(), 0.0f})).with(ObjectAnimator.ofFloat(this.mLoadingIcon, "scaleY", new float[]{this.mLoadingIcon.getScaleY(), 0.0f})).with(ObjectAnimator.ofFloat(this.mLoadingTitle, DictionaryKeys.CTRLXY_X, new float[]{this.mLoadingTitle.getX(), x})).with(ObjectAnimator.ofFloat(this.mLoadingTitle, DictionaryKeys.CTRLXY_Y, new float[]{this.mLoadingTitle.getY(), dimen2}));
            animatorSet.start();
        } else {
            super.performAnimation(str, animatorListener);
        }
    }

    private void updateStatusBar() {
        if (this.hostActivity != null) {
            this.hostActivity.getClass().getName().equals("com.alipay.mobile.core.loading.impl.LoadingPage");
        }
    }
}
