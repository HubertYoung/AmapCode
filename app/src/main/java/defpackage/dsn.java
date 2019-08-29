package defpackage;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.core.network.inter.response.StringResponse;
import com.autonavi.minimap.onekeycheck.action.ActionListener;
import com.autonavi.minimap.onekeycheck.action.BaseAction;
import com.autonavi.minimap.onekeycheck.module.CloudConfigInfos;
import com.autonavi.minimap.onekeycheck.module.CloudInterfInfos;

/* renamed from: dsn reason: default package */
/* compiled from: PullCloudIntefsAction */
public final class dsn extends BaseAction {
    /* access modifiers changed from: private */
    public Thread d;
    /* access modifiers changed from: private */
    public bpf e;
    /* access modifiers changed from: private */
    public Handler f = new Handler() {
        public final void handleMessage(Message message) {
            dsn.this.d = null;
            CloudConfigInfos cloudConfigInfos = null;
            if (message != null) {
                String str = message.obj == null ? null : (String) message.obj;
                if (!TextUtils.isEmpty(str)) {
                    cloudConfigInfos = (CloudConfigInfos) dsn.this.parse(str, CloudConfigInfos.class);
                }
            }
            eao.b("key_detection", "--cloudConfigInfos:".concat(String.valueOf(cloudConfigInfos)));
            dsn.a(dsn.this, cloudConfigInfos);
        }
    };

    /* renamed from: dsn$a */
    /* compiled from: PullCloudIntefsAction */
    class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(dsn dsn, byte b) {
            this();
        }

        public final void run() {
            dsn.this.a.update(3);
            try {
                String b = lo.a().b("one_key_feedback");
                eao.b("key_detection", "---cloudDataStr:".concat(String.valueOf(b)));
                Message obtainMessage = dsn.this.f.obtainMessage();
                obtainMessage.obj = b;
                dsn.this.f.sendMessage(obtainMessage);
            } catch (Exception unused) {
                dsn.this.f.sendMessage(null);
            }
        }
    }

    public dsn(ActionListener actionListener) {
        super(actionListener);
    }

    public final void start() {
        if (getState().getState() <= 0) {
            super.start();
            this.d = new Thread(new a(this, 0));
            this.d.start();
        }
    }

    public final void stop() {
        if (this.d != null && !this.d.isInterrupted()) {
            this.d.interrupt();
        }
        if (this.e != null) {
            yq.a();
            yq.a((bph) this.e);
        }
        super.stop();
    }

    /* access modifiers changed from: private */
    public void a() {
        this.e = null;
        finish();
        callbackOnResponse(new CloudInterfInfos());
    }

    static /* synthetic */ void a(dsn dsn, CloudConfigInfos cloudConfigInfos) {
        if (cloudConfigInfos != null) {
            dsn.e = new bpf();
            String str = cloudConfigInfos.urls == null ? null : cloudConfigInfos.urls.url;
            eao.b("key_detection", "--cloudjsonurl:".concat(String.valueOf(str)));
            if (str != null) {
                dsn.e.setUrl(str);
                yq.a();
                yq.a((bph) dsn.e, (bpl<T>) new bpl<StringResponse>() {
                    public final /* synthetic */ void onSuccess(bpk bpk) {
                        final StringResponse stringResponse = (StringResponse) bpk;
                        aho.a(new Runnable() {
                            public final void run() {
                                dsn.this.e = null;
                                String responseBodyString = stringResponse.getResponseBodyString();
                                CloudInterfInfos cloudInterfInfos = (responseBodyString == null || TextUtils.isEmpty(responseBodyString)) ? null : (CloudInterfInfos) dsn.this.parse(responseBodyString, CloudInterfInfos.class);
                                dsn.this.finish();
                                dsn.this.callbackOnResponse(cloudInterfInfos);
                            }
                        });
                    }

                    public final void onFailure(bph bph, ResponseException responseException) {
                        aho.a(new Runnable() {
                            public final void run() {
                                dsn.this.a();
                            }
                        });
                    }
                });
            }
            return;
        }
        dsn.a();
    }
}
