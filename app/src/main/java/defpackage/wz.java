package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: wz reason: default package */
/* compiled from: SetConfigExecutor */
public class wz implements wv {
    private static final String a = "wz";
    private StringBuilder b = new StringBuilder();
    private Context c;

    public final boolean a(Command command) {
        return true;
    }

    public wz(Context context) {
        this.c = context;
    }

    public final CommandResult a(String str, int i, Command command) {
        Command command2 = command;
        try {
            String e = command2.e("key");
            String e2 = command2.e("value");
            if (TextUtils.isEmpty(e) || !a(e, e2)) {
                StringBuilder sb = this.b;
                sb.append("unSupport kv=");
                sb.append(e);
                sb.append(":");
                sb.append(e2);
                sb.append(";");
                CommandResult commandResult = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, this.b.toString());
                return commandResult;
            }
            CommandResult commandResult2 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 1, this.b.toString());
            return commandResult2;
        } catch (Exception e3) {
            StringBuilder sb2 = this.b;
            sb2.append(e3.toString());
            sb2.append(";");
            CommandResult commandResult3 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, this.b.toString());
            return commandResult3;
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r6, java.lang.String r7) {
        /*
            int r0 = r6.hashCode()
            r1 = 16
            r2 = 17
            r3 = 18
            r4 = 0
            r5 = 1
            switch(r0) {
                case -2111860539: goto L_0x00d9;
                case -2070266668: goto L_0x00ce;
                case -1891780240: goto L_0x00c3;
                case -1634026315: goto L_0x00b9;
                case -1451624981: goto L_0x00ae;
                case -1412029430: goto L_0x00a4;
                case -1409654199: goto L_0x009a;
                case -913217683: goto L_0x008f;
                case -663428675: goto L_0x0084;
                case -639746689: goto L_0x0079;
                case -365905218: goto L_0x006d;
                case -364233557: goto L_0x0062;
                case -150608859: goto L_0x0056;
                case 119399332: goto L_0x004b;
                case 631243924: goto L_0x0040;
                case 922839854: goto L_0x0034;
                case 1440569092: goto L_0x0028;
                case 1832075265: goto L_0x001c;
                case 1990017581: goto L_0x0011;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x00e3
        L_0x0011:
            java.lang.String r0 = "DAY_NIGHT_MODE"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 2
            goto L_0x00e4
        L_0x001c:
            java.lang.String r0 = "ACCS_SWITCH"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 13
            goto L_0x00e4
        L_0x0028:
            java.lang.String r0 = "USER_INSIGHT"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 15
            goto L_0x00e4
        L_0x0034:
            java.lang.String r0 = "LOC_LOG_SWITCH"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 18
            goto L_0x00e4
        L_0x0040:
            java.lang.String r0 = "CAR_DIRECTION"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 3
            goto L_0x00e4
        L_0x004b:
            java.lang.String r0 = "DOWNLOAD_INTERSECTION_OF_REAL_MAP"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 7
            goto L_0x00e4
        L_0x0056:
            java.lang.String r0 = "NAVIGATION_VOICE_CONTROL"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 8
            goto L_0x00e4
        L_0x0062:
            java.lang.String r0 = "SCALE_AUTO_CHANGE"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 6
            goto L_0x00e4
        L_0x006d:
            java.lang.String r0 = "BAT_SWITCH"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 14
            goto L_0x00e4
        L_0x0079:
            java.lang.String r0 = "LOTUSPOOL_APP_LAUNCH_INTERVAL"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 12
            goto L_0x00e4
        L_0x0084:
            java.lang.String r0 = "LOTUSPOOL_LAUNCH_INTERVAL"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 11
            goto L_0x00e4
        L_0x008f:
            java.lang.String r0 = "TTS_MIXED_MUSIC_MODE"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 10
            goto L_0x00e4
        L_0x009a:
            java.lang.String r0 = "NAVIMODE"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 1
            goto L_0x00e4
        L_0x00a4:
            java.lang.String r0 = "LIGHT_INTENSITY"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 4
            goto L_0x00e4
        L_0x00ae:
            java.lang.String r0 = "CALLING_SPEAK_TTS"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 9
            goto L_0x00e4
        L_0x00b9:
            java.lang.String r0 = "PARKING_RECOMMEND"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 5
            goto L_0x00e4
        L_0x00c3:
            java.lang.String r0 = "TAXI_LOG_SWITCH"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 17
            goto L_0x00e4
        L_0x00ce:
            java.lang.String r0 = "UT_SWITCH"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 16
            goto L_0x00e4
        L_0x00d9:
            java.lang.String r0 = "PLAY_ELE_EYE"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x00e3
            r0 = 0
            goto L_0x00e4
        L_0x00e3:
            r0 = -1
        L_0x00e4:
            switch(r0) {
                case 0: goto L_0x0262;
                case 1: goto L_0x0253;
                case 2: goto L_0x023a;
                case 3: goto L_0x022b;
                case 4: goto L_0x021c;
                case 5: goto L_0x020d;
                case 6: goto L_0x01fe;
                case 7: goto L_0x01ee;
                case 8: goto L_0x01de;
                case 9: goto L_0x01ce;
                case 10: goto L_0x01be;
                case 11: goto L_0x01b2;
                case 12: goto L_0x01a8;
                case 13: goto L_0x0198;
                case 14: goto L_0x0188;
                case 15: goto L_0x0173;
                case 16: goto L_0x0163;
                case 17: goto L_0x0153;
                case 18: goto L_0x0108;
                default: goto L_0x00e7;
            }
        L_0x00e7:
            boolean r0 = defpackage.bno.a
            if (r0 == 0) goto L_0x0270
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "invalid key:"
            r1.<init>(r2)
            r1.append(r6)
            java.lang.String r6 = ",value:"
            r1.append(r6)
            r1.append(r7)
            java.lang.String r6 = r1.toString()
            com.amap.bundle.logs.AMapLog.d(r0, r6)
            goto L_0x0270
        L_0x0108:
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x0135 }
            r6.<init>(r7)     // Catch:{ Exception -> 0x0135 }
            java.lang.String r7 = "log_switch"
            java.lang.String r0 = "log_switch"
            boolean r0 = r6.optBoolean(r0, r4)     // Catch:{ Exception -> 0x0135 }
            a(r7, r0)     // Catch:{ Exception -> 0x0135 }
            java.lang.String r7 = "log_encrypt"
            java.lang.String r0 = "log_encrypt"
            boolean r0 = r6.optBoolean(r0, r5)     // Catch:{ Exception -> 0x0135 }
            a(r7, r0)     // Catch:{ Exception -> 0x0135 }
            java.lang.String r7 = "log_level"
            java.lang.String r0 = "log_level"
            int r6 = r6.optInt(r0, r4)     // Catch:{ Exception -> 0x0135 }
            a(r7, r6)     // Catch:{ Exception -> 0x0135 }
            java.lang.String r6 = "LOC_LOG_SWITCH"
            a(r6, r5)     // Catch:{ Exception -> 0x0135 }
            goto L_0x01bb
        L_0x0135:
            r6 = move-exception
            boolean r7 = defpackage.bno.a
            if (r7 == 0) goto L_0x0270
            java.lang.String r7 = a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "LOC_LOG_SWITCH error:"
            r0.<init>(r1)
            java.lang.String r6 = android.util.Log.getStackTraceString(r6)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            com.amap.bundle.logs.AMapLog.e(r7, r6, r5)
            goto L_0x0270
        L_0x0153:
            java.lang.String r6 = "TAXI_LOG_SWITCH"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x0163:
            java.lang.String r6 = "batactionhelper_ut"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x0173:
            com.amap.bundle.mapstorage.MapSharePreference r6 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r0 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r6.<init>(r0)
            java.lang.String r0 = "evaluate_user_insight"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            r6.putBooleanValue(r0, r7)
            return r5
        L_0x0188:
            java.lang.String r6 = "batactionhelper_switch"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x0198:
            java.lang.String r6 = "ACCS_SWITCH"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x01a8:
            java.lang.String r6 = "LOTUSPOOL_APP_LAUNCH_INTERVAL"
            long r0 = java.lang.Long.parseLong(r7)
            defpackage.xf.a(r6, r0)
            goto L_0x01bb
        L_0x01b2:
            java.lang.String r6 = "LOTUSPOOL_LAUNCH_INTERVAL"
            long r0 = java.lang.Long.parseLong(r7)
            defpackage.xf.a(r6, r0)
        L_0x01bb:
            r4 = 1
            goto L_0x0270
        L_0x01be:
            java.lang.String r6 = "TTSMixedMusicMode"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x01ce:
            java.lang.String r6 = "CallingSpeakTTS"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x01de:
            java.lang.String r6 = "navigation_voice_control"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x01ee:
            java.lang.String r6 = "DownloadIntersectionOfRealMap"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x01fe:
            java.lang.String r6 = "ScaleAutoChange"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x020d:
            java.lang.String r6 = "RecommendPark"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x021c:
            java.lang.String r6 = "LightnessControl"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x022b:
            java.lang.String r6 = "NaviMapMode"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x023a:
            boolean r6 = defpackage.xi.b(r7)
            if (r6 == 0) goto L_0x0270
            int r6 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x0245 }
            goto L_0x0246
        L_0x0245:
            r6 = 0
        L_0x0246:
            if (r6 == r1) goto L_0x024c
            if (r6 == r2) goto L_0x024c
            if (r6 != r3) goto L_0x0270
        L_0x024c:
            java.lang.String r7 = "NaviModeSet"
            boolean r4 = a(r7, r6)
            goto L_0x0270
        L_0x0253:
            java.lang.String r6 = "3Dperspective"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
            goto L_0x0270
        L_0x0262:
            java.lang.String r6 = "PlayEleEye"
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            boolean r7 = r7.booleanValue()
            boolean r4 = a(r6, r7)
        L_0x0270:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.wz.a(java.lang.String, java.lang.String):boolean");
    }

    private static boolean a(String str, boolean z) {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).sharedPrefs().edit().putBoolean(str, z).commit();
    }

    private static boolean a(String str, int i) {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).sharedPrefs().edit().putInt(str, i).commit();
    }

    public static boolean b(Command command) {
        return !TextUtils.equals("_FROG_CLEAR_PRIVACY_SETTING", command.a("key", "")) && !TextUtils.equals("LOTUSPOOL_LAUNCH_INTERVAL", command.a("key", "")) && !TextUtils.equals("LOTUSPOOL_APP_LAUNCH_INTERVAL", command.a("key", ""));
    }
}
