package android.support.v4.media.session;

import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;

class MediaSessionCompatApi19 {

    static class OnMetadataUpdateListener<T extends Callback> implements android.media.RemoteControlClient.OnMetadataUpdateListener {
        protected final T a;

        public OnMetadataUpdateListener(T t) {
            this.a = t;
        }

        public void onMetadataUpdate(int i, Object obj) {
            if (i == 268435457 && (obj instanceof Rating)) {
                this.a.a(obj);
            }
        }
    }

    MediaSessionCompatApi19() {
    }

    public static void a(Object obj, long j) {
        RemoteControlClient remoteControlClient = (RemoteControlClient) obj;
        int a = MediaSessionCompatApi18.a(j);
        if ((j & 128) != 0) {
            a |= 512;
        }
        remoteControlClient.setTransportControlFlags(a);
    }

    public static Object a(Callback callback) {
        return new OnMetadataUpdateListener(callback);
    }

    public static void a(Object obj, Bundle bundle, long j) {
        MetadataEditor editMetadata = ((RemoteControlClient) obj).editMetadata(true);
        MediaSessionCompatApi14.a(bundle, editMetadata);
        if (bundle != null) {
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_YEAR)) {
                editMetadata.putLong(8, bundle.getLong(MediaMetadataCompat.METADATA_KEY_YEAR));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_RATING)) {
                editMetadata.putObject(101, bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_RATING));
            }
            if (bundle.containsKey(MediaMetadataCompat.METADATA_KEY_USER_RATING)) {
                editMetadata.putObject(268435457, bundle.getParcelable(MediaMetadataCompat.METADATA_KEY_USER_RATING));
            }
        }
        if ((j & 128) != 0) {
            editMetadata.addEditableKey(268435457);
        }
        editMetadata.apply();
    }

    public static void a(Object obj, Object obj2) {
        ((RemoteControlClient) obj).setMetadataUpdateListener((android.media.RemoteControlClient.OnMetadataUpdateListener) obj2);
    }
}
