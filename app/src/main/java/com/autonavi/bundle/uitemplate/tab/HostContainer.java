package com.autonavi.bundle.uitemplate.tab;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.R;
import java.util.List;

public class HostContainer extends RelativeLayout implements AnimationListener {
    private static final boolean ANIMATION_SUPPORT = false;
    private boolean isTransationLocked = false;
    /* access modifiers changed from: private */
    public View mCurrentTabContent;
    private Handler mH;
    /* access modifiers changed from: private */
    public TabBar mTabBar;
    /* access modifiers changed from: private */
    public a mTransitionAnimationListener;

    public interface a {
        void c();

        void d();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void safeCheck(View view) {
    }

    public HostContainer(Handler handler, Context context, List<bep> list) {
        super(context);
        this.mH = handler;
        this.mTabBar = new TabBar(this.mH, context, list);
        this.mTabBar.setTag("tabLayout");
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(12);
        addView(this.mTabBar, layoutParams);
        this.mTabBar.bringToFront();
    }

    public void setTransitionAnimationListener(a aVar) {
        this.mTransitionAnimationListener = aVar;
    }

    private void lockTransaction() {
        this.isTransationLocked = true;
    }

    /* access modifiers changed from: private */
    public void unlockTransaction() {
        this.isTransationLocked = false;
    }

    public boolean isTransationLocked() {
        return this.isTransationLocked;
    }

    public void initDefaultTab(View view) {
        this.mCurrentTabContent = view;
        addTabContent(view);
    }

    public void showTabContent(View view, View view2, boolean z) {
        this.mCurrentTabContent = view;
        showTabContentDirect(view, view2, z);
    }

    private void showTabContentDirect(View view, View view2, boolean z) {
        lockTransaction();
        this.mTabBar.setTabClickable(false);
        if (this.mTransitionAnimationListener != null) {
            new StringBuilder("HostContainer#showTabContentDirect#onTransitionStart ").append(this.mCurrentTabContent.getTag(R.id.tab_host_tab_tag));
            this.mTransitionAnimationListener.c();
        }
        if (view2 != null) {
            addTabContent(view2);
            if (this.mTransitionAnimationListener != null) {
                this.mTransitionAnimationListener.d();
            }
            if (this.mCurrentTabContent != null) {
                new StringBuilder("HostContainer#showTabContentDirect#setVisibility(GONE) ").append(this.mCurrentTabContent.getTag(R.id.tab_host_tab_tag));
                this.mCurrentTabContent.setVisibility(8);
            }
            unlockTransaction();
            this.mTabBar.setTabClickable(true);
        }
    }

    private void addTabContent(View view) {
        int childCount = getChildCount() - 1;
        if (childCount < 0) {
            childCount = 0;
        }
        if (indexOfChild(view) == -1) {
            addView(view, childCount);
        }
        view.setVisibility(0);
        if (indexOfChild(this.mTabBar) != getChildCount() - 1) {
            this.mTabBar.bringToFront();
        }
    }

    private void showTabContentWithAnimation(View view, View view2, boolean z) {
        Animation animation;
        Animation animation2;
        lockTransaction();
        if (view != null) {
            if (z) {
                animation2 = ber.a(ber.a(0.0f, (float) getWidth()));
            } else {
                animation2 = ber.a(ber.a(0.0f, (float) (-getWidth())));
            }
            new StringBuilder("HostContainer#showTabContentWithAnimation: current.startAnimation - ").append(view.getTag(R.id.tab_host_tab_tag));
            view.startAnimation(animation2);
        }
        if (view2 != null) {
            addTabContent(view2);
            if (z) {
                animation = ber.a(ber.a((float) (-getWidth()), 0.0f));
            } else {
                animation = ber.a(ber.a((float) getWidth(), 0.0f));
            }
            new StringBuilder("HostContainer#showTabContentWithAnimation: next.startAnimation - ").append(view2.getTag(R.id.tab_host_tab_tag));
            animation.setAnimationListener(this);
            view2.startAnimation(animation);
        }
    }

    public TabBar getTabBar() {
        return this.mTabBar;
    }

    public void onAnimationStart(Animation animation) {
        this.mTabBar.setTabClickable(false);
        StringBuilder sb = new StringBuilder("HostContainer#onAnimationStart");
        sb.append(animation);
        sb.append(" - ");
        sb.append(this.mCurrentTabContent.getTag(R.id.tab_host_tab_tag));
        this.mH.post(new Runnable() {
            public final void run() {
                if (HostContainer.this.mTransitionAnimationListener != null) {
                    HostContainer.this.mTransitionAnimationListener.c();
                }
            }
        });
    }

    public void onAnimationEnd(Animation animation) {
        StringBuilder sb = new StringBuilder("HostContainer#onAnimationEnd");
        sb.append(animation);
        sb.append(" - ");
        sb.append(this.mCurrentTabContent.getTag(R.id.tab_host_tab_tag));
        this.mH.post(new Runnable() {
            public final void run() {
                if (HostContainer.this.mTransitionAnimationListener != null) {
                    HostContainer.this.mTransitionAnimationListener.d();
                }
                if (HostContainer.this.mCurrentTabContent != null) {
                    new StringBuilder("HostContainer#onAnimationEnd#setVisibility(GONE) ").append(HostContainer.this.mCurrentTabContent.getTag(R.id.tab_host_tab_tag));
                    HostContainer.this.mCurrentTabContent.setVisibility(8);
                }
                HostContainer.this.unlockTransaction();
                HostContainer.this.mTabBar.setTabClickable(true);
            }
        });
    }

    public void destroyTabContent(View view) {
        StringBuilder sb = new StringBuilder("HostContainer#destroyTabContent - ");
        sb.append(view.getTag(R.id.tab_host_tab_tag));
        sb.append(" isTransationLocked ");
        sb.append(this.isTransationLocked);
        if (!this.isTransationLocked) {
            new StringBuilder("HostContainer#destroyTabContent#removeView - ").append(view.getTag(R.id.tab_host_tab_tag));
            removeView(view);
        }
    }
}
