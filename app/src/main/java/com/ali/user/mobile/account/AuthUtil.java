package com.ali.user.mobile.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.ali.user.mobile.account.bean.CellIdInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.AliUserLogUtil;
import com.alipay.android.phone.inside.common.util.MD5Util;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.model.CdmaModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.TelephoneInfo;
import java.util.List;
import org.json.JSONObject;

public class AuthUtil {
    public static void a(Context context, String str, boolean z) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.alipay.android.phone.wallet.accountauth", 0);
            if (sharedPreferences != null && !TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("UserAutoLogin");
                String a = MD5Util.a(sb.toString());
                if (!TextUtils.isEmpty(a)) {
                    sharedPreferences.edit().putInt(a, z ? 1 : 0).apply();
                    AliUserLogUtil.c("AuthUtil", String.format("setCurrentAutoLoginState = %s", new Object[]{Boolean.valueOf(z)}));
                }
            }
        } catch (Exception e) {
            AliUserLogUtil.a("AuthUtil", "setCurrentAutoLoginState", e);
        }
    }

    private static String a(CellIdInfo cellIdInfo) {
        if (cellIdInfo == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mcc", cellIdInfo.getMcc());
            jSONObject.put("mnc", cellIdInfo.getMnc());
            jSONObject.put("lac", cellIdInfo.getLac());
            jSONObject.put("cid", cellIdInfo.getCid());
            jSONObject.put("type", cellIdInfo.getType());
            return jSONObject.toString();
        } catch (Throwable th) {
            AliUserLog.a("AuthUtil", "cellIdInfoToJsonString error", th);
            return "";
        }
    }

    private static CellIdInfo a(CdmaModel cdmaModel) {
        try {
            CellIdInfo cellIdInfo = new CellIdInfo();
            if (cdmaModel == null) {
                AliUserLogUtil.a((String) "AuthUtil", (String) "CdmaCellLocation is null!!!");
                return null;
            }
            int i = -1;
            cellIdInfo.setCid(TextUtils.isEmpty(cdmaModel.a()) ? -1 : Integer.valueOf(cdmaModel.a()).intValue());
            cellIdInfo.setMcc(-1);
            cellIdInfo.setMnc(TextUtils.isEmpty(cdmaModel.d()) ? -1 : Integer.valueOf(cdmaModel.d()).intValue());
            if (!TextUtils.isEmpty(cdmaModel.b())) {
                i = Integer.valueOf(cdmaModel.b()).intValue();
            }
            cellIdInfo.setLac(i);
            cellIdInfo.setType("cdma");
            return cellIdInfo;
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append(th.getMessage());
            AliUserLogUtil.b("AuthUtil", sb.toString());
            return null;
        }
    }

    private static CellIdInfo a(List<GsmModel> list) {
        try {
            CellIdInfo cellIdInfo = new CellIdInfo();
            GsmModel gsmModel = list.get(0);
            if (gsmModel == null) {
                AliUserLogUtil.a((String) "AuthUtil", (String) "GsmCellLocation is null!!!");
                return null;
            }
            int i = -1;
            cellIdInfo.setCid(TextUtils.isEmpty(gsmModel.c()) ? -1 : Integer.valueOf(gsmModel.c()).intValue());
            cellIdInfo.setMcc(TextUtils.isEmpty(gsmModel.b()) ? -1 : Integer.valueOf(gsmModel.b()).intValue());
            cellIdInfo.setMnc(TextUtils.isEmpty(gsmModel.a()) ? -1 : Integer.valueOf(gsmModel.a()).intValue());
            if (!TextUtils.isEmpty(gsmModel.d())) {
                i = Integer.valueOf(gsmModel.d()).intValue();
            }
            cellIdInfo.setLac(i);
            cellIdInfo.setType("gsm");
            return cellIdInfo;
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append(th.getMessage());
            AliUserLogUtil.b("AuthUtil", sb.toString());
            return null;
        }
    }

    public static void a(String str, String str2) {
        String str3;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("-StackTrace:");
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(" ### ");
                sb.append(stackTraceElement.toString());
            }
            str3 = sb.toString();
        } else {
            str3 = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[Thread:");
        sb2.append(Thread.currentThread().getId());
        sb2.append("] ");
        sb2.append(str3);
        AliUserLogUtil.c(str, sb2.toString());
        AliUserLogUtil.a(str3);
    }

    public static String a() {
        CellIdInfo cellIdInfo;
        try {
            TelephoneInfo e = OutsideConfig.e();
            if (e == null) {
                cellIdInfo = null;
            } else {
                List<GsmModel> a = e.a();
                CdmaModel b = e.b();
                CellIdInfo cellIdInfo2 = new CellIdInfo();
                if (a != null && !a.isEmpty()) {
                    cellIdInfo = a(a);
                } else if (b != null) {
                    cellIdInfo = a(b);
                } else {
                    cellIdInfo = cellIdInfo2;
                }
            }
            StringBuilder sb = new StringBuilder("cellIdInfoToJsonString: ");
            sb.append(a(cellIdInfo));
            AliUserLog.c("AuthUtil", sb.toString());
            return a(cellIdInfo);
        } catch (Throwable th) {
            AliUserLogUtil.a((String) "AuthUtil", th);
            return "";
        }
    }
}
