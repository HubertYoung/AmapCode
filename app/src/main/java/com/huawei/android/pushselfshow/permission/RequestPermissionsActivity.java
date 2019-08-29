package com.huawei.android.pushselfshow.permission;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.SelfShowReceiver;
import com.huawei.android.pushselfshow.utils.a;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestPermissionsActivity extends Activity {
    private static final String[] a = {"android.permission.READ_PHONE_STATE"};
    private static List c = new ArrayList();
    private static boolean d = false;
    private String[] b = new String[0];

    public static void a(Context context, Intent intent) {
        c.b("PushSelfShowLog", "enter startPermissionActivity");
        if (context != null) {
            Intent intent2 = new Intent(context.getApplicationContext(), RequestPermissionsActivity.class);
            intent2.addFlags(276824064);
            if (intent != null) {
                intent2.putExtra("previous_intent", intent);
            }
            try {
                context.startActivity(intent2);
            } catch (ActivityNotFoundException e) {
                c.c("PushSelfShowLog", e.toString(), e);
            }
        }
    }

    public static boolean a(Context context) {
        return VERSION.SDK_INT >= 23 && !a(context, a);
    }

    protected static boolean a(Context context, String[] strArr) {
        if (context == null || strArr == null || strArr.length == 0) {
            return false;
        }
        for (String str : strArr) {
            if (context.checkSelfPermission(str) != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" need request");
                c.a("PushSelfShowLog", sb.toString());
                return false;
            }
        }
        return true;
    }

    private boolean a(String str) {
        return Arrays.asList(a()).contains(str);
    }

    private boolean a(String[] strArr) {
        int i = 0;
        while (i < strArr.length) {
            if (checkSelfPermission(strArr[i]) == 0 || !a(strArr[i])) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder("request permissions failed:");
                sb.append(strArr[i]);
                c.b("PushSelfShowLog", sb.toString());
                return false;
            }
        }
        c.b("PushSelfShowLog", "request all permissions success:");
        return true;
    }

    private boolean a(String[] strArr, int[] iArr) {
        int i = 0;
        while (i < strArr.length) {
            if (iArr[i] == 0 || !a(strArr[i])) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder("request permissions failed:");
                sb.append(strArr[i]);
                c.a("PushSelfShowLog", sb.toString());
                return false;
            }
        }
        c.a("PushSelfShowLog", "request all permissions success:");
        return true;
    }

    private void b(String[] strArr) {
        if (d) {
            c.b("PushSelfShowLog", "has Start PermissionActivity, do nothing");
            finish();
            return;
        }
        d = true;
        try {
            Intent intent = new Intent("huawei.intent.action.REQUEST_PERMISSIONS");
            intent.setPackage("com.huawei.systemmanager");
            intent.putExtra("KEY_HW_PERMISSION_ARRAY", strArr);
            intent.putExtra("KEY_HW_PERMISSION_PKG", getPackageName());
            if (a.a((Context) this, (String) "com.huawei.systemmanager", intent).booleanValue()) {
                try {
                    c.b("PushSelfShowLog", "checkAndRequestPermission: systemmanager permission activity is exist");
                    startActivityForResult(intent, 1357);
                } catch (Exception e) {
                    c.c("PushSelfShowLog", "checkAndRequestPermission: Exception", e);
                    requestPermissions(strArr, 1357);
                }
            } else {
                c.b("PushSelfShowLog", "checkAndRequestPermission: systemmanager permission activity is not exist");
                requestPermissions(strArr, 1357);
            }
        } catch (Exception e2) {
            c.c("PushSelfShowLog", e2.toString(), e2);
        }
    }

    private void c() {
        String[] b2;
        ArrayList arrayList = new ArrayList();
        for (String str : b()) {
            if (checkSelfPermission(str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.size() != 0) {
            this.b = (String[]) arrayList.toArray(new String[arrayList.size()]);
            b(this.b);
            return;
        }
        c.a("PushSelfShowLog", "unsatisfiedPermissions size is 0, finish");
        finish();
    }

    /* access modifiers changed from: protected */
    public String[] a() {
        return a;
    }

    /* access modifiers changed from: protected */
    public String[] b() {
        return a;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (1357 == i) {
            if (i2 == 0) {
                try {
                    c.b("PushSelfShowLog", "onActivityResult: RESULT_CANCELED");
                } catch (Exception e) {
                    c.c("PushSelfShowLog", e.toString(), e);
                    return;
                }
            } else if (-1 == i2) {
                c.b("PushSelfShowLog", "onActivityResult: RESULT_OK");
                if (!(this.b == null || this.b.length == 0 || !a(this.b))) {
                    c.b("PushSelfShowLog", "onActivityResult: Permission is granted");
                    StringBuilder sb = new StringBuilder("mCacheIntents size: ");
                    sb.append(c.size());
                    c.b("PushSelfShowLog", sb.toString());
                    for (Intent onReceive : c) {
                        new SelfShowReceiver().onReceive(this, onReceive);
                    }
                }
            }
        }
        d = false;
        c.clear();
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String str;
        String str2;
        super.onCreate(bundle);
        c.a((Context) this);
        c.b("PushSelfShowLog", "enter RequestPermissionsActivity onCreate");
        requestWindowFeature(1);
        Intent intent = getIntent();
        if (intent == null) {
            str = "PushSelfShowLog";
            str2 = "enter RequestPermissionsActivity onCreate, intent is null, finish";
        } else if (VERSION.SDK_INT < 23) {
            str = "PushSelfShowLog";
            str2 = "enter RequestPermissionsActivity onCreate, SDK version is less than 23, finish";
        } else {
            try {
                if (intent.getExtras() != null) {
                    Intent intent2 = (Intent) intent.getExtras().get("previous_intent");
                    StringBuilder sb = new StringBuilder("mCacheIntents size is ");
                    sb.append(c.size());
                    c.a("PushSelfShowLog", sb.toString());
                    if (c.size() >= 30) {
                        c.remove(0);
                    }
                    c.add(intent2);
                }
            } catch (Exception e) {
                c.c("PushSelfShowLog", e.toString(), e);
            }
            c.a("PushSelfShowLog", "savedInstanceState is ".concat(String.valueOf(bundle)));
            if (bundle == null) {
                c();
            }
            return;
        }
        c.b(str, str2);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        c.a("PushSelfShowLog", "enter RequestPermissionsActivity onDestroy");
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        c.a("PushSelfShowLog", "enter RequestPermissionsActivity onNewIntent");
        super.onNewIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        c.a("PushSelfShowLog", "RequestPermissionsActivity onPause");
        super.onPause();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        c.b("PushSelfShowLog", "RequestPermissionsActivity onRequestPermissionsResult");
        if (1357 == i && strArr != null && strArr.length > 0 && iArr != null && iArr.length > 0 && a(strArr, iArr)) {
            for (Intent onReceive : c) {
                new SelfShowReceiver().onReceive(this, onReceive);
            }
        }
        d = false;
        c.clear();
        finish();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        c.a("PushSelfShowLog", "RequestPermissionsActivity onStop");
        super.onStop();
    }
}
