package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadEngine;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class PauseOnPageChangeListener implements OnPageChangeListener {
    private static final String a;
    private static final Logger b;
    private final ImageLoadEngine c;
    private final boolean d;
    private final OnPageChangeListener e;

    static {
        String simpleName = PauseOnPageChangeListener.class.getSimpleName();
        a = simpleName;
        b = Logger.getLogger(simpleName);
    }

    public PauseOnPageChangeListener(ImageLoadEngine loadEngine, boolean pauseOnScroll, OnPageChangeListener customListener) {
        this.c = loadEngine;
        this.d = pauseOnScroll;
        this.e = customListener;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.e != null) {
            this.e.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (this.e != null) {
            this.e.onPageSelected(position);
        }
    }

    public void onPageScrollStateChanged(int state) {
        b.d("onPageScrollStateChanged " + state, new Object[0]);
        switch (state) {
            case 0:
                this.c.resume();
                break;
            case 1:
                if (this.d) {
                    this.c.pause();
                    break;
                }
                break;
        }
        if (this.e != null) {
            this.e.onPageScrollStateChanged(state);
        }
    }
}
