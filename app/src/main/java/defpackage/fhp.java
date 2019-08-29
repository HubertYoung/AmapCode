package defpackage;

import android.graphics.SurfaceTexture;
import android.support.annotation.RequiresApi;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import pl.droidsonroids.gif.GifTextureView.a;

@RequiresApi(14)
/* renamed from: fhp reason: default package */
/* compiled from: PlaceholderDrawingSurfaceTextureListener */
public final class fhp implements SurfaceTextureListener {
    private final a a;

    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public fhp(a aVar) {
        this.a = aVar;
    }

    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Surface surface = new Surface(surfaceTexture);
        surface.unlockCanvasAndPost(surface.lockCanvas(null));
        surface.release();
    }
}
