package com.alipay.mobile.common.transport.httpdns.downloader;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.common.netsdkextdependapi.security.SecurityUtil;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class HttpClient {
    private int a;
    private boolean b;
    private boolean c;
    HttpURLConnection conn;
    public int conntimeout;
    public int requesttimeout;

    public HttpClient(Context ctx) {
        this.conn = null;
        this.requesttimeout = HttpConstants.CONNECTION_TIME_OUT;
        this.conntimeout = HttpConstants.CONNECTION_TIME_OUT;
        this.b = true;
        this.c = true;
        this.a = 0;
        this.c = false;
    }

    public int getResponseCode() {
        return this.a;
    }

    public HttpURLConnection getConnection(URL url) {
        HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
        if (conn2 == null) {
            return null;
        }
        conn2.setInstanceFollowRedirects(false);
        conn2.setRequestMethod("POST");
        conn2.setConnectTimeout(this.conntimeout);
        conn2.setReadTimeout(this.requesttimeout);
        conn2.setRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, DataflowMonitorModel.METHOD_NAME_CLOSE);
        conn2.setRequestProperty("Accept-Encoding", "gzip");
        conn2.setRequestProperty("Content-Type", "text/json");
        conn2.setRequestProperty(H5AppHttpRequest.HEADER_UA, "A");
        if (!MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            conn2.setRequestProperty("Host", DnsUtil.getAmdcHost());
        }
        conn2.setDoOutput(true);
        return conn2;
    }

    private void a(StrategyRequest request) {
        boolean z;
        boolean useGzip;
        String requestContent = StrategyItemParser.generateStrategyReq(request);
        if (!TextUtils.isEmpty(requestContent)) {
            a(requestContent);
            byte[] requestBytes = requestContent.getBytes("UTF-8");
            if (this.c) {
                this.conn.setRequestProperty("X-appid", "mwallet");
                LogCatUtil.debug("HTTP_DNS_HClient", "Configured appid in header");
            }
            if (!this.c) {
                z = true;
            } else {
                z = false;
            }
            byte[] gZippedContent = a(requestBytes, z);
            if (gZippedContent != null) {
                useGzip = true;
            } else {
                useGzip = false;
            }
            if (this.c) {
                byte[] encrytedContent = b(gZippedContent);
                if (encrytedContent != null) {
                    LogCatUtil.debug("HTTP_DNS_HClient", "Prepared cooked data, size:" + encrytedContent.length);
                    this.conn.getOutputStream().write(encrytedContent);
                    this.conn.getOutputStream().flush();
                    return;
                }
                LogCatUtil.debug("HTTP_DNS_HClient", "Cooking failed");
            } else if (useGzip) {
                this.conn.setRequestProperty(TransportConstants.KEY_X_CONTENT_ENCODING, "gzip");
                this.conn.getOutputStream().write(gZippedContent);
                this.conn.getOutputStream().flush();
            } else {
                OutputStreamWriter writer = new OutputStreamWriter(this.conn.getOutputStream());
                writer.write(requestContent);
                writer.flush();
                writer.close();
            }
        }
    }

    public StrategyResponse getStrategyFromServer(String urlStr, StrategyRequest request) {
        this.a = 0;
        try {
            this.conn = getConnection(new URL(urlStr));
            if (this.conn != null) {
                a(request);
                String result = a();
                if (TextUtils.isEmpty(result)) {
                    throw new Exception("response is null");
                }
                StrategyResponse response = StrategyItemParser.parseStrategyContent(result);
                if (response != null) {
                    return response;
                }
                throw new Exception("parse exception");
            }
            throw new Exception("Conn_Failed_to_Create");
        } catch (Exception ex) {
            LogCatUtil.debug("HTTP_DNS_HClient", "General error: " + ex.toString());
            throw ex;
        }
    }

    private String a() {
        String result;
        int i;
        this.a = this.conn.getResponseCode();
        LogCatUtil.debug("HTTP_DNS_HClient", "responseCode : " + this.a);
        if (this.a != 200) {
            Map responseHeader = this.conn.getHeaderFields();
            if (responseHeader != null) {
                for (Entry entry : responseHeader.entrySet()) {
                    LogCatUtil.debug("HTTP_DNS_HClient", "key:" + ((String) entry.getKey()) + ",value:" + ((String) ((List) entry.getValue()).get(0)));
                }
            }
            throw new Exception("resCode:" + this.a + " invalid");
        }
        String contentEncoding = this.conn.getContentEncoding();
        InputStream inStream = this.conn.getInputStream();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = inStream.read(buffer);
            if (len == -1) {
                break;
            }
            arrayOutputStream.write(buffer, 0, len);
        }
        byte[] serverRespData = arrayOutputStream.toByteArray();
        byte[] gZippedData = null;
        if (this.c) {
            gZippedData = c(serverRespData);
            StringBuilder sb = new StringBuilder("Got Zipped data, size:");
            if (gZippedData != null) {
                i = gZippedData.length;
            } else {
                i = 0;
            }
            LogCatUtil.verbose("HTTP_DNS_HClient", sb.append(i).toString());
        }
        if (this.c) {
            result = new String(a(gZippedData), "utf-8");
        } else if (contentEncoding == null || contentEncoding.indexOf("gzip") == -1) {
            LogCatUtil.debug("HTTP_DNS_HClient", "Received no zip data, size:" + serverRespData.length);
            result = new String(serverRespData, "utf-8");
        } else {
            result = new String(a(serverRespData), "utf-8");
        }
        String result2 = result.trim();
        arrayOutputStream.close();
        inStream.close();
        b(result2);
        return result2;
    }

    private byte[] a(byte[] requestBytes, boolean smartMode) {
        if (!this.b) {
            return null;
        }
        ByteArrayOutputStream zippedCountingOS = new ByteArrayOutputStream(1024);
        OutputStream tempout = new GZIPOutputStream(zippedCountingOS) {
            {
                this.def.setLevel(9);
            }
        };
        tempout.write(requestBytes);
        tempout.flush();
        tempout.close();
        LogCatUtil.debug("HTTP_DNS_HClient", "Gzip Pre-check, compressed size =" + zippedCountingOS.size() + ", origsize =" + requestBytes.length);
        byte[] result = zippedCountingOS.toByteArray();
        zippedCountingOS.close();
        if (!smartMode || zippedCountingOS.size() + 24 < requestBytes.length) {
            return result;
        }
        return null;
    }

    private byte[] a(byte[] zippedData) {
        byte[] result = null;
        if (this.b) {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(zippedData);
            GZIPInputStream unzipStream = new GZIPInputStream(arrayInputStream);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte[] deCryptBuffer = new byte[1024];
            while (true) {
                int lenBuffer = unzipStream.read(deCryptBuffer);
                if (lenBuffer == -1) {
                    break;
                }
                arrayOutputStream.write(deCryptBuffer, 0, lenBuffer);
            }
            result = arrayOutputStream.toByteArray();
            if (result != null) {
                LogCatUtil.debug("HTTP_DNS_HClient", "Unzipped data, compressed size" + zippedData.length + ", origsize =" + result.length);
            }
            arrayOutputStream.close();
            unzipStream.close();
            arrayInputStream.close();
        }
        return result;
    }

    private static byte[] b(byte[] originalData) {
        if (originalData == null) {
            LogCatUtil.debug("HTTP_DNS_HClient", "Original Data is empty, can't proceed");
            return null;
        }
        try {
            byte[] encryptData = SecurityUtil.encrypt(originalData, "sync-data-aes128");
            LogCatUtil.verbose("HTTP_DNS_HClient", "proceed result:\n" + Base64.encodeToString(encryptData, 0));
            return encryptData;
        } catch (Exception ex) {
            LogCatUtil.debug("HTTP_DNS_HClient", "Failed to encode data, err:" + ex.toString());
            throw ex;
        }
    }

    private static byte[] c(byte[] encryptedData) {
        if (encryptedData == null) {
            LogCatUtil.debug("HTTP_DNS_HClient", "Original Cooked Data is empty, can't proceed");
            return null;
        }
        try {
            byte[] decryptedData = SecurityUtil.decrypt(encryptedData, "sync-data-aes128");
            LogCatUtil.verbose("HTTP_DNS_HClient", "proceed result:\n" + Base64.encodeToString(decryptedData, 0));
            return decryptedData;
        } catch (Exception ex) {
            LogCatUtil.debug("HTTP_DNS_HClient", "Failed to decode data, err:" + ex.toString());
            throw ex;
        }
    }

    private void a(String requestContent) {
        try {
            if (DnsUtil.isUseSign()) {
                String timeStampStr = String.valueOf(System.currentTimeMillis());
                String signData = DnsUtil.getSignData(this.conn.getURL().toString(), requestContent, timeStampStr, DnsUtil.getAppId());
                this.conn.setRequestProperty("x-amdc-appid", DnsUtil.getAppId());
                this.conn.setRequestProperty("x-amdc-ts", timeStampStr);
                this.conn.setRequestProperty("x-amdc-sign", signData);
                if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
                    for (Entry entry : this.conn.getRequestProperties().entrySet()) {
                        LogCatUtil.debug("HTTP_DNS_HClient", "key:" + ((String) entry.getKey()) + ",value:" + ((String) ((List) entry.getValue()).get(0)));
                    }
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "HTTP_DNS_HClient", "addSignParams ex:" + ex.toString());
        }
    }

    private void b(String result) {
        if (DnsUtil.isUseSign()) {
            if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
                for (Entry entry : this.conn.getHeaderFields().entrySet()) {
                    LogCatUtil.debug("HTTP_DNS_HClient", "key:" + ((String) entry.getKey()) + ",value:" + ((String) ((List) entry.getValue()).get(0)));
                }
            }
            String amdcRespSign = this.conn.getHeaderField("x-amdc-sign");
            String amdcTs = this.conn.getHeaderField("x-amdc-ts");
            String amdcCode = this.conn.getHeaderField("x-amdc-code");
            if (TextUtils.equals(amdcCode, "7000") || TextUtils.equals(amdcCode, "7001") || TextUtils.equals(amdcCode, "7002")) {
                throw new Exception("x-amdc-code:" + amdcCode + ",server signature verify fail");
            }
            String localSign = DnsUtil.getSignData(this.conn.getURL().toString(), result, amdcTs, DnsUtil.getAppId());
            LogCatUtil.debug("HTTP_DNS_HClient", "amdcRespSign:" + amdcRespSign + ",localSign:" + localSign);
            if (TextUtils.isEmpty(amdcRespSign) || TextUtils.isEmpty(amdcTs)) {
                throw new Exception("8001,client signature verify fail");
            } else if (!TextUtils.equals(amdcRespSign, localSign)) {
                throw new Exception("8002,client signature verify fail");
            }
        }
    }
}
