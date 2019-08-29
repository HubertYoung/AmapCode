package com.alipay.mobile.security.faceauth;

import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisBehavLog;
import com.alipay.bis.common.service.facade.gw.model.face.BisFaceJson.BisFaceUploadContent;

public class FaceRpcGwRequest {
    public BisBehavLog bisBehavLog;
    public BisFaceUploadContent bisFaceUploadContent;
    public FaceRpcChannel faceRpcChannel = FaceRpcChannel.JSON;
    public InvokeType invokeType;
    public boolean isNineShoot = false;
    public String publicKey = "";
    public UserVerifyInfo userVerifyInfo;
}
