package defpackage;

import android.widget.ImageView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.activity.IActivityEventDelegate;
import com.autonavi.bundle.uitemplate.mapwidget.widget.activity.IActivityEventDelegate.LoadGifImgCallback;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ary reason: default package */
/* compiled from: ActivityEventDelegate */
public final class ary implements IActivityEventDelegate {
    public final void executeMsgAction(AmapMessage amapMessage) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", amapMessage.id);
            jSONObject.put("time", dbf.a());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_AD_ENTRANCE_CLICK, jSONObject);
        MessageBoxManager.getInstance().executeBtnAction(amapMessage.actionUri);
        if (amapMessage.isADMsg()) {
            MessageBoxManager.getInstance().reportDisplayLogIgnoreError(amapMessage.id, 3, 2);
        }
    }

    public final void loadGifImg(ImageView imageView, String str, final LoadGifImgCallback loadGifImgCallback) {
        dau.a(imageView, str, (a) new a() {
            public final void a(final File file) {
                if (loadGifImgCallback != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            loadGifImgCallback.onSuccess(file);
                        }
                    });
                }
            }

            public final void a() {
                if (loadGifImgCallback != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            loadGifImgCallback.onFail();
                        }
                    });
                }
            }
        });
    }

    public final void reportActivityShowLog(AmapMessage amapMessage) {
        if (amapMessage.isADMsg()) {
            MessageBoxManager.getInstance().reportDisplayLog(amapMessage.id, 3, 1);
        }
    }
}
