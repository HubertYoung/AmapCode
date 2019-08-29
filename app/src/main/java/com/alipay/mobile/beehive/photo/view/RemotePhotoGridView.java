package com.alipay.mobile.beehive.photo.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RelativeLayout;
import com.alipay.mobile.antui.basic.AUPullLoadingView;
import com.alipay.mobile.antui.basic.AUPullRefreshView;
import com.alipay.mobile.antui.basic.AUPullRefreshView.RefreshListener;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.data.GridAdapter.OnGridListener;
import com.alipay.mobile.beehive.photo.data.PhotoItem;
import com.alipay.mobile.beehive.photo.data.PhotoResult;
import com.alipay.mobile.beehive.photo.data.PhotoRpcRunnable;
import com.alipay.mobile.beehive.photo.data.RemotePhotoAdapter;
import com.alipay.mobile.beehive.rpc.LoadingMode;
import com.alipay.mobile.beehive.rpc.RpcRunConfig;
import com.alipay.mobile.beehive.rpc.RpcRunner;
import com.alipay.mobile.beehive.rpc.RpcSubscriber;
import com.alipay.mobile.beehive.rpc.RpcTask;
import com.alipay.mobile.beehive.rpc.RpcUiProcessor;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.ArrayList;
import java.util.List;

public class RemotePhotoGridView extends RelativeLayout {
    public static final String LOADING_TAG = "loading";
    public static final String MOCK_TAG = "mock";
    public static final String REFRESH_CURSOR = "0";
    /* access modifiers changed from: private */
    public boolean isLoading;
    /* access modifiers changed from: private */
    public APLoadingView mAPLoadingView;
    /* access modifiers changed from: private */
    public RemotePhotoAdapter mGridAdapter;
    private PhotoRpcRunnable mLoadMoreRpcRunnable;
    /* access modifiers changed from: private */
    public RpcRunner mLoadMoreRpcRunner;
    public String mNextCursor;
    private int mNumColumns;
    private PhotoGridView mPhotoGridView;
    /* access modifiers changed from: private */
    public List<PhotoItem> mPhotoItems;
    /* access modifiers changed from: private */
    public AUPullRefreshView mPullRefreshView;
    private PhotoRpcRunnable mRefreshRpcRunnable;
    /* access modifiers changed from: private */
    public RpcRunner mRefreshRpcRunner;
    private RpcRunConfig mRpcRunConfig;
    /* access modifiers changed from: private */
    public Handler mUIHandler;

    public RemotePhotoGridView(Context context) {
        this(context, null);
    }

    public RemotePhotoGridView(Context context, AttributeSet as) {
        this(context, as, null, null, null);
    }

    public RemotePhotoGridView(Context context, AttributeSet as, PhotoRpcRunnable refreshRpcRunnable, PhotoRpcRunnable loadMoreRpcRunnable, Bundle params) {
        super(context, as);
        this.mNumColumns = 3;
        this.isLoading = false;
        this.mUIHandler = new Handler(Looper.getMainLooper());
        init(context, refreshRpcRunnable, loadMoreRpcRunnable, params);
    }

    private void init(Context context, PhotoRpcRunnable refreshRpcRunnable, PhotoRpcRunnable loadMoreRpcRunnable, Bundle params) {
        LayoutInflater.from(context).inflate(R.layout.remote_photo_grid, this);
        parseParams(params);
        initLoadingView();
        initPullRefreshView();
        initGridView();
        initRpc(refreshRpcRunnable, loadMoreRpcRunnable);
        render();
    }

    private void initLoadingView() {
        this.mAPLoadingView = (APLoadingView) findViewById(R.id.loading_view);
    }

    private void render() {
        if (this.mPhotoItems == null || this.mPhotoItems.isEmpty() || TextUtils.isEmpty(this.mNextCursor)) {
            this.mRefreshRpcRunner.start("0");
            return;
        }
        this.mAPLoadingView.setVisibility(8);
        this.mPullRefreshView.setVisibility(0);
        addLoadMockData(this.mPhotoItems);
        this.mGridAdapter.setData(this.mPhotoItems);
        this.mGridAdapter.notifyDataSetChanged();
    }

    private void parseParams(Bundle params) {
        if (params != null) {
            this.mNumColumns = params.getInt(PhotoParam.REMOTEPHOTO_NUMCOLUMNS, 3);
            this.mPhotoItems = params.getParcelableArrayList(PhotoParam.REMOTEPHOTO_PRELOADDATA);
            this.mNextCursor = params.getString(PhotoParam.REMOTEPHOTO_CURSOR);
        }
    }

    private void initRpc(PhotoRpcRunnable refreshRpcRunnable, PhotoRpcRunnable loadMoreRpcRunnable) {
        this.mRefreshRpcRunnable = refreshRpcRunnable;
        this.mLoadMoreRpcRunnable = loadMoreRpcRunnable;
        this.mRpcRunConfig = new RpcRunConfig();
        this.mRpcRunConfig.loadingMode = LoadingMode.SILENT;
        this.mRpcRunConfig.showFlowTipOnEmpty = false;
        this.mRpcRunConfig.flowTipHolderViewId = R.id.tips;
        this.mRefreshRpcRunner = new RpcRunner(this.mRpcRunConfig, this.mRefreshRpcRunnable, new RpcSubscriber<PhotoResult>((Activity) getContext()) {
            /* access modifiers changed from: protected */
            public final void onStart() {
                super.onStart();
                RemotePhotoGridView.this.isLoading = true;
            }

            /* access modifiers changed from: private */
            /* renamed from: a */
            public void onSuccess(PhotoResult result) {
                RemotePhotoGridView.this.mNextCursor = result.nextCursor;
                RemotePhotoGridView.this.addLoadMockData(result.photoItems);
                RemotePhotoGridView.this.mPhotoItems = result.photoItems;
                RemotePhotoGridView.this.mGridAdapter.setData(RemotePhotoGridView.this.mPhotoItems);
                RemotePhotoGridView.this.mGridAdapter.notifyDataSetChanged();
                RemotePhotoGridView.this.mUIHandler.post(new Runnable() {
                    public final void run() {
                        RemotePhotoGridView.this.mAPLoadingView.setVisibility(8);
                        RemotePhotoGridView.this.mPullRefreshView.setVisibility(0);
                    }
                });
            }

            /* access modifiers changed from: private */
            /* renamed from: b */
            public void onFail(PhotoResult result) {
                super.onFail(result);
                a();
            }

            private void a() {
                if (getRpcUiProcessor() == null) {
                    return;
                }
                if (RemotePhotoGridView.this.mPhotoItems == null || RemotePhotoGridView.this.mPhotoItems.isEmpty()) {
                    final RpcUiProcessor rpcUiProcessor = getRpcUiProcessor();
                    rpcUiProcessor.showNetworkError(null, null, new Runnable() {
                        public final void run() {
                            RemotePhotoGridView.this.mRefreshRpcRunner.start("0");
                            rpcUiProcessor.hideFlowTipViewIfShow();
                        }
                    });
                }
            }

            /* access modifiers changed from: protected */
            public final void onException(Exception ex, RpcTask task) {
                super.onException(ex, task);
                a();
            }

            /* access modifiers changed from: protected */
            public final void onFinishEnd() {
                RemotePhotoGridView.this.mPullRefreshView.refreshFinished();
                RemotePhotoGridView.this.isLoading = false;
            }
        });
        this.mLoadMoreRpcRunner = new RpcRunner(this.mRpcRunConfig, this.mLoadMoreRpcRunnable, new RpcSubscriber<PhotoResult>() {
            /* access modifiers changed from: protected */
            public final void onStart() {
                super.onStart();
                RemotePhotoGridView.this.isLoading = true;
            }

            /* access modifiers changed from: private */
            /* renamed from: a */
            public void onSuccess(PhotoResult result) {
                if (!RemotePhotoGridView.this.mPhotoItems.isEmpty()) {
                    List photoItems = RemotePhotoGridView.this.filterLoadMockData(RemotePhotoGridView.this.mPhotoItems);
                    photoItems.addAll(result.photoItems);
                    RemotePhotoGridView.this.mNextCursor = result.nextCursor;
                    if (!TextUtils.isEmpty(RemotePhotoGridView.this.mNextCursor) && result.photoItems != null && !result.photoItems.isEmpty()) {
                        RemotePhotoGridView.this.addLoadMockData(photoItems);
                    }
                    RemotePhotoGridView.this.mPhotoItems = photoItems;
                    RemotePhotoGridView.this.mGridAdapter.setData(RemotePhotoGridView.this.mPhotoItems);
                    RemotePhotoGridView.this.mGridAdapter.notifyDataSetChanged();
                }
            }

            /* access modifiers changed from: private */
            /* renamed from: b */
            public void onFail(PhotoResult result) {
                super.onFail(result);
                RemotePhotoGridView.this.onLoadMoreError();
            }

            /* access modifiers changed from: protected */
            public final void onException(Exception ex, RpcTask task) {
                super.onException(ex, task);
                RemotePhotoGridView.this.onLoadMoreError();
            }

            /* access modifiers changed from: protected */
            public final void onFinishEnd() {
                RemotePhotoGridView.this.isLoading = false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void onLoadMoreError() {
        this.mPhotoItems = filterLoadMockData(this.mPhotoItems);
        this.mGridAdapter.setData(this.mPhotoItems);
        this.mGridAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public void addLoadMockData(List<PhotoItem> photoItems) {
        if (!photoItems.isEmpty()) {
            int loadmoreItemNum = this.mNumColumns - (photoItems.size() % this.mNumColumns);
            while (loadmoreItemNum < this.mNumColumns && loadmoreItemNum > 0) {
                photoItems.add(new PhotoItem((String) "mock"));
                loadmoreItemNum--;
            }
            if (this.mLoadMoreRpcRunner != null && !TextUtils.isEmpty(this.mNextCursor) && !this.mNextCursor.equals("0")) {
                photoItems.add(new PhotoItem((String) LOADING_TAG));
            }
        }
    }

    private void initGridView() {
        this.mPhotoGridView = (PhotoGridView) findViewById(R.id.gv_photo);
        this.mPhotoGridView.setNumColumns(this.mNumColumns);
        this.mPhotoGridView.setOnScrollListener(new OnScrollListener() {
            public final void onScrollStateChanged(AbsListView absListView, int i) {
            }

            public final void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (RemotePhotoGridView.this.canLoadMore(view)) {
                    RemotePhotoGridView.this.mLoadMoreRpcRunner.start(RemotePhotoGridView.this.mNextCursor);
                }
            }
        });
        this.mGridAdapter = new RemotePhotoAdapter(getContext(), null);
        this.mGridAdapter.setGridListener(new OnGridListener() {
            public final void onGridClick(PhotoGrid grid, int position) {
                if (grid != null && RemotePhotoGridView.this.mPhotoItems != null && position < RemotePhotoGridView.this.mPhotoItems.size()) {
                    RemotePhotoGridView.this.preview(position);
                }
            }

            public final void onGridChecked(PhotoGrid grid, int position) {
            }
        });
        this.mPhotoGridView.setAdapter(this.mGridAdapter);
        this.mGridAdapter.setData(this.mPhotoItems);
        this.mGridAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public boolean canLoadMore(AbsListView view) {
        if (view.getLastVisiblePosition() < 0 || this.mPhotoItems == null || view.getLastVisiblePosition() != view.getCount() - 1 || view.getLastVisiblePosition() >= this.mPhotoItems.size()) {
            return false;
        }
        return LOADING_TAG.equals(this.mPhotoItems.get(view.getLastVisiblePosition()).getPhotoPath()) && this.mLoadMoreRpcRunner != null && !this.isLoading && !TextUtils.isEmpty(this.mNextCursor) && !this.mNextCursor.equals("0");
    }

    /* access modifiers changed from: private */
    public void preview(int index) {
        List photoInfos = new ArrayList();
        Bundle bundle = new Bundle();
        bundle.putInt(PhotoParam.PREVIEW_POSITION, index);
        bundle.putBoolean(PhotoParam.PREVIEW_CLICK_EXIT, true);
        photoInfos.addAll(filterLoadMockData(this.mPhotoItems));
        ((PhotoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PhotoService.class.getName())).browsePhoto(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication(), photoInfos, bundle, null);
    }

    /* access modifiers changed from: private */
    @NonNull
    public List<PhotoItem> filterLoadMockData(List<PhotoItem> sourcePhotoItems) {
        List photoItems = new ArrayList(sourcePhotoItems);
        int mockNum = 0;
        for (int i = photoItems.size() - 1; i >= 0; i--) {
            PhotoItem item = (PhotoItem) photoItems.get(i);
            if (!LOADING_TAG.equals(item.getPhotoPath()) && !"mock".equals(item.getPhotoPath())) {
                break;
            }
            mockNum++;
        }
        while (mockNum > 0) {
            photoItems.remove(photoItems.size() - 1);
            mockNum--;
        }
        return photoItems;
    }

    private void initPullRefreshView() {
        this.mPullRefreshView = (AUPullRefreshView) findViewById(R.id.pullrefreshview);
        this.mPullRefreshView.setEnablePull(true);
        this.mPullRefreshView.setRefreshListener(new RefreshListener() {
            public final void onRefresh() {
                if (RemotePhotoGridView.this.isLoading) {
                    RemotePhotoGridView.this.mPullRefreshView.refreshFinished();
                    return;
                }
                RemotePhotoGridView.this.mRefreshRpcRunner.start("0");
            }

            public final AUPullLoadingView getOverView() {
                return (AUPullLoadingView) LayoutInflater.from(RemotePhotoGridView.this.getContext()).inflate(com.alipay.mobile.antui.R.layout.au_framework_pullrefresh_overview, null);
            }

            public final boolean canRefresh() {
                return true;
            }
        });
    }
}
