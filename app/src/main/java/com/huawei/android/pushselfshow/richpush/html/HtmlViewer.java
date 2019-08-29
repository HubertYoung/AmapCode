package com.huawei.android.pushselfshow.richpush.html;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.huawei.android.pushagent.a.a.e;
import com.huawei.android.pushselfshow.richpush.html.api.ExposedJsApi;
import com.huawei.android.pushselfshow.richpush.tools.Console;
import com.huawei.android.pushselfshow.utils.b.b;
import com.huawei.android.pushselfshow.utils.c;
import com.huawei.android.pushselfshow.utils.c.a;
import com.huawei.android.pushselfshow.utils.d;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HtmlViewer implements a {
    public static final String TAG = "PushSelfShowLog";
    PageProgressView a;
    c b = new c(this);
    c c = new c(this);
    /* access modifiers changed from: private */
    public Activity d;
    public b dtl = null;
    /* access modifiers changed from: private */
    public WebView e;
    /* access modifiers changed from: private */
    public com.huawei.android.pushselfshow.richpush.tools.a f;
    /* access modifiers changed from: private */
    public com.huawei.android.pushselfshow.b.a g = null;
    private String h;
    private ExposedJsApi i;
    /* access modifiers changed from: private */
    public MenuItem j;
    /* access modifiers changed from: private */
    public MenuItem k;
    /* access modifiers changed from: private */
    public MenuItem l;
    private boolean m;
    private boolean n = false;
    /* access modifiers changed from: private */
    public boolean o = false;
    private AlertDialog p = null;
    private AlertDialog q = null;
    /* access modifiers changed from: private */
    public boolean r = false;

    private View a(Context context) {
        String str;
        String str2;
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(d.c(context, "hwpush_collect_tip_image"), null);
        try {
            Class<?> cls = Class.forName("huawei.android.widget.DialogContentHelper");
            Object newInstance = cls.getDeclaredConstructor(new Class[]{Boolean.TYPE, Boolean.TYPE, Context.class}).newInstance(new Object[]{Boolean.TRUE, Boolean.TRUE, context});
            Method declaredMethod = cls.getDeclaredMethod("beginLayout", new Class[0]);
            Method declaredMethod2 = cls.getDeclaredMethod("insertView", new Class[]{View.class, OnClickListener.class});
            Method declaredMethod3 = cls.getDeclaredMethod("insertBodyText", new Class[]{CharSequence.class});
            Method declaredMethod4 = cls.getDeclaredMethod("endLayout", new Class[0]);
            declaredMethod.invoke(newInstance, new Object[0]);
            declaredMethod2.invoke(newInstance, new Object[]{inflate, null});
            declaredMethod3.invoke(newInstance, new Object[]{this.d.getString(d.a(context, "hwpush_collect_tip"))});
            return (View) declaredMethod4.invoke(newInstance, new Object[0]);
        } catch (ClassNotFoundException unused) {
            com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "DialogContentHelper ClassNotFoundException");
            return null;
        } catch (SecurityException e2) {
            str2 = "PushSelfShowLog";
            str = e2.toString();
            r13 = e2;
            com.huawei.android.pushagent.a.a.c.c(str2, str, r13);
            return null;
        } catch (NoSuchMethodException e3) {
            str2 = "PushSelfShowLog";
            str = e3.toString();
            r13 = e3;
            com.huawei.android.pushagent.a.a.c.c(str2, str, r13);
            return null;
        } catch (IllegalArgumentException e4) {
            str2 = "PushSelfShowLog";
            str = e4.toString();
            r13 = e4;
            com.huawei.android.pushagent.a.a.c.c(str2, str, r13);
            return null;
        } catch (IllegalAccessException e5) {
            str2 = "PushSelfShowLog";
            str = e5.toString();
            r13 = e5;
            com.huawei.android.pushagent.a.a.c.c(str2, str, r13);
            return null;
        } catch (InvocationTargetException e6) {
            str2 = "PushSelfShowLog";
            str = e6.toString();
            r13 = e6;
            com.huawei.android.pushagent.a.a.c.c(str2, str, r13);
            return null;
        } catch (InstantiationException e7) {
            str2 = "PushSelfShowLog";
            str = e7.toString();
            r13 = e7;
            com.huawei.android.pushagent.a.a.c.c(str2, str, r13);
            return null;
        }
    }

    private void a() {
        this.e.getSettings().setJavaScriptEnabled(true);
        if (VERSION.SDK_INT >= 11 && VERSION.SDK_INT <= 16) {
            this.e.removeJavascriptInterface("searchBoxJavaBridge_");
            this.e.removeJavascriptInterface("accessibility");
            this.e.removeJavascriptInterface("accessibilityTraversal");
        }
        if (VERSION.SDK_INT <= 18) {
            this.e.getSettings().setSavePassword(false);
        }
        this.e.getSettings().setPluginState(PluginState.ON);
        this.e.getSettings().setLoadsImagesAutomatically(true);
        this.e.getSettings().setDomStorageEnabled(true);
        this.e.getSettings().setSupportZoom(true);
        this.e.setScrollBarStyle(0);
        this.e.setHorizontalScrollBarEnabled(false);
        this.e.setVerticalScrollBarEnabled(false);
        this.e.getSettings().setSupportMultipleWindows(true);
        this.e.setDownloadListener(new a(this));
        this.e.setOnTouchListener(new c(this));
        this.e.setWebChromeClient(new d(this));
        this.e.setWebViewClient(new e(this));
    }

    /* access modifiers changed from: private */
    public void a(Activity activity) {
        if (activity != null) {
            this.l.setEnabled(false);
            this.r = true;
            if (VERSION.SDK_INT < 23 || com.huawei.android.pushselfshow.utils.a.e(activity) || !com.huawei.android.pushselfshow.utils.a.f(activity) || activity.checkSelfPermission("com.huawei.pushagent.permission.RICHMEDIA_PROVIDER") == 0) {
                new Thread(new h(this, activity)).start();
            } else {
                a(new String[]{"com.huawei.pushagent.permission.RICHMEDIA_PROVIDER"}, 10003);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        ActionBar actionBar = this.d.getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(str);
        }
    }

    private void a(String[] strArr, int i2) {
        try {
            Intent intent = new Intent("huawei.intent.action.REQUEST_PERMISSIONS");
            intent.setPackage("com.huawei.systemmanager");
            intent.putExtra("KEY_HW_PERMISSION_ARRAY", strArr);
            intent.putExtra("KEY_HW_PERMISSION_PKG", this.d.getPackageName());
            if (com.huawei.android.pushselfshow.utils.a.a((Context) this.d, (String) "com.huawei.systemmanager", intent).booleanValue()) {
                try {
                    com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "checkAndRequestPermission: systemmanager permission activity is exist");
                    this.d.startActivityForResult(intent, i2);
                } catch (Exception e2) {
                    com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "checkAndRequestPermission: Exception", e2);
                    this.d.requestPermissions(strArr, i2);
                }
            } else {
                com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "checkAndRequestPermission: systemmanager permission activity is not exist");
                this.d.requestPermissions(strArr, i2);
            }
        } catch (Exception e3) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", e3.toString(), e3);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0048  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int b(android.app.Activity r6) {
        /*
            r5 = this;
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url order by pushmsg._id desc limit 1000;"
            r2 = 0
            com.huawei.android.pushselfshow.richpush.a.b r3 = com.huawei.android.pushselfshow.richpush.a.b.a()     // Catch:{ Exception -> 0x0027 }
            android.net.Uri r4 = com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a.f     // Catch:{ Exception -> 0x0027 }
            android.database.Cursor r6 = r3.a(r6, r4, r1, r2)     // Catch:{ Exception -> 0x0027 }
            if (r6 == 0) goto L_0x001f
            int r1 = r6.getCount()     // Catch:{ Exception -> 0x001c, all -> 0x0019 }
            r0 = r1
            goto L_0x001f
        L_0x0019:
            r0 = move-exception
            r2 = r6
            goto L_0x0046
        L_0x001c:
            r1 = move-exception
            r2 = r6
            goto L_0x0028
        L_0x001f:
            if (r6 == 0) goto L_0x0036
            r6.close()
            goto L_0x0036
        L_0x0025:
            r0 = move-exception
            goto L_0x0046
        L_0x0027:
            r1 = move-exception
        L_0x0028:
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.String r3 = r1.toString()     // Catch:{ all -> 0x0025 }
            com.huawei.android.pushagent.a.a.c.c(r6, r3, r1)     // Catch:{ all -> 0x0025 }
            if (r2 == 0) goto L_0x0036
            r2.close()
        L_0x0036:
            java.lang.String r6 = "PushSelfShowLog"
            java.lang.String r1 = "currentExistCount:"
            java.lang.String r2 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r2)
            com.huawei.android.pushagent.a.a.c.a(r6, r1)
            return r0
        L_0x0046:
            if (r2 == 0) goto L_0x004b
            r2.close()
        L_0x004b:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.HtmlViewer.b(android.app.Activity):int");
    }

    private void c(Activity activity) {
        this.q = new Builder(activity, com.huawei.android.pushselfshow.utils.a.h(activity)).setTitle(d.a(activity, "hwpush_dialog_limit_title")).setMessage(d.a(activity, "hwpush_dialog_limit_message")).setNegativeButton(17039360, null).setPositiveButton(d.a(activity, "hwpush_dialog_limit_ok"), new j(this)).setOnDismissListener(new i(this, activity)).create();
        this.q.show();
    }

    /* access modifiers changed from: private */
    public void d(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent.putExtra("type", "favorite");
            if (this.g != null) {
                intent.putExtra("selfshow_info", this.g.c());
                intent.putExtra("selfshow_token", this.g.d());
            }
            intent.setFlags(268468240);
            intent.putExtra("selfshowMsgOutOfBound", true);
            intent.setPackage(activity.getPackageName());
            activity.finish();
            activity.startActivity(intent);
        }
    }

    public void downLoadFailed() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "downLoadFailed:");
        this.c = null;
        showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a((Context) this.d, (String) "富媒体文件下载失败", (String) "Failed to load the message."));
    }

    public void downLoadSuccess(String str) {
        try {
            StringBuilder sb = new StringBuilder("downLoadSuccess:");
            sb.append(str);
            sb.append("，and start loadLocalZip");
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
            loadLocalZip(str);
        } catch (Exception e2) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "downLoadSuccess failed", e2);
        }
    }

    public void enableJavaJS(String str) {
        ExposedJsApi exposedJsApi;
        try {
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "enable JavaJs support and indexFileUrl is ".concat(String.valueOf(str)));
            String str2 = null;
            if (str != null) {
                str2 = str.substring(0, str.lastIndexOf("/"));
            }
            StringBuilder sb = new StringBuilder("m_activity is ");
            sb.append(this.d);
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
            StringBuilder sb2 = new StringBuilder("webView is ");
            sb2.append(this.e);
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb2.toString());
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "localPath is ".concat(String.valueOf(str2)));
            if (this.g.H != 0) {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "pushmsg.needUserId true");
                exposedJsApi = new ExposedJsApi(this.d, this.e, str2, true);
            } else {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "pushmsg.needUserId false");
                exposedJsApi = new ExposedJsApi(this.d, this.e, str2, false);
            }
            this.i = exposedJsApi;
            this.e.addJavascriptInterface(new Console(), "console");
            this.e.addJavascriptInterface(this.i, "_nativeApi");
        } catch (Exception e2) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "enable JavaJs support failed ", e2);
        }
    }

    public void handleMessage(Message message) {
        StringBuilder sb = new StringBuilder("handleMessage ");
        sb.append(message.what);
        sb.append(",");
        sb.append(message.toString());
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
        int i2 = message.what;
        if (i2 != 1000) {
            switch (i2) {
                case 1:
                    downLoadSuccess((String) message.obj);
                    return;
                case 2:
                    downLoadFailed();
                    return;
                case 3:
                    return;
                default:
                    return;
            }
        } else {
            c(this.d);
        }
    }

    public void loadLocalZip(String str) {
        if (str != null && str.length() > 0) {
            this.h = com.huawei.android.pushselfshow.richpush.tools.d.a(this.d, str);
            if (this.h == null || this.h.length() <= 0) {
                com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "check index.html file failed");
                this.c = null;
            } else {
                Uri fromFile = Uri.fromFile(new File(this.h));
                enableJavaJS(this.h);
                this.g.D = fromFile.toString();
                this.g.F = "text/html_local";
                this.f.a(this.g);
                this.e.loadUrl(fromFile.toString());
                return;
            }
        }
        showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a((Context) this.d, (String) "富媒体内容不正确", (String) "Invalid content."));
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        try {
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "run HtmlViewer onActivityResult");
            if (this.i != null) {
                this.i.onActivityResult(i2, i3, intent);
            }
            if (10003 == i2) {
                if (i3 == 0) {
                    com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "onActivityResult: RESULT_CANCELED");
                    this.l.setEnabled(true);
                    this.r = false;
                } else if (-1 == i3) {
                    com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "onActivityResult: RESULT_OK");
                    if (this.d.checkSelfPermission("com.huawei.pushagent.permission.RICHMEDIA_PROVIDER") == 0) {
                        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "onActivityResult: Permission is granted");
                        new Thread(new f(this)).start();
                        return;
                    }
                    this.l.setEnabled(true);
                    this.r = false;
                }
            } else if (10004 == i2) {
                if (!(this.g == null || this.c == null)) {
                    this.dtl = new b(this.c, this.d, this.g.D, com.huawei.android.pushselfshow.richpush.tools.b.a("application/zip"));
                    this.dtl.b();
                }
            } else if (10005 == i2 && this.g != null) {
                loadLocalZip(this.g.D);
            }
        } catch (Exception e2) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", e2.toString(), e2);
        }
    }

    public void onCreate(Intent intent) {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "HtmlViewer onCreate");
        if (intent == null) {
            com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "onCreate, intent is null");
            return;
        }
        try {
            this.m = intent.getBooleanExtra("selfshow_from_list", false);
            this.r = intent.getBooleanExtra("collect_img_disable", false);
            StringBuilder sb = new StringBuilder("mCollectImgDisable:");
            sb.append(this.r);
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
            this.f = new com.huawei.android.pushselfshow.richpush.tools.a(this.d);
            ActionBar actionBar = this.d.getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            this.d.setRequestedOrientation(5);
            RelativeLayout relativeLayout = new RelativeLayout(this.d);
            String str = null;
            RelativeLayout relativeLayout2 = (RelativeLayout) this.d.getLayoutInflater().inflate(d.c(this.d, "hwpush_msg_show"), null);
            relativeLayout.addView(relativeLayout2);
            this.d.setContentView(relativeLayout);
            this.a = (PageProgressView) relativeLayout2.findViewById(d.e(this.d, "hwpush_progressbar"));
            this.e = (WebView) relativeLayout2.findViewById(d.e(this.d, "hwpush_msg_show_view"));
            a();
            if (intent.hasExtra("selfshow_info")) {
                this.g = new com.huawei.android.pushselfshow.b.a(intent.getByteArrayExtra("selfshow_info"), intent.getByteArrayExtra("selfshow_token"));
                if (!this.g.b()) {
                    com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "parseMessage failed");
                    return;
                }
                StringBuilder sb2 = new StringBuilder("pushmsg.rpct:");
                sb2.append(this.g.F);
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb2.toString());
                this.f.a(this.g);
            } else {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "pushmsg is null");
                showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a((Context) this.d, (String) "富媒体内容不正确", (String) "Invalid content."));
            }
            if (this.g != null) {
                StringBuilder sb3 = new StringBuilder("fileurl :");
                sb3.append(this.g.D);
                sb3.append(", the pushmsg is ");
                sb3.append(this.g.toString());
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb3.toString());
            } else {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "pushmsg is null :");
                this.g = new com.huawei.android.pushselfshow.b.a();
            }
            StringBuilder sb4 = new StringBuilder("pushmsg.rpct:");
            sb4.append(this.g.F);
            com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", sb4.toString());
            if ("application/zip".equals(this.g.F)) {
                if (-1 == com.huawei.android.pushagent.a.a.a.a((Context) this.d)) {
                    com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "no network. can not load message");
                    return;
                }
                this.dtl = new b(this.c, this.d, this.g.D, com.huawei.android.pushselfshow.richpush.tools.b.a("application/zip"));
                this.dtl.b();
            } else if ("application/zip_local".equals(this.g.F)) {
                loadLocalZip(this.g.D);
            } else {
                if (!"text/html".equals(this.g.F)) {
                    if (!"text/html_local".equals(this.g.F)) {
                        showErrorHtmlURI(com.huawei.android.pushselfshow.utils.a.a((Context) this.d, (String) "富媒体内容不正确", (String) "Invalid content."));
                        return;
                    }
                }
                if ("text/html_local".equals(this.g.F)) {
                    str = this.g.D;
                }
                enableJavaJS(str);
                this.e.loadUrl(this.g.D);
            }
        } catch (RuntimeException e2) {
            StringBuilder sb5 = new StringBuilder("call");
            sb5.append(HtmlViewer.class.getName());
            sb5.append(" onCreate(Intent intent) err: ");
            sb5.append(e2.toString());
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", sb5.toString(), e2);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "HtmlViewer onCreateOptionsMenu:".concat(String.valueOf(menu)));
        this.d.getMenuInflater().inflate(d.d(this.d, "hwpush_msg_show_menu"), menu);
        return true;
    }

    public void onDestroy() {
        try {
            if (this.p != null && this.p.isShowing()) {
                this.p.dismiss();
            }
            if (this.q != null && this.q.isShowing()) {
                this.q.dismiss();
            }
            if (this.i != null) {
                this.i.onDestroy();
            }
            if (this.h != null && !this.o) {
                String substring = this.h.substring(0, this.h.lastIndexOf("/"));
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "try to remove dir ".concat(String.valueOf(substring)));
                com.huawei.android.pushselfshow.utils.a.a(new File(substring));
            }
            if (this.dtl != null && this.dtl.e) {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "cancel ProgressDialog loading dialog when richpush file is downloading");
                this.dtl.a();
                this.c = null;
            }
            this.e.stopLoading();
            this.e = null;
        } catch (Exception | IndexOutOfBoundsException unused) {
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "remove unsuccess ,maybe removed before");
        }
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 == 4 && keyEvent.getAction() == 0) {
            if (this.m) {
                Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
                intent.putExtra("type", "favorite");
                intent.setPackage(this.d.getPackageName());
                this.d.finish();
                this.d.startActivity(intent);
            } else {
                this.d.finish();
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "HtmlViewer onOptionsItemSelected:".concat(String.valueOf(menuItem)));
        if (menuItem == null) {
            com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "onOptionsItemSelected, item is null");
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onKeyDown(4, new KeyEvent(0, 4));
            return true;
        }
        if (itemId == d.e(this.d, "hwpush_menu_back")) {
            if (this.e != null && this.e.canGoBack()) {
                StringBuilder sb = new StringBuilder("can go back ");
                sb.append(this.e.canGoBack());
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb.toString());
                this.e.goBack();
                return true;
            }
        } else if (itemId == d.e(this.d, "hwpush_menu_forward")) {
            if (this.e != null && this.e.canGoForward()) {
                StringBuilder sb2 = new StringBuilder(" can Go Forward ");
                sb2.append(this.e.canGoForward());
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", sb2.toString());
                this.e.goForward();
                return true;
            }
        } else if (itemId == d.e(this.d, "hwpush_menu_refresh")) {
            setProgress(0);
            this.e.reload();
            return true;
        } else if (itemId == d.e(this.d, "hwpush_menu_collect")) {
            e eVar = new e(this.d, "push_client_self_info");
            if (!eVar.c("isFirstCollect")) {
                this.p = new Builder(this.d).setPositiveButton(d.a(this.d, "hwpush_collect_tip_known"), new b(this, eVar)).create();
                if (com.huawei.android.pushagent.a.a.a.a() > 9) {
                    View a2 = a((Context) this.d);
                    if (a2 != null) {
                        this.p.setView(a2);
                        this.p.show();
                        return true;
                    }
                }
                this.p.setTitle(d.a(this.d, "hwpush_msg_collect"));
                this.p.setMessage(this.d.getString(d.a(this.d, "hwpush_collect_tip")));
                this.p.show();
                return true;
            }
            a(this.d);
        }
        return true;
    }

    public void onPause() {
        if (this.i != null) {
            this.i.onPause();
        }
        try {
            this.e.getClass().getMethod("onPause", new Class[0]).invoke(this.e, null);
        } catch (Exception e2) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "htmlviewer onpause error", e2);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "HtmlViewer onPrepareOptionsMenu:".concat(String.valueOf(menu)));
        this.j = menu.findItem(d.e(this.d, "hwpush_menu_back"));
        this.k = menu.findItem(d.e(this.d, "hwpush_menu_forward"));
        this.l = menu.findItem(d.e(this.d, "hwpush_menu_collect"));
        if (this.e != null) {
            if (this.e.canGoBack()) {
                this.j.setEnabled(true);
            } else {
                this.j.setEnabled(false);
            }
            if (this.e.canGoForward()) {
                this.k.setEnabled(true);
            } else {
                this.k.setEnabled(false);
            }
        }
        if (this.m || this.r) {
            this.l.setEnabled(false);
            this.r = true;
        }
        return true;
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "enter HtmlViewer onRequestPermissionsResult");
        if (10003 != i2) {
            if (10004 == i2) {
                if (!(this.g == null || this.c == null)) {
                    this.dtl = new b(this.c, this.d, this.g.D, com.huawei.android.pushselfshow.richpush.tools.b.a("application/zip"));
                    this.dtl.b();
                }
            } else if (10005 == i2 && this.g != null) {
                loadLocalZip(this.g.D);
            }
        } else if (iArr == null || iArr.length <= 0 || iArr[0] != 0) {
            this.l.setEnabled(true);
            this.r = false;
        } else {
            new Thread(new g(this)).start();
        }
    }

    public void onResume() {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "HtmlViewer onResume");
        if (this.i != null) {
            this.i.onResume();
        }
        try {
            this.e.getClass().getMethod("onResume", new Class[0]).invoke(this.e, null);
        } catch (Exception e2) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "htmlviewer onResume error", e2);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("collect_img_disable", this.r);
    }

    public void onStop() {
        if (this.i != null) {
            this.i.onPause();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0100 A[Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0149 A[Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x016b A[Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0192 A[Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String prepareJS(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r0.<init>()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            android.app.Activity r1 = r9.d     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r1 = com.huawei.android.pushselfshow.utils.b.b.b(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r0.append(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r1 = java.io.File.separator     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r0.append(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            android.app.Activity r1 = r9.d     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r2 = "."
            java.lang.String r3 = ""
            java.lang.String r1 = r1.replace(r2, r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r0.append(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.io.File r1 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r1.<init>(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            boolean r1 = r1.exists()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r1 != 0) goto L_0x0045
            java.io.File r1 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r1.<init>(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            boolean r1 = r1.mkdirs()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r1 == 0) goto L_0x0045
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "mkdir true"
            com.huawei.android.pushagent.a.a.c.e(r1, r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
        L_0x0045:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "prepareJS fileHeader is "
            java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushagent.a.a.c.e(r1, r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.<init>()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.append(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = java.io.File.separator     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.append(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = "newpush.js"
            r2.append(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.io.File r3 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r3.<init>(r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            boolean r4 = r3.exists()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r5 = -1
            if (r4 == 0) goto L_0x00ce
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            long r3 = r3.lastModified()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r8 = 0
            long r6 = r6 - r3
            r3 = 1300000000(0x4d7c6d00, double:6.422853396E-315)
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x00c1
            java.lang.String r3 = "PushSelfShowLog"
            java.lang.String r4 = "new push.js may be out of date ,or try to update"
            com.huawei.android.pushagent.a.a.c.a(r3, r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            android.app.Activity r3 = r9.d     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            int r3 = com.huawei.android.pushagent.a.a.a.a(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r3 == r5) goto L_0x00fe
            com.huawei.android.pushselfshow.utils.b.b r3 = new com.huawei.android.pushselfshow.utils.b.b     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r3.<init>()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            android.app.Activity r4 = r9.d     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r5 = "http://open.hicloud.com/android/push1.0.js"
            boolean r3 = r3.b(r4, r5, r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r3 == 0) goto L_0x00fe
            java.io.File r3 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r3.<init>(r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            boolean r3 = r3.exists()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r3 == 0) goto L_0x00fe
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = "prepareJS dlUtil.downLoadSgThread  pushUrl is "
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
        L_0x00bd:
            com.huawei.android.pushagent.a.a.c.e(r1, r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            goto L_0x00fd
        L_0x00c1:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = "prepareJS  not arrival update  pushUrl is "
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            goto L_0x00bd
        L_0x00ce:
            android.app.Activity r3 = r9.d     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            int r3 = com.huawei.android.pushagent.a.a.a.a(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r3 == r5) goto L_0x00fe
            com.huawei.android.pushselfshow.utils.b.b r3 = new com.huawei.android.pushselfshow.utils.b.b     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r3.<init>()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            android.app.Activity r4 = r9.d     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r5 = "http://open.hicloud.com/android/push1.0.js"
            boolean r3 = r3.b(r4, r5, r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r3 == 0) goto L_0x00fe
            java.io.File r3 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r3.<init>(r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            boolean r3 = r3.exists()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r3 == 0) goto L_0x00fe
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r3 = "prepareJS new js isnot exist, so  downloaded  pushUrl is "
            java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            goto L_0x00bd
        L_0x00fd:
            r1 = r2
        L_0x00fe:
            if (r1 == 0) goto L_0x010a
            int r2 = r1.length()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r2 != 0) goto L_0x0107
            goto L_0x010a
        L_0x0107:
            r0 = r1
            goto L_0x018c
        L_0x010a:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "  pushUrl is "
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushagent.a.a.c.e(r2, r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.<init>()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.append(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r0 = java.io.File.separator     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.append(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r0 = "push1.0.js"
            r2.append(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r0 = r2.toString()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "  pushjsPath is "
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushagent.a.a.c.e(r2, r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.io.File r2 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.<init>(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            boolean r2 = r2.exists()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r2 == 0) goto L_0x016b
            java.io.File r2 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.<init>(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            boolean r2 = r2.delete()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r2 == 0) goto L_0x015b
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "delete pushjsPath success"
            com.huawei.android.pushagent.a.a.c.a(r2, r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
        L_0x015b:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "prepareJS new js  is not prepared so use local  pushUrl is "
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r1 = r3.concat(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushagent.a.a.c.e(r2, r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            goto L_0x0172
        L_0x016b:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = " new File(pushjsPath) not exists() "
            com.huawei.android.pushagent.a.a.c.e(r1, r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
        L_0x0172:
            android.app.Activity r1 = r9.d     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = "pushresources"
            r2.<init>(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = java.io.File.separator     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.append(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r3 = "push1.0.js"
            r2.append(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushselfshow.utils.a.b(r1, r2, r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
        L_0x018c:
            int r1 = r0.length()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            if (r1 <= 0) goto L_0x01f5
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.String r2 = "  pushUrl is "
            java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushagent.a.a.c.e(r1, r2)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r1 = "/"
            int r1 = r0.lastIndexOf(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r1 = r0.substring(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.<init>()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r3 = 7
            java.lang.String r4 = "/"
            int r4 = r10.lastIndexOf(r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r10 = r10.substring(r3, r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.append(r10)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.append(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r10 = r2.toString()     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = " pushUrlName is %s,destPath is %s "
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r5 = 0
            r4[r5] = r1     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r5 = 1
            r4[r5] = r10     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushagent.a.a.c.a(r2, r3, r4)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.io.File r2 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r2.<init>(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.io.File r0 = new java.io.File     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            r0.<init>(r10)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            com.huawei.android.pushselfshow.utils.a.a(r2, r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r10 = "."
            java.lang.String r0 = java.lang.String.valueOf(r1)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            java.lang.String r10 = r10.concat(r0)     // Catch:{ Exception | IndexOutOfBoundsException -> 0x01ed }
            return r10
        L_0x01ed:
            r10 = move-exception
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.String r1 = "prepareJS"
            com.huawei.android.pushagent.a.a.c.d(r0, r1, r10)
        L_0x01f5:
            java.lang.String r10 = "http://open.hicloud.com/android/push1.0.js"
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.HtmlViewer.prepareJS(java.lang.String):java.lang.String");
    }

    public void setActivity(Activity activity) {
        this.d = activity;
    }

    public void setProgress(int i2) {
        if (i2 >= 100) {
            this.a.a(10000);
            this.a.setVisibility(4);
            this.n = false;
            return;
        }
        if (!this.n) {
            this.a.setVisibility(0);
            this.n = true;
        }
        this.a.a((i2 * 10000) / 100);
    }

    public void showErrorHtmlURI(String str) {
        Activity activity;
        String str2;
        try {
            String a2 = new com.huawei.android.pushselfshow.richpush.tools.c(this.d, str).a();
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "showErrorHtmlURI,filePath is ".concat(String.valueOf(a2)));
            if (a2 != null && a2.length() > 0) {
                Uri fromFile = Uri.fromFile(new File(a2));
                enableJavaJS(null);
                this.e.loadUrl(fromFile.toString());
            }
        } catch (Exception e2) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "showErrorHtmlURI failed", e2);
        }
        if (com.huawei.android.pushselfshow.utils.a.a((Context) this.d, (String) "富媒体文件下载失败", (String) "Failed to load the message.").equals(str)) {
            activity = this.d;
            str2 = "12";
        } else {
            activity = this.d;
            str2 = "6";
        }
        com.huawei.android.pushselfshow.utils.a.a((Context) activity, str2, this.g);
    }
}
