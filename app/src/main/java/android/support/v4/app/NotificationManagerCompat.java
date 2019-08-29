package android.support.v4.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.support.annotation.GuardedBy;
import android.support.v4.app.INotificationSideChannel.Stub;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    @GuardedBy("sEnabledNotificationListenersLock")
    private static Set<String> sEnabledNotificationListenerPackages = new HashSet();
    @GuardedBy("sEnabledNotificationListenersLock")
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock = new Object();
    private static final Object sLock = new Object();
    @GuardedBy("sLock")
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager = ((NotificationManager) this.mContext.getSystemService("notification"));

    static class CancelTask implements Task {
        final String a;
        final int b;
        final String c;
        final boolean d;

        CancelTask(String str) {
            this.a = str;
            this.b = 0;
            this.c = null;
            this.d = true;
        }

        CancelTask(String str, int i, String str2) {
            this.a = str;
            this.b = i;
            this.c = str2;
            this.d = false;
        }

        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            if (this.d) {
                iNotificationSideChannel.cancelAll(this.a);
            } else {
                iNotificationSideChannel.cancel(this.a, this.b, this.c);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CancelTask[");
            sb.append("packageName:");
            sb.append(this.a);
            sb.append(", id:");
            sb.append(this.b);
            sb.append(", tag:");
            sb.append(this.c);
            sb.append(", all:");
            sb.append(this.d);
            sb.append("]");
            return sb.toString();
        }
    }

    static class NotifyTask implements Task {
        final String a;
        final int b;
        final String c;
        final Notification d;

        NotifyTask(String str, int i, String str2, Notification notification) {
            this.a = str;
            this.b = i;
            this.c = str2;
            this.d = notification;
        }

        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            iNotificationSideChannel.notify(this.a, this.b, this.c, this.d);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("NotifyTask[");
            sb.append("packageName:");
            sb.append(this.a);
            sb.append(", id:");
            sb.append(this.b);
            sb.append(", tag:");
            sb.append(this.c);
            sb.append("]");
            return sb.toString();
        }
    }

    static class ServiceConnectedEvent {
        final ComponentName a;
        final IBinder b;

        ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.a = componentName;
            this.b = iBinder;
        }
    }

    static class SideChannelManager implements ServiceConnection, Callback {
        final Handler a;
        private final Context b;
        private final HandlerThread c;
        private final Map<ComponentName, ListenerRecord> d = new HashMap();
        private Set<String> e = new HashSet();

        static class ListenerRecord {
            public final ComponentName a;
            public boolean b = false;
            public INotificationSideChannel c;
            public LinkedList<Task> d = new LinkedList<>();
            public int e = 0;

            public ListenerRecord(ComponentName componentName) {
                this.a = componentName;
            }
        }

        public SideChannelManager(Context context) {
            this.b = context;
            this.c = new HandlerThread("NotificationManagerCompat");
            this.c.start();
            this.a = new Handler(this.c.getLooper(), this);
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    Task task = (Task) message.obj;
                    Set<String> enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(this.b);
                    if (!enabledListenerPackages.equals(this.e)) {
                        this.e = enabledListenerPackages;
                        List<ResolveInfo> queryIntentServices = this.b.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0);
                        HashSet<ComponentName> hashSet = new HashSet<>();
                        for (ResolveInfo next : queryIntentServices) {
                            if (enabledListenerPackages.contains(next.serviceInfo.packageName)) {
                                ComponentName componentName = new ComponentName(next.serviceInfo.packageName, next.serviceInfo.name);
                                if (next.serviceInfo.permission != null) {
                                    StringBuilder sb = new StringBuilder("Permission present on component ");
                                    sb.append(componentName);
                                    sb.append(", not adding listener record.");
                                } else {
                                    hashSet.add(componentName);
                                }
                            }
                        }
                        for (ComponentName componentName2 : hashSet) {
                            if (!this.d.containsKey(componentName2)) {
                                if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                                    new StringBuilder("Adding listener record for ").append(componentName2);
                                }
                                this.d.put(componentName2, new ListenerRecord(componentName2));
                            }
                        }
                        Iterator<Entry<ComponentName, ListenerRecord>> it = this.d.entrySet().iterator();
                        while (it.hasNext()) {
                            Entry next2 = it.next();
                            if (!hashSet.contains(next2.getKey())) {
                                if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                                    new StringBuilder("Removing listener record for ").append(next2.getKey());
                                }
                                a((ListenerRecord) next2.getValue());
                                it.remove();
                            }
                        }
                    }
                    for (ListenerRecord next3 : this.d.values()) {
                        next3.d.add(task);
                        c(next3);
                    }
                    return true;
                case 1:
                    ServiceConnectedEvent serviceConnectedEvent = (ServiceConnectedEvent) message.obj;
                    ComponentName componentName3 = serviceConnectedEvent.a;
                    IBinder iBinder = serviceConnectedEvent.b;
                    ListenerRecord listenerRecord = this.d.get(componentName3);
                    if (listenerRecord != null) {
                        listenerRecord.c = Stub.asInterface(iBinder);
                        listenerRecord.e = 0;
                        c(listenerRecord);
                    }
                    return true;
                case 2:
                    ListenerRecord listenerRecord2 = this.d.get((ComponentName) message.obj);
                    if (listenerRecord2 != null) {
                        a(listenerRecord2);
                    }
                    return true;
                case 3:
                    ListenerRecord listenerRecord3 = this.d.get((ComponentName) message.obj);
                    if (listenerRecord3 != null) {
                        c(listenerRecord3);
                    }
                    return true;
                default:
                    return false;
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                new StringBuilder("Connected to service ").append(componentName);
            }
            this.a.obtainMessage(1, new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                new StringBuilder("Disconnected from service ").append(componentName);
            }
            this.a.obtainMessage(2, componentName).sendToTarget();
        }

        private void a(ListenerRecord listenerRecord) {
            if (listenerRecord.b) {
                this.b.unbindService(this);
                listenerRecord.b = false;
            }
            listenerRecord.c = null;
        }

        private void b(ListenerRecord listenerRecord) {
            if (!this.a.hasMessages(3, listenerRecord.a)) {
                listenerRecord.e++;
                if (listenerRecord.e > 6) {
                    StringBuilder sb = new StringBuilder("Giving up on delivering ");
                    sb.append(listenerRecord.d.size());
                    sb.append(" tasks to ");
                    sb.append(listenerRecord.a);
                    sb.append(" after ");
                    sb.append(listenerRecord.e);
                    sb.append(" retries");
                    listenerRecord.d.clear();
                    return;
                }
                int i = (1 << (listenerRecord.e - 1)) * 1000;
                if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                    StringBuilder sb2 = new StringBuilder("Scheduling retry for ");
                    sb2.append(i);
                    sb2.append(" ms");
                }
                this.a.sendMessageDelayed(this.a.obtainMessage(3, listenerRecord.a), (long) i);
            }
        }

        private void c(ListenerRecord listenerRecord) {
            boolean z;
            if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                StringBuilder sb = new StringBuilder("Processing component ");
                sb.append(listenerRecord.a);
                sb.append(", ");
                sb.append(listenerRecord.d.size());
                sb.append(" queued tasks");
            }
            if (!listenerRecord.d.isEmpty()) {
                if (listenerRecord.b) {
                    z = true;
                } else {
                    listenerRecord.b = this.b.bindService(new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.a), this, 33);
                    if (listenerRecord.b) {
                        listenerRecord.e = 0;
                    } else {
                        new StringBuilder("Unable to bind to listener ").append(listenerRecord.a);
                        this.b.unbindService(this);
                    }
                    z = listenerRecord.b;
                }
                if (!z || listenerRecord.c == null) {
                    b(listenerRecord);
                    return;
                }
                while (true) {
                    Task peek = listenerRecord.d.peek();
                    if (peek == null) {
                        break;
                    }
                    try {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            new StringBuilder("Sending task ").append(peek);
                        }
                        peek.send(listenerRecord.c);
                        listenerRecord.d.remove();
                    } catch (DeadObjectException unused) {
                        if (Log.isLoggable(NotificationManagerCompat.TAG, 3)) {
                            new StringBuilder("Remote service has died: ").append(listenerRecord.a);
                        }
                    } catch (RemoteException unused2) {
                        new StringBuilder("RemoteException communicating with ").append(listenerRecord.a);
                    }
                }
                if (!listenerRecord.d.isEmpty()) {
                    b(listenerRecord);
                }
            }
        }
    }

    interface Task {
        void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException;
    }

    public static NotificationManagerCompat from(Context context) {
        return new NotificationManagerCompat(context);
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
    }

    public final void cancel(int i) {
        cancel(null, i);
    }

    public final void cancel(String str, int i) {
        this.mNotificationManager.cancel(str, i);
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), i, str));
        }
    }

    public final void cancelAll() {
        this.mNotificationManager.cancelAll();
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
        }
    }

    public final void notify(int i, Notification notification) {
        notify(null, i, notification);
    }

    public final void notify(String str, int i, Notification notification) {
        if (useSideChannelForNotification(notification)) {
            pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), i, str, notification));
            this.mNotificationManager.cancel(str, i);
            return;
        }
        this.mNotificationManager.notify(str, i, notification);
    }

    public final boolean areNotificationsEnabled() {
        if (VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.areNotificationsEnabled();
        }
        if (VERSION.SDK_INT < 19) {
            return true;
        }
        AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService("appops");
        ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
        String packageName = this.mContext.getApplicationContext().getPackageName();
        int i = applicationInfo.uid;
        try {
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            if (((Integer) cls.getMethod(CHECK_OP_NO_THROW, new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) cls.getDeclaredField(OP_POST_NOTIFICATION).get(Integer.class)).intValue()), Integer.valueOf(i), packageName})).intValue() == 0) {
                return true;
            }
            return false;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException unused) {
            return true;
        }
    }

    public final int getImportance() {
        if (VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.getImportance();
        }
        return -1000;
    }

    public static Set<String> getEnabledListenerPackages(Context context) {
        Set<String> set;
        String string = Secure.getString(context.getContentResolver(), SETTING_ENABLED_NOTIFICATION_LISTENERS);
        synchronized (sEnabledNotificationListenersLock) {
            if (string != null) {
                try {
                    if (!string.equals(sEnabledNotificationListeners)) {
                        String[] split = string.split(":");
                        HashSet hashSet = new HashSet(split.length);
                        for (String unflattenFromString : split) {
                            ComponentName unflattenFromString2 = ComponentName.unflattenFromString(unflattenFromString);
                            if (unflattenFromString2 != null) {
                                hashSet.add(unflattenFromString2.getPackageName());
                            }
                        }
                        sEnabledNotificationListenerPackages = hashSet;
                        sEnabledNotificationListeners = string;
                    }
                } finally {
                }
            }
            set = sEnabledNotificationListenerPackages;
        }
        return set;
    }

    private static boolean useSideChannelForNotification(Notification notification) {
        Bundle extras = NotificationCompat.getExtras(notification);
        return extras != null && extras.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    private void pushSideChannelQueue(Task task) {
        synchronized (sLock) {
            if (sSideChannelManager == null) {
                sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
            }
            sSideChannelManager.a.obtainMessage(0, task).sendToTarget();
        }
    }
}
