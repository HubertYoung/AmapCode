package com.uc.webview.export.internal;

import com.uc.webview.export.internal.interfaces.InvokeObject;
import com.uc.webview.export.internal.utility.Log;
import java.util.Arrays;

/* compiled from: ProGuard */
public class d implements InvokeObject {
    private static String a = "d";
    private static d b;
    private InvokeObject c;

    private d(InvokeObject invokeObject) {
        this.c = invokeObject;
        this.c.invoke(101, new Object[]{this});
    }

    public static void a(InvokeObject invokeObject) {
        if (b == null) {
            b = new d(invokeObject);
        }
    }

    public Object invoke(int i, Object[] objArr) {
        String str = a;
        StringBuilder sb = new StringBuilder("invoke.case.id: ");
        sb.append(i);
        sb.append(" params: ");
        sb.append(Arrays.toString(objArr));
        Log.d(str, sb.toString());
        switch (i) {
            case 201:
                if (objArr != null && objArr.length == 1) {
                    Log.d(a, "onCoreClearRecord value: ".concat(String.valueOf(objArr[0].intValue())));
                }
                return null;
            case 202:
                if (objArr != null && objArr.length == 1) {
                    Log.d(a, "onProxySettingChanged value: ".concat(String.valueOf(objArr[0].booleanValue())));
                }
                return null;
            default:
                return null;
        }
    }
}
