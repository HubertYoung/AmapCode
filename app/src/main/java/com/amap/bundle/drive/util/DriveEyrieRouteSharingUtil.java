package com.amap.bundle.drive.util;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.navi.navidata.AbstractNavigationDataResult;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.d;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DriveEyrieRouteSharingUtil {
    static a a;

    static class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private AbstractNavigationDataResult mAutoNaviDataResult;
        private ph mCarPathResult;
        private Context mContext;
        private GeoPoint mGeoPoint = null;
        private POI mPoi = null;
        private dct mShareType;

        public ReverseGeocodeListener(Context context, POI poi, ph phVar, dct dct) {
            this.mContext = context;
            this.mPoi = poi;
            this.mGeoPoint = poi.getPoint();
            this.mCarPathResult = phVar;
            this.mShareType = dct;
        }

        public ReverseGeocodeListener(Context context, POI poi, AbstractNavigationDataResult abstractNavigationDataResult, dct dct) {
            this.mContext = context;
            this.mPoi = poi;
            this.mGeoPoint = poi.getPoint();
            this.mAutoNaviDataResult = abstractNavigationDataResult;
            this.mShareType = dct;
        }

        public GeoPoint getPoint() {
            return this.mGeoPoint;
        }

        public void error(Throwable th, boolean z) {
            DriveEyrieRouteSharingUtil.a = null;
            ToastHelper.showToast("请检查网络后重试！");
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            DriveEyrieRouteSharingUtil.a = null;
            if (reverseGeocodeResponser != null) {
                if (this.mPoi != null) {
                    this.mPoi.setName(reverseGeocodeResponser.getDesc());
                }
                if (this.mCarPathResult != null) {
                    DriveEyrieRouteSharingUtil.a(this.mContext, this.mShareType, this.mCarPathResult);
                } else if (this.mAutoNaviDataResult != null) {
                    Context context = this.mContext;
                    dct dct = this.mShareType;
                    AbstractNavigationDataResult abstractNavigationDataResult = this.mAutoNaviDataResult;
                    if (abstractNavigationDataResult != null && abstractNavigationDataResult.getFromPOI() != null && abstractNavigationDataResult.getToPOI() != null && abstractNavigationDataResult.getShareToPOI() != null) {
                        POI fromPOI = abstractNavigationDataResult.getFromPOI();
                        POI toPOI = abstractNavigationDataResult.getToPOI();
                        ArrayList<POI> shareMidPOI = abstractNavigationDataResult.getShareMidPOI();
                        if (dct.f) {
                            String name = fromPOI.getName();
                            if (TextUtils.isEmpty(name) || (!name.equals(context.getString(R.string.my_location)) && !name.equals(context.getString(R.string.map_specific_location)) && !name.equals(context.getString(R.string.current_location)))) {
                                if (shareMidPOI != null && shareMidPOI.size() > 0) {
                                    Iterator<POI> it = shareMidPOI.iterator();
                                    while (it.hasNext()) {
                                        POI next = it.next();
                                        if (next != null) {
                                            String name2 = next.getName();
                                            if (!TextUtils.isEmpty(name2) && (name2.equals(context.getString(R.string.my_location)) || name2.equals(context.getString(R.string.map_specific_location)))) {
                                                DriveEyrieRouteSharingUtil.a(new ReverseGeocodeListener(context, next, abstractNavigationDataResult, dct));
                                                return;
                                            }
                                        }
                                    }
                                }
                                String name3 = toPOI.getName();
                                if (!TextUtils.isEmpty(name3) && (name3.equals(context.getString(R.string.map_specific_location)) || name3.equals(context.getString(R.string.select_point_from_map)) || name3.equals(context.getString(R.string.unkown_place)))) {
                                    DriveEyrieRouteSharingUtil.a(new ReverseGeocodeListener(context, toPOI, abstractNavigationDataResult, dct));
                                    return;
                                }
                            } else {
                                DriveEyrieRouteSharingUtil.a(new ReverseGeocodeListener(context, fromPOI, abstractNavigationDataResult, dct));
                                return;
                            }
                        }
                        dcb dcb = (dcb) a.a.a(dcb.class);
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null && dcb != null) {
                            dcb.a(pageContext, dct, (dcd) new dcd(abstractNavigationDataResult) {
                                final /* synthetic */ AbstractNavigationDataResult a;

                                {
                                    this.a = r1;
                                }

                                public final ShareParam getShareDataByType(int i) {
                                    switch (i) {
                                        case 3:
                                            e eVar = new e(0);
                                            eVar.h = this.a.getNaviSharePicPath("EndNaviShare.png");
                                            eVar.g = this.a.getThumbnailsBitmap(this.a.getNaviSharePicPath("EndNaviShareThumbnail.png"));
                                            eVar.e = 3;
                                            eVar.c = false;
                                            return eVar;
                                        case 4:
                                            e eVar2 = new e(1);
                                            eVar2.g = this.a.getThumbnailsBitmap(this.a.getNaviSharePicPath("EndNaviShareThumbnail.png"));
                                            eVar2.h = this.a.getNaviSharePicPath("EndNaviShare.png");
                                            eVar2.c = false;
                                            eVar2.e = 3;
                                            return eVar2;
                                        case 5:
                                            f fVar = new f();
                                            fVar.a = this.a.getShareSinaWeiboBody();
                                            fVar.j = true;
                                            fVar.h = this.a.getNaviSharePicPath("EndNaviShare.png");
                                            fVar.c = false;
                                            return fVar;
                                        default:
                                            return null;
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    public static void a(final Context context, dct dct, ph phVar) {
        ph phVar2;
        final String str;
        if (phVar != null && context != null) {
            dct.f = true;
            dct.d = true;
            dct.e = true;
            dct.a = true;
            dct.h = true;
            dct.g = true;
            try {
                phVar2 = phVar.clone();
            } catch (CloneNotSupportedException unused) {
                phVar2 = null;
            }
            if (phVar2 != null) {
                POI poi = phVar2.g;
                POI poi2 = phVar2.h;
                if (poi != null && !TextUtils.isEmpty(poi.getName()) && poi2 != null && !TextUtils.isEmpty(poi2.getName())) {
                    List<POI> list = phVar2.i;
                    final rd rdVar = new rd(phVar2);
                    rdVar.a = phVar2;
                    if (poi.getName().equals(context.getString(R.string.my_location)) || poi.getName().equals(context.getString(R.string.map_specific_location))) {
                        a(new ReverseGeocodeListener(context, poi, phVar2, dct));
                        return;
                    }
                    if (list != null && list.size() > 0) {
                        for (POI next : list) {
                            if (next != null && (next.getName().equals(context.getString(R.string.my_location)) || next.getName().equals(context.getString(R.string.map_specific_location)))) {
                                a(new ReverseGeocodeListener(context, next, phVar2, dct));
                                return;
                            }
                        }
                    }
                    if (poi2.getName().equals(context.getString(R.string.my_location)) || poi2.getName().equals(context.getString(R.string.map_specific_location))) {
                        a(new ReverseGeocodeListener(context, poi2, phVar2, dct));
                        return;
                    }
                    if (list == null || list.size() <= 0) {
                        str = a(poi, poi2, (List<POI>) null);
                    } else {
                        str = a(poi, poi2, list);
                    }
                    dcb dcb = (dcb) a.a.a(dcb.class);
                    bid pageContext = AMapPageUtil.getPageContext();
                    if (dcb != null && pageContext != null) {
                        dcb.a(pageContext, dct, (dcd) new dcd() {
                            public final ShareParam getShareDataByType(int i) {
                                switch (i) {
                                    case 0:
                                        d dVar = new d();
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(rdVar.b("驾车："));
                                        sb.append(" 详见：");
                                        dVar.a = sb.toString();
                                        dVar.b = str;
                                        return dVar;
                                    case 1:
                                        ShareParam.a aVar = new ShareParam.a();
                                        aVar.b = str;
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(rdVar.b("驾车："));
                                        sb2.append(" 详见：");
                                        aVar.a = sb2.toString();
                                        return aVar;
                                    case 3:
                                        e eVar = new e(0);
                                        eVar.f = rdVar.a((String) "驾车:");
                                        eVar.a = rdVar.a();
                                        eVar.g = ahc.a(context, R.drawable.weixin_route);
                                        eVar.b = str;
                                        eVar.e = 0;
                                        eVar.c = false;
                                        return eVar;
                                    case 4:
                                        e eVar2 = new e(1);
                                        eVar2.f = rdVar.a((String) "驾车:");
                                        eVar2.a = rdVar.a();
                                        eVar2.g = ahc.a(context, R.drawable.weixin_route);
                                        eVar2.b = str;
                                        eVar2.c = false;
                                        eVar2.e = 0;
                                        return eVar2;
                                    case 5:
                                        f fVar = new f();
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append(rdVar.b("驾车线路，"));
                                        sb3.append(" 详见：");
                                        fVar.a = sb3.toString();
                                        fVar.b = str;
                                        return fVar;
                                    default:
                                        return null;
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    static void a(ReverseGeocodeListener reverseGeocodeListener) {
        if (a != null) {
            a.cancel();
        }
        a = ReverseGeocodeManager.getReverseGeocodeResult(reverseGeocodeListener.getPoint(), reverseGeocodeListener);
    }

    private static String a(POI poi, POI poi2, List<POI> list) {
        if (poi == null || poi2 == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (poi.getPoint() == null || poi2.getPoint() == null) {
            return "";
        }
        String name = poi.getName();
        if (name == null || name.length() <= 0) {
            name = "指定位置";
        }
        try {
            sb.append("?r=");
            sb.append(poi.getPoint().getLatitude());
            sb.append(",");
            sb.append(poi.getPoint().getLongitude());
            sb.append(",");
            sb.append(URLEncoder.encode(name, "UTF-8"));
            sb.append(",");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String name2 = poi2.getName();
        if (name2 == null || name2.length() <= 0) {
            name2 = "指定位置";
        }
        try {
            sb.append(poi2.getPoint().getLatitude());
            sb.append(",");
            sb.append(poi2.getPoint().getLongitude());
            sb.append(",");
            sb.append(URLEncoder.encode(name2, "UTF-8"));
            sb.append(",");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        String lastRoutingChoice = DriveUtil.getLastRoutingChoice();
        if (lastRoutingChoice == null) {
            lastRoutingChoice = "0";
        }
        sb.append(String.valueOf(dhw.b(lastRoutingChoice)));
        sb.append(",0");
        if (list != null && list.size() > 0) {
            POI poi3 = list.get(0);
            if (poi3 != null) {
                String name3 = poi3.getName();
                if (name3 == null || name3.length() <= 0) {
                    name3 = "指定位置";
                }
                try {
                    sb.append(",1,");
                    sb.append(poi3.getPoint().getLatitude());
                    sb.append(",");
                    sb.append(poi3.getPoint().getLongitude());
                    sb.append(",");
                    sb.append(URLEncoder.encode(name3, "UTF-8"));
                    sb.append(",");
                } catch (UnsupportedEncodingException e3) {
                    e3.printStackTrace();
                }
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ConfigerHelper.getInstance().getShareMsgUrl());
        sb2.append(sb.toString());
        return sb2.toString();
    }

    public static void a(ph phVar) {
        dct dct = new dct();
        dct.a();
        a((Context) AMapAppGlobal.getApplication(), dct, phVar);
    }
}
