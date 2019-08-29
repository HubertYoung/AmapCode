package com.amap.bundle.drivecommon.navi.navidata;

import android.graphics.Bitmap;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;

@KeepClassMemberNames
@Keep
public abstract class AbstractNavigationDataResult {
    public abstract Bitmap getCompressBitmap(String str);

    public abstract POI getFromPOI();

    public abstract List<POI> getMidPOI();

    /* access modifiers changed from: protected */
    public abstract String getNaviShareFilePath(String str);

    public abstract String getNaviSharePicPath(String str);

    public abstract ArrayList<POI> getPassedViaPoints();

    public abstract GeoPoint getShareEndPos();

    public abstract POI getShareFromPOI();

    public abstract ArrayList<POI> getShareMidPOI();

    public abstract ArrayList<GeoPoint> getShareMidPos();

    public abstract String getShareSinaWeiboBody();

    public abstract GeoPoint getShareStartPos();

    public abstract POI getShareToPOI();

    public abstract String getShareUrl();

    public abstract Bitmap getThumbnailsBitmap(String str);

    public abstract POI getToPOI();
}
