package com.autonavi.minimap.bundle.activities.entity;

import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.banner.net.BannerResult;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.c;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.activities.page.ActivitiesPage;
import java.lang.ref.WeakReference;

public class BannerCallBack implements Callback<BannerResult>, c {
    private WeakReference<cub> mWeakFragment;

    public BannerCallBack(cub cub) {
        this.mWeakFragment = new WeakReference<>(cub);
    }

    public void callback(BannerResult bannerResult) {
        if (!(this.mWeakFragment == null || this.mWeakFragment.get() == null)) {
            cub cub = (cub) this.mWeakFragment.get();
            if (TextUtils.isEmpty(cub.a) || !cub.a.equalsIgnoreCase(bannerResult.token)) {
                cue a = cue.a();
                if (!(bannerResult == null || bannerResult.items == null || bannerResult.items.isEmpty())) {
                    a.a.items.clear();
                    a.a.items.addAll(bannerResult.items);
                    a.a.responseTimestamp = bannerResult.responseTimestamp;
                    a.a.interval = bannerResult.interval;
                    a.a.token = bannerResult.token;
                }
                cub.a = bannerResult.token;
                cub.b = bannerResult.responseTimestamp;
                ((ActivitiesPage) cub.mPage).a();
            } else {
                cub.a("");
            }
        }
    }

    public void error(Throwable th, boolean z) {
        if (!(this.mWeakFragment == null || this.mWeakFragment.get() == null)) {
            if (th == null) {
                ((cub) this.mWeakFragment.get()).a("");
                return;
            }
            ((cub) this.mWeakFragment.get()).a(AMapAppGlobal.getApplication().getString(R.string.net_error_message));
        }
    }

    public String getLoadingMessage() {
        return AMapAppGlobal.getApplication().getString(R.string.activities_loading);
    }
}
