package com.autonavi.jni.eyrie.amap.tbt.bus;

import android.text.TextUtils;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusAlterBusRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusETARequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusRealTimeRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusRouteRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusTrafficRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.TaxiComparatorRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusAlterBusResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRouteResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusTrafficResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.TaxiComparatorResponse;

public class BusServiceManager {
    private IJSONParser mJsonParser;

    static class Holder {
        /* access modifiers changed from: private */
        public static final BusServiceManager _instance = new BusServiceManager();

        private Holder() {
        }
    }

    private static native void nativeCancel(int i);

    private static native void nativeCancelAll();

    private static native void nativeRegisterBusService(BusServiceObserver busServiceObserver);

    private static native void nativeRequest(int i, int i2, String str);

    private static native void nativeSendCommand(int i, String str);

    private static native void nativeUnregisterBusService(BusServiceObserver busServiceObserver);

    private BusServiceManager() {
    }

    public static BusServiceManager getInstance() {
        return Holder._instance;
    }

    public void setJsonParser(IJSONParser iJSONParser) {
        this.mJsonParser = iJSONParser;
    }

    public void sendCommand(BusCommandType busCommandType, String str) {
        nativeSendCommand(busCommandType.ordinal(), str);
    }

    public void registerBusService(BusServiceObserver busServiceObserver) {
        nativeRegisterBusService(busServiceObserver);
    }

    public void unregisterBusService(BusServiceObserver busServiceObserver) {
        nativeUnregisterBusService(busServiceObserver);
    }

    public BusRouteResponse parseBusRoute(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (BusRouteResponse) this.mJsonParser.parseObject(str, BusRouteResponse.class);
    }

    public BusAlterBusResponse parseAlterBus(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (BusAlterBusResponse) this.mJsonParser.parseObject(str, BusAlterBusResponse.class);
    }

    public BusRealTimeResponse parseRealTime(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (BusRealTimeResponse) this.mJsonParser.parseObject(str, BusRealTimeResponse.class);
    }

    public BusTrafficResponse parseBusTraffic(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (BusTrafficResponse) this.mJsonParser.parseObject(str, BusTrafficResponse.class);
    }

    public TaxiComparatorResponse parseTaxiComparator(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (TaxiComparatorResponse) this.mJsonParser.parseObject(str, TaxiComparatorResponse.class);
    }

    public void requestBusRoute(int i, BusRouteRequestParam busRouteRequestParam) {
        request(i, BusRequestType.BusRequestTypeRoute, this.mJsonParser.toJSONString(busRouteRequestParam));
    }

    public void requestBusRealTime(int i, BusRealTimeRequestParam busRealTimeRequestParam) {
        request(i, BusRequestType.BusRequestTypeRealTime, this.mJsonParser.toJSONString(busRealTimeRequestParam));
    }

    public void requestBusETA(int i, BusETARequestParam busETARequestParam) {
        request(i, BusRequestType.BusRequestTypeETA, this.mJsonParser.toJSONString(busETARequestParam));
    }

    public void requestBusTraffic(int i, BusTrafficRequestParam busTrafficRequestParam) {
        request(i, BusRequestType.BusRequestTypeTraffic, this.mJsonParser.toJSONString(busTrafficRequestParam));
    }

    public void requestBusAlterBus(int i, BusAlterBusRequestParam busAlterBusRequestParam) {
        request(i, BusRequestType.BusRequestTypeAlterBus, this.mJsonParser.toJSONString(busAlterBusRequestParam));
    }

    public void requestTaxiComparator(int i, TaxiComparatorRequestParam taxiComparatorRequestParam) {
        request(i, BusRequestType.BusRequestTypeTaxiComparator, this.mJsonParser.toJSONString(taxiComparatorRequestParam));
    }

    private void request(int i, BusRequestType busRequestType, String str) {
        nativeRequest(i, busRequestType.ordinal(), str);
    }

    public void cancel(int i) {
        nativeCancel(i);
    }

    public void cancelAll() {
        nativeCancelAll();
    }
}
