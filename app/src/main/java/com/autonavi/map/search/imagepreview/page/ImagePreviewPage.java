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
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.imagepreview.adapter.ImageDetailAdapter;
import com.autonavi.minimap.R;
import com.autonavi.widget.photoview.PhotoView;
import com.autonavi.widget.ui.LoadingViewBL;
import java.util.ArrayList;

public class ImagePreviewPage extends AbstractBasePage<byh> implements OnPageChangeListener, OnClickListener, byd {
    private ImageButton a;
    private ViewPager b;
    private ArrayList<cal> c = new ArrayList<>();
    private int d = 1;
    private int e;
    private final int f = 1;
    private ImageDetailAdapter g;

    public void onPageScrolled(int i, float f2, int i2) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.image_preview_detail_fragment);
        View contentView = getContentView();
        this.a = (ImageButton) contentView.findViewById(R.id.title_btn_left_custom);
        this.a.setOnClickListener(this);
        this.b = (ViewPager) contentView.findViewById(R.id.image_detail_pager);
        this.b.setOnPageChangeListener(this);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.c = (ArrayList) arguments.get("data");
            this.e = this.c.size();
            if (this.e > 0) {
                int intValue = ((Integer) arguments.get("jsindex")).intValue();
                this.g = new ImageDetailAdapter(this);
                this.g.a(this.c);
                this.b.setAdapter(this.g);
                this.b.setCurrentItem(intValue + 1, false);
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.title_btn_left_custom) {
            finish();
            return;
        }
        if (view.getId() == R.id.image_detail_photo) {
            finish();
        }
    }

    public void onPageSelected(int i) {
        if (i > this.e) {
            this.d = 1;
        } else if (i <= 0) {
            this.d = this.e;
        } else {
            this.d = i;
        }
    }

    public void onPageScrollStateChanged(int i) {
        if (i == 0 && this.b != null) {
            this.b.setCurrentItem(this.d, false);
        }
    }

    public final View a(int i) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.image_preview_item_fragment, null);
        final PhotoView photoView = (PhotoView) inflate.findViewById(R.id.image_detail_photo);
        TextView textView = (TextView) inflate.findViewById(R.id.image_detail_title);
        TextView textView2 = (TextView) inflate.findViewById(R.id.image_detail_index);
        final LoadingViewBL loadingViewBL = (LoadingViewBL) inflate.findViewById(R.id.image_detail_loading_view);
        ((ImageView) inflate.findViewById(R.id.image_detail_download)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                Bitmap a2 = byi.a(photoView.getDrawable());
                if (a2 != null) {
                    ahl.b(new a<String>(ImagePreviewPage.this.getContext(), a2) {
                        public final /* synthetic */ void onFinished(Object obj) {
                            if (!TextUtils.isEmpty((String) obj)) {
                                ToastHelper.showToast(ImagePreviewPage.this.getString(R.string.image_save_to_album_success, cby.a));
                                return;
                            }
                            ToastHelper.showToast(ImagePreviewPage.this.getResources().getString(R.string.image_save_to_album_sdcard_full));
                        }

                        public final void onError(Throwable th) {
                            ToastHelper.showToast(ImagePreviewPage.this.getResources().getString(R.string.image_save_to_album_fail));
                        }

                        public final /* synthetic */ Object doBackground() throws Exception {
                            return byi.a(r2, r3);
                        }
                    });
                }
            }
        });
        photoView.setOnPhotoTapListener(new d() {
            public final void a(View view) {
                ImagePreviewPage.this.onClick(view);
            }
        });
        cal cal = this.g.a.get(i);
        if (cal != null) {
            textView.setText(cal.b);
            StringBuilder sb = new StringBuilder();
            sb.append(cal.a);
            sb.append("/");
            sb.append(this.e);
            textView2.setText(sb.toString());
            String str = cal.e;
            if (TextUtils.isEmpty(str) && str.indexOf("is.autonavi.com/showpic") >= 0 && str.indexOf("?") == -1) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str);
                stringBuffer.append("?operate=detail");
                str = stringBuffer.toString();
            }
            ko.a(photoView, str, null, 0, new bkf() {
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
        return new byh(this);
    }
}
