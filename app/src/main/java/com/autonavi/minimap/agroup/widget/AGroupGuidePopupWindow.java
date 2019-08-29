package com.autonavi.minimap.agroup.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class AGroupGuidePopupWindow implements OnDismissListener, cuk {
    private static final int AUTO_DISMISS_TIME = 3225;
    private static final int DELAY_DEFAULT_SHOW_TIME = 800;
    private a mAnchorGlobalLayoutListener;
    /* access modifiers changed from: private */
    public View mAnchorView;
    /* access modifiers changed from: private */
    public Runnable mAutoDismissRunnable = new b(this, 0);
    private View mContentView;
    private defpackage.cuk.a mDismissCallback;
    private TextView mGuideTipsTV = ((TextView) this.mContentView.findViewById(R.id.agroup_guide_tips_tv));
    /* access modifiers changed from: private */
    public boolean mHasDismiss;
    private OnTouchListener mOnTouchListener;
    private bid mPageContext;
    /* access modifiers changed from: private */
    public PopupWindow mPopupWindow = new PopupWindow(this.mContentView, -2, -2, true);
    private Runnable mShowRunnable = new c(this, 0);
    private ViewTreeObserver mViewTreeObserver;

    static class a implements OnGlobalLayoutListener {
        private int[] a;
        private AGroupGuidePopupWindow b;
        private int c;
        private int d;

        a(AGroupGuidePopupWindow aGroupGuidePopupWindow) {
            this.b = aGroupGuidePopupWindow;
        }

        public final void onGlobalLayout() {
            if (this.b.mPopupWindow.isShowing() && this.b.mAnchorView != null && this.b.mAnchorView.isShown() && this.b.mAnchorView.getWindowToken() != null && !this.b.isActivityFinish()) {
                this.a = this.b.getAnchorLocation(this.b.mAnchorView);
                if (!(this.a[0] == this.c && this.a[1] == this.d)) {
                    this.c = this.a[0];
                    this.d = this.a[1];
                    this.b.mPopupWindow.setAnimationStyle(0);
                    try {
                        this.b.mPopupWindow.update(this.c, this.d, -1, -1);
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    class b implements Runnable {
        private b() {
        }

        /* synthetic */ b(AGroupGuidePopupWindow aGroupGuidePopupWindow, byte b) {
            this();
        }

        public final void run() {
            AGroupGuidePopupWindow.this.dismiss();
        }
    }

    class c implements Runnable {
        private c() {
        }

        /* synthetic */ c(AGroupGuidePopupWindow aGroupGuidePopupWindow, byte b) {
            this();
        }

        public final void run() {
            if (!AGroupGuidePopupWindow.this.mHasDismiss && AGroupGuidePopupWindow.this.mAnchorView != null && AGroupGuidePopupWindow.this.mAnchorView.isShown() && AGroupGuidePopupWindow.this.mAnchorView.getWindowToken() != null && !AGroupGuidePopupWindow.this.isActivityFinish() && !cjr.b() && !cjr.a()) {
                int[] access$300 = AGroupGuidePopupWindow.this.getAnchorLocation(AGroupGuidePopupWindow.this.mAnchorView);
                try {
                    AGroupGuidePopupWindow.this.mPopupWindow.showAtLocation(AGroupGuidePopupWindow.this.mAnchorView, 8388659, access$300[0], access$300[1]);
                } catch (Exception unused) {
                }
                aho.b(AGroupGuidePopupWindow.this.mAutoDismissRunnable);
                aho.a(AGroupGuidePopupWindow.this.mAutoDismissRunnable, 3225);
            }
        }
    }

    public AGroupGuidePopupWindow(@NonNull bid bid) {
        this.mPageContext = bid;
        this.mContentView = LayoutInflater.from(bid.getContext()).inflate(R.layout.view_agroup_guide_layout, null);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setTouchInterceptor(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                AGroupGuidePopupWindow.this.handleMotionEvent(motionEvent);
                return true;
            }
        });
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopupWindow.setOnDismissListener(this);
        this.mAnchorGlobalLayoutListener = new a(this);
    }

    public void showAtLocation(@NonNull View view) {
        showAtLocation(view, -1, null);
    }

    public void showAtLocation(@NonNull View view, int i, OnTouchListener onTouchListener) {
        this.mHasDismiss = false;
        if (!this.mPopupWindow.isShowing()) {
            if (i < 0) {
                i = 800;
            }
            this.mAnchorView = view;
            this.mOnTouchListener = onTouchListener;
            this.mViewTreeObserver = this.mAnchorView.getViewTreeObserver();
            this.mPopupWindow.setAnimationStyle(R.style.agroup_guide_popupwindow_anim_style);
            this.mAnchorView.postDelayed(this.mShowRunnable, (long) i);
            if (this.mViewTreeObserver.isAlive()) {
                this.mViewTreeObserver.removeGlobalOnLayoutListener(this.mAnchorGlobalLayoutListener);
                this.mViewTreeObserver.addOnGlobalLayoutListener(this.mAnchorGlobalLayoutListener);
            }
        }
    }

    public void setGuideTips(String str) {
        this.mGuideTipsTV.setText(str);
    }

    public boolean isShowing() {
        return this.mPopupWindow.isShowing();
    }

    public void setDismissCallback(defpackage.cuk.a aVar) {
        this.mDismissCallback = aVar;
    }

    public void dismiss() {
        this.mHasDismiss = true;
        if (this.mAnchorView != null) {
            this.mAnchorView.removeCallbacks(this.mShowRunnable);
        }
        aho.b(this.mAutoDismissRunnable);
        if (this.mPopupWindow.isShowing() && !isActivityFinish()) {
            try {
                this.mPopupWindow.dismiss();
            } catch (Exception unused) {
            }
        }
    }

    public void onDismiss() {
        removeOnGlobalLayoutListener();
        if (this.mAnchorView != null) {
            this.mAnchorView.removeCallbacks(this.mShowRunnable);
        }
        aho.b(this.mAutoDismissRunnable);
    }

    public void onDestroy() {
        this.mDismissCallback = null;
        this.mOnTouchListener = null;
        dismiss();
        removeOnGlobalLayoutListener();
        aho.b(this.mAutoDismissRunnable);
    }

    /* access modifiers changed from: private */
    public int[] getAnchorLocation(View view) {
        int[] iArr = new int[2];
        if (view == null) {
            return iArr;
        }
        view.getLocationInWindow(iArr);
        iArr[0] = iArr[0] - getContentViewWidth();
        iArr[1] = (iArr[1] - agn.a(this.mPageContext.getContext(), 28.0f)) + (view.getHeight() / 2);
        return iArr;
    }

    private void removeOnGlobalLayoutListener() {
        if (this.mViewTreeObserver != null && this.mViewTreeObserver.isAlive()) {
            this.mViewTreeObserver.removeGlobalOnLayoutListener(this.mAnchorGlobalLayoutListener);
        }
    }

    private int getContentViewWidth() {
        int width = this.mContentView.getWidth();
        if (width > 0) {
            return width;
        }
        this.mContentView.measure(0, 0);
        return this.mContentView.getMeasuredWidth();
    }

    /* access modifiers changed from: private */
    public void handleMotionEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (ahx.a(this.mAnchorView, motionEvent)) {
            if (this.mOnTouchListener != null) {
                if (action == 1) {
                    forceDismiss();
                }
                this.mOnTouchListener.onTouch(this.mAnchorView, motionEvent);
            } else if (this.mAnchorView.isClickable()) {
                forceDismiss();
                this.mAnchorView.performClick();
            } else {
                dismiss();
            }
        } else if (ahx.a(this.mContentView, motionEvent)) {
            forceDismiss();
            cjp.a("amapuri://AGroup/joinGroup", "");
        } else {
            dismiss();
        }
    }

    private void forceDismiss() {
        dismiss();
    }

    /* access modifiers changed from: private */
    public boolean isActivityFinish() {
        if (this.mPageContext == null) {
            return true;
        }
        Activity activity = this.mPageContext.getActivity();
        boolean z = activity == null || activity.isFinishing();
        if (z) {
            return true;
        }
        if (VERSION.SDK_INT >= 17) {
            z = activity.isDestroyed();
        }
        return z;
    }
}
