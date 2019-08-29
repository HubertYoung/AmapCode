package com.alipay.android.phone.scancode.export;

import com.alipay.mobile.scansdk.constant.Constants;

public class ScanRequest {
    private String mActionText;
    private String mActionUrl;
    private String mCallBackUrl;
    private String mDataType;
    private String mExtra;
    private boolean mNotSupportAlbum;
    private ScanType mScanType = ScanType.BARCODE;
    private String mSourceId;
    private String mTitleText;
    private String mViewText;

    public enum DataType {
        RAWDATA("rawData");
        
        String typeStr;

        private DataType(String type) {
            this.typeStr = type;
        }
    }

    public enum ScanType {
        BARCODE(Constants.SCAN_BAR),
        QRCODE(Constants.SCAN_QR);
        
        String typeStr;

        private ScanType(String type) {
            this.typeStr = type;
        }
    }

    public ScanRequest setScanType(ScanType scanType) {
        this.mScanType = scanType;
        return this;
    }

    public ScanRequest setSourceId(String sourceId) {
        this.mSourceId = sourceId;
        return this;
    }

    public ScanRequest setDataType(String dataType) {
        this.mDataType = dataType;
        return this;
    }

    public ScanRequest setCallBackUrl(String callBackUrl) {
        this.mCallBackUrl = callBackUrl;
        return this;
    }

    public ScanRequest setViewText(String viewText) {
        this.mViewText = viewText;
        return this;
    }

    public ScanRequest setExtra(String extra) {
        this.mExtra = extra;
        return this;
    }

    public ScanRequest setmActionText(String actionText) {
        this.mActionText = actionText;
        return this;
    }

    public ScanRequest setmTitleText(String titleText) {
        this.mTitleText = titleText;
        return this;
    }

    public ScanRequest setmActionUrl(String actionUrl) {
        this.mActionUrl = actionUrl;
        return this;
    }

    public ScanRequest setNotSupportAlbum(boolean notSupport) {
        this.mNotSupportAlbum = notSupport;
        return this;
    }

    public String getmActionUrl() {
        return this.mActionUrl;
    }

    public String getmTitleText() {
        return this.mTitleText;
    }

    public String getmActionText() {
        return this.mActionText;
    }

    public String getViewText() {
        return this.mViewText;
    }

    public String getScanType() {
        return this.mScanType.typeStr;
    }

    public String getSourceId() {
        return this.mSourceId;
    }

    public String getDataType() {
        return this.mDataType;
    }

    public String getCallBackUrl() {
        return this.mCallBackUrl;
    }

    public String getExtra() {
        return this.mExtra;
    }

    public boolean getNotSupportAlbum() {
        return this.mNotSupportAlbum;
    }
}
