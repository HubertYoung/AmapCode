package com.alipay.mobile.security.bio.common.statistics;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.Hashtable;

/* compiled from: RecordExtServiceImpl */
final class a extends Hashtable<RecordExtAction, MetaRecord> {
    final /* synthetic */ RecordExtServiceImpl this$0;

    a(RecordExtServiceImpl recordExtServiceImpl) {
        this.this$0 = recordExtServiceImpl;
        put(RecordExtAction.RECORD_FC_ENTRY_SDK, new MetaRecord((String) "UC-RZHY-170118-01", (String) "event", (String) "20000189", (String) "fcEntrySDK", 1));
        put(RecordExtAction.RECORD_FC_GET_PARAM, new MetaRecord((String) "UC-RZHY-170118-02", (String) "event", (String) "20000189", (String) "fcGetParameters", 1));
        put(RecordExtAction.RECORD_FC_ENTRY_CERT_SDK, new MetaRecord((String) "UC-RZHY-170118-03", (String) "event", (String) "20000189", (String) "fcEntryCertSDK", 1));
        put(RecordExtAction.RECORD_FC_ENTER_H5_PAGE, new MetaRecord((String) "UC-RZHY-170118-04", (String) "event", (String) "20000189", (String) "fcEnterH5Page", 1));
        put(RecordExtAction.RECORD_FC_EXIT_H5_PAGE, new MetaRecord((String) "UC-RZHY-170118-05", (String) "event", (String) "20000189", (String) "fcExitH5Page", 1));
        put(RecordExtAction.RECORD_FC_DEVICE_CHECK_START, new MetaRecord((String) "UC-RZHY-170118-07", (String) "event", (String) "20000189", (String) "fcDeviceCheckStart", 1));
        put(RecordExtAction.RECORD_FC_DEVICE_CHECK_END, new MetaRecord((String) "UC-RZHY-170118-08", (String) "event", (String) "20000189", (String) "fcDeviceCheckEnd", 1));
        put(RecordExtAction.RECORD_FC_ALERT_APPEAR, new MetaRecord((String) "UC-RZHY-170118-09", (String) "event", (String) "20000189", (String) "fcAlertAppear", 1));
        put(RecordExtAction.RECORD_FC_ALERT_CHOOSE, new MetaRecord((String) "UC-RZHY-170118-10", (String) "event", (String) "20000189", (String) "fcAlertChoose", 1));
        put(RecordExtAction.RECORD_FC_ENTER_DETECTION_START, new MetaRecord((String) "UC-RZHY-170118-11", (String) "event", (String) "20000189", (String) "fcEnterDetectionStart", 1));
        put(RecordExtAction.RECORD_FC_ENTER_DETECTION_END, new MetaRecord((String) "UC-RZHY-170118-12", (String) "event", (String) "20000189", (String) "fcEnterDetectionEnd", 1));
        put(RecordExtAction.RECORD_FC_CERT_CHECK_START, new MetaRecord((String) "UC-RZHY-170118-13", (String) "event", (String) "20000189", (String) "fcCertCheckStart", 1));
        put(RecordExtAction.RECORD_FC_CERT_CHECK_END, new MetaRecord((String) "UC-RZHY-170118-14", (String) "event", (String) "20000189", (String) "fcCertCheckEnd", 1));
        put(RecordExtAction.RECORD_FC_CLICK_BUTTON, new MetaRecord((String) "UC-RZHY-170118-15", (String) "event", (String) "20000189", (String) "fcClickButton", 1));
        put(RecordExtAction.RECORD_FC_CERT_QUALITY_ALERT_APPEAR, new MetaRecord((String) "UC-RZHY-170118-18", (String) "event", (String) "20000189", (String) "fcCertQualityAlertAppear", 1));
        put(RecordExtAction.RECORD_FC_CERT_QUALITY_ALERT_CHOOSE, new MetaRecord((String) "UC-RZHY-170118-19", (String) "event", (String) "20000189", (String) "fcCertQualityAlertChoose", 1));
        put(RecordExtAction.RECORD_FC_UPLOAD_CERT_AVARRIABLE, new MetaRecord((String) "UC-RZHY-170118-20", (String) "event", (String) "20000189", (String) "fcUploadCertAvailable", 1));
        put(RecordExtAction.RECORD_FC_UPLOAD_CERT_START, new MetaRecord((String) "UC-RZHY-170118-21", (String) "event", (String) "20000189", (String) "fcUploadCertStart", 1));
        put(RecordExtAction.RECORD_FC_UPLOAD_CERT_END, new MetaRecord((String) "UC-RZHY-170118-22", (String) "event", (String) "20000189", (String) "fcUploadCertEnd", 1));
        put(RecordExtAction.RECORD_FC_CERT_GET_SERVER_RESULT, new MetaRecord((String) "UC-RZHY-170118-23", (String) "event", (String) "20000189", (String) "fcCertGetServerResult", 1));
        put(RecordExtAction.RECORD_FC_ENTER_CERT_BACK_FILL, new MetaRecord((String) "UC-RZHY-170118-24", (String) "event", (String) "20000189", (String) "fcEnterCertBackfill", 1));
        put(RecordExtAction.RECORD_FC_EXIT_CERT_BACK_FILL_ERROR, new MetaRecord((String) "UC-RZHY-170118-25", (String) "event", (String) "20000189", (String) "fcExitCertBackfillError", 1));
        put(RecordExtAction.RECORD_FC_EXIT_CERT_BACK_FILL_NORMAL, new MetaRecord((String) "UC-RZHY-170118-26", (String) "event", (String) "20000189", (String) "fcExitCertBackfillNormal", 1));
        put(RecordExtAction.RECORD_FC_CERT_CALL_BACK_BIS_SYSTEM, new MetaRecord((String) "UC-RZHY-170118-27", (String) "event", (String) "20000189", (String) "fcCertCallbackBisSystem", 1));
        put(RecordExtAction.RECORD_FC_EXIT_CERT_SDK, new MetaRecord((String) "UC-RZHY-170118-28", (String) "event", (String) "20000189", (String) "fcExitCertSDK", 1));
        put(RecordExtAction.RECORD_FC_FACE_GET_SERVER_RESULT, new MetaRecord((String) "UC-RZHY-170118-29", (String) "event", (String) "20000189", (String) "fcFaceGetServerResult", 1));
        put(RecordExtAction.RECORD_FC_FACE_CMPR_CERT_START, new MetaRecord((String) "UC-RZHY-170118-30", (String) "event", (String) "20000189", (String) "fcFaceCmprCertStart", 1));
        put(RecordExtAction.RECORD_FC_FACE_CALL_BACK_BIS_SYSTEM, new MetaRecord((String) "UC-RZHY-170118-31", (String) "event", (String) "20000189", (String) "fcFaceCallbackBisSystem", 1));
        put(RecordExtAction.RECORD_FC_CALL_BACK_VERITY_SYSTEM, new MetaRecord((String) "UC-RZHY-170118-32", (String) "event", (String) "20000189", (String) "fcCallbackVeritySystem", 1));
        put(RecordExtAction.RECORD_FC_EXIT_SDK, new MetaRecord((String) "UC-RZHY-170118-33", (String) "event", (String) "20000189", (String) "fcExitSDK", 1));
        put(RecordExtAction.RECORD_HANDLE_PROTOCOL_START, new MetaRecord((String) "UC-RZHY-170118-34", (String) "event", (String) "20000189", (String) "handleProtocolStart", 1));
        put(RecordExtAction.RECORD_HANDLE_PROTOCOL_END, new MetaRecord((String) "UC-RZHY-170118-35", (String) "event", (String) "20000189", (String) "handleProtocolEnd", 1));
        put(RecordExtAction.ALGORITHM_RESULT, new MetaRecord((String) "UC-RZHY-170118-36", (String) "event", (String) "20000189", (String) "algorithmResult", 1));
        put(RecordExtAction.COMPRESS_CERT_START, new MetaRecord((String) "UC-RZHY-170118-37", (String) "event", (String) "20000189", (String) "compressCertStart", 1));
        put(RecordExtAction.COMPRESS_CERT_END, new MetaRecord((String) "UC-RZHY-170118-37", (String) "event", (String) "20000189", (String) "compressCertEnd", 1));
    }
}
