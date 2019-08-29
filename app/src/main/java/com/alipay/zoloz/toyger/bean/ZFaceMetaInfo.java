package com.alipay.zoloz.toyger.bean;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.config.BisSdkModuleEnum;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.service.BioMetaInfo;
import com.alipay.mobile.security.bio.service.BioServiceDescription;
import com.alipay.zoloz.toyger.ToygerMetaInfo;
import com.alipay.zoloz.toyger.extservice.ToygerIspService;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.workspace.ToygerActivity;

public class ZFaceMetaInfo extends BioMetaInfo {
    public ZFaceMetaInfo() {
        BioAppDescription bioAppDescription = new BioAppDescription();
        bioAppDescription.setBioType(100);
        bioAppDescription.setAppName(ToygerActivity.class.getName());
        bioAppDescription.setAppInterfaceName(ToygerActivity.class.getName());
        addApplication(bioAppDescription);
        BioServiceDescription bioServiceDescription = new BioServiceDescription();
        bioServiceDescription.setClazz(ToygerRecordService.class);
        bioServiceDescription.setInterfaceName(ToygerRecordService.class.getName());
        addExtService(bioServiceDescription);
        BioServiceDescription bioServiceDescription2 = new BioServiceDescription();
        bioServiceDescription2.setClazz(ToygerIspService.class);
        bioServiceDescription2.setInterfaceName(ToygerIspService.class.getName());
        addExtService(bioServiceDescription2);
        addProductID(Long.valueOf(BisSdkModuleEnum.SME_PANO.getProductID()));
        if (TextUtils.equals("Cherry", "Cherry")) {
            addProductID(Long.valueOf(BisSdkModuleEnum.SME_DARK.getProductID()));
        }
        if (ToygerMetaInfo.isGemini()) {
            addProductID(Long.valueOf(BisSdkModuleEnum.SME_GEMINI.getProductID()));
        }
        if (ToygerMetaInfo.isDragonfly()) {
            addProductID(Long.valueOf(BisSdkModuleEnum.SME_DRAGONFLY.getProductID()));
        }
        if (ToygerMetaInfo.isBat()) {
            addProductID(Long.valueOf(BisSdkModuleEnum.SME_3D.getProductID()));
        }
    }
}
