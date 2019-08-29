package com.amap.bundle.aosservice.request;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public abstract class AosRequest {
    private static final String TAG = "AosRequest";
    protected int mChannel = 0;
    protected int mCommonParamStrategy = 0;
    protected Map<String, String> mCustomCommonParams;
    protected List<String> mDisabledCommonParams = new ArrayList();
    protected String mEncryptSignParam;
    protected int mEncryptStrategy = 2;
    protected Map<String, String> mHeaders = new HashMap();
    protected bph mHttpRequest;
    protected volatile boolean mIsCanceled;
    protected int mMethod = 0;
    protected int mOutput = 0;
    protected int mPriority = Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
    protected Map<String, String> mReqParams = new HashMap();
    protected int mRetryTimes = 3;
    protected List<String> mSignParams = new ArrayList();
    protected int mTimeout = HttpConstants.CONNECTION_TIME_OUT;
    protected String mUrl;
    protected boolean mWithoutSign;

    private String getOutputFormat(int i) {
        switch (i) {
            case 1:
                return "xml";
            case 2:
                return "jsonp";
            case 3:
                return "bin";
            default:
                return "json";
        }
    }

    /* access modifiers changed from: protected */
    public abstract bph createHttpRequest();

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public String getUrl() {
        return this.mUrl;
    }

    /* access modifiers changed from: protected */
    public void setMethod(int i) {
        this.mMethod = i;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public void setRetryTimes(int i) {
        this.mRetryTimes = i;
    }

    public int getRetryTimes() {
        return this.mRetryTimes;
    }

    public void setTimeout(int i) {
        this.mTimeout = i;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public void setPriority(int i) {
        this.mPriority = i;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public void setChannel(int i) {
        this.mChannel = i;
    }

    public int getChannel() {
        return this.mChannel;
    }

    public void addReqParam(String str, String str2) {
        if (str2 != null) {
            this.mReqParams.put(str, str2);
        }
    }

    public void addReqParams(Map<String, String> map) {
        if (map != null) {
            this.mReqParams.putAll(map);
        }
    }

    public Map<String, String> getReqParams() {
        return this.mReqParams;
    }

    public void addHeader(String str, String str2) {
        if (str2 != null) {
            this.mHeaders.put(str, str2);
        }
    }

    public void addHeaders(Map<String, String> map) {
        if (map != null) {
            this.mHeaders.putAll(map);
        }
    }

    public void addCustomCommonParam(String str, String str2) {
        if (this.mCustomCommonParams == null) {
            this.mCustomCommonParams = new HashMap();
        }
        if (str2 != null) {
            this.mCustomCommonParams.put(str, str2);
        }
    }

    public Map<String, String> getCustomCommonParams() {
        return this.mCustomCommonParams;
    }

    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public void addSignParam(String str) {
        this.mSignParams.add(str);
    }

    public void addSignParams(List<String> list) {
        if (list != null) {
            this.mSignParams.addAll(list);
        }
    }

    public List<String> getSignParams() {
        return this.mSignParams;
    }

    public void setWithoutSign(boolean z) {
        this.mWithoutSign = z;
    }

    public boolean withoutSign() {
        return this.mWithoutSign;
    }

    public void setEncryptSignParam(String str) {
        this.mEncryptSignParam = str;
    }

    public String getEncryptSignParam() {
        return this.mEncryptSignParam;
    }

    public void setEncryptStrategy(int i) {
        this.mEncryptStrategy = i;
    }

    public int getEncryptStrategy() {
        return this.mEncryptStrategy;
    }

    public void setOutput(int i) {
        this.mOutput = i;
    }

    public int getOutput() {
        return this.mOutput;
    }

    public void setCommonParamStrategy(int i) {
        this.mCommonParamStrategy = i;
    }

    public int getCommonParamStrategy() {
        return this.mCommonParamStrategy;
    }

    public void setDisabledCommonParams(List<String> list) {
        if (list != null) {
            this.mDisabledCommonParams.addAll(list);
        }
    }

    public List<String> getDisabledCommonParams() {
        return this.mDisabledCommonParams;
    }

    public void cancel() {
        this.mIsCanceled = true;
        if (this.mHttpRequest != null) {
            this.mHttpRequest.cancel();
        }
    }

    public boolean isCanceled() {
        return this.mIsCanceled;
    }

    public bph getHttpRequest() {
        return this.mHttpRequest;
    }

    public bph buildHttpRequest() {
        String str = this.mUrl;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("url can not be null!");
        }
        bph createHttpRequest = createHttpRequest();
        createHttpRequest.setRetryTimes(this.mRetryTimes);
        createHttpRequest.setTimeout(this.mTimeout);
        createHttpRequest.setPriority(this.mPriority);
        createHttpRequest.setChannel(this.mChannel);
        for (Entry next : this.mHeaders.entrySet()) {
            createHttpRequest.addHeader((String) next.getKey(), (String) next.getValue());
        }
        is a = ip.a();
        io aosCommonParam = getAosCommonParam();
        if (iu.a != null) {
            iu.a.a(createHttpRequest, aosCommonParam);
        }
        if (aosCommonParam != null && aosCommonParam.b.size() > 0) {
            for (Entry next2 : aosCommonParam.b.entrySet()) {
                createHttpRequest.addHeader((String) next2.getKey(), (String) next2.getValue());
            }
        }
        Map<String, String> map = this.mReqParams;
        Map hashMap = aosCommonParam == null ? new HashMap() : aosCommonParam.a;
        if (!this.mWithoutSign) {
            if (TextUtils.isEmpty(this.mEncryptSignParam)) {
                List<String> list = this.mSignParams;
                list.remove("channel");
                list.add(0, "channel");
                list.remove("_aosmd5");
                StringBuilder sb = new StringBuilder();
                for (String next3 : list) {
                    String str2 = map.get(next3);
                    if (TextUtils.isEmpty(str2)) {
                        str2 = (String) hashMap.get(next3);
                    }
                    if (str2 != null) {
                        sb.append(str2);
                    }
                }
                sb.append(AUScreenAdaptTool.PREFIX_ID);
                sb.append(a.a());
                hashMap.put("sign", a.a(sb.toString().getBytes()));
            } else {
                hashMap.put("sign", this.mEncryptSignParam);
            }
        }
        jc jcVar = new jc(str);
        String str3 = jcVar.a;
        String str4 = jcVar.f;
        String str5 = jcVar.h;
        in a2 = in.a();
        if (a2.b != null && "http".equals(str3)) {
            StringBuilder sb2 = new StringBuilder(str3);
            sb2.append("://");
            sb2.append(str4);
            if (!TextUtils.isEmpty(str5)) {
                sb2.append(str5);
            }
            a2.b.a(sb2.toString());
        }
        readEncryptStrategyFromParams(jcVar, map);
        processParams(jcVar, hashMap, map);
        StringBuilder sb3 = new StringBuilder();
        Charset forName = Charset.forName("UTF-8");
        StringBuilder sb4 = new StringBuilder();
        if (jcVar.a != null) {
            sb4.append(jcVar.a);
            sb4.append(':');
        }
        if (jcVar.b != null) {
            sb4.append(jcVar.b);
        } else {
            if (jcVar.c != null) {
                sb4.append("//");
                sb4.append(jcVar.c);
            } else if (jcVar.f != null) {
                sb4.append("//");
                if (jcVar.e != null) {
                    sb4.append(jcVar.e);
                    sb4.append(AUScreenAdaptTool.PREFIX_ID);
                } else if (jcVar.d != null) {
                    sb4.append(jd.a(jcVar.d, forName));
                    sb4.append(AUScreenAdaptTool.PREFIX_ID);
                }
                if (iz.a(jcVar.f)) {
                    sb4.append("[");
                    sb4.append(jcVar.f);
                    sb4.append("]");
                } else {
                    sb4.append(jcVar.f);
                }
                if (jcVar.g >= 0) {
                    sb4.append(":");
                    sb4.append(jcVar.g);
                }
            }
            if (jcVar.i != null) {
                sb4.append(jc.b(jcVar.i));
            } else if (jcVar.h != null) {
                String c = jd.c(jc.b(jcVar.h), forName);
                if (c != null) {
                    c.replace("+", "20%");
                }
                sb4.append(c);
            }
            if (jcVar.j != null) {
                sb4.append("?");
                sb4.append(jcVar.j);
            } else if (jcVar.k != null) {
                sb4.append("?");
                sb4.append(jd.a((Iterable<? extends ja>) jcVar.k, forName));
            }
        }
        if (jcVar.m != null) {
            sb4.append(MetaRecord.LOG_SEPARATOR);
            sb4.append(jcVar.m);
        } else if (jcVar.l != null) {
            sb4.append(MetaRecord.LOG_SEPARATOR);
            sb4.append(jd.b(jcVar.l, forName));
        }
        String sb5 = sb4.toString();
        int indexOf = sb5.indexOf(63);
        String str6 = "";
        if (indexOf > 0 && indexOf < sb5.length() - 2) {
            int i = indexOf + 1;
            str6 = sb5.substring(i);
            sb5 = sb5.substring(0, i);
        }
        String str7 = null;
        if (this.mEncryptStrategy != 2) {
            sb3.append(str6);
        } else {
            addQueryParam(sb3, "ent", "2");
            if (!TextUtils.isEmpty(str6)) {
                str7 = a.a(str6);
                addQueryParam(sb3, "in", URLEncoder.encode(str7));
            }
        }
        securityGuardSign(createHttpRequest, str7);
        addQueryParam(sb3, "csid", UUID.randomUUID().toString());
        if (sb5.charAt(sb5.length() - 1) != '?') {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(sb5);
            sb6.append('?');
            sb5 = sb6.toString();
        }
        StringBuilder sb7 = new StringBuilder();
        sb7.append(sb5);
        sb7.append(sb3.toString());
        createHttpRequest.setUrl(sb7.toString());
        this.mHttpRequest = createHttpRequest;
        return createHttpRequest;
    }

    /* access modifiers changed from: protected */
    public void processParams(jc jcVar, Map<String, String> map, Map<String, String> map2) {
        HashMap hashMap = new HashMap();
        hashMap.putAll(map);
        hashMap.putAll(map2);
        for (Entry entry : hashMap.entrySet()) {
            if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                jcVar.a((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void securityGuardSign(bph bph, String str) {
        securityGuardSign(bph, str, null);
    }

    /* access modifiers changed from: protected */
    public final void securityGuardSign(bph bph, String str, String str2) {
        is a = ip.a();
        if (!this.mWithoutSign && a != null && a.b()) {
            if (str == null) {
                str = str2;
            }
            if (!TextUtils.isEmpty(str) && a != null) {
                long currentTimeMillis = System.currentTimeMillis();
                String valueOf = String.valueOf(currentTimeMillis / 1000);
                String a2 = a.a(str, valueOf);
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                long currentTimeMillis3 = System.currentTimeMillis();
                String c = a.c();
                long currentTimeMillis4 = System.currentTimeMillis() - currentTimeMillis3;
                if (!TextUtils.isEmpty(a2)) {
                    bph.addHeader("x-pv", "5.1");
                    bph.addHeader("x-t", valueOf);
                    bph.addHeader("x-appkey", a.d());
                    bph.addHeader("x-sign", a2);
                    bph.requestStatistics.q.put("xsign_cost", Long.valueOf(currentTimeMillis2));
                }
                if (!TextUtils.isEmpty(c)) {
                    String str3 = "";
                    try {
                        str3 = URLEncoder.encode(c, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        StringBuilder sb = new StringBuilder("miniwua encode error, ");
                        sb.append(e.getLocalizedMessage());
                        sb.append("; miniwua: ");
                        sb.append(c);
                        bpv.d(TAG, sb.toString());
                    }
                    bph.addHeader("x-mini-wua", str3);
                    bph.requestStatistics.q.put("wua_cost", Long.valueOf(currentTimeMillis4));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void readEncryptStrategyFromParams(jc jcVar, Map<String, String> map) {
        String a = jcVar.a("ent");
        if (TextUtils.isEmpty(a) && map != null) {
            a = map.remove("ent");
        }
        if (!TextUtils.isEmpty(a)) {
            try {
                setEncryptStrategy(Integer.parseInt(a));
            } catch (Throwable unused) {
            }
        }
    }

    private io getAosCommonParam() {
        io a = ip.b().a(this.mUrl, this.mCommonParamStrategy);
        if (a == null) {
            a = new io();
        }
        if (this.mCustomCommonParams != null) {
            a.a.putAll(this.mCustomCommonParams);
        }
        a.a.put(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, getOutputFormat(this.mOutput));
        if (a != null) {
            for (String remove : this.mDisabledCommonParams) {
                a.a.remove(remove);
            }
        }
        return a;
    }

    private void addQueryParam(StringBuilder sb, String str, String str2) {
        if (sb.length() > 0) {
            sb.append('&');
        }
        sb.append(str);
        sb.append('=');
        sb.append(str2);
    }

    static String paramsToString(Map<String, String> map) {
        if (map.isEmpty()) {
            return "";
        }
        ArrayList arrayList = new ArrayList();
        for (Entry next : map.entrySet()) {
            arrayList.add(new ja((String) next.getKey(), (String) next.getValue()));
        }
        return jd.a((List<? extends ja>) arrayList, (String) "UTF-8");
    }
}
