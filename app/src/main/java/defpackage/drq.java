package defpackage;

import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.View;
import com.autonavi.minimap.component.ContainerView;
import com.autonavi.minimap.component.SearchButton;
import com.autonavi.minimap.component.SkipButton;
import com.autonavi.minimap.landingpage.LandingPageContainerView;
import com.autonavi.minimap.widget.GifMovieView.ErrorType;
import com.autonavi.minimap.widget.GifMovieView.GifHandler;

/* renamed from: drq reason: default package */
/* compiled from: EventListener */
public final class drq extends GifHandler implements drr {
    /* access modifiers changed from: private */
    public dmj a = null;

    public drq(dmj dmj) {
        this.a = dmj;
    }

    public final void onClick(View view) {
        if (view instanceof SkipButton) {
            this.a.d();
            return;
        }
        if ((view instanceof ContainerView) || (view instanceof SearchButton)) {
            this.a.e();
        }
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (view instanceof LandingPageContainerView) {
            dnj.a().b();
            this.a.a(false, true);
        }
        return true;
    }

    public final void a() {
        dnj.a().b();
        this.a.a(false, true);
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        ahm.a(new Runnable() {
            public final void run() {
                drq.this.a.c();
            }
        });
    }

    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        this.a.a(i, i2);
        return false;
    }

    public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        if (i == 3) {
            this.a.b();
        }
        return false;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        this.a.a(false, false);
    }

    public final void onError(ErrorType errorType, String str, Throwable th) {
        super.onError(errorType, str, th);
        this.a.a(errorType != null ? errorType.ordinal() : 0, str, th != null ? th.getMessage() : "");
    }
}
