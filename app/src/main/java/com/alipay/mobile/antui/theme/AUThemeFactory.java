package com.alipay.mobile.antui.theme;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;
import com.alipay.mobile.antui.BuildConfig;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.ArrayList;
import java.util.List;

public class AUThemeFactory implements Factory {
    private List<AntUI> mItems = new ArrayList();

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if (!name.contains(BuildConfig.APPLICATION_ID)) {
            return null;
        }
        View view = createView(context, name, attrs);
        if (view == null) {
            return null;
        }
        if (!(view instanceof AntUI)) {
            return view;
        }
        this.mItems.add((AntUI) view);
        return view;
    }

    private View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
            if (-1 == name.indexOf(46)) {
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            } else {
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }
            AuiLogger.info("AUThemeFactory", "about to create " + name);
            return view;
        } catch (Exception e) {
            AuiLogger.error("AUThemeFactory", "error while create 【" + name + "】 : " + e.getMessage());
            return null;
        }
    }

    public void applySkin(Context context, AUAbsTheme theme) {
        for (AntUI antUI : this.mItems) {
            if (antUI != null) {
                antUI.upDateTheme(context, theme);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0019  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clean() {
        /*
            r2 = this;
            java.util.List<com.alipay.mobile.antui.api.AntUI> r0 = r2.mItems
            if (r0 == 0) goto L_0x000c
            java.util.List<com.alipay.mobile.antui.api.AntUI> r0 = r2.mItems
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x000d
        L_0x000c:
            return
        L_0x000d:
            java.util.List<com.alipay.mobile.antui.api.AntUI> r0 = r2.mItems
            java.util.Iterator r1 = r0.iterator()
        L_0x0013:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x000c
            java.lang.Object r0 = r1.next()
            com.alipay.mobile.antui.api.AntUI r0 = (com.alipay.mobile.antui.api.AntUI) r0
            if (r0 == 0) goto L_0x0013
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.antui.theme.AUThemeFactory.clean():void");
    }
}
