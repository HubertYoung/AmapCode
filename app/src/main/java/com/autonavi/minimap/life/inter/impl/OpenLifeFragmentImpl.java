package com.autonavi.minimap.life.inter.impl;

import android.support.annotation.Nullable;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.life.common.NearbyUtils;
import com.autonavi.minimap.life.inter.IOpenLifeFragment;
import com.autonavi.minimap.life.travelguide.page.TransparentTitleWebPage;

public class OpenLifeFragmentImpl implements IOpenLifeFragment {
    public final void a(@Nullable bid bid, int i, @Nullable PageBundle pageBundle) {
        if (bid != null) {
            if (i != 1) {
                if (i != 33) {
                    switch (i) {
                        case 23:
                            a(bid, pageBundle);
                            return;
                        case 24:
                            a(bid);
                            return;
                    }
                } else {
                    b(bid, pageBundle);
                }
                return;
            }
            NearbyUtils.a(bid);
        }
    }

    private static void a(bid bid, PageBundle pageBundle) {
        if (pageBundle != null) {
            dpe.a(bid, pageBundle.getString(TrafficUtil.POIID, ""), pageBundle.getString("poiName", ""), pageBundle.getString("floor", ""), pageBundle.getBoolean("showIndoorMap", false), pageBundle.getString("classify", ""), pageBundle.getString("prefercial", ""));
        }
    }

    private static void a(bid bid) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_order_home/src/pages/OrderHome.page.js");
        pageBundle.putString("pageId", "OrderHome");
        bid.startPage(Ajx3Page.class, pageBundle);
    }

    private static void b(bid bid, PageBundle pageBundle) {
        if (pageBundle != null) {
            String string = pageBundle.getString("url", "");
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.putString("url", string);
            bid.startPage(TransparentTitleWebPage.class, pageBundle2);
        }
    }
}
