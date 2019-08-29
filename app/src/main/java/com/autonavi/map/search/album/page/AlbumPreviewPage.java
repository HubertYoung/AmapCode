package com.autonavi.map.search.album.page;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.album.adapter.AlbumPreviewPagerAdapter;
import com.autonavi.map.search.album.page.AlbumMainPage.a;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.map.search.view.FixedViewPager;
import com.autonavi.minimap.R;
import de.greenrobot.event.EventBus;
import java.util.List;

@PageAction("amap.album.action.AlbumPreviewPage")
public class AlbumPreviewPage extends AbstractBasePage<bvh> implements OnClickListener, bvm, LocationNone {
    protected TextView a;
    public boolean b = false;
    public int c;
    protected FixedViewPager d;
    protected AlbumPreviewPagerAdapter e;
    /* access modifiers changed from: private */
    public View f;
    private View g;
    /* access modifiers changed from: private */
    public CheckBox h;
    /* access modifiers changed from: private */
    public View i;
    private TextView j;
    private TextView k;
    private int l = 500;
    private int m;
    private String n;
    private int o;
    /* access modifiers changed from: private */
    public boolean p = false;
    private anq q = new anq() {
        public final void loginOrBindCancel() {
        }

        public final void onComplete(boolean z) {
            if (z) {
                ((bvh) AlbumPreviewPage.this.mPresenter).a(((bvh) AlbumPreviewPage.this.mPresenter).b());
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.real_scene_album_preview_fragment);
        PageBundle arguments = getArguments();
        if (arguments == null) {
            finish();
        } else {
            this.o = getRequestCode();
            caj caj = (caj) arguments.getObject("album_preview_builder");
            if (caj.j == 2) {
                this.b = true;
            }
            this.l = caj.a;
            this.m = caj.g;
            this.c = caj.h;
            this.n = caj.l;
            bvh bvh = (bvh) this.mPresenter;
            bvh.a = (bvm) bvh.mPage;
            caj caj2 = (caj) arguments.getObject("album_preview_builder");
            bvh.b = caj2.c;
            bvh.c = caj2.b;
            bvh.d = caj2.d;
            bvh.f = caj2.f;
            bvh.g = caj2.e;
            if (bvh.d != null) {
                bvh.a.a(bvh.b);
            } else {
                bvh.a.a(bvh.c);
            }
            bvh.h = caj2.k;
            bvh.i = caj2.l;
            bvh.e = caj2.i;
        }
        View contentView = getContentView();
        this.f = contentView.findViewById(R.id.title);
        this.g = contentView.findViewById(R.id.scene_detail_back);
        this.g.setOnClickListener(this);
        this.a = (TextView) contentView.findViewById(R.id.scene_detail_image_index);
        TextView textView = this.a;
        StringBuilder sb = new StringBuilder("1/");
        sb.append(this.e.getCount());
        textView.setText(sb.toString());
        this.h = (CheckBox) contentView.findViewById(R.id.album_image_checkbox);
        this.h.setOnClickListener(this);
        this.d = (FixedViewPager) getContentView().findViewById(R.id.scene_image_detail_pager);
        this.d.setAdapter(this.e);
        this.e.b = new e() {
            public final void a() {
                if (AlbumPreviewPage.this.p) {
                    AlbumPreviewPage.this.f.startAnimation(AlbumPreviewPage.a(true, (AnimationListener) new AnimationListener() {
                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                        }

                        public final void onAnimationEnd(Animation animation) {
                            AlbumPreviewPage.this.p = false;
                            AlbumPreviewPage.this.g.setClickable(!AlbumPreviewPage.this.p);
                        }
                    }));
                    AlbumPreviewPage.this.i.startAnimation(AlbumPreviewPage.b(false, (AnimationListener) null));
                    return;
                }
                AlbumPreviewPage.this.f.startAnimation(AlbumPreviewPage.b(true, (AnimationListener) new AnimationListener() {
                    public final void onAnimationRepeat(Animation animation) {
                    }

                    public final void onAnimationStart(Animation animation) {
                    }

                    public final void onAnimationEnd(Animation animation) {
                        AlbumPreviewPage.this.p = true;
                        AlbumPreviewPage.this.g.setClickable(true ^ AlbumPreviewPage.this.p);
                    }
                }));
                AlbumPreviewPage.this.i.startAnimation(AlbumPreviewPage.a(false, (AnimationListener) null));
            }
        };
        this.d.setOnPageChangeListener(new OnPageChangeListener() {
            public final void onPageScrollStateChanged(int i) {
            }

            public final void onPageScrolled(int i, float f, int i2) {
            }

            public final void onPageSelected(int i) {
                TextView textView = AlbumPreviewPage.this.a;
                StringBuilder sb = new StringBuilder();
                sb.append(i + 1);
                sb.append("/");
                sb.append(AlbumPreviewPage.this.e.getCount());
                textView.setText(sb.toString());
                ImageInfo a2 = AlbumPreviewPage.this.e.a(i);
                if (a2 != null) {
                    AlbumPreviewPage.this.h.setChecked(a2.g);
                }
            }
        });
        View contentView2 = getContentView();
        this.i = contentView2.findViewById(R.id.real_scene_detail_bottom);
        this.j = (TextView) contentView2.findViewById(R.id.album_publish);
        this.j.setOnClickListener(this);
        switch (this.c) {
            case 1:
                this.j.setEnabled(true);
                this.j.setText("确定");
                break;
            case 2:
                this.j.setText("上传");
                break;
            default:
                this.j.setVisibility(8);
                break;
        }
        switch (this.m) {
            case 2:
                contentView2.findViewById(R.id.album_image_checkbox).setVisibility(8);
                contentView2.findViewById(R.id.album_image_del_btn).setVisibility(0);
                break;
            case 3:
                contentView2.findViewById(R.id.album_image_checkbox).setVisibility(0);
                contentView2.findViewById(R.id.album_image_del_btn).setVisibility(8);
                break;
            default:
                contentView2.findViewById(R.id.album_image_checkbox).setVisibility(8);
                contentView2.findViewById(R.id.album_image_del_btn).setVisibility(8);
                break;
        }
        contentView2.findViewById(R.id.album_image_del_btn).setOnClickListener(this);
        this.k = (TextView) contentView2.findViewById(R.id.album_num);
        ((bvh) this.mPresenter).a(((bvh) this.mPresenter).a());
        bvh bvh2 = (bvh) this.mPresenter;
        bvh2.a.a(bvh2.b, bvh2.d);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.scene_detail_back) {
            if (this.b) {
                finish();
            } else if (this.c == 1) {
                b();
            } else if (this.c == 2) {
                a();
            } else if (this.c == 0) {
                c();
            }
        } else if (id == R.id.album_image_checkbox) {
            if (!this.h.isChecked() || ((bvh) this.mPresenter).a() < this.l) {
                ImageInfo a2 = this.e.a(this.d.getCurrentItem());
                a2.g = this.h.isChecked();
                ((bvh) this.mPresenter).a(a2);
                bvh bvh = (bvh) this.mPresenter;
                if (bvh.c != null && !bvh.c.contains(a2)) {
                    bvh.c.add(a2);
                }
                ((bvh) this.mPresenter).a(((bvh) this.mPresenter).a());
                EventBus.getDefault().post(new a(a2));
                return;
            }
            this.h.setChecked(false);
            StringBuilder sb = new StringBuilder("最多可以选择");
            sb.append(this.l);
            sb.append("张照片!");
            ToastHelper.showToast(sb.toString());
        } else if (id == R.id.album_publish) {
            if (this.c == 1) {
                b();
            } else if (this.c == 2) {
                a();
            }
        } else if (id == R.id.album_image_del_btn) {
            ImageInfo a3 = this.e.a(this.d.getCurrentItem());
            a3.g = false;
            ((bvh) this.mPresenter).a(a3);
            ((bvh) this.mPresenter).a(((bvh) this.mPresenter).a());
            LogManager.actionLogV2("P00180", "B001");
            if (((bvh) this.mPresenter).a() == 0) {
                c();
                return;
            }
            bvh bvh2 = (bvh) this.mPresenter;
            if (bvh2.b != null && bvh2.b.contains(a3)) {
                bvh2.b.remove(a3);
            }
            if (bvh2.c != null && bvh2.c.contains(a3)) {
                bvh2.c.remove(a3);
            }
            if (bvh2.b != null) {
                bvh2.a.b(bvh2.b);
                return;
            }
            bvh2.a.b(bvh2.c);
        }
    }

    private void c() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("BUNDLE_KEY_SELECTED_IMAGE_LIST", ((bvh) this.mPresenter).b());
        pageBundle.putInt("COMMENT_REQUEST_CODE", 20484);
        finish();
        setResult(ResultType.OK, pageBundle);
    }

    public final void b() {
        if (!TextUtils.isEmpty(this.n)) {
            List<ImageInfo> b2 = ((bvh) this.mPresenter).b();
            if (b2 == null || b2.size() <= 0) {
                finish();
                return;
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("BUNDLE_KEY_SELECTED_IMAGE_LIST", b2);
            if (this.o == 12291) {
                pageBundle.putInt("COMMENT_REQUEST_CODE", 20486);
            } else {
                pageBundle.putInt("COMMENT_REQUEST_CODE", 20482);
            }
            if ("amap.search.action.comment".contains(this.n)) {
                startPageForResult(this.n, pageBundle, 1);
            } else {
                startPage(this.n, pageBundle);
            }
        }
    }

    public final void a(int i2) {
        if (i2 != 0) {
            this.k.setVisibility(0);
            this.k.setText(String.valueOf(i2));
            this.j.setEnabled(true);
            return;
        }
        this.k.setVisibility(8);
        this.j.setEnabled(false);
    }

    public final void b(List<ImageInfo> list) {
        if (this.e == null) {
            this.e = new AlbumPreviewPagerAdapter(getContext());
        }
        int currentItem = this.d.getCurrentItem();
        if (currentItem >= list.size()) {
            currentItem = list.size() - 1;
        }
        this.e.a = list;
        this.e.notifyDataSetChanged();
        this.d.setCurrentItem(currentItem);
        TextView textView = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append(currentItem + 1);
        sb.append("/");
        sb.append(this.e.getCount());
        textView.setText(sb.toString());
    }

    public final void a(List<ImageInfo> list, ImageInfo imageInfo) {
        if (imageInfo != null) {
            int indexOf = list.indexOf(imageInfo);
            this.d.setCurrentItem(indexOf);
            TextView textView = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append(indexOf + 1);
            sb.append("/");
            sb.append(this.e.getCount());
            textView.setText(sb.toString());
            ImageInfo a2 = this.e.a(indexOf);
            if (a2 != null) {
                this.h.setChecked(a2.g);
            }
            return;
        }
        ImageInfo a3 = this.e.a(0);
        if (a3 != null) {
            this.h.setChecked(a3.g);
        }
    }

    public final void a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (iAccountService.a()) {
                ((bvh) this.mPresenter).a(((bvh) this.mPresenter).b());
            } else {
                iAccountService.a(getPageContext(), this.q);
            }
        }
    }

    public final void a(List<ImageInfo> list) {
        this.e = new AlbumPreviewPagerAdapter(getContext());
        this.e.a = list;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bvh(this);
    }

    static /* synthetic */ TranslateAnimation a(boolean z, AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, z ? -1.0f : 0.0f, 1, z ? 0.0f : 1.0f);
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.setAnimationListener(animationListener);
        return translateAnimation;
    }

    static /* synthetic */ TranslateAnimation b(boolean z, AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, z ? 0.0f : 1.0f, 1, z ? -1.0f : 0.0f);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(animationListener);
        return translateAnimation;
    }
}
