package com.airbnb.lottie.model.content;

public final class Mask {
    public final MaskMode a;
    public final he b;
    public final ha c;

    public enum MaskMode {
        MaskModeAdd,
        MaskModeSubtract,
        MaskModeIntersect,
        MaskModeUnknown
    }

    public static class a {
        /* JADX WARNING: Removed duplicated region for block: B:17:0x003b  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x003e  */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0041  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0044  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static com.airbnb.lottie.model.content.Mask a(org.json.JSONObject r4, defpackage.ev r5) {
            /*
                java.lang.String r0 = "mode"
                java.lang.String r0 = r4.optString(r0)
                int r1 = r0.hashCode()
                r2 = 97
                r3 = 0
                if (r1 == r2) goto L_0x002d
                r2 = 105(0x69, float:1.47E-43)
                if (r1 == r2) goto L_0x0023
                r2 = 115(0x73, float:1.61E-43)
                if (r1 == r2) goto L_0x0018
                goto L_0x0037
            L_0x0018:
                java.lang.String r1 = "s"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0037
                r0 = 1
                goto L_0x0038
            L_0x0023:
                java.lang.String r1 = "i"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0037
                r0 = 2
                goto L_0x0038
            L_0x002d:
                java.lang.String r1 = "a"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0037
                r0 = 0
                goto L_0x0038
            L_0x0037:
                r0 = -1
            L_0x0038:
                switch(r0) {
                    case 0: goto L_0x0044;
                    case 1: goto L_0x0041;
                    case 2: goto L_0x003e;
                    default: goto L_0x003b;
                }
            L_0x003b:
                com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeUnknown
                goto L_0x0046
            L_0x003e:
                com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeIntersect
                goto L_0x0046
            L_0x0041:
                com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeSubtract
                goto L_0x0046
            L_0x0044:
                com.airbnb.lottie.model.content.Mask$MaskMode r0 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeAdd
            L_0x0046:
                java.lang.String r1 = "pt"
                org.json.JSONObject r1 = r4.optJSONObject(r1)
                he r1 = defpackage.he.a.a(r1, r5)
                java.lang.String r2 = "o"
                org.json.JSONObject r4 = r4.optJSONObject(r2)
                ha r4 = defpackage.ha.a.a(r4, r5)
                com.airbnb.lottie.model.content.Mask r5 = new com.airbnb.lottie.model.content.Mask
                r5.<init>(r0, r1, r4, r3)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.content.Mask.a.a(org.json.JSONObject, ev):com.airbnb.lottie.model.content.Mask");
        }
    }

    /* synthetic */ Mask(MaskMode maskMode, he heVar, ha haVar, byte b2) {
        this(maskMode, heVar, haVar);
    }

    private Mask(MaskMode maskMode, he heVar, ha haVar) {
        this.a = maskMode;
        this.b = heVar;
        this.c = haVar;
    }
}
