package com.alipay.mobile.security.bio.service.impl;

import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alipay.bis.common.service.facade.gw.model.upload.BisJsonUploadGwRequest;
import com.alipay.bis.common.service.facade.gw.model.upload.BisJsonUploadGwResult;
import com.alipay.bis.common.service.facade.gw.upload.BisJsonUploadGwFacade;
import com.alipay.bis.common.service.facade.gw.zim.ZimDispatchJsonGwFacade;
import com.alipay.bis.common.service.facade.gw.zim.ZimValidateGwResponse;
import com.alipay.bis.common.service.facade.gw.zim.ZimValidateJsonGwRequest;
import com.alipay.mobile.security.bio.constants.CodeConstants;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioStoreParameter;
import com.alipay.mobile.security.bio.service.BioStoreResult;
import com.alipay.mobile.security.bio.service.BioUploadItem;
import com.alipay.mobile.security.bio.service.BioUploadResult;
import com.alipay.mobile.security.bio.service.local.rpc.IRpcException;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.StringUtil;

public class BioUploadJsonGWImpl extends BioUploadGW {
    public BioUploadJsonGWImpl(BioServiceManager bioServiceManager) {
        super(bioServiceManager);
    }

    public BioUploadResult upload(String str, BioUploadItem bioUploadItem) {
        if (StringUtil.isNullorEmpty(bioUploadItem.publicKey)) {
            return unEncrytUploadInfo(str, bioUploadItem);
        }
        return encryptUploadInfo(str, bioUploadItem);
    }

    public BioUploadResult encryptUploadInfo(String str, BioUploadItem bioUploadItem) {
        byte[] random = this.c.getRandom();
        BioStoreParameter bioStoreParameter = new BioStoreParameter();
        bioStoreParameter.content = bioUploadItem.log;
        bioStoreParameter.publicKey = bioUploadItem.publicKey;
        bioStoreParameter.random = random;
        BioStoreResult encryptWithRandom = this.c.encryptWithRandom(bioStoreParameter);
        BisJsonUploadGwRequest bisJsonUploadGwRequest = new BisJsonUploadGwRequest();
        bisJsonUploadGwRequest.bisToken = bioUploadItem.bisToken;
        if (encryptWithRandom != null) {
            bisJsonUploadGwRequest.behavLog = Base64.encodeToString(encryptWithRandom.encodeContent, 10);
            bisJsonUploadGwRequest.behavLogSig = Base64.encodeToString(encryptWithRandom.encodeSeed, 10);
        }
        if (bioUploadItem.content == null) {
            return a(str, bisJsonUploadGwRequest);
        }
        bisJsonUploadGwRequest.content = new String(bioUploadItem.content);
        bisJsonUploadGwRequest.contentSig = Base64.encodeToString(bioUploadItem.contentSig, 10);
        return a(str, bisJsonUploadGwRequest);
    }

    public BioUploadResult unEncrytUploadInfo(String str, BioUploadItem bioUploadItem) {
        BisJsonUploadGwRequest bisJsonUploadGwRequest = new BisJsonUploadGwRequest();
        bisJsonUploadGwRequest.bisToken = bioUploadItem.bisToken;
        bisJsonUploadGwRequest.behavLogSig = "";
        bisJsonUploadGwRequest.contentSig = "";
        bisJsonUploadGwRequest.content = new String(bioUploadItem.content);
        bisJsonUploadGwRequest.behavLog = Base64.encodeToString(bioUploadItem.log, 10);
        return a(str, bisJsonUploadGwRequest);
    }

    private BioUploadResult a(String str, BisJsonUploadGwRequest bisJsonUploadGwRequest) {
        ZimValidateGwResponse validateStandard;
        if (TextUtils.isEmpty(str) || str.contains("_bis")) {
            BioUploadResult bioUploadResult = new BioUploadResult();
            try {
                BisJsonUploadGwResult upload = ((BisJsonUploadGwFacade) this.b.getRpcProxy(BisJsonUploadGwFacade.class)).upload(bisJsonUploadGwRequest);
                if (upload != null) {
                    bioUploadResult.productRetCode = Integer.parseInt(upload.retCode);
                    return bioUploadResult;
                }
                bioUploadResult.productRetCode = 3002;
                return bioUploadResult;
            } catch (Exception e) {
                BioLog.w((Throwable) e);
                bioUploadResult.productRetCode = 3001;
                return bioUploadResult;
            }
        } else {
            try {
                ZimDispatchJsonGwFacade zimDispatchJsonGwFacade = (ZimDispatchJsonGwFacade) this.b.getRpcProxy(ZimDispatchJsonGwFacade.class);
                ZimValidateJsonGwRequest zimValidateJsonGwRequest = new ZimValidateJsonGwRequest();
                zimValidateJsonGwRequest.zimId = str;
                zimValidateJsonGwRequest.zimData = JSON.toJSONString(bisJsonUploadGwRequest);
                BioLog.e("upload(): request= " + zimValidateJsonGwRequest);
                if (str.contains("_bis")) {
                    validateStandard = zimDispatchJsonGwFacade.validate(zimValidateJsonGwRequest);
                } else {
                    validateStandard = zimDispatchJsonGwFacade.validateStandard(zimValidateJsonGwRequest);
                }
                BioLog.e("upload(): response= " + validateStandard);
                if (validateStandard != null) {
                    return new BioUploadResult(validateStandard);
                }
                BioUploadResult bioUploadResult2 = new BioUploadResult();
                bioUploadResult2.validationRetCode = 1001;
                bioUploadResult2.productRetCode = 3002;
                bioUploadResult2.subCode = CodeConstants.SERVER_PARAM_ERROR;
                bioUploadResult2.subMsg = CodeConstants.getMessage(CodeConstants.SERVER_PARAM_ERROR);
                return bioUploadResult2;
            } catch (Exception e2) {
                BioLog.w((Throwable) e2);
                BioUploadResult bioUploadResult3 = new BioUploadResult();
                if (e2 instanceof IRpcException) {
                    int code = ((IRpcException) e2).getCode();
                    if (code == 4001 || code == 5 || code == 16 || code == 2) {
                        bioUploadResult3.validationRetCode = 3001;
                        bioUploadResult3.productRetCode = 3002;
                        bioUploadResult3.subCode = CodeConstants.NETWORK_ERROR;
                        bioUploadResult3.subMsg = CodeConstants.getMessage(CodeConstants.NETWORK_ERROR);
                        return bioUploadResult3;
                    }
                    bioUploadResult3.validationRetCode = 1001;
                    bioUploadResult3.productRetCode = 3002;
                    bioUploadResult3.subCode = CodeConstants.SERVER_PARAM_ERROR;
                    bioUploadResult3.subMsg = CodeConstants.getMessage(CodeConstants.SERVER_PARAM_ERROR);
                    return bioUploadResult3;
                }
                bioUploadResult3.validationRetCode = 1001;
                bioUploadResult3.productRetCode = 3002;
                bioUploadResult3.subCode = CodeConstants.SERVER_PARAM_ERROR;
                bioUploadResult3.subMsg = CodeConstants.getMessage(CodeConstants.SERVER_PARAM_ERROR);
                return bioUploadResult3;
            }
        }
    }
}
