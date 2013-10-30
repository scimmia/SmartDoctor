package com.zljysoft.SmartDoctor.doctor.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zljysoft.SmartDoctor.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-30
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
public class CalendarAdapter extends BaseAdapter {
    Context mContext;
    HashMap<GregorianCalendar,LinkedList<String>> events;
    LinkedList<GregorianCalendar> daysList;
    GregorianCalendar selectedDay;


    public CalendarAdapter(Context mContext, HashMap<GregorianCalendar, LinkedList<String>> events, LinkedList<GregorianCalendar> daysList, GregorianCalendar selectedDay) {
        this.mContext = mContext;
        this.events = events;
        this.daysList = daysList;
        this.selectedDay = selectedDay;
    }

    @Override
    public int getCount() {
        return daysList.size();
    }

    @Override
    public Object getItem(int position) {
        return daysList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.doctor_adapter_progress_note, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.date);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.date_icon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundResource(android.R.color.white);
        GregorianCalendar gregorianCalendarTemp = (GregorianCalendar) getItem(position);
        viewHolder.textView.setText(""+gregorianCalendarTemp.get(Calendar.DAY_OF_MONTH));
        if (gregorianCalendarTemp.get(Calendar.MONTH)!=selectedDay.get(Calendar.MONTH)){
            viewHolder.textView.setTextColor(Color.WHITE);
        }else {
            if (gregorianCalendarTemp.equals(selectedDay)){
//                setSelected(convertView);
                convertView.setBackgroundResource(android.R.color.holo_blue_light);
            }
            viewHolder.textView.setTextColor(Color.BLUE);
        }
        if (events.containsKey(gregorianCalendarTemp)){
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imageView.setVisibility(View.GONE);
        }

        return convertView;
    }
    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
