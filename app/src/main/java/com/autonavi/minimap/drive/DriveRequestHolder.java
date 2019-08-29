package com.autonavi.minimap.drive;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.drive.param.CategoryRequest;
import com.autonavi.minimap.drive.param.ListRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class DriveRequestHolder {
    private static volatile DriveRequestHolder instance;

    private DriveRequestHolder() {
    }

    public static DriveRequestHolder getInstance() {
        if (instance == null) {
            synchronized (DriveRequestHolder.class) {
                if (instance == null) {
                    instance = new DriveRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendCategory(CategoryRequest categoryRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendCategory(categoryRequest, new dkn(), aosResponseCallback);
    }

    public void sendList(ListRequest listRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendList(listRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendCategory(CategoryRequest categoryRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            categoryRequest.addHeaders(dkn.d);
            categoryRequest.setTimeout(dkn.b);
            categoryRequest.setRetryTimes(dkn.c);
        }
        categoryRequest.setUrl(CategoryRequest.a);
        categoryRequest.addSignParam("channel");
        categoryRequest.addSignParam("ts");
        categoryRequest.addReqParam("ts", categoryRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) categoryRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) categoryRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendList(ListRequest listRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            listRequest.addHeaders(dkn.d);
            listRequest.setTimeout(dkn.b);
            listRequest.setRetryTimes(dkn.c);
        }
        listRequest.setUrl(ListRequest.a);
        listRequest.addSignParam("channel");
        listRequest.addSignParam("ts");
        listRequest.addSignParam("pagesize");
        listRequest.addSignParam("page_num");
        listRequest.addReqParam("ts", listRequest.b);
        listRequest.addReqParam("pagesize", listRequest.c);
        listRequest.addReqParam("page_num", listRequest.d);
        listRequest.addReqParam("cate_id", listRequest.e);
        listRequest.addReqParam(DictionaryKeys.CTRLXY_X, listRequest.f);
        listRequest.addReqParam(DictionaryKeys.CTRLXY_Y, listRequest.g);
        if (dkn != null) {
            in.a().a((AosRequest) listRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) listRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
