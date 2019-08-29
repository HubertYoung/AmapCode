package com.autonavi.minimap.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.IBinder;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.minimap.search.utils.SearchUtils.a;
import com.autonavi.minimap.widget.IVoiceDlgManager.ISearchEdit;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchEdit extends RelativeLayout implements OnClickListener, ISearchEdit {
    /* access modifiers changed from: private */
    public int FROM_PAGE = 0;
    public final int MODE_CITY = 1;
    private boolean SEARCH_WITH_HINT = false;
    public final ScaleAnimation animLeftIn;
    public final ScaleAnimation animLeftOut;
    public final ScaleAnimation animRightIn;
    public final ScaleAnimation animRightOut;
    /* access modifiers changed from: private */
    public String bit1 = "";
    private boolean isVoiceSearch;
    private long mAdcode;
    private GeoPoint mCenter;
    /* access modifiers changed from: private */
    public View mClearButton;
    private Context mContext;
    /* access modifiers changed from: private */
    public String mKeyword;
    /* access modifiers changed from: private */
    public String mOldKeyWord = "";
    public int mPosition = -1;
    /* access modifiers changed from: private */
    public ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public LinearLayout mRightContainer;
    /* access modifiers changed from: private */
    public EditText mSearchEdit;
    /* access modifiers changed from: private */
    public ISearchEditEventListener mSearchEditEventListener;
    public boolean mSelfCall = false;
    public OnItemEventListener onItemEventListener = new OnItemEventListener() {
        public void onItemClicked(TipItem tipItem, int i, boolean z) {
            if (tipItem.type == 1) {
                tipItem.userInput = SearchEdit.this.getTextFromEditSearch();
            }
            SearchEdit.this.mSelfCall = true;
            SearchEdit.this.setText(tipItem.name);
            SearchEdit.this.mSearchEdit.clearFocus();
            if (!TextUtils.isEmpty(tipItem.searchQuery)) {
                tipItem.name = tipItem.searchQuery;
            }
            if (SearchEdit.this.mSearchEditEventListener != null) {
                SuperId.getInstance().setBit1(SearchEdit.this.bit1);
                if (tipItem.type == 0) {
                    SearchEdit.this.mPosition = i;
                    SuperId.getInstance().setBit2("02");
                    if (!SearchHistoryHelper.isUserfulPoi(tipItem)) {
                        SuperId.getInstance().setBit3("06");
                    } else {
                        SuperId.getInstance().setBit3("07");
                    }
                } else {
                    SuperId.getInstance().setBit2("01");
                    if (TextUtils.isEmpty(tipItem.poiid) && z) {
                        SuperId.getInstance().setBit3("15");
                    } else if ((tipItem.tipItemList == null || tipItem.tipItemList.size() <= 0) && !tipItem.isSugChildClick) {
                        if (SearchHistoryHelper.isUserfulPoi(tipItem)) {
                            SuperId.getInstance().setBit3("01");
                        }
                    } else if (TextUtils.isEmpty(tipItem.poiid)) {
                        SuperId.getInstance().setBit3("02");
                    } else {
                        SuperId.getInstance().setBit3("03");
                    }
                }
                SearchEdit.this.mSearchEditEventListener.onItemClicked(tipItem);
            }
            if (tipItem.type != 3) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("ItemId", i);
                    if (tipItem.type != 0) {
                        jSONObject.put("sugpre", SearchEdit.this.getTextFromEditSearch());
                    }
                    jSONObject.put("ItemName", tipItem.name);
                    jSONObject.put("from_page", SearchEdit.this.FROM_PAGE);
                    if (z) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put(TrafficUtil.KEYWORD, tipItem.shortname);
                        if (SearchEdit.this.FROM_PAGE == 10049) {
                            if (TextUtils.isEmpty(tipItem.poiid)) {
                                LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B017", jSONObject2);
                            } else {
                                LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B006");
                            }
                        } else if (SearchEdit.this.FROM_PAGE == 11102) {
                            if (TextUtils.isEmpty(tipItem.poiid)) {
                                LogManager.actionLogV2("P00004", "B036", jSONObject2);
                            }
                        } else if (SearchEdit.this.FROM_PAGE == 10300 && TextUtils.isEmpty(tipItem.poiid)) {
                            LogManager.actionLogV2("P00003", "B012", jSONObject2);
                        }
                    } else if (SearchEdit.this.FROM_PAGE == 10049) {
                        LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B004");
                    } else if (SearchEdit.this.FROM_PAGE == 11102) {
                        if (tipItem.type == 0) {
                            jSONObject.put("from", "historyList");
                            LogManager.actionLogV2("P00004", "B011", jSONObject);
                        }
                        if (tipItem.type == 1) {
                            jSONObject.put("from", "suggest");
                            LogManager.actionLogV2("P00004", "B010", jSONObject);
                        }
                    } else {
                        LogManager.actionLogV2("P00003", "B004");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onItemLongClicked(TipItem tipItem) {
            SearchEdit.this.hideInputMethod();
            SearchEdit.this.mSearchEditEventListener.onItemLongClicked(tipItem);
        }

        public void onRouteClicked(TipItem tipItem) {
            SearchEdit.this.mSearchEditEventListener.onRoute(tipItem);
        }

        public void onAddClicked(TipItem tipItem, int i) {
            if (TextUtils.isEmpty(SearchEdit.this.mKeyword) && tipItem.type == 0) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("ItemId", i);
                    jSONObject.put("from_page", SearchEdit.this.FROM_PAGE);
                    jSONObject.put(TrafficUtil.KEYWORD, tipItem.name);
                    jSONObject.put("from", "historyList");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (SearchEdit.this.FROM_PAGE != 11102) {
                    if (10049 == SearchEdit.this.FROM_PAGE) {
                        LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B009");
                    } else {
                        LogManager.actionLogV2("P00003", "B007", jSONObject);
                    }
                }
            } else if (tipItem.type == 0) {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("ItemId", i);
                    jSONObject2.put("from_page", SearchEdit.this.FROM_PAGE);
                    jSONObject2.put(TrafficUtil.KEYWORD, tipItem.name);
                    jSONObject2.put("from", "suggest");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (SearchEdit.this.FROM_PAGE != 11102) {
                    if (10049 == SearchEdit.this.FROM_PAGE) {
                        LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B009");
                    } else {
                        LogManager.actionLogV2("P00003", "B007", jSONObject2);
                    }
                }
            } else if (tipItem.type == 1) {
                JSONObject jSONObject3 = new JSONObject();
                try {
                    jSONObject3.put("ItemId", i);
                    jSONObject3.put("from_page", SearchEdit.this.FROM_PAGE);
                    jSONObject3.put(TrafficUtil.KEYWORD, tipItem.name);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                if (SearchEdit.this.FROM_PAGE != 11102) {
                    if (10049 == SearchEdit.this.FROM_PAGE) {
                        LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B008");
                    } else {
                        LogManager.actionLogV2("P00003", "B008", jSONObject3);
                    }
                }
            }
            SearchEdit.this.requestEditFocus();
            SearchEdit.this.clearText();
            SearchEdit.this.setText(tipItem.name);
            String str = TextUtils.isEmpty(tipItem.poiid) ? "Tquery" : "IDQ";
            if (SearchEdit.this.FROM_PAGE == 11102) {
                JSONObject jSONObject4 = new JSONObject();
                try {
                    jSONObject4.put("from", str);
                    jSONObject4.put("itemId", i + 1);
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                LogManager.actionLogV2("P00004", "B014", jSONObject4);
            }
        }
    };
    private TextWatcher textWatch = new TextWatcher() {
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            SearchEdit.this.mOldKeyWord = charSequence.toString();
        }

        public void afterTextChanged(Editable editable) {
            if (SearchEdit.this.mSearchEditEventListener != null) {
                SearchEdit.this.mSearchEditEventListener.afterTextChanged(editable);
                if (editable.length() <= 0) {
                    SearchEdit.this.mClearButton.setVisibility(8);
                    SearchEdit.this.mRightContainer.setVisibility(8);
                    SearchEdit.this.showHistory();
                } else {
                    SearchEdit.this.mRightContainer.setVisibility(0);
                    SearchEdit.this.mProgressBar.setVisibility(8);
                    SearchEdit.this.mClearButton.setVisibility(0);
                }
                if (SearchEdit.this.mSelfCall || SearchEdit.this.isVoiceSearch()) {
                    SearchEdit.this.setVoiceSearch(false);
                    SearchEdit.this.mSelfCall = false;
                } else if ("".equals(editable.toString().trim())) {
                    SearchEdit.this.mProgressBar.setVisibility(8);
                    if (SearchEdit.this.mSearchEdit.getText().length() > 0) {
                        SearchEdit.this.mClearButton.setVisibility(0);
                    } else {
                        SearchEdit.this.mClearButton.setVisibility(8);
                    }
                } else {
                    SearchEdit.this.mProgressBar.setVisibility(8);
                    if (SearchEdit.this.mSearchEdit.getText().length() > 0) {
                        SearchEdit.this.mClearButton.setVisibility(0);
                    } else {
                        SearchEdit.this.mClearButton.setVisibility(8);
                    }
                    if (SearchEdit.this.mSearchEdit.isFocused()) {
                        SearchEdit.this.mKeyword = editable.toString().trim();
                        SearchEdit.this.showInputSuggest();
                        SearchEdit.this.mProgressBar.setVisibility(8);
                        if (SearchEdit.this.mSearchEdit.getText().length() > 0) {
                            SearchEdit.this.mClearButton.setVisibility(0);
                            return;
                        }
                        SearchEdit.this.mClearButton.setVisibility(8);
                    }
                }
            }
        }
    };

    public interface ISearchEditEventListener {
        boolean afterTextChanged(Editable editable);

        void onClearEdit();

        void onHideHistory();

        void onHideSugg();

        void onItemClicked(TipItem tipItem);

        void onItemLongClicked(TipItem tipItem);

        void onRoute(TipItem tipItem);

        void onShowHistory(int i);

        void onShowSugg(int i);
    }

    public interface OnItemEventListener {
        void onAddClicked(TipItem tipItem, int i);

        void onItemClicked(TipItem tipItem, int i, boolean z);

        void onItemLongClicked(TipItem tipItem);

        void onRouteClicked(TipItem tipItem);
    }

    public void dissmissIatDialog() {
    }

    public boolean isIatDialogShowing() {
        return false;
    }

    public void showIatDialog() {
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public SearchEdit(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animRightIn = scaleAnimation;
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animRightOut = scaleAnimation2;
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animLeftIn = scaleAnimation3;
        ScaleAnimation scaleAnimation4 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animLeftOut = scaleAnimation4;
        initialize(context);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public SearchEdit(Context context) {
        super(context);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animRightIn = scaleAnimation;
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animRightOut = scaleAnimation2;
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animLeftIn = scaleAnimation3;
        ScaleAnimation scaleAnimation4 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animLeftOut = scaleAnimation4;
        initialize(context);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public SearchEdit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animRightIn = scaleAnimation;
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animRightOut = scaleAnimation2;
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, 1, 0.0f, 1, 0.5f);
        this.animLeftIn = scaleAnimation3;
        ScaleAnimation scaleAnimation4 = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        this.animLeftOut = scaleAnimation4;
        initialize(context);
    }

    public void setFromPage(int i) {
        this.FROM_PAGE = i;
    }

    public void setSearchWithHint(boolean z) {
        this.SEARCH_WITH_HINT = z;
    }

    public void setSelfCall(boolean z) {
        this.mSelfCall = z;
    }

    public void setCenterPoint(GeoPoint geoPoint) {
        this.mCenter = geoPoint;
    }

    private void initialize(Context context) {
        this.mContext = context;
        this.animRightIn.setDuration(200);
        this.animLeftIn.setDuration(200);
        this.animRightOut.setDuration(200);
        this.animLeftOut.setDuration(200);
        addViews();
    }

    public String getOldKeyWord() {
        return this.mOldKeyWord;
    }

    public String getText() {
        return this.mSearchEdit.getText().toString();
    }

    public void clearText() {
        if (this.mSearchEdit != null) {
            this.mSearchEdit.setText("");
        }
    }

    public void setText(String str) {
        try {
            this.mSearchEdit.setText(str);
            Editable text = this.mSearchEdit.getText();
            Selection.setSelection(text, text.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHint() {
        return this.mSearchEdit.getHint().toString();
    }

    public void setHint(String str) {
        this.mSearchEdit.setHint(str);
    }

    public void setHintColor(String str) {
        try {
            this.mSearchEdit.setHintTextColor(Color.parseColor(str));
        } catch (Exception unused) {
            this.mSearchEdit.setHintTextColor(getResources().getColor(R.color.default_font_color_cad));
        }
    }

    public void setHintColor(int i) {
        this.mSearchEdit.setHintTextColor(i);
    }

    public EditText getEditText() {
        return this.mSearchEdit;
    }

    public void requestEditFocus() {
        if (!this.mSearchEdit.hasFocus()) {
            this.mSearchEdit.requestFocus();
        }
    }

    private void addViews() {
        RelativeLayout relativeLayout = (RelativeLayout) ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.search_edit_layout, null);
        this.mRightContainer = (LinearLayout) relativeLayout.findViewById(R.id.right_container);
        this.mProgressBar = (ProgressBar) relativeLayout.findViewById(R.id.input_progressbar);
        this.mSearchEdit = (EditText) relativeLayout.findViewById(R.id.search_text);
        this.mClearButton = relativeLayout.findViewById(R.id.search_clear);
        addView(relativeLayout, new LayoutParams(-2, -1));
        this.mSearchEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    SearchEdit.this.showInputMethod();
                } else {
                    SearchEdit.this.hideInputMethod();
                }
            }
        });
        this.mSearchEdit.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 3) {
                    return SearchEdit.this.startSearchFromInputMethod();
                }
                return false;
            }
        });
        this.mSearchEdit.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 0 && i == 66) {
                    return SearchEdit.this.startSearchFromInputMethod();
                }
                return false;
            }
        });
        this.mSearchEdit.setOnClickListener(this);
        this.mSearchEdit.addTextChangedListener(this.textWatch);
        this.mClearButton.setOnClickListener(this);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x008c */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b4 A[Catch:{ Exception -> 0x00e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00da A[Catch:{ Exception -> 0x00e0 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean startSearchFromInputMethod() {
        /*
            r6 = this;
            java.lang.String r0 = r6.getTextFromEditSearch()     // Catch:{ Exception -> 0x00e0 }
            android.widget.EditText r1 = r6.mSearchEdit     // Catch:{ Exception -> 0x00e0 }
            java.lang.CharSequence r1 = r1.getHint()     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00e0 }
            int r2 = r6.FROM_PAGE     // Catch:{ Exception -> 0x00e0 }
            r3 = 11102(0x2b5e, float:1.5557E-41)
            if (r2 != r3) goto L_0x0047
            boolean r2 = defpackage.elc.b     // Catch:{ Exception -> 0x00e0 }
            if (r2 != 0) goto L_0x0047
            boolean r2 = defpackage.elc.a     // Catch:{ Exception -> 0x00e0 }
            if (r2 != 0) goto L_0x0047
            android.widget.EditText r2 = r6.mSearchEdit     // Catch:{ Exception -> 0x00e0 }
            android.text.Editable r2 = r2.getText()     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00e0 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00e0 }
            if (r2 == 0) goto L_0x0058
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x00e0 }
            if (r2 != 0) goto L_0x0058
            android.content.res.Resources r2 = r6.getResources()     // Catch:{ Exception -> 0x00e0 }
            int r3 = com.autonavi.minimap.R.string.act_search_arround_bar     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r2 = r2.getString(r3)     // Catch:{ Exception -> 0x00e0 }
            boolean r2 = r1.equals(r2)     // Catch:{ Exception -> 0x00e0 }
            if (r2 != 0) goto L_0x0058
            java.lang.String r0 = r1.trim()     // Catch:{ Exception -> 0x00e0 }
            goto L_0x0058
        L_0x0047:
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00e0 }
            if (r2 == 0) goto L_0x0058
            int r2 = r6.FROM_PAGE     // Catch:{ Exception -> 0x00e0 }
            r3 = 10300(0x283c, float:1.4433E-41)
            if (r2 != r3) goto L_0x0058
            boolean r2 = r6.SEARCH_WITH_HINT     // Catch:{ Exception -> 0x00e0 }
            if (r2 == 0) goto L_0x0058
            r0 = r1
        L_0x0058:
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00e0 }
            r2 = 1
            if (r1 == 0) goto L_0x006f
            android.content.Context r0 = r6.mContext     // Catch:{ Exception -> 0x00e0 }
            android.content.res.Resources r0 = r0.getResources()     // Catch:{ Exception -> 0x00e0 }
            int r1 = com.autonavi.minimap.R.string.act_search_error_empty     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r0 = r0.getString(r1)     // Catch:{ Exception -> 0x00e0 }
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r0)     // Catch:{ Exception -> 0x00e0 }
            goto L_0x00df
        L_0x006f:
            com.autonavi.bundle.entity.sugg.TipItem r1 = new com.autonavi.bundle.entity.sugg.TipItem     // Catch:{ Exception -> 0x00e0 }
            r1.<init>()     // Catch:{ Exception -> 0x00e0 }
            r1.name = r0     // Catch:{ Exception -> 0x00e0 }
            r1.isFromRealSceneSearch = r2     // Catch:{ Exception -> 0x00e0 }
            r3 = 0
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x008b }
            r4.<init>()     // Catch:{ Exception -> 0x008b }
            java.lang.String r3 = "Keyword"
            r4.put(r3, r0)     // Catch:{ Exception -> 0x008c }
            java.lang.String r0 = "from_page"
            int r3 = r6.FROM_PAGE     // Catch:{ Exception -> 0x008c }
            r4.put(r0, r3)     // Catch:{ Exception -> 0x008c }
            goto L_0x008c
        L_0x008b:
            r4 = r3
        L_0x008c:
            com.autonavi.common.SuperId r0 = com.autonavi.common.SuperId.getInstance()     // Catch:{ Exception -> 0x00e0 }
            r0.reset()     // Catch:{ Exception -> 0x00e0 }
            com.autonavi.common.SuperId r0 = com.autonavi.common.SuperId.getInstance()     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = r6.bit1     // Catch:{ Exception -> 0x00e0 }
            r0.setBit1(r3)     // Catch:{ Exception -> 0x00e0 }
            com.autonavi.common.SuperId r0 = com.autonavi.common.SuperId.getInstance()     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = "03"
            r0.setBit2(r3)     // Catch:{ Exception -> 0x00e0 }
            int r0 = r6.FROM_PAGE     // Catch:{ Exception -> 0x00e0 }
            com.amap.bundle.statistics.LogManager.actionLog(r0, r2, r4)     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r0 = r6.getTextFromEditSearch()     // Catch:{ Exception -> 0x00e0 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00e0 }
            if (r0 == 0) goto L_0x00bb
            java.lang.String r0 = "P00004"
            java.lang.String r3 = "B037"
            com.amap.bundle.statistics.LogManager.actionLogV2(r0, r3)     // Catch:{ Exception -> 0x00e0 }
        L_0x00bb:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x00e0 }
            r0.<init>()     // Catch:{ Exception -> 0x00e0 }
            java.lang.String r3 = "from"
            android.content.res.Resources r4 = r6.getResources()     // Catch:{ Exception -> 0x00cf }
            int r5 = com.autonavi.minimap.R.string.search_keyboard     // Catch:{ Exception -> 0x00cf }
            java.lang.String r4 = r4.getString(r5)     // Catch:{ Exception -> 0x00cf }
            r0.put(r3, r4)     // Catch:{ Exception -> 0x00cf }
        L_0x00cf:
            java.lang.String r3 = "P00004"
            java.lang.String r4 = "B004"
            com.amap.bundle.statistics.LogManager.actionLogV2(r3, r4, r0)     // Catch:{ Exception -> 0x00e0 }
            com.autonavi.minimap.widget.SearchEdit$ISearchEditEventListener r0 = r6.mSearchEditEventListener     // Catch:{ Exception -> 0x00e0 }
            if (r0 == 0) goto L_0x00df
            com.autonavi.minimap.widget.SearchEdit$ISearchEditEventListener r0 = r6.mSearchEditEventListener     // Catch:{ Exception -> 0x00e0 }
            r0.onItemClicked(r1)     // Catch:{ Exception -> 0x00e0 }
        L_0x00df:
            return r2
        L_0x00e0:
            r0 = move-exception
            defpackage.kf.a(r0)
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.SearchEdit.startSearchFromInputMethod():boolean");
    }

    public void clearSuggestionData() {
        this.mSearchEdit.setText("");
    }

    public void onClick(View view) {
        if (this.mClearButton.equals(view)) {
            onClearEditEvent();
            return;
        }
        if (this.mSearchEdit.equals(view) && this.FROM_PAGE == 11102) {
            LogManager.actionLogV2("P00004", "B001");
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        clearFocus();
        super.onDetachedFromWindow();
    }

    public void onClearEditEvent() {
        this.mSelfCall = true;
        this.mSearchEdit.setText("");
        this.mKeyword = "";
        if (this.mSearchEditEventListener != null) {
            this.mSearchEditEventListener.onClearEdit();
        }
    }

    public void setSearchEditEventListener(ISearchEditEventListener iSearchEditEventListener) {
        this.mSearchEditEventListener = iSearchEditEventListener;
    }

    public void onDestory() {
        if (this.mClearButton != null) {
            this.mClearButton.setOnClickListener(null);
        }
        if (this.mSearchEdit != null) {
            this.mSearchEdit.setOnFocusChangeListener(null);
            this.mSearchEdit.setOnClickListener(null);
            this.mSearchEdit.removeTextChangedListener(this.textWatch);
            this.mSearchEdit.setOnFocusChangeListener(null);
            this.mSearchEdit.setOnEditorActionListener(null);
            if (this.textWatch != null) {
                this.textWatch = null;
            }
        }
        if (this.mSearchEditEventListener != null) {
            this.mSearchEditEventListener.onClearEdit();
            this.mSearchEditEventListener = null;
        }
        if (this.onItemEventListener != null) {
            this.onItemEventListener = null;
        }
        fixInputEventReceiver();
    }

    public void showHistory() {
        this.mSearchEditEventListener.onShowHistory(0);
        this.mSearchEditEventListener.onHideSugg();
    }

    public void showInputSuggest() {
        this.mSearchEditEventListener.onShowSugg(0);
        this.mSearchEditEventListener.onHideHistory();
    }

    public void setProgressBarVisibility(boolean z) {
        if (z) {
            this.mProgressBar.setVisibility(0);
        } else {
            this.mProgressBar.setVisibility(8);
        }
    }

    public void setClearButtonVisibility(boolean z) {
        if (z) {
            this.mClearButton.setVisibility(0);
        } else {
            this.mClearButton.setVisibility(8);
        }
    }

    public void setAdcode(long j) {
        this.mAdcode = j;
    }

    public void setSuperIdBit1(String str) {
        this.bit1 = str;
    }

    private String getCurCity() {
        lj ljVar;
        if (this.mCenter != null) {
            ljVar = li.a().b(this.mCenter.x, this.mCenter.y);
        } else if (this.mAdcode != 0) {
            ljVar = li.a().a((int) this.mAdcode);
        } else {
            ljVar = null;
        }
        return ljVar != null ? ljVar.a : "";
    }

    public void onResults(String str) {
        setVoiceSearch(true);
        this.mSearchEdit.setText(str);
        try {
            this.mSearchEdit.setSelection(str.length());
        } catch (IndexOutOfBoundsException unused) {
        }
        this.mSelfCall = false;
        if (this.mSearchEditEventListener != null) {
            TipItem tipItem = new TipItem();
            tipItem.name = str;
            tipItem.isFromRealSceneSearch = true;
            this.mSearchEditEventListener.onItemClicked(tipItem);
        }
    }

    public boolean isVoiceSearch() {
        return this.isVoiceSearch;
    }

    public void setVoiceSearch(boolean z) {
        this.isVoiceSearch = z;
    }

    public void showInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        try {
            if (inputMethodManager.isActive()) {
                inputMethodManager.showSoftInput(this.mSearchEdit, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (inputMethodManager != null) {
            try {
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(this.mSearchEdit.getWindowToken(), 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getTextFromEditSearch() {
        String obj = this.mSearchEdit.getText().toString();
        return !TextUtils.isEmpty(obj) ? obj.trim() : obj;
    }

    public void addSugLog(int i, String str) {
        String str2;
        if (i >= 0 && this.mRightContainer.getVisibility() == 0) {
            int i2 = this.FROM_PAGE;
            if (i2 != 11102) {
                switch (i2) {
                    case LogConstant.SEARCH_FROM_ARROUND /*10049*/:
                        str2 = getResources().getString(R.string.search_arround_log);
                        break;
                    case LogConstant.NAVI_ERROR_REPORT /*10050*/:
                        str2 = getResources().getString(R.string.search_feed);
                        break;
                    default:
                        str2 = getResources().getString(R.string.search_others);
                        break;
                }
            } else {
                str2 = getResources().getString(R.string.search_pagehome);
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
                jSONObject.put("itemId", i + 1);
                jSONObject.put("from", str2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00004", LogConstant.MORE_MINE_COMMENT, jSONObject);
        }
    }

    public void addSearchHomeLog(int i, TipItem tipItem) {
        String str;
        if (i >= 0) {
            int i2 = tipItem.iconinfo;
            if (i2 != 4) {
                switch (i2) {
                    case 0:
                        if (!TextUtils.isEmpty(tipItem.poiid)) {
                            str = "IDQ";
                            break;
                        } else {
                            str = "tquery";
                            break;
                        }
                    case 1:
                        str = "busline";
                        break;
                    default:
                        str = "others";
                        break;
                }
            } else {
                str = "tquery";
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
                jSONObject.put("itemId", i + 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00004", LogConstant.MAIN_MAP_INVIEW_SEARCH, jSONObject);
        }
    }

    private void fixInputEventReceiver() {
        View rootView = this.mSearchEdit.getRootView();
        SearchUtils.invokeMethodExceptionSafe(rootView.getParent(), "clearChildFocus", new a(rootView, View.class));
    }

    private void fixInputMethodManager() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService("input_method");
        SearchUtils.invokeMethodExceptionSafe(inputMethodManager, "windowDismissed", new a(this.mSearchEdit.getWindowToken(), IBinder.class));
        SearchUtils.invokeMethodExceptionSafe(inputMethodManager, "startGettingWindowFocus", new a(null, View.class));
    }
}
