package com.amap.bundle.drive.ajx.module;

import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.inter.IFullScreenChangeCallback;
import com.amap.bundle.drive.ajx.inter.IReportEvent;
import com.amap.bundle.drive.ajx.inter.ShareStatusListener;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.d;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI.Token;
import org.json.JSONException;
import org.json.JSONObject;

public class ModuleDriveNaviImpl {
    private final String TAG = "ModuleDriveNaviImpl";

    class SafeHomeShareStatusCallback extends dcd {
        int isFirst;
        JsFunctionCallback jsFunctionCallback;
        String parms;
        ShareStatusListener shareStatusListener;

        public SafeHomeShareStatusCallback(String str, JsFunctionCallback jsFunctionCallback2, ShareStatusListener shareStatusListener2) {
            this.parms = str;
            this.jsFunctionCallback = jsFunctionCallback2;
            this.shareStatusListener = shareStatusListener2;
        }

        public ShareParam getShareDataByType(int i) {
            if (this.shareStatusListener != null) {
                this.shareStatusListener.getShareDataByType(i);
            }
            if (i != 0) {
                switch (i) {
                    case 3:
                        e eVar = new e(0);
                        try {
                            JSONObject jSONObject = new JSONObject(this.parms);
                            eVar.f = jSONObject.optString("title");
                            eVar.a = jSONObject.optString("content");
                            eVar.b = jSONObject.optString("shortUrl");
                            this.isFirst = jSONObject.optInt("isFirst");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        eVar.g = BitmapFactory.decodeResource(AMapAppGlobal.getApplication().getResources(), R.drawable.weixin_route);
                        eVar.e = 0;
                        return eVar;
                    case 4:
                        e eVar2 = new e(1);
                        try {
                            JSONObject jSONObject2 = new JSONObject(this.parms);
                            eVar2.f = jSONObject2.optString("title");
                            eVar2.a = jSONObject2.optString("content");
                            eVar2.b = jSONObject2.optString("shortUrl");
                            this.isFirst = jSONObject2.optInt("isFirst");
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        eVar2.g = BitmapFactory.decodeResource(AMapAppGlobal.getApplication().getResources(), R.drawable.weixin_route);
                        eVar2.e = 0;
                        return eVar2;
                    default:
                        return null;
                }
            } else {
                d dVar = new d();
                try {
                    JSONObject jSONObject3 = new JSONObject(this.parms);
                    dVar.a = jSONObject3.optString("content");
                    dVar.b = jSONObject3.optString("shortUrl");
                    this.isFirst = jSONObject3.optInt("isFirst");
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                return dVar;
            }
        }

        public void onFinish(int i, int i2) {
            super.onFinish(i, i2);
            ku a = ku.a();
            StringBuilder sb = new StringBuilder("SafeHomeShareStatusCallback  onFinish shareType:");
            sb.append(i);
            sb.append("   resultCode:");
            sb.append(i2);
            a.c("NaviMonitor", sb.toString());
            if (this.shareStatusListener != null) {
                this.shareStatusListener.onFinish(i, i2);
            }
            final JSONObject jSONObject = new JSONObject();
            switch (i2) {
                case -1:
                    try {
                        jSONObject.put("code", 0);
                        ModuleDriveNaviImpl.this.safeShareCallbackAjx(this.jsFunctionCallback, jSONObject);
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                case 0:
                    if (i == 3 || i == 4) {
                        ku a2 = ku.a();
                        StringBuilder sb2 = new StringBuilder("SafeHomeShareStatusCallback  onFinish  case IShareVApp.ShareConstant.SHARE_RESULT_OK this.isFirst:");
                        sb2.append(this.isFirst);
                        a2.c("NaviMonitor", sb2.toString());
                        if (this.isFirst == 1) {
                            final ctl ctl = (ctl) a.a.a(ctl.class);
                            if (ctl != null) {
                                ctl.a("3", new Callback<ctm>() {
                                    public void error(Throwable th, boolean z) {
                                        try {
                                            jSONObject.put("code", 1);
                                            jSONObject.put("data", 0);
                                            ModuleDriveNaviImpl.this.safeShareCallbackAjx(SafeHomeShareStatusCallback.this.jsFunctionCallback, jSONObject);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    public void callback(ctm ctm) {
                                        if (ctm != null) {
                                            try {
                                                if (ctm.a == 1) {
                                                    int i = ctm.b;
                                                    if (i != 1) {
                                                        if (i != 2) {
                                                            jSONObject.put("code", 1);
                                                            jSONObject.put("data", 0);
                                                        }
                                                    }
                                                    jSONObject.put("code", 1);
                                                    jSONObject.put("data", i);
                                                    ctl.a(null, "3", null);
                                                } else {
                                                    jSONObject.put("code", 1);
                                                    jSONObject.put("data", 0);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            ModuleDriveNaviImpl.this.safeShareCallbackAjx(SafeHomeShareStatusCallback.this.jsFunctionCallback, jSONObject);
                                        }
                                    }
                                });
                            }
                            return;
                        }
                        try {
                            jSONObject.put("code", 1);
                            jSONObject.put("data", 0);
                            ModuleDriveNaviImpl.this.safeShareCallbackAjx(this.jsFunctionCallback, jSONObject);
                            return;
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
            }
        }
    }

    public void onReportButtonClick(int i, IReportEvent iReportEvent) {
        if (iReportEvent != null) {
            iReportEvent.onReportEvent(i);
        }
    }

    public void safetyShare(String str, String str2, ShareStatusListener shareStatusListener, JsFunctionCallback jsFunctionCallback) {
        dct dct;
        if (Token.WX_TOKEN_PLATFORMID_VALUE.equals(str)) {
            dct = new dct(0);
            dct.d = true;
            dct.m = true;
        } else if ("wechattimeline".equals(str)) {
            dct = new dct(0);
            dct.e = true;
            dct.m = true;
        } else if ("sms".equals(str)) {
            dct = new dct(0);
            dct.a = true;
            dct.m = true;
        } else {
            dct = null;
        }
        SafeHomeShareStatusCallback safeHomeShareStatusCallback = new SafeHomeShareStatusCallback(str2, jsFunctionCallback, shareStatusListener);
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            dcb.a(dct, (dcd) safeHomeShareStatusCallback);
        }
    }

    /* access modifiers changed from: private */
    public void safeShareCallbackAjx(JsFunctionCallback jsFunctionCallback, JSONObject jSONObject) {
        ku.a().c("NaviMonitor", "safeShareCallbackAjx  jo:".concat(String.valueOf(jSONObject)));
        if (jsFunctionCallback != null && jSONObject != null) {
            jsFunctionCallback.callback(jSONObject.toString());
        }
    }

    public void updateDayNightState(String str) {
        Float valueOf = Float.valueOf(Float.parseFloat(str));
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (valueOf.floatValue() == 1.0f) {
            if (cuh != null) {
                cuh.b().a(MemberIconStyle.SMALL_DAY);
            }
        } else if (valueOf.floatValue() == 0.0f && cuh != null) {
            cuh.b().a(MemberIconStyle.SMALL_NIGHT);
        }
    }

    public void receiveMitNaviCalcRoute(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int optInt = jSONObject.optInt("isSucceed");
                int optInt2 = jSONObject.optInt("tokenID");
                StringBuilder sb = new StringBuilder("tokenID：");
                sb.append(optInt2);
                sb.append("isSucceed:");
                sb.append(optInt);
                tq.b("NaviMonitor", "receiveMitNaviCalcRoute", sb.toString());
                String str2 = null;
                if (optInt == 1) {
                    int optInt3 = jSONObject.optInt("time");
                    int optInt4 = jSONObject.optInt("length");
                    String optString = jSONObject.optString("highlightRoadName");
                    bgb a = d.a.a(optInt2);
                    if (a != null) {
                        bfe bfe = d.a;
                        str2 = rm.a(bfe.a(a.h, a.i, 10000), optString, optInt4, optInt3);
                        if (a.n) {
                            PlaySoundUtils.getInstance().playSound(str2);
                            return;
                        }
                    }
                    d.a.a(optInt2, 10000, str2);
                    return;
                }
                int optInt5 = jSONObject.optInt("errorCode");
                StringBuilder sb2 = new StringBuilder("tokenID：");
                sb2.append(optInt2);
                sb2.append("errorCode:");
                sb2.append(optInt5);
                tq.b("NaviMonitor", "receiveMitNaviCalcRoute", sb2.toString());
                d.a.a(optInt2, optInt5, (String) null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setFullScreen(String str, IFullScreenChangeCallback iFullScreenChangeCallback) {
        try {
            int i = new JSONObject(str).getInt("isShow");
            boolean z = true;
            if (i == 1) {
                tt.b(AMapAppGlobal.getTopActivity());
                z = false;
            } else {
                tt.a(AMapAppGlobal.getTopActivity());
            }
            if (iFullScreenChangeCallback != null) {
                iFullScreenChangeCallback.onChangeFullScreen(z);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void log(String str) {
        AMapLog.i("ModuleDriveNaviImpl", "module_opt ".concat(String.valueOf(str)));
    }

    public void onGroupOverlayHighlighted(final JsFunctionCallback jsFunctionCallback) {
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a((cuj) new cuj() {
                public void onMemberSelected(String str) {
                    jsFunctionCallback.callback(new Object[0]);
                }
            });
        }
    }

    public void unHighlightedGroupOverlay() {
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().f();
        }
    }
}
