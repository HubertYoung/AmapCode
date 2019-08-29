package com.alipay.mobile.securitycommon.taobaobind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.account.adapter.AppInfoAdapter;
import com.alipay.mobile.account.adapter.CommonAdapter;
import com.alipay.mobile.account.adapter.DeviceInfoAdapter;
import com.alipay.mobile.account.adapter.LogAdapter;
import com.alipay.mobile.account.adapter.RpcAdapter;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.alipay.mobile.securitycommon.taobaobind.TaobaoBindConstans.Key;
import com.alipay.mobile.securitycommon.taobaobind.util.AUH5Plugin;
import com.alipay.mobile.securitycommon.taobaobind.util.H5Wrapper;
import com.alipay.mobile.securitycommon.taobaobind.util.TaobaoBindUtil;
import com.alipay.mobile.securitycommon.taobaobind.util.TimeConsumingLogAgent;
import com.alipay.mobileapp.biz.rpc.taobao.bind.vo.BindTaobaoReq;
import com.alipay.mobileapp.biz.rpc.taobao.bind.vo.BindTaobaoRes;
import com.alipay.mobileapp.biz.rpc.taobao.bind.vo.TaobaoBindFacade;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaobaoBindService {
    public static final String ACTION_ALIPAY_BIND_TAOBAO = "com.ali.user.mobile.account.bind.alipay_bind_taobao";
    public static final int CODE_ACTIVE_ERROR = -3;
    public static final int CODE_ACTIVE_SUCCESS = 3;
    public static final int CODE_BIND_ERROR = -2;
    public static final int CODE_BIND_SUCCESS = 2;
    public static final int CODE_RPC_EXCEPTION = -1000;
    public static final int CODE_USER_CANCEL = -1;
    public static final int CODE_USER_GRANT = 1;
    public static final int MSG_SHOW_WAITING_ACTIVITY = 1;
    public static final String RET_ACTIVE_TAOBAO_FAIL = "2004";
    public static final String RET_BIND_PHONE_FAIL = "2002";
    public static final String RET_RPC_EXCEPTION = "2003";
    public static final String RET_SUCCESS = "1000";
    public static final String RET_USER_CANCEL = "2001";
    private static TaobaoBindService a;
    private TaobaoBindFacade b;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private final Context g;
    private AtomicBoolean h = new AtomicBoolean(false);
    private AtomicBoolean i = new AtomicBoolean(false);
    private final Object j = new Object();
    private final Object k = new Object();
    private final Handler l = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            LogAdapter.a((String) "BindManager", String.format("receive msg: %s, obj: %s", new Object[]{Integer.valueOf(message.what), message.obj}));
            if (message.what == 1) {
                final BindWarp bindWarp = (BindWarp) message.obj;
                new Thread(new Runnable() {
                    public void run() {
                        LogAdapter.a((String) "BindManager", (String) "startWaitingActivityPending thread execute");
                        TaobaoBindService.this.a((String) null, bindWarp.a, bindWarp.b);
                    }
                }, "aliAutoLogin.startWaitingActivityPending").start();
                LogAdapter.a((String) "BindManager", (String) "startWaitingActivityPending thread start");
            }
        }
    };

    class BindWarp {
        Bundle a;
        OnBindCaller b;

        public BindWarp(Bundle bundle, OnBindCaller onBindCaller) {
            this.a = bundle;
            this.b = onBindCaller;
        }
    }

    private TaobaoBindService(Context context) {
        this.g = context.getApplicationContext();
        RpcAdapter.a();
        this.b = (TaobaoBindFacade) RpcAdapter.a(TaobaoBindFacade.class);
    }

    public static TaobaoBindService getInstance(Context context) {
        if (a == null) {
            synchronized (TaobaoBindService.class) {
                try {
                    if (a == null) {
                        a = new TaobaoBindService(context);
                    }
                }
            }
        }
        return a;
    }

    private BindTaobaoRes a(String str) {
        String str2;
        TimeConsumingLogAgent timeConsumingLogAgent;
        TimeConsumingLogAgent timeConsumingLogAgent2 = new TimeConsumingLogAgent("YWUC-JTTYZH-C29", "alipayAccountBinding");
        timeConsumingLogAgent2.logStart().logFacade("alipay.client.bindTaobaoAccount");
        try {
            BindTaobaoReq bindTaobaoReq = new BindTaobaoReq();
            bindTaobaoReq.userid = str;
            DeviceInfoAdapter.a();
            bindTaobaoReq.imei = DeviceInfoAdapter.e();
            DeviceInfoAdapter.a();
            bindTaobaoReq.imsi = DeviceInfoAdapter.f();
            AppInfoAdapter.a();
            bindTaobaoReq.productId = AppInfoAdapter.c();
            AppInfoAdapter.a();
            bindTaobaoReq.productVersion = AppInfoAdapter.b();
            bindTaobaoReq.umidToken = TaobaoBindUtil.getUmidToken(this.g);
            BindTaobaoRes alipayAccountBinding = this.b.alipayAccountBinding(bindTaobaoReq);
            if (alipayAccountBinding == null) {
                timeConsumingLogAgent = timeConsumingLogAgent2.logEnd();
                str2 = "NN";
            } else {
                timeConsumingLogAgent = timeConsumingLogAgent2.logEnd();
                str2 = "1000".equals(alipayAccountBinding.resultCode) ? "Y" : "N";
            }
            timeConsumingLogAgent.addParam1(str2).commit();
            return alipayAccountBinding;
        } catch (RpcException e2) {
            TimeConsumingLogAgent.logRpcException(timeConsumingLogAgent2, e2);
            timeConsumingLogAgent2.logEnd().commit();
            throw e2;
        }
    }

    public void alipayBindTaobao(Bundle bundle, OnBindCaller onBindCaller) {
        LogAdapter.a((String) "BindManager", "alipayAccountBind - param:".concat(String.valueOf(bundle)));
        if (bundle != null && !TextUtils.isEmpty(bundle.getString("userId"))) {
            try {
                this.h.set(false);
                this.i.set(false);
                if (onBindCaller != null) {
                    onBindCaller.onPreRpc();
                }
                Message obtainMessage = this.l.obtainMessage();
                obtainMessage.what = 1;
                obtainMessage.obj = new BindWarp(bundle, onBindCaller);
                this.l.sendMessageDelayed(obtainMessage, 1000);
                BindTaobaoRes a2 = a(bundle.getString("userId"));
                a();
                LogAdapter.a((String) "BindManager", String.format("bind taobao result: %s, %s", new Object[]{a2.resultCode, a2.memo}));
                if (onBindCaller != null) {
                    onBindCaller.onPostRpc();
                }
                if (this.h.get()) {
                    LogAdapter.a((String) "BindManager", (String) "user already canceled, do not process bind result");
                    this.h.set(false);
                    return;
                }
                b(a2.resultCode);
                if ("1000".equals(a2.resultCode)) {
                    if (onBindCaller != null) {
                        bundle.putString("taobaoId", a2.taobaoId);
                        onBindCaller.onBindSuccess(bundle);
                    }
                } else if ("1002".equals(a2.resultCode)) {
                    startBindPhone(bundle, onBindCaller, a2);
                } else if (Result.TAOBAO_ACTIVE.equals(a2.resultCode)) {
                    taobaoActive(bundle, onBindCaller, a2);
                } else if ("1001".equals(a2.resultCode)) {
                    startBindPhone(bundle, onBindCaller, a2);
                } else if (!this.i.get()) {
                    a(a2.resultCode, bundle, onBindCaller);
                }
            } catch (RpcException e2) {
                LogAdapter.a((String) "BindManager", (Throwable) e2);
                a();
                if (this.h.get()) {
                    LogAdapter.a((String) "BindManager", (String) "user already canceled, do not toast rpc exception");
                    this.h.set(false);
                } else if (this.i.get()) {
                    LogAdapter.a((String) "BindManager", (String) "waiting activity started, notify to toast");
                    b("2003");
                } else {
                    LogAdapter.a((String) "BindManager", (String) "waiting activity not started, thorw e to framework");
                    a(bundle, onBindCaller, (String) "2003");
                    throw e2;
                }
            }
        }
    }

    private void a() {
        LogAdapter.a((String) "BindManager", (String) "removeWaitingMessage");
        this.l.removeMessages(1);
    }

    /* access modifiers changed from: private */
    public void a(String str, Bundle bundle, OnBindCaller onBindCaller) {
        LogAdapter.a((String) "BindManager", (String) "startWaitingActivityPending");
        LogAdapter.a((String) "BindManager", (String) "startWaitingActivity");
        this.i.set(true);
        Intent intent = new Intent(this.g, AliuserWaitingActivity.class);
        intent.putExtra("flag", str);
        if (!(this.g instanceof Activity)) {
            intent.addFlags(268435456);
        }
        this.g.startActivity(intent);
        synchronized (this.k) {
            try {
                this.k.wait();
            } catch (Exception e2) {
                LogAdapter.a((String) "BindManager", (Throwable) e2);
            }
        }
        if (this.f == -1) {
            a(bundle, onBindCaller, (String) "2001");
        } else if (this.f == -1000) {
            a(bundle, onBindCaller, (String) "2003");
        } else {
            if (this.f != 1) {
                a(bundle, onBindCaller, String.valueOf(this.f));
            }
        }
    }

    public void notifyUserWaiting(int i2) {
        this.f = i2;
        if (i2 == -1) {
            this.h.set(true);
            LogAdapter.a((String) "BindManager", (String) "user cancel when waiting binding taobao rpc");
        }
        LogAdapter.a((String) "BindManager", String.format("waiting result:%s", new Object[]{Integer.valueOf(this.f)}));
        synchronized (this.k) {
            try {
                this.k.notifyAll();
            } catch (Exception e2) {
                LogAdapter.a((String) "BindManager", (Throwable) e2);
            }
        }
    }

    private void b(String str) {
        Intent intent = new Intent(ACTION_ALIPAY_BIND_TAOBAO);
        intent.putExtra("flag", str);
        LocalBroadcastManager.getInstance(this.g).sendBroadcast(intent);
    }

    public void alipayAccountBind(Bundle bundle, OnBindCaller onBindCaller) {
        LogAdapter.a((String) "BindManager", "alipayAccountBind - param:".concat(String.valueOf(bundle)));
        if (bundle != null && !TextUtils.isEmpty(bundle.getString("userId"))) {
            if (onBindCaller != null) {
                try {
                    onBindCaller.onPreRpc();
                } catch (RpcException e2) {
                    LogAdapter.a((String) "BindManager", (Throwable) e2);
                    a(bundle, onBindCaller, (String) "2003");
                    throw e2;
                }
            }
            BindTaobaoRes a2 = a(bundle.getString("userId"));
            LogAdapter.a((String) "BindManager", String.format("bind taobao result: %s, %s", new Object[]{a2.resultCode, a2.memo}));
            if (onBindCaller != null) {
                onBindCaller.onPostRpc();
            }
            if ("1000".equals(a2.resultCode)) {
                if (onBindCaller != null) {
                    bundle.putString("taobaoId", a2.taobaoId);
                    onBindCaller.onBindSuccess(bundle);
                }
            } else if ("1002".equals(a2.resultCode)) {
                startBindPhone(bundle, onBindCaller, a2);
            } else if (Result.TAOBAO_ACTIVE.equals(a2.resultCode)) {
                taobaoActive(bundle, onBindCaller, a2);
            } else if ("1001".equals(a2.resultCode)) {
                startBindPhone(bundle, onBindCaller, a2);
            } else {
                a(bundle, onBindCaller, a2.resultCode);
            }
        }
    }

    private static void a(Context context, BindTaobaoRes bindTaobaoRes) {
        Intent intent = new Intent(context, AliuserBindActivity.class);
        intent.putExtra(Key.BIND_TOKEN, bindTaobaoRes);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public Bundle bindPhoneForResult(Bundle bundle, BindTaobaoRes bindTaobaoRes) {
        StringBuilder sb = new StringBuilder("bindPhoneForResult，thread:");
        sb.append(Thread.currentThread().getId());
        LogAdapter.a((String) "BindManager", sb.toString());
        a(this.g, bindTaobaoRes);
        c();
        LogAdapter.a((String) "BindManager", String.format("bindPhoneForResult, grant result:%s", new Object[]{Integer.valueOf(this.c)}));
        if (this.c == 1) {
            return toBindPhoneForResult(bundle, bindTaobaoRes);
        }
        if (this.c != -1) {
            return bundle;
        }
        bundle.putString("resultCode", "2001");
        return bundle;
    }

    public Bundle toBindPhoneForResult(Bundle bundle, BindTaobaoRes bindTaobaoRes) {
        LogAdapter.a((String) "BindManager", (String) "toBindPhone (with plugin)");
        H5Wrapper.startPage(TaobaoBindUtil.addCallbackToUrl(bindTaobaoRes.h5Url, "%3Faction%3DcontinueLogin"), new AUH5Plugin() {
            public boolean onOverrideUrlLoading(String str) {
                if ("continueLogin".equalsIgnoreCase(getIntentExtra().getStringExtra("action"))) {
                    getPage().exitPage();
                    onSuccess();
                    return true;
                } else if (!"false".equals(getIntentExtra().getStringExtra("isSuc"))) {
                    return super.onOverrideUrlLoading(str);
                } else {
                    getPage().exitPage();
                    onFail();
                    return true;
                }
            }

            public void onSuccess() {
                LogAdapter.a((String) "BindManager", (String) "bind phone For Result success");
                TaobaoBindService.this.notifyBindPhone(2);
            }

            public void onCancel() {
                LogAdapter.a((String) "BindManager", (String) "bind phone For Result cancel");
                TaobaoBindService.this.notifyBindPhone(-1);
            }

            public void onFail() {
                LogAdapter.a((String) "BindManager", (String) "bind phone For Result fail");
                TaobaoBindService.this.notifyBindPhone(-2);
            }
        });
        this.d = 0;
        b();
        LogAdapter.a((String) "BindManager", String.format("toBindPhoneForResult, bind result:%s", new Object[]{Integer.valueOf(this.d)}));
        if (this.d == 2) {
            bundle.putString("resultCode", "1000");
        } else if (this.d == -1) {
            bundle.putString("resultCode", "2001");
        } else {
            bundle.putString("resultCode", "2002");
        }
        return bundle;
    }

    public void startBindPhone(Bundle bundle, OnBindCaller onBindCaller, BindTaobaoRes bindTaobaoRes) {
        StringBuilder sb = new StringBuilder("open bind pohone h5，thread:");
        sb.append(Thread.currentThread().getId());
        LogAdapter.a((String) "BindManager", sb.toString());
        a(this.g, bindTaobaoRes);
        c();
        if (this.c == 1) {
            LogAdapter.a((String) "BindManager", (String) "start bind phone");
            toBindPhone(bundle, onBindCaller, bindTaobaoRes);
        } else if (this.c == -1) {
            LogAdapter.a((String) "BindManager", (String) "cancel bind phone");
            a(bundle, onBindCaller, (String) "2001");
        } else {
            a(bundle, onBindCaller, bindTaobaoRes.resultCode);
        }
    }

    public void toBindPhone(final Bundle bundle, final OnBindCaller onBindCaller, BindTaobaoRes bindTaobaoRes) {
        LogAdapter.a((String) "BindManager", (String) "toBindPhone");
        H5Wrapper.startPage(TaobaoBindUtil.addCallbackToUrl(bindTaobaoRes.h5Url, "%3Faction%3DcontinueLogin"), new AUH5Plugin() {
            public boolean onOverrideUrlLoading(String str) {
                if ("continueLogin".equalsIgnoreCase(getIntentExtra().getStringExtra("action"))) {
                    getPage().exitPage();
                    onSuccess();
                    return true;
                } else if (!"false".equals(getIntentExtra().getStringExtra("isSuc"))) {
                    return super.onOverrideUrlLoading(str);
                } else {
                    getPage().exitPage();
                    onFail();
                    return true;
                }
            }

            public void onSuccess() {
                LogAdapter.a((String) "BindManager", (String) "bind phone success");
                new Thread(new Runnable() {
                    public void run() {
                        TaobaoBindService.this.alipayAccountBind(bundle, onBindCaller);
                    }
                }, "aliAutoLogin.alipayAccountBind").start();
            }

            public void onCancel() {
                LogAdapter.a((String) "BindManager", (String) "bind phone cancel");
                TaobaoBindService.a(bundle, onBindCaller, (String) "2001");
            }

            public void onFail() {
                LogAdapter.a((String) "BindManager", (String) "bind phone fail");
                TaobaoBindService.a(bundle, onBindCaller, (String) "2002");
            }
        });
    }

    public Bundle taobaoActiveForResult(Bundle bundle, BindTaobaoRes bindTaobaoRes) {
        LogAdapter.a((String) "BindManager", (String) "taobaoActive For Result");
        AnonymousClass4 r0 = new AUH5Plugin() {
            public boolean onOverrideUrlLoading(String str) {
                getPage().exitPage();
                onSuccess();
                return true;
            }

            public void onSuccess() {
                LogAdapter.a((String) "BindManager", (String) "active taobao For Result success");
                TaobaoBindService.this.notifyTaobaoActive(3);
            }

            public void onCancel() {
                LogAdapter.a((String) "BindManager", (String) "active taoba For Resulto cancel");
                TaobaoBindService.this.notifyTaobaoActive(-1);
            }

            public void onFail() {
                LogAdapter.a((String) "BindManager", (String) "active taobao For Result fail");
                TaobaoBindService.this.notifyTaobaoActive(-3);
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append(TaobaoBindUtil.addCallbackToUrl(bindTaobaoRes.h5Url));
        sb.append("&__webview_options__=ssoLoginEnabled%3DNO%26preSSOLogin%3dNO");
        H5Wrapper.startPage(sb.toString(), r0);
        this.e = 0;
        b();
        LogAdapter.a((String) "BindManager", String.format("taobaoActiveForResult, active result:%s", new Object[]{Integer.valueOf(this.e)}));
        if (this.e == 3) {
            bundle.putString("resultCode", "1000");
        } else if (this.e == -1) {
            bundle.putString("resultCode", "2001");
        } else {
            bundle.putString("resultCode", "2004");
        }
        return bundle;
    }

    public void taobaoActive(final Bundle bundle, final OnBindCaller onBindCaller, BindTaobaoRes bindTaobaoRes) {
        LogAdapter.a((String) "BindManager", (String) "need to active taobao");
        if (bindTaobaoRes == null || TextUtils.isEmpty(bindTaobaoRes.h5Url)) {
            CommonAdapter.a();
            Toast.makeText(CommonAdapter.b(), "system error", 0).show();
            return;
        }
        AnonymousClass5 r0 = new AUH5Plugin() {
            public boolean onOverrideUrlLoading(String str) {
                getPage().exitPage();
                onSuccess();
                return true;
            }

            public void onSuccess() {
                LogAdapter.a((String) "BindManager", (String) "active taobao success");
                new Thread(new Runnable() {
                    public void run() {
                        TaobaoBindService.this.alipayAccountBind(bundle, onBindCaller);
                    }
                }, "aliAutoLogin.alipayAccountBind").start();
            }

            public void onCancel() {
                LogAdapter.a((String) "BindManager", (String) "active taobao cancel");
                TaobaoBindService.a(bundle, onBindCaller, (String) "2001");
            }

            public void onFail() {
                LogAdapter.a((String) "BindManager", (String) "active taobao fail");
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append(TaobaoBindUtil.addCallbackToUrl(bindTaobaoRes.h5Url));
        sb.append("&__webview_options__=ssoLoginEnabled%3DNO%26preSSOLogin%3dNO");
        H5Wrapper.startPage(sb.toString(), r0);
    }

    private void b() {
        synchronized (this.j) {
            try {
                this.j.wait();
            } catch (Exception e2) {
                LogAdapter.a((String) "BindManager", (Throwable) e2);
            }
        }
    }

    private void c() {
        this.c = 0;
        b();
    }

    public void notifyUserGrant(int i2) {
        this.c = i2;
        LogAdapter.a((String) "BindManager", String.format("user grant result:%s", new Object[]{Integer.valueOf(this.c)}));
        synchronized (this.j) {
            try {
                this.j.notifyAll();
            } catch (Exception e2) {
                LogAdapter.a((String) "BindManager", (Throwable) e2);
            }
        }
    }

    public void notifyTaobaoActive(int i2) {
        this.e = i2;
        LogAdapter.a((String) "BindManager", String.format("taobao active result:%s", new Object[]{Integer.valueOf(this.e)}));
        synchronized (this.j) {
            try {
                this.j.notifyAll();
            } catch (Exception e2) {
                LogAdapter.a((String) "BindManager", (Throwable) e2);
            }
        }
    }

    public void notifyBindPhone(int i2) {
        this.d = i2;
        LogAdapter.a((String) "BindManager", String.format("user bind phone result:%s", new Object[]{Integer.valueOf(this.d)}));
        synchronized (this.j) {
            try {
                this.j.notifyAll();
            } catch (Exception e2) {
                LogAdapter.a((String) "BindManager", (Throwable) e2);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void a(Bundle bundle, OnBindCaller onBindCaller, String str) {
        if (onBindCaller != null) {
            bundle.putString("resultCode", str);
            onBindCaller.onBindError(bundle);
        }
    }
}
