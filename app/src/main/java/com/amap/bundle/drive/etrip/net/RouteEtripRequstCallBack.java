package com.amap.bundle.drive.etrip.net;

import com.autonavi.common.Callback.PrepareCallback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public abstract class RouteEtripRequstCallBack implements PrepareCallback<byte[], nu> {
    private nu etripResponser;

    public RouteEtripRequstCallBack(nu nuVar) {
        this.etripResponser = nuVar;
    }

    public nu prepare(byte[] bArr) {
        this.etripResponser.parser(bArr);
        return this.etripResponser;
    }
}
