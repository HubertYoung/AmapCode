package com.alipay.mobile.nebulacore.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;

public class H5Dialog {
    /* access modifiers changed from: private */
    public boolean a;
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public AlertDialog c;
    private Builder d;
    /* access modifiers changed from: private */
    public View e;
    /* access modifiers changed from: private */
    public int f;
    /* access modifiers changed from: private */
    public CharSequence g;
    /* access modifiers changed from: private */
    public int h;
    /* access modifiers changed from: private */
    public CharSequence i;
    /* access modifiers changed from: private */
    public Button j;
    /* access modifiers changed from: private */
    public LayoutParams k;
    /* access modifiers changed from: private */
    public Button l;
    private EditText m;
    private boolean n = false;
    /* access modifiers changed from: private */
    public Drawable o;
    /* access modifiers changed from: private */
    public int p;
    /* access modifiers changed from: private */
    public View q;
    /* access modifiers changed from: private */
    public OnDismissListener r;

    private class Builder {
        private TextView b;
        private TextView c;
        /* access modifiers changed from: private */
        public Window d;
        private LinearLayout e;

        /* synthetic */ Builder(H5Dialog x0, byte b2) {
            this();
        }

        private Builder() {
            H5Dialog.this.c = new android.app.AlertDialog.Builder(H5Dialog.this.b).create();
            H5Dialog.this.c.show();
            H5Dialog.this.c.getWindow().clearFlags(131080);
            H5Dialog.this.c.getWindow().setSoftInputMode(4);
            this.d = H5Dialog.this.c.getWindow();
            View contv = LayoutInflater.from(H5Dialog.this.b).inflate(R.layout.h5_dialog, null);
            contv.setFocusable(true);
            contv.setFocusableInTouchMode(true);
            this.d.setBackgroundDrawableResource(R.drawable.h5_dialog_window);
            this.d.setContentView(contv);
            this.b = (TextView) this.d.findViewById(R.id.title);
            this.c = (TextView) this.d.findViewById(R.id.h5_message);
            this.c.setMovementMethod(ScrollingMovementMethod.getInstance());
            this.e = (LinearLayout) this.d.findViewById(R.id.h5_buttonLayout);
            if (H5Dialog.this.e != null) {
                LinearLayout linearLayout = (LinearLayout) this.d.findViewById(R.id.h5_contentView);
                linearLayout.removeAllViews();
                linearLayout.addView(H5Dialog.this.e);
            }
            if (H5Dialog.this.f != 0) {
                setTitle(H5Dialog.this.f);
            }
            if (H5Dialog.this.g != null) {
                setTitle(H5Dialog.this.g);
            }
            if (H5Dialog.this.g == null && H5Dialog.this.f == 0) {
                this.b.setVisibility(8);
            }
            if (H5Dialog.this.h != 0) {
                setMessage(H5Dialog.this.h);
            }
            if (H5Dialog.this.i != null) {
                setMessage(H5Dialog.this.i);
            }
            if (H5Dialog.this.j != null) {
                this.e.addView(H5Dialog.this.j);
            }
            if (!(H5Dialog.this.k == null || H5Dialog.this.l == null)) {
                if (this.e.getChildCount() > 0) {
                    H5Dialog.this.k.setMargins(H5DimensionUtil.dip2px(H5Dialog.this.b, 12.0f), 0, 0, H5DimensionUtil.dip2px(H5Dialog.this.b, 9.0f));
                    H5Dialog.this.l.setLayoutParams(H5Dialog.this.k);
                    this.e.addView(H5Dialog.this.l, 1);
                } else {
                    H5Dialog.this.l.setLayoutParams(H5Dialog.this.k);
                    this.e.addView(H5Dialog.this.l);
                }
            }
            if (H5Dialog.this.p != 0) {
                ((LinearLayout) this.d.findViewById(R.id.h5_material_background)).setBackgroundResource(H5Dialog.this.p);
            }
            if (H5Dialog.this.o != null) {
                LinearLayout linearLayout2 = (LinearLayout) this.d.findViewById(R.id.h5_material_background);
                if (VERSION.SDK_INT >= 16) {
                    linearLayout2.setBackground(H5Dialog.this.o);
                }
            }
            if (H5Dialog.this.q != null) {
                setContentView(H5Dialog.this.q);
            }
            H5Dialog.this.c.setCanceledOnTouchOutside(H5Dialog.this.a);
            if (H5Dialog.this.r != null) {
                H5Dialog.this.c.setOnDismissListener(H5Dialog.this.r);
            }
        }

        public void setTitle(int resId) {
            this.b.setText(resId);
        }

        public void setTitle(CharSequence title) {
            this.b.setText(title);
        }

        public void setMessage(int resId) {
            this.c.setText(resId);
        }

        public void setMessage(CharSequence message) {
            this.c.setText(message);
        }

        public void setPositiveButton(String text, OnClickListener listener) {
            Button button = new Button(H5Dialog.this.b);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setBackgroundResource(R.drawable.h5_dialog_card);
            button.setTextColor(Color.argb(255, 35, 159, FavoritesPointFragment.REQUEST_COMPNAY));
            button.setText(text);
            button.setGravity(17);
            button.setTextSize(14.0f);
            button.setPadding(H5DimensionUtil.dip2px(H5Dialog.this.b, 12.0f), 0, H5DimensionUtil.dip2px(H5Dialog.this.b, 32.0f), H5DimensionUtil.dip2px(H5Dialog.this.b, 9.0f));
            button.setOnClickListener(listener);
            this.e.addView(button);
        }

        public void setNegativeButton(String text, OnClickListener listener) {
            Button button = new Button(H5Dialog.this.b);
            LayoutParams params = new LayoutParams(-2, -2);
            button.setLayoutParams(params);
            button.setBackgroundResource(R.drawable.h5_dialog_card);
            button.setText(text);
            button.setTextColor(Color.argb(222, 0, 0, 0));
            button.setTextSize(14.0f);
            button.setGravity(17);
            button.setPadding(0, 0, 0, H5DimensionUtil.dip2px(H5Dialog.this.b, 8.0f));
            button.setOnClickListener(listener);
            if (this.e.getChildCount() > 0) {
                params.setMargins(20, 0, 10, H5DimensionUtil.dip2px(H5Dialog.this.b, 9.0f));
                button.setLayoutParams(params);
                this.e.addView(button, 1);
                return;
            }
            button.setLayoutParams(params);
            this.e.addView(button);
        }

        public void setView(View view) {
            LinearLayout l = (LinearLayout) this.d.findViewById(R.id.h5_contentView);
            l.removeAllViews();
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            view.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    Builder.this.d.setSoftInputMode(5);
                    ((InputMethodManager) H5Dialog.this.b.getSystemService("input_method")).toggleSoftInput(2, 1);
                }
            });
            l.addView(view);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    if (viewGroup.getChildAt(i) instanceof EditText) {
                        EditText editText = (EditText) viewGroup.getChildAt(i);
                        editText.setFocusable(true);
                        editText.requestFocus();
                        editText.setFocusableInTouchMode(true);
                    }
                }
                for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                    if (viewGroup.getChildAt(i2) instanceof AutoCompleteTextView) {
                        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) viewGroup.getChildAt(i2);
                        autoCompleteTextView.setFocusable(true);
                        autoCompleteTextView.requestFocus();
                        autoCompleteTextView.setFocusableInTouchMode(true);
                    }
                }
            }
        }

        public void setContentView(View contentView) {
            contentView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            if (contentView instanceof ListView) {
                H5Dialog.setListViewHeightBasedOnChildren((ListView) contentView);
            }
            LinearLayout linearLayout = (LinearLayout) this.d.findViewById(R.id.h5_message_content_view);
            if (linearLayout != null) {
                linearLayout.removeAllViews();
                linearLayout.addView(contentView);
            }
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                if (linearLayout.getChildAt(i) instanceof AutoCompleteTextView) {
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) linearLayout.getChildAt(i);
                    autoCompleteTextView.setFocusable(true);
                    autoCompleteTextView.requestFocus();
                    autoCompleteTextView.setFocusableInTouchMode(true);
                }
            }
        }

        public void setBackground(Drawable drawable) {
            LinearLayout linearLayout = (LinearLayout) this.d.findViewById(R.id.h5_material_background);
            if (VERSION.SDK_INT >= 16) {
                linearLayout.setBackground(drawable);
            }
        }

        public void setBackgroundResource(int resId) {
            ((LinearLayout) this.d.findViewById(R.id.h5_material_background)).setBackgroundResource(resId);
        }

        public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            H5Dialog.this.c.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
    }

    public H5Dialog(Context context) {
        this.b = context;
    }

    private static boolean a() {
        return VERSION.SDK_INT >= 21;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int totalHeight = 0;
            for (int i2 = 0; i2 < listAdapter.getCount(); i2++) {
                View listItem = listAdapter.getView(i2, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + totalHeight;
            listView.setLayoutParams(params);
        }
    }

    public void show() {
        if (!this.n) {
            this.d = new Builder(this, 0);
        } else {
            this.c.show();
        }
        this.n = true;
    }

    public H5Dialog setView(View view) {
        this.e = view;
        if (this.d != null) {
            this.d.setView(view);
        }
        return this;
    }

    public H5Dialog setContentView(View view) {
        this.q = view;
        if (this.d != null) {
            this.d.setContentView(this.q);
        }
        return this;
    }

    public H5Dialog setBackground(Drawable drawable) {
        this.o = drawable;
        if (this.d != null) {
            this.d.setBackground(this.o);
        }
        return this;
    }

    public H5Dialog setBackgroundResource(int resId) {
        this.p = resId;
        if (this.d != null) {
            this.d.setBackgroundResource(this.p);
        }
        return this;
    }

    public void dismiss() {
        this.c.dismiss();
    }

    public H5Dialog setTitle(int resId) {
        this.f = resId;
        if (this.d != null) {
            this.d.setTitle(resId);
        }
        return this;
    }

    public H5Dialog setTitle(CharSequence title) {
        this.g = title;
        if (this.d != null) {
            this.d.setTitle(title);
        }
        return this;
    }

    public H5Dialog setMessage(int resId) {
        this.h = resId;
        if (this.d != null) {
            this.d.setMessage(resId);
        }
        return this;
    }

    public H5Dialog setMessage(CharSequence message) {
        this.i = message;
        if (this.d != null) {
            this.d.setMessage(message);
        }
        return this;
    }

    public H5Dialog setPositiveButton(int resId, OnClickListener listener) {
        this.j = new Button(this.b);
        this.j.setLayoutParams(new LayoutParams(-2, -2));
        this.j.setBackgroundResource(R.drawable.h5_dialog_button);
        this.j.setTextColor(Color.argb(255, 35, 159, FavoritesPointFragment.REQUEST_COMPNAY));
        this.j.setText(resId);
        this.j.setGravity(17);
        this.j.setTextSize(14.0f);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.setMargins(H5DimensionUtil.dip2px(this.b, 2.0f), 0, H5DimensionUtil.dip2px(this.b, 12.0f), H5DimensionUtil.dip2px(this.b, 9.0f));
        this.j.setLayoutParams(layoutParams);
        this.j.setOnClickListener(listener);
        if (a()) {
            this.j.setBackgroundResource(17170445);
        }
        return this;
    }

    public H5Dialog setPositiveButton(String text, OnClickListener listener) {
        this.j = new Button(this.b);
        this.j.setLayoutParams(new LayoutParams(-2, -2));
        this.j.setBackgroundResource(R.drawable.h5_dialog_button);
        this.j.setTextColor(Color.argb(255, 35, 159, FavoritesPointFragment.REQUEST_COMPNAY));
        this.j.setText(text);
        this.j.setGravity(17);
        this.j.setTextSize(14.0f);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.setMargins(H5DimensionUtil.dip2px(this.b, 2.0f), 0, H5DimensionUtil.dip2px(this.b, 12.0f), H5DimensionUtil.dip2px(this.b, 9.0f));
        this.j.setLayoutParams(layoutParams);
        this.j.setOnClickListener(listener);
        if (a()) {
            this.j.setBackgroundResource(17170445);
        }
        return this;
    }

    public H5Dialog setNegativeButton(int resId, OnClickListener listener) {
        this.l = new Button(this.b);
        this.k = new LayoutParams(-2, -2);
        this.l.setLayoutParams(this.k);
        this.l.setBackgroundResource(R.drawable.h5_dialog_button);
        this.l.setText(resId);
        this.l.setTextColor(Color.argb(222, 0, 0, 0));
        this.l.setTextSize(14.0f);
        this.l.setGravity(17);
        this.l.setOnClickListener(listener);
        if (a()) {
            this.l.setBackgroundResource(17170445);
        }
        return this;
    }

    public H5Dialog setPrompt(int resId, OnClickListener listener) {
        this.m = new EditText(this.b);
        this.k = new LayoutParams(-2, -2);
        this.m.setLayoutParams(this.k);
        this.m.setText(resId);
        this.m.setTextSize(14.0f);
        this.m.setGravity(17);
        this.m.setOnClickListener(listener);
        if (a()) {
            this.m.setBackgroundResource(17170445);
        }
        return this;
    }

    public H5Dialog setNegativeButton(String text, OnClickListener listener) {
        this.l = new Button(this.b);
        this.k = new LayoutParams(-2, -2);
        this.l.setLayoutParams(this.k);
        this.l.setBackgroundResource(R.drawable.h5_dialog_button);
        this.l.setText(text);
        this.l.setTextColor(Color.argb(222, 0, 0, 0));
        this.l.setTextSize(14.0f);
        this.l.setGravity(17);
        this.l.setOnClickListener(listener);
        if (a()) {
            this.l.setBackgroundResource(17170445);
        }
        return this;
    }

    public H5Dialog setCanceledOnTouchOutside(boolean cancel) {
        this.a = cancel;
        if (this.d != null) {
            this.d.setCanceledOnTouchOutside(this.a);
        }
        return this;
    }

    public H5Dialog setOnDismissListener(OnDismissListener onDismissListener) {
        this.r = onDismissListener;
        return this;
    }
}
