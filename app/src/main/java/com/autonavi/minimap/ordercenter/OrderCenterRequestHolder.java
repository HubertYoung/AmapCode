package com.autonavi.minimap.ordercenter;

import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.ordercenter.param.OrderCategoryRequest;
import com.autonavi.minimap.ordercenter.param.OrderDeleteRequest;
import com.autonavi.minimap.ordercenter.param.ScenicOrdersListRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class OrderCenterRequestHolder {
    private static volatile OrderCenterRequestHolder instance;

    private OrderCenterRequestHolder() {
    }

    public static OrderCenterRequestHolder getInstance() {
        if (instance == null) {
            synchronized (OrderCenterRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new OrderCenterRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendOrderCategory(OrderCategoryRequest orderCategoryRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendOrderCategory(orderCategoryRequest, new dkn(), aosResponseCallback);
    }

    public void sendOrderDelete(OrderDeleteRequest orderDeleteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendOrderDelete(orderDeleteRequest, new dkn(), aosResponseCallback);
    }

    public void sendScenicOrdersList(ScenicOrdersListRequest scenicOrdersListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendScenicOrdersList(scenicOrdersListRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendOrderCategory(OrderCategoryRequest orderCategoryRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            orderCategoryRequest.addHeaders(dkn.d);
            orderCategoryRequest.setTimeout(dkn.b);
            orderCategoryRequest.setRetryTimes(dkn.c);
        }
        orderCategoryRequest.setUrl(OrderCategoryRequest.a);
        orderCategoryRequest.addSignParam("channel");
        orderCategoryRequest.addSignParam("hotel_category");
        orderCategoryRequest.addReqParam("hotel_category", Integer.toString(orderCategoryRequest.b));
        orderCategoryRequest.addReqParam(H5RpcFailResult.LIMIT, Integer.toString(orderCategoryRequest.c));
        orderCategoryRequest.addReqParam("pagenum", Integer.toString(orderCategoryRequest.d));
        orderCategoryRequest.addReqParam("pagesize", Integer.toString(orderCategoryRequest.e));
        if (dkn != null) {
            in.a().a((AosRequest) orderCategoryRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) orderCategoryRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendOrderDelete(OrderDeleteRequest orderDeleteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            orderDeleteRequest.addHeaders(dkn.d);
            orderDeleteRequest.setTimeout(dkn.b);
            orderDeleteRequest.setRetryTimes(dkn.c);
        }
        orderDeleteRequest.setUrl(OrderDeleteRequest.a);
        orderDeleteRequest.addSignParam("channel");
        orderDeleteRequest.addSignParam("mobile");
        orderDeleteRequest.addSignParam("code");
        orderDeleteRequest.addReqParam("oids", orderDeleteRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) orderDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) orderDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendScenicOrdersList(ScenicOrdersListRequest scenicOrdersListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            scenicOrdersListRequest.addHeaders(dkn.d);
            scenicOrdersListRequest.setTimeout(dkn.b);
            scenicOrdersListRequest.setRetryTimes(dkn.c);
        }
        scenicOrdersListRequest.setUrl(ScenicOrdersListRequest.a);
        scenicOrdersListRequest.addSignParam("channel");
        scenicOrdersListRequest.addSignParam("mobile");
        scenicOrdersListRequest.addSignParam("code");
        scenicOrdersListRequest.addReqParam("pagesize", Integer.toString(scenicOrdersListRequest.b));
        scenicOrdersListRequest.addReqParam("pagenum", Integer.toString(scenicOrdersListRequest.c));
        scenicOrdersListRequest.addReqParam("mobile", scenicOrdersListRequest.d);
        scenicOrdersListRequest.addReqParam("code", scenicOrdersListRequest.e);
        scenicOrdersListRequest.addReqParam("timestamp", scenicOrdersListRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) scenicOrdersListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) scenicOrdersListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
