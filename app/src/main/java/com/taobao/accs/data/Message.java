package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.base.TaoBaseService.ExtHeaderType;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.net.BaseConnection;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.JsonUtility.JsonObjectBuilder;
import com.taobao.accs.utl.MessageStreamBuilder;
import com.taobao.accs.utl.RomInfoCollecter;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;

public class Message {
    public static int CONTROL_MAX_RETRY_TIMES = 5;
    public static final int EXT_HEADER_VALUE_MAX_LEN = 1023;
    public static final int FLAG_ACK_TYPE = 32;
    public static final int FLAG_BIZ_RET = 64;
    public static final int FLAG_DATA_TYPE = 32768;
    public static final int FLAG_ERR = 4096;
    public static final int FLAG_REQ_BIT1 = 16384;
    public static final int FLAG_REQ_BIT2 = 8192;
    public static final int FLAG_RET = 2048;
    public static final String KEY_BINDAPP = "ctrl_bindapp";
    public static final String KEY_BINDSERVICE = "ctrl_bindservice";
    public static final String KEY_BINDUSER = "ctrl_binduser";
    public static final String KEY_UNBINDAPP = "ctrl_unbindapp";
    public static final String KEY_UNBINDSERVICE = "ctrl_unbindservice";
    public static final String KEY_UNBINDUSER = "ctrl_unbinduser";
    public static final int MAX_RETRY_TIMES = 3;
    private static final String TAG = "Msg";
    static long baseMessageId = 1;
    String appKey = null;
    public String appSign = null;
    String appVersion = null;
    public String bizId = null;
    String brand = null;
    public Integer command = null;
    byte compress = 0;
    public String cunstomDataId;
    byte[] data;
    public String dataId;
    short dataLength;
    public long delyTime = 0;
    Map<Integer, String> extHeader;
    short extHeaderLength;
    String exts = null;
    short flags;
    public boolean force = false;
    public URL host;
    String imei = null;
    String imsi = null;
    public boolean isAck = false;
    public boolean isCancel = false;
    String macAddress = null;
    String model = null;
    transient NetPerformanceMonitor netPerformanceMonitor;
    byte noUse = 0;
    int node;
    String notifyEnable = null;
    Integer osType = null;
    String osVersion = null;
    String packageName = null;
    public int retryTimes = 0;
    Integer sdkVersion = null;
    long sendTime;
    public String serviceId = null;
    String source;
    byte sourceLength;
    public long startSendTime;
    String tag = null;
    String target;
    byte targetLength;
    public int timeout = 40000;
    short totalLength;
    String ttid = null;
    int type = -1;
    Integer updateDevice = Integer.valueOf(0);
    public String userinfo = null;
    String venderOsName = null;
    String venderOsVersion = null;

    public static class MsgResType {
        public static final int INVALID = -1;
        public static final int NEED_ACK = 1;
        public static final int NO_ACK = 0;

        public static String name(int i) {
            switch (i) {
                case 0:
                    return "NO_ACK";
                case 1:
                    return "NEED_ACK";
                default:
                    return "INVALID";
            }
        }

        public static int valueOf(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    return 1;
            }
        }
    }

    public static class MsgType {
        public static final int CONTROL = 0;
        public static final int DATA = 1;
        public static final int HANDSHAKE = 3;
        public static final int INVALID = -1;
        public static final int PING = 2;

        public static String name(int i) {
            switch (i) {
                case 0:
                    return "CONTROL";
                case 1:
                    return SecureSignatureDefine.SG_KEY_SIGN_DATA;
                case 2:
                    return "PING";
                case 3:
                    return "HANDSHAKE";
                default:
                    return "INVALID";
            }
        }

        public static int valueOf(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                default:
                    return 0;
            }
        }
    }

    public enum ReqType {
        DATA,
        ACK,
        REQ,
        RES;

        public static ReqType valueOf(int i) {
            switch (i) {
                case 0:
                    return DATA;
                case 1:
                    return ACK;
                case 2:
                    return REQ;
                case 3:
                    return RES;
                default:
                    return DATA;
            }
        }
    }

    private Message() {
        synchronized (Message.class) {
            long j = baseMessageId;
            baseMessageId = 1 + j;
            this.dataId = String.valueOf(j);
        }
        this.startSendTime = System.currentTimeMillis();
    }

    public int getNode() {
        return this.node;
    }

    public int getType() {
        return this.type;
    }

    public String getDataId() {
        return this.dataId;
    }

    public boolean isControlFrame() {
        return Constants.TARGET_CONTROL.equals(this.target);
    }

    public int getIntDataId() {
        try {
            if (!this.isAck) {
                return Integer.valueOf(this.dataId).intValue();
            }
            return -((int) baseMessageId);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder("parse int dataId error ");
            sb.append(this.dataId);
            ALog.w(TAG, sb.toString(), new Object[0]);
            return -1;
        }
    }

    public void setSendTime(long j) {
        this.sendTime = j;
    }

    public NetPerformanceMonitor getNetPermanceMonitor() {
        return this.netPerformanceMonitor;
    }

    public long getDelyTime() {
        return this.delyTime;
    }

    public int getRetryTimes() {
        return this.retryTimes;
    }

    private String getTag() {
        StringBuilder sb = new StringBuilder("Msg_");
        sb.append(this.tag);
        return sb.toString();
    }

    public String getPackageName() {
        return this.packageName == null ? "" : this.packageName;
    }

    public boolean isTimeOut() {
        boolean z = (System.currentTimeMillis() - this.startSendTime) + this.delyTime >= ((long) this.timeout);
        if (z) {
            String tag2 = getTag();
            StringBuilder sb = new StringBuilder("delay time:");
            sb.append(this.delyTime);
            sb.append(" beforeSendTime:");
            sb.append(System.currentTimeMillis() - this.startSendTime);
            sb.append(" timeout");
            sb.append(this.timeout);
            ALog.e(tag2, sb.toString(), new Object[0]);
        }
        return z;
    }

    public byte[] build(Context context, int i) {
        byte[] bArr;
        try {
            buildData(context);
        } catch (JSONException e) {
            ALog.e(getTag(), "build1", e, new Object[0]);
        } catch (UnsupportedEncodingException e2) {
            ALog.e(getTag(), "build2", e2, new Object[0]);
        }
        String str = this.data != null ? new String(this.data) : "";
        compressData();
        if (!this.isAck) {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilityImpl.getDeviceId(context));
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.packageName);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.serviceId == null ? "" : this.serviceId);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.userinfo == null ? "" : this.userinfo);
            this.source = sb.toString();
        }
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.dataId);
            bArr = sb2.toString().getBytes("utf-8");
            this.sourceLength = (byte) this.source.getBytes("utf-8").length;
            this.targetLength = (byte) this.target.getBytes("utf-8").length;
        } catch (Exception e3) {
            e3.printStackTrace();
            ALog.e(getTag(), "build3", e3, new Object[0]);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.dataId);
            bArr = sb3.toString().getBytes();
            this.sourceLength = (byte) this.source.getBytes().length;
            this.targetLength = (byte) this.target.getBytes().length;
        }
        short extHeaderLen = getExtHeaderLen(this.extHeader);
        this.dataLength = (short) (this.targetLength + 3 + 1 + this.sourceLength + 1 + bArr.length + (this.data == null ? 0 : this.data.length) + extHeaderLen + 2);
        this.totalLength = (short) (this.dataLength + 2);
        MessageStreamBuilder messageStreamBuilder = new MessageStreamBuilder(this.totalLength + 2 + 4);
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(getTag(), "Build Message", Constants.KEY_DATA_ID, new String(bArr));
        }
        try {
            messageStreamBuilder.writeByte((byte) (this.compress | 32));
            if (ALog.isPrintLog(Level.D)) {
                String tag2 = getTag();
                StringBuilder sb4 = new StringBuilder("\tversion:2 compress:");
                sb4.append(this.compress);
                ALog.d(tag2, sb4.toString(), new Object[0]);
            }
            if (i == 0) {
                messageStreamBuilder.writeByte(Byte.MIN_VALUE);
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(getTag(), "\tflag: 0x80", new Object[0]);
                }
            } else {
                messageStreamBuilder.writeByte(64);
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(getTag(), "\tflag: 0x40", new Object[0]);
                }
            }
            messageStreamBuilder.writeShort(this.totalLength);
            if (ALog.isPrintLog(Level.D)) {
                String tag3 = getTag();
                StringBuilder sb5 = new StringBuilder("\ttotalLength:");
                sb5.append(this.totalLength);
                ALog.d(tag3, sb5.toString(), new Object[0]);
            }
            messageStreamBuilder.writeShort(this.dataLength);
            if (ALog.isPrintLog(Level.D)) {
                String tag4 = getTag();
                StringBuilder sb6 = new StringBuilder("\tdataLength:");
                sb6.append(this.dataLength);
                ALog.d(tag4, sb6.toString(), new Object[0]);
            }
            messageStreamBuilder.writeShort(this.flags);
            if (ALog.isPrintLog(Level.D)) {
                String tag5 = getTag();
                StringBuilder sb7 = new StringBuilder("\tflags:");
                sb7.append(Integer.toHexString(this.flags));
                ALog.d(tag5, sb7.toString(), new Object[0]);
            }
            messageStreamBuilder.writeByte(this.targetLength);
            if (ALog.isPrintLog(Level.D)) {
                String tag6 = getTag();
                StringBuilder sb8 = new StringBuilder("\ttargetLength:");
                sb8.append(this.targetLength);
                ALog.d(tag6, sb8.toString(), new Object[0]);
            }
            messageStreamBuilder.write(this.target.getBytes("utf-8"));
            if (ALog.isPrintLog(Level.D)) {
                String tag7 = getTag();
                StringBuilder sb9 = new StringBuilder("\ttarget:");
                sb9.append(new String(this.target));
                ALog.d(tag7, sb9.toString(), new Object[0]);
            }
            messageStreamBuilder.writeByte(this.sourceLength);
            if (ALog.isPrintLog(Level.D)) {
                String tag8 = getTag();
                StringBuilder sb10 = new StringBuilder("\tsourceLength:");
                sb10.append(this.sourceLength);
                ALog.d(tag8, sb10.toString(), new Object[0]);
            }
            messageStreamBuilder.write(this.source.getBytes("utf-8"));
            if (ALog.isPrintLog(Level.D)) {
                String tag9 = getTag();
                StringBuilder sb11 = new StringBuilder("\tsource:");
                sb11.append(new String(this.source));
                ALog.d(tag9, sb11.toString(), new Object[0]);
            }
            messageStreamBuilder.writeByte((byte) bArr.length);
            if (ALog.isPrintLog(Level.D)) {
                String tag10 = getTag();
                StringBuilder sb12 = new StringBuilder("\tdataIdLength:");
                sb12.append(bArr.length);
                ALog.d(tag10, sb12.toString(), new Object[0]);
            }
            messageStreamBuilder.write(bArr);
            if (ALog.isPrintLog(Level.D)) {
                String tag11 = getTag();
                StringBuilder sb13 = new StringBuilder("\tdataId:");
                sb13.append(new String(bArr));
                ALog.d(tag11, sb13.toString(), new Object[0]);
            }
            messageStreamBuilder.writeShort(extHeaderLen);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(getTag(), "\textHeader len:".concat(String.valueOf(extHeaderLen)), new Object[0]);
            }
            if (this.extHeader != null) {
                for (Integer intValue : this.extHeader.keySet()) {
                    int intValue2 = intValue.intValue();
                    String str2 = this.extHeader.get(Integer.valueOf(intValue2));
                    if (!TextUtils.isEmpty(str2)) {
                        messageStreamBuilder.writeShort((short) ((((short) intValue2) << 10) | ((short) (str2.getBytes("utf-8").length & EXT_HEADER_VALUE_MAX_LEN))));
                        messageStreamBuilder.write(str2.getBytes("utf-8"));
                        if (ALog.isPrintLog(Level.D)) {
                            String tag12 = getTag();
                            StringBuilder sb14 = new StringBuilder("\textHeader key:");
                            sb14.append(intValue2);
                            sb14.append(" value:");
                            sb14.append(str2);
                            ALog.d(tag12, sb14.toString(), new Object[0]);
                        }
                    }
                }
            }
            if (this.data != null) {
                messageStreamBuilder.write(this.data);
            }
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(getTag(), "\toriData:".concat(String.valueOf(str)), new Object[0]);
            }
            messageStreamBuilder.flush();
        } catch (IOException e4) {
            ALog.e(getTag(), "build4", e4, new Object[0]);
        }
        byte[] byteArray = messageStreamBuilder.toByteArray();
        try {
            messageStreamBuilder.close();
        } catch (IOException e5) {
            ALog.e(getTag(), "build5", e5, new Object[0]);
        }
        return byteArray;
    }

    /* access modifiers changed from: 0000 */
    public short getExtHeaderLen(Map<Integer, String> map) {
        short s = 0;
        if (map != null) {
            try {
                for (Integer intValue : map.keySet()) {
                    String str = map.get(Integer.valueOf(intValue.intValue()));
                    if (!TextUtils.isEmpty(str)) {
                        s = (short) (s + ((short) (str.getBytes("utf-8").length & EXT_HEADER_VALUE_MAX_LEN)) + 2);
                    }
                }
            } catch (Exception unused) {
            }
        }
        return s;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x004e A[SYNTHETIC, Splitter:B:31:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0053 A[Catch:{ Exception -> 0x0057 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x005c A[SYNTHETIC, Splitter:B:40:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0061 A[Catch:{ Exception -> 0x0064 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void compressData() {
        /*
            r6 = this;
            r0 = 0
            byte[] r1 = r6.data     // Catch:{ Throwable -> 0x0042, all -> 0x003d }
            if (r1 != 0) goto L_0x0006
            return
        L_0x0006:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0042, all -> 0x003d }
            r1.<init>()     // Catch:{ Throwable -> 0x0042, all -> 0x003d }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x0038, all -> 0x0033 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0038, all -> 0x0033 }
            byte[] r0 = r6.data     // Catch:{ Throwable -> 0x0031 }
            r2.write(r0)     // Catch:{ Throwable -> 0x0031 }
            r2.finish()     // Catch:{ Throwable -> 0x0031 }
            byte[] r0 = r1.toByteArray()     // Catch:{ Throwable -> 0x0031 }
            if (r0 == 0) goto L_0x0029
            int r3 = r0.length     // Catch:{ Throwable -> 0x0031 }
            byte[] r4 = r6.data     // Catch:{ Throwable -> 0x0031 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x0031 }
            if (r3 >= r4) goto L_0x0029
            r6.data = r0     // Catch:{ Throwable -> 0x0031 }
            r0 = 1
            r6.compress = r0     // Catch:{ Throwable -> 0x0031 }
        L_0x0029:
            r2.close()     // Catch:{ Exception -> 0x0030 }
            r1.close()     // Catch:{ Exception -> 0x0030 }
            return
        L_0x0030:
            return
        L_0x0031:
            r0 = move-exception
            goto L_0x0046
        L_0x0033:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x005a
        L_0x0038:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0046
        L_0x003d:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
            goto L_0x005a
        L_0x0042:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
        L_0x0046:
            r6.getTag()     // Catch:{ all -> 0x0059 }
            r0.printStackTrace()     // Catch:{ all -> 0x0059 }
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch:{ Exception -> 0x0057 }
        L_0x0051:
            if (r1 == 0) goto L_0x0058
            r1.close()     // Catch:{ Exception -> 0x0057 }
            goto L_0x0058
        L_0x0057:
            return
        L_0x0058:
            return
        L_0x0059:
            r0 = move-exception
        L_0x005a:
            if (r2 == 0) goto L_0x005f
            r2.close()     // Catch:{ Exception -> 0x0064 }
        L_0x005f:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ Exception -> 0x0064 }
        L_0x0064:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.Message.compressData():void");
    }

    /* access modifiers changed from: 0000 */
    public void buildData(Context context) throws JSONException, UnsupportedEncodingException {
        if (this.command != null && this.command.intValue() != 100 && this.command.intValue() != 102) {
            this.data = new JsonObjectBuilder().put((String) "command", this.command.intValue() == 100 ? null : this.command).put((String) "appKey", this.appKey).put((String) Constants.KEY_OS_TYPE, this.osType).put((String) "sign", this.appSign).put((String) Constants.KEY_SDK_VERSION, this.sdkVersion).put((String) "appVersion", this.appVersion).put((String) "ttid", this.ttid).put((String) Constants.KEY_MODEL, this.model).put((String) "brand", this.brand).put((String) Constants.KEY_IMEI, this.imei).put((String) Constants.KEY_IMSI, this.imsi).put((String) "os", this.osVersion).put((String) Constants.KEY_EXTS, this.exts).build().toString().getBytes("utf-8");
        }
    }

    /* access modifiers changed from: 0000 */
    public void printByte(byte[] bArr) {
        if (ALog.isPrintLog(Level.D)) {
            String tag2 = getTag();
            StringBuilder sb = new StringBuilder("len:");
            sb.append(bArr.length);
            ALog.d(tag2, sb.toString(), new Object[0]);
            if (bArr.length < 512) {
                String str = "";
                for (byte b : bArr) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(Integer.toHexString(b & 255));
                    sb2.append(Token.SEPARATOR);
                    str = sb2.toString();
                }
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(getTag(), str, new Object[0]);
                }
            }
        }
    }

    public static Message BuildPing(boolean z, int i) {
        Message message = new Message();
        message.type = 2;
        message.command = Integer.valueOf(201);
        message.force = z;
        message.delyTime = (long) i;
        return message;
    }

    public static Message buildHandshake(String str) {
        Message message = new Message();
        message.type(3, ReqType.DATA, 1);
        message.packageName = str;
        message.target = Constants.TARGET_CONTROL;
        message.command = Integer.valueOf(200);
        return message;
    }

    public static Message buildBindApp(BaseConnection baseConnection, Context context, Intent intent) {
        Message message;
        try {
            String stringExtra = intent.getStringExtra("packageName");
            String stringExtra2 = intent.getStringExtra("userInfo");
            String stringExtra3 = intent.getStringExtra("appKey");
            String stringExtra4 = intent.getStringExtra("ttid");
            String stringExtra5 = intent.getStringExtra(Constants.KEY_SID);
            String stringExtra6 = intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            String stringExtra7 = intent.getStringExtra("appVersion");
            Context context2 = context;
            message = buildBindApp(context2, baseConnection.mConfigTag, stringExtra3, intent.getStringExtra("app_sercet"), stringExtra, stringExtra4, stringExtra7, stringExtra5, stringExtra2, stringExtra6);
            try {
                setControlHost(baseConnection, context, message);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.e(TAG, "buildBindApp", e.getMessage());
            return message;
        }
        return message;
    }

    public static Message buildBindApp(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        String str10 = null;
        if (TextUtils.isEmpty(str4)) {
            return null;
        }
        Message message = new Message();
        message.node = 1;
        message.type(1, ReqType.DATA, 1);
        message.osType = Integer.valueOf(1);
        StringBuilder sb = new StringBuilder();
        sb.append(VERSION.SDK_INT);
        message.osVersion = sb.toString();
        message.packageName = str4;
        message.target = Constants.TARGET_CONTROL;
        message.command = Integer.valueOf(1);
        message.appKey = str2;
        message.appSign = UtilityImpl.getAppsign(context, str2, str3, UtilityImpl.getDeviceId(context), str);
        message.sdkVersion = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.appVersion = str6;
        message.packageName = str4;
        message.ttid = str5;
        message.model = Build.MODEL;
        message.brand = Build.BRAND;
        message.cunstomDataId = KEY_BINDAPP;
        message.tag = str;
        message.exts = new JsonObjectBuilder().put((String) "notifyEnable", UtilityImpl.isNotificationEnabled(context)).put((String) "romInfo", RomInfoCollecter.getCollecter().collect()).build().toString();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            message.imei = telephonyManager != null ? telephonyManager.getDeviceId() : null;
            if (telephonyManager != null) {
                str10 = telephonyManager.getSubscriberId();
            }
            message.imsi = str10;
        } catch (Throwable th) {
            ALog.w(TAG, "buildBindApp imei", th.getMessage());
        }
        return message;
    }

    public static Message buildUnbindApp(BaseConnection baseConnection, Context context, Intent intent) {
        Message message;
        StringBuilder sb = new StringBuilder("buildUnbindApp1");
        sb.append(UtilityImpl.getStackMsg(new Exception()));
        ALog.e(TAG, sb.toString(), new Object[0]);
        try {
            BaseConnection baseConnection2 = baseConnection;
            Context context2 = context;
            message = buildUnbindApp(baseConnection2, context2, intent.getStringExtra("packageName"), intent.getStringExtra(Constants.KEY_SID), intent.getStringExtra("userInfo"), intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE));
            try {
                setControlHost(baseConnection, context, message);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.e(TAG, "buildUnbindApp1", e.getMessage());
            return message;
        }
        return message;
    }

    public static Message buildUnbindApp(BaseConnection baseConnection, Context context, String str, String str2, String str3, String str4) {
        Message message;
        try {
            StringBuilder sb = new StringBuilder("buildUnbindApp");
            sb.append(UtilityImpl.getStackMsg(new Exception()));
            ALog.e(TAG, sb.toString(), new Object[0]);
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            message = new Message();
            try {
                message.node = 1;
                message.type(1, ReqType.DATA, 1);
                message.packageName = str;
                message.target = Constants.TARGET_CONTROL;
                message.command = Integer.valueOf(2);
                message.packageName = str;
                message.sdkVersion = Integer.valueOf(Constants.SDK_VERSION_CODE);
                message.cunstomDataId = KEY_UNBINDAPP;
                setControlHost(baseConnection, context, message);
            } catch (Exception e) {
                e = e;
            }
            return message;
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.e(TAG, "buildUnbindApp", e.getMessage());
            return message;
        }
    }

    public static Message buildBindService(BaseConnection baseConnection, Context context, Intent intent) {
        Message message;
        try {
            Context context2 = context;
            message = buildBindService(context2, intent.getStringExtra("packageName"), intent.getStringExtra("appKey"), intent.getStringExtra("serviceId"), intent.getStringExtra(Constants.KEY_SID), intent.getStringExtra("userInfo"), intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE));
            try {
                message.tag = baseConnection.mConfigTag;
                setControlHost(baseConnection, context, message);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.e(TAG, "buildBindService", e, new Object[0]);
            e.printStackTrace();
            return message;
        }
        return message;
    }

    public static Message buildBindService(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            return null;
        }
        Message message = new Message();
        message.node = 1;
        message.type(1, ReqType.DATA, 1);
        message.packageName = str;
        message.serviceId = str3;
        message.target = Constants.TARGET_CONTROL;
        message.command = Integer.valueOf(5);
        message.packageName = str;
        message.serviceId = str3;
        message.sdkVersion = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.cunstomDataId = KEY_BINDSERVICE;
        return message;
    }

    public static Message buildUnbindService(BaseConnection baseConnection, Context context, Intent intent) {
        Message message;
        try {
            Context context2 = context;
            message = buildUnbindService(context2, intent.getStringExtra("packageName"), intent.getStringExtra("appKey"), intent.getStringExtra("serviceId"), intent.getStringExtra(Constants.KEY_SID), intent.getStringExtra("userInfo"), intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE));
            try {
                message.tag = baseConnection.mConfigTag;
                setControlHost(baseConnection, context, message);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.e(TAG, "buildUnbindService", e, new Object[0]);
            e.printStackTrace();
            return message;
        }
        return message;
    }

    public static Message buildUnbindService(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            return null;
        }
        Message message = new Message();
        message.node = 1;
        message.type(1, ReqType.DATA, 1);
        message.packageName = str;
        message.serviceId = str3;
        message.target = Constants.TARGET_CONTROL;
        message.command = Integer.valueOf(6);
        message.packageName = str;
        message.serviceId = str3;
        message.sdkVersion = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.cunstomDataId = KEY_UNBINDSERVICE;
        return message;
    }

    public static Message buildBindUser(BaseConnection baseConnection, Context context, Intent intent) {
        Message message;
        try {
            Context context2 = context;
            message = buildBindUser(context2, intent.getStringExtra("packageName"), intent.getStringExtra("appKey"), intent.getStringExtra(Constants.KEY_SID), intent.getStringExtra("userInfo"), intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE));
            if (message != null) {
                try {
                    message.tag = baseConnection.mConfigTag;
                    setControlHost(baseConnection, context, message);
                } catch (Exception e) {
                    e = e;
                }
            }
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.e(TAG, "buildBindUser", e, new Object[0]);
            e.printStackTrace();
            return message;
        }
        return message;
    }

    public static Message buildBindUser(Context context, String str, String str2, String str3, String str4, String str5) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str4)) {
            return null;
        }
        Message message = new Message();
        message.node = 1;
        message.type(1, ReqType.DATA, 1);
        message.packageName = str;
        message.userinfo = str4;
        message.target = Constants.TARGET_CONTROL;
        message.command = Integer.valueOf(3);
        message.packageName = str;
        message.userinfo = str4;
        message.sdkVersion = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.cunstomDataId = KEY_BINDUSER;
        return message;
    }

    public static Message buildUnbindUser(BaseConnection baseConnection, Context context, Intent intent) {
        Message message;
        try {
            Context context2 = context;
            message = buildUnbindUser(context2, intent.getStringExtra("packageName"), intent.getStringExtra("appKey"), intent.getStringExtra(Constants.KEY_SID), intent.getStringExtra("userInfo"), intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE));
            try {
                message.tag = baseConnection.mConfigTag;
                setControlHost(baseConnection, context, message);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.e(TAG, "buildUnbindUser", e, new Object[0]);
            e.printStackTrace();
            return message;
        }
        return message;
    }

    public static Message buildUnbindUser(Context context, String str, String str2, String str3, String str4, String str5) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.node = 1;
        message.type(1, ReqType.DATA, 1);
        message.packageName = str;
        message.target = Constants.TARGET_CONTROL;
        message.command = Integer.valueOf(4);
        message.sdkVersion = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.cunstomDataId = KEY_UNBINDUSER;
        return message;
    }

    public static Message buildSendData(BaseConnection baseConnection, Context context, String str, String str2, AccsRequest accsRequest) {
        return buildSendData(baseConnection, context, str, str2, accsRequest, true);
    }

    public static Message buildSendData(BaseConnection baseConnection, Context context, String str, String str2, AccsRequest accsRequest, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.node = 1;
        message.type(1, ReqType.DATA, 1);
        message.command = Integer.valueOf(100);
        message.packageName = str;
        message.serviceId = accsRequest.serviceId;
        message.userinfo = accsRequest.userId;
        message.data = accsRequest.data;
        String str3 = TextUtils.isEmpty(accsRequest.targetServiceName) ? accsRequest.serviceId : accsRequest.targetServiceName;
        StringBuilder sb = new StringBuilder(Constants.TARGET_SERVICE_PRE);
        sb.append(str3);
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(accsRequest.target == null ? "" : accsRequest.target);
        message.target = sb.toString();
        message.cunstomDataId = accsRequest.dataId;
        message.bizId = accsRequest.businessId;
        if (accsRequest.timeout > 0) {
            message.timeout = accsRequest.timeout;
        }
        if (z) {
            setUnit(baseConnection, context, message, accsRequest);
        } else {
            message.host = accsRequest.host;
        }
        fillExtHeader(context, message, GlobalClientInfo.getInstance(context).getSid(baseConnection.mConfigTag), GlobalClientInfo.getInstance(context).getUserId(baseConnection.mConfigTag), GlobalClientInfo.mCookieSec, accsRequest.businessId, accsRequest.tag);
        message.netPerformanceMonitor = new NetPerformanceMonitor();
        message.netPerformanceMonitor.setDataId(accsRequest.dataId);
        message.netPerformanceMonitor.setServiceId(accsRequest.serviceId);
        message.netPerformanceMonitor.setHost(message.host.toString());
        message.tag = baseConnection.mConfigTag;
        return message;
    }

    public static Message buildRequest(BaseConnection baseConnection, Context context, String str, String str2, AccsRequest accsRequest, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.node = 1;
        message.type(1, ReqType.REQ, 1);
        message.command = Integer.valueOf(100);
        message.packageName = str;
        message.serviceId = accsRequest.serviceId;
        message.userinfo = accsRequest.userId;
        message.data = accsRequest.data;
        String str3 = TextUtils.isEmpty(accsRequest.targetServiceName) ? accsRequest.serviceId : accsRequest.targetServiceName;
        StringBuilder sb = new StringBuilder(Constants.TARGET_SERVICE_PRE);
        sb.append(str3);
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(accsRequest.target == null ? "" : accsRequest.target);
        message.target = sb.toString();
        message.cunstomDataId = accsRequest.dataId;
        message.bizId = accsRequest.businessId;
        message.tag = baseConnection.mConfigTag;
        if (accsRequest.timeout > 0) {
            message.timeout = accsRequest.timeout;
        }
        if (z) {
            setUnit(baseConnection, context, message, accsRequest);
        } else {
            message.host = accsRequest.host;
        }
        fillExtHeader(context, message, GlobalClientInfo.getInstance(context).getSid(baseConnection.mConfigTag), GlobalClientInfo.getInstance(context).getUserId(baseConnection.mConfigTag), GlobalClientInfo.mCookieSec, accsRequest.businessId, accsRequest.tag);
        message.netPerformanceMonitor = new NetPerformanceMonitor();
        message.netPerformanceMonitor.setDataId(accsRequest.dataId);
        message.netPerformanceMonitor.setServiceId(accsRequest.serviceId);
        message.netPerformanceMonitor.setHost(message.host.toString());
        message.tag = baseConnection.mConfigTag;
        return message;
    }

    private static void setUnit(BaseConnection baseConnection, Context context, Message message, AccsRequest accsRequest) {
        if (accsRequest.host == null) {
            try {
                message.host = new URL(baseConnection.getHost(context, null));
            } catch (MalformedURLException e) {
                ALog.e(TAG, "setUnit", e, new Object[0]);
                e.printStackTrace();
            }
        } else {
            message.host = accsRequest.host;
        }
    }

    private static void setControlHost(BaseConnection baseConnection, Context context, Message message) {
        try {
            message.host = new URL(baseConnection.getHost(context, null));
        } catch (Exception e) {
            ALog.e(TAG, "setControlHost", e, new Object[0]);
        }
    }

    public static Message buildPushAck(BaseConnection baseConnection, String str, String str2, String str3, boolean z, short s, String str4, Map<Integer, String> map) {
        Message message = new Message();
        message.node = 1;
        message.setPushAckFlag(s, z);
        message.source = str;
        message.target = str2;
        message.dataId = str3;
        message.isAck = true;
        message.extHeader = map;
        try {
            if (TextUtils.isEmpty(str4)) {
                message.host = new URL(baseConnection.getHost(GlobalClientInfo.getContext(), null));
            } else {
                message.host = new URL(str4);
            }
            message.tag = baseConnection.mConfigTag;
            if (message.host == null) {
                try {
                    message.host = new URL(baseConnection.getHost(GlobalClientInfo.getContext(), null));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (message.host == null) {
                try {
                    message.host = new URL(baseConnection.getHost(GlobalClientInfo.getContext(), null));
                } catch (MalformedURLException e2) {
                    e2.printStackTrace();
                }
            }
            throw th;
        }
        return message;
    }

    public static Message buildParameterError(String str, int i) {
        Message message = new Message();
        message.type(1, ReqType.ACK, 0);
        message.command = Integer.valueOf(i);
        message.packageName = str;
        return message;
    }

    private static void fillExtHeader(Context context, Message message, String str, String str2, String str3, String str4, String str5) {
        if (!TextUtils.isEmpty(str4) || !TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str5) || str3 != null) {
            message.extHeader = new HashMap();
            if (str4 != null && UtilityImpl.getByteLen(str4) <= 1023) {
                message.extHeader.put(Integer.valueOf(ExtHeaderType.TYPE_BUSINESS.ordinal()), str4);
            }
            if (str != null && UtilityImpl.getByteLen(str) <= 1023) {
                message.extHeader.put(Integer.valueOf(ExtHeaderType.TYPE_SID.ordinal()), str);
            }
            if (str2 != null && UtilityImpl.getByteLen(str2) <= 1023) {
                message.extHeader.put(Integer.valueOf(ExtHeaderType.TYPE_USERID.ordinal()), str2);
            }
            if (str5 != null && UtilityImpl.getByteLen(str5) <= 1023) {
                message.extHeader.put(Integer.valueOf(ExtHeaderType.TYPE_TAG.ordinal()), str5);
            }
            if (str3 != null && UtilityImpl.getByteLen(str3) <= 1023) {
                message.extHeader.put(Integer.valueOf(ExtHeaderType.TYPE_COOKIE.ordinal()), str3);
            }
        }
    }

    private void type(int i, ReqType reqType, int i2) {
        this.type = i;
        if (i != 2) {
            this.flags = (short) (((((i & 1) << 4) | (reqType.ordinal() << 2)) | i2) << 11);
        }
    }

    private void setPushAckFlag(short s, boolean z) {
        this.type = 1;
        this.flags = s;
        this.flags = (short) (this.flags & -16385);
        this.flags = (short) (this.flags | 8192);
        this.flags = (short) (this.flags & -2049);
        this.flags = (short) (this.flags & -65);
        if (z) {
            this.flags = (short) (this.flags | 32);
        }
    }
}
