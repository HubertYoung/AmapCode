package com.autonavi.minimap.basemap.traffic.inter.impl;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.inter.ICarILlegalUtil;
import com.autonavi.minimap.controller.AppManager;

public class CarILlegalUtilImpl implements ICarILlegalUtil {
    public final void a() {
        AMapPageUtil.getPageContext();
        String illegalUrl = ConfigerHelper.getInstance().getIllegalUrl();
        StringBuilder sb = new StringBuilder();
        sb.append(illegalUrl);
        if (illegalUrl.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        sb.append("pid=0");
        if (!TextUtils.isEmpty(b())) {
            try {
                sb.append("&phone=".concat(String.valueOf(boc.a(b()))));
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        lj myLocationOrMapCenterCityInfo = AppManager.getInstance().getMyLocationOrMapCenterCityInfo(DoNotUseTool.getMapManager().getMapView());
        if (myLocationOrMapCenterCityInfo != null) {
            String valueOf = String.valueOf(myLocationOrMapCenterCityInfo.j);
            if (!TextUtils.isEmpty(valueOf)) {
                sb.append("&adcode=".concat(String.valueOf(valueOf)));
            }
        }
        StringBuilder sb2 = new StringBuilder("&token=");
        sb2.append(Uri.encode(NetworkParam.getTaobaoID(), "UTF-8"));
        sb.append(sb2.toString());
        sb.append(NetworkParam.getNetworkParam(illegalUrl));
        final String string = AMapAppGlobal.getApplication().getString(R.string.car_iileage);
        aja aja = new aja(sb.toString());
        aja.b = new ajf() {
            public final String b() {
                return string;
            }

            public final b c() {
                return new b() {
                    public final boolean a() {
                        return false;
                    }

                    public final long c() {
                        return 1000;
                    }

                    public final String b() {
                        return string;
                    }
                };
            }
        };
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a(AMapPageUtil.getPageContext(), aja);
        }
    }

    private static String b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.h;
    }
}
