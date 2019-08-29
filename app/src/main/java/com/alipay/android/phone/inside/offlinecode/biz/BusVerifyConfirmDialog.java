package com.alipay.android.phone.inside.offlinecode.biz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class BusVerifyConfirmDialog extends Activity {
    public static final String ID = "id";
    private String id;

    public void onBackPressed() {
    }

    public static final void show(Context context, String str) {
        Intent intent = new Intent();
        intent.setFlags(268500992);
        intent.setClass(context, BusVerifyConfirmDialog.class);
        intent.putExtra("id", str);
        context.startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature();
        handleIntent(getIntent());
        showDialog();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void requestWindowFeature() {
        try {
            requestWindowFeature(1);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    private void handleIntent(Intent intent) {
        this.id = intent.getStringExtra("id");
    }

    /* access modifiers changed from: protected */
    public void showDialog() {
        Builder builder = new Builder(this, 16973939);
        builder.setMessage("为确保使用安全，需要在支付宝中确认你的身份");
        builder.setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                BusVerifyConfirmDialog.this.onConfirm();
            }
        });
        builder.setCancelable(false);
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.getWindow().setBackgroundDrawableResource(17170445);
        create.show();
    }

    /* access modifiers changed from: protected */
    public void onConfirm() {
        IdentifyVerify.onConfirm(this.id);
        finish();
        overridePendingTransition(0, 0);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
