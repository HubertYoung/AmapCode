package com.alipay.zoloz.toyger.upload;

import com.alibaba.fastjson.JSON;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisBehavLog;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioUploadItem;
import com.alipay.mobile.security.bio.service.BioUploadService;
import com.alipay.mobile.security.bio.utils.BioLog;

public class UploadChannelByJson extends UploadChannel {
    private BioUploadService mBioUploadService;

    public UploadChannelByJson(BioServiceManager bioServiceManager) {
        if (bioServiceManager == null) {
            throw new BioIllegalArgumentException("BioServiceManager is null...");
        }
        this.mBioUploadService = (BioUploadService) bioServiceManager.getBioService(BioUploadService.class);
    }

    public void uploadFaceInfo(UploadContent uploadContent, BisBehavLog bisBehavLog, String str, String str2) {
        if (uploadContent != null && bisBehavLog != null) {
            BioUploadItem bioUploadItem = new BioUploadItem();
            bioUploadItem.publicKey = str2;
            bioUploadItem.content = uploadContent.content;
            bioUploadItem.log = JSON.toJSONString(bisBehavLog).getBytes();
            bioUploadItem.bisToken = str;
            bioUploadItem.contentSig = uploadContent.contentSig;
            bioUploadItem.isNeedSendResponse = true;
            this.mBioUploadService.upload(bioUploadItem);
        }
    }

    public void uploadNineShoot(UploadContent uploadContent, BisBehavLog bisBehavLog, String str, String str2) {
        if (uploadContent.content == null) {
            BioLog.w((Throwable) new IllegalArgumentException("content is empty"));
            return;
        }
        BioUploadItem bioUploadItem = new BioUploadItem();
        bioUploadItem.publicKey = str2;
        bioUploadItem.content = uploadContent.content;
        bioUploadItem.log = JSON.toJSONString(bisBehavLog).getBytes();
        bioUploadItem.bisToken = str;
        bioUploadItem.isNeedSendResponse = false;
        bioUploadItem.contentSig = uploadContent.contentSig;
        this.mBioUploadService.upload(bioUploadItem);
    }

    public void uploadBehaviourLog(BisBehavLog bisBehavLog, String str, String str2) {
        if (bisBehavLog != null) {
            BioUploadItem bioUploadItem = new BioUploadItem();
            bioUploadItem.publicKey = str2;
            bioUploadItem.log = JSON.toJSONString(bisBehavLog).getBytes();
            bioUploadItem.bisToken = str;
            bioUploadItem.isNeedSendResponse = false;
            this.mBioUploadService.upload(bioUploadItem);
        }
    }
}
