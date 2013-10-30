package com.zljysoft.SmartDoctor.doctor.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-30
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */

public class CalendarEventAdapter extends BaseAdapter {
    LinkedList<String> mSelectedDayEvents;
    Context mContext;
    public CalendarEventAdapter(Context mContext,LinkedList<String> mSelectedDayEvents) {
        this.mContext = mContext;
        this.mSelectedDayEvents = mSelectedDayEvents;
    }

    @Override
    public int getCount() {
        if (mSelectedDayEvents == null){
            return 0;
        } else {
            return mSelectedDayEvents.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSelectedDayEvents == null){
            return null;
        } else {
            return mSelectedDayEvents.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new TextView(mContext);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (TextView) convertView;
        }
        imageView.setText(getItem(position).toString());
        return imageView;
    }
}
