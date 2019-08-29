package com.autonavi.map.search.comment;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.map.search.comment.adapter.MyCommentedListAdapter;
import com.autonavi.map.search.comment.common.net.JsonParserCallback;
import com.autonavi.map.search.comment.model.MyCommentedListResponse;
import com.autonavi.map.search.comment.net.CommentParam.CommentedListParam;
import com.autonavi.map.search.comment.view.FloatingGroupExpandableListView;
import com.autonavi.map.search.comment.view.PullToRefreshFloatingGroupListView;
import com.autonavi.map.search.view.FloatingBaseExpandableListAdapter;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class MyCommentedListView extends FrameLayout {
    /* access modifiers changed from: private */
    public bwi mActionCommand;
    private MyCommentedListAdapter mListAdapter;
    /* access modifiers changed from: private */
    public Callback<MyCommentedListResponse> mLoadCallback = new Callback<MyCommentedListResponse>() {
        public void callback(MyCommentedListResponse myCommentedListResponse) {
            MyCommentedListView.this.mActionCommand.a(bwj.a(myCommentedListResponse, false));
            MyCommentedListView.this.mPullToRefreshView.onRefreshComplete();
        }

        public void error(Throwable th, boolean z) {
            ToastHelper.showToast(th.getMessage());
            MyCommentedListView.this.mPullToRefreshView.onRefreshComplete();
        }
    };
    /* access modifiers changed from: private */
    public AosRequest mLoadRequest;
    /* access modifiers changed from: private */
    public AosResponseCallback mLoadResponse;
    private d mOnRefreshListener = new d() {
        public final void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        }

        public final void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
            MyCommentedListView.this.mLoadRequest = aax.a(new CommentedListParam(MyCommentedListView.this.mState.i + 1));
            MyCommentedListView.this.mLoadResponse = new JsonParserCallback(new WeakReference(MyCommentedListView.this.mLoadCallback), null);
            yq.a();
            yq.a(MyCommentedListView.this.mLoadRequest, MyCommentedListView.this.mLoadResponse);
        }
    };
    /* access modifiers changed from: private */
    public PullToRefreshFloatingGroupListView mPullToRefreshView;
    private ViewGroup mSceneBannerContainer;
    private ImageView mSceneImageView;
    private TextView mSceneTextView;
    private ViewGroup mSpecialSceneView;
    /* access modifiers changed from: private */
    public bwm mState;

    public MyCommentedListView(@NonNull Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mListAdapter.onConfigurationChanged(configuration);
    }

    public MyCommentedListView(Context context, bwi bwi) {
        super(context);
        this.mActionCommand = bwi;
        LayoutInflater.from(context).inflate(R.layout.comment_view_list_done, this);
        this.mPullToRefreshView = (PullToRefreshFloatingGroupListView) findViewById(R.id.comment_list);
        this.mSpecialSceneView = (ViewGroup) findViewById(R.id.special_scene);
        this.mSceneImageView = (ImageView) findViewById(R.id.scene_image);
        this.mSceneTextView = (TextView) findViewById(R.id.scene_text);
        this.mSceneBannerContainer = (ViewGroup) findViewById(R.id.scene_banner);
        this.mPullToRefreshView.setMode(Mode.PULL_FROM_END);
        this.mPullToRefreshView.setFootershowflag(true);
        this.mListAdapter = new MyCommentedListAdapter(getContext(), bwi);
        ((FloatingGroupExpandableListView) this.mPullToRefreshView.getRefreshableView()).setAdapter((FloatingBaseExpandableListAdapter) this.mListAdapter);
        ((FloatingGroupExpandableListView) this.mPullToRefreshView.getRefreshableView()).setOverScrollMode(2);
        ((FloatingGroupExpandableListView) this.mPullToRefreshView.getRefreshableView()).setGroupIndicator(null);
        ((FloatingGroupExpandableListView) this.mPullToRefreshView.getRefreshableView()).setDivider(null);
        ((FloatingGroupExpandableListView) this.mPullToRefreshView.getRefreshableView()).disableGroupExpand();
        this.mPullToRefreshView.setOnRefreshListener(this.mOnRefreshListener);
        this.mPullToRefreshView.setVisibility(8);
        this.mSpecialSceneView.setVisibility(8);
    }

    public void updateUI(bwm bwm, bwm bwm2) {
        Mode mode;
        this.mState = bwm;
        if (bwm.l) {
            List<T> unmodifiableList = Collections.unmodifiableList(bwm.d);
            if (unmodifiableList.size() > 0) {
                this.mPullToRefreshView.setVisibility(0);
                this.mSpecialSceneView.setVisibility(8);
                this.mListAdapter.setGroupLists(unmodifiableList);
                ((FloatingGroupExpandableListView) this.mPullToRefreshView.getRefreshableView()).expandAllGroup();
                PullToRefreshFloatingGroupListView pullToRefreshFloatingGroupListView = this.mPullToRefreshView;
                if (bwm.i >= bwm.j) {
                    mode = Mode.DISABLED;
                } else {
                    mode = Mode.PULL_FROM_END;
                }
                pullToRefreshFloatingGroupListView.setMode(mode);
            } else if (!bwm.n) {
                showEmptyView();
            } else {
                this.mPullToRefreshView.setVisibility(8);
                this.mSpecialSceneView.setVisibility(0);
                this.mSceneImageView.setImageResource(R.drawable.comment_scene_1);
                this.mSceneTextView.setText("你还没有在任何地点留下评论，\n先去看看你去过哪些待点评的地方吧~");
            }
        }
    }

    public void showEmptyView() {
        this.mPullToRefreshView.setVisibility(8);
        this.mSpecialSceneView.setVisibility(0);
        this.mSceneImageView.setImageResource(R.drawable.comment_scene_1);
        this.mSceneTextView.setText("你还没有在任何地点留下评论，\n不如先去逛逛吧~");
    }

    public void onDestroy() {
        if (this.mLoadRequest != null) {
            yq.a();
            yq.a(this.mLoadRequest);
        }
        this.mListAdapter.onDestroy();
        this.mPullToRefreshView.unRegistAllListener();
    }
}
