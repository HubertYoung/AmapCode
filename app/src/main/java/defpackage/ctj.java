package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.activities.ActivitiesService$1;
import com.autonavi.minimap.oss.OssRequestHolder;
import com.autonavi.minimap.oss.param.OperationInfoRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(ctl.class)
/* renamed from: ctj reason: default package */
/* compiled from: ActivitiesService */
public class ctj extends esi implements ctl {
    private final String a = "operations_activities_type";
    private final MapSharePreference b = new MapSharePreference((String) "OperationsActivities");
    private Map<String, AosRequest> c = new HashMap();
    private agk<vy> e = new agk<>();

    public final void a(String str, Callback<ctm> callback) {
        if (bny.a) {
            try {
                if (new JSONObject(bny.c).optBoolean(WidgetType.ACTIVITY)) {
                    return;
                }
            } catch (Throwable unused) {
            }
        }
        if (!TextUtils.isEmpty(str)) {
            if (!a()) {
                a(callback, 2);
                return;
            }
            ArrayList<String> c2 = c(str);
            if (c2.size() <= 0) {
                a(callback, -1);
                return;
            }
            StringBuilder sb = new StringBuilder();
            boolean z = false;
            Iterator<String> it = c2.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (z) {
                    sb.append(",".concat(String.valueOf(next)));
                } else {
                    sb.append(next);
                    z = true;
                }
            }
            b(str);
            OperationInfoRequest operationInfoRequest = new OperationInfoRequest();
            operationInfoRequest.d = sb.toString();
            OssRequestHolder.getInstance().sendOperationInfo(operationInfoRequest, new ActivitiesService$1(this, callback, str));
            this.c.put(str, operationInfoRequest);
        }
    }

    private void b(String str) {
        AosRequest aosRequest = this.c.get(str);
        if (aosRequest != null && !aosRequest.isCanceled()) {
            aosRequest.cancel();
            this.c.remove(str);
        }
    }

    public final void a(AbstractBasePage abstractBasePage, String str, String str2) {
        if (a(str, str2) && this.c.get(str) != null) {
            Intent intent = new Intent();
            Uri parse = Uri.parse(str2);
            intent.setData(parse);
            if (abstractBasePage != null && abstractBasePage.isStarted()) {
                if (parse.toString().contains("webview/transparent")) {
                    String queryParameter = parse.getQueryParameter("url");
                    if (TextUtils.isEmpty(queryParameter)) {
                        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_open_web_fail));
                        return;
                    }
                    aix aix = (aix) a.a.a(aix.class);
                    if (aix != null) {
                        this.e.a(aix.a((bid) abstractBasePage, queryParameter));
                    }
                } else {
                    abstractBasePage.startScheme(intent);
                }
            }
        }
        d(str);
    }

    public final void a(String str) {
        b(str);
        int a2 = this.e.a();
        int i = 0;
        while (i < a2) {
            agk<vy> agk = this.e;
            agk.b();
            vy vyVar = (vy) ((i < 0 || i >= agk.a()) ? null : agk.a.get(i).get());
            if (vyVar != null) {
                vyVar.a();
            }
            i++;
        }
    }

    private static boolean a() {
        if (bny.a) {
            try {
                if (new JSONObject(bny.c).optBoolean(WidgetType.ACTIVITY)) {
                    return false;
                }
            } catch (Throwable unused) {
            }
        }
        lu luVar = lt.a().d;
        if (luVar.d != null) {
            return luVar.d.booleanValue();
        }
        return false;
    }

    private ArrayList<String> c(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (String add : str.split(",")) {
            arrayList.add(add);
        }
        if (c()) {
            return arrayList;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            String str2 = arrayList.get(i);
            String stringValue = this.b.getStringValue("operations_activities_type".concat(String.valueOf(str2)), "");
            if (stringValue.isEmpty()) {
                arrayList2.add(str2);
            } else {
                try {
                    JSONObject jSONObject = new JSONObject(stringValue);
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append("click");
                    int optInt = jSONObject.optInt(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append(H5RpcFailResult.LIMIT);
                    int i2 = jSONObject.getInt(sb2.toString());
                    if (optInt < i2 || i2 == 0) {
                        arrayList2.add(str2);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return arrayList2;
    }

    private void d(String str) {
        ArrayList<String> c2 = c(str);
        for (int i = 0; i < c2.size(); i++) {
            String str2 = c2.get(i);
            String stringValue = this.b.getStringValue("operations_activities_type".concat(String.valueOf(str2)), "");
            if (!stringValue.isEmpty()) {
                try {
                    JSONObject jSONObject = new JSONObject(stringValue);
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append("click");
                    int optInt = jSONObject.optInt(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append("click");
                    jSONObject.put(sb2.toString(), optInt + 1);
                    this.b.putStringValue("operations_activities_type".concat(String.valueOf(str2)), jSONObject.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean a(String str, String str2) {
        ArrayList<String> c2 = c(str);
        if (c2.size() > 0) {
            for (int i = 0; i < c2.size(); i++) {
                String str3 = c2.get(i);
                String stringValue = this.b.getStringValue("operations_activities_type".concat(String.valueOf(str3)), "");
                if (!stringValue.isEmpty() && !TextUtils.isEmpty(str2)) {
                    try {
                        JSONObject jSONObject = new JSONObject(stringValue);
                        StringBuilder sb = new StringBuilder();
                        sb.append(str3);
                        sb.append("click");
                        int optInt = jSONObject.optInt(sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str3);
                        sb2.append(H5RpcFailResult.LIMIT);
                        int i2 = jSONObject.getInt(sb2.toString());
                        if (optInt < i2 || i2 == 0) {
                            return true;
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static void a(Callback<ctm> callback, int i) {
        ctm ctm = new ctm();
        if (i == -1) {
            ctm.a = -1;
        } else if (i != 2) {
            ctm.a = 0;
        } else {
            ctm.a = 2;
        }
        callback.callback(ctm);
    }

    /* access modifiers changed from: private */
    public ctm a(JSONObject jSONObject, String str) {
        try {
            String optString = jSONObject.optString("code");
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            String optString2 = optJSONObject.optString("action");
            int optInt = optJSONObject.optInt("activity_flag");
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("rule");
            if (!TextUtils.isEmpty(optString) && optString.equals("1") && !TextUtils.isEmpty(optString2)) {
                if (optJSONObject2 != null) {
                    String[] split = str.split(",");
                    ArrayList arrayList = new ArrayList();
                    for (String add : split) {
                        arrayList.add(add);
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        String str2 = (String) it.next();
                        try {
                            String optString3 = optJSONObject2.optString(str2);
                            if (!optString3.isEmpty()) {
                                int parseInt = Integer.parseInt(optString3);
                                String stringValue = this.b.getStringValue("operations_activities_type".concat(String.valueOf(str2)), "");
                                if (stringValue.isEmpty()) {
                                    JSONObject jSONObject2 = new JSONObject();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(str2);
                                    sb.append(H5RpcFailResult.LIMIT);
                                    jSONObject2.put(sb.toString(), parseInt);
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(str2);
                                    sb2.append("click");
                                    jSONObject2.put(sb2.toString(), 0);
                                    this.b.putStringValue("operations_activities_type".concat(String.valueOf(str2)), jSONObject2.toString());
                                } else {
                                    JSONObject jSONObject3 = new JSONObject(stringValue);
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(str2);
                                    sb3.append(H5RpcFailResult.LIMIT);
                                    if (parseInt != jSONObject3.optInt(sb3.toString())) {
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append(str2);
                                        sb4.append(H5RpcFailResult.LIMIT);
                                        jSONObject3.put(sb4.toString(), parseInt);
                                        this.b.putStringValue("operations_activities_type".concat(String.valueOf(str2)), jSONObject3.toString());
                                    }
                                }
                            }
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                    ctm ctm = new ctm();
                    ctm.c = optString2;
                    ctm.b = optInt;
                    ctm.a = 1;
                    ctm.d = jSONObject == null ? "" : jSONObject.toString();
                    return ctm;
                }
            }
            return b();
        } catch (Exception unused) {
            return b();
        }
    }

    private static ctm b() {
        ctm ctm = new ctm();
        ctm.a = 0;
        return ctm;
    }

    private boolean c() {
        try {
            int intValue = this.b.getIntValue("operations_activities_show_date", -1);
            int parseInt = Integer.parseInt(String.format("%02d", new Object[]{Integer.valueOf(Calendar.getInstance(Locale.CHINA).get(6))}));
            if (intValue < 0) {
                this.b.putIntValue("operations_activities_show_date", parseInt);
                return true;
            }
            if (parseInt != intValue) {
                this.b.edit().clear().apply();
                this.b.putIntValue("operations_activities_show_date", parseInt);
                return true;
            }
            return false;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
        }
    }
}
