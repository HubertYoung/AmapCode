package android.support.v4.media;

import android.media.VolumeProvider;

class VolumeProviderCompatApi21 {

    public interface Delegate {
        void onAdjustVolume(int i);

        void onSetVolumeTo(int i);
    }

    VolumeProviderCompatApi21() {
    }

    public static Object a(int i, int i2, int i3, final Delegate delegate) {
        return new VolumeProvider(i, i2, i3) {
            public final void onSetVolumeTo(int i) {
                delegate.onSetVolumeTo(i);
            }

            public final void onAdjustVolume(int i) {
                delegate.onAdjustVolume(i);
            }
        };
    }

    public static void a(Object obj, int i) {
        ((VolumeProvider) obj).setCurrentVolume(i);
    }
}
