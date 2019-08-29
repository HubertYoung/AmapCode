package defpackage;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: mi reason: default package */
/* compiled from: AlertDialogCompat */
public final class mi {
    public Dialog a;
    public CharSequence b;
    public boolean c = true;
    a d;
    a e;
    private Context f;
    private Builder g;
    private CharSequence h;
    private CharSequence i;
    private CharSequence j;

    public mi(Context context) {
        if (context != null) {
            this.f = context;
            this.g = new Builder(this.f, R.style.AlertDialogCompat);
        }
    }

    public final void a(int i2) {
        this.b = this.f.getText(i2);
    }

    public final void a(int i2, a aVar) {
        this.i = this.f.getText(i2);
        this.d = aVar;
    }

    public final void b(int i2, a aVar) {
        this.j = this.f.getText(i2);
        this.e = aVar;
    }

    public final void a() {
        if (this.f != null) {
            View inflate = LayoutInflater.from(this.f).inflate(R.layout.alert_dialog_compat, null);
            this.g.setView(inflate);
            if (TextUtils.isEmpty(this.b)) {
                ((LinearLayout) inflate.findViewById(R.id.topPanel)).setVisibility(8);
            } else {
                ((TextView) inflate.findViewById(R.id.alertTitle)).setText(this.b);
            }
            if (TextUtils.isEmpty(this.h)) {
                ((LinearLayout) inflate.findViewById(R.id.contentPanel)).setVisibility(8);
            } else {
                ((TextView) inflate.findViewById(R.id.message)).setText(this.h);
            }
            ArrayList arrayList = new ArrayList();
            final Button button = (Button) inflate.findViewById(R.id.button1);
            if (TextUtils.isEmpty(this.j)) {
                button.setVisibility(8);
            } else {
                button.setText(this.j);
                button.setVisibility(0);
                button.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        a aVar = mi.this.e;
                        mi miVar = mi.this;
                        button.getId();
                        aVar.a(miVar);
                    }
                });
                arrayList.add(button);
            }
            final Button button2 = (Button) inflate.findViewById(R.id.button2);
            if (TextUtils.isEmpty(this.i)) {
                button2.setVisibility(8);
            } else {
                button2.setText(this.i);
                button2.setVisibility(0);
                button2.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        a aVar = mi.this.d;
                        mi miVar = mi.this;
                        button2.getId();
                        aVar.a(miVar);
                    }
                });
                arrayList.add(button2);
            }
            if (arrayList.size() == 1) {
                inflate.findViewById(R.id.divide_right).setVisibility(8);
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                int size = arrayList.size();
                ShapeDrawable a2 = a(i2, this.f.getResources().getColor(R.color.c_1), size);
                ShapeDrawable a3 = a(i2, this.f.getResources().getColor(R.color.c_3), size);
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{16842919}, a3);
                stateListDrawable.addState(new int[0], a2);
                ((View) arrayList.get(i2)).setBackgroundDrawable(stateListDrawable);
            }
            this.g.setCancelable(this.c);
            this.g.create();
        }
    }

    public final void b() {
        this.a = this.g.show();
        LayoutParams attributes = this.a.getWindow().getAttributes();
        attributes.width = agn.a(this.f, 270.0f);
        this.a.getWindow().setAttributes(attributes);
    }

    private ShapeDrawable a(int i2, int i3, int i4) {
        float[] fArr;
        int dimensionPixelOffset = this.f.getResources().getDimensionPixelOffset(R.dimen.title_tab_radius);
        if (i4 == 1) {
            float f2 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f2, f2, f2, f2};
        } else if (i2 == 0) {
            float f3 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f3, f3};
        } else if (i2 == i4 - 1) {
            float f4 = (float) dimensionPixelOffset;
            fArr = new float[]{0.0f, 0.0f, 0.0f, 0.0f, f4, f4, 0.0f, 0.0f};
        } else {
            fArr = null;
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        shapeDrawable.getPaint().setColor(i3);
        return shapeDrawable;
    }
}
