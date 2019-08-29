package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.sina.weibo.sdk.statistic.LogBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dmn reason: default package */
/* compiled from: DiscountSubscribeAction */
public class dmn extends vz {
    private static long a(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime() / 1000;
        } catch (ParseException e) {
            kf.a((Throwable) e);
            return 0;
        }
    }

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString("id");
            int optInt = jSONObject.optInt("state");
            new JSONObject();
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("_action", waVar.b);
            avu avu = (avu) a.a.a(avu.class);
            if (avu != null) {
                avt a2 = avu.a();
                if (optInt == 0) {
                    if (!TextUtils.isEmpty(optString)) {
                        if (a2.a(optString)) {
                            jSONObject2.put("result", "1");
                        } else {
                            jSONObject2.put("result", "0");
                        }
                    }
                } else if (1 == optInt) {
                    avx avx = new avx();
                    avx.a = optJSONObject.optString("discount_gd_id");
                    avx.b = Long.valueOf(a(optJSONObject.optString(LogBuilder.KEY_START_TIME)));
                    avx.c = Long.valueOf(a(optJSONObject.optString(LogBuilder.KEY_END_TIME)));
                    avx.d = Long.valueOf(new Date().getTime() / 1000);
                    avx.e = optJSONObject.toString();
                    try {
                        JSONObject jSONObject3 = new JSONObject(avx.e);
                        avx.f = jSONObject3.optString("discount_title");
                        avx.g = jSONObject3.optString("discount_desc");
                        avx.h = jSONObject3.optString("discount_used");
                        avx.i = jSONObject3.optString("carddetails");
                        avx.j = jSONObject3.optString("discount_details");
                        try {
                            avx.k = ((JSONObject) jSONObject3.optJSONArray("pic_info").get(0)).optString("url");
                        } catch (JSONException e) {
                            kf.a((Throwable) e);
                        }
                        avx.l = jSONObject3.optString("src_type");
                        avx.m = jSONObject3.optString("discount_id");
                        avx.n = jSONObject3.optString("usetype");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    if (a2.a(avx)) {
                        jSONObject2.put("result", "1");
                        SharedPreferences sharedPreferences = DoNotUseTool.getActivity().getSharedPreferences("isFristCollect", 0);
                        if (sharedPreferences.getBoolean("isFristCollect", true)) {
                            ToastHelper.showLongToast(a.mPageContext.getActivity().getResources().getString(R.string.frist_collect));
                            Editor edit = sharedPreferences.edit();
                            edit.putBoolean("isFristCollect", false);
                            edit.commit();
                        } else {
                            ToastHelper.showLongToast(a.mPageContext.getActivity().getResources().getString(R.string.save_success));
                        }
                    } else {
                        jSONObject2.put("result", "0");
                    }
                } else if (2 == optInt) {
                    if (a2.b(optString)) {
                        jSONObject2.put("result", "1");
                        ToastHelper.showLongToast(a.mPageContext.getActivity().getResources().getString(R.string.del_success1));
                    } else {
                        jSONObject2.put("result", "0");
                    }
                }
                a.callJs(waVar.a, jSONObject2.toString());
            }
        }
    }
}
