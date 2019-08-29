package android.support.v4.media.routing;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.Display;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MediaRouterJellybeanMr1 extends MediaRouterJellybean {

    public static final class ActiveScanWorkaround implements Runnable {
        private static final int WIFI_DISPLAY_SCAN_INTERVAL = 15000;
        private boolean mActivelyScanningWifiDisplays;
        private final DisplayManager mDisplayManager;
        private final Handler mHandler;
        private Method mScanWifiDisplaysMethod;

        public ActiveScanWorkaround(Context context, Handler handler) {
            if (VERSION.SDK_INT != 17) {
                throw new UnsupportedOperationException();
            }
            this.mDisplayManager = (DisplayManager) context.getSystemService("display");
            this.mHandler = handler;
            try {
                this.mScanWifiDisplaysMethod = DisplayManager.class.getMethod("scanWifiDisplays", new Class[0]);
            } catch (NoSuchMethodException unused) {
            }
        }

        public final void setActiveScanRouteTypes(int i) {
            if ((i & 2) != 0) {
                if (!this.mActivelyScanningWifiDisplays && this.mScanWifiDisplaysMethod != null) {
                    this.mActivelyScanningWifiDisplays = true;
                    this.mHandler.post(this);
                }
            } else if (this.mActivelyScanningWifiDisplays) {
                this.mActivelyScanningWifiDisplays = false;
                this.mHandler.removeCallbacks(this);
            }
        }

        public final void run() {
            if (this.mActivelyScanningWifiDisplays) {
                try {
                    this.mScanWifiDisplaysMethod.invoke(this.mDisplayManager, new Object[0]);
                } catch (IllegalAccessException | InvocationTargetException unused) {
                }
                this.mHandler.postDelayed(this, 15000);
            }
        }
    }

    public interface Callback extends android.support.v4.media.routing.MediaRouterJellybean.Callback {
        void onRoutePresentationDisplayChanged(Object obj);
    }

    static class CallbackProxy<T extends Callback> extends CallbackProxy<T> {
        public void onRoutePresentationDisplayChanged(MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            ((Callback) this.a).onRoutePresentationDisplayChanged(routeInfo);
        }
    }

    public static final class IsConnectingWorkaround {
        private Method mGetStatusCodeMethod;
        private int mStatusConnecting;

        public IsConnectingWorkaround() {
            if (VERSION.SDK_INT != 17) {
                throw new UnsupportedOperationException();
            }
            try {
                this.mStatusConnecting = android.media.MediaRouter.RouteInfo.class.getField("STATUS_CONNECTING").getInt(null);
                this.mGetStatusCodeMethod = android.media.MediaRouter.RouteInfo.class.getMethod("getStatusCode", new Class[0]);
            } catch (NoSuchFieldException | NoSuchMethodException unused) {
            } catch (IllegalAccessException unused2) {
            }
        }

        public final boolean isConnecting(Object obj) {
            android.media.MediaRouter.RouteInfo routeInfo = (android.media.MediaRouter.RouteInfo) obj;
            if (this.mGetStatusCodeMethod != null) {
                try {
                    if (((Integer) this.mGetStatusCodeMethod.invoke(routeInfo, new Object[0])).intValue() == this.mStatusConnecting) {
                        return true;
                    }
                    return false;
                } catch (IllegalAccessException | InvocationTargetException unused) {
                }
            }
            return false;
        }
    }

    public static final class RouteInfo {
        public static boolean isEnabled(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).isEnabled();
        }

        public static Display getPresentationDisplay(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getPresentationDisplay();
        }
    }

    MediaRouterJellybeanMr1() {
    }
}
