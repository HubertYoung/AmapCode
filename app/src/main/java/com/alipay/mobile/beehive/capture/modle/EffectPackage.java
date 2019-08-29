package com.alipay.mobile.beehive.capture.modle;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.APPackageInfo;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class EffectPackage implements Serializable {
    public static final String LATEST_USED_PACKAGE_ID = "LATEST_USED_PACKAGE_ID";
    public String desc;
    public List<Effect> effects;
    public boolean isSelected;
    public String packageIcon;
    public String packageId;
    public String selectedIcon;

    public EffectPackage(APPackageInfo info) {
        this.packageId = info.packageId;
        this.packageIcon = info.iconId;
        this.selectedIcon = info.selectedIconId;
        this.effects = parseEffects(info);
        this.desc = info.shortCut;
    }

    public EffectPackage() {
        this.packageId = LATEST_USED_PACKAGE_ID;
    }

    public boolean isLatestUsedPackage() {
        return TextUtils.equals(this.packageId, LATEST_USED_PACKAGE_ID);
    }

    private List<Effect> parseEffects(APPackageInfo pInfo) {
        if (pInfo.mMaterialInfos == null || pInfo.mMaterialInfos.isEmpty()) {
            return null;
        }
        List effects2 = new LinkedList();
        effects2.add(new Effect());
        for (APMaterialInfo mi : pInfo.mMaterialInfos) {
            effects2.add(new Effect(mi));
        }
        return effects2;
    }
}
