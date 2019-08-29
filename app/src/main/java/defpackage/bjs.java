package defpackage;

import android.net.Uri;
import com.autonavi.common.imageloader.IDownloader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.core.network.inter.response.InputStreamResponse;

/* renamed from: bjs reason: default package */
/* compiled from: ImageDownloader */
public final class bjs implements IDownloader {
    private boy a;

    public final void a(boy boy) {
        this.a = boy;
    }

    public final a a(Uri uri) {
        bpf bpf = new bpf();
        bpf.setUrl(uri.toString());
        InputStreamResponse inputStreamResponse = (InputStreamResponse) this.a.a((bph) bpf, InputStreamResponse.class);
        a aVar = new a(inputStreamResponse.getBodyInputStream(), LoadedFrom.NETWORK);
        aVar.e = inputStreamResponse.getContentLength();
        return aVar;
    }
}
