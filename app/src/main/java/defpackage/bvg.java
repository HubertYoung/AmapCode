package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.search.album.page.AlbumMainPage;
import com.autonavi.map.search.album.upload.inner.IRealSceneUploadService;
import com.autonavi.map.search.album.upload.inner.IRealSceneUploadService.IRealSceneUploadServiceBinder;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import de.greenrobot.event.EventBus;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Map;

/* renamed from: bvg reason: default package */
/* compiled from: AlbumMainPresenter */
public final class bvg extends AbstractBasePresenter<AlbumMainPage> implements b {
    public cao a;
    public bvk b;
    public String c;
    public String d;
    public cah e;
    public a f = new a() {
        public final void a(final List<cak> list, final Map<String, List<ImageInfo>> map, final List<ImageInfo> list2, boolean z) {
            AlbumMainPage albumMainPage = (AlbumMainPage) bvg.this.mPage;
            if (albumMainPage.c != null && albumMainPage.c.isShowing()) {
                albumMainPage.c.dismiss();
            }
            if (z && albumMainPage.g) {
                ToastHelper.showToast(albumMainPage.getString(R.string.album_filter_finish));
            }
            bvg.this.i.post(new Runnable() {
                public final void run() {
                    AlbumMainPage albumMainPage = (AlbumMainPage) bvg.this.mPage;
                    List list = list;
                    Map map = map;
                    List list2 = list2;
                    if (list == null || map == null || map.size() == 0) {
                        albumMainPage.b.setVisibility(0);
                        albumMainPage.a.setActionTextVisibility(4);
                        return;
                    }
                    albumMainPage.e.addData(list, map);
                    if (list2 != null) {
                        albumMainPage.e.setSelectedData(list2);
                        albumMainPage.a(list2.size());
                    }
                }
            });
        }
    };
    private Context g;
    /* access modifiers changed from: private */
    public List<ImageInfo> h;
    /* access modifiers changed from: private */
    public Handler i = new Handler();
    private MapSharePreference j = new MapSharePreference(SharePreferenceName.SharedPreferences);
    /* access modifiers changed from: private */
    public IRealSceneUploadService k;
    private ServiceConnection l = new ServiceConnection() {
        @SuppressFBWarnings({"BC_UNCONFIRMED_CAST"})
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bvg.this.k = ((IRealSceneUploadServiceBinder) iBinder).getService();
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            StringBuilder sb = new StringBuilder();
            sb.append(latestPosition.getLongitude());
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(latestPosition.getLatitude());
            String sb4 = sb3.toString();
            String str = bvg.this.a != null ? bvg.this.a.a : null;
            for (ImageInfo imageInfo : bvg.this.h) {
                imageInfo.k = sb2;
                imageInfo.l = sb4;
                imageInfo.m = str;
            }
            bvg.this.k;
            bvg.this.h;
            AMapLog.i("lz", "onServiceConnected");
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            bvg.this.k = null;
        }
    };

    public bvg(AlbumMainPage albumMainPage) {
        super(albumMainPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.g = ((AlbumMainPage) this.mPage).getContext();
    }

    public final void onStart() {
        super.onStart();
        ((AlbumMainPage) this.mPage).requestScreenOrientation(1);
        super.onStart();
        if (((AlbumMainPage) this.mPage).i) {
            if (!((AlbumMainPage) this.mPage).j.isEmpty()) {
                ((AlbumMainPage) this.mPage).e.updateSelectedData(((AlbumMainPage) this.mPage).j);
                ((AlbumMainPage) this.mPage).j.clear();
            }
            ((AlbumMainPage) this.mPage).a(((AlbumMainPage) this.mPage).e.getSelectedData().size());
            ((AlbumMainPage) this.mPage).e.notifyDataSetChanged();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.b != null) {
            bvk bvk = this.b;
            bvk.b.clear();
            bvk.a = null;
            bvk.c = false;
            bvk.d = null;
        }
        AlbumMainPage albumMainPage = (AlbumMainPage) this.mPage;
        if (albumMainPage.d != null) {
            albumMainPage.d.setOnGroupClickListener(null);
        }
        EventBus.getDefault().unregister(albumMainPage);
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (pageBundle == null || !pageBundle.getBoolean("PHOTO_FRAGMENT_CLOSE")) {
            if (i2 == 12290) {
                if (ResultType.OK.equals(resultType)) {
                    if (((AlbumMainPage) this.mPage).h == 5) {
                        ((AlbumMainPage) this.mPage).b();
                        return;
                    }
                    ((AlbumMainPage) this.mPage).a(((AlbumMainPage) this.mPage).e.getSelectedData().size());
                    ((AlbumMainPage) this.mPage).e.notifyDataSetChanged();
                    return;
                } else if (ResultType.CANCEL.equals(resultType)) {
                    ((AlbumMainPage) this.mPage).finish();
                }
            }
            return;
        }
        ((AlbumMainPage) this.mPage).setResult(ResultType.OK, pageBundle);
        ((AlbumMainPage) this.mPage).finish();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((AlbumMainPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        ((AlbumMainPage) this.mPage).a();
        return super.onBackPressed();
    }

    public final void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (-1 == i3) {
            if (i2 != 20482) {
                switch (i2) {
                    case 4:
                        ((AlbumMainPage) this.mPage).finish();
                        bvj.a((AbstractBasePage) this.mPage, this.a, intent, i2, this.e);
                        return;
                    case 5:
                        bvj.a((AbstractBasePage) this.mPage, this.a, intent, i2, this.e);
                        return;
                    case 6:
                        bvj.a((AbstractBasePage) this.mPage, this.a, intent, i2, this.e);
                        return;
                    case 7:
                        ((AlbumMainPage) this.mPage).finish();
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putObject("photo_select_list", intent);
                        ((AlbumMainPage) this.mPage).setResult(ResultType.OK, pageBundle);
                        break;
                    default:
                        switch (i2) {
                            case 20484:
                            case 20485:
                                ((AlbumMainPage) this.mPage).c();
                                return;
                        }
                }
            } else {
                ((AlbumMainPage) this.mPage).b();
            }
        }
    }

    public final void a(List<ImageInfo> list) {
        this.h = list;
        if (list.size() == 1) {
            ((AlbumMainPage) this.mPage).finish();
            bvj.a((AbstractBasePage) this.mPage, list.get(0).b, this.a, this.e.d);
            return;
        }
        if (list.size() > 1) {
            LogManager.actionLogV2(LogConstant.ALBUM_PAGE_FOR_REAL_SCENE, "B004", null);
            if (!TextUtils.isEmpty(this.c)) {
                Intent intent = new Intent();
                intent.setClassName("com.autonavi.minimap", this.c);
                this.g.bindService(intent, this.l, 1);
            }
            this.j.putBooleanValue("scene_user_resume_need_update", true);
            ((AlbumMainPage) this.mPage).finish();
        }
    }

    public final void a(Map<String, List<ImageInfo>> map) {
        if (this.mPage != null) {
            ((AlbumMainPage) this.mPage).f = map;
        }
    }
}
