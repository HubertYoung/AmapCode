package com.autonavi.minimap.wallet;

import com.alipay.mobile.common.share.widget.ResUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.wallet.param.AmountRequest;
import com.autonavi.minimap.wallet.param.BillsRequest;
import com.autonavi.minimap.wallet.param.CashoutRequest;
import com.autonavi.minimap.wallet.param.TradeLogRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class WalletRequestHolder {
    private static volatile WalletRequestHolder instance;

    private WalletRequestHolder() {
    }

    public static WalletRequestHolder getInstance() {
        if (instance == null) {
            synchronized (WalletRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new WalletRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendAmount(AmountRequest amountRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAmount(amountRequest, new dkn(), aosResponseCallback);
    }

    public void sendBills(BillsRequest billsRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBills(billsRequest, new dkn(), aosResponseCallback);
    }

    public void sendCashout(CashoutRequest cashoutRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendCashout(cashoutRequest, new dkn(), aosResponseCallback);
    }

    public void sendTradeLog(TradeLogRequest tradeLogRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTradeLog(tradeLogRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendAmount(AmountRequest amountRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            amountRequest.addHeaders(dkn.d);
            amountRequest.setTimeout(dkn.b);
            amountRequest.setRetryTimes(dkn.c);
        }
        amountRequest.setUrl(AmountRequest.a);
        amountRequest.addSignParam("channel");
        amountRequest.addReqParam("pagenum", Integer.toString(amountRequest.b));
        amountRequest.addReqParam("pagesize", Integer.toString(amountRequest.c));
        amountRequest.addReqParam(ResUtils.STYLE, amountRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) amountRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) amountRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBills(BillsRequest billsRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            billsRequest.addHeaders(dkn.d);
            billsRequest.setTimeout(dkn.b);
            billsRequest.setRetryTimes(dkn.c);
        }
        billsRequest.setUrl(BillsRequest.a);
        billsRequest.addSignParam("channel");
        billsRequest.addReqParam("pagenum", Integer.toString(billsRequest.b));
        billsRequest.addReqParam("pagesize", Integer.toString(billsRequest.c));
        billsRequest.addReqParam("status", Integer.toString(billsRequest.d));
        billsRequest.addReqParam("source_id", billsRequest.e);
        billsRequest.addReqParam("source_md5", billsRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) billsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) billsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendCashout(CashoutRequest cashoutRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            cashoutRequest.addHeaders(dkn.d);
            cashoutRequest.setTimeout(dkn.b);
            cashoutRequest.setRetryTimes(dkn.c);
        }
        cashoutRequest.setUrl(CashoutRequest.a);
        cashoutRequest.addSignParam("channel");
        cashoutRequest.addSignParam("type");
        cashoutRequest.addSignParam("top_token");
        cashoutRequest.addReqParam("type", cashoutRequest.b);
        cashoutRequest.addReqParam("top_token", cashoutRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) cashoutRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) cashoutRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTradeLog(TradeLogRequest tradeLogRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            tradeLogRequest.addHeaders(dkn.d);
            tradeLogRequest.setTimeout(dkn.b);
            tradeLogRequest.setRetryTimes(dkn.c);
        }
        tradeLogRequest.setUrl(TradeLogRequest.a);
        tradeLogRequest.addSignParam("channel");
        tradeLogRequest.addReqParam("pagenum", Integer.toString(tradeLogRequest.b));
        tradeLogRequest.addReqParam("pagesize", Integer.toString(tradeLogRequest.c));
        tradeLogRequest.addReqParam(ResUtils.STYLE, tradeLogRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) tradeLogRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) tradeLogRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
