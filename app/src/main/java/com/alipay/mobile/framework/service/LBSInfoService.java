package com.alipay.mobile.framework.service;

import android.content.Context;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.map.model.WifiItemInfo;
import com.alipay.mobilelbs.common.service.facade.vo.Location;
import java.util.List;

public abstract class LBSInfoService extends ExternalService {

    public interface LBSInfoListener {
        void onGetLBSInfoFailed(int i);

        void onLBSInfoChanged(Location location);
    }

    public abstract Location getLastKnownLBSInfo();

    public abstract List<WifiItemInfo> getWifiInfo(int i);

    public abstract void removeLBSInfoUpdatesContinuous(LBSInfoListener lBSInfoListener);

    public abstract void removeUpdates(LBSInfoListener lBSInfoListener);

    public abstract void requestLBSInfoCompensate(Context context, LBSInfoListener lBSInfoListener, int i, String str);

    public abstract void requestLBSInfoUpdates(LBSInfoListener lBSInfoListener);

    public abstract void requestLBSInfoUpdatesContinuous(LBSInfoListener lBSInfoListener, long j, float f);

    public abstract void requestLBSInfoUpdatesEnhanceByIP(LBSInfoListener lBSInfoListener, String str);

    public abstract void tryToGetLocationByWifi(Context context, String str, String str2, OnLBSLocationListener onLBSLocationListener, OnReGeocodeListener onReGeocodeListener);

    @Deprecated
    public abstract void upLoadLocation();
}
