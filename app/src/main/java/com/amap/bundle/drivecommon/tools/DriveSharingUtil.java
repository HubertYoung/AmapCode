package com.amap.bundle.drivecommon.tools;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.navi.navidata.AbstractNavigationDataResult;
import com.amap.bundle.utils.ui.ToastHelper;
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

public final class DriveSharingUtil {
    private static a a;

    static class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private AbstractNavigationDataResult mAutoNaviDataResult;
        private ICarRouteResult mCarPathResult;
        private Context mContext;
        private GeoPoint mGeoPoint = null;
        private POI mPoi = null;
        private dct mShareType;

        public ReverseGeocodeListener(Context context, POI poi, ICarRouteResult iCarRouteResult, dct dct) {
            this.mContext = context;
            this.mPoi = poi;
            this.mGeoPoint = poi.getPoint();
            this.mCarPathResult = iCarRouteResult;
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
            DriveSharingUtil.a();
            ToastHelper.showToast("请检查网络后重试！");
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            String str;
            DriveSharingUtil.a();
            if (reverseGeocodeResponser != null) {
                if (this.mPoi != null) {
                    this.mPoi.setName(reverseGeocodeResponser.getDesc());
                }
                if (this.mCarPathResult != null) {
                    Context context = this.mContext;
                    dct dct = this.mShareType;
                    ICarRouteResult iCarRouteResult = this.mCarPathResult;
                    if (iCarRouteResult != null && context != null) {
                        dct.f = true;
                        dct.d = true;
                        dct.e = true;
                        dct.a = true;
                        dct.h = true;
                        dct.g = true;
                        POI shareFromPOI = iCarRouteResult.getShareFromPOI();
                        POI shareToPOI = iCarRouteResult.getShareToPOI();
                        if (shareFromPOI != null && !TextUtils.isEmpty(shareFromPOI.getName()) && shareToPOI != null && !TextUtils.isEmpty(shareToPOI.getName())) {
                            ArrayList<POI> shareMidPOIs = iCarRouteResult.getShareMidPOIs();
                            sg sgVar = new sg(iCarRouteResult);
                            sgVar.a = iCarRouteResult;
                            if (shareFromPOI.getName().equals(context.getString(R.string.my_location)) || shareFromPOI.getName().equals(context.getString(R.string.map_specific_location))) {
                                DriveSharingUtil.a(new ReverseGeocodeListener(context, shareFromPOI, iCarRouteResult, dct));
                                return;
                            }
                            if (shareMidPOIs != null && shareMidPOIs.size() > 0) {
                                Iterator<POI> it = shareMidPOIs.iterator();
                                while (it.hasNext()) {
                                    POI next = it.next();
                                    if (next != null && (next.getName().equals(context.getString(R.string.my_location)) || next.getName().equals(context.getString(R.string.map_specific_location)))) {
                                        DriveSharingUtil.a(new ReverseGeocodeListener(context, next, iCarRouteResult, dct));
                                        return;
                                    }
                                }
                            }
                            if (shareToPOI.getName().equals(context.getString(R.string.my_location)) || shareToPOI.getName().equals(context.getString(R.string.map_specific_location))) {
                                DriveSharingUtil.a(new ReverseGeocodeListener(context, shareToPOI, iCarRouteResult, dct));
                                return;
                            }
                            if (shareMidPOIs == null || shareMidPOIs.size() <= 0) {
                                str = DriveSharingUtil.a(shareFromPOI, shareToPOI, null, iCarRouteResult.getMethod());
                            } else {
                                str = DriveSharingUtil.a(shareFromPOI, shareToPOI, shareMidPOIs, iCarRouteResult.getMethod());
                            }
                            dcb dcb = (dcb) a.a.a(dcb.class);
                            bid pageContext = AMapPageUtil.getPageContext();
                            if (pageContext != null && dcb != null) {
                                dcb.a(pageContext, dct, (dcd) new dcd(sgVar, context, str) {
                                    final /* synthetic */ sg a;
                                    final /* synthetic */ Context b;
                                    final /* synthetic */ String c;

                                    {
                                        this.a = r1;
                                        this.b = r2;
                                        this.c = r3;
                                    }

                                    public final ShareParam getShareDataByType(int i) {
                                        switch (i) {
                                            case 0:
                                                d dVar = new d();
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(this.a.b("驾车："));
                                                sb.append(" 详见：");
                                                dVar.a = sb.toString();
                                                dVar.b = this.c;
                                                return dVar;
                                            case 1:
                                                ShareParam.a aVar = new ShareParam.a();
                                                aVar.b = this.c;
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(this.a.b("驾车："));
                                                sb2.append(" 详见：");
                                                aVar.a = sb2.toString();
                                                return aVar;
                                            case 3:
                                                e eVar = new e(0);
                                                eVar.f = this.a.a((String) "驾车:");
                                                eVar.a = this.a.a();
                                                eVar.g = ahc.a(this.b, R.drawable.weixin_route);
                                                eVar.b = this.c;
                                                eVar.e = 0;
                                                eVar.c = false;
                                                return eVar;
                                            case 4:
                                                e eVar2 = new e(1);
                                                eVar2.f = this.a.a((String) "驾车:");
                                                eVar2.a = this.a.a();
                                                eVar2.g = ahc.a(this.b, R.drawable.weixin_route);
                                                eVar2.b = this.c;
                                                eVar2.c = false;
                                                eVar2.e = 0;
                                                return eVar2;
                                            case 5:
                                                f fVar = new f();
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append(this.a.b("驾车线路，"));
                                                sb3.append(" 详见：");
                                                fVar.a = sb3.toString();
                                                fVar.b = this.c;
                                                return fVar;
                                            default:
                                                return null;
                                        }
                                    }
                                });
                            }
                        }
                    }
                } else if (this.mAutoNaviDataResult != null) {
                    Context context2 = this.mContext;
                    dct dct2 = this.mShareType;
                    AbstractNavigationDataResult abstractNavigationDataResult = this.mAutoNaviDataResult;
                    if (abstractNavigationDataResult != null && abstractNavigationDataResult.getFromPOI() != null && abstractNavigationDataResult.getToPOI() != null && abstractNavigationDataResult.getShareToPOI() != null) {
                        POI fromPOI = abstractNavigationDataResult.getFromPOI();
                        POI toPOI = abstractNavigationDataResult.getToPOI();
                        ArrayList<POI> shareMidPOI = abstractNavigationDataResult.getShareMidPOI();
                        if (dct2.f) {
                            String name = fromPOI.getName();
                            if (TextUtils.isEmpty(name) || (!name.equals(context2.getString(R.string.my_location)) && !name.equals(context2.getString(R.string.map_specific_location)) && !name.equals(context2.getString(R.string.current_location)))) {
                                if (shareMidPOI != null && shareMidPOI.size() > 0) {
                                    Iterator<POI> it2 = shareMidPOI.iterator();
                                    while (it2.hasNext()) {
                                        POI next2 = it2.next();
                                        if (next2 != null) {
                                            String name2 = next2.getName();
                                            if (!TextUtils.isEmpty(name2) && (name2.equals(context2.getString(R.string.my_location)) || name2.equals(context2.getString(R.string.map_specific_location)))) {
                                                DriveSharingUtil.a(new ReverseGeocodeListener(context2, next2, abstractNavigationDataResult, dct2));
                                                return;
                                            }
                                        }
                                    }
                                }
                                String name3 = toPOI.getName();
                                if (!TextUtils.isEmpty(name3) && (name3.equals(context2.getString(R.string.map_specific_location)) || name3.equals(context2.getString(R.string.select_point_from_map)) || name3.equals(context2.getString(R.string.unkown_place)))) {
                                    DriveSharingUtil.a(new ReverseGeocodeListener(context2, toPOI, abstractNavigationDataResult, dct2));
                                    return;
                                }
                            } else {
                                DriveSharingUtil.a(new ReverseGeocodeListener(context2, fromPOI, abstractNavigationDataResult, dct2));
                                return;
                            }
                        }
                        dcb dcb2 = (dcb) a.a.a(dcb.class);
                        bid pageContext2 = AMapPageUtil.getPageContext();
                        if (pageContext2 != null && dcb2 != null) {
                            dcb2.a(pageContext2, dct2, (dcd) new dcd(abstractNavigationDataResult) {
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

    static synchronized void a(ReverseGeocodeListener reverseGeocodeListener) {
        synchronized (DriveSharingUtil.class) {
            if (a != null) {
                a.cancel();
            }
            a = ReverseGeocodeManager.getReverseGeocodeResult(reverseGeocodeListener.getPoint(), reverseGeocodeListener);
        }
    }

    static synchronized void a() {
        synchronized (DriveSharingUtil.class) {
            a = null;
        }
    }

    static String a(POI poi, POI poi2, ArrayList<POI> arrayList, String str) {
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
        if (str == null) {
            str = "0";
        }
        sb.append(String.valueOf(dhw.b(str)));
        sb.append(",0");
        if (arrayList != null && arrayList.size() > 0) {
            POI poi3 = arrayList.get(0);
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
}
