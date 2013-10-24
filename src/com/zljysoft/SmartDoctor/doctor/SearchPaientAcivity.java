package com.zljysoft.SmartDoctor.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            getActivity().findViewById(R.id.btn_search_bed).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText = (EditText) getActivity().findViewById(R.id.et_search_content);
                    String searchContent = editText.getText().toString();
                    if (searchContent.isEmpty()){
                        editText.setError(getString(R.string.error_field_required));
                    }
                    else {
                        //todo
                        new SearchPaientsTask().execute();
                    }
                }
            });
            mListView = (ListView) getActivity().findViewById(R.id.lv_search_result);
//            String[] paientsTemp = getResources().getStringArray(R.array.all_paients);
//            LinkedList<String> data = new LinkedList<String>();
//            for (String s :paientsTemp){
//                data.add(s);
//            }
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
                String[] m = getResources().getStringArray(R.array.all_paients);
                patientsList.clear();
                for (String n:m){
                    if (n.contains(editText.getText().toString())){
                        patientsList.add(n);
                    }
                }
//                patientsList = new LinkedList<Object>();
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

    public static class SearchPaientLoader extends AsyncTaskLoader<LinkedList<String>> {
        String mContent;
        String[] mPatients;
        LinkedList<String> result;
        public SearchPaientLoader(Context context,String content,String[] patients) {
            super(context);
            mContent = content;

            mPatients = patients;
        }

        @Override
        public LinkedList<String> loadInBackground() {
            //todo
            result = new LinkedList<String>();
            for(String paient : mPatients){
                if (paient.contains(mContent)){
                   result.add(paient);
                }
            }

            return result;  //To change body of implemented methods use File | Settings | File Templates.
        }

        /**
         * Handles a request to start the Loader.
         */
        @Override
        protected void onStartLoading() {
            if (result !=null) {
                // If we currently have a result available, deliver it
                // immediately.
                deliverResult(result);
            }

            if (result == null) {
                // If the data has changed since the last time it was loaded
                // or is not currently available, start a load.
                forceLoad();
            }
        }

        /**
         * Handles a request to stop the Loader.
         */
        @Override
        protected void onStopLoading() {
            // Attempt to cancel the current load task if possible.
            cancelLoad();
        }

        /**
         * Handles a request to cancel a load.
         */
        @Override
        public void onCanceled(LinkedList<String> apps) {
            super.onCanceled(apps);

            // At this point we can release the resources associated with 'apps'
            // if needed.
            onReleaseResources(apps);
        }

        /**
         * Handles a request to completely reset the Loader.
         */
        @Override
        protected void onReset() {
            super.onReset();

            // Ensure the loader is stopped
            onStopLoading();

            // At this point we can release the resources associated with 'apps'
            // if needed.
            if (result != null) {
                onReleaseResources(result);
                result = null;
            }


        }

        /**
         * Helper function to take care of releasing resources associated
         * with an actively loaded data set.
         */
        protected void onReleaseResources(LinkedList<String> apps) {
            // For a simple List<> there is nothing to do.  For something
            // like a Cursor, we would close it here.
        }
    }


}