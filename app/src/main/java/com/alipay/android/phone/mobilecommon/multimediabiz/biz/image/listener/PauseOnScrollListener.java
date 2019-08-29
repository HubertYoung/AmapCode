package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.listener;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadEngine;

public class PauseOnScrollListener implements OnScrollListener {
    private final ImageLoadEngine a;
    private final boolean b;
    private final boolean c;
    private final OnScrollListener d;

    public PauseOnScrollListener(ImageLoadEngine loadEngine, boolean pauseOnScroll, boolean pauseOnFling) {
        this(loadEngine, pauseOnScroll, pauseOnFling, null);
    }

    public PauseOnScrollListener(ImageLoadEngine loadEngine, boolean pauseOnScroll, boolean pauseOnFling, OnScrollListener customListener) {
        this.a = loadEngine;
        this.b = pauseOnScroll;
        this.c = pauseOnFling;
        this.d = customListener;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case 0:
                this.a.resume();
                break;
            case 1:
                if (this.b) {
                    this.a.pause();
                    break;
                }
                break;
            case 2:
                if (this.c) {
                    this.a.pause();
                    break;
                }
                break;
        }
        if (this.d != null) {
            this.d.onScrollStateChanged(view, scrollState);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.d != null) {
            this.d.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}
