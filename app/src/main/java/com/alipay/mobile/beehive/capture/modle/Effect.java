package com.alipay.mobile.beehive.capture.modle;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;
import java.io.Serializable;

public class Effect implements Serializable {
    private static final String SPILT = ",";
    public String desc;
    public String effectIcon;
    public String effectId = "";
    public boolean isSelected;
    public ResLocalState localState = ResLocalState.NOT_EXIST;

    public enum ResLocalState {
        EXIST,
        DOWNLOADING,
        NOT_EXIST
    }

    public Effect(APMaterialInfo materialInfo) {
        this.effectId = materialInfo.materialId;
        this.effectIcon = materialInfo.iconId;
        this.desc = materialInfo.shortCut;
    }

    public boolean isNonEffect() {
        return TextUtils.isEmpty(this.effectId);
    }

    public Effect() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.effectId);
        sb.append(",").append(this.effectIcon);
        return sb.toString();
    }

    public Effect(String str) {
        String[] attr = str.split(",");
        this.effectId = attr[0];
        this.effectIcon = attr[1];
    }

    public boolean equals(Object o) {
        return TextUtils.equals(this.effectId, ((Effect) o).effectId);
    }
}
