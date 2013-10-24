package com.zljysoft.SmartDoctor.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zljysoft.SmartDoctor.R;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-24
 * Time: 下午5:24
 * To change this template use File | Settings | File Templates.
 */
public class PatientsAdapter extends BaseAdapter {
    LinkedList<Object> mPatientsList;
    Context mContext;
    public PatientsAdapter(Context mContext,LinkedList<Object> mPatientsList) {
        this.mContext = mContext;
        this.mPatientsList = mPatientsList;
        if (this.mPatientsList==null){
            this.mPatientsList = new LinkedList<Object>();
        }
    }

    @Override
    public int getCount() {
        return mPatientsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPatientsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView name;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.doctor_adapter_patient, null);
            name = (TextView) convertView
                    .findViewById(R.id.tv_customer_name);
            convertView.setTag(name);
        } else {
            name = (TextView) convertView.getTag();
        }

        name.setText(getItem(position).toString());
        return convertView;
    }
}
