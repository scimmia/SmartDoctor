package com.zljysoft.SmartDoctor.doctor.calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import com.zljysoft.SmartDoctor.R;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-30
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class ProgressNotesActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            ProgressNotesFragment list = new ProgressNotesFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class ProgressNotesFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.activity_doctor_progress_note, container, false);
        }
        TextView mTextView;
        GridView mGridView;
        ListView mEventList;

        HashMap<String,LinkedList<String>> eventsMap;

        LinkedList<GregorianCalendar> daysList;
        CalendarAdapter customerCalendarAdapter;
        GregorianCalendar selectedDay;

        LinkedList<String> mSelectedDayEvents;
        CalendarEventAdapter mEventsAdater;
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            getActivity().findViewById(R.id.header).setOnClickListener(null);
            mTextView = (TextView) getActivity().findViewById(R.id.customer_title);
            mGridView = (GridView) getActivity().findViewById(R.id.customer_gridview);
//            mEventList = (ListView)getActivity().findViewById(R.id.customer_listview);

            mEventList = (ListView)getActivity().findViewById(android.R.id.list);
            TextView emptyText = (TextView)getActivity().findViewById(android.R.id.empty);
            mEventList.setEmptyView(emptyText);

            eventsMap = new HashMap<String, LinkedList<String>>();
            daysList = new LinkedList<GregorianCalendar>();
            selectedDay = (GregorianCalendar) GregorianCalendar.getInstance();
            mTextView.setText(android.text.format.DateFormat.format("yyyy MMMM", selectedDay));
            updateCalendarList();
            customerCalendarAdapter = new CalendarAdapter(getActivity(),eventsMap,daysList,selectedDay);
            mSelectedDayEvents = new LinkedList<String>();
            mEventsAdater = new CalendarEventAdapter(getActivity(),mSelectedDayEvents);

            mGridView.setAdapter(customerCalendarAdapter);
            mEventList.setAdapter(mEventsAdater);
            getActivity().findViewById(R.id.customer_previous).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preMonth();
                    mTextView.setText(android.text.format.DateFormat.format("yyyy MMMM", selectedDay));
                    customerCalendarAdapter.notifyDataSetChanged();
                }
            });
            getActivity().findViewById(R.id.customer_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextMonth();
                    mTextView.setText(android.text.format.DateFormat.format("yyyy MMMM", selectedDay));
                    customerCalendarAdapter.notifyDataSetChanged();
                }
            });
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    GregorianCalendar temp = daysList.get(position);
                    if (DataUtil.compareSameDay(temp,selectedDay)==0){
                        return;
                    }
                    int result = DataUtil.compareSameMonth(temp,selectedDay);
                    selectedDay.set(temp.get(Calendar.YEAR),temp.get(Calendar.MONTH),temp.get(Calendar.DATE));
                    if (result!=0){
                        updateCalendarList();
                    }
                    testGeneraEvents();
                    customerCalendarAdapter.notifyDataSetChanged();

                }
            });
        }
        public static void log(String s){
//        System.out.println(s);
            Log.e("TAG", s) ;
        }

        public static void log(Calendar calendar){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String today = df.format(calendar.getTime());
            log(today);
        }
        public void preMonth(){
            if (selectedDay.get(GregorianCalendar.MONTH) == selectedDay
                    .getActualMinimum(GregorianCalendar.MONTH)) {
                selectedDay.set((selectedDay.get(GregorianCalendar.YEAR) - 1),
                        selectedDay.getActualMaximum(GregorianCalendar.MONTH), 1);
            } else {
                selectedDay.set(GregorianCalendar.MONTH,
                        selectedDay.get(GregorianCalendar.MONTH) - 1);
                selectedDay.set(GregorianCalendar.DATE,1);
            }
            updateCalendarList();
        }
        public void nextMonth(){
            if (selectedDay.get(GregorianCalendar.MONTH) == selectedDay
                    .getActualMaximum(GregorianCalendar.MONTH)) {
                selectedDay.set((selectedDay.get(GregorianCalendar.YEAR) + 1),
                        selectedDay.getActualMinimum(GregorianCalendar.MONTH), 1);
            } else {
                selectedDay.set(GregorianCalendar.MONTH,
                        selectedDay.get(GregorianCalendar.MONTH) + 1);
                selectedDay.set(GregorianCalendar.DATE,1);
            }
            updateCalendarList();
        }

        public void updateCalendarList(){
            mTextView.setText(android.text.format.DateFormat.format("yyyy MMMM", selectedDay));
            GregorianCalendar firstDay = (GregorianCalendar) selectedDay.clone();
            firstDay.set(GregorianCalendar.WEEK_OF_MONTH ,1);
            firstDay.set(GregorianCalendar.DAY_OF_WEEK, 1);
            log("--------------");
            log(firstDay);
            log(selectedDay);
            daysList.clear();
            daysList.add(firstDay);
            int maxDays = 7*selectedDay.getActualMaximum(Calendar.WEEK_OF_MONTH);
            for (int i = 1;i<maxDays;i++){
                GregorianCalendar temp = (GregorianCalendar) firstDay.clone();
                temp.add(Calendar.DATE,i);
                daysList.add(temp);
            }
        }

        public void testGeneraEvents(){
            GregorianCalendar firstDay = (GregorianCalendar) selectedDay.clone();
            firstDay.set(GregorianCalendar.WEEK_OF_MONTH ,1);
            firstDay.set(GregorianCalendar.DAY_OF_WEEK, 1);
            int maxDays = 7*selectedDay.getActualMaximum(Calendar.WEEK_OF_MONTH);
            for (int i = 1;i<maxDays;i++){
                GregorianCalendar temp = (GregorianCalendar) firstDay.clone();
                temp.add(Calendar.DATE,i);

                int m = new Random().nextInt(5);
                if (m==3){
                    LinkedList<String> events = new LinkedList<String>();
                    int n = new Random().nextInt(10);
                    for (int h =0;h<n;h++){
                        events.add("event:"+h);
                    }
                    if (!eventsMap.containsKey(DataUtil.transferCalendarToString(temp)))
                        eventsMap.put(DataUtil.transferCalendarToString(temp),events);
                }
            }
            mSelectedDayEvents.clear();
            if (eventsMap.get(DataUtil.transferCalendarToString(selectedDay))!=null)
                mSelectedDayEvents.addAll(eventsMap.get(DataUtil.transferCalendarToString(selectedDay)));
            mEventsAdater.notifyDataSetChanged();
        }

    }

}