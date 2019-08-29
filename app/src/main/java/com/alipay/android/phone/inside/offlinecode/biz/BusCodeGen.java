package com.alipay.android.phone.inside.offlinecode.biz;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.gen.AlipayCodeProtocolV1;
import com.alipay.android.phone.inside.offlinecode.gen.AlipayCodeProtocolV2;
import com.alipay.android.phone.inside.offlinecode.gen.ICodeProtocol;
import com.alipay.android.phone.inside.offlinecode.gen.ScriptGenCodeProtocol;
import com.alipay.android.phone.inside.offlinecode.gen.TransportDepCodeProtocol;
import com.alipay.android.phone.inside.offlinecode.gen.YctCodeProtocol;
import com.alipay.android.phone.inside.offlinecode.model.BehaviorRecord;
import com.alipay.android.phone.inside.offlinecode.model.CardData;
import com.alipay.android.phone.inside.offlinecode.model.UnknownProtocolException;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.OfflineDataInfo;
import com.alipay.android.phone.inside.offlinecode.storage.CardDataStorage;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;

public class BusCodeGen {
    public String generateCode(Context context, CardData cardData) throws Exception {
        ICodeProtocol iCodeProtocol;
        OfflineDataInfo offlineData = cardData.getOfflineData();
        if (isOnlineMode(offlineData)) {
            LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusGenCodeOnline").g = cardData.getCardType();
            return offlineData.qrcode;
        }
        String str = "BusGenCodeLocal";
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("generateCode, cardType=");
        sb.append(cardData.getCardType());
        sb.append(", certType=");
        sb.append(offlineData.certType);
        f.b((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, sb.toString());
        if (isUseScript(offlineData)) {
            str = "BusGenCodeScript";
            iCodeProtocol = new ScriptGenCodeProtocol(offlineData);
        } else if (isAlipayProtocolV1(offlineData)) {
            iCodeProtocol = new AlipayCodeProtocolV1();
        } else if (isAlipayProtocolV2(offlineData)) {
            iCodeProtocol = new AlipayCodeProtocolV2();
        } else if (isYctProtocolV1(offlineData)) {
            iCodeProtocol = new YctCodeProtocol();
        } else if (isTransportProtocol(offlineData)) {
            iCodeProtocol = new TransportDepCodeProtocol();
        } else {
            Behavior a = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusGenCodeUnknownProtocol");
            a.g = cardData.getCardType();
            a.h = offlineData.certType;
            throw new UnknownProtocolException();
        }
        String str2 = offlineData.offlineData;
        String str3 = offlineData.privateKey;
        cardData.incGenerateTimes();
        CardDataStorage.getInstance().saveCardData(context, cardData);
        Behavior a2 = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, str);
        a2.g = cardData.getCardType();
        a2.h = offlineData.certType;
        StringBuilder sb2 = new StringBuilder("mg:");
        sb2.append(cardData.getBehaviorRecord().generateMgTimes);
        sb2.append("|og:");
        sb2.append(cardData.getBehaviorRecord().generateOgTimes);
        a2.a(sb2.toString());
        return iCodeProtocol.generateCode(str2, str3);
    }

    private boolean isTransportProtocol(OfflineDataInfo offlineDataInfo) {
        return TextUtils.equals("MOT_OFFLINE_CERT_DOUBLE_SM2", offlineDataInfo.certType) || TextUtils.equals("MOT_OFFLINE_CERT_TRIPLE_SM2", offlineDataInfo.certType);
    }

    private boolean needClearYctCache(CardData cardData) {
        if (cardData == null || !isYctCardType(cardData.getCardType()) || isYctProtocolV1(cardData.getOfflineData())) {
            return false;
        }
        Behavior a = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "NeedClearYctCache");
        a.g = cardData.getCardType();
        a.h = cardData.getOfflineData().certType;
        return true;
    }

    private boolean isYctCardType(String str) {
        return TextUtils.equals("Y1440100", str);
    }

    private boolean isYctProtocolV1(OfflineDataInfo offlineDataInfo) {
        return offlineDataInfo != null && TextUtils.equals("YCT_OFFLINE_CERT_V1", offlineDataInfo.certType);
    }

    private boolean isAlipayProtocolV1(OfflineDataInfo offlineDataInfo) {
        return TextUtils.equals("ALIPAY_OFFLINE_CERT_V1", offlineDataInfo.certType);
    }

    private boolean isAlipayProtocolV2(OfflineDataInfo offlineDataInfo) {
        return TextUtils.equals("ALIPAY_OFFLINE_CERT_V2", offlineDataInfo.certType);
    }

    public boolean isNeedUpdateCardData(CardData cardData) {
        OfflineDataInfo offlineData = cardData.getOfflineData();
        if (offlineData == null) {
            LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "OfflineDataEmpty");
        }
        return offlineData == null || isOfflineDataExpire(cardData) || isOnlineMode(offlineData) || needClearYctCache(cardData);
    }

    public boolean isReachQrcodeOgTimes(CardData cardData) {
        if (isOgCheckTimeExpire(cardData)) {
            cardData.resetOgTimes();
        } else {
            int cardConfig = cardData.getCardConfig("QR_OG_TIM");
            int i = cardData.getBehaviorRecord().generateOgTimes;
            if (cardConfig > 0 && i >= cardConfig) {
                return true;
            }
        }
        return false;
    }

    public boolean isReachQrcodeMgTime(CardData cardData) {
        if (isMgCheckTimeExpire(cardData)) {
            cardData.resetMgTimes();
        } else {
            int cardConfig = cardData.getCardConfig("QR_MG_TIM");
            int i = cardData.getBehaviorRecord().generateMgTimes;
            if (cardConfig > 0 && i >= cardConfig) {
                return true;
            }
        }
        return false;
    }

    private boolean isOgCheckTimeExpire(CardData cardData) {
        long j;
        BehaviorRecord behaviorRecord = cardData.getBehaviorRecord();
        if (cardData.getBehaviorRecord().checkOgTime <= 0) {
            j = System.currentTimeMillis();
        } else {
            j = behaviorRecord.checkOgTime;
        }
        return System.currentTimeMillis() >= j + 43200000;
    }

    private boolean isMgCheckTimeExpire(CardData cardData) {
        long j;
        BehaviorRecord behaviorRecord = cardData.getBehaviorRecord();
        if (cardData.getBehaviorRecord().checkMgTime <= 0) {
            j = System.currentTimeMillis();
        } else {
            j = behaviorRecord.checkMgTime;
        }
        return System.currentTimeMillis() >= j + 43200000;
    }

    private boolean isOfflineDataExpire(CardData cardData) {
        if (System.currentTimeMillis() / 1000 <= cardData.getExpireTime()) {
            return false;
        }
        LoggerFactory.d().b(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "OfflineDataExpire");
        return true;
    }

    private boolean isOnlineMode(OfflineDataInfo offlineDataInfo) {
        boolean equals = TextUtils.equals("ONLINE", offlineDataInfo.authMode);
        if (equals) {
            LoggerFactory.d().b(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "OnlineMode");
        }
        return equals;
    }

    private boolean isUseScript(OfflineDataInfo offlineDataInfo) {
        return offlineDataInfo.useScript;
    }
}
