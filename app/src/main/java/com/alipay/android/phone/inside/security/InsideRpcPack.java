package com.alipay.android.phone.inside.security;

import com.alipay.android.phone.inside.security.net.ClientPackEnum;
import com.alipay.android.phone.inside.security.net.ClientPackProxy;

public class InsideRpcPack {
    private ClientPackProxy a = new ClientPackProxy(ClientPackEnum.RPC);

    public final byte[] a(byte[] bArr) throws Exception {
        return this.a.a(bArr);
    }

    public final byte[] b(byte[] bArr) throws Exception {
        return this.a.b(bArr);
    }
}
