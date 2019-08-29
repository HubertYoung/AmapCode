package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage.a;
import com.autonavi.minimap.widget.ClearableEditText;
import com.autonavi.minimap.widget.ClearableEditText.ClearListener;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class SavePointEditExtraPage extends AbstractBasePage<crh> implements launchModeSingleTask, Transparent {
    /* access modifiers changed from: private */
    public ClearableEditText a;
    private Button b;
    private Button c;
    /* access modifiers changed from: private */
    public bth d;
    private ViewGroup e;
    /* access modifiers changed from: private */
    public a f;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_set_extra);
        View contentView = getContentView();
        contentView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
            }
        });
        contentView.setBackgroundColor(getResources().getColor(R.color.dialog_bg));
        this.a = (ClearableEditText) contentView.findViewById(R.id.clear_edit_text);
        this.c = (Button) contentView.findViewById(R.id.save);
        this.b = (Button) contentView.findViewById(R.id.cancel);
        this.e = (ViewGroup) contentView.findViewById(R.id.container);
        this.a.setClearListener(new ClearListener() {
            public final void clickClearBtn() {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", 3);
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B012", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.b);
        arrayList.add(this.c);
        for (int i = 0; i < arrayList.size(); i++) {
            int size = arrayList.size();
            ShapeDrawable a2 = a(i, getResources().getColor(R.color.c_1), size);
            ShapeDrawable a3 = a(i, getResources().getColor(R.color.c_3), size);
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{16842919}, a3);
            stateListDrawable.addState(new int[0], a2);
            ((View) arrayList.get(i)).setBackgroundDrawable(stateListDrawable);
        }
        a(getArguments());
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null && pageBundle.containsKey("savepointkey")) {
            this.d = (bth) pageBundle.getObject("savepointkey");
        }
        if (pageBundle != null && pageBundle.containsKey("save_fragment_key")) {
            this.f = (a) pageBundle.get("save_fragment_key");
        }
        final String a2 = cru.a(this.d);
        this.a.setText(a2);
        this.b.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SavePointEditExtraPage.this.finish();
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", 2);
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B012", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        this.c.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                String obj = SavePointEditExtraPage.this.a.getText().toString();
                if (TextUtils.isEmpty(obj)) {
                    cru.a((String) "", SavePointEditExtraPage.this.d);
                    if (SavePointEditExtraPage.this.f != null) {
                        SavePointEditExtraPage.this.f.editSavePoint(SavePointEditExtraPage.this.d);
                    }
                    SavePointEditExtraPage.this.finish();
                } else if (obj.equals(a2)) {
                    SavePointEditExtraPage.this.finish();
                } else {
                    cru.a(obj, SavePointEditExtraPage.this.d);
                    if (SavePointEditExtraPage.this.f != null) {
                        SavePointEditExtraPage.this.f.editSavePoint(SavePointEditExtraPage.this.d);
                    }
                    SavePointEditExtraPage.this.finish();
                }
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", 1);
                    LogManager.actionLogV2(LogConstant.PAGE_SAVE_MAIN_POINT, "B012", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        a();
    }

    private void a() {
        Editable text = this.a.getText();
        if (text != null) {
            Selection.setSelection(text, text.length());
        }
        new Handler().post(new Runnable() {
            public final void run() {
                SavePointEditExtraPage.this.a.requestFocus();
                int i = 1;
                boolean z = SavePointEditExtraPage.this.getResources().getConfiguration().orientation == 2;
                InputMethodManager inputMethodManager = (InputMethodManager) SavePointEditExtraPage.this.getActivity().getSystemService("input_method");
                ClearableEditText a2 = SavePointEditExtraPage.this.a;
                if (z) {
                    i = 2;
                }
                inputMethodManager.showSoftInput(a2, i);
            }
        });
    }

    private ShapeDrawable a(int i, int i2, int i3) {
        float[] fArr;
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.title_tab_radius);
        if (i3 == 1) {
            float f2 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f2, f2, f2, f2};
        } else if (i == 0) {
            float f3 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f3, f3};
        } else if (i == i3 - 1) {
            float f4 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f4, f4, 0.0f, 0.0f};
        } else {
            fArr = null;
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        shapeDrawable.getPaint().setColor(i2);
        return shapeDrawable;
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new crh(this);
    }
}
