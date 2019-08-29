package com.alipay.mobile.nebulacore.util;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class H5PPQueryThread implements Runnable {
    public static final String TAG = "H5PPQueryThread";
    private static H5PPQueryThread a;
    private OnGetQueryResult b;
    private String c;
    private int d;
    private String e = "http://patriot.cs.pp.cn/api/resource.app.detect";
    private H5Page f;
    private boolean g = false;

    public interface OnGetQueryResult {
        void onQueryResult(String str, String str2);
    }

    private H5PPQueryThread() {
    }

    public static synchronized H5PPQueryThread getInstance() {
        H5PPQueryThread h5PPQueryThread;
        synchronized (H5PPQueryThread.class) {
            try {
                if (a == null) {
                    a = new H5PPQueryThread();
                }
                h5PPQueryThread = a;
            }
        }
        return h5PPQueryThread;
    }

    public void setParams(String url, int type, OnGetQueryResult onGetQueryResult, H5Page h5Page) {
        this.c = url;
        this.d = type;
        this.b = onGetQueryResult;
        this.f = h5Page;
        JSONObject ppConfig = H5Utils.parseObject(H5Environment.getConfig("h5_ppConfig"));
        if (ppConfig != null) {
            this.e = H5Utils.getString(ppConfig, (String) "h5_ppQueryUrl");
            H5Log.d(TAG, "queryUrl is " + this.e);
        }
    }

    public void run() {
        if (this.g) {
            H5Log.d(TAG, "isRunning return");
            return;
        }
        try {
            this.g = true;
            this.f.sendEvent("showLoading", null);
            String result = doPost(a());
            H5Log.d(TAG, "result is " + result);
            a(result);
        } catch (Exception e2) {
            H5Log.e(TAG, "catch exception", e2);
        } finally {
            this.f.sendEvent(CommonEvents.HIDE_LOADING, null);
            this.g = false;
        }
    }

    private String a() {
        long uuid = System.currentTimeMillis();
        String sign = H5SecurityUtil.getMD5("secret.alipay.client" + ("query=" + this.c + "type=" + this.d) + "fjwofu4n2wg");
        JSONObject requestData = new JSONObject();
        try {
            requestData.put((String) "id", (Object) String.valueOf(uuid));
            requestData.put((String) DriveUtil.SCHEME_PARAM_ENCRYPT, (Object) "md5");
            requestData.put((String) "sign", (Object) sign);
            JSONObject client = new JSONObject();
            client.put((String) "caller", (Object) "secret.alipay.client");
            requestData.put((String) "client", (Object) client);
            JSONObject data = new JSONObject();
            data.put((String) "query", (Object) this.c);
            data.put((String) "type", (Object) Integer.valueOf(this.d));
            requestData.put((String) "data", (Object) data);
        } catch (Exception e2) {
        }
        return requestData.toString();
    }

    public String doPost(String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        long startTime = System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) new URL(this.e).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        if (param != null && !param.trim().equals("")) {
            PrintWriter out2 = new PrintWriter(conn.getOutputStream());
            try {
                out2.print(param);
                out2.flush();
                out = out2;
            } catch (Exception e2) {
                e = e2;
                out = out2;
                try {
                    H5Log.e(TAG, "dopost catch exception ", e);
                    a((Closeable) in);
                    a((Closeable) out);
                    return result;
                } catch (Throwable th) {
                    th = th;
                    a((Closeable) in);
                    a((Closeable) out);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                out = out2;
                a((Closeable) in);
                a((Closeable) out);
                throw th;
            }
        }
        try {
            int statusCode = conn.getResponseCode();
            if (statusCode == 200) {
                BufferedReader in2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    try {
                        String line = in2.readLine();
                        if (line == null) {
                            break;
                        }
                        result = result + line;
                    } catch (Exception e3) {
                        e = e3;
                        in = in2;
                        H5Log.e(TAG, "dopost catch exception ", e);
                        a((Closeable) in);
                        a((Closeable) out);
                        return result;
                    } catch (Throwable th3) {
                        th = th3;
                        in = in2;
                        a((Closeable) in);
                        a((Closeable) out);
                        throw th;
                    }
                }
                in = in2;
            } else {
                JSONObject params = new JSONObject();
                params.put((String) "queryUrl", (Object) this.e);
                params.put((String) "queryParam", (Object) param);
                params.put((String) "statusCode", (Object) Integer.valueOf(statusCode));
                this.f.sendEvent(CommonEvents.H5_PAGE_QUERY_PP, params);
            }
            long httpCost = System.currentTimeMillis() - startTime;
            JSONObject params2 = new JSONObject();
            params2.put((String) "httpcost", (Object) Long.valueOf(httpCost));
            this.f.sendEvent(CommonEvents.H5_PAGE_QUERY_PP_COST, params2);
            H5Log.d(TAG, "http cost " + httpCost);
            a((Closeable) in);
            a((Closeable) out);
        } catch (Exception e4) {
            e = e4;
        }
        return result;
    }

    private void a(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                JSONObject resultJson = JSONObject.parseObject(result);
                JSONObject app = null;
                if (resultJson != null) {
                    JSONObject data = resultJson.getJSONObject("data");
                    if (data != null) {
                        app = data.getJSONObject("app");
                    }
                }
                String detailUrl = null;
                String packageName = null;
                if (app != null) {
                    detailUrl = app.getString("detailUrl");
                    packageName = app.getString("packageName");
                    H5Log.d(TAG, "detailUrl is " + detailUrl + ", packageName is " + packageName);
                }
                this.b.onQueryResult(detailUrl, packageName);
                return;
            }
            this.b.onQueryResult("", "");
        } catch (Exception e2) {
            H5Log.e(TAG, "parseRespnseJsonStr catch exception ", e2);
        }
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable t) {
                H5Log.e(TAG, "silentClose exception.", t);
            }
        }
    }
}
