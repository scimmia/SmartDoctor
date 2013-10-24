package com.zljysoft.SmartDoctor.doctor;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import com.zljysoft.SmartDoctor.R;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-18
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */
public class VisitPatientsActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tabs);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        String[] titles = this.getResources().getStringArray(R.array.doctor_maina_funcation);
        Bundle bundle = new Bundle();
        bundle.putInt("PATIENT_LIST_TYPE",1);
        mTabHost.addTab(mTabHost.newTabSpec(titles[0]).setIndicator(titles[0],this.getResources().getDrawable(
                android.R.drawable.ic_dialog_info)),
                LoadPaientsActivity.LoadPaientsListFragment.class, bundle);
        mTabHost.addTab(mTabHost.newTabSpec(titles[1]).setIndicator(titles[1],this.getResources().getDrawable(
                android.R.drawable.ic_dialog_info)),
                LoadPaientsActivity.LoadPaientsListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(titles[2]).setIndicator(titles[2],this.getResources().getDrawable(
                android.R.drawable.ic_search_category_default)),
                SearchPaientAcivity.SearchPaientFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(titles[3]).setIndicator(titles[3],this.getResources().getDrawable(
                android.R.drawable.ic_menu_camera)),
                SearchPaientQRCodeActivity.SearchPaientQRCodeFragment.class, null);
    }
}