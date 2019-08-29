package com.autonavi.bundle.searchcommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class SearchKeywordResultTitleView extends RelativeLayout implements OnClickListener {
    private View mButtonLeft;
    private Button mButtonSearch;
    private TextView mButtonSwitch;
    private View mHeaderMainLayout;
    private a mListener;

    public interface a {
        void a();

        void b();

        void c();

        void d();
    }

    public void setOnSearchKeywordResultTitleViewListener(a aVar) {
        this.mListener = aVar;
    }

    public SearchKeywordResultTitleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.search_keyword_result_title_layout, this);
        this.mButtonLeft = findViewById(R.id.title_btn_left);
        this.mButtonSearch = (Button) findViewById(R.id.btn_search);
        this.mButtonSearch.setVisibility(0);
        this.mButtonSwitch = (TextView) findViewById(R.id.img_showmap);
        this.mHeaderMainLayout = findViewById(R.id.header_main_layout);
        this.mButtonSwitch.setTag(Boolean.TRUE);
        this.mButtonSearch.setOnClickListener(this);
        this.mButtonLeft.setOnClickListener(this);
        this.mButtonSwitch.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_search) {
            if (this.mListener != null) {
                this.mListener.b();
            }
        } else if (id == R.id.title_btn_left) {
            if (this.mListener != null) {
                this.mListener.a();
            }
        } else if (R.id.img_showmap == id && this.mListener != null) {
            this.mListener.d();
        }
    }

    public void showListModel(boolean z) {
        if (z) {
            this.mButtonSwitch.setTag(Boolean.TRUE);
            this.mButtonSwitch.setText(getContext().getString(R.string.v4_btn_map));
            this.mButtonSwitch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ditu, 0, 0, 0);
            return;
        }
        this.mButtonSwitch.setTag(Boolean.FALSE);
        this.mButtonSwitch.setText(getContext().getString(R.string.v4_btn_list));
        this.mButtonSwitch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liebiao, 0, 0, 0);
    }

    public void setKeyword(String str) {
        this.mButtonSearch.setText(str);
    }

    public String getKeyword() {
        return this.mButtonSearch != null ? this.mButtonSearch.getText().toString() : "";
    }

    public void goneRightButton() {
        if (this.mButtonSwitch != null) {
            this.mButtonSwitch.setVisibility(8);
        }
    }

    public void visiableRightButton() {
        if (this.mButtonSwitch != null) {
            this.mButtonSwitch.setVisibility(0);
        }
    }

    public void setTitleBackGround(int i) {
        this.mHeaderMainLayout.setBackgroundResource(i);
    }
}
