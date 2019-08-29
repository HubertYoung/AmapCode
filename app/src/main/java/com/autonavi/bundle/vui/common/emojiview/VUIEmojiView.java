package com.autonavi.bundle.vui.common.emojiview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieAnimationView.CacheStrategy;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.BalloonLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import defpackage.bfh;
import defpackage.bfy;
import defpackage.bno;

public class VUIEmojiView extends LinearLayout implements OnClickListener {
    private static final float END_PROGRESS = 1.0f;
    private static final float ERROR_START_PROGRESS = 0.7f;
    private static final float IDLE_START_PROGRESS = 0.3f;
    private static final float START_PROGRESS = 0.0f;
    private final String TAG = "VUIEmojiView";
    private AnimatorListener mAnimatorListener = new AnimatorListener() {
        public final void onAnimationStart(Animator animator) {
            if ( bno.a) {
                StringBuilder sb = new StringBuilder("onAnimationStart()-state:");
                sb.append(((State) VUIEmojiView.this.mLottieView.getTag()).a);
                bfh.a("VUIEmojiView", sb.toString());
            }
            if (VUIEmojiView.this.mViewListenner != null) {
                VUIEmojiView.this.mViewListenner.a(true);
            }
        }

        public final void onAnimationEnd(Animator animator) {
            State state = (State) VUIEmojiView.this.mLottieView.getTag();
            if (bno.a) {
                StringBuilder sb = new StringBuilder("onAnimationEnd(),state:");
                sb.append(state.a);
                sb.append(",pause:");
                sb.append(VUIEmojiView.this.mController.c);
                bfh.a("VUIEmojiView", sb.toString());
            }
            Handler handler = VUIEmojiView.this.mLottieView.getHandler();
            if (!VUIEmojiView.this.mController.c && handler != null && State.INIT == state) {
                handler.post(VUIEmojiView.this.mAniminationRunner);
            }
        }

        public final void onAnimationCancel(Animator animator) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("onAnimationCancel()，state:");
                sb.append(((State) VUIEmojiView.this.mLottieView.getTag()).a);
                bfh.a("VUIEmojiView", sb.toString());
            }
        }

        public final void onAnimationRepeat(Animator animator) {
            if (bno.a) {
                State state = (State) VUIEmojiView.this.mLottieView.getTag();
                if (State.INIT == state) {
                    StringBuilder sb = new StringBuilder("onAnimationRepeat,state:");
                    sb.append(state.a);
                    bfh.a("VUIEmojiView", sb.toString());
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Runnable mAniminationRunner = new Runnable() {
        public final void run() {
            VUIEmojiView.this.mController.a(false, false);
        }
    };
    private boolean mBShowInit = false;
    /* access modifiers changed from: private */
    public bfy mController;
    /* access modifiers changed from: private */
    public LottieAnimationView mLottieView;
    private a mOnVuiEmojiViewClickListener;
    /* access modifiers changed from: private */
    public b mViewListenner;

    public enum State {
        INIT("INIT"),
        IDLE(Configuration.IDLE),
        ERROR("ERROR");
        
        String a;

        private State(String str) {
            this.a = str;
        }
    }

    public interface a {
        void onClick(View view);
    }

    public interface b {
        void a(boolean z);
    }

    public void checkRecordPermission() {
    }

    public VUIEmojiView(Context context) {
        super(context);
        init();
    }

    public VUIEmojiView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void show(boolean z, State state) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("show():");
            sb.append(state.a);
            bfh.a("VUIEmojiView", sb.toString());
        }
        if (this.mLottieView.getVisibility() != 0) {
            this.mLottieView.setVisibility(0);
            requestLayout();
        }
        this.mLottieView.loop(false);
        if (State.INIT == state) {
            this.mLottieView.setMinAndMaxProgress(0.0f, IDLE_START_PROGRESS);
        } else if (State.IDLE == state) {
            this.mLottieView.setMinAndMaxProgress(IDLE_START_PROGRESS, ERROR_START_PROGRESS);
        } else {
            this.mLottieView.setMinAndMaxProgress(ERROR_START_PROGRESS, 1.0f);
        }
        this.mLottieView.setTag(state);
        if (!this.mBShowInit || !z) {
            this.mLottieView.playAnimation();
            return;
        }
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
        this.mLottieView.postDelayed(new Runnable() {
            public final void run() {
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
                VUIEmojiView.this.mLottieView.playAnimation();
            }
        }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    public Object getEmojiTag() {
        return this.mLottieView.getTag();
    }

    public void hide() {
        if (bno.a) {
            bfh.a("VUIEmojiView", "hide()");
        }
        this.mLottieView.pauseAnimation();
        this.mLottieView.setVisibility(8);
        if (this.mViewListenner != null) {
            this.mViewListenner.a(false);
        }
    }

    public void setOnVisibleListener(b bVar) {
        this.mViewListenner = bVar;
    }

    public void setOnVuiEmojiViewClickListener(a aVar) {
        this.mOnVuiEmojiViewClickListener = aVar;
    }

    public int getEmojiVisibility() {
        return this.mLottieView.getVisibility();
    }

    public void setShowInit(boolean z) {
        this.mBShowInit = z;
    }

    public boolean isShowInit() {
        return this.mBShowInit;
    }

    private String getLottieFilePath(State state) {
        switch (state) {
            case INIT:
            case IDLE:
            case ERROR:
                return "lottie/emoji/data.json";
            default:
                return null;
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_lottie_emojiview, this);
        setOnClickListener(this);
        this.mLottieView = (LottieAnimationView) findViewById(R.id.lottie_view);
        this.mLottieView.addAnimatorListener(this.mAnimatorListener);
        this.mLottieView.setOnClickListener(this);
        this.mLottieView.enableMergePathsForKitKatAndAbove(true);
        this.mLottieView.setAnimation(getLottieFilePath(State.INIT), CacheStrategy.Strong);
        this.mController = new bfy(this);
    }

    public void onClick(View view) {
        bfy bfy = this.mController;
        if (bno.a) {
            bfh.a("VUIEmojiController", "onClick()");
        }
        if (!VUIStateManager.f().p()) {
            if (AMapAppGlobal.getTopActivity() != null) {
                kj.a(AMapAppGlobal.getTopActivity(), new String[]{"android.permission.RECORD_AUDIO"}, (defpackage.kj.b) bfy.b);
            }
            VUIStateManager.f().q();
        } else {
            bfy.a();
        }
        if (this.mOnVuiEmojiViewClickListener != null) {
            this.mOnVuiEmojiViewClickListener.onClick(view);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mController.b();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mController.c();
    }

    public void pause() {
        if (bno.a) {
            bfh.a("VUIEmojiView", "pause()");
        }
        this.mLottieView.pauseAnimation();
        Handler handler = this.mLottieView.getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mAniminationRunner);
        }
    }
}
