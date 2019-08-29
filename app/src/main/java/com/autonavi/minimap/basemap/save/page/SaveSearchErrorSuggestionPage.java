package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;

public class SaveSearchErrorSuggestionPage extends AbstractBasePage<crm> implements OnItemClickListener, LocationNone {
    public static final String BUNDLE_KEY_KEYWORD = "bundle_key_keyword";
    public static final String BUNDLE_KEY_LISTENER = "bundle_key_listener";
    public static final String BUNDLE_KEY_SELECTED = "bunde_key_selected";
    private ListView lv;
    private OnItemClickListener mOnItemClickListener;
    private View title_view;

    /* renamed from: tv reason: collision with root package name */
    private TextView f8tv;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_search_error_correction_xml);
        init(getContentView());
    }

    public void init(View view) {
        initView(view);
        initData();
    }

    private void initData() {
        PageBundle arguments = getArguments();
        String[] strArr = (String[]) arguments.get("bunde_key_selected");
        Object obj = arguments.get(BUNDLE_KEY_LISTENER);
        if (obj != null && (obj instanceof OnItemClickListener)) {
            setOnItemClickListener((OnItemClickListener) obj);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.save_search_keyword_error_item_xml, R.id.error_info, strArr);
        String string = arguments.getString("bundle_key_keyword");
        this.lv.setAdapter(arrayAdapter);
        this.f8tv.setText(string);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.title_view = view.findViewById(R.id.title_layout);
        this.lv = (ListView) view.findViewById(R.id.list);
        this.title_view.findViewById(R.id.title_btn_right).setVisibility(4);
        this.f8tv = (TextView) view.findViewById(R.id.title_text_name);
        this.lv.setOnItemClickListener(this);
        this.title_view.findViewById(R.id.title_btn_left).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SaveSearchErrorSuggestionPage.this.finish();
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(adapterView, view, i, j);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public crm createPresenter() {
        return new crm(this);
    }
}
