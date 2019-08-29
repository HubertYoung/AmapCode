package com.autonavi.map.search.album.page;

import android.content.Context;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.map.search.album.view.AlbumSelectPhotoView;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.List;

@PageAction("amap.album.action.AlbumSelectPhotoPage")
public class AlbumSelectPhotoPage extends AbstractBasePage<bvi> implements Transparent {
    public AlbumSelectPhotoView a;
    public cao b;
    private boolean c = true;
    private int d = 20481;
    private int e = 4;
    private a f;

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.real_scene_select_photo_fragment);
        this.a = (AlbumSelectPhotoView) getContentView().findViewById(R.id.publish_layout);
        PageBundle arguments = getArguments();
        if (arguments == null) {
            finish();
        } else {
            Object object = arguments.getObject("album_bundle_builder");
            if (object != null) {
                cah cah = (cah) object;
                this.c = cah.e;
                this.b = cah.i;
                this.a.setNodeFragment(this);
                this.a.setRealSceneTipInfo(this.b);
                this.a.setMaxPhotoSelectCount(cah.f);
                this.a.setFilterLocation(this.c);
                this.d = getRequestCode();
                this.e = cah.g;
                this.a.setCameraRequestCode(this.e);
                this.a.setmPhotoRequestCode(this.d);
                this.a.setmBundleBuilder(cah);
                if (cah.h != null) {
                    this.a.setmSelectedImageList(cah.h);
                }
                ((bvi) this.mPresenter).a = cah;
            }
            Object obj = arguments.get("callback");
            if (obj != null) {
                this.f = (a) obj;
            }
        }
        this.a.doOpenAnim();
        if (this.e == 4 || this.d == 20481) {
            LogManager.actionLogV2(LogConstant.ALBUM_SELECTE_PHOTO_PAGE_FOR_REAL_SCENE, "B001", null);
        }
    }

    public final void a(List<ImageInfo> list) {
        if (this.f != null && list != null) {
            this.f.onPhotoSelection(list);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bvi(this);
    }
}
