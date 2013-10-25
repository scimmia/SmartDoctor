package com.zljysoft.SmartDoctor.doctor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.zljysoft.SmartDoctor.R;
import com.zljysoft.SmartDoctor.zoomview.ZoomTextView;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-17
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class DetailsFragment extends BaseFragment {
    public DetailsFragment(String identification) {
        super(identification);
    }

    public static DetailsFragment newInstance(String identification) {
        DetailsFragment f = new DetailsFragment(identification);
        return f;
    }
    TextView mTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        ScrollView scroller = new ScrollView(getActivity());
        mTextView = new TextView(getActivity());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                4, getActivity().getResources().getDisplayMetrics());
        mTextView.setPadding(padding, padding, padding, padding);
        float zoomScale = 0.5f;// 缩放比例
        //装饰
        new ZoomTextView(mTextView, zoomScale);

        scroller.addView(mTextView);
        return scroller;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillTextView();

    }
    private void fillTextView() {
        new LoadTextTask(getActivity()).execute(getIdentification());
    }

    private class LoadTextTask extends AsyncTask<String,Object,Spanned >{
        ProgressDialog mpDialog;
        Context mContent;

        private LoadTextTask(Context mContent) {
            this.mContent = mContent;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mpDialog = new ProgressDialog(mContent);
            mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mpDialog.setMessage(mContent.getResources().getString(R.string.doctor_loading_patients));
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
        protected Spanned doInBackground(String... params) {
            //todo
            Spanned result;
            String string = null;
            switch (params[0]){
                case "入院记录":
                     string = "<h2>Welcome to the HTML editor!</h2>\n" +
                             "<p>Just type the HTML and it will be shown below.</p>\n" +
                             "\n" +
                             "<p>Lorem ipus orci <strong>luctus et ultrices posuere</strong> cubilet, tellus. <a href=\"http://www.clesto.com\">Clesto.com</a></p>\n" +
                             "\n" +
                             "<img src=\"http://www.google.se/images/google_80wht.gif\" alt=\"Google logo\">\n" +
                             "\n" +
                             "<h2>Heading in h2, som more sample text</h2>\n" +
                             "\n";
                    break;
                case "手术记录":
                     string = "\n" +
                             "<h3>Heading in h3, som more sample text</h3>\n" +
                             "\n" +
                             "<p>Nulla fal justo. Nullam posuere purus sed arcu.</p>\n" +
                             "\n" +
                             "<ul>\n" +
                             "\t<li>Nulla facilisi.</li>\n" +
                             "\t<li>Pellentesque habitant morbi</li>\n" +
                             "\t<li>Quisque vel justo.</li>\n" +
                             "\t<li>Nullam posuere purus sed arcu.</li>\n" +
                             "</ul>\n";
                    break;
                default:
                    break;
            }
            result = Html.fromHtml(string);
            return result;
        }

        @Override
        protected void onPostExecute(Spanned result) {
            super.onPostExecute(result);
            mpDialog.dismiss();
            mTextView.setText(result);
        }
    }
}