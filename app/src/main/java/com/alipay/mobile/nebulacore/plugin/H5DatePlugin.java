package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.common.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class H5DatePlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.DATE_PICKER);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (CommonEvents.DATE_PICKER.equals(event.getAction())) {
            a(event, context);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext h5BridgeContext) {
        JSONObject param = event.getParam();
        int mode = H5Utils.getInt(param, (String) Constants.KEY_MODE);
        String minDateStr = H5Utils.getString(param, (String) "minDate", (String) "");
        String maxDateStr = H5Utils.getString(param, (String) "maxDate", (String) "");
        String beginDateStr = H5Utils.getString(param, (String) "beginDate");
        boolean isIDCard = H5Utils.getBoolean(param, (String) "isIDCard", false);
        String format = null;
        if (mode == 0) {
            format = "HH:mm:ss";
        } else if (mode == 1) {
            format = "yyyy-MM-dd";
        } else if (mode == 2) {
            format = "yyyy-MM-dd HH:mm:ss";
        } else if (mode == 3) {
            format = "yyyy-MM";
        } else if (mode == 4) {
            format = "yyyy";
        }
        Calendar minDate = b(minDateStr, format);
        Calendar maxDate = b(maxDateStr, format);
        if (minDate == null || maxDate == null || minDate.getTimeInMillis() <= maxDate.getTimeInMillis()) {
            Calendar beginDate = b(beginDateStr, format);
            if (beginDate == null) {
                beginDate = Calendar.getInstance();
            }
            JSONObject result = new JSONObject();
            if (mode == 0) {
                a(event, beginDate, minDate, maxDate, h5BridgeContext, isIDCard, result);
            } else {
                a(event, beginDate, minDate, maxDate, isIDCard, mode == 2, h5BridgeContext, result, mode);
            }
        } else {
            H5Log.e((String) "H5DatePlugin", (String) "min date should less than max date!");
            h5BridgeContext.sendError(2, (String) "min date should less than max date!");
        }
    }

    private void a(H5Event event, Calendar beginDate, Calendar minDate, Calendar maxDate, boolean isCard, boolean isShowTimeDialog, H5BridgeContext h5BridgeContext, JSONObject result, int mode) {
        Activity activity = event.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final Calendar calendar = minDate;
            final Calendar calendar2 = maxDate;
            final boolean z = isShowTimeDialog;
            final JSONObject jSONObject = result;
            final H5Event h5Event = event;
            final Calendar calendar3 = beginDate;
            final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
            final boolean z2 = isCard;
            final int i = mode;
            AnonymousClass1 r4 = new OnDateSetListener() {
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    if (!(calendar == null || calendar2 == null)) {
                        if (calendar2.get(1) <= year) {
                            year = calendar2.get(1);
                        } else if (calendar.get(1) >= year) {
                            year = calendar.get(1);
                        }
                    }
                    String monthStr = month + 1 < 10 ? "0" + (month + 1) : (month + 1);
                    String dayStr = day < 10 ? "0" + day : String.valueOf(day);
                    if (z) {
                        jSONObject.put((String) "date", (Object) year + "/" + monthStr + "/" + dayStr + Token.SEPARATOR);
                        H5DatePlugin.this.a(h5Event, calendar3, calendar, calendar2, h5BridgeContext2, z2, jSONObject);
                        return;
                    }
                    if (i == 4) {
                        jSONObject.put((String) "date", (Object) String.valueOf(year));
                    } else if (i == 3) {
                        jSONObject.put((String) "date", (Object) year + "-" + monthStr);
                    } else {
                        jSONObject.put((String) "date", (Object) year + "-" + monthStr + "-" + dayStr);
                    }
                    h5BridgeContext2.sendBridgeResult(jSONObject);
                }
            };
            AnonymousClass2 r5 = new DatePickerDialog(activity, r4, beginDate.get(1), beginDate.get(2), beginDate.get(5)) {
                /* access modifiers changed from: protected */
                public void onStop() {
                }
            };
            r5.setTitle(H5Environment.getResources().getString(R.string.h5_choosedate));
            r5.setCancelable(false);
            final H5BridgeContext h5BridgeContext3 = h5BridgeContext;
            r5.setButton(-2, H5Environment.getResources().getString(R.string.h5_datecancel), new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    h5BridgeContext3.sendBridgeResult("error", "11");
                    dialog.dismiss();
                }
            });
            if (isCard) {
                final JSONObject jSONObject2 = result;
                final H5BridgeContext h5BridgeContext4 = h5BridgeContext;
                r5.setButton(-3, H5Environment.getResources().getString(R.string.h5_datevalid), new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        jSONObject2.put((String) "date", (Object) H5Environment.getResources().getString(R.string.h5_longterm));
                        h5BridgeContext4.sendBridgeResult(jSONObject2);
                        dialog.dismiss();
                    }
                });
            }
            try {
                DatePicker datePicker = r5.getDatePicker();
                if (minDate != null) {
                    datePicker.setMinDate(minDate.getTimeInMillis());
                }
                if (maxDate != null) {
                    datePicker.setMaxDate(maxDate.getTimeInMillis());
                }
            } catch (Throwable t) {
                H5Log.e("H5DatePlugin", "set min or max date exception.", t);
            }
            try {
                DatePicker datePicker2 = r5.getDatePicker();
                if (mode == 3) {
                    datePicker2.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(8);
                } else if (mode == 4) {
                    datePicker2.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(8);
                    datePicker2.findViewById(Resources.getSystem().getIdentifier("month", "id", "android")).setVisibility(8);
                }
            } catch (Exception e) {
                H5Log.e("H5DatePlugin", "set date mode exception", e);
            }
            try {
                r5.show();
            } catch (Exception e2) {
                H5Log.e("H5DatePlugin", "show date dialog exception.", e2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, Calendar beginDate, Calendar minDate, Calendar maxDate, H5BridgeContext h5BridgeContext, boolean idCard, JSONObject result) {
        Activity activity = event.getActivity();
        if (activity != null && !activity.isFinishing()) {
            final JSONObject jSONObject = result;
            final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
            final H5Event h5Event = event;
            final Calendar calendar = minDate;
            final Calendar calendar2 = maxDate;
            AnonymousClass5 r2 = new OnTimeSetListener() {
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String date = jSONObject.getString("date");
                    if (TextUtils.isEmpty(date)) {
                        date = "";
                    }
                    StringBuilder builder = new StringBuilder(date);
                    builder.append(hour + ":" + minute + ":00");
                    Calendar selectedDate = H5DatePlugin.b(builder.toString(), TextUtils.isEmpty(date) ? "HH:mm:ss" : "yyyy/MM/dd HH:mm:ss");
                    if (selectedDate != null || h5BridgeContext2 == null) {
                        if (calendar != null && calendar.getTimeInMillis() > selectedDate.getTimeInMillis()) {
                            H5Log.d("H5DatePlugin", "set selected date as min date");
                            selectedDate = calendar;
                        }
                        if (calendar2 != null && calendar2.getTimeInMillis() < selectedDate.getTimeInMillis()) {
                            H5Log.d("H5DatePlugin", "set selected date as max date");
                            selectedDate = calendar2;
                        }
                        if (selectedDate != null || h5BridgeContext2 == null) {
                            StringBuilder dateResult = new StringBuilder();
                            if (!TextUtils.isEmpty(date)) {
                                int year = selectedDate.get(1);
                                int month = selectedDate.get(2);
                                int day = selectedDate.get(5);
                                dateResult.append(year + "/" + (month + 1 < 10 ? "0" + (month + 1) : (month + 1)) + "/" + (day < 10 ? "0" + day : String.valueOf(day)) + Token.SEPARATOR);
                            }
                            int hour2 = selectedDate.get(11);
                            int minute2 = selectedDate.get(12);
                            dateResult.append((hour2 < 10 ? "0" + hour2 : String.valueOf(hour2)) + ":" + (minute2 < 10 ? "0" + minute2 : String.valueOf(minute2)) + ":00");
                            jSONObject.put((String) "date", (Object) dateResult.toString());
                            H5Log.e((String) "H5DatePlugin", builder.toString());
                            h5BridgeContext2.sendBridgeResult(jSONObject);
                            return;
                        }
                        h5BridgeContext2.sendError(h5Event, Error.INVALID_PARAM);
                        return;
                    }
                    h5BridgeContext2.sendError(h5Event, Error.INVALID_PARAM);
                }
            };
            AnonymousClass6 r3 = new TimePickerDialog(activity, r2, beginDate.get(11), beginDate.get(12)) {
                /* access modifiers changed from: protected */
                public void onStop() {
                }
            };
            r3.setTitle(H5Environment.getResources().getString(R.string.h5_choosetime));
            r3.setCancelable(false);
            final H5BridgeContext h5BridgeContext3 = h5BridgeContext;
            r3.setButton(-2, H5Environment.getResources().getString(R.string.h5_datecancel), new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    h5BridgeContext3.sendBridgeResult("error", "11");
                    dialog.dismiss();
                }
            });
            if (idCard) {
                final JSONObject jSONObject2 = result;
                final H5BridgeContext h5BridgeContext4 = h5BridgeContext;
                r3.setButton(-3, H5Environment.getResources().getString(R.string.h5_datevalid), new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        jSONObject2.put((String) "date", (Object) H5Environment.getResources().getString(R.string.h5_longterm));
                        h5BridgeContext4.sendBridgeResult(jSONObject2);
                        dialog.dismiss();
                    }
                });
            }
            try {
                r3.show();
            } catch (Exception e) {
                H5Log.e("H5DatePlugin", "show time dialog exception.", e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static Calendar b(String dateStr, String format) {
        if (TextUtils.isEmpty(dateStr) || TextUtils.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(dateStr));
            return calendar;
        } catch (ParseException e) {
            H5Log.e("H5DatePlugin", "exception detail", e);
            return null;
        }
    }
}
