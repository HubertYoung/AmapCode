package android.support.v4.media;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.support.v4.view.KeyEventCompat;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetPriority;
import java.util.ArrayList;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class TransportMediator extends TransportController {
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MEDIA_RECORD = 130;
    final AudioManager mAudioManager;
    final TransportPerformer mCallbacks;
    final Context mContext;
    final TransportMediatorJellybeanMR2 mController;
    final Object mDispatcherState;
    final Callback mKeyEventCallback;
    final ArrayList<TransportStateListener> mListeners;
    final TransportMediatorCallback mTransportKeyCallback;
    final View mView;

    static boolean isMediaKey(int i) {
        if (!(i == 79 || i == 130)) {
            switch (i) {
                case WidgetPriority.GPS /*85*/:
                case 86:
                case 87:
                case IjkMediaMeta.FF_PROFILE_H264_EXTENDED /*88*/:
                case 89:
                case 90:
                case 91:
                    break;
                default:
                    switch (i) {
                        case KEYCODE_MEDIA_PLAY /*126*/:
                        case 127:
                            break;
                        default:
                            return false;
                    }
            }
        }
        return true;
    }

    public TransportMediator(Activity activity, TransportPerformer transportPerformer) {
        this(activity, null, transportPerformer);
    }

    public TransportMediator(View view, TransportPerformer transportPerformer) {
        this(null, view, transportPerformer);
    }

    private TransportMediator(Activity activity, View view, TransportPerformer transportPerformer) {
        this.mListeners = new ArrayList<>();
        this.mTransportKeyCallback = new TransportMediatorCallback() {
            public final void a(KeyEvent keyEvent) {
                keyEvent.dispatch(TransportMediator.this.mKeyEventCallback);
            }

            public final void a(int i) {
                TransportMediator.this.mCallbacks.onAudioFocusChange(i);
            }

            public final long a() {
                return TransportMediator.this.mCallbacks.onGetCurrentPosition();
            }

            public final void a(long j) {
                TransportMediator.this.mCallbacks.onSeekTo(j);
            }
        };
        this.mKeyEventCallback = new Callback() {
            public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
                return false;
            }

            public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
                return false;
            }

            public boolean onKeyDown(int i, KeyEvent keyEvent) {
                if (TransportMediator.isMediaKey(i)) {
                    return TransportMediator.this.mCallbacks.onMediaButtonDown(i, keyEvent);
                }
                return false;
            }

            public boolean onKeyUp(int i, KeyEvent keyEvent) {
                if (TransportMediator.isMediaKey(i)) {
                    return TransportMediator.this.mCallbacks.onMediaButtonUp(i, keyEvent);
                }
                return false;
            }
        };
        this.mContext = activity != 0 ? activity : view.getContext();
        this.mCallbacks = transportPerformer;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mView = activity != 0 ? activity.getWindow().getDecorView() : view;
        this.mDispatcherState = KeyEventCompat.getKeyDispatcherState(this.mView);
        if (VERSION.SDK_INT >= 18) {
            this.mController = new TransportMediatorJellybeanMR2(this.mContext, this.mAudioManager, this.mView, this.mTransportKeyCallback);
        } else {
            this.mController = null;
        }
    }

    public Object getRemoteControlClient() {
        if (this.mController != null) {
            return this.mController.o;
        }
        return null;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return KeyEventCompat.dispatch(keyEvent, this.mKeyEventCallback, this.mDispatcherState, this);
    }

    public void registerStateListener(TransportStateListener transportStateListener) {
        this.mListeners.add(transportStateListener);
    }

    public void unregisterStateListener(TransportStateListener transportStateListener) {
        this.mListeners.remove(transportStateListener);
    }

    private TransportStateListener[] getListeners() {
        if (this.mListeners.size() <= 0) {
            return null;
        }
        TransportStateListener[] transportStateListenerArr = new TransportStateListener[this.mListeners.size()];
        this.mListeners.toArray(transportStateListenerArr);
        return transportStateListenerArr;
    }

    private void reportPlayingChanged() {
        TransportStateListener[] listeners = getListeners();
        if (listeners != null) {
            for (TransportStateListener onPlayingChanged : listeners) {
                onPlayingChanged.onPlayingChanged(this);
            }
        }
    }

    private void reportTransportControlsChanged() {
        TransportStateListener[] listeners = getListeners();
        if (listeners != null) {
            for (TransportStateListener onTransportControlsChanged : listeners) {
                onTransportControlsChanged.onTransportControlsChanged(this);
            }
        }
    }

    private void pushControllerState() {
        if (this.mController != null) {
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
            boolean onIsPlaying = this.mCallbacks.onIsPlaying();
            long onGetCurrentPosition = this.mCallbacks.onGetCurrentPosition();
            int onGetTransportControlFlags = this.mCallbacks.onGetTransportControlFlags();
            if (transportMediatorJellybeanMR2.o != null) {
                transportMediatorJellybeanMR2.o.setPlaybackState(onIsPlaying ? 3 : 1, onGetCurrentPosition, onIsPlaying ? 1.0f : 0.0f);
                transportMediatorJellybeanMR2.o.setTransportControlFlags(onGetTransportControlFlags);
            }
        }
    }

    public void refreshState() {
        pushControllerState();
        reportPlayingChanged();
        reportTransportControlsChanged();
    }

    public void startPlaying() {
        if (this.mController != null) {
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
            if (transportMediatorJellybeanMR2.q != 3) {
                transportMediatorJellybeanMR2.q = 3;
                transportMediatorJellybeanMR2.o.setPlaybackState(3);
            }
            if (transportMediatorJellybeanMR2.p) {
                transportMediatorJellybeanMR2.a();
            }
        }
        this.mCallbacks.onStart();
        pushControllerState();
        reportPlayingChanged();
    }

    public void pausePlaying() {
        if (this.mController != null) {
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
            if (transportMediatorJellybeanMR2.q == 3) {
                transportMediatorJellybeanMR2.q = 2;
                transportMediatorJellybeanMR2.o.setPlaybackState(2);
            }
            transportMediatorJellybeanMR2.b();
        }
        this.mCallbacks.onPause();
        pushControllerState();
        reportPlayingChanged();
    }

    public void stopPlaying() {
        if (this.mController != null) {
            TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
            if (transportMediatorJellybeanMR2.q != 1) {
                transportMediatorJellybeanMR2.q = 1;
                transportMediatorJellybeanMR2.o.setPlaybackState(1);
            }
            transportMediatorJellybeanMR2.b();
        }
        this.mCallbacks.onStop();
        pushControllerState();
        reportPlayingChanged();
    }

    public long getDuration() {
        return this.mCallbacks.onGetDuration();
    }

    public long getCurrentPosition() {
        return this.mCallbacks.onGetCurrentPosition();
    }

    public void seekTo(long j) {
        this.mCallbacks.onSeekTo(j);
    }

    public boolean isPlaying() {
        return this.mCallbacks.onIsPlaying();
    }

    public int getBufferPercentage() {
        return this.mCallbacks.onGetBufferPercentage();
    }

    public int getTransportControlFlags() {
        return this.mCallbacks.onGetTransportControlFlags();
    }

    public void destroy() {
        TransportMediatorJellybeanMR2 transportMediatorJellybeanMR2 = this.mController;
        transportMediatorJellybeanMR2.d();
        transportMediatorJellybeanMR2.c.getViewTreeObserver().removeOnWindowAttachListener(transportMediatorJellybeanMR2.h);
        transportMediatorJellybeanMR2.c.getViewTreeObserver().removeOnWindowFocusChangeListener(transportMediatorJellybeanMR2.i);
    }
}
