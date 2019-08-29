package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.alipay.mobile.scansdk.constant.Constants;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.photograph.LaunchOnlyCameraPage;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: dtg reason: default package */
/* compiled from: LaunchOnlyCameraPresenter */
public final class dtg extends AbstractBasePresenter<LaunchOnlyCameraPage> {
    public dtg(LaunchOnlyCameraPage launchOnlyCameraPage) {
        super(launchOnlyCameraPage);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        LaunchOnlyCameraPage launchOnlyCameraPage = (LaunchOnlyCameraPage) this.mPage;
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return false;
        }
        launchOnlyCameraPage.finish();
        return true;
    }

    public final void onPageCreated() {
        LaunchOnlyCameraPage launchOnlyCameraPage = (LaunchOnlyCameraPage) this.mPage;
        PageBundle arguments = launchOnlyCameraPage.getArguments();
        if (arguments != null) {
            launchOnlyCameraPage.c = (JSONObject) arguments.getObject("example");
            launchOnlyCameraPage.b = arguments.getString("_action");
            launchOnlyCameraPage.f = (Callback) arguments.getObject("callback");
            launchOnlyCameraPage.g = arguments.getString("businessName");
            launchOnlyCameraPage.h = arguments.getString(Constants.SERVICE_TITLE_TEXT);
            LaunchOnlyCameraPage.d = arguments.getString("returnType");
            launchOnlyCameraPage.a = true;
            String string = arguments.getString("maxLength");
            if (!TextUtils.isEmpty(string)) {
                int parseInt = Integer.parseInt(string);
                if (parseInt <= 10) {
                    launchOnlyCameraPage.i = 10;
                } else if (parseInt >= 2000) {
                    launchOnlyCameraPage.i = 2000;
                } else {
                    launchOnlyCameraPage.i = parseInt;
                }
            }
        }
        if (LaunchOnlyCameraPage.a()) {
            kj.a(launchOnlyCameraPage.getActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
                public final void run() {
                    LaunchOnlyCameraPage.b(LaunchOnlyCameraPage.this);
                }
            });
            return;
        }
        if (!LaunchOnlyCameraPage.a() && launchOnlyCameraPage != null) {
            Activity activity = launchOnlyCameraPage.getActivity();
            launchOnlyCameraPage.startAlertDialogPage(new Builder(activity).setTitle(R.string.real_scene_gps_tip).setPositiveButton(R.string.audio_guide_set_gps, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener(activity) {
                final /* synthetic */ Activity a;

                {
                    this.a = r2;
                }

                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    try {
                        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                        intent.setFlags(268435456);
                        this.a.startActivityForResult(intent, 4098);
                    } catch (ActivityNotFoundException unused) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.life_common_dlg_open_setting_failed));
                    } catch (SecurityException unused2) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.life_common_dlg_open_setting_failed));
                    }
                }
            }));
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        LaunchOnlyCameraPage launchOnlyCameraPage = (LaunchOnlyCameraPage) this.mPage;
        if (-1 == i2 && i == 4096) {
            launchOnlyCameraPage.finish();
            Map<String, Object> a = adu.a(intent);
            StringBuilder sb = new StringBuilder();
            sb.append(((Integer) a.get("shooted_orientation")).intValue());
            launchOnlyCameraPage.e = sb.toString();
            ahm.a(new Runnable((String) a.get("camera_pic_path"), launchOnlyCameraPage.i) {
                final /* synthetic */ String a;
                final /* synthetic */ int b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final void run() {
                    Bitmap a2 = dte.a(this.a, this.b);
                    StringBuilder sb = new StringBuilder("bmp.width = ");
                    sb.append(a2.getWidth());
                    sb.append(", bmp.height = ");
                    sb.append(a2.getHeight());
                    AMapLog.e("Aragorn", sb.toString());
                    Bitmap a3 = dte.a(a2, this.b);
                    if (a3 != a2) {
                        dte.a(a2);
                    }
                    StringBuilder sb2 = new StringBuilder("scaledBmp.width = ");
                    sb2.append(a3.getWidth());
                    sb2.append(", scaledBmp.height = ");
                    sb2.append(a3.getHeight());
                    AMapLog.e("Aragorn", sb2.toString());
                    LaunchOnlyCameraPage.this.k = dte.a(LaunchOnlyCameraPage.this.j, a3, dte.a(this.a));
                    StringBuilder sb3 = new StringBuilder("imagePath = ");
                    sb3.append(this.a);
                    AMapLog.e("Aragorn", sb3.toString());
                    StringBuilder sb4 = new StringBuilder("mTmpImagePath = ");
                    sb4.append(LaunchOnlyCameraPage.this.k);
                    AMapLog.e("Aragorn", sb4.toString());
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = a3.getWidth();
                    message.arg2 = a3.getHeight();
                    LaunchOnlyCameraPage.this.l.sendMessage(message);
                }
            });
        }
    }

    public final void onStart() {
        LaunchOnlyCameraPage launchOnlyCameraPage = (LaunchOnlyCameraPage) this.mPage;
        if (!launchOnlyCameraPage.a) {
            launchOnlyCameraPage.finish();
        }
    }

    public final void onStop() {
        ((LaunchOnlyCameraPage) this.mPage).a = false;
    }
}
