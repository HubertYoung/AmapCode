package defpackage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.LocalBroadcastManager;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.permission.PermissionModel;
import com.autonavi.map.permission.PermissionModel.PermissionGroup;
import com.autonavi.map.permission.SplashPrivacyPolicyDialog;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: buw reason: default package */
/* compiled from: PermissionManager */
public final class buw {
    Activity a;
    SplashPrivacyPolicyDialog b;
    private List<PermissionModel> c;
    private buu d;

    private static boolean a(int i) {
        return i == 100001 || i == 100002;
    }

    private buw(Activity activity, List<PermissionModel> list, buu buu) {
        this.a = activity;
        this.c = list;
        this.d = buu;
    }

    public static boolean a(Activity activity) {
        if (!c()) {
            return false;
        }
        if (VERSION.SDK_INT < 23) {
            return true;
        }
        if (activity.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 && activity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0 && activity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0 && activity.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 && activity.checkSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
            return true;
        }
        return false;
    }

    public static buw a(Activity activity, buu buu) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PermissionModel("android.permission.ACCESS_FINE_LOCATION", activity.getString(R.string.permission_explain_location), PermissionGroup.LOCATION));
        arrayList.add(new PermissionModel("android.permission.ACCESS_COARSE_LOCATION", activity.getString(R.string.permission_explain_location), PermissionGroup.LOCATION));
        arrayList.add(new PermissionModel("android.permission.WRITE_EXTERNAL_STORAGE", activity.getString(R.string.permission_explain_storage), PermissionGroup.STORAGE));
        arrayList.add(new PermissionModel("android.permission.READ_EXTERNAL_STORAGE", activity.getString(R.string.permission_explain_storage), PermissionGroup.STORAGE));
        arrayList.add(new PermissionModel("android.permission.READ_PHONE_STATE", activity.getString(R.string.permission_explain_phone_state), PermissionGroup.PHONE));
        if (g()) {
            arrayList.add(new PermissionModel("android.permission.RECORD_AUDIO", activity.getString(R.string.permission_explain_record_audio), PermissionGroup.MICROPHONE));
        }
        buw buw = new buw(activity, arrayList, buu);
        if (!c()) {
            cke.a((String) "1");
            buw.d();
        } else {
            buw.b();
        }
        return buw;
    }

    public final void a() {
        if (this.b != null && this.b.isShowing()) {
            this.b.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (VERSION.SDK_INT >= 23) {
            e();
            return;
        }
        if (this.d != null) {
            this.d.a();
        }
    }

    private static boolean c() {
        return new MapSharePreference((String) "SharedPreferences").sharedPrefs().getBoolean("privacy_agreed_flag", false);
    }

    private void d() {
        this.b = new SplashPrivacyPolicyDialog(this.a);
        this.b.setOnDismissListener(new OnDismissListener() {
            public final void onDismiss(DialogInterface dialogInterface) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("status", buw.this.a.getString(buw.this.b.a ? R.string.accpet_privacy : R.string.deny_privacy));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B242", jSONObject);
                if (buw.this.b.a) {
                    Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
                    String str = a.a().a;
                    if (str == null) {
                        str = "";
                    } else {
                        String[] split = str.split("\\.");
                        if (split != null && split.length >= 2) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(split[0]);
                            sb.append(".");
                            sb.append(split[1]);
                            str = sb.toString();
                        }
                    }
                    edit.putString("privacy_agreed_versioncode", str);
                    edit.putBoolean("privacy_agreed_flag", true);
                    edit.apply();
                    Editor edit2 = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
                    edit2.putBoolean("privacy_vui_voice_flag", true);
                    edit2.apply();
                    buw.this.b();
                    return;
                }
                buw.this.a.finish();
            }
        });
        if (VERSION.SDK_INT < 17 || !this.a.isDestroyed()) {
            this.b.show();
        }
    }

    @TargetApi(23)
    private void e() {
        if (this.c == null || this.c.size() == 0) {
            if (this.d != null) {
                this.d.a();
            }
            return;
        }
        ArrayList<PermissionModel> arrayList = new ArrayList<>();
        for (PermissionModel next : this.c) {
            if (this.a.checkSelfPermission(next.a) == 0) {
                arrayList.add(next);
            }
        }
        for (PermissionModel permissionModel : arrayList) {
            if (permissionModel != null) {
                this.c.remove(permissionModel);
            }
        }
        if (this.c.size() == 0) {
            if (this.d != null) {
                this.d.a();
            }
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        for (PermissionModel permissionModel2 : this.c) {
            arrayList2.add(permissionModel2.a);
        }
        this.a.requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 100001);
    }

    @TargetApi(23)
    private boolean f() {
        PermissionModel permissionModel;
        Iterator<PermissionModel> it = this.c.iterator();
        while (true) {
            if (!it.hasNext()) {
                permissionModel = null;
                break;
            }
            permissionModel = it.next();
            if (this.a.shouldShowRequestPermissionRationale(permissionModel.a)) {
                break;
            }
        }
        final ArrayList arrayList = new ArrayList();
        if (permissionModel != null) {
            arrayList.add(permissionModel);
            for (PermissionModel next : this.c) {
                if (next != permissionModel && next.c == permissionModel.c && this.a.shouldShowRequestPermissionRationale(next.a)) {
                    arrayList.add(next);
                }
            }
        }
        if (arrayList.size() > 0) {
            mi miVar = new mi(this.a);
            miVar.b = permissionModel.b;
            miVar.a(R.string.allow_this_permission, new a() {
                public final void a(mi miVar) {
                    miVar.a.dismiss();
                    ArrayList arrayList = new ArrayList();
                    for (PermissionModel permissionModel : arrayList) {
                        arrayList.add(permissionModel.a);
                    }
                    buw.this.a.requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 100002);
                }
            });
            miVar.c = false;
            miVar.a();
            miVar.b();
            return true;
        } else if (this.c.size() <= 0) {
            return false;
        } else {
            mi miVar2 = new mi(this.a);
            miVar2.b = this.c.get(0).b;
            miVar2.a(R.string.allow_this_permission, new a() {
                public final void a(mi miVar) {
                    miVar.a.dismiss();
                    buw buw = buw.this;
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", buw.a.getPackageName(), null));
                    buw.a.startActivity(intent);
                    buw.this.a.finish();
                }
            });
            miVar2.c = false;
            miVar2.a();
            miVar2.b();
            return true;
        }
    }

    @TargetApi(23)
    public final void a(int i, String[] strArr, int[] iArr) {
        if (a(i)) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = strArr[i2];
                int i3 = iArr[i2];
                a(str, i3);
                if (i3 == 0 || "android.permission.RECORD_AUDIO".equals(str)) {
                    this.c.remove(PermissionModel.a(this.c, str));
                }
                Application application = AMapAppGlobal.getApplication();
                if (application != null) {
                    Intent intent = new Intent(str);
                    intent.putExtra("grantResult", i3);
                    LocalBroadcastManager.getInstance(application).sendBroadcast(intent);
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("send broadcast,filter:");
                        sb.append(str);
                        sb.append(",result:");
                        sb.append(i3);
                        AMapLog.debug("paas.main", "PermissionManager", sb.toString());
                    }
                }
            }
            if (this.c.size() == 0) {
                if (this.d != null) {
                    this.d.a();
                }
            } else if (!f() && this.c.size() == 0 && this.d != null) {
                this.d.a();
            }
        }
    }

    private void a(String str, int i) {
        String str2 = ("android.permission.WRITE_EXTERNAL_STORAGE".equalsIgnoreCase(str) || "android.permission.READ_EXTERNAL_STORAGE".equalsIgnoreCase(str)) ? this.a.getString(R.string.permission_storage) : ("android.permission.ACCESS_FINE_LOCATION".equalsIgnoreCase(str) || "android.permission.ACCESS_COARSE_LOCATION".equalsIgnoreCase(str)) ? this.a.getString(R.string.permission_location) : "android.permission.READ_PHONE_STATE".equalsIgnoreCase(str) ? this.a.getString(R.string.permission_phone) : null;
        if (str2 != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str2);
                jSONObject.put("status", this.a.getString(i == 0 ? R.string.permission_action_accept : R.string.permission_action_deny));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B243", jSONObject);
        }
    }

    private static boolean g() {
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            return bfo.i();
        }
        return false;
    }
}
