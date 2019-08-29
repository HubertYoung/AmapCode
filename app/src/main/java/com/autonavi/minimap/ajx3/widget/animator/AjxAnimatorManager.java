package com.autonavi.minimap.ajx3.widget.animator;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.JsDomAnimation;
import com.autonavi.minimap.ajx3.dom.JsDomAnimationSet;
import com.autonavi.minimap.ajx3.dom.JsDomEventAnimation;
import com.autonavi.minimap.ajx3.dom.JsDomEventAnimationGroup;
import com.autonavi.minimap.ajx3.dom.managers.AjxUiEventManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class AjxAnimatorManager extends AjxUiEventManager {
    private LongSparseArray<AjxAnimatable> mAnimatorSparseArray = new LongSparseArray<>();
    private Set<Long> mNeedPostAnimatorSet = new HashSet();

    public AjxAnimatorManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void destroy() {
        for (int i = 0; i < this.mAnimatorSparseArray.size(); i++) {
            AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(this.mAnimatorSparseArray.keyAt(i));
            if (ajxAnimatable != null) {
                ajxAnimatable.destroy();
            }
        }
        this.mAnimatorSparseArray.clear();
        this.mNeedPostAnimatorSet.clear();
    }

    public boolean finishAnimation(long j, IAjxContext iAjxContext) {
        final AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(j);
        if (ajxAnimatable == null) {
            return false;
        }
        if (this.mNeedPostAnimatorSet.contains(Long.valueOf(j))) {
            iAjxContext.getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    ajxAnimatable.finish();
                }
            });
        } else {
            ajxAnimatable.finish();
        }
        return true;
    }

    public boolean animateUpdateForbidFlag(JsDomEventAnimation jsDomEventAnimation) {
        AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(jsDomEventAnimation.animationId);
        if (ajxAnimatable == null) {
            return false;
        }
        ajxAnimatable.setForbidEventFlag(true);
        return true;
    }

    public boolean newAnimation(JsDomEventAnimation jsDomEventAnimation, IAjxContext iAjxContext) {
        try {
            long j = jsDomEventAnimation.nodeId;
            long j2 = jsDomEventAnimation.animationId;
            JsDomAnimation jsDomAnimation = jsDomEventAnimation.animation;
            if (jsDomAnimation != null) {
                Object obj = jsDomAnimation.keyFrames;
                Object obj2 = jsDomAnimation.options;
                if ((obj instanceof JSONArray) && (obj2 instanceof JSONObject)) {
                    AjxAnimator ajxAnimator = new AjxAnimator(iAjxContext, (JSONArray) obj, (JSONObject) obj2, j, j2);
                    this.mAnimatorSparseArray.put(j2, ajxAnimator);
                    return true;
                }
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean playAnimation(long j, @NonNull IAjxContext iAjxContext) {
        final AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(j);
        boolean z = false;
        if (ajxAnimatable == null) {
            return false;
        }
        long[] targetNodeIds = ajxAnimatable.getTargetNodeIds();
        if (targetNodeIds != null && targetNodeIds.length > 0) {
            int length = targetNodeIds.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (iAjxContext.getDomTree().findViewByNodeId(targetNodeIds[i]) == null) {
                    this.mNeedPostAnimatorSet.add(Long.valueOf(j));
                    z = true;
                    break;
                }
                i++;
            }
        }
        if (z) {
            iAjxContext.getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    ajxAnimatable.play();
                }
            });
        } else {
            ajxAnimatable.play();
        }
        return true;
    }

    public boolean pauseAnimation(long j, IAjxContext iAjxContext) {
        final AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(j);
        if (ajxAnimatable == null) {
            return false;
        }
        if (this.mNeedPostAnimatorSet.contains(Long.valueOf(j))) {
            iAjxContext.getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    ajxAnimatable.pause();
                }
            });
        } else {
            ajxAnimatable.pause();
        }
        return true;
    }

    public boolean reverseAnimation(long j, IAjxContext iAjxContext) {
        final AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(j);
        boolean z = false;
        if (ajxAnimatable == null) {
            return false;
        }
        long[] targetNodeIds = ajxAnimatable.getTargetNodeIds();
        int length = targetNodeIds.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (iAjxContext.getDomTree().findViewByNodeId(targetNodeIds[i]) == null) {
                this.mNeedPostAnimatorSet.add(Long.valueOf(j));
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            iAjxContext.getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    ajxAnimatable.reverse();
                }
            });
        } else {
            ajxAnimatable.reverse();
        }
        return true;
    }

    public boolean cancelAnimation(long j, IAjxContext iAjxContext) {
        final AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(j);
        if (ajxAnimatable == null) {
            return false;
        }
        if (this.mNeedPostAnimatorSet.contains(Long.valueOf(j))) {
            iAjxContext.getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    ajxAnimatable.cancel();
                }
            });
        } else {
            ajxAnimatable.cancel();
        }
        return true;
    }

    public boolean clearAnimation(final long j, IAjxContext iAjxContext) {
        if (this.mNeedPostAnimatorSet.contains(Long.valueOf(j))) {
            iAjxContext.getDomTree().getRootView().post(new Runnable() {
                public void run() {
                    AjxAnimatorManager.this.doClearAnimation(j);
                }
            });
        } else {
            doClearAnimation(j);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doClearAnimation(long j) {
        this.mAnimatorSparseArray.remove(j);
        this.mNeedPostAnimatorSet.remove(Long.valueOf(j));
    }

    public boolean serialAnimation(JsDomEventAnimationGroup jsDomEventAnimationGroup, IAjxContext iAjxContext) {
        JsDomAnimationSet jsDomAnimationSet = jsDomEventAnimationGroup.animationSet;
        if (jsDomAnimationSet == null || jsDomAnimationSet.childAnimationIds == null) {
            return false;
        }
        AjxAnimatorSet ajxAnimatorSet = new AjxAnimatorSet(iAjxContext, jsDomEventAnimationGroup.groupAnimationId);
        ArrayList arrayList = new ArrayList();
        for (long j : jsDomAnimationSet.childAnimationIds) {
            AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(j);
            if (ajxAnimatable != null) {
                Animator animator = ajxAnimatable.getAnimator();
                if (arrayList.contains(animator)) {
                    arrayList.add(animator.clone());
                } else {
                    arrayList.add(animator);
                }
                ajxAnimatorSet.addAjxAnimatable(ajxAnimatable);
            }
        }
        ajxAnimatorSet.serialAnimation(arrayList);
        this.mAnimatorSparseArray.put(jsDomEventAnimationGroup.groupAnimationId, ajxAnimatorSet);
        return true;
    }

    public boolean parallelAnimation(JsDomEventAnimationGroup jsDomEventAnimationGroup, IAjxContext iAjxContext) {
        JsDomAnimationSet jsDomAnimationSet = jsDomEventAnimationGroup.animationSet;
        if (jsDomAnimationSet == null || jsDomAnimationSet.childAnimationIds == null) {
            return false;
        }
        AjxAnimatorSet ajxAnimatorSet = new AjxAnimatorSet(iAjxContext, jsDomEventAnimationGroup.groupAnimationId);
        ArrayList arrayList = new ArrayList();
        for (long j : jsDomAnimationSet.childAnimationIds) {
            AjxAnimatable ajxAnimatable = (AjxAnimatable) this.mAnimatorSparseArray.get(j);
            if (ajxAnimatable != null) {
                Animator animator = ajxAnimatable.getAnimator();
                if (arrayList.contains(animator)) {
                    arrayList.add(animator.clone());
                } else {
                    arrayList.add(animator);
                }
                ajxAnimatorSet.addAjxAnimatable(ajxAnimatable);
            }
        }
        ajxAnimatorSet.parallelAnimation(arrayList);
        this.mAnimatorSparseArray.put(jsDomEventAnimationGroup.groupAnimationId, ajxAnimatorSet);
        return true;
    }
}
