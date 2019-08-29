package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.InboxStyle;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.dontuse.app.BundleUtil;
import android.support.dontuse.app.RemoteInputCompatBase.RemoteInput;
import android.support.dontuse.app.RemoteInputCompatJellybean;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v4.app.NotificationCompatBase.Action.Factory;
import android.util.SparseArray;
import android.widget.RemoteViews;
import com.alipay.mobile.h5container.api.H5Param;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(16)
class NotificationCompatJellybean {
    private static final Object a = new Object();
    private static Field b;
    private static boolean c;
    private static final Object d = new Object();
    private static Class<?> e;
    private static Field f;
    private static Field g;
    private static Field h;
    private static Field i;
    private static boolean j;

    public static class Builder implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor {
        private android.app.Notification.Builder b;
        private List<Bundle> mActionExtrasList = new ArrayList();
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private final Bundle mExtras;

        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, int i4, CharSequence charSequence4, boolean z3, Bundle bundle, String str, boolean z4, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            PendingIntent pendingIntent3;
            Notification notification2 = notification;
            Bundle bundle2 = bundle;
            String str3 = str;
            String str4 = str2;
            boolean z5 = false;
            android.app.Notification.Builder deleteIntent = new android.app.Notification.Builder(context).setWhen(notification2.when).setSmallIcon(notification2.icon, notification2.iconLevel).setContent(notification2.contentView).setTicker(notification2.tickerText, remoteViews).setSound(notification2.sound, notification2.audioStreamType).setVibrate(notification2.vibrate).setLights(notification2.ledARGB, notification2.ledOnMS, notification2.ledOffMS).setOngoing((notification2.flags & 2) != 0).setOnlyAlertOnce((notification2.flags & 8) != 0).setAutoCancel((notification2.flags & 16) != 0).setDefaults(notification2.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification2.deleteIntent);
            if ((notification2.flags & 128) != 0) {
                pendingIntent3 = pendingIntent2;
                z5 = true;
            } else {
                pendingIntent3 = pendingIntent2;
            }
            this.b = deleteIntent.setFullScreenIntent(pendingIntent3, z5).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z2).setPriority(i4).setProgress(i2, i3, z);
            this.mExtras = new Bundle();
            if (bundle2 != null) {
                this.mExtras.putAll(bundle2);
            }
            if (z3) {
                this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
            }
            if (str3 != null) {
                this.mExtras.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, str3);
                if (z4) {
                    this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY, true);
                } else {
                    this.mExtras.putBoolean(NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL, true);
                }
            }
            if (str4 != null) {
                this.mExtras.putString(NotificationCompatExtras.EXTRA_SORT_KEY, str4);
            }
            this.mContentView = remoteViews2;
            this.mBigContentView = remoteViews3;
        }

        public void addAction(Action action) {
            this.mActionExtrasList.add(NotificationCompatJellybean.a(this.b, action));
        }

        public android.app.Notification.Builder getBuilder() {
            return this.b;
        }

        public Notification build() {
            Notification build = this.b.build();
            Bundle a = NotificationCompatJellybean.a(build);
            Bundle bundle = new Bundle(this.mExtras);
            for (String str : this.mExtras.keySet()) {
                if (a.containsKey(str)) {
                    bundle.remove(str);
                }
            }
            a.putAll(bundle);
            SparseArray<Bundle> a2 = NotificationCompatJellybean.a(this.mActionExtrasList);
            if (a2 != null) {
                NotificationCompatJellybean.a(build).putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, a2);
            }
            if (this.mContentView != null) {
                build.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                build.bigContentView = this.mBigContentView;
            }
            return build;
        }
    }

    NotificationCompatJellybean() {
    }

    public static void a(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean z, CharSequence charSequence2, CharSequence charSequence3) {
        BigTextStyle bigText = new BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(charSequence).bigText(charSequence3);
        if (z) {
            bigText.setSummaryText(charSequence2);
        }
    }

    public static void a(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean z, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean z2) {
        BigPictureStyle bigPicture = new BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(charSequence).bigPicture(bitmap);
        if (z2) {
            bigPicture.bigLargeIcon(bitmap2);
        }
        if (z) {
            bigPicture.setSummaryText(charSequence2);
        }
    }

    public static void a(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean z, CharSequence charSequence2, ArrayList<CharSequence> arrayList) {
        InboxStyle bigContentTitle = new InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(charSequence);
        if (z) {
            bigContentTitle.setSummaryText(charSequence2);
        }
        Iterator<CharSequence> it = arrayList.iterator();
        while (it.hasNext()) {
            bigContentTitle.addLine(it.next());
        }
    }

    public static SparseArray<Bundle> a(List<Bundle> list) {
        int size = list.size();
        SparseArray<Bundle> sparseArray = null;
        for (int i2 = 0; i2 < size; i2++) {
            Bundle bundle = list.get(i2);
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                sparseArray.put(i2, bundle);
            }
        }
        return sparseArray;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0042, code lost:
        c = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0045, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[ExcHandler: IllegalAccessException | NoSuchFieldException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:8:0x000b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.Bundle a(android.app.Notification r6) {
        /*
            java.lang.Object r0 = a
            monitor-enter(r0)
            boolean r1 = c     // Catch:{ all -> 0x0046 }
            r2 = 0
            if (r1 == 0) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            return r2
        L_0x000a:
            r1 = 1
            java.lang.reflect.Field r3 = b     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            if (r3 != 0) goto L_0x002c
            java.lang.Class<android.app.Notification> r3 = android.app.Notification.class
            java.lang.String r4 = "extras"
            java.lang.reflect.Field r3 = r3.getDeclaredField(r4)     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            java.lang.Class<android.os.Bundle> r4 = android.os.Bundle.class
            java.lang.Class r5 = r3.getType()     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            boolean r4 = r4.isAssignableFrom(r5)     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            if (r4 != 0) goto L_0x0027
            c = r1     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            return r2
        L_0x0027:
            r3.setAccessible(r1)     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            b = r3     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
        L_0x002c:
            java.lang.reflect.Field r3 = b     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            java.lang.Object r3 = r3.get(r6)     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            android.os.Bundle r3 = (android.os.Bundle) r3     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            if (r3 != 0) goto L_0x0040
            android.os.Bundle r3 = new android.os.Bundle     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            r3.<init>()     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            java.lang.reflect.Field r4 = b     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
            r4.set(r6, r3)     // Catch:{ IllegalAccessException | NoSuchFieldException -> 0x0042 }
        L_0x0040:
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            return r3
        L_0x0042:
            c = r1     // Catch:{ all -> 0x0046 }
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            return r2
        L_0x0046:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompatJellybean.a(android.app.Notification):android.os.Bundle");
    }

    public static Action a(Factory factory, RemoteInput.Factory factory2, int i2, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        boolean z;
        RemoteInput[] remoteInputArr;
        RemoteInput[] remoteInputArr2;
        if (bundle != null) {
            RemoteInput[] a2 = RemoteInputCompatJellybean.a(BundleUtil.a(bundle, NotificationCompatExtras.EXTRA_REMOTE_INPUTS), factory2);
            remoteInputArr = RemoteInputCompatJellybean.a(BundleUtil.a(bundle, "android.support.dataRemoteInputs"), factory2);
            remoteInputArr2 = a2;
            z = bundle.getBoolean("android.support.allowGeneratedReplies");
        } else {
            remoteInputArr2 = null;
            remoteInputArr = null;
            z = false;
        }
        return factory.build(i2, charSequence, pendingIntent, bundle, remoteInputArr2, remoteInputArr, z);
    }

    public static Bundle a(android.app.Notification.Builder builder, Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent());
        Bundle bundle = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            bundle.putParcelableArray(NotificationCompatExtras.EXTRA_REMOTE_INPUTS, RemoteInputCompatJellybean.a(action.getRemoteInputs()));
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            bundle.putParcelableArray("android.support.dataRemoteInputs", RemoteInputCompatJellybean.a(action.getDataOnlyRemoteInputs()));
        }
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        return bundle;
    }

    public static int b(Notification notification) {
        int length;
        synchronized (d) {
            try {
                Object[] c2 = c(notification);
                length = c2 != null ? c2.length : 0;
            }
        }
        return length;
    }

    public static Action a(Notification notification, int i2, Factory factory, RemoteInput.Factory factory2) {
        Bundle bundle;
        synchronized (d) {
            try {
                Object[] c2 = c(notification);
                if (c2 != null) {
                    Object obj = c2[i2];
                    Bundle a2 = a(notification);
                    if (a2 != null) {
                        SparseArray sparseParcelableArray = a2.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
                        if (sparseParcelableArray != null) {
                            bundle = (Bundle) sparseParcelableArray.get(i2);
                            Action a3 = a(factory, factory2, g.getInt(obj), (CharSequence) h.get(obj), (PendingIntent) i.get(obj), bundle);
                            return a3;
                        }
                    }
                    bundle = null;
                    Action a32 = a(factory, factory2, g.getInt(obj), (CharSequence) h.get(obj), (PendingIntent) i.get(obj), bundle);
                    return a32;
                }
            } catch (IllegalAccessException unused) {
                j = true;
            } catch (Throwable th) {
                throw th;
            }
        }
        return null;
    }

    private static Object[] c(Notification notification) {
        synchronized (d) {
            if (!a()) {
                return null;
            }
            try {
                Object[] objArr = (Object[]) f.get(notification);
                return objArr;
            } catch (IllegalAccessException unused) {
                j = true;
                return null;
            }
        }
    }

    private static boolean a() {
        if (j) {
            return false;
        }
        try {
            if (f == null) {
                Class<?> cls = Class.forName("android.app.Notification$Action");
                e = cls;
                g = cls.getDeclaredField(H5Param.MENU_ICON);
                h = e.getDeclaredField("title");
                i = e.getDeclaredField("actionIntent");
                Field declaredField = Notification.class.getDeclaredField("actions");
                f = declaredField;
                declaredField.setAccessible(true);
            }
        } catch (ClassNotFoundException unused) {
            j = true;
        } catch (NoSuchFieldException unused2) {
            j = true;
        }
        return !j;
    }

    public static Action[] a(ArrayList<Parcelable> arrayList, Factory factory, RemoteInput.Factory factory2) {
        if (arrayList == null) {
            return null;
        }
        Action[] newArray = factory.newArray(arrayList.size());
        for (int i2 = 0; i2 < newArray.length; i2++) {
            Bundle bundle = (Bundle) arrayList.get(i2);
            Bundle bundle2 = bundle.getBundle("extras");
            newArray[i2] = factory.build(bundle.getInt(H5Param.MENU_ICON), bundle.getCharSequence("title"), (PendingIntent) bundle.getParcelable("actionIntent"), bundle.getBundle("extras"), RemoteInputCompatJellybean.a(BundleUtil.a(bundle, "remoteInputs"), factory2), RemoteInputCompatJellybean.a(BundleUtil.a(bundle, "dataOnlyRemoteInputs"), factory2), bundle2 != null ? bundle2.getBoolean("android.support.allowGeneratedReplies", false) : false);
        }
        return newArray;
    }

    public static ArrayList<Parcelable> a(Action[] actionArr) {
        Bundle bundle;
        if (actionArr == null) {
            return null;
        }
        ArrayList<Parcelable> arrayList = new ArrayList<>(actionArr.length);
        for (Action action : actionArr) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt(H5Param.MENU_ICON, action.getIcon());
            bundle2.putCharSequence("title", action.getTitle());
            bundle2.putParcelable("actionIntent", action.getActionIntent());
            if (action.getExtras() != null) {
                bundle = new Bundle(action.getExtras());
            } else {
                bundle = new Bundle();
            }
            bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
            bundle2.putBundle("extras", bundle);
            bundle2.putParcelableArray("remoteInputs", RemoteInputCompatJellybean.a(action.getRemoteInputs()));
            arrayList.add(bundle2);
        }
        return arrayList;
    }
}
