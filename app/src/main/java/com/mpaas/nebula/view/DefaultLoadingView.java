package com.mpaas.nebula.view;

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
import com.mpaas.nebula.adapter.R;
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
    private static final String a = DefaultLoadingView.class.getSimpleName();
    private static int b = 855638016;
    private Context c;
    private ImageView d;
    private TextView e;
    private ImageButton f;
    private View g;
    private Paint h;
    private Timer i;
    private TimerTask j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    /* access modifiers changed from: private */
    public int r;

    public DefaultLoadingView(Context context) {
        this(context, null);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.r = 0;
        this.c = context;
        initView();
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DefaultLoadingView.this.cancel();
            }
        });
    }

    public void initView() {
        this.d = new ImageView(this.c);
        this.d.setScaleType(ScaleType.FIT_XY);
        this.d.setImageResource(R.drawable.default_loading_icon);
        this.e = new TextView(this.c);
        this.e.setGravity(17);
        this.e.setTextColor(this.c.getResources().getColor(R.color.h5_web_loading_text));
        this.e.setSingleLine();
        this.e.setTextSize(16.0f);
        this.e.setEllipsize(TruncateAt.END);
        this.e.setLayoutParams(new LayoutParams(-2, -2));
        addView(this.d);
        addView(this.e);
        this.f = new ImageButton(this.c);
        this.f.setBackgroundResource(R.drawable.h5_title_bar_back_btn_bg_selector);
        this.f.setImageResource(R.drawable.h5_title_bar_back_btn_selector);
        this.f.setScaleType(ScaleType.CENTER_INSIDE);
        this.g = new View(this.c);
        this.g.setBackgroundColor(-2500135);
        addView(this.f);
        addView(this.g);
        this.l = this.c.getResources().getColor(R.color.h5_web_loading_dot_dark_new);
        this.m = this.c.getResources().getColor(R.color.h5_web_loading_dot_light_new);
        this.q = a(R.dimen.h5_loading_dot_size);
        this.h = new Paint();
        this.h.setStyle(Style.FILL);
        this.p = a(R.dimen.h5_loading_dot_margin);
        setBackgroundColor(this.c.getResources().getColor(R.color.h5_web_loading_default_bg));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = a(R.dimen.h5_loading_icon_size);
        this.d.measure(b(size), b(size));
        int height = a(R.dimen.h5_loading_title_height);
        this.e.measure(MeasureSpec.makeMeasureSpec(a(R.dimen.h5_loading_title_width), Integer.MIN_VALUE), b(height));
        this.f.measure(b(a(R.dimen.h5_loading_back_button_width)), b(a(R.dimen.h5_loading_titlebar_height)));
        this.g.measure(b(a(R.dimen.h5_loading_divider_width)), b(a(R.dimen.h5_loading_divider_height)));
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.f.layout(0, 0, this.f.getMeasuredWidth(), this.f.getMeasuredHeight());
        int offsetX = this.f.getMeasuredWidth();
        int offsetY = (a(R.dimen.h5_loading_titlebar_height) - this.g.getMeasuredHeight()) / 2;
        this.g.layout(offsetX, offsetY, this.g.getMeasuredWidth() + offsetX, this.g.getMeasuredHeight() + offsetY);
        int offsetX2 = (getMeasuredWidth() - this.d.getMeasuredWidth()) / 2;
        int offsetY2 = a(R.dimen.h5_loading_titlebar_height) + a(R.dimen.h5_loading_icon_margin_top);
        this.d.layout(offsetX2, offsetY2, this.d.getMeasuredWidth() + offsetX2, this.d.getMeasuredHeight() + offsetY2);
        int offsetX3 = (getMeasuredWidth() - this.e.getMeasuredWidth()) / 2;
        int offsetY3 = this.d.getMeasuredHeight() + offsetY2 + a(R.dimen.h5_loading_title_margin_top);
        this.e.layout(offsetX3, offsetY3, this.e.getMeasuredWidth() + offsetX3, this.e.getMeasuredHeight() + offsetY3);
        this.n = ((getMeasuredWidth() / 2) - this.q) - this.p;
        this.o = this.e.getMeasuredHeight() + offsetY3 + a(R.dimen.h5_loading_dot_margin_top);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.k) {
            this.h.setColor(this.l);
            this.n = ((getMeasuredWidth() / 2) - this.q) - this.p;
            int i2 = 0;
            while (i2 < 3) {
                this.h.setColor(this.r == i2 ? this.m : this.l);
                canvas.drawCircle((float) this.n, (float) this.o, (float) (this.q / 2), this.h);
                this.n = this.n + this.p + this.q;
                i2++;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return true;
    }

    public void startLoadingAnimation() {
        if (!this.k) {
            this.k = true;
            if (this.j == null) {
                this.j = new TimerTask() {
                    public void run() {
                        DefaultLoadingView.this.r = DefaultLoadingView.this.r + 1;
                        if (DefaultLoadingView.this.r > 2) {
                            DefaultLoadingView.this.r = 0;
                        }
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                DefaultLoadingView.this.invalidate();
                            }
                        });
                    }
                };
            }
            if (this.i == null) {
                try {
                    this.i = new Timer();
                    this.i.schedule(this.j, 0, 200);
                } catch (Throwable th) {
                }
            }
        }
    }

    public void stopLoadingAnimation() {
        this.k = false;
        if (this.i != null) {
            this.i.cancel();
        }
        if (this.j != null) {
            this.j.cancel();
        }
        invalidate();
    }

    private int a(int id) {
        return this.c.getResources().getDimensionPixelSize(id);
    }

    private static int b(int size) {
        return MeasureSpec.makeMeasureSpec(size, UCCore.VERIFY_POLICY_QUICK);
    }

    public void onStart() {
        a();
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
                this.d.setImageDrawable(loadingIcon);
            }
            String text = (String) data.get("UPDATE_APPEARANCE_LOADING_TEXT");
            if (text != null) {
                this.e.setText(text);
            }
            String textColor = (String) data.get("UPDATE_APPEARANCE_LOADING_TEXT_COLOR");
            if (!TextUtils.isEmpty(textColor)) {
                this.e.setTextColor(Color.parseColor(textColor));
            }
        }
    }

    public void performAnimation(final String animationType, final AnimatorListener animationListener) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            a(animationType, animationListener);
        } else {
            post(new Runnable() {
                public void run() {
                    DefaultLoadingView.this.a(animationType, animationListener);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String animationType, AnimatorListener animationListener) {
        if (getParent() == null) {
            LoggerFactory.getTraceLogger().error(a, (String) "loading view has not added to parent container");
        } else if ("ANIMATION_STOP_LOADING_PREPARE".equals(animationType)) {
            this.k = false;
            int offsetTargetY = a(R.dimen.h5_loading_titlebar_height) / 4;
            float titleTargetX = this.g.getX() + ((float) this.g.getMeasuredWidth()) + ((float) a(R.dimen.h5_loading_title_margin_left));
            float titleTargetY = (float) ((a(R.dimen.h5_loading_titlebar_height) - this.e.getMeasuredHeight()) / 2);
            AnimatorSet prepareStopLoadingAnimator = new AnimatorSet();
            prepareStopLoadingAnimator.setDuration(400);
            if (animationListener != null) {
                prepareStopLoadingAnimator.addListener(animationListener);
            }
            prepareStopLoadingAnimator.play(ObjectAnimator.ofFloat(this.d, DictionaryKeys.CTRLXY_Y, new float[]{this.d.getY(), (float) offsetTargetY})).with(ObjectAnimator.ofFloat(this.d, "scaleX", new float[]{this.d.getScaleX(), 0.0f})).with(ObjectAnimator.ofFloat(this.d, "scaleY", new float[]{this.d.getScaleY(), 0.0f})).with(ObjectAnimator.ofFloat(this.e, DictionaryKeys.CTRLXY_X, new float[]{this.e.getX(), titleTargetX})).with(ObjectAnimator.ofFloat(this.e, DictionaryKeys.CTRLXY_Y, new float[]{this.e.getY(), titleTargetY}));
            prepareStopLoadingAnimator.start();
        } else {
            super.performAnimation(animationType, animationListener);
        }
    }

    private void a() {
        if (this.hostActivity != null) {
            this.hostActivity.getClass().getName().equals("com.alipay.mobile.core.loading.impl.LoadingPage");
        }
    }
}
