package com.amap.bundle.impressionreporter.ajx;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("impressionReporter")
@KeepPublicClassMembers
@KeepName
public class ModuleImpressionReporter extends AbstractModule {
    public ModuleImpressionReporter(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("impression")
    public void impression(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                int parseInt = Integer.parseInt(str2);
                vw vwVar = (vw) a.a.a(vw.class);
                if (vwVar != null) {
                    vwVar.a(parseInt, str);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
