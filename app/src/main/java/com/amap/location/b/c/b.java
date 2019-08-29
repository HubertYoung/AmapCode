package com.amap.location.b.c;

import com.amap.location.common.model.CellStatus.HistoryCell;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Cell */
public class b {
    public byte a;
    public String b;
    public ArrayList<c> c = new ArrayList<>();
    public List<HistoryCell> d = new ArrayList();

    public void a(byte b2, String str) {
        this.a = b2;
        this.b = str;
        this.c.clear();
        this.d.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Cell{mRadioType=");
        sb.append(this.a);
        sb.append(", mOperator='");
        sb.append(this.b);
        sb.append('\'');
        sb.append(", mCellPart=");
        sb.append(this.c);
        sb.append(", mHistoryCellList=");
        sb.append(this.d);
        sb.append('}');
        return sb.toString();
    }
}
