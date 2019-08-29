package com.ali.user.mobile.register.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.register.region.RegionAdapter;
import com.ali.user.mobile.register.region.RegionChoice;
import com.ali.user.mobile.register.region.RegionChoice.RegionCallback;
import com.ali.user.mobile.register.region.RegionInfo;
import com.ali.user.mobile.register.region.RegionViewModel;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APBladeView;
import com.ali.user.mobile.ui.widget.APPinnedHeaderListView;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.AUBladeView;
import java.util.List;
import java.util.Map;

public class AliUserRegisterChoiceRegionActivity extends BaseActivity implements OnItemClickListener {
    private static RegionCallback mRegionCallback;
    private Map<String, Integer> mLetterMap;
    protected AUBladeView mLetterView;
    private List<RegionInfo> mList;
    protected APPinnedHeaderListView mListView;
    protected APTitleBar mRegisterTitle;

    public static void setRegionCallback(RegionCallback regionCallback) {
        mRegionCallback = regionCallback;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.l);
        findViews();
        init();
        LoggerUtils.a("", "AliUserRegisterChoiceRegionActivity", "login", "");
    }

    private void findViews() {
        this.mRegisterTitle = (APTitleBar) findViewById(R.id.bx);
        this.mListView = (APPinnedHeaderListView) findViewById(R.id.bw);
        this.mLetterView = (AUBladeView) findViewById(R.id.N);
    }

    private void init() {
        this.mRegisterTitle.getTitleTextView().setEllipsize(TruncateAt.MIDDLE);
        this.mRegisterTitle.getTitleTextView().setMaxEms(14);
        this.mRegisterTitle.getTitleTextView().setSingleLine(true);
        this.mRegisterTitle.getTitleTextView().setSupportEmoji(false);
        UIConfigManager.a(this.mRegisterTitle);
        RegionViewModel b = RegionChoice.a().b();
        if (b == null) {
            if (mRegionCallback != null) {
                mRegionCallback.a((String) "system error");
            }
            toast(getResources().getString(R.string.cD), 3000);
            return;
        }
        this.mList = b.b();
        this.mLetterMap = b.c();
        List<String> d = b.d();
        if (this.mList == null || this.mLetterMap == null || d == null || d.isEmpty()) {
            if (mRegionCallback != null) {
                mRegionCallback.a((String) "system error");
            }
            toast(getResources().getString(R.string.cD), 3000);
            return;
        }
        this.mListView.setAdapter((ListAdapter) new RegionAdapter(this, this.mList));
        this.mListView.setOnItemClickListener(this);
        this.mLetterView.setOnItemClickListener(new APBladeView.OnItemClickListener() {
            public final void a(String str) {
                AliUserRegisterChoiceRegionActivity.this.onLetterItemClick(str);
            }
        });
    }

    /* access modifiers changed from: private */
    public void onLetterItemClick(String str) {
        if (str != null) {
            for (char charAt = str.charAt(0); charAt >= 'A'; charAt = (char) (charAt - 1)) {
                String valueOf = String.valueOf(charAt);
                if (this.mLetterMap.containsKey(valueOf)) {
                    int intValue = this.mLetterMap.get(valueOf).intValue();
                    if (intValue != -1) {
                        this.mListView.setSelection(intValue);
                        return;
                    }
                }
            }
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        RegionInfo regionInfo = this.mList.get(i);
        Intent intent = new Intent();
        intent.putExtra("region", regionInfo);
        setResult(1, intent);
        finish();
        if (mRegionCallback != null) {
            mRegionCallback.a(regionInfo);
            mRegionCallback = null;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mRegionCallback = null;
    }

    public void setAppId() {
        this.mAppId = "20000009";
    }
}
