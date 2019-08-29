package defpackage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioFocusRequest.Builder;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.cloudsync.widget.PhoneListDialogAdapter;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.PhoneUtil$1;
import com.autonavi.common.utils.PhoneUtil$3;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.uc.webview.export.WebView;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* renamed from: bnz reason: default package */
/* compiled from: PhoneUtil */
public final class bnz {
    static AudioManager a;
    static OnAudioFocusChangeListener b;
    static AudioFocusRequest c;
    private static final Pattern d = Pattern.compile("^(1)\\d{10}$");

    public static void a(List<String> list, Activity activity, String str) {
        a(null, list, activity, str);
    }

    public static void a(final POI poi, List<String> list, Activity activity, final String str) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                String str2 = list.get(0);
                if (str2.contains("$")) {
                    str2 = str2.substring(str2.lastIndexOf("$") + 1);
                }
                a(str2);
                return;
            }
            final bje bje = new bje(activity);
            bje.a((ListAdapter) new PhoneListDialogAdapter(list, activity));
            bje.a((OnItemClickListener) new PhoneUtil$1(bje, activity, str, poi));
            bje.a((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    if (str.equals(LogConstant.SEARCH_POI_DETAIL)) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            if (poi != null) {
                                jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
                                jSONObject.put("type", poi.getType());
                                jSONObject.put("name", poi.getName());
                            }
                            LogManager.actionLogV2(str, "B002", jSONObject);
                        } catch (Exception unused) {
                        }
                    }
                    bje.dismiss();
                }
            });
            bje.show();
        }
    }

    public static void a(ArrayList<String> arrayList, Activity activity) {
        if (arrayList.size() > 0) {
            if (arrayList.size() == 1) {
                String str = arrayList.get(0);
                if (str.contains("$")) {
                    str = str.substring(str.lastIndexOf("$") + 1);
                }
                a(str);
                return;
            }
            final bje bje = new bje(activity);
            bje.a((ListAdapter) new PhoneListDialogAdapter(arrayList, activity));
            bje.a((OnItemClickListener) new PhoneUtil$3(bje, activity));
            bje.a((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    bje.dismiss();
                }
            });
            bje.show();
        }
    }

    public static void a(String str) {
        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(WebView.SCHEME_TEL.concat(String.valueOf(str))));
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity != null) {
            try {
                topActivity.startActivity(intent);
            } catch (Exception unused) {
            }
        }
    }

    public static void a(Context context, String str, String str2) {
        int simState = ((TelephonyManager) context.getSystemService("phone")).getSimState();
        if (simState == 0) {
            ToastHelper.showToast(context.getResources().getString(R.string.msg_message_unknow));
        } else if (simState == 1) {
            ToastHelper.showToast(context.getResources().getString(R.string.tel_message_absent));
        } else {
            if (simState == 5) {
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:".concat(String.valueOf(str))));
                intent.putExtra("sms_body", str2);
                Activity topActivity = AMapAppGlobal.getTopActivity();
                if (topActivity != null) {
                    topActivity.startActivity(intent);
                }
            }
        }
    }

    public static void a(Context context, String str) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (Exception unused) {
        }
    }

    public static boolean a() {
        if (a == null) {
            a = (AudioManager) AMapAppGlobal.getApplication().getSystemService("audio");
        }
        if (a == null) {
            return false;
        }
        return a.isMusicActive();
    }

    public static boolean a(Context context) {
        return a(context, true);
    }

    @TargetApi(8)
    public static boolean a(Context context, boolean z) {
        if (context == null) {
            return false;
        }
        try {
            if (a == null) {
                a = (AudioManager) context.getSystemService("audio");
            }
            if (a == null) {
                return false;
            }
            b = new OnAudioFocusChangeListener() {
                public final void onAudioFocusChange(int i) {
                }
            };
            int i = 3;
            if (VERSION.SDK_INT >= 26) {
                if (!z) {
                    i = 2;
                }
                c = new Builder(i).setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setAcceptsDelayedFocusGain(true).setOnAudioFocusChangeListener(b).build();
                a.requestAudioFocus(c);
            } else if (z) {
                a.requestAudioFocus(b, 3, 3);
            } else {
                a.requestAudioFocus(b, 3, 2);
            }
            return true;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return false;
        }
    }

    @TargetApi(8)
    public static boolean b(Context context) {
        if (context == null) {
            return false;
        }
        try {
            if (a == null) {
                a = (AudioManager) context.getSystemService("audio");
            }
            if (a == null) {
                return false;
            }
            if (VERSION.SDK_INT >= 26) {
                if (c != null) {
                    a.abandonAudioFocusRequest(c);
                }
                c = null;
            } else if (b != null) {
                a.abandonAudioFocus(b);
            }
            b = null;
            return true;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return false;
        }
    }

    public static boolean b(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return false;
        }
        return d.matcher(str).matches();
    }

    public static boolean b() {
        return c(AMapAppGlobal.getApplication()) == 100;
    }

    public static int c(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        boolean z = false;
        if (locationManager == null) {
            try {
                return R.string.start_navi_msg;
            } catch (Exception unused) {
            }
        } else {
            List<String> allProviders = locationManager.getAllProviders();
            boolean contains = allProviders != null ? allProviders.contains(WidgetType.GPS) : false;
            if (contains && VERSION.SDK_INT >= 19) {
                int i = Secure.getInt(context.getContentResolver(), "location_mode", 0);
                if (i == 0) {
                    return R.string.start_navi_msg;
                }
                if (!(i == 3 || i == 1)) {
                    return R.string.start_navi_msg_gpsmode;
                }
            }
            if (!locationManager.isProviderEnabled(WidgetType.GPS)) {
                return R.string.start_navi_msg;
            }
            z = contains;
            if (!z) {
                return R.string.start_navi_msg;
            }
            return 100;
        }
    }
}
