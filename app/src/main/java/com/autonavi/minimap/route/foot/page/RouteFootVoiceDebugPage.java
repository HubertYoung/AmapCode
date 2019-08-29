package com.autonavi.minimap.route.foot.page;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.ArrayList;

public class RouteFootVoiceDebugPage extends AbstractBasePage<ecr> implements OnClickListener {
    private ListView a;
    private ListView b;
    private Button c;
    private Button d;
    private Button e;
    /* access modifiers changed from: private */
    public LvTextAdapter f;
    private LvCodeAdapter g;
    /* access modifiers changed from: private */
    public View h;
    private View i;
    private View j;
    /* access modifiers changed from: private */
    public EditText k;
    /* access modifiers changed from: private */
    public String l = "请选择语音播报";
    /* access modifiers changed from: private */
    public int m = -1;

    class LvCodeAdapter extends BaseAdapter {
        private Context mContext;
        /* access modifiers changed from: private */
        public ArrayList<String> mList = new ArrayList<>();

        public long getItemId(int i) {
            return (long) i;
        }

        public LvCodeAdapter(Context context, ArrayList<String> arrayList) {
            this.mContext = context;
            this.mList = arrayList;
        }

        public int getCount() {
            return this.mList.size();
        }

        public Object getItem(int i) {
            return this.mList.get(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(this.mContext).inflate(R.layout.fg_voice_debug_item, null);
                aVar = new a();
                aVar.a = (TextView) view.findViewById(R.id.tv_code);
                aVar.b = (Button) view.findViewById(R.id.btn_play);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            aVar.a.setText(this.mList.get(i));
            aVar.b.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    PlaySoundUtils instance = PlaySoundUtils.getInstance();
                    StringBuilder sb = new StringBuilder();
                    sb.append(RouteFootVoiceDebugPage.this.l);
                    sb.append((String) LvCodeAdapter.this.mList.get(i));
                    instance.playSound(sb.toString());
                }
            });
            return view;
        }
    }

    static class LvTextAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> mList = new ArrayList<>();

        public long getItemId(int i) {
            return (long) i;
        }

        public LvTextAdapter(Context context) {
            this.mContext = context;
        }

        public void bindData(ArrayList<String> arrayList) {
            this.mList = arrayList;
        }

        public int getCount() {
            return this.mList.size();
        }

        public Object getItem(int i) {
            return this.mList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(this.mContext).inflate(R.layout.fg_voice_debug_item, null);
                aVar = new a();
                aVar.a = (TextView) view.findViewById(R.id.tv_code);
                aVar.b = (Button) view.findViewById(R.id.btn_play);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            aVar.b.setVisibility(8);
            aVar.a.setText(this.mList.get(i));
            return view;
        }
    }

    static class a {
        TextView a;
        Button b;

        a() {
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.fg_voice_debug);
        View contentView = getContentView();
        this.a = (ListView) contentView.findViewById(R.id.lv_voice_text);
        this.b = (ListView) contentView.findViewById(R.id.lv_voice_code);
        this.c = (Button) contentView.findViewById(R.id.btn_back);
        this.d = (Button) contentView.findViewById(R.id.btn_bus);
        this.e = (Button) contentView.findViewById(R.id.btn_foot);
        this.i = contentView.findViewById(R.id.line_bus);
        this.j = contentView.findViewById(R.id.line_foot);
        this.k = (EditText) contentView.findViewById(R.id.editText);
        this.c.setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.e.setOnClickListener(this);
        this.k.setOnClickListener(this);
        this.k.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                RouteFootVoiceDebugPage.this.a();
                return false;
            }
        });
        this.k.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                RouteFootVoiceDebugPage.this.l = charSequence.toString();
            }
        });
        this.f = new LvTextAdapter(getContext());
        this.f.bindData(ebb.d());
        this.a.setAdapter(this.f);
        this.f.notifyDataSetChanged();
        this.g = new LvCodeAdapter(getContext(), ebb.f());
        this.b.setAdapter(this.g);
        this.g.notifyDataSetChanged();
        this.a.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (RouteFootVoiceDebugPage.this.m != i) {
                    RouteFootVoiceDebugPage.this.k.clearFocus();
                    RouteFootVoiceDebugPage.this.k.setText("");
                    RouteFootVoiceDebugPage.this.l = (String) RouteFootVoiceDebugPage.this.f.getItem(i);
                    view.setBackgroundColor(RouteFootVoiceDebugPage.this.getResources().getColor(R.color.blue));
                    if (RouteFootVoiceDebugPage.this.h != null) {
                        RouteFootVoiceDebugPage.this.h.setBackgroundColor(RouteFootVoiceDebugPage.this.getResources().getColor(R.color.white));
                    }
                    RouteFootVoiceDebugPage.this.h = view;
                    RouteFootVoiceDebugPage.this.m = i;
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.h != null) {
            this.h.setBackgroundColor(getResources().getColor(R.color.white));
            this.h = null;
        }
        this.l = "请选择语音播报";
        this.m = -1;
    }

    public void onClick(View view) {
        a();
        int id = view.getId();
        if (id == R.id.btn_back) {
            finish();
        } else if (id == R.id.btn_bus) {
            this.f.bindData(ebb.d());
            this.f.notifyDataSetChanged();
            this.j.setVisibility(0);
            this.i.setVisibility(0);
            this.i.setBackgroundColor(getResources().getColor(R.color.blue));
            this.j.setBackgroundColor(getResources().getColor(R.color.white));
            this.e.setBackgroundColor(getResources().getColor(R.color.white));
            this.d.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (id == R.id.btn_foot) {
            this.f.bindData(ebb.e());
            this.f.notifyDataSetChanged();
            this.j.setVisibility(0);
            this.i.setVisibility(0);
            this.i.setBackgroundColor(getResources().getColor(R.color.white));
            this.j.setBackgroundColor(getResources().getColor(R.color.blue));
            this.e.setBackgroundColor(getResources().getColor(R.color.white));
            this.d.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            if (id == R.id.editText) {
                this.f.notifyDataSetChanged();
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ecr(this);
    }
}
