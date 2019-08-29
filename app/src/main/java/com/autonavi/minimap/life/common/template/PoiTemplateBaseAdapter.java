package com.autonavi.minimap.life.common.template;

import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.search.templete.model.ITemplate;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import defpackage.dol;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.HashMap;
import java.util.Map;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR"})
public abstract class PoiTemplateBaseAdapter<T extends ITemplate<PoiLayoutTemplate>, VH extends dol> extends AbstractViewHolderBaseAdapter<T, VH> {
    private Map<Integer, doj<T, VH>> mProcessMap = new HashMap();
    private Map<Integer, dok<T, VH>> mProcessMap2 = new HashMap();

    public boolean isUseTemplate(int i) {
        return true;
    }

    public abstract void onBindUnTemplateViewHolder(VH vh, T t, int i, int i2);

    public abstract void onFinishProcessViewHolder(VH vh, T t, int i, int i2);

    public abstract void onPreprocessViewHolder(VH vh, T t, int i, int i2);

    public final void AddProcessTemplate(int i, doj<T, VH> doj) {
        if (!this.mProcessMap.containsKey(Integer.valueOf(i))) {
            this.mProcessMap.put(Integer.valueOf(i), doj);
        }
    }

    public final void AddProcessTemplate2(int i, dok<T, VH> dok) {
        if (!this.mProcessMap2.containsKey(Integer.valueOf(i))) {
            this.mProcessMap2.put(Integer.valueOf(i), dok);
        }
    }

    public final void RemoveProcessTemplate(int i) {
        if (this.mProcessMap.containsKey(Integer.valueOf(i))) {
            this.mProcessMap.remove(Integer.valueOf(i));
        }
    }

    public final void RemoveProcessTemplate2(int i) {
        if (this.mProcessMap2.containsKey(Integer.valueOf(i))) {
            this.mProcessMap2.remove(Integer.valueOf(i));
        }
    }

    public void onBindViewHolderWithData(VH vh, T t, int i, int i2) {
        onPreprocessViewHolder(vh, t, i, i2);
        if (!isUseTemplate(i2) || t == null) {
            onBindUnTemplateViewHolder(vh, t, i, i2);
        } else {
            if (!(t.getTemplateDataMap() == null || this.mProcessMap2 == null)) {
                for (Integer intValue : this.mProcessMap2.keySet()) {
                    int intValue2 = intValue.intValue();
                    dok dok = this.mProcessMap2.get(Integer.valueOf(intValue2));
                    if (t.getTemplateDataMap().containsKey(Integer.valueOf(intValue2))) {
                        if (dok != null) {
                            dok.a(vh, t, (PoiLayoutTemplate) t.getTemplateDataMap().get(Integer.valueOf(intValue2)), true);
                        }
                    } else if (dok != null) {
                        dok.a(vh, t, null, false);
                    }
                }
            }
            if (t.getTemplateData() != null) {
                for (PoiLayoutTemplate poiLayoutTemplate : t.getTemplateData()) {
                    if (poiLayoutTemplate != null && this.mProcessMap.containsKey(Integer.valueOf(poiLayoutTemplate.getId()))) {
                        this.mProcessMap.get(Integer.valueOf(poiLayoutTemplate.getId()));
                    }
                }
            } else {
                onBindUnTemplateViewHolder(vh, t, i, i2);
            }
        }
        onFinishProcessViewHolder(vh, t, i, i2);
    }
}
