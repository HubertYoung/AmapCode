package com.tencent.mm.opensdk.diffdev.a;

import com.tencent.mm.opensdk.utils.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public final class e {
    public static byte[] a(String str) {
        if (str == null || str.length() == 0) {
            Log.e("MicroMsg.SDK.NetUtil", "httpGet, url is null");
            return null;
        }
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(str);
        try {
            HttpConnectionParams.setSoTimeout(defaultHttpClient.getParams(), 60000);
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            if (execute.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toByteArray(execute.getEntity());
            }
            StringBuilder sb = new StringBuilder("httpGet fail, status code = ");
            sb.append(execute.getStatusLine().getStatusCode());
            Log.e("MicroMsg.SDK.NetUtil", sb.toString());
            return null;
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("httpGet, Exception ex = ");
            sb2.append(e.getMessage());
            Log.e("MicroMsg.SDK.NetUtil", sb2.toString());
            return null;
        } catch (IncompatibleClassChangeError e2) {
            StringBuilder sb3 = new StringBuilder("httpGet, IncompatibleClassChangeError ex = ");
            sb3.append(e2.getMessage());
            Log.e("MicroMsg.SDK.NetUtil", sb3.toString());
            return null;
        } catch (Throwable th) {
            StringBuilder sb4 = new StringBuilder("httpGet, Throwable ex = ");
            sb4.append(th.getMessage());
            Log.e("MicroMsg.SDK.NetUtil", sb4.toString());
            return null;
        }
    }
}
