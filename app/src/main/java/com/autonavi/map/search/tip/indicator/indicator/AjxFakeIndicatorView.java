package com.autonavi.map.search.tip.indicator.indicator;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.map.search.manager.SearchResultTipDetailViewManager;
import com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView;
import com.autonavi.map.widget.DrawableCenterTextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.PoiDetailSlidingView;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxFakeIndicatorView extends FrameLayout implements OnClickListener {
    private static final String TAG = "AjxFakeIndicatorView";
    protected LinearLayout m01Layout;
    protected DrawableCenterTextView m02Button;
    protected LinearLayout m02Layout;
    protected DrawableCenterTextView m03Button;
    protected LinearLayout m03Layout;
    protected ImageView mDetailsImage;
    protected TextView mDetailsText;
    private OnTouchListener mDetailsTouchListener;
    private String mIndicatorViewType;
    private a mIndicatorVisibleHolder;
    /* access modifiers changed from: private */
    public int sTouchSlop;

    public static class a {
        String a;
        String b;
        String c;
    }

    public AjxFakeIndicatorView(Context context) {
        this(context, null);
    }

    public AjxFakeIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sTouchSlop = 0;
        this.mDetailsTouchListener = new OnTouchListener() {
            private int b;
            private boolean c;
            private boolean d;

            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean z = true;
                switch (motionEvent.getAction()) {
                    case 0:
                        this.d = true;
                        this.b = (int) motionEvent.getY();
                        this.c = true;
                        break;
                    case 1:
                        if (this.c && this.d) {
                            this.d = false;
                            if (motionEvent.getAction() == 1) {
                                AjxFakeIndicatorView.this.handleClick01Button();
                                break;
                            }
                        }
                        break;
                    case 2:
                        if (Math.abs(this.b - ((int) motionEvent.getY())) >= AjxFakeIndicatorView.this.sTouchSlop) {
                            z = false;
                        }
                        this.c = z;
                        break;
                }
                return false;
            }
        };
        LayoutInflater.from(context).inflate(R.layout.poi_indicator, this, true);
        this.sTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void updateUI(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("indicatorJsonFromAJX must not be null.");
        }
        parseIndicator(str);
        initView();
        checkBtnVisibility();
    }

    private void checkBtnVisibility() {
        if (this.mIndicatorVisibleHolder != null) {
            int i = 8;
            this.m01Layout.setVisibility(TextUtils.equals(this.mIndicatorVisibleHolder.a, "VISIBLE") ? 0 : 8);
            this.m02Layout.setVisibility(TextUtils.equals(this.mIndicatorVisibleHolder.b, "VISIBLE") ? 0 : 8);
            LinearLayout linearLayout = this.m03Layout;
            if (TextUtils.equals(this.mIndicatorVisibleHolder.c, "VISIBLE")) {
                i = 0;
            }
            linearLayout.setVisibility(i);
        }
    }

    private void parseIndicator(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.mIndicatorViewType = jSONObject.optString("indicatorViewType");
            JSONObject jSONObject2 = new JSONObject(jSONObject.optString("visible"));
            if (this.mIndicatorVisibleHolder == null) {
                this.mIndicatorVisibleHolder = new a();
            }
            this.mIndicatorVisibleHolder.a = jSONObject2.optString("1002");
            this.mIndicatorVisibleHolder.b = jSONObject2.optString("1005");
            this.mIndicatorVisibleHolder.c = jSONObject2.optString("2003");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        init01Button();
        init02Button();
        init03Button();
    }

    public void init01Button() {
        this.m01Layout = (LinearLayout) findViewById(R.id.linear_01);
        this.mDetailsImage = (ImageView) findViewById(R.id.image_indicator_details);
        this.mDetailsText = (TextView) findViewById(R.id.text_indicator_details);
        if (ags.a(DoNotUseTool.getContext()).right < 720) {
            this.mDetailsText.setText(R.string.poi_indicator_map);
        } else {
            this.mDetailsText.setText(R.string.poi_indicator_check_map);
        }
        this.mDetailsImage.setImageResource(R.drawable.indicator_detail_map_selector);
        this.m01Layout.setOnTouchListener(this.mDetailsTouchListener);
    }

    public void init02Button() {
        this.m02Layout = (LinearLayout) findViewById(R.id.linear_02);
        this.m02Button = (DrawableCenterTextView) findViewById(R.id.text_indicator_02_btn);
        if (TextUtils.equals(this.mIndicatorViewType, "type_my_position")) {
            this.m02Button.setText(R.string.poi_indicator_call_taxi);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_call_taxi), null, null, null);
        } else if (TextUtils.equals(this.mIndicatorViewType, "type_press_long")) {
            this.m02Button.setText(R.string.poi_indicator_new_add);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_new_add), null, null, null);
        } else if (TextUtils.equals(this.mIndicatorViewType, "type_call_taxi_by_server")) {
            this.m02Button.setText(R.string.poi_indicator_call_taxi);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_call_taxi), null, null, null);
        } else {
            this.m02Button.setText(R.string.poi_indicator_btn);
            this.m02Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_btn), null, null, null);
        }
        this.m02Layout.setOnClickListener(this);
    }

    public void init03Button() {
        this.m03Layout = (LinearLayout) findViewById(R.id.linear_03);
        this.m03Button = (DrawableCenterTextView) findViewById(R.id.text_indicator_03_btn);
        this.m03Button.setText(R.string.poi_indicator_route);
        this.m03Button.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.poi_indicator_route), null, null, null);
        this.m03Layout.setOnClickListener(this);
    }

    /* access modifiers changed from: private */
    public void handleClick01Button() {
        View findCurrentSlidingParentView = findCurrentSlidingParentView();
        if (findCurrentSlidingParentView != null) {
            ((SearchPoiIndicatorView) findCurrentSlidingParentView.findViewById(R.id.IndicatorView_Root)).handleClick01Button();
        }
    }

    private void handleClick02Button() {
        View findCurrentSlidingParentView = findCurrentSlidingParentView();
        if (findCurrentSlidingParentView != null) {
            ((SearchPoiIndicatorView) findCurrentSlidingParentView.findViewById(R.id.IndicatorView_Root)).handleClick02Button();
        }
    }

    private void handleClick03Button() {
        View findCurrentSlidingParentView = findCurrentSlidingParentView();
        if (findCurrentSlidingParentView != null) {
            ((SearchPoiIndicatorView) findCurrentSlidingParentView.findViewById(R.id.IndicatorView_Root)).handleClick03Button();
        }
    }

    private View findCurrentSlidingParentView() {
        View view = (View) getParent();
        do {
            view = (View) view.getParent();
        } while (!(view instanceof PoiDetailSlidingView));
        return view;
    }

    public void onClick(View view) {
        if (!bnp.a()) {
            if (view == this.m02Layout) {
                handleClick02Button();
                return;
            }
            if (view == this.m03Layout) {
                handleClick03Button();
            }
        }
    }

    private View findCurrentAuiRootViewByInnerPage() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof PoiDetailPageNew) {
            PoiDetailPageNew poiDetailPageNew = (PoiDetailPageNew) pageContext;
            if (((cbe) poiDetailPageNew.mPresenter) instanceof cbe) {
                bxk bxk = ((cbe) poiDetailPageNew.mPresenter).a;
                if (bxk instanceof bxl) {
                    return ((SearchResultTipDetailViewManager) ((bxl) bxk).F).k.a().q;
                }
            }
        }
        return null;
    }
}
