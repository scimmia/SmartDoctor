package com.zljysoft.SmartDoctor.doctor;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.zljysoft.SmartDoctor.R;
import com.zljysoft.SmartDoctor.doctor.calendar.ProgressNotesActivity;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-25
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public class PatientInfoActivity extends FragmentActivity {
    private SlidingPaneLayout mSlidingLayout;
    private ListView mList;
    private TextView mContent;

    private ActionBarHelper mActionBar;
    String[] mLists;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patientinfo);

        mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        mList = (ListView) findViewById(R.id.left_pane);
//        mContent = (TextView) findViewById(R.id.content_text);

        mSlidingLayout.setPanelSlideListener(new SliderListener());
        mSlidingLayout.openPane();
        mLists = getResources().getStringArray(R.array.doctor_main_funcation);
        mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.doctor_main_funcation)));
        mList.setOnItemClickListener(new ListItemClickListener());

        mActionBar = createActionBarHelper();
        mActionBar.init();

        mSlidingLayout.getViewTreeObserver().addOnGlobalLayoutListener(new FirstLayoutListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * The action bar up action should open the slider if it is currently closed,
         * as the left pane contains content one level up in the navigation hierarchy.
         */
//        if (item.getItemId() == android.R.id.home && !mSlidingLayout.isOpen()) {
//            mSlidingLayout.smoothSlideOpen();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This list item click listener implements very simple view switching by changing
     * the primary content text. The slider is closed when a selection is made to fully
     * reveal the content.
     */
    private class ListItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mList.setSelection(position);
            View detailsFrame = findViewById(R.id.fl_details);

            // Check what fragment is currently shown, replace if needed.
//            DetailsFragment details = (DetailsFragment)
//                    getSupportFragmentManager().findFragmentById(R.id.fl_details);
//            if (details == null || details.getShownIndex() != position) {
//                // Make new fragment to show this selection.
//                details = DetailsFragment.newInstance(position);
//
//                // Execute a transaction, replacing any existing fragment
//                // with this one inside the frame.
////                getSupportFragmentManager().beginTransaction().add(
////                        android.R.id.fl_details, details).commit();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                if (position == 0) {
//                    ft.replace(R.id.fl_details, details);
//                } else {
//                    ft.replace(R.id.fl_details, details);
//                }
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.commit();
//            }

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment;
            switch (position){
                case 0:
                case 2:
                    fragment = DetailsFragment.newInstance(mLists[position]);
                    break;
                case 1:
                    fragment = new ProgressNotesActivity.ProgressNotesFragment();
                    break;
                case 3:
                    fragment = new StandingOrderListActivity.StandingOrderListFragment();
                    break;
                default:
                    fragment = new SearchPaientAcivity.SearchPaientFragment();

            }
            if (position == 0) {
                ft.replace(R.id.fl_details, fragment);
            } else {
                ft.replace(R.id.fl_details, fragment);
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

//            mContent.setText(Shakespeare.DIALOGUE[position]);
            mActionBar.setTitle(getResources().getStringArray(R.array.doctor_main_funcation)[position]);
            mSlidingLayout.closePane();
        }
    }

    /**
     * This panel slide listener updates the action bar accordingly for each panel state.
     */
    private class SliderListener extends SlidingPaneLayout.SimplePanelSlideListener {
        @Override
        public void onPanelOpened(View panel) {
            mActionBar.onPanelOpened();
        }

        @Override
        public void onPanelClosed(View panel) {
            mActionBar.onPanelClosed();
        }
    }

    /**
     * This global layout listener is used to fire an event after first layout occurs
     * and then it is removed. This gives us a chance to configure parts of the UI
     * that adapt based on available space after they have had the opportunity to measure
     * and layout.
     */
    private class FirstLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            mActionBar.onFirstLayout();
//            mSlidingLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    /**
     * Create a compatible helper that will manipulate the action bar if available.
     */
    private ActionBarHelper createActionBarHelper() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            return new ActionBarHelperICS();
//        } else {
        return new ActionBarHelperICS();
//        }
    }

    /**
     * Stub action bar helper; this does nothing.
     */
    private class ActionBarHelper {
        public void init() {}
        public void onPanelClosed() {}
        public void onPanelOpened() {}
        public void onFirstLayout() {}
        public void setTitle(CharSequence title) {}
    }
    private class ActionBarHelperICS extends ActionBarHelper {
        private final ActionBar mActionBar;
        private CharSequence mDrawerTitle;
        private CharSequence mTitle;

        ActionBarHelperICS() {
            mActionBar = getActionBar();
        }

        @Override
        public void init() {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mTitle = mDrawerTitle = getTitle();
        }

        @Override
        public void onPanelClosed() {
            super.onPanelClosed();
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setTitle(mTitle);
        }

        @Override
        public void onPanelOpened() {
            super.onPanelOpened();
            mActionBar.setHomeButtonEnabled(false);
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setTitle(mDrawerTitle);
        }

        @Override
        public void onFirstLayout() {
            if (!mSlidingLayout.isOpen()) {
                onPanelClosed();
            } else {
                onPanelOpened();
            }
        }

        @Override
        public void setTitle(CharSequence title) {
            mTitle = title;
        }
    }
}