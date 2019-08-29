package com.amap.bundle.drivecommon.route.utils;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.drivecommon.model.GroupNavigationSection;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.LongDistnceSceneData;
import com.amap.bundle.drivecommon.model.LongDistnceSceneData.a;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.model.NavigationSection;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.model.FormWay;
import com.autonavi.jni.ae.route.model.GroupSegment;
import com.autonavi.jni.ae.route.model.JamInfo;
import com.autonavi.jni.ae.route.model.LabelInfo;
import com.autonavi.jni.ae.route.model.LineItem;
import com.autonavi.jni.ae.route.model.RestrictionInfo;
import com.autonavi.jni.ae.route.model.RouteIncident;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.jni.ae.route.route.RouteLink;
import com.autonavi.jni.ae.route.route.RouteSegment;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class CarRouteParser {
    public static final int LINE_SEG_CAP = 50;

    public static NavigationResult parseCalcRouteResult(ICarRouteResult iCarRouteResult, CalcRouteResult calcRouteResult) {
        CalcRouteScene calcRouteScene = iCarRouteResult.getCalcRouteScene();
        long currentTimeMillis = System.currentTimeMillis();
        iCarRouteResult.setCalcRouteResult(calcRouteResult);
        NavigationResult navigationResult = new NavigationResult();
        int pathCount = calcRouteResult.getPathCount();
        POI fromPOI = iCarRouteResult.getFromPOI();
        POI toPOI = iCarRouteResult.getToPOI();
        navigationResult.mPathNum = pathCount;
        navigationResult.mstartX = fromPOI.getPoint().x;
        navigationResult.mstartY = fromPOI.getPoint().y;
        navigationResult.mendX = toPOI.getPoint().x;
        navigationResult.mendY = toPOI.getPoint().y;
        navigationResult.mPaths = new NavigationPath[pathCount];
        if (!(calcRouteScene == CalcRouteScene.SCENE_HOME_TMC || calcRouteScene == CalcRouteScene.SCENE_COMPANY_TMC)) {
            navigationResult.maxBound = DriveUtil.getRouteResultBound(calcRouteResult);
        }
        for (int i = 0; i < pathCount; i++) {
            navigationResult.mPaths[i] = parseNavigationPath(calcRouteResult.getRoute(i), calcRouteScene);
        }
        iCarRouteResult.setNaviResultData(fromPOI, toPOI, navigationResult, iCarRouteResult.getMethod());
        StringBuilder sb = new StringBuilder();
        sb.append(iCarRouteResult.getFromPOI().getName());
        sb.append("-> ");
        sb.append(iCarRouteResult.getToPOI().getName());
        sb.append(Token.SEPARATOR);
        sb.append(cfe.a(navigationResult.mPaths[0].mPathlength));
        sb.append(" 解析耗时：");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        AMapLog.e("sinber", sb.toString());
        return navigationResult;
    }

    public static NavigationPath parseNavigationPath(Route route, CalcRouteScene calcRouteScene) {
        int i;
        int i2;
        int segmentCount = route.getSegmentCount();
        NavigationPath navigationPath = new NavigationPath();
        LabelInfo[] pathLabel = route.getPathLabel();
        if (pathLabel != null && pathLabel.length > 0) {
            navigationPath.mTagName = pathLabel[0].content;
        }
        navigationPath.mDataLength = 0;
        navigationPath.mPathlength = route.getRouteLength();
        navigationPath.mCostTime = route.getRouteTime();
        navigationPath.mPathStrategy = route.getRouteStrategy();
        navigationPath.mSectionNum = segmentCount;
        navigationPath.mSections = new NavigationSection[segmentCount];
        navigationPath.mLimitRoadFlag = route.getBypassLimitedRoad();
        navigationPath.mTmcTime = route.getDiffToTMCRoute();
        navigationPath.isTruckRoute = route.isTruckPath() ? 1 : 0;
        navigationPath.mLongDistnceSceneData = new LongDistnceSceneData();
        navigationPath.mLongDistnceSceneData.a(route.getCityAdcodeList());
        navigationPath.mTollCost = route.getTollCost();
        navigationPath.mTollLength = route.getTollLength();
        navigationPath.mIsHolidayFree = route.isHolidayFree();
        navigationPath.mRouteTip = route.getTip();
        navigationPath.mLineIconPoints = route.getLineIconPoints();
        navigationPath.mTrafficNum = route.getTrafficLightNum();
        navigationPath.mRouteId = route.getRouteId();
        navigationPath.mPathId = route.getPathId();
        if (calcRouteScene == CalcRouteScene.SCENE_HOME_TMC || calcRouteScene == CalcRouteScene.SCENE_COMPANY_TMC || calcRouteScene == CalcRouteScene.SCENE_TRAFIC_REMIND_TMC) {
            return navigationPath;
        }
        GroupSegment[] groupSegmentList = route.getGroupSegmentList();
        if (groupSegmentList != null) {
            int length = groupSegmentList.length;
            for (int i3 = 0; i3 < length; i3++) {
                GroupSegment groupSegment = groupSegmentList[i3];
                if (groupSegment != null) {
                    navigationPath.mGroupNaviSectionList.add(convertToGroupNavigationSection(i3, groupSegment));
                }
            }
        }
        navigationPath.mRestAreaInfo = route.getRestAreas(0, 0);
        RestrictionInfo restrictionInfo = route.getRestrictionInfo();
        if (restrictionInfo != null) {
            sq sqVar = new sq();
            sqVar.b = restrictionInfo.desc;
            sqVar.a = restrictionInfo.title;
            sqVar.c = restrictionInfo.type;
            sqVar.d = restrictionInfo.cityCode;
            sqVar.e = restrictionInfo.titleType;
            sqVar.f = restrictionInfo.tips;
            sqVar.g = restrictionInfo.infoList;
            sqVar.h = restrictionInfo.tailNums;
            navigationPath.mRestrictionInfo = sqVar;
        }
        navigationPath.cityCodes = route.getCityAdcodeList();
        LineItem[] lineItems = route.getLineItems();
        if (lineItems != null && lineItems.length > 0) {
            navigationPath.mEngineLineItem = lineItems[0];
        }
        CalcRouteScene calcRouteScene2 = CalcRouteScene.SCENE_COMMUTE;
        for (int i4 = 0; i4 < segmentCount; i4++) {
            RouteSegment segment = route.getSegment(i4);
            if (segment != null) {
                parseNavigationSection(navigationPath, segment, i4);
            }
        }
        JamInfo[] jamInfoList = route.getJamInfoList();
        if (jamInfoList != null) {
            int length2 = jamInfoList.length;
            for (int i5 = 0; i5 < length2; i5++) {
                if (jamInfoList[i5] != null) {
                    com.amap.bundle.drivecommon.model.JamInfo jamInfo = new com.amap.bundle.drivecommon.model.JamInfo();
                    jamInfo.gPoint = new GeoPoint(jamInfoList[i5].lon, jamInfoList[i5].lat);
                    jamInfo.speed = jamInfoList[i5].speed;
                    navigationPath.mJamInfo.add(jamInfo);
                }
            }
        }
        navigationPath.mTDRJamFadeAreas = route.getTDRJamFadeAreas();
        RouteIncident[] inRouteIncident = route.getInRouteIncident();
        RouteIncident[] routeIncident = route.getRouteIncident();
        if (inRouteIncident == null) {
            i = 0;
        } else {
            i = inRouteIncident.length;
        }
        if (routeIncident == null) {
            i2 = 0;
        } else {
            i2 = routeIncident.length;
        }
        int i6 = i + i2;
        if (i6 > 0) {
            navigationPath.mIncidentTipsTypeArray = new int[i6];
            navigationPath.mIncidentStrArray = new String[i6];
            navigationPath.mHasShowIncidentArray = new boolean[i6];
            navigationPath.mIncidentEventId = new int[i6];
        }
        if (inRouteIncident != null && i > 0) {
            for (int i7 = 0; i7 < i; i7++) {
                RouteIncident routeIncident2 = inRouteIncident[i7];
                if (!(routeIncident2 == null || routeIncident2.tipsType == 5)) {
                    navigationPath.mHasShowIncidentArray[i7] = false;
                    navigationPath.mIncidentTipsTypeArray[i7] = routeIncident2.tipsType;
                    navigationPath.mIncidentStrArray[i7] = getIncidentDesc(routeIncident2);
                    navigationPath.mIncidentEventId[i7] = routeIncident2.id;
                }
            }
        }
        if (routeIncident != null && i2 > 0) {
            for (int i8 = 0; i8 < i2; i8++) {
                RouteIncident routeIncident3 = routeIncident[i8];
                if (routeIncident3 != null) {
                    if (navigationPath.mHasShowIncidentArray != null) {
                        navigationPath.mHasShowIncidentArray[i + i8] = false;
                    }
                    if (navigationPath.mIncidentTipsTypeArray != null) {
                        navigationPath.mIncidentTipsTypeArray[i + i8] = routeIncident3.tipsType;
                    }
                    if (navigationPath.mIncidentStrArray != null) {
                        navigationPath.mIncidentStrArray[i + i8] = getIncidentDesc(routeIncident3);
                    }
                    if (navigationPath.mIncidentEventId != null) {
                        navigationPath.mIncidentEventId[i + i8] = routeIncident3.id;
                    }
                }
            }
        }
        LongDistnceSceneData longDistnceSceneData = navigationPath.mLongDistnceSceneData;
        List<GroupNavigationSection> list = navigationPath.mGroupNaviSectionList;
        if (longDistnceSceneData.a == null) {
            longDistnceSceneData.a = new ArrayList();
        }
        for (GroupNavigationSection next : list) {
            a aVar = new a();
            aVar.b = next.m_GroupName;
            aVar.a = next.posPoint;
            aVar.c = next.m_nDistance;
            longDistnceSceneData.a.add(aVar);
        }
        return navigationPath;
    }

    private static void parseNavigationSection(NavigationPath navigationPath, RouteSegment routeSegment, int i) {
        NavigationSection navigationSection = new NavigationSection();
        navigationSection.mLineOverlayList = new ArrayList<>();
        navigationSection.mLineBgOverlayList = new ArrayList<>();
        navigationSection.mRestrictedLineItemList = new ArrayList();
        navigationSection.mIndex = i;
        int i2 = 0;
        navigationSection.mDataLength = 0;
        RouteLink noCrossLink = getNoCrossLink(routeSegment);
        if (noCrossLink != null) {
            navigationSection.mStreetName = toDBC(noCrossLink.getLinkRoadName());
        }
        navigationSection.mPathlength = routeSegment.getSegLength();
        navigationSection.mTollCost = routeSegment.getSegTollCost();
        navigationSection.mChargeLength = routeSegment.getSegChargeLength();
        navigationSection.mTollPathName = toDBC(routeSegment.getSegTollPathName());
        navigationSection.mGeoPoints = new GeoPoint[1];
        GeoPoint geoPoint = new GeoPoint(routeSegment.getStartPoint().getX(), routeSegment.getStartPoint().getY());
        navigationSection.mGeoPoints[0] = geoPoint;
        navigationPath.mStackGeoPoint.add(geoPoint);
        navigationSection.mTrafficLights = routeSegment.getTrafficLightNum();
        navigationSection.isRightPassArea = routeSegment.isRightPassArea();
        int mainAction = routeSegment.getMainAction();
        int assistAction = routeSegment.getAssistAction();
        byte b = 9;
        if (assistAction != 5) {
            switch (assistAction) {
                case 33:
                    b = 13;
                    break;
                case 34:
                    b = 14;
                    break;
                case 35:
                    b = 10;
                    break;
                case 36:
                    b = 15;
                    break;
                default:
                    switch (mainAction) {
                        case 1:
                            b = 2;
                            break;
                        case 2:
                            b = 3;
                            break;
                        case 3:
                        case 9:
                            b = 4;
                            break;
                        case 4:
                        case 10:
                            b = 5;
                            break;
                        case 5:
                            b = 6;
                            break;
                        case 6:
                            b = 7;
                            break;
                        case 7:
                            b = 8;
                            break;
                        case 11:
                            b = ClientRpcPack.SYMMETRIC_ENCRYPT_AES;
                            break;
                        case 12:
                            b = ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
                            break;
                    }
            }
        } else {
            b = 16;
        }
        navigationSection.mNavigtionAction = b;
        navigationSection.mNaviAssiAction = (byte) routeSegment.getAssistAction();
        if (navigationPath.mGroupNaviSectionList != null && navigationPath.mGroupNaviSectionList.size() > 0) {
            Iterator<GroupNavigationSection> it = navigationPath.mGroupNaviSectionList.iterator();
            while (true) {
                if (it.hasNext()) {
                    GroupNavigationSection next = it.next();
                    if (i < next.m_nStartSegId + next.m_nSegCount) {
                        if (next.posPoint == null) {
                            GeoPoint[] geoPointArr = navigationSection.mGeoPoints;
                            int length = geoPointArr.length;
                            while (true) {
                                if (i2 < length) {
                                    GeoPoint geoPoint2 = geoPointArr[i2];
                                    if (geoPoint2 != null) {
                                        next.posPoint = geoPoint2;
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                        }
                        next.mSectionList.add(navigationSection);
                        next.m_nTrafficLights += navigationSection.mTrafficLights;
                    }
                }
            }
        }
        navigationPath.mSections[i] = navigationSection;
    }

    static String toDBC(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 12288) {
                charArray[i] = ' ';
            } else if (charArray[i] > 65280 && charArray[i] < 65375) {
                charArray[i] = (char) (charArray[i] - 65248);
            }
        }
        return new String(charArray);
    }

    static GroupNavigationSection convertToGroupNavigationSection(int i, GroupSegment groupSegment) {
        GroupNavigationSection groupNavigationSection = new GroupNavigationSection();
        groupNavigationSection.index = i;
        if (TextUtils.isEmpty(groupSegment.roadName)) {
            groupNavigationSection.m_GroupName = "无名道路";
        } else {
            groupNavigationSection.m_GroupName = toDBC(groupSegment.roadName);
        }
        groupNavigationSection.m_nSegCount = groupSegment.segCount;
        groupNavigationSection.m_bArrivePass = groupSegment.isViaPoint;
        groupNavigationSection.m_nStartSegId = groupSegment.startSegId;
        groupNavigationSection.m_nDistance = groupSegment.length;
        groupNavigationSection.m_nToll = groupSegment.tollCost;
        groupNavigationSection.m_nStatus = groupSegment.status;
        groupNavigationSection.m_nSpeed = groupSegment.speed;
        groupNavigationSection.m_bIsSrucial = groupSegment.isSrucial;
        return groupNavigationSection;
    }

    static String getIncidentDesc(RouteIncident routeIncident) {
        if (TextUtils.isEmpty(routeIncident.title)) {
            return null;
        }
        if (4 != routeIncident.tipsType) {
            return routeIncident.title;
        }
        String str = routeIncident.title;
        if (str.endsWith(".") || str.endsWith("。") || str.endsWith(",")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, str.length() - 1));
            sb.append(AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_already_avoid_incident_with_comma));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(AMapAppGlobal.getApplication().getResources().getString(R.string.autonavi_already_avoid_incident_with_comma));
        return sb2.toString();
    }

    private static RouteLink getNoCrossLink(RouteSegment routeSegment) {
        for (int i = 0; i < routeSegment.getLinkCount(); i++) {
            RouteLink link = routeSegment.getLink(i);
            if (link != null && link.getLinkFormWay() != FormWay.Formway_Cross_Link) {
                return link;
            }
        }
        return null;
    }

    private static String string(int i) {
        return AMapAppGlobal.getApplication().getString(i);
    }
}
