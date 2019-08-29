package defpackage;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.HttpUrlCollector;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.bl.NetworkInitializer;
import com.autonavi.minimap.bl.net.INetworkProvider;
import com.autonavi.server.aos.serverkey;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig.Builder;
import com.taobao.accs.AccsException;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.TaobaoRegister;
import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

/* renamed from: clf reason: default package */
/* compiled from: Network */
public final class clf {

    /* renamed from: clf$a */
    /* compiled from: Network */
    public static class a extends cky {
        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return "ACCS";
        }

        public final void a(final Application application) {
            int i;
            long currentTimeMillis = System.currentTimeMillis();
            j.a(yr.a());
            String accsAppkey = ConfigerHelper.getInstance().getAccsAppkey();
            String accsMode = ConfigerHelper.getInstance().getAccsMode();
            String str = "gaode-acs.m.taobao.com";
            String str2 = "gaode-jmacs.m.taobao.com";
            int i2 = 11;
            if (accsMode.equalsIgnoreCase("test")) {
                i = 2;
                str = "msgacs.waptest.taobao.com";
                str2 = "acs.waptest.taobao.com";
                i2 = 0;
            } else if (accsMode.equalsIgnoreCase("preview")) {
                i = 1;
                str = "acs.wapa.taobao.com";
                str2 = "acs.wapa.taobao.com";
            } else {
                boolean equalsIgnoreCase = accsMode.equalsIgnoreCase("release");
                i = 0;
            }
            ALog.setUseTlog(false);
            ACCSManager.setAppkey(application, accsAppkey, i);
            ACCSClient.setEnvironment(application, i);
            try {
                ACCSClient.init((Context) application, new Builder().setAppKey(accsAppkey).setInappHost(str).setChannelHost(str2).setChannelPubKey(i2).setInappPubKey(i2).setTag("default").build());
            } catch (AccsException e) {
                e.printStackTrace();
            }
            ahm.a(new Runnable() {
                public final void run() {
                    fhb fhb = (fhb) defpackage.esb.a.a.a(fhb.class);
                    if (fhb != null) {
                        try {
                            ACCSClient.getAccsClient("default").bindApp(NetworkParam.getTaobaoID(), fhb.c());
                        } catch (AccsException e) {
                            e.printStackTrace();
                        }
                    }
                    if (bim.aa().k((String) UploadConstants.STATUS_PUSH_RECEIVED)) {
                        TaobaoRegister.bindAgoo(application, new ICallback() {
                            public final void onSuccess() {
                                AMapLog.i("TaobaoRegister.bindAgoo-onSuccess", "");
                            }

                            public final void onFailure(String str, String str2) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(str);
                                sb.append(",");
                                sb.append(str2);
                                AMapLog.i("TaobaoRegister.bindAgoo-onFailure:", sb.toString());
                            }
                        });
                    }
                    MiPushRegistar.a(application, "2882303761517465546", "5831746549546");
                    HuaWeiRegister.a(application);
                    OppoRegister.a(application, "jmUAJQDkKWjn1guyqYnVA6XX", "2ioX0e7lsNUBDSRa28xl9LMC");
                    eww.a((Context) application);
                    if (eww.b()) {
                        VivoRegister.a(application);
                    }
                }
            });
            new StringBuilder("doInitialization: ").append(System.currentTimeMillis() - currentTimeMillis);
        }
    }

    /* renamed from: clf$b */
    /* compiled from: Network */
    public static class b extends cky {
        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return "BLNetwork";
        }

        public final void a(Application application) {
            NetworkInitializer.init(application, new com.autonavi.minimap.bl.NetworkInitializer.b().a((iq) new aaa()).a((is) new aac()).a((INetworkProvider) new clg()).a((ctg) new ctg() {
                public final String a() {
                    return aat.a();
                }
            }).a());
        }
    }

    /* renamed from: clf$c */
    /* compiled from: Network */
    public static class c extends cky {
        anp a = new anp() {
            public final void onUserInfoUpdate(ant ant) {
            }

            public final void onLoginStateChanged(boolean z, boolean z2) {
                if (!z2) {
                    yh.a((yi) null);
                } else {
                    yh.a(c.this.b());
                }
            }
        };
        private boolean b = true;

        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return "MQTT";
        }

        public final void a(Application application) {
            yh.a(b());
            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.a(this.a);
            }
        }

        /* access modifiers changed from: 0000 */
        public final yi b() {
            String str;
            String str2;
            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
            defpackage.abi.a aVar = null;
            if (iAccountService == null || TextUtils.isEmpty(iAccountService.b())) {
                return null;
            }
            abj a2 = abj.a((Context) AMapAppGlobal.getApplication());
            if (a2 != null) {
                aVar = a2.b("sessionid");
            }
            defpackage.yi.a aVar2 = new defpackage.yi.a();
            aVar2.a = iAccountService.b();
            if (this.b) {
                str = NetworkParam.getAosTsMqttUrlSSL();
            } else {
                str = NetworkParam.getAosTsMqttUrl();
            }
            aVar2.b = str;
            aVar2.c = serverkey.getAosKey();
            aVar2.d = NetworkParam.getTaobaoID();
            aVar2.e = serverkey.getAosChannel();
            aVar2.f = NetworkParam.getDip();
            aVar2.g = NetworkParam.getDic();
            aVar2.h = NetworkParam.getDiu();
            aVar2.i = NetworkParam.getAdiu();
            aVar2.j = NetworkParam.getDiv();
            if (aVar != null) {
                str2 = aVar.b;
            } else {
                str2 = "";
            }
            aVar2.k = str2;
            return aVar2.a();
        }
    }

    /* renamed from: clf$d */
    /* compiled from: Network */
    public static class d extends cky {
        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return "NetworkContext";
        }

        /* access modifiers changed from: 0000 */
        public final void a(Application application) {
            yq.a((aae) new cme(application.getApplicationContext()));
        }
    }

    /* renamed from: clf$e */
    /* compiled from: Network */
    public static class e extends cky {
        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return LogStrategyManager.SP_STRATEGY_KEY_NETWORK;
        }

        /* access modifiers changed from: 0000 */
        public final void a(Application application) {
            yq.b();
            HttpUrlCollector.a();
        }
    }
}
