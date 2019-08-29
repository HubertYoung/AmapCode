package com.autonavi.minimap.route.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.d;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.route.foot.inter.IFootRouteResult;
import com.autonavi.minimap.route.foot.model.OnFootNaviPath;
import com.autonavi.minimap.route.foot.model.OnFootNaviResult;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class RouteSharingUtil {
    static com.autonavi.common.Callback.a a;

    static class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private IBusRouteResult mBusPathResult = null;
        private Context mContext;
        private ebw mFootNaviDataResult = null;
        private GeoPoint mGeoPoint = null;
        private IFootRouteResult mOnFootPathresult = null;
        private POI mPoi = null;
        private a mShareResult;
        private dct mShareType;

        public ReverseGeocodeListener(Context context, POI poi, ebw ebw, dct dct) {
            this.mContext = context;
            this.mPoi = poi;
            this.mGeoPoint = poi.getPoint();
            this.mFootNaviDataResult = ebw;
            this.mShareType = dct;
        }

        public ReverseGeocodeListener(Context context, POI poi, IBusRouteResult iBusRouteResult, dct dct) {
            this.mContext = context;
            this.mPoi = poi;
            this.mGeoPoint = poi.getPoint();
            this.mBusPathResult = iBusRouteResult;
            this.mShareType = dct;
        }

        public ReverseGeocodeListener(Context context, POI poi, IFootRouteResult iFootRouteResult, dct dct) {
            this.mContext = context;
            this.mPoi = poi;
            this.mGeoPoint = poi.getPoint();
            this.mOnFootPathresult = iFootRouteResult;
            this.mShareType = dct;
        }

        public ReverseGeocodeListener(Context context, POI poi, dct dct, a aVar) {
            this.mContext = context;
            this.mPoi = poi;
            this.mGeoPoint = poi.getPoint();
            this.mShareResult = aVar;
            this.mShareType = dct;
        }

        public GeoPoint getPoint() {
            return this.mGeoPoint;
        }

        public void error(Throwable th, boolean z) {
            RouteSharingUtil.a = null;
            ToastHelper.showToast("请检查网络后重试！");
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            RouteSharingUtil.a = null;
            if (reverseGeocodeResponser != null) {
                if (this.mPoi != null) {
                    this.mPoi.setName(reverseGeocodeResponser.getDesc());
                }
                if (this.mShareResult != null) {
                    RouteSharingUtil.a(this.mContext, this.mShareType, this.mShareResult);
                } else if (this.mBusPathResult != null) {
                    RouteSharingUtil.a(this.mContext, this.mShareType, this.mBusPathResult);
                } else if (this.mOnFootPathresult != null) {
                    Context context = this.mContext;
                    dct dct = this.mShareType;
                    IFootRouteResult iFootRouteResult = this.mOnFootPathresult;
                    dct.f = true;
                    dct.d = true;
                    dct.e = true;
                    dct.a = true;
                    POI shareFromPOI = iFootRouteResult.getShareFromPOI();
                    POI shareToPOI = iFootRouteResult.getShareToPOI();
                    if (shareFromPOI != null && (TextUtils.equals(shareFromPOI.getName(), context.getString(R.string.my_location)) || TextUtils.equals(shareFromPOI.getName(), context.getString(R.string.map_specific_location)))) {
                        RouteSharingUtil.a(new ReverseGeocodeListener(context, shareFromPOI, iFootRouteResult, dct));
                    } else if (shareToPOI == null || (!TextUtils.equals(shareToPOI.getName(), context.getString(R.string.my_location)) && !TextUtils.equals(shareToPOI.getName(), context.getString(R.string.map_specific_location)))) {
                        String a = RouteSharingUtil.a(shareFromPOI, shareToPOI, iFootRouteResult.getMethod(), 2);
                        dcb dcb = (dcb) defpackage.esb.a.a.a(dcb.class);
                        if (dcb != null) {
                            dcb.a(dct, (dcd) new dcd(iFootRouteResult, context, a) {
                                final /* synthetic */ IFootRouteResult a;
                                final /* synthetic */ Context b;
                                final /* synthetic */ String c;

                                {
                                    this.a = r1;
                                    this.b = r2;
                                    this.c = r3;
                                }

                                public final ShareParam getShareDataByType(int i) {
                                    String str;
                                    String str2;
                                    String str3;
                                    switch (i) {
                                        case 0:
                                            d dVar = new d();
                                            IFootRouteResult iFootRouteResult = this.a;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(this.b.getString(R.string.route_foot_navi_line));
                                            sb.append("：");
                                            String sb2 = sb.toString();
                                            if (iFootRouteResult == null) {
                                                str = "";
                                            } else {
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append(RouteSharingUtil.a(iFootRouteResult, sb2));
                                                OnFootNaviResult onFootPlanResult = iFootRouteResult.getOnFootPlanResult();
                                                if (onFootPlanResult == null) {
                                                    str = sb3.toString();
                                                } else {
                                                    OnFootNaviPath[] onFootNaviPathArr = onFootPlanResult.mOnFootNaviPath;
                                                    if (onFootNaviPathArr == null) {
                                                        str = sb3.toString();
                                                    } else {
                                                        OnFootNaviPath onFootNaviPath = onFootNaviPathArr[iFootRouteResult.getFocusTabIndex()];
                                                        if (onFootNaviPath == null) {
                                                            str = sb3.toString();
                                                        } else {
                                                            sb3.append("，");
                                                            sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
                                                            sb3.append(cfe.a(onFootNaviPath.mPathlength));
                                                            sb3.append("，");
                                                            sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_need));
                                                            sb3.append(axt.a(onFootNaviPath.mPathTime, false));
                                                            sb3.append("。");
                                                            sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_detail_info_url));
                                                            str = sb3.toString();
                                                        }
                                                    }
                                                }
                                            }
                                            dVar.a = str;
                                            dVar.b = this.c;
                                            return dVar;
                                        case 1:
                                            com.autonavi.minimap.bundle.share.entity.ShareParam.a aVar = new com.autonavi.minimap.bundle.share.entity.ShareParam.a();
                                            IFootRouteResult iFootRouteResult2 = this.a;
                                            StringBuilder sb4 = new StringBuilder();
                                            sb4.append(this.b.getString(R.string.route_foot));
                                            sb4.append("：");
                                            String sb5 = sb4.toString();
                                            if (iFootRouteResult2 == null) {
                                                str2 = "";
                                            } else {
                                                StringBuilder sb6 = new StringBuilder();
                                                sb6.append(RouteSharingUtil.a(iFootRouteResult2, sb5));
                                                OnFootNaviResult onFootPlanResult2 = iFootRouteResult2.getOnFootPlanResult();
                                                if (onFootPlanResult2 == null) {
                                                    str2 = sb6.toString();
                                                } else {
                                                    OnFootNaviPath[] onFootNaviPathArr2 = onFootPlanResult2.mOnFootNaviPath;
                                                    if (onFootNaviPathArr2 == null) {
                                                        str2 = sb6.toString();
                                                    } else {
                                                        OnFootNaviPath onFootNaviPath2 = onFootNaviPathArr2[iFootRouteResult2.getFocusTabIndex()];
                                                        if (onFootNaviPath2 == null) {
                                                            str2 = sb6.toString();
                                                        } else {
                                                            StringBuilder sb7 = new StringBuilder("，");
                                                            sb7.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
                                                            sb6.append(sb7.toString());
                                                            sb6.append(cfe.a(onFootNaviPath2.mPathlength));
                                                            StringBuilder sb8 = new StringBuilder("，");
                                                            sb8.append(AMapAppGlobal.getApplication().getString(R.string.route_need));
                                                            sb6.append(sb8.toString());
                                                            sb6.append(axt.a(onFootNaviPath2.mPathTime, false));
                                                            str2 = sb6.toString();
                                                        }
                                                    }
                                                }
                                            }
                                            aVar.a = str2;
                                            aVar.b = this.c;
                                            return aVar;
                                        case 3:
                                            e eVar = new e(0);
                                            IFootRouteResult iFootRouteResult3 = this.a;
                                            StringBuilder sb9 = new StringBuilder();
                                            sb9.append(this.b.getString(R.string.route_foot));
                                            sb9.append("：");
                                            eVar.f = RouteSharingUtil.a(iFootRouteResult3, sb9.toString());
                                            eVar.a = RouteSharingUtil.a(this.a);
                                            eVar.g = ahc.a(this.b, R.drawable.weixin_route);
                                            eVar.b = this.c;
                                            eVar.e = 0;
                                            return eVar;
                                        case 4:
                                            e eVar2 = new e(1);
                                            IFootRouteResult iFootRouteResult4 = this.a;
                                            StringBuilder sb10 = new StringBuilder();
                                            sb10.append(this.b.getString(R.string.route_foot));
                                            sb10.append("：");
                                            eVar2.f = RouteSharingUtil.a(iFootRouteResult4, sb10.toString());
                                            eVar2.a = RouteSharingUtil.a(this.a);
                                            eVar2.g = ahc.a(this.b, R.drawable.weixin_route);
                                            eVar2.b = this.c;
                                            eVar2.e = 0;
                                            return eVar2;
                                        case 5:
                                            f fVar = new f();
                                            IFootRouteResult iFootRouteResult5 = this.a;
                                            StringBuilder sb11 = new StringBuilder();
                                            sb11.append(this.b.getString(R.string.route_foot_line));
                                            sb11.append("：");
                                            String sb12 = sb11.toString();
                                            if (iFootRouteResult5 == null) {
                                                str3 = "";
                                            } else {
                                                StringBuilder sb13 = new StringBuilder();
                                                sb13.append(RouteSharingUtil.a(iFootRouteResult5, sb12));
                                                OnFootNaviResult onFootPlanResult3 = iFootRouteResult5.getOnFootPlanResult();
                                                if (onFootPlanResult3 == null) {
                                                    str3 = sb13.toString();
                                                } else {
                                                    OnFootNaviPath[] onFootNaviPathArr3 = onFootPlanResult3.mOnFootNaviPath;
                                                    if (onFootNaviPathArr3 == null) {
                                                        str3 = sb13.toString();
                                                    } else {
                                                        OnFootNaviPath onFootNaviPath3 = onFootNaviPathArr3[iFootRouteResult5.getFocusTabIndex()];
                                                        if (onFootNaviPath3 == null) {
                                                            str3 = sb13.toString();
                                                        } else {
                                                            StringBuilder sb14 = new StringBuilder("，");
                                                            sb14.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
                                                            sb13.append(sb14.toString());
                                                            sb13.append(cfe.a(onFootNaviPath3.mPathlength));
                                                            StringBuilder sb15 = new StringBuilder("，");
                                                            sb15.append(AMapAppGlobal.getApplication().getString(R.string.route_need));
                                                            sb13.append(sb15.toString());
                                                            sb13.append(axt.a(onFootNaviPath3.mPathTime, false));
                                                            str3 = sb13.toString();
                                                        }
                                                    }
                                                }
                                            }
                                            fVar.a = str3;
                                            fVar.b = this.c;
                                            return fVar;
                                        default:
                                            return null;
                                    }
                                }
                            });
                        }
                    } else {
                        RouteSharingUtil.a(new ReverseGeocodeListener(context, shareToPOI, iFootRouteResult, dct));
                    }
                } else if (this.mFootNaviDataResult != null) {
                    Context context2 = this.mContext;
                    dct dct2 = this.mShareType;
                    ebw ebw = this.mFootNaviDataResult;
                    if (ebw != null && ebw.a != null && ebw.b != null) {
                        POI poi = ebw.a;
                        POI poi2 = ebw.b;
                        if (dct2.f) {
                            String name = poi.getName();
                            if (!TextUtils.isEmpty(name) && (name.equals(context2.getString(R.string.my_location)) || name.equals(context2.getString(R.string.map_specific_location)) || name.equals(context2.getString(R.string.current_location)))) {
                                RouteSharingUtil.a(new ReverseGeocodeListener(context2, poi, ebw, dct2));
                                return;
                            } else if (!TextUtils.isEmpty(name) && (name.equals(context2.getString(R.string.map_specific_location)) || name.equals(context2.getString(R.string.select_point_from_map)) || name.equals(context2.getString(R.string.unkown_place)))) {
                                RouteSharingUtil.a(new ReverseGeocodeListener(context2, poi2, ebw, dct2));
                                return;
                            }
                        }
                        dcb dcb2 = (dcb) defpackage.esb.a.a.a(dcb.class);
                        if (dcb2 != null) {
                            dcb2.a(dct2, (dcd) new dcd(ebw) {
                                final /* synthetic */ ebw a;

                                {
                                    this.a = r1;
                                }

                                public final ShareParam getShareDataByType(int i) {
                                    switch (i) {
                                        case 3:
                                            e eVar = new e(0);
                                            eVar.g = BitmapFactory.decodeFile(ebw.a("EndNaviShareThumbnail.png"));
                                            eVar.h = ebw.a("EndNaviShare.png");
                                            eVar.c = false;
                                            eVar.e = 3;
                                            return eVar;
                                        case 4:
                                            e eVar2 = new e(1);
                                            eVar2.g = BitmapFactory.decodeFile(ebw.a("EndNaviShareThumbnail.png"));
                                            eVar2.h = ebw.a("EndNaviShare.png");
                                            eVar2.c = false;
                                            eVar2.e = 3;
                                            return eVar2;
                                        case 5:
                                            f fVar = new f();
                                            fVar.a = AMapAppGlobal.getApplication().getString(R.string.foot_navi_share_weibo_body);
                                            fVar.j = true;
                                            fVar.h = ebw.a("EndNaviShare.png");
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

    public static class a {
        public POI a;
        public POI b;
        public int c;
        public int d;

        private a() {
        }

        public /* synthetic */ a(byte b2) {
            this();
        }
    }

    private static String a(BusPath busPath) {
        if (busPath == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            Resources resources = AMapAppGlobal.getApplication().getResources();
            if (busPath.mSectionNum > 2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(resources.getString(R.string.bus_route_change));
                sb2.append(busPath.mSectionNum - 1);
                sb2.append(resources.getString(R.string.bus_section_num));
                sb2.append("，");
                sb.append(sb2.toString());
            }
            if (busPath.taxiBusPath != null && busPath.taxiBusPath.isStart) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(resources.getString(R.string.taxi));
                sb3.append(resources.getString(R.string.to));
                sb3.append(busPath.taxiBusPath.mEndName);
                sb.append(sb3.toString());
                sb.append("，");
            }
            for (int i = 0; i < busPath.mSectionNum; i++) {
                if (busPath.mPathSections[i] != null) {
                    String c = ebm.c(busPath.mPathSections[i].mSectionName);
                    if (i == 0) {
                        sb.append(resources.getString(R.string.take));
                        sb.append(c);
                        sb.append("(");
                        sb.append(busPath.mPathSections[i].getSubwayPortDesc());
                        sb.append("，");
                        sb.append(busPath.mPathSections[i].mStationNum - 1);
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(resources.getString(R.string.bus_stop));
                        sb4.append(")");
                        sb.append(sb4.toString());
                    } else {
                        sb.append("，");
                        sb.append(resources.getString(R.string.bus_route_change));
                        sb.append(c);
                        sb.append("(");
                        sb.append(busPath.mPathSections[i].getSubwayPortDesc());
                        sb.append("，");
                        sb.append(busPath.mPathSections[i].mStationNum - 1);
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(resources.getString(R.string.bus_stop));
                        sb5.append(")");
                        sb.append(sb5.toString());
                    }
                }
            }
            if (busPath.taxiBusPath == null) {
                if (busPath.mEndWalkLength > 0) {
                    StringBuilder sb6 = new StringBuilder("，");
                    sb6.append(resources.getString(R.string.by_foot));
                    sb.append(sb6.toString());
                    sb.append(cfe.a(busPath.mEndWalkLength));
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append(resources.getString(R.string.arrive_in_end_point));
                sb7.append("。");
                sb.append(sb7.toString());
            } else if (!busPath.taxiBusPath.isStart) {
                StringBuilder sb8 = new StringBuilder("，");
                sb8.append(resources.getString(R.string.taxi));
                sb8.append(resources.getString(R.string.arrive_in_end_point));
                sb8.append("。");
                sb.append(sb8.toString());
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return sb.toString();
    }

    static String a(IBusRouteResult iBusRouteResult) {
        if (iBusRouteResult == null) {
            return null;
        }
        BusPath focusBusPath = iBusRouteResult.getFocusBusPath();
        if (focusBusPath == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            int i = focusBusPath.mEndWalkLength;
            if (focusBusPath.taxiBusPath != null && focusBusPath.taxiBusPath.isStart) {
                if (focusBusPath.taxiBusPath.mCost > 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                    sb2.append("(");
                    sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
                    sb2.append(focusBusPath.taxiBusPath.mCost);
                    sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_yuan));
                    sb2.append(")→");
                    sb.append(sb2.toString());
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                    sb3.append("→");
                    sb.append(sb3.toString());
                }
            }
            int i2 = i;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < focusBusPath.mSectionNum; i5++) {
                BusPathSection busPathSection = focusBusPath.mPathSections[i5];
                if (busPathSection != null) {
                    if (BusPath.isBus(busPathSection.mBusType)) {
                        i3 += busPathSection.mStationNum - 1;
                    } else if (BusPath.isSubway(busPathSection.mBusType)) {
                        i4 += busPathSection.mStationNum - 1;
                    }
                    i2 += busPathSection.mFootLength;
                    sb.append(ebm.c(busPathSection.mSectionName));
                    sb.append(a(busPathSection.alter_list));
                    if (focusBusPath.mSectionNum > 1 && i5 < focusBusPath.mSectionNum - 1) {
                        sb.append("→");
                    }
                }
            }
            if (focusBusPath.taxiBusPath != null && !focusBusPath.taxiBusPath.isStart) {
                if (focusBusPath.taxiBusPath.mCost > 0) {
                    StringBuilder sb4 = new StringBuilder("→");
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                    sb4.append("(");
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
                    sb4.append(focusBusPath.taxiBusPath.mCost);
                    sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_yuan));
                    sb4.append(")");
                    sb.append(sb4.toString());
                } else {
                    StringBuilder sb5 = new StringBuilder("→");
                    sb5.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                    sb.append(sb5.toString());
                }
            }
            if (i2 > 0) {
                StringBuilder sb6 = new StringBuilder("， ");
                sb6.append(AMapAppGlobal.getApplication().getString(R.string.route_sum_foot));
                sb.append(sb6.toString());
                sb.append(cfe.a(i2));
            }
            int i6 = i3 + i4;
            if (i6 > 0) {
                StringBuilder sb7 = new StringBuilder("，");
                sb7.append(AMapAppGlobal.getApplication().getString(R.string.route_total));
                sb.append(sb7.toString());
                sb.append(i6);
                sb.append(AMapAppGlobal.getApplication().getString(R.string.route_station));
            }
            if (focusBusPath.taxiBusPath == null && iBusRouteResult.getBusPathsResult().mtaxiPrice > 0) {
                StringBuilder sb8 = new StringBuilder("；");
                sb8.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                sb8.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
                sb8.append(":");
                sb.append(sb8.toString());
                sb.append(iBusRouteResult.getBusPathsResult().mtaxiPrice);
                sb.append(AMapAppGlobal.getApplication().getString(R.string.route_yuan));
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return sb.toString();
    }

    private static String b(IBusRouteResult iBusRouteResult) {
        if (iBusRouteResult == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_from));
        sb.append(iBusRouteResult.getShareFromPOI().getName());
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
        sb.append(iBusRouteResult.getShareToPOI().getName());
        return sb.toString();
    }

    static String a(IBusRouteResult iBusRouteResult, String str) {
        if (iBusRouteResult == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_from));
        sb.append(iBusRouteResult.getShareFromPOI().getName());
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
        sb.append(iBusRouteResult.getShareToPOI().getName());
        BusPath focusBusPath = iBusRouteResult.getFocusBusPath();
        if (focusBusPath != null) {
            try {
                String a2 = axt.a(focusBusPath);
                if (focusBusPath.taxiBusPath != null && focusBusPath.taxiBusPath.isStart) {
                    if (focusBusPath.taxiBusPath.mCost > 0) {
                        StringBuilder sb2 = new StringBuilder("，");
                        sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                        sb2.append("(");
                        sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
                        sb2.append(focusBusPath.taxiBusPath.mCost);
                        sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_yuan));
                        sb2.append(")");
                        sb.append(sb2.toString());
                        if (!TextUtils.isEmpty(focusBusPath.taxiBusPath.mEndName)) {
                            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
                            sb.append(focusBusPath.taxiBusPath.mEndName);
                        }
                    } else {
                        StringBuilder sb3 = new StringBuilder("，");
                        sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                        sb.append(sb3.toString());
                        if (!TextUtils.isEmpty(focusBusPath.taxiBusPath.mEndName)) {
                            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
                            sb.append(focusBusPath.taxiBusPath.mEndName);
                        }
                    }
                }
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < focusBusPath.mSectionNum; i3++) {
                    BusPathSection busPathSection = focusBusPath.mPathSections[i3];
                    if (busPathSection != null) {
                        i += busPathSection.foot_time;
                        i2 += busPathSection.mStationNum - 1;
                        String c = ebm.c(busPathSection.mSectionName);
                        if (i3 > 0) {
                            sb.append("→");
                            sb.append(c);
                        } else {
                            sb.append("，");
                            sb.append(c);
                        }
                        sb.append(a(busPathSection.alter_list));
                    }
                }
                if (focusBusPath.taxiBusPath != null && !focusBusPath.taxiBusPath.isStart) {
                    if (focusBusPath.taxiBusPath.mCost > 0) {
                        StringBuilder sb4 = new StringBuilder("，");
                        sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                        sb4.append("(");
                        sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
                        sb4.append(focusBusPath.taxiBusPath.mCost);
                        sb4.append(AMapAppGlobal.getApplication().getString(R.string.route_yuan));
                        sb4.append(")");
                        sb.append(sb4.toString());
                        if (!TextUtils.isEmpty(focusBusPath.taxiBusPath.mEndName)) {
                            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
                            sb.append(focusBusPath.taxiBusPath.mEndName);
                        }
                    } else {
                        StringBuilder sb5 = new StringBuilder("，");
                        sb5.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
                        sb.append(sb5.toString());
                        if (!TextUtils.isEmpty(focusBusPath.taxiBusPath.mEndName)) {
                            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
                            sb.append(focusBusPath.taxiBusPath.mEndName);
                        }
                    }
                }
                int i4 = i + focusBusPath.endfoottime;
                StringBuilder sb6 = new StringBuilder("， ");
                sb6.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
                sb6.append(a2);
                sb6.append("(");
                sb6.append(AMapAppGlobal.getApplication().getString(R.string.route_foot));
                sb6.append(axt.a(i4, false));
                sb6.append(")");
                sb6.append(AMapAppGlobal.getApplication().getString(R.string.route_total));
                sb6.append(i2);
                sb6.append(AMapAppGlobal.getApplication().getString(R.string.route_station));
                sb.append(sb6.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        return sb.toString();
    }

    private static String a(BusPathSection[] busPathSectionArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (busPathSectionArr != null) {
            try {
                int length = busPathSectionArr.length;
                for (int i = 0; i < length; i++) {
                    String c = ebm.c(busPathSectionArr[i].mSectionName);
                    if (c != null && !c.equals("")) {
                        stringBuffer.append("/".concat(String.valueOf(c)));
                        if (i > 0) {
                            return stringBuffer.toString();
                        }
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        return stringBuffer.toString();
    }

    static String b(IBusRouteResult iBusRouteResult, String str) {
        if (iBusRouteResult == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(b(iBusRouteResult));
        BusPath focusBusPath = iBusRouteResult.getFocusBusPath();
        if (focusBusPath != null) {
            sb.append("，");
            sb.append(a(focusBusPath));
        }
        return sb.toString();
    }

    static String a(IFootRouteResult iFootRouteResult, String str) {
        if (iFootRouteResult == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_from));
        sb.append(iFootRouteResult.getShareFromPOI().getName());
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
        sb.append(iFootRouteResult.getShareToPOI().getName());
        return sb.toString();
    }

    static String a(POI poi, POI poi2, String str) {
        if (poi == null || poi2 == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_from));
        sb.append(poi.getName());
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_to));
        sb.append(poi2.getName());
        return sb.toString();
    }

    public static void a(final Context context, dct dct, final IBusRouteResult iBusRouteResult) {
        dct.f = true;
        dct.d = true;
        dct.e = true;
        dct.a = true;
        if (iBusRouteResult != null && context != null) {
            POI shareFromPOI = iBusRouteResult.getShareFromPOI();
            POI shareToPOI = iBusRouteResult.getShareToPOI();
            if (shareFromPOI != null && (TextUtils.equals(shareFromPOI.getName(), context.getString(R.string.my_location)) || TextUtils.equals(shareFromPOI.getName(), context.getString(R.string.map_specific_location)))) {
                a(new ReverseGeocodeListener(context, shareFromPOI, iBusRouteResult, dct));
            } else if (shareToPOI == null || (!TextUtils.equals(shareToPOI.getName(), context.getString(R.string.my_location)) && !TextUtils.equals(shareToPOI.getName(), context.getString(R.string.map_specific_location)))) {
                final String a2 = a(shareFromPOI, shareToPOI, iBusRouteResult.getMethod(), 1);
                dcb dcb = (dcb) defpackage.esb.a.a.a(dcb.class);
                if (dcb != null) {
                    dcb.a(dct, (dcd) new dcd() {
                        public final ShareParam getShareDataByType(int i) {
                            switch (i) {
                                case 0:
                                    d dVar = new d();
                                    IBusRouteResult iBusRouteResult = iBusRouteResult;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(context.getString(R.string.route_bus));
                                    sb.append("：");
                                    dVar.a = RouteSharingUtil.b(iBusRouteResult, sb.toString());
                                    dVar.b = a2;
                                    return dVar;
                                case 1:
                                    com.autonavi.minimap.bundle.share.entity.ShareParam.a aVar = new com.autonavi.minimap.bundle.share.entity.ShareParam.a();
                                    IBusRouteResult iBusRouteResult2 = iBusRouteResult;
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(context.getString(R.string.route_bus));
                                    sb2.append("：");
                                    aVar.a = RouteSharingUtil.b(iBusRouteResult2, sb2.toString());
                                    aVar.b = a2;
                                    return aVar;
                                case 3:
                                    e eVar = new e(0);
                                    IBusRouteResult iBusRouteResult3 = iBusRouteResult;
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(context.getString(R.string.route_bus));
                                    sb3.append(":");
                                    eVar.f = RouteSharingUtil.c(iBusRouteResult3, sb3.toString());
                                    eVar.a = RouteSharingUtil.a(iBusRouteResult);
                                    eVar.g = ahc.a(context, R.drawable.weixin_route);
                                    eVar.b = a2;
                                    eVar.e = 0;
                                    return eVar;
                                case 4:
                                    e eVar2 = new e(1);
                                    IBusRouteResult iBusRouteResult4 = iBusRouteResult;
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(context.getString(R.string.route_bus));
                                    sb4.append(":");
                                    eVar2.f = RouteSharingUtil.c(iBusRouteResult4, sb4.toString());
                                    eVar2.a = RouteSharingUtil.a(iBusRouteResult);
                                    eVar2.g = ahc.a(context, R.drawable.weixin_route);
                                    eVar2.b = a2;
                                    eVar2.e = 0;
                                    return eVar2;
                                case 5:
                                    f fVar = new f();
                                    IBusRouteResult iBusRouteResult5 = iBusRouteResult;
                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append(context.getString(R.string.route_bus_line));
                                    sb5.append("：");
                                    fVar.a = RouteSharingUtil.a(iBusRouteResult5, sb5.toString());
                                    fVar.b = a2;
                                    return fVar;
                                default:
                                    return null;
                            }
                        }
                    });
                }
            } else {
                a(new ReverseGeocodeListener(context, shareToPOI, iBusRouteResult, dct));
            }
        }
    }

    public static void a(final Context context, dct dct, final a aVar) {
        if (aVar != null) {
            if (TextUtils.equals(aVar.a.getName(), context.getString(R.string.my_location)) || TextUtils.equals(aVar.a.getName(), context.getString(R.string.map_specific_location))) {
                a(new ReverseGeocodeListener(context, aVar.a, dct, aVar));
            } else if (TextUtils.equals(aVar.b.getName(), context.getString(R.string.my_location)) || TextUtils.equals(aVar.b.getName(), context.getString(R.string.map_specific_location))) {
                a(new ReverseGeocodeListener(context, aVar.b, dct, aVar));
            } else {
                final String a2 = a(aVar.a, aVar.b, null, 2);
                dcb dcb = (dcb) defpackage.esb.a.a.a(dcb.class);
                if (dcb != null) {
                    dcb.a(dct, (dcd) new dcd() {
                        public final ShareParam getShareDataByType(int i) {
                            String str;
                            String str2;
                            String str3;
                            switch (i) {
                                case 0:
                                    d dVar = new d();
                                    POI poi = aVar.a;
                                    POI poi2 = aVar.b;
                                    int i2 = aVar.c;
                                    int i3 = aVar.d;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(context.getString(R.string.route_foot_navi_line));
                                    sb.append("：");
                                    String sb2 = sb.toString();
                                    if (poi == null || poi2 == null) {
                                        str = "";
                                    } else {
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append(RouteSharingUtil.a(poi, poi2, sb2));
                                        sb3.append("，");
                                        sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
                                        sb3.append(cfe.a(i2));
                                        sb3.append("，");
                                        sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_need));
                                        sb3.append(axt.a(i3, false));
                                        sb3.append("。");
                                        sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_detail_info_url));
                                        str = sb3.toString();
                                    }
                                    dVar.a = str;
                                    dVar.b = a2;
                                    return dVar;
                                case 1:
                                    com.autonavi.minimap.bundle.share.entity.ShareParam.a aVar = new com.autonavi.minimap.bundle.share.entity.ShareParam.a();
                                    POI poi3 = aVar.a;
                                    POI poi4 = aVar.b;
                                    int i4 = aVar.c;
                                    int i5 = aVar.d;
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(context.getString(R.string.route_foot));
                                    sb4.append("：");
                                    String sb5 = sb4.toString();
                                    if (poi3 == null || poi4 == null) {
                                        str2 = "";
                                    } else {
                                        StringBuilder sb6 = new StringBuilder();
                                        sb6.append(RouteSharingUtil.a(poi3, poi4, sb5));
                                        StringBuilder sb7 = new StringBuilder("，");
                                        sb7.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
                                        sb6.append(sb7.toString());
                                        sb6.append(cfe.a(i4));
                                        StringBuilder sb8 = new StringBuilder("，");
                                        sb8.append(AMapAppGlobal.getApplication().getString(R.string.route_need));
                                        sb6.append(sb8.toString());
                                        sb6.append(axt.a(i5, false));
                                        str2 = sb6.toString();
                                    }
                                    aVar.a = str2;
                                    aVar.b = a2;
                                    return aVar;
                                case 3:
                                    e eVar = new e(0);
                                    POI poi5 = aVar.a;
                                    POI poi6 = aVar.b;
                                    StringBuilder sb9 = new StringBuilder();
                                    sb9.append(context.getString(R.string.route_foot));
                                    sb9.append("：");
                                    eVar.f = RouteSharingUtil.a(poi5, poi6, sb9.toString());
                                    eVar.a = RouteSharingUtil.a(aVar.a, aVar.b, aVar.c);
                                    eVar.g = ahc.a(context, R.drawable.weixin_route);
                                    eVar.b = a2;
                                    eVar.e = 0;
                                    return eVar;
                                case 4:
                                    e eVar2 = new e(1);
                                    POI poi7 = aVar.a;
                                    POI poi8 = aVar.b;
                                    StringBuilder sb10 = new StringBuilder();
                                    sb10.append(context.getString(R.string.route_foot));
                                    sb10.append("：");
                                    eVar2.f = RouteSharingUtil.a(poi7, poi8, sb10.toString());
                                    eVar2.a = RouteSharingUtil.a(aVar.a, aVar.b, aVar.c);
                                    eVar2.g = ahc.a(context, R.drawable.weixin_route);
                                    eVar2.b = a2;
                                    eVar2.e = 0;
                                    return eVar2;
                                case 5:
                                    f fVar = new f();
                                    POI poi9 = aVar.a;
                                    POI poi10 = aVar.b;
                                    int i6 = aVar.c;
                                    int i7 = aVar.d;
                                    StringBuilder sb11 = new StringBuilder();
                                    sb11.append(context.getString(R.string.route_foot_line));
                                    sb11.append("：");
                                    String sb12 = sb11.toString();
                                    if (poi9 == null || poi10 == null) {
                                        str3 = "";
                                    } else {
                                        StringBuilder sb13 = new StringBuilder();
                                        sb13.append(RouteSharingUtil.a(poi9, poi10, sb12));
                                        StringBuilder sb14 = new StringBuilder("，");
                                        sb14.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
                                        sb13.append(sb14.toString());
                                        sb13.append(cfe.a(i6));
                                        StringBuilder sb15 = new StringBuilder("，");
                                        sb15.append(AMapAppGlobal.getApplication().getString(R.string.route_need));
                                        sb13.append(sb15.toString());
                                        sb13.append(axt.a(i7, false));
                                        str3 = sb13.toString();
                                    }
                                    fVar.a = str3;
                                    fVar.b = a2;
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

    static String a(POI poi, POI poi2, String str, int i) {
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
        if (i < 0) {
            i = 0;
        }
        if (i == 0) {
            str = String.valueOf(dhw.b(str));
        }
        sb.append(str);
        sb.append(",");
        sb.append(i);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ConfigerHelper.getInstance().getShareMsgUrl());
        sb2.append(sb.toString());
        return sb2.toString();
    }

    static void a(ReverseGeocodeListener reverseGeocodeListener) {
        if (a != null) {
            a.cancel();
        }
        a = ReverseGeocodeManager.getReverseGeocodeResult(reverseGeocodeListener.getPoint(), reverseGeocodeListener);
    }

    static /* synthetic */ String c(IBusRouteResult iBusRouteResult, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(b(iBusRouteResult));
        return sb.toString();
    }

    static /* synthetic */ String a(IFootRouteResult iFootRouteResult) {
        if (iFootRouteResult == null) {
            return null;
        }
        OnFootNaviResult onFootPlanResult = iFootRouteResult.getOnFootPlanResult();
        if (onFootPlanResult == null) {
            return null;
        }
        OnFootNaviPath[] onFootNaviPathArr = onFootPlanResult.mOnFootNaviPath;
        if (onFootNaviPathArr == null) {
            return null;
        }
        OnFootNaviPath onFootNaviPath = onFootNaviPathArr[iFootRouteResult.getFocusTabIndex()];
        if (onFootNaviPath == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
        sb.append(cfe.a(onFootNaviPath.mPathlength));
        sb.append("\n");
        if (onFootNaviPath.mTaxiFee > 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_taxi));
            sb2.append(AMapAppGlobal.getApplication().getString(R.string.route_about));
            sb.append(sb2.toString());
            sb.append(onFootNaviPath.mTaxiFee);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.route_yuan));
        }
        return sb.toString();
    }

    static /* synthetic */ String a(POI poi, POI poi2, int i) {
        if (poi == null || poi2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getString(R.string.route_all_length));
        sb.append(cfe.a(i));
        sb.append("\n");
        return sb.toString();
    }
}
