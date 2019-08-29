package com.alipay.mobile.beehive.api;

import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.beehive.model.BeehiveParams;
import com.alipay.mobile.beehive.model.BeehiveResult;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class BeehiveService extends ExternalService {
    public abstract BeehiveResult executeBeehiveServiceByName(Context context, BeehiveParams beehiveParams);

    public abstract BeehiveResult executeBeehiveServiceByName(Context context, BeehiveParams beehiveParams, String str);

    public abstract BirdNestUrlGetter getBirdNestUrlGetter();

    public abstract EmotionParserExecutor getEmotionParserExecutor();

    public abstract GetServerTimeExecutor getGetServerTimeExecutor();

    public abstract LocationPermissionSettingExecutor getLocationPermissionSettingExecutor();

    public abstract PoiExtExecutor getPoiExtExecutor();

    public abstract ScanExecutor getScanExecutor();

    public abstract SchemaExecutor getSchemaExecutor();

    public abstract UserIDGetter getUserIDGetter();

    public abstract UserSceneExecutor getUserSceneExecutor();

    public abstract void setBirdNestUrlGetter(BirdNestUrlGetter birdNestUrlGetter);

    public abstract void setEmotionParserExecutor(EmotionParserExecutor emotionParserExecutor);

    public abstract void setGetServerTimeExecutor(GetServerTimeExecutor getServerTimeExecutor);

    public abstract void setLocationPermissionSettingExecutor(LocationPermissionSettingExecutor locationPermissionSettingExecutor);

    public abstract void setPoiExtExecutor(PoiExtExecutor poiExtExecutor);

    public abstract void setScanExecutor(ScanExecutor scanExecutor);

    public abstract void setSchemaExecutor(SchemaExecutor schemaExecutor);

    public abstract void setUserIDGetter(UserIDGetter userIDGetter);

    public abstract void setUserSceneExecutor(UserSceneExecutor userSceneExecutor);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
