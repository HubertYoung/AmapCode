package com.amap.location.offline.e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.amap.location.common.d.a;
import java.util.Calendar;

/* compiled from: SharedPrefUtil */
public class c {
    private static SharedPreferences a = null;
    private static int b = -1;
    private static int c = -1;
    private static int d = -1;
    private static int e = -1;
    private static int f = -1;
    private static int g = -1;
    private static long h = -1;

    private static void e(Context context) {
        if (a == null) {
            a = context.getSharedPreferences("location_offline", 0);
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean a(Context context, int i) {
        try {
            e(context);
            if (b == -1) {
                b = a.getInt("last_upload_time", 0);
            }
            if (c == -1) {
                c = a.getInt("uploaded_count", 0);
            }
            if (Calendar.getInstance().get(6) != b) {
                c = 0;
                Editor edit = a.edit();
                edit.putInt("uploaded_count", c);
                if (VERSION.SDK_INT >= 9) {
                    edit.apply();
                } else {
                    edit.commit();
                }
                return true;
            } else if (c < i) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable th) {
            a.a(th);
            return false;
        }
    }

    public static int b(Context context, int i) {
        try {
            e(context);
            if (c == -1) {
                c = a.getInt("uploaded_count", 0);
            }
            return Math.max(0, i - c);
        } catch (Throwable th) {
            a.a(th);
            return 0;
        }
    }

    @SuppressLint({"NewApi"})
    public static void c(Context context, int i) {
        try {
            e(context);
            b = Calendar.getInstance().get(6);
            c += i;
            Editor edit = a.edit();
            edit.putInt("last_upload_time", b);
            edit.putInt("uploaded_count", c);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (Throwable th) {
            a.a(th);
        }
    }

    public static boolean a(Context context) {
        try {
            e(context);
            if (d == -1) {
                d = a.getInt("first_downloaded", 0);
            }
            if (d == 1) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            a.a(th);
            return true;
        }
    }

    @SuppressLint({"NewApi"})
    public static void b(Context context) {
        try {
            e(context);
            d = 1;
            Editor edit = a.edit();
            edit.putInt("first_downloaded", d);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (Throwable th) {
            a.a(th);
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean d(Context context, int i) {
        try {
            e(context);
            if (((long) e) == -1) {
                e = a.getInt("last_download_time", 0);
            }
            int i2 = Calendar.getInstance().get(6);
            if (i2 != e) {
                e = i2;
                f = 0;
                g = 0;
                Editor edit = a.edit();
                edit.putInt("last_download_time", e);
                edit.putInt("downloaded_count", f);
                edit.putInt("nonwifi_downloaded_count", g);
                if (VERSION.SDK_INT >= 9) {
                    edit.apply();
                } else {
                    edit.commit();
                }
                return true;
            }
            if (f == -1) {
                f = a.getInt("downloaded_count", 0);
            }
            if (f < i) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            a.a(th);
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean e(Context context, int i) {
        try {
            e(context);
            if (((long) e) == -1) {
                e = a.getInt("last_download_time", 0);
            }
            int i2 = Calendar.getInstance().get(6);
            if (i2 != e) {
                e = i2;
                f = 0;
                g = 0;
                Editor edit = a.edit();
                edit.putInt("last_download_time", e);
                edit.putInt("downloaded_count", f);
                edit.putInt("nonwifi_downloaded_count", g);
                if (VERSION.SDK_INT >= 9) {
                    edit.apply();
                } else {
                    edit.commit();
                }
                return true;
            }
            if (g == -1) {
                g = a.getInt("nonwifi_downloaded_count", 0);
            }
            if (g < i) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            a.a(th);
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    public static void c(Context context) {
        try {
            e(context);
            if (f == -1) {
                f = a.getInt("downloaded_count", 0);
            }
            f++;
            Editor edit = a.edit();
            edit.putInt("downloaded_count", f);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (Throwable th) {
            a.a(th);
        }
    }

    @SuppressLint({"NewApi"})
    public static void d(Context context) {
        try {
            e(context);
            if (g == -1) {
                g = a.getInt("nonwifi_downloaded_count", 0);
            }
            g++;
            Editor edit = a.edit();
            edit.putInt("nonwifi_downloaded_count", g);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (Throwable th) {
            a.a(th);
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean a(Context context, long j) {
        try {
            e(context);
            if (h == -1) {
                h = a.getLong("config_time", 0);
            }
            boolean z = h != j;
            if (z) {
                h = j;
                Editor edit = a.edit();
                edit.putLong("config_time", h);
                if (VERSION.SDK_INT >= 9) {
                    edit.apply();
                } else {
                    edit.commit();
                }
            }
            return z;
        } catch (Throwable th) {
            a.a(th);
            return false;
        }
    }
}
