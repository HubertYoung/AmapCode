package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.diy.DIYMainMapEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class DIYMainMapPresenter extends BaseMapWidgetPresenter<DIYMapWidget> {
    public static final String DIY_ENTRY_KEY_AGROUP = "agroup";
    public static final String DIY_ENTRY_KEY_AGROUP_TEST = "zuduiceshi";
    public static final String DIY_ENTRY_KEY_BUS_CARD = "buscode";
    public static final String DIY_ENTRY_KEY_CHECK_TEST = "xuanzhongceshi";
    public static final String DIY_ENTRY_KEY_FREERIDE = "free_ride";
    public static final String DIY_ENTRY_KEY_MORE = "更多";
    public static final String DIY_ENTRY_KEY_REPORT = "report";
    public static final String DIY_ENTRY_REALTIME_BUS_EVENT_KEY = "realtime_event";
    public static final String DIY_ENTRY_TRANFIC_EVENT_KEY = "traffic_event";
    public static final String DIY_MAIN_MAP_CONFIG_MODULE_NAME = "main_map_entry";
    public static final int MAX_ENTRY_NUMBER;
    private static final int POSITION_MORE_ENTRIES = 100;
    public static final String SP_KEY_DIY_MAIN_MAP_ENTRIES = "main_map_entries";
    public static final String SP_KEY_DIY_MAIN_MAP_ENTRIYS_NEW = "main_map_entries_new";
    private lp mConfigChangeListener = new lp() {
        public void onConfigCallBack(int i) {
        }

        public void onConfigResultCallBack(int i, String str) {
            DIYMainMapPresenter.this.processData(str);
        }
    };
    private DIYMainMapWidgetManager mDIYMainMapWidgetManager;
    /* access modifiers changed from: private */
    public List<DIYMainMapEntry> mEntries = new ArrayList();
    /* access modifiers changed from: private */
    public boolean mIsNewShowing = false;
    /* access modifiers changed from: private */
    public DIYMainMapEventListener mListener;
    private List<DIYMainMapEntry> mMainMapShowEntriesCache = new ArrayList();
    /* access modifiers changed from: private */
    public MapSharePreference mMapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private OnTouchListener mOnTouchListener = new MyOnTouchListener();
    private ViewGroup mViewGroup;

    class MyOnTouchListener implements OnTouchListener {
        private MyOnTouchListener() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (view instanceof DIYEntryView) {
                DIYEntryView dIYEntryView = (DIYEntryView) view;
                String str = (String) dIYEntryView.getTag();
                switch (motionEvent.getAction()) {
                    case 0:
                        switch (DIYMainMapPresenter.this.mListener.getPosition(dIYEntryView)) {
                            case BOTTOM:
                                dIYEntryView.getIcon().setBackgroundResource(R.drawable.icon_c_pre_down);
                                break;
                            case TOP:
                                dIYEntryView.getIcon().setBackgroundResource(R.drawable.icon_c_pre_up);
                                break;
                            default:
                                dIYEntryView.getIcon().setBackgroundResource(R.drawable.icon_c_pre_mid);
                                break;
                        }
                    case 1:
                        if (DIYMainMapPresenter.DIY_ENTRY_KEY_MORE.equals(str)) {
                            DIYMainMapPresenter.this.mListener.onEntryMoreClick(DIYMainMapPresenter.this.mEntries);
                            if (DIYMainMapPresenter.this.mIsNewShowing) {
                                DIYMainMapPresenter.this.mMapSharePreference.putBooleanValue(DIYMainMapPresenter.SP_KEY_DIY_MAIN_MAP_ENTRIYS_NEW, false);
                                DIYMainMapPresenter.this.mIsNewShowing = false;
                            }
                        } else {
                            Iterator it = DIYMainMapPresenter.this.mEntries.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    DIYMainMapEntry dIYMainMapEntry = (DIYMainMapEntry) it.next();
                                    if (dIYMainMapEntry.key.equals(str)) {
                                        DIYMainMapPresenter.this.mListener.onEntryClick(dIYMainMapEntry);
                                    }
                                }
                            }
                        }
                        dIYEntryView.getIcon().setBackgroundColor(0);
                        break;
                }
            }
            return true;
        }
    }

    public void attachView(brh brh) {
        if (brh instanceof ViewGroup) {
            this.mViewGroup = (ViewGroup) brh;
        }
        init();
    }

    static {
        if (AMapPageUtil.getAppContext().getResources().getDisplayMetrics().densityDpi >= 320) {
            MAX_ENTRY_NUMBER = 4;
        } else {
            MAX_ENTRY_NUMBER = 3;
        }
    }

    public void initialize(DIYMapWidget dIYMapWidget) {
        super.initialize(dIYMapWidget);
    }

    public void init(DIYMainMapWidgetManager dIYMainMapWidgetManager) {
        this.mDIYMainMapWidgetManager = dIYMainMapWidgetManager;
        this.mDIYMainMapWidgetManager.setmDIYMainMapPresenter(this);
        this.mDIYMainMapWidgetManager.setmDIYMainMapView((DIYMainMapView) ((DIYMapWidget) getWidget()).getContentView());
        this.mDIYMainMapWidgetManager.init();
        this.mListener = this.mDIYMainMapWidgetManager;
    }

    private void init() {
        lo.a().a((String) DIY_MAIN_MAP_CONFIG_MODULE_NAME, this.mConfigChangeListener);
        try {
            processData(lo.a().a((String) DIY_MAIN_MAP_CONFIG_MODULE_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detachView() {
        lo.a().b(DIY_MAIN_MAP_CONFIG_MODULE_NAME, this.mConfigChangeListener);
    }

    /* access modifiers changed from: private */
    public void processData(String str) {
        if (!TextUtils.isEmpty(str)) {
            List<DIYMainMapEntry> parseData = parseData(str);
            List<DIYMainMapEntry> savedEntries = getSavedEntries();
            boolean isComparedSame = isComparedSame(parseData, savedEntries);
            AtomicReference atomicReference = new AtomicReference(Boolean.FALSE);
            if (saveEntries(mergeEntries(parseData, savedEntries, atomicReference)) && ((Boolean) atomicReference.get()).booleanValue() && !isComparedSame) {
                this.mMapSharePreference.putBooleanValue(SP_KEY_DIY_MAIN_MAP_ENTRIYS_NEW, true);
            }
        }
    }

    private boolean isComparedSame(List<DIYMainMapEntry> list, List<DIYMainMapEntry> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        Iterator<DIYMainMapEntry> it = list.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                return true;
            }
            DIYMainMapEntry next = it.next();
            if (!"traffic_event".equals(next.key)) {
                Iterator<DIYMainMapEntry> it2 = list2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = false;
                        break;
                    }
                    DIYMainMapEntry next2 = it2.next();
                    if (isEntryStringEquals(next.key, next2.key) && isEntryStringEquals(next.name, next2.name) && isEntryIconEquals(next.icon, next2.icon) && isEntryIconEquals(next.highlightIcon, next2.highlightIcon) && isEntryStringEquals(next.normalText, next2.normalText) && isEntryStringEquals(next.highlightText, next2.highlightText) && isEntryStringEquals(next.scheme, next2.scheme) && next.order == next2.order) {
                        break;
                    }
                }
                if (!z) {
                    return false;
                }
            }
        }
    }

    private boolean isEntryStringEquals(String str, String str2) {
        if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
            return TextUtils.equals(str, str2);
        }
        return true;
    }

    private boolean isEntryIconEquals(Object obj, Object obj2) {
        if ((obj instanceof String) && (obj2 instanceof String)) {
            return isEntryStringEquals((String) obj, (String) obj2);
        }
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    public void getNativeEntries() {
        this.mEntries.clear();
        this.mEntries.addAll(getSavedEntries());
        callMainMapShowEntries();
    }

    public void onDestroy() {
        lo.a().b(DIY_MAIN_MAP_CONFIG_MODULE_NAME, this.mConfigChangeListener);
    }

    private void parseJsonToEntry(DIYMainMapEntry dIYMainMapEntry, JSONObject jSONObject, String str) throws JSONException {
        if (dIYMainMapEntry != null && jSONObject != null) {
            if (TextUtils.isEmpty(str)) {
                str = jSONObject.optString("key", "");
            }
            dIYMainMapEntry.key = str;
            dIYMainMapEntry.scheme = jSONObject.optString("scheme", "");
            dIYMainMapEntry.icon = jSONObject.opt(H5Param.MENU_ICON);
            dIYMainMapEntry.highlightIcon = jSONObject.opt("highlightIcon");
            dIYMainMapEntry.normalText = jSONObject.optString("normalText", "");
            dIYMainMapEntry.highlightText = jSONObject.optString("highlightText", "");
            dIYMainMapEntry.name = jSONObject.optString("name", "");
            int optInt = jSONObject.optInt("order", 0);
            if (optInt <= 0) {
                optInt = Integer.MAX_VALUE;
            }
            dIYMainMapEntry.order = optInt;
            if (!TextUtils.isEmpty(dIYMainMapEntry.key) && dIYMainMapEntry.key.equals("traffic_event")) {
                dIYMainMapEntry.icon = Integer.valueOf(this.mMapSharePreference.getBooleanValue("traffic", true) ? R.drawable.icon_c_diy8 : R.drawable.icon_c_diy1);
            }
        }
    }

    private String getJsonString(List<DIYMainMapEntry> list) {
        String str = "";
        if (list != null) {
            try {
                JSONArray jSONArray = new JSONArray();
                for (DIYMainMapEntry next : list) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("key", next.key);
                    jSONObject.put("scheme", next.scheme);
                    jSONObject.put(H5Param.MENU_ICON, next.icon);
                    jSONObject.put("highlightIcon", next.highlightIcon);
                    jSONObject.put("normalText", next.normalText);
                    jSONObject.put("highlightText", next.highlightText);
                    jSONObject.put("name", next.name);
                    jSONObject.put("order", next.order);
                    jSONArray.put(jSONObject);
                }
                str = jSONArray.toString();
            } catch (Throwable th) {
                th.printStackTrace();
                return "";
            }
        }
        return str;
    }

    private List<DIYMainMapEntry> getSavedEntries() {
        ArrayList arrayList = new ArrayList();
        String stringValue = this.mMapSharePreference.getStringValue(SP_KEY_DIY_MAIN_MAP_ENTRIES, null);
        if (TextUtils.isEmpty(stringValue)) {
            arrayList.add(createTrafficEventEntry());
            return arrayList;
        }
        try {
            JSONArray jSONArray = new JSONArray(stringValue);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                DIYMainMapEntry dIYMainMapEntry = new DIYMainMapEntry();
                parseJsonToEntry(dIYMainMapEntry, jSONObject, null);
                arrayList.add(dIYMainMapEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            arrayList.add(createTrafficEventEntry());
        }
        Collections.sort(arrayList, new Comparator<DIYMainMapEntry>() {
            public int compare(DIYMainMapEntry dIYMainMapEntry, DIYMainMapEntry dIYMainMapEntry2) {
                return dIYMainMapEntry.order - dIYMainMapEntry2.order;
            }
        });
        return arrayList;
    }

    private boolean saveEntries(List<DIYMainMapEntry> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        try {
            this.mMapSharePreference.putStringValue(SP_KEY_DIY_MAIN_MAP_ENTRIES, getJsonString(list));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateOrder(List<DIYMainMapEntry> list) {
        saveEntries(mergeEntries(getSavedEntries(), list, new AtomicReference(Boolean.FALSE)));
    }

    private List<DIYMainMapEntry> mergeEntries(List<DIYMainMapEntry> list, List<DIYMainMapEntry> list2, AtomicReference<Boolean> atomicReference) {
        if (atomicReference == null) {
            atomicReference = new AtomicReference<>(Boolean.FALSE);
        }
        atomicReference.set(Boolean.FALSE);
        int size = list.size();
        ArrayList arrayList = new ArrayList();
        for (DIYMainMapEntry next : list) {
            boolean z = true;
            Iterator<DIYMainMapEntry> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DIYMainMapEntry next2 = it.next();
                if (next.key.equals(next2.key)) {
                    next.order = next2.order;
                    z = false;
                    break;
                }
            }
            next.isNew = z;
            if (z) {
                arrayList.add(next);
            }
            if (size > MAX_ENTRY_NUMBER && z && next.order > MAX_ENTRY_NUMBER) {
                atomicReference.set(Boolean.TRUE);
            }
        }
        return sortEntries(list, arrayList);
    }

    private DIYMainMapEntry createTrafficEventEntry() {
        DIYMainMapEntry dIYMainMapEntry = new DIYMainMapEntry();
        dIYMainMapEntry.key = "traffic_event";
        dIYMainMapEntry.scheme = "";
        dIYMainMapEntry.icon = Integer.valueOf(this.mMapSharePreference.getBooleanValue("traffic", true) ? R.drawable.icon_c_diy8 : R.drawable.icon_c_diy1);
        dIYMainMapEntry.name = "路况";
        dIYMainMapEntry.order = 1;
        return dIYMainMapEntry;
    }

    private List<DIYMainMapEntry> parseData(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(createTrafficEventEntry());
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                JSONObject optJSONObject = jSONObject.optJSONObject(next);
                if (optJSONObject != null) {
                    DIYMainMapEntry dIYMainMapEntry = new DIYMainMapEntry();
                    parseJsonToEntry(dIYMainMapEntry, optJSONObject, next);
                    arrayList.add(dIYMainMapEntry);
                }
            }
        } catch (JSONException e) {
            arrayList.clear();
            e.printStackTrace();
        }
        return arrayList;
    }

    private List<DIYMainMapEntry> sortEntries(List<DIYMainMapEntry> list, List<DIYMainMapEntry> list2) {
        list.removeAll(list2);
        Collections.sort(list, new Comparator<DIYMainMapEntry>() {
            public int compare(DIYMainMapEntry dIYMainMapEntry, DIYMainMapEntry dIYMainMapEntry2) {
                return dIYMainMapEntry.order - dIYMainMapEntry2.order;
            }
        });
        Collections.sort(list2, new Comparator<DIYMainMapEntry>() {
            public int compare(DIYMainMapEntry dIYMainMapEntry, DIYMainMapEntry dIYMainMapEntry2) {
                return dIYMainMapEntry.order - dIYMainMapEntry2.order;
            }
        });
        for (DIYMainMapEntry next : list2) {
            int i = next.order - 1;
            int size = list.size();
            if (i >= size) {
                i = size;
            }
            list.add(i, next);
        }
        int i2 = 0;
        while (i2 < list.size()) {
            i2++;
            list.get(i2).order = i2;
        }
        return list;
    }

    private void callMainMapShowEntries() {
        this.mListener.clearEntries();
        this.mMainMapShowEntriesCache.clear();
        int size = this.mEntries.size() <= MAX_ENTRY_NUMBER ? this.mEntries.size() : MAX_ENTRY_NUMBER;
        boolean z = false;
        for (int i = 0; i < size; i++) {
            DIYMainMapEntry dIYMainMapEntry = this.mEntries.get(i);
            this.mListener.addEntry(i, dIYMainMapEntry, this.mOnTouchListener);
            this.mMainMapShowEntriesCache.add(dIYMainMapEntry);
        }
        this.mListener.addMore(100, this.mOnTouchListener);
        boolean isShowNewEntry = isShowNewEntry();
        this.mIsNewShowing = isShowNewEntry;
        DIYMainMapEventListener dIYMainMapEventListener = this.mListener;
        if (isShowNewEntry && this.mEntries.size() > MAX_ENTRY_NUMBER) {
            z = true;
        }
        dIYMainMapEventListener.showNewEntry(z);
    }

    public List<DIYMainMapEntry> getMainMapShowEntries() {
        return this.mMainMapShowEntriesCache;
    }

    private boolean isShowNewEntry() {
        return this.mMapSharePreference.getBooleanValue(SP_KEY_DIY_MAIN_MAP_ENTRIYS_NEW, false);
    }

    public List<DIYMainMapEntry> getEntries() {
        return this.mEntries;
    }

    public OnTouchListener getOnTouchListener() {
        return this.mOnTouchListener;
    }
}
