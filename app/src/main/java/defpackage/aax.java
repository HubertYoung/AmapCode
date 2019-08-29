package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosFileUploadRequest;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosHeadRequest;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.network.request.param.builder.AosRequestBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilderFactory;
import com.amap.bundle.network.request.param.builder.URLBuilderFactory.a;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: aax reason: default package */
/* compiled from: ParamEntityProcessor */
public abstract class aax {
    public static AosGetRequest a(ParamEntity paramEntity) {
        return (AosGetRequest) a(paramEntity, 0);
    }

    public static AosPostRequest b(ParamEntity paramEntity) {
        return (AosPostRequest) a(paramEntity, 1);
    }

    public static AosMultipartRequest c(ParamEntity paramEntity) {
        return (AosMultipartRequest) a(paramEntity, 3);
    }

    public static AosRequest a(ParamEntity paramEntity, AosRequest aosRequest) {
        a entityInfo = URLBuilderFactory.getEntityInfo(paramEntity);
        AosRequestBuilder aosRequestBuilder = new AosRequestBuilder();
        try {
            aosRequestBuilder.parse(entityInfo.a, entityInfo.b, paramEntity, false);
        } catch (IllegalAccessException e) {
            if (bno.a) {
                throw new RuntimeException(e);
            }
        }
        String url = aosRequestBuilder.getUrl();
        Map<String, Object> params = aosRequestBuilder.getParams();
        List<String> signParams = aosRequestBuilder.getSignParams();
        if (!bno.a || !TextUtils.isEmpty(url)) {
            aosRequest.setUrl(url);
            if (params != null && params.size() > 0) {
                for (Entry next : params.entrySet()) {
                    if (!TextUtils.isEmpty((CharSequence) next.getKey()) && next.getValue() != null) {
                        aosRequest.addReqParam((String) next.getKey(), next.getValue().toString());
                    }
                }
            }
            if (signParams != null && !signParams.isEmpty()) {
                aosRequest.addSignParams(signParams);
            }
            return aosRequest;
        }
        throw new IllegalStateException("entityToAosRequest, url is null!");
    }

    private static AosRequest a(ParamEntity paramEntity, int i) {
        AosRequest aosRequest;
        switch (i) {
            case 1:
                aosRequest = new AosPostRequest();
                break;
            case 2:
                aosRequest = new AosHeadRequest();
                break;
            case 3:
                aosRequest = new AosMultipartRequest();
                break;
            case 4:
                aosRequest = new AosFileUploadRequest();
                break;
            default:
                aosRequest = new AosGetRequest();
                break;
        }
        return a(paramEntity, aosRequest);
    }
}
