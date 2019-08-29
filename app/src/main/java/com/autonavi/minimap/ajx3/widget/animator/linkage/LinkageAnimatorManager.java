package com.autonavi.minimap.ajx3.widget.animator.linkage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.dom.JsDomEventRelativeAnimation;
import com.autonavi.minimap.ajx3.dom.JsDomRelativeAnimation;
import com.autonavi.minimap.ajx3.dom.managers.AjxUiEventManager;
import com.autonavi.minimap.ajx3.widget.AjxView;

public class LinkageAnimatorManager extends AjxUiEventManager {
    private static final int TYPE_OBSERVER_AUTO_DIS = 1;
    private LongSparseArray<AjxLinkageAnimator> mLinkageAnimatorSparseArray = new LongSparseArray<>();
    private LongSparseArray<LongSparseArray<AjxLinkageAnimator>> mNode2AnimatorArray = new LongSparseArray<>();

    public LinkageAnimatorManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public boolean bindAnimationTarget(@NonNull AjxDomTree ajxDomTree, JsDomEventRelativeAnimation jsDomEventRelativeAnimation) {
        final long j = jsDomEventRelativeAnimation.nodeId;
        final long j2 = jsDomEventRelativeAnimation.animationId;
        JsDomRelativeAnimation jsDomRelativeAnimation = jsDomEventRelativeAnimation.relativeAnimation;
        if (jsDomRelativeAnimation == null) {
            return false;
        }
        final String str = jsDomRelativeAnimation.keyFrames;
        if (ajxDomTree.findViewByNodeId(j) != null) {
            doBindAnimationTarget(j, str, j2, this.mAjxContext);
        } else {
            AjxView rootView = ajxDomTree.getRootView();
            AnonymousClass1 r0 = new Runnable() {
                public void run() {
                    LinkageAnimatorManager.this.doBindAnimationTarget(j, str, j2, LinkageAnimatorManager.this.mAjxContext);
                }
            };
            rootView.post(r0);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doBindAnimationTarget(long j, @Nullable String str, long j2, @NonNull IAjxContext iAjxContext) {
        if (!TextUtils.isEmpty(str)) {
            JsContextRef jsContext = iAjxContext.getJsContext();
            if (jsContext != null && Ajx.getInstance().getAjxContext(jsContext.shadow()) != null && !Ajx.getInstance().getAjxContext(jsContext.shadow()).hasDestroy()) {
                AjxLinkageAnimator ajxLinkageAnimator = new AjxLinkageAnimator(j, str, j2, iAjxContext);
                this.mLinkageAnimatorSparseArray.put(j2, ajxLinkageAnimator);
                LongSparseArray longSparseArray = (LongSparseArray) this.mNode2AnimatorArray.get(j);
                if (longSparseArray == null) {
                    longSparseArray = new LongSparseArray();
                }
                longSparseArray.put(j2, ajxLinkageAnimator);
                this.mNode2AnimatorArray.put(j, longSparseArray);
            }
        }
    }

    public void propertyChange(long j) {
        LongSparseArray longSparseArray = (LongSparseArray) this.mNode2AnimatorArray.get(j);
        if (longSparseArray != null) {
            for (int i = 0; i < longSparseArray.size(); i++) {
                ((AjxLinkageAnimator) longSparseArray.get(longSparseArray.keyAt(i))).propertyChange();
            }
        }
    }

    public void savePropertyValue(long j, String str, Object obj) {
        LongSparseArray longSparseArray = (LongSparseArray) this.mNode2AnimatorArray.get(j);
        if (longSparseArray != null) {
            for (int i = 0; i < longSparseArray.size(); i++) {
                ((AjxLinkageAnimator) longSparseArray.get(longSparseArray.keyAt(i))).savePropertyValue(str, obj);
            }
        }
    }

    public void propertyChange(long j, String str, Object obj) {
        LongSparseArray longSparseArray = (LongSparseArray) this.mNode2AnimatorArray.get(j);
        if (longSparseArray != null) {
            for (int i = 0; i < longSparseArray.size(); i++) {
                AjxLinkageAnimator ajxLinkageAnimator = (AjxLinkageAnimator) longSparseArray.get(longSparseArray.keyAt(i));
                if (str == null) {
                    ajxLinkageAnimator.propertyChange();
                } else {
                    ajxLinkageAnimator.propertyChange(str, obj);
                }
            }
        }
    }

    public boolean addAnimationObserver(@NonNull AjxDomTree ajxDomTree, JsDomEventRelativeAnimation jsDomEventRelativeAnimation) {
        final long j = jsDomEventRelativeAnimation.nodeId;
        final long j2 = jsDomEventRelativeAnimation.animationId;
        final JsDomRelativeAnimation jsDomRelativeAnimation = jsDomEventRelativeAnimation.relativeAnimation;
        if (jsDomRelativeAnimation == null) {
            return false;
        }
        if (ajxDomTree.findViewByNodeId(j) != null) {
            doAddAnimationObserver(j2, j, jsDomRelativeAnimation.keyFrames, jsDomRelativeAnimation.option);
        } else {
            AjxView rootView = ajxDomTree.getRootView();
            AnonymousClass2 r0 = new Runnable() {
                public void run() {
                    LinkageAnimatorManager.this.doAddAnimationObserver(j2, j, jsDomRelativeAnimation.keyFrames, jsDomRelativeAnimation.option);
                }
            };
            rootView.post(r0);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doAddAnimationObserver(long j, long j2, @Nullable String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            boolean z = false;
            if (1 == i) {
                clearDisposableObserver();
                z = true;
            }
            AjxLinkageAnimator ajxLinkageAnimator = (AjxLinkageAnimator) this.mLinkageAnimatorSparseArray.get(j);
            if (ajxLinkageAnimator != null) {
                ajxLinkageAnimator.addObserver(j2, str, z);
            }
        }
    }

    public boolean removeRelativeAnimationByNode(AjxDomTree ajxDomTree, JsDomEventRelativeAnimation jsDomEventRelativeAnimation) {
        final long j = jsDomEventRelativeAnimation.nodeId;
        final long j2 = jsDomEventRelativeAnimation.animationId;
        if (ajxDomTree.findViewByNodeId(j) != null) {
            doRemoveRelativeAnimationByNode(j2, j);
        } else {
            AjxView rootView = ajxDomTree.getRootView();
            AnonymousClass3 r0 = new Runnable() {
                public void run() {
                    LinkageAnimatorManager.this.doRemoveRelativeAnimationByNode(j2, j);
                }
            };
            rootView.post(r0);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doRemoveRelativeAnimationByNode(long j, long j2) {
        AjxLinkageAnimator ajxLinkageAnimator = (AjxLinkageAnimator) this.mLinkageAnimatorSparseArray.get(j);
        if (ajxLinkageAnimator != null) {
            ajxLinkageAnimator.remove(j2);
        }
    }

    public boolean removeRelativeAnimations(long j) {
        AjxLinkageAnimator ajxLinkageAnimator = (AjxLinkageAnimator) this.mLinkageAnimatorSparseArray.get(j);
        if (ajxLinkageAnimator != null) {
            ajxLinkageAnimator.clear();
        }
        this.mLinkageAnimatorSparseArray.remove(j);
        for (int i = 0; i < this.mNode2AnimatorArray.size(); i++) {
            LongSparseArray longSparseArray = (LongSparseArray) this.mNode2AnimatorArray.get(this.mNode2AnimatorArray.keyAt(i));
            if (longSparseArray != null) {
                longSparseArray.remove(j);
            }
        }
        return true;
    }

    public void syncLinkageAnimators() {
        if (this.mLinkageAnimatorSparseArray != null) {
            for (int i = 0; i < this.mLinkageAnimatorSparseArray.size(); i++) {
                ((AjxLinkageAnimator) this.mLinkageAnimatorSparseArray.get(this.mLinkageAnimatorSparseArray.keyAt(i))).syncLinkageAnimator();
            }
        }
    }

    public void clearDisposableObserver() {
        if (this.mLinkageAnimatorSparseArray != null) {
            for (int i = 0; i < this.mLinkageAnimatorSparseArray.size(); i++) {
                ((AjxLinkageAnimator) this.mLinkageAnimatorSparseArray.get(this.mLinkageAnimatorSparseArray.keyAt(i))).clearDisposableObserver();
            }
        }
    }

    public void destroy() {
        if (this.mLinkageAnimatorSparseArray != null) {
            for (int i = 0; i < this.mLinkageAnimatorSparseArray.size(); i++) {
                ((AjxLinkageAnimator) this.mLinkageAnimatorSparseArray.get(this.mLinkageAnimatorSparseArray.keyAt(i))).destroy();
            }
        }
    }
}
