package com.autonavi.minimap.bundle.maphome.impl;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.geo.param.ReverseCodeRequest;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class ReverseGeocodeManagerImpl$1 extends FalconAosPrepareResponseCallback<ReverseGeocodeResponser> {
    final /* synthetic */ Callback a;
    final /* synthetic */ ReverseCodeRequest b;
    final /* synthetic */ cyy c;

    public ReverseGeocodeManagerImpl$1(cyy cyy, Callback callback, ReverseCodeRequest reverseCodeRequest) {
        this.c = cyy;
        this.a = callback;
        this.b = reverseCodeRequest;
    }

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        ReverseGeocodeResponser reverseGeocodeResponser = (ReverseGeocodeResponser) obj;
        if (this.a != null) {
            if (reverseGeocodeResponser == null || !reverseGeocodeResponser.result) {
                a(new Throwable("result is false!"), true);
            } else {
                this.a.callback(reverseGeocodeResponser);
            }
        }
    }

    private static ReverseGeocodeResponser b(AosByteResponse aosByteResponse) {
        ReverseGeocodeResponser reverseGeocodeResponser = new ReverseGeocodeResponser();
        try {
            reverseGeocodeResponser.parser((byte[]) aosByteResponse.getResult());
        } catch (UnsupportedEncodingException e) {
            kf.a((Throwable) e);
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        return reverseGeocodeResponser;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        a((Throwable) aosResponseException, false);
    }

    private void a(final Throwable th, final boolean z) {
        float f;
        adz adz = (adz) a.a.a(adz.class);
        if (adz != null) {
            ady b2 = adz.b();
            if (b2 != null) {
                float f2 = 0.0f;
                try {
                    f = Float.parseFloat(this.b.b);
                    try {
                        f2 = Float.parseFloat(this.b.c);
                    } catch (Throwable unused) {
                    }
                } catch (Throwable unused2) {
                    f = 0.0f;
                }
                b2.a(f, f2, (OnSearchResultListener) new OnSearchResultListener() {
                    public final void onGetSearchResult(int i, final GPoiResult gPoiResult) {
                        aho.a(new Runnable() {
                            public final void run() {
                                if (gPoiResult == null || gPoiResult.getPoiList() == null || gPoiResult.getPoiList().size() <= 0) {
                                    if (ReverseGeocodeManagerImpl$1.this.a != null) {
                                        ReverseGeocodeManagerImpl$1.this.a.error(th, z);
                                    }
                                } else if (ReverseGeocodeManagerImpl$1.this.a != null) {
                                    cyy cyy = ReverseGeocodeManagerImpl$1.this.c;
                                    GPoiResult gPoiResult = gPoiResult;
                                    ReverseGeocodeResponser reverseGeocodeResponser = new ReverseGeocodeResponser();
                                    if (!(gPoiResult == null || gPoiResult.getPoiList() == null || gPoiResult.getPoiList().size() <= 0)) {
                                        reverseGeocodeResponser.setPoiList(cyy.a(gPoiResult));
                                        reverseGeocodeResponser.errorCode = 1;
                                    }
                                    int i = 0;
                                    while (true) {
                                        if (i >= reverseGeocodeResponser.getPoiList().size()) {
                                            break;
                                        }
                                        String addr = reverseGeocodeResponser.getPoiList().get(i).getAddr();
                                        if (addr != null && addr.trim().length() > 0) {
                                            reverseGeocodeResponser.setDesc(addr);
                                            break;
                                        }
                                        i++;
                                    }
                                    ReverseGeocodeManagerImpl$1.this.a.callback(reverseGeocodeResponser);
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
