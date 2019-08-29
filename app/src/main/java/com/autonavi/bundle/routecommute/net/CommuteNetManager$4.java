package com.autonavi.bundle.routecommute.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class CommuteNetManager$4 extends FalconAosPrepareResponseCallback<bal> {
    final /* synthetic */ a a;
    final /* synthetic */ int b;
    final /* synthetic */ bau c;

    public CommuteNetManager$4(bau bau, a aVar, int i) {
        this.c = bau;
        this.a = aVar;
        this.b = i;
    }

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* bridge */ /* synthetic */ void a(Object obj) {
        bal bal = (bal) obj;
        if (this.a != null) {
            if (bal == null || bal.a == null) {
                this.a.a();
            } else {
                this.a.a(bal.a, this.b);
            }
        }
    }

    private static bal b(AosByteResponse aosByteResponse) {
        bal bal = new bal();
        try {
            bal.parser((byte[]) aosByteResponse.getResult());
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return bal;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.a();
        }
    }
}
