package com.autonavi.minimap.drive.widget;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RouteBanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CarPlateKeyboardAdapter extends BaseAdapter {
    private static final Map<String, Integer> sProvinceMap;
    private static String[] sProvinceNames = {"京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新"};
    private Context mContext;
    private String mCurrentProvinceCode = "11";
    /* access modifiers changed from: private */
    public int mCurrentSelected = 0;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public List<a> mProvinceNameItemList = new ArrayList();

    static class a {
        String a;
        boolean b = false;

        a() {
        }
    }

    static class b {
        TextView a;

        b() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public CarPlateKeyboardAdapter(Context context) {
        this.mContext = context;
        initData();
    }

    private void initData() {
        this.mProvinceNameItemList.clear();
        for (String str : sProvinceNames) {
            a aVar = new a();
            aVar.a = str;
            this.mProvinceNameItemList.add(aVar);
        }
    }

    public int getCount() {
        return this.mProvinceNameItemList.size();
    }

    public a getItem(int i) {
        return this.mProvinceNameItemList.get(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view2;
        b bVar;
        if (view == null) {
            bVar = new b();
            view2 = LayoutInflater.from(this.mContext).inflate(R.layout.keyboard_key_item, viewGroup, false);
            bVar.a = (TextView) view2.findViewById(R.id.key_name);
            view2.setTag(bVar);
        } else {
            view2 = view;
            bVar = (b) view.getTag();
        }
        a item = getItem(i);
        bVar.a.setText(item.a);
        if (item.b) {
            bVar.a.setTextColor(this.mContext.getResources().getColor(R.color.f_c_6));
        } else {
            bVar.a.setTextColor(-14606047);
        }
        bVar.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                int access$000 = CarPlateKeyboardAdapter.this.mCurrentSelected;
                CarPlateKeyboardAdapter.this.mCurrentSelected = i;
                ((a) CarPlateKeyboardAdapter.this.mProvinceNameItemList.get(access$000)).b = false;
                ((a) CarPlateKeyboardAdapter.this.mProvinceNameItemList.get(CarPlateKeyboardAdapter.this.mCurrentSelected)).b = true;
                CarPlateKeyboardAdapter.this.notifyDataSetChanged();
                CarPlateKeyboardAdapter.this.sendMsgKeyboardUpdated();
            }
        });
        return view2;
    }

    /* access modifiers changed from: private */
    public void sendMsgKeyboardUpdated() {
        this.mHandler.sendEmptyMessage(100);
    }

    private void sendMsgProvinceNameUpdated() {
        this.mHandler.sendEmptyMessage(101);
    }

    public void setProvinceCode(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mCurrentProvinceCode = str;
            if (sProvinceMap.containsKey(this.mCurrentProvinceCode)) {
                Integer num = sProvinceMap.get(this.mCurrentProvinceCode);
                if (num != null) {
                    this.mCurrentSelected = num.intValue();
                    if (this.mCurrentSelected >= 0) {
                        clearSelect(this.mCurrentSelected);
                        this.mProvinceNameItemList.get(this.mCurrentSelected).b = true;
                        notifyDataSetChanged();
                        sendMsgKeyboardUpdated();
                    }
                }
            }
        }
    }

    public void setProvinceName(String str) {
        if (!TextUtils.isEmpty(str) && str.length() == 1) {
            int length = sProvinceNames.length;
            int i = 0;
            int i2 = 0;
            while (i2 < length && !str.equals(sProvinceNames[i2])) {
                i2++;
            }
            if (i2 < sProvinceNames.length) {
                i = i2;
            }
            this.mCurrentSelected = i;
            clearSelect(this.mCurrentSelected);
            notifyDataSetChanged();
            sendMsgProvinceNameUpdated();
        }
    }

    public void clearSelect(int i) {
        int size = this.mProvinceNameItemList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 == this.mCurrentSelected) {
                this.mProvinceNameItemList.get(i2).b = true;
            } else {
                this.mProvinceNameItemList.get(i2).b = false;
            }
        }
    }

    public String getCurrentProvinceShortName() {
        return sProvinceNames[this.mCurrentSelected];
    }

    public String getCurrentProvinceCode() {
        if (this.mCurrentSelected == 0) {
            return null;
        }
        for (Entry next : sProvinceMap.entrySet()) {
            if (((Integer) next.getValue()).intValue() == this.mCurrentSelected) {
                return (String) next.getKey();
            }
        }
        return this.mCurrentProvinceCode;
    }

    static {
        HashMap hashMap = new HashMap();
        sProvinceMap = hashMap;
        hashMap.put("11", Integer.valueOf(0));
        sProvinceMap.put("12", Integer.valueOf(1));
        sProvinceMap.put("13", Integer.valueOf(2));
        sProvinceMap.put("14", Integer.valueOf(3));
        sProvinceMap.put("15", Integer.valueOf(4));
        sProvinceMap.put("21", Integer.valueOf(5));
        sProvinceMap.put("22", Integer.valueOf(6));
        sProvinceMap.put("23", Integer.valueOf(7));
        sProvinceMap.put("31", Integer.valueOf(8));
        sProvinceMap.put("32", Integer.valueOf(9));
        sProvinceMap.put(RouteBanner.REQUEST_KEY_TRAIN, Integer.valueOf(10));
        sProvinceMap.put("34", Integer.valueOf(11));
        sProvinceMap.put("35", Integer.valueOf(12));
        sProvinceMap.put(RouteBanner.REQUEST_KEY_RIDE, Integer.valueOf(13));
        sProvinceMap.put(RouteBanner.REQUEST_KEY_COACH, Integer.valueOf(14));
        sProvinceMap.put(SuperId.BIT_2_INDOOR_TAG_HOT, Integer.valueOf(15));
        sProvinceMap.put("42", Integer.valueOf(16));
        sProvinceMap.put("43", Integer.valueOf(17));
        sProvinceMap.put("44", Integer.valueOf(18));
        sProvinceMap.put("45", Integer.valueOf(19));
        sProvinceMap.put("46", Integer.valueOf(20));
        sProvinceMap.put("50", Integer.valueOf(21));
        sProvinceMap.put("51", Integer.valueOf(22));
        sProvinceMap.put("52", Integer.valueOf(23));
        sProvinceMap.put("53", Integer.valueOf(24));
        sProvinceMap.put("54", Integer.valueOf(25));
        sProvinceMap.put("61", Integer.valueOf(26));
        sProvinceMap.put("62", Integer.valueOf(27));
        sProvinceMap.put(SuperId.BIT_2_REALTIMEBUS_BUSSTATION_AUTO, Integer.valueOf(28));
        sProvinceMap.put(SuperId.BIT_2_REALTIMEBUS_BUSSTATION, Integer.valueOf(29));
        sProvinceMap.put(SuperId.BIT_2_MAIN_BUSSTATION, Integer.valueOf(30));
    }
}
