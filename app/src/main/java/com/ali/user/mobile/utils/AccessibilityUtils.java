package com.ali.user.mobile.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityUtils {
    public static boolean a(Context context) {
        return ((AccessibilityManager) context.getSystemService("accessibility")).isEnabled();
    }

    @TargetApi(14)
    public static String b(Context context) {
        StringBuilder sb;
        StringBuilder sb2 = new StringBuilder(200);
        try {
            AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
            List<AccessibilityServiceInfo> installedAccessibilityServiceList = accessibilityManager.getInstalledAccessibilityServiceList();
            boolean isEnabled = accessibilityManager.isEnabled();
            sb2.append("{isAccessibilityEnabled:");
            sb2.append(isEnabled ? 1 : 0);
            boolean isTouchExplorationEnabled = accessibilityManager.isTouchExplorationEnabled();
            sb2.append(",isInExplorationMode:");
            sb2.append(isTouchExplorationEnabled ? 1 : 0);
            sb2.append(",");
            ArrayList arrayList = new ArrayList();
            for (AccessibilityServiceInfo id : accessibilityManager.getEnabledAccessibilityServiceList(-1)) {
                String id2 = id.getId();
                if (!arrayList.contains(id2)) {
                    arrayList.add(id2);
                }
            }
            for (AccessibilityServiceInfo next : installedAccessibilityServiceList) {
                boolean contains = arrayList.contains(next.getId());
                sb2.append("{ isEnabled:");
                sb2.append(contains ? 1 : 0);
                sb2.append(",");
                sb2.append(next.toString());
                sb2.append("},");
            }
            sb = sb2.deleteCharAt(sb2.length() - 1);
            try {
                sb.append(h.d);
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            sb = sb2;
        }
        return sb.toString();
    }
}
