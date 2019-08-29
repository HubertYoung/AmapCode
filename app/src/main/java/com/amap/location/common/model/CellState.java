package com.amap.location.common.model;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.Locale;

public class CellState {
    public static final int I_CDMA_T = 2;
    public static final int I_DEF_CGI_T = 0;
    public static final int I_GSM_T = 1;
    public static final int I_LTE_T = 3;
    public static final int I_WCDMA_T = 4;
    public int bid = 0;
    @Deprecated
    public short cellAge = 0;
    public int cid = 0;
    public int lac = 0;
    @Deprecated
    public long lastUpdateTimeMills = 0;
    public long lastUpdateUtcMills = 0;
    public int latitude;
    public int longitude;
    public int mcc = 0;
    public int mnc = 0;
    public boolean newapi = true;
    public int nid = 0;
    public short pci = Short.MAX_VALUE;
    public boolean registered;
    public int sid = 0;
    public int signalStrength = 99;
    public int type = 0;

    private boolean bidValid(int i) {
        return i >= 0 && i <= 65535;
    }

    private boolean cidValid(int i) {
        return i >= 0 && i <= 268435455;
    }

    private boolean lacValid(int i) {
        return i >= 0 && i <= 65535;
    }

    private boolean nidValid(int i) {
        return i >= 0 && i <= 65535;
    }

    private boolean sidValid(int i) {
        return i > 0 && i <= 32767;
    }

    public CellState(int i, boolean z) {
        this.type = i;
        this.registered = z;
    }

    public CellState(int i, boolean z, boolean z2) {
        this.type = i;
        this.registered = z;
        this.newapi = z2;
    }

    public String getKey() {
        String keyWithOutInterface = getKeyWithOutInterface();
        if (TextUtils.isEmpty(keyWithOutInterface)) {
            return "";
        }
        boolean z = this.newapi;
        StringBuilder sb = new StringBuilder();
        sb.append(z ? 1 : 0);
        sb.append(MetaRecord.LOG_SEPARATOR);
        sb.append(keyWithOutInterface);
        return sb.toString();
    }

    public String getKeyWithOutInterface() {
        switch (this.type) {
            case 1:
            case 3:
            case 4:
                StringBuilder sb = new StringBuilder();
                sb.append(this.type);
                sb.append(MetaRecord.LOG_SEPARATOR);
                sb.append(this.mcc);
                sb.append(MetaRecord.LOG_SEPARATOR);
                sb.append(this.mnc);
                sb.append(MetaRecord.LOG_SEPARATOR);
                sb.append(this.lac);
                sb.append(MetaRecord.LOG_SEPARATOR);
                sb.append(this.cid);
                return sb.toString();
            case 2:
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.type);
                sb2.append(MetaRecord.LOG_SEPARATOR);
                sb2.append(this.sid);
                sb2.append(MetaRecord.LOG_SEPARATOR);
                sb2.append(this.nid);
                sb2.append(MetaRecord.LOG_SEPARATOR);
                sb2.append(this.bid);
                return sb2.toString();
            default:
                return "";
        }
    }

    public boolean isValid() {
        switch (this.type) {
            case 1:
            case 3:
            case 4:
                if (!lacValid(this.lac) || !cidValid(this.cid)) {
                    return false;
                }
            case 2:
                if (!sidValid(this.sid) || !nidValid(this.nid) || !bidValid(this.bid)) {
                    return false;
                }
            default:
                return false;
        }
        return true;
    }

    public CellState clone() {
        CellState cellState = new CellState(this.type, this.registered, this.newapi);
        cellState.mcc = this.mcc;
        cellState.mnc = this.mnc;
        cellState.lac = this.lac;
        cellState.cid = this.cid;
        cellState.sid = this.sid;
        cellState.nid = this.nid;
        cellState.bid = this.bid;
        cellState.signalStrength = this.signalStrength;
        cellState.latitude = this.latitude;
        cellState.longitude = this.longitude;
        cellState.cellAge = this.cellAge;
        cellState.lastUpdateTimeMills = this.lastUpdateTimeMills;
        cellState.lastUpdateUtcMills = this.lastUpdateUtcMills;
        cellState.pci = this.pci;
        return cellState;
    }

    public String toString() {
        switch (this.type) {
            case 1:
                return String.format(Locale.CHINA, "[type=GSM, mcc=%d, mnc=%d, lac=%d, cid=%d, sig=%d, age=%d, reg=%b, new=%b]", new Object[]{Integer.valueOf(this.mcc), Integer.valueOf(this.mnc), Integer.valueOf(this.lac), Integer.valueOf(this.cid), Integer.valueOf(this.signalStrength), Short.valueOf(this.cellAge), Boolean.valueOf(this.registered), Boolean.valueOf(this.newapi)});
            case 2:
                return String.format(Locale.CHINA, "[type=CDMA, mcc=%d, mnc=%d, sid=%d, nid=%d, bid=%d, sig=%d, age=%d, reg=%b, new=%b]", new Object[]{Integer.valueOf(this.mcc), Integer.valueOf(this.mnc), Integer.valueOf(this.sid), Integer.valueOf(this.nid), Integer.valueOf(this.bid), Integer.valueOf(this.signalStrength), Short.valueOf(this.cellAge), Boolean.valueOf(this.registered), Boolean.valueOf(this.newapi)});
            case 3:
                return String.format(Locale.CHINA, "[type=LTE, mcc=%d, mnc=%d, lac=%d, cid=%d, sig=%d, age=%d, reg=%b, new=%b, pci=%d]", new Object[]{Integer.valueOf(this.mcc), Integer.valueOf(this.mnc), Integer.valueOf(this.lac), Integer.valueOf(this.cid), Integer.valueOf(this.signalStrength), Short.valueOf(this.cellAge), Boolean.valueOf(this.registered), Boolean.valueOf(this.newapi), Short.valueOf(this.pci)});
            case 4:
                return String.format(Locale.CHINA, "[type=WCDMA, mcc=%d, mnc=%d, lac=%d, cid=%d, sig=%d, age=%d, reg=%b, new=%b, psc=%d]", new Object[]{Integer.valueOf(this.mcc), Integer.valueOf(this.mnc), Integer.valueOf(this.lac), Integer.valueOf(this.cid), Integer.valueOf(this.signalStrength), Short.valueOf(this.cellAge), Boolean.valueOf(this.registered), Boolean.valueOf(this.newapi), Short.valueOf(this.pci)});
            default:
                return "unknown";
        }
    }
}
