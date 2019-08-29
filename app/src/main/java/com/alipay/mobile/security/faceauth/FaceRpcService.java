package com.alipay.mobile.security.faceauth;

import com.alipay.mobile.security.bio.service.BioService;

public abstract class FaceRpcService extends BioService {
    public abstract void addGwRequest(FaceRpcGwRequest faceRpcGwRequest);

    public abstract void setRpcListener(FaceRpcListener faceRpcListener);
}
