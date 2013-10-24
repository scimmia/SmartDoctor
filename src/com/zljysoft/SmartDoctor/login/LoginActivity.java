package com.zljysoft.SmartDoctor.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zljysoft.SmartDoctor.R;
import com.zljysoft.SmartDoctor.soaputil.DeptFromHIS;
import com.zljysoft.SmartDoctor.soaputil.SoapClient;
import com.zljysoft.SmartDoctor.soaputil.SoapRequestStruct;

import java.util.LinkedList;

public class LoginActivity extends Activity {
    public static final String LOGIN_USER_NAME = "LOGIN_USER_NAME";
    public static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";
    public static final String LOGIN_SAVE_PASSWORD = "LOGIN_SAVE_PASSWORD";

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private CheckBox mSavePasswordView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
        mUsernameView = (EditText)findViewById(R.id.login_et_username);
        mPasswordView = (EditText)findViewById(R.id.login_et_password);
        mSavePasswordView = (CheckBox)findViewById(R.id.login_cb_save_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_GO) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        findViewById(R.id.login_btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsernameView.setText(sharedPreferences.getString(LOGIN_USER_NAME,null));
        mPasswordView.setText(sharedPreferences.getString(LOGIN_PASSWORD, null));
        mSavePasswordView.setChecked(sharedPreferences.getBoolean(LOGIN_SAVE_PASSWORD, true));
    }

    public void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            SoapRequestStruct soapRequestStruct = new SoapRequestStruct();
            soapRequestStruct.setServiceUrl("http://192.168.1.34:8080/HisService.asmx");
            soapRequestStruct.setServiceNameSpace("http://tempuri.org/");
            soapRequestStruct.setMethodName("getDeptFromHIS");
            soapRequestStruct.addProperty("strAID","370783111");
            soapRequestStruct.addProperty("iType",username);

            new LoginTask().execute(soapRequestStruct);
        }
    }

    private class LoginTask extends AsyncTask<SoapRequestStruct,Void,Object> {
        private ProgressDialog mpDialog;
        @Override
        protected void onPreExecute(){
            mpDialog = new ProgressDialog(LoginActivity.this);
            mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mpDialog.setMessage(getResources().getString(R.string.login_logining));
//            mpDialog.setButton("cancel",new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    cancel(true);
//                }
//            });
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
        protected Object doInBackground(SoapRequestStruct... params) {
            String response = SoapClient.getSoapFromURL(params[0]).toString();
            Gson gson = new Gson();

            LinkedList<DeptFromHIS> listaa =
                    gson.fromJson(response,new TypeToken<LinkedList<DeptFromHIS>>(){}.getType());
            Log.e("TAG", listaa.toString());
            Log.e("TAG", listaa.getFirst().toString());

            String username = mUsernameView.getText().toString();
            String password = mPasswordView.getText().toString();
            boolean savePassword = mSavePasswordView.isChecked();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LOGIN_USER_NAME,username);
            if (savePassword){
                editor.putString(LOGIN_PASSWORD,password);
            } else {
                editor.remove(LOGIN_PASSWORD);
            }
            editor.putBoolean(LOGIN_SAVE_PASSWORD,savePassword);
            editor.commit();


            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
        @Override
        protected void onPostExecute(Object result) {
            Log.e("TAG", "onPostExecute");

            mpDialog.dismiss();
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent);
            finish();
        }
        @Override
        protected void onCancelled(){
            super.onCancelled();
        }
    }
}
