package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.search.album.page.AlbumPreviewPage;
import com.autonavi.map.search.album.upload.inner.IRealSceneUploadService;
import com.autonavi.map.search.album.upload.inner.IRealSceneUploadService.IRealSceneUploadServiceBinder;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.sdk.location.LocationInstrument;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: bvh reason: default package */
/* compiled from: AlbumPreviewPresenter */
public final class bvh extends AbstractBasePresenter<AlbumPreviewPage> {
    public bvm a;
    public List<ImageInfo> b;
    public List<ImageInfo> c;
    public ImageInfo d;
    public cao e;
    public List<cak> f;
    public Map<String, List<ImageInfo>> g;
    public String h;
    public String i;
    /* access modifiers changed from: private */
    public IRealSceneUploadService j;
    private Context k;
    private ServiceConnection l = new ServiceConnection() {
        @SuppressFBWarnings({"BC_UNCONFIRMED_CAST"})
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bvh.this.j = ((IRealSceneUploadServiceBinder) iBinder).getService();
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            StringBuilder sb = new StringBuilder();
            sb.append(latestPosition.getLongitude());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(latestPosition.getLatitude());
            String sb4 = sb3.toString();
            String str = bvh.this.e != null ? bvh.this.e.a : null;
            for (ImageInfo imageInfo : bvh.this.c) {
                imageInfo.k = sb2;
                imageInfo.l = sb4;
                imageInfo.m = str;
            }
            bvh.this.j;
            bvh.this.c;
            AMapLog.i("lz", "onServiceConnected");
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            bvh.this.j = null;
        }
    };

    public bvh(AlbumPreviewPage albumPreviewPage) {
        super(albumPreviewPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.k = ((AlbumPreviewPage) this.mPage).getContext();
    }

    public final void onStart() {
        super.onStart();
        ((AlbumPreviewPage) this.mPage).requestScreenOrientation(1);
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (pageBundle != null && pageBundle.getBoolean("PHOTO_FRAGMENT_CLOSE")) {
            ((AlbumPreviewPage) this.mPage).setResult(ResultType.OK, pageBundle);
            ((AlbumPreviewPage) this.mPage).finish();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((AlbumPreviewPage) this.mPage).b) {
            return super.onBackPressed();
        }
        if (((AlbumPreviewPage) this.mPage).c == 1) {
            ((AlbumPreviewPage) this.mPage).b();
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (((AlbumPreviewPage) this.mPage).c != 2) {
            return super.onBackPressed();
        } else {
            ((AlbumPreviewPage) this.mPage).a();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    public final void a(int i2) {
        this.a.a(i2);
    }

    public final int a() {
        int i2 = 0;
        for (int i3 = 0; i3 < this.c.size(); i3++) {
            if (this.c.get(i3).g) {
                i2++;
            }
        }
        return i2;
    }

    public final List<ImageInfo> b() {
        Iterator<ImageInfo> it = this.c.iterator();
        while (it.hasNext()) {
            if (!it.next().g) {
                it.remove();
            }
        }
        return this.c;
    }

    public final void a(List<ImageInfo> list) {
        this.c = list;
        ((AlbumPreviewPage) this.mPage).setResult(ResultType.CANCEL, new PageBundle());
        ((AlbumPreviewPage) this.mPage).finish();
        if (list.size() == 1) {
            bvj.a((AbstractBasePage) this.mPage, list.get(0).b, this.e, this.i);
            return;
        }
        if (list.size() > 1 && !TextUtils.isEmpty(this.h)) {
            Intent intent = new Intent();
            intent.setClassName("com.autonavi.minimap", this.h);
            this.k.bindService(intent, this.l, 1);
        }
    }

    public final void a(ImageInfo imageInfo) {
        if (this.g != null && this.g.size() != 0 && this.f != null && this.f.size() != 0) {
            for (Entry next : this.g.entrySet()) {
                List list = (List) next.getValue();
                if (list.contains(imageInfo)) {
                    String str = (String) next.getKey();
                    boolean z = false;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= list.size()) {
                            z = true;
                            break;
                        } else if (!((ImageInfo) list.get(i2)).g) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    for (cak next2 : this.f) {
                        if (next2.a.equals(str)) {
                            next2.b = z;
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }
}
