package com.autonavi.minimap.ajx3.widget.animator;

import android.animation.Animator;

interface AjxAnimatable {
    void cancel();

    void checkStartEndValue();

    void destroy();

    void finish();

    Animator getAnimator();

    long getAnimatorId();

    String getPlayState();

    long[] getTargetNodeIds();

    boolean isForbidEvent();

    void pause();

    void play();

    void resume();

    void reverse();

    void setForbidEventFlag(boolean z);
}
