package com.autonavi.map.search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a;
import com.autonavi.bundle.vui.common.emojiview.VUIEmojiView;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.minimap.R;

public class SearchResultHeaderView extends RelativeLayout implements OnClickListener {
    private ImageView mBackImageView;
    private ImageView mCloseImageView;
    private a mListener;
    private TextView mSearchContentTextView;
    private VUIEmojiView mVoiceEmojiView;

    public void setOnSearchKeywordResultTitleViewListener(a aVar) {
        this.mListener = aVar;
    }

    public SearchResultHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.search_result_header_map_and_list, this);
        this.mBackImageView = (ImageView) findViewById(R.id.back_imageview);
        this.mSearchContentTextView = (TextView) findViewById(R.id.btn_search);
        this.mSearchContentTextView.setVisibility(0);
        this.mCloseImageView = (ImageView) findViewById(R.id.close_imageview);
        this.mVoiceEmojiView = (VUIEmojiView) findViewById(R.id.btn_emoji);
        if (VUIStateManager.f().m()) {
            this.mVoiceEmojiView.setVisibility(0);
        } else {
            this.mVoiceEmojiView.setVisibility(8);
        }
        this.mCloseImageView.setTag(Boolean.TRUE);
        this.mSearchContentTextView.setOnClickListener(this);
        this.mBackImageView.setOnClickListener(this);
        this.mCloseImageView.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_search) {
            if (this.mListener != null) {
                this.mListener.b();
            }
        } else if (id == R.id.back_imageview) {
            if (this.mListener != null) {
                this.mListener.a();
            }
        } else if (id == R.id.close_imageview && this.mListener != null) {
            this.mListener.c();
        }
    }

    public void setKeyword(String str) {
        this.mSearchContentTextView.setText(str);
    }

    public String getKeyword() {
        return this.mSearchContentTextView != null ? this.mSearchContentTextView.getText().toString() : "";
    }
}
