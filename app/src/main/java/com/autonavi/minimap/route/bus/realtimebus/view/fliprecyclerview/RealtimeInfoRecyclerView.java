package com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class RealtimeInfoRecyclerView extends RecyclerView {
    /* access modifiers changed from: private */
    public int MarginBottom;
    /* access modifiers changed from: private */
    public int ScreenHight;
    /* access modifiers changed from: private */
    public int ScreenWidth;
    private boolean canTouch;
    private LayoutAnimationController mGetInController;
    /* access modifiers changed from: private */
    public RealtimeInfoLayoutManager mRealtimeInfoLayoutManager;
    /* access modifiers changed from: private */
    public a mRealtimeInfoStatusListener;

    public interface a {
        void a();

        void a(int i);
    }

    public RealtimeInfoRecyclerView(Context context) {
        this(context, null);
    }

    public RealtimeInfoRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RealtimeInfoRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.canTouch = true;
        this.ScreenWidth = ags.a(getContext()).width() / 2;
        this.ScreenHight = ags.a(getContext()).height();
        this.MarginBottom = agn.a(getContext(), 44.0f);
    }

    public void setRealtimeInfoStatusListener(a aVar) {
        this.mRealtimeInfoStatusListener = aVar;
    }

    public void setTouchable(boolean z) {
        this.canTouch = z;
    }

    public void initAnimationController() {
        this.mRealtimeInfoLayoutManager = new RealtimeInfoLayoutManager(getContext());
        setLayoutManager(this.mRealtimeInfoLayoutManager);
        this.mGetInController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_from_bottom);
        this.mGetInController.setInterpolator(new AccelerateInterpolator());
    }

    public void startEnterAnimation() {
        clearDisappearingChildren();
        clearAnimation();
        this.mRealtimeInfoLayoutManager.setAutoMeasureEnabled(false);
        this.mRealtimeInfoLayoutManager.setMeasurementCacheEnabled(false);
        this.mRealtimeInfoLayoutManager.a = false;
        this.mRealtimeInfoLayoutManager.setMeasurementCacheEnabled(false);
        this.mRealtimeInfoLayoutManager.setAutoMeasureEnabled(false);
        this.mRealtimeInfoLayoutManager.a = false;
        setEnabled(false);
        setTouchable(false);
        updateAnimationStatus(true);
        setLayoutAnimation(this.mGetInController);
        setVisibility(0);
        final ArrayList arrayList = new ArrayList();
        this.mGetInController.getAnimation().setAnimationListener(new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
                arrayList.add(animation);
            }

            public final void onAnimationEnd(Animation animation) {
                arrayList.remove(animation);
                if (arrayList.isEmpty()) {
                    RealtimeInfoRecyclerView.this.post(new Runnable() {
                        public final void run() {
                            RealtimeInfoRecyclerView.this.setEnabled(true);
                            RealtimeInfoRecyclerView.this.setTouchable(true);
                            RealtimeInfoRecyclerView.this.mRealtimeInfoLayoutManager.a = true;
                            if (RealtimeInfoRecyclerView.this.mRealtimeInfoStatusListener != null) {
                                RealtimeInfoRecyclerView.this.mRealtimeInfoStatusListener.a();
                            }
                            RealtimeInfoRecyclerView.this.clearDisappearingChildren();
                            RealtimeInfoRecyclerView.this.updateAnimationStatus(false);
                        }
                    });
                }
            }
        });
        startLayoutAnimation();
        requestLayout();
    }

    /* access modifiers changed from: private */
    public void updateAnimationStatus(boolean z) {
        Adapter adapter = getAdapter();
        if (adapter instanceof dyy) {
            dyy dyy = (dyy) adapter;
            dyy.e = z;
            if (!z) {
                if (dyy.d != null) {
                    dyy.a(dyy.d);
                    dyy.d = null;
                }
                if (dyy.c != null) {
                    dyy.a(dyy.c);
                    dyy.c = null;
                }
            }
        }
    }

    public void startNewOutAnimation(int i, final int i2) {
        postDelayed(new Runnable() {
            public final void run() {
                int childCount = RealtimeInfoRecyclerView.this.getChildCount();
                ArrayList arrayList = new ArrayList();
                AnimatorSet animatorSet = new AnimatorSet();
                RealtimeInfoRecyclerView.this.clearAnimation();
                for (int i = childCount - 1; i > 0; i--) {
                    ZoomBoardViewBg zoomBoardViewBg = (ZoomBoardViewBg) RealtimeInfoRecyclerView.this.getChildAt(i).findViewById(R.id.realtime_info_item_summary_tip);
                    if (zoomBoardViewBg != null) {
                        final View findViewById = RealtimeInfoRecyclerView.this.getChildAt(i).findViewById(R.id.realtime_info_item_left_tip);
                        final View childAt = RealtimeInfoRecyclerView.this.getChildAt(i);
                        zoomBoardViewBg.clearAnimation();
                        childAt.clearAnimation();
                        findViewById.clearAnimation();
                        final float access$500 = ((float) (RealtimeInfoRecyclerView.this.ScreenWidth - (zoomBoardViewBg.getWidth() / 2))) - ((View) zoomBoardViewBg.getParent()).getX();
                        final float access$300 = (((float) ((RealtimeInfoRecyclerView.this.ScreenHight - childAt.getHeight()) - RealtimeInfoRecyclerView.this.MarginBottom)) - childAt.getY()) - RealtimeInfoRecyclerView.this.getY();
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                        ofFloat.setDuration(220);
                        ofFloat.setInterpolator(new AccelerateInterpolator());
                        final boolean z = !zoomBoardViewBg.getSelectItem();
                        if (!z) {
                            childAt.bringToFront();
                            zoomBoardViewBg.bringToFront();
                            RealtimeInfoRecyclerView.this.bringChildToFront(childAt);
                        }
                        AnonymousClass1 r8 = new AnimatorUpdateListener() {
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                float f2 = 1.0f - (2.0f * floatValue);
                                findViewById.setAlpha(f2);
                                if (z) {
                                    childAt.setAlpha(f2);
                                } else {
                                    childAt.bringToFront();
                                    RealtimeInfoRecyclerView.this.bringChildToFront(childAt);
                                }
                                childAt.setTranslationX(access$500 * floatValue);
                                childAt.setTranslationY(floatValue * access$300);
                            }
                        };
                        ofFloat.addUpdateListener(r8);
                        int i2 = ((childCount - i) * 110) + 110;
                        ofFloat.setStartDelay(i2 > 800 ? 800 : (long) i2);
                        arrayList.add(ofFloat);
                    }
                }
                RealtimeInfoRecyclerView.this.mRealtimeInfoLayoutManager.a = false;
                if (arrayList.size() > 0) {
                    ValueAnimator[] valueAnimatorArr = new ValueAnimator[arrayList.size()];
                    arrayList.toArray(valueAnimatorArr);
                    animatorSet.playTogether(valueAnimatorArr);
                    animatorSet.start();
                    animatorSet.addListener(new AnimatorListener() {
                        public final void onAnimationCancel(Animator animator) {
                        }

                        public final void onAnimationRepeat(Animator animator) {
                        }

                        public final void onAnimationStart(Animator animator) {
                        }

                        public final void onAnimationEnd(Animator animator) {
                            RealtimeInfoRecyclerView.this.updateAnimationStatus(false);
                            RealtimeInfoRecyclerView.this.setEnabled(true);
                            RealtimeInfoRecyclerView.this.setTouchable(true);
                            RealtimeInfoRecyclerView.this.clearDisappearingChildren();
                            RealtimeInfoRecyclerView.this.requestLayout();
                            if (RealtimeInfoRecyclerView.this.mRealtimeInfoLayoutManager != null) {
                                RealtimeInfoRecyclerView.this.mRealtimeInfoLayoutManager.a = true;
                            }
                            if (RealtimeInfoRecyclerView.this.mRealtimeInfoStatusListener != null) {
                                RealtimeInfoRecyclerView.this.mRealtimeInfoStatusListener.a(i2);
                            }
                        }
                    });
                    return;
                }
                RealtimeInfoRecyclerView.this.updateAnimationStatus(false);
                RealtimeInfoRecyclerView.this.setEnabled(true);
                RealtimeInfoRecyclerView.this.setTouchable(true);
                RealtimeInfoRecyclerView.this.removeAllViews();
                if (RealtimeInfoRecyclerView.this.mRealtimeInfoLayoutManager != null) {
                    RealtimeInfoRecyclerView.this.mRealtimeInfoLayoutManager.a = true;
                }
                if (RealtimeInfoRecyclerView.this.mRealtimeInfoStatusListener != null) {
                    RealtimeInfoRecyclerView.this.mRealtimeInfoStatusListener.a(i2);
                }
            }
        }, 200);
        this.mRealtimeInfoLayoutManager.setMeasurementCacheEnabled(false);
        this.mRealtimeInfoLayoutManager.setAutoMeasureEnabled(false);
        this.mRealtimeInfoLayoutManager.a = false;
        setEnabled(false);
        setTouchable(false);
        updateAnimationStatus(true);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.canTouch) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
