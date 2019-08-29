package android.support.v4.media.routing;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter;
import android.media.RemoteControlClient;
import android.os.Build.VERSION;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class MediaRouterJellybean {

    public interface Callback {
        void onRouteAdded(Object obj);

        void onRouteChanged(Object obj);

        void onRouteGrouped(Object obj, Object obj2, int i);

        void onRouteRemoved(Object obj);

        void onRouteSelected(int i, Object obj);

        void onRouteUngrouped(Object obj, Object obj2);

        void onRouteUnselected(int i, Object obj);

        void onRouteVolumeChanged(Object obj);
    }

    static class CallbackProxy<T extends Callback> extends android.media.MediaRouter.Callback {
        protected final T a;

        public void onRouteSelected(MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            this.a.onRouteSelected(i, routeInfo);
        }

        public void onRouteUnselected(MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            this.a.onRouteUnselected(i, routeInfo);
        }

        public void onRouteAdded(MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.a.onRouteAdded(routeInfo);
        }

        public void onRouteRemoved(MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.a.onRouteRemoved(routeInfo);
        }

        public void onRouteChanged(MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.a.onRouteChanged(routeInfo);
        }

        public void onRouteGrouped(MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i) {
            this.a.onRouteGrouped(routeInfo, routeGroup, i);
        }

        public void onRouteUngrouped(MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup) {
            this.a.onRouteUngrouped(routeInfo, routeGroup);
        }

        public void onRouteVolumeChanged(MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.a.onRouteVolumeChanged(routeInfo);
        }
    }

    public static final class GetDefaultRouteWorkaround {
        private Method mGetSystemAudioRouteMethod;

        public GetDefaultRouteWorkaround() {
            if (VERSION.SDK_INT < 16 || VERSION.SDK_INT > 17) {
                throw new UnsupportedOperationException();
            }
            try {
                this.mGetSystemAudioRouteMethod = MediaRouter.class.getMethod("getSystemAudioRoute", new Class[0]);
            } catch (NoSuchMethodException unused) {
            }
        }

        public final Object getDefaultRoute(Object obj) {
            MediaRouter mediaRouter = (MediaRouter) obj;
            if (this.mGetSystemAudioRouteMethod != null) {
                try {
                    return this.mGetSystemAudioRouteMethod.invoke(mediaRouter, new Object[0]);
                } catch (IllegalAccessException | InvocationTargetException unused) {
                }
            }
            return mediaRouter.getRouteAt(0);
        }
    }

    public static final class RouteCategory {
        public static CharSequence getName(Object obj, Context context) {
            return ((android.media.MediaRouter.RouteCategory) obj).getName(context);
        }

        public static List getRoutes(Object obj) {
            ArrayList arrayList = new ArrayList();
            ((android.media.MediaRouter.RouteCategory) obj).getRoutes(arrayList);
            return arrayList;
        }

        public static int getSupportedTypes(Object obj) {
            return ((android.media.MediaRouter.RouteCategory) obj).getSupportedTypes();
        }

        public static boolean isGroupable(Object obj) {
            return ((android.media.MediaRouter.RouteCategory) obj).isGroupable();
        }
    }

    public static final class RouteGroup {
        public static List getGroupedRoutes(Object obj) {
            android.media.MediaRouter.RouteGroup routeGroup = (android.media.MediaRouter.RouteGroup) obj;
            int routeCount = routeGroup.getRouteCount();
            ArrayList arrayList = new ArrayList(routeCount);
            for (int i = 0; i < routeCount; i++) {
                arrayList.add(routeGroup.getRouteAt(i));
            }
            return arrayList;
        }
    }

    public static final class RouteInfo {
        public static CharSequence getName(Object obj, Context context) {
            return ((android.media.MediaRouter.RouteInfo) obj).getName(context);
        }

        public static CharSequence getStatus(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getStatus();
        }

        public static int getSupportedTypes(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getSupportedTypes();
        }

        public static Object getCategory(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getCategory();
        }

        public static Drawable getIconDrawable(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getIconDrawable();
        }

        public static int getPlaybackType(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getPlaybackType();
        }

        public static int getPlaybackStream(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getPlaybackStream();
        }

        public static int getVolume(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getVolume();
        }

        public static int getVolumeMax(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getVolumeMax();
        }

        public static int getVolumeHandling(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getVolumeHandling();
        }

        public static Object getTag(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getTag();
        }

        public static void setTag(Object obj, Object obj2) {
            ((android.media.MediaRouter.RouteInfo) obj).setTag(obj2);
        }

        public static void requestSetVolume(Object obj, int i) {
            ((android.media.MediaRouter.RouteInfo) obj).requestSetVolume(i);
        }

        public static void requestUpdateVolume(Object obj, int i) {
            ((android.media.MediaRouter.RouteInfo) obj).requestUpdateVolume(i);
        }

        public static Object getGroup(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getGroup();
        }

        public static boolean isGroup(Object obj) {
            return obj instanceof android.media.MediaRouter.RouteGroup;
        }
    }

    public static final class SelectRouteWorkaround {
        private Method mSelectRouteIntMethod;

        public SelectRouteWorkaround() {
            if (VERSION.SDK_INT < 16 || VERSION.SDK_INT > 17) {
                throw new UnsupportedOperationException();
            }
            try {
                this.mSelectRouteIntMethod = MediaRouter.class.getMethod("selectRouteInt", new Class[]{Integer.TYPE, android.media.MediaRouter.RouteInfo.class});
            } catch (NoSuchMethodException unused) {
            }
        }

        public final void selectRoute(Object obj, int i, Object obj2) {
            MediaRouter mediaRouter = (MediaRouter) obj;
            android.media.MediaRouter.RouteInfo routeInfo = (android.media.MediaRouter.RouteInfo) obj2;
            if ((routeInfo.getSupportedTypes() & 8388608) == 0 && this.mSelectRouteIntMethod != null) {
                try {
                    this.mSelectRouteIntMethod.invoke(mediaRouter, new Object[]{Integer.valueOf(i), routeInfo});
                    return;
                } catch (IllegalAccessException | InvocationTargetException unused) {
                }
            }
            mediaRouter.selectRoute(i, routeInfo);
        }
    }

    public static final class UserRouteInfo {
        public static void setName(Object obj, CharSequence charSequence) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setName(charSequence);
        }

        public static void setStatus(Object obj, CharSequence charSequence) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setStatus(charSequence);
        }

        public static void setIconDrawable(Object obj, Drawable drawable) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setIconDrawable(drawable);
        }

        public static void setPlaybackType(Object obj, int i) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setPlaybackType(i);
        }

        public static void setPlaybackStream(Object obj, int i) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setPlaybackStream(i);
        }

        public static void setVolume(Object obj, int i) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setVolume(i);
        }

        public static void setVolumeMax(Object obj, int i) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setVolumeMax(i);
        }

        public static void setVolumeHandling(Object obj, int i) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setVolumeHandling(i);
        }

        public static void setVolumeCallback(Object obj, Object obj2) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setVolumeCallback((android.media.MediaRouter.VolumeCallback) obj2);
        }

        public static void setRemoteControlClient(Object obj, Object obj2) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setRemoteControlClient((RemoteControlClient) obj2);
        }
    }

    public interface VolumeCallback {
        void onVolumeSetRequest(Object obj, int i);

        void onVolumeUpdateRequest(Object obj, int i);
    }

    static class VolumeCallbackProxy<T extends VolumeCallback> extends android.media.MediaRouter.VolumeCallback {
        protected final T a;

        public void onVolumeSetRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            this.a.onVolumeSetRequest(routeInfo, i);
        }

        public void onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            this.a.onVolumeUpdateRequest(routeInfo, i);
        }
    }

    MediaRouterJellybean() {
    }
}
