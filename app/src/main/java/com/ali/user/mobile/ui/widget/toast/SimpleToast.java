package com.ali.user.mobile.ui.widget.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ali.user.mobile.security.ui.R;

@SuppressLint({"ShowToast"})
public class SimpleToast {
    public static Toast a(Context context, int i, int i2) {
        return a(context, 0, context.getResources().getText(i), i2);
    }

    public static Toast a(Context context, int i, CharSequence charSequence, int i2) {
        View view;
        Toast makeText = Toast.makeText(context, charSequence, i2);
        if (i != 0) {
            view = LayoutInflater.from(context).inflate(R.layout.I, null);
            ((ImageView) view.findViewById(R.id.ae)).setBackgroundResource(i);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.H, null);
        }
        ((TextView) view.findViewById(R.id.bY)).setText(charSequence);
        makeText.setView(view);
        makeText.setGravity(17, 0, 0);
        return makeText;
    }
}
