package com.autonavi.bundle.routecommute.common.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class CommuteControlBean implements Serializable {
    private static final long serialVersionUID = 1;
    public String busBubbleRule;
    public String carBubbleRule;
    public String commuteSwitch;
    private String mBusActivitySwitch;
    private azo mBusBubbleRuleInfo;
    private a mBusOperationOptions;
    private String mDriveActivitySwitch;
    private b mDriveOperationOptions;
    private azp newUserBubbleRule;
    public String reultJson;
    public String showBus;

    public static class a extends c {
        public String a;
        public String b;
    }

    public static class b extends c {
    }

    public static class c {
        public String c;
        public String d;
    }

    private void CommuteControlBean() {
    }

    @Nullable
    public static CommuteControlBean create(String str) {
        CommuteControlBean commuteControlBean;
        azp azp;
        a aVar;
        defpackage.azp.b bVar;
        defpackage.azp.a aVar2;
        defpackage.azp.c cVar;
        b bVar2 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("commuteSwitch");
            String optString2 = jSONObject.optString("showBus");
            String optString3 = jSONObject.optString("busBubbleRule");
            String optString4 = jSONObject.optString("carBubbleRule");
            commuteControlBean = new CommuteControlBean();
            try {
                commuteControlBean.commuteSwitch = optString;
                commuteControlBean.showBus = optString2;
                commuteControlBean.busBubbleRule = optString3;
                commuteControlBean.carBubbleRule = optString4;
                commuteControlBean.reultJson = str;
                JSONObject optJSONObject = jSONObject.optJSONObject("NewUserResource");
                if (optJSONObject == null) {
                    azp = null;
                } else {
                    azp = new azp();
                    JSONObject optJSONObject2 = optJSONObject.optJSONObject("CarNewUserBubble");
                    if (optJSONObject2 == null) {
                        bVar = null;
                    } else {
                        bVar = new defpackage.azp.b();
                        bVar.a = optJSONObject2.optString("toWorkMainHeading");
                        bVar.b = optJSONObject2.optString("toWorkSubHeading");
                        bVar.c = optJSONObject2.optString("toHomeMainHeading");
                        bVar.d = optJSONObject2.optString("toHomeSubHeading");
                    }
                    azp.a = bVar;
                    JSONObject optJSONObject3 = optJSONObject.optJSONObject("BusNewUserBubble");
                    if (optJSONObject3 == null) {
                        aVar2 = null;
                    } else {
                        aVar2 = new defpackage.azp.a();
                        aVar2.a = optJSONObject3.optString("MainHeading");
                        aVar2.b = optJSONObject3.optString("SubHeading");
                    }
                    azp.b = aVar2;
                    JSONObject optJSONObject4 = optJSONObject.optJSONObject("NewUserPic");
                    if (optJSONObject4 == null) {
                        cVar = null;
                    } else {
                        cVar = new defpackage.azp.c();
                        cVar.a = optJSONObject4.optString("picUrl");
                    }
                    azp.c = cVar;
                }
                commuteControlBean.newUserBubbleRule = azp;
                commuteControlBean.mBusActivitySwitch = jSONObject.optString("BusActivitySwitch");
                commuteControlBean.mDriveActivitySwitch = jSONObject.optString("DriveActivitySwitch");
                JSONObject optJSONObject5 = jSONObject.optJSONObject("BusActivityOptions");
                if (optJSONObject5 == null) {
                    aVar = null;
                } else {
                    aVar = new a();
                    aVar.c = optJSONObject5.optString("homeUrl");
                    aVar.d = optJSONObject5.optString("workUrl");
                    aVar.b = optJSONObject5.optString("commonUrl");
                    aVar.a = optJSONObject5.optString("emergencyUrl");
                }
                commuteControlBean.mBusOperationOptions = aVar;
                JSONObject optJSONObject6 = jSONObject.optJSONObject("DriveActivityOptions");
                if (optJSONObject6 != null) {
                    bVar2 = new b();
                    bVar2.c = optJSONObject6.optString("homeUrl");
                    bVar2.d = optJSONObject6.optString("workUrl");
                }
                commuteControlBean.mDriveOperationOptions = bVar2;
            } catch (JSONException e) {
                e = e;
                e.printStackTrace();
                return commuteControlBean;
            }
        } catch (JSONException e2) {
            e = e2;
            commuteControlBean = null;
            e.printStackTrace();
            return commuteControlBean;
        }
        return commuteControlBean;
    }

    public boolean isCommuteSwitch() {
        return TextUtils.equals(this.commuteSwitch, "1");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0032  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isOperateEventEnable(java.lang.String r4) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 97920(0x17e80, float:1.37215E-40)
            r2 = 0
            if (r0 == r1) goto L_0x001a
            r1 = 95852938(0x5b6998a, float:1.7171599E-35)
            if (r0 == r1) goto L_0x0010
            goto L_0x0024
        L_0x0010:
            java.lang.String r0 = "drive"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0024
            r4 = 1
            goto L_0x0025
        L_0x001a:
            java.lang.String r0 = "bus"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0024
            r4 = 0
            goto L_0x0025
        L_0x0024:
            r4 = -1
        L_0x0025:
            switch(r4) {
                case 0: goto L_0x0032;
                case 1: goto L_0x0029;
                default: goto L_0x0028;
            }
        L_0x0028:
            return r2
        L_0x0029:
            java.lang.String r4 = r3.mDriveActivitySwitch
            java.lang.String r0 = "1"
            boolean r4 = android.text.TextUtils.equals(r4, r0)
            return r4
        L_0x0032:
            java.lang.String r4 = r3.mBusActivitySwitch
            java.lang.String r0 = "1"
            boolean r4 = android.text.TextUtils.equals(r4, r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommute.common.bean.CommuteControlBean.isOperateEventEnable(java.lang.String):boolean");
    }

    @Nullable
    public a getBusOperationOptions() {
        return this.mBusOperationOptions;
    }

    @Nullable
    public b getDriveOperationOptions() {
        return this.mDriveOperationOptions;
    }

    @NonNull
    public azo getBusBubbleRuleInfo() {
        if (this.mBusBubbleRuleInfo == null) {
            this.mBusBubbleRuleInfo = new azo();
        }
        azo azo = this.mBusBubbleRuleInfo;
        String str = this.busBubbleRule;
        if (!TextUtils.equals(str, azo.c)) {
            azo.c = str;
            try {
                JSONObject jSONObject = new JSONObject(azo.c);
                azo.b(jSONObject);
                double optDouble = jSONObject.optDouble("minDistance");
                double optDouble2 = jSONObject.optDouble("maxDistance");
                if (!Double.isNaN(optDouble)) {
                    azo.a = optDouble * 1000.0d;
                }
                if (!Double.isNaN(optDouble2)) {
                    azo.b = optDouble2 * 1000.0d;
                }
                azo.a(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this.mBusBubbleRuleInfo;
    }

    public azp getNewUserBubbleRule() {
        return this.newUserBubbleRule;
    }
}
