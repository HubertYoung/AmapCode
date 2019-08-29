package com.autonavi.bundle.smart.scenic.ajx;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("search_smart_scenicarea_set")
@KeepPublicClassMembers
@KeepName
public class ModuleSmartSearchScenicSet extends AbstractModule {
    public static final String MODULE_NAME = "search_smart_scenicarea_set";
    private a mListener;

    public interface a {
        void a(JsFunctionCallback jsFunctionCallback);

        void a(String str);
    }

    public ModuleSmartSearchScenicSet(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setUiListener(@NonNull a aVar) {
        this.mListener = aVar;
    }

    @AjxMethod("tabHeightDidChange")
    public void tabHeightDidChange(String str) {
        if (this.mListener != null) {
            this.mListener.a(str);
        }
    }

    @AjxMethod("registerAjxTipsCallback")
    public void registerAjxTipsCallback(JsFunctionCallback jsFunctionCallback) {
        if (this.mListener != null) {
            this.mListener.a(jsFunctionCallback);
        }
    }
}
