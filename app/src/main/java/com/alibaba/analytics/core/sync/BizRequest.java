package com.alibaba.analytics.core.sync;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.timestamp.ConfigTimeStampMgr;
import com.alibaba.analytics.core.device.Constants;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.alibaba.analytics.utils.ByteUtils;
import com.alibaba.analytics.utils.DeviceUtil;
import com.alibaba.analytics.utils.GzipUtils;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.ReflectUtils;
import com.alibaba.analytics.utils.WuaHelper;
import com.alibaba.analytics.utils.ZipDictUtils;
import com.alibaba.analytics.version.UTBuildInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class BizRequest {
    private static final byte BYTE_ZERO = 0;
    private static final byte FLAGS_GET_CONFIG = 32;
    private static final byte FLAGS_GZIP = 1;
    private static final byte FLAGS_GZIP_FLUSH_DIC = 2;
    private static final byte FLAGS_KEEP_ALIVE = 8;
    private static final byte FLAGS_REAL_TIME_DEBUG = 16;
    private static final int HEAD_LENGTH = 8;
    private static final String LOG_SEPARATE = "\u0001";
    private static final boolean NeedConfigByResponse = true;
    private static final boolean NeedMiniWua = true;
    private static final int PAYLOAD_MAX_LENGTH = 16777216;
    private static final int SplitNumber = 34;
    private static boolean bTestFlowGenterClsExist = false;
    private static Class flowClz;
    private static ByteArrayOutputStream mByteArrayOutputStream;
    private static GZIPOutputStream mGZIPOutputStream;
    static String mMiniWua;
    private static int mMiniWuaIndex;
    public static final SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();
    private static long mReceivedDataLen;
    static String mResponseAdditionalData;

    public static byte[] getPackRequest(Map<String, String> map) throws Exception {
        return getPackRequest(map, 1);
    }

    static byte[] getPackRequestByRealtime(Map<String, String> map) throws Exception {
        return getPackRequest(map, 2);
    }

    static byte[] getPackRequest(Map<String, String> map, int i) throws Exception {
        byte[] bArr;
        int i2 = 2;
        int i3 = 1;
        if (Variables.getInstance().isGzipUpload() || Variables.getInstance().isHttpService()) {
            bArr = GzipUtils.gzip(getPayload(map));
            i2 = 1;
        } else {
            TnetUtil.initTnetStream();
            if (mGZIPOutputStream != null) {
                mGZIPOutputStream.write(getPayloadByDictZip(map));
                mGZIPOutputStream.flush();
                bArr = mByteArrayOutputStream.toByteArray();
                mByteArrayOutputStream.reset();
                i3 = 2;
            } else {
                bArr = GzipUtils.gzip(getPayloadByDictZip(map));
            }
        }
        if (bArr == null) {
            return null;
        }
        if (bArr.length >= 16777216) {
            if (Variables.getInstance().isSelfMonitorTurnOn()) {
                mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.DATALEN_OVERFLOW, String.valueOf(bArr.length), Double.valueOf(1.0d)));
            }
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(i2);
        byteArrayOutputStream.write(ByteUtils.intToBytes3(bArr.length));
        byteArrayOutputStream.write(i);
        byte b = (byte) (i3 | 8);
        if (Variables.getInstance().isRealTimeDebug()) {
            b = (byte) (b | FLAGS_REAL_TIME_DEBUG);
        }
        byteArrayOutputStream.write((byte) (b | FLAGS_GET_CONFIG));
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(bArr);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Logger.e(null, e, new Object[0]);
        }
        return byteArray;
    }

    private static byte[] getPayload(Map<String, String> map) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String head = getHead();
        if (head == null || head.length() <= 0) {
            byteArrayOutputStream.write(ByteUtils.intToBytes2(0));
        } else {
            byteArrayOutputStream.write(ByteUtils.intToBytes2(head.getBytes().length));
            byteArrayOutputStream.write(head.getBytes());
        }
        if (map != null && map.size() > 0) {
            for (String next : map.keySet()) {
                byteArrayOutputStream.write(ByteUtils.intToBytes4(Integer.valueOf(next).intValue()));
                String str = map.get(next);
                if (str != null) {
                    byteArrayOutputStream.write(ByteUtils.intToBytes4(str.getBytes().length));
                    byteArrayOutputStream.write(str.getBytes());
                } else {
                    byteArrayOutputStream.write(ByteUtils.intToBytes4(0));
                }
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    private static byte[] getPayloadByDictZip(Map<String, String> map) throws Exception {
        String[] split;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(ZipDictUtils.getHeadBytes(getHead()));
        if (map != null && map.size() > 0) {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            for (String next : map.keySet()) {
                byteArrayOutputStream.write(ZipDictUtils.getLengthBytes(Integer.valueOf(next).intValue()));
                String str = map.get(next);
                if (str != null) {
                    for (String str2 : str.split(LOG_SEPARATE)) {
                        if (!TextUtils.isEmpty(str2)) {
                            String[] splitResult = getSplitResult(str2);
                            if (splitResult != null && 34 == splitResult.length) {
                                for (String bytes : splitResult) {
                                    byteArrayOutputStream2.write(ZipDictUtils.getBytes(bytes));
                                }
                                byteArrayOutputStream2.write(0);
                            }
                        }
                    }
                    byteArrayOutputStream.write(ZipDictUtils.getLengthBytes(byteArrayOutputStream2.size()));
                    byteArrayOutputStream.write(byteArrayOutputStream2.toByteArray());
                    byteArrayOutputStream2.reset();
                } else {
                    byteArrayOutputStream.write(0);
                }
            }
            try {
                byteArrayOutputStream2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return byteArray;
    }

    public static String getHead() {
        String str;
        String appkey = AppInfoUtil.getAppkey();
        Context context = Variables.getInstance().getContext();
        String appVersion = Variables.getInstance().getAppVersion();
        if (appVersion == null) {
            appVersion = "";
        }
        String str2 = "";
        Map<String, String> deviceInfo = DeviceUtil.getDeviceInfo(context);
        if (deviceInfo != null) {
            str2 = deviceInfo.get(LogField.APPVERSION.toString());
            if (str2 == null) {
                str2 = "";
            }
        }
        String channel = AppInfoUtil.getChannel();
        if (channel == null) {
            channel = "";
        }
        String str3 = "";
        if (deviceInfo != null) {
            str3 = deviceInfo.get(LogField.UTDID.toString());
        }
        String fullSDKVersion = UTBuildInfo.getInstance().getFullSDKVersion();
        if (Variables.getInstance().isRealTimeDebug()) {
            str = String.format("ak=%s&av=%s&avsys=%s&c=%s&d=%s&sv=%s&dk=%s", new Object[]{appkey, appVersion, str2, channel, str3, fullSDKVersion, Variables.getInstance().getDebugKey()});
        } else {
            str = String.format("ak=%s&av=%s&avsys=%s&c=%s&d=%s&sv=%s", new Object[]{appkey, appVersion, str2, channel, str3, fullSDKVersion});
        }
        StringBuilder sb = new StringBuilder(str);
        if (Variables.getInstance().isHttpService()) {
            if (mMiniWuaIndex == 0) {
                mMiniWua = WuaHelper.getMiniWua();
            }
            int i = mMiniWuaIndex + 1;
            mMiniWuaIndex = i;
            if (i > 50) {
                mMiniWuaIndex = 0;
            }
        } else {
            TnetUtil.refreshMiniWua();
        }
        if (!TextUtils.isEmpty(mMiniWua)) {
            sb.append("&wua=");
            sb.append(mMiniWua);
        }
        sb.append("&_ut_sample=");
        sb.append(ConfigTimeStampMgr.getInstance().get("ut_sample"));
        sb.append("&_utap_system=");
        sb.append(ConfigTimeStampMgr.getInstance().get("utap_system"));
        sb.append("&_ap_stat=");
        sb.append(ConfigTimeStampMgr.getInstance().get("ap_stat"));
        sb.append("&_ap_alarm=");
        sb.append(ConfigTimeStampMgr.getInstance().get("ap_alarm"));
        sb.append("&_ap_counter=");
        sb.append(ConfigTimeStampMgr.getInstance().get("ap_counter"));
        sb.append("&_ut_bussiness=");
        sb.append(ConfigTimeStampMgr.getInstance().get("ut_bussiness"));
        sb.append("&_ut_realtime=");
        sb.append(ConfigTimeStampMgr.getInstance().get("ut_realtime"));
        String sb2 = sb.toString();
        Logger.i((String) "PostData", "send url :".concat(String.valueOf(sb2)));
        return sb2;
    }

    @TargetApi(19)
    static void initOutputStream() {
        if (VERSION.SDK_INT >= 19) {
            closeOutputStream();
            mByteArrayOutputStream = new ByteArrayOutputStream();
            try {
                mGZIPOutputStream = new GZIPOutputStream(mByteArrayOutputStream, true);
            } catch (Exception unused) {
            }
        }
    }

    static void closeOutputStream() {
        closeOutputStream(mGZIPOutputStream);
        closeOutputStream(mByteArrayOutputStream);
    }

    static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String[] getSplitResult(String str) {
        String[] strArr = new String[34];
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= 33) {
                break;
            }
            int indexOf = str.indexOf(Constants.SEPARATOR, i2);
            if (indexOf == -1) {
                strArr[i] = str.substring(i2);
                break;
            }
            strArr[i] = str.substring(i2, indexOf);
            i2 = indexOf + 2;
            i++;
        }
        strArr[33] = str.substring(i2);
        return strArr;
    }

    static int parseResult(byte[] bArr) {
        int i = -1;
        if (bArr == null || bArr.length < 12) {
            Logger.e((String) "", "recv errCode UNKNOWN_ERROR");
        } else {
            mReceivedDataLen = (long) bArr.length;
            if (ByteUtils.bytesToInt(bArr, 1, 3) + 8 != bArr.length) {
                Logger.e((String) "", "recv len error");
            } else {
                boolean z = 1 == (bArr[5] & 1);
                int bytesToInt = ByteUtils.bytesToInt(bArr, 8, 4);
                int length = bArr.length - 12 >= 0 ? bArr.length - 12 : 0;
                if (length <= 0) {
                    mResponseAdditionalData = null;
                } else if (z) {
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 12, bArr2, 0, length);
                    byte[] unGzip = GzipUtils.unGzip(bArr2);
                    mResponseAdditionalData = new String(unGzip, 0, unGzip.length);
                } else {
                    mResponseAdditionalData = new String(bArr, 12, length);
                }
                i = bytesToInt;
            }
        }
        if (107 == i) {
            Variables.getInstance().setHttpService(true);
        }
        if (109 == i) {
            Variables.getInstance().setGzipUpload(true);
        }
        if (115 == i) {
            Variables.getInstance().setRealtimeServiceClosed(true);
        }
        if (116 == i) {
            Variables.getInstance().setAllServiceClosed(true);
        }
        Logger.d((String) "", "errCode", Integer.valueOf(i));
        return i;
    }

    static void recordTraffic(long j) {
        try {
            Context context = Variables.getInstance().getContext();
            if (context != null) {
                if (!bTestFlowGenterClsExist && flowClz != null) {
                    flowClz = Class.forName("com.taobao.analysis.FlowCenter");
                    bTestFlowGenterClsExist = true;
                }
                if (flowClz != null) {
                    Object invokeStaticMethod = ReflectUtils.invokeStaticMethod(flowClz, (String) "getInstance");
                    if (invokeStaticMethod != null) {
                        Logger.d((String) "", "sendBytes", Long.valueOf(j), "mReceivedDataLen", Long.valueOf(mReceivedDataLen));
                        ReflectUtils.invokeMethod(invokeStaticMethod, "commitFlow", new Object[]{context, LogItem.MM_C03_K4_UPLOAD_TYPE, Boolean.TRUE, LogItem.MM_C03_K4_UPLOAD_TYPE, Long.valueOf(j), Long.valueOf(mReceivedDataLen)}, Context.class, String.class, Boolean.TYPE, String.class, Long.TYPE, Long.TYPE);
                    }
                }
            }
        } catch (Throwable unused) {
        } finally {
            mReceivedDataLen = 0;
        }
    }
}
