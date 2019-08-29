package com.alipay.android.phone.mobilecommon.multimediabiz.biz.net;

import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.FileApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.ImageApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl.HttpDjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpClientUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class UriFactory {
    private static final Logger a = Logger.getLogger((String) "UriFactory");
    private static TokenApi b;
    private static LruCache<String, SparseArray<String>> c = new LruCache<>(100000);

    public static class Request {
        public static final int SOURCE_TYPE_FILE = 0;
        public static final int SOURCE_TYPE_IMAGE = 1;
        public boolean isPublic = false;
        public Map<String, String> params;
        public boolean preferHttps;
        public final int sourceType;

        public Request(int sourceType2) {
            this.sourceType = sourceType2;
        }

        public String toString() {
            return "Request{sourceType=" + this.sourceType + ", preferHttps=" + this.preferHttps + ", params=" + this.params + '}';
        }
    }

    private static int a(String uri) {
        if (PathUtils.isHttp(uri)) {
            return 0;
        }
        if (PathUtils.isDjangoPath(uri)) {
            return 1;
        }
        if (PathUtils.checkAftId(uri)) {
            return 2;
        }
        return -1;
    }

    public static String buildGetUrl(String uri, Request request) {
        switch (a(uri)) {
            case 0:
                return b(uri);
            case 1:
                return a(uri, request);
            case 2:
                return b(uri, request);
            default:
                throw new IllegalArgumentException("unknown uri type,  " + uri);
        }
    }

    private static String b(String uri) {
        return uri;
    }

    private static String a(String uri, Request request) {
        return c(uri, request);
    }

    private static String b(String uri, Request request) {
        return c(uri, request);
    }

    private static String c(String id, Request request) {
        String url;
        if (PathUtils.isDjangoPath(id) || PathUtils.checkAftId(id)) {
            List params = new ArrayList();
            switch (request.sourceType) {
                case 0:
                    url = FileApiInfo.DOWNLOAD_BATCH.getUrlApi();
                    break;
                case 1:
                    url = ImageApiInfo.DOWNLOAD_THUMBNAILS.getUrlApi();
                    break;
                default:
                    throw new IllegalArgumentException("unknown sourceType! id: " + id + ", request: " + request);
            }
            params.add(new BasicNameValuePair("fileIds", id));
            if (!request.isPublic) {
                a(id, params);
            }
            a(params, request.params);
            String url2 = HttpClientUtils.urlAppendParams(url, params);
            if (request.preferHttps) {
                url2 = c(url2);
            }
            a.d("buildById url: " + url2, new Object[0]);
            return url2;
        }
        throw new IllegalArgumentException("id: " + id + " is not aftsId or djgId");
    }

    private static String c(String url) {
        return CommonUtils.changeUriByParams(new URI(url), "https", ConfigManager.getInstance().getCommonConfigItem().net.dlHttpsHost, 443).toString();
    }

    private static void a(String id, List<NameValuePair> params) {
        String token = a().getTokenString();
        if (TextUtils.isEmpty(token)) {
            throw new IllegalStateException("token is null");
        }
        SparseArray aclTimestamp = a(id, token);
        params.add(new BasicNameValuePair("token", token));
        params.add(new BasicNameValuePair("timestamp", aclTimestamp.get(2)));
        params.add(new BasicNameValuePair("acl", aclTimestamp.get(1)));
    }

    private static void a(List<NameValuePair> params, Map<String, String> extParams) {
        if (extParams != null && !extParams.isEmpty()) {
            for (Entry entry : extParams.entrySet()) {
                params.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }
    }

    private static TokenApi a() {
        if (b == null) {
            b = new HttpDjangoClient(AppUtils.getApplicationContext(), new HttpConnectionManager()).getTokenApi();
        }
        return b;
    }

    private static SparseArray<String> a(String id, String token) {
        String key = token + MetaRecord.LOG_SEPARATOR + id;
        SparseArray aclTimestamp = (SparseArray) c.get(key);
        if (aclTimestamp != null) {
            return aclTimestamp;
        }
        String timestampStr = String.valueOf(System.currentTimeMillis());
        String acl = EnvSwitcher.getAclString(id, timestampStr, new HttpConnectionManager(), false);
        SparseArray aclTimestamp2 = new SparseArray(2);
        aclTimestamp2.put(1, acl);
        aclTimestamp2.put(2, timestampStr);
        c.put(key, aclTimestamp2);
        return aclTimestamp2;
    }
}
