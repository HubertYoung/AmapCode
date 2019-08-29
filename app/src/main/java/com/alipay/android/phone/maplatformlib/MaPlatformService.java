package com.alipay.android.phone.maplatformlib;

import android.text.TextUtils;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.mascanengine.MaScanResult;
import com.alipay.mobile.mascanengine.MaScanType;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobilecodec.service.pai.PaipaiFacade;
import com.alipay.mobilecodec.service.pai.req.RouteCommandReq;
import java.util.HashMap;
import java.util.Map;

public class MaPlatformService {
    public MaPlatformResult requestToMaPlatform(MicroApplicationContext ctx, MaScanResult maScanResult, String product, String productVersion, MaLocation maLocation) {
        if (ctx == null || maScanResult == null || TextUtils.isEmpty(product) || TextUtils.isEmpty(productVersion)) {
            return null;
        }
        RpcService mRPCService = (RpcService) ctx.findServiceByInterface(RpcService.class.getName());
        if (mRPCService == null) {
            return null;
        }
        Map<String, String> decodeData = new HashMap<>();
        String paiType = composeRouteData(maScanResult, decodeData);
        RouteCommandReq req = new RouteCommandReq();
        req.decodeData = decodeData;
        req.paiType = paiType;
        Map<String, String> extData = new HashMap<>();
        String lbsInfo = maLocation != null ? maLocation.toJsonString() : null;
        if (!TextUtils.isEmpty(lbsInfo)) {
            extData.put("lbsInfo", lbsInfo);
        }
        req.extData = extData;
        Map<String, String> productContext = new HashMap<>();
        if (!TextUtils.isEmpty(product)) {
            productContext.put("product", product);
        }
        if (!TextUtils.isEmpty(productVersion)) {
            productContext.put(LoggingSPCache.STORAGE_PRODUCTVERSION, productVersion);
        }
        req.productContext = productContext;
        try {
            return new MaPlatformResult(((PaipaiFacade) mRPCService.getRpcProxy(PaipaiFacade.class)).route(req));
        } catch (RpcException e) {
            return new MaPlatformResult(e);
        } catch (Exception e2) {
            return new MaPlatformResult(e2);
        }
    }

    private String composeRouteData(MaScanResult result, Map<String, String> decodeData) {
        if (decodeData == null) {
            return null;
        }
        if (MaScanType.PRODUCT == result.type || MaScanType.MEDICINE == result.type || MaScanType.EXPRESS == result.type) {
            decodeData.put("code", result.text);
            return Constants.NORMAL_MA_TYPE_BAR;
        } else if (MaScanType.QR == result.type || MaScanType.TB_ANTI_FAKE == result.type || MaScanType.TB_4G == result.type) {
            decodeData.put("code", result.text);
            return Constants.NORMAL_MA_TYPE_QR;
        } else if (MaScanType.GEN3 == result.type) {
            decodeData.put("code", result.text);
            decodeData.put("visualCode", "true");
            return Constants.NORMAL_MA_TYPE_QR;
        } else if (MaScanType.DM != result.type) {
            return "error";
        } else {
            decodeData.put("code", result.text);
            decodeData.put(GlobalConstants.CODE_TYPE, "dm");
            return "lottery";
        }
    }
}
