package defpackage;

import android.content.Context;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.aosservice.request.AosFileUploadRequest;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.autonavi.minimap.bl.HttpNetworkImpl;
import com.autonavi.minimap.bl.net.AosRequest;
import com.autonavi.minimap.bl.net.HttpRequest;
import com.autonavi.minimap.bl.net.IAosNetwork;
import com.autonavi.minimap.bl.net.IHttpResponseCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: cta reason: default package */
/* compiled from: AosNetworkImpl */
public final class cta extends HttpNetworkImpl implements IAosNetwork {
    private boolean f;

    public cta(Context context, boolean z, ctg ctg) {
        super(context, ctg);
        this.f = z;
    }

    public final int send(AosRequest aosRequest, IHttpResponseCallback iHttpResponseCallback) {
        return super.send(aosRequest, iHttpResponseCallback);
    }

    public final int send(AosRequest aosRequest, IHttpResponseCallback iHttpResponseCallback, int i) {
        return super.send(aosRequest, iHttpResponseCallback, i);
    }

    public final com.amap.bundle.aosservice.request.AosRequest a(HttpRequest httpRequest) {
        return super.a(httpRequest);
    }

    public final void a(com.amap.bundle.aosservice.request.AosRequest aosRequest, HttpRequest httpRequest) {
        if (httpRequest instanceof AosRequest) {
            int i = 0;
            aosRequest.setWithoutSign(false);
            int i2 = -1;
            if (ctb.b(httpRequest)) {
                i = -1;
            }
            aosRequest.setCommonParamStrategy(i);
            if (this.f) {
                i2 = 2;
            }
            aosRequest.setEncryptStrategy(i2);
            return;
        }
        super.a(aosRequest, httpRequest);
    }

    public final void b(com.amap.bundle.aosservice.request.AosRequest aosRequest, HttpRequest httpRequest) {
        super.b(aosRequest, httpRequest);
        if (httpRequest instanceof AosRequest) {
            AosRequest aosRequest2 = (AosRequest) httpRequest;
            aosRequest.setEncryptSignParam(aosRequest2.getEncryptSignParams());
            aosRequest.addSignParams(aosRequest2.getSignParams());
            aosRequest.setOutput(aosRequest2.getOutputFormat());
            Map<String, String> reqParams = aosRequest2.getReqParams();
            if (!reqParams.isEmpty()) {
                ArrayList<String> arrayList = null;
                for (Entry next : reqParams.entrySet()) {
                    if ("flag_request_value_na".equalsIgnoreCase((String) next.getValue())) {
                        if (arrayList == null) {
                            arrayList = new ArrayList<>();
                        }
                        arrayList.add(next.getKey());
                    }
                }
                if (arrayList != null) {
                    aosRequest2.getDisabledCommonParams().addAll(arrayList);
                    for (String remove : arrayList) {
                        reqParams.remove(remove);
                    }
                }
            }
            aosRequest.setDisabledCommonParams(aosRequest2.getDisabledCommonParams());
            Iterator<String> it = aosRequest.getDisabledCommonParams().iterator();
            while (true) {
                if (it.hasNext()) {
                    if ("ACCS".equals(it.next())) {
                        aosRequest.setChannel(1);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (ctb.b(httpRequest)) {
                String c = ctb.c(httpRequest);
                is a = ip.a();
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                sb.append(AUScreenAdaptTool.PREFIX_ID);
                sb.append(a.a());
                aosRequest.setEncryptSignParam(a.a(sb.toString().getBytes()));
            }
        }
    }

    public final void a(AosFileUploadRequest aosFileUploadRequest, HttpRequest httpRequest) {
        super.a(aosFileUploadRequest, httpRequest);
    }

    public final void a(AosMultipartRequest aosMultipartRequest, HttpRequest httpRequest) {
        super.a(aosMultipartRequest, httpRequest);
    }

    public final void a(AosPostRequest aosPostRequest, HttpRequest httpRequest) {
        boolean z = httpRequest instanceof AosRequest;
        int i = 0;
        boolean z2 = z && this.f;
        byte[] body = httpRequest.getBody();
        if (body != null && z2) {
            body = ip.a().b(body);
        }
        aosPostRequest.setBody(body);
        int reqParamFormat = httpRequest.getReqParamFormat();
        if (reqParamFormat != 3) {
            i = reqParamFormat;
        }
        aosPostRequest.setReqParamFormatStrategy(i);
        aosPostRequest.setContentCompression(httpRequest.isContentCompression());
        if (z) {
            aosPostRequest.setCommonParamFormatStrategy(((AosRequest) httpRequest).getCommonParamPolicy());
            if (ctb.b(httpRequest)) {
                aosPostRequest.setReqParamFormatStrategy(1);
                aosPostRequest.setCommonParamFormatStrategy(1);
            }
        }
    }
}
