package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RealTimeBusRepeateAdapter;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusDateSettingPage;
import java.util.ArrayList;

/* renamed from: dyn reason: default package */
/* compiled from: RTBusDateSettingPresenter */
public final class dyn extends eaf<RTBusDateSettingPage> {
    private ArrayList<Integer> a = new ArrayList<>();

    public dyn(RTBusDateSettingPage rTBusDateSettingPage) {
        super(rTBusDateSettingPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((RTBusDateSettingPage) this.mPage).getArguments();
        if (arguments != null) {
            this.a = (ArrayList) arguments.getObject("position.list");
        }
        RTBusDateSettingPage rTBusDateSettingPage = (RTBusDateSettingPage) this.mPage;
        ArrayList<Integer> arrayList = this.a;
        rTBusDateSettingPage.a.setOnBackClickListener((OnClickListener) new OnClickListener(arrayList) {
            final /* synthetic */ ArrayList a;

            {
                this.a = r2;
            }

            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("position.list", this.a);
                RTBusDateSettingPage.this.setResult(ResultType.OK, pageBundle);
                RTBusDateSettingPage.this.finish();
            }
        });
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(rTBusDateSettingPage.getString(R.string.busline_setting_day_one));
        arrayList2.add(rTBusDateSettingPage.getString(R.string.busline_setting_day_two));
        arrayList2.add(rTBusDateSettingPage.getString(R.string.busline_setting_day_three));
        arrayList2.add(rTBusDateSettingPage.getString(R.string.busline_setting_day_four));
        arrayList2.add(rTBusDateSettingPage.getString(R.string.busline_setting_day_five));
        arrayList2.add(rTBusDateSettingPage.getString(R.string.busline_setting_day_six));
        arrayList2.add(rTBusDateSettingPage.getString(R.string.busline_setting_day_seven));
        rTBusDateSettingPage.c = new RealTimeBusRepeateAdapter(rTBusDateSettingPage, arrayList2);
        rTBusDateSettingPage.c.setPositionList(arrayList);
        rTBusDateSettingPage.b.setAdapter(rTBusDateSettingPage.c);
        rTBusDateSettingPage.b.setOnItemClickListener(new OnItemClickListener(arrayList) {
            final /* synthetic */ ArrayList val$dateList;

            {
                this.val$dateList = r2;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!this.val$dateList.toString().contains(String.valueOf(i))) {
                    this.val$dateList.add(Integer.valueOf(i));
                } else if (this.val$dateList.size() != 1) {
                    this.val$dateList.remove(Integer.valueOf(i));
                } else {
                    return;
                }
                RTBusDateSettingPage.this.c.setPositionList(this.val$dateList);
                RTBusDateSettingPage.this.c.notifyDataSetChanged();
            }
        });
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
    }

    public final void onStart() {
        super.onStart();
    }

    public final ON_BACK_TYPE onBackPressed() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("position.list", this.a);
        ((RTBusDateSettingPage) this.mPage).setResult(ResultType.OK, pageBundle);
        return super.onBackPressed();
    }
}
