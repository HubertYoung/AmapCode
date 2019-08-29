package com.alipay.android.phone.inside.proxy;

import android.content.Context;
import android.os.Bundle;
import com.alipay.android.phone.inside.api.IRemoteServiceCallback;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.util.OperationResultUtil;
import com.alipay.android.phone.inside.bizadapter.InsideSdkInitializer;
import com.alipay.android.phone.inside.bizadapter.service.IInteractionProxy;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.commonbiz.action.SdkActionFactory;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.perf.PerfLogger;
import com.alipay.android.phone.inside.main.action.AccountInfoAction;
import com.alipay.android.phone.inside.main.action.AccountLogoutAction;
import com.alipay.android.phone.inside.main.action.AccountManagerAction;
import com.alipay.android.phone.inside.main.action.AliAutoLoginAction;
import com.alipay.android.phone.inside.main.action.AliAutoLoginCheckConditionAction;
import com.alipay.android.phone.inside.main.action.AlipayFastOAuthAction;
import com.alipay.android.phone.inside.main.action.AlipayFuncListAction;
import com.alipay.android.phone.inside.main.action.AlipayOAuthAction;
import com.alipay.android.phone.inside.main.action.AuthAction;
import com.alipay.android.phone.inside.main.action.BusAllCardListAction;
import com.alipay.android.phone.inside.main.action.BusAllCityListAction;
import com.alipay.android.phone.inside.main.action.BusAuthAction;
import com.alipay.android.phone.inside.main.action.BusCardListAction;
import com.alipay.android.phone.inside.main.action.BusCloseAction;
import com.alipay.android.phone.inside.main.action.BusGenAction;
import com.alipay.android.phone.inside.main.action.BusReceiveCardAction;
import com.alipay.android.phone.inside.main.action.ChangeCodeAuthAction;
import com.alipay.android.phone.inside.main.action.CheckAccountUniformityAction;
import com.alipay.android.phone.inside.main.action.CheckAlipayStatusAction;
import com.alipay.android.phone.inside.main.action.CodeInvalidAction;
import com.alipay.android.phone.inside.main.action.GenerateCodeAction;
import com.alipay.android.phone.inside.main.action.IotAdsExitAction;
import com.alipay.android.phone.inside.main.action.IotAdsInitAction;
import com.alipay.android.phone.inside.main.action.IotAdsLoadingPageAction;
import com.alipay.android.phone.inside.main.action.IotAdsPayPrepareAction;
import com.alipay.android.phone.inside.main.action.IotAdsPreMemberAction;
import com.alipay.android.phone.inside.main.action.IotAdsResultPageAction;
import com.alipay.android.phone.inside.main.action.IotAdxAction;
import com.alipay.android.phone.inside.main.action.IotAdxBoothInfoAction;
import com.alipay.android.phone.inside.main.action.IotAdxExitAction;
import com.alipay.android.phone.inside.main.action.IotAdxInitAction;
import com.alipay.android.phone.inside.main.action.IotCashierBindAction;
import com.alipay.android.phone.inside.main.action.IotCashierCheckBindStatusAction;
import com.alipay.android.phone.inside.main.action.IotCashierInitAction;
import com.alipay.android.phone.inside.main.action.IotCashierPayAndResultAction;
import com.alipay.android.phone.inside.main.action.IotCashierResultAction;
import com.alipay.android.phone.inside.main.action.IotCashierResultErrorAction;
import com.alipay.android.phone.inside.main.action.IotCashierUnbindAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayBindAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayCheckBindAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayClearAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayGenSerialNoAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayInitAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayOrderAndPayAction;
import com.alipay.android.phone.inside.main.action.IotFtfPayQueryMerchantAction;
import com.alipay.android.phone.inside.main.action.IotPayBindCodeAction;
import com.alipay.android.phone.inside.main.action.IotPayCheckBindStatusAction;
import com.alipay.android.phone.inside.main.action.IotPayGenTransferCodeAction;
import com.alipay.android.phone.inside.main.action.IotPayInitAction;
import com.alipay.android.phone.inside.main.action.IotPayOrderAndPayAction;
import com.alipay.android.phone.inside.main.action.IotPayQueryBindResultAction;
import com.alipay.android.phone.inside.main.action.IotPayQueryPayResultAction;
import com.alipay.android.phone.inside.main.action.IotPayQueryUnbindResultAction;
import com.alipay.android.phone.inside.main.action.IotPayTradeCloseAction;
import com.alipay.android.phone.inside.main.action.IotPayTradeRefundAction;
import com.alipay.android.phone.inside.main.action.IotPayUnbindCodeAction;
import com.alipay.android.phone.inside.main.action.JiebeiCheckAuthRelationAction;
import com.alipay.android.phone.inside.main.action.JiebeiStartAction;
import com.alipay.android.phone.inside.main.action.JumpAlipayFuncAction;
import com.alipay.android.phone.inside.main.action.JumpAlipaySchemeAction;
import com.alipay.android.phone.inside.main.action.JumpShareTokenAction;
import com.alipay.android.phone.inside.main.action.JumpTinyAppAction;
import com.alipay.android.phone.inside.main.action.LaunchAction;
import com.alipay.android.phone.inside.main.action.LoginOutAction;
import com.alipay.android.phone.inside.main.action.McAccountChangeAction;
import com.alipay.android.phone.inside.main.action.OAuthAccountUniformityAction;
import com.alipay.android.phone.inside.main.action.OfflineRenderAction;
import com.alipay.android.phone.inside.main.action.OnlinePayAction;
import com.alipay.android.phone.inside.main.action.OpenAuthLoginAction;
import com.alipay.android.phone.inside.main.action.PreCheckAction;
import com.alipay.android.phone.inside.main.action.PreLoadAction;
import com.alipay.android.phone.inside.main.action.PreLoadConfigAction;
import com.alipay.android.phone.inside.main.action.PushAction;
import com.alipay.android.phone.inside.main.action.QueryPayResultAction;
import com.alipay.android.phone.inside.main.action.ReportDeviceEnvAction;
import com.alipay.android.phone.inside.main.action.SafeJumpAlipaySchemeAction;
import com.alipay.android.phone.inside.main.action.ScanAction;
import com.alipay.android.phone.inside.main.action.ScanActionV2;
import com.alipay.android.phone.inside.main.action.ShareTokenAction;
import com.alipay.android.phone.inside.main.action.SmartSellPayAuthAction;
import com.alipay.android.phone.inside.main.action.SmartSellPayAuthPreloadAction;
import com.alipay.android.phone.inside.main.action.SmartSellV2AppInfoAction;
import com.alipay.android.phone.inside.main.action.SmartSellV2FaceAuthAction;
import com.alipay.android.phone.inside.main.action.SmartSellV2PreinitAction;
import com.alipay.android.phone.inside.main.action.SmartSellV2PreloadAction;
import com.alipay.android.phone.inside.main.action.SwitchChannelAction;
import com.alipay.android.phone.inside.main.action.SwitchUserAction;
import com.alipay.android.phone.inside.main.action.WalletPreloadAction;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class InsideInteractionProxy implements IInteractionProxy {
    private static List<String> b;
    private final Context a;
    /* access modifiers changed from: private */
    public IRemoteServiceCallback c;

    public static void a() {
    }

    public final Bundle b(Bundle bundle) {
        InsideSdkInitializer.a(this.a, this);
        LoggerFactory.a();
        OperationResult a2 = a(c(bundle));
        LoggerFactory.b();
        InsideSdkInitializer.a(this.a);
        Bundle bundle2 = new Bundle();
        if (a2 != null) {
            return OperationResultUtil.serializeResultToBundle(a2);
        }
        LoggerFactory.e().a("action", "OperationResultNull");
        return bundle2;
    }

    public InsideInteractionProxy(Context context) {
        this.a = context;
    }

    public final void a(IRemoteServiceCallback iRemoteServiceCallback) {
        this.c = iRemoteServiceCallback;
    }

    private static void b() {
        ServiceExecutor.a((String) "COMMON_SERVICE_SET_RUNNING_STATUS", Boolean.FALSE);
    }

    private static JSONObject c(Bundle bundle) {
        try {
            return new JSONObject(bundle.getString("content"));
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "insideSdk", th);
            return null;
        }
    }

    public final void a(final Bundle bundle) {
        if (this.c == null) {
            LoggerFactory.e().a("main", "NotifyServiceCallbackNull");
            return;
        }
        LoggerFactory.d().b("main", BehaviorType.EVENT, "NotifyServiceStart");
        new Thread(new Runnable() {
            public void run() {
                LoggerFactory.d().b("main", BehaviorType.EVENT, "NotifyServiceProcess");
                try {
                    InsideInteractionProxy.this.c.notify(bundle);
                } catch (Throwable th) {
                    LoggerFactory.e().a((String) "main", (String) "NotifyServiceEx", th);
                }
            }
        }).start();
    }

    private static OperationResult a(JSONObject jSONObject) {
        String string;
        try {
            ServiceExecutor.a((String) "COMMON_SERVICE_SET_RUNNING_STATUS", Boolean.TRUE);
            string = jSONObject.getString("action");
            if (b.contains(string)) {
                PluginManager.b("REPORT_DEVICE_LOCATION_SERVICE").start(string);
            }
        } catch (Throwable th) {
            try {
                LoggerFactory.f().b((String) "insideSdk", th);
            } catch (Throwable th2) {
                b();
                throw th2;
            }
        }
        OutsideConfig.a(jSONObject);
        try {
            ServiceExecutor.b("COMMONBIZ_SERVICE_OPEN_AUTH_ACCOUNTUNIFORMITY", new Bundle());
        } catch (Throwable th3) {
            LoggerFactory.f().c((String) "insideSdk", th3);
        }
        SdkAction a2 = SdkActionFactory.a(string);
        if (a2 != null) {
            RunningConfig.a(a2.a());
            BehaviorLogger d = LoggerFactory.d();
            BehaviorType behaviorType = BehaviorType.EVENT;
            StringBuilder sb = new StringBuilder("ActionEnter|");
            sb.append(a2.a());
            d.b("action", behaviorType, sb.toString());
            long currentTimeMillis = System.currentTimeMillis();
            OperationResult a3 = a2.a(jSONObject);
            BehaviorLogger d2 = LoggerFactory.d();
            BehaviorType behaviorType2 = BehaviorType.EVENT;
            StringBuilder sb2 = new StringBuilder("ActionResult|");
            sb2.append(a2.a());
            sb2.append(MergeUtil.SEPARATOR_KV);
            sb2.append(a3.getCode().getValue());
            d2.b("action", behaviorType2, sb2.toString());
            PerfLogger c2 = LoggerFactory.c();
            StringBuilder sb3 = new StringBuilder("ActionTime|");
            sb3.append(a2.a());
            c2.a("action", sb3.toString(), Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            b();
            return a3;
        }
        b();
        return null;
    }

    static {
        try {
            SdkActionFactory.a((SdkAction) new CheckAccountUniformityAction());
            SdkActionFactory.a((SdkAction) new PreCheckAction());
            SdkActionFactory.a((SdkAction) new PreLoadAction());
            SdkActionFactory.a((SdkAction) new AuthAction());
            SdkActionFactory.a((SdkAction) new GenerateCodeAction());
            SdkActionFactory.a((SdkAction) new QueryPayResultAction());
            SdkActionFactory.a((SdkAction) new OnlinePayAction());
            SdkActionFactory.a((SdkAction) new PushAction());
            SdkActionFactory.a((SdkAction) new SwitchUserAction());
            SdkActionFactory.a((SdkAction) new LoginOutAction());
            SdkActionFactory.a((SdkAction) new AccountLogoutAction());
            SdkActionFactory.a((SdkAction) new ScanAction());
            SdkActionFactory.a((SdkAction) new ScanActionV2());
            SdkActionFactory.a((SdkAction) new OfflineRenderAction());
            SdkActionFactory.a((SdkAction) new SwitchChannelAction());
            SdkActionFactory.a((SdkAction) new CodeInvalidAction());
            SdkActionFactory.a((SdkAction) new ChangeCodeAuthAction());
            SdkActionFactory.a((SdkAction) new AccountManagerAction());
            SdkActionFactory.a((SdkAction) new AccountInfoAction());
            SdkActionFactory.a((SdkAction) new BusAuthAction());
            SdkActionFactory.a((SdkAction) new BusGenAction());
            SdkActionFactory.a((SdkAction) new BusCloseAction());
            SdkActionFactory.a((SdkAction) new BusReceiveCardAction());
            SdkActionFactory.a((SdkAction) new BusAllCardListAction());
            SdkActionFactory.a((SdkAction) new BusCardListAction());
            SdkActionFactory.a((SdkAction) new BusAllCityListAction());
            SdkActionFactory.a((SdkAction) new JiebeiStartAction());
            SdkActionFactory.a((SdkAction) new JiebeiCheckAuthRelationAction());
            SdkActionFactory.a((SdkAction) new LaunchAction());
            SdkActionFactory.a((SdkAction) new ShareTokenAction());
            SdkActionFactory.a((SdkAction) new JumpShareTokenAction());
            SdkActionFactory.a((SdkAction) new JumpAlipayFuncAction());
            SdkActionFactory.a((SdkAction) new AlipayFuncListAction());
            SdkActionFactory.a((SdkAction) new CheckAlipayStatusAction());
            SdkActionFactory.a((SdkAction) new JumpAlipaySchemeAction());
            SdkActionFactory.a((SdkAction) new WalletPreloadAction());
            SdkActionFactory.a((SdkAction) new SafeJumpAlipaySchemeAction());
            SdkActionFactory.a((SdkAction) new JumpTinyAppAction());
            SdkActionFactory.a((SdkAction) new SmartSellPayAuthAction());
            SdkActionFactory.a((SdkAction) new SmartSellPayAuthPreloadAction());
            SdkActionFactory.a((SdkAction) new SmartSellV2FaceAuthAction());
            SdkActionFactory.a((SdkAction) new SmartSellV2AppInfoAction());
            SdkActionFactory.a((SdkAction) new SmartSellV2PreloadAction());
            SdkActionFactory.a((SdkAction) new SmartSellV2PreinitAction());
            SdkActionFactory.a((SdkAction) new IotPayInitAction());
            SdkActionFactory.a((SdkAction) new IotPayCheckBindStatusAction());
            SdkActionFactory.a((SdkAction) new IotPayBindCodeAction());
            SdkActionFactory.a((SdkAction) new IotPayQueryBindResultAction());
            SdkActionFactory.a((SdkAction) new IotPayUnbindCodeAction());
            SdkActionFactory.a((SdkAction) new IotPayQueryUnbindResultAction());
            SdkActionFactory.a((SdkAction) new IotPayOrderAndPayAction());
            SdkActionFactory.a((SdkAction) new IotPayQueryPayResultAction());
            SdkActionFactory.a((SdkAction) new IotPayTradeCloseAction());
            SdkActionFactory.a((SdkAction) new IotPayTradeRefundAction());
            SdkActionFactory.a((SdkAction) new IotPayGenTransferCodeAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayInitAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayCheckBindAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayQueryMerchantAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayBindAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayGenSerialNoAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayOrderAndPayAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayClearAction());
            SdkActionFactory.a((SdkAction) new IotFtfPayAction());
            SdkActionFactory.a((SdkAction) new AlipayFastOAuthAction());
            SdkActionFactory.a((SdkAction) new AlipayOAuthAction());
            SdkActionFactory.a((SdkAction) new McAccountChangeAction());
            SdkActionFactory.a((SdkAction) new OpenAuthLoginAction());
            SdkActionFactory.a((SdkAction) new OAuthAccountUniformityAction());
            SdkActionFactory.a((SdkAction) new ReportDeviceEnvAction());
            SdkActionFactory.a((SdkAction) new AliAutoLoginAction());
            SdkActionFactory.a((SdkAction) new AliAutoLoginCheckConditionAction());
            SdkActionFactory.a((SdkAction) new PreLoadConfigAction());
            SdkActionFactory.a((SdkAction) new IotAdsResultPageAction());
            SdkActionFactory.a((SdkAction) new IotAdsInitAction());
            SdkActionFactory.a((SdkAction) new IotAdsExitAction());
            SdkActionFactory.a((SdkAction) new IotAdsLoadingPageAction());
            SdkActionFactory.a((SdkAction) new IotAdsPreMemberAction());
            SdkActionFactory.a((SdkAction) new IotAdsPayPrepareAction());
            SdkActionFactory.a((SdkAction) new IotAdxAction());
            SdkActionFactory.a((SdkAction) new IotAdxInitAction());
            SdkActionFactory.a((SdkAction) new IotAdxExitAction());
            SdkActionFactory.a((SdkAction) new IotAdxBoothInfoAction());
            SdkActionFactory.a((SdkAction) new IotCashierInitAction());
            SdkActionFactory.a((SdkAction) new IotCashierBindAction());
            SdkActionFactory.a((SdkAction) new IotCashierCheckBindStatusAction());
            SdkActionFactory.a((SdkAction) new IotCashierPayAndResultAction());
            SdkActionFactory.a((SdkAction) new IotCashierResultAction());
            SdkActionFactory.a((SdkAction) new IotCashierResultErrorAction());
            SdkActionFactory.a((SdkAction) new IotCashierUnbindAction());
            if (b == null) {
                b = new ArrayList();
            }
            b.add(ActionEnum.GENERATE_CODE.getActionName());
            b.add(ActionEnum.ONLINE_PAY.getActionName());
            b.add(ActionEnum.QUERY_PAY_RESULT.getActionName());
            b.add(ActionEnum.SWITCH_USER.getActionName());
            b.add(ActionEnum.PUSH.getActionName());
            b.add(ActionEnum.AUTH.getActionName());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "action", (String) "InitActionEx", th);
        }
    }
}
