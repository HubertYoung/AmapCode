package com.autonavi.minimap.life.common.widget.view.headersearchview;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon.a;
import com.autonavi.minimap.widget.AutoCompleteEdit;
import com.autonavi.minimap.widget.AutoCompleteEdit.TextWatcherEventListener;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.request.AosInputSuggestionParam;
import com.autonavi.widget.ui.AlertView;
import java.util.List;
import org.json.JSONObject;

public class HeaderSearchView extends RelativeLayout implements OnClickListener, Callback<List<TipItem>>, a, TextWatcherEventListener {
    private static final int PAGE = 2;
    private int FROM_PAGE;
    /* access modifiers changed from: private */
    public SuggestHistoryAdapterCommon adapter;
    public final ScaleAnimation animLeftIn;
    public final ScaleAnimation animLeftOut;
    public final ScaleAnimation animRightIn;
    public final ScaleAnimation animRightOut;
    private final Animation animTopIn;
    private String bit1;
    private Button btnSearch;
    private GeoPoint center;
    private String citycode;
    private LinearLayout container;
    private View delHisView;
    private View headview;
    OnTouchListener hisTouch;
    /* access modifiers changed from: private */
    public int hisType;
    OnClickListener hisclick;
    private View input_progressbar;
    private boolean isSuggestionEnable;
    private boolean isVoiceSearch;
    /* access modifiers changed from: private */
    public ImageView mBtnSearchClear;
    /* access modifiers changed from: private */
    public Context mContext;
    private boolean mDefaultShowVoiceBtn;
    public AutoCompleteEdit mEditSearch;
    /* access modifiers changed from: private */
    public bid mPageContext;
    /* access modifiers changed from: private */
    public doq mPosSearchViewEventListener;
    private dop mViewPresenter;
    public boolean m_bIsForceOffline = false;
    /* access modifiers changed from: private */
    public int searchFor;
    public boolean self_call;
    private boolean showAnimTopIn;
    private List<TipItem> tipItems;

    public void beforeTextChanged(View view, CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onDelClick() {
    }

    public void onFavorClick() {
    }

    public void onNoResult() {
    }

    public void onTextChanged(View view, CharSequence charSequence, int i, int i2, int i3) {
    }

    public void showIatDialog() {
    }

    public void initPosSearch(bid bid, GeoPoint geoPoint, String str, int i, int i2) {
        this.mPageContext = bid;
        this.center = geoPoint;
        this.citycode = str;
        this.hisType = i;
        this.FROM_PAGE = i2;
    }

    public void setTogleView(View view, LinearLayout linearLayout) {
        this.headview = view;
        this.container = linearLayout;
        if (this.container != null) {
            this.container.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    HeaderSearchView.this.hideInputMethod();
                    return false;
                }
            });
        }
    }

    public void setContainer(LinearLayout linearLayout) {
        this.container = linearLayout;
        if (this.container != null) {
            this.container.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    HeaderSearchView.this.hideInputMethod();
                    return false;
                }
            });
        }
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public HeaderSearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animRightIn = scaleAnimation;
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animRightOut = scaleAnimation2;
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animLeftIn = scaleAnimation3;
        ScaleAnimation scaleAnimation4 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animLeftOut = scaleAnimation4;
        this.animTopIn = AnimationUtils.loadAnimation(AMapAppGlobal.getApplication(), R.anim.autonavi_top_in);
        this.citycode = null;
        this.mPosSearchViewEventListener = null;
        this.tipItems = null;
        this.self_call = false;
        this.FROM_PAGE = 0;
        this.mDefaultShowVoiceBtn = true;
        this.searchFor = -1;
        this.mPageContext = null;
        this.isSuggestionEnable = true;
        this.hisTouch = new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                HeaderSearchView.this.hideInputMethod();
                return false;
            }
        };
        this.hisclick = new OnClickListener() {
            public final void onClick(View view) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (HeaderSearchView.this.adapter != null) {
                    TipItem tipItem = null;
                    try {
                        tipItem = HeaderSearchView.this.adapter.a(intValue);
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                    if (tipItem != null) {
                        HeaderSearchView.this.logActionSearchHis(intValue, tipItem);
                        HeaderSearchView.this.onTextClick(tipItem);
                    }
                }
            }
        };
        this.bit1 = "";
        initialize(context);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public HeaderSearchView(Context context) {
        super(context);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animRightIn = scaleAnimation;
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animRightOut = scaleAnimation2;
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animLeftIn = scaleAnimation3;
        ScaleAnimation scaleAnimation4 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animLeftOut = scaleAnimation4;
        this.animTopIn = AnimationUtils.loadAnimation(AMapAppGlobal.getApplication(), R.anim.autonavi_top_in);
        this.citycode = null;
        this.mPosSearchViewEventListener = null;
        this.tipItems = null;
        this.self_call = false;
        this.FROM_PAGE = 0;
        this.mDefaultShowVoiceBtn = true;
        this.searchFor = -1;
        this.mPageContext = null;
        this.isSuggestionEnable = true;
        this.hisTouch = new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                HeaderSearchView.this.hideInputMethod();
                return false;
            }
        };
        this.hisclick = new OnClickListener() {
            public final void onClick(View view) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (HeaderSearchView.this.adapter != null) {
                    TipItem tipItem = null;
                    try {
                        tipItem = HeaderSearchView.this.adapter.a(intValue);
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                    if (tipItem != null) {
                        HeaderSearchView.this.logActionSearchHis(intValue, tipItem);
                        HeaderSearchView.this.onTextClick(tipItem);
                    }
                }
            }
        };
        this.bit1 = "";
        initialize(context);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public HeaderSearchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animRightIn = scaleAnimation;
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animRightOut = scaleAnimation2;
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animLeftIn = scaleAnimation3;
        ScaleAnimation scaleAnimation4 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animLeftOut = scaleAnimation4;
        this.animTopIn = AnimationUtils.loadAnimation(AMapAppGlobal.getApplication(), R.anim.autonavi_top_in);
        this.citycode = null;
        this.mPosSearchViewEventListener = null;
        this.tipItems = null;
        this.self_call = false;
        this.FROM_PAGE = 0;
        this.mDefaultShowVoiceBtn = true;
        this.searchFor = -1;
        this.mPageContext = null;
        this.isSuggestionEnable = true;
        this.hisTouch = new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                HeaderSearchView.this.hideInputMethod();
                return false;
            }
        };
        this.hisclick = new OnClickListener() {
            public final void onClick(View view) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (HeaderSearchView.this.adapter != null) {
                    TipItem tipItem = null;
                    try {
                        tipItem = HeaderSearchView.this.adapter.a(intValue);
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                    if (tipItem != null) {
                        HeaderSearchView.this.logActionSearchHis(intValue, tipItem);
                        HeaderSearchView.this.onTextClick(tipItem);
                    }
                }
            }
        };
        this.bit1 = "";
        initialize(context);
    }

    private void initialize(Context context) {
        this.mContext = context.getApplicationContext();
        this.mViewPresenter = new dop(this, this.mContext);
        initAnim();
        addViews();
    }

    private void initAnim() {
        this.animRightIn.setDuration(200);
        this.animLeftIn.setDuration(200);
        this.animRightOut.setDuration(200);
        this.animLeftOut.setDuration(200);
    }

    public void setSearchButton(Button button) {
        this.btnSearch = button;
    }

    private void addViews() {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        this.delHisView = layoutInflater.inflate(R.layout.v4_del_his_footer, null);
        this.delHisView.setOnClickListener(this);
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.pos_search_view, null);
        this.input_progressbar = relativeLayout.findViewById(R.id.input_progressbar);
        this.mEditSearch = (AutoCompleteEdit) relativeLayout.findViewById(R.id.search_text);
        this.mBtnSearchClear = (ImageView) relativeLayout.findViewById(R.id.search_clear);
        addView(relativeLayout, new LayoutParams(-2, -1));
        this.mEditSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                if (z) {
                    HeaderSearchView.this.showHistory();
                    HeaderSearchView.this.showInputMethod();
                    return;
                }
                HeaderSearchView.this.hideInputMethod();
            }
        });
        this.mEditSearch.setImeOptions(3);
        this.mEditSearch.setOnKeyListener(new OnKeyListener() {
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    if (keyEvent.getAction() == 0 && i == 66) {
                        String access$100 = HeaderSearchView.this.getTextFromEditSearch();
                        String charSequence = HeaderSearchView.this.mEditSearch.getHint().toString();
                        if (access$100.length() <= 0 && !charSequence.equals("搜索") && HeaderSearchView.this.searchFor == 0) {
                            HeaderSearchView.this.searchFor = -1;
                            access$100 = charSequence;
                        }
                        if (TextUtils.isEmpty(access$100)) {
                            ToastHelper.showLongToast(HeaderSearchView.this.mContext.getResources().getString(R.string.act_search_error_empty));
                        } else {
                            if (HeaderSearchView.this.mBtnSearchClear != null) {
                                HeaderSearchView.this.mBtnSearchClear.setVisibility(0);
                            }
                            new TipItem().name = access$100;
                            HeaderSearchView.this.logActionSearchBtn(access$100);
                            HeaderSearchView.this.mPosSearchViewEventListener;
                        }
                        return true;
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                return false;
            }
        });
        this.mEditSearch.setTextWatcherEventListener(this);
        this.mEditSearch.setOnClickListener(this);
        this.mBtnSearchClear.setOnClickListener(this);
    }

    /* access modifiers changed from: private */
    public void logActionSearchBtn(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            try {
                jSONObject.put("Keyword", str);
                jSONObject.put("from_page", this.FROM_PAGE);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            jSONObject = null;
            kf.a((Throwable) e);
            LogManager.actionLog(this.FROM_PAGE, 1, jSONObject);
        }
        LogManager.actionLog(this.FROM_PAGE, 1, jSONObject);
    }

    /* access modifiers changed from: private */
    public void logActionSearchHis(int i, TipItem tipItem) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ItemId", i);
            if (tipItem.type != 0) {
                jSONObject.put("sugpre", getTextFromEditSearch());
            }
            jSONObject.put("ItemName", tipItem.name);
            jSONObject.put("from_page", this.FROM_PAGE);
            if (tipItem.type == 0) {
                jSONObject.put("from", "historyList");
            }
            if (tipItem.type == 1) {
                jSONObject.put("from", "suggest");
            }
            LogManager.actionLog(this.FROM_PAGE, 6, jSONObject);
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public void onClick(View view) {
        if (this.mBtnSearchClear.equals(view)) {
            this.mEditSearch.setText("");
            showHistory();
        } else if (!this.mEditSearch.equals(view) || !this.mEditSearch.isFocused() || this.mEditSearch.getText().length() != 0) {
            if (this.delHisView.equals(view) && this.mPageContext != null) {
                delHisViewOnClick();
            }
        } else {
            showHistory();
        }
    }

    private void delHisViewOnClick() {
        KeyboardUtil.hideInputMethod(this.mPageContext.getActivity());
        AlertView.a aVar = new AlertView.a(this.mPageContext.getActivity());
        aVar.a(R.string.clean_search_history);
        String string = getResources().getString(R.string.del_now);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.f_c_8)), 0, string.length(), 33);
        aVar.a((CharSequence) spannableStringBuilder, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                HeaderSearchView.this.mPageContext.dismissViewLayer(alertView);
                LogManager.actionLog(2, 5);
                SearchHistoryHelper.getInstance(HeaderSearchView.this.mContext).deleteRecordByHistoryType(HeaderSearchView.this.hisType);
                HeaderSearchView.this.showHistory();
            }
        });
        aVar.b(R.string.cancel, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                HeaderSearchView.this.mPageContext.dismissViewLayer(alertView);
            }
        });
        aVar.b = new a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.c = new a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.a(false);
        AlertView a = aVar.a();
        this.mPageContext.showViewLayer(a);
        a.startAnimation();
    }

    public void setPosSearchViewEventListener(doq doq) {
        this.mPosSearchViewEventListener = doq;
    }

    public void setSuggestionEnable(boolean z) {
        this.isSuggestionEnable = z;
    }

    public boolean isSuggestionEnable() {
        return this.isSuggestionEnable;
    }

    public void showHistory() {
        this.mViewPresenter.a(0, this.hisType);
    }

    public void refreshHistoryUi(List<TipItem> list) {
        if (list == null) {
            if (this.container != null) {
                this.container.removeAllViews();
                this.container.setVisibility(8);
            }
            return;
        }
        this.adapter = new SuggestHistoryAdapterCommon(this.mContext, list, this, this.FROM_PAGE);
        this.adapter.b = this;
        if (this.container != null) {
            this.container.removeAllViews();
            this.container.setVisibility(8);
            for (int i = 0; i < this.adapter.a(); i++) {
                TipItem a = this.adapter.a(i);
                if (a == null || !"我的位置".equals(a.name)) {
                    View c = this.adapter.c(i);
                    View findViewById = c.findViewById(R.id.main_content_rl);
                    View findViewById2 = c.findViewById(R.id.img_plus_view);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) findViewById2.getLayoutParams();
                    layoutParams.height = agn.a(getContext(), 40.0f);
                    layoutParams.width = agn.a(getContext(), 29.0f);
                    findViewById2.setLayoutParams(layoutParams);
                    this.container.addView(c);
                    if (findViewById != null) {
                        findViewById.setTag(Integer.valueOf(i));
                        findViewById.setOnTouchListener(this.hisTouch);
                        findViewById.setOnClickListener(this.hisclick);
                    }
                }
            }
            if (this.container.getChildCount() > 0) {
                this.container.setVisibility(0);
                if (this.delHisView != null) {
                    this.delHisView.setVisibility(0);
                    this.container.addView(this.delHisView);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c5, code lost:
        r5 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void refreshInputSuggestUi(java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r17) {
        /*
            r16 = this;
            r6 = r16
            r0 = r17
            java.lang.String r7 = r16.getTextFromEditSearch()
            int r1 = r7.length()
            if (r1 > 0) goto L_0x000f
            return
        L_0x000f:
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r1 = r6.tipItems
            r8 = 1105723392(0x41e80000, float:29.0)
            r9 = 1109393408(0x42200000, float:40.0)
            r10 = 8
            r11 = 1
            r12 = 0
            if (r1 == 0) goto L_0x018c
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r1 = r6.tipItems
            java.lang.String r2 = r16.getTextFromEditSearch()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            if (r0 == 0) goto L_0x006a
            int r4 = r17.size()
            r5 = 0
        L_0x002d:
            if (r5 >= r4) goto L_0x006a
            java.lang.Object r13 = r0.get(r5)
            com.autonavi.bundle.entity.sugg.TipItem r13 = (com.autonavi.bundle.entity.sugg.TipItem) r13
            java.util.List<java.lang.String> r14 = r13.inputs
            int r14 = r14.size()
            if (r14 <= 0) goto L_0x0057
            java.util.List<java.lang.String> r14 = r13.inputs
            java.util.Iterator r14 = r14.iterator()
        L_0x0043:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x0057
            java.lang.Object r15 = r14.next()
            java.lang.String r15 = (java.lang.String) r15
            boolean r15 = r2.equals(r15)
            if (r15 == 0) goto L_0x0043
            r14 = 1
            goto L_0x0058
        L_0x0057:
            r14 = 0
        L_0x0058:
            if (r14 != 0) goto L_0x0062
            java.lang.String r14 = r13.name
            int r14 = r14.indexOf(r2)
            if (r14 != 0) goto L_0x0067
        L_0x0062:
            r13.type = r12
            r3.add(r13)
        L_0x0067:
            int r5 = r5 + 1
            goto L_0x002d
        L_0x006a:
            if (r1 == 0) goto L_0x00ce
            int r0 = r1.size()
            if (r0 <= 0) goto L_0x00ce
            int r0 = r1.size()
            r2 = 0
        L_0x0077:
            if (r2 >= r0) goto L_0x00ce
            java.lang.Object r4 = r1.get(r2)
            com.autonavi.bundle.entity.sugg.TipItem r4 = (com.autonavi.bundle.entity.sugg.TipItem) r4
            r5 = 0
        L_0x0080:
            int r13 = r3.size()
            if (r5 >= r13) goto L_0x00c5
            java.lang.Object r13 = r3.get(r5)
            com.autonavi.bundle.entity.sugg.TipItem r13 = (com.autonavi.bundle.entity.sugg.TipItem) r13
            java.lang.String r14 = r13.name
            java.lang.String r15 = r4.name
            boolean r14 = r14.equals(r15)
            if (r14 == 0) goto L_0x00c2
            java.lang.String r14 = r13.poiid
            java.lang.String r15 = r4.poiid
            boolean r14 = r14.equals(r15)
            if (r14 == 0) goto L_0x00c2
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r14 = r4.tipItemList
            if (r14 == 0) goto L_0x00b0
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r14 = r4.tipItemList
            int r14 = r14.size()
            if (r14 <= 0) goto L_0x00b0
            r3.remove(r5)
            goto L_0x00c5
        L_0x00b0:
            java.lang.String r5 = r4.poiinfo
            r13.poiinfo = r5
            int r5 = r4.poiinfoColor
            r13.poiinfoColor = r5
            java.lang.String r5 = r4.taginfo
            r13.taginfo = r5
            java.lang.String r5 = r4.funcText
            r13.funcText = r5
            r5 = 0
            goto L_0x00c6
        L_0x00c2:
            int r5 = r5 + 1
            goto L_0x0080
        L_0x00c5:
            r5 = 1
        L_0x00c6:
            if (r5 == 0) goto L_0x00cb
            r3.add(r4)
        L_0x00cb:
            int r2 = r2 + 1
            goto L_0x0077
        L_0x00ce:
            int r0 = r3.size()
            if (r0 <= 0) goto L_0x00d6
            r2 = r3
            goto L_0x00d8
        L_0x00d6:
            r0 = 0
            r2 = r0
        L_0x00d8:
            if (r2 != 0) goto L_0x00db
            return
        L_0x00db:
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r13 = new com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon
            android.content.Context r1 = r6.mContext
            int r4 = r6.FROM_PAGE
            r5 = 0
            r0 = r13
            r3 = r6
            r0.<init>(r1, r2, r3, r4, r5)
            r6.adapter = r13
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r0 = r6.adapter
            r0.d = r7
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r0 = r6.adapter
            r0.b = r6
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r0 = r6.adapter
            android.content.Context r1 = r6.mContext
            android.content.res.Resources r1 = r1.getResources()
            int r2 = com.autonavi.minimap.R.color.v3_font_white
            int r1 = r1.getColor(r2)
            r0.a = r1
            android.widget.LinearLayout r0 = r6.container
            if (r0 == 0) goto L_0x018b
            android.widget.LinearLayout r0 = r6.container
            r0.removeAllViews()
            android.widget.LinearLayout r0 = r6.container
            r0.setVisibility(r10)
            r0 = 0
        L_0x0110:
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r1 = r6.adapter
            int r1 = r1.a()
            if (r0 >= r1) goto L_0x0171
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r1 = r6.adapter
            android.view.View r1 = r1.c(r0)
            int r2 = com.autonavi.minimap.R.id.main_content_rl
            android.view.View r2 = r1.findViewById(r2)
            int r3 = com.autonavi.minimap.R.id.img_plus_view
            android.view.View r3 = r1.findViewById(r3)
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            android.content.Context r4 = r16.getContext()
            int r4 = defpackage.agn.a(r4, r9)
            r3.height = r4
            android.content.Context r4 = r16.getContext()
            int r4 = defpackage.agn.a(r4, r8)
            r3.width = r4
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r3 = r6.adapter
            int r3 = r3.a()
            int r3 = r3 - r11
            if (r0 != r3) goto L_0x0156
            int r3 = com.autonavi.minimap.R.id.bottom_driver
            android.view.View r3 = r1.findViewById(r3)
            r3.setVisibility(r10)
        L_0x0156:
            android.widget.LinearLayout r3 = r6.container
            r3.addView(r1)
            if (r2 == 0) goto L_0x016e
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            r2.setTag(r1)
            android.view.View$OnTouchListener r1 = r6.hisTouch
            r2.setOnTouchListener(r1)
            android.view.View$OnClickListener r1 = r6.hisclick
            r2.setOnClickListener(r1)
        L_0x016e:
            int r0 = r0 + 1
            goto L_0x0110
        L_0x0171:
            android.widget.LinearLayout r0 = r6.container
            int r0 = r0.getChildCount()
            if (r0 <= 0) goto L_0x018b
            android.widget.LinearLayout r0 = r6.container
            r0.setVisibility(r12)
            boolean r0 = r6.showAnimTopIn
            if (r0 == 0) goto L_0x018b
            android.widget.LinearLayout r0 = r6.container
            android.view.animation.Animation r1 = r6.animTopIn
            r0.startAnimation(r1)
            r6.showAnimTopIn = r12
        L_0x018b:
            return
        L_0x018c:
            java.lang.String r1 = r16.getTextFromEditSearch()
            java.util.List r2 = com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon.a(r0, r1)
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r13 = new com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon
            android.content.Context r1 = r6.mContext
            int r4 = r6.FROM_PAGE
            r5 = 0
            r0 = r13
            r3 = r6
            r0.<init>(r1, r2, r3, r4, r5)
            r6.adapter = r13
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r0 = r6.adapter
            r0.b = r6
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r0 = r6.adapter
            android.content.Context r1 = r6.mContext
            android.content.res.Resources r1 = r1.getResources()
            int r2 = com.autonavi.minimap.R.color.v3_font_white
            int r1 = r1.getColor(r2)
            r0.a = r1
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r0 = r6.adapter
            r0.d = r7
            android.widget.LinearLayout r0 = r6.container
            if (r0 == 0) goto L_0x023f
            android.widget.LinearLayout r0 = r6.container
            r0.removeAllViews()
            android.widget.LinearLayout r0 = r6.container
            r0.setVisibility(r10)
            r0 = 0
        L_0x01c9:
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r1 = r6.adapter
            int r1 = r1.a()
            if (r0 >= r1) goto L_0x0225
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r1 = r6.adapter
            android.view.View r1 = r1.c(r0)
            com.autonavi.minimap.life.common.widget.view.headersearchview.SuggestHistoryAdapterCommon r2 = r6.adapter
            int r2 = r2.a()
            int r2 = r2 - r11
            if (r0 != r2) goto L_0x01e9
            int r2 = com.autonavi.minimap.R.id.bottom_driver
            android.view.View r2 = r1.findViewById(r2)
            r2.setVisibility(r10)
        L_0x01e9:
            int r2 = com.autonavi.minimap.R.id.main_content_rl
            android.view.View r2 = r1.findViewById(r2)
            int r3 = com.autonavi.minimap.R.id.img_plus_view
            android.view.View r3 = r1.findViewById(r3)
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            android.content.Context r4 = r16.getContext()
            int r4 = defpackage.agn.a(r4, r9)
            r3.height = r4
            android.content.Context r4 = r16.getContext()
            int r4 = defpackage.agn.a(r4, r8)
            r3.width = r4
            android.widget.LinearLayout r3 = r6.container
            r3.addView(r1)
            if (r2 == 0) goto L_0x0222
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            r2.setTag(r1)
            android.view.View$OnClickListener r1 = r6.hisclick
            r2.setOnClickListener(r1)
        L_0x0222:
            int r0 = r0 + 1
            goto L_0x01c9
        L_0x0225:
            android.widget.LinearLayout r0 = r6.container
            int r0 = r0.getChildCount()
            if (r0 <= 0) goto L_0x023f
            android.widget.LinearLayout r0 = r6.container
            r0.setVisibility(r12)
            boolean r0 = r6.showAnimTopIn
            if (r0 == 0) goto L_0x023f
            android.widget.LinearLayout r0 = r6.container
            android.view.animation.Animation r1 = r6.animTopIn
            r0.startAnimation(r1)
            r6.showAnimTopIn = r12
        L_0x023f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.life.common.widget.view.headersearchview.HeaderSearchView.refreshInputSuggestUi(java.util.List):void");
    }

    public void setCitycode(String str) {
        this.citycode = str;
    }

    public void error(Throwable th, boolean z) {
        suggestError();
    }

    public void callbackRunOnUiThread(final List<TipItem> list) {
        if (this.mPageContext != null && this.mPageContext.getActivity() != null) {
            this.mPageContext.getActivity().runOnUiThread(new Runnable() {
                public final void run() {
                    HeaderSearchView.this.callback(list);
                }
            });
        }
    }

    public void callback(List<TipItem> list) {
        suggestCallback(list);
    }

    private void suggestCallback(List<TipItem> list) {
        this.input_progressbar.setVisibility(8);
        String textFromEditSearch = getTextFromEditSearch();
        if (!TextUtils.isEmpty(textFromEditSearch) && textFromEditSearch.length() > 0) {
            this.mBtnSearchClear.setVisibility(0);
        } else if (TextUtils.isEmpty(textFromEditSearch)) {
            this.mBtnSearchClear.setVisibility(4);
        }
        this.tipItems = list;
        this.mViewPresenter.a(1, this.hisType);
    }

    private void suggestError() {
        if (TextUtils.isEmpty(this.citycode) || this.center == null || !this.mViewPresenter.a(null, this.center.getLongitude(), this.center.getLatitude())) {
            this.input_progressbar.setVisibility(8);
            this.mBtnSearchClear.setVisibility(0);
        }
    }

    public void setSuperIdBit1(String str) {
        this.bit1 = str;
    }

    public void onTextClick(TipItem tipItem) {
        if (!this.adapter.c) {
            tipItem.userInput = getTextFromEditSearch();
        }
        this.self_call = true;
        this.mEditSearch.setText(tipItem.name);
        this.mEditSearch.dismissDropDown();
        this.mEditSearch.setSelection(tipItem.name.length());
        this.mEditSearch.clearFocus();
        if (!TextUtils.isEmpty(tipItem.searchQuery)) {
            tipItem.name = tipItem.searchQuery;
        }
        if (this.mPosSearchViewEventListener != null) {
            SuperId.getInstance().setBit1(this.bit1);
            if (tipItem.type == 0) {
                SuperId.getInstance().setBit2("02");
                if (!SearchHistoryHelper.isUserfulPoi(tipItem)) {
                    SuperId.getInstance().setBit3("06");
                } else {
                    SuperId.getInstance().setBit3("07");
                }
            } else {
                SuperId.getInstance().setBit2("01");
                if ((tipItem.tipItemList != null && tipItem.tipItemList.size() > 0) || tipItem.isSugChildClick) {
                    SuperId.getInstance().setBit3("03");
                } else if (!SearchHistoryHelper.isUserfulPoi(tipItem)) {
                    SuperId.getInstance().setBit3("02");
                } else {
                    SuperId.getInstance().setBit3("01");
                }
            }
        }
    }

    public void onSelectionClicked(String str) {
        this.self_call = true;
        this.mEditSearch.setText(str);
        this.mEditSearch.dismissDropDown();
        this.mEditSearch.setSelection(str.length());
        this.mEditSearch.clearFocus();
    }

    private String getCurCity() {
        lj ljVar;
        if (this.center != null) {
            ljVar = li.a().b(this.center.x, this.center.y);
        } else if (this.citycode != null) {
            ljVar = li.a().a(this.citycode);
        } else {
            ljVar = null;
        }
        return ljVar != null ? ljVar.a : "";
    }

    public void onResults(String str) {
        this.self_call = true;
        this.mEditSearch.setText(str);
        this.mEditSearch.setSelection(str.length());
        this.self_call = false;
        setVoiceSearch(true);
        if (this.mPosSearchViewEventListener != null) {
            new TipItem().name = str;
        }
    }

    public boolean isVoiceSearch() {
        return this.isVoiceSearch;
    }

    public void setVoiceSearch(boolean z) {
        this.isVoiceSearch = z;
    }

    /* access modifiers changed from: private */
    public void showInputMethod() {
        ((InputMethodManager) this.mContext.getSystemService("input_method")).showSoftInput(this.mEditSearch, 0);
    }

    public void hideInputMethod() {
        ((InputMethodManager) this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(this.mEditSearch.getApplicationWindowToken(), 0);
    }

    public void afterTextChanged(View view, Editable editable) {
        afterTextChanged(editable);
    }

    private void afterTextChanged(Editable editable) {
        this.tipItems = null;
        if (editable.length() > 0) {
            this.mBtnSearchClear.setVisibility(0);
            if (this.headview != null) {
                this.headview.setVisibility(8);
            }
            if (this.btnSearch != null) {
                this.btnSearch.setEnabled(true);
            }
        } else {
            this.mBtnSearchClear.setVisibility(4);
            this.showAnimTopIn = false;
            if (this.headview != null) {
                this.headview.setVisibility(0);
            }
            if (this.container != null) {
                if (this.container.getChildCount() > 0) {
                    this.container.setVisibility(0);
                    if (this.delHisView != null) {
                        this.delHisView.setVisibility(0);
                    }
                } else {
                    this.container.setVisibility(8);
                }
            }
            if (this.btnSearch != null && this.mEditSearch.getHint().toString().equals(this.mContext.getResources().getString(R.string.act_search_arround_bar))) {
                this.btnSearch.setEnabled(false);
            }
        }
        String trim = editable.toString().trim();
        if ("".equals(trim) || "我的位置".equals(trim)) {
            if (this.isSuggestionEnable) {
                this.input_progressbar.setVisibility(8);
                this.mViewPresenter.a();
                if (this.delHisView != null) {
                    this.delHisView.setVisibility(8);
                }
                if (this.self_call) {
                    this.self_call = false;
                } else if (this.mEditSearch.isFocused()) {
                    showHistory();
                }
            }
        } else if (this.isSuggestionEnable) {
            this.input_progressbar.setVisibility(8);
            this.mViewPresenter.a();
            if (this.self_call) {
                this.self_call = false;
            } else if (this.mEditSearch.isFocused()) {
                this.mViewPresenter.a(1, this.hisType);
                if (this.mPageContext != null) {
                    this.input_progressbar.setVisibility(0);
                    this.mBtnSearchClear.setVisibility(4);
                    String textFromEditSearch = getTextFromEditSearch();
                    String str = this.citycode;
                    String userLocInfo = AppManager.getInstance().getUserLocInfo();
                    StringBuilder sb = new StringBuilder();
                    sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
                    AosInputSuggestionParam aosInputSuggestionParam = new AosInputSuggestionParam(textFromEditSearch, str, userLocInfo, sb.toString(), null, "poi", this.FROM_PAGE == 10049, DoNotUseTool.getMapManager().getMapView().H(), this.center.x, this.center.y);
                    if (!this.m_bIsForceOffline || (!TextUtils.isEmpty(this.citycode) && this.center != null)) {
                        dop dop = this.mViewPresenter;
                        boolean z = this.m_bIsForceOffline;
                        dop.e = this.mPageContext;
                        dop.a();
                        if (!TextUtils.isEmpty(aosInputSuggestionParam.words)) {
                            dop.d.removeMessages(4098);
                            dop.d.removeMessages(4097);
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("suggest_param", aosInputSuggestionParam);
                            message.setData(bundle);
                            if (z) {
                                message.what = 4098;
                                dop.d.sendMessageDelayed(message, 250);
                                return;
                            }
                            message.what = 4097;
                            dop.d.sendMessageDelayed(message, 250);
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public String getTextFromEditSearch() {
        String obj = this.mEditSearch.getText().toString();
        if (!TextUtils.isEmpty(obj)) {
            obj = obj.trim();
        }
        return obj == null ? "" : obj;
    }

    public void onConfigurationChanged() {
        if (!(this.adapter == null || this.tipItems == null || this.container == null || this.container.getVisibility() != 0)) {
            for (int i = 0; i < this.adapter.a(); i++) {
                if (this.adapter.b(i) == 3) {
                    this.mViewPresenter.a(1, this.hisType);
                    return;
                }
            }
        }
    }

    public AutoCompleteEdit getSearchEdit() {
        return this.mEditSearch;
    }

    public ScaleAnimation getAnimRightIn() {
        return this.animRightIn;
    }

    public ScaleAnimation getAnimRightOut() {
        return this.animRightOut;
    }

    public ScaleAnimation getAnimLeftIn() {
        return this.animLeftIn;
    }

    public ScaleAnimation getAnimLeftOut() {
        return this.animLeftOut;
    }
}
