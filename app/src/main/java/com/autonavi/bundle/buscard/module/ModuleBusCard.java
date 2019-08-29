package com.autonavi.bundle.buscard.module;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.buscode.BusCitiesModel;
import com.alipay.android.phone.inside.api.model.buscode.BusGenModel;
import com.alipay.android.phone.inside.api.model.buscode.BusReceiveCardModel;
import com.alipay.android.phone.inside.api.model.buscode.BusUnauthModel;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.HashMap;
import org.json.JSONObject;

@AjxModule("buscard")
public class ModuleBusCard extends AbstractModule {
    public static final int INSIDE_BUS_AUTH = 1;
    public static final int INSIDE_BUS_AUTH_TOKEN_INVALID = 7;
    public static final int INSIDE_BUS_CARD_LIST = 3;
    public static final int INSIDE_BUS_CARD_LIST_FOR_RECERVE = 6;
    public static final int INSIDE_BUS_CITIES_LIST = 2;
    public static final int INSIDE_BUS_CODE = 0;
    public static final int INSIDE_BUS_RECERVE_CARD = 4;
    public static final int INSIDE_BUS_UNAUTH = 5;
    public static final String MODULE_NAME = "buscard";
    private axc mBusCardListener = null;
    /* access modifiers changed from: private */
    public HashMap<Integer, JsFunctionCallback> mCallBackList = new HashMap<>();
    private asq orientationChangeListener;

    public ModuleBusCard(IAjxContext iAjxContext) {
        super(iAjxContext);
        setBusCardMsgListener();
    }

    public void setBusCardListener(axc axc) {
        this.mBusCardListener = axc;
    }

    @AjxMethod("handleAjxMessage")
    public void handleAjxMessage(int i, String str) {
        ask.a("song---", "handleAjxMessage");
    }

    @AjxMethod("getBusCardMsg")
    public void getBusCardMsg(int i, String str, JsFunctionCallback jsFunctionCallback) {
        ask.a("song-", "****native getBusCardMsg key - ".concat(String.valueOf(i)));
        this.mCallBackList.put(Integer.valueOf(i), jsFunctionCallback);
        switch (i) {
            case 0:
                getBusCode(str);
                return;
            case 1:
                getBusAuthCode(str);
                return;
            case 2:
                getBusCities();
                return;
            case 3:
                getBusCardList();
                return;
            case 4:
                getBusReceiveCard(str);
                return;
            case 5:
                getUnAuth();
                return;
            case 6:
                getBusCardListForReceive();
                break;
        }
    }

    private void getBusCode(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.optString("cardNo");
            String optString = jSONObject.optString("cardType");
            asl.a();
            BusGenModel busGenModel = new BusGenModel();
            busGenModel.setCardType(optString);
            String c = asl.c();
            String b = asl.b();
            boolean z = !TextUtils.isEmpty(c) && !TextUtils.isEmpty(b);
            busGenModel.setAlipayUserId(b);
            busGenModel.setAuthToken(c);
            busGenModel.setOpenAuthLogin(z);
            asl.a((BaseModel) busGenModel);
            asl.a((BaseModel) busGenModel, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBusAuthCode(String str) {
        if (TextUtils.isEmpty(str)) {
            asl.a().a(false);
        }
        try {
            asl.a().a(new JSONObject(str).optBoolean("isInvalid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBusCities() {
        asl.a();
        BusCitiesModel busCitiesModel = new BusCitiesModel();
        String c = asl.c();
        String b = asl.b();
        boolean z = !TextUtils.isEmpty(c) && !TextUtils.isEmpty(b);
        busCitiesModel.setAlipayUserId(b);
        busCitiesModel.setAuthToken(c);
        busCitiesModel.setOpenAuthLogin(z);
        asl.a((BaseModel) busCitiesModel);
        asl.a((BaseModel) busCitiesModel, 2);
    }

    private void getBusCardList() {
        asl.a().a(3);
    }

    private void getBusCardListForReceive() {
        asl.a().a(6);
    }

    private void getBusReceiveCard(String str) {
        try {
            String optString = new JSONObject(str).optString("cardType");
            asl.a();
            BusReceiveCardModel busReceiveCardModel = new BusReceiveCardModel();
            if (!TextUtils.isEmpty(optString)) {
                busReceiveCardModel.setCardType(optString);
            }
            String c = asl.c();
            String b = asl.b();
            boolean z = false;
            if (!TextUtils.isEmpty(c) && !TextUtils.isEmpty(b)) {
                z = true;
            }
            busReceiveCardModel.setAlipayUserId(b);
            busReceiveCardModel.setAuthToken(c);
            busReceiveCardModel.setOpenAuthLogin(z);
            busReceiveCardModel.setMinVersionCode(131);
            asl.a((BaseModel) busReceiveCardModel);
            asl.a((BaseModel) busReceiveCardModel, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUnAuth() {
        asl.a();
        BusUnauthModel busUnauthModel = new BusUnauthModel();
        String c = asl.c();
        String b = asl.b();
        busUnauthModel.setAuthToken(c);
        busUnauthModel.setAlipayUserId(b);
        busUnauthModel.setOpenAuthLogin(true);
        asl.a((BaseModel) busUnauthModel);
        asl.a((BaseModel) busUnauthModel, 5);
    }

    private void setBusCardMsgListener() {
        asl.a().c = new c() {
            public final void a(int i, String str, String str2) {
                JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) ModuleBusCard.this.mCallBackList.get(Integer.valueOf(i));
                if (jsFunctionCallback != null) {
                    ask.a("song---", "**************************native to js************************");
                    jsFunctionCallback.callback(Integer.valueOf(i), str, str2);
                }
            }
        };
    }

    public final MapManager getMapManager() {
        return DoNotUseTool.getMapManager();
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        ask.a("song---", "ModuleBusCard - onModuleDestroy");
        if (this.mBusCardListener != null) {
            this.mBusCardListener = null;
        }
        if (this.mCallBackList != null && !this.mCallBackList.isEmpty()) {
            this.mCallBackList.clear();
        }
        asl.a().c = null;
    }

    @AjxMethod("loginAmap")
    public void loginAmap(final JsFunctionCallback jsFunctionCallback) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        jsFunctionCallback.callback(Boolean.TRUE);
                        brn brn = (brn) ank.a(brn.class);
                        if (brn != null) {
                            brn.b();
                        }
                        return;
                    }
                    jsFunctionCallback.callback(Boolean.FALSE);
                }
            });
        }
    }
}
