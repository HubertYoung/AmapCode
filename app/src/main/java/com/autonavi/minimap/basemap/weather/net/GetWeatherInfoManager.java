package com.autonavi.minimap.basemap.weather.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AosParserResponse;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.basemap.weather.net.entity.WeatherResponse;
import com.autonavi.minimap.basemap.weather.net.entity.WeatherWrapper;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.location.LocationInstrument;
import de.greenrobot.event.EventBus;
import java.lang.ref.WeakReference;

public final class GetWeatherInfoManager {
    public csx a;
    public volatile com.autonavi.common.Callback.a b;
    private WeatherInfoCallback c = new WeatherInfoCallback(this);
    private boolean d = true;

    static class GpsCallback implements Callback<GeoPoint> {
        private WeakReference<GetWeatherInfoManager> mHost;

        public GpsCallback(GetWeatherInfoManager getWeatherInfoManager) {
            this.mHost = new WeakReference<>(getWeatherInfoManager);
        }

        public void callback(GeoPoint geoPoint) {
            GetWeatherInfoManager getWeatherInfoManager = (GetWeatherInfoManager) this.mHost.get();
            if (getWeatherInfoManager != null) {
                GetWeatherInfoManager.a(getWeatherInfoManager, geoPoint);
            }
        }

        public void error(Throwable th, boolean z) {
            GetWeatherInfoManager getWeatherInfoManager = (GetWeatherInfoManager) this.mHost.get();
            if (getWeatherInfoManager != null) {
                GetWeatherInfoManager.a(getWeatherInfoManager, LocationInstrument.getInstance().getLatestPosition());
            }
        }
    }

    static class WeatherInfoCallback implements AosResponseCallbackOnUi<WeatherResponse> {
        private WeakReference<GetWeatherInfoManager> a;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            WeatherResponse weatherResponse = (WeatherResponse) aosResponse;
            GetWeatherInfoManager getWeatherInfoManager = (GetWeatherInfoManager) this.a.get();
            if (getWeatherInfoManager != null) {
                if (weatherResponse == null || !weatherResponse.j || weatherResponse.f != 1) {
                    GetWeatherInfoManager.a(getWeatherInfoManager);
                } else {
                    StringBuilder sb = new StringBuilder("fetch data success：");
                    sb.append(weatherResponse.toString());
                    GetWeatherInfoManager.a(sb.toString());
                    GetWeatherInfoManager.a(weatherResponse);
                    getWeatherInfoManager.b(weatherResponse);
                }
            }
        }

        public WeatherInfoCallback(GetWeatherInfoManager getWeatherInfoManager) {
            this.a = new WeakReference<>(getWeatherInfoManager);
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            GetWeatherInfoManager getWeatherInfoManager = (GetWeatherInfoManager) this.a.get();
            if (getWeatherInfoManager != null) {
                GetWeatherInfoManager.a(getWeatherInfoManager);
            }
        }
    }

    static class a {
        private WeakReference<GetWeatherInfoManager> a;

        /* synthetic */ a(GetWeatherInfoManager getWeatherInfoManager, byte b) {
            this(getWeatherInfoManager);
        }

        private a(GetWeatherInfoManager getWeatherInfoManager) {
            this.a = new WeakReference<>(getWeatherInfoManager);
        }
    }

    public GetWeatherInfoManager() {
        a((String) "add view success.");
        LogManager.actionLogV2("P00386", "B001");
        if (((czg) ((IMainMapService) ank.a(IMainMapService.class)).a(czg.class)) != null) {
            a((String) "inject delegate success.");
            new a(this, 0);
        }
    }

    public final void a() {
        a((String) "start fetch data.");
        if (this.a == null) {
            this.a = new csx();
        }
        this.a.a();
        com.autonavi.common.Callback.a aVar = this.b;
        if (aVar != null && !aVar.isCancelled()) {
            aVar.cancel();
        }
        a((String) "start get geo point.");
        this.b = bib.a(new GpsCallback(this), 20000);
    }

    public static void a(String str) {
        bez.b("GetWeatherInfoManager", "log:".concat(String.valueOf(str)), new bew[0]);
    }

    static void a(WeatherResponse weatherResponse) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a((AosParserResponse) weatherResponse);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(WeatherResponse weatherResponse) {
        if (this.d) {
            csy csy = new csy();
            csy.a = weatherResponse;
            if (weatherResponse != null) {
                csy.b = true;
            }
            EventBus.getDefault().post(csy);
            this.d = false;
        }
    }

    static /* synthetic */ void a(GetWeatherInfoManager getWeatherInfoManager) {
        a((String) "fetch data failure.");
        a((WeatherResponse) null);
        getWeatherInfoManager.b(null);
    }

    static /* synthetic */ void a(GetWeatherInfoManager getWeatherInfoManager, GeoPoint geoPoint) {
        StringBuilder sb = new StringBuilder("get geo point success:");
        sb.append(geoPoint == null ? " null" : geoPoint.toString());
        a(sb.toString());
        getWeatherInfoManager.b = null;
        if (geoPoint != null) {
            csx csx = getWeatherInfoManager.a;
            WeatherInfoCallback weatherInfoCallback = getWeatherInfoManager.c;
            csx.a = aax.a(new WeatherWrapper(geoPoint));
            yq.a();
            yq.a(csx.a, (AosResponseCallback<T>) weatherInfoCallback);
        }
    }
}
