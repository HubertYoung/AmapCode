package com.autonavi.minimap.nativesupport.amap;

import android.text.TextUtils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.minimap.ackor.ackoramap.IAmapHttpRequest;
import com.autonavi.minimap.nativesupport.platform.HttpRequestImpl;
import com.autonavi.minimap.net.Sign;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.server.aos.serverkey;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class AmapRequestImpl extends IAmapHttpRequest {
    private boolean isNeedEncrpyt = true;
    private HttpRequestImpl mHttpRequestImpl = new MyHttpRequestImpl(this);
    private boolean mIsDownLoad = false;
    private Map<String, String> mParams = new HashMap();
    private String[] signKeys;

    static class MyHttpRequestImpl extends HttpRequestImpl {
        WeakReference<AmapRequestImpl> mAmapRequestWeakReference;

        public MyHttpRequestImpl(AmapRequestImpl amapRequestImpl) {
            this.mAmapRequestWeakReference = new WeakReference<>(amapRequestImpl);
        }

        public boolean onRequestReceiveData(byte[] bArr, int i) {
            AmapRequestImpl amapRequestImpl = (AmapRequestImpl) this.mAmapRequestWeakReference.get();
            if (amapRequestImpl != null) {
                amapRequestImpl.nativeOnRequestReceiveData(amapRequestImpl, amapRequestImpl.mShadow, bArr, i);
            }
            return true;
        }

        public void onRequestFinished() {
            AmapRequestImpl amapRequestImpl = (AmapRequestImpl) this.mAmapRequestWeakReference.get();
            if (amapRequestImpl != null) {
                amapRequestImpl.nativeOnRequestFinished(amapRequestImpl, amapRequestImpl.mShadow);
            }
        }

        public void onRequestFailed(int i) {
            AmapRequestImpl amapRequestImpl = (AmapRequestImpl) this.mAmapRequestWeakReference.get();
            if (amapRequestImpl != null) {
                amapRequestImpl.nativeOnRequestFailed(amapRequestImpl, amapRequestImpl.mShadow, i);
            }
        }

        public void appendParams(bph bph) {
            AmapRequestImpl amapRequestImpl = (AmapRequestImpl) this.mAmapRequestWeakReference.get();
            if (amapRequestImpl != null) {
                amapRequestImpl.appendParams(bph);
            }
        }

        public void processRequest(String str, bph bph) {
            AmapRequestImpl amapRequestImpl = (AmapRequestImpl) this.mAmapRequestWeakReference.get();
            if (amapRequestImpl != null) {
                amapRequestImpl.processRequest(str, bph);
            }
        }
    }

    public void setSignKes(String[] strArr) {
        this.signKeys = strArr;
    }

    public void setParma(String str, String str2) {
        this.mParams.put(str, str2);
    }

    public void setNeedEncrpyt(boolean z) {
        this.isNeedEncrpyt = z;
    }

    public void setForDownloadFile(boolean z) {
        this.mIsDownLoad = z;
        if (z) {
            addHeader(OfflineUtil.CDN_HEADER_MAC, aat.a());
        }
    }

    public void cancel() {
        this.mHttpRequestImpl.cancel();
    }

    public void request(String str, int i) {
        if (str.contains("mock/1")) {
            i = 1;
        }
        switch (i) {
            case 0:
                this.mHttpRequestImpl.head(str);
                return;
            case 1:
                this.mHttpRequestImpl.get(str);
                return;
            case 2:
                this.mHttpRequestImpl.post(str, null, 0);
                break;
        }
    }

    public IAmapHttpRequest addHeader(String str, String str2) {
        this.mHttpRequestImpl.addHeader(str, str2);
        return this;
    }

    public void setUserData(long j) {
        this.mHttpRequestImpl.setUserData(j);
    }

    public long getUserData() {
        return this.mHttpRequestImpl.getUserData();
    }

    public void setTimeOut(int i) {
        this.mHttpRequestImpl.setTimeOut(i);
    }

    public String getResponseHeader(String str) {
        return this.mHttpRequestImpl.getResponseHeader(str);
    }

    public int getResponseCode() {
        return this.mHttpRequestImpl.getResponseCode();
    }

    public void appendParams(bph bph) {
        for (Entry next : this.mParams.entrySet()) {
            bph.addParam((String) next.getKey(), (String) next.getValue());
        }
    }

    public void processRequest(String str, bph bph) {
        String[] strArr;
        if (!this.mIsDownLoad) {
            HashMap hashMap = new HashMap();
            hashMap.put("channel", serverkey.getAosChannel());
            hashMap.put(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, "json");
            hashMap.putAll(NetworkParam.getNetworkParamMap(str));
            StringBuffer stringBuffer = new StringBuffer();
            if (this.signKeys != null) {
                for (String str2 : this.signKeys) {
                    if (!TextUtils.isEmpty(str2) && !str2.equals("channel")) {
                        appendValue(hashMap, str2, stringBuffer);
                        appendValue(bph.getParams(), str2, stringBuffer);
                    }
                }
            }
            hashMap.put("sign", Sign.getSign(stringBuffer.toString()));
            aaz aaz = new aaz(str);
            for (Entry entry : hashMap.entrySet()) {
                aaz.a((String) entry.getKey(), (String) entry.getValue());
            }
            if (!(bph instanceof bpj) || ((bpj) bph).getBody() != null) {
                Map<String, String> params = bph.getParams();
                if (params != null) {
                    for (Entry next : params.entrySet()) {
                        aaz.a((String) next.getKey(), (String) next.getValue());
                    }
                    params.clear();
                }
            } else {
                Map<String, String> params2 = bph.getParams();
                if (params2 != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Entry next2 : params2.entrySet()) {
                        arrayList.add(new abg((String) next2.getKey(), (String) next2.getValue()));
                    }
                    String a = aba.a((List<? extends abg>) arrayList, (String) "UTF-8");
                    AMapLog.d("post", "paramsStr : ".concat(String.valueOf(a)));
                    try {
                        ((bpj) bph).setBody(serverkey.amapEncode(a).getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException unused) {
                    }
                    params2.clear();
                }
            }
            String str3 = "1".equals(aaz.a((String) "is_bin")) ? "&is_bin=1" : "";
            String b = aaz.b("UTF-8");
            String str4 = "";
            if (aaz.a().size() > 0 && this.isNeedEncrpyt) {
                int indexOf = b.indexOf(63);
                if (indexOf <= 0 || indexOf >= b.length() - 2) {
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
                    int i = indexOf + 1;
                    str4 = b.substring(i);
                    try {
                        StringBuilder sb4 = new StringBuilder("ent=2&in=");
                        sb4.append(URLEncoder.encode(serverkey.amapEncode(str4), "UTF-8"));
                        String sb5 = sb4.toString();
                        try {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(sb5);
                            sb6.append("&csid=");
                            sb6.append(NetworkParam.getCsid());
                            str4 = sb6.toString();
                        } catch (UnsupportedEncodingException unused2) {
                            str4 = sb5;
                        }
                    } catch (UnsupportedEncodingException unused3) {
                    }
                    b = b.substring(0, i);
                }
            }
            StringBuilder sb7 = new StringBuilder();
            sb7.append(b);
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
            bph.setUrl(sb8);
        }
    }

    private static final void appendValue(Map<String, String> map, String str, StringBuffer stringBuffer) {
        String str2 = map.get(str);
        if (str2 != null) {
            stringBuffer.append(str2);
        }
    }
}
