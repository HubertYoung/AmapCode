package com.amap.bundle.webview.uc;

import android.webkit.ValueCallback;

public class UCInitializer$13 implements ValueCallback<Object[]> {
    public final void onReceiveValue(Object[] objArr) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(":");
            sb.append(objArr[1]);
            sb.append(":");
            sb.append(objArr[2]);
            sb.append(":");
            sb.append(objArr[5]);
            String sb2 = sb.toString();
            if (objArr[6] != null) {
                if ("v".equals(objArr[3])) {
                    new StringBuilder("UCCore.init:").append(objArr[4]);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append(objArr[6]);
                } else if ("d".equals(objArr[3])) {
                    new StringBuilder("UCCore.init:").append(objArr[4]);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(sb2);
                    sb4.append(objArr[6]);
                } else if ("i".equals(objArr[3])) {
                    new StringBuilder("UCCore.init:").append(objArr[4]);
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(sb2);
                    sb5.append(objArr[6]);
                } else if ("w".equals(objArr[3])) {
                    new StringBuilder("UCCore.init:").append(objArr[4]);
                } else if ("e".equals(objArr[3])) {
                    new StringBuilder("UCCore.init:").append(objArr[4]);
                }
            } else if ("v".equals(objArr[3])) {
                new StringBuilder("UCCore.init:").append(objArr[4]);
            } else if ("d".equals(objArr[3])) {
                new StringBuilder("UCCore.init:").append(objArr[4]);
            } else if ("i".equals(objArr[3])) {
                new StringBuilder("UCCore.init:").append(objArr[4]);
            } else if ("w".equals(objArr[3])) {
                new StringBuilder("UCCore.init:").append(objArr[4]);
            } else if ("e".equals(objArr[3])) {
                new StringBuilder("UCCore.init:").append(objArr[4]);
            }
        } catch (Throwable unused) {
        }
    }
}
