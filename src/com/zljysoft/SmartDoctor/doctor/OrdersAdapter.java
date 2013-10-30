package com.zljysoft.SmartDoctor.doctor;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.zljysoft.SmartDoctor.R;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-28
 * Time: 下午1:00
 * To change this template use File | Settings | File Templates.
 */
public class OrdersAdapter extends BaseAdapter {
    LinkedList<OrderStruct> mOrders;
    Context mContext;

    public OrdersAdapter(Context mContext,LinkedList<OrderStruct> mOrders) {
        this.mContext = mContext;
        this.mOrders = mOrders;
        if (this.mOrders==null){
            this.mOrders = new LinkedList<OrderStruct>();
        }
    }

    @Override
    public int getCount() {
        return mOrders.size();
    }

    @Override
    public OrderStruct getItem(int position) {
        return mOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
//        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.doctor_adapter_order, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (CheckedTextView) convertView.findViewById(R.id.tv_order_title);
            viewHolder.detailLayout = (RelativeLayout) convertView.findViewById(R.id.rl_order_detail);
            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        OrderStruct orderStruct = getItem(position);
        viewHolder.tv_title.setText(String.format("%1$tF %1$tR %2$s",orderStruct.getOrderCreateTime(),orderStruct.getOrderType()));
        TextView tv_medicineTemp = null;
        TextView tv_dosageTemp = null;
        for (OrderDetailStruct detailStruct : orderStruct.getDetails()){
            TextView tv_medicine;
            TextView tv_dosage;
            if (tv_dosageTemp == null && tv_medicineTemp == null){
                tv_medicineTemp = (TextView) convertView.findViewById(R.id.tv_order_medicine);
                tv_dosageTemp = (TextView) convertView.findViewById(R.id.tv_order_dosage);
                tv_medicineTemp.setText(detailStruct.getMedicine());
                tv_dosageTemp.setText(detailStruct.getDosage());
            }else{
                RelativeLayout relativeLayout = viewHolder.detailLayout;
                int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mContext.getResources().getDisplayMetrics());

                tv_dosage = new TextView(mContext);
                tv_dosage.setId(tv_dosageTemp.getId()+111);
                tv_dosage.setText(detailStruct.getDosage());
                RelativeLayout.LayoutParams lp_dosage =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp_dosage.addRule(RelativeLayout.BELOW,tv_dosageTemp.getId());
                lp_dosage.addRule(RelativeLayout.ALIGN_RIGHT,tv_dosageTemp.getId());
                lp_dosage.setMargins(px,px,0,px);
                tv_dosage.setLayoutParams(lp_dosage);
                relativeLayout.addView(tv_dosage,lp_dosage);
                tv_dosageTemp = tv_dosage;

                tv_medicine = new TextView(mContext);
                tv_medicine.setId(tv_medicineTemp.getId() +1);
                tv_medicine.setText(detailStruct.getMedicine());
                tv_medicine.setSingleLine(true);
                tv_medicine.setEllipsize(TextUtils.TruncateAt.END);
                RelativeLayout.LayoutParams lp_medicine =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp_medicine.addRule(RelativeLayout.LEFT_OF,tv_dosage.getId());
                lp_medicine.addRule(RelativeLayout.BELOW,tv_medicineTemp.getId());
                lp_medicine.addRule(RelativeLayout.ALIGN_LEFT,tv_medicineTemp.getId());
                lp_medicine.setMargins(0,px,px,px);
                tv_medicine.setLayoutParams(lp_medicine);
                relativeLayout.addView(tv_medicine,lp_medicine);
//                Log.e("TAG",tv_medicineTemp.getId()+"\t"+tv_medicine.getId());
                tv_medicineTemp = tv_medicine;
            }
        }
//        convertView.setLayoutParams(
//                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return convertView;
    }
    static class ViewHolder {
        CheckedTextView tv_title;
        RelativeLayout detailLayout;
    }
}
//public class OrdersAdapter extends ArrayAdapter<OrderStruct> {
////    LinkedList<OrderStruct> mOrders;
//    Context mContext;
//
//    public OrdersAdapter(Context mContext,LinkedList<OrderStruct> mOrders) {
//        super(mContext,0,0,mOrders);
//        this.mContext = mContext;
////        this.mOrders = mOrders;
////        if (this.mOrders==null){
////            this.mOrders = new LinkedList<OrderStruct>();
////        }
//    }
//
////    @Override
////    public int getCount() {
////        return mOrders.size();
////    }
////
////    @Override
////    public OrderStruct getItem(int position) {
////        return mOrders.get(position);
////    }
////
////    @Override
////    public long getItemId(int position) {
////        return position;
////    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
////        if (convertView == null){
//        convertView = LayoutInflater.from(mContext).inflate(R.layout.doctor_adapter_order, null);
//        viewHolder = new ViewHolder();
//        viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_order_title);
//        viewHolder.detailLayout = (RelativeLayout) convertView.findViewById(R.id.rl_order_detail);
//        convertView.setTag(viewHolder);
////        } else {
////            viewHolder = (ViewHolder) convertView.getTag();
////        }
//        OrderStruct orderStruct = getItem(position);
//        viewHolder.tv_title.setText(String.format("%1$tF %1$tR %2$s",orderStruct.getOrderCreateTime(),orderStruct.getOrderType()));
//        TextView tv_medicineTemp = null;
//        TextView tv_dosageTemp = null;
//        for (OrderDetailStruct detailStruct : orderStruct.getDetails()){
//            TextView tv_medicine;
//            TextView tv_dosage;
//            if (tv_dosageTemp == null && tv_medicineTemp == null){
//                tv_medicineTemp = (TextView) convertView.findViewById(R.id.tv_order_medicine);
//                tv_dosageTemp = (TextView) convertView.findViewById(R.id.tv_order_dosage);
//                tv_medicineTemp.setText(detailStruct.getMedicine());
//                tv_dosageTemp.setText(detailStruct.getDosage());
//            }else{
//                RelativeLayout relativeLayout = viewHolder.detailLayout;
//                tv_medicine = new TextView(mContext);
//                tv_medicine.setId(tv_medicineTemp.getId() +1);
//                tv_medicine.setText(detailStruct.getMedicine());
//                RelativeLayout.LayoutParams lp_medicine =
//                        new RelativeLayout.LayoutParams(
//                                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                lp_medicine.addRule(RelativeLayout.BELOW,tv_medicineTemp.getId());
//                lp_medicine.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                tv_medicine.setLayoutParams(lp_medicine);
//                relativeLayout.addView(tv_medicine,lp_medicine);
//                Log.e("TAG",tv_medicineTemp.getId()+"\t"+tv_medicine.getId());
//                tv_medicineTemp = tv_medicine;
//
//                tv_dosage = new TextView(mContext);
//                tv_dosage.setId(tv_dosageTemp.getId()+1);
//                tv_dosage.setText(detailStruct.getDosage());
//                RelativeLayout.LayoutParams lp_dosage =
//                        new RelativeLayout.LayoutParams(
//                                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                lp_dosage.addRule(RelativeLayout.BELOW,tv_dosageTemp.getId());
//                lp_dosage.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                tv_dosage.setLayoutParams(lp_dosage);
//                relativeLayout.addView(tv_dosage,lp_dosage);
//                tv_dosageTemp = tv_dosage;
////                parent.addView(relativeLayout);
//            }
//        }
////        convertView.setLayoutParams(
////                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        return convertView;
//    }
//    static class ViewHolder {
//        TextView tv_title;
//        RelativeLayout detailLayout;
//    }
//}