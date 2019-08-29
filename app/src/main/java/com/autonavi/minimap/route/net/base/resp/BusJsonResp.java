package com.autonavi.minimap.route.net.base.resp;

public class BusJsonResp extends BaseBeanResponse {
    private Object a;

    public final <T> T a(Class<T> cls) {
        if (this.a != null && this.a.getClass() == cls) {
            return this.a;
        }
        T a2 = super.a(cls);
        this.a = a2;
        return a2;
    }
}
