package com.autonavi.minimap.route.foot.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.location.Criteria;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.minimap.R;
import java.math.BigDecimal;

public class CompassView extends RelativeLayout implements com.autonavi.minimap.route.foot.view.Compass.a {
    private static final int DEFAULT_BACKGROUND = R.drawable.compassview_bg;
    private static final int DEFAULT_CAMPASS_WIDTH = 300;
    private static final int DEFAULT_COMPASS_DESTINATION_GAP = 220;
    private static final float MAX_ROATE_DEGREE = 1.0f;
    private static final int REFRESH_TIME = 33;
    /* access modifiers changed from: private */
    public static Handler mHandler;
    private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        public final void onAnimationEnd(Animator animator) {
            CompassView.this.hideAnim();
        }
    };
    /* access modifiers changed from: private */
    public ImageView closeBtn;
    /* access modifiers changed from: private */
    public Compass compass;
    /* access modifiers changed from: private */
    public CompassBG compassBg;
    private int compassRectWidth;
    private Context context;
    private DecelerateInterpolator decelerateInterpolator;
    private float density;
    /* access modifiers changed from: private */
    public boolean hasFindDirection;
    private AnimatorSet hideAnimSet2D;
    private AnimatorSet hideAnimSet3D;
    private AnimatorListenerAdapter hideListener = new AnimatorListenerAdapter() {
        public final void onAnimationEnd(Animator animator) {
            CompassView.this.compass.setVisibility(8);
            CompassView.this.compassBg.setVisibility(8);
            CompassView.this.setVisibility(8);
            CompassView.this.isAniming = false;
            if (CompassView.this.onHidedListener != null) {
                CompassView.this.onHidedListener.a();
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean isAniming = false;
    private boolean isHeaderUpAnd3DVisual;
    protected Runnable mCompassViewUpdater = new Runnable() {
        public final void run() {
            if (CompassView.this.compass != null && !CompassView.this.mStopDrawing) {
                if (CompassView.this.mDirection != CompassView.this.mTargetDirection) {
                    float access$200 = CompassView.this.mTargetDirection;
                    if (access$200 - CompassView.this.mDirection > 180.0f) {
                        access$200 -= 360.0f;
                    } else if (access$200 - CompassView.this.mDirection < -180.0f) {
                        access$200 += 360.0f;
                    }
                    float access$600 = access$200 - CompassView.this.mDirection;
                    if (Math.abs(access$600) > 1.0f) {
                        access$600 = access$600 > 0.0f ? 1.0f : -1.0f;
                    }
                    CompassView.this.mDirection = CompassView.this.normalizeDegree(CompassView.this.mDirection + ((access$200 - CompassView.this.mDirection) * CompassView.this.mInterpolator.getInterpolation(Math.abs(access$600) > 1.0f ? 0.9f : 0.8f)));
                    CompassView.this.compass.updateDirection(CompassView.this.mDirection);
                    CompassView.this.compassBg.updateBGDirection(CompassView.this.targetDirection - (360.0f - CompassView.this.mDirection));
                }
                if (CompassView.mHandler != null) {
                    CompassView.mHandler.postDelayed(CompassView.this.mCompassViewUpdater, 33);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public float mDirection;
    /* access modifiers changed from: private */
    public AccelerateInterpolator mInterpolator;
    private LocListener mLocListener = new LocListener() {
        public final void updateNaviInfo(LocInfo locInfo) {
            if (!CompassView.this.hasFindDirection && locInfo.CompassCourse != -1.0d) {
                CompassView.this.mTargetDirection = CompassView.this.normalizeDegree(((float) locInfo.CompassCourse) * -1.0f);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mStopDrawing;
    /* access modifiers changed from: private */
    public float mTargetDirection;
    /* access modifiers changed from: private */
    public com.autonavi.minimap.route.foot.view.Compass.a onFindRightDirectionListener;
    /* access modifiers changed from: private */
    public c onHidedListener;
    private AnimatorSet showAnimSet2D;
    private AnimatorSet showAnimSet3D;
    /* access modifiers changed from: private */
    public float targetDirection;
    /* access modifiers changed from: private */
    public int transGap;
    /* access modifiers changed from: private */
    public float transY2d;
    /* access modifiers changed from: private */
    public float transY3d;
    /* access modifiers changed from: private */
    public View viewBackGround;

    class a {
        ObjectAnimator a;
        ObjectAnimator b;
        ObjectAnimator c;
        ObjectAnimator d;
        ObjectAnimator e;
        ObjectAnimator f;
        ObjectAnimator g;
        ObjectAnimator h;
        ObjectAnimator i;
        ObjectAnimator j;
        ObjectAnimator k;
        ObjectAnimator l;

        private a() {
        }

        /* synthetic */ a(CompassView compassView, byte b2) {
            this();
        }

        public final a a() {
            this.c = ObjectAnimator.ofFloat(CompassView.this.viewBackGround, "alpha", new float[]{1.0f, 0.0f});
            this.c.setDuration(500);
            this.c.setStartDelay(160);
            this.l = ObjectAnimator.ofFloat(CompassView.this.closeBtn, "alpha", new float[]{1.0f, 0.0f});
            this.l.setDuration(500);
            this.l.setStartDelay(160);
            this.a = ObjectAnimator.ofFloat(CompassView.this.compass, "scaleX", new float[]{1.0f, 0.3f, 0.3f});
            this.b = ObjectAnimator.ofFloat(CompassView.this.compass, "scaleY", new float[]{1.0f, 0.3f, 0.3f});
            this.a.setDuration(330);
            this.b.setDuration(330);
            this.f = ObjectAnimator.ofFloat(CompassView.this.compassBg, "scaleX", new float[]{1.0f, 0.3f, 0.3f});
            this.g = ObjectAnimator.ofFloat(CompassView.this.compassBg, "scaleY", new float[]{1.0f, 0.3f, 0.3f});
            this.f.setDuration(330);
            this.g.setDuration(330);
            this.h = ObjectAnimator.ofFloat(CompassView.this.compass, "translationY", new float[]{CompassView.this.transY2d, (float) CompassView.this.transGap, CompassView.this.transY3d});
            this.i = ObjectAnimator.ofFloat(CompassView.this.compassBg, "translationY", new float[]{CompassView.this.transY2d, (float) CompassView.this.transGap, CompassView.this.transY3d});
            this.h.setDuration(330);
            this.i.setDuration(330);
            this.d = ObjectAnimator.ofFloat(CompassView.this.compass, "alpha", new float[]{1.0f, 0.0f});
            this.e = ObjectAnimator.ofFloat(CompassView.this.compassBg, "alpha", new float[]{1.0f, 0.0f});
            this.d.setDuration(330);
            this.e.setDuration(330);
            this.j = ObjectAnimator.ofFloat(CompassView.this.compass, "translationY", new float[]{CompassView.this.transY3d, (float) CompassView.this.transGap, CompassView.this.transY2d});
            this.k = ObjectAnimator.ofFloat(CompassView.this.compassBg, "translationY", new float[]{CompassView.this.transY3d, (float) CompassView.this.transGap, CompassView.this.transY2d});
            this.j.setDuration(0);
            this.k.setDuration(0);
            this.j.setStartDelay(500);
            this.k.setStartDelay(500);
            return this;
        }
    }

    class b {
        ObjectAnimator a;
        ObjectAnimator b;
        ObjectAnimator c;
        ObjectAnimator d;
        ObjectAnimator e;
        ObjectAnimator f;
        ObjectAnimator g;
        ObjectAnimator h;
        ObjectAnimator i;
        ObjectAnimator j;

        private b() {
        }

        /* synthetic */ b(CompassView compassView, byte b2) {
            this();
        }

        public final b a() {
            this.a = ObjectAnimator.ofFloat(CompassView.this.viewBackGround, "alpha", new float[]{0.0f, 1.0f});
            this.j = ObjectAnimator.ofFloat(CompassView.this.closeBtn, "alpha", new float[]{0.0f, 1.0f});
            this.a.setDuration(500);
            this.j.setDuration(500);
            this.b = ObjectAnimator.ofFloat(CompassView.this.compass, "scaleX", new float[]{0.3f, 0.3f, 1.0f});
            this.c = ObjectAnimator.ofFloat(CompassView.this.compass, "scaleY", new float[]{0.3f, 0.3f, 1.0f});
            this.b.setDuration(330);
            this.c.setDuration(330);
            this.b.setStartDelay(250);
            this.c.setStartDelay(250);
            this.d = ObjectAnimator.ofFloat(CompassView.this.compassBg, "scaleX", new float[]{0.3f, 0.3f, 1.0f});
            this.e = ObjectAnimator.ofFloat(CompassView.this.compassBg, "scaleY", new float[]{0.3f, 0.3f, 1.0f});
            this.d.setDuration(330);
            this.e.setDuration(330);
            this.d.setStartDelay(250);
            this.e.setStartDelay(250);
            this.f = ObjectAnimator.ofFloat(CompassView.this.compass, "translationY", new float[]{CompassView.this.transY3d, (float) CompassView.this.transGap, CompassView.this.transY2d});
            this.g = ObjectAnimator.ofFloat(CompassView.this.compassBg, "translationY", new float[]{CompassView.this.transY3d, (float) CompassView.this.transGap, CompassView.this.transY2d});
            this.f.setDuration(330);
            this.g.setDuration(330);
            this.f.setStartDelay(250);
            this.g.setStartDelay(250);
            this.h = ObjectAnimator.ofFloat(CompassView.this.compass, "alpha", new float[]{0.0f, 1.0f});
            this.i = ObjectAnimator.ofFloat(CompassView.this.compassBg, "alpha", new float[]{0.0f, 1.0f});
            this.h.setDuration(330);
            this.i.setDuration(330);
            this.h.setStartDelay(250);
            this.i.setStartDelay(250);
            return this;
        }
    }

    public interface c {
        void a();
    }

    public CompassView(Context context2) {
        super(context2);
        this.context = context2;
        initView();
    }

    public CompassView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.context = context2;
        initView();
    }

    public CompassView(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        this.context = context2;
        initView();
    }

    private void initView() {
        this.density = this.context.getResources().getDisplayMetrics().density;
        this.transGap = this.context.getResources().getDisplayMetrics().heightPixels / 6;
        this.compassRectWidth = (int) (this.density * 300.0f);
        this.decelerateInterpolator = new DecelerateInterpolator();
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.viewBackGround = new View(this.context);
        this.viewBackGround.setBackgroundDrawable(getResources().getDrawable(DEFAULT_BACKGROUND));
        this.viewBackGround.setLayoutParams(layoutParams);
        addView(this.viewBackGround);
        LayoutParams layoutParams2 = new LayoutParams(this.compassRectWidth, this.compassRectWidth);
        layoutParams2.addRule(13);
        this.compass = new Compass(this.context);
        this.compass.setLayoutParams(layoutParams2);
        this.compass.setOnFindRightDirectionListener(this);
        this.compass.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
            }
        });
        int i = (int) ((((float) this.compassRectWidth) + (this.density * 220.0f)) - (((float) this.compassRectWidth) * 0.23f));
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(13);
        this.compassBg = new CompassBG(this.context);
        this.compassBg.setLayoutParams(layoutParams3);
        this.closeBtn = new ImageView(this.context);
        this.closeBtn.setImageResource(R.drawable.close_btn_selector);
        LayoutParams layoutParams4 = new LayoutParams(-2, -2);
        layoutParams4.addRule(14);
        layoutParams4.addRule(12);
        layoutParams4.bottomMargin = (int) (this.density * 55.0f);
        this.closeBtn.setLayoutParams(layoutParams4);
        addView(this.compassBg);
        addView(this.compass);
        addView(this.closeBtn);
        initSensor();
        initService();
        this.compass.setVisibility(4);
        this.compassBg.setVisibility(4);
        this.compassBg.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
            }
        });
        this.closeBtn.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                dys.a((String) "P00031", (String) "D005", (String) "click");
                CompassView.this.attemptToHide();
            }
        });
        setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                dys.a((String) "P00031", (String) "D005", (String) "click");
                CompassView.this.attemptToHide();
            }
        });
    }

    /* access modifiers changed from: private */
    public void attemptToHide() {
        if (!this.isAniming) {
            this.isAniming = true;
            stopCompass();
            this.compassBg.pointerhideAnim(this.animatorListenerAdapter);
        }
    }

    private void initService() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(1);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(1);
    }

    private void initSensor() {
        this.mDirection = 0.0f;
        this.mTargetDirection = 0.0f;
        this.mInterpolator = new AccelerateInterpolator();
        this.mStopDrawing = true;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.transY2d = this.compass.getTranslationY();
        this.transY3d = this.transY2d + ((float) this.transGap);
        startCompass();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopCompass();
    }

    public void clearCache() {
        if (this.compass != null) {
            this.compass.clearCache();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    private void startCompass() {
        anf.a();
        anf.a(this.mLocListener, 0);
        this.mStopDrawing = false;
        if (mHandler == null) {
            mHandler = new Handler();
        }
        if (mHandler != null) {
            mHandler.postDelayed(this.mCompassViewUpdater, 33);
        }
    }

    private void stopCompass() {
        this.mStopDrawing = true;
        anf.a(this.mLocListener);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    /* access modifiers changed from: private */
    public float normalizeDegree(float f) {
        double d = (double) ((f + 720.0f) % 360.0f);
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return this.mTargetDirection;
        }
        return new BigDecimal(d).setScale(1, 4).floatValue();
    }

    public void setOnFindRightDirectionListener(com.autonavi.minimap.route.foot.view.Compass.a aVar) {
        this.onFindRightDirectionListener = aVar;
    }

    public void setOnHidedListener(c cVar) {
        this.onHidedListener = cVar;
    }

    public void setTargetDirection(float f, float f2) {
        this.mDirection = f;
        this.mTargetDirection = f;
        this.targetDirection = f2;
        this.compass.setTargetDirection(f2);
        float f3 = 360.0f - f;
        this.compass.updateDirection(f3);
        this.compassBg.updateBGDirection(f2 + f3);
    }

    public void showAnim(boolean z) {
        this.hasFindDirection = false;
        startCompass();
        this.isHeaderUpAnd3DVisual = z;
        if (this.isHeaderUpAnd3DVisual) {
            if (this.showAnimSet3D == null) {
                initShowAnim3D();
            }
            this.showAnimSet3D.start();
            return;
        }
        if (this.showAnimSet2D == null) {
            initShowAnim2D();
        }
        this.showAnimSet2D.start();
    }

    public void hideAnim() {
        if (this.isHeaderUpAnd3DVisual) {
            if (this.hideAnimSet3D == null) {
                initHideAnim3D();
            }
            this.hideAnimSet3D.start();
            return;
        }
        if (this.hideAnimSet2D == null) {
            initHideAnim2D();
        }
        this.hideAnimSet2D.start();
    }

    public void onFindRightDirection() {
        this.hasFindDirection = true;
        this.mTargetDirection = 360.0f - this.targetDirection;
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                public final void run() {
                    dys.a((String) "P00031", (String) "D005", (String) "aim");
                    CompassView.this.attemptToHide();
                    if (CompassView.this.onFindRightDirectionListener != null) {
                        CompassView.this.onFindRightDirectionListener.onFindRightDirection();
                    }
                }
            }, 500);
        }
    }

    private void initShowAnim2D() {
        b a2 = new b(this, 0).a();
        ObjectAnimator objectAnimator = a2.b;
        ObjectAnimator objectAnimator2 = a2.a;
        ObjectAnimator objectAnimator3 = a2.d;
        ObjectAnimator objectAnimator4 = a2.e;
        ObjectAnimator objectAnimator5 = a2.c;
        ObjectAnimator objectAnimator6 = a2.f;
        ObjectAnimator objectAnimator7 = a2.g;
        ObjectAnimator objectAnimator8 = a2.h;
        ObjectAnimator objectAnimator9 = a2.i;
        ObjectAnimator objectAnimator10 = a2.j;
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationStart(Animator animator) {
                CompassView.this.compass.setVisibility(0);
            }
        });
        this.showAnimSet2D = new AnimatorSet();
        this.showAnimSet2D.playTogether(new Animator[]{objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8, objectAnimator9, objectAnimator10});
        this.showAnimSet2D.setInterpolator(this.decelerateInterpolator);
        this.showAnimSet2D.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                CompassView.this.compassBg.pointerShowAnim();
            }
        });
    }

    private void initShowAnim3D() {
        b a2 = new b(this, 0).a();
        ObjectAnimator objectAnimator = a2.b;
        ObjectAnimator objectAnimator2 = a2.a;
        ObjectAnimator objectAnimator3 = a2.d;
        ObjectAnimator objectAnimator4 = a2.e;
        ObjectAnimator objectAnimator5 = a2.c;
        ObjectAnimator objectAnimator6 = a2.f;
        ObjectAnimator objectAnimator7 = a2.g;
        ObjectAnimator objectAnimator8 = a2.h;
        ObjectAnimator objectAnimator9 = a2.i;
        ObjectAnimator objectAnimator10 = a2.j;
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationStart(Animator animator) {
                CompassView.this.compass.setVisibility(0);
            }
        });
        this.showAnimSet3D = new AnimatorSet();
        this.showAnimSet3D.playTogether(new Animator[]{objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8, objectAnimator9, objectAnimator10});
        this.showAnimSet3D.setInterpolator(this.decelerateInterpolator);
        this.showAnimSet3D.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                CompassView.this.compassBg.pointerShowAnim();
            }
        });
    }

    private void initHideAnim2D() {
        a a2 = new a(this, 0).a();
        ObjectAnimator objectAnimator = a2.c;
        ObjectAnimator objectAnimator2 = a2.f;
        ObjectAnimator objectAnimator3 = a2.g;
        ObjectAnimator objectAnimator4 = a2.a;
        ObjectAnimator objectAnimator5 = a2.b;
        ObjectAnimator objectAnimator6 = a2.d;
        ObjectAnimator objectAnimator7 = a2.e;
        ObjectAnimator objectAnimator8 = a2.l;
        ObjectAnimator objectAnimator9 = a2.j;
        ObjectAnimator objectAnimator10 = a2.k;
        this.hideAnimSet2D = new AnimatorSet();
        this.hideAnimSet2D.playTogether(new Animator[]{objectAnimator9, objectAnimator10, objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8});
        this.hideAnimSet2D.setInterpolator(this.decelerateInterpolator);
        this.hideAnimSet2D.addListener(this.hideListener);
    }

    private void initHideAnim3D() {
        a a2 = new a(this, 0).a();
        ObjectAnimator objectAnimator = a2.c;
        ObjectAnimator objectAnimator2 = a2.f;
        ObjectAnimator objectAnimator3 = a2.g;
        ObjectAnimator objectAnimator4 = a2.a;
        ObjectAnimator objectAnimator5 = a2.b;
        ObjectAnimator objectAnimator6 = a2.h;
        ObjectAnimator objectAnimator7 = a2.i;
        ObjectAnimator objectAnimator8 = a2.d;
        ObjectAnimator objectAnimator9 = a2.e;
        ObjectAnimator objectAnimator10 = a2.l;
        ObjectAnimator objectAnimator11 = a2.j;
        ObjectAnimator objectAnimator12 = a2.k;
        this.hideAnimSet3D = new AnimatorSet();
        this.hideAnimSet3D.playTogether(new Animator[]{objectAnimator11, objectAnimator12, objectAnimator10, objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8, objectAnimator9});
        this.hideAnimSet3D.setInterpolator(this.decelerateInterpolator);
        this.hideAnimSet3D.addListener(this.hideListener);
    }
}
