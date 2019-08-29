package defpackage;

import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.core.network.inter.response.InputStreamResponse;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.jni.drive.offline.HttpRequestInterface;
import com.autonavi.jni.drive.offline.HttpRequestObserver;
import com.autonavi.minimap.nativesupport.platform.LogPathManager;
import com.autonavi.minimap.net.Sign;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.server.aos.serverkey;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: oi reason: default package */
/* compiled from: HttpBaseRequestImpl */
public final class oi implements bpl<InputStreamResponse>, HttpRequestInterface {
    protected bpk a;
    HttpRequestObserver b;
    private int c = 30;
    private Map<String, String> d = new HashMap();
    private long e;
    private bph f;
    private volatile boolean g;
    private int h = 0;
    private int i = 0;
    private long j = 0;
    private long k = 0;
    private boolean l = false;
    private boolean m = true;
    private String[] n;
    private Map<String, String> o = new HashMap();

    public final /* synthetic */ void onSuccess(bpk bpk) {
        long j2;
        int read;
        this.a = (InputStreamResponse) bpk;
        bph request = this.a.getRequest();
        int method = request != null ? request.getMethod() : -1;
        InputStream bodyInputStream = this.a.getBodyInputStream();
        if (bodyInputStream == null || method == 2) {
            a();
            return;
        }
        byte[] bArr = new byte[4096];
        long j3 = 0;
        while (true) {
            try {
                j2 = SystemClock.elapsedRealtime();
                try {
                    read = bodyInputStream.read(bArr);
                    if (read > 0 && !this.g) {
                        this.k += (long) read;
                        if (oh.a().d) {
                            if (!oh.a().f) {
                                if (this.b != null) {
                                    this.b.onRequestReceiveData(bArr, read);
                                }
                            }
                        }
                        j3 = j2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        this.j = j2 - SystemClock.elapsedRealtime();
                        e.printStackTrace();
                        this.h = -3;
                        a((String) "quality3d_offline_error", e);
                        a(-3);
                    } finally {
                        bow.a(bodyInputStream);
                    }
                }
            } catch (Exception e3) {
                e = e3;
                j2 = j3;
                this.j = j2 - SystemClock.elapsedRealtime();
                e.printStackTrace();
                this.h = -3;
                a((String) "quality3d_offline_error", e);
                a(-3);
            }
        }
        try {
            if (!this.g || read == -1) {
                a();
            } else {
                this.h = -1;
                this.i = read;
                a((String) "cancel", (Exception) null);
                a(-1);
            }
            bow.a(bodyInputStream);
        } catch (Exception e4) {
            e = e4;
            j2 -= SystemClock.elapsedRealtime();
            this.j = j2 - SystemClock.elapsedRealtime();
            e.printStackTrace();
            this.h = -3;
            a((String) "quality3d_offline_error", e);
            a(-3);
        }
    }

    private void a(String str, int i2) {
        String[] strArr;
        this.g = false;
        this.h = 0;
        if (i2 == 1) {
            this.f = new bpf();
        } else if (i2 == 2) {
            this.f = new bpj();
        } else {
            this.f = new bpg();
        }
        this.f.setUrl(str);
        bph bph = this.f;
        for (Entry next : this.d.entrySet()) {
            bph.addHeader((String) next.getKey(), (String) next.getValue());
        }
        bph bph2 = this.f;
        for (Entry next2 : this.o.entrySet()) {
            bph2.addParam((String) next2.getKey(), (String) next2.getValue());
        }
        this.f.setTimeout(this.c * 1000);
        bph bph3 = this.f;
        if (!this.l) {
            HashMap hashMap = new HashMap();
            hashMap.put("channel", serverkey.getAosChannel());
            hashMap.put(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, "json");
            hashMap.putAll(NetworkParam.getNetworkParamMap(str));
            StringBuffer stringBuffer = new StringBuffer();
            if (this.n != null) {
                for (String str2 : this.n) {
                    if (!TextUtils.isEmpty(str2) && !"channel".equals(str2)) {
                        a(hashMap, str2, stringBuffer);
                        a(bph3.getParams(), str2, stringBuffer);
                    }
                }
            }
            hashMap.put("sign", Sign.getSign(stringBuffer.toString()));
            aaz aaz = new aaz(str);
            for (Entry entry : hashMap.entrySet()) {
                aaz.a((String) entry.getKey(), (String) entry.getValue());
            }
            if (!(bph3 instanceof bpj) || ((bpj) bph3).getBody() != null) {
                Map<String, String> params = bph3.getParams();
                if (params != null) {
                    for (Entry next3 : params.entrySet()) {
                        aaz.a((String) next3.getKey(), (String) next3.getValue());
                    }
                    params.clear();
                }
            } else {
                Map<String, String> params2 = bph3.getParams();
                if (params2 != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Entry next4 : params2.entrySet()) {
                        arrayList.add(new abg((String) next4.getKey(), (String) next4.getValue()));
                    }
                    String a2 = aba.a((List<? extends abg>) arrayList, (String) "UTF-8");
                    AMapLog.d("post", "paramsStr : ".concat(String.valueOf(a2)));
                    try {
                        ((bpj) bph3).setBody(serverkey.amapEncode(a2).getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException unused) {
                    }
                    params2.clear();
                }
            }
            String str3 = "1".equals(aaz.a((String) "is_bin")) ? "&is_bin=1" : "";
            String b2 = aaz.b("UTF-8");
            String str4 = "";
            if (aaz.a().size() > 0 && this.m) {
                int indexOf = b2.indexOf(63);
                if (indexOf <= 0 || indexOf >= b2.length() - 2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str4);
                    sb.append(indexOf > 0 ? "" : "?");
                    sb.append("ent=2");
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append("&csid=");
                    sb3.append(NetworkParam.getCsid());
                    str4 = sb3.toString();
                } else {
                    int i3 = indexOf + 1;
                    String substring = b2.substring(i3);
                    try {
                        StringBuilder sb4 = new StringBuilder("ent=2&in=");
                        sb4.append(URLEncoder.encode(serverkey.amapEncode(substring), "UTF-8"));
                        String sb5 = sb4.toString();
                        try {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(sb5);
                            sb6.append("&csid=");
                            sb6.append(NetworkParam.getCsid());
                            substring = sb6.toString();
                        } catch (UnsupportedEncodingException unused2) {
                            substring = sb5;
                        }
                    } catch (UnsupportedEncodingException unused3) {
                    }
                    str4 = substring;
                    b2 = b2.substring(0, i3);
                }
            }
            StringBuilder sb7 = new StringBuilder();
            sb7.append(b2);
            sb7.append(str4);
            String sb8 = sb7.toString();
            if (str3.length() > 0) {
                if (sb8.contains("?")) {
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(sb8);
                    sb9.append(str3);
                    sb8 = sb9.toString();
                } else {
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(sb8);
                    sb10.append("?");
                    sb10.append(str3);
                    sb8 = sb10.toString();
                }
            }
            bph3.setUrl(sb8);
        }
        oj.a().a.a(this.f, (bpl<T>) this);
    }

    public final void cancel() {
        this.g = true;
        oj a2 = oj.a();
        a2.a.a(this.f);
    }

    public final String getResponseHeader(String str) {
        if (this.a == null) {
            return null;
        }
        if (DebugLog.isDebug()) {
            Map<String, List<String>> headers = this.a.getHeaders();
            if (headers != null) {
                Set<String> keySet = headers.keySet();
                if (keySet != null) {
                    for (String isEmpty : keySet) {
                        TextUtils.isEmpty(isEmpty);
                    }
                }
            }
        }
        return this.a.getHeader(str);
    }

    public final int getResponseCode() {
        if (this.h != 0) {
            return this.h;
        }
        if (this.a == null) {
            return -1;
        }
        return this.a.getStatusCode();
    }

    public final HttpRequestInterface addHeader(String str, String str2) {
        this.d.put(str, str2);
        return this;
    }

    public final void setTimeOut(int i2) {
        this.c = i2;
        if (this.c < 0) {
            throw new IllegalArgumentException("timeout must large than 0 second.");
        }
    }

    public final void request(String str, int i2) {
        StringBuilder sb = new StringBuilder("HttpBaseRequest.request url = ");
        sb.append(str);
        sb.append(", type = ");
        sb.append(i2);
        if (str.contains("mock/1")) {
            i2 = 1;
        }
        switch (i2) {
            case 0:
                a(str, 0);
                return;
            case 1:
                a(str, 1);
                return;
            case 2:
                a(str, 2);
                break;
        }
    }

    public final void setForDownloadFile(boolean z) {
        this.l = z;
        if (z) {
            addHeader(OfflineUtil.CDN_HEADER_MAC, aat.a());
        }
    }

    public final void setSignKes(String[] strArr) {
        this.n = strArr;
    }

    public final void setParma(String str, String str2) {
        StringBuilder sb = new StringBuilder("HttpBaseRequest.setParam: [");
        sb.append(str);
        sb.append(" : ");
        sb.append(str2);
        sb.append("]");
        this.o.put(str, str2);
    }

    public final void setNeedEncrpyt(boolean z) {
        this.m = z;
    }

    public final void setUserData(long j2) {
        this.e = j2;
    }

    public final long getUserData() {
        return this.e;
    }

    private void a() {
        if (this.b != null) {
            this.b.onRequestFinished();
        }
    }

    private void a(int i2) {
        if (this.b != null) {
            this.b.onRequestFailed(i2);
        }
    }

    public final void onFailure(bph bph, ResponseException responseException) {
        if (this.h == 0) {
            this.h = responseException.errorCode;
        }
        a((String) "quality3d_offline_error", responseException.exception);
        a(this.h);
    }

    private void a(String str, Exception exc) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("time:");
            sb.append(b());
            sb.append("\n");
            if (this.f != null) {
                sb.append("request url :");
                sb.append(this.f.getUrl());
                sb.append("\n");
            }
            sb.append("read time :");
            sb.append(Math.abs(this.j));
            sb.append("\n");
            sb.append("read total length :");
            sb.append(this.k);
            sb.append("\n");
            sb.append("content length ");
            sb.append(this.a.getContentLength());
            sb.append("\n");
            if ("cancel".equals(str)) {
                sb.append("errortype:cancel");
                sb.append("\n");
                if (this.i != 0) {
                    sb.append("length :");
                    sb.append(this.i);
                    sb.append("\n");
                }
            } else if ("quality3d_offline_error".equals(str)) {
                sb.append("errortype:quality3doffline");
                sb.append("\n");
            } else if ("network_error".equals(str)) {
                sb.append("errortype:network");
                sb.append("\n");
            }
            if (this.f != null) {
                sb.append("method :");
                sb.append(this.f.getMethod());
                sb.append("\n");
            }
            if (exc != null) {
                sb.append("exception :");
                sb.append(a((Throwable) exc));
                sb.append("\n");
            }
            if (this.h != 0) {
                sb.append("mStatusCode :");
                sb.append(this.h);
                sb.append("\n");
            }
            String upLoadLogPath = LogPathManager.getUpLoadLogPath();
            if (!TextUtils.isEmpty(upLoadLogPath)) {
                FileUtil.saveLogToPath(sb.toString(), upLoadLogPath);
            }
        } catch (Exception unused) {
        }
    }

    private static String b() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }

    private static String a(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        try {
            th.printStackTrace(printWriter);
            return stringWriter.toString();
        } finally {
            printWriter.close();
        }
    }

    private static void a(Map<String, String> map, String str, StringBuffer stringBuffer) {
        String str2 = map.get(str);
        if (str2 != null) {
            stringBuffer.append(str2);
        }
    }
}
