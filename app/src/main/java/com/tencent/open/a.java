package com.tencent.open;

import android.net.Uri;
import android.webkit.WebView;
import com.tencent.open.a.f;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: ProGuard */
public class a {
    protected HashMap<String, b> a = new HashMap<>();

    /* renamed from: com.tencent.open.a$a reason: collision with other inner class name */
    /* compiled from: ProGuard */
    public static class C0065a {
        protected WeakReference<WebView> a;
        protected long b;
        protected String c;

        public C0065a(WebView webView, long j, String str) {
            this.a = new WeakReference<>(webView);
            this.b = j;
            this.c = str;
        }

        public void a(Object obj) {
            WebView webView = (WebView) this.a.get();
            if (webView != null) {
                String str = "'undefined'";
                if (obj instanceof String) {
                    String replace = ((String) obj).replace("\\", "\\\\").replace("'", "\\'");
                    StringBuilder sb = new StringBuilder("'");
                    sb.append(replace);
                    sb.append("'");
                    str = sb.toString();
                } else if ((obj instanceof Number) || (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Double) || (obj instanceof Float)) {
                    str = obj.toString();
                } else if (obj instanceof Boolean) {
                    str = obj.toString();
                }
                StringBuilder sb2 = new StringBuilder("javascript:window.JsBridge&&JsBridge.callback(");
                sb2.append(this.b);
                sb2.append(",{'r':0,'result':");
                sb2.append(str);
                sb2.append("});");
                webView.loadUrl(sb2.toString());
            }
        }

        public void a() {
            WebView webView = (WebView) this.a.get();
            if (webView != null) {
                StringBuilder sb = new StringBuilder("javascript:window.JsBridge&&JsBridge.callback(");
                sb.append(this.b);
                sb.append(",{'r':1,'result':'no such method'})");
                webView.loadUrl(sb.toString());
            }
        }

        public void a(String str) {
            WebView webView = (WebView) this.a.get();
            if (webView != null) {
                webView.loadUrl("javascript:".concat(String.valueOf(str)));
            }
        }
    }

    /* compiled from: ProGuard */
    public static class b {
        public boolean customCallback() {
            return false;
        }

        public void call(String str, List<String> list, C0065a aVar) {
            String str2;
            Method method;
            Object obj;
            Method[] declaredMethods = getClass().getDeclaredMethods();
            int length = declaredMethods.length;
            int i = 0;
            while (true) {
                str2 = null;
                if (i >= length) {
                    method = null;
                    break;
                }
                method = declaredMethods[i];
                if (method.getName().equals(str) && method.getParameterTypes().length == list.size()) {
                    break;
                }
                i++;
            }
            if (method != null) {
                try {
                    switch (list.size()) {
                        case 0:
                            obj = method.invoke(this, new Object[0]);
                            break;
                        case 1:
                            obj = method.invoke(this, new Object[]{list.get(0)});
                            break;
                        case 2:
                            obj = method.invoke(this, new Object[]{list.get(0), list.get(1)});
                            break;
                        case 3:
                            obj = method.invoke(this, new Object[]{list.get(0), list.get(1), list.get(2)});
                            break;
                        case 4:
                            obj = method.invoke(this, new Object[]{list.get(0), list.get(1), list.get(2), list.get(3)});
                            break;
                        case 5:
                            obj = method.invoke(this, new Object[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)});
                            break;
                        default:
                            Object[] objArr = new Object[6];
                            objArr[0] = list.get(0);
                            objArr[1] = list.get(1);
                            objArr[2] = list.get(2);
                            objArr[3] = list.get(3);
                            objArr[4] = list.get(4);
                            objArr[5] = list.get(5);
                            obj = method.invoke(this, objArr);
                            break;
                    }
                    Class<?> returnType = method.getReturnType();
                    StringBuilder sb = new StringBuilder("-->call, result: ");
                    sb.append(obj);
                    sb.append(" | ReturnType: ");
                    sb.append(returnType.getName());
                    f.b("openSDK_LOG.JsBridge", sb.toString());
                    if (!"void".equals(returnType.getName())) {
                        if (returnType != Void.class) {
                            if (aVar != null && customCallback()) {
                                if (obj != null) {
                                    str2 = obj.toString();
                                }
                                aVar.a(str2);
                            }
                        }
                    }
                    if (aVar != null) {
                        aVar.a((Object) null);
                    }
                } catch (Exception e) {
                    f.b("openSDK_LOG.JsBridge", "-->handler call mehtod ex. targetMethod: ".concat(String.valueOf(method)), e);
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            } else {
                if (aVar != null) {
                    aVar.a();
                }
            }
        }
    }

    public void a(b bVar, String str) {
        this.a.put(str, bVar);
    }

    public void a(String str, String str2, List<String> list, C0065a aVar) {
        StringBuilder sb = new StringBuilder("getResult---objName = ");
        sb.append(str);
        sb.append(" methodName = ");
        sb.append(str2);
        f.a("openSDK_LOG.JsBridge", sb.toString());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            try {
                list.set(i, URLDecoder.decode(list.get(i), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        b bVar = this.a.get(str);
        if (bVar != null) {
            f.b("openSDK_LOG.JsBridge", "call----");
            bVar.call(str2, list, aVar);
            return;
        }
        f.b("openSDK_LOG.JsBridge", "not call----objName NOT FIND");
        if (aVar != null) {
            aVar.a();
        }
    }

    public boolean a(WebView webView, String str) {
        f.a("openSDK_LOG.JsBridge", "-->canHandleUrl---url = ".concat(String.valueOf(str)));
        if (str == null || !Uri.parse(str).getScheme().equals("jsbridge")) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/#");
        ArrayList arrayList = new ArrayList(Arrays.asList(sb.toString().split("/")));
        if (arrayList.size() < 6) {
            return false;
        }
        List subList = arrayList.subList(4, arrayList.size() - 1);
        C0065a aVar = new C0065a(webView, 4, str);
        webView.getUrl();
        a((String) arrayList.get(2), (String) arrayList.get(3), subList, aVar);
        return true;
    }
}
