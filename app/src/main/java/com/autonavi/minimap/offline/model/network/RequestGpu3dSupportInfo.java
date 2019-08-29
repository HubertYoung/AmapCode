package com.autonavi.minimap.offline.model.network;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.offline.controller.IOfflineCallback;
import com.autonavi.minimap.offline.utils.OfflineSpUtil;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepClassMembers;

public class RequestGpu3dSupportInfo {
    private String mGpuModel;
    /* access modifiers changed from: private */
    public ReentrantLock mLock = new ReentrantLock();

    class RequestGpu3dSupportCallback extends FalconAosPrepareResponseCallback<byte[]> {
        boolean a = false;
        private IOfflineCallback c;

        public final /* synthetic */ void a(Object obj) {
            byte[] bArr = (byte[]) obj;
            OfflineSpUtil.setSuportDimension(this.a);
            if (this.c != null) {
                this.c.callback(bArr != null);
            }
        }

        public RequestGpu3dSupportCallback(IOfflineCallback iOfflineCallback) {
            this.c = iOfflineCallback;
        }

        /* access modifiers changed from: private */
        /* renamed from: b */
        public byte[] a(AosByteResponse aosByteResponse) {
            if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String((byte[]) aosByteResponse.getResult(), "UTF-8"));
                if (jSONObject.optBoolean("result")) {
                    this.a = jSONObject.optBoolean("support");
                }
                return new byte[0];
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            if (this.c != null) {
                this.c.callback(false);
            }
            if (RequestGpu3dSupportInfo.this.mLock.isHeldByCurrentThread()) {
                RequestGpu3dSupportInfo.this.mLock.unlock();
            }
        }
    }

    @KeepClassMembers
    @Path(builder = AosURLBuilder.class, host = "offline_aos_url", sign = {"gpu_model"}, url = "ws/app/check/gpu_3d_support?")
    static class RequestGpu3dSupportParam implements ParamEntity {
        String gpu_model = "";

        public RequestGpu3dSupportParam(String str) {
            this.gpu_model = str;
        }
    }

    public RequestGpu3dSupportInfo(String str) {
        this.mGpuModel = str;
    }

    public void exec(IOfflineCallback iOfflineCallback) {
        RequestGpu3dSupportParam requestGpu3dSupportParam = new RequestGpu3dSupportParam(this.mGpuModel);
        try {
            this.mLock.lock();
            AosGetRequest a = aax.a(requestGpu3dSupportParam);
            a.setRetryTimes(0);
            a.setTimeout(8000);
            yq.a();
            yq.a((AosRequest) a, (AosResponseCallback<T>) new RequestGpu3dSupportCallback<T>(iOfflineCallback));
        } finally {
            try {
                this.mLock.unlock();
            } catch (Exception unused) {
            }
        }
    }
}
