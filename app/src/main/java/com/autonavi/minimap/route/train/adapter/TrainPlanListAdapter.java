package com.autonavi.minimap.route.train.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class TrainPlanListAdapter extends BaseAdapter {
    public static final int SORTTYPE_DEPARTURE_TIME = 2;
    public static final int SORTTYPE_TIME_SHORT = 1;
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TrainPlanBaseInfoItem> mOriginalTrainList;
    private ArrayList<TrainPlanBaseInfoItem> mSortedAndFilteredTrainList;
    private a mTrainListSortFilterCondition = new a();
    private eiv mTrainPlanFilterHelper = new eiv(this.mTrainListSortFilterCondition);

    public static class a {
        public int a = 2;
        public boolean b = false;
        public boolean c = false;
        public boolean[] d = {true, false, false, false, false};
        public boolean[] e = {true, false, false, false, false};
        public boolean[] f = {true, false, false, false, false};
    }

    static class b {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView e;
        public TextView f;
        public TextView g;
        public TextView h;
        public TextView i;
        public TextView j;
        public TextView k;
        public TextView l;
        public TextView m;
        public TextView n;
        public TextView o;
        public TextView p;

        private b() {
        }

        /* synthetic */ b(byte b2) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public TrainPlanListAdapter(Context context, ArrayList<TrainPlanBaseInfoItem> arrayList) {
        this.mOriginalTrainList = arrayList;
        this.mSortedAndFilteredTrainList = arrayList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mTrainPlanFilterHelper = new eiv(this.mTrainListSortFilterCondition);
    }

    public int getCount() {
        if (this.mSortedAndFilteredTrainList == null) {
            return 0;
        }
        return this.mSortedAndFilteredTrainList.size();
    }

    public TrainPlanBaseInfoItem getItem(int i) {
        if (this.mSortedAndFilteredTrainList == null || this.mSortedAndFilteredTrainList.size() == 0) {
            return null;
        }
        return this.mSortedAndFilteredTrainList.get(i - 1);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        b bVar;
        if (view == null) {
            bVar = new b(0);
            view2 = this.mInflater.inflate(R.layout.train_info_list_item_new, null);
            bVar.a = (TextView) view2.findViewById(R.id.train_departure_time);
            bVar.b = (TextView) view2.findViewById(R.id.train_arrival_time);
            bVar.c = (TextView) view2.findViewById(R.id.train_departure_station);
            bVar.d = (TextView) view2.findViewById(R.id.train_arrival_station);
            bVar.e = (TextView) view2.findViewById(R.id.train_name);
            bVar.f = (TextView) view2.findViewById(R.id.train_running_time);
            bVar.g = (TextView) view2.findViewById(R.id.train_min_ticket_price);
            bVar.h = (TextView) view2.findViewById(R.id.train_cheapest_ticket_remain_mount);
            bVar.i = (TextView) view2.findViewById(R.id.train_seat_info_0);
            bVar.j = (TextView) view2.findViewById(R.id.train_seat_info_1);
            bVar.k = (TextView) view2.findViewById(R.id.train_seat_info_2);
            bVar.l = (TextView) view2.findViewById(R.id.train_seat_info_3);
            bVar.m = (TextView) view2.findViewById(R.id.train_seat_info_4);
            bVar.n = (TextView) view2.findViewById(R.id.train_seat_info_5);
            bVar.o = (TextView) view2.findViewById(R.id.train_seat_info_6);
            bVar.p = (TextView) view2.findViewById(R.id.train_seat_info_7);
            view2.setTag(bVar);
        } else {
            view2 = view;
            bVar = (b) view.getTag();
        }
        dataSet(bVar, i);
        return view2;
    }

    public void sort(int i) {
        switch (i) {
            case 1:
                this.mTrainListSortFilterCondition.a = 1;
                ArrayList<TrainPlanBaseInfoItem> arrayList = this.mSortedAndFilteredTrainList;
                if (arrayList == null || arrayList.size() == 0) {
                    arrayList = null;
                } else {
                    Collections.sort(arrayList, new Comparator<TrainPlanBaseInfoItem>() {
                        public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
                            return ((TrainPlanBaseInfoItem) obj).trainRunningTime - ((TrainPlanBaseInfoItem) obj2).trainRunningTime;
                        }
                    });
                }
                this.mSortedAndFilteredTrainList = arrayList;
                break;
            case 2:
                this.mTrainListSortFilterCondition.a = 2;
                ArrayList<TrainPlanBaseInfoItem> arrayList2 = this.mSortedAndFilteredTrainList;
                if (arrayList2 == null || arrayList2.size() == 0) {
                    arrayList2 = null;
                } else {
                    Collections.sort(arrayList2, new Comparator<TrainPlanBaseInfoItem>() {
                        public final /* synthetic */ int compare(Object obj, Object obj2) {
                            return eax.a(((TrainPlanBaseInfoItem) obj).trainDepartureTimeToCompare, ((TrainPlanBaseInfoItem) obj2).trainDepartureTimeToCompare);
                        }
                    });
                }
                this.mSortedAndFilteredTrainList = arrayList2;
                break;
        }
        notifyDataSetChanged();
    }

    public void filter() {
        this.mSortedAndFilteredTrainList = this.mOriginalTrainList;
        if (this.mTrainListSortFilterCondition.c) {
            ArrayList<TrainPlanBaseInfoItem> arrayList = this.mOriginalTrainList;
            ArrayList<TrainPlanBaseInfoItem> arrayList2 = new ArrayList<>();
            if (!(arrayList == null || arrayList.size() == 0)) {
                Iterator<TrainPlanBaseInfoItem> it = arrayList.iterator();
                while (it.hasNext()) {
                    TrainPlanBaseInfoItem next = it.next();
                    if (next.trainTicketRemainOfAllSeatType == -1 || next.trainTicketRemainOfAllSeatType > 0) {
                        arrayList2.add(next);
                    }
                }
            }
            this.mSortedAndFilteredTrainList = arrayList2;
        }
        if (this.mTrainListSortFilterCondition.b) {
            this.mSortedAndFilteredTrainList = eju.a(this.mSortedAndFilteredTrainList);
        }
        this.mSortedAndFilteredTrainList = eju.a(this.mSortedAndFilteredTrainList, this.mTrainPlanFilterHelper);
        notifyDataSetChanged();
    }

    public void setOriginalTrainList(ArrayList<TrainPlanBaseInfoItem> arrayList) {
        this.mOriginalTrainList = arrayList;
        this.mSortedAndFilteredTrainList = arrayList;
        if (this.mSortedAndFilteredTrainList != null) {
            filter();
            sort(this.mTrainListSortFilterCondition.a);
            notifyDataSetChanged();
        }
    }

    public void setFilterCondition(a aVar) {
        this.mTrainListSortFilterCondition = aVar;
    }

    public a getFilterCondition() {
        return this.mTrainListSortFilterCondition;
    }

    public void refreshList() {
        this.mTrainPlanFilterHelper.a(this.mTrainListSortFilterCondition);
        filter();
        sort(this.mTrainListSortFilterCondition.a);
    }

    public void refreshFilterCondition() {
        this.mTrainListSortFilterCondition = new a();
        this.mTrainPlanFilterHelper.a(this.mTrainListSortFilterCondition);
        filter();
        sort(this.mTrainListSortFilterCondition.a);
    }

    public int getOriginalTrainListSize() {
        if (this.mOriginalTrainList == null || this.mOriginalTrainList.size() <= 0) {
            return 0;
        }
        return this.mOriginalTrainList.size();
    }

    private boolean dataSet(b bVar, int i) {
        if (bVar == null || this.mSortedAndFilteredTrainList == null || i > this.mSortedAndFilteredTrainList.size() - 1) {
            return false;
        }
        TrainPlanBaseInfoItem trainPlanBaseInfoItem = this.mSortedAndFilteredTrainList.get(i);
        bVar.a.setText(trainPlanBaseInfoItem.trainDepartureTime);
        bVar.b.setText(trainPlanBaseInfoItem.getFormatedArrivalTime());
        TextView textView = bVar.c;
        StringBuilder sb = new StringBuilder();
        sb.append(trainPlanBaseInfoItem.trainDepartureName);
        sb.append("站");
        textView.setText(sb.toString());
        TextView textView2 = bVar.d;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(trainPlanBaseInfoItem.trainArrivalName);
        sb2.append("站");
        textView2.setText(sb2.toString());
        bVar.e.setText(trainPlanBaseInfoItem.trip);
        bVar.f.setText(trainPlanBaseInfoItem.getFormatRunningTime(this.mContext));
        bVar.g.setText(trainPlanBaseInfoItem.getMinPriceTip(this.mContext));
        bVar.h.setText(trainPlanBaseInfoItem.getAlltypeSeatItemDescription(this.mContext));
        int seatTypeSize = trainPlanBaseInfoItem.getSeatTypeSize();
        bVar.i.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 0));
        bVar.j.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 1));
        bVar.k.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 2));
        bVar.l.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 3));
        if (seatTypeSize > 4) {
            bVar.m.setVisibility(0);
            bVar.n.setVisibility(0);
            bVar.o.setVisibility(0);
            bVar.p.setVisibility(0);
        } else {
            bVar.m.setVisibility(8);
            bVar.n.setVisibility(8);
            bVar.o.setVisibility(8);
            bVar.p.setVisibility(8);
        }
        if (seatTypeSize > 4) {
            bVar.m.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 4));
        }
        if (seatTypeSize > 5) {
            bVar.n.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 5));
        } else if (seatTypeSize > 4) {
            bVar.n.setVisibility(4);
        }
        if (seatTypeSize > 6) {
            bVar.o.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 6));
        } else if (seatTypeSize > 4) {
            bVar.o.setVisibility(4);
        }
        if (seatTypeSize > 7) {
            bVar.p.setText(trainPlanBaseInfoItem.getLeveledSeatDescription(this.mContext, 7));
        } else if (seatTypeSize > 4) {
            bVar.p.setVisibility(4);
        }
        return true;
    }
}
