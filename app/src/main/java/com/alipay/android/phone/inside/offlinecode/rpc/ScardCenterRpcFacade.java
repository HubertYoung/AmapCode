package com.alipay.android.phone.inside.offlinecode.rpc;

import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardIdentityVerifyRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardDataRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardDetailRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardListRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardSceneRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryScriptRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQuerySubSceneRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardUploadDataRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardIdentityVerifyResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardDataResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardDetailResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardListResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardSceneResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryScriptResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQuerySubSceneResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardUploadDataResponse;
import com.alipay.inside.mobile.framework.service.annotation.CheckLogin;
import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface ScardCenterRpcFacade {
    @CheckLogin
    @SignCheck
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.identityverify")
    VirtualCardIdentityVerifyResponse identityVerify(VirtualCardIdentityVerifyRequest virtualCardIdentityVerifyRequest);

    @CheckLogin
    @SignCheck
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.querycarddata")
    VirtualCardQueryCardDataResponse queryCardData(VirtualCardQueryCardDataRequest virtualCardQueryCardDataRequest);

    @CheckLogin
    @SignCheck
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.querycarddetail")
    VirtualCardQueryCardDetailResponse queryCardDetail(VirtualCardQueryCardDetailRequest virtualCardQueryCardDetailRequest);

    @CheckLogin
    @SignCheck
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.querycardlist")
    VirtualCardQueryCardListResponse queryCardList(VirtualCardQueryCardListRequest virtualCardQueryCardListRequest);

    @CheckLogin
    @SignCheck
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.querycardscene")
    VirtualCardQueryCardSceneResponse queryCardScene(VirtualCardQueryCardSceneRequest virtualCardQueryCardSceneRequest);

    @CheckLogin
    @SignCheck
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.queryscript")
    VirtualCardQueryScriptResponse queryScript(VirtualCardQueryScriptRequest virtualCardQueryScriptRequest);

    @CheckLogin
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.querysubscene")
    VirtualCardQuerySubSceneResponse querySubScene(VirtualCardQuerySubSceneRequest virtualCardQuerySubSceneRequest);

    @CheckLogin
    @SignCheck
    @OperationType("alipay.offlinepay.virtualcard.rpc.card.uploaddata")
    VirtualCardUploadDataResponse virtualCardUploadData(VirtualCardUploadDataRequest virtualCardUploadDataRequest);
}
