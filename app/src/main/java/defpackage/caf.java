package defpackage;

import android.support.annotation.IntRange;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import java.util.List;

/* renamed from: caf reason: default package */
/* compiled from: IPhotoUploadService */
public interface caf extends esc {

    /* renamed from: caf$a */
    /* compiled from: IPhotoUploadService */
    public interface a {
        void onPhotoSelection(List<ImageInfo> list);
    }

    void a(bid bid);

    void a(bid bid, String str);

    void a(bid bid, boolean z, @IntRange(from = 1, to = 6) int i, a aVar);
}
