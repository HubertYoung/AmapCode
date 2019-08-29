package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Callback;
import android.media.session.PlaybackState.Builder;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import java.util.HashSet;
import java.util.Set;

class MediaHelper {
    private static final long MEDIA_SESSION_ACTIONS = 823;
    /* access modifiers changed from: private */
    public static Set<OnMediaButtonEventListener> sListenerSet = new HashSet();
    private static MediaSession sMediaSession;

    interface OnMediaButtonEventListener {
        void onMediaButtonEvent(int i, int i2);
    }

    MediaHelper() {
    }

    public static void addMediaButtonEvent(@NonNull Context context, OnMediaButtonEventListener onMediaButtonEventListener) {
        if (onMediaButtonEventListener != null) {
            sListenerSet.add(onMediaButtonEventListener);
            checkMediaSession(context);
        }
    }

    public static void removeMediaButtonEvent(OnMediaButtonEventListener onMediaButtonEventListener) {
        if (onMediaButtonEventListener != null) {
            sListenerSet.remove(onMediaButtonEventListener);
            checkMediaSession(null);
        }
    }

    private static void checkMediaSession(Context context) {
        destroyMediaSession();
        if (sListenerSet.size() > 0) {
            initMediaSession(context);
        }
    }

    private static void initMediaSession(Context context) {
        if (sMediaSession == null) {
            MediaSession mediaSession = new MediaSession(context, MediaHelper.class.getName());
            sMediaSession = mediaSession;
            mediaSession.setFlags(3);
            sMediaSession.setPlaybackState(new Builder().setActions(MEDIA_SESSION_ACTIONS).build());
            sMediaSession.setCallback(new Callback() {
                public final boolean onMediaButtonEvent(@NonNull Intent intent) {
                    if ("android.intent.action.MEDIA_BUTTON".equals(intent.getAction())) {
                        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
                        if (keyEvent != null) {
                            for (OnMediaButtonEventListener onMediaButtonEvent : MediaHelper.sListenerSet) {
                                onMediaButtonEvent.onMediaButtonEvent(keyEvent.getAction(), keyEvent.getKeyCode());
                            }
                            return true;
                        }
                    }
                    return super.onMediaButtonEvent(intent);
                }
            });
        }
        if (!sMediaSession.isActive()) {
            sMediaSession.setActive(true);
        }
    }

    private static void destroyMediaSession() {
        if (sMediaSession != null) {
            sMediaSession.setCallback(null);
            sMediaSession.setActive(false);
            sMediaSession.release();
            sMediaSession = null;
        }
    }
}
