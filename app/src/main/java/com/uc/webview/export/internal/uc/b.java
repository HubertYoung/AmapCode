package com.uc.webview.export.internal.uc;

import com.uc.webview.export.internal.interfaces.INetworkDelegate;
import com.uc.webview.export.internal.interfaces.IRequestData;
import com.uc.webview.export.internal.interfaces.IResponseData;
import com.uc.webview.export.internal.interfaces.InvokeObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ProGuard */
public final class b implements InvokeObject {
    private INetworkDelegate a;

    /* compiled from: ProGuard */
    static class a implements IResponseData {
        /* access modifiers changed from: private */
        public Map<String, Object> a = new HashMap();

        public a(Map<String, Object> map) {
            this.a = map;
        }

        public final String getUrl() {
            return (String) this.a.get("1");
        }

        public final void setUrl(String str) {
            this.a.put("1", str);
        }

        public final int getStatus() {
            return ((Integer) this.a.get("4")).intValue();
        }

        public final void setStatus(int i) {
            this.a.put("4", Integer.valueOf(i));
        }

        public final Map<String, String> getHeaders() {
            return (Map) this.a.get("3");
        }

        public final void setHeaders(Map<String, String> map) {
            this.a.put("3", map);
        }

        public final Map<String, List<String>> getHeadersV2() {
            return (Map) this.a.get("3");
        }

        public final void setHeadersV2(Map<String, List<String>> map) {
            this.a.put("3", map);
        }
    }

    /* renamed from: com.uc.webview.export.internal.uc.b$b reason: collision with other inner class name */
    /* compiled from: ProGuard */
    static class C0070b implements IRequestData {
        /* access modifiers changed from: private */
        public Map<String, Object> a = new HashMap();

        public C0070b(Map<String, Object> map) {
            this.a = map;
        }

        public final String getUrl() {
            return (String) this.a.get("1");
        }

        public final void setUrl(String str) {
            this.a.put("1", str);
        }

        public final String getMethod() {
            return (String) this.a.get("2");
        }

        public final void setMethod(String str) {
            this.a.put("2", str);
        }

        public final Map<String, String> getHeaders() {
            return (Map) this.a.get("3");
        }

        public final void setHeaders(Map<String, String> map) {
            this.a.put("3", map);
        }
    }

    public b(INetworkDelegate iNetworkDelegate) {
        this.a = iNetworkDelegate;
    }

    public final Object invoke(int i, Object[] objArr) {
        switch (i) {
            case 1:
                return ((C0070b) this.a.onSendRequest(new C0070b(objArr[0]))).a;
            case 2:
            case 3:
                return ((a) this.a.onReceiveResponse(new a(objArr[0]))).a;
            default:
                return null;
        }
    }
}
