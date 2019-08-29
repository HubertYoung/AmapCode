package com.autonavi.minimap.drive.navi.navitts.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

public class NavigationTtsMicView extends RelativeLayout implements OnTouchListener {
    private static final int HANDLER_MSG_UPDATE_PROGRESSING_BAR = 1;
    private static final int PROGRESSING_INTERVAL_IN_MILLISECONDS = 50;
    private static final int PROGRESSING_MAX_LENGTH = 102;
    /* access modifiers changed from: private */
    public static float sCurrentProgress = 0.0f;
    /* access modifiers changed from: private */
    public static boolean sIsRingNeedUpdated = true;
    /* access modifiers changed from: private */
    public static long sTime1;
    /* access modifiers changed from: private */
    public static long sTime2;
    private GestureDetector mGestureDetector;
    private OnGestureListener mGestureListener = new OnGestureListener() {
        public final boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onLongPress(MotionEvent motionEvent) {
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public final void onShowPress(MotionEvent motionEvent) {
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            if (NavigationTtsMicView.this.mListener != null) {
                NavigationTtsMicView.this.mListener;
            }
            return true;
        }
    };
    /* access modifiers changed from: private */
    public Handler mHandler = new b(this);
    private a mInnerAniInterpolator;
    private ImageView mInnerCircle;
    private Animation mInnerCircleAni;
    /* access modifiers changed from: private */
    public boolean mIsClickable = true;
    private Animation mListenCircleAni;
    /* access modifiers changed from: private */
    public c mListener = null;
    private ImageView mMicImage;
    private ImageView mOuterCircle;
    private AnimationSet mOuterCircleAni;
    private Rect mRect = null;
    /* access modifiers changed from: private */
    public RingProgressBar mRingProgressBar;
    private d mTouchListener = null;
    /* access modifiers changed from: private */
    public Runnable mUpdateProgressBarRunnable = new Runnable() {
        public final void run() {
            if (NavigationTtsMicView.sIsRingNeedUpdated) {
                NavigationTtsMicView.this.mHandler.sendEmptyMessage(1);
            }
        }
    };

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        CENTER
    }

    static class a implements Interpolator {
        float a;
        float b;
        float c;
        private float d;
        private float e;

        public a() {
            a();
        }

        public final void a() {
            this.d = 0.0f;
            this.a = -1.0f;
            this.b = 0.0f;
            this.c = -0.8f;
            this.e = -0.8f;
        }

        public final float getInterpolation(float f) {
            float f2;
            float f3;
            this.b += f >= this.d ? f - this.d : (f + 1.0f) - this.d;
            if (this.b > 1.8f) {
                this.b -= 1.8f;
                this.c = -0.8f;
                if (this.e == -2.0f) {
                    return this.e;
                }
            }
            if (this.b <= 1.0f) {
                f2 = (this.c - this.e) * this.b;
                f3 = this.e;
            } else {
                f2 = ((this.e - this.c) / 0.8f) * (this.b - 1.0f);
                f3 = this.c;
            }
            this.a = f2 + f3;
            this.d = f;
            return this.a;
        }
    }

    static class b extends Handler {
        private WeakReference<NavigationTtsMicView> a;

        public b(NavigationTtsMicView navigationTtsMicView) {
            this.a = new WeakReference<>(navigationTtsMicView);
        }

        public final void handleMessage(Message message) {
            NavigationTtsMicView navigationTtsMicView = (NavigationTtsMicView) this.a.get();
            if (navigationTtsMicView != null && message.what == 1) {
                NavigationTtsMicView.sTime2 = Math.abs(System.nanoTime());
                NavigationTtsMicView.sCurrentProgress = NavigationTtsMicView.sCurrentProgress + (((float) (NavigationTtsMicView.sTime2 - NavigationTtsMicView.sTime1)) / 5.0E7f);
                navigationTtsMicView.mRingProgressBar.setCurrentLength(NavigationTtsMicView.sCurrentProgress);
                if (NavigationTtsMicView.sCurrentProgress >= 102.0f) {
                    NavigationTtsMicView.sIsRingNeedUpdated = false;
                } else {
                    postDelayed(navigationTtsMicView.mUpdateProgressBarRunnable, 50);
                    NavigationTtsMicView.sTime1 = NavigationTtsMicView.sTime2;
                }
            }
        }
    }

    public interface c {
        void a();

        void b();
    }

    public interface d {
        boolean a(MotionEvent motionEvent);
    }

    public NavigationTtsMicView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, R.layout.navigation_tts_mic_animation_view, this);
        initView(this);
        initAnimation();
        initMicLayout(context, attributeSet);
        initListeners(context);
    }

    private void initView(View view) {
        this.mMicImage = (ImageView) view.findViewById(R.id.mic_img);
        this.mOuterCircle = (ImageView) view.findViewById(R.id.outer_circle);
        this.mInnerCircle = (ImageView) view.findViewById(R.id.inner_circle);
        this.mRingProgressBar = (RingProgressBar) view.findViewById(R.id.ring_progress_bar);
        this.mRingProgressBar.setMaxLength(102);
        this.mRingProgressBar.setCurrentLength(sCurrentProgress);
    }

    private void initAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -360.0f, 1, 0.5f, 1, 0.5f);
        this.mListenCircleAni = rotateAnimation;
        this.mListenCircleAni.setDuration(2000);
        this.mListenCircleAni.setRepeatCount(-1);
        this.mListenCircleAni.setInterpolator(new LinearInterpolator());
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(700);
        scaleAnimation.setRepeatCount(-1);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setRepeatCount(-1);
        this.mOuterCircleAni = new AnimationSet(true);
        this.mOuterCircleAni.addAnimation(scaleAnimation);
        this.mOuterCircleAni.addAnimation(alphaAnimation);
        this.mOuterCircleAni.setInterpolator(new DecelerateInterpolator());
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.95f, 2.0f, 0.95f, 2.0f, 1, 0.5f, 1, 0.5f);
        this.mInnerCircleAni = scaleAnimation2;
        this.mInnerCircleAni.setDuration(500);
        this.mInnerCircleAni.setRepeatCount(-1);
        this.mInnerAniInterpolator = new a();
        this.mInnerCircleAni.setInterpolator(this.mInnerAniInterpolator);
    }

    private void initMicLayout(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.navigation_tts_mic);
        float dimension = obtainStyledAttributes.getDimension(R.styleable.voice_style_bottom_distance, 0.0f);
        if (dimension != 0.0f) {
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            int i = (int) dimension;
            layoutParams.bottomMargin = i;
            layoutParams.setMargins(0, 0, 0, i);
            layoutParams.addRule(12);
            this.mMicImage.setLayoutParams(layoutParams);
        }
        obtainStyledAttributes.recycle();
    }

    private void initListeners(Context context) {
        this.mGestureDetector = new GestureDetector(context, this.mGestureListener);
        this.mMicImage.setOnTouchListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        stopAnimations();
        stopProgressing();
        super.onDetachedFromWindow();
    }

    public void setClickable(boolean z) {
        super.setClickable(z);
        this.mMicImage.setClickable(z);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mTouchListener != null) {
            this.mTouchListener.a(motionEvent);
        }
        if (!this.mIsClickable) {
            return true;
        }
        if (this.mGestureDetector.onTouchEvent(motionEvent)) {
            this.mIsClickable = false;
            view.postDelayed(new Runnable() {
                public final void run() {
                    NavigationTtsMicView.this.mIsClickable = true;
                }
            }, 300);
        }
        if (this.mListener == null) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.mListener.a();
                return true;
            case 1:
                if (isInChangeImageZone(view, (int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                    Direction direction = Direction.CENTER;
                } else {
                    Direction direction2 = Direction.UP;
                }
                this.mListener.b();
                return true;
            case 2:
                if (isInChangeImageZone(view, (int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                    Direction direction3 = Direction.CENTER;
                } else {
                    Direction direction4 = Direction.UP;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean isInChangeImageZone(View view, int i, int i2) {
        if (this.mRect == null) {
            this.mRect = new Rect();
        }
        view.getDrawingRect(this.mRect);
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        this.mRect.left = iArr[0];
        this.mRect.top = iArr[1];
        this.mRect.right += iArr[0];
        this.mRect.bottom += iArr[1];
        return this.mRect.contains(i, i2);
    }

    public void startAnimations() {
        startProgressing();
        this.mOuterCircleAni.reset();
        this.mOuterCircle.setVisibility(0);
        this.mOuterCircle.startAnimation(this.mOuterCircleAni);
        this.mInnerAniInterpolator.a();
        this.mInnerCircleAni.reset();
        this.mInnerCircle.setVisibility(0);
        this.mInnerCircle.startAnimation(this.mInnerCircleAni);
        this.mMicImage.setImageResource(R.drawable.navitts_record_pressed);
    }

    public void stopAnimations() {
        this.mOuterCircleAni.cancel();
        this.mOuterCircle.clearAnimation();
        this.mOuterCircle.setVisibility(8);
        this.mInnerCircleAni.cancel();
        this.mInnerCircle.clearAnimation();
        this.mInnerCircle.setVisibility(8);
        this.mMicImage.setImageResource(R.drawable.navitts_record_normal);
        stopProgressing();
    }

    public void setVolume(int i) {
        a aVar = this.mInnerAniInterpolator;
        int i2 = (i - 17) << 2;
        if (i2 != 0) {
            float min = Math.min(((float) i2) / 30.0f, 2.0f);
            if (min >= aVar.a && Math.abs(min - aVar.c) >= 0.05f) {
                aVar.c = min;
                aVar.b = (aVar.a - -0.8f) / (aVar.c - -0.8f);
            }
        }
    }

    private void startProgressing() {
        sIsRingNeedUpdated = true;
        this.mRingProgressBar.setVisibility(0);
        this.mHandler.postDelayed(this.mUpdateProgressBarRunnable, 50);
        sTime1 = Math.abs(System.nanoTime());
    }

    private void stopProgressing() {
        sIsRingNeedUpdated = false;
        sCurrentProgress = 0.0f;
        sTime1 = 0;
        sTime2 = 0;
        this.mRingProgressBar.setCurrentLength(sCurrentProgress);
        this.mRingProgressBar.setVisibility(8);
    }

    public void setListener(c cVar) {
        this.mListener = cVar;
    }

    public void setTouchListener(d dVar) {
        this.mTouchListener = dVar;
    }
}
