package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyapp.R;
import java.util.ArrayList;
import java.util.Arrays;

public class H5PhoneContactAddPlugin extends H5SimplePlugin {
    static final String ADD_PHONE_CONTACT = "addPhoneContact";
    static final int MAX_IPC_SIZE = 262144;
    static final String TAG = H5PhoneContactAddPlugin.class.getSimpleName();

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ADD_PHONE_CONTACT);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (TextUtils.isEmpty(action)) {
            return false;
        }
        char c = 65535;
        switch (action.hashCode()) {
            case -1889072621:
                if (action.equals(ADD_PHONE_CONTACT)) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                addPhoneContact(event, context);
                return true;
            default:
                return false;
        }
    }

    static void addPhoneContact(final H5Event event, final H5BridgeContext context) {
        Activity a = event.getActivity();
        if (a != null) {
            AUListDialog d = new AUListDialog((Context) a, new ArrayList<>(Arrays.asList(new String[]{a.getString(R.string.h5_add_contact_create), a.getString(R.string.h5_add_contact_update)})));
            d.setOnItemClickListener((OnItemClickListener) new OnItemClickListener() {
                public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            H5PhoneContactAddPlugin.createContact(event, context);
                            return;
                        case 1:
                            H5PhoneContactAddPlugin.updateContact(event, context);
                            return;
                        default:
                            return;
                    }
                }
            });
            d.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface d) {
                    context.sendError(11, (String) "fail cancel");
                }
            });
            d.show();
        }
    }

    static Intent transferParamsIntoIntent(Activity a, Intent i, H5Event event) {
        JSONObject o = event.getParam();
        if (o != null) {
            ContentValues name = new ContentValues(8);
            name.put("mimetype", "vnd.android.cursor.item/name");
            name.put("data3", o.getString("lastName"));
            name.put("data5", o.getString("middleName"));
            name.put("data2", o.getString("firstName"));
            name.put("data1", getDisplayName(name));
            ContentValues imAlipay = new ContentValues(8);
            imAlipay.put("mimetype", "vnd.android.cursor.item/im");
            imAlipay.put("data2", Integer.valueOf(3));
            imAlipay.put("data1", o.getString("alipayAccount"));
            imAlipay.put("data5", Integer.valueOf(-1));
            imAlipay.put("data6", a.getString(a.getApplicationInfo().labelRes));
            ContentValues imWeChat = new ContentValues(8);
            imWeChat.put("mimetype", "vnd.android.cursor.item/im");
            imWeChat.put("data2", Integer.valueOf(3));
            imWeChat.put("data1", o.getString("weChatNumber"));
            imWeChat.put("data5", Integer.valueOf(-1));
            imWeChat.put("data6", a.getString(R.string.h5_add_contact_wechat));
            ContentValues photo = new ContentValues(4);
            photo.put("mimetype", "vnd.android.cursor.item/photo");
            photo.put("data15", filePathToByteArray(o.getString("photoFilePath")));
            ContentValues nickName = new ContentValues(4);
            nickName.put("mimetype", "vnd.android.cursor.item/nickname");
            nickName.put("data2", Integer.valueOf(1));
            nickName.put("data1", o.getString("nickName"));
            ContentValues contentValues = new ContentValues(4);
            contentValues.put("mimetype", "vnd.android.cursor.item/phone_v2");
            contentValues.put("data2", Integer.valueOf(3));
            contentValues.put("data1", o.getString("workPhoneNumber"));
            ContentValues contentValues2 = new ContentValues(4);
            contentValues2.put("mimetype", "vnd.android.cursor.item/phone_v2");
            contentValues2.put("data2", Integer.valueOf(4));
            contentValues2.put("data1", o.getString("workFaxNumber"));
            ContentValues homePhone = new ContentValues(4);
            homePhone.put("mimetype", "vnd.android.cursor.item/phone_v2");
            homePhone.put("data2", Integer.valueOf(1));
            homePhone.put("data1", o.getString("homePhoneNumber"));
            ContentValues homeFax = new ContentValues(4);
            homeFax.put("mimetype", "vnd.android.cursor.item/phone_v2");
            homeFax.put("data2", Integer.valueOf(5));
            homeFax.put("data1", o.getString("homeFaxNumber"));
            ContentValues companyPhone = new ContentValues(4);
            companyPhone.put("mimetype", "vnd.android.cursor.item/phone_v2");
            companyPhone.put("data2", Integer.valueOf(7));
            companyPhone.put("data1", o.getString("hostNumber"));
            ContentValues website = new ContentValues(4);
            website.put("mimetype", "vnd.android.cursor.item/website");
            website.put("data2", Integer.valueOf(7));
            website.put("data1", o.getString("url"));
            ContentValues mainPostal = new ContentValues(8);
            mainPostal.put("mimetype", "vnd.android.cursor.item/postal-address_v2");
            mainPostal.put("data2", Integer.valueOf(3));
            mainPostal.put("data10", o.getString("addressCountry"));
            mainPostal.put("data8", o.getString("addressState"));
            mainPostal.put("data7", o.getString("addressCity"));
            mainPostal.put("data4", o.getString("addressStreet"));
            mainPostal.put("data9", o.getString("addressPostalCode"));
            mainPostal.put("data1", getDisplayPostal(mainPostal));
            ContentValues workPostal = new ContentValues(8);
            workPostal.put("mimetype", "vnd.android.cursor.item/postal-address_v2");
            workPostal.put("data2", Integer.valueOf(2));
            workPostal.put("data10", o.getString("workAddressCountry"));
            workPostal.put("data8", o.getString("workAddressState"));
            workPostal.put("data7", o.getString("workAddressCity"));
            workPostal.put("data4", o.getString("workAddressStreet"));
            workPostal.put("data9", o.getString("workAddressPostalCode"));
            workPostal.put("data1", getDisplayPostal(workPostal));
            ContentValues homePostal = new ContentValues(8);
            homePostal.put("mimetype", "vnd.android.cursor.item/postal-address_v2");
            homePostal.put("data2", Integer.valueOf(1));
            homePostal.put("data10", o.getString("homeAddressCountry"));
            homePostal.put("data8", o.getString("homeAddressState"));
            homePostal.put("data7", o.getString("homeAddressCity"));
            homePostal.put("data4", o.getString("homeAddressStreet"));
            homePostal.put("data9", o.getString("homeAddressPostalCode"));
            homePostal.put("data1", getDisplayPostal(homePostal));
            ContentValues notes = new ContentValues(4);
            notes.put("mimetype", "vnd.android.cursor.item/note");
            notes.put("data1", o.getString("remark"));
            i.putParcelableArrayListExtra("data", new ArrayList(Arrays.asList(new ContentValues[]{name, imAlipay, imWeChat, photo, nickName, contentValues, contentValues2, homePhone, homeFax, companyPhone, website, mainPostal, workPostal, homePostal, notes})));
            i.putExtra("name", name.getAsString("data1"));
            i.putExtra("phone", o.getString("mobilePhoneNumber"));
            i.putExtra("company", o.getString("organization"));
            i.putExtra("job_title", o.getString("title"));
            i.putExtra(NotificationCompat.CATEGORY_EMAIL, o.getString(NotificationCompat.CATEGORY_EMAIL));
        }
        return i;
    }

    static String getDisplayPostal(ContentValues values) {
        String[] structuredData = {values.getAsString("data4"), values.getAsString("data7"), values.getAsString("data8"), values.getAsString("data9"), values.getAsString("data10")};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String elem = structuredData[i];
            if (!TextUtils.isEmpty(elem)) {
                builder.append(elem);
                if (i != 4) {
                    builder.append(10);
                }
            }
        }
        return builder.toString();
    }

    static String getDisplayName(ContentValues name) {
        String firstName = name.getAsString("data2");
        String middleName = name.getAsString("data5");
        String lastName = name.getAsString("data3");
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(firstName)) {
            sb.append(firstName);
        }
        if (!TextUtils.isEmpty(middleName)) {
            sb.append(' ').append(middleName);
        }
        if (!TextUtils.isEmpty(lastName)) {
            sb.append(' ').append(lastName);
        }
        return sb.toString();
    }

    static void createContact(H5Event event, H5BridgeContext context) {
        Activity a = event.getActivity();
        if (a != null) {
            Intent i = new Intent("android.intent.action.INSERT");
            i.setType("vnd.android.cursor.dir/contact");
            try {
                a.startActivity(transferParamsIntoIntent(a, i, event));
                context.sendSuccess();
            } catch (Exception e) {
                H5Log.e(TAG, (Throwable) e);
                context.sendError(3, "fail " + e);
            }
        }
    }

    static void updateContact(H5Event event, H5BridgeContext context) {
        Activity a = event.getActivity();
        if (a != null) {
            Intent i = new Intent("android.intent.action.INSERT_OR_EDIT");
            i.setType("vnd.android.cursor.item/contact");
            try {
                a.startActivity(transferParamsIntoIntent(a, i, event));
                context.sendSuccess();
            } catch (Exception e) {
                H5Log.e(TAG, (Throwable) e);
                context.sendError(3, "fail " + e);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002c A[SYNTHETIC, Splitter:B:16:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0063 A[SYNTHETIC, Splitter:B:31:0x0063] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static byte[] filePathToByteArray(java.lang.String r11) {
        /*
            r7 = 0
            boolean r8 = android.text.TextUtils.isEmpty(r11)
            if (r8 == 0) goto L_0x0009
            r1 = r7
        L_0x0008:
            return r1
        L_0x0009:
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0070 }
            r5.<init>(r11)     // Catch:{ IOException -> 0x0070 }
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            r6.<init>()     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            r8 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r8]     // Catch:{ IOException -> 0x0023, all -> 0x006d }
        L_0x0018:
            int r3 = r5.read(r0)     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            if (r3 < 0) goto L_0x0031
            r8 = 0
            r6.write(r0, r8, r3)     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            goto L_0x0018
        L_0x0023:
            r2 = move-exception
            r4 = r5
        L_0x0025:
            java.lang.String r8 = TAG     // Catch:{ all -> 0x0060 }
            com.alipay.mobile.nebula.util.H5Log.e(r8, r2)     // Catch:{ all -> 0x0060 }
            if (r4 == 0) goto L_0x002f
            r4.close()     // Catch:{ IOException -> 0x0069 }
        L_0x002f:
            r1 = r7
            goto L_0x0008
        L_0x0031:
            byte[] r1 = r6.toByteArray()     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            int r8 = r1.length     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            r9 = 262144(0x40000, float:3.67342E-40)
            if (r8 <= r9) goto L_0x005a
            java.lang.String r8 = TAG     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            java.lang.String r10 = "Photo file too large for an IPC transaction, ignoring. file size = "
            r9.<init>(r10)     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            int r10 = r1.length     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            java.lang.String r10 = ", max allowed = 262144"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            java.lang.String r9 = r9.toString()     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            com.alipay.mobile.nebula.util.H5Log.e(r8, r9)     // Catch:{ IOException -> 0x0023, all -> 0x006d }
            r5.close()     // Catch:{ IOException -> 0x0067 }
        L_0x0058:
            r1 = r7
            goto L_0x0008
        L_0x005a:
            r5.close()     // Catch:{ IOException -> 0x005e }
            goto L_0x0008
        L_0x005e:
            r7 = move-exception
            goto L_0x0008
        L_0x0060:
            r7 = move-exception
        L_0x0061:
            if (r4 == 0) goto L_0x0066
            r4.close()     // Catch:{ IOException -> 0x006b }
        L_0x0066:
            throw r7
        L_0x0067:
            r8 = move-exception
            goto L_0x0058
        L_0x0069:
            r8 = move-exception
            goto L_0x002f
        L_0x006b:
            r8 = move-exception
            goto L_0x0066
        L_0x006d:
            r7 = move-exception
            r4 = r5
            goto L_0x0061
        L_0x0070:
            r2 = move-exception
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.h5plugin.H5PhoneContactAddPlugin.filePathToByteArray(java.lang.String):byte[]");
    }
}
