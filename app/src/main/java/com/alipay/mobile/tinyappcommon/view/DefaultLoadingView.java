package com.alipay.mobile.tinyappcommon.view;

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
import com.alipay.mobile.common.logging.LogCatLog;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyapp.R;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
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
    public static final String DATA_UPDATE_APPERRANCE_LOADING_BOTTOM_TIP = "UPDATE_APPEARANCE_LOADING_BOTTOM_TIP";
    public static final String MSG_UPDATE_APPEARANCE = "UPDATE_APPEARANCE";
    private static final String a = DefaultLoadingView.class.getSimpleName();
    private Context b;
    private Paint c;
    private Timer d;
    private TimerTask e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    /* access modifiers changed from: private */
    public int m;
    protected ImageButton mBackButton;
    protected TextView mBottomTip;
    protected ImageView mLoadingIcon;
    protected TextView mLoadingTitle;

    public DefaultLoadingView(Context context) {
        this(context, null);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.m = 0;
        this.b = context;
        initView();
        this.mBackButton.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                DefaultLoadingView.this.cancel();
            }
        });
    }

    public void initView() {
        this.mLoadingIcon = new ImageView(this.b);
        this.mLoadingIcon.setScaleType(ScaleType.FIT_XY);
        this.mLoadingIcon.setImageResource(R.drawable.default_loading_icon);
        this.mLoadingTitle = new TextView(this.b);
        this.mLoadingTitle.setGravity(17);
        this.mLoadingTitle.setTextColor(this.b.getResources().getColor(R.color.h5_web_loading_text));
        this.mLoadingTitle.setSingleLine();
        this.mLoadingTitle.setTextSize(1, 18.0f);
        this.mLoadingTitle.setEllipsize(TruncateAt.END);
        this.mLoadingTitle.setLayoutParams(new LayoutParams(-2, -2));
        addView(this.mLoadingIcon);
        addView(this.mLoadingTitle);
        this.mBackButton = new ImageButton(this.b);
        this.mBackButton.setBackgroundResource(R.drawable.h5_title_bar_back_btn_bg_selector);
        this.mBackButton.setImageResource(R.drawable.h5_title_bar_back_btn_selector);
        this.mBackButton.setScaleType(ScaleType.CENTER_INSIDE);
        addView(this.mBackButton);
        this.mBottomTip = new TextView(this.b);
        this.mBottomTip.setTextColor(this.b.getResources().getColor(R.color.h5_web_loading_bottom_tip_text));
        this.mBottomTip.setTextSize(12.0f);
        this.mBottomTip.setGravity(17);
        this.mBottomTip.setLayoutParams(new LayoutParams(-2, -2));
        addView(this.mBottomTip);
        this.g = this.b.getResources().getColor(R.color.h5_web_loading_dot_dark_new);
        this.h = this.b.getResources().getColor(R.color.h5_web_loading_dot_light_new);
        this.l = a(R.dimen.h5_loading_dot_size);
        this.c = new Paint();
        this.c.setStyle(Style.FILL);
        this.k = a(R.dimen.h5_loading_dot_margin);
        setBackgroundColor(this.b.getResources().getColor(R.color.h5_web_loading_default_bg));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = a(R.dimen.h5_loading_icon_size);
        this.mLoadingIcon.measure(b(size), b(size));
        int height = a(R.dimen.h5_loading_title_height);
        this.mLoadingTitle.measure(MeasureSpec.makeMeasureSpec(a(R.dimen.h5_loading_title_width), Integer.MIN_VALUE), b(height));
        this.mBottomTip.measure(b(a(R.dimen.h5_loading_bottom_tip_width)), MeasureSpec.makeMeasureSpec(a(R.dimen.h5_loading_bottom_tip_height), Integer.MIN_VALUE));
        this.mBackButton.measure(b(a(R.dimen.h5_loading_back_button_width)), b(a(R.dimen.h5_loading_titlebar_height)));
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mBackButton.layout(0, 0, this.mBackButton.getMeasuredWidth(), this.mBackButton.getMeasuredHeight());
        int offsetX = (getMeasuredWidth() - this.mLoadingIcon.getMeasuredWidth()) / 2;
        int offsetY = a(R.dimen.h5_loading_titlebar_height) + a(R.dimen.h5_loading_icon_margin_top);
        this.mLoadingIcon.layout(offsetX, offsetY, this.mLoadingIcon.getMeasuredWidth() + offsetX, this.mLoadingIcon.getMeasuredHeight() + offsetY);
        int offsetX2 = (getMeasuredWidth() - this.mLoadingTitle.getMeasuredWidth()) / 2;
        int offsetY2 = this.mLoadingIcon.getMeasuredHeight() + offsetY + a(R.dimen.h5_loading_title_margin_top);
        this.mLoadingTitle.layout(offsetX2, offsetY2, this.mLoadingTitle.getMeasuredWidth() + offsetX2, this.mLoadingTitle.getMeasuredHeight() + offsetY2);
        this.i = ((getMeasuredWidth() / 2) - this.l) - this.k;
        this.j = this.mLoadingTitle.getMeasuredHeight() + offsetY2 + a(R.dimen.h5_loading_dot_margin_top);
        int offsetX3 = (getMeasuredWidth() - this.mBottomTip.getMeasuredWidth()) / 2;
        int offsetY3 = (getMeasuredHeight() - a(R.dimen.h5_loading_bottom_tip_margin_bottom)) - this.mBottomTip.getMeasuredHeight();
        this.mBottomTip.layout(offsetX3, offsetY3, this.mBottomTip.getMeasuredWidth() + offsetX3, this.mBottomTip.getMeasuredHeight() + offsetY3);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.f) {
            this.c.setColor(this.g);
            this.i = ((getMeasuredWidth() / 2) - this.l) - this.k;
            int i2 = 0;
            while (i2 < 3) {
                this.c.setColor(this.m == i2 ? this.h : this.g);
                canvas.drawCircle((float) this.i, (float) this.j, (float) (this.l / 2), this.c);
                this.i = this.i + this.k + this.l;
                i2++;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return true;
    }

    public void startLoadingAnimation() {
        if (!this.f) {
            this.f = true;
            if (this.e == null) {
                this.e = new TimerTask() {
                    public final void run() {
                        DefaultLoadingView.this.m = DefaultLoadingView.this.m + 1;
                        if (DefaultLoadingView.this.m > 2) {
                            DefaultLoadingView.this.m = 0;
                        }
                        H5Utils.runOnMain(new Runnable() {
                            public final void run() {
                                DefaultLoadingView.this.invalidate();
                            }
                        });
                    }
                };
            }
            if (this.d == null) {
                try {
                    this.d = new Timer();
                    this.d.schedule(this.e, 0, 200);
                } catch (Throwable throwable) {
                    LogCatLog.e(a, "printMonitor error", throwable);
                }
            }
        }
    }

    public void stopLoadingAnimation() {
        this.f = false;
        if (this.d != null) {
            this.d.cancel();
        }
        if (this.e != null) {
            this.e.cancel();
        }
        invalidate();
    }

    private int a(int id) {
        return this.b.getResources().getDimensionPixelSize(id);
    }

    private static int b(int size) {
        return MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK);
    }

    public void onStart() {
        startLoadingAnimation();
    }

    public void onStop() {
        stopLoadingAnimation();
    }

    public void onHandleMessage(String msg, Map<String, Object> data) {
        if ("UPDATE_APPEARANCE".equals(msg)) {
            String bgColor = (String) data.get("UPDATE_APPEARANCE_BG_COLOR");
            if (!TextUtils.isEmpty(bgColor)) {
                setBackgroundColor(Color.parseColor(bgColor));
            }
            Drawable loadingIcon = (Drawable) data.get("UPDATE_APPEARANCE_LOADING_ICON");
            if (loadingIcon != null) {
                this.mLoadingIcon.setImageDrawable(loadingIcon);
            }
            String text = (String) data.get("UPDATE_APPEARANCE_LOADING_TEXT");
            if (text != null) {
                this.mLoadingTitle.setText(text);
            }
            String textColor = (String) data.get("UPDATE_APPEARANCE_LOADING_TEXT_COLOR");
            if (!TextUtils.isEmpty(textColor)) {
                this.mLoadingTitle.setTextColor(Color.parseColor(textColor));
            }
            String bottomTip = (String) data.get("UPDATE_APPEARANCE_LOADING_BOTTOM_TIP");
            if (bottomTip != null) {
                this.mBottomTip.setText(bottomTip);
            }
        }
    }

    public void performAnimation(final String animationType, final AnimatorListener animationListener) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            a(animationType, animationListener);
        } else {
            post(new Runnable() {
                public final void run() {
                    DefaultLoadingView.this.a(animationType, animationListener);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String animationType, AnimatorListener animationListener) {
        float titleTargetX;
        if (getParent() == null) {
            LoggerFactory.getTraceLogger().error(a, (String) "loading view has not added to parent container");
        } else if ("ANIMATION_STOP_LOADING_PREPARE".equals(animationType)) {
            this.f = false;
            int offsetTargetY = a(R.dimen.h5_loading_titlebar_height) / 4;
            if (isBackButtonVisible()) {
                titleTargetX = this.mBackButton.getX() + ((float) this.mBackButton.getMeasuredWidth());
            } else {
                titleTargetX = getTitleLeftMargin();
            }
            float titleTargetY = (float) ((a(R.dimen.h5_loading_titlebar_height) - this.mLoadingTitle.getMeasuredHeight()) / 2);
            AnimatorSet prepareStopLoadingAnimator = new AnimatorSet();
            prepareStopLoadingAnimator.setDuration(400);
            if (animationListener != null) {
                prepareStopLoadingAnimator.addListener(animationListener);
            }
            prepareStopLoadingAnimator.play(ObjectAnimator.ofFloat(this.mLoadingIcon, DictionaryKeys.CTRLXY_Y, new float[]{this.mLoadingIcon.getY(), (float) offsetTargetY})).with(ObjectAnimator.ofFloat(this.mLoadingIcon, "scaleX", new float[]{this.mLoadingIcon.getScaleX(), 0.0f})).with(ObjectAnimator.ofFloat(this.mLoadingIcon, "scaleY", new float[]{this.mLoadingIcon.getScaleY(), 0.0f})).with(ObjectAnimator.ofFloat(this.mLoadingTitle, DictionaryKeys.CTRLXY_X, new float[]{this.mLoadingTitle.getX(), titleTargetX})).with(ObjectAnimator.ofFloat(this.mLoadingTitle, DictionaryKeys.CTRLXY_Y, new float[]{this.mLoadingTitle.getY(), titleTargetY}));
            prepareStopLoadingAnimator.start();
        } else {
            super.performAnimation(animationType, animationListener);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isBackButtonVisible() {
        return true;
    }

    /* access modifiers changed from: protected */
    public float getTitleLeftMargin() {
        return 0.0f;
    }
}
