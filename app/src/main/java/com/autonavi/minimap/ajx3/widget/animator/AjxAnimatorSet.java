package com.autonavi.minimap.ajx3.widget.animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.animator.interpolator.ReverseInterpolator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

class AjxAnimatorSet implements AjxAnimatable {
    private final int MODE_PLAY = 0;
    private final int MODE_REVERSE = 1;
    private final List<AjxAnimatable> mAjxAnimatableList;
    /* access modifiers changed from: private */
    public IAjxContext mAjxContext;
    /* access modifiers changed from: private */
    public final long mAnimatorId;
    private final AnimatorSet mAnimatorSet;
    /* access modifiers changed from: private */
    public int mCurState = 0;
    /* access modifiers changed from: private */
    public boolean mIsForbidEvent = false;
    private int mPlayMode = 0;
    private final AnimatorSet mReverseAnimatorSet;

    class AjxAnimatorListener implements AnimatorListener {
        private AjxAnimatorListener() {
        }

        public void onAnimationStart(Animator animator) {
            AjxAnimatorSet.this.mCurState = 1;
            if (AjxAnimatorSet.this.mIsForbidEvent) {
                AjxAnimatorSet.this.mAjxContext.getDomTree().beginForbidEvents(AjxAnimatorSet.this.getAnimatorId());
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (AjxAnimatorSet.this.mIsForbidEvent) {
                AjxAnimatorSet.this.mAjxContext.getDomTree().stopForbidEvents(AjxAnimatorSet.this.getAnimatorId());
            }
            if (4 != AjxAnimatorSet.this.mCurState && 1 == AjxAnimatorSet.this.mCurState) {
                AjxAnimatorSet.this.mCurState = 3;
                AjxAnimatorSet.this.mAjxContext.getJsContext().invokeAnimation(AjxAnimatorSet.this.mAnimatorId, "onfinish", AjxAnimatorSet.this.getPlayState());
            }
        }

        public void onAnimationCancel(Animator animator) {
            if (AjxAnimatorSet.this.mIsForbidEvent) {
                AjxAnimatorSet.this.mAjxContext.getDomTree().stopForbidEvents(AjxAnimatorSet.this.getAnimatorId());
            }
        }

        public void onAnimationRepeat(Animator animator) {
            if (AjxAnimatorSet.this.mIsForbidEvent) {
                AjxAnimatorSet.this.mAjxContext.getDomTree().beginForbidEvents(AjxAnimatorSet.this.getAnimatorId());
            }
        }
    }

    AjxAnimatorSet(@NonNull IAjxContext iAjxContext, long j) {
        this.mAjxContext = iAjxContext;
        this.mAnimatorId = j;
        this.mAnimatorSet = new AnimatorSet();
        this.mReverseAnimatorSet = new AnimatorSet();
        this.mAjxAnimatableList = new ArrayList();
        addListener();
    }

    @Nullable
    public long[] getTargetNodeIds() {
        int i;
        Vector vector = new Vector();
        Iterator<AjxAnimatable> it = this.mAjxAnimatableList.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            AjxAnimatable next = it.next();
            if (next instanceof AjxAnimator) {
                vector.add(Long.valueOf(next.getTargetNodeIds()[0]));
            } else {
                long[] targetNodeIds = next.getTargetNodeIds();
                if (targetNodeIds != null && targetNodeIds.length > 0) {
                    int length = targetNodeIds.length;
                    while (i < length) {
                        vector.add(Long.valueOf(targetNodeIds[i]));
                        i++;
                    }
                }
            }
        }
        int size = vector.size();
        if (size <= 0) {
            return null;
        }
        long[] jArr = new long[size];
        while (i < vector.size()) {
            jArr[i] = ((Long) vector.get(i)).longValue();
            i++;
        }
        return jArr;
    }

    public long getAnimatorId() {
        return this.mAnimatorId;
    }

    public void checkStartEndValue() {
        for (AjxAnimatable checkStartEndValue : this.mAjxAnimatableList) {
            checkStartEndValue.checkStartEndValue();
        }
    }

    /* access modifiers changed from: 0000 */
    public void serialAnimation(List<Animator> list) {
        this.mAnimatorSet.playSequentially(list);
        ArrayList arrayList = new ArrayList();
        ListIterator<Animator> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
        }
        while (listIterator.hasPrevious()) {
            arrayList.add(listIterator.previous());
        }
        this.mReverseAnimatorSet.playSequentially(arrayList);
    }

    /* access modifiers changed from: 0000 */
    public void parallelAnimation(List<Animator> list) {
        this.mAnimatorSet.playTogether(list);
        ArrayList arrayList = new ArrayList();
        ListIterator<Animator> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
        }
        while (listIterator.hasPrevious()) {
            arrayList.add(listIterator.previous());
        }
        this.mReverseAnimatorSet.playTogether(arrayList);
    }

    /* access modifiers changed from: 0000 */
    public void addAjxAnimatable(AjxAnimatable ajxAnimatable) {
        this.mAjxAnimatableList.add(ajxAnimatable);
    }

    public Animator getAnimator() {
        return this.mAnimatorSet;
    }

    public void play() {
        if (2 == this.mCurState) {
            resume();
            return;
        }
        checkStartEndValue();
        this.mPlayMode = 0;
        this.mAnimatorSet.start();
        this.mCurState = 1;
    }

    private void addListener() {
        this.mAnimatorSet.addListener(new AjxAnimatorListener());
        this.mReverseAnimatorSet.addListener(new AjxAnimatorListener());
    }

    public void pause() {
        if (3 != this.mCurState) {
            this.mCurState = 2;
            if (this.mPlayMode == 0) {
                for (AjxAnimatable pause : this.mAjxAnimatableList) {
                    pause.pause();
                }
                return;
            }
            if (this.mPlayMode == 1) {
                ListIterator<AjxAnimatable> listIterator = this.mAjxAnimatableList.listIterator();
                while (listIterator.hasNext()) {
                    listIterator.next();
                }
                while (listIterator.hasPrevious()) {
                    listIterator.previous().pause();
                }
            }
        }
    }

    public void resume() {
        this.mCurState = 1;
        if (this.mPlayMode == 0) {
            this.mAnimatorSet.start();
            for (AjxAnimatable resume : this.mAjxAnimatableList) {
                resume.resume();
            }
            return;
        }
        if (this.mPlayMode == 1) {
            this.mReverseAnimatorSet.start();
            ListIterator<AjxAnimatable> listIterator = this.mAjxAnimatableList.listIterator();
            while (listIterator.hasNext()) {
                listIterator.next();
            }
            while (listIterator.hasPrevious()) {
                listIterator.previous().resume();
            }
        }
    }

    public void cancel() {
        if (this.mCurState != 0 && 4 != this.mCurState) {
            this.mAnimatorSet.cancel();
            this.mCurState = 3;
            if (this.mPlayMode == 0) {
                for (AjxAnimatable cancel : this.mAjxAnimatableList) {
                    cancel.cancel();
                }
            } else if (this.mPlayMode == 1) {
                ListIterator<AjxAnimatable> listIterator = this.mAjxAnimatableList.listIterator();
                while (listIterator.hasNext()) {
                    listIterator.next();
                }
                while (listIterator.hasPrevious()) {
                    listIterator.previous().cancel();
                }
            }
            this.mAjxContext.getJsContext().invokeAnimation(this.mAnimatorId, "oncancel", getPlayState());
        }
    }

    public void finish() {
        if (this.mPlayMode == 0) {
            this.mAnimatorSet.end();
            return;
        }
        if (this.mPlayMode == 1) {
            this.mReverseAnimatorSet.end();
        }
    }

    public void reverse() {
        checkStartEndValue();
        this.mPlayMode = 1;
        this.mReverseAnimatorSet.setInterpolator(new ReverseInterpolator());
        this.mReverseAnimatorSet.start();
    }

    public void destroy() {
        this.mCurState = 4;
        this.mAnimatorSet.removeAllListeners();
        this.mReverseAnimatorSet.removeAllListeners();
        for (AjxAnimatable destroy : this.mAjxAnimatableList) {
            destroy.destroy();
        }
        if (this.mAnimatorSet.isRunning()) {
            this.mAnimatorSet.cancel();
        }
        if (this.mReverseAnimatorSet.isRunning()) {
            this.mReverseAnimatorSet.cancel();
        }
    }

    public String getPlayState() {
        switch (this.mCurState) {
            case 0:
                return "pending";
            case 1:
                return MiscUtils.KEY_RUNNING;
            case 2:
                return "paused";
            case 3:
                return "finish";
            case 4:
                return "destroy";
            default:
                return "pending";
        }
    }

    public boolean isForbidEvent() {
        return this.mIsForbidEvent;
    }

    public void setForbidEventFlag(boolean z) {
        this.mIsForbidEvent = z;
    }
}
