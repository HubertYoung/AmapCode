package com.autonavi.map.search.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.map.search.comment.adapter.MyCommentingListAdapter;
import com.autonavi.map.search.comment.common.adapter.GroupList;
import com.autonavi.map.search.comment.model.MyCommentingListResponse.Item;
import com.autonavi.map.search.comment.view.FloatingGroupExpandableListView;
import com.autonavi.map.search.view.FloatingBaseExpandableListAdapter;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyCommentingListView extends FrameLayout {
    private bwi mActionCommand;
    private MyCommentingListAdapter mListAdapter;
    private FloatingGroupExpandableListView mListView;
    private ViewGroup mSceneBannerContainer;
    private ImageView mSceneImageView;
    private TextView mSceneTextView;
    private ViewGroup mSpecialSceneView;

    public void onDestroy() {
    }

    public MyCommentingListView(@NonNull Context context) {
        super(context);
    }

    public MyCommentingListView(Context context, bwi bwi) {
        super(context);
        this.mActionCommand = bwi;
        LayoutInflater.from(context).inflate(R.layout.comment_view_list_todo, this);
        this.mListView = (FloatingGroupExpandableListView) findViewById(R.id.comment_list);
        this.mSpecialSceneView = (ViewGroup) findViewById(R.id.special_scene);
        this.mSceneImageView = (ImageView) findViewById(R.id.scene_image);
        this.mSceneTextView = (TextView) findViewById(R.id.scene_text);
        this.mSceneBannerContainer = (ViewGroup) findViewById(R.id.scene_banner);
        this.mListView.setOverScrollMode(2);
        this.mListView.disableGroupExpand();
        this.mListAdapter = new MyCommentingListAdapter(context, this.mActionCommand);
        this.mListView.setAdapter((FloatingBaseExpandableListAdapter) this.mListAdapter);
        this.mListView.setVisibility(8);
        this.mSpecialSceneView.setVisibility(8);
    }

    public void showEmptyView() {
        this.mListView.setVisibility(8);
        this.mSpecialSceneView.setVisibility(0);
        this.mSceneImageView.setImageResource(R.drawable.comment_scene_1);
        this.mSceneTextView.setText("你还没有待点评的地点，\n不如先去逛逛吧~");
    }

    private List<GroupList<bwo, Item>> buildGroupLists(bwm bwm) {
        ArrayList arrayList = new ArrayList();
        List<T> unmodifiableList = Collections.unmodifiableList(bwm.e);
        if (unmodifiableList != null && unmodifiableList.size() > 0) {
            GroupList groupList = new GroupList(new bwo(0, Html.fromHtml(bwm.g)));
            groupList.addAll(unmodifiableList);
            arrayList.add(groupList);
        }
        List<T> unmodifiableList2 = Collections.unmodifiableList(bwm.f);
        if (unmodifiableList2 != null && unmodifiableList2.size() > 0) {
            GroupList groupList2 = new GroupList(new bwo(1, Html.fromHtml(bwm.h)));
            groupList2.addAll(unmodifiableList2);
            arrayList.add(groupList2);
        }
        return arrayList;
    }

    public void updateUI(bwm bwm, bwm bwm2) {
        if (bwm.m) {
            List<GroupList<bwo, Item>> buildGroupLists = buildGroupLists(bwm);
            if (buildGroupLists.size() > 0) {
                this.mListView.setVisibility(0);
                this.mSpecialSceneView.setVisibility(8);
                this.mListAdapter.setGroupLists(buildGroupLists);
                this.mListView.expandAllGroup();
            } else if (!bwm.o) {
                showEmptyView();
            } else {
                this.mListView.setVisibility(8);
                this.mSpecialSceneView.setVisibility(0);
                this.mSceneImageView.setImageResource(R.drawable.comment_scene_2);
                this.mSceneTextView.setText("评论达人!\n恭喜你完成了所有的评论!");
            }
        }
    }
}
