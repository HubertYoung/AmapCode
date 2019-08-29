package com.autonavi.minimap.bundle.activities.ajx;

import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("activities")
public class ModuleActivities extends AbstractModule {
    public ModuleActivities(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("requestAndOpenActivity")
    public void requestAndOpenActivity(final String str, final JsFunctionCallback jsFunctionCallback) {
        final ctl ctl = (ctl) a.a.a(ctl.class);
        if (ctl != null) {
            ctl.a(str, new Callback<ctm>() {
                public void error(Throwable th, boolean z) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(Integer.valueOf(0), "");
                    }
                }

                public void callback(ctm ctm) {
                    if (ctm != null && ctm.a == 1) {
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext instanceof AbstractBasePage) {
                            String str = ctm.c;
                            if (!TextUtils.isEmpty(str)) {
                                try {
                                    ctl.a((AbstractBasePage) pageContext, str, str);
                                    if (jsFunctionCallback != null) {
                                        jsFunctionCallback.callback(Integer.valueOf(ctm.a), ctm.d);
                                    }
                                } catch (Exception unused) {
                                    if (jsFunctionCallback != null) {
                                        jsFunctionCallback.callback(Integer.valueOf(0), "");
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @AjxMethod("cancelActivityRequest")
    public void cancelActivityRequest(String str) {
        ctl ctl = (ctl) a.a.a(ctl.class);
        if (ctl != null && (AMapPageUtil.getPageContext() instanceof AbstractBasePage)) {
            ctl.a(str);
        }
    }
}
