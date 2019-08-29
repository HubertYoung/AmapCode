package com.squareup.leakcanary.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@TargetApi(11)
public final class DisplayLeakActivity extends Activity {
    private static final String SHOW_LEAK_EXTRA = "show_latest";
    private static final String TAG = "DisplayLeakActivity";
    private Button actionButton;
    private TextView failureView;
    /* access modifiers changed from: private */
    public List<Leak> leaks;
    private ListView listView;
    /* access modifiers changed from: private */
    public int maxStoredLeaks;
    /* access modifiers changed from: private */
    public String visibleLeakRefKey;

    static class Leak {
        final HeapDump heapDump;
        final AnalysisResult result;

        Leak(HeapDump heapDump2, AnalysisResult analysisResult) {
            this.heapDump = heapDump2;
            this.result = analysisResult;
        }
    }

    class LeakListAdapter extends BaseAdapter {
        public long getItemId(int i) {
            return (long) i;
        }

        LeakListAdapter() {
        }

        public int getCount() {
            return DisplayLeakActivity.this.leaks.size();
        }

        public Leak getItem(int i) {
            return (Leak) DisplayLeakActivity.this.leaks.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            String str;
            String str2;
            if (view == null) {
                view = LayoutInflater.from(DisplayLeakActivity.this).inflate(R.layout.__leak_canary_leak_row, viewGroup, false);
            }
            TextView textView = (TextView) view.findViewById(R.id.__leak_canary_row_text);
            TextView textView2 = (TextView) view.findViewById(R.id.__leak_canary_row_time);
            Leak item = getItem(i);
            if (i == 0 && DisplayLeakActivity.this.leaks.size() == DisplayLeakActivity.this.maxStoredLeaks) {
                str = "MAX. ";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(DisplayLeakActivity.this.leaks.size() - i);
                sb.append(". ");
                str = sb.toString();
            }
            if (item.result.failure == null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(DisplayLeakActivity.this.getString(R.string.__leak_canary_class_has_leaked, new Object[]{DisplayLeakActivity.classSimpleName(item.result.className)}));
                str2 = sb2.toString();
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(item.result.failure.getClass().getSimpleName());
                sb3.append(Token.SEPARATOR);
                sb3.append(item.result.failure.getMessage());
                str2 = sb3.toString();
            }
            textView.setText(str2);
            textView2.setText(DateUtils.formatDateTime(DisplayLeakActivity.this, item.heapDump.heapDumpFile.lastModified(), 17));
            return view;
        }
    }

    static class LoadLeaks implements Runnable {
        static final Executor backgroundExecutor = Executors.newSingleThreadExecutor();
        static final List<LoadLeaks> inFlight = new ArrayList();
        /* access modifiers changed from: private */
        public DisplayLeakActivity activityOrNull;
        private final File leakDirectory = LeakCanaryInternals.detectedLeakDirectory();
        private final Handler mainHandler = new Handler(Looper.getMainLooper());

        static void load(DisplayLeakActivity displayLeakActivity) {
            LoadLeaks loadLeaks = new LoadLeaks(displayLeakActivity);
            inFlight.add(loadLeaks);
            backgroundExecutor.execute(loadLeaks);
        }

        static void forgetActivity() {
            for (LoadLeaks loadLeaks : inFlight) {
                loadLeaks.activityOrNull = null;
            }
            inFlight.clear();
        }

        LoadLeaks(DisplayLeakActivity displayLeakActivity) {
            this.activityOrNull = displayLeakActivity;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(6:13|12|16|17|(0)(0)|21) */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0043, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0044, code lost:
            r7 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            r6.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r7.close();
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0046 */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0058 A[SYNTHETIC, Splitter:B:19:0x0058] */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0060 A[SYNTHETIC, Splitter:B:24:0x0060] */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x005b A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r10 = this;
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                java.io.File r1 = r10.leakDirectory
                com.squareup.leakcanary.internal.DisplayLeakActivity$LoadLeaks$1 r2 = new com.squareup.leakcanary.internal.DisplayLeakActivity$LoadLeaks$1
                r2.<init>()
                java.io.File[] r1 = r1.listFiles(r2)
                if (r1 == 0) goto L_0x006c
                int r2 = r1.length
                r3 = 0
            L_0x0014:
                if (r3 >= r2) goto L_0x0064
                r4 = r1[r3]
                java.io.File r5 = com.squareup.leakcanary.internal.LeakCanaryInternals.leakResultFile(r4)
                r6 = 0
                java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ IOException | ClassNotFoundException -> 0x0046 }
                r7.<init>(r5)     // Catch:{ IOException | ClassNotFoundException -> 0x0046 }
                java.io.ObjectInputStream r6 = new java.io.ObjectInputStream     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                r6.<init>(r7)     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                java.lang.Object r8 = r6.readObject()     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                com.squareup.leakcanary.HeapDump r8 = (com.squareup.leakcanary.HeapDump) r8     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                java.lang.Object r6 = r6.readObject()     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                com.squareup.leakcanary.AnalysisResult r6 = (com.squareup.leakcanary.AnalysisResult) r6     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                com.squareup.leakcanary.internal.DisplayLeakActivity$Leak r9 = new com.squareup.leakcanary.internal.DisplayLeakActivity$Leak     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                r9.<init>(r8, r6)     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                r0.add(r9)     // Catch:{ IOException | ClassNotFoundException -> 0x0041, all -> 0x003f }
                r7.close()     // Catch:{ IOException -> 0x005b }
                goto L_0x005b
            L_0x003f:
                r0 = move-exception
                goto L_0x005e
            L_0x0041:
                r6 = r7
                goto L_0x0046
            L_0x0043:
                r0 = move-exception
                r7 = r6
                goto L_0x005e
            L_0x0046:
                r4.delete()     // Catch:{ all -> 0x0043 }
                r5.delete()     // Catch:{ all -> 0x0043 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0043 }
                java.lang.String r7 = "Could not read result file, deleted result and heap dump:"
                r5.<init>(r7)     // Catch:{ all -> 0x0043 }
                r5.append(r4)     // Catch:{ all -> 0x0043 }
                if (r6 == 0) goto L_0x005b
                r6.close()     // Catch:{ IOException -> 0x005b }
            L_0x005b:
                int r3 = r3 + 1
                goto L_0x0014
            L_0x005e:
                if (r7 == 0) goto L_0x0063
                r7.close()     // Catch:{ IOException -> 0x0063 }
            L_0x0063:
                throw r0
            L_0x0064:
                com.squareup.leakcanary.internal.DisplayLeakActivity$LoadLeaks$2 r1 = new com.squareup.leakcanary.internal.DisplayLeakActivity$LoadLeaks$2
                r1.<init>()
                java.util.Collections.sort(r0, r1)
            L_0x006c:
                android.os.Handler r1 = r10.mainHandler
                com.squareup.leakcanary.internal.DisplayLeakActivity$LoadLeaks$3 r2 = new com.squareup.leakcanary.internal.DisplayLeakActivity$LoadLeaks$3
                r2.<init>(r0)
                r1.post(r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.leakcanary.internal.DisplayLeakActivity.LoadLeaks.run():void");
        }
    }

    public static PendingIntent createPendingIntent(Context context, String str) {
        Intent intent = new Intent(context, DisplayLeakActivity.class);
        intent.putExtra(SHOW_LEAK_EXTRA, str);
        intent.setFlags(335544320);
        return PendingIntent.getActivity(context, 1, intent, 134217728);
    }

    /* access modifiers changed from: protected */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.visibleLeakRefKey = bundle.getString("visibleLeakRefKey");
        } else {
            Intent intent = getIntent();
            if (intent.hasExtra(SHOW_LEAK_EXTRA)) {
                this.visibleLeakRefKey = intent.getStringExtra(SHOW_LEAK_EXTRA);
            }
        }
        this.leaks = (List) getLastNonConfigurationInstance();
        setContentView(R.layout.__leak_canary_display_leak);
        this.listView = (ListView) findViewById(R.id.__leak_canary_display_leak_list);
        this.failureView = (TextView) findViewById(R.id.__leak_canary_display_leak_failure);
        this.actionButton = (Button) findViewById(R.id.__leak_canary_action);
        this.maxStoredLeaks = getResources().getInteger(R.integer.__leak_canary_max_stored_leaks);
        updateUi();
    }

    public final Object onRetainNonConfigurationInstance() {
        return this.leaks;
    }

    /* access modifiers changed from: protected */
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("visibleLeakRefKey", this.visibleLeakRefKey);
    }

    /* access modifiers changed from: protected */
    public final void onResume() {
        super.onResume();
        LoadLeaks.load(this);
    }

    /* access modifiers changed from: protected */
    public final void onDestroy() {
        super.onDestroy();
        LoadLeaks.forgetActivity();
    }

    public final boolean onCreateOptionsMenu(Menu menu) {
        if (getVisibleLeak() == null) {
            return false;
        }
        menu.add(R.string.__leak_canary_share_leak).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                DisplayLeakActivity.this.shareLeak();
                return true;
            }
        });
        menu.add(R.string.__leak_canary_share_heap_dump).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                DisplayLeakActivity.this.shareHeapDump();
                return true;
            }
        });
        return true;
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.visibleLeakRefKey = null;
            updateUi();
        }
        return true;
    }

    public final void onBackPressed() {
        if (this.visibleLeakRefKey != null) {
            this.visibleLeakRefKey = null;
            updateUi();
            return;
        }
        super.onBackPressed();
    }

    /* access modifiers changed from: private */
    public void shareLeak() {
        Leak visibleLeak = getVisibleLeak();
        String leakInfo = LeakCanary.leakInfo(this, visibleLeak.heapDump, visibleLeak.result, true);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", leakInfo);
        startActivity(Intent.createChooser(intent, getString(R.string.__leak_canary_share_with)));
    }

    /* access modifiers changed from: private */
    public void shareHeapDump() {
        File file = getVisibleLeak().heapDump.heapDumpFile;
        file.setReadable(true, false);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(FilePart.DEFAULT_CONTENT_TYPE);
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
        startActivity(Intent.createChooser(intent, getString(R.string.__leak_canary_share_with)));
    }

    /* access modifiers changed from: private */
    public void updateUi() {
        final DisplayLeakAdapter displayLeakAdapter;
        if (this.leaks == null) {
            setTitle("Loading leaks...");
            return;
        }
        if (this.leaks.isEmpty()) {
            this.visibleLeakRefKey = null;
        }
        Leak visibleLeak = getVisibleLeak();
        if (visibleLeak == null) {
            this.visibleLeakRefKey = null;
        }
        ListAdapter adapter = this.listView.getAdapter();
        int i = 0;
        this.listView.setVisibility(0);
        this.failureView.setVisibility(8);
        if (visibleLeak != null) {
            AnalysisResult analysisResult = visibleLeak.result;
            if (analysisResult.failure != null) {
                this.listView.setVisibility(8);
                this.failureView.setVisibility(0);
                TextView textView = this.failureView;
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.__leak_canary_failure_report));
                sb.append(Log.getStackTraceString(analysisResult.failure));
                textView.setText(sb.toString());
                setTitle(R.string.__leak_canary_analysis_failed);
                invalidateOptionsMenu();
                getActionBar().setDisplayHomeAsUpEnabled(true);
                this.actionButton.setVisibility(0);
                this.actionButton.setText(R.string.__leak_canary_delete);
                this.listView.setAdapter(null);
                return;
            }
            if (adapter instanceof DisplayLeakAdapter) {
                displayLeakAdapter = (DisplayLeakAdapter) adapter;
            } else {
                displayLeakAdapter = new DisplayLeakAdapter();
                this.listView.setAdapter(displayLeakAdapter);
                this.listView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        displayLeakAdapter.toggleRow(i);
                    }
                });
                invalidateOptionsMenu();
                getActionBar().setDisplayHomeAsUpEnabled(true);
                this.actionButton.setVisibility(0);
                this.actionButton.setText(R.string.__leak_canary_delete);
                this.actionButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Leak access$200 = DisplayLeakActivity.this.getVisibleLeak();
                        LeakCanaryInternals.leakResultFile(access$200.heapDump.heapDumpFile).delete();
                        access$200.heapDump.heapDumpFile.delete();
                        DisplayLeakActivity.this.visibleLeakRefKey = null;
                        DisplayLeakActivity.this.leaks.remove(access$200);
                        DisplayLeakActivity.this.updateUi();
                    }
                });
            }
            HeapDump heapDump = visibleLeak.heapDump;
            displayLeakAdapter.update(analysisResult.leakTrace, heapDump.referenceKey, heapDump.referenceName);
            setTitle(getString(R.string.__leak_canary_class_has_leaked, new Object[]{classSimpleName(analysisResult.className)}));
            return;
        }
        if (adapter instanceof LeakListAdapter) {
            ((LeakListAdapter) adapter).notifyDataSetChanged();
        } else {
            this.listView.setAdapter(new LeakListAdapter());
            this.listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    DisplayLeakActivity.this.visibleLeakRefKey = ((Leak) DisplayLeakActivity.this.leaks.get(i)).heapDump.referenceKey;
                    DisplayLeakActivity.this.updateUi();
                }
            });
            invalidateOptionsMenu();
            setTitle(getString(R.string.__leak_canary_leak_list_title, new Object[]{getPackageName()}));
            getActionBar().setDisplayHomeAsUpEnabled(false);
            this.actionButton.setText(R.string.__leak_canary_delete_all);
            this.actionButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    File[] listFiles = LeakCanaryInternals.detectedLeakDirectory().listFiles();
                    if (listFiles != null) {
                        for (File delete : listFiles) {
                            delete.delete();
                        }
                    }
                    DisplayLeakActivity.this.leaks = Collections.emptyList();
                    DisplayLeakActivity.this.updateUi();
                }
            });
        }
        Button button = this.actionButton;
        if (this.leaks.size() == 0) {
            i = 8;
        }
        button.setVisibility(i);
    }

    /* access modifiers changed from: private */
    public Leak getVisibleLeak() {
        if (this.leaks == null) {
            return null;
        }
        for (Leak next : this.leaks) {
            if (next.heapDump.referenceKey.equals(this.visibleLeakRefKey)) {
                return next;
            }
        }
        return null;
    }

    static String classSimpleName(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return str;
        }
        return str.substring(lastIndexOf + 1);
    }
}
