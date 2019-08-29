package com.amap.bundle.environmentmap.net.request;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.environmentmap.net.parser.EnvironmentGridParser;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import java.lang.ref.WeakReference;

public final class EnvironmentGridRequest {
    WeakReference<uq> a;
    public EnvironmentGridCallback b;
    public AosPostRequest c;
    public volatile boolean d = false;

    class EnvironmentGridCallback implements AosResponseCallbackOnUi<EnvironmentGridParser> {
        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        }

        private EnvironmentGridCallback() {
        }

        /* synthetic */ EnvironmentGridCallback(EnvironmentGridRequest environmentGridRequest, byte b) {
            this();
        }

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            EnvironmentGridParser environmentGridParser = (EnvironmentGridParser) aosResponse;
            if (EnvironmentGridRequest.this.a != null && EnvironmentGridRequest.this.a.get() != null) {
                ((uq) EnvironmentGridRequest.this.a.get()).a(environmentGridParser.a);
            }
        }
    }

    @Path(builder = AosURLBuilder.class, host = "environment_grid_url", url = "/ws/mps/lyrdata/environment/grid")
    public static class EnvironmentGridParam implements ParamEntity {
        private EnvironmentGridParam() {
        }

        public /* synthetic */ EnvironmentGridParam(byte b) {
            this();
        }
    }

    public EnvironmentGridRequest(uq uqVar) {
        this.a = new WeakReference<>(uqVar);
        this.b = new EnvironmentGridCallback(this, 0);
    }
}
