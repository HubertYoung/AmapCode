package defpackage;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.security.zim.api.ZIMCallback;
import com.alipay.mobile.security.zim.api.ZIMFacade;
import com.alipay.mobile.security.zim.api.ZIMFacadeBuilder;
import com.alipay.mobile.security.zim.api.ZIMResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: anu reason: default package */
/* compiled from: AMapFaceRecognitionManager */
public class anu {
    private static volatile boolean a = false;

    /* renamed from: anu$a */
    /* compiled from: AMapFaceRecognitionManager */
    public interface a {
    }

    public static void a(Context context) {
        if (!a) {
            synchronized (anu.class) {
                if (!a) {
                    LauncherApplicationAgent.getInstance();
                    ZIMFacade.install(context);
                    a = true;
                }
            }
        }
    }

    public static String a() {
        Application application = AMapAppGlobal.getApplication();
        if (application == null) {
            AMapLog.error("basemap.account", "FaceRecognitionManager", " application is null");
            return "";
        }
        a(application);
        return ZIMFacade.getMetaInfos(application);
    }

    public static void a(String str, String str2, ZIMCallback zIMCallback) {
        if (TextUtils.isEmpty(str)) {
            AMapLog.error("basemap.account", "FaceRecognitionManager", "非法参数：请检查参数是否正确，第一个参数错误");
            ZIMResponse zIMResponse = new ZIMResponse();
            zIMResponse.code = -4;
            zIMResponse.reason = "非法参数：请检查参数是否正确，第一个参数错误";
            zIMCallback.response(zIMResponse);
        } else if (AMapAppGlobal.getTopActivity() == null) {
            AMapLog.error("basemap.account", "FaceRecognitionManager", "展示人脸识别界面失败，没有找到当前的页面");
            ZIMResponse zIMResponse2 = new ZIMResponse();
            zIMResponse2.code = -4;
            zIMResponse2.reason = "展示人脸识别界面失败，没有找到当前页面";
            zIMCallback.response(zIMResponse2);
        } else {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("zimID:");
                sb.append(str);
                sb.append(",extParams:");
                sb.append(str2);
                AMapLog.debug("basemap.account", "FaceRecognitionManager", sb.toString());
            }
            ZIMFacade create = ZIMFacadeBuilder.create(AMapAppGlobal.getTopActivity());
            HashMap hashMap = null;
            if (!TextUtils.isEmpty(str2)) {
                hashMap = new HashMap();
                a(str2, hashMap);
            }
            create.verify(str, hashMap, zIMCallback);
        }
    }

    private static void a(String str, Map<String, String> map) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                map.put(next, jSONObject.optString(next, ""));
            }
        } catch (JSONException e) {
            StringBuilder sb = new StringBuilder("buildParamMap error:");
            sb.append(Log.getStackTraceString(e));
            AMapLog.error("basemap.account", "FaceRecognitionManager", sb.toString());
        }
    }
}
