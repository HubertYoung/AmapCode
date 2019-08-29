package defpackage;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.minimap.onekeycheck.action.ActionListener;
import com.autonavi.minimap.onekeycheck.action.BaseAction;
import com.autonavi.minimap.onekeycheck.module.CloudInterfResData;
import com.autonavi.minimap.onekeycheck.module.PackData;
import com.autonavi.minimap.onekeycheck.module.ResultData;
import com.autonavi.minimap.onekeycheck.module.TraceRouteInfo;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/* renamed from: dsm reason: default package */
/* compiled from: PackDatasAction */
public final class dsm extends BaseAction {
    public final int d = 100;
    private PackData e;
    private boolean f;
    private boolean g;
    private float h;
    private TraceRouteInfo i;

    public dsm(ActionListener actionListener) {
        super(actionListener);
    }

    public final void a(ResultData resultData) {
        Object obj;
        float f2;
        if (3 == this.a.getState()) {
            if (resultData == null) {
                startWaitTimer();
            } else if (this.e.getPackRootNode() != null) {
                String str = "";
                boolean z = true;
                String str2 = null;
                if (resultData instanceof CloudInterfResData) {
                    CloudInterfResData cloudInterfResData = (CloudInterfResData) resultData;
                    str2 = cloudInterfResData.getTag();
                    str = cloudInterfResData.getCurNodeName();
                    this.f = cloudInterfResData.isLastResponse();
                    obj = cloudInterfResData.getCurNode();
                    f2 = a(obj);
                } else if (resultData instanceof TraceRouteInfo) {
                    this.i = (TraceRouteInfo) resultData;
                    str2 = "network";
                    this.g = true;
                    obj = this.i.getTraceInfoNodeMap();
                    f2 = a(obj);
                } else {
                    obj = null;
                    f2 = 0.0f;
                }
                StringBuilder sb = new StringBuilder("---parentNodeName:");
                sb.append(str2);
                sb.append("----curNodeTotalK:");
                sb.append(f2);
                eao.b("key_detection", sb.toString());
                if (f2 > 0.0f) {
                    if (f2 > this.h) {
                        a();
                        int i2 = (f2 > 100.0f ? 1 : (f2 == 100.0f ? 0 : -1));
                        if (i2 == 0) {
                            a(str2, str, obj);
                            a();
                        } else if (i2 <= 0 && f2 < 100.0f) {
                            a(str2, str, obj);
                            this.h = 100.0f - f2;
                        }
                    } else if (f2 <= this.h) {
                        a(str2, str, obj);
                        this.h -= f2;
                        if (this.h <= 0.0f) {
                            a();
                        }
                    }
                }
                if (!this.f || !this.g) {
                    z = false;
                }
                StringBuilder sb2 = new StringBuilder("---isFinish:");
                sb2.append(z);
                sb2.append("-----isLastUrlNode:");
                sb2.append(this.f);
                sb2.append("-----isNetTraceFinish:");
                sb2.append(this.g);
                eao.b("key_detection", sb2.toString());
                this.e.setPackFlag(z);
                if (z) {
                    if (this.e.getPackRootNode().toJSONString().length() > 2) {
                        a();
                    }
                    finish();
                    cancelWaitTimer();
                    callbackOnResponse(this.i);
                }
            }
        }
    }

    private void a(String str, String str2, Object obj) {
        JSONObject nodeByKey = !TextUtils.isEmpty(str) ? this.e.getNodeByKey(str) : null;
        if (obj != null) {
            if (!(obj instanceof JSONObject)) {
                nodeByKey.putAll((Map) obj);
            } else if (nodeByKey != null && !TextUtils.isEmpty(str2)) {
                nodeByKey.put(str2, obj);
            }
        }
    }

    private static float a(Object obj) {
        if (obj != null) {
            try {
                return ((float) JSONObject.toJSONString(obj).getBytes("utf-8").length) / 1024.0f;
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return 0.0f;
    }

    private void a() {
        PackData packData = this.e;
        packData.getPackRootNode().put((String) "user_info", (Object) dsw.a());
        callbackOnResponse(packData);
        b();
    }

    private void b() {
        this.e = new PackData();
        this.h = 100.0f;
    }

    public final void start() {
        super.start();
        b();
        this.a.update(3);
    }
}
