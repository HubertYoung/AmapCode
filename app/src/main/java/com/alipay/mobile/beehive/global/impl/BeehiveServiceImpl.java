package com.alipay.mobile.beehive.global.impl;

import android.content.Context;
import com.alipay.mobile.beehive.api.BeehiveService;
import com.alipay.mobile.beehive.api.BirdNestUrlGetter;
import com.alipay.mobile.beehive.api.EmotionParserExecutor;
import com.alipay.mobile.beehive.api.GetServerTimeExecutor;
import com.alipay.mobile.beehive.api.LocationPermissionSettingExecutor;
import com.alipay.mobile.beehive.api.PoiExtExecutor;
import com.alipay.mobile.beehive.api.ScanExecutor;
import com.alipay.mobile.beehive.api.SchemaExecutor;
import com.alipay.mobile.beehive.api.UserIDGetter;
import com.alipay.mobile.beehive.api.UserSceneExecutor;
import com.alipay.mobile.beehive.model.BeehiveParams;
import com.alipay.mobile.beehive.model.BeehiveResult;

public class BeehiveServiceImpl extends BeehiveService {
    private BirdNestUrlGetter mBirdNestUrlGetter;
    private EmotionParserExecutor mEmotionParserExecutor;
    private GetServerTimeExecutor mGetServerTimeExecutor;
    private LocationPermissionSettingExecutor mLocationPermissionSettingExecutor;
    private PoiExtExecutor mPoiExtExecutor;
    private ScanExecutor mScanExecutor;
    private SchemaExecutor mSchemaExecutor;
    private UserIDGetter mUserIDGetter;
    private UserSceneExecutor userSceneExecutor;

    public BeehiveResult executeBeehiveServiceByName(Context context, BeehiveParams beehiveParams) {
        return null;
    }

    public BeehiveResult executeBeehiveServiceByName(Context context, BeehiveParams beehiveParams, String bizType) {
        return null;
    }

    public LocationPermissionSettingExecutor getLocationPermissionSettingExecutor() {
        return this.mLocationPermissionSettingExecutor;
    }

    public void setLocationPermissionSettingExecutor(LocationPermissionSettingExecutor mLocationPermissionSettingExecutor2) {
        this.mLocationPermissionSettingExecutor = mLocationPermissionSettingExecutor2;
    }

    public SchemaExecutor getSchemaExecutor() {
        return this.mSchemaExecutor;
    }

    public void setSchemaExecutor(SchemaExecutor schemaExecutor) {
        this.mSchemaExecutor = schemaExecutor;
    }

    public ScanExecutor getScanExecutor() {
        return this.mScanExecutor;
    }

    public void setUserIDGetter(UserIDGetter userIDGetter) {
        this.mUserIDGetter = userIDGetter;
    }

    public UserIDGetter getUserIDGetter() {
        return this.mUserIDGetter;
    }

    public void setGetServerTimeExecutor(GetServerTimeExecutor getServerTimeExecutor) {
        this.mGetServerTimeExecutor = getServerTimeExecutor;
    }

    public GetServerTimeExecutor getGetServerTimeExecutor() {
        return this.mGetServerTimeExecutor;
    }

    public void setEmotionParserExecutor(EmotionParserExecutor emotionParserExecutor) {
        this.mEmotionParserExecutor = emotionParserExecutor;
    }

    public EmotionParserExecutor getEmotionParserExecutor() {
        return this.mEmotionParserExecutor;
    }

    public void setScanExecutor(ScanExecutor mScanExecutor2) {
        this.mScanExecutor = mScanExecutor2;
    }

    public void setBirdNestUrlGetter(BirdNestUrlGetter birdNestUrlGetter) {
        this.mBirdNestUrlGetter = birdNestUrlGetter;
    }

    public BirdNestUrlGetter getBirdNestUrlGetter() {
        return this.mBirdNestUrlGetter;
    }

    public PoiExtExecutor getPoiExtExecutor() {
        return this.mPoiExtExecutor;
    }

    public void setPoiExtExecutor(PoiExtExecutor mPoiExtExecutor2) {
        this.mPoiExtExecutor = mPoiExtExecutor2;
    }

    public void setUserSceneExecutor(UserSceneExecutor executor) {
        this.userSceneExecutor = executor;
    }

    public UserSceneExecutor getUserSceneExecutor() {
        return this.userSceneExecutor;
    }
}
