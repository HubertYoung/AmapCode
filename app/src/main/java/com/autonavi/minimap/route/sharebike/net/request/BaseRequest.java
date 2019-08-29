package com.autonavi.minimap.route.sharebike.net.request;

import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import java.lang.reflect.InvocationTargetException;

public abstract class BaseRequest {
    protected dkn config = new dkn((int) HttpConstants.CONNECTION_TIME_OUT);
    protected BaseResponser mBaseResponser;
    protected AosRequest mParam;
    protected a mRequestListener;

    public interface a {
        void a(BaseNetResult baseNetResult);
    }

    public abstract Class<? extends BaseResponser> getResponsorClass();

    public abstract void send(AosResponseCallback<AosByteResponse> aosResponseCallback);

    public BaseRequest(AosRequest aosRequest, a aVar) {
        this.mParam = aosRequest;
        this.mRequestListener = aVar;
    }

    public AosRequest getEntity() {
        return this.mParam;
    }

    @Nullable
    public BaseResponser initResponser() {
        BaseResponser baseResponser;
        try {
            baseResponser = (BaseResponser) getResponsorClass().getConstructor(new Class[]{Class.class, a.class}).newInstance(new Object[]{getClass(), this.mRequestListener});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            baseResponser = null;
        }
        this.mBaseResponser = baseResponser;
        return baseResponser;
    }

    public a getRequestListener() {
        return this.mRequestListener;
    }
}
