package com.autonavi.minimap.drive.navi.navitts.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.map.core.LocationMode.LocationIgnore;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.NaviRecordUtil;
import com.autonavi.minimap.drive.navi.navitts.NaviRecordUtil.a;
import com.autonavi.minimap.drive.navi.navitts.adapter.NaviTtsMyAdapter;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import java.util.ArrayList;
import java.util.List;

public class OfflineNaviTtsMyFragment extends Fragment implements LocationIgnore {
    private NaviTtsMyAdapter adapter;
    private int fromFlag = -1;
    private RecyclerView mRvDownload;
    private OfflineNaviTtsFragment parentFragment;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_offline_navitts_my, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mRvDownload = (RecyclerView) view.findViewById(R.id.rv_navitts_my);
        this.mRvDownload.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRvDownload.setItemAnimator(null);
        this.mRvDownload.setHasFixedSize(true);
        this.adapter = new NaviTtsMyAdapter(this.parentFragment, this.fromFlag);
        notifyAllData();
        this.mRvDownload.setAdapter(this.adapter);
    }

    public void setParentFragment(OfflineNaviTtsFragment offlineNaviTtsFragment) {
        this.parentFragment = offlineNaviTtsFragment;
    }

    public void setFromFlag(int i) {
        this.fromFlag = i;
    }

    public void notifyAllData() {
        if (this.adapter != null) {
            NaviTtsMyAdapter naviTtsMyAdapter = this.adapter;
            ArrayList<a> customVoices = NaviRecordUtil.getCustomVoices();
            naviTtsMyAdapter.a.clear();
            for (a next : customVoices) {
                List<dgl> list = naviTtsMyAdapter.a;
                tw twVar = new tw();
                twVar.c = next.a;
                twVar.l = next.b;
                twVar.f = next.c;
                twVar.o = 50;
                twVar.g = next.e;
                twVar.k = a.a();
                ua uaVar = new ua();
                uaVar.b = next.c;
                uaVar.c = 4;
                uaVar.d = next.f;
                uaVar.e = next.j;
                uaVar.f = next.h;
                list.add(new dgl(twVar, uaVar));
            }
            NaviTtsMyAdapter naviTtsMyAdapter2 = this.adapter;
            DriveOfflineSDK.e();
            naviTtsMyAdapter2.a(DriveOfflineSDK.l());
            this.adapter.notifyDataSetChanged();
        }
    }

    public void notifyData(dgl dgl) {
        if (this.adapter != null) {
            this.adapter.b(dgl);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }
}
