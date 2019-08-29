package com.autonavi.minimap.multidexload;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"DM_EXIT"})
public class MdLoadErrorActivity extends Activity implements OnClickListener {
    private Button a;

    public void onBackPressed() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_md_load_error);
        this.a = (Button) findViewById(R.id.btn_exit_app);
        this.a.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_exit_app) {
            System.exit(0);
        }
    }
}
