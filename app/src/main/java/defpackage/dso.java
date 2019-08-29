package defpackage;

import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.core.network.inter.response.StringResponse;
import com.autonavi.minimap.onekeycheck.action.ActionListener;
import com.autonavi.minimap.onekeycheck.action.BaseAction;
import com.autonavi.minimap.onekeycheck.module.CloudInterfInfos;
import com.autonavi.minimap.onekeycheck.module.CloudInterfInfos.RequestUnit;
import com.autonavi.minimap.onekeycheck.module.CloudInterfResData;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: dso reason: default package */
/* compiled from: ReqInterfsDataAction */
public final class dso extends BaseAction {
    public CloudInterfInfos d;
    List<dss> e;

    /* renamed from: dso$a */
    /* compiled from: ReqInterfsDataAction */
    public static class a implements bpl<StringResponse> {
        /* access modifiers changed from: private */
        public dso a;

        public final /* synthetic */ void onSuccess(bpk bpk) {
            final StringResponse stringResponse = (StringResponse) bpk;
            aho.a(new Runnable() {
                public final void run() {
                    if (stringResponse != null) {
                        int statusCode = stringResponse.getStatusCode();
                        a.a(a.this, stringResponse.getRequest(), agv.a(stringResponse.getResponseBodyData()), true, statusCode);
                    }
                }
            });
        }

        public final void onFailure(final bph bph, final ResponseException responseException) {
            aho.a(new Runnable() {
                public final void run() {
                    try {
                        a.a(a.this, bph, agv.a((responseException == null ? "接口返回数据失败" : responseException.getMessage()).getBytes("utf-8")), false, responseException == null ? -1 : responseException.errorCode);
                    } catch (UnsupportedEncodingException unused) {
                    }
                }
            });
        }

        static /* synthetic */ void a(a aVar, bph bph, String str, boolean z, int i) {
            String str2;
            RequestUnit requestUnit;
            CloudInterfResData cloudInterfResData;
            if (aVar.a != null) {
                if (bph != null) {
                    if (bph instanceof dsu) {
                        dsu dsu = (dsu) bph;
                        str2 = dsu.c;
                        requestUnit = dsu.b;
                    } else {
                        dst dst = (dst) bph;
                        str2 = dst.c;
                        requestUnit = dst.b;
                    }
                    if (requestUnit == null) {
                        requestUnit = new RequestUnit();
                        requestUnit.url = bph.getUrl();
                    }
                } else {
                    requestUnit = null;
                    str2 = null;
                }
                if (str != null) {
                    cloudInterfResData = new CloudInterfResData(str2, requestUnit, str, z);
                } else {
                    try {
                        cloudInterfResData = new CloudInterfResData(str2, requestUnit, agv.a("接口返回数据为空".getBytes("utf-8")), false);
                    } catch (UnsupportedEncodingException unused) {
                        cloudInterfResData = null;
                    }
                }
                if (cloudInterfResData != null) {
                    cloudInterfResData.setStatusCode(i);
                    dso dso = aVar.a;
                    boolean z2 = true;
                    if (dsx.a(dso.e) || dso.e.size() != 1) {
                        z2 = false;
                    }
                    cloudInterfResData.setIsLastResponse(z2);
                }
                aVar.a.callbackOnResponse(cloudInterfResData);
                dso dso2 = aVar.a;
                if (bph != null && !dsx.a(dso2.e) && dso2.e.contains(bph)) {
                    dso2.e.remove(bph);
                }
                if (dso2.e.size() == 0) {
                    dso2.finish();
                    dso2.callbackOnResponse(null);
                }
            }
        }
    }

    public dso(ActionListener actionListener) {
        super(actionListener);
    }

    public final void start() {
        super.start();
        getState().update(3);
        boolean z = false;
        if (this.d != null) {
            if (this.e == null) {
                this.e = new ArrayList();
            }
            if (!dsx.a(this.d.cdn_urls)) {
                for (String dst : this.d.cdn_urls) {
                    dst dst2 = new dst(dst, (String) "cdn");
                    dst2.a.a = this;
                    this.e.add(dst2);
                    z = true;
                }
            }
            if (!dsx.a(this.d.service_urls)) {
                for (RequestUnit next : this.d.service_urls) {
                    if ("post".equalsIgnoreCase(next.method)) {
                        dsu dsu = new dsu(next, "urls");
                        dsu.a.a = this;
                        this.e.add(dsu);
                    } else {
                        dst dst3 = new dst(next, (String) "urls");
                        dst3.a.a = this;
                        this.e.add(dst3);
                    }
                    z = true;
                }
            }
        }
        if (z) {
            a();
            return;
        }
        CloudInterfResData cloudInterfResData = new CloudInterfResData();
        cloudInterfResData.setIsLastResponse(true);
        callbackOnResponse(cloudInterfResData);
    }

    private void a() {
        if (!dsx.a(this.e)) {
            for (dss next : this.e) {
                yq.a();
                yq.a((bph) next, next.a());
            }
        }
    }

    public final void stop() {
        if (!dsx.a(this.e)) {
            Iterator<dss> it = this.e.iterator();
            while (it.hasNext()) {
                yq.a();
                yq.a((bph) it.next());
            }
        }
        super.stop();
    }

    public final void finish() {
        super.finish();
    }
}
