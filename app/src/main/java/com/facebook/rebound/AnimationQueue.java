package com.facebook.rebound;

import com.facebook.rebound.ChoreographerCompat.FrameCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AnimationQueue {
    private final Queue<Double> mAnimationQueue = new LinkedList();
    private final List<Callback> mCallbacks = new ArrayList();
    private final ChoreographerCompat mChoreographer = ChoreographerCompat.getInstance();
    private final FrameCallback mChoreographerCallback = new FrameCallback() {
        public void doFrame(long j) {
            AnimationQueue.this.onFrame(j);
        }
    };
    private final Queue<Double> mPendingQueue = new LinkedList();
    private boolean mRunning;
    private final ArrayList<Double> mTempValues = new ArrayList<>();

    public interface Callback {
        void onFrame(Double d);
    }

    public void addValue(Double d) {
        this.mPendingQueue.add(d);
        runIfIdle();
    }

    public void addAllValues(Collection<Double> collection) {
        this.mPendingQueue.addAll(collection);
        runIfIdle();
    }

    public void clearValues() {
        this.mPendingQueue.clear();
    }

    public void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void clearCallbacks() {
        this.mCallbacks.clear();
    }

    private void runIfIdle() {
        if (!this.mRunning) {
            this.mRunning = true;
            this.mChoreographer.postFrameCallback(this.mChoreographerCallback);
        }
    }

    /* access modifiers changed from: private */
    public void onFrame(long j) {
        int i;
        Double poll = this.mPendingQueue.poll();
        if (poll != null) {
            this.mAnimationQueue.offer(poll);
            i = 0;
        } else {
            i = Math.max(this.mCallbacks.size() - this.mAnimationQueue.size(), 0);
        }
        this.mTempValues.addAll(this.mAnimationQueue);
        for (int size = this.mTempValues.size() - 1; size >= 0; size--) {
            Double d = this.mTempValues.get(size);
            int size2 = ((this.mTempValues.size() - 1) - size) + i;
            if (this.mCallbacks.size() > size2) {
                this.mCallbacks.get(size2).onFrame(d);
            }
        }
        this.mTempValues.clear();
        while (this.mAnimationQueue.size() + i >= this.mCallbacks.size()) {
            this.mAnimationQueue.poll();
        }
        if (!this.mAnimationQueue.isEmpty() || !this.mPendingQueue.isEmpty()) {
            this.mChoreographer.postFrameCallback(this.mChoreographerCallback);
        } else {
            this.mRunning = false;
        }
    }
}
