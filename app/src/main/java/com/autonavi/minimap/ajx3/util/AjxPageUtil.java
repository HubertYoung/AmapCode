package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import java.util.ArrayList;
import java.util.Iterator;

public class AjxPageUtil {
    public static final String LAUNCH_MODE_SINGLE_INSTANCE = "singleInstance";
    public static final String LAUNCH_MODE_SINGLE_TASK = "singleTask";
    public static final String LAUNCH_MODE_SINGLE_TOP = "singleTop";
    public static final String LAUNCH_MODE_STANDARD = "standard";

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getLaunchMode(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = -1
            if (r4 == 0) goto L_0x0071
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            if (r1 == 0) goto L_0x000b
            goto L_0x0071
        L_0x000b:
            com.autonavi.minimap.ajx3.loading.LoadingConfig r4 = com.autonavi.minimap.ajx3.Ajx3Path.getLoadingConfig(r4, r5)
            if (r4 == 0) goto L_0x0014
            java.lang.String r4 = r4.launchMode
            goto L_0x0015
        L_0x0014:
            r4 = 0
        L_0x0015:
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 == 0) goto L_0x001c
            return r0
        L_0x001c:
            int r5 = r4.hashCode()
            r1 = -1494548499(0xffffffffa6eaffed, float:-1.6306381E-15)
            r2 = 0
            r3 = 2
            if (r5 == r1) goto L_0x0058
            r1 = 866432253(0x33a4b4fd, float:7.669767E-8)
            if (r5 == r1) goto L_0x004d
            r1 = 913623533(0x3674c9ed, float:3.647637E-6)
            if (r5 == r1) goto L_0x0042
            r1 = 1312628413(0x4e3d1ebd, float:7.9322707E8)
            if (r5 == r1) goto L_0x0037
            goto L_0x0063
        L_0x0037:
            java.lang.String r5 = "standard"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0063
            r4 = 3
            goto L_0x0064
        L_0x0042:
            java.lang.String r5 = "singleTask"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0063
            r4 = 1
            goto L_0x0064
        L_0x004d:
            java.lang.String r5 = "singleInstance"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0063
            r4 = 2
            goto L_0x0064
        L_0x0058:
            java.lang.String r5 = "singleTop"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0063
            r4 = 0
            goto L_0x0064
        L_0x0063:
            r4 = -1
        L_0x0064:
            switch(r4) {
                case 0: goto L_0x006f;
                case 1: goto L_0x006d;
                case 2: goto L_0x006a;
                case 3: goto L_0x0068;
                default: goto L_0x0067;
            }
        L_0x0067:
            goto L_0x0070
        L_0x0068:
            r0 = 0
            goto L_0x0070
        L_0x006a:
            r0 = 16
            goto L_0x0070
        L_0x006d:
            r0 = 4
            goto L_0x0070
        L_0x006f:
            r0 = 2
        L_0x0070:
            return r0
        L_0x0071:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.AjxPageUtil.getLaunchMode(android.content.Context, java.lang.String):int");
    }

    public static PageBundle makePageBundle(@NonNull Context context, @NonNull String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", str);
        return pageBundle;
    }

    public static String getLaunchModeString(String str) {
        if ("standard".equals(str) || LAUNCH_MODE_SINGLE_TOP.equals(str) || LAUNCH_MODE_SINGLE_TASK.equals(str) || LAUNCH_MODE_SINGLE_INSTANCE.equals(str)) {
            return str;
        }
        r2 = "undefine";
        return "undefine";
    }

    public static String dumpPageStack() {
        StringBuilder sb = new StringBuilder();
        bid pageContext = AMapPageUtil.getPageContext();
        if (!(pageContext instanceof bup)) {
            return sb.toString();
        }
        bul mvpActivityContext = ((bup) pageContext).getMvpActivityContext();
        if (mvpActivityContext == null) {
            return sb.toString();
        }
        ArrayList<akc> d = mvpActivityContext.d();
        if (d == null) {
            return sb.toString();
        }
        ArrayList arrayList = new ArrayList(d);
        int i = 0;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            akc akc = (akc) it.next();
            if (akc instanceof bun) {
                AbstractBasePage abstractBasePage = ((bun) akc).a;
                if (abstractBasePage != null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(abstractBasePage.getClass());
                    sb2.append("[[");
                    sb2.append(abstractBasePage.hashCode());
                    sb2.append("]]");
                    String sb3 = sb2.toString();
                    sb.append("====================\n");
                    StringBuilder sb4 = new StringBuilder("栈中位置： ");
                    int i2 = i + 1;
                    sb4.append(i);
                    sb4.append("\n");
                    sb.append(sb4.toString());
                    StringBuilder sb5 = new StringBuilder("当前页面：");
                    sb5.append(sb3);
                    sb5.append("\n");
                    sb.append(sb5.toString());
                    if (abstractBasePage instanceof Ajx3Page) {
                        Ajx3Page ajx3Page = (Ajx3Page) abstractBasePage;
                        StringBuilder sb6 = new StringBuilder("页面路径：");
                        sb6.append(ajx3Page.getAjx3Url());
                        sb6.append("\n");
                        sb.append(sb6.toString());
                        StringBuilder sb7 = new StringBuilder("加载模式：");
                        sb7.append(getLaunchModeString(ajx3Page.getLaunchMode()));
                        sb7.append("\n");
                        sb.append(sb7.toString());
                    }
                    sb.append("\n\n\n");
                    i = i2;
                }
            }
        }
        return sb.toString();
    }
}
