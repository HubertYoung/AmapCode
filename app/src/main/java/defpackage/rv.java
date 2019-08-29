package defpackage;

import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.drivecommon.model.GroupNavigationSection;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationSection;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: rv reason: default package */
/* compiled from: SaveCarRouteUtil */
public final class rv {
    private static String a(cos cos) {
        if (!cos.k || cos.o == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(cos.m.getName());
            sb.append("→");
            sb.append(cos.n.getName());
            return sb.toString();
        }
        ArrayList<POI> arrayList = cos.o;
        StringBuilder sb2 = new StringBuilder();
        for (POI next : arrayList) {
            if (next != null) {
                if (sb2.length() > 0) {
                    sb2.append("→");
                }
                sb2.append(next.getName());
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(cos.m.getName());
        sb3.append("→");
        sb3.append(sb2.toString());
        sb3.append("→");
        sb3.append(cos.n.getName());
        return sb3.toString();
    }

    public static cos a(String str, te teVar) {
        cos cos = null;
        if (str == null) {
            return null;
        }
        cos cos2 = new cos();
        try {
            JSONObject jSONObject = new JSONObject(str);
            boolean z = true;
            cos2.a = 1;
            JSONObject jSONObject2 = jSONObject.getJSONObject(H5PageData.KEY_UC_START);
            if (jSONObject2 != null) {
                cos2.b = jSONObject2.optInt(DictionaryKeys.CTRLXY_X);
                cos2.c = jSONObject2.optInt(DictionaryKeys.CTRLXY_Y);
                if (cos2.b == 0 || cos2.c == 0) {
                    double optDouble = jSONObject2.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON, 0.0d);
                    double optDouble2 = jSONObject2.optDouble("lat", 0.0d);
                    GeoPoint geoPoint = new GeoPoint();
                    geoPoint.setLonLat(optDouble, optDouble2);
                    cos2.b = geoPoint.x;
                    cos2.c = geoPoint.y;
                }
                cos2.m = bnx.a(jSONObject2.toString());
            }
            JSONObject jSONObject3 = jSONObject.getJSONObject("end");
            if (jSONObject3 != null) {
                cos2.d = jSONObject3.optInt(DictionaryKeys.CTRLXY_X);
                cos2.e = jSONObject3.optInt(DictionaryKeys.CTRLXY_Y);
                if (cos2.d == 0 || cos2.e == 0) {
                    double optDouble3 = jSONObject2.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON, 0.0d);
                    double optDouble4 = jSONObject2.optDouble("lat", 0.0d);
                    GeoPoint geoPoint2 = new GeoPoint();
                    geoPoint2.setLonLat(optDouble3, optDouble4);
                    cos2.d = geoPoint2.x;
                    cos2.e = geoPoint2.y;
                }
                cos2.n = bnx.a(jSONObject3.toString());
            }
            cos2.f = jSONObject.getString("method");
            cos2.j = jSONObject.getInt("length");
            DriveUtil.resetRgeoPOI(cos2.m, teVar);
            DriveUtil.resetRgeoPOI(cos2.n, teVar);
            JSONArray optJSONArray = jSONObject.optJSONArray("via");
            if (optJSONArray == null || optJSONArray.length() <= 0) {
                z = false;
            }
            cos2.k = z;
            if (cos2.k) {
                int length = optJSONArray.length();
                cos2.o = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    POI a = bnx.a(optJSONArray.getJSONObject(i).toString());
                    if (a != null) {
                        DriveUtil.resetRgeoPOI(a, teVar);
                        cos2.o.add(a);
                    }
                }
            }
            cos2.h = a(cos2);
            NavigationPath navigationPath = new NavigationPath();
            navigationPath.mCostTime = jSONObject.optInt("time");
            navigationPath.mTaxiFee = jSONObject.optInt("taxiPrice");
            navigationPath.mPathlength = jSONObject.optInt("length");
            navigationPath.mPathStrategy = jSONObject.optInt("strategy");
            cos2.l = navigationPath;
            cos = cos2;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cos;
    }

    public static NavigationPath a(JSONObject jSONObject, boolean z) {
        int i;
        NavigationPath navigationPath = new NavigationPath();
        navigationPath.mDataLength = agd.a(jSONObject, "mDataLength");
        navigationPath.mSectionNum = agd.a(jSONObject, "mSectionNum");
        navigationPath.mPathStrategy = agd.a(jSONObject, "strategy");
        navigationPath.mPathlength = agd.a(jSONObject, "route_len");
        navigationPath.mTaxiFee = agd.a(jSONObject, "taxi_price");
        navigationPath.mCostTime = agd.a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME);
        if (z) {
            return navigationPath;
        }
        try {
            if (jSONObject.has("carGroupSegment")) {
                JSONArray optJSONArray = jSONObject.optJSONArray("carGroupSegment");
                int length = optJSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    GroupNavigationSection groupNavigationSection = new GroupNavigationSection();
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i2);
                    groupNavigationSection.m_nSegCount = agd.a(jSONObject2, "groupSegCount");
                    groupNavigationSection.m_bArrivePass = agd.a(jSONObject2, "groupArrivePass") == 1;
                    groupNavigationSection.m_nStartSegId = agd.a(jSONObject2, "groupStartSegId");
                    groupNavigationSection.m_nDistance = agd.a(jSONObject2, "groupDistance");
                    groupNavigationSection.m_nToll = agd.a(jSONObject2, "groupToll");
                    groupNavigationSection.m_GroupName = agd.e(jSONObject2, "groupName");
                    navigationPath.mGroupNaviSectionList.add(groupNavigationSection);
                }
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("navigationSection");
            if (optJSONArray2 != null) {
                navigationPath.mSectionNum = optJSONArray2.length();
                i = navigationPath.mSectionNum;
                navigationPath.mSections = new NavigationSection[i];
            } else {
                i = 0;
            }
            for (int i3 = 0; i3 < i; i3++) {
                NavigationSection navigationSection = new NavigationSection();
                JSONObject jSONObject3 = optJSONArray2.getJSONObject(i3);
                int a = agd.a(jSONObject3, "mNavigtionAction");
                if (a <= 0) {
                    a = 0;
                }
                navigationSection.mNavigtionAction = (byte) (a & 255);
                navigationSection.mIndex = i3;
                int a2 = agd.a(jSONObject3, "mNaviAssiAction");
                if (a2 <= 0) {
                    a2 = 0;
                }
                navigationSection.mNaviAssiAction = (byte) (a2 & 255);
                agd.a(jSONObject3, (String) "mNaviAssiAction", (int) navigationSection.mNaviAssiAction);
                navigationSection.mStreetName = agd.e(jSONObject3, "mStreetName");
                navigationSection.mDataLength = agd.a(jSONObject3, "mDataLength");
                navigationSection.mPathlength = agd.a(jSONObject3, "mPathlength");
                navigationSection.mPointNum = agd.a(jSONObject3, "mPointNum");
                JSONArray jSONArray = jSONObject3.getJSONArray("points");
                int length2 = jSONArray.length();
                navigationSection.mGeoPoints = new GeoPoint[length2];
                for (int i4 = 0; i4 < length2; i4++) {
                    JSONObject jSONObject4 = jSONArray.getJSONObject(i4);
                    GeoPoint geoPoint = new GeoPoint(agd.a(jSONObject4, DictionaryKeys.CTRLXY_X), agd.a(jSONObject4, DictionaryKeys.CTRLXY_Y));
                    navigationSection.mGeoPoints[i4] = geoPoint;
                    navigationPath.mStackGeoPoint.add(geoPoint);
                }
                GeoPoint geoPoint2 = null;
                if (i3 > 1) {
                    NavigationSection navigationSection2 = navigationPath.mSections[i3 - 1];
                    if (navigationSection2 != null && navigationSection2.mGeoPoints.length > 0) {
                        GeoPoint geoPoint3 = navigationSection2.mGeoPoints[navigationSection2.mGeoPoints.length - 1];
                        GeoPoint[] geoPointArr = navigationSection.mGeoPoints;
                        if (!(geoPoint3 == null || geoPointArr == null)) {
                            if (geoPointArr.length - 0 > 0) {
                                int i5 = geoPoint3.x - geoPointArr[0].x;
                                int i6 = geoPoint3.y - geoPointArr[0].y;
                                if (Math.abs(i5) >= 25 || Math.abs(i6) >= 25) {
                                    if (Math.abs(i5) >= 50 || Math.abs(i6) >= 50) {
                                        geoPoint2 = geoPoint3;
                                    } else {
                                        geoPointArr[0] = geoPoint3;
                                    }
                                }
                            }
                        }
                    }
                }
                if (navigationPath.mGroupNaviSectionList != null && navigationPath.mGroupNaviSectionList.size() > 0) {
                    Iterator<GroupNavigationSection> it = navigationPath.mGroupNaviSectionList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        GroupNavigationSection next = it.next();
                        if (i3 < next.m_nStartSegId + next.m_nSegCount) {
                            next.mSectionList.add(navigationSection);
                            break;
                        }
                    }
                }
                new ArrayList();
                if (geoPoint2 != null) {
                    GeoPoint[] geoPointArr2 = new GeoPoint[(length2 + 1)];
                    System.arraycopy(navigationSection.mGeoPoints, 0, geoPointArr2, 1, length2);
                    geoPointArr2[0] = geoPoint2;
                }
                navigationPath.mSections[i3] = navigationSection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return navigationPath;
    }

    public static String a(Object obj) {
        if (obj == null) {
            return null;
        }
        NavigationPath navigationPath = (NavigationPath) obj;
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME, navigationPath.mCostTime);
        agd.a(jSONObject, (String) "taxi_price", navigationPath.mTaxiFee);
        agd.a(jSONObject, (String) "mDataLength", navigationPath.mDataLength);
        agd.a(jSONObject, (String) "mPathlength", navigationPath.mPathlength);
        agd.a(jSONObject, (String) "strategy", navigationPath.mPathStrategy);
        try {
            if (navigationPath.mPathlength > 1000000) {
                return jSONObject.toString();
            }
            if (navigationPath.mSections != null) {
                agd.a(jSONObject, (String) "mSectionNum", navigationPath.mSections.length);
            }
            JSONArray jSONArray = new JSONArray();
            int i = 0;
            while (navigationPath.mSections != null && i < navigationPath.mSections.length) {
                NavigationSection navigationSection = navigationPath.mSections[i];
                JSONObject jSONObject2 = new JSONObject();
                agd.a(jSONObject2, (String) "mNavigtionAction", (int) navigationSection.mNavigtionAction);
                agd.a(jSONObject2, (String) "mNaviAssiAction", (int) navigationSection.mNaviAssiAction);
                agd.a(jSONObject2, (String) "mStreetName", navigationSection.mStreetName);
                agd.a(jSONObject2, (String) "mDataLength", navigationSection.mDataLength);
                agd.a(jSONObject2, (String) "mPathlength", navigationSection.mPathlength);
                agd.a(jSONObject2, (String) "mPointNum", navigationSection.mPointNum);
                JSONArray jSONArray2 = new JSONArray();
                for (int i2 = 0; i2 < navigationSection.mPointNum; i2++) {
                    JSONObject jSONObject3 = new JSONObject();
                    agd.a(jSONObject3, (String) DictionaryKeys.CTRLXY_X, navigationSection.mGeoPoints[i2].x);
                    agd.a(jSONObject3, (String) DictionaryKeys.CTRLXY_Y, navigationSection.mGeoPoints[i2].y);
                    jSONArray2.put(jSONObject3);
                }
                jSONObject2.put("points", jSONArray2);
                jSONArray.put(jSONObject2);
                i++;
            }
            jSONObject.put("navigationSection", jSONArray);
            if (navigationPath.mGroupNaviSectionList != null && navigationPath.mGroupNaviSectionList.size() > 0) {
                JSONArray jSONArray3 = new JSONArray();
                for (GroupNavigationSection next : navigationPath.mGroupNaviSectionList) {
                    JSONObject jSONObject4 = new JSONObject();
                    agd.a(jSONObject4, (String) "groupSegCount", next.m_nSegCount);
                    agd.a(jSONObject4, (String) "groupArrivePass", next.m_bArrivePass ? 1 : 0);
                    agd.a(jSONObject4, (String) "groupStartSegId", next.m_nStartSegId);
                    agd.a(jSONObject4, (String) "groupName", next.m_GroupName);
                    agd.a(jSONObject4, (String) "groupDistance", next.m_nDistance);
                    agd.a(jSONObject4, (String) "groupToll", next.m_nToll);
                    jSONArray3.put(jSONObject4);
                }
                jSONObject.put("carGroupSegment", jSONArray3);
            }
            try {
                return jSONObject.toString();
            } catch (OutOfMemoryError unused) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
