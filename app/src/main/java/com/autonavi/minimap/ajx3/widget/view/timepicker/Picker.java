package com.autonavi.minimap.ajx3.widget.view.timepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.PickerProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerWidgetView.TouchListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"ViewConstructor"})
public class Picker extends FrameLayout implements ViewExtension, TouchListener {
    private static final boolean DEBUG = false;
    private static final int MSG_SAVE_DATA = 1003;
    private static final int MSG_UPDATE_CHANGE = 1004;
    private static final String TAG = "Picker";
    private boolean isLayout = false;
    private IAjxContext mAjxContext;
    private String mData;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1003:
                    removeMessages(1003);
                    Picker.this.saveData();
                    return;
                case 1004:
                    removeMessages(1004);
                    if (Picker.this.needNotifyChange) {
                        Picker.this.updateChangeToJs();
                        break;
                    }
                    break;
            }
        }
    };
    private float mMaxFontSize;
    private LinearLayout mParent;
    private final BaseProperty mProperty;
    private String mValue;
    private List<TimePickerWidgetView> mViewList = new ArrayList();
    /* access modifiers changed from: private */
    public boolean needNotifyChange = false;

    static class MyAdapter implements TimePickerAdapter {
        private int mCurrent;
        private List<Object> mData;

        private MyAdapter() {
            this.mData = new ArrayList();
        }

        public void setData(List<Object> list) {
            if (list != null) {
                this.mData = list;
            }
        }

        public int getItemsCount() {
            return this.mData.size();
        }

        public void setCurrent(int i) {
            if (this.mCurrent != i && i >= 0 && i < this.mData.size()) {
                this.mCurrent = i;
            }
        }

        /* access modifiers changed from: 0000 */
        public Object getCurrentItem(int i) {
            return (i < 0 || i >= this.mData.size()) ? "" : this.mData.get(i);
        }

        /* access modifiers changed from: 0000 */
        public int getItemIndex(Object obj) {
            if (this.mData.contains(obj)) {
                return this.mData.indexOf(obj);
            }
            return 0;
        }

        public String getItem(int i) {
            this.mCurrent = 0;
            if (i >= 0 && i < this.mData.size()) {
                this.mCurrent = i;
                Object obj = this.mData.get(i);
                if (obj != null) {
                    return itemToString(obj);
                }
            }
            return "";
        }

        private String itemToString(Object obj) {
            if (obj == null) {
                return "";
            }
            if ((obj instanceof Integer) || (obj instanceof Double) || (obj instanceof String)) {
                return String.valueOf(obj);
            }
            return "";
        }

        public int getMaximumLength() {
            int i = 0;
            if (this.mData != null && this.mData.size() > 0) {
                for (Object itemToString : this.mData) {
                    String itemToString2 = itemToString(itemToString);
                    if (!TextUtils.isEmpty(itemToString2) && itemToString2.length() > i) {
                        i = itemToString2.length();
                    }
                }
            }
            return i;
        }

        public int getCurrentIndex() {
            return this.mCurrent;
        }
    }

    class PickerData {
        boolean isCyclic;
        int itemTextColor;
        float itemTextSize;
        List<Object> mData = new ArrayList();
        float mFountSize;
        Object mSelected = null;
        boolean mUsPercent = false;
        double mWidthInPercent;
        double mWidthInPix;
        int valueBorderColor;
        int valueSolidColor;
        int valueTextColor;

        PickerData() {
            this.mFountSize = Picker.this.getResources().getDimension(R.dimen.timepicker_selected_text_size);
            this.itemTextSize = (float) (((double) this.mFountSize) * 0.8d);
            this.valueTextColor = Picker.this.getResources().getColor(R.color.f_c_2);
            computeItemTextColor();
            this.valueBorderColor = Picker.this.getResources().getColor(R.color.default_font_color_cad);
            this.valueSolidColor = Picker.this.getResources().getColor(R.color.transparent);
            this.isCyclic = true;
        }

        /* access modifiers changed from: 0000 */
        public void setColor(int i) {
            this.valueTextColor = i;
            computeItemTextColor();
        }

        private void computeItemTextColor() {
            this.itemTextColor = Color.argb((int) (((double) Color.alpha(this.valueTextColor)) * 0.7d), Color.red(this.valueTextColor), Color.green(this.valueTextColor), Color.blue(this.valueTextColor));
        }

        /* access modifiers changed from: 0000 */
        public void setFontSize(String str) {
            if (!TextUtils.isEmpty(str)) {
                if (str.endsWith(Params.UNIT_PX) || str.endsWith("PX")) {
                    double doubleValue = Double.valueOf(str.substring(0, str.length() - 2)).doubleValue();
                    if (doubleValue > 0.0d) {
                        this.mFountSize = DimensionUtils.standardUnitToPixelPrecise((float) doubleValue);
                        this.itemTextSize = DimensionUtils.standardUnitToPixelPrecise((float) (doubleValue * 0.8d));
                    }
                    return;
                }
                double doubleValue2 = Double.valueOf(str).doubleValue();
                if (doubleValue2 > 0.0d) {
                    this.mFountSize = DimensionUtils.standardUnitToPixelPrecise((float) doubleValue2);
                    this.itemTextSize = DimensionUtils.standardUnitToPixelPrecise((float) (doubleValue2 * 0.8d));
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean setData(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            List<Object> access$600 = Picker.this.getData(str);
            if (access$600 == null || access$600.size() <= 0) {
                return false;
            }
            this.mData = access$600;
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void setSelect(Object obj) {
            if (obj == null || !this.mData.contains(obj)) {
                if (this.mData != null && this.mData.size() > 0) {
                    this.mSelected = this.mData.get(0);
                }
                return;
            }
            this.mSelected = obj;
        }

        /* access modifiers changed from: 0000 */
        public void setWidth(String str) {
            if (TextUtils.isEmpty(str)) {
                this.mWidthInPix = 0.0d;
                this.mWidthInPercent = 0.0d;
                this.mUsPercent = false;
            } else if (str.endsWith(Params.UNIT_PX)) {
                this.mWidthInPix = Double.valueOf(str.substring(0, str.length() - 2)).doubleValue();
                this.mWidthInPercent = 0.0d;
                this.mUsPercent = false;
            } else if (str.endsWith("%")) {
                this.mWidthInPercent = Double.valueOf(str.substring(0, str.length() - 1)).doubleValue();
                this.mWidthInPix = 0.0d;
                this.mUsPercent = true;
            } else {
                this.mWidthInPix = Double.valueOf(str).doubleValue();
                this.mWidthInPercent = 0.0d;
                this.mUsPercent = false;
            }
        }
    }

    public Picker(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = createProperty();
        initView(iAjxContext.getNativeContext());
    }

    private PickerProperty createProperty() {
        return new PickerProperty(this, this.mAjxContext);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    private void initView(Context context) {
        this.mParent = (LinearLayout) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.picker_layout, this).findViewById(R.id.picker_selector_layout);
    }

    private void initChild(Context context, int i, PickerData pickerData) {
        LayoutParams layoutParams = new LayoutParams(i, -1);
        TimePickerWidgetView timePickerWidgetView = new TimePickerWidgetView(context, pickerData);
        timePickerWidgetView.setItemHeight(this.mMaxFontSize);
        timePickerWidgetView.setTouchListener(this);
        this.mParent.addView(timePickerWidgetView, layoutParams);
        this.mViewList.add(timePickerWidgetView);
    }

    private void initPicker() {
        for (TimePickerWidgetView next : this.mViewList) {
            next.setAdapter(new MyAdapter());
            next.setCurrentItem(0);
            next.addScrollingListener(new TimePickerScrollListener() {
                public void onScrollingStarted(TimePickerWidgetView timePickerWidgetView) {
                }

                public void onScrollingFinished(TimePickerWidgetView timePickerWidgetView) {
                    Picker.this.updateChange();
                }
            });
            next.addChangingListener(new TimePickerChangedListener() {
                public void onChanged(TimePickerWidgetView timePickerWidgetView, int i, int i2) {
                    Picker.this.saveResult();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
        this.isLayout = true;
        if (!TextUtils.isEmpty(this.mData)) {
            initPickerData(this.mData);
            this.mData = null;
        }
        if (!TextUtils.isEmpty(this.mValue)) {
            updateSelect(this.mValue);
            this.mValue = null;
        }
    }

    public void initPickerData(String str) {
        boolean z;
        boolean z2;
        if (!this.isLayout) {
            this.mData = str;
            return;
        }
        int i = 0;
        if (this.mViewList != null && this.mViewList.size() > 0) {
            Iterator<TimePickerWidgetView> it = this.mViewList.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().isScrollingPerformed()) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        z = false;
        if (!z) {
            this.mParent.removeAllViews();
            this.mViewList.clear();
            ArrayList arrayList = new ArrayList();
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                z2 = false;
                while (i < length) {
                    try {
                        String string = jSONArray.getString(i);
                        if (!TextUtils.isEmpty(string)) {
                            PickerData pickerData = new PickerData();
                            JSONObject jSONObject = new JSONObject(string);
                            if (jSONObject.has("data") && pickerData.setData(jSONObject.getString("data"))) {
                                arrayList.add(pickerData);
                            }
                            if (jSONObject.has("width")) {
                                try {
                                    pickerData.setWidth(jSONObject.getString("width"));
                                    z2 = true;
                                } catch (JSONException unused) {
                                    z2 = true;
                                }
                            } else {
                                pickerData.setWidth("");
                            }
                            if (jSONObject.has("value")) {
                                pickerData.setSelect(jSONObject.get("value"));
                            }
                            if (jSONObject.has("fontsize")) {
                                Object obj = jSONObject.get("fontsize");
                                if (obj instanceof String) {
                                    pickerData.setFontSize((String) obj);
                                }
                            }
                            if (jSONObject.has("color")) {
                                Object obj2 = jSONObject.get("color");
                                if (obj2 instanceof String) {
                                    pickerData.setColor(StringUtils.parseColorByAnimator((String) obj2));
                                }
                            }
                            if (jSONObject.has("circularly")) {
                                Object obj3 = jSONObject.get("circularly");
                                if (obj3 instanceof String) {
                                    pickerData.isCyclic = Boolean.valueOf((String) obj3).booleanValue();
                                } else if (obj3 instanceof Boolean) {
                                    pickerData.isCyclic = ((Boolean) obj3).booleanValue();
                                }
                            }
                        }
                        i++;
                    } catch (JSONException unused2) {
                    }
                }
            } catch (JSONException unused3) {
                z2 = false;
            }
            if (arrayList.size() > 0) {
                dileWithFontSize(arrayList);
                initChildView(getContext(), arrayList, z2);
            }
        }
    }

    private void dileWithFontSize(List<PickerData> list) {
        if (list.size() > 0) {
            float f = 0.0f;
            for (PickerData pickerData : list) {
                float f2 = pickerData.mFountSize;
                if (f2 > f) {
                    f = f2;
                }
            }
            this.mMaxFontSize = f;
        }
    }

    private void initChildView(Context context, List<PickerData> list, boolean z) {
        int i;
        int size = list.size();
        if (size > 0) {
            int standardUnitToPixel = DimensionUtils.standardUnitToPixel(this.mProperty.getSize("width"));
            if (!z) {
                int i2 = standardUnitToPixel / size;
                if (i2 < 0) {
                    i2 = 0;
                }
                for (int i3 = 0; i3 < size; i3++) {
                    initChild(context, i2, list.get(i3));
                }
            } else {
                for (int i4 = 0; i4 < size; i4++) {
                    PickerData pickerData = list.get(i4);
                    if (pickerData.mUsPercent) {
                        i = (int) (((double) standardUnitToPixel) * (pickerData.mWidthInPercent / 100.0d));
                    } else {
                        i = DimensionUtils.standardUnitToPixel((float) pickerData.mWidthInPix);
                    }
                    if (i < 0) {
                        i = 0;
                    }
                    initChild(context, i, pickerData);
                }
            }
            initPicker();
            for (int i5 = 0; i5 < size; i5++) {
                PickerData pickerData2 = list.get(i5);
                initChildData(i5, pickerData2.mData, pickerData2.mSelected);
            }
        }
    }

    private void initChildData(int i, List<Object> list, Object obj) {
        if (i >= 0 && i < this.mViewList.size() && this.mViewList.size() > 0 && list != null && list.size() > 0) {
            TimePickerWidgetView timePickerWidgetView = this.mViewList.get(i);
            timePickerWidgetView.setVisibility(0);
            ((MyAdapter) timePickerWidgetView.getAdapter()).setData(list);
            if (obj == null || !list.contains(obj)) {
                timePickerWidgetView.setCurrentItem(0);
            } else {
                timePickerWidgetView.setCurrentItem(list.indexOf(obj));
            }
        }
    }

    public void updateSelect(String str) {
        this.needNotifyChange = false;
        if (!this.isLayout) {
            this.mValue = str;
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                Object obj = jSONArray.get(i);
                if (obj != null && i < this.mViewList.size()) {
                    TimePickerWidgetView timePickerWidgetView = this.mViewList.get(i);
                    timePickerWidgetView.setCurrentItem(((MyAdapter) timePickerWidgetView.getAdapter()).getItemIndex(obj), ((PickerProperty) this.mProperty).hasChangeAnimation());
                }
            }
        } catch (JSONException unused) {
        }
    }

    public void updateData(String str) {
        boolean z;
        JSONArray jSONArray;
        if (this.mViewList != null && this.mViewList.size() > 0) {
            Iterator<TimePickerWidgetView> it = this.mViewList.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().isScrollingPerformed()) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        z = false;
        if (!z) {
            int i = -1;
            Object obj = null;
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("column")) {
                    i = jSONObject.getInt("column");
                }
                jSONArray = jSONObject.has("data") ? jSONObject.getJSONArray("data") : null;
                try {
                    if (jSONObject.has("value")) {
                        obj = jSONObject.get("value");
                    }
                } catch (JSONException unused) {
                }
            } catch (JSONException unused2) {
                jSONArray = null;
            }
            if (i >= 0 && i < this.mViewList.size()) {
                TimePickerWidgetView timePickerWidgetView = this.mViewList.get(i);
                if (jSONArray == null) {
                    timePickerWidgetView.setVisibility(8);
                    return;
                }
                ArrayList arrayList = new ArrayList();
                int length = jSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    try {
                        Object obj2 = jSONArray.get(i2);
                        if (obj2 != null) {
                            arrayList.add(obj2);
                        }
                    } catch (Exception unused3) {
                    }
                }
                if (arrayList.size() <= 0) {
                    timePickerWidgetView.setVisibility(8);
                    return;
                }
                timePickerWidgetView.setVisibility(0);
                ((MyAdapter) timePickerWidgetView.getAdapter()).setData(arrayList);
                if (obj != null && arrayList.contains(obj)) {
                    timePickerWidgetView.setCurrentItem(arrayList.indexOf(obj));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void saveResult() {
        this.mHandler.sendEmptyMessage(1003);
    }

    /* access modifiers changed from: private */
    public void updateChange() {
        if (this.mHandler.hasMessages(1004)) {
            this.mHandler.removeMessages(1004);
        }
        if (this.mProperty != null && ((PickerProperty) this.mProperty).hasChangeListener()) {
            this.mHandler.sendEmptyMessageDelayed(1004, 300);
        }
    }

    /* access modifiers changed from: private */
    public void saveData() {
        int size = this.mViewList.size();
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < size; i++) {
            TimePickerWidgetView timePickerWidgetView = this.mViewList.get(i);
            if (timePickerWidgetView.getVisibility() == 0) {
                int currentItem = timePickerWidgetView.getCurrentItem();
                ((MyAdapter) timePickerWidgetView.getAdapter()).setCurrent(currentItem);
                jSONArray.put(((MyAdapter) timePickerWidgetView.getAdapter()).getCurrentItem(currentItem));
            }
        }
        this.mProperty.updateAttribute("value", jSONArray.toString(), false, false, true, false);
    }

    /* access modifiers changed from: private */
    public void updateChangeToJs() {
        ((PickerProperty) this.mProperty).invokeJSChangeEvent();
    }

    /* access modifiers changed from: private */
    public List<Object> getData(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(jSONArray.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public void onTouched() {
        this.needNotifyChange = true;
    }
}
