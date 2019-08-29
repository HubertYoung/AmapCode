package android.support.v4.media.routing;

class MediaRouterJellybeanMr2 extends MediaRouterJellybeanMr1 {

    public static final class RouteInfo {
        public static CharSequence getDescription(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).getDescription();
        }

        public static boolean isConnecting(Object obj) {
            return ((android.media.MediaRouter.RouteInfo) obj).isConnecting();
        }
    }

    public static final class UserRouteInfo {
        public static void setDescription(Object obj, CharSequence charSequence) {
            ((android.media.MediaRouter.UserRouteInfo) obj).setDescription(charSequence);
        }
    }

    MediaRouterJellybeanMr2() {
    }
}
