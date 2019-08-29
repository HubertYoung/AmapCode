package com.alipay.rdssecuritysdk.v3.impl;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Usr.Action;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Usr.Ua;
import com.uc.webview.export.internal.interfaces.IWaStat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserBehaviourBuilder {
    private final int a;
    private final int b;
    private TraceLogger c;
    private List<UserBehaviourGroup> d;
    private AtomicInteger e;
    private long f;

    class UserBehaviourGroup {
        public UserBehaviourType a = UserBehaviourType.TYPE_NULL;
        public Action b = new Action();

        public UserBehaviourGroup() {
        }
    }

    public enum UserBehaviourType {
        TYPE_PAGE_ENTER(H5Param.SAFEPAY_ENABLE),
        TYPE_EDITTEXT_INPUT("ei"),
        TYPE_FOUCS_CHANGES(DictionaryKeys.EVENT_TYPE_FOCUS),
        TYPE_CLICK(IWaStat.KEY_CHECK_COMPRESS),
        TYPE_SCREEN_TOUCH("st"),
        TYPE_NULL("");
        
        public final String uaEventTag;

        private UserBehaviourType(String str) {
            this.uaEventTag = str;
        }
    }

    private UserBehaviourBuilder() {
        this.a = 15;
        this.b = 20;
        this.c = LoggerFactory.f();
        this.f = 0;
        this.d = new ArrayList();
        this.e = new AtomicInteger(0);
        this.f = System.currentTimeMillis();
    }

    public static UserBehaviourBuilder a() {
        return new UserBehaviourBuilder();
    }

    public final void a(String str, String str2) {
        a(UserBehaviourType.TYPE_CLICK, str, str2, null, null, false, 0.0d, 0.0d);
    }

    public final void a(String str, String str2, String str3) {
        a(UserBehaviourType.TYPE_EDITTEXT_INPUT, str, str2, null, str3, false, 0.0d, 0.0d);
    }

    public final void a(String str, String str2, boolean z) {
        a(UserBehaviourType.TYPE_FOUCS_CHANGES, str, str2, null, null, z, 0.0d, 0.0d);
    }

    public final void a(String str, String str2, double d2, double d3) {
        a(UserBehaviourType.TYPE_SCREEN_TOUCH, str, str2, null, null, false, d2, d3);
    }

    public final void b(String str, String str2) {
        a(UserBehaviourType.TYPE_PAGE_ENTER, str, null, str2, null, false, 0.0d, 0.0d);
    }

    public final Ua b() {
        Ua ua = new Ua();
        ua.action = new ArrayList();
        for (UserBehaviourGroup userBehaviourGroup : this.d) {
            ua.action.add(userBehaviourGroup.b);
        }
        ua.num = String.valueOf(this.d.size());
        ua.t = String.valueOf(this.f);
        return ua;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00e0, code lost:
        if (com.alipay.security.mobile.module.commonutils.CommonUtils.equals(r2.b.cn, r5) != false) goto L_0x0118;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder.UserBehaviourType r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, boolean r22, double r23, double r25) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r20
            r3 = r21
            boolean r4 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r18)
            if (r4 == 0) goto L_0x0011
            java.lang.String r4 = "-"
            goto L_0x0013
        L_0x0011:
            r4 = r18
        L_0x0013:
            boolean r5 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r19)
            if (r5 == 0) goto L_0x001c
            java.lang.String r5 = "-"
            goto L_0x001e
        L_0x001c:
            r5 = r19
        L_0x001e:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r6 = r0.c
            java.lang.String r7 = "APSecuritySdk"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "handleNewActionByActionType: type = "
            r8.<init>(r9)
            java.lang.String r9 = r1.uaEventTag
            r8.append(r9)
            java.lang.String r9 = ", pageName = "
            r8.append(r9)
            r9 = r18
            r8.append(r9)
            java.lang.String r10 = ", ctrlName = "
            r8.append(r10)
            r10 = r19
            r8.append(r10)
            java.lang.String r11 = ", optionalPr = "
            r8.append(r11)
            r8.append(r2)
            java.lang.String r11 = ", optionalKey = "
            r8.append(r11)
            r8.append(r3)
            java.lang.String r11 = ", hasFocus = "
            r8.append(r11)
            r11 = r22
            r8.append(r11)
            java.lang.String r12 = ", x = "
            r8.append(r12)
            r12 = r23
            r8.append(r12)
            java.lang.String r14 = ", y = "
            r8.append(r14)
            r14 = r25
            r8.append(r14)
            java.lang.String r8 = r8.toString()
            r6.b(r7, r8)
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$AD r6 = new com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$AD
            r6.<init>()
            long r7 = java.lang.System.currentTimeMillis()
            java.lang.String r7 = java.lang.String.valueOf(r7)
            r6.t = r7
            int[] r7 = com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder.AnonymousClass1.a
            int r8 = r17.ordinal()
            r7 = r7[r8]
            switch(r7) {
                case 1: goto L_0x00a9;
                case 2: goto L_0x00a6;
                case 3: goto L_0x0099;
                case 4: goto L_0x00ab;
                case 5: goto L_0x0092;
                default: goto L_0x0091;
            }
        L_0x0091:
            return
        L_0x0092:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r22)
            r6.f = r2
            goto L_0x00ab
        L_0x0099:
            java.lang.String r2 = java.lang.String.valueOf(r23)
            r6.x = r2
            java.lang.String r2 = java.lang.String.valueOf(r25)
            r6.y = r2
            goto L_0x00ab
        L_0x00a6:
            r6.key = r3
            goto L_0x00ab
        L_0x00a9:
            r6.pr = r2
        L_0x00ab:
            java.util.List<com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup> r2 = r0.d
            int r2 = r2.size()
            r3 = 0
            if (r2 <= 0) goto L_0x00e3
            java.util.List<com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup> r2 = r0.d
            java.util.List<com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup> r7 = r0.d
            int r7 = r7.size()
            int r7 = r7 + -1
            java.lang.Object r2 = r2.get(r7)
            com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup r2 = (com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder.UserBehaviourGroup) r2
            if (r2 == 0) goto L_0x00e3
            com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourType r7 = r2.a
            if (r7 == 0) goto L_0x00e3
            com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourType r7 = r2.a
            if (r7 != r1) goto L_0x00e3
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            java.lang.String r7 = r7.pn
            boolean r7 = com.alipay.security.mobile.module.commonutils.CommonUtils.equals(r7, r4)
            if (r7 == 0) goto L_0x00e3
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            java.lang.String r7 = r7.cn
            boolean r7 = com.alipay.security.mobile.module.commonutils.CommonUtils.equals(r7, r5)
            if (r7 == 0) goto L_0x00e3
            goto L_0x0118
        L_0x00e3:
            java.util.List<com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup> r2 = r0.d
            int r2 = r2.size()
            r7 = 15
            if (r2 != r7) goto L_0x00f2
            java.util.List<com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup> r2 = r0.d
            r2.remove(r3)
        L_0x00f2:
            com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup r2 = new com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup
            r2.<init>()
            r2.a = r1
            java.util.List<com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourGroup> r7 = r0.d
            r7.add(r2)
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            java.util.concurrent.atomic.AtomicInteger r8 = r0.e
            int r8 = r8.incrementAndGet()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            r7.seq = r8
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            long r11 = java.lang.System.currentTimeMillis()
            java.lang.String r8 = java.lang.String.valueOf(r11)
            r7.t = r8
        L_0x0118:
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            java.util.List<com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$AD> r7 = r7.ad
            int r7 = r7.size()
            r8 = 20
            if (r7 != r8) goto L_0x012b
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            java.util.List<com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$AD> r7 = r7.ad
            r7.remove(r3)
        L_0x012b:
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            boolean r8 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r18)
            if (r8 == 0) goto L_0x0136
            java.lang.String r8 = "2"
            goto L_0x0141
        L_0x0136:
            boolean r8 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r19)
            if (r8 == 0) goto L_0x013f
            java.lang.String r8 = "1"
            goto L_0x0141
        L_0x013f:
            java.lang.String r8 = "0"
        L_0x0141:
            r7.type = r8
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r7 = r2.b
            java.lang.String r1 = r1.uaEventTag
            r7.et = r1
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r1 = r2.b
            r1.pn = r4
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r1 = r2.b
            r1.cn = r5
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r1 = r2.b
            java.util.List<com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$AD> r1 = r1.ad
            r1.add(r3, r6)
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r1 = r2.b
            com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$Action r2 = r2.b
            java.util.List<com.alipay.rdssecuritysdk.v3.RdsRequestMessage$Sdk$Usr$AD> r2 = r2.ad
            int r2 = r2.size()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1.num = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder.a(com.alipay.rdssecuritysdk.v3.impl.UserBehaviourBuilder$UserBehaviourType, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, double, double):void");
    }
}
