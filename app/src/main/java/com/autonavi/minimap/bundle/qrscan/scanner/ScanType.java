package com.autonavi.minimap.bundle.qrscan.scanner;

import android.text.TextUtils;

public enum ScanType {
    SCAN_MA("MA");
    
    private String value;

    /* renamed from: com.autonavi.minimap.bundle.qrscan.scanner.ScanType$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$autonavi$minimap$bundle$qrscan$scanner$ScanType = null;

        static {
            $SwitchMap$com$autonavi$minimap$bundle$qrscan$scanner$ScanType = new int[ScanType.values().length];
            try {
                $SwitchMap$com$autonavi$minimap$bundle$qrscan$scanner$ScanType[ScanType.SCAN_MA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private ScanType(String str) {
        this.value = str;
    }

    public static ScanType getType(String str) {
        ScanType[] values;
        for (ScanType scanType : values()) {
            if (TextUtils.equals(scanType.value, str)) {
                return scanType;
            }
        }
        return SCAN_MA;
    }

    public final String toBqcScanType() {
        return AnonymousClass1.$SwitchMap$com$autonavi$minimap$bundle$qrscan$scanner$ScanType[ordinal()] != 1 ? "MA" : "MA";
    }
}
