package com.autonavi.map.search.tip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.List;

public class SherlorkDetector$2 extends BaseAdapter {
    final /* synthetic */ cbv this$0;
    final /* synthetic */ List val$templateList;

    public long getItemId(int i) {
        return (long) i;
    }

    SherlorkDetector$2(cbv cbv, List list) {
        this.this$0 = cbv;
        this.val$templateList = list;
    }

    public int getCount() {
        return this.val$templateList.size();
    }

    public Object getItem(int i) {
        return this.val$templateList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        c cVar;
        if (view == null) {
            view = LayoutInflater.from(this.this$0.a).inflate(R.layout.dialog_poi_template_item, null);
            cVar = new c(0);
            cVar.a = (TextView) view.findViewById(R.id.txt_template_id);
            cVar.b = (TextView) view.findViewById(R.id.txt_content);
            view.setTag(cVar);
        } else {
            cVar = (c) view.getTag();
        }
        PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) getItem(i);
        TextView textView = cVar.a;
        StringBuilder sb = new StringBuilder("id:");
        sb.append(poiLayoutTemplate.getId());
        textView.setText(sb.toString());
        cVar.b.setText(cbv.a(poiLayoutTemplate));
        return view;
    }
}
