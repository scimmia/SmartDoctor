package com.zljysoft.SmartDoctor.doctor;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.zljysoft.SmartDoctor.R;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-28
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public class StandingOrderListActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            StandingOrderListFragment list = new StandingOrderListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }


    public static class StandingOrderListFragment extends ListFragment {
        OrdersAdapter createTestAdapter(){
            LinkedList<OrderStruct> list = new LinkedList<>();
            for (int i =0;i<10;i++){
                OrderStruct orderStruct = new OrderStruct(new Date(),1,null);
                for (int j = 0;j<=i;j++){
                    OrderDetailStruct orderDetailStruct = new OrderDetailStruct("medicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicinemedicine"+j,"dosage"+j);
                    orderStruct.getDetails().add(orderDetailStruct);
                }
                list.add(orderStruct);
            }
            OrdersAdapter ordersAdapter = new OrdersAdapter(getActivity(),list);
            return ordersAdapter;
        }
        ModeCallback modeCallback;
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            getActivity().getActionBar().setSubtitle(getResources().getString(R.string.long_press_to_select));
            setHasOptionsMenu(true);
            ListView lv = getListView();
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            modeCallback = new ModeCallback();
            lv.setMultiChoiceModeListener(modeCallback);
            setListAdapter(createTestAdapter());
//            setListAdapter(new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.all_paients)));
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_order_main,menu);
        }

        @Override
        public boolean onOptionsItemSelected(android.view.MenuItem item) {
            super.onOptionsItemSelected(item);
            switch (item.getItemId()){
                case R.id.menu_order_add:
                    //todo
//                    mActionMode = getActivity().startActionMode(modeCallback);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);


            }
        }

        private class ModeCallback implements ListView.MultiChoiceModeListener {

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mActionMode = mode;
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_order_multiselect, menu);
                mode.setTitle(getResources().getString(R.string.select_orders));
//                MenuItem item = menu.add("Search");
//                item.setIcon(android.R.drawable.ic_menu_search);
//                MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS
//                        | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

                setSubtitle(mode);
                return true;
            }

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_order_stop:
                        //todo
                        Toast.makeText(getActivity(), "menu_order_stop " + getListView().getCheckedItemCount() +
                                " items", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;
                    case R.id.menu_order_cancel:
                        //todo
                        Toast.makeText(getActivity(), "menu_order_cancel " + getListView().getCheckedItemCount() +
                                " items", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;
                    case R.id.menu_order_start:
                        //todo
                        Toast.makeText(getActivity(), "menu_order_start " + getListView().getCheckedItemCount() +
                                " items", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Clicked " + item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
            }

            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                setSubtitle(mode);
            }

            private void setSubtitle(ActionMode mode) {
                final int checkedCount = getListView().getCheckedItemCount();
                switch (checkedCount) {
                    case 0:
                        mode.setSubtitle(null);
                        break;
                    default:
                        mode.setSubtitle("已选择 " + checkedCount + " 项");
                        break;
                }
            }
        }
        ActionMode mActionMode;
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("TAG", "Item clicked: " + id);
        }
        @Override
        public void onPause(){
            super.onPause();
            if (mActionMode!=null){
                mActionMode.finish();
            }
        }
    }
}