package com.autonavi.map.search.imagepreview.page;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.search.imagepreview.adapter.ImageGridAdapter;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ImageGridNodePage extends AbsImageGridNodeBasePage implements OnItemClickListener, launchModeSingleInstance {
    /* access modifiers changed from: private */
    public ImageGridAdapter mAdapter;
    private PageBundle mBundle;
    private View mButtonLeft;
    private ImageButton mCamera;
    /* access modifiers changed from: private */
    public GridView mGridView;
    private ArrayList<cal> mImageInfoList = new ArrayList<>();
    /* access modifiers changed from: private */
    public int mImageThumbSpacing;
    private OnGlobalLayoutListener mListener = null;
    private String mPhotoType = null;
    private boolean mShowBtn = false;
    private TextView mTextViewTitle;
    private final int numColumns = 2;

    static class a implements OnGlobalLayoutListener {
        private WeakReference<ImageGridNodePage> a;

        public a(ImageGridNodePage imageGridNodePage) {
            this.a = new WeakReference<>(imageGridNodePage);
        }

        public final void onGlobalLayout() {
            ImageGridNodePage imageGridNodePage = (ImageGridNodePage) this.a.get();
            if (!(imageGridNodePage == null || imageGridNodePage.mAdapter == null || imageGridNodePage.mAdapter.getNumColumns() != 0)) {
                imageGridNodePage.mAdapter.setNumColumns(2);
                imageGridNodePage.mAdapter.setItemHeight((imageGridNodePage.mGridView.getWidth() / 2) - imageGridNodePage.mImageThumbSpacing);
            }
        }
    }

    public void onCallbackDestroy() {
    }

    /* access modifiers changed from: protected */
    public byg createPresenter() {
        return new byg(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.photopreview_grid_main);
        if (this.mBundle != null) {
            this.mBundle.clear();
        }
        this.mBundle = getArguments();
        if (this.mBundle != null) {
            this.mImageInfoList = (ArrayList) this.mBundle.getObject("data");
            this.mShowBtn = this.mBundle.getBoolean("show_btn");
            this.mPhotoType = this.mBundle.getString("type", "");
        }
        if (this.mImageInfoList == null) {
            this.mImageInfoList = new ArrayList<>();
        }
        initView(getContentView());
        initData();
    }

    private void initView(View view) {
        if (view != null) {
            this.mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
            this.mButtonLeft = view.findViewById(R.id.title_btn_left);
            this.mButtonLeft.setClickable(true);
            this.mButtonLeft.setOnClickListener(this);
            if (this.mShowBtn) {
                this.mCamera = (ImageButton) view.findViewById(R.id.camera);
                this.mCamera.setVisibility(0);
                this.mCamera.setOnClickListener(this);
            }
            this.mTextViewTitle = (TextView) view.findViewById(R.id.title_text_name);
            this.mTextViewTitle.setText(R.string.photo_preview_title);
            this.mGridView = (GridView) view.findViewById(R.id.gridView);
            AMapPageUtil.setPageStateListener(this, new IPageStateListener() {
                public final void onCover() {
                }

                public final void onAppear() {
                    ImageGridNodePage.this.requestScreenOrientation(-1);
                    ImageGridNodePage.this.requestScreenOrientation(1);
                }
            });
        }
    }

    private void initData() {
        this.mAdapter = new ImageGridAdapter(getContext(), this.mImageInfoList, this.mPhotoType);
        if (this.mGridView.getAdapter() == null) {
            this.mGridView.setAdapter(this.mAdapter);
        }
        this.mGridView.setOnItemClickListener(this);
        this.mGridView.setSelector(new ColorDrawable(0));
        if (this.mListener == null) {
            this.mListener = new a(this);
            this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(this.mListener);
        }
    }

    public void destroy() {
        if (this.mGridView != null) {
            this.mGridView.setAdapter(null);
        }
        if (this.mAdapter != null) {
            this.mAdapter = null;
        }
        if (this.mListener != null) {
            this.mListener = null;
        }
    }

    public void stop() {
        if (this.mGridView != null) {
            this.mGridView.getViewTreeObserver().removeGlobalOnLayoutListener(this.mListener);
        }
    }

    public void start() {
        if (this.mGridView != null) {
            this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(this.mListener);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.title_btn_left) {
            finish();
            return;
        }
        if (view.getId() == R.id.camera) {
            caf caf = (caf) defpackage.esb.a.a.a(caf.class);
            if (caf != null) {
                caf.a(this);
            }
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.mBundle.putInt("jsindex", (int) j);
        this.mBundle.putObject("data", this.mImageInfoList);
        this.mBundle.putString("type", this.mPhotoType);
        startPage(ImageDetailPage.class, this.mBundle);
    }
}
