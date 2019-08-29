package com.mpaas.nebula.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUProgressDialog;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5ImageByteListener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.mpaas.nebula.NebulaBiz;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class H5UploadPlugin extends H5SimplePlugin {
    public static final String TAG = "H5UploadPlugin";
    /* access modifiers changed from: private */
    public H5Page a;

    private class UploadFile implements Runnable {
        AUProgressDialog apGenericProgressDialog;
        byte[] bytes;
        H5BridgeContext context;
        String filePath;
        JSONObject fromData;
        boolean hasSend = false;
        JSONObject headers;
        String localId;
        String name;
        String reqUrl;
        String uploadType;

        public UploadFile(String filePath2, String name2, String reqUrl2, JSONObject headers2, JSONObject fromData2, H5BridgeContext context2, byte[] bytes2, String localId2, String uploadType2, AUProgressDialog apGenericProgressDialog2) {
            this.filePath = filePath2;
            this.name = name2;
            this.reqUrl = reqUrl2;
            this.headers = headers2;
            this.fromData = fromData2;
            this.context = context2;
            this.bytes = bytes2;
            this.localId = localId2;
            this.uploadType = uploadType2;
            this.apGenericProgressDialog = apGenericProgressDialog2;
        }

        /* JADX WARNING: type inference failed for: r40v0, types: [java.io.InputStream] */
        /* JADX WARNING: type inference failed for: r19v1 */
        /* JADX WARNING: type inference failed for: r33v0 */
        /* JADX WARNING: type inference failed for: r33v1 */
        /* JADX WARNING: type inference failed for: r0v46, types: [java.io.InputStream] */
        /* JADX WARNING: type inference failed for: r33v2 */
        /* JADX WARNING: type inference failed for: r1v5, types: [java.io.InputStream] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 4 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r44 = this;
                r29 = 0
                r0 = r44
                byte[] r5 = r0.bytes     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x013b
                java.io.ByteArrayInputStream r25 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                byte[] r5 = r0.bytes     // Catch:{ Exception -> 0x00ca }
                r0 = r25
                r0.<init>(r5)     // Catch:{ Exception -> 0x00ca }
                android.content.Context r5 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ Exception -> 0x00ca }
                java.lang.String r14 = com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest.getDefaultDownloadDir(r5)     // Catch:{ Exception -> 0x00ca }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ca }
                r5.<init>()     // Catch:{ Exception -> 0x00ca }
                java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = "/"
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                java.lang.String r6 = r0.localId     // Catch:{ Exception -> 0x00ca }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = ".jpg"
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r29 = r5.toString()     // Catch:{ Exception -> 0x00ca }
                java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x00ca }
                r0 = r29
                r5.<init>(r0)     // Catch:{ Exception -> 0x00ca }
                r0 = r25
                com.alipay.mobile.nebula.util.H5FileUtil.copyToFile(r0, r5)     // Catch:{ Exception -> 0x00ca }
                java.io.File r16 = new java.io.File     // Catch:{ Exception -> 0x00ca }
                r0 = r16
                r1 = r29
                r0.<init>(r1)     // Catch:{ Exception -> 0x00ca }
            L_0x0051:
                java.lang.String r5 = "H5UploadPlugin"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = "file "
                r6.<init>(r7)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = r16.getAbsolutePath()     // Catch:{ Exception -> 0x00ca }
                java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.util.H5Log.d(r5, r6)     // Catch:{ Exception -> 0x00ca }
                java.util.ArrayList r32 = new java.util.ArrayList     // Catch:{ Exception -> 0x00ca }
                r32.<init>()     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.fromData     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x0148
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.fromData     // Catch:{ Exception -> 0x00ca }
                boolean r5 = r5.isEmpty()     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x0148
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.fromData     // Catch:{ Exception -> 0x00ca }
                java.util.Set r5 = r5.keySet()     // Catch:{ Exception -> 0x00ca }
                java.util.Iterator r26 = r5.iterator()     // Catch:{ Exception -> 0x00ca }
            L_0x008a:
                boolean r5 = r26.hasNext()     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x0148
                java.lang.Object r28 = r26.next()     // Catch:{ Throwable -> 0x00c1 }
                java.lang.String r28 = (java.lang.String) r28     // Catch:{ Throwable -> 0x00c1 }
                java.lang.String r43 = ""
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.fromData     // Catch:{ Throwable -> 0x00c1 }
                r0 = r28
                java.lang.Object r5 = r5.get(r0)     // Catch:{ Throwable -> 0x00c1 }
                if (r5 == 0) goto L_0x00b2
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.fromData     // Catch:{ Throwable -> 0x00c1 }
                r0 = r28
                java.lang.Object r5 = r5.get(r0)     // Catch:{ Throwable -> 0x00c1 }
                java.lang.String r43 = r5.toString()     // Catch:{ Throwable -> 0x00c1 }
            L_0x00b2:
                com.alipay.mobile.common.transport.http.multipart.StringPart r5 = new com.alipay.mobile.common.transport.http.multipart.StringPart     // Catch:{ Throwable -> 0x00c1 }
                r0 = r28
                r1 = r43
                r5.<init>(r0, r1)     // Catch:{ Throwable -> 0x00c1 }
                r0 = r32
                r0.add(r5)     // Catch:{ Throwable -> 0x00c1 }
                goto L_0x008a
            L_0x00c1:
                r41 = move-exception
                java.lang.String r5 = "H5UploadPlugin"
                r0 = r41
                com.alipay.mobile.nebula.util.H5Log.e(r5, r0)     // Catch:{ Exception -> 0x00ca }
                goto L_0x008a
            L_0x00ca:
                r15 = move-exception
                java.lang.String r5 = "H5UploadPlugin"
                java.lang.String r6 = "exception detail"
                com.alipay.mobile.nebula.util.H5Log.e(r5, r6, r15)     // Catch:{ all -> 0x0218 }
                java.lang.Class<com.alipay.mobile.nebula.provider.H5LogProvider> r5 = com.alipay.mobile.nebula.provider.H5LogProvider.class
                java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x0218 }
                java.lang.Object r4 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r5)     // Catch:{ all -> 0x0218 }
                com.alipay.mobile.nebula.provider.H5LogProvider r4 = (com.alipay.mobile.nebula.provider.H5LogProvider) r4     // Catch:{ all -> 0x0218 }
                if (r4 == 0) goto L_0x00f8
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0218 }
                java.lang.String r6 = "H5UploadPlugin^uploadFileException="
                r5.<init>(r6)     // Catch:{ all -> 0x0218 }
                java.lang.StringBuilder r5 = r5.append(r15)     // Catch:{ all -> 0x0218 }
                java.lang.String r10 = r5.toString()     // Catch:{ all -> 0x0218 }
                java.lang.String r5 = "H5UploadPlugin"
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 0
                r4.log(r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0218 }
            L_0x00f8:
                r0 = r44
                boolean r5 = r0.hasSend     // Catch:{ all -> 0x0218 }
                if (r5 != 0) goto L_0x0112
                r5 = 1
                r0 = r44
                r0.hasSend = r5     // Catch:{ all -> 0x0218 }
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r5 = com.mpaas.nebula.plugin.H5UploadPlugin.this     // Catch:{ all -> 0x0218 }
                r0 = r44
                com.alipay.mobile.h5container.api.H5BridgeContext r6 = r0.context     // Catch:{ all -> 0x0218 }
                java.lang.String r7 = r15.toString()     // Catch:{ all -> 0x0218 }
                com.mpaas.nebula.plugin.H5UploadPlugin.a(r6, r7)     // Catch:{ all -> 0x0218 }
            L_0x0112:
                r0 = r44
                boolean r5 = r0.hasSend
                if (r5 != 0) goto L_0x012a
                r5 = 1
                r0 = r44
                r0.hasSend = r5
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r5 = com.mpaas.nebula.plugin.H5UploadPlugin.this
                r0 = r44
                com.alipay.mobile.h5container.api.H5BridgeContext r6 = r0.context
                java.lang.String r7 = "网络异常"
                com.mpaas.nebula.plugin.H5UploadPlugin.a(r6, r7)
            L_0x012a:
                r0 = r44
                com.alipay.mobile.antui.dialog.AUProgressDialog r5 = r0.apGenericProgressDialog
                if (r5 == 0) goto L_0x013a
                com.mpaas.nebula.plugin.H5UploadPlugin$UploadFile$1 r5 = new com.mpaas.nebula.plugin.H5UploadPlugin$UploadFile$1
                r0 = r44
                r5.<init>()
                com.alipay.mobile.nebula.util.H5Utils.runOnMain(r5)
            L_0x013a:
                return
            L_0x013b:
                java.io.File r16 = new java.io.File     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                java.lang.String r5 = r0.filePath     // Catch:{ Exception -> 0x00ca }
                r0 = r16
                r0.<init>(r5)     // Catch:{ Exception -> 0x00ca }
                goto L_0x0051
            L_0x0148:
                r42 = 0
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x0193
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = "Content-Type"
                boolean r5 = r5.containsKey(r6)     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x0193
                java.lang.String r5 = "no"
                java.lang.String r6 = "h5_uploadFile_type"
                java.lang.String r6 = com.mpaas.nebula.NebulaBiz.getConfig(r6)     // Catch:{ Exception -> 0x00ca }
                boolean r5 = r5.equalsIgnoreCase(r6)     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x0193
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = "Content-Type"
                java.lang.String r42 = com.alipay.mobile.nebula.util.H5Utils.getString(r5, r6)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "H5UploadPlugin"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = "type "
                r6.<init>(r7)     // Catch:{ Exception -> 0x00ca }
                r0 = r42
                java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.util.H5Log.d(r5, r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = "Content-Type"
                r5.remove(r6)     // Catch:{ Exception -> 0x00ca }
            L_0x0193:
                boolean r5 = android.text.TextUtils.isEmpty(r42)     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x0242
                com.alipay.mobile.common.transport.http.multipart.FilePart r5 = new com.alipay.mobile.common.transport.http.multipart.FilePart     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                java.lang.String r6 = r0.name     // Catch:{ Exception -> 0x00ca }
                r0 = r16
                r1 = r42
                r5.<init>(r6, r0, r1)     // Catch:{ Exception -> 0x00ca }
                r0 = r32
                r0.add(r5)     // Catch:{ Exception -> 0x00ca }
            L_0x01ab:
                com.alipay.mobile.common.transport.http.multipart.MultipartEntity r34 = new com.alipay.mobile.common.transport.http.multipart.MultipartEntity     // Catch:{ Exception -> 0x00ca }
                r0 = r34
                r1 = r32
                r0.<init>(r1)     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.common.transport.h5.H5HttpUrlRequest r20 = new com.alipay.mobile.common.transport.h5.H5HttpUrlRequest     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                java.lang.String r5 = r0.reqUrl     // Catch:{ Exception -> 0x00ca }
                r6 = 0
                r7 = 0
                r0 = r20
                r1 = r34
                r0.<init>(r5, r1, r6, r7)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "POST"
                r0 = r20
                r0.setRequestMethod(r5)     // Catch:{ Exception -> 0x00ca }
                r6 = 60000(0xea60, double:2.9644E-319)
                r0 = r20
                r0.setTimeout(r6)     // Catch:{ Exception -> 0x00ca }
                r5 = 1
                r0 = r20
                r0.setAsyncMonitorLog(r5)     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x0256
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                boolean r5 = r5.isEmpty()     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x0256
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                java.util.Set r5 = r5.keySet()     // Catch:{ Exception -> 0x00ca }
                java.util.Iterator r26 = r5.iterator()     // Catch:{ Exception -> 0x00ca }
            L_0x01f4:
                boolean r5 = r26.hasNext()     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x0256
                java.lang.Object r28 = r26.next()     // Catch:{ Exception -> 0x00ca }
                java.lang.String r28 = (java.lang.String) r28     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.alibaba.fastjson.JSONObject r5 = r0.headers     // Catch:{ Exception -> 0x00ca }
                r0 = r28
                java.lang.Object r5 = r5.get(r0)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r43 = r5.toString()     // Catch:{ Exception -> 0x00ca }
                r0 = r20
                r1 = r28
                r2 = r43
                r0.addHeader(r1, r2)     // Catch:{ Exception -> 0x00ca }
                goto L_0x01f4
            L_0x0218:
                r5 = move-exception
                r0 = r44
                boolean r6 = r0.hasSend
                if (r6 != 0) goto L_0x0231
                r6 = 1
                r0 = r44
                r0.hasSend = r6
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r6 = com.mpaas.nebula.plugin.H5UploadPlugin.this
                r0 = r44
                com.alipay.mobile.h5container.api.H5BridgeContext r7 = r0.context
                java.lang.String r8 = "网络异常"
                com.mpaas.nebula.plugin.H5UploadPlugin.a(r7, r8)
            L_0x0231:
                r0 = r44
                com.alipay.mobile.antui.dialog.AUProgressDialog r6 = r0.apGenericProgressDialog
                if (r6 == 0) goto L_0x0241
                com.mpaas.nebula.plugin.H5UploadPlugin$UploadFile$1 r6 = new com.mpaas.nebula.plugin.H5UploadPlugin$UploadFile$1
                r0 = r44
                r6.<init>()
                com.alipay.mobile.nebula.util.H5Utils.runOnMain(r6)
            L_0x0241:
                throw r5
            L_0x0242:
                com.alipay.mobile.common.transport.http.multipart.FilePart r5 = new com.alipay.mobile.common.transport.http.multipart.FilePart     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                java.lang.String r6 = r0.name     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = ""
                r0 = r16
                r5.<init>(r6, r0, r7)     // Catch:{ Exception -> 0x00ca }
                r0 = r32
                r0.add(r5)     // Catch:{ Exception -> 0x00ca }
                goto L_0x01ab
            L_0x0256:
                java.lang.String r5 = "accept"
                java.lang.String r6 = "*/*"
                r0 = r20
                r0.addHeader(r5, r6)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "connection"
                java.lang.String r6 = "Keep-Alive"
                r0 = r20
                r0.addHeader(r5, r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r5 = com.mpaas.nebula.plugin.H5UploadPlugin.this     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.h5container.api.H5Page r5 = r5.a     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x02d0
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r5 = com.mpaas.nebula.plugin.H5UploadPlugin.this     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.h5container.api.H5Page r5 = r5.a     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.webview.APWebView r5 = r5.getWebView()     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x02d0
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r5 = com.mpaas.nebula.plugin.H5UploadPlugin.this     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.h5container.api.H5Page r5 = r5.a     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.webview.APWebView r5 = r5.getWebView()     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.webview.APWebSettings r5 = r5.getSettings()     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x02d0
                java.lang.String r5 = "user-agent"
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r6 = com.mpaas.nebula.plugin.H5UploadPlugin.this     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.h5container.api.H5Page r6 = r6.a     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.webview.APWebView r6 = r6.getWebView()     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.webview.APWebSettings r6 = r6.getSettings()     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = r6.getUserAgentString()     // Catch:{ Exception -> 0x00ca }
                r0 = r20
                r0.addHeader(r5, r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r5 = com.mpaas.nebula.plugin.H5UploadPlugin.this     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.h5container.api.H5Page r5 = r5.a     // Catch:{ Exception -> 0x00ca }
                android.os.Bundle r31 = r5.getParams()     // Catch:{ Exception -> 0x00ca }
                if (r31 == 0) goto L_0x02d0
                java.lang.String r5 = "appId"
                r0 = r31
                java.lang.String r11 = com.alipay.mobile.nebula.util.H5Utils.getString(r0, r5)     // Catch:{ Exception -> 0x00ca }
                boolean r5 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x02d0
                java.lang.String r5 = "bizId"
                r0 = r20
                r0.addTags(r5, r11)     // Catch:{ Exception -> 0x00ca }
            L_0x02d0:
                r0 = r44
                java.lang.String r5 = r0.reqUrl     // Catch:{ Exception -> 0x00ca }
                java.lang.String r13 = com.alipay.mobile.nebula.util.H5CookieUtil.getCookie(r5)     // Catch:{ Exception -> 0x00ca }
                boolean r5 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x02ff
                java.lang.String r5 = "Cookie"
                r0 = r20
                r0.addHeader(r5, r13)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "H5UploadPlugin"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = "add cookie:"
                r6.<init>(r7)     // Catch:{ Exception -> 0x00ca }
                java.lang.StringBuilder r6 = r6.append(r13)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = " , for h5HttpUrlRequest"
                java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.util.H5Log.d(r5, r6)     // Catch:{ Exception -> 0x00ca }
            L_0x02ff:
                com.alipay.mobile.common.transport.h5.H5NetworkManager r5 = new com.alipay.mobile.common.transport.h5.H5NetworkManager     // Catch:{ Exception -> 0x00ca }
                android.content.Context r6 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ Exception -> 0x00ca }
                r5.<init>(r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r20
                java.util.concurrent.Future r17 = r5.enqueue(r0)     // Catch:{ Exception -> 0x00ca }
                if (r17 == 0) goto L_0x042b
                java.lang.Object r24 = r17.get()     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.common.transport.h5.H5HttpUrlResponse r24 = (com.alipay.mobile.common.transport.h5.H5HttpUrlResponse) r24     // Catch:{ Exception -> 0x00ca }
                if (r24 == 0) goto L_0x042b
                com.alipay.mobile.common.transport.http.HttpUrlHeader r5 = r24.getHeader()     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x042b
                java.io.InputStream r5 = r24.getInputStream()     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x042b
                com.alibaba.fastjson.JSONObject r37 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x00ca }
                r37.<init>()     // Catch:{ Exception -> 0x00ca }
                r18 = 0
                com.alipay.mobile.common.transport.http.HttpUrlHeader r5 = r24.getHeader()     // Catch:{ Exception -> 0x00ca }
                org.apache.http.Header[] r38 = r5.getAllHeaders()     // Catch:{ Exception -> 0x00ca }
                if (r38 == 0) goto L_0x03a5
                r0 = r38
                int r5 = r0.length     // Catch:{ Exception -> 0x00ca }
                if (r5 <= 0) goto L_0x03a5
                r0 = r38
                int r6 = r0.length     // Catch:{ Exception -> 0x00ca }
                r5 = 0
            L_0x033e:
                if (r5 >= r6) goto L_0x03a5
                r21 = r38[r5]     // Catch:{ Exception -> 0x00ca }
                java.lang.String r22 = r21.getName()     // Catch:{ Exception -> 0x00ca }
                if (r22 == 0) goto L_0x03a2
                java.lang.String r23 = r21.getValue()     // Catch:{ Exception -> 0x00ca }
                r0 = r37
                r1 = r22
                r2 = r23
                r0.put(r1, r2)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = "Content-Encoding"
                r0 = r22
                boolean r7 = r7.equalsIgnoreCase(r0)     // Catch:{ Exception -> 0x00ca }
                if (r7 == 0) goto L_0x036b
                java.lang.String r7 = "gzip"
                r0 = r23
                boolean r7 = r7.equalsIgnoreCase(r0)     // Catch:{ Exception -> 0x00ca }
                if (r7 == 0) goto L_0x036b
                r18 = 1
            L_0x036b:
                java.lang.String r7 = "set-cookie"
                r0 = r22
                boolean r7 = r0.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x00ca }
                if (r7 == 0) goto L_0x03a2
                r0 = r44
                java.lang.String r7 = r0.reqUrl     // Catch:{ Exception -> 0x00ca }
                r0 = r23
                com.alipay.mobile.nebula.util.H5CookieUtil.setCookie(r7, r0)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r7 = "H5UploadPlugin"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ca }
                java.lang.String r9 = "insert cookie:"
                r8.<init>(r9)     // Catch:{ Exception -> 0x00ca }
                r0 = r23
                java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r9 = " , for "
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                java.lang.String r9 = r0.reqUrl     // Catch:{ Exception -> 0x00ca }
                java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.util.H5Log.d(r7, r8)     // Catch:{ Exception -> 0x00ca }
            L_0x03a2:
                int r5 = r5 + 1
                goto L_0x033e
            L_0x03a5:
                r19 = 0
                java.io.InputStream r40 = r24.getInputStream()     // Catch:{ Exception -> 0x00ca }
                if (r18 == 0) goto L_0x03b6
                java.util.zip.GZIPInputStream r19 = new java.util.zip.GZIPInputStream     // Catch:{ Exception -> 0x00ca }
                r0 = r19
                r1 = r40
                r0.<init>(r1)     // Catch:{ Exception -> 0x00ca }
            L_0x03b6:
                if (r19 == 0) goto L_0x03d7
                r33 = r19
            L_0x03ba:
                r5 = 1024(0x400, float:1.435E-42)
                byte[] r12 = new byte[r5]     // Catch:{ Exception -> 0x00ca }
                java.io.ByteArrayOutputStream r30 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00ca }
                r30.<init>()     // Catch:{ Exception -> 0x00ca }
            L_0x03c3:
                r0 = r33
                int r39 = r0.read(r12)     // Catch:{ Exception -> 0x00ca }
                r5 = -1
                r0 = r39
                if (r0 == r5) goto L_0x03da
                r5 = 0
                r0 = r30
                r1 = r39
                r0.write(r12, r5, r1)     // Catch:{ Exception -> 0x00ca }
                goto L_0x03c3
            L_0x03d7:
                r33 = r40
                goto L_0x03ba
            L_0x03da:
                byte[] r36 = r30.toByteArray()     // Catch:{ Exception -> 0x00ca }
                if (r36 == 0) goto L_0x042b
                java.lang.String r35 = new java.lang.String     // Catch:{ Exception -> 0x00ca }
                r35.<init>(r36)     // Catch:{ Exception -> 0x00ca }
                com.alibaba.fastjson.JSONObject r27 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x00ca }
                r27.<init>()     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "statusCode"
                int r6 = r24.getCode()     // Catch:{ Exception -> 0x00ca }
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r27
                r0.put(r5, r6)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "data"
                r0 = r27
                r1 = r35
                r0.put(r5, r1)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "header"
                r0 = r27
                r1 = r37
                r0.put(r5, r1)     // Catch:{ Exception -> 0x00ca }
                java.lang.String r5 = "success"
                r6 = 1
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r27
                r0.put(r5, r6)     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.alipay.mobile.h5container.api.H5BridgeContext r5 = r0.context     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x042b
                r5 = 1
                r0 = r44
                r0.hasSend = r5     // Catch:{ Exception -> 0x00ca }
                r0 = r44
                com.alipay.mobile.h5container.api.H5BridgeContext r5 = r0.context     // Catch:{ Exception -> 0x00ca }
                r0 = r27
                r5.sendBridgeResult(r0)     // Catch:{ Exception -> 0x00ca }
            L_0x042b:
                boolean r5 = android.text.TextUtils.isEmpty(r29)     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x0434
                com.alipay.mobile.nebula.util.H5FileUtil.delete(r29)     // Catch:{ Exception -> 0x00ca }
            L_0x0434:
                r0 = r44
                java.lang.String r5 = r0.filePath     // Catch:{ Exception -> 0x00ca }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x045b
                r0 = r44
                java.lang.String r5 = r0.localId     // Catch:{ Exception -> 0x00ca }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x00ca }
                if (r5 != 0) goto L_0x045b
                r0 = r44
                java.lang.String r5 = r0.uploadType     // Catch:{ Exception -> 0x00ca }
                java.lang.String r6 = "audio"
                boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x00ca }
                if (r5 == 0) goto L_0x045b
                r0 = r44
                java.lang.String r5 = r0.filePath     // Catch:{ Exception -> 0x00ca }
                com.alipay.mobile.nebula.util.H5FileUtil.delete(r5)     // Catch:{ Exception -> 0x00ca }
            L_0x045b:
                r0 = r44
                boolean r5 = r0.hasSend
                if (r5 != 0) goto L_0x0473
                r5 = 1
                r0 = r44
                r0.hasSend = r5
                r0 = r44
                com.mpaas.nebula.plugin.H5UploadPlugin r5 = com.mpaas.nebula.plugin.H5UploadPlugin.this
                r0 = r44
                com.alipay.mobile.h5container.api.H5BridgeContext r6 = r0.context
                java.lang.String r7 = "网络异常"
                com.mpaas.nebula.plugin.H5UploadPlugin.a(r6, r7)
            L_0x0473:
                r0 = r44
                com.alipay.mobile.antui.dialog.AUProgressDialog r5 = r0.apGenericProgressDialog
                if (r5 == 0) goto L_0x013a
                com.mpaas.nebula.plugin.H5UploadPlugin$UploadFile$1 r5 = new com.mpaas.nebula.plugin.H5UploadPlugin$UploadFile$1
                r0 = r44
                r5.<init>()
                com.alipay.mobile.nebula.util.H5Utils.runOnMain(r5)
                goto L_0x013a
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mpaas.nebula.plugin.H5UploadPlugin.UploadFile.run():void");
        }
    }

    private class UploadFileHttpConnect implements Runnable {
        AUProgressDialog apGenericProgressDialog;
        byte[] bytes;
        H5BridgeContext context;
        String filePath;
        JSONObject fromData;
        JSONObject headers;
        String localId;
        String name;
        String reqUrl;
        String uploadType;

        public UploadFileHttpConnect(String filePath2, String name2, String reqUrl2, JSONObject headers2, JSONObject fromData2, H5BridgeContext context2, byte[] bytes2, String localId2, String uploadType2, AUProgressDialog apGenericProgressDialog2) {
            this.filePath = filePath2;
            this.name = name2;
            this.reqUrl = reqUrl2;
            this.headers = headers2;
            this.fromData = fromData2;
            this.context = context2;
            this.bytes = bytes2;
            this.localId = localId2;
            this.uploadType = uploadType2;
            this.apGenericProgressDialog = apGenericProgressDialog2;
        }

        public void run() {
            File file;
            String localIdPath = null;
            try {
                if (this.bytes != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.bytes);
                    localIdPath = H5DownloadRequest.getDefaultDownloadDir(H5Utils.getContext()) + "/" + this.localId + ".jpg";
                    H5FileUtil.copyToFile((InputStream) byteArrayInputStream, new File(localIdPath));
                    file = new File(localIdPath);
                } else {
                    file = new File(this.filePath);
                }
                H5Log.d(H5UploadPlugin.TAG, "file " + file.getAbsolutePath());
                StringBuilder sb = new StringBuilder();
                if (this.fromData != null && !this.fromData.isEmpty()) {
                    for (String key : this.fromData.keySet()) {
                        if (this.fromData.get(key) != null) {
                            String value = this.fromData.get(key).toString();
                            sb.append("------WebKitFormBoundaryT1HoybnYeFOGFlBR\r\n");
                            sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
                            sb.append(MultipartUtility.LINE_FEED);
                            sb.append(value + MultipartUtility.LINE_FEED);
                        }
                    }
                }
                sb.append("------WebKitFormBoundaryT1HoybnYeFOGFlBR\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + this.name + "\"; filename=\"" + file.getName() + "\"\r\n");
                if (TextUtils.equals(this.uploadType, "video")) {
                    sb.append("Content-Type: video/mp4;\r\n");
                }
                sb.append(MultipartUtility.LINE_FEED);
                byte[] headerInfo = sb.toString().getBytes("UTF-8");
                byte[] endInfo = "\r\n------WebKitFormBoundaryT1HoybnYeFOGFlBR--\r\n".getBytes("UTF-8");
                HttpURLConnection conn = (HttpURLConnection) new URL(this.reqUrl).openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryT1HoybnYeFOGFlBR");
                conn.setRequestProperty("Content-Length", String.valueOf(((long) headerInfo.length) + file.length() + ((long) endInfo.length)));
                if (this.headers != null && !this.headers.isEmpty()) {
                    for (String key2 : this.headers.keySet()) {
                        conn.setRequestProperty(key2, this.headers.get(key2).toString());
                    }
                }
                conn.setConnectTimeout(60000);
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                if (!(H5UploadPlugin.this.a == null || H5UploadPlugin.this.a.getWebView() == null || H5UploadPlugin.this.a.getWebView().getSettings() == null)) {
                    conn.setRequestProperty(MtopJSParam.USER_AGENT, H5UploadPlugin.this.a.getWebView().getSettings().getUserAgentString());
                }
                conn.setDoOutput(true);
                String cookieStr = H5CookieUtil.getCookie(this.reqUrl);
                if (!TextUtils.isEmpty(cookieStr)) {
                    conn.setRequestProperty("Cookie", cookieStr);
                    H5Log.d(H5UploadPlugin.TAG, "in UploadFileHttpConnect, add cookie:" + cookieStr + " , for conn");
                }
                OutputStream out = conn.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(file);
                out.write(headerInfo);
                byte[] buf = new byte[1024];
                while (true) {
                    int len = fileInputStream.read(buf);
                    if (len == -1) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
                out.write(endInfo);
                out.flush();
                fileInputStream.close();
                out.close();
                H5Log.d(H5UploadPlugin.TAG, conn.getResponseCode() + Token.SEPARATOR + conn.getResponseMessage());
                String result = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    result = result + line;
                }
                Map map = conn.getHeaderFields();
                JSONObject key3 = new JSONObject();
                if (map != null && !map.isEmpty()) {
                    for (Entry entry : map.entrySet()) {
                        String entryKey = (String) entry.getKey();
                        if (entry.getKey() != null) {
                            String value2 = "";
                            H5Log.d(H5UploadPlugin.TAG, "Key : " + entryKey + " ,Value : " + entry.getValue());
                            for (String s : (List) entry.getValue()) {
                                if (TextUtils.isEmpty(value2)) {
                                    value2 = s;
                                } else {
                                    value2 = value2 + ", " + s;
                                }
                            }
                            key3.put((String) entry.getKey(), (Object) value2);
                            if (((String) entry.getKey()).equalsIgnoreCase("set-cookie")) {
                                H5CookieUtil.setCookie(this.reqUrl, value2);
                                H5Log.d(H5UploadPlugin.TAG, "in UploadFileHttpConnect, insert cookie:" + value2 + " , for " + this.reqUrl);
                            }
                        }
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "statusCode", (Object) Integer.valueOf(conn.getResponseCode()));
                jsonObject.put((String) "data", (Object) result);
                jsonObject.put((String) Performance.KEY_LOG_HEADER, (Object) key3);
                jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
                if (this.context != null) {
                    this.context.sendBridgeResult(jsonObject);
                }
                if (!TextUtils.isEmpty(localIdPath)) {
                    H5FileUtil.delete(localIdPath);
                }
                if (!TextUtils.isEmpty(this.filePath) && !TextUtils.isEmpty(this.localId) && this.uploadType.equals("audio")) {
                    H5FileUtil.delete(this.filePath);
                }
                conn.disconnect();
                if (this.apGenericProgressDialog != null) {
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            try {
                                UploadFileHttpConnect.this.apGenericProgressDialog.dismiss();
                            } catch (Throwable throwable) {
                                H5Log.e((String) H5UploadPlugin.TAG, throwable);
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                if (this.apGenericProgressDialog != null) {
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            try {
                                UploadFileHttpConnect.this.apGenericProgressDialog.dismiss();
                            } catch (Throwable throwable) {
                                H5Log.e((String) H5UploadPlugin.TAG, throwable);
                            }
                        }
                    });
                }
                throw th;
            }
        }
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction("upload");
        h5EventFilter.addAction("uploadFile");
    }

    public void onRelease() {
        this.a = null;
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        String action = h5Event.getAction();
        if (!"upload".equals(action) && "uploadFile".equals(action)) {
            a(h5Event, h5BridgeContext);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext context) {
        if (event.getTarget() instanceof H5Page) {
            this.a = (H5Page) event.getTarget();
        }
        JSONObject jsonObject = event.getParam();
        String url = H5Utils.getString(jsonObject, (String) "url");
        String filePath = H5Utils.getString(jsonObject, (String) "filePath");
        String name = H5Utils.getString(jsonObject, (String) "name");
        String localId = H5Utils.getString(jsonObject, (String) "localId");
        String type = H5Utils.getString(jsonObject, (String) "type");
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(name)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        if (!TextUtils.isEmpty(filePath)) {
            filePath = NebulaBiz.getLocalPathFromId(filePath);
        }
        if (!TextUtils.isEmpty(filePath) && filePath.startsWith("file://")) {
            filePath = filePath.replaceAll("file://", "");
        }
        try {
            if (H5Utils.getContext().getFilesDir() != null && filePath.contains(H5Utils.getContext().getFilesDir().getParent())) {
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null && BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_uploadFile_dataPath"))) {
                    context.sendError(11, "can not upload " + filePath);
                    H5LogProvider h5LogProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
                    if (h5LogProvider != null) {
                        h5LogProvider.log("H5_uploadFie_useDataPath", url, filePath, this.a.getUrl(), null);
                        return;
                    }
                    return;
                }
            }
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
        JSONObject headers = H5Utils.getJSONObject(jsonObject, Performance.KEY_LOG_HEADER, null);
        JSONObject fromData = H5Utils.getJSONObject(jsonObject, "formData", null);
        if (TextUtils.isEmpty(filePath)) {
            if (TextUtils.isEmpty(localId)) {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            } else if (TextUtils.equals(type, "video")) {
                filePath = NebulaBiz.getVideoPath(localId);
            } else if (TextUtils.equals(type, "audio")) {
                uploadFile(NebulaBiz.getAudioPath(localId), name, url, headers, fromData, context, null, localId, type);
                return;
            } else if (TextUtils.equals(type, "image")) {
                final String str = name;
                final String str2 = url;
                final JSONObject jSONObject = headers;
                final JSONObject jSONObject2 = fromData;
                final H5BridgeContext h5BridgeContext = context;
                final String str3 = localId;
                final String str4 = type;
                NebulaBiz.getImageData(localId, new H5ImageByteListener() {
                    public void onImageByte(byte[] bytes) {
                        H5UploadPlugin.this.uploadFile(null, str, str2, jSONObject, jSONObject2, h5BridgeContext, bytes, str3, str4);
                    }
                });
                return;
            } else {
                context.sendError(event, Error.INVALID_PARAM);
                return;
            }
        }
        uploadFile(filePath, name, url, headers, fromData, context, null, null, type);
    }

    public void uploadFile(String filePath, String name, String reqUrl, JSONObject headers, JSONObject fromData, H5BridgeContext context, byte[] bytes, String localId, String uploadType) {
        AUProgressDialog apGenericProgressDialog = null;
        if (!(this.a == null || this.a.getContext() == null || this.a.getContext().getContext() == null)) {
            apGenericProgressDialog = new AUProgressDialog(this.a.getContext().getContext());
            apGenericProgressDialog.setCancelable(true);
            apGenericProgressDialog.setCanceledOnTouchOutside(false);
            apGenericProgressDialog.setMessage("正在上传");
            apGenericProgressDialog.setProgressVisiable(true);
            apGenericProgressDialog.show();
        }
        if (a()) {
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new UploadFileHttpConnect(filePath, name, reqUrl, headers, fromData, context, bytes, localId, uploadType, apGenericProgressDialog));
        } else {
            H5Utils.getExecutor(H5ThreadType.URGENT).execute(new UploadFile(filePath, name, reqUrl, headers, fromData, context, bytes, localId, uploadType, apGenericProgressDialog));
        }
    }

    private static boolean a() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !"yes".equalsIgnoreCase(h5ConfigProvider.getConfig("H5_uploadFile_useHttpConnect"))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void a(H5BridgeContext context, String errorMessage) {
        if (context != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "error", (Object) Integer.valueOf(12));
            if (errorMessage != null) {
                jsonObject.put((String) "errorMessage", (Object) errorMessage);
            }
            context.sendBridgeResult(jsonObject);
        }
    }
}
