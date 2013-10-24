package com.zljysoft.SmartDoctor.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.zljysoft.SmartDoctor.R;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-18
 * Time: 下午1:18
 * To change this template use File | Settings | File Templates.
 */
public class LoadPaientsActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            LoadPaientsListFragment list = new LoadPaientsListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }


    public static class LoadPaientsListFragment extends ListFragment{
        String[] patientsFetched;
        LinkedList<Object> patientsList;
        int patientListType = 0;
        PatientsAdapter mPatientAdapter;
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Bundle b=getArguments();
            if (b!=null){
                patientListType = b.getInt("PATIENT_LIST_TYPE",0);
            }
            setEmptyText("无病人信息");

            // We have a menu item to show in action bar.
            setHasOptionsMenu(true);


            patientsList = new LinkedList<Object>();
            mPatientAdapter = new PatientsAdapter(getActivity(),patientsList);
            setListAdapter(mPatientAdapter);
            // Start out with a progress indicator.
            new LoadPaientsTask().execute();
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Place an action bar item for searching.
            MenuItem item = menu.add("Search");
            item.setIcon(android.R.drawable.ic_menu_search);
            MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS
                    | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            final View searchView = SearchViewCompat.newSearchView(getActivity());
            if (searchView != null) {
                SearchViewCompat.setOnQueryTextListener(searchView,
                        new SearchViewCompat.OnQueryTextListenerCompat() {
                            @Override
                            public boolean onQueryTextChange(String newText) {
                                patientsList.clear();
                                if (TextUtils.isEmpty(newText)) {
                                    for(String patient:patientsFetched){
                                        patientsList.add(patient);
                                    }
                                }else{
                                    for(String patient:patientsFetched){
                                        if (patient.contains(newText)){
                                            patientsList.add(patient);
                                        }
                                    }
                                }
                                mPatientAdapter.notifyDataSetChanged();
                                return true;
                            }
                        });
                SearchViewCompat.setOnCloseListener(searchView,
                        new SearchViewCompat.OnCloseListenerCompat() {
                            @Override
                            public boolean onClose() {
                                if (!TextUtils.isEmpty(SearchViewCompat.getQuery(searchView))) {
                                    SearchViewCompat.setQuery(searchView, null, true);
                                }
                                return true;
                            }

                        });
                MenuItemCompat.setActionView(item, searchView);
            }
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            //todo
            Log.i("FragmentComplexList", "Item clicked: " + id);
        }
        private class LoadPaientsTask extends AsyncTask<Void,Void,Void> {
            private ProgressDialog mpDialog;
            @Override
            protected void onPreExecute(){
                mpDialog = new ProgressDialog(getActivity());
                mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
                mpDialog.setMessage(getResources().getString(R.string.doctor_loading_patients));
                mpDialog.setCancelable(true);

                mpDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        cancel(true);
                    }
                });
                mpDialog.show();
            }

            @Override
            protected Void doInBackground(Void... p) {
                switch (patientListType){
                    case 1://all
                        patientsFetched = getResources().getStringArray(R.array.all_paients);
                        break;
                    case 0:
                    default:
                        patientsFetched = getResources().getStringArray(R.array.my_paients);
                }
                patientsList.clear();
                for(String patient:patientsFetched){
                    patientsList.add(patient);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                Log.e("TAG", "onPostExecute");
                mPatientAdapter.notifyDataSetChanged();
                mpDialog.dismiss();

            }
            @Override
            protected void onCancelled(){
                super.onCancelled();
            }
        }

    }

}