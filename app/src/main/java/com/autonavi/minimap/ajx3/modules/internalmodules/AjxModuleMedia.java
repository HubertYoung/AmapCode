package com.autonavi.minimap.ajx3.modules.internalmodules;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.media.AudioManager;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;

@AjxModule("ajx.media")
public class AjxModuleMedia extends AbstractModule {
    private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    /* access modifiers changed from: private */
    public int mCurrVolume = -1;
    private IntentFilter mFilter = new IntentFilter();
    /* access modifiers changed from: private */
    public boolean mHasModuleDestroy = false;
    /* access modifiers changed from: private */
    public JsFunctionCallback mHeadSetCallback;
    /* access modifiers changed from: private */
    public JsFunctionCallback mMediaButtonCallback;
    private OnMediaButtonEventListener mMediaButtonEventListener;
    private HeadsetReceiver mReceiver = null;
    /* access modifiers changed from: private */
    public JsFunctionCallback mVolumeCallback;

    public class HeadsetReceiver extends BroadcastReceiver {
        public HeadsetReceiver() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0039  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0092  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r6, android.content.Intent r7) {
            /*
                r5 = this;
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                boolean r6 = r6.mHasModuleDestroy
                if (r6 == 0) goto L_0x0009
                return
            L_0x0009:
                java.lang.String r6 = r7.getAction()
                int r0 = r6.hashCode()
                r1 = -1940635523(0xffffffff8c54407d, float:-1.6351292E-31)
                r2 = -1
                r3 = 1
                r4 = 0
                if (r0 == r1) goto L_0x0029
                r1 = -1676458352(0xffffffff9c134690, float:-4.872942E-22)
                if (r0 == r1) goto L_0x001f
                goto L_0x0033
            L_0x001f:
                java.lang.String r0 = "android.intent.action.HEADSET_PLUG"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0033
                r6 = 0
                goto L_0x0034
            L_0x0029:
                java.lang.String r0 = "android.media.VOLUME_CHANGED_ACTION"
                boolean r6 = r6.equals(r0)
                if (r6 == 0) goto L_0x0033
                r6 = 1
                goto L_0x0034
            L_0x0033:
                r6 = -1
            L_0x0034:
                switch(r6) {
                    case 0: goto L_0x0092;
                    case 1: goto L_0x0039;
                    default: goto L_0x0037;
                }
            L_0x0037:
                goto L_0x00dd
            L_0x0039:
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                com.autonavi.minimap.ajx3.core.JsFunctionCallback r6 = r6.mVolumeCallback
                if (r6 != 0) goto L_0x0042
                return
            L_0x0042:
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                android.content.Context r6 = r6.getNativeContext()
                if (r6 == 0) goto L_0x00dd
                java.lang.String r6 = "android.media.EXTRA_VOLUME_STREAM_TYPE"
                int r6 = r7.getIntExtra(r6, r2)
                r7 = 3
                if (r6 != r7) goto L_0x00dd
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                android.content.Context r6 = r6.getNativeContext()
                java.lang.String r0 = "audio"
                java.lang.Object r6 = r6.getSystemService(r0)
                android.media.AudioManager r6 = (android.media.AudioManager) r6
                int r0 = r6.getStreamVolume(r7)
                int r6 = r6.getStreamMaxVolume(r7)
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r7 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                int r7 = r7.mCurrVolume
                if (r0 == r7) goto L_0x008c
                if (r0 <= r6) goto L_0x0075
                r0 = r6
                goto L_0x0078
            L_0x0075:
                if (r0 >= 0) goto L_0x0078
                r0 = 0
            L_0x0078:
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r7 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                com.autonavi.minimap.ajx3.core.JsFunctionCallback r7 = r7.mVolumeCallback
                java.lang.Object[] r1 = new java.lang.Object[r3]
                int r2 = r0 * 100
                int r2 = r2 / r6
                java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
                r1[r4] = r6
                r7.callback(r1)
            L_0x008c:
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                r6.mCurrVolume = r0
                goto L_0x00dd
            L_0x0092:
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                com.autonavi.minimap.ajx3.core.JsFunctionCallback r6 = r6.mHeadSetCallback
                if (r6 != 0) goto L_0x009b
                return
            L_0x009b:
                java.lang.String r6 = "state"
                boolean r6 = r7.hasExtra(r6)
                if (r6 == 0) goto L_0x00dd
                java.lang.String r6 = "state"
                r0 = 2
                int r6 = r7.getIntExtra(r6, r0)
                if (r6 != 0) goto L_0x00c4
                boolean r6 = r5.isInitialStickyBroadcast()
                if (r6 != 0) goto L_0x00dd
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                com.autonavi.minimap.ajx3.core.JsFunctionCallback r6 = r6.mHeadSetCallback
                java.lang.Object[] r7 = new java.lang.Object[r3]
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                r7[r4] = r0
                r6.callback(r7)
                return
            L_0x00c4:
                java.lang.String r6 = "state"
                int r6 = r7.getIntExtra(r6, r0)
                if (r6 != r3) goto L_0x00dd
                com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia r6 = com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.this
                com.autonavi.minimap.ajx3.core.JsFunctionCallback r6 = r6.mHeadSetCallback
                java.lang.Object[] r7 = new java.lang.Object[r3]
                java.lang.Boolean r0 = java.lang.Boolean.TRUE
                r7[r4] = r0
                r6.callback(r7)
                return
            L_0x00dd:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleMedia.HeadsetReceiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    @AjxMethod("onMediaButtonEvent")
    public void onMediaButtonEvent(String[] strArr, JsFunctionCallback jsFunctionCallback) {
    }

    @AjxMethod("setMediaSystemCenterInfo")
    public void setMediaSystemCenterInfo(String str) {
    }

    public AjxModuleMedia(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mFilter.addAction("android.intent.action.HEADSET_PLUG");
        this.mFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
    }

    @AjxMethod("onEarphoneInOut")
    public void onEarphoneInOut(JsFunctionCallback jsFunctionCallback) {
        if (this.mReceiver == null) {
            this.mReceiver = new HeadsetReceiver();
            getNativeContext().registerReceiver(this.mReceiver, this.mFilter);
        }
        this.mHeadSetCallback = jsFunctionCallback;
    }

    @AjxMethod("onVolumeChange")
    public void onVolumeChange(JsFunctionCallback jsFunctionCallback) {
        if (this.mReceiver == null) {
            this.mReceiver = new HeadsetReceiver();
            getNativeContext().registerReceiver(this.mReceiver, this.mFilter);
        }
        this.mCurrVolume = ((AudioManager) getNativeContext().getSystemService("audio")).getStreamVolume(3);
        this.mVolumeCallback = jsFunctionCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "isEarphoneIn")
    public boolean isEarphoneIn() {
        return ((AudioManager) getNativeContext().getSystemService("audio")).isWiredHeadsetOn();
    }

    @AjxMethod(invokeMode = "sync", value = "getVolume")
    public int getVolume() {
        AudioManager audioManager = (AudioManager) getNativeContext().getSystemService("audio");
        int streamVolume = audioManager.getStreamVolume(3);
        int streamMaxVolume = audioManager.getStreamMaxVolume(3);
        if (streamVolume > streamMaxVolume) {
            streamVolume = streamMaxVolume;
        } else if (streamVolume < 0) {
            streamVolume = 0;
        }
        return (streamVolume * 100) / streamMaxVolume;
    }

    @AjxMethod("setVolume")
    public void setVolume(int i) {
        if (i > 100) {
            i = 100;
        } else if (i < 0) {
            i = 0;
        }
        AudioManager audioManager = (AudioManager) getNativeContext().getSystemService("audio");
        audioManager.setStreamVolume(3, (i * audioManager.getStreamMaxVolume(3)) / 100, 0);
    }

    private void unListenVolume() {
        if (this.mReceiver != null) {
            getNativeContext().unregisterReceiver(this.mReceiver);
        }
    }

    @AjxMethod("onMediaButtonEventForAndroid")
    public void onMediaButtonEventForAndroid(JsFunctionCallback jsFunctionCallback) {
        this.mMediaButtonCallback = jsFunctionCallback;
        if (this.mMediaButtonEventListener == null) {
            this.mMediaButtonEventListener = new OnMediaButtonEventListener() {
                public void onMediaButtonEvent(int i, int i2) {
                    if (AjxModuleMedia.this.mMediaButtonCallback != null) {
                        AjxModuleMedia.this.mMediaButtonCallback.callback(Integer.valueOf(i), Integer.valueOf(i2));
                    }
                }
            };
        }
        MediaHelper.addMediaButtonEvent(getNativeContext(), this.mMediaButtonEventListener);
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        unListenVolume();
        this.mHeadSetCallback = null;
        this.mVolumeCallback = null;
        this.mHasModuleDestroy = true;
        if (this.mMediaButtonEventListener != null) {
            MediaHelper.removeMediaButtonEvent(this.mMediaButtonEventListener);
            this.mMediaButtonEventListener = null;
        }
    }
}
