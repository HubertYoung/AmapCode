package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.session.MediaController;
import android.os.Parcelable;
import android.view.View;
import java.util.List;
import java.util.Map;

class ActivityCompat21 {

    public static abstract class SharedElementCallback21 {
        public abstract Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF);

        public abstract View onCreateSnapshotView(Context context, Parcelable parcelable);

        public abstract void onMapSharedElements(List<String> list, Map<String, View> map);

        public abstract void onRejectSharedElements(List<View> list);

        public abstract void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3);

        public abstract void onSharedElementStart(List<String> list, List<View> list2, List<View> list3);
    }

    static class SharedElementCallbackImpl extends SharedElementCallback {
        private SharedElementCallback21 a;

        public SharedElementCallbackImpl(SharedElementCallback21 sharedElementCallback21) {
            this.a = sharedElementCallback21;
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

    ActivityCompat21() {
    }

    public static void a(Activity activity, Object obj) {
        activity.setMediaController((MediaController) obj);
    }

    public static void a(Activity activity) {
        activity.finishAfterTransition();
    }

    public static void a(Activity activity, SharedElementCallback21 sharedElementCallback21) {
        activity.setEnterSharedElementCallback(a(sharedElementCallback21));
    }

    public static void b(Activity activity, SharedElementCallback21 sharedElementCallback21) {
        activity.setExitSharedElementCallback(a(sharedElementCallback21));
    }

    public static void b(Activity activity) {
        activity.postponeEnterTransition();
    }

    public static void c(Activity activity) {
        activity.startPostponedEnterTransition();
    }

    private static SharedElementCallback a(SharedElementCallback21 sharedElementCallback21) {
        if (sharedElementCallback21 != null) {
            return new SharedElementCallbackImpl(sharedElementCallback21);
        }
        return null;
    }
}
