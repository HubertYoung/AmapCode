package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.onekeycheck.action.ActionListener;
import com.autonavi.minimap.onekeycheck.action.BaseAction;
import com.autonavi.minimap.onekeycheck.action.State;
import com.autonavi.minimap.onekeycheck.action.UploadDatasAction$1;
import com.autonavi.minimap.onekeycheck.exception.OneKeyCheckException;
import com.autonavi.minimap.onekeycheck.module.CloudInterfInfos;
import com.autonavi.minimap.onekeycheck.module.PackData;
import com.autonavi.minimap.onekeycheck.module.ResultData;
import com.autonavi.minimap.onekeycheck.module.TraceRouteInfo;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import com.autonavi.minimap.onekeycheck.request.UpLoadParaEntity;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

/* renamed from: dsl reason: default package */
/* compiled from: OneKeyCheckManager */
public final class dsl implements ActionListener {
    public dsn a;
    public a b;
    private dso c;
    private dsm d;
    private dsq e;
    private dsp f;
    private StringBuffer g;
    private amt h = new amt();
    private defpackage.amt.a i = new defpackage.amt.a() {
        public final void a(Context context) {
            if (!aaw.c(AMapPageUtil.getAppContext())) {
                dsl.this.a((String) "网络连接异常", false);
            }
        }
    };

    /* renamed from: dsl$a */
    /* compiled from: OneKeyCheckManager */
    public interface a {
        void onFinish(String str);
    }

    public dsl() {
        this.h.a(AMapPageUtil.getAppContext(), true);
        this.h.a = this.i;
    }

    private void b() {
        if (this.a != null) {
            this.a.stop();
            this.a = null;
        }
        if (this.c != null) {
            this.c.stop();
            this.c = null;
        }
        if (this.d != null) {
            this.d.stop();
            this.d = null;
        }
        if (this.f != null) {
            this.f.stop();
            this.f = null;
        }
        if (this.e != null) {
            this.e.stop();
            this.e = null;
        }
    }

    private void a(ResultData resultData) {
        if (this.d == null) {
            this.d = new dsm(this);
        }
        if (!this.d.isStart()) {
            this.d.start();
        }
        this.d.a(resultData);
    }

    public final void onResponse(BaseAction baseAction, ResultData resultData) {
        String str;
        if (baseAction != null) {
            State state = baseAction.getState();
            if (state != null) {
                switch (state.getState()) {
                    case 3:
                        if (baseAction instanceof dso) {
                            a(resultData);
                        }
                        if (baseAction instanceof dsm) {
                            if (this.e == null) {
                                this.e = new dsq(this);
                            }
                            if (!this.e.isStart()) {
                                this.e.start();
                            }
                            dsq dsq = this.e;
                            if (resultData == null || !(resultData instanceof PackData)) {
                                dsq.startWaitTimer(new TimerTask() {
                                    public final void run() {
                                        dsq.a(dsq.this, false, true);
                                    }
                                });
                                break;
                            } else {
                                PackData packData = (PackData) resultData;
                                AosPostRequest b2 = aax.b(new UpLoadParaEntity(dsq.a(packData), dsq.d));
                                b2.addHeader("content-type", "application/x-www-form-urlencoded");
                                dsq.e.add(b2);
                                yq.a((AosRequest) b2, (AosResponseCallback<T>) new UploadDatasAction$1<T>(dsq, packData));
                                return;
                            }
                        }
                        break;
                    case 4:
                        if (baseAction instanceof dsp) {
                            a(resultData);
                            return;
                        } else if (baseAction instanceof dsn) {
                            if (resultData == null || !(resultData instanceof CloudInterfInfos)) {
                                str = null;
                            } else {
                                str = ((CloudInterfInfos) resultData).tracert_url;
                            }
                            this.f = new dsp(this, str);
                            this.f.start();
                            AMapPageUtil.getAppContext();
                            this.c = new dso(this);
                            dso dso = this.c;
                            if (resultData == null || !(resultData instanceof CloudInterfInfos)) {
                                dso.d = null;
                            } else {
                                dso.d = (CloudInterfInfos) resultData;
                            }
                            this.c.start();
                            return;
                        } else if (baseAction instanceof dsm) {
                            if (resultData != null && (resultData instanceof TraceRouteInfo)) {
                                Set<Entry<String, String>> entrySet = ((TraceRouteInfo) resultData).getTraceInfoNodeMap().entrySet();
                                this.g = new StringBuffer();
                                for (Entry next : entrySet) {
                                    StringBuffer stringBuffer = this.g;
                                    stringBuffer.append((String) next.getKey());
                                    stringBuffer.append(":");
                                    stringBuffer.append((String) next.getValue());
                                    stringBuffer.append("\n");
                                }
                                return;
                            }
                        } else if (baseAction instanceof dsq) {
                            if (resultData != null && (resultData instanceof UploadDataResult)) {
                                StringBuffer stringBuffer2 = this.g;
                                stringBuffer2.append("data_upload_state:");
                                stringBuffer2.append(((UploadDataResult) resultData).getPackageState());
                            }
                            a(this.g.toString(), true);
                            return;
                        }
                        break;
                }
            }
        }
    }

    public final void onException(BaseAction baseAction, OneKeyCheckException oneKeyCheckException) {
        StringBuffer stringBuffer = new StringBuffer();
        if (oneKeyCheckException == null) {
            stringBuffer.append("errorCode:0,\n检测失败");
        } else {
            stringBuffer.append("errorCode:");
            stringBuffer.append(oneKeyCheckException.getErrorCode());
            stringBuffer.append(",\n");
            stringBuffer.append(oneKeyCheckException.getMessage());
        }
        a(stringBuffer.toString(), false);
    }

    /* access modifiers changed from: private */
    public void a(String str, boolean z) {
        JSONObject jSONObject = new JSONObject();
        if (z) {
            jSONObject.put((String) "state", (Object) "1");
        } else {
            jSONObject.put((String) "state", (Object) "0");
        }
        if (this.g != null && !TextUtils.isEmpty(str)) {
            jSONObject.put((String) "result", (Object) str);
        }
        this.b.onFinish(jSONObject.toJSONString());
        a();
    }

    public final void a() {
        this.h.a(AMapPageUtil.getAppContext(), false);
        b();
    }
}
