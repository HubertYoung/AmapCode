package com.tencent.mm.opensdk.openapi;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.tencent.mm.opensdk.channel.MMessageActV2;
import com.tencent.mm.opensdk.channel.MMessageActV2.Args;
import com.tencent.mm.opensdk.channel.a.a;
import com.tencent.mm.opensdk.channel.a.a.C0064a;
import com.tencent.mm.opensdk.channel.a.b;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.constants.ConstantsAPI.Token;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.AddCardToWXCardPackage;
import com.tencent.mm.opensdk.modelbiz.ChooseCardFromWXCardPackage;
import com.tencent.mm.opensdk.modelbiz.CreateChatroom;
import com.tencent.mm.opensdk.modelbiz.HandleScanResult;
import com.tencent.mm.opensdk.modelbiz.JoinChatroom;
import com.tencent.mm.opensdk.modelbiz.OpenWebview;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage.Resp;
import com.tencent.mm.opensdk.modelbiz.SubscribeMiniProgramMsg;
import com.tencent.mm.opensdk.modelbiz.WXInvoiceAuthInsert;
import com.tencent.mm.opensdk.modelbiz.WXInvoiceAuthInsert.Req;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXNontaxPay;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessView;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessWebview;
import com.tencent.mm.opensdk.modelbiz.WXPayInsurance;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.LaunchFromWX;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelpay.JumpToOfflinePay;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.utils.ILog;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;
import java.net.URLEncoder;
import org.json.JSONObject;

class BaseWXApiImplV10 implements IWXAPI {
    protected static final String TAG = "MicroMsg.SDK.WXApiImplV10";
    private static String wxappPayEntryClassname;
    protected String appId;
    protected boolean checkSignature = false;
    protected Context context;
    protected boolean detached = false;

    BaseWXApiImplV10(Context context2, String str, boolean z) {
        StringBuilder sb = new StringBuilder("<init>, appId = ");
        sb.append(str);
        sb.append(", checkSignature = ");
        sb.append(z);
        Log.d(TAG, sb.toString());
        this.context = context2;
        this.appId = str;
        this.checkSignature = z;
    }

    private boolean checkSumConsistent(byte[] bArr, byte[] bArr2) {
        String str;
        String str2;
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0) {
            str = TAG;
            str2 = "checkSumConsistent fail, invalid arguments";
        } else if (bArr.length != bArr2.length) {
            str = TAG;
            str2 = "checkSumConsistent fail, length is different";
        } else {
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] != bArr2[i]) {
                    return false;
                }
            }
            return true;
        }
        Log.e(str, str2);
        return false;
    }

    private boolean createChatroom(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/createChatroom"), null, null, new String[]{this.appId, bundle.getString("_wxapi_basereq_transaction", ""), bundle.getString("_wxapi_create_chatroom_group_id", ""), bundle.getString("_wxapi_create_chatroom_chatroom_name", ""), bundle.getString("_wxapi_create_chatroom_chatroom_nickname", ""), bundle.getString("_wxapi_create_chatroom_ext_msg", ""), bundle.getString("_wxapi_basereq_openid", "")}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private String getTokenFromWX(Context context2) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/genTokenForOpenSdk"), null, null, new String[]{this.appId, "620953856"}, null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        String string = query.getString(0);
        Log.i(TAG, "getTokenFromWX token is ".concat(String.valueOf(string)));
        query.close();
        return string;
    }

    private boolean handleWxInternalRespType(String str, IWXAPIEventHandler iWXAPIEventHandler) {
        Log.i(TAG, "handleWxInternalRespType, extInfo = ".concat(String.valueOf(str)));
        try {
            Uri parse = Uri.parse(str);
            String queryParameter = parse.getQueryParameter("wx_internal_resptype");
            Log.i(TAG, "handleWxInternalRespType, respType = ".concat(String.valueOf(queryParameter)));
            if (d.b(queryParameter)) {
                Log.e(TAG, "handleWxInternalRespType fail, respType is null");
                return false;
            } else if (queryParameter.equals("subscribemessage")) {
                Resp resp = new Resp();
                String queryParameter2 = parse.getQueryParameter("ret");
                if (queryParameter2 != null && queryParameter2.length() > 0) {
                    resp.errCode = d.c(queryParameter2);
                }
                resp.openId = parse.getQueryParameter("openid");
                resp.templateID = parse.getQueryParameter("template_id");
                resp.scene = d.c(parse.getQueryParameter(H5AppUtil.scene));
                resp.action = parse.getQueryParameter("action");
                resp.reserved = parse.getQueryParameter("reserved");
                iWXAPIEventHandler.onResp(resp);
                return true;
            } else if (queryParameter.contains("invoice_auth_insert")) {
                WXInvoiceAuthInsert.Resp resp2 = new WXInvoiceAuthInsert.Resp();
                String queryParameter3 = parse.getQueryParameter("ret");
                if (queryParameter3 != null && queryParameter3.length() > 0) {
                    resp2.errCode = d.c(queryParameter3);
                }
                resp2.wxOrderId = parse.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp2);
                return true;
            } else if (queryParameter.contains("payinsurance")) {
                WXPayInsurance.Resp resp3 = new WXPayInsurance.Resp();
                String queryParameter4 = parse.getQueryParameter("ret");
                if (queryParameter4 != null && queryParameter4.length() > 0) {
                    resp3.errCode = d.c(queryParameter4);
                }
                resp3.wxOrderId = parse.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp3);
                return true;
            } else if (queryParameter.contains("nontaxpay")) {
                WXNontaxPay.Resp resp4 = new WXNontaxPay.Resp();
                String queryParameter5 = parse.getQueryParameter("ret");
                if (queryParameter5 != null && queryParameter5.length() > 0) {
                    resp4.errCode = d.c(queryParameter5);
                }
                resp4.wxOrderId = parse.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp4);
                return true;
            } else {
                if (!"subscribeminiprogrammsg".equals(queryParameter)) {
                    if (!"5".equals(queryParameter)) {
                        Log.e(TAG, "this open sdk version not support the request type");
                        return false;
                    }
                }
                SubscribeMiniProgramMsg.Resp resp5 = new SubscribeMiniProgramMsg.Resp();
                String queryParameter6 = parse.getQueryParameter("ret");
                if (queryParameter6 != null && queryParameter6.length() > 0) {
                    resp5.errCode = d.c(queryParameter6);
                }
                resp5.openId = parse.getQueryParameter("openid");
                resp5.unionId = parse.getQueryParameter("unionid");
                resp5.nickname = parse.getQueryParameter("nickname");
                resp5.errStr = parse.getQueryParameter("errmsg");
                iWXAPIEventHandler.onResp(resp5);
                return true;
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("handleWxInternalRespType fail, ex = ");
            sb.append(e.getMessage());
            Log.e(TAG, sb.toString());
            return false;
        }
    }

    private boolean joinChatroom(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/joinChatroom"), null, null, new String[]{this.appId, bundle.getString("_wxapi_basereq_transaction", ""), bundle.getString("_wxapi_join_chatroom_group_id", ""), bundle.getString("_wxapi_join_chatroom_chatroom_nickname", ""), bundle.getString("_wxapi_join_chatroom_ext_msg", ""), bundle.getString("_wxapi_basereq_openid", "")}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendAddCardToWX(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/addCardToWX"), null, null, new String[]{this.appId, bundle.getString("_wxapi_add_card_to_wx_card_list"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendChooseCardFromWX(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/chooseCardFromWX"), null, null, new String[]{bundle.getString("_wxapi_choose_card_from_wx_card_app_id"), bundle.getString("_wxapi_choose_card_from_wx_card_location_id"), bundle.getString("_wxapi_choose_card_from_wx_card_sign_type"), bundle.getString("_wxapi_choose_card_from_wx_card_card_sign"), bundle.getString("_wxapi_choose_card_from_wx_card_time_stamp"), bundle.getString("_wxapi_choose_card_from_wx_card_nonce_str"), bundle.getString("_wxapi_choose_card_from_wx_card_card_id"), bundle.getString("_wxapi_choose_card_from_wx_card_card_type"), bundle.getString("_wxapi_choose_card_from_wx_card_can_multi_select"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendHandleScanResult(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/handleScanResult"), null, null, new String[]{this.appId, bundle.getString("_wxapi_scan_qrcode_result")}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendInvoiceAuthInsert(Context context2, BaseReq baseReq) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "2", URLEncoder.encode(String.format("url=%s", new Object[]{URLEncoder.encode(((Req) baseReq).url)}))}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendJumpToBizProfileReq(Context context2, Bundle bundle) {
        ContentResolver contentResolver = context2.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizProfile");
        StringBuilder sb = new StringBuilder();
        sb.append(bundle.getInt("_wxapi_jump_to_biz_profile_req_scene"));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(bundle.getInt("_wxapi_jump_to_biz_profile_req_profile_type"));
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_profile_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_profile_req_ext_msg"), sb.toString(), sb2.toString()}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendJumpToBizTempSessionReq(Context context2, Bundle bundle) {
        ContentResolver contentResolver = context2.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizTempSession");
        StringBuilder sb = new StringBuilder();
        sb.append(bundle.getInt("_wxapi_jump_to_biz_webview_req_show_type"));
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_webview_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_webview_req_session_from"), sb.toString()}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendJumpToBizWebviewReq(Context context2, Bundle bundle) {
        ContentResolver contentResolver = context2.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizProfile");
        StringBuilder sb = new StringBuilder();
        sb.append(bundle.getInt("_wxapi_jump_to_biz_webview_req_scene"));
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_webview_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_webview_req_ext_msg"), sb.toString()}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendJumpToOfflinePayReq(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToOfflinePay"), null, null, new String[]{this.appId}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendLaunchWXMiniprogram(Context context2, BaseReq baseReq) {
        WXLaunchMiniProgram.Req req = (WXLaunchMiniProgram.Req) baseReq;
        ContentResolver contentResolver = context2.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/launchWXMiniprogram");
        StringBuilder sb = new StringBuilder();
        sb.append(req.miniprogramType);
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, req.userName, req.path, sb.toString(), req.extData}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendNonTaxPay(Context context2, BaseReq baseReq) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "3", URLEncoder.encode(String.format("url=%s", new Object[]{URLEncoder.encode(((WXNontaxPay.Req) baseReq).url)}))}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendOpenBusiLuckyMoney(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusiLuckyMoney"), null, null, new String[]{this.appId, bundle.getString("_wxapi_open_busi_lucky_money_timeStamp"), bundle.getString("_wxapi_open_busi_lucky_money_nonceStr"), bundle.getString("_wxapi_open_busi_lucky_money_signType"), bundle.getString("_wxapi_open_busi_lucky_money_signature"), bundle.getString("_wxapi_open_busi_lucky_money_package")}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendOpenBusinessView(Context context2, BaseReq baseReq) {
        WXOpenBusinessView.Req req = (WXOpenBusinessView.Req) baseReq;
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusinessView"), null, null, new String[]{this.appId, req.businessType, req.query, req.extInfo}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendOpenBusinessWebview(Context context2, BaseReq baseReq) {
        WXOpenBusinessWebview.Req req = (WXOpenBusinessWebview.Req) baseReq;
        ContentResolver contentResolver = context2.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusinessWebview");
        String str = "";
        if (req.queryInfo != null && req.queryInfo.size() > 0) {
            str = new JSONObject(req.queryInfo).toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(req.businessType);
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, sb.toString(), str}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendOpenRankListReq(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openRankList"), null, null, new String[0], null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendOpenWebview(Context context2, Bundle bundle) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openWebview"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_webview_url"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendPayInSurance(Context context2, BaseReq baseReq) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "4", URLEncoder.encode(String.format("url=%s", new Object[]{URLEncoder.encode(((WXPayInsurance.Req) baseReq).url)}))}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendPayReq(Context context2, Bundle bundle) {
        if (wxappPayEntryClassname == null) {
            wxappPayEntryClassname = new MMSharedPreferences(context2).getString("_wxapp_pay_entry_classname_", null);
            StringBuilder sb = new StringBuilder("pay, set wxappPayEntryClassname = ");
            sb.append(wxappPayEntryClassname);
            Log.d(TAG, sb.toString());
            if (wxappPayEntryClassname == null) {
                try {
                    wxappPayEntryClassname = context2.getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getString("com.tencent.mm.BuildInfo.OPEN_SDK_PAY_ENTRY_CLASSNAME", null);
                } catch (Exception e) {
                    StringBuilder sb2 = new StringBuilder("get from metaData failed : ");
                    sb2.append(e.getMessage());
                    Log.e(TAG, sb2.toString());
                }
            }
            if (wxappPayEntryClassname == null) {
                Log.e(TAG, "pay fail, wxappPayEntryClassname is null");
                return false;
            }
        }
        Args args = new Args();
        args.bundle = bundle;
        args.targetPkgName = "com.tencent.mm";
        args.targetClassName = wxappPayEntryClassname;
        return MMessageActV2.send(context2, args);
    }

    private boolean sendSubscribeMessage(Context context2, BaseReq baseReq) {
        SubscribeMessage.Req req = (SubscribeMessage.Req) baseReq;
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "1", String.valueOf(req.scene), req.templateID, req.reserved}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    private boolean sendSubscribeMiniProgramMsg(Context context2, BaseReq baseReq) {
        Cursor query = context2.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "5", ((SubscribeMiniProgramMsg.Req) baseReq).miniProgramAppId}, null);
        if (query != null) {
            query.close();
        }
        return true;
    }

    public void detach() {
        Log.d(TAG, "detach");
        this.detached = true;
        this.context = null;
    }

    public int getWXAppSupportAPI() {
        if (this.detached) {
            throw new IllegalStateException("getWXAppSupportAPI fail, WXMsgImpl has been detached");
        } else if (!isWXAppInstalled()) {
            Log.e(TAG, "open wx app failed, not installed or signature check failed");
            return 0;
        } else {
            int i = new MMSharedPreferences(this.context).getInt("_build_info_sdk_int_", 0);
            if (i == 0) {
                try {
                    return this.context.getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getInt("com.tencent.mm.BuildInfo.OPEN_SDK_VERSION", 0);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("get from metaData failed : ");
                    sb.append(e.getMessage());
                    Log.e(TAG, sb.toString());
                }
            }
            return i;
        }
    }

    public boolean handleIntent(Intent intent, IWXAPIEventHandler iWXAPIEventHandler) {
        try {
            if (!WXApiImplComm.isIntentFromWx(intent, Token.WX_TOKEN_VALUE_MSG)) {
                Log.i(TAG, "handleIntent fail, intent not from weixin msg");
                return false;
            } else if (this.detached) {
                throw new IllegalStateException("handleIntent fail, WXMsgImpl has been detached");
            } else {
                String stringExtra = intent.getStringExtra(ConstantsAPI.CONTENT);
                int intExtra = intent.getIntExtra(ConstantsAPI.SDK_VERSION, 0);
                String stringExtra2 = intent.getStringExtra(ConstantsAPI.APP_PACKAGE);
                if (stringExtra2 != null) {
                    if (stringExtra2.length() != 0) {
                        if (!checkSumConsistent(intent.getByteArrayExtra(ConstantsAPI.CHECK_SUM), b.a(stringExtra, intExtra, stringExtra2))) {
                            Log.e(TAG, "checksum fail");
                            return false;
                        }
                        int intExtra2 = intent.getIntExtra("_wxapi_command_type", 0);
                        Log.i(TAG, "handleIntent, cmd = ".concat(String.valueOf(intExtra2)));
                        switch (intExtra2) {
                            case 1:
                                iWXAPIEventHandler.onResp(new SendAuth.Resp(intent.getExtras()));
                                return true;
                            case 2:
                                iWXAPIEventHandler.onResp(new SendMessageToWX.Resp(intent.getExtras()));
                                return true;
                            case 3:
                                iWXAPIEventHandler.onReq(new GetMessageFromWX.Req(intent.getExtras()));
                                return true;
                            case 4:
                                ShowMessageFromWX.Req req = new ShowMessageFromWX.Req(intent.getExtras());
                                String str = req.message.messageExt;
                                if (str == null || !str.contains("wx_internal_resptype")) {
                                    if (str != null && str.contains("openbusinesswebview")) {
                                        try {
                                            Uri parse = Uri.parse(str);
                                            if (parse == null || !"openbusinesswebview".equals(parse.getHost())) {
                                                Log.d(TAG, "not openbusinesswebview %".concat(String.valueOf(str)));
                                            } else {
                                                WXOpenBusinessWebview.Resp resp = new WXOpenBusinessWebview.Resp();
                                                String queryParameter = parse.getQueryParameter("ret");
                                                if (queryParameter != null && queryParameter.length() > 0) {
                                                    resp.errCode = d.c(queryParameter);
                                                }
                                                resp.resultInfo = parse.getQueryParameter("resultInfo");
                                                resp.errStr = parse.getQueryParameter("errmsg");
                                                String queryParameter2 = parse.getQueryParameter("type");
                                                if (queryParameter2 != null && queryParameter2.length() > 0) {
                                                    resp.businessType = d.c(queryParameter2);
                                                }
                                                iWXAPIEventHandler.onResp(resp);
                                                return true;
                                            }
                                        } catch (Exception e) {
                                            StringBuilder sb = new StringBuilder("parse fail, ex = ");
                                            sb.append(e.getMessage());
                                            Log.e(TAG, sb.toString());
                                        }
                                    }
                                    iWXAPIEventHandler.onReq(req);
                                    return true;
                                }
                                boolean handleWxInternalRespType = handleWxInternalRespType(str, iWXAPIEventHandler);
                                Log.i(TAG, "handleIntent, extInfo contains wx_internal_resptype, ret = ".concat(String.valueOf(handleWxInternalRespType)));
                                return handleWxInternalRespType;
                            case 5:
                                iWXAPIEventHandler.onResp(new PayResp(intent.getExtras()));
                                return true;
                            case 6:
                                iWXAPIEventHandler.onReq(new LaunchFromWX.Req(intent.getExtras()));
                                return true;
                            case 9:
                                iWXAPIEventHandler.onResp(new AddCardToWXCardPackage.Resp(intent.getExtras()));
                                return true;
                            case 12:
                                iWXAPIEventHandler.onResp(new OpenWebview.Resp(intent.getExtras()));
                                return true;
                            case 14:
                                iWXAPIEventHandler.onResp(new CreateChatroom.Resp(intent.getExtras()));
                                return true;
                            case 15:
                                iWXAPIEventHandler.onResp(new JoinChatroom.Resp(intent.getExtras()));
                                return true;
                            case 16:
                                iWXAPIEventHandler.onResp(new ChooseCardFromWXCardPackage.Resp(intent.getExtras()));
                                return true;
                            case 17:
                                iWXAPIEventHandler.onResp(new HandleScanResult.Resp(intent.getExtras()));
                                return true;
                            case 19:
                                iWXAPIEventHandler.onResp(new WXLaunchMiniProgram.Resp(intent.getExtras()));
                                return true;
                            case 24:
                                iWXAPIEventHandler.onResp(new JumpToOfflinePay.Resp(intent.getExtras()));
                                return true;
                            case 25:
                                iWXAPIEventHandler.onResp(new WXOpenBusinessWebview.Resp(intent.getExtras()));
                                return true;
                            case 26:
                                iWXAPIEventHandler.onResp(new WXOpenBusinessView.Resp(intent.getExtras()));
                                return true;
                            default:
                                Log.e(TAG, "unknown cmd = ".concat(String.valueOf(intExtra2)));
                                return false;
                        }
                        StringBuilder sb2 = new StringBuilder("handleIntent fail, ex = ");
                        sb2.append(e.getMessage());
                        Log.e(TAG, sb2.toString());
                        return false;
                    }
                }
                Log.e(TAG, "invalid argument");
                return false;
            }
        } catch (Exception e2) {
            StringBuilder sb22 = new StringBuilder("handleIntent fail, ex = ");
            sb22.append(e2.getMessage());
            Log.e(TAG, sb22.toString());
            return false;
        }
    }

    public boolean isWXAppInstalled() {
        if (this.detached) {
            throw new IllegalStateException("isWXAppInstalled fail, WXMsgImpl has been detached");
        }
        try {
            PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo("com.tencent.mm", 64);
            if (packageInfo == null) {
                return false;
            }
            return WXApiImplComm.validateAppSignature(this.context, packageInfo.signatures, this.checkSignature);
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    public boolean openWXApp() {
        if (this.detached) {
            throw new IllegalStateException("openWXApp fail, WXMsgImpl has been detached");
        } else if (!isWXAppInstalled()) {
            Log.e(TAG, "open wx app failed, not installed or signature check failed");
            return false;
        } else {
            try {
                this.context.startActivity(this.context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm"));
                return true;
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("startActivity fail, exception = ");
                sb.append(e.getMessage());
                Log.e(TAG, sb.toString());
                return false;
            }
        }
    }

    public boolean registerApp(String str) {
        return registerApp(str, 0);
    }

    public boolean registerApp(String str, long j) {
        if (this.detached) {
            throw new IllegalStateException("registerApp fail, WXMsgImpl has been detached");
        } else if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.e(TAG, "register app failed for wechat app signature check failed");
            return false;
        } else {
            Log.d(TAG, "registerApp, appId = ".concat(String.valueOf(str)));
            if (str != null) {
                this.appId = str;
            }
            Log.d(TAG, "registerApp, appId = ".concat(String.valueOf(str)));
            if (str != null) {
                this.appId = str;
            }
            StringBuilder sb = new StringBuilder("register app ");
            sb.append(this.context.getPackageName());
            Log.d(TAG, sb.toString());
            C0064a aVar = new C0064a();
            aVar.a = "com.tencent.mm";
            aVar.action = ConstantsAPI.ACTION_HANDLE_APP_REGISTER;
            StringBuilder sb2 = new StringBuilder("weixin://registerapp?appid=");
            sb2.append(this.appId);
            aVar.content = sb2.toString();
            aVar.b = j;
            return a.a(this.context, aVar);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:119:0x0222  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendReq(com.tencent.mm.opensdk.modelbase.BaseReq r9) {
        /*
            r8 = this;
            boolean r0 = r8.detached
            if (r0 == 0) goto L_0x000c
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "sendReq fail, WXMsgImpl has been detached"
            r9.<init>(r0)
            throw r9
        L_0x000c:
            android.content.Context r0 = r8.context
            java.lang.String r1 = "com.tencent.mm"
            boolean r2 = r8.checkSignature
            boolean r0 = com.tencent.mm.opensdk.openapi.WXApiImplComm.validateAppSignatureForPackage(r0, r1, r2)
            r1 = 0
            if (r0 != 0) goto L_0x0021
            java.lang.String r9 = "MicroMsg.SDK.WXApiImplV10"
            java.lang.String r0 = "sendReq failed for wechat app signature check failed"
        L_0x001d:
            com.tencent.mm.opensdk.utils.Log.e(r9, r0)
            return r1
        L_0x0021:
            boolean r0 = r9.checkArgs()
            if (r0 != 0) goto L_0x002c
            java.lang.String r9 = "MicroMsg.SDK.WXApiImplV10"
            java.lang.String r0 = "sendReq checkArgs fail"
            goto L_0x001d
        L_0x002c:
            java.lang.String r0 = "MicroMsg.SDK.WXApiImplV10"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "sendReq, req type = "
            r2.<init>(r3)
            int r3 = r9.getType()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.tencent.mm.opensdk.utils.Log.i(r0, r2)
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            r9.toBundle(r0)
            int r2 = r9.getType()
            r3 = 5
            if (r2 != r3) goto L_0x0059
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendPayReq(r9, r0)
            return r9
        L_0x0059:
            int r2 = r9.getType()
            r3 = 7
            if (r2 != r3) goto L_0x0067
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendJumpToBizProfileReq(r9, r0)
            return r9
        L_0x0067:
            int r2 = r9.getType()
            r3 = 8
            if (r2 != r3) goto L_0x0076
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendJumpToBizWebviewReq(r9, r0)
            return r9
        L_0x0076:
            int r2 = r9.getType()
            r3 = 10
            if (r2 != r3) goto L_0x0085
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendJumpToBizTempSessionReq(r9, r0)
            return r9
        L_0x0085:
            int r2 = r9.getType()
            r3 = 9
            if (r2 != r3) goto L_0x0094
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendAddCardToWX(r9, r0)
            return r9
        L_0x0094:
            int r2 = r9.getType()
            r3 = 16
            if (r2 != r3) goto L_0x00a3
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendChooseCardFromWX(r9, r0)
            return r9
        L_0x00a3:
            int r2 = r9.getType()
            r3 = 11
            if (r2 != r3) goto L_0x00b2
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendOpenRankListReq(r9, r0)
            return r9
        L_0x00b2:
            int r2 = r9.getType()
            r3 = 12
            if (r2 != r3) goto L_0x00c1
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendOpenWebview(r9, r0)
            return r9
        L_0x00c1:
            int r2 = r9.getType()
            r3 = 25
            if (r2 != r3) goto L_0x00d0
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendOpenBusinessWebview(r0, r9)
            return r9
        L_0x00d0:
            int r2 = r9.getType()
            r3 = 13
            if (r2 != r3) goto L_0x00df
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendOpenBusiLuckyMoney(r9, r0)
            return r9
        L_0x00df:
            int r2 = r9.getType()
            r3 = 14
            if (r2 != r3) goto L_0x00ee
            android.content.Context r9 = r8.context
            boolean r9 = r8.createChatroom(r9, r0)
            return r9
        L_0x00ee:
            int r2 = r9.getType()
            r3 = 15
            if (r2 != r3) goto L_0x00fd
            android.content.Context r9 = r8.context
            boolean r9 = r8.joinChatroom(r9, r0)
            return r9
        L_0x00fd:
            int r2 = r9.getType()
            r3 = 17
            if (r2 != r3) goto L_0x010c
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendHandleScanResult(r9, r0)
            return r9
        L_0x010c:
            int r2 = r9.getType()
            r3 = 18
            if (r2 != r3) goto L_0x011b
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendSubscribeMessage(r0, r9)
            return r9
        L_0x011b:
            int r2 = r9.getType()
            r3 = 23
            if (r2 != r3) goto L_0x012a
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendSubscribeMiniProgramMsg(r0, r9)
            return r9
        L_0x012a:
            int r2 = r9.getType()
            r3 = 19
            if (r2 != r3) goto L_0x0139
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendLaunchWXMiniprogram(r0, r9)
            return r9
        L_0x0139:
            int r2 = r9.getType()
            r3 = 26
            if (r2 != r3) goto L_0x0148
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendOpenBusinessView(r0, r9)
            return r9
        L_0x0148:
            int r2 = r9.getType()
            r3 = 20
            if (r2 != r3) goto L_0x0157
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendInvoiceAuthInsert(r0, r9)
            return r9
        L_0x0157:
            int r2 = r9.getType()
            r3 = 21
            if (r2 != r3) goto L_0x0166
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendNonTaxPay(r0, r9)
            return r9
        L_0x0166:
            int r2 = r9.getType()
            r3 = 22
            if (r2 != r3) goto L_0x0175
            android.content.Context r0 = r8.context
            boolean r9 = r8.sendPayInSurance(r0, r9)
            return r9
        L_0x0175:
            int r2 = r9.getType()
            r3 = 24
            if (r2 != r3) goto L_0x0184
            android.content.Context r9 = r8.context
            boolean r9 = r8.sendJumpToOfflinePayReq(r9, r0)
            return r9
        L_0x0184:
            int r2 = r9.getType()
            r3 = 2
            if (r2 != r3) goto L_0x0227
            java.lang.String r2 = "_wxapi_sendmessagetowx_req_media_type"
            int r2 = r0.getInt(r2)
            boolean r3 = com.tencent.mm.opensdk.utils.d.a(r2)
            if (r3 == 0) goto L_0x0227
            r3 = r9
            com.tencent.mm.opensdk.modelmsg.SendMessageToWX$Req r3 = (com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req) r3
            int r4 = r8.getWXAppSupportAPI()
            r5 = 620756993(0x25000001, float:1.1102232E-16)
            if (r4 >= r5) goto L_0x01b5
            com.tencent.mm.opensdk.modelmsg.WXWebpageObject r2 = new com.tencent.mm.opensdk.modelmsg.WXWebpageObject
            r2.<init>()
        L_0x01a8:
            java.lang.String r4 = "_wxminiprogram_webpageurl"
            java.lang.String r4 = r0.getString(r4)
            r2.webpageUrl = r4
            com.tencent.mm.opensdk.modelmsg.WXMediaMessage r4 = r3.message
            r4.mediaObject = r2
            goto L_0x021d
        L_0x01b5:
            r4 = 46
            if (r2 != r4) goto L_0x01c8
            int r2 = r8.getWXAppSupportAPI()
            r4 = 620953856(0x25030100, float:1.1362778E-16)
            if (r2 >= r4) goto L_0x01c8
            com.tencent.mm.opensdk.modelmsg.WXWebpageObject r2 = new com.tencent.mm.opensdk.modelmsg.WXWebpageObject
            r2.<init>()
            goto L_0x01a8
        L_0x01c8:
            com.tencent.mm.opensdk.modelmsg.WXMediaMessage r2 = r3.message
            com.tencent.mm.opensdk.modelmsg.WXMediaMessage$IMediaObject r2 = r2.mediaObject
            com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject r2 = (com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject) r2
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = r2.userName
            r4.append(r5)
            java.lang.String r5 = "@app"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r2.userName = r4
            java.lang.String r4 = r2.path
            boolean r5 = com.tencent.mm.opensdk.utils.d.b(r4)
            if (r5 != 0) goto L_0x021d
            java.lang.String r5 = "\\?"
            java.lang.String[] r4 = r4.split(r5)
            int r5 = r4.length
            r6 = 1
            if (r5 <= r6) goto L_0x020e
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r7 = r4[r1]
            r5.append(r7)
            java.lang.String r7 = ".html?"
            r5.append(r7)
            r4 = r4[r6]
        L_0x0206:
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            goto L_0x021b
        L_0x020e:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r4 = r4[r1]
            r5.append(r4)
            java.lang.String r4 = ".html"
            goto L_0x0206
        L_0x021b:
            r2.path = r4
        L_0x021d:
            int r2 = r3.scene
            r4 = 3
            if (r2 == r4) goto L_0x0224
            r3.scene = r1
        L_0x0224:
            r9.toBundle(r0)
        L_0x0227:
            android.content.Context r9 = r8.context
            java.lang.String r9 = r8.getTokenFromWX(r9)
            com.tencent.mm.opensdk.channel.MMessageActV2$Args r1 = new com.tencent.mm.opensdk.channel.MMessageActV2$Args
            r1.<init>()
            r1.bundle = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "weixin://sendreq?appid="
            r0.<init>(r2)
            java.lang.String r2 = r8.appId
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            r1.content = r0
            java.lang.String r0 = "com.tencent.mm"
            r1.targetPkgName = r0
            java.lang.String r0 = "com.tencent.mm.plugin.base.stub.WXEntryActivity"
            r1.targetClassName = r0
            r1.token = r9
            android.content.Context r9 = r8.context
            boolean r9 = com.tencent.mm.opensdk.channel.MMessageActV2.send(r9, r1)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mm.opensdk.openapi.BaseWXApiImplV10.sendReq(com.tencent.mm.opensdk.modelbase.BaseReq):boolean");
    }

    public boolean sendResp(BaseResp baseResp) {
        String str;
        String str2;
        if (this.detached) {
            throw new IllegalStateException("sendResp fail, WXMsgImpl has been detached");
        }
        if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            str = TAG;
            str2 = "sendResp failed for wechat app signature check failed";
        } else if (!baseResp.checkArgs()) {
            str = TAG;
            str2 = "sendResp checkArgs fail";
        } else {
            Bundle bundle = new Bundle();
            baseResp.toBundle(bundle);
            Args args = new Args();
            args.bundle = bundle;
            StringBuilder sb = new StringBuilder("weixin://sendresp?appid=");
            sb.append(this.appId);
            args.content = sb.toString();
            args.targetPkgName = "com.tencent.mm";
            args.targetClassName = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
            return MMessageActV2.send(this.context, args);
        }
        Log.e(str, str2);
        return false;
    }

    public void setLogImpl(ILog iLog) {
        Log.setLogImpl(iLog);
    }

    public void unregisterApp() {
        if (this.detached) {
            throw new IllegalStateException("unregisterApp fail, WXMsgImpl has been detached");
        } else if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.e(TAG, "unregister app failed for wechat app signature check failed");
        } else {
            StringBuilder sb = new StringBuilder("unregisterApp, appId = ");
            sb.append(this.appId);
            Log.d(TAG, sb.toString());
            if (this.appId == null || this.appId.length() == 0) {
                Log.e(TAG, "unregisterApp fail, appId is empty");
                return;
            }
            StringBuilder sb2 = new StringBuilder("unregister app ");
            sb2.append(this.context.getPackageName());
            Log.d(TAG, sb2.toString());
            C0064a aVar = new C0064a();
            aVar.a = "com.tencent.mm";
            aVar.action = ConstantsAPI.ACTION_HANDLE_APP_UNREGISTER;
            StringBuilder sb3 = new StringBuilder("weixin://unregisterapp?appid=");
            sb3.append(this.appId);
            aVar.content = sb3.toString();
            a.a(this.context, aVar);
        }
    }
}
