package com.autonavi.minimap.bundle.maphome.service;

import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import com.amap.bundle.network.response.AosParserResponse;
import com.autonavi.bundle.life.api.api.IScenicGuideItemClickCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayCloseCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayRouteItemClickCallback;
import com.autonavi.bundle.life.api.api.IScenicPlaySelectCallback;
import com.autonavi.bundle.life.api.entity.ScenicGuideListItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayEntity;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import java.util.ArrayList;

public interface IMainMapService extends bie {
    @Nullable
    <T extends czi> T a(Class<T> cls);

    void a(AosParserResponse aosParserResponse);

    void a(Object obj);

    void a(boolean z);

    void a(boolean z, String str, String str2);

    boolean a();

    @Nullable
    cde b();

    void b(Object obj);

    AbsMsgBoxDispatcher c();

    @Keep
    void closeScenicPlayWidget(IScenicPlayCloseCallback iScenicPlayCloseCallback, boolean z);

    @Keep
    void closeScenicSpeakOperateView();

    @Nullable
    MapManager d();

    @Nullable
    bid e();

    @Nullable
    bty f();

    @Nullable
    bty g();

    @Keep
    int getScenicPlayWidgetLocationY();

    void h();

    int i();

    @Keep
    void initScenicGuideView(ArrayList<ScenicGuideListItemEntity> arrayList);

    @Keep
    boolean isScenicGuideShown();

    @Keep
    boolean isScenicPlayRouteShown();

    @Keep
    boolean isScenicPlayShown();

    @Keep
    boolean isScenicSpeakShown();

    @Keep
    void setMapHomePageStateCollapsed();

    @Keep
    void setScenicGuide(boolean z, ArrayList<ScenicGuideListItemEntity> arrayList, IScenicGuideItemClickCallback iScenicGuideItemClickCallback);

    @Keep
    void setScenicPlay(boolean z, IScenicPlaySelectCallback iScenicPlaySelectCallback);

    @Keep
    void setScenicPlayRoute(boolean z, ScenicPlayEntity scenicPlayEntity, IScenicPlayRouteItemClickCallback iScenicPlayRouteItemClickCallback, IScenicPlayCloseCallback iScenicPlayCloseCallback, amv amv, int i);

    @Keep
    void setScenicSpeak(boolean z, String str, String str2, String str3, int i, boolean z2, amv amv);
}
