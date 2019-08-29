package com.jiuyan.inimage.a;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.jiuyan.inimage.InSDKEntrance;
import com.jiuyan.inimage.b.q;
import com.jiuyan.inimage.bean.BeanDataPaster;
import com.jiuyan.inimage.util.e;
import com.jiuyan.inimage.util.f;
import com.jiuyan.inimage.util.g;
import com.jiuyan.inimage.widget.RoundProgressBar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: PasterGalleryRecommendAdapter */
public class c extends a {
    private List<BeanDataPaster> c = new ArrayList();
    /* access modifiers changed from: private */
    public i d;
    private List<String> e = new ArrayList();
    private MultimediaFileService f;
    private MultimediaImageService g;

    public c(Context context) {
        String[] strArr;
        super(context);
        a(false);
        b(false);
        try {
            strArr = this.a.getAssets().list(ImageEditService.IN_EDIT_TYPE_PASTER);
        } catch (IOException e2) {
            e2.printStackTrace();
            strArr = null;
        }
        if (strArr != null) {
            this.e.addAll(Arrays.asList(strArr));
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(i iVar) {
        this.d = iVar;
    }

    public void a(List<BeanDataPaster> list) {
        this.c.addAll(list);
        notifyDataSetChanged();
    }

    public void b(List<BeanDataPaster> list) {
        this.c.clear();
        a(list);
    }

    public void b() {
        this.c.clear();
    }

    public ViewHolder a(ViewGroup viewGroup) {
        return null;
    }

    public void a(ViewHolder viewHolder, int i) {
    }

    public ViewHolder b(ViewGroup viewGroup) {
        return new g(this.b.inflate(R.layout.in_alipay_paster_item_of_recycler_paster_gallery_recommend_footer, viewGroup, false));
    }

    public void b(ViewHolder viewHolder, int i) {
        viewHolder.itemView.setOnClickListener(new d(this));
    }

    public ViewHolder c(ViewGroup viewGroup) {
        return new h(this.b.inflate(R.layout.in_alipay_paster_item_of_recycler_paster_gallery_recommend, viewGroup, false));
    }

    public void c(ViewHolder viewHolder, int i) {
        h hVar = (h) viewHolder;
        BeanDataPaster beanDataPaster = this.c.get(i);
        d().loadImage(beanDataPaster.thumb_url, hVar.a, new Builder().showImageOnLoading(new ColorDrawable(-7829368)).build(), (APImageDownLoadCallback) null, InSDKEntrance.getDownloadBiz());
        hVar.b.setVisibility(8);
        if (!a(beanDataPaster.url)) {
        }
        hVar.itemView.setOnClickListener(new e(this, beanDataPaster, hVar, i));
    }

    private MultimediaFileService c() {
        if (this.f == null) {
            this.f = (MultimediaFileService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaFileService.class.getName());
        }
        return this.f;
    }

    private MultimediaImageService d() {
        if (this.g == null) {
            this.g = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageService.class.getName());
        }
        return this.g;
    }

    /* access modifiers changed from: private */
    public boolean a(String str) {
        if (!TextUtils.isEmpty(str)) {
            APFileQueryResult queryCacheFile = c().queryCacheFile(str);
            if (queryCacheFile != null && !TextUtils.isEmpty(queryCacheFile.path) && e.a(queryCacheFile.path)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void a(BeanDataPaster beanDataPaster, RoundProgressBar roundProgressBar, int i) {
        String a = f.a(beanDataPaster.url);
        q.a(this.a, beanDataPaster.id, beanDataPaster.url, g.a, a, new f(this, roundProgressBar, i, beanDataPaster));
    }

    public int a() {
        return this.c.size();
    }
}
