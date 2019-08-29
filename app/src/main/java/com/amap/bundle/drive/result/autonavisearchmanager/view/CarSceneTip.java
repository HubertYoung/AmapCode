package com.amap.bundle.drive.result.autonavisearchmanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.List;

public class CarSceneTip extends LinearLayout {
    private View mCloseView;
    private LinearLayout mContainerView;
    private Button mFirstTag;
    private OnClickListener mOnClickListener;
    /* access modifiers changed from: private */
    public a mOnTipClickListener;
    private View mRootView;
    private Button mSecondTag;
    private Button mThirdTag;

    public interface a {
        void a(defpackage.oo.a aVar);
    }

    public CarSceneTip(Context context) {
        this(context, null);
    }

    public CarSceneTip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CarSceneTip(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOnClickListener = new OnClickListener() {
            public final void onClick(View view) {
                CarSceneTip.this.setVisibility(8);
                Object tag = view.getTag();
                if (tag == null || !(tag instanceof defpackage.oo.a) || CarSceneTip.this.mOnTipClickListener == null) {
                    if (CarSceneTip.this.mOnTipClickListener != null) {
                        CarSceneTip.this.mOnTipClickListener.a(null);
                    }
                    return;
                }
                CarSceneTip.this.mOnTipClickListener.a((defpackage.oo.a) tag);
            }
        };
        this.mRootView = inflate(context, R.layout.car_scene_tip, this);
        initView();
        setData();
    }

    private void initView() {
        this.mFirstTag = (Button) this.mRootView.findViewById(R.id.car_scene_first);
        this.mSecondTag = (Button) this.mRootView.findViewById(R.id.car_scene_second);
        this.mThirdTag = (Button) this.mRootView.findViewById(R.id.car_scene_third);
        this.mContainerView = (LinearLayout) this.mRootView.findViewById(R.id.route_carscene_container);
        this.mCloseView = this.mRootView.findViewById(R.id.car_scene_close);
    }

    private void setData() {
        this.mFirstTag.setOnClickListener(this.mOnClickListener);
        this.mSecondTag.setOnClickListener(this.mOnClickListener);
        this.mThirdTag.setOnClickListener(this.mOnClickListener);
        this.mCloseView.setOnClickListener(this.mOnClickListener);
    }

    public void setData(oo ooVar) {
        if (ooVar == null) {
            setVisibility(8);
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (ooVar.d != null && ooVar.d.size() > 0) {
            for (int i = 0; i < ooVar.d.size(); i++) {
                ISearchPoiData iSearchPoiData = ooVar.d.get(i);
                defpackage.oo.a aVar = new defpackage.oo.a();
                aVar.a = 101;
                aVar.c = iSearchPoiData.getName();
                aVar.b = ooVar.d;
                aVar.d = iSearchPoiData;
                arrayList.add(aVar);
            }
        }
        if (arrayList.size() < 2) {
            arrayList.clear();
            if (ooVar.e != null && ooVar.e.size() > 0) {
                defpackage.oo.a aVar2 = new defpackage.oo.a();
                aVar2.a = 105;
                aVar2.c = AMapAppGlobal.getApplication().getString(R.string.car_scene_start);
                aVar2.b = ooVar.e;
                arrayList.add(aVar2);
            }
            if (ooVar.f != null && ooVar.f.size() > 0) {
                defpackage.oo.a aVar3 = new defpackage.oo.a();
                aVar3.a = 106;
                aVar3.c = AMapAppGlobal.getApplication().getString(R.string.car_scene_arrive);
                aVar3.b = ooVar.f;
                arrayList.add(aVar3);
            }
            if (ooVar.a != null && ooVar.a.size() > 0) {
                defpackage.oo.a aVar4 = new defpackage.oo.a();
                aVar4.a = 102;
                aVar4.c = AMapAppGlobal.getApplication().getString(R.string.car_scene_in);
                aVar4.b = ooVar.a;
                arrayList.add(aVar4);
            }
            if (ooVar.b != null && ooVar.b.size() > 0) {
                defpackage.oo.a aVar5 = new defpackage.oo.a();
                aVar5.a = 103;
                aVar5.c = AMapAppGlobal.getApplication().getString(R.string.car_scene_out);
                aVar5.b = ooVar.b;
                arrayList.add(aVar5);
            }
            if (ooVar.c != null && ooVar.c.size() > 0) {
                defpackage.oo.a aVar6 = new defpackage.oo.a();
                aVar6.a = 104;
                aVar6.c = AMapAppGlobal.getApplication().getString(R.string.car_scene_parking);
                aVar6.b = ooVar.c;
                arrayList.add(aVar6);
            }
        }
        setTag(arrayList);
    }

    private void setTag(List<defpackage.oo.a> list) {
        switch (list.size()) {
            case 1:
                defpackage.oo.a aVar = list.get(0);
                this.mFirstTag.setTag(aVar);
                this.mFirstTag.setText(aVar.c);
                this.mSecondTag.setVisibility(8);
                this.mThirdTag.setVisibility(8);
                int a2 = agn.a(getContext(), 46.0f);
                this.mContainerView.setPadding(a2, 0, a2, 0);
                return;
            case 2:
                defpackage.oo.a aVar2 = list.get(0);
                this.mFirstTag.setTag(aVar2);
                this.mFirstTag.setText(aVar2.c);
                defpackage.oo.a aVar3 = list.get(1);
                this.mSecondTag.setTag(aVar3);
                this.mSecondTag.setText(aVar3.c);
                this.mThirdTag.setVisibility(8);
                return;
            case 3:
                defpackage.oo.a aVar4 = list.get(0);
                this.mFirstTag.setTag(aVar4);
                this.mFirstTag.setText(aVar4.c);
                defpackage.oo.a aVar5 = list.get(1);
                this.mSecondTag.setTag(aVar5);
                this.mSecondTag.setText(aVar5.c);
                defpackage.oo.a aVar6 = list.get(2);
                this.mThirdTag.setTag(aVar6);
                this.mThirdTag.setText(aVar6.c);
                return;
            default:
                defpackage.oo.a aVar7 = list.get(0);
                this.mFirstTag.setTag(aVar7);
                this.mFirstTag.setText(aVar7.c);
                defpackage.oo.a aVar8 = list.get(1);
                this.mSecondTag.setTag(aVar8);
                this.mSecondTag.setText(aVar8.c);
                defpackage.oo.a aVar9 = list.get(2);
                this.mThirdTag.setTag(aVar9);
                this.mThirdTag.setText(aVar9.c);
                return;
        }
    }

    public void setMessage(String str) {
        this.mFirstTag.setText(str);
    }

    public void setOnTipClickListener(a aVar) {
        this.mOnTipClickListener = aVar;
    }
}
