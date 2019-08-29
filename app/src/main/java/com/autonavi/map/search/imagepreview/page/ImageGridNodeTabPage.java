package com.autonavi.map.search.imagepreview.page;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.search.imagepreview.adapter.ImageGridAdapter;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.b;
import java.lang.ref.WeakReference;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class ImageGridNodeTabPage extends AbsImageGridNodeBasePage implements launchModeSingleInstance {
    private final int a = 2;
    /* access modifiers changed from: private */
    public int b;
    private TitleBar c;
    private boolean d = false;
    /* access modifiers changed from: private */
    public String e = null;
    private POI f;
    /* access modifiers changed from: private */
    public PageBundle g;
    /* access modifiers changed from: private */
    public Tab[] h = {new Tab(this), new Tab(this)};
    private erq i = new erq() {
        public final void b(int i) {
        }

        public final void a(int i) {
            for (int i2 = 0; i2 < ImageGridNodeTabPage.this.h.length; i2++) {
                if (i == i2) {
                    ImageGridNodeTabPage.this.h[i2].show();
                } else {
                    ImageGridNodeTabPage.this.h[i2].hide();
                }
            }
            ImageGridNodeTabPage.a(ImageGridNodeTabPage.this, i);
        }
    };
    private OnClickListener j = new OnClickListener() {
        public final void onClick(View view) {
            caf caf = (caf) a.a.a(caf.class);
            if (caf != null) {
                caf.a(ImageGridNodeTabPage.this);
            }
        }
    };
    private OnClickListener k = new OnClickListener() {
        public final void onClick(View view) {
            ImageGridNodeTabPage.this.finish();
        }
    };

    static class Tab implements OnGlobalLayoutListener, OnItemClickListener {
        private ImageGridAdapter mAdapter;
        /* access modifiers changed from: private */
        public GridView mGridView;
        /* access modifiers changed from: private */
        public ArrayList<cal> mImageInfoList = new ArrayList<>();
        private WeakReference<ImageGridNodeTabPage> mPage;

        public Tab(ImageGridNodeTabPage imageGridNodeTabPage) {
            this.mPage = new WeakReference<>(imageGridNodeTabPage);
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ImageGridNodeTabPage imageGridNodeTabPage = (ImageGridNodeTabPage) this.mPage.get();
            if (imageGridNodeTabPage != null) {
                imageGridNodeTabPage.g.putInt("jsindex", (int) j);
                imageGridNodeTabPage.g.putObject("data", this.mImageInfoList);
                imageGridNodeTabPage.g.putString("type", imageGridNodeTabPage.e);
                imageGridNodeTabPage.g.putBoolean("key_from_idq_plus", true);
                imageGridNodeTabPage.startPage(ImageDetailPage.class, imageGridNodeTabPage.g);
            }
        }

        public void onGlobalLayout() {
            ImageGridNodeTabPage imageGridNodeTabPage = (ImageGridNodeTabPage) this.mPage.get();
            if (!(imageGridNodeTabPage == null || this.mAdapter == null || this.mAdapter.getNumColumns() != 0)) {
                this.mAdapter.setNumColumns(2);
                this.mAdapter.setItemHeight((this.mGridView.getWidth() / 2) - imageGridNodeTabPage.b);
            }
        }

        public void init(String str) {
            ImageGridNodeTabPage imageGridNodeTabPage = (ImageGridNodeTabPage) this.mPage.get();
            if (imageGridNodeTabPage != null) {
                this.mAdapter = new ImageGridAdapter(imageGridNodeTabPage.getContext(), this.mImageInfoList, str);
                this.mGridView.setOnItemClickListener(this);
                this.mGridView.setSelector(new ColorDrawable(0));
                this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            }
        }

        public void show() {
            this.mGridView.setVisibility(0);
            if (this.mGridView.getAdapter() == null) {
                this.mGridView.setAdapter(this.mAdapter);
            }
        }

        public void hide() {
            this.mGridView.setVisibility(8);
        }

        public void onDestroy() {
            if (this.mAdapter != null) {
                this.mAdapter = null;
            }
            if (this.mGridView != null) {
                this.mGridView.setAdapter(null);
            }
        }

        public void onStop() {
            if (this.mGridView != null) {
                this.mGridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }

        public void onStart() {
            if (this.mGridView != null) {
                this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(this);
            }
        }
    }

    public void onClick(View view) {
    }

    public void onCreate(Context context) {
        int i2;
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.photopreview_grid_tab_main);
        if (this.g != null) {
            this.g.clear();
        }
        this.g = getArguments();
        if (this.g != null) {
            ArrayList arrayList = (ArrayList) this.g.getObject("key_tab_data");
            if (arrayList != null) {
                this.h[0].mImageInfoList = (ArrayList) arrayList.get(0);
                this.h[1].mImageInfoList = (ArrayList) arrayList.get(1);
            }
            this.d = this.g.getBoolean("show_btn");
            this.e = this.g.getString("type", "");
            i2 = this.g.getInt("default_tag", 0);
            this.f = (POI) this.g.getObject("POI");
        } else {
            i2 = 0;
        }
        View contentView = getContentView();
        if (contentView != null) {
            this.b = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
            this.h[0].mGridView = (GridView) contentView.findViewById(R.id.gridView_a);
            this.h[1].mGridView = (GridView) contentView.findViewById(R.id.gridView_b);
            this.c = (TitleBar) findViewById(R.id.titlebar);
            TitleBar titleBar = this.c;
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(0, new b((CharSequence) getString(R.string.image_preview_tag_restaurant)));
            arrayList2.add(1, new b((CharSequence) getString(R.string.image_preview_tag_menu)));
            titleBar.addTabs(arrayList2, i2);
            titleBar.setActionImgVisibility(this.d ? 0 : 8);
            titleBar.setOnActionClickListener(this.j);
            titleBar.setOnBackClickListener(this.k);
            titleBar.setOnTabSelectedListener(this.i);
            AMapPageUtil.setPageStateListener(this, new IPageStateListener() {
                public final void onCover() {
                }

                public final void onAppear() {
                    ImageGridNodeTabPage.this.requestScreenOrientation(-1);
                    ImageGridNodeTabPage.this.requestScreenOrientation(1);
                }
            });
        }
        a(i2);
        if (this.f != null) {
            LogManager.actionLogV25("P00306", "B001", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.f.getId()));
        }
    }

    private void a(int i2) {
        for (Tab init : this.h) {
            init.init(this.e);
        }
        this.h[i2].show();
    }

    public void start() {
        for (Tab onStart : this.h) {
            onStart.onStart();
        }
    }

    public void stop() {
        for (Tab onStop : this.h) {
            onStop.onStop();
        }
    }

    public void destroy() {
        for (Tab onDestroy : this.h) {
            onDestroy.onDestroy();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new byg(this);
    }

    static /* synthetic */ void a(ImageGridNodeTabPage imageGridNodeTabPage, int i2) {
        if (imageGridNodeTabPage.f != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2 + 1);
            LogManager.actionLogV25("P00306", "B002", new SimpleEntry("type", sb.toString()), new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, imageGridNodeTabPage.f.getId()));
        }
    }
}
