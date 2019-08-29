package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat21.SharedElementCallback21;
import android.support.v4.content.ContextCompat;
import android.view.View;
import java.util.List;
import java.util.Map;

public class ActivityCompat extends ContextCompat {

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr);
    }

    static class SharedElementCallback21Impl extends SharedElementCallback21 {
        private SharedElementCallback a;

        public SharedElementCallback21Impl(SharedElementCallback sharedElementCallback) {
            this.a = sharedElementCallback;
        }

        public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
            this.a.onSharedElementStart(list, list2, list3);
        }

        public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
            this.a.onSharedElementEnd(list, list2, list3);
        }

        public void onRejectSharedElements(List<View> list) {
            this.a.onRejectSharedElements(list);
        }

        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            this.a.onMapSharedElements(list, map);
        }

        public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
            return this.a.onCaptureSharedElementSnapshot(view, matrix, rectF);
        }

        public View onCreateSnapshotView(Context context, Parcelable parcelable) {
            return this.a.onCreateSnapshotView(context, parcelable);
        }
    }

    public static boolean invalidateOptionsMenu(Activity activity) {
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        ActivityCompatHoneycomb.a(activity);
        return true;
    }

    public static void startActivity(Activity activity, Intent intent, @Nullable Bundle bundle) {
        if (VERSION.SDK_INT >= 16) {
            ActivityCompatJB.a(activity, intent, bundle);
        } else {
            activity.startActivity(intent);
        }
    }

    public static void startActivityForResult(Activity activity, Intent intent, int i, @Nullable Bundle bundle) {
        if (VERSION.SDK_INT >= 16) {
            ActivityCompatJB.a(activity, intent, i, bundle);
        } else {
            activity.startActivityForResult(intent, i);
        }
    }

    public static void finishAffinity(Activity activity) {
        if (VERSION.SDK_INT >= 16) {
            ActivityCompatJB.a(activity);
        } else {
            activity.finish();
        }
    }

    public static void finishAfterTransition(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.a(activity);
        } else {
            activity.finish();
        }
    }

    public Uri getReferrer(Activity activity) {
        if (VERSION.SDK_INT >= 22) {
            return ActivityCompat22.a(activity);
        }
        Intent intent = activity.getIntent();
        Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.REFERRER");
        if (uri != null) {
            return uri;
        }
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (stringExtra != null) {
            return Uri.parse(stringExtra);
        }
        return null;
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback sharedElementCallback) {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.a(activity, createCallback(sharedElementCallback));
        }
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback sharedElementCallback) {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.b(activity, createCallback(sharedElementCallback));
        }
    }

    public static void postponeEnterTransition(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.b(activity);
        }
    }

    public static void startPostponedEnterTransition(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.c(activity);
        }
    }

    public static void requestPermissions(@NonNull final Activity activity, @NonNull final String[] strArr, final int i) {
        if (VERSION.SDK_INT >= 23) {
            ActivityCompatApi23.a(activity, strArr, i);
            return;
        }
        if (activity instanceof OnRequestPermissionsResultCallback) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public final void run() {
                    int[] iArr = new int[strArr.length];
                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();
                    int length = strArr.length;
                    for (int i = 0; i < length; i++) {
                        iArr[i] = packageManager.checkPermission(strArr[i], packageName);
                    }
                    ((OnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(i, strArr, iArr);
                }
            });
        }
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String str) {
        if (VERSION.SDK_INT >= 23) {
            return ActivityCompatApi23.a(activity, str);
        }
        return false;
    }

    private static SharedElementCallback21 createCallback(SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback != null) {
            return new SharedElementCallback21Impl(sharedElementCallback);
        }
        return null;
    }
}
