package defpackage;

import android.os.Message;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.buscode.BusAuthModel;
import com.alipay.android.phone.inside.api.model.buscode.BusCardListModel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: asl reason: default package */
/* compiled from: BusCodeInsideSdkUtil */
public final class asl {
    static b a;
    private static asl d;
    boolean b = false;
    public c c;

    /* renamed from: asl$a */
    /* compiled from: BusCodeInsideSdkUtil */
    class a implements anq {
        private String b;

        public final void loginOrBindCancel() {
        }

        a(String str) {
            this.b = str;
        }

        public final void onComplete(boolean z) {
            ask.a("song---", "bind finish callback");
            asl.this.a(1, "bus_auth_9000", this.b);
        }
    }

    /* renamed from: asl$b */
    /* compiled from: BusCodeInsideSdkUtil */
    static class b extends ecs<asl> {
        b(asl asl) {
            super(asl);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            asl asl = (asl) a();
            if (asl == null) {
                ask.a("song---", "CodeHandler-BusCodeInsideSdkUtil is empty");
                return;
            }
            OperationResult operationResult = (OperationResult) message.obj;
            if (operationResult == null) {
                ToastHelper.showToast(UserTrackerConstants.EM_REQUEST_FAILURE);
                return;
            }
            int i = message.what;
            ask.a("song---", " key= ".concat(String.valueOf(i)));
            StringBuilder sb = new StringBuilder(" result= ");
            sb.append(operationResult.getResult());
            ask.a("song---", sb.toString());
            StringBuilder sb2 = new StringBuilder(" memo= ");
            sb2.append(operationResult.getCodeMemo());
            ask.a("song---", sb2.toString());
            StringBuilder sb3 = new StringBuilder(" value= ");
            sb3.append(operationResult.getCodeValue());
            ask.a("song---", sb3.toString());
            if (i == 1) {
                if (!TextUtils.equals(operationResult == null ? "" : operationResult.getCodeValue(), "bus_auth_9000") || !asl.b) {
                    ask.a("song---", "三方登录");
                    asl.a(1, operationResult);
                    return;
                }
                ask.a("song---", "二方登录");
                asl.b = false;
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    String result = operationResult.getResult();
                    try {
                        String optString = new JSONObject(result).optString("result");
                        if (optString != null) {
                            String substring = optString.substring(optString.indexOf("&auth_code=") + 11, optString.indexOf("&result_code="));
                            optString.substring(optString.indexOf("&user_id=") + 9);
                            iAccountService.a(AMapPageUtil.getPageContext(), new a(result), "auth_transport", substring);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            }
            asl.a(i, operationResult);
        }
    }

    /* renamed from: asl$c */
    /* compiled from: BusCodeInsideSdkUtil */
    public interface c {
        void a(int i, String str, String str2);
    }

    /* renamed from: asl$d */
    /* compiled from: BusCodeInsideSdkUtil */
    static class d extends Thread {
        BaseModel a;
        int b;

        d(BaseModel baseModel, int i) {
            this.a = baseModel;
            this.b = i;
        }

        public final void run() {
            super.run();
            if (this.a == null) {
                ask.a("song---", "start request mModel is empty-------");
            } else if (this.b == 0) {
                synchronized (this.a.getClass().getName()) {
                    a();
                }
            } else {
                a();
            }
        }

        private void a() {
            try {
                StringBuilder sb = new StringBuilder("start request -------");
                sb.append(this.a.getClass().getName());
                ask.a("song---", sb.toString());
                OperationResult startAction = InsideOperationService.getInstance().startAction(AMapPageUtil.getAppContext(), this.a);
                StringBuilder sb2 = new StringBuilder("request finish -------");
                sb2.append(this.a.getClass().getName());
                ask.a("song---", sb2.toString());
                if (asl.a != null) {
                    Message obtainMessage = asl.a.obtainMessage();
                    obtainMessage.what = this.b;
                    obtainMessage.obj = startAction;
                    asl.a.sendMessage(obtainMessage);
                    return;
                }
                ask.a("song---", "request finish sHandler is empty-------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized asl a() {
        asl asl;
        synchronized (asl.class) {
            try {
                if (d == null) {
                    d = new asl();
                    a = new b(d);
                }
                asl = d;
            }
        }
        return asl;
    }

    public final void a(boolean z) {
        BusAuthModel busAuthModel = new BusAuthModel();
        if (z) {
            this.b = true;
            busAuthModel.setOpenAuthLogin(false);
        } else {
            String c2 = c();
            String b2 = b();
            if (TextUtils.isEmpty(c2) || TextUtils.isEmpty(b2)) {
                this.b = true;
                busAuthModel.setOpenAuthLogin(false);
            } else {
                busAuthModel.setOpenAuthLogin(true);
                busAuthModel.setAuthToken(c2);
                busAuthModel.setAlipayUserId(b2);
            }
        }
        a((BaseModel) busAuthModel);
        if (asj.a == null) {
            asj.a = new asj();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("app_id", "2016011801101621");
        hashMap.put("pid", "2088021122108311");
        hashMap.put("apiname", "com.alipay.account.auth");
        hashMap.put("method", "alipay.open.auth.sdk.code.get");
        hashMap.put("app_name", "inside");
        hashMap.put("biz_type", "openservice");
        hashMap.put("product_id", "APP_FAST_LOGIN");
        hashMap.put("scope", "auth_transport");
        hashMap.put("target_id", Long.toString(System.currentTimeMillis()));
        hashMap.put("auth_type", "ALIPAYINSIDE");
        hashMap.put("sign_type", "RSA");
        String a2 = asj.a(hashMap);
        String a3 = asj.a(hashMap, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMguYBEHR99slsyl\nV9xtPyDueJsro5yhRdIYBCE2OvQMfuA2b/nvZlrdUmuOcv+bXN3ujv5pZb9PB2jF\n700q3i5F/qqhiLYl0xtzejg2D8e445zoAHuDT2tL1kjNDmXC4dxFFGZZ+yMxzCnO\ny2Y/twryuGJF0n66c3chw+Oi4ejlAgMBAAECgYBT8KZV3aC0vlsJmzeZdbHoBDdM\nkeL8dd/KNkndB1l3Jpo5OHqB6nIYHgBGm6f7KNGrOjJ52gZRTzlDJOSwjg41yTWg\n26NmUHrlukrsHCv2ndoeJGBh6X9RZRkJxgGXWZ0NOt6badtRhoOoCe7DqTX94ZBQ\nAFkcYxP8p8n17IdoAQJBAOivgYsPErzX41M3O+QnSpzNp5jzfr3qG7pZdUdzlA4Y\n5f0oJpwe+kGTcPgcXiD3kpHX+HmlYDYQmU4tAjoPRuUCQQDcPR2eLmzZpC6tpYyp\nxYLI2qCw9hsTWvGSHfVH7FrKpRId0XR0Mf+6YEoLJo3AZ0xDpIcd31Pksimqk6HO\ny/oBAkEAj2847M7C3zQ5xp9ixPbPkK9ZY/idpVZ99zaUDBKcLsB8bbzlaBHUdL39\nwoRCJhJXAJ5gZiRilZFP35fxKncmXQJAIIQ1d0FLeOawrZqfpgEvShBdYUM0xCrN\nN9GMgU34KastfZGLLAylwRKuW+8ZRqr5q5MDD/oFHOLhG/ooDaw4AQJBAMtZD/dU\n7abQL5iSXzjQPVdNl5XxHSi4bGvPRycrbdrlV7IN0/J4aDih2ok/jexh+2SB8t82\nAhV3eKzeM+GQzM8=\n");
        StringBuilder sb = new StringBuilder();
        sb.append(a2);
        sb.append("&");
        sb.append(a3);
        busAuthModel.setAuthBizData(sb.toString());
        String diu = TextUtils.isEmpty(NetworkParam.getAdiu()) ? NetworkParam.getDiu() : NetworkParam.getAdiu();
        if (TextUtils.isEmpty(diu)) {
            diu = NetworkParam.getTaobaoID();
        }
        busAuthModel.setPushDeviceId(diu);
        a((BaseModel) busAuthModel, 1);
    }

    public final void a(int i) {
        String c2 = c();
        String b2 = b();
        if (TextUtils.isEmpty(c2) || TextUtils.isEmpty(b2)) {
            a(i, "bus_cardlist_7000", "");
            return;
        }
        BusCardListModel busCardListModel = new BusCardListModel();
        busCardListModel.setAlipayUserId(b2);
        busCardListModel.setAuthToken(c2);
        busCardListModel.setOpenAuthLogin(true);
        a((BaseModel) busCardListModel);
        a((BaseModel) busCardListModel, i);
    }

    public static void a(BaseModel baseModel) {
        baseModel.setAppKey("");
        baseModel.setThirdPartyApp(true);
    }

    public static void a(BaseModel baseModel, int i) {
        new d(baseModel, i).start();
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i, OperationResult operationResult) {
        if (operationResult == null) {
            ask.a("song---", "sendLitenerMsg result is empty --- ");
            a(i, "", "");
            return;
        }
        a(i, operationResult.getCodeValue(), operationResult.getResult());
    }

    public final void a(int i, String str, String str2) {
        if (this.c == null) {
            ask.a("song---", "sendListenerMsg2 BusCodeInsideSdkUtil is empty --- ");
        } else {
            this.c.a(i, str, str2);
        }
    }

    public static String b() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        String str = "";
        if (!(iAccountService == null || !iAccountService.a() || iAccountService.e() == null)) {
            str = iAccountService.e().u;
        }
        ask.a("song---", "alipayUserId = ".concat(String.valueOf(str)));
        return str;
    }

    public static String c() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        String str = "";
        if (iAccountService != null) {
            str = iAccountService.a((String) "auth_transport");
        }
        ask.a("song---", "token = ".concat(String.valueOf(str)));
        return str;
    }
}
