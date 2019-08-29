package com.autonavi.map.search.imagepreview.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.imagepreview.adapter.ImageDetailAdapter;
import com.autonavi.minimap.R;
import com.autonavi.widget.photoview.PhotoView;
import com.autonavi.widget.ui.LoadingViewBL;
import java.util.ArrayList;

public class ImageDetailPage extends AbstractBasePage<byf> implements OnPageChangeListener, OnClickListener, byd, launchModeSingleInstance {
    private static int g;
    private ImageButton a;
    private ImageButton b;
    private ViewPager c;
    private String d = "";
    private int e = 1;
    private PageBundle f;
    private ArrayList<cal> h = new ArrayList<>();
    private boolean i = false;
    private ImageDetailAdapter j;

    public void onPageScrolled(int i2, float f2, int i3) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.photopreview_image_detail_pager);
        View contentView = getContentView();
        this.a = (ImageButton) contentView.findViewById(R.id.title_btn_left_custom);
        this.a.setOnClickListener(this);
        this.b = (ImageButton) contentView.findViewById(R.id.backlist);
        this.b.setOnClickListener(this);
        this.c = (ViewPager) contentView.findViewById(R.id.image_detail_pager);
        this.c.setOnPageChangeListener(this);
        a();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.title_btn_left_custom || view.getId() != R.id.backlist) {
            finish();
            return;
        }
        PageBundle arguments = getArguments();
        arguments.putObject("data", this.h);
        arguments.putString("type", this.d);
        if (this.i) {
            startPage(ImageGridNodeTabPage.class, arguments);
        } else {
            startPage(ImageGridNodePage.class, arguments);
        }
    }

    public final void a() {
        this.f = getArguments();
        if (this.f != null) {
            this.d = this.f.getString("type", "");
            this.h = (ArrayList) this.f.get("data");
            this.i = this.f.getBoolean("key_from_idq_plus", false);
            if (this.h.size() > 0) {
                int intValue = ((Integer) this.f.get("jsindex")).intValue();
                g = this.h.size();
                this.j = new ImageDetailAdapter(this);
                this.j.a(this.h);
                this.c.setAdapter(this.j);
                this.c.setCurrentItem(intValue + 1, false);
            }
        }
    }

    public void onPageSelected(int i2) {
        if (i2 > g) {
            this.e = 1;
        } else if (i2 <= 0) {
            this.e = g;
        } else {
            this.e = i2;
        }
    }

    public void onPageScrollStateChanged(int i2) {
        if (i2 == 0 && this.c != null) {
            this.c.setCurrentItem(this.e, false);
        }
    }

    public final View a(int i2) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.photopreview_image_detail_item_fragment, null);
        final PhotoView photoView = (PhotoView) inflate.findViewById(R.id.imageDetailPhoto);
        TextView textView = (TextView) inflate.findViewById(R.id.ImageDetailTitle);
        TextView textView2 = (TextView) inflate.findViewById(R.id.ImageDetailMessage);
        TextView textView3 = (TextView) inflate.findViewById(R.id.ImageDetailNumber);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.ImageRecommendImg);
        TextView textView4 = (TextView) inflate.findViewById(R.id.ImageRecommend);
        TextView textView5 = (TextView) inflate.findViewById(R.id.image_source);
        final LoadingViewBL loadingViewBL = (LoadingViewBL) inflate.findViewById(R.id.image_detail_loading_view);
        photoView.setOnPhotoTapListener(new d() {
            public final void a(View view) {
                ImageDetailPage.this.onClick(view);
            }
        });
        cal cal = this.j.a.get(i2);
        if (cal != null) {
            textView.setText(cal.b);
            textView2.setText(cal.c);
            String str = cal.d;
            if (TextUtils.isEmpty(str)) {
                imageView.setVisibility(4);
                textView4.setVisibility(4);
            } else {
                imageView.setVisibility(0);
                textView4.setVisibility(0);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("人推荐");
            textView4.setText(sb.toString());
            if (!TextUtils.isEmpty(cal.f)) {
                textView5.setText(cal.f);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(cal.a);
            sb2.append("/");
            sb2.append(g);
            textView3.setText(sb2.toString());
            String str2 = cal.e;
            Bitmap a2 = ahc.a(str2);
            if (a2 != null) {
                photoView.setImageBitmap(a2);
                return inflate;
            }
            if (!str2.equals("") && str2.indexOf("is.autonavi.com/showpic") >= 0 && str2.indexOf("?") == -1) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str2);
                stringBuffer.append("?operate=detail");
                str2 = stringBuffer.toString();
            }
            ko.a(photoView, str2, null, 0, new bkf() {
                public final void onPrepareLoad(Drawable drawable) {
                }

                public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
                    loadingViewBL.setVisibility(8);
                }

                public final void onBitmapFailed(Drawable drawable) {
                    loadingViewBL.setVisibility(8);
                    photoView.setImageResource(R.drawable.housenob_image_add);
                }
            });
        }
        return inflate;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new byf(this);
    }
}
