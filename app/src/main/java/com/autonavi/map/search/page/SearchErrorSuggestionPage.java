package com.autonavi.map.search.page;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;

public class SearchErrorSuggestionPage extends AbstractSearchBasePage<cbd> implements OnClickListener, OnItemClickListener {
    public static final String BUNDLE_KEY_KEYWORD = "bundle_key_keyword";
    public static final String BUNDLE_KEY_ONITEMLISTENER = "bundle_key_onItemListener";
    public static final String BUNDLE_KEY_SELECTED = "bunde_key_selected";
    private View guideTipLayout;
    private ListView lv;
    private String mKeyword;
    private OnItemClickListener mOnItemClickListener;
    private View tipDivider;
    private View title_view;

    /* renamed from: tv reason: collision with root package name */
    private TextView f5tv;
    private TextView tvGuideBtn;
    private TextView tvGuideText;

    /* access modifiers changed from: protected */
    public cbd createPresenter() {
        return new cbd(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.search_error_correction_xml);
        requestScreenOrientation(1);
        initView(getContentView());
        initData();
    }

    private void initData() {
        PageBundle arguments = getArguments();
        this.mKeyword = arguments.getString("bundle_key_keyword");
        this.mOnItemClickListener = (OnItemClickListener) arguments.getObject(BUNDLE_KEY_ONITEMLISTENER);
        this.lv.setAdapter(new ArrayAdapter(getContext(), R.layout.search_keyword_error_item, R.id.error_info, (String[]) arguments.get("bunde_key_selected")));
        this.f5tv.setText(this.mKeyword);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.title_view = findView(R.id.title_layout);
        this.lv = (ListView) findView(R.id.list);
        this.title_view.findViewById(R.id.title_btn_right).setVisibility(4);
        this.f5tv = (TextView) findView(R.id.title_text_name);
        this.lv.setOnItemClickListener(this);
        this.title_view.findViewById(R.id.title_btn_left).setOnClickListener(this);
        this.guideTipLayout = findView(R.id.guide_tip_layout);
        this.tvGuideText = (TextView) this.guideTipLayout.findViewById(R.id.tv_offline_tip);
        this.tvGuideBtn = (TextView) this.guideTipLayout.findViewById(R.id.tv_guide_btn);
        this.tipDivider = findView(R.id.tip_divider);
        if (!elc.b || !elc.a || elc.d == null) {
            this.guideTipLayout.setVisibility(8);
            this.tipDivider.setVisibility(0);
            return;
        }
        SpannableString titleSpannable = getTitleSpannable(elc.d);
        this.tvGuideText.setTextColor(getColor(R.color.f_c_3));
        this.tvGuideText.setTextSize(0, (float) getDimensionPixelOffset(R.dimen.font_26));
        this.tvGuideText.setText(titleSpannable);
        this.tvGuideBtn.setVisibility(8);
        this.guideTipLayout.setVisibility(0);
        this.tipDivider.setVisibility(8);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(adapterView, view, i, j);
        }
    }

    private SpannableString getTitleSpannable(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(getString(R.string.search_indoor_no_result_tips_start));
        String sb2 = sb.toString();
        String string = getString(R.string.search_indoor_no_result_tips_middle);
        String string2 = getString(R.string.search_indoor_no_result_tips_end);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(string);
        sb3.append(string2);
        SpannableString spannableString = new SpannableString(sb3.toString());
        int length = sb2.length();
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.f_c_6)), length, string.length() + length, 17);
        return spannableString;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.title_btn_left) {
            finish();
        }
    }
}
