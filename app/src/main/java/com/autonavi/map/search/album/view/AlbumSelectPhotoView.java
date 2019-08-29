package com.autonavi.map.search.album.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.album.page.AlbumMainPage.b;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class AlbumSelectPhotoView extends RelativeLayout implements OnClickListener {
    /* access modifiers changed from: private */
    public boolean isCloseAnimationing = false;
    private boolean isFilterLocation = true;
    /* access modifiers changed from: private */
    public boolean isOpenAnimationing = false;
    private cah mBundleBuilder;
    private ImageView mCameraImg;
    /* access modifiers changed from: private */
    public RelativeLayout mCameraLayout;
    private int mCameraRequestCode = 4;
    /* access modifiers changed from: private */
    public ImageView mCloseImg;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mMaxPhotoSelectCount = 500;
    private ImageView mPhotoImg;
    /* access modifiers changed from: private */
    public RelativeLayout mPhotoLayout;
    private int mPhotoRequestCode = 20481;
    private RelativeLayout mPublishLayout;
    private cao mRealSceneTipInfo;
    private List<ImageInfo> mSelectedImageList;
    /* access modifiers changed from: private */
    public AbstractBasePage page;

    public void setNodeFragment(AbstractBasePage abstractBasePage) {
        this.page = abstractBasePage;
    }

    public void setmBundleBuilder(cah cah) {
        this.mBundleBuilder = cah;
    }

    public void setRealSceneTipInfo(cao cao) {
        this.mRealSceneTipInfo = cao;
    }

    public void setFilterLocation(boolean z) {
        this.isFilterLocation = z;
    }

    public void setMaxPhotoSelectCount(int i) {
        this.mMaxPhotoSelectCount = i;
    }

    public void setCameraRequestCode(int i) {
        this.mCameraRequestCode = i;
    }

    public void setmSelectedImageList(List<ImageInfo> list) {
        this.mSelectedImageList = list;
    }

    public void setmPhotoRequestCode(int i) {
        this.mPhotoRequestCode = i;
    }

    public AlbumSelectPhotoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public AlbumSelectPhotoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public AlbumSelectPhotoView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        iwantView();
    }

    private void iwantView() {
        View inflate = this.mInflater.inflate(R.layout.real_scene_select_photo_widget_layout, null);
        this.mPublishLayout = (RelativeLayout) inflate;
        inflate.findViewById(R.id.anim_layout).setOnClickListener(this);
        this.mCameraLayout = (RelativeLayout) inflate.findViewById(R.id.camera_layout);
        this.mPhotoLayout = (RelativeLayout) inflate.findViewById(R.id.photo_layout);
        this.mPhotoImg = (ImageView) inflate.findViewById(R.id.photo_img);
        this.mCameraImg = (ImageView) inflate.findViewById(R.id.camera_img);
        this.mCloseImg = (ImageView) inflate.findViewById(R.id.close_img);
        initEvents();
        addView(inflate, new LayoutParams(-1, -1));
    }

    private void initEvents() {
        this.mPhotoImg.setOnClickListener(this);
        this.mCameraImg.setOnClickListener(this);
        this.mCloseImg.setOnClickListener(this);
        this.mPublishLayout.setOnClickListener(this);
    }

    public void doOpenAnim() {
        if (!this.isOpenAnimationing) {
            this.isOpenAnimationing = true;
            doAnimation(this.mCameraLayout, true);
            this.mPhotoLayout.setVisibility(8);
            new Handler().postDelayed(new Runnable() {
                public final void run() {
                    AlbumSelectPhotoView.this.mPhotoLayout.setVisibility(0);
                    AlbumSelectPhotoView.this.doAnimation(AlbumSelectPhotoView.this.mPhotoLayout, true);
                }
            }, 200);
        }
    }

    public void doCloseAnim() {
        if (!this.isCloseAnimationing) {
            this.isCloseAnimationing = true;
            if (this.mPhotoRequestCode == 20481 || this.mCameraRequestCode == 4) {
                LogManager.actionLogV2(LogConstant.ALBUM_SELECTE_PHOTO_PAGE_FOR_REAL_SCENE, "B004", null);
            }
            doAnimation(this.mPhotoLayout, false);
            new Handler().postDelayed(new Runnable() {
                public final void run() {
                    AlbumSelectPhotoView.this.doAnimation(AlbumSelectPhotoView.this.mCameraLayout, false);
                }
            }, 200);
        }
    }

    public void doAnimation(final View view, final boolean z) {
        int i;
        if (z) {
            i = R.anim.photo_album_show;
        } else {
            i = R.anim.photo_album_dismiss;
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, i);
        loadAnimation.setAnimationListener(new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                AlbumSelectPhotoView.this.post(new Runnable() {
                    public final void run() {
                        if (z) {
                            if (view == AlbumSelectPhotoView.this.mPhotoLayout) {
                                AlbumSelectPhotoView.this.mCloseImg.setVisibility(0);
                                AlbumSelectPhotoView.this.isOpenAnimationing = false;
                            }
                        } else if (view == AlbumSelectPhotoView.this.mCameraLayout) {
                            AlbumSelectPhotoView.this.mCameraLayout.setVisibility(8);
                            AlbumSelectPhotoView.this.mCloseImg.setVisibility(8);
                            AlbumSelectPhotoView.this.isCloseAnimationing = false;
                            if (AlbumSelectPhotoView.this.page != null) {
                                AlbumSelectPhotoView.this.page.finish();
                            }
                        } else {
                            AlbumSelectPhotoView.this.mPhotoLayout.setVisibility(8);
                        }
                        view.clearAnimation();
                    }
                });
            }
        });
        view.startAnimation(loadAnimation);
    }

    public void doAlphaAnim(View view, boolean z) {
        float f;
        float f2 = 1.0f;
        if (z) {
            f = 0.5f;
        } else {
            f = 1.0f;
            f2 = 0.0f;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setDuration(400);
        view.startAnimation(alphaAnimation);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.photo_img) {
            switch (this.mPhotoRequestCode) {
                case 20482:
                    AbstractBasePage abstractBasePage = this.page;
                    int i = this.mMaxPhotoSelectCount;
                    boolean z = this.isFilterLocation;
                    List<ImageInfo> list = this.mSelectedImageList;
                    cah cah = this.mBundleBuilder;
                    if (abstractBasePage != null) {
                        PageBundle pageBundle = new PageBundle();
                        b bVar = new b();
                        bVar.b = abstractBasePage.getString(R.string.sure);
                        bVar.e = z;
                        bVar.g = i;
                        bVar.d = false;
                        bVar.c = true;
                        bVar.a = abstractBasePage.getString(R.string.comment_album_title);
                        bVar.f = 5;
                        pageBundle.putObject("ALBUM_FRAGMENT_STYLE", bVar);
                        if (list != null) {
                            pageBundle.putObject("SELECT_DATA_LIST", list);
                        }
                        pageBundle.putObject("album_bundle_builder", cah);
                        abstractBasePage.finish();
                        abstractBasePage.startPageForResult((String) "amap.album.action.AlbumMainPage", pageBundle, 20482);
                    }
                    LogManager.actionLogV2("P00177", "B002");
                    return;
                case 20484:
                case 20485:
                    AbstractBasePage abstractBasePage2 = this.page;
                    int i2 = this.mMaxPhotoSelectCount;
                    boolean z2 = this.isFilterLocation;
                    List<ImageInfo> list2 = this.mSelectedImageList;
                    cah cah2 = this.mBundleBuilder;
                    int i3 = this.mPhotoRequestCode;
                    if (abstractBasePage2 != null) {
                        PageBundle pageBundle2 = new PageBundle();
                        b bVar2 = new b();
                        bVar2.b = abstractBasePage2.getString(R.string.sure);
                        bVar2.e = z2;
                        bVar2.g = i2;
                        bVar2.d = false;
                        bVar2.c = true;
                        bVar2.a = abstractBasePage2.getString(R.string.comment_album_title);
                        bVar2.f = 6;
                        pageBundle2.putObject("ALBUM_FRAGMENT_STYLE", bVar2);
                        if (list2 != null) {
                            pageBundle2.putObject("SELECT_DATA_LIST", list2);
                        }
                        pageBundle2.putObject("album_bundle_builder", cah2);
                        abstractBasePage2.finish();
                        abstractBasePage2.startPageForResult((String) "amap.album.action.AlbumMainPage", pageBundle2, i3);
                    }
                    return;
                case 20486:
                    AbstractBasePage abstractBasePage3 = this.page;
                    int i4 = this.mMaxPhotoSelectCount;
                    boolean z3 = this.isFilterLocation;
                    List<ImageInfo> list3 = this.mSelectedImageList;
                    cah cah3 = this.mBundleBuilder;
                    int i5 = this.mPhotoRequestCode;
                    if (abstractBasePage3 != null) {
                        PageBundle pageBundle3 = new PageBundle();
                        b bVar3 = new b();
                        bVar3.b = abstractBasePage3.getString(R.string.sure);
                        bVar3.e = z3;
                        bVar3.g = i4;
                        bVar3.d = false;
                        bVar3.c = false;
                        bVar3.a = abstractBasePage3.getString(R.string.comment_album_title);
                        bVar3.f = 6;
                        pageBundle3.putObject("ALBUM_FRAGMENT_STYLE", bVar3);
                        if (list3 != null) {
                            pageBundle3.putObject("SELECT_DATA_LIST", list3);
                        }
                        pageBundle3.putObject("album_bundle_builder", cah3);
                        abstractBasePage3.startPageForResult((String) "amap.album.action.AlbumMainPage", pageBundle3, i5);
                    }
                    return;
                default:
                    AbstractBasePage abstractBasePage4 = this.page;
                    cao cao = this.mRealSceneTipInfo;
                    cah cah4 = this.mBundleBuilder;
                    if (abstractBasePage4 != null) {
                        PageBundle pageBundle4 = new PageBundle();
                        pageBundle4.putObject("activity_tip", cao);
                        pageBundle4.putObject("album_bundle_builder", cah4);
                        abstractBasePage4.finish();
                        abstractBasePage4.startPageForResult((String) "amap.album.action.AlbumMainPage", pageBundle4, 20481);
                    }
                    return;
            }
        } else if (id == R.id.camera_img) {
            addLogClickCamera();
            bvj.a(this.page, this.isFilterLocation, this.mCameraRequestCode);
        } else {
            if (id == R.id.anim_layout || id == R.id.close_img) {
                doCloseAnim();
            }
        }
    }

    private void addLogClickCamera() {
        if (this.mCameraRequestCode != 5) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("status", aaw.b(this.mContext));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2(LogConstant.ALBUM_SELECTE_PHOTO_PAGE_FOR_REAL_SCENE, "B002", jSONObject);
            return;
        }
        LogManager.actionLogV2("P00177", "B001");
    }

    public void draw(Canvas canvas) {
        try {
            super.draw(canvas);
        } catch (NullPointerException unused) {
        }
    }
}
