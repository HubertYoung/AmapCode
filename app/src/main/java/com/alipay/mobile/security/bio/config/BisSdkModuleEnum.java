package com.alipay.mobile.security.bio.config;

import com.alipay.zoloz.toyger.face.ToygerFaceAlgorithmConfig;

public enum BisSdkModuleEnum {
    SME_FACE_CHERRY("faceCherry", 0),
    SME_FACE_DUCKEGG("faceDuckegg", 1),
    SME_FACE_VIDEO("faceEyeCherry", 2),
    SME_FACE_EYE_CHERRY("faceEyeCherry", 3),
    SME_FACE_EYE_DUCKEGG("faceEyeDuckegg", 4),
    SME_HAND_WRITING("handWriting", 5),
    SME_ID_CARD("idCard", 6),
    SME_FACE_PLUS_PLUS("FacePlusPlus", 7),
    SME_EYE_VERIFY("EyeVerify", 8),
    SME_VOICE_PRINT("VoicePrint", 9),
    SME_IRIS_VERIFY("IrisVerify", 10),
    SME_IDCARD_AND_FACE("IDCardandFace", 11),
    SME_GEMINI("gemini", 12),
    SME_DRAGONFLY("dragonfly", 13),
    SME_3D("bat", 14),
    SME_PANO(ToygerFaceAlgorithmConfig.PANO, 15),
    SME_DARK("dark", 16),
    SME_ZFACE("zface", 17),
    SME_UDOC("udoc", 18),
    SME_ZFACE_BLINK("zfaceblink", 19),
    SME_GARFI("garfi", 20);
    
    private String mProduct;
    private long mProductID;

    private BisSdkModuleEnum(String str, long j) {
        this.mProduct = str;
        this.mProductID = j;
    }

    public final String getProduct() {
        return this.mProduct;
    }

    public final void setProduct(String str) {
        this.mProduct = str;
    }

    public final long getProductID() {
        return this.mProductID;
    }

    public final void setProductID(int i) {
        this.mProductID = (long) i;
    }
}
