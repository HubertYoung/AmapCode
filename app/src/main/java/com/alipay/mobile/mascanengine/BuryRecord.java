package com.alipay.mobile.mascanengine;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;

public class BuryRecord {
    public static void recordScanSuccess(final MaScanResult maScanResult) {
        new AsyncTask<Object, Object, Object>() {
            /* access modifiers changed from: protected */
            public final Object doInBackground(Object... params) {
                Behavor behavor = new Behavor();
                behavor.setParam1("mpaas_scan");
                behavor.setParam2(MaScanResult.this.type.toString());
                behavor.setParam3(MaScanResult.this.text);
                behavor.setBehaviourPro("Scan");
                LoggerFactory.getBehavorLogger().event("", behavor);
                return null;
            }
        }.execute(new Object[0]);
    }

    public static void recordRsBinarizeException(final String param1) {
        new AsyncTask<Object, Object, Object>() {
            /* access modifiers changed from: protected */
            public final Object doInBackground(Object... params) {
                Behavor behavor = new Behavor();
                behavor.setSeedID("SCAN_RS_BINARIZE_EXCEPTION");
                behavor.setUserCaseID("SCAN_RS_BINARIZE_EXCEPTION");
                behavor.setBehaviourPro("Scan");
                behavor.setParam1(param1);
                LoggerFactory.getBehavorLogger().event("event", behavor);
                return null;
            }
        }.execute(new Object[0]);
    }

    public static void recordProblemCode(final MaScanResult result) {
        new AsyncTask<Object, Object, Object>() {
            /* access modifiers changed from: protected */
            public final Object doInBackground(Object... params) {
                Behavor behavor = new Behavor();
                behavor.setSeedID("SCAN_FAILED_RISK_TRACE");
                behavor.setUserCaseID("UC_SCAN_2017050200_RISK");
                behavor.setParam1(MaScanResult.this.text);
                behavor.setParam2(Integer.valueOf(MaScanResult.this.version).toString());
                behavor.setParam3(Character.valueOf(MaScanResult.this.ecLevel).toString());
                behavor.setBehaviourPro("Scan");
                behavor.addExtParam("errorBit", Integer.valueOf(MaScanResult.this.bitErrors).toString());
                behavor.addExtParam("strategy", String.valueOf(MaScanResult.this.strategy));
                LoggerFactory.getBehavorLogger().click(behavor);
                return null;
            }
        }.execute(new Object[0]);
    }

    public static void recordLazyRecorgnized(final boolean candidate, final String text) {
        new AsyncTask<Object, Object, Object>() {
            /* access modifiers changed from: protected */
            public final Object doInBackground(Object... params) {
                Behavor behavor = new Behavor();
                behavor.setSeedID("SCAN_LAZY_FRAME_RESULT");
                behavor.setUserCaseID("android.slfr");
                behavor.setParam1(String.valueOf(candidate));
                behavor.setParam2(text);
                behavor.setBehaviourPro("Scan");
                LoggerFactory.getBehavorLogger().click(behavor);
                return null;
            }
        }.execute(new Object[0]);
    }

    public static void recordTwoCodeHasBlackList(final String maText) {
        if (!TextUtils.isEmpty(maText)) {
            new AsyncTask<Object, Object, Object>() {
                /* access modifiers changed from: protected */
                public final Object doInBackground(Object... params) {
                    Behavor behavor = new Behavor();
                    behavor.setUserCaseID("android-scan-code-black");
                    behavor.setSeedID("scan.ascb");
                    behavor.setParam1(maText);
                    behavor.setBehaviourPro("Scan");
                    LoggerFactory.getBehavorLogger().event("event", behavor);
                    return null;
                }
            }.execute(new Object[0]);
        }
    }

    public static void recordScanDecodeTrack(final String text) {
        new AsyncTask<Object, Object, Object>() {
            /* access modifiers changed from: protected */
            public final Object doInBackground(Object... params) {
                LoggerFactory.getTraceLogger().debug("BuryRecord", "recordScanDecodeTrack:" + text);
                Behavor behavor = new Behavor();
                behavor.setSeedID("SCAN_CODE_DECODE_TRACK");
                behavor.setUserCaseID("SCAN_CODE_DECODE_TRACK");
                behavor.setParam1(text);
                behavor.setBehaviourPro("Scan");
                LoggerFactory.getBehavorLogger().click(behavor);
                return null;
            }
        }.execute(new Object[0]);
    }
}
