package com.alipay.mobile.antui.basic.banner;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.alipay.mobile.antui.basic.banner.BannerView.BannerViewPager;
import com.alipay.mobile.antui.basic.banner.BannerView.BaseBannerPagerAdapter;
import java.lang.ref.WeakReference;

/* compiled from: BannerView */
final class b extends Handler {
    WeakReference<BannerView> a;

    public b(BannerView bannerView) {
        this.a = new WeakReference<>(bannerView);
    }

    public final void handleMessage(Message msg) {
        super.handleMessage(msg);
        try {
            BannerView bannerView = (BannerView) this.a.get();
            if (bannerView == null) {
                Log.d("BannerView", "handleMessage, bannerView==null");
                return;
            }
            BannerViewPager pager = bannerView.mPager;
            Log.d("BannerView", "handleMessage, " + pager.getCurrentItem());
            if (bannerView.isDetached) {
                Log.w("BannerView", "BannerView isDetached,stop rotating");
            } else if (pager.isTouching) {
                Log.w("BannerView", "page isTouching");
                bannerView.doRotation();
            } else if (((BaseBannerPagerAdapter) pager.getAdapter()).getRealCount() <= 1) {
                Log.w("BannerView", "getRealCount <=1");
            } else if (pager.getCurrentItem() >= pager.getAdapter().getCount() - 1) {
                pager.setCurrentItem(0);
            } else {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        } catch (Throwable e) {
            Log.e("BannerView", "BannerView handleMessage", e);
        }
    }
}
