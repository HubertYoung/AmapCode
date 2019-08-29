package com.autonavi.map.fragmentcontainer.page.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TipContainer {
    private static final int ANIMATION_DURATION = 250;
    public static final int ANIM_DIMISS = 2;
    public static final int ANIM_NONE = 0;
    public static final int ANIM_SHOW = 4;
    private static final int TIP_TAG_KEY = 134217728;
    private final ViewGroup container;
    private volatile int currentToken = 0;
    private boolean needSaveState = true;
    private final String notTipTag = AMapAppGlobal.getApplication().getString(R.string.map_footer_not_tip);
    private HashMap<View, Integer> saveStates = new HashMap<>();
    private List<OnTipChangedListener> tipListenerList = new LinkedList();
    private final String tipTag = AMapAppGlobal.getApplication().getString(R.string.map_footer_is_tip);
    /* access modifiers changed from: private */
    public Set<View> tipsRecorder = new HashSet();

    public interface OnTipChangedListener {
        void onTipDimiss();

        void onTipShow();
    }

    private boolean dimissSpec(int i) {
        return (i & 2) > 0;
    }

    private boolean showSpec(int i) {
        return (i & 4) > 0;
    }

    public boolean onBackKeyPressed() {
        return false;
    }

    public void setView(View view) {
    }

    public TipContainer(ViewGroup viewGroup) {
        if (viewGroup == null) {
            throw new NullPointerException("container could not be null");
        }
        this.container = viewGroup;
    }

    public ViewGroup getContainer() {
        return this.container;
    }

    public LayoutParams getLayoutParams() {
        return this.container.getLayoutParams();
    }

    public void setLayoutParams(LayoutParams layoutParams) {
        this.container.setLayoutParams(layoutParams);
    }

    public void addOnTipChangedListener(OnTipChangedListener onTipChangedListener) {
        if (!this.tipListenerList.contains(onTipChangedListener)) {
            this.tipListenerList.add(onTipChangedListener);
        }
    }

    public void removeOnTipChangedListener(OnTipChangedListener onTipChangedListener) {
        this.tipListenerList.remove(onTipChangedListener);
    }

    public void addAndShowTipView(View view, int i, Callback<Integer> callback) {
        View view2;
        if (view == null) {
            throw new NullPointerException("target view was null");
        }
        ViewParent parent = view.getParent();
        if (parent == null || this.container.equals(parent)) {
            if (parent == null) {
                LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(-1, -2);
                }
                view.setVisibility(8);
                this.container.addView(view, layoutParams);
            }
            view.setTag(TIP_TAG_KEY, this.tipTag);
            boolean dimissSpec = dimissSpec(i);
            boolean showSpec = showSpec(i);
            if (view.getVisibility() != 0 || dimissSpec || showSpec) {
                boolean z = false;
                if (this.needSaveState) {
                    this.needSaveState = false;
                    saveOtherChildrenState();
                }
                if (view.getVisibility() == 0) {
                    z = true;
                }
                if (!z) {
                    Iterator<View> it = this.tipsRecorder.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            view2 = null;
                            break;
                        }
                        View next = it.next();
                        if (next.getAnimation() != null) {
                            next.getAnimation().setAnimationListener(null);
                            next.clearAnimation();
                        }
                        if (next.getVisibility() == 0) {
                            view2 = next;
                            break;
                        }
                    }
                } else {
                    view2 = view;
                }
                if (z || view2 != null) {
                    showFooterViewAndDimissOther(view, view2, showSpec, dimissSpec, callback);
                } else {
                    justShowFooter(view, showSpec, callback);
                }
            } else {
                int currentTimeMillis = (int) System.currentTimeMillis();
                this.currentToken = currentTimeMillis;
                this.tipsRecorder.add(view);
                if (callback != null) {
                    callback.callback(Integer.valueOf(currentTimeMillis));
                }
            }
        } else {
            StringBuilder sb = new StringBuilder("target view: ");
            sb.append(view);
            sb.append(" got a parent, but not my container ");
            sb.append(this.container);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void showTip(View view, int i, Callback<Integer> callback) {
        addAndShowTipView(view, i, callback);
    }

    private boolean isViewTagMatch(View view, int i, String str) {
        boolean z = false;
        if (view == null) {
            return false;
        }
        Object tag = i != 0 ? view.getTag(i) : view.getTag();
        if (tag != null && (tag instanceof String) && tag.equals(str)) {
            z = true;
        }
        return z;
    }

    private void saveOtherChildrenState() {
        this.saveStates.clear();
        int childCount = this.container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.container.getChildAt(i);
            if (childAt != null && !isViewTagMatch(childAt, TIP_TAG_KEY, this.tipTag)) {
                this.saveStates.put(childAt, Integer.valueOf(childAt.getVisibility()));
                childAt.setVisibility(8);
            }
        }
    }

    public void refreshSaveOtherChildrenState() {
        this.saveStates.clear();
        int childCount = this.container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.container.getChildAt(i);
            if (childAt != null && !isViewTagMatch(childAt, TIP_TAG_KEY, this.tipTag)) {
                this.saveStates.put(childAt, Integer.valueOf(childAt.getVisibility()));
            }
        }
    }

    private void showFooterViewAndDimissOther(final View view, View view2, final boolean z, boolean z2, final Callback<Integer> callback) {
        if (view != null && view2 != null) {
            if (view2.getVisibility() != 0) {
                justShowFooter(view, z, callback);
            } else {
                justDimissFooter(view2, z2, new Callback<Integer>() {
                    public void error(Throwable th, boolean z) {
                    }

                    public void callback(Integer num) {
                        TipContainer.this.justShowFooter(view, z, callback);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void justShowFooter(final View view, boolean z, final Callback<Integer> callback) {
        if (view != null && view.getVisibility() != 0) {
            final int currentTimeMillis = (int) System.currentTimeMillis();
            this.currentToken = currentTimeMillis;
            for (OnTipChangedListener onTipShow : this.tipListenerList) {
                onTipShow.onTipShow();
            }
            if (z) {
                bif.a(view, 250, new AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        view.setVisibility(0);
                        TipContainer.this.tipsRecorder.add(view);
                        if (callback != null) {
                            callback.callback(Integer.valueOf(currentTimeMillis));
                        }
                    }
                });
                return;
            }
            view.setVisibility(0);
            this.tipsRecorder.add(view);
            if (callback != null) {
                callback.callback(Integer.valueOf(currentTimeMillis));
            }
        }
    }

    private void justDimissFooter(final View view, boolean z, final Callback<Integer> callback) {
        if (view != null && view.getVisibility() != 8) {
            this.currentToken = 0;
            if (z) {
                bif.b(view, 250, new AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        view.setVisibility(8);
                        TipContainer.this.tipsRecorder.remove(view);
                        if (callback != null) {
                            callback.callback(Integer.valueOf(0));
                        }
                    }
                });
                return;
            }
            view.setVisibility(8);
            this.tipsRecorder.remove(view);
            if (callback != null) {
                callback.callback(Integer.valueOf(0));
            }
        }
    }

    public boolean dimissTips() {
        return dimissTips(false);
    }

    public boolean dimissTips(boolean z) {
        int childCount = this.container.getChildCount();
        boolean z2 = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = this.container.getChildAt(i);
            if (childAt != null) {
                boolean isViewTagMatch = isViewTagMatch(childAt, TIP_TAG_KEY, this.tipTag);
                if (isViewTagMatch || !isViewTagMatch(childAt, 0, this.notTipTag)) {
                    if (childAt.getVisibility() == 0) {
                        childAt.setVisibility(8);
                        if (isViewTagMatch) {
                            z2 = true;
                        }
                        this.currentToken = 0;
                    }
                } else if (this.saveStates.containsKey(childAt)) {
                    childAt.setVisibility(this.saveStates.get(childAt).intValue());
                }
            }
        }
        if (z2 || z) {
            for (int size = this.tipListenerList.size() - 1; size >= 0; size--) {
                this.tipListenerList.get(size).onTipDimiss();
            }
        }
        this.needSaveState = true;
        return z2;
    }

    public View getCurrentTips() {
        int childCount = this.container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.container.getChildAt(i);
            if (childAt != null && isViewTagMatch(childAt, TIP_TAG_KEY, this.tipTag) && !isViewTagMatch(childAt, 0, this.notTipTag) && childAt.getVisibility() == 0) {
                return childAt;
            }
        }
        return null;
    }

    public boolean isTokenAvailable(int i) {
        return i != 0 && this.currentToken == i;
    }
}
