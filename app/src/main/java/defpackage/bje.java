package defpackage;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.minimap.R;

/* renamed from: bje reason: default package */
/* compiled from: CustomTelListDialog */
public final class bje extends CompatDialog {
    public ListAdapter a;
    private ListView b = ((ListView) findViewById(R.id.list));
    private TextView c = ((TextView) findViewById(R.id.title));
    private OnItemClickListener d;
    private Button e = ((Button) findViewById(R.id.btn_cancle));

    public bje(Activity activity) {
        super(activity, R.style.custom_dlg);
        setContentView(R.layout.custom_tel_listview_dialog_layout);
        this.e.setText(R.string.cancel);
        this.e.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                bje.this.dismiss();
            }
        });
    }

    public final void a(ListAdapter listAdapter) {
        this.a = listAdapter;
        this.b.setAdapter(this.a);
        this.b.setItemsCanFocus(true);
        this.b.setChoiceMode(1);
    }

    public final void a(OnItemClickListener onItemClickListener) {
        this.d = onItemClickListener;
        this.b.setOnItemClickListener(this.d);
    }

    public final void a(OnClickListener onClickListener) {
        this.e.setOnClickListener(onClickListener);
    }

    public final void show() {
        Window window = getWindow();
        window.setGravity(80);
        window.setLayout(-1, -2);
        window.setWindowAnimations(R.style.custom_dlg_animation);
        super.show();
    }
}
