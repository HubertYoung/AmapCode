package com.alipay.android.phone.inside.commonbiz.report.model;

import com.alipay.android.phone.inside.protobuf.wire.Message;
import com.alipay.android.phone.inside.protobuf.wire.Message.Datatype;
import com.alipay.android.phone.inside.protobuf.wire.Message.Label;
import com.alipay.android.phone.inside.protobuf.wire.ProtoField;
import java.util.Collections;
import java.util.List;

public final class DeviceLocationReqPbPB extends Message {
    public static final String DEFAULT_ACCESSWIRELESSNETTYPE = "";
    public static final Double DEFAULT_ACCURACY = Double.valueOf(0.0d);
    public static final Double DEFAULT_ALTITUDE = Double.valueOf(0.0d);
    public static final String DEFAULT_APDID = "";
    public static final String DEFAULT_APDIDTOKEN = "";
    public static final String DEFAULT_BLUETOOTHMAC = "";
    public static final Boolean DEFAULT_BLUETOOTHOPEN = Boolean.FALSE;
    public static final List<CDMAInfoPbPB> DEFAULT_CDMAINFOS = Collections.emptyList();
    public static final Boolean DEFAULT_CELLCONN = Boolean.FALSE;
    public static final CellTypeEnumPbPB DEFAULT_CELLTYPE = CellTypeEnumPbPB.GSM;
    public static final String DEFAULT_CURRENTMOBILEOPERATOR = "";
    public static final Double DEFAULT_DIRECTION = Double.valueOf(0.0d);
    public static final List<DeviceLocationExtraInfoPbPB> DEFAULT_EXTRAINFOS = Collections.emptyList();
    public static final List<GSMInfoPbPB> DEFAULT_GSMINFOS = Collections.emptyList();
    public static final String DEFAULT_IMEI = "";
    public static final String DEFAULT_IMSI = "";
    public static final Double DEFAULT_LATITUDE = Double.valueOf(0.0d);
    public static final Boolean DEFAULT_LBSOPEN = Boolean.FALSE;
    public static final Double DEFAULT_LONGITUDE = Double.valueOf(0.0d);
    public static final String DEFAULT_OS = "";
    public static final String DEFAULT_OSVERSION = "";
    public static final Boolean DEFAULT_QUERYLBS = Boolean.FALSE;
    public static final String DEFAULT_SOURCE = "";
    public static final Double DEFAULT_SPEED = Double.valueOf(0.0d);
    public static final String DEFAULT_UMIDTOKEN = "";
    public static final String DEFAULT_VIEWID = "";
    public static final String DEFAULT_VOICEOVER = "";
    public static final Boolean DEFAULT_WIFICONN = Boolean.FALSE;
    public static final Boolean DEFAULT_WIFICONNECTBYPASSWORD = Boolean.FALSE;
    public static final List<WifiInfoPbPB> DEFAULT_WIFIINFOS = Collections.emptyList();
    public static final int TAG_ACCESSWIRELESSNETTYPE = 19;
    public static final int TAG_ACCURACY = 14;
    public static final int TAG_ALTITUDE = 9;
    public static final int TAG_APDID = 1;
    public static final int TAG_APDIDTOKEN = 30;
    public static final int TAG_BLUETOOTHMAC = 20;
    public static final int TAG_BLUETOOTHOPEN = 21;
    public static final int TAG_CDMAINFOS = 28;
    public static final int TAG_CELLCONN = 25;
    public static final int TAG_CELLTYPE = 26;
    public static final int TAG_CURRENTMOBILEOPERATOR = 18;
    public static final int TAG_DIRECTION = 11;
    public static final int TAG_EXTRAINFOS = 2;
    public static final int TAG_GSMINFOS = 27;
    public static final int TAG_IMEI = 5;
    public static final int TAG_IMSI = 6;
    public static final int TAG_LATITUDE = 13;
    public static final int TAG_LBSOPEN = 16;
    public static final int TAG_LONGITUDE = 12;
    public static final int TAG_OS = 4;
    public static final int TAG_OSVERSION = 24;
    public static final int TAG_QUERYLBS = 23;
    public static final int TAG_SOURCE = 22;
    public static final int TAG_SPEED = 10;
    public static final int TAG_TIDINFO = 29;
    public static final int TAG_UMIDTOKEN = 31;
    public static final int TAG_VIEWID = 7;
    public static final int TAG_VOICEOVER = 8;
    public static final int TAG_WIFICONN = 15;
    public static final int TAG_WIFICONNECTBYPASSWORD = 17;
    public static final int TAG_WIFIINFOS = 3;
    @ProtoField(tag = 19, type = Datatype.STRING)
    public String accessWirelessNetType;
    @ProtoField(tag = 14, type = Datatype.DOUBLE)
    public Double accuracy;
    @ProtoField(tag = 9, type = Datatype.DOUBLE)
    public Double altitude;
    @ProtoField(tag = 1, type = Datatype.STRING)
    public String apdid;
    @ProtoField(tag = 30, type = Datatype.STRING)
    public String apdidToken;
    @ProtoField(tag = 20, type = Datatype.STRING)
    public String bluetoothMac;
    @ProtoField(tag = 21, type = Datatype.BOOL)
    public Boolean bluetoothOpen;
    @ProtoField(label = Label.REPEATED, tag = 28)
    public List<CDMAInfoPbPB> cdmaInfos;
    @ProtoField(tag = 25, type = Datatype.BOOL)
    public Boolean cellConn;
    @ProtoField(tag = 26, type = Datatype.ENUM)
    public CellTypeEnumPbPB cellType;
    @ProtoField(tag = 18, type = Datatype.STRING)
    public String currentMobileOperator;
    @ProtoField(tag = 11, type = Datatype.DOUBLE)
    public Double direction;
    @ProtoField(label = Label.REPEATED, tag = 2)
    public List<DeviceLocationExtraInfoPbPB> extraInfos;
    @ProtoField(label = Label.REPEATED, tag = 27)
    public List<GSMInfoPbPB> gsmInfos;
    @ProtoField(tag = 5, type = Datatype.STRING)
    public String imei;
    @ProtoField(tag = 6, type = Datatype.STRING)
    public String imsi;
    @ProtoField(tag = 13, type = Datatype.DOUBLE)
    public Double latitude;
    @ProtoField(tag = 16, type = Datatype.BOOL)
    public Boolean lbsOpen;
    @ProtoField(tag = 12, type = Datatype.DOUBLE)
    public Double longitude;
    @ProtoField(tag = 4, type = Datatype.STRING)
    public String os;
    @ProtoField(tag = 24, type = Datatype.STRING)
    public String osVersion;
    @ProtoField(tag = 23, type = Datatype.BOOL)
    public Boolean queryLbs;
    @ProtoField(tag = 22, type = Datatype.STRING)
    public String source;
    @ProtoField(tag = 10, type = Datatype.DOUBLE)
    public Double speed;
    @ProtoField(tag = 29)
    public TidInfoPbPB tidInfo;
    @ProtoField(tag = 31, type = Datatype.STRING)
    public String umidToken;
    @ProtoField(tag = 7, type = Datatype.STRING)
    public String viewId;
    @ProtoField(tag = 8, type = Datatype.STRING)
    public String voiceOver;
    @ProtoField(tag = 15, type = Datatype.BOOL)
    public Boolean wifiConn;
    @ProtoField(tag = 17, type = Datatype.BOOL)
    public Boolean wifiConnectBypassword;
    @ProtoField(label = Label.REPEATED, tag = 3)
    public List<WifiInfoPbPB> wifiInfos;

    public DeviceLocationReqPbPB(DeviceLocationReqPbPB deviceLocationReqPbPB) {
        super(deviceLocationReqPbPB);
        if (deviceLocationReqPbPB != null) {
            this.apdid = deviceLocationReqPbPB.apdid;
            this.extraInfos = copyOf(deviceLocationReqPbPB.extraInfos);
            this.wifiInfos = copyOf(deviceLocationReqPbPB.wifiInfos);
            this.os = deviceLocationReqPbPB.os;
            this.imei = deviceLocationReqPbPB.imei;
            this.imsi = deviceLocationReqPbPB.imsi;
            this.viewId = deviceLocationReqPbPB.viewId;
            this.voiceOver = deviceLocationReqPbPB.voiceOver;
            this.altitude = deviceLocationReqPbPB.altitude;
            this.speed = deviceLocationReqPbPB.speed;
            this.direction = deviceLocationReqPbPB.direction;
            this.longitude = deviceLocationReqPbPB.longitude;
            this.latitude = deviceLocationReqPbPB.latitude;
            this.accuracy = deviceLocationReqPbPB.accuracy;
            this.wifiConn = deviceLocationReqPbPB.wifiConn;
            this.lbsOpen = deviceLocationReqPbPB.lbsOpen;
            this.wifiConnectBypassword = deviceLocationReqPbPB.wifiConnectBypassword;
            this.currentMobileOperator = deviceLocationReqPbPB.currentMobileOperator;
            this.accessWirelessNetType = deviceLocationReqPbPB.accessWirelessNetType;
            this.bluetoothMac = deviceLocationReqPbPB.bluetoothMac;
            this.bluetoothOpen = deviceLocationReqPbPB.bluetoothOpen;
            this.source = deviceLocationReqPbPB.source;
            this.queryLbs = deviceLocationReqPbPB.queryLbs;
            this.osVersion = deviceLocationReqPbPB.osVersion;
            this.cellConn = deviceLocationReqPbPB.cellConn;
            this.cellType = deviceLocationReqPbPB.cellType;
            this.gsmInfos = copyOf(deviceLocationReqPbPB.gsmInfos);
            this.cdmaInfos = copyOf(deviceLocationReqPbPB.cdmaInfos);
            this.tidInfo = deviceLocationReqPbPB.tidInfo;
            this.apdidToken = deviceLocationReqPbPB.apdidToken;
            this.umidToken = deviceLocationReqPbPB.umidToken;
        }
    }

    public DeviceLocationReqPbPB() {
    }

    public final DeviceLocationReqPbPB fillTagValue(int i, Object obj) {
        switch (i) {
            case 1:
                this.apdid = (String) obj;
                break;
            case 2:
                this.extraInfos = immutableCopyOf((List) obj);
                break;
            case 3:
                this.wifiInfos = immutableCopyOf((List) obj);
                break;
            case 4:
                this.os = (String) obj;
                break;
            case 5:
                this.imei = (String) obj;
                break;
            case 6:
                this.imsi = (String) obj;
                break;
            case 7:
                this.viewId = (String) obj;
                break;
            case 8:
                this.voiceOver = (String) obj;
                break;
            case 9:
                this.altitude = (Double) obj;
                break;
            case 10:
                this.speed = (Double) obj;
                break;
            case 11:
                this.direction = (Double) obj;
                break;
            case 12:
                this.longitude = (Double) obj;
                break;
            case 13:
                this.latitude = (Double) obj;
                break;
            case 14:
                this.accuracy = (Double) obj;
                break;
            case 15:
                this.wifiConn = (Boolean) obj;
                break;
            case 16:
                this.lbsOpen = (Boolean) obj;
                break;
            case 17:
                this.wifiConnectBypassword = (Boolean) obj;
                break;
            case 18:
                this.currentMobileOperator = (String) obj;
                break;
            case 19:
                this.accessWirelessNetType = (String) obj;
                break;
            case 20:
                this.bluetoothMac = (String) obj;
                break;
            case 21:
                this.bluetoothOpen = (Boolean) obj;
                break;
            case 22:
                this.source = (String) obj;
                break;
            case 23:
                this.queryLbs = (Boolean) obj;
                break;
            case 24:
                this.osVersion = (String) obj;
                break;
            case 25:
                this.cellConn = (Boolean) obj;
                break;
            case 26:
                this.cellType = (CellTypeEnumPbPB) obj;
                break;
            case 27:
                this.gsmInfos = immutableCopyOf((List) obj);
                break;
            case 28:
                this.cdmaInfos = immutableCopyOf((List) obj);
                break;
            case 29:
                this.tidInfo = (TidInfoPbPB) obj;
                break;
            case 30:
                this.apdidToken = (String) obj;
                break;
            case 31:
                this.umidToken = (String) obj;
                break;
        }
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DeviceLocationReqPbPB)) {
            return false;
        }
        DeviceLocationReqPbPB deviceLocationReqPbPB = (DeviceLocationReqPbPB) obj;
        return equals((Object) this.apdid, (Object) deviceLocationReqPbPB.apdid) && equals(this.extraInfos, deviceLocationReqPbPB.extraInfos) && equals(this.wifiInfos, deviceLocationReqPbPB.wifiInfos) && equals((Object) this.os, (Object) deviceLocationReqPbPB.os) && equals((Object) this.imei, (Object) deviceLocationReqPbPB.imei) && equals((Object) this.imsi, (Object) deviceLocationReqPbPB.imsi) && equals((Object) this.viewId, (Object) deviceLocationReqPbPB.viewId) && equals((Object) this.voiceOver, (Object) deviceLocationReqPbPB.voiceOver) && equals((Object) this.altitude, (Object) deviceLocationReqPbPB.altitude) && equals((Object) this.speed, (Object) deviceLocationReqPbPB.speed) && equals((Object) this.direction, (Object) deviceLocationReqPbPB.direction) && equals((Object) this.longitude, (Object) deviceLocationReqPbPB.longitude) && equals((Object) this.latitude, (Object) deviceLocationReqPbPB.latitude) && equals((Object) this.accuracy, (Object) deviceLocationReqPbPB.accuracy) && equals((Object) this.wifiConn, (Object) deviceLocationReqPbPB.wifiConn) && equals((Object) this.lbsOpen, (Object) deviceLocationReqPbPB.lbsOpen) && equals((Object) this.wifiConnectBypassword, (Object) deviceLocationReqPbPB.wifiConnectBypassword) && equals((Object) this.currentMobileOperator, (Object) deviceLocationReqPbPB.currentMobileOperator) && equals((Object) this.accessWirelessNetType, (Object) deviceLocationReqPbPB.accessWirelessNetType) && equals((Object) this.bluetoothMac, (Object) deviceLocationReqPbPB.bluetoothMac) && equals((Object) this.bluetoothOpen, (Object) deviceLocationReqPbPB.bluetoothOpen) && equals((Object) this.source, (Object) deviceLocationReqPbPB.source) && equals((Object) this.queryLbs, (Object) deviceLocationReqPbPB.queryLbs) && equals((Object) this.osVersion, (Object) deviceLocationReqPbPB.osVersion) && equals((Object) this.cellConn, (Object) deviceLocationReqPbPB.cellConn) && equals((Object) this.cellType, (Object) deviceLocationReqPbPB.cellType) && equals(this.gsmInfos, deviceLocationReqPbPB.gsmInfos) && equals(this.cdmaInfos, deviceLocationReqPbPB.cdmaInfos) && equals((Object) this.tidInfo, (Object) deviceLocationReqPbPB.tidInfo) && equals((Object) this.apdidToken, (Object) deviceLocationReqPbPB.apdidToken) && equals((Object) this.umidToken, (Object) deviceLocationReqPbPB.umidToken);
    }

    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int i2 = 0;
        int i3 = 1;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((this.apdid != null ? this.apdid.hashCode() : 0) * 37) + (this.extraInfos != null ? this.extraInfos.hashCode() : 1)) * 37) + (this.wifiInfos != null ? this.wifiInfos.hashCode() : 1)) * 37) + (this.os != null ? this.os.hashCode() : 0)) * 37) + (this.imei != null ? this.imei.hashCode() : 0)) * 37) + (this.imsi != null ? this.imsi.hashCode() : 0)) * 37) + (this.viewId != null ? this.viewId.hashCode() : 0)) * 37) + (this.voiceOver != null ? this.voiceOver.hashCode() : 0)) * 37) + (this.altitude != null ? this.altitude.hashCode() : 0)) * 37) + (this.speed != null ? this.speed.hashCode() : 0)) * 37) + (this.direction != null ? this.direction.hashCode() : 0)) * 37) + (this.longitude != null ? this.longitude.hashCode() : 0)) * 37) + (this.latitude != null ? this.latitude.hashCode() : 0)) * 37) + (this.accuracy != null ? this.accuracy.hashCode() : 0)) * 37) + (this.wifiConn != null ? this.wifiConn.hashCode() : 0)) * 37) + (this.lbsOpen != null ? this.lbsOpen.hashCode() : 0)) * 37) + (this.wifiConnectBypassword != null ? this.wifiConnectBypassword.hashCode() : 0)) * 37) + (this.currentMobileOperator != null ? this.currentMobileOperator.hashCode() : 0)) * 37) + (this.accessWirelessNetType != null ? this.accessWirelessNetType.hashCode() : 0)) * 37) + (this.bluetoothMac != null ? this.bluetoothMac.hashCode() : 0)) * 37) + (this.bluetoothOpen != null ? this.bluetoothOpen.hashCode() : 0)) * 37) + (this.source != null ? this.source.hashCode() : 0)) * 37) + (this.queryLbs != null ? this.queryLbs.hashCode() : 0)) * 37) + (this.osVersion != null ? this.osVersion.hashCode() : 0)) * 37) + (this.cellConn != null ? this.cellConn.hashCode() : 0)) * 37) + (this.cellType != null ? this.cellType.hashCode() : 0)) * 37) + (this.gsmInfos != null ? this.gsmInfos.hashCode() : 1)) * 37;
        if (this.cdmaInfos != null) {
            i3 = this.cdmaInfos.hashCode();
        }
        int hashCode2 = (((((hashCode + i3) * 37) + (this.tidInfo != null ? this.tidInfo.hashCode() : 0)) * 37) + (this.apdidToken != null ? this.apdidToken.hashCode() : 0)) * 37;
        if (this.umidToken != null) {
            i2 = this.umidToken.hashCode();
        }
        int i4 = hashCode2 + i2;
        this.hashCode = i4;
        return i4;
    }
}
