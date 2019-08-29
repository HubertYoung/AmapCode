package com.alipay.mobile.antui.basic.banner;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.basic.banner.BannerView.BaseBannerPagerAdapter;

/* compiled from: BannerView */
final class a implements OnClickListener {
    final /* synthetic */ int a;
    final /* synthetic */ BaseBannerPagerAdapter b;

    a(BaseBannerPagerAdapter this$0, int i) {
        this.b = this$0;
        this.a = i;
    }

    public final void onClick(View v) {
        if (this.b.bannerClickListener != null) {
            this.b.bannerClickListener.onBannerAdClick(v, this.a);
        }
        try {
            this.b.bannerView.mPager.isTouching = false;
        } catch (Exception e) {
        }
        Log.d("BannerView", "click " + this.a);
    }
}
