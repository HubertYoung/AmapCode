package com.autonavi.minimap.ajx3.loader.picasso;

import android.net.Uri;
import com.autonavi.core.network.inter.response.InputStreamResponse;
import com.autonavi.minimap.ajx3.loader.picasso.Downloader.Response;
import java.io.IOException;

public class ImageDownloader implements Downloader {
    private boy client = new boy();

    public Response load(Uri uri, int i) throws IOException {
        bpf bpf = new bpf();
        bpf.setUrl(uri.toString());
        InputStreamResponse inputStreamResponse = (InputStreamResponse) this.client.a((bph) bpf, InputStreamResponse.class);
        return new Response(inputStreamResponse.getBodyInputStream(), false, inputStreamResponse.getContentLength());
    }

    public void shutdown() {
        boy boy = this.client;
        if (bpv.a(4)) {
            bpv.c("ANet-NetworkClient", "shutdown");
        }
        synchronized (boy) {
            if (boy.c != null) {
                boy.c.a.a();
            }
        }
    }
}
