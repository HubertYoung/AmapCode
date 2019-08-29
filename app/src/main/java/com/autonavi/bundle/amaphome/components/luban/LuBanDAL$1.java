package com.autonavi.bundle.amaphome.components.luban;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;

public class LuBanDAL$1 extends FalconAosPrepareResponseCallback<aqt> {
    final /* synthetic */ a a;
    final /* synthetic */ aqr b;

    public LuBanDAL$1(aqr aqr, a aVar) {
        this.b = aqr;
        this.a = aVar;
    }

    public final /* synthetic */ void a(Object obj) {
        aqt aqt = (aqt) obj;
        if (aqt != null) {
            this.b.d = aqt;
            this.b.e = this.b.d.a;
            this.b.a.putStringValue("lu_ban_hot_word_text_cache", this.b.e);
            this.b.f = this.b.d.b;
            this.b.a.putStringValue("Lu_ban_hot_word_hint_cache", this.b.f);
            this.b.g = this.b.d.c;
            this.b.a.putStringValue("lu_ban_hot_word_text_color_cache", this.b.g);
            this.b.h = this.b.d.d;
            this.b.a.putStringValue("Lu_ban_hot_word_hint_color_cache", this.b.h);
            this.b.a.putIntValue("lu_ban_hot_word_time_cache", this.b.b.get(6));
            if (this.a != null) {
                this.a.a(this.b.e);
            }
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        AMapLog.e("LuBanDAL", aosResponseException.getLocalizedMessage());
    }

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        this.b.c.parser((byte[]) aosByteResponse.getResult());
        return this.b.c.a;
    }
}
