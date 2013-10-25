package com.zljysoft.SmartDoctor.doctor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.zljysoft.SmartDoctor.R;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-21
 * Time: 上午9:21
 * To change this template use File | Settings | File Templates.
 */
public class SearchPaientAcivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            SearchPaientFragment list = new SearchPaientFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class SearchPaientFragment extends Fragment{
        EditText editText;
        ListView mListView;
        LinkedList<Object> patientsList;
        PatientsAdapter mPatientAdapter;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.activity_doctor_search_patient, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            editText = (EditText) getActivity().findViewById(R.id.et_search_content);
            editText.setError(null);
//            editText.requestFocus();
            getActivity().findViewById(R.id.btn_search_bed).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(editText.getText())){
                        editText.setError(getString(R.string.error_field_required));
                    }
                    else {
                        //todo
                        new SearchPaientsTask().execute();
                    }
                }
            });
            mListView = (ListView) getActivity().findViewById(R.id.lv_search_result);

            patientsList = new LinkedList<Object>();
            mPatientAdapter = new PatientsAdapter(getActivity(),patientsList);
            mListView.setAdapter(mPatientAdapter);
        }
        private class SearchPaientsTask extends AsyncTask<Void,Void,Void> {
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
                // todo
                String[] m = getResources().getStringArray(R.array.all_paients);
                patientsList.clear();
                for (String n:m){
                    if (n.contains(editText.getText().toString())){
                        patientsList.add(n);
                    }
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