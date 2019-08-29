package com.alipay.mobile.framework.service;

import android.content.Context;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationListener;

@Deprecated
public interface LBSLocationProxyInterface {
    void doRequestLocationUpdates(Context context, boolean z, LBSLocationListener lBSLocationListener, long j, long j2, boolean z2, String str, boolean z3, String str2, boolean z4);

    LBSLocation getLastKnownLocation(Context context);

    boolean isEnable();

    void removeUpdates(Context context, LBSLocationListener lBSLocationListener);

    void removeUpdatesContinuous(Context context, LBSLocationListener lBSLocationListener);

    void requestLocationUpdatesContinuous(Context context, boolean z, long j, float f, LBSLocationListener lBSLocationListener, String str, boolean z2, String str2);

    void setEnable(boolean z);

    void setSdkLocationFailedisFromAPP(boolean z);
}
