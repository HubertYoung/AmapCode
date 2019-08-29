package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import anet.channel.statist.StatObject;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.antibrush.AntiBrush;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message.MsgResType;
import com.taobao.accs.data.Message.MsgType;
import com.taobao.accs.data.Message.ReqType;
import com.taobao.accs.flowcontrol.FlowControl;
import com.taobao.accs.net.BaseConnection;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.ut.monitor.TrafficsMonitor;
import com.taobao.accs.ut.monitor.TrafficsMonitor.TrafficInfo;
import com.taobao.accs.ut.statistics.BindAppStatistic;
import com.taobao.accs.ut.statistics.BindUserStatistic;
import com.taobao.accs.ut.statistics.ReceiveMsgStat;
import com.taobao.accs.ut.statistics.SendAckStatistic;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.JsonUtility;
import com.taobao.accs.utl.MessageStreamReader;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.zip.GZIPInputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageHandler {
    private static final int MESSAGE_ID_CACHE_SIZE = 50;
    private String TAG = "MsgRecv_";
    private Map<String, AssembleMessage> assembleMessageMap = new HashMap();
    private LinkedHashMap<String, String> handledMessageId = new LinkedHashMap<String, String>() {
        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Entry<String, String> entry) {
            return size() > 50;
        }
    };
    public String mAccsDeviceToken = "";
    public AntiBrush mAntiBrush;
    public int mConnectType;
    private BaseConnection mConnection;
    private Context mContext;
    public FlowControl mFlowControl;
    private Message mLastSendMessage;
    private ReceiveMsgStat mReceiveMsgStat;
    private Runnable mRestoreTrafficsRunnable = new Runnable() {
        public void run() {
            if (MessageHandler.this.mTrafficMonitor != null) {
                MessageHandler.this.mTrafficMonitor.restoreTraffics();
            }
        }
    };
    protected TrafficsMonitor mTrafficMonitor;
    public ConcurrentMap<String, ScheduledFuture<?>> reqTasks = new ConcurrentHashMap();
    private ConcurrentMap<String, Message> unHandleMessage = new ConcurrentHashMap();
    private boolean unRevPing = false;

    private boolean isNetWorkError(int i) {
        return i == -1 || i == -9 || i == -10 || i == -11;
    }

    public MessageHandler(Context context, BaseConnection baseConnection) {
        String str;
        this.mContext = context;
        this.mConnection = baseConnection;
        this.mTrafficMonitor = new TrafficsMonitor(this.mContext);
        this.mFlowControl = new FlowControl(this.mContext);
        this.mAntiBrush = new AntiBrush(this.mContext);
        if (baseConnection == null) {
            str = this.TAG;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(this.TAG);
            sb.append(baseConnection.mConfigTag);
            str = sb.toString();
        }
        this.TAG = str;
        restoreMessageId();
        restoreTraffics();
    }

    public void onMessage(byte[] bArr) throws IOException {
        onMessage(bArr, null);
    }

    public void onMessage(byte[] bArr, String str) throws IOException {
        if (ALog.isPrintLog(Level.I)) {
            ALog.i(this.TAG, "onMessage", "host", str);
        }
        MessageStreamReader messageStreamReader = new MessageStreamReader(bArr);
        try {
            int readByte = messageStreamReader.readByte();
            int i = (readByte & 240) >> 4;
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.TAG, "version:".concat(String.valueOf(i)), new Object[0]);
            }
            int i2 = readByte & 15;
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.TAG, "compress:".concat(String.valueOf(i2)), new Object[0]);
            }
            messageStreamReader.readByte();
            int readShort = messageStreamReader.readShort();
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.TAG, "totalLen:".concat(String.valueOf(readShort)), new Object[0]);
            }
            int i3 = 0;
            while (i3 < readShort) {
                int readShort2 = messageStreamReader.readShort();
                int i4 = i3 + 2;
                if (readShort2 > 0) {
                    byte[] bArr2 = new byte[readShort2];
                    messageStreamReader.read(bArr2);
                    if (ALog.isPrintLog(Level.D)) {
                        String str2 = this.TAG;
                        StringBuilder sb = new StringBuilder("buf len:");
                        sb.append(bArr2.length);
                        ALog.d(str2, sb.toString(), new Object[0]);
                    }
                    i3 = i4 + bArr2.length;
                    handleMessage(i2, bArr2, str, i);
                } else {
                    throw new IOException("data format error");
                }
            }
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mConnectType);
            sb2.append(th.toString());
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", sb2.toString());
            ALog.e(this.TAG, "", th, new Object[0]);
        } finally {
            messageStreamReader.close();
        }
    }

    private void handleMessage(int i, byte[] bArr, String str, int i2) throws IOException {
        boolean z;
        byte[] bArr2;
        Map map;
        int i3;
        Exception exc;
        Map map2;
        boolean z2;
        Map map3;
        String str2;
        byte[] bArr3;
        int i4;
        String str3;
        ReqType reqType;
        String str4;
        int i5;
        byte[] bArr4;
        boolean z3;
        Map map4;
        byte[] bArr5;
        String str5;
        long j;
        boolean z4;
        String str6;
        long j2;
        int i6;
        String str7;
        int i7;
        String str8;
        int i8;
        Map map5;
        Map map6;
        boolean z5;
        byte[] bArr6;
        int i9 = i;
        byte[] bArr7 = bArr;
        String str9 = str;
        MessageStreamReader messageStreamReader = new MessageStreamReader(bArr7);
        long readShort = (long) messageStreamReader.readShort();
        if (ALog.isPrintLog(Level.D)) {
            String str10 = this.TAG;
            StringBuilder sb = new StringBuilder("flag:");
            sb.append(Integer.toHexString((int) readShort));
            ALog.d(str10, sb.toString(), new Object[0]);
        }
        String readString = messageStreamReader.readString(messageStreamReader.readByte());
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(this.TAG, "target:".concat(String.valueOf(readString)), new Object[0]);
        }
        String readString2 = messageStreamReader.readString(messageStreamReader.readByte());
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(this.TAG, "source:".concat(String.valueOf(readString2)), new Object[0]);
        }
        try {
            String readString3 = messageStreamReader.readString(messageStreamReader.readByte());
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.TAG, "dataId:".concat(String.valueOf(readString3)), new Object[0]);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(readString2);
            sb2.append(readString3);
            String sb3 = sb2.toString();
            if (messageStreamReader.available() > 0) {
                if (i2 == 2) {
                    Map<Integer, String> parseExtHeader = parseExtHeader(messageStreamReader);
                    if (parseExtHeader == null || !parseExtHeader.containsKey(Integer.valueOf(16)) || !parseExtHeader.containsKey(Integer.valueOf(17))) {
                        map6 = parseExtHeader;
                    } else {
                        map6 = parseExtHeader;
                        z5 = true;
                        if (i9 != 0 || z5) {
                            bArr6 = messageStreamReader.readAll();
                        } else if (i9 == 1) {
                            bArr6 = gzipInputStream(messageStreamReader);
                        } else {
                            z = z5;
                            map = map6;
                            bArr2 = null;
                        }
                        z = z5;
                        map = map6;
                        bArr2 = bArr6;
                    }
                } else {
                    map6 = null;
                }
                z5 = false;
                if (i9 != 0) {
                }
                bArr6 = messageStreamReader.readAll();
                z = z5;
                map = map6;
                bArr2 = bArr6;
            } else {
                map = null;
                bArr2 = null;
                z = false;
            }
            messageStreamReader.close();
            if (bArr2 == null) {
                try {
                    map2 = map;
                    ALog.d(this.TAG, "oriData is null", new Object[0]);
                } catch (Exception e) {
                    exc = e;
                    i3 = 0;
                }
            } else {
                map2 = map;
                if (ALog.isPrintLog(Level.D)) {
                    String str11 = this.TAG;
                    StringBuilder sb4 = new StringBuilder("oriData:");
                    sb4.append(String.valueOf(bArr2));
                    ALog.d(str11, sb4.toString(), new Object[0]);
                }
            }
            int valueOf = MsgType.valueOf((int) ((readShort >> 15) & 1));
            ReqType valueOf2 = ReqType.valueOf((int) ((readShort >> 13) & 3));
            String str12 = readString2;
            int i10 = (int) ((readShort >> 12) & 1);
            int valueOf3 = MsgResType.valueOf((int) ((readShort >> 11) & 1));
            boolean z6 = ((int) ((readShort >> 6) & 1)) == 1;
            long j3 = readShort;
            if (ALog.isPrintLog(Level.I)) {
                ALog.i(this.TAG, "handleMessage", Constants.KEY_DATA_ID, readString3, "type", MsgType.name(valueOf), "reqType", valueOf2.name(), "resType", MsgResType.name(valueOf3), "target", readString);
            }
            if (valueOf == 1) {
                if (valueOf2 != ReqType.ACK) {
                    if (valueOf2 != ReqType.RES) {
                        reqType = valueOf2;
                        i5 = valueOf;
                        bArr3 = bArr2;
                        str2 = sb3;
                        i4 = valueOf3;
                        z2 = z6;
                        map3 = map2;
                        str4 = str;
                        str3 = readString3;
                    }
                }
                Message message = (Message) this.unHandleMessage.remove(readString3);
                if (message != null) {
                    if (ALog.isPrintLog(Level.D)) {
                        i7 = valueOf;
                        str7 = sb3;
                        i3 = 0;
                        try {
                            ALog.d(this.TAG, "handleMessage reqMessage not null", new Object[0]);
                        } catch (Exception e2) {
                            e = e2;
                            exc = e;
                            ALog.e(this.TAG, "handleMessage", exc, new Object[i3]);
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(this.mConnectType);
                            sb5.append(exc.toString());
                            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", sb5.toString());
                        }
                    } else {
                        i7 = valueOf;
                        str7 = sb3;
                    }
                    int i11 = 200;
                    if (i10 == 1) {
                        try {
                            i11 = new JSONObject(new String(bArr2)).getInt("code");
                        } catch (Exception unused) {
                            i11 = -3;
                        }
                    }
                    if (message.getNetPermanceMonitor() != null) {
                        message.getNetPermanceMonitor().onRecAck();
                    }
                    if (valueOf2 == ReqType.RES) {
                        reqType = valueOf2;
                        map5 = map2;
                        i4 = valueOf3;
                        bArr3 = bArr2;
                        i8 = i7;
                        str2 = str7;
                        str8 = readString3;
                        onResult(message, i11, reqType, bArr3, map5);
                    } else {
                        reqType = valueOf2;
                        bArr3 = bArr2;
                        str8 = readString3;
                        i4 = valueOf3;
                        map5 = map2;
                        i8 = i7;
                        str2 = str7;
                        onResult(message, i11, map5);
                    }
                    map3 = map5;
                    str4 = str;
                    z2 = z6;
                    TrafficInfo trafficInfo = r1;
                    TrafficInfo trafficInfo2 = new TrafficInfo(message.serviceId, m.h(), str4, (long) bArr7.length);
                    addTrafficsInfo(trafficInfo);
                    i5 = i8;
                    str3 = str8;
                } else {
                    reqType = valueOf2;
                    bArr3 = bArr2;
                    str2 = sb3;
                    i4 = valueOf3;
                    z2 = z6;
                    map3 = map2;
                    str4 = str;
                    str3 = readString3;
                    ALog.e(this.TAG, "handleMessage data ack/res reqMessage is null", Constants.KEY_DATA_ID, str3);
                    i5 = valueOf;
                }
            } else {
                reqType = valueOf2;
                bArr3 = bArr2;
                str2 = sb3;
                i4 = valueOf3;
                z2 = z6;
                map3 = map2;
                str4 = str;
                str3 = readString3;
                i5 = valueOf;
            }
            if (i5 == 0 && reqType == ReqType.RES) {
                Message message2 = (Message) this.unHandleMessage.remove(str3);
                if (message2 != null) {
                    handleControlMessage(message2, bArr3, bArr7, str4);
                    return;
                }
                bArr4 = bArr3;
                ALog.e(this.TAG, "handleMessage contorl ACK reqMessage is null", Constants.KEY_DATA_ID, str3);
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(this.TAG, "handleMessage not handled", com.autonavi.minimap.ajx3.util.Constants.BODY, new String(bArr4));
                }
            } else {
                bArr4 = bArr3;
            }
            if (i5 == 1) {
                if (reqType == ReqType.DATA && readString != null) {
                    String[] split = readString.split("\\|");
                    if (split != null) {
                        if (split.length >= 2) {
                            if (ALog.isPrintLog(Level.D)) {
                                z3 = z;
                                ALog.d(this.TAG, "handleMessage onPush", "isBurstData", Boolean.valueOf(z3));
                            } else {
                                z3 = z;
                            }
                            try {
                                if (this.mReceiveMsgStat != null) {
                                    this.mReceiveMsgStat.commitUT();
                                }
                                this.mReceiveMsgStat = new ReceiveMsgStat();
                                this.mReceiveMsgStat.receiveDate = String.valueOf(System.currentTimeMillis());
                                if (UtilityImpl.packageExist(this.mContext, split[1])) {
                                    String str13 = split.length >= 3 ? split[2] : null;
                                    this.mReceiveMsgStat.serviceId = str13;
                                    String str14 = str2;
                                    if (isDuplicateMessage(str14)) {
                                        ALog.e(this.TAG, "handleMessage msg duplicate", Constants.KEY_DATA_ID, str3);
                                        this.mReceiveMsgStat.repeat = true;
                                        str6 = readString;
                                        str5 = str12;
                                        j2 = j3;
                                        i6 = i4;
                                        map4 = map3;
                                        z4 = z2;
                                    } else {
                                        if (z3) {
                                            map4 = map3;
                                            bArr5 = putBurstMessage(str14, map4, bArr4);
                                            if (bArr5 != null) {
                                                if (i9 == 1) {
                                                    MessageStreamReader messageStreamReader2 = new MessageStreamReader(bArr5);
                                                    bArr5 = gzipInputStream(messageStreamReader2);
                                                    if (ALog.isPrintLog(Level.D)) {
                                                        ALog.d(this.TAG, "handleMessage gzip completeOriData", Constants.KEY_DATA_ID, str14, "length", Integer.valueOf(bArr5.length));
                                                    }
                                                    messageStreamReader2.close();
                                                }
                                            } else {
                                                return;
                                            }
                                        } else {
                                            map4 = map3;
                                            bArr5 = bArr4;
                                        }
                                        recordMessageId(str14);
                                        if ("accs".equals(str13)) {
                                            ALog.e(this.TAG, "handleMessage try deliverMsg", Constants.KEY_DATA_ID, str3, "target", split[1], "serviceId", str13);
                                        } else if (ALog.isPrintLog(Level.I)) {
                                            ALog.i(this.TAG, "handleMessage try deliverMsg", Constants.KEY_DATA_ID, str3, "target", split[1], "serviceId", str13);
                                        }
                                        Intent intent = new Intent(Constants.ACTION_RECEIVE);
                                        intent.setPackage(split[1]);
                                        intent.putExtra("command", 101);
                                        if (split.length >= 3) {
                                            intent.putExtra("serviceId", split[2]);
                                        }
                                        String str15 = "";
                                        if (split.length >= 4) {
                                            str15 = split[3];
                                            intent.putExtra("userInfo", str15);
                                        }
                                        intent.putExtra("data", bArr5);
                                        intent.putExtra(Constants.KEY_DATA_ID, str3);
                                        intent.putExtra("packageName", this.mContext.getPackageName());
                                        intent.putExtra("host", str4);
                                        intent.putExtra(Constants.KEY_CONN_TYPE, this.mConnectType);
                                        boolean z7 = z2;
                                        intent.putExtra(Constants.KEY_NEED_BUSINESS_ACK, z7);
                                        intent.putExtra("appKey", this.mConnection.getAppkey());
                                        intent.putExtra(Constants.KEY_CONFIG_TAG, this.mConnection.mConfigTag);
                                        putExtHeaderToIntent(map4, intent);
                                        if (z7) {
                                            j = j3;
                                            str5 = str12;
                                            putBusinessAckInfoToIntent(intent, str5, readString, (short) ((int) j));
                                        } else {
                                            str5 = str12;
                                            j = j3;
                                        }
                                        MsgDistribute.distribMessage(this.mContext, intent);
                                        UTMini instance = UTMini.getInstance();
                                        StringBuilder sb6 = new StringBuilder("serviceId=");
                                        sb6.append(str13);
                                        sb6.append(" dataId=");
                                        sb6.append(str3);
                                        instance.commitEvent(66001, "MsgToBussPush", "commandId=101", sb6.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
                                        long j4 = j;
                                        z4 = z7;
                                        str6 = readString;
                                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_TO_BUSS, "1commandId=101serviceId=".concat(String.valueOf(str13)), 0.0d);
                                        this.mReceiveMsgStat.dataId = str3;
                                        this.mReceiveMsgStat.userId = str15;
                                        ReceiveMsgStat receiveMsgStat = this.mReceiveMsgStat;
                                        StringBuilder sb7 = new StringBuilder();
                                        sb7.append(bArr5 == null ? 0 : bArr5.length);
                                        receiveMsgStat.dataLen = sb7.toString();
                                        this.mReceiveMsgStat.deviceId = UtilityImpl.getDeviceId(this.mContext);
                                        this.mReceiveMsgStat.toBzDate = String.valueOf(System.currentTimeMillis());
                                        TrafficInfo trafficInfo3 = r1;
                                        j2 = j4;
                                        TrafficInfo trafficInfo4 = new TrafficInfo(str13, m.h(), str4, (long) bArr7.length);
                                        addTrafficsInfo(trafficInfo3);
                                        i6 = i4;
                                    }
                                    if (i6 == 1) {
                                        if ("accs".equals(str13)) {
                                            ALog.e(this.TAG, "handleMessage try sendAck dataId", Constants.KEY_DATA_ID, str3);
                                        } else {
                                            ALog.i(this.TAG, "handleMessage try sendAck dataId", Constants.KEY_DATA_ID, str3);
                                        }
                                        boolean z8 = z4;
                                        String str16 = str13;
                                        Map map7 = map4;
                                        i3 = 0;
                                        Message buildPushAck = Message.buildPushAck(this.mConnection, str6, str5, str3, false, (short) ((int) j2), str, map4);
                                        this.mConnection.send(buildPushAck, true);
                                        utStatSendAck(buildPushAck.dataId, str16);
                                        if (z8) {
                                            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_ACK, "", 0.0d);
                                        }
                                    }
                                    return;
                                }
                                ALog.e(this.TAG, "handleMessage not exist, unbind it", "package", split[1]);
                                this.mConnection.send(Message.buildUnbindApp(this.mConnection, this.mContext, split[1], null, null, null), true);
                            } catch (Exception e3) {
                                e = e3;
                                i3 = 0;
                                exc = e;
                                ALog.e(this.TAG, "handleMessage", exc, new Object[i3]);
                                StringBuilder sb52 = new StringBuilder();
                                sb52.append(this.mConnectType);
                                sb52.append(exc.toString());
                                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", sb52.toString());
                            }
                        }
                    }
                }
            }
        } catch (Exception e4) {
            Exception exc2 = e4;
            String str17 = this.TAG;
            StringBuilder sb8 = new StringBuilder("dataId read error ");
            sb8.append(exc2.toString());
            ALog.e(str17, sb8.toString(), new Object[0]);
            messageStreamReader.close();
            StringBuilder sb9 = new StringBuilder();
            sb9.append(this.mConnectType);
            sb9.append("data id read error");
            sb9.append(exc2.toString());
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", sb9.toString());
        }
    }

    private byte[] gzipInputStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = gZIPInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("uncompress data error ");
            sb.append(e.toString());
            ALog.e(str, sb.toString(), new Object[0]);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mConnectType);
            sb2.append(" uncompress data error ");
            sb2.append(e.toString());
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", sb2.toString());
            return null;
        } finally {
            try {
                gZIPInputStream.close();
                byteArrayOutputStream.close();
            } catch (Exception unused) {
            }
        }
    }

    private void handleControlMessage(Message message, byte[] bArr, byte[] bArr2, String str) {
        byte[] bArr3;
        int i;
        Throwable th;
        Message message2 = message;
        try {
            bArr3 = bArr;
            try {
                JSONObject jSONObject = new JSONObject(new String(bArr3));
                if (ALog.isPrintLog(Level.D)) {
                    ALog.d(this.TAG, "handleControlMessage parse", "json", jSONObject.toString());
                }
                i = jSONObject.getInt("code");
                if (i == 200) {
                    try {
                        switch (message2.command.intValue()) {
                            case 1:
                                UtilityImpl.saveUtdid(Constants.SP_FILE_NAME, this.mContext);
                                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                                this.mAccsDeviceToken = JsonUtility.getString(jSONObject2, Constants.KEY_DEVICE_TOKEN, null);
                                if (jSONObject2 != null) {
                                    JSONArray jSONArray = jSONObject2.getJSONArray(Constants.KEY_PACKAGE_NAMES);
                                    if (jSONArray != null) {
                                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                            String string = jSONArray.getString(i2);
                                            if (UtilityImpl.packageExist(this.mContext, string)) {
                                                this.mConnection.getClientManager().onAppBind(message2.packageName);
                                            } else {
                                                ALog.e(this.TAG, "unbind app", "pkg", string);
                                                this.mConnection.send(Message.buildUnbindApp(this.mConnection, this.mContext, string, null, null, null), true);
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            case 2:
                                this.mConnection.getClientManager().onAppUnbind(message2.packageName);
                                break;
                            case 3:
                                this.mConnection.getClientManager().onUserBind(message2.packageName, message2.userinfo);
                                break;
                            case 4:
                                this.mConnection.getClientManager().onUserUnBind(message2.packageName, message2.userinfo);
                                break;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        ALog.e(this.TAG, "handleControlMessage", th, new Object[0]);
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mConnectType);
                        sb.append(th.toString());
                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "handleControlMessage", "", sb.toString());
                    }
                } else if (message2.command.intValue() == 3 && i == 300) {
                    this.mConnection.getClientManager().onAppUnbind(message2.packageName);
                }
            } catch (Throwable th3) {
                th = th3;
                th = th;
                i = -8;
                ALog.e(this.TAG, "handleControlMessage", th, new Object[0]);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mConnectType);
                sb2.append(th.toString());
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "handleControlMessage", "", sb2.toString());
                onResult(message2, i, null, bArr3, null);
                TrafficInfo trafficInfo = new TrafficInfo(message2.serviceId, m.h(), str, (long) bArr2.length);
                addTrafficsInfo(trafficInfo);
            }
        } catch (Throwable th4) {
            th = th4;
            bArr3 = bArr;
            th = th;
            i = -8;
            ALog.e(this.TAG, "handleControlMessage", th, new Object[0]);
            StringBuilder sb22 = new StringBuilder();
            sb22.append(this.mConnectType);
            sb22.append(th.toString());
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "handleControlMessage", "", sb22.toString());
            onResult(message2, i, null, bArr3, null);
            TrafficInfo trafficInfo2 = new TrafficInfo(message2.serviceId, m.h(), str, (long) bArr2.length);
            addTrafficsInfo(trafficInfo2);
        }
        onResult(message2, i, null, bArr3, null);
        TrafficInfo trafficInfo22 = new TrafficInfo(message2.serviceId, m.h(), str, (long) bArr2.length);
        addTrafficsInfo(trafficInfo22);
    }

    private Map<Integer, String> parseExtHeader(MessageStreamReader messageStreamReader) {
        Map<Integer, String> map;
        if (messageStreamReader == null) {
            return null;
        }
        try {
            int readShort = messageStreamReader.readShort();
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(this.TAG, "extHeaderLen:".concat(String.valueOf(readShort)), new Object[0]);
            }
            map = null;
            int i = 0;
            while (i < readShort) {
                try {
                    int readShort2 = messageStreamReader.readShort();
                    int i2 = (64512 & readShort2) >> 10;
                    int i3 = readShort2 & Message.EXT_HEADER_VALUE_MAX_LEN;
                    String readString = messageStreamReader.readString(i3);
                    i = i + 2 + i3;
                    if (map == null) {
                        map = new HashMap<>();
                    }
                    map.put(Integer.valueOf(i2), readString);
                    if (ALog.isPrintLog(Level.D)) {
                        ALog.d(this.TAG, "", "extHeaderType", Integer.valueOf(i2), "value", readString);
                    }
                } catch (Exception e) {
                    e = e;
                    ALog.e(this.TAG, "parseExtHeader", e, new Object[0]);
                    return map;
                }
            }
        } catch (Exception e2) {
            e = e2;
            map = null;
            ALog.e(this.TAG, "parseExtHeader", e, new Object[0]);
            return map;
        }
        return map;
    }

    public void onResult(Message message, int i) {
        onResult(message, i, null, null, null);
    }

    public void onResult(Message message, int i, Map<Integer, String> map) {
        onResult(message, i, null, null, map);
    }

    public void onResult(Message message, int i, ReqType reqType, byte[] bArr, Map<Integer, String> map) {
        byte[] bArr2;
        ReqType reqType2;
        int i2;
        Message message2 = message;
        if (message2.command == null || message.getType() < 0 || message.getType() == 2) {
            ALog.d(this.TAG, "onError, skip ping/ack", new Object[0]);
            return;
        }
        if (message2.cunstomDataId != null) {
            this.reqTasks.remove(message2.cunstomDataId);
        }
        Map<Integer, String> map2 = map;
        String str = null;
        if (this.mAntiBrush.checkAntiBrush(message2.host, map2)) {
            i2 = ErrorCode.SERVIER_ANTI_BRUSH;
            map2 = null;
            reqType2 = null;
            bArr2 = null;
        } else {
            i2 = i;
            reqType2 = reqType;
            bArr2 = bArr;
        }
        int updateFlowCtrlInfo = this.mFlowControl.updateFlowCtrlInfo(map2, message2.serviceId);
        if (updateFlowCtrlInfo != 0) {
            int i3 = updateFlowCtrlInfo == 2 ? ErrorCode.SERVIER_HIGH_LIMIT : updateFlowCtrlInfo == 3 ? ErrorCode.SERVIER_HIGH_LIMIT_BRUSH : ErrorCode.SERVIER_LOW_LIMIT;
            map2 = null;
            reqType2 = null;
            bArr2 = null;
        }
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(this.TAG, "onResult", "command", message2.command, "erorcode", Integer.valueOf(i2));
        }
        if (message2.command.intValue() != 102) {
            if (message2.isCancel) {
                ALog.e(this.TAG, "onResult message is cancel", "command", message2.command);
            } else if (!isNetWorkError(i2) || message2.command.intValue() == 100 || message2.retryTimes > Message.CONTROL_MAX_RETRY_TIMES) {
                Intent buildBaseReceiveIntent = buildBaseReceiveIntent(message);
                buildBaseReceiveIntent.putExtra("errorCode", i2);
                ReqType valueOf = ReqType.valueOf((message2.flags >> 13) & 3);
                if (reqType2 == ReqType.RES || valueOf == ReqType.REQ) {
                    buildBaseReceiveIntent.putExtra(Constants.KEY_SEND_TYPE, "res");
                }
                if (i2 == 200) {
                    buildBaseReceiveIntent.putExtra("data", bArr2);
                }
                buildBaseReceiveIntent.putExtra("appKey", this.mConnection.mAppkey);
                buildBaseReceiveIntent.putExtra(Constants.KEY_CONFIG_TAG, this.mConnection.mConfigTag);
                putExtHeaderToIntent(map2, buildBaseReceiveIntent);
                MsgDistribute.distribMessage(this.mContext, buildBaseReceiveIntent);
                if (!TextUtils.isEmpty(message2.serviceId)) {
                    UTMini instance = UTMini.getInstance();
                    StringBuilder sb = new StringBuilder("commandId=");
                    sb.append(message2.command);
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder("serviceId=");
                    sb3.append(message2.serviceId);
                    sb3.append(" errorCode=");
                    sb3.append(i2);
                    sb3.append(" dataId=");
                    sb3.append(message2.dataId);
                    instance.commitEvent(66001, "MsgToBuss0", sb2, sb3.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
                    StringBuilder sb4 = new StringBuilder("1commandId=");
                    sb4.append(message2.command);
                    sb4.append("serviceId=");
                    sb4.append(message2.serviceId);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_TO_BUSS, sb4.toString(), 0.0d);
                }
            } else {
                message2.startSendTime = System.currentTimeMillis();
                message2.retryTimes++;
                ALog.d(this.TAG, "onResult", "retryTimes", Integer.valueOf(message2.retryTimes));
                this.mConnection.send(message2, true);
            }
            NetPerformanceMonitor netPermanceMonitor = message.getNetPermanceMonitor();
            if (netPermanceMonitor != null) {
                netPermanceMonitor.onToBizDate();
                if (message2.host != null) {
                    str = message2.host.toString();
                }
                if (i2 == 200) {
                    netPermanceMonitor.setRet(true);
                    if (message2.retryTimes > 0) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "succ", 0.0d);
                        StringBuilder sb5 = new StringBuilder("succ_");
                        sb5.append(message2.retryTimes);
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, sb5.toString(), 0.0d);
                    } else {
                        AppMonitorAdapter.commitAlarmSuccess("accs", BaseMonitor.ALARM_POINT_REQUEST, str);
                    }
                } else {
                    if (message2.retryTimes > 0) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "fail＿".concat(String.valueOf(i2)), 0.0d);
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, UploadDataResult.FAIL_MSG, 0.0d);
                    } else if (i2 != -13) {
                        String int2String = UtilityImpl.int2String(i2);
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(this.mConnectType);
                        sb6.append(message2.serviceId);
                        sb6.append(message2.timeout);
                        AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQUEST, str, int2String, sb6.toString());
                    }
                    netPermanceMonitor.setRet(false);
                    netPermanceMonitor.setFailReason(i2);
                }
                x.a().a((StatObject) message.getNetPermanceMonitor());
            }
            utStat(message2, i2);
        }
    }

    public void onSendPing() {
        ALog.d(this.TAG, "onSendPing", new Object[0]);
        synchronized (MessageHandler.class) {
            this.unRevPing = true;
        }
    }

    public void onRcvPing() {
        ALog.d(this.TAG, "onRcvPing", new Object[0]);
        synchronized (MessageHandler.class) {
            this.unRevPing = false;
        }
    }

    public boolean getUnrcvPing() {
        return this.unRevPing;
    }

    public void onSend(Message message) {
        if (!(this.mLastSendMessage == null || message.cunstomDataId == null || message.serviceId == null || this.mLastSendMessage.cunstomDataId != message.cunstomDataId || this.mLastSendMessage.serviceId != message.serviceId)) {
            UTMini.getInstance().commitEvent(66001, "SEND_REPEAT", message.serviceId, message.cunstomDataId, Long.valueOf(Thread.currentThread().getId()));
        }
        if (message.getType() != -1 && message.getType() != 2 && !message.isAck) {
            this.unHandleMessage.put(message.getDataId(), message);
        }
    }

    public void onNetworkFail(int i) {
        this.unRevPing = false;
        String[] strArr = (String[]) this.unHandleMessage.keySet().toArray(new String[0]);
        if (strArr != null && strArr.length > 0) {
            ALog.d(this.TAG, "onNetworkFail", new Object[0]);
            for (String remove : strArr) {
                Message message = (Message) this.unHandleMessage.remove(remove);
                if (message != null) {
                    onResult(message, i);
                }
            }
        }
    }

    public void cancelControlMessage(Message message) {
        if (this.unHandleMessage.keySet() != null && this.unHandleMessage.keySet().size() > 0) {
            for (String str : this.unHandleMessage.keySet()) {
                Message message2 = (Message) this.unHandleMessage.get(str);
                if (!(message2 == null || message2.command == null || !message2.getPackageName().equals(message.getPackageName()))) {
                    switch (message.command.intValue()) {
                        case 1:
                        case 2:
                            if (message2.command.intValue() == 1 || message2.command.intValue() == 2) {
                                message2.isCancel = true;
                                break;
                            }
                        case 3:
                        case 4:
                            if (message2.command.intValue() == 3 || message2.command.intValue() == 4) {
                                message2.isCancel = true;
                                break;
                            }
                        case 5:
                        case 6:
                            if (message2.command.intValue() == 5 || message2.command.intValue() == 6) {
                                message2.isCancel = true;
                                break;
                            }
                    }
                }
                if (message2 != null && message2.isCancel) {
                    ALog.e(this.TAG, "cancelControlMessage", "command", message2.command);
                }
            }
        }
    }

    public int getUnhandledCount() {
        return this.unHandleMessage.size();
    }

    public Collection<Message> getUnhandledMessages() {
        return this.unHandleMessage.values();
    }

    public Message getUnhandledMessage(String str) {
        return (Message) this.unHandleMessage.get(str);
    }

    public Message removeUnhandledMessage(String str) {
        if (!TextUtils.isEmpty(str)) {
            return (Message) this.unHandleMessage.remove(str);
        }
        return null;
    }

    private boolean isDuplicateMessage(String str) {
        if (!TextUtils.isEmpty(str) && this.handledMessageId.containsKey(str)) {
            return true;
        }
        return false;
    }

    private byte[] putBurstMessage(String str, Map<Integer, String> map, byte[] bArr) {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    int parseInt = Integer.parseInt(map.get(Integer.valueOf(17)));
                    int parseInt2 = Integer.parseInt(map.get(Integer.valueOf(16)));
                    if (parseInt2 <= 1) {
                        throw new RuntimeException("burstNums <= 1");
                    }
                    if (parseInt >= 0) {
                        if (parseInt < parseInt2) {
                            String str2 = map.get(Integer.valueOf(18));
                            long j = 0;
                            String str3 = map.get(Integer.valueOf(15));
                            if (!TextUtils.isEmpty(str3)) {
                                j = Long.parseLong(str3);
                            }
                            AssembleMessage assembleMessage = this.assembleMessageMap.get(str);
                            if (assembleMessage == null) {
                                if (ALog.isPrintLog(Level.I)) {
                                    ALog.i(this.TAG, "putBurstMessage", Constants.KEY_DATA_ID, str, "burstLength", Integer.valueOf(parseInt2));
                                }
                                assembleMessage = new AssembleMessage(str, parseInt2, str2);
                                assembleMessage.setTimeOut(j);
                                this.assembleMessageMap.put(str, assembleMessage);
                            }
                            return assembleMessage.putBurst(parseInt, parseInt2, bArr);
                        }
                    }
                    throw new RuntimeException(String.format("burstNums:%s burstIndex:%s", new Object[]{Integer.valueOf(parseInt2), Integer.valueOf(parseInt)}));
                }
            } catch (Throwable th) {
                ALog.w(this.TAG, "putBurstMessage", th, new Object[0]);
                return null;
            }
        }
        throw new RuntimeException("burstLength == 0");
    }

    private void recordMessageId(String str) {
        if (!TextUtils.isEmpty(str) && !this.handledMessageId.containsKey(str)) {
            this.handledMessageId.put(str, str);
            saveMessageId();
        }
    }

    private void restoreMessageId() {
        try {
            File dir = this.mContext.getDir("accs", 0);
            StringBuilder sb = new StringBuilder("message");
            sb.append(this.mConnection.getAppkey());
            File file = new File(dir, sb.toString());
            if (!file.exists()) {
                ALog.d(this.TAG, "message file not exist", new Object[0]);
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    this.handledMessageId.put(readLine, readLine);
                } else {
                    bufferedReader.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMessageId() {
        try {
            File dir = this.mContext.getDir("accs", 0);
            StringBuilder sb = new StringBuilder("message");
            sb.append(this.mConnection.getAppkey());
            FileWriter fileWriter = new FileWriter(new File(dir, sb.toString()));
            fileWriter.write("");
            for (String append : this.handledMessageId.keySet()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(append);
                sb2.append(MultipartUtility.LINE_FEED);
                fileWriter.append(sb2.toString());
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Intent buildBaseReceiveIntent(Message message) {
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(message.packageName);
        intent.putExtra("command", message.command);
        intent.putExtra("serviceId", message.serviceId);
        intent.putExtra("userInfo", message.userinfo);
        if (message.command != null && message.command.intValue() == 100) {
            intent.putExtra(Constants.KEY_DATA_ID, message.cunstomDataId);
        }
        return intent;
    }

    private void putExtHeaderToIntent(Map<Integer, String> map, Intent intent) {
        if (map != null && intent != null) {
            intent.putExtra(ExtraInfo.EXT_HEADER, (HashMap) map);
        }
    }

    private void putBusinessAckInfoToIntent(Intent intent, String str, String str2, short s) {
        if (intent != null) {
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("source", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                intent.putExtra("target", str2);
            }
            intent.putExtra(Constants.KEY_FLAGS, s);
        }
    }

    public void setReceiveMsgStat(ReceiveMsgStat receiveMsgStat) {
        this.mReceiveMsgStat = receiveMsgStat;
    }

    public ReceiveMsgStat getReceiveMsgStat() {
        return this.mReceiveMsgStat;
    }

    private void utStat(Message message, int i) {
        if (message != null) {
            String deviceId = UtilityImpl.getDeviceId(this.mContext);
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            String sb2 = sb.toString();
            boolean z = i == 200;
            int intValue = message.command.intValue();
            if (intValue != 1) {
                if (intValue == 3) {
                    BindUserStatistic bindUserStatistic = new BindUserStatistic();
                    bindUserStatistic.deviceId = deviceId;
                    bindUserStatistic.time = sb2;
                    bindUserStatistic.ret = z;
                    bindUserStatistic.userId = message.userinfo;
                    bindUserStatistic.setFailReason(i);
                    bindUserStatistic.commitUT();
                }
                return;
            }
            BindAppStatistic bindAppStatistic = new BindAppStatistic();
            bindAppStatistic.deviceId = deviceId;
            bindAppStatistic.time = sb2;
            bindAppStatistic.ret = z;
            bindAppStatistic.setFailReason(i);
            bindAppStatistic.commitUT();
        }
    }

    private void utStatSendAck(String str, String str2) {
        SendAckStatistic sendAckStatistic = new SendAckStatistic();
        sendAckStatistic.deviceId = UtilityImpl.getDeviceId(this.mContext);
        sendAckStatistic.dataId = str;
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sendAckStatistic.sendTime = sb.toString();
        sendAckStatistic.failReason = "";
        sendAckStatistic.serviceId = str2;
        sendAckStatistic.sessionId = "";
        sendAckStatistic.commitUT();
    }

    public void restoreTraffics() {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(this.mRestoreTrafficsRunnable);
        } catch (Throwable th) {
            ALog.e(this.TAG, "restoreTraffics", th, new Object[0]);
        }
    }

    public void addTrafficsInfo(final TrafficInfo trafficInfo) {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new Runnable() {
                public void run() {
                    if (MessageHandler.this.mTrafficMonitor != null) {
                        MessageHandler.this.mTrafficMonitor.addTrafficInfo(trafficInfo);
                    }
                }
            });
        } catch (Throwable th) {
            ALog.e(this.TAG, "addTrafficsInfo", th, new Object[0]);
        }
    }
}
