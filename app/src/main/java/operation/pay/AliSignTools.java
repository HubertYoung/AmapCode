package operation.pay;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.R;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

public final class AliSignTools {
    public static final boolean i = bno.a;
    public ans<String> a;
    AosResponseCallbackOnUi b = new AosResponseCallbackOnUi<AosStringResponse>() {
        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            String str = (String) ((AosStringResponse) aosResponse).getResult();
            if (AliSignTools.this.j != null) {
                Activity activity = (Activity) AliSignTools.this.j.get();
                if (activity != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        int optInt = jSONObject.optInt("code");
                        boolean optBoolean = jSONObject.optBoolean("result");
                        String optString = jSONObject.optString("data");
                        if (optInt == 1 || optBoolean) {
                            if (!TextUtils.isEmpty(optString)) {
                                String optString2 = new JSONObject(optString).optString("url");
                                if (!TextUtils.isEmpty(optString2)) {
                                    activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("alipays://platformapi/startapp?appId=20000067&url=".concat(String.valueOf(URLEncoder.encode(optString2, "UTF-8"))))));
                                }
                            }
                            return;
                        }
                        AliSignTools.this.a(String.valueOf(optInt));
                    } catch (UnsupportedEncodingException | JSONException unused) {
                    } catch (ActivityNotFoundException unused2) {
                        AliSignTools.this.a((String) "103");
                    }
                }
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_msg));
        }
    };
    public AosResponseCallbackOnUi c = new AosResponseCallbackOnUi<AosStringResponse>() {
        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AliSignTools.this.a((String) ((AosStringResponse) aosResponse).getResult());
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_msg));
        }
    };
    public String d = "";
    AosResponseCallbackOnUi e = new AosResponseCallbackOnUi<AosStringResponse>() {
        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            String str;
            String str2 = (String) ((AosStringResponse) aosResponse).getResult();
            AliSignTools.a((String) "AliSignTools. mZhiMaConfCallback. onSuccess. result: %s ", str2);
            if (AliSignTools.this.j != null) {
                Activity activity = (Activity) AliSignTools.this.j.get();
                if (activity != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(str2);
                        int optInt = jSONObject.optInt("code");
                        boolean optBoolean = jSONObject.optBoolean("result");
                        String optString = jSONObject.optString("data");
                        if (optInt == 1 || optBoolean) {
                            if (!TextUtils.isEmpty(optString)) {
                                String optString2 = new JSONObject(optString).optString("scheme");
                                if (!TextUtils.isEmpty(optString2)) {
                                    IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                                    if (iAccountService == null) {
                                        str = "";
                                    } else {
                                        ant e = iAccountService.e();
                                        if (e == null) {
                                            str = "";
                                        } else {
                                            str = e.a;
                                        }
                                    }
                                    String l = Long.toString(System.currentTimeMillis());
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(str);
                                    sb.append(",");
                                    sb.append(l);
                                    sb.append(",");
                                    sb.append(AliSignTools.this.d);
                                    String format = String.format("&callBackUrl=amapuri://alipay/signzhimacallback?params=%s", new Object[]{sb});
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(optString2);
                                    sb2.append(format);
                                    Uri parse = Uri.parse("alipays://platformapi/startapp?appId=20000067&url=".concat(String.valueOf(URLEncoder.encode(sb2.toString(), "UTF-8"))));
                                    AliSignTools.a((String) "AliSignTools. mZhiMaConfCallback. onSuccess. uri: %s ", parse);
                                    activity.startActivity(new Intent("android.intent.action.VIEW", parse));
                                    MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                                    mapSharePreference.putStringValue("zhima_sign_tranid", l);
                                    mapSharePreference.putStringValue("zhima_sign_role", AliSignTools.this.d);
                                }
                            }
                            return;
                        }
                        AliSignTools.this.a(AliSignTools.a(String.valueOf(optInt), (String) null));
                    } catch (UnsupportedEncodingException | JSONException unused) {
                    } catch (ActivityNotFoundException unused2) {
                        AliSignTools.this.a(AliSignTools.a((String) "103", (String) null));
                    }
                }
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            AliSignTools.b("AliSignTools. mZhiMaConfCallback. onFailure ", new Object[0]);
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_msg));
        }
    };
    public AosResponseCallbackOnUi f = new AosResponseCallbackOnUi<AosStringResponse>() {
        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            String str = (String) ((AosStringResponse) aosResponse).getResult();
            AliSignTools.a((String) "AliSignTools. mAlipayConfCallback. onSuccess. result: %s ", str);
            if (AliSignTools.this.j != null) {
                Activity activity = (Activity) AliSignTools.this.j.get();
                if (activity != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        int optInt = jSONObject.optInt("code");
                        boolean optBoolean = jSONObject.optBoolean("result");
                        String optString = jSONObject.optString("data");
                        if (optInt == 1 || optBoolean) {
                            if (!TextUtils.isEmpty(optString)) {
                                String optString2 = new JSONObject(optString).optString("url");
                                if (!TextUtils.isEmpty(optString2)) {
                                    String l = Long.toString(System.currentTimeMillis());
                                    Uri parse = Uri.parse("alipays://platformapi/startapp?appId=20000067&url=".concat(String.valueOf(URLEncoder.encode(optString2, "UTF-8"))));
                                    AliSignTools.a((String) "AliSignTools. mAlipayConfCallback. onSuccess. uri: %s ", parse);
                                    activity.startActivity(new Intent("android.intent.action.VIEW", parse));
                                    MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                                    mapSharePreference.putStringValue("zhima_sign_tranid", l);
                                    mapSharePreference.putStringValue("zhima_sign_role", AliSignTools.this.d);
                                }
                            }
                            return;
                        }
                        AliSignTools.this.a(AliSignTools.b());
                    } catch (UnsupportedEncodingException | JSONException unused) {
                    } catch (ActivityNotFoundException unused2) {
                        AliSignTools.this.a(AliSignTools.b());
                    }
                }
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            AliSignTools.b("AliSignTools. mAlipayConfCallback. onFailure ", new Object[0]);
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_msg));
        }
    };
    public boolean g = false;
    AosResponseCallbackOnUi h = new AosResponseCallbackOnUi<AosStringResponse>() {
        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AliSignTools.this.g = false;
            String str = (String) ((AosStringResponse) aosResponse).getResult();
            AliSignTools.a((String) "AliSignTools. mZhiMaCheckCallback. onSuccess. result: %s ", str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                int optInt = jSONObject.optInt("code");
                AliSignTools.this.a(AliSignTools.a(Integer.toString(optInt), jSONObject.optString("data")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            AliSignTools.this.g = false;
            AliSignTools.b("AliSignTools. mZhiMaCheckCallback. onFailure ", new Object[0]);
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_msg));
        }
    };
    /* access modifiers changed from: private */
    public WeakReference<Activity> j;

    @KeepClassMembers
    @Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"tid", "product"}, url = "ws/pp/payment/bind/alipay/")
    @KeepImplementations
    @KeepName
    public class AliPaySignWrapper implements ParamEntity {
        String product = "1";
        String tid = NetworkParam.getTaobaoID();

        public AliPaySignWrapper() {
        }
    }

    @KeepClassMembers
    @Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"tid", "product"}, url = "ws/pp/payment/unbind/alipay/")
    @KeepImplementations
    @KeepName
    public class AliPayUnbindWrapper implements ParamEntity {
        String product = "1";
        String tid = NetworkParam.getTaobaoID();

        public AliPayUnbindWrapper() {
        }
    }

    @KeepClassMembers
    @Path(builder = AosURLBuilder.class, host = "aos_passport_url", sign = {"tid"}, url = "ws/pp/thirdcredit/alipay/conf/")
    @KeepImplementations
    @KeepName
    public class AlipayConfWrapper implements ParamEntity {
        public String tid = NetworkParam.getTaobaoID();

        public AlipayConfWrapper() {
        }
    }

    @KeepClassMembers
    @Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"tid", "adiu"}, url = "ws/travel/user/zhima/check/")
    @KeepImplementations
    @KeepName
    public class ZhiMaCheckWrapper implements ParamEntity {
        String auth_no = "";
        String role = "";
        String state = "";
        public String tid = NetworkParam.getTaobaoID();

        public ZhiMaCheckWrapper() {
        }
    }

    @KeepClassMembers
    @Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"tid", "adiu"}, url = "ws/travel/user/zhima/conf/")
    @KeepImplementations
    @KeepName
    public class ZhiMaConfWrapper implements ParamEntity {
        public ZhiMaConfWrapper() {
        }
    }

    public final void a(Activity activity) {
        this.j = new WeakReference<>(activity);
    }

    public final void a() {
        AosGetRequest a2 = aax.a(new AliPaySignWrapper());
        a2.addHeader("Cookie", abj.a().b());
        yq.a();
        yq.a((AosRequest) a2, (AosResponseCallback<T>) this.b);
    }

    public final void a(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            this.g = true;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("auth_code", str2);
                jSONObject.put("state", str3);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            a(jSONObject.toString());
        }
    }

    public static String a(String str, String str2) {
        Object obj;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", str);
            if (!TextUtils.isEmpty(str2)) {
                try {
                    obj = new JSONObject(str2);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    obj = null;
                }
                if (obj != null) {
                    jSONObject.put("data", obj);
                } else {
                    jSONObject.put("data", str2);
                }
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static String b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("auth_code", 103);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.a(str);
        }
    }

    public static void a(String str, Object... objArr) {
        if (i) {
            b(str, objArr);
        }
    }

    public static void b(String str, Object... objArr) {
        if (i) {
            try {
                String.format(str, objArr);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
