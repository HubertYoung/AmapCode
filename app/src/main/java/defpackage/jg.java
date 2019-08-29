package defpackage;

import android.app.Activity;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.view.Window;
import android.widget.RemoteViews;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.HashMap;

/* renamed from: jg reason: default package */
/* compiled from: AppDownloadManager */
public final class jg {
    private static jg b = new jg();
    public HashMap<String, jk> a = new HashMap<>();

    private jg() {
    }

    public static jg a() {
        return b;
    }

    /* JADX INFO: finally extract failed */
    public final synchronized jk a(String str, String str2, String str3, int i, boolean z, int i2, Activity activity, js jsVar) {
        jk jkVar;
        String str4 = str;
        int i3 = i2;
        synchronized (this) {
            try {
                jk jkVar2 = this.a.get(str4);
                if (jkVar2 == null) {
                    jkVar = new jk(str4, str2, str3, i, z, activity, jsVar);
                    this.a.put(str4, jkVar);
                } else {
                    jkVar = jkVar2;
                }
                jkVar.j = i3;
                if (i3 != 0) {
                    if (i3 == 1) {
                        Activity activity2 = (Activity) jkVar.h.get();
                        if (activity2 != null) {
                            jl jlVar = new jl(activity2, true ^ jkVar.c);
                            jlVar.setOnCancelListener(new OnCancelListener() {
                                public final void onCancel(DialogInterface dialogInterface) {
                                    String str;
                                    jk.this.j = 2;
                                    jf jfVar = new jf(jk.this.a, jk.this.b);
                                    int i = jk.this.e;
                                    if (!(jfVar.b == null || jfVar.a == null)) {
                                        int i2 = R.drawable.downapp;
                                        if (i < 100) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(jfVar.a);
                                            sb.append(AMapAppGlobal.getApplication().getString(R.string.app_download_start_download));
                                            str = sb.toString();
                                        } else {
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(jfVar.a);
                                            sb2.append(AMapAppGlobal.getApplication().getString(R.string.app_download_finish_download));
                                            str = sb2.toString();
                                        }
                                        jfVar.d = new Builder(jfVar.b).setSmallIcon(i2).setTicker(str).setWhen(System.currentTimeMillis());
                                        ky.a(jfVar.d, NotificationChannelIds.d);
                                        if (VERSION.SDK_INT >= 16) {
                                            jfVar.c = jfVar.d.build();
                                        } else {
                                            jfVar.c = jfVar.d.getNotification();
                                        }
                                        jfVar.c.flags = 2;
                                        RemoteViews remoteViews = new RemoteViews(jfVar.b.getPackageName(), R.layout.download_notification_layout);
                                        int i3 = R.id.appname;
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append(jfVar.a);
                                        sb3.append(AMapAppGlobal.getApplication().getString(R.string.app_download_downloading));
                                        remoteViews.setTextViewText(i3, sb3.toString());
                                        jfVar.c.contentView = remoteViews;
                                        RemoteViews remoteViews2 = jfVar.c.contentView;
                                        int i4 = R.id.progress_txt;
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append(i);
                                        sb4.append("%");
                                        remoteViews2.setTextViewText(i4, sb4.toString());
                                        jfVar.c.contentView.setProgressBar(R.id.progressbar, 100, i, false);
                                        jfVar.c.contentIntent = PendingIntent.getActivity(jfVar.b, jfVar.e, new Intent("com.autonavi.minimap.ACTION"), 134217728);
                                        ((NotificationManager) jfVar.b.getSystemService("notification")).notify(jfVar.e, jfVar.c);
                                    }
                                    jk.this.g.a();
                                    jk.this.g = jfVar;
                                }
                            });
                            if (jkVar.g != null) {
                                jkVar.g.c();
                            }
                            jkVar.g = jlVar;
                            if (!activity2.isFinishing()) {
                                jlVar.show();
                                Window window = jlVar.getWindow();
                                if (window != null) {
                                    window.setLayout(activity2.getResources().getDimensionPixelOffset(R.dimen.update_downloader_dialog_width), -2);
                                }
                            }
                        }
                    } else if (i3 == 2) {
                        jkVar.g = new jf(jkVar.a, jkVar.b);
                    }
                }
                if (!jkVar.b()) {
                    jkVar.a();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return jkVar;
    }

    public final void b() {
        if (this.a != null) {
            for (jk next : this.a.values()) {
                if (!next.b() && !next.d) {
                    next.a();
                }
            }
        }
    }

    public final boolean c() {
        if (this.a == null) {
            return false;
        }
        for (jk b2 : this.a.values()) {
            if (b2.b()) {
                return true;
            }
        }
        return false;
    }
}
