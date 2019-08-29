package defpackage;

import android.content.Context;
import android.media.ExifInterface;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import java.io.IOException;

/* renamed from: bjp reason: default package */
/* compiled from: FileRequestHandler */
public final class bjp extends bjm {
    public bjp(Context context) {
        super(context);
    }

    public final boolean a(bjz bjz) {
        return "file".equals(bjz.d.getScheme());
    }

    public final a a(bjz bjz, int i) throws IOException {
        return new a(null, b(bjz), LoadedFrom.DISK, new ExifInterface(bjz.d.getPath()).getAttributeInt("Orientation", 1));
    }
}
