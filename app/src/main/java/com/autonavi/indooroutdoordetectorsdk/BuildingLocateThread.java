package com.autonavi.indooroutdoordetectorsdk;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import com.alipay.android.phone.a.a.a;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.indoor.entity.ScanData;
import com.autonavi.indoor.entity.ScanPair;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MapUtils;
import com.autonavi.indoor.util.PackageHelper;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.json.JSONObject;

public class BuildingLocateThread extends Thread {
    static final short ACTIONGCODE_ONLINE_V5 = 17;
    public static Request SHUTDOWN_REQ = new Request("SHUTDOWN", null);
    public final Context context;
    boolean hasBeenStarted = false;
    boolean isPdrEnable = true;
    private Handler mHandler = null;
    private BlockingQueue<Request> requestToPost = new ArrayBlockingQueue(1);
    private volatile boolean shuttingDown;
    private volatile boolean terminated;

    static class Request {
        ScanData scanData;
        public String url;

        public Request(String str, ScanData scanData2) {
            this.url = str;
            this.scanData = scanData2;
        }
    }

    public BuildingLocateThread(Handler handler, boolean z, Context context2) {
        start();
        this.mHandler = handler;
        this.isPdrEnable = z;
        this.context = context2;
    }

    public void locate(String str, ScanData scanData) {
        if (this.shuttingDown || this.terminated) {
            if (L.isLogging) {
                L.d((String) "BuildingLocateThread has been stoped.");
            }
            return;
        }
        Request request = new Request(str, scanData);
        while (!this.requestToPost.offer(request)) {
            if (L.isLogging) {
                L.d((String) "queen is full, poll one");
            }
            this.requestToPost.poll();
        }
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("add a request:");
            sb.append(this.requestToPost.size());
            L.d(sb.toString());
        }
    }

    public void shutDown() throws InterruptedException {
        if (this.shuttingDown) {
            if (L.isLogging) {
                L.d((String) "Thread is shuttingDown...");
            }
            return;
        }
        this.shuttingDown = true;
        this.hasBeenStarted = false;
        while (!this.requestToPost.offer(SHUTDOWN_REQ)) {
            if (L.isLogging) {
                L.d((String) "queen is full, poll one");
            }
            this.requestToPost.poll();
        }
        if (L.isLogging) {
            L.d((String) "stop BuildingLocateThread");
        }
    }

    public void run() {
        while (true) {
            try {
                if (this.shuttingDown) {
                    if (L.isLogging) {
                        L.d((String) "Thread is shuttingDown.");
                    }
                    this.requestToPost.clear();
                } else {
                    Request take = this.requestToPost.take();
                    if (take != SHUTDOWN_REQ) {
                        consumRequest(take);
                    } else if (L.isLogging) {
                        L.d((String) "consum poison pill");
                    }
                }
            } catch (InterruptedException unused) {
                return;
            } finally {
                this.terminated = true;
            }
        }
        if (L.isLogging) {
            L.d((String) "end of run");
        }
        this.mHandler = null;
    }

    /* access modifiers changed from: 0000 */
    public void consumRequest(Request request) {
        HttpURLConnection httpURLConnection;
        long currentTimeMillis = System.currentTimeMillis();
        byte[] encodeLocateRequest = encodeLocateRequest(request.scanData);
        if (encodeLocateRequest != null) {
            byte[] compressBody = compressBody(encodeLocateRequest);
            String str = request.url;
            byte[] amapEncode = MapUtils.amapEncode(compressBody);
            if (amapEncode != null) {
                if (L.isLogging) {
                    L.d((String) "Request encryptData is null");
                }
                compressBody = amapEncode;
            }
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("Request ");
                sb.append(str);
                sb.append(", buffer.length=");
                sb.append(compressBody.length);
                L.d(sb.toString());
            }
            int i = 0;
            if (L.isLogging) {
                L.d("Request body".concat(String.valueOf(Base64.encodeToString(compressBody, 0))));
            }
            if (!this.shuttingDown) {
                try {
                    if (L.isLogging) {
                        StringBuilder sb2 = new StringBuilder("consumRequest ");
                        sb2.append(currentTimeMillis);
                        sb2.append(" Request ");
                        sb2.append(str);
                        sb2.append(", buffer.length=");
                        sb2.append(compressBody.length);
                        L.d(sb2.toString());
                    }
                    httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                    try {
                        if (L.isLogging) {
                            L.d((String) "发送网络请求...");
                        }
                        httpURLConnection.setReadTimeout(10000);
                        httpURLConnection.setConnectTimeout(10000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setUseCaches(false);
                        httpURLConnection.setInstanceFollowRedirects(true);
                        httpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
                        httpURLConnection.setRequestProperty("Charset", "UTF-8");
                        httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        outputStream.write(compressBody);
                        outputStream.close();
                        httpURLConnection.connect();
                        int responseCode = httpURLConnection.getResponseCode();
                        if (responseCode != 200) {
                            httpURLConnection.disconnect();
                            sendEmptyMessage2parent(MessageCode.MSG_SERVER_ERROR);
                            if (L.isLogging) {
                                L.d("服务器建立连接失败，返回值错误code：".concat(String.valueOf(responseCode)));
                            }
                            Map headerFields = httpURLConnection.getHeaderFields();
                            if (L.isLogging) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(headerFields.toString());
                                sb3.append(", ");
                                sb3.append(headerFields.size());
                                L.d(sb3.toString());
                            }
                            for (String str2 : headerFields.keySet()) {
                                List<String> list = (List) headerFields.get(str2);
                                if (L.isLogging) {
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(list.toString());
                                    sb4.append(", ");
                                    sb4.append(list.size());
                                    L.d(sb4.toString());
                                }
                                for (String str3 : list) {
                                    if (L.isLogging) {
                                        StringBuilder sb5 = new StringBuilder();
                                        sb5.append(str2);
                                        sb5.append(": ");
                                        sb5.append(str3);
                                        L.d(sb5.toString());
                                    }
                                }
                            }
                            httpURLConnection.disconnect();
                            return;
                        }
                        String contentType = httpURLConnection.getContentType();
                        int contentLength = httpURLConnection.getContentLength();
                        if (contentLength <= 0) {
                            contentLength = 2097152;
                            if (L.isLogging) {
                                L.d(httpURLConnection.getResponseMessage());
                            }
                        }
                        byte[] bArr = new byte[contentLength];
                        InputStream inputStream = httpURLConnection.getInputStream();
                        while (i < contentLength) {
                            int read = inputStream.read(bArr, i, contentLength - i);
                            if (read <= 0) {
                                break;
                            }
                            i += read;
                        }
                        byte[] copyOf = MapUtils.copyOf(bArr, i);
                        inputStream.close();
                        if (L.isLogging) {
                            StringBuilder sb6 = new StringBuilder("Response ");
                            sb6.append(str);
                            sb6.append(", buffer[1]=");
                            sb6.append(compressBody[1]);
                            sb6.append(", code=");
                            sb6.append(responseCode);
                            sb6.append(", length=");
                            sb6.append(copyOf.length);
                            sb6.append(", len=");
                            sb6.append(contentLength);
                            sb6.append(", contentLength=");
                            sb6.append(httpURLConnection.getContentLength());
                            sb6.append(", ");
                            sb6.append(httpURLConnection.getResponseMessage());
                            sb6.append(", ");
                            sb6.append(contentType);
                            L.d(sb6.toString());
                        }
                        if (contentType.startsWith("application/json")) {
                            String str4 = new String(copyOf);
                            if (L.isLogging) {
                                L.d("ServerError:".concat(String.valueOf(str4)));
                            }
                            JSONObject jSONObject = new JSONObject(str4);
                            if (jSONObject.has("status")) {
                                String string = jSONObject.getString("status");
                                if (L.isLogging) {
                                    L.d("json status:".concat(String.valueOf(string)));
                                }
                                String string2 = jSONObject.getString("info");
                                if (L.isLogging) {
                                    L.d("json info:".concat(String.valueOf(string2)));
                                }
                                int i2 = MessageCode.MSG_LBS_ERROR;
                                if (string2.equals("OK")) {
                                    if (L.isLogging) {
                                        L.d((String) "正常");
                                    }
                                } else if (string2.equals("INVALID_USER_KEY")) {
                                    i2 = MessageCode.MSG_LBS_INVALID_USER_KEY;
                                    if (L.isLogging) {
                                        L.d((String) "用户key非法或过期");
                                    }
                                } else if (string2.equals("SERVICE_NOT_AVAILBALE")) {
                                    i2 = MessageCode.MSG_LBS_SERVICE_NOT_AVAILBALE;
                                    if (L.isLogging) {
                                        L.d((String) "请求服务不存在");
                                    }
                                } else if (string2.equals("SERVICE_RESPONSE_ERROR")) {
                                    i2 = MessageCode.MSG_LBS_SERVICE_RESPONSE_ERROR;
                                    if (L.isLogging) {
                                        L.d((String) "请求服务响应错误");
                                    }
                                } else if (string2.equals("INSUFFICIENT_PRIVILEGES")) {
                                    i2 = MessageCode.MSG_LBS_INSUFFICIENT_PRIVILEGES;
                                    if (L.isLogging) {
                                        L.d((String) "无权限访问此服务");
                                    }
                                } else if (string2.equals("OVER_QUOTA")) {
                                    i2 = MessageCode.MSG_LBS_OVER_QUOTA;
                                    if (L.isLogging) {
                                        L.d((String) "请求超出配额");
                                    }
                                } else if (string2.equals("INVALID_PARAMS")) {
                                    i2 = MessageCode.MSG_LBS_INVALID_PARAMS;
                                    if (L.isLogging) {
                                        L.d((String) "请求参数非法");
                                    }
                                } else if (string2.equals("UNKNOWN_ERROR")) {
                                    i2 = 720;
                                    if (L.isLogging) {
                                        L.d((String) "未知错误");
                                    }
                                }
                                sendEmptyMessage2parent(i2);
                            } else if (jSONObject.has("result")) {
                                String string3 = jSONObject.getString("result");
                                if (L.isLogging) {
                                    L.d("json result:".concat(String.valueOf(string3)));
                                }
                                if (string3.equals("false")) {
                                    sendMessage2parent(MessageCode.MSG_SERVER_ERROR, jSONObject.getString("message"));
                                }
                            }
                            if (L.isLogging) {
                                L.d(httpURLConnection.getContent().toString());
                            }
                        } else {
                            BuildingLocationResult decodeLocateResponse = decodeLocateResponse(copyOf);
                            if (decodeLocateResponse != null) {
                                if (L.isLogging) {
                                    L.d((Object) decodeLocateResponse);
                                }
                                sendMessage2parent(208, decodeLocateResponse);
                            } else {
                                if (L.isLogging) {
                                    L.d(copyOf);
                                }
                                sendMessage2parent(MessageCode.MSG_SERVER_ERROR, "DecodeLocateResponse failed");
                            }
                        }
                        httpURLConnection.disconnect();
                    } catch (Throwable th) {
                        if (L.isLogging) {
                            StringBuilder sb7 = new StringBuilder("postRequest异常：");
                            sb7.append(th);
                            sb7.append(", url=");
                            sb7.append(str);
                            L.d(sb7.toString());
                        }
                        if (L.isLogging) {
                            L.d(th);
                        }
                        sendEmptyMessage2parent(212);
                        httpURLConnection.disconnect();
                    }
                } catch (Throwable th2) {
                    if (L.isLogging) {
                        L.d("打开连接异常：".concat(String.valueOf(th2)));
                    }
                    sendEmptyMessage2parent(212);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isValidHandler(Handler handler) {
        return (handler == null || handler.getLooper() == null || handler.getLooper().getThread() == null || !handler.getLooper().getThread().isAlive()) ? false : true;
    }

    static byte[] compressBody(byte[] bArr) {
        try {
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            byte[] bArr2 = new byte[(bArr.length - 6)];
            short s = wrap.getShort();
            int i = wrap.getInt();
            wrap.get(bArr2);
            byte[] jniCompress = JNIWrapper.jniCompress(bArr2);
            byte[] bArr3 = new byte[(jniCompress.length + 6)];
            try {
                ByteBuffer wrap2 = ByteBuffer.wrap(bArr3);
                wrap2.putShort((short) s);
                wrap2.putInt(i + 2);
                wrap2.put(jniCompress);
                return bArr3;
            } catch (Throwable unused) {
                return bArr3;
            }
        } catch (Throwable unused2) {
            return bArr;
        }
    }

    /* access modifiers changed from: 0000 */
    public byte[] encodeLocateRequest(ScanData scanData) {
        int i = scanData.type_ == 0 ? 1 : 2;
        if (L.isLogging) {
            L.d("locationType:".concat(String.valueOf(i)));
        }
        long j = scanData.time_;
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.putShort(ACTIONGCODE_ONLINE_V5);
        allocate.putInt(1);
        ByteBuffer allocate2 = ByteBuffer.allocate(10240);
        try {
            MapUtils.put(allocate2, "");
            allocate2.put((byte) i);
            MapUtils.put(allocate2, "IOD");
            MapUtils.put(allocate2, "6.9");
            MapUtils.put(allocate2, Build.MODEL);
            StringBuilder sb = new StringBuilder(a.a);
            sb.append(VERSION.SDK_INT);
            MapUtils.put(allocate2, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(PackageHelper.getApplicationName(this.context));
            sb2.append(PackageHelper.getApplicationVersion(this.context));
            MapUtils.put(allocate2, sb2.toString());
            MapUtils.put(allocate2, "");
            MapUtils.put(allocate2, MapUtils.getImei());
            MapUtils.put(allocate2, MapUtils.getImsi());
            String upperCase = MapUtils.encodeWifiMac(com.amap.location.common.a.f(this.context)).toUpperCase();
            MapUtils.writeMac2SixByteBuffer(upperCase, allocate2);
            MapUtils.writeMac2SixByteBuffer(upperCase, allocate2);
            allocate2.put(0);
            MapUtils.put(allocate2, "SSID");
            allocate2.put(this.isPdrEnable ? (byte) 1 : 0);
            allocate2.putLong(j);
            allocate2.put(1);
            if (L.isLogging) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Build.MODEL);
                sb3.append(",Android");
                sb3.append(VERSION.RELEASE);
                sb3.append(",");
                sb3.append(PackageHelper.getApplicationName(this.context));
                sb3.append(PackageHelper.getApplicationVersion(this.context));
                L.d(sb3.toString());
            }
            if (scanData.type_ == 0) {
                allocate2.putShort((short) ((int) (scanData.time_ - j)));
                allocate2.putShort((short) scanData.scans_.size());
                for (int i2 = 0; i2 < scanData.scans_.size(); i2++) {
                    ScanPair scanPair = scanData.scans_.get(i2);
                    allocate2.put(GeoFenceHelper.getMacBytes(scanPair.mID));
                    allocate2.put((byte) scanPair.mRSSI);
                    allocate2.put(0);
                }
                allocate2.putShort(0);
            }
            if (scanData.type_ == 1) {
                String str = "";
                allocate2.putShort((short) ((int) (scanData.time_ - j)));
                allocate2.putShort(0);
                allocate2.putShort((short) scanData.scans_.size());
                for (int i3 = 0; i3 < scanData.scans_.size(); i3++) {
                    ScanPair scanPair2 = scanData.scans_.get(i3);
                    MapUtils.putDummyData(allocate2, 6);
                    String[] split = scanPair2.mID.split("_");
                    if (split.length == 3 && split[0].length() == 32 && split[1].length() == 4) {
                        if (split[2].length() == 4) {
                            allocate2.put(split[0].getBytes());
                            allocate2.put(split[1].getBytes());
                            allocate2.put(split[2].getBytes());
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str);
                            sb4.append(scanPair2.mID);
                            sb4.append(MetaRecord.LOG_SEPARATOR);
                            sb4.append(scanPair2.mRSSI);
                            sb4.append("$");
                            str = sb4.toString();
                            allocate2.put((byte) scanPair2.mRSSI);
                            allocate2.putInt(0);
                            allocate2.putInt(0);
                        }
                    }
                    MapUtils.putDummyData(allocate2, 40);
                    if (L.isLogging) {
                        StringBuilder sb5 = new StringBuilder("ERROR mac!!");
                        sb5.append(scanPair2.mID);
                        L.d(sb5.toString());
                    }
                    StringBuilder sb42 = new StringBuilder();
                    sb42.append(str);
                    sb42.append(scanPair2.mID);
                    sb42.append(MetaRecord.LOG_SEPARATOR);
                    sb42.append(scanPair2.mRSSI);
                    sb42.append("$");
                    str = sb42.toString();
                    allocate2.put((byte) scanPair2.mRSSI);
                    allocate2.putInt(0);
                    allocate2.putInt(0);
                }
            }
            allocate2.putInt(0);
            allocate2.putInt(0);
            allocate2.putInt(0);
            allocate2.put(GeoFenceHelper.getCheckData(allocate2));
            allocate.putInt((allocate2.position() - 2) + 1);
            try {
                ByteBuffer allocate3 = ByteBuffer.allocate(allocate2.position() + 10);
                allocate3.put(allocate.array());
                byte[] array = allocate3.array();
                System.arraycopy(allocate2.array(), 0, array, 10, allocate2.position());
                return array;
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
                return null;
            }
        } catch (Throwable th2) {
            if (L.isLogging) {
                L.d(th2);
            }
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0147, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x014a, code lost:
        if (com.autonavi.indoor.util.L.isLogging == false) goto L_0x0182;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x014c, code lost:
        r15 = new java.lang.StringBuilder("y:");
        r15.append(r7.y);
        com.autonavi.indoor.util.L.d(r15.toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.autonavi.indooroutdoordetectorsdk.BuildingLocationResult decodeLocateResponse(byte[] r15) {
        /*
            r14 = this;
            r0 = 0
            boolean r1 = com.autonavi.indoor.util.MapUtils.isEmpty(r15)     // Catch:{ Throwable -> 0x017a }
            if (r1 == 0) goto L_0x000f
            boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r1 == 0) goto L_0x000e
            com.autonavi.indoor.util.L.d(r15)     // Catch:{ Throwable -> 0x017a }
        L_0x000e:
            return r0
        L_0x000f:
            java.nio.ByteBuffer r15 = java.nio.ByteBuffer.wrap(r15)     // Catch:{ Throwable -> 0x017a }
            short r1 = r15.getShort()     // Catch:{ Throwable -> 0x017a }
            r2 = 17
            if (r1 == r2) goto L_0x002d
            boolean r15 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r15 == 0) goto L_0x002c
            java.lang.String r15 = "action_code:"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r15 = r15.concat(r1)     // Catch:{ Throwable -> 0x017a }
            com.autonavi.indoor.util.L.d(r15)     // Catch:{ Throwable -> 0x017a }
        L_0x002c:
            return r0
        L_0x002d:
            int r2 = r15.getInt()     // Catch:{ Throwable -> 0x017a }
            r15.getLong()     // Catch:{ Throwable -> 0x017a }
            int r3 = r15.getInt()     // Catch:{ Throwable -> 0x017a }
            if (r3 == 0) goto L_0x004d
            boolean r15 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r15 == 0) goto L_0x004c
            java.lang.String r15 = "status_code:"
            java.lang.String r1 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r15 = r15.concat(r1)     // Catch:{ Throwable -> 0x017a }
            com.autonavi.indoor.util.L.d(r15)     // Catch:{ Throwable -> 0x017a }
        L_0x004c:
            return r0
        L_0x004d:
            int r4 = r15.getInt()     // Catch:{ Throwable -> 0x017a }
            byte r5 = r15.get()     // Catch:{ Throwable -> 0x017a }
            byte r6 = r15.get()     // Catch:{ Throwable -> 0x017a }
            boolean r7 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r7 == 0) goto L_0x0096
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a }
            java.lang.String r8 = "BuilingLocate result action_code="
            r7.<init>(r8)     // Catch:{ Throwable -> 0x017a }
            r7.append(r1)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = ",action_version="
            r7.append(r1)     // Catch:{ Throwable -> 0x017a }
            r7.append(r2)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = ", status_code="
            r7.append(r1)     // Catch:{ Throwable -> 0x017a }
            r7.append(r3)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = ",len="
            r7.append(r1)     // Catch:{ Throwable -> 0x017a }
            r7.append(r4)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = ",LocationType(0GPS 1Wifi 2蓝牙 3混合)="
            r7.append(r1)     // Catch:{ Throwable -> 0x017a }
            r7.append(r5)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = ",Result="
            r7.append(r1)     // Catch:{ Throwable -> 0x017a }
            r7.append(r6)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = r7.toString()     // Catch:{ Throwable -> 0x017a }
            com.autonavi.indoor.util.L.d(r1)     // Catch:{ Throwable -> 0x017a }
        L_0x0096:
            if (r6 >= 0) goto L_0x00ab
            boolean r15 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r15 == 0) goto L_0x00aa
            java.lang.String r15 = "returncode:"
            java.lang.String r1 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r15 = r15.concat(r1)     // Catch:{ Throwable -> 0x017a }
            com.autonavi.indoor.util.L.d(r15)     // Catch:{ Throwable -> 0x017a }
        L_0x00aa:
            return r0
        L_0x00ab:
            java.lang.String r1 = com.autonavi.indoor.util.MapUtils.getString(r15)     // Catch:{ Throwable -> 0x017a }
            byte r2 = r15.get()     // Catch:{ Throwable -> 0x017a }
            r3 = 128(0x80, float:1.794E-43)
            java.nio.ByteBuffer r3 = java.nio.ByteBuffer.allocate(r3)     // Catch:{ Throwable -> 0x017a }
            r4 = 0
            r6 = 0
        L_0x00bb:
            if (r6 >= r2) goto L_0x0182
            com.autonavi.indooroutdoordetectorsdk.BuildingLocationResult r7 = new com.autonavi.indooroutdoordetectorsdk.BuildingLocationResult     // Catch:{ Throwable -> 0x017a }
            r7.<init>()     // Catch:{ Throwable -> 0x017a }
            double r8 = r15.getDouble()     // Catch:{ Throwable -> 0x017a }
            r7.x = r8     // Catch:{ Throwable -> 0x017a }
            double r8 = r7.x     // Catch:{ Throwable -> 0x017a }
            r10 = -4582834833314545664(0xc066800000000000, double:-180.0)
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 < 0) goto L_0x0161
            double r8 = r7.x     // Catch:{ Throwable -> 0x017a }
            r12 = 4640537203540230144(0x4066800000000000, double:180.0)
            int r8 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r8 <= 0) goto L_0x00e0
            goto L_0x0161
        L_0x00e0:
            double r8 = r15.getDouble()     // Catch:{ Throwable -> 0x017a }
            r7.y = r8     // Catch:{ Throwable -> 0x017a }
            double r8 = r7.y     // Catch:{ Throwable -> 0x017a }
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 < 0) goto L_0x0148
            double r8 = r7.y     // Catch:{ Throwable -> 0x017a }
            int r8 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r8 <= 0) goto L_0x00f3
            goto L_0x0148
        L_0x00f3:
            r3.position(r4)     // Catch:{ Throwable -> 0x017a }
            double r8 = r7.x     // Catch:{ Throwable -> 0x017a }
            r3.putDouble(r8)     // Catch:{ Throwable -> 0x017a }
            double r8 = r7.y     // Catch:{ Throwable -> 0x017a }
            r3.putDouble(r8)     // Catch:{ Throwable -> 0x017a }
            r3.position(r4)     // Catch:{ Throwable -> 0x017a }
            long r8 = r3.getLong()     // Catch:{ Throwable -> 0x017a }
            int r10 = r3.getInt()     // Catch:{ Throwable -> 0x017a }
            r11 = 0
            int r11 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r11 != 0) goto L_0x0134
            if (r10 != 0) goto L_0x0134
            boolean r7 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r7 == 0) goto L_0x0131
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a }
            java.lang.String r11 = "x:"
            r7.<init>(r11)     // Catch:{ Throwable -> 0x017a }
            r7.append(r8)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r8 = ", y:"
            r7.append(r8)     // Catch:{ Throwable -> 0x017a }
            r7.append(r10)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x017a }
            com.autonavi.indoor.util.L.d(r7)     // Catch:{ Throwable -> 0x017a }
        L_0x0131:
            int r6 = r6 + 1
            goto L_0x00bb
        L_0x0134:
            byte r2 = r15.get()     // Catch:{ Throwable -> 0x017a }
            r7.z = r2     // Catch:{ Throwable -> 0x017a }
            r15.getFloat()     // Catch:{ Throwable -> 0x017a }
            r15.getFloat()     // Catch:{ Throwable -> 0x017a }
            r15.getFloat()     // Catch:{ Throwable -> 0x017a }
            r7.bid = r1     // Catch:{ Throwable -> 0x017a }
            r7.d = r5     // Catch:{ Throwable -> 0x017a }
            return r7
        L_0x0148:
            boolean r15 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r15 == 0) goto L_0x0182
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = "y:"
            r15.<init>(r1)     // Catch:{ Throwable -> 0x017a }
            double r1 = r7.y     // Catch:{ Throwable -> 0x017a }
            r15.append(r1)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r15 = r15.toString()     // Catch:{ Throwable -> 0x017a }
            com.autonavi.indoor.util.L.d(r15)     // Catch:{ Throwable -> 0x017a }
            goto L_0x0182
        L_0x0161:
            boolean r15 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x017a }
            if (r15 == 0) goto L_0x0182
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x017a }
            java.lang.String r1 = "x:"
            r15.<init>(r1)     // Catch:{ Throwable -> 0x017a }
            double r1 = r7.x     // Catch:{ Throwable -> 0x017a }
            r15.append(r1)     // Catch:{ Throwable -> 0x017a }
            java.lang.String r15 = r15.toString()     // Catch:{ Throwable -> 0x017a }
            com.autonavi.indoor.util.L.d(r15)     // Catch:{ Throwable -> 0x017a }
            goto L_0x0182
        L_0x017a:
            r15 = move-exception
            boolean r1 = com.autonavi.indoor.util.L.isLogging
            if (r1 == 0) goto L_0x0182
            com.autonavi.indoor.util.L.d(r15)
        L_0x0182:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.BuildingLocateThread.decodeLocateResponse(byte[]):com.autonavi.indooroutdoordetectorsdk.BuildingLocationResult");
    }

    /* access modifiers changed from: 0000 */
    public void sendMessage2parent(int i, Object obj) {
        try {
            if (isValidHandler(this.mHandler)) {
                Message obtain = Message.obtain(this.mHandler, i);
                obtain.obj = obj;
                this.mHandler.sendMessage(obtain);
            }
        } catch (Exception e) {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("sendMessage to handler failed:");
                sb.append(e.toString());
                L.d(sb.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void sendEmptyMessage2parent(int i) {
        try {
            if (isValidHandler(this.mHandler)) {
                this.mHandler.sendEmptyMessage(i);
            }
        } catch (Exception e) {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("sendMessage to handler failed:");
                sb.append(e.toString());
                L.d(sb.toString());
            }
        }
    }
}
