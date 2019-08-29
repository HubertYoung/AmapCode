package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.gdtaojin.basemap.CompatDialog;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aqy reason: default package */
/* compiled from: LocationCheckDialog */
public class aqy implements OnClickListener, ceh {
    private Activity a = null;
    private ImageView b = null;
    private TextView c = null;
    private TextView d = null;
    private TextView e = null;
    private CompatDialog f = null;
    private FrameLayout g = null;
    private boolean h = false;
    private boolean i = false;

    public final void a(Activity activity) {
        this.a = activity;
        this.f = new CompatDialog(activity, R.style.custom_dlg);
        this.f.setContentView(R.layout.v3_location_icon_dialog);
        this.c = (TextView) this.f.findViewById(R.id.tv_location_dialog_title);
        this.g = (FrameLayout) this.f.findViewById(R.id.frame_dialog_close);
        this.b = (ImageView) this.f.findViewById(R.id.img_loation_dialog);
        this.e = (TextView) this.f.findViewById(R.id.tv_location_dialog_warning);
        this.d = (TextView) this.f.findViewById(R.id.tv_location_dialog_turnon);
    }

    public final void a() {
        this.h = b();
        this.i = c();
        this.d.setOnClickListener(this);
        this.g.setOnClickListener(this);
        if (!this.h && this.i) {
            a(R.string.location_open_wifi_title, R.drawable.wifi_icon, a(this.a, R.string.location_wifi_content_one, R.string.location_all_content_two, R.string.location_wifi_content_three));
        } else if (this.h && !this.i) {
            a(R.string.location_open_gps_title, R.drawable.gps_icon, Html.fromHtml(this.a.getResources().getString(R.string.location_gps_content)));
        } else if (!this.h && !this.i) {
            a(R.string.location_open_all_title, R.drawable.gps_wifi_icon, a(this.a, R.string.location_all_content_one, R.string.location_all_content_two, R.string.location_all_content_three));
        }
        this.f.show();
    }

    public void onClick(View view) {
        if (view == this.d) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", 3);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (!this.h && this.i) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_WIFI_DIALOG, jSONObject);
            } else if (this.h && !this.i) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_GPS_DIALOG, jSONObject);
            } else if (!this.h && !this.i) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_GPS_AND_WIFI_DIALOG, jSONObject);
            }
            boolean b2 = b();
            boolean c2 = c();
            if (!b2 && c2) {
                try {
                    this.a.startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 12368);
                } catch (ActivityNotFoundException e3) {
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.error_fail_to_open_setting));
                    e3.printStackTrace();
                } catch (SecurityException e4) {
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getApplicationContext().getResources().getString(R.string.error_fail_to_open_setting));
                    e4.printStackTrace();
                }
            } else if (b2 && !c2) {
                this.a.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 12368);
            } else if (b2 || c2) {
                CharSequence text = this.c.getText();
                if (!TextUtils.isEmpty(text)) {
                    int i2 = R.string.location_gps_wlan_opend;
                    if (text.toString().equals(this.a.getString(R.string.location_open_wifi_title))) {
                        i2 = R.string.location_wlan_opend;
                    } else if (text.toString().equals(this.a.getString(R.string.location_open_gps_title))) {
                        i2 = R.string.location_gps_opend;
                    }
                    ToastHelper.showToast(this.a.getString(i2));
                }
            } else {
                this.a.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 12368);
            }
            this.f.dismiss();
            return;
        }
        if (view == this.g) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("type", 2);
            } catch (JSONException e5) {
                e5.printStackTrace();
            }
            if (!this.h && this.i) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_WIFI_DIALOG, jSONObject2);
            } else if (this.h && !this.i) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_GPS_DIALOG, jSONObject2);
            } else if (!this.h && !this.i) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_GPS_AND_WIFI_DIALOG, jSONObject2);
            }
            this.f.dismiss();
        }
    }

    private static Spanned a(Context context, int i2, int i3, int i4) {
        String string = context.getResources().getString(i2);
        String string2 = context.getResources().getString(i3);
        String string3 = context.getResources().getString(i4);
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append("<font color=\"#e63212\">");
        sb.append(string2);
        sb.append("</font>");
        sb.append(string3);
        return Html.fromHtml(sb.toString());
    }

    private void a(int i2, int i3, Spanned spanned) {
        if (this.c != null) {
            this.c.setText(i2);
        }
        if (this.b != null) {
            this.b.setImageResource(i3);
        }
        if (this.e != null) {
            this.e.setText(spanned);
        }
    }

    private static boolean b() {
        try {
            return ((WifiManager) AMapAppGlobal.getApplication().getApplicationContext().getSystemService("wifi")).isWifiEnabled();
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static boolean c() {
        try {
            return ((LocationManager) AMapAppGlobal.getApplication().getSystemService("location")).isProviderEnabled(WidgetType.GPS);
        } catch (SecurityException unused) {
            return false;
        }
    }
}
