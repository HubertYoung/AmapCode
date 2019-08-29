package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.widget.view.video.AjxVideo;
import com.autonavi.minimap.ajx3.widget.view.video.ui.ScreenState;
import com.autonavi.minimap.ajx3.widget.view.video.ui.VideoView.OnPlayStateChangedListener;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;

public class VideoProperty extends BaseProperty<AjxVideo> {
    private static final String EVENT_ONENDED = "onended";
    private static final String EVENT_ONERROR = "onerror";
    private static final String EVENT_ONPAUSE = "onpause";
    private static final String EVENT_ONPLAY = "onplay";
    private static final String EVENT_ONPLAYING = "onplaying";
    private static final String PLAYSTATE_PAUSE = "pause";
    private static final String PLAYSTATE_PLAY = "play";
    private static final String PLAYSTATE_STOP = "stop";
    private static final String PROPERTY_AUTOPLAY = "autoplay";
    private static final String PROPERTY_BRIGHTNESS = "windowbrightness";
    private static final String PROPERTY_CONTROLS = "controls";
    private static final String PROPERTY_CURRENTTIME = "currenttime";
    private static final String PROPERTY_DURATION = "duration";
    private static final String PROPERTY_FULLSCREEN_BRGOUNDCOLOR = "fullscreen-background-color";
    private static final String PROPERTY_LOOP = "loop";
    private static final String PROPERTY_MUTED = "muted";
    private static final String PROPERTY_NETWORKSTATE = "networkstate";
    private static final String PROPERTY_PLAYSTATE = "playstate";
    private static final String PROPERTY_POSTER = "poster";
    private static final String PROPERTY_SCREENSTATE = "screenstate";
    private static final String PROPERTY_SRC = "src";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_VOLUME = "volume";
    private static final String SCREENSTATE_FULLSCREEN = "fullscreen";
    private static final String SCREENSTATE_NORMAL = "normal";
    /* access modifiers changed from: private */
    public boolean isLoop = false;

    public VideoProperty(@NonNull AjxVideo ajxVideo, @NonNull IAjxContext iAjxContext) {
        super(ajxVideo, iAjxContext);
        ((AjxVideo) this.mView).setUiStateChangedListener(new OnPlayStateChangedListener() {
            private String mCurrentState = null;
            private long mLastPositon = -1;

            public void onLoading() {
                Utils.log("------onLoading-------");
                if (!"play".equals(this.mCurrentState)) {
                    VideoProperty.this.invokeJsEvent(VideoProperty.EVENT_ONPLAY, null, null);
                }
                this.mCurrentState = null;
            }

            public void onPlaying() {
                Utils.log("------onPlaying-------");
                if (!"stop".equals(this.mCurrentState) || !VideoProperty.this.isLoop) {
                    VideoProperty.this.invokeJsEvent(VideoProperty.EVENT_ONPLAYING, null, null);
                }
                ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_PLAYSTATE, "play", false, true, true, false);
                this.mCurrentState = "play";
            }

            public void onPause() {
                Utils.log("------onPause-------");
                VideoProperty.this.invokeJsEvent(VideoProperty.EVENT_ONPAUSE, null, null);
                ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_PLAYSTATE, "pause", false, true, true, false);
                this.mCurrentState = "pause";
            }

            public void onComplete() {
                Utils.log("------onComplete-------");
                if (!VideoProperty.this.isLoop) {
                    VideoProperty.this.invokeJsEvent(VideoProperty.EVENT_ONENDED, null, null);
                }
                ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_PLAYSTATE, "stop", false, true, true, false);
                this.mCurrentState = "stop";
            }

            public void onStop() {
                if (!"stop".equals(this.mCurrentState) && this.mCurrentState != null) {
                    VideoProperty.this.invokeJsEvent(VideoProperty.EVENT_ONENDED, null, null);
                }
                this.mCurrentState = "stop";
            }

            public void onError() {
                Utils.log("------onError-------");
                VideoProperty.this.invokeJsEvent(VideoProperty.EVENT_ONERROR, null, null);
                this.mCurrentState = "stop";
            }

            public void onPositionUpdated(long j) {
                long j2 = j / 1000;
                if (j != -1 && this.mLastPositon != j2) {
                    Utils.log("------onPositionUpdated------- position = ".concat(String.valueOf(j2)));
                    ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_CURRENTTIME, String.valueOf(j2), false, true, true, false);
                    this.mLastPositon = j2;
                }
            }

            public void onDurationUpdated(long j) {
                Utils.log("------onDurationUpdated------- duration = ".concat(String.valueOf(j)));
                StringBuilder sb = new StringBuilder();
                sb.append(j / 1000);
                ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_DURATION, sb.toString(), false, true, true, false);
            }

            public void onVolumeUpdated(float f) {
                Utils.log("------onVolumeUpdated------- volume".concat(String.valueOf(f)));
                ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_VOLUME, String.valueOf(f), false, true, true, false);
            }

            public void onBrightnessUpdated(float f) {
                Utils.log("------onBrightnessUpdated-------  brightness = ".concat(String.valueOf(f)));
                ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_BRIGHTNESS, String.valueOf(f), false, true, true, false);
            }

            public void onScreenStateUpdated(int i) {
                if (ScreenState.isFullScreen(i)) {
                    Utils.log("------onScreenStateUpdated-------  screenState = FullScreen");
                    ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_SCREENSTATE, "fullscreen", false, true, true, false);
                    return;
                }
                if (ScreenState.isNormal(i)) {
                    Utils.log("------onScreenStateUpdated-------  screenState = Normal");
                    ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_SCREENSTATE, "normal", false, true, true, false);
                }
            }

            public void onMutedUpdate(boolean z) {
                Utils.log("------onMutedUpdate-------  muted = ".concat(String.valueOf(z)));
                ((AjxVideo) VideoProperty.this.mView).setAttribute(VideoProperty.PROPERTY_MUTED, String.valueOf(z), false, true, true, false);
            }

            public void onDetachedFromWindow() {
                Utils.log("------onDetachedFromWindow-------  ");
                VideoProperty.this.invokeJsEvent("onDetachedFromWindow", null, null);
            }
        });
        ((AjxVideo) this.mView).setVideoClickListener(new OnClickListener() {
            public void onClick(View view) {
                if ("fullscreen".equals(VideoProperty.this.getNode().getAttributeValue(VideoProperty.PROPERTY_SCREENSTATE))) {
                    Utils.log("------onClick------- ");
                    VideoProperty.this.invokeJsEvent("click", null, null);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void invokeJsEvent(String str, String str2, String str3) {
        Parcel parcel;
        long nodeId = this.mAjxContext.getDomTree().getNodeId(this.mView);
        if (str2 == null || str3 == null) {
            parcel = null;
        } else {
            parcel = new Parcel();
            parcel.writeInt(2);
            parcel.writeString(str2);
            parcel.writeString(str3);
        }
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(str).setNodeId(nodeId).setAttribute(parcel).build());
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        if (i != 1056964680) {
            super.updateStyle(i, obj, z);
        } else {
            updateFillMode(obj);
        }
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r3, java.lang.Object r4) {
        /*
            r2 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "updateAttribyte key = "
            r0.<init>(r1)
            r0.append(r3)
            java.lang.String r1 = "  value = "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r1 = " mView = "
            r0.append(r1)
            android.view.View r1 = r2.mView
            com.autonavi.minimap.ajx3.widget.view.video.AjxVideo r1 = (com.autonavi.minimap.ajx3.widget.view.video.AjxVideo) r1
            int r1 = r1.hashCode()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.autonavi.minimap.ajx3.widget.view.video.util.Utils.log(r0)
            int r0 = r3.hashCode()
            switch(r0) {
                case -1859053091: goto L_0x00b8;
                case -1589650367: goto L_0x00ac;
                case -982450867: goto L_0x00a2;
                case -810883302: goto L_0x0096;
                case -566933834: goto L_0x008c;
                case -24615003: goto L_0x0081;
                case 114148: goto L_0x0076;
                case 3327652: goto L_0x006c;
                case 104264043: goto L_0x0061;
                case 110371416: goto L_0x0056;
                case 158766550: goto L_0x004a;
                case 602188742: goto L_0x003e;
                case 1439562083: goto L_0x0033;
                default: goto L_0x0031;
            }
        L_0x0031:
            goto L_0x00c2
        L_0x0033:
            java.lang.String r0 = "autoplay"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 3
            goto L_0x00c3
        L_0x003e:
            java.lang.String r0 = "currenttime"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 10
            goto L_0x00c3
        L_0x004a:
            java.lang.String r0 = "fullscreen-background-color"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 12
            goto L_0x00c3
        L_0x0056:
            java.lang.String r0 = "title"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 1
            goto L_0x00c3
        L_0x0061:
            java.lang.String r0 = "muted"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 11
            goto L_0x00c3
        L_0x006c:
            java.lang.String r0 = "loop"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 4
            goto L_0x00c3
        L_0x0076:
            java.lang.String r0 = "src"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 0
            goto L_0x00c3
        L_0x0081:
            java.lang.String r0 = "screenstate"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 7
            goto L_0x00c3
        L_0x008c:
            java.lang.String r0 = "controls"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 5
            goto L_0x00c3
        L_0x0096:
            java.lang.String r0 = "volume"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 8
            goto L_0x00c3
        L_0x00a2:
            java.lang.String r0 = "poster"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 2
            goto L_0x00c3
        L_0x00ac:
            java.lang.String r0 = "windowbrightness"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 9
            goto L_0x00c3
        L_0x00b8:
            java.lang.String r0 = "playstate"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x00c2
            r0 = 6
            goto L_0x00c3
        L_0x00c2:
            r0 = -1
        L_0x00c3:
            switch(r0) {
                case 0: goto L_0x0181;
                case 1: goto L_0x0177;
                case 2: goto L_0x016d;
                case 3: goto L_0x0159;
                case 4: goto L_0x0145;
                case 5: goto L_0x0131;
                case 6: goto L_0x0127;
                case 7: goto L_0x011d;
                case 8: goto L_0x010e;
                case 9: goto L_0x00ff;
                case 10: goto L_0x00ec;
                case 11: goto L_0x00d8;
                case 12: goto L_0x00ca;
                default: goto L_0x00c6;
            }
        L_0x00c6:
            super.updateAttribute(r3, r4)
            return
        L_0x00ca:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x00d7
            java.lang.String r4 = (java.lang.String) r4
            int r3 = android.graphics.Color.parseColor(r4)
            r2.updateFullScreenBackgroundColor(r3)
        L_0x00d7:
            return
        L_0x00d8:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 != 0) goto L_0x00e0
            boolean r3 = r4 instanceof java.lang.Boolean
            if (r3 == 0) goto L_0x00eb
        L_0x00e0:
            java.lang.String r3 = r4.toString()
            boolean r3 = java.lang.Boolean.parseBoolean(r3)
            r2.updateMuted(r3)
        L_0x00eb:
            return
        L_0x00ec:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x00fe
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ NumberFormatException -> 0x00fe }
            long r3 = java.lang.Long.parseLong(r4)     // Catch:{ NumberFormatException -> 0x00fe }
            r0 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 * r0
            r2.updateCurrentTime(r3)     // Catch:{ NumberFormatException -> 0x00fe }
            return
        L_0x00fe:
            return
        L_0x00ff:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x010d
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ NumberFormatException -> 0x010d }
            float r3 = java.lang.Float.parseFloat(r4)     // Catch:{ NumberFormatException -> 0x010d }
            r2.updateBrightness(r3)     // Catch:{ NumberFormatException -> 0x010d }
            return
        L_0x010d:
            return
        L_0x010e:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x011c
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ NumberFormatException -> 0x011c }
            float r3 = java.lang.Float.parseFloat(r4)     // Catch:{ NumberFormatException -> 0x011c }
            r2.updateVolume(r3)     // Catch:{ NumberFormatException -> 0x011c }
            return
        L_0x011c:
            return
        L_0x011d:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x0126
            java.lang.String r4 = (java.lang.String) r4
            r2.updateScreenState(r4)
        L_0x0126:
            return
        L_0x0127:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x0130
            java.lang.String r4 = (java.lang.String) r4
            r2.updatePlayState(r4)
        L_0x0130:
            return
        L_0x0131:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 != 0) goto L_0x0139
            boolean r3 = r4 instanceof java.lang.Boolean
            if (r3 == 0) goto L_0x0144
        L_0x0139:
            java.lang.String r3 = r4.toString()
            boolean r3 = java.lang.Boolean.parseBoolean(r3)
            r2.updateControls(r3)
        L_0x0144:
            return
        L_0x0145:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 != 0) goto L_0x014d
            boolean r3 = r4 instanceof java.lang.Boolean
            if (r3 == 0) goto L_0x0158
        L_0x014d:
            java.lang.String r3 = r4.toString()
            boolean r3 = java.lang.Boolean.parseBoolean(r3)
            r2.updateLoop(r3)
        L_0x0158:
            return
        L_0x0159:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 != 0) goto L_0x0161
            boolean r3 = r4 instanceof java.lang.Boolean
            if (r3 == 0) goto L_0x016c
        L_0x0161:
            java.lang.String r3 = r4.toString()
            boolean r3 = java.lang.Boolean.parseBoolean(r3)
            r2.updateAutoPlay(r3)
        L_0x016c:
            return
        L_0x016d:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x0176
            java.lang.String r4 = (java.lang.String) r4
            r2.updateVideoThumb(r4)
        L_0x0176:
            return
        L_0x0177:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x0180
            java.lang.String r4 = (java.lang.String) r4
            r2.updateVideoTitle(r4)
        L_0x0180:
            return
        L_0x0181:
            boolean r3 = r4 instanceof java.lang.String
            if (r3 == 0) goto L_0x018a
            java.lang.String r4 = (java.lang.String) r4
            r2.updateVideoSrc(r4)
        L_0x018a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.VideoProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateVideoSrc(String str) {
        ((AjxVideo) this.mView).setSrc(str);
    }

    private void updateVideoTitle(String str) {
        ((AjxVideo) this.mView).setTitle(str);
    }

    private void updateVideoThumb(String str) {
        ((AjxVideo) this.mView).setThumb(str);
    }

    private void updateAutoPlay(boolean z) {
        ((AjxVideo) this.mView).setAutoPlay(z);
    }

    private void updateControls(boolean z) {
        ((AjxVideo) this.mView).setControls(z);
    }

    private void updateScreenState(String str) {
        if ("normal".equals(str)) {
            ((AjxVideo) this.mView).updateScreenState(1);
            return;
        }
        if ("fullscreen".equals(str)) {
            ((AjxVideo) this.mView).updateScreenState(2);
        }
    }

    private void updateLoop(boolean z) {
        ((AjxVideo) this.mView).setLoop(z);
        this.isLoop = z;
    }

    private void updateVolume(float f) {
        ((AjxVideo) this.mView).setVolume(f);
    }

    private void updateBrightness(float f) {
        ((AjxVideo) this.mView).setBrightness(f);
    }

    private void updateCurrentTime(long j) {
        ((AjxVideo) this.mView).seekTo(j);
    }

    private void updatePlayState(String str) {
        if ("play".equals(str)) {
            ((AjxVideo) this.mView).requestPlay();
        } else if ("pause".equals(str)) {
            ((AjxVideo) this.mView).requestPause();
        } else {
            if ("stop".equals(str)) {
                ((AjxVideo) this.mView).requestStop();
            }
        }
    }

    private void updateMuted(boolean z) {
        ((AjxVideo) this.mView).setMuted(z);
    }

    private void updateFullScreenBackgroundColor(int i) {
        ((AjxVideo) this.mView).setFullScreenBackgroundColor(i);
    }

    private void updateFillMode(Object obj) {
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            if (1056964746 == intValue) {
                ((AjxVideo) this.mView).setScaleType(1);
            } else if (1056964744 == intValue) {
                ((AjxVideo) this.mView).setScaleType(2);
            } else if (1056964745 == intValue) {
                ((AjxVideo) this.mView).setScaleType(3);
            } else if (1056964747 == intValue) {
                ((AjxVideo) this.mView).setScaleType(0);
            }
        }
    }
}
