package com.autonavi.minimap.drive.navi.navitts.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.map.core.LocationMode.LocationIgnore;
import com.autonavi.minimap.R;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class OfflineNaviTtsSquareFragment extends Fragment implements LocationIgnore {
    private dft adapter;
    private LinkedList<BannerItem> bannerItems;
    private int fromFlag = -1;
    private CopyOnWriteArrayList<dgl> mAllVoiceDataList;
    private RecyclerView mRvSquare;
    private OfflineNaviTtsFragment parentFragment;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_offline_navitts_square, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mRvSquare = (RecyclerView) view.findViewById(R.id.rv_navitts_square);
        this.mRvSquare.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRvSquare.setItemAnimator(null);
        this.mRvSquare.setHasFixedSize(true);
        this.adapter = new dft(this.parentFragment, this.fromFlag);
        this.mRvSquare.addItemDecoration(new dfv(this.adapter));
        this.adapter.a(this.mAllVoiceDataList);
        if (this.bannerItems != null) {
            this.adapter.a(this.bannerItems);
        }
        this.adapter.notifyDataSetChanged();
        this.mRvSquare.setAdapter(this.adapter);
    }

    public void setParentFragment(OfflineNaviTtsFragment offlineNaviTtsFragment) {
        this.parentFragment = offlineNaviTtsFragment;
    }

    public synchronized void setData(CopyOnWriteArrayList<dgl> copyOnWriteArrayList) {
        this.mAllVoiceDataList = copyOnWriteArrayList;
    }

    public void setFromFlag(int i) {
        this.fromFlag = i;
    }

    public void setBannerData(LinkedList<BannerItem> linkedList) {
        this.bannerItems = linkedList;
    }

    public void notifyAllData() {
        if (this.adapter != null) {
            this.adapter.a(this.mAllVoiceDataList);
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
        notifyAllData();
    }
}
