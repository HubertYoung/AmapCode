package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadEngine;

public class PauseRecyclerViewOnScrollListener extends OnScrollListener {
    private final ImageLoadEngine a;
    private final boolean b;
    private final OnScrollListener c;

    public PauseRecyclerViewOnScrollListener(ImageLoadEngine loadEngine, boolean pauseOnScroll) {
        this(loadEngine, pauseOnScroll, null);
    }

    public PauseRecyclerViewOnScrollListener(ImageLoadEngine loadEngine, boolean pauseOnScroll, OnScrollListener customListener) {
        this.a = loadEngine;
        this.b = pauseOnScroll;
        this.c = customListener;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case 0:
                this.a.resume();
                break;
            case 1:
                this.a.resume();
                break;
            case 2:
                if (this.b) {
                    this.a.pause();
                    break;
                }
                break;
        }
        if (this.c != null) {
            this.c.onScrollStateChanged(recyclerView, newState);
        }
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (this.c != null) {
            this.c.onScrolled(recyclerView, dx, dy);
        }
    }
}
