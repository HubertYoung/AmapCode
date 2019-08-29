package com.autonavi.minimap.ajx3.widget.view.listener;

public interface ScrollListener {

    public interface ScrollBeginListener {
        void onDragBegin();
    }

    public interface ScrollEndListener {
        void onScrollEnd();
    }

    public interface ScrollInertiaEnd {
        void onScrollInertiaEnd();
    }
}
