package com.autonavi.minimap.bundle.activities.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheInfo;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ActivityPreloadView extends FrameLayout {
    private static long MAX_DURATION = 10000;
    private static final String TAG = "ActivityPreloadView";
    private int dp30;
    private boolean isFinishing;
    private boolean mAllowFinish;
    /* access modifiers changed from: private */
    public b mAnimatorEndListener;
    private List<Animator> mAnimators;
    private ImageView mBackView;
    private Context mContext;
    private Handler mHandler;
    private LinearInterpolator mInterpolator;
    private ImageView mMaskView;
    private List<c> mNails;
    private ImageView mRayView;
    private boolean mShouldFinish;
    private ImageView mStarView;
    private long mStartDelayDelta;
    private View mTitleBarView;
    private TextView mTitleTextView;
    /* access modifiers changed from: private */
    public ImageView mVortexView;

    static class a extends Handler {
        private WeakReference<ActivityPreloadView> a;

        public a(ActivityPreloadView activityPreloadView) {
            this.a = new WeakReference<>(activityPreloadView);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            ActivityPreloadView activityPreloadView = (ActivityPreloadView) this.a.get();
            if (activityPreloadView != null) {
                switch (message.what) {
                    case 1:
                        activityPreloadView.startMaskAnimator();
                        return;
                    case 2:
                        if (message.obj != null && (message.obj instanceof View)) {
                            activityPreloadView.startNailAnimator((View) message.obj);
                            return;
                        }
                    case 3:
                        activityPreloadView.startNailShootAnimator();
                        return;
                    case 4:
                        activityPreloadView.startVortexAnimator();
                        return;
                    case 5:
                        activityPreloadView.startStarAnimator();
                        return;
                    case 6:
                        activityPreloadView.startRayAnimator();
                        return;
                    case 7:
                        activityPreloadView.startFinishAnimator();
                        break;
                }
            }
        }
    }

    public interface b {
    }

    public static class c {
        static int a;
        public ImageView b;
        public int c;
        public int d;
        public String e;

        public c(ImageView imageView, int i, int i2) {
            this.b = imageView;
            this.c = i;
            this.d = i2;
        }
    }

    public ActivityPreloadView(Context context) {
        this(context, null);
    }

    public ActivityPreloadView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityPreloadView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimators = new ArrayList();
        this.mStartDelayDelta = 2000;
        this.mInterpolator = new LinearInterpolator();
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.dp30 = agn.a(this.mContext, 30.0f);
        this.mHandler = new a(this);
        inflate(this.mContext, R.layout.view_activity_preload, this);
        initView();
    }

    private void initView() {
        this.mTitleBarView = findViewById(R.id.vg_title_bar);
        this.mBackView = (ImageView) findViewById(R.id.iv_back);
        this.mBackView.clearAnimation();
        this.mTitleTextView = (TextView) findViewById(R.id.tv_title);
    }

    private LayoutParams generateCenterLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        return layoutParams;
    }

    public void setAnimatorEndListener(b bVar) {
        this.mAnimatorEndListener = bVar;
    }

    public void setBackClickListener(OnClickListener onClickListener) {
        this.mBackView.setOnClickListener(onClickListener);
    }

    public void setTitleText(CharSequence charSequence) {
        this.mTitleTextView.setText(charSequence);
    }

    public void finish() {
        this.mShouldFinish = true;
        judgeFinish();
    }

    public void setNails(List<c> list) {
        log("setNails");
        if (this.mNails == null) {
            onDestroy();
            resetContainerState();
            this.mNails = list;
            this.mStartDelayDelta = (this.mNails == null || this.mNails.isEmpty()) ? 0 : 2000;
            initAnimationViews();
            addNailViews(list);
            addView(this.mMaskView, 0, generateDefaultLayoutParams());
            addView(this.mVortexView, generateCenterLayoutParams());
            addView(this.mStarView, generateCenterLayoutParams());
            addView(this.mRayView, generateCenterLayoutParams());
            startAnimator();
            StringBuilder sb = new StringBuilder("startAnimator = ");
            sb.append(this.mStartDelayDelta);
            log(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        onDestroy();
        super.onDetachedFromWindow();
    }

    public void onDestroy() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
        if (!this.mAnimators.isEmpty()) {
            for (Animator cancel : this.mAnimators) {
                cancel.cancel();
            }
            this.mAnimators.clear();
        }
    }

    private void startAnimator() {
        this.mHandler.sendEmptyMessage(1);
        if (this.mNails != null && !this.mNails.isEmpty()) {
            for (int i = 0; i < this.mNails.size(); i++) {
                Message obtainMessage = this.mHandler.obtainMessage(2);
                obtainMessage.obj = this.mNails.get(i).b;
                this.mHandler.sendMessageDelayed(obtainMessage, (long) (i * 100));
            }
        }
        this.mHandler.sendEmptyMessageDelayed(3, this.mStartDelayDelta);
        this.mHandler.sendEmptyMessageDelayed(6, this.mStartDelayDelta + 0);
        this.mHandler.sendEmptyMessageDelayed(4, this.mStartDelayDelta + 300);
        this.mHandler.sendEmptyMessageDelayed(5, this.mStartDelayDelta + 300);
        this.mHandler.postDelayed(new Runnable() {
            public final void run() {
                ActivityPreloadView.this.finish();
            }
        }, MAX_DURATION - 1000);
        this.mHandler.postDelayed(new Runnable() {
            public final void run() {
                ActivityPreloadView.this.allowFinish();
            }
        }, this.mStartDelayDelta + 600);
    }

    private void log(String str) {
        AMapLog.i(TAG, str);
    }

    /* access modifiers changed from: private */
    public void allowFinish() {
        this.mAllowFinish = true;
        judgeFinish();
    }

    private void judgeFinish() {
        if (!this.isFinishing && this.mShouldFinish && this.mAllowFinish) {
            this.isFinishing = true;
            this.mHandler.sendEmptyMessage(7);
        }
    }

    private void resetContainerState() {
        removeAllViews();
        addView(this.mTitleBarView);
    }

    private void addNailViews(List<c> list) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                c cVar = list.get(i);
                cVar.b.setAlpha(0.0f);
                cVar.b.setScaleY(0.5f);
                ImageView imageView = cVar.b;
                Context context = this.mContext;
                if (c.a <= 0) {
                    c.a = agn.a(context, 68.0f);
                }
                int i2 = cVar.c - (c.a / 2);
                int i3 = c.a;
                LayoutParams layoutParams = new LayoutParams(i3, i3);
                layoutParams.leftMargin = i2;
                layoutParams.topMargin = cVar.d - (c.a / 2);
                addView(imageView, 0, layoutParams);
            }
        }
    }

    private void initAnimationViews() {
        if (this.mMaskView == null) {
            this.mMaskView = new ImageView(this.mContext);
            this.mMaskView.setBackgroundColor(Color.parseColor("#4C000000"));
            this.mVortexView = new ImageView(this.mContext);
            this.mVortexView.setImageResource(R.drawable.icon_activity_vortex);
            this.mStarView = new ImageView(this.mContext);
            this.mStarView.setImageResource(R.drawable.icon_activity_star);
            this.mRayView = new ImageView(this.mContext);
            this.mRayView.setImageResource(R.drawable.icon_activity_ray);
        }
        this.mMaskView.setAlpha(0.0f);
        this.mVortexView.setAlpha(0.0f);
        this.mVortexView.setScaleX(0.5f);
        this.mVortexView.setScaleY(0.5f);
        this.mStarView.setAlpha(0.0f);
        this.mRayView.setAlpha(0.0f);
    }

    /* access modifiers changed from: private */
    public void startMaskAnimator() {
        this.mMaskView.animate().alpha(1.0f).setDuration(300).setInterpolator(this.mInterpolator).start();
    }

    /* access modifiers changed from: private */
    public void startNailAnimator(View view) {
        view.setAlpha(0.0f);
        view.setScaleY(0.5f);
        view.setScaleX(0.5f);
        view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).translationY((float) (-this.dp30)).setDuration(700).setInterpolator(new DecelerateInterpolator()).start();
    }

    /* access modifiers changed from: private */
    public void startNailShootAnimator() {
        if (this.mNails != null && !this.mNails.isEmpty()) {
            int width = getWidth() / 2;
            int height = getHeight() / 2;
            for (int i = 0; i < this.mNails.size(); i++) {
                c cVar = this.mNails.get(i);
                cVar.b.animate().translationX((float) (width - cVar.c)).translationY((float) (height - cVar.d)).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
            }
        }
    }

    /* access modifiers changed from: private */
    public void startVortexAnimator() {
        this.mVortexView.animate().alpha(1.0f).scaleX(2.0f).scaleY(2.0f).setDuration(300).setInterpolator(this.mInterpolator).start();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, ((float) (MAX_DURATION * 2)) * 0.36f});
        ofFloat.setInterpolator(this.mInterpolator);
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ActivityPreloadView.this.mVortexView.setRotation(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.setDuration(MAX_DURATION * 2);
        this.mAnimators.add(ofFloat);
        ofFloat.start();
    }

    /* access modifiers changed from: private */
    @SuppressFBWarnings({"ICAST_IDIV_CAST_TO_DOUBLE"})
    public void startStarAnimator() {
        this.mStarView.animate().alpha(1.0f).scaleX(2.0f).scaleY(2.0f).setDuration(300).setInterpolator(this.mInterpolator).start();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mStarView, APCacheInfo.EXTRA_ROTATION, new float[]{0.0f, (float) (((MAX_DURATION * 5) * 2) / 1000)});
        ofFloat.setDuration(MAX_DURATION * 2);
        ofFloat.setInterpolator(this.mInterpolator);
        this.mAnimators.add(ofFloat);
        ofFloat.start();
    }

    /* access modifiers changed from: private */
    @SuppressFBWarnings({"ICAST_IDIV_CAST_TO_DOUBLE"})
    public void startRayAnimator() {
        this.mRayView.animate().alpha(1.0f).scaleY(2.0f).scaleX(2.0f).setDuration(300).setInterpolator(this.mInterpolator).start();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mRayView, APCacheInfo.EXTRA_ROTATION, new float[]{0.0f, (float) (((MAX_DURATION * -5) * 2) / 1000)});
        ofFloat.setDuration(MAX_DURATION);
        ofFloat.setInterpolator(this.mInterpolator);
        this.mAnimators.add(ofFloat);
        ofFloat.start();
    }

    /* access modifiers changed from: private */
    public void startFinishAnimator() {
        ViewPropertyAnimator interpolator = this.mRayView.animate().scaleX(20.0f).scaleY(20.0f).setDuration(500).setInterpolator(this.mInterpolator);
        interpolator.start();
        interpolator.setListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                if (ActivityPreloadView.this.mAnimatorEndListener != null) {
                    ActivityPreloadView.this.mAnimatorEndListener;
                }
            }
        });
        ViewPropertyAnimator interpolator2 = animate().alpha(0.0f).setDuration(500).setStartDelay(500).setInterpolator(this.mInterpolator);
        interpolator2.setListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                if (ActivityPreloadView.this.mAnimatorEndListener != null) {
                    ActivityPreloadView.this.mAnimatorEndListener;
                }
            }
        });
        interpolator2.start();
    }
}
