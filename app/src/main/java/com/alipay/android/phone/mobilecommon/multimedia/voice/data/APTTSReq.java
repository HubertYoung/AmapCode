package com.alipay.android.phone.mobilecommon.multimedia.voice.data;

public class APTTSReq {
    public static final int BUSINESS_TYPE_RECEIVE_MONEY = 0;
    public static final int BUSINESS_TYPE_RECEIVE_MONEY_DEFAULT = 1;
    public static final int BUSINESS_TYPE_TRANSFER_SUCCESS = 2;
    public double amount;
    public String businessId;
    public int businessType;
}
