package com.autonavi.map.search.tip;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.List;

public class SearchPoiTipView extends SearchPoiTipBaseView implements cbs {
    private TextView mTip_text_2001;

    public interface a {
    }

    public boolean getShowTipClose() {
        return false;
    }

    public defpackage.cbs.a getTipViewListener() {
        return null;
    }

    public void setShowTipClose(boolean z) {
    }

    public void setTipViewListener(defpackage.cbs.a aVar) {
    }

    public /* bridge */ /* synthetic */ void setChildPoiClickListener(cbr cbr) {
        super.setChildPoiClickListener(cbr);
    }

    public /* bridge */ /* synthetic */ void setIsPoiChildMark(boolean z) {
        super.setIsPoiChildMark(z);
    }

    public /* bridge */ /* synthetic */ void setTipItemEvent(defpackage.ely.a aVar) {
        super.setTipItemEvent(aVar);
    }

    public /* bridge */ /* synthetic */ void setTipsHeightChangedListener(bxj bxj, a aVar) {
        super.setTipsHeightChangedListener(bxj, aVar);
    }

    public SearchPoiTipView(Context context) {
        super(context);
        setContentView(R.layout.poi_layout_tip_temp);
        initViewTemp();
        defpackage.cbv.a.a;
    }

    private void initViewTemp() {
        this.mTip_text_2001 = (TextView) findViewById(R.id.tip_text_2001);
    }

    public void update(InfoliteResult infoliteResult, SearchPoi searchPoi) {
        List<PoiLayoutTemplate> templateData = searchPoi.getTemplateData();
        if (templateData != null && templateData.size() != 0) {
            for (PoiLayoutTemplate processLine1 : templateData) {
                processLine1(searchPoi, processLine1);
            }
        }
    }

    private defpackage.cbq.a getPoiNameTextProcessor(final ISearchPoiData iSearchPoiData) {
        return new defpackage.cbq.a() {
            public final CharSequence a(PoiLayoutTemplate poiLayoutTemplate) {
                String str = (String) iSearchPoiData.getPoiExtra().get("titleName");
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
                return cbq.a().a(poiLayoutTemplate);
            }
        };
    }

    private void processLine1(SearchPoi searchPoi, PoiLayoutTemplate poiLayoutTemplate) {
        cbq.a(this.mTip_text_2001, poiLayoutTemplate, getPoiNameTextProcessor(searchPoi));
    }
}
