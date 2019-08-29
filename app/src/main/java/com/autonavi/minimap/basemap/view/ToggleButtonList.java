package com.autonavi.minimap.basemap.view;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;

public final class ToggleButtonList {
    public HashSet a = new HashSet();
    public CompoundButton b;
    ArrayList<CompoundButton> c = new ArrayList<>();
    boolean d = false;
    OnCheckedChangeListener e = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (z) {
                ToggleButtonList.this.b = compoundButton;
                ToggleButtonList toggleButtonList = ToggleButtonList.this;
                Iterator<CompoundButton> it = toggleButtonList.c.iterator();
                while (it.hasNext()) {
                    CompoundButton next = it.next();
                    boolean z2 = false;
                    next.setChecked(next == compoundButton);
                    if (!toggleButtonList.d) {
                        if (next != compoundButton) {
                            z2 = true;
                        }
                        next.setEnabled(z2);
                    }
                }
                ToggleButtonList.a(ToggleButtonList.this, compoundButton);
                return;
            }
            if (ToggleButtonList.this.d && ToggleButtonList.this.b == compoundButton) {
                ToggleButtonList.this.b = null;
                ToggleButtonList.a(ToggleButtonList.this, compoundButton);
            }
        }
    };

    public class SelectedChangedArgs extends EventObject {
        public CompoundButton SelectedButton;

        public SelectedChangedArgs(Object obj, CompoundButton compoundButton) {
            super(obj);
            this.SelectedButton = compoundButton;
        }
    }

    public interface a {
        void a();
    }

    public ToggleButtonList(CompoundButton[] compoundButtonArr) {
        a(compoundButtonArr);
    }

    private void a(CompoundButton[] compoundButtonArr) {
        this.d = true;
        if (compoundButtonArr != null && compoundButtonArr.length > 0) {
            for (CompoundButton compoundButton : compoundButtonArr) {
                compoundButton.setOnCheckedChangeListener(this.e);
                this.c.add(compoundButton);
            }
        }
    }

    static /* synthetic */ void a(ToggleButtonList toggleButtonList, CompoundButton compoundButton) {
        Iterator it = toggleButtonList.a.iterator();
        while (it.hasNext()) {
            new SelectedChangedArgs(toggleButtonList, compoundButton);
            ((a) it.next()).a();
        }
    }
}
