package com.autonavi.minimap.myProfile.page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

@PageAction("amap.basemap.action.verifyuser_page")
public class VerifyUserPage extends AbstractBasePage<drz> implements OnClickListener {
    public EditText a;
    private ImageButton b;
    private Button c;
    private TextView d;
    /* access modifiers changed from: private */
    public ProgressDlg e;
    /* access modifiers changed from: private */
    public Handler f = new Handler() {
        public final void handleMessage(Message message) {
            VerifyUserPage.this.e.dismiss();
            VerifyUserPage.this.a();
            super.handleMessage(message);
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.verifyuser_activity_layout);
        this.b = (ImageButton) findViewById(R.id.title_btn_left);
        this.c = (Button) findViewById(R.id.doconfirmmappoint);
        this.c.setVisibility(0);
        this.c.setOnClickListener(this);
        this.b.setOnClickListener(this);
        this.d = (TextView) findViewById(R.id.title_text_name);
        this.d.setText(R.string.user_verify_modify_config);
        this.a = (EditText) findViewById(R.id.contact);
        this.a.setHorizontallyScrolling(true);
        a();
    }

    /* access modifiers changed from: private */
    public void a() {
        if (ConfigerHelper.getInstance().IsConfigerFileExist()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ConfigerHelper.getInstance().GetConfigerFile(), "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(readLine);
                        sb.append("\n");
                        stringBuffer.append(sb.toString());
                    } else {
                        this.a.setText(stringBuffer.toString());
                        bufferedReader.close();
                        return;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            final Builder negativeButton = new Builder(getActivity()).setTitle(R.string.user_verify_modify_title).setCancelable(false).setCanceledOnTouchOutside(false).setPositiveButton(R.string.user_verify_modify_generate, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    VerifyUserPage.this.e = new ProgressDlg(VerifyUserPage.this.getActivity());
                    VerifyUserPage.this.e.setMessage(AMapAppGlobal.getApplication().getString(R.string.hint_waiting));
                    VerifyUserPage.this.e.setCancelable(false);
                    VerifyUserPage.this.e.show();
                    new Thread(new Runnable() {
                        public final void run() {
                            ConfigerHelper.getInstance().CopyConfigerFileToSdCard();
                            VerifyUserPage.this.f.sendEmptyMessage(0);
                        }
                    }).start();
                }
            }).setNegativeButton(R.string.cancle, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    VerifyUserPage.this.finish();
                }
            });
            this.f.postAtTime(new Runnable() {
                public final void run() {
                    try {
                        AMapPageUtil.startAlertDialogPage(negativeButton);
                    } catch (Throwable th) {
                        DebugLog.error(th);
                    }
                }
            }, 500);
        }
    }

    public void onClick(View view) {
        if (view == this.b) {
            finish();
            return;
        }
        if (view == this.c) {
            drz drz = (drz) this.mPresenter;
            String obj = ((VerifyUserPage) drz.mPage).a.getText().toString();
            try {
                FileWriter fileWriter = new FileWriter(ConfigerHelper.getInstance().GetConfigerFilePath(), false);
                fileWriter.write(obj);
                fileWriter.close();
                if (((VerifyUserPage) drz.mPage).isAlive()) {
                    ToastHelper.showLongToast(((VerifyUserPage) drz.mPage).getContext().getString(R.string.user_verify_save_success));
                }
            } catch (Exception unused) {
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new drz(this);
    }
}
